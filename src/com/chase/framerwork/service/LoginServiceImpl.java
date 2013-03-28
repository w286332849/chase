package com.chase.framerwork.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chase.framerwork.dao.UserDao;
import com.chase.framerwork.entity.User;
import com.chase.framerwork.exception.SystemException;

/**
 * 登陆Service实现
 * @author Chase
 * 
 */
@Service
public class LoginServiceImpl extends BaseServiceImpl<User, String> implements
		LoginService {

	private UserDao userDao;

	/**
	 * 检查用户是否存在
	 */
	public boolean checkUserIsExists(String username, String password)
			throws SystemException {

		User user = loadUser(username, password);
		if (user != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 得到user
	 */
	public User loadUser(String username, String password)
			throws SystemException {
		
		User user = userDao.findUser(username, password);
		
		return user;
	}
	
	
	
	
	public UserDao getUserDao() {
		return userDao;
	}
	
	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
