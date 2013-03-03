package com.chase.framerwork.filter;


import java.io.IOException;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sun.reflect.Reflection;


/**
 * 防sql注入过滤器
 */
public class SqlFilter implements Filter {
	
	public static final Logger logger = LogManager.getLogger(Reflection.getCallerClass(1));
	
	//非法字符
	private static final String LEGAL_CHARS="!,-,',<,>,and,exec,insert,select,%20,delete,update,count,*,%,chr,mid,master,truncate,char,like,declare,&,#,(,),/**/,=,script";
	
	
	public void init(FilterConfig cfg) throws ServletException {
		
	}
	
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		//获取所有的表单参数
		Iterator<String[]> values = req.getParameterMap().values().iterator();
		while(values.hasNext()){
		   String[] parameters = values.next();
		   for (String parameter : parameters) {
			   if(isLegal(parameter)){
				   request.setAttribute("msgStr", "请不要输入非法参数："+ parameter +" !");
				   res.sendRedirect(req.getContextPath()+"/commonnew/error/error.jsp");
			       return;
			   }
		   }
		}
		chain.doFilter(request, response);
	}
	
	/**
	 * 判断参数是否包含非法字符
	 * @param str
	 * @return
	 */
	public boolean isLegal(String str){
		
		if(StringUtils.isEmpty(str)){
			return false;
		}
		
		String[] chars = LEGAL_CHARS.split(",");
		for (String cha : chars) {
			if(str.contains(cha)){
				return true;
			}
		}
		return false;
	}

	public void destroy() {
	}
}
