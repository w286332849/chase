package com.chase.framerwork.action;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.chase.framerwork.bean.Pager;
import com.chase.framerwork.entity.User;
import com.chase.framerwork.service.UserService;
import com.chase.framerwork.vo.UserVo;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@ParentPackage("chase-struts")
@Namespace("/user")
@Action(value = "user", results = {
			@Result(name = "add", location = "/add.jsp"),
			@Result(name = "delete", location = "/delete.jsp"),
			@Result(name = "userList", location = "/userList.jsp"),
			@Result(name = "load", location = "/load.jsp"),
			@Result(name = "testTx", location = "/textTx.jsp")
})
public class UserAction extends BaseAction
{
	
	private static final long serialVersionUID = 7228043685105009379L;
	
	@Resource
	private UserService userService;
	
	private String name;
	
	private Pager pager = new Pager();
	
	private User user;
	
	private UserVo userVo = new UserVo();
	private UserVo userVo2 = new UserVo();
	
	// 验证
//	@InputConfig(resultName = "error") // 验证失败之后 跳转到的result
//	@Validations(
//			
////			Struts2内置验证方法：
////			@RequiredFieldValidator：字符串非空验证
////			@EmailValidator：E-mail验证
////			@urls：Url验证
////			@StringLengthFieldValidator：字符串长度验证
////			@intRangeFields：数值大小验证
////			@dateRangeFields：日期验证
////			@RegexFieldValidator：正则表达式验证
//			
//			// 验证必填项
//			requiredStrings = { 
//					@RequiredStringValidator(fieldName = "name", message = "请填写名称")
//			},
//			
//			// 验证字符串长度
//			stringLengthFields = {
//					@StringLengthFieldValidator(fieldName="name", minLength="3", maxLength="10", message = "长度必须在${minLength}-${maxLength}之间")
//			},
//			
//			// 数值验证
//			intRangeFields = {
//					@IntRangeFieldValidator(fieldName= "", min="", max="", message="")
//			},
//			
//			// 通过正则表达式验证
//			regexFields = {
//					@RegexFieldValidator(fieldName = "", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$" , message = "用户名只允许包含中文、英文、数字和下划线!")
//			},
//			
//			// Email验证
//			emails = {
//					@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!")
//			},
//			
//			// 日期验证
//			dateRangeFields = {
//					@DateRangeFieldValidator(fieldName="")
//			},
//			
//			// url验证
//			urls = {
//					@UrlValidator()
//			}
//	)
	
	public UserVo getUserVo2() {
		return userVo2;
	}


	public void setUserVo2(UserVo userVo2) {
		this.userVo2 = userVo2;
	}


	public String add() throws Exception {
		JSONObject json = new JSONObject();
		try{
			if(userVo.getPassword().equals(userVo.getConfirmPassword())) {
				User user = new User();
				BeanUtils.copyProperties(user, userVo);
				
				userService.save(user);
			} 
			else {
				json.put("errorMsg", "Enter two passwords do not match");
			}
		}catch (Exception e) {
			json.put("errorMsg", e.getMessage());
		}
		
		outJSON(json);
		
		return null;
	}
	
	
	// 跳过验证 
	//@SkipValidation
	public String delete() throws Exception {
		JSONObject json = new JSONObject();
		
		try {
			User user = new User();
			BeanUtils.copyProperties(user, userVo);
			userService.delete(user);

			json.put("success", true);
		} catch (Exception e) {
			json.put("errorMsg", e.getMessage());
		}
		
		outJSON(json);
		
		return null;
	}
	
	//@SkipValidation
	public String update() throws Exception {
		
		User user = new User();
		user.setId("123456");
		
		userService.update(user);
		
		return UPDATE;
	}
	
	public String loadUser() throws Exception {
		
		user = userService.load(userVo.getId());
		
		return "load";
	}
	
	
	public String userList() throws Exception {
		
		pager = userService.findAll(pager);
		
		outJSONList(pager);
		
		return null;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

}
