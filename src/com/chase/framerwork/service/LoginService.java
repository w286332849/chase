package com.chase.framerwork.service;

import com.chase.framerwork.entity.User;
import com.chase.framerwork.exception.SystemException;

/**
 * 登陆Service
 * @author Chase
 *
 */
public interface LoginService extends BaseService<User, String> {

	boolean checkUserIsExists(String username, String password) throws SystemException;
	
	User loadUser (String username, String password) throws SystemException;

}
