package com.chase.framerwork.dao;

import java.io.Serializable;

import com.chase.framerwork.entity.User;
import com.chase.framerwork.exception.SystemException;

public interface UserDao extends BaseDao<User, Serializable> {

	User findUser(String username, String password) throws SystemException;

}
