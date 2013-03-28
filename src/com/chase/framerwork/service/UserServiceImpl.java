package com.chase.framerwork.service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chase.framerwork.bean.Pager;
import com.chase.framerwork.dao.UserDao;
import com.chase.framerwork.entity.User;

/**
 * 用户Service实现
 * 
 * @author Chase
 * 
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Serializable>
		implements UserService {

	private UserDao userDao;

	public Pager findAll(Pager pager) {
		return userDao.findPager(pager);
	}

	@Resource
	public void setBaseDao(UserDao userDao) {
		super.setBaseDao(userDao);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
