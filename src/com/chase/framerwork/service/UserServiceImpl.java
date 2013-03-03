package com.chase.framerwork.service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chase.framerwork.bean.Pager;
import com.chase.framerwork.dao.UserDao;
import com.chase.framerwork.entity.User;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Serializable> implements UserService {
	
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

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
