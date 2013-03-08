package com.chase.framerwork.interceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.XWorkConstants;
import com.opensymphony.xwork2.conversion.impl.InstantiatingNullHandler;
import com.opensymphony.xwork2.conversion.impl.XWorkConverter;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.DefaultWorkflowInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.interceptor.NoParameters;
import com.opensymphony.xwork2.interceptor.ParameterNameAware;
import com.opensymphony.xwork2.interceptor.ParametersInterceptor;
import com.opensymphony.xwork2.util.ArrayUtils;
import com.opensymphony.xwork2.util.ClearableValueStack;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.opensymphony.xwork2.util.MemberAccessValueStack;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.util.reflection.ReflectionContextState;

/**
 * 下一代属性驱动拦截器
 * @author Chase
 *
 */
public class ParametersNgInterceptor extends MethodFilterInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(ParametersNgInterceptor.class);

    protected static final int PARAM_NAME_MAX_LENGTH = 100;

    private int paramNameMaxLength = PARAM_NAME_MAX_LENGTH;

    protected boolean ordered = false;
    protected Set<Pattern> excludeParams = Collections.emptySet();
    protected Set<Pattern> acceptParams = Collections.emptySet();

    private boolean devMode = false;

    // Allowed names of parameters
    private String acceptedParamNames = "\\w+((\\.\\w+)|(\\[\\d+\\])|(\\(\\d+\\))|(\\['\\w+'\\])|(\\('\\w+'\\)))*";
    private Pattern acceptedPattern = Pattern.compile(acceptedParamNames);

    private ValueStackFactory valueStackFactory;

    @Inject
    public void setValueStackFactory(ValueStackFactory valueStackFactory) {
        this.valueStackFactory = valueStackFactory;
    }

    @Inject(XWorkConstants.DEV_MODE)
    public void setDevMode(String mode) {
        devMode = "true".equals(mode);
    }

	public void setAcceptParamNames(String commaDelim) {
        Collection<String> acceptPatterns = ArrayUtils.asCollection(commaDelim);
        if (acceptPatterns != null) {
            acceptParams = new HashSet<Pattern>();
            for (String pattern : acceptPatterns) {
                acceptParams.add(Pattern.compile(pattern));
            }
        }
    }

    public void setParamNameMaxLength(int paramNameMaxLength) {
        this.paramNameMaxLength = paramNameMaxLength;
    }

    static private int countOGNLCharacters(String s) {
        int count = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == '.' || c == '[') count++;
        }
        return count;
    }

    static final Comparator<String> rbCollator = new Comparator<String>() {
        public int compare(String s1, String s2) {
            int l1 = countOGNLCharacters(s1),
                l2 = countOGNLCharacters(s2);
            return l1 < l2 ? -1 : (l2 < l1 ? 1 : s1.compareTo(s2));
        }

    };

    @Override
    public String doIntercept(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();
        if (!(action instanceof NoParameters)) {
            ActionContext ac = invocation.getInvocationContext();
            final Map<String, Object> parameters = retrieveParameters(ac);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Setting params " + getParameterLogMap(parameters));
            }

            if (parameters != null) {
                Map<String, Object> contextMap = ac.getContextMap();
                try {
                    ReflectionContextState.setCreatingNullObjects(contextMap, true);
                    ReflectionContextState.setDenyMethodExecution(contextMap, true);
                    ReflectionContextState.setReportingConversionErrors(contextMap, true);

                    ValueStack stack = ac.getValueStack();
                    setParameters(action, stack, parameters);
                } finally {
                    ReflectionContextState.setCreatingNullObjects(contextMap, false);
                    ReflectionContextState.setDenyMethodExecution(contextMap, false);
                    ReflectionContextState.setReportingConversionErrors(contextMap, false);
                }
            }
        }
        return invocation.invoke();
    }

    protected Map<String, Object> retrieveParameters(ActionContext ac) {
        return ac.getParameters();
    }

    protected void addParametersToContext(ActionContext ac, Map<String, Object> newParams) {
    }

    protected void setParameters(Object action, ValueStack stack, final Map<String, Object> parameters) {
        ParameterNameAware parameterNameAware = (action instanceof ParameterNameAware)
                ? (ParameterNameAware) action : null;

        Map<String, Object> params;
        Map<String, Object> acceptableParameters;
        if (ordered) {
            params = new TreeMap<String, Object>(getOrderedComparator());
            acceptableParameters = new TreeMap<String, Object>(getOrderedComparator());
            params.putAll(parameters);
        } else {
            params = new TreeMap<String, Object>(parameters);
            acceptableParameters = new TreeMap<String, Object>();
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String name = entry.getKey();

            boolean acceptableName = acceptableName(name)
                    || (parameterNameAware != null && parameterNameAware.acceptableParameterName(name));

            if (acceptableName) {
            	// start 这里是关键部分  add by Chase 2013-3-8
            	Object value = entry.getValue();
            	if (value instanceof String[]) {
    				String[] values = (String[]) value;
    				for (int i = 0; i < values.length; i++) {
    					values[i] = values[i].trim();
    				}
    				acceptableParameters.put(name, values);
    			// end 这里是关键部分
    			} else {
    				acceptableParameters.put(name, entry.getValue());
    			}
            }
        }

        ValueStack newStack = valueStackFactory.createValueStack(stack);
        boolean clearableStack = newStack instanceof ClearableValueStack;
        if (clearableStack) {
            //if the stack's context can be cleared, do that to prevent OGNL
            //from having access to objects in the stack, see XW-641
            ((ClearableValueStack)newStack).clearContextValues();
            Map<String, Object> context = newStack.getContext();
            ReflectionContextState.setCreatingNullObjects(context, true);
            ReflectionContextState.setDenyMethodExecution(context, true);
            ReflectionContextState.setReportingConversionErrors(context, true);

            //keep locale from original context
            context.put(ActionContext.LOCALE, stack.getContext().get(ActionContext.LOCALE));
        }

        boolean memberAccessStack = newStack instanceof MemberAccessValueStack;
        if (memberAccessStack) {
            //block or allow access to properties
            //see WW-2761 for more details
            MemberAccessValueStack accessValueStack = (MemberAccessValueStack) newStack;
            accessValueStack.setAcceptProperties(acceptParams);
            accessValueStack.setExcludeProperties(excludeParams);
        }

        for (Map.Entry<String, Object> entry : acceptableParameters.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            try {
                newStack.setParameter(name, value);
            } catch (RuntimeException e) {
                if (devMode) {
                    String developerNotification = LocalizedTextUtil.findText(ParametersInterceptor.class, "devmode.notification", ActionContext.getContext().getLocale(), "Developer Notification:\n{0}", new Object[]{
                             "Unexpected Exception caught setting '" + name + "' on '" + action.getClass() + ": " + e.getMessage()
                    });
                    LOG.error(developerNotification);
                    if (action instanceof ValidationAware) {
                        ((ValidationAware) action).addActionMessage(developerNotification);
                    }
                }
            }
        }

        if (clearableStack && (stack.getContext() != null) && (newStack.getContext() != null))
            stack.getContext().put(ActionContext.CONVERSION_ERRORS, newStack.getContext().get(ActionContext.CONVERSION_ERRORS));

        addParametersToContext(ActionContext.getContext(), acceptableParameters);
    }

    /**
     * Gets an instance of the comparator to use for the ordered sorting.  Override this
     * method to customize the ordering of the parameters as they are set to the
     * action.
     *
     * @return A comparator to sort the parameters
     */
    protected Comparator<String> getOrderedComparator() {
        return rbCollator;
    }

    protected String getParameterLogMap(Map<String, Object> parameters) {
        if (parameters == null) {
            return "NONE";
        }

        StringBuilder logEntry = new StringBuilder();
        for (Map.Entry entry : parameters.entrySet()) {
            logEntry.append(String.valueOf(entry.getKey()));
            logEntry.append(" => ");
            if (entry.getValue() instanceof Object[]) {
                Object[] valueArray = (Object[]) entry.getValue();
                logEntry.append("[ ");
                if (valueArray.length > 0 ) {
                    for (int indexA = 0; indexA < (valueArray.length - 1); indexA++) {
                        Object valueAtIndex = valueArray[indexA];
                        logEntry.append(String.valueOf(valueAtIndex));
                        logEntry.append(", ");
                    }
                    logEntry.append(String.valueOf(valueArray[valueArray.length - 1]));
                }
                logEntry.append(" ] ");
            } else {
                logEntry.append(String.valueOf(entry.getValue()));
            }
        }

        return logEntry.toString();
    }

    protected boolean acceptableName(String name) {
        return isWithinLengthLimit(name) && isAccepted(name) && !isExcluded(name);
    }

	protected boolean isWithinLengthLimit( String name ) {
        boolean matchLength = name.length() <= paramNameMaxLength;
        if (!matchLength) {
            notifyDeveloper("Parameter [#0] is too long, allowed length is [#1]", name, String.valueOf(paramNameMaxLength));
        }
        return matchLength;
	}

    protected boolean isAccepted(String paramName) {
        if (!this.acceptParams.isEmpty()) {
            for (Pattern pattern : acceptParams) {
                Matcher matcher = pattern.matcher(paramName);
                if (matcher.matches()) {
                    return true;
                }
            }
            notifyDeveloper("Parameter [#0] didn't match acceptParams list of patterns!", paramName);
            return false;
        } else {
            boolean matches = acceptedPattern.matcher(paramName).matches();
            if (!matches) {
                notifyDeveloper("Parameter [#0] didn't match acceptedPattern pattern!", paramName);
            }
            return matches;
        }
    }

    protected boolean isExcluded(String paramName) {
        if (!this.excludeParams.isEmpty()) {
            for (Pattern pattern : excludeParams) {
                Matcher matcher = pattern.matcher(paramName);
                if (matcher.matches()) {
                    notifyDeveloper("Parameter [#0] is on the excludeParams list of patterns!", paramName);
                    return true;
                }
            }
        }
        notifyDeveloper("Parameter [#0] is not on the excludeParams list of patterns and will be appended to action!", paramName);
        return false;
    }

    private void notifyDeveloper(String message, String... parameters) {
        if (devMode) {
            LOG.warn(message, parameters);
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug(message, parameters);
            }
        }
    }

    /**
     * Whether to order the parameters or not
     *
     * @return True to order
     */
    public boolean isOrdered() {
        return ordered;
    }

    /**
     * Set whether to order the parameters by object depth or not
     *
     * @param ordered True to order them
     */
    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    /**
     * Gets a set of regular expressions of parameters to remove
     * from the parameter map
     *
     * @return A set of compiled regular expression patterns
     */
    protected Set getExcludeParamsSet() {
        return excludeParams;
    }

    /**
     * Sets a comma-delimited list of regular expressions to match
     * parameters that should be removed from the parameter map.
     *
     * @param commaDelim A comma-delimited list of regular expressions
     */
    public void setExcludeParams(String commaDelim) {
        Collection<String> excludePatterns = ArrayUtils.asCollection(commaDelim);
        if (excludePatterns != null) {
            excludeParams = new HashSet<Pattern>();
            for (String pattern : excludePatterns) {
                excludeParams.add(Pattern.compile(pattern));
            }
        }
    }

}
