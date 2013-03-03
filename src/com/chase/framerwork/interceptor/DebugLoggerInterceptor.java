package com.chase.framerwork.interceptor;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 一般调试信息拦截器
 * author：  Chase ↗
 * desc:
 * date： Mar 18, 201211:23:29 AM
 */
public class DebugLoggerInterceptor extends AbstractInterceptor {
	
	
	private static Logger logger = LogManager.getLogger(DebugLoggerInterceptor.class.getName());  
	
	/**
	 * fields
	 */
	private static final long serialVersionUID = -974129429866051172L;
 
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		long begin = System.currentTimeMillis();
		
		String result = null;
		
		try {
			result = invocation.invoke();
		} catch (Exception e) {
			logger.catching(e);
			
			throw e;
		}
		
		
		long end = System.currentTimeMillis();
		
		Class<?> clazz = invocation.getAction().getClass();
		
		logger.trace("\r\n" + clazz.getName() + "." + invocation.getProxy().getMethod() + "() 耗时 : [" + (end - begin) + "] 毫秒.");
		
		return result;
	}

}
