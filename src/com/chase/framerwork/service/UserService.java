package com.chase.framerwork.service;

import java.io.Serializable;

import com.chase.framerwork.bean.Pager;
import com.chase.framerwork.entity.User;

public interface UserService extends BaseService<User, Serializable> {

	Pager findAll(Pager pager);

	
}
