package com.chase.framerwork.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;

import com.chase.framerwork.common.ResponseCode;
import com.chase.framerwork.entity.User;
import com.chase.framerwork.service.LoginService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 登陆操作Action
 * @author Chase
 *
 */
@ParentPackage("chase-struts")
@Namespace("/login")
@Action(value = "login", results = {
		@Result(name = "success", location = "/framework/main.jsp"),
		@Result(name = "login", location = "/framework/login.jsp") })
@Scope("prototype")
public class LoginAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5989455606615751305L;
	
	private static final String LOGIN = "login";
	private static final String LOGOUT = "logout";

	private LoginService loginService;

	private String username;
	private String password;

	@InputConfig(resultName = "error")
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "username", message = "用户名不能为空"),
			@RequiredStringValidator(fieldName = "password", message = "密码不能为空") }, stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "username", minLength = "3", maxLength = "12", message = "用户名长度必须在${minLength}-${maxLength}之间"),
			@StringLengthFieldValidator(fieldName = "password", minLength = "3", maxLength = "12", message = "密码长度必须在${minLength}-${maxLength}之间") })
	public String login() throws Exception {

		User user = loginService.loadUser(username, password);
		if (user != null) {
			setSession("user", user);
			
			setResponseCode(ResponseCode.LOGIN_001);
			
			return SUCCESS;
			
		} else {
			setResponseCode(ResponseCode.LOGIN_002);
			return LOGIN;
		}
	}

	@SkipValidation
	public String logout() {

		return LOGOUT;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	@Resource
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

}
