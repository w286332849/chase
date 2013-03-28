package com.chase.framerwork.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.chase.framerwork.entity.Menu;

/**
 * 菜单Dao实现
 * @author Chase
 *
 */
@Repository("menuDaoImpl")
public class MenuDaoImpl extends BaseDaoImpl<Menu, Serializable> implements MenuDao {

}
