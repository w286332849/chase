package com.chase.framerwork.service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chase.framerwork.dao.MenuDao;
import com.chase.framerwork.entity.Menu;
/**
 * 菜单Service实现
 * @author Chase
 *
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Serializable> implements MenuService {

	private MenuDao menuDao;
	
	
	@Resource
	public void setBaseDao(MenuDao menuDao) {
		super.setBaseDao(menuDao);
	}

	public MenuDao getMenuDao() {
		return menuDao;
	}
	
	@Resource
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
}
