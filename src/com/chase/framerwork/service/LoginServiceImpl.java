package com.chase.framerwork.service;

import org.springframework.stereotype.Service;

import com.chase.framerwork.dao.UserDao;
import com.chase.framerwork.entity.User;
import com.chase.framerwork.exception.SystemException;

/**
 * 系统登陆Service
 * 
 * @author Administrator
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

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
