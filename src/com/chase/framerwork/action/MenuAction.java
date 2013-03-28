package com.chase.framerwork.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;

import com.chase.framerwork.entity.Menu;
import com.chase.framerwork.service.MenuService;

/**
 * 菜单操作Action
 * @author Chase
 * 
 */
@ParentPackage("chase-struts")
@Namespace("/menu")
@Action(value = "menu")
@Scope("prototype")
public class MenuAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2244565949150836482L;
	
	private MenuService menuService;
	
	private List<Menu> menuList;
	
	public String findMenuList() throws Exception {
		
		menuList = menuService.getAllList();
		
		JSONObject json = new JSONObject();
		json.put("menuList", menuList);
		
		outJSON(json);
		
		return null;
	}

	public MenuService getMenuService() {
		return menuService;
	}
	
	@Resource
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	
}
