package com.chase.framerwork.dao;

import java.io.Serializable;

import com.chase.framerwork.entity.User;
import com.chase.framerwork.exception.SystemException;

/**
 * 用户Dao
 * @author Chase
 *
 */
public interface UserDao extends BaseDao<User, Serializable> {

	User findUser(String username, String password) throws SystemException;

}
