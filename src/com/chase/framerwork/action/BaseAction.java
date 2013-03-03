package com.chase.framerwork.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.chase.framerwork.bean.Pager;
import com.chase.framerwork.common.ResponseCode;
import com.chase.framerwork.util.CookieUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = -5406156774433594044L;

	protected static final String STATUS = "status";// 操作状态
	protected static final String MESSAGE = "message";// 操作消息
	
	protected static final String ADD_INPUT = "addInput";
	protected static final String EDIT_INPUT = "editInput";
	protected static final String REMOVE_INPUT = "removeInput";
	protected static final String FIND_INPUT = "findInput";
	
	protected static final String ADD = "add";
	protected static final String CREATE = "create";
	protected static final String NEW = "new";
	
	protected static final String UPDATE = "update";
	protected static final String MARGRE = "update";
	protected static final String EDIT = "edit";
	
	protected static final String DELETE = "delete";
	protected static final String REMOVE = "remove";
	
	protected static final String FIND = "find";
	protected static final String FIND_ALL = "findAll";
	protected static final String FIND_BY_ID = "findById";
	protected static final String FIND_BY_LIKE = "findByLike";
	protected static final String FIND_BY_NAME = "findByName";
	
	protected static final String LIST = "list";
	protected static final String ITERATOR = "iterator";
	
	protected static final String LOAD = "load";
	protected static final String LOAD_BY_ID = "loadById";
	protected static final String LOAD_BY_LIKE = "loadByLike";
	protected static final String LOAD_BY_NAME = "loadByName";
	
	protected static final String SEARCH = "search";
	
	protected static final String AJAX = "ajax";
	
	protected static final String ROWS = "rows";
	protected static final String TOTAL = "total";
	
	// 查询方法
	protected enum QUERY_METHOD {
		findAll,findById,findByLike,findByName,findBy
	}
	
	// 响应码
	private ResponseCode responseCode;
	
	// 获取Request
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	// 获取Response
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	// 获取ServletContext
	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}
	
	// 获取Attribute
	protected Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	// 设置Attribute
	protected void setAttribute(String name, Object value) {
		ServletActionContext.getRequest().setAttribute(name, value);
	}
	
	// 获取Parameter
	protected String getParameter(String name) {
		return ServletActionContext.getRequest().getParameter(name);
	}

	// 获取Parameter数组
	protected String[] getParameterValues(String name) {
		return ServletActionContext.getRequest().getParameterValues(name);
	}
	
	// 获取Session
	protected Object getSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(name);
	}
	
	// 设置Session
	protected void setSession(String name, Object value) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(name, value);
	}

	// 移除Session
	protected void removeSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.remove(name);
	}
	
	// 获取Cookie
	protected String getCookie(String name) {
		return CookieUtil.getCookie(ServletActionContext.getRequest(), name);
	}
	
	// 设置Cookie
	protected void setCookie(String name, String value) {
 		CookieUtil.addCookie(ServletActionContext.getRequest(), ServletActionContext.getResponse(), name, value, ServletActionContext.getRequest().getContextPath() + "/");
	}
	
	// 设置Cookie
	protected void setCookie(String name, String value, Integer maxAge) {
 		CookieUtil.addCookie(ServletActionContext.getRequest(), ServletActionContext.getResponse(), name, value, ServletActionContext.getRequest().getContextPath() + "/", maxAge, null, null);
	}

	// 移除Cookie
	protected void removeCookie(String name) {
		CookieUtil.removeCookie(ServletActionContext.getRequest(), ServletActionContext.getResponse(), name, ServletActionContext.getRequest().getContextPath() + "/");
	}
	
	// 获取真实路径
	protected String getRealPath(String path) {
		return ServletActionContext.getServletContext().getRealPath(path);
	}
	
	// 获取ContextPath
	protected String getContextPath() {
		return ServletActionContext.getRequest().getContextPath();
	}
	
	// AJAX输出
	protected void ajax(String content, String contentType) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void outJSONList(Pager pager) throws IOException {
		
		JSONObject json = new JSONObject();
		json.put(ROWS, pager.getResult());
		json.put(TOTAL, pager.getTotalCount());
		
		outJSON(json);
	}

	protected void outJSON(JSONObject json) throws IOException {
		getResponse().getWriter().print(json.toString());
	}
	
	// 设置响应码
	protected void setResponseCode(ResponseCode responseCode){
		this.responseCode = responseCode;
	}
	
	// 得到响应码
	public ResponseCode getResponseCode() {
		return responseCode;
	}

}
