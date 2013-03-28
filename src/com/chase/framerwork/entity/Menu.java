package com.chase.framerwork.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 菜单Entity
 * @author Chase
 *
 */
@Entity
@Table(name="SYS_MENU")
public class Menu extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6741976080604789465L;
	
	/**
	 * 父节点对象
	 */
	//private Menu menu;
	
	/**
	 * 子节点列表
	 */
	//private List<Menu> childMenus = new ArrayList<Menu>();
	
	private Long pId;
	
	private String name;
	
	//private String url;
	
	private String action;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	private Integer status;
	
	private Integer isLeaf;
	
	private Integer isParent;
	
	private Long orderSN;
	
	// 一个节点下面有多个子节点,所以用OneToMany
	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "childMenus")
//	public List<Menu> getChildMenus() {
//		return childMenus;
//	}
//
//	public void setChildMenus(List<Menu> childMenus) {
//		this.childMenus = childMenus;
//	}
	
	// 一个父节点id可以被多个节点所使用,所以用ManyToOne
//	@ManyToOne
//	@JoinColumn(name = "pid")
//	public Menu getMenu() {
//		return menu;
//	}
//
//	public void setMenu(Menu menu) {
//		this.menu = menu;
//	}
	
	public Long getpId() {
		return pId;
	}
	
	public void setpId(Long pId) {
		this.pId = pId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public Long getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(Long orderSN) {
		this.orderSN = orderSN;
	}
	
}
