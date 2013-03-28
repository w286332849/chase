package com.chase.framerwork.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.chase.framerwork.entity.User;
import com.chase.framerwork.exception.SystemException;

/**
 * 用户Dao实现
 * @author Administrator
 *
 */
@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User, Serializable> implements
		UserDao {

	public User findUser(String username, String password)
			throws SystemException {
		Assert.notNull(username, " username not be null!");
		Assert.notNull(password, " password not be null!");

		String hql = " from User u where u.username = :username and u.password = :password";

		Query query = getSession().createQuery(hql).setParameter("username",
				username).setParameter("password", password);
		
		List<User> list = query.list();

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	};

}
