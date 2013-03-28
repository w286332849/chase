package com.chase.framerwork.action;

/**
 * Action常用常量接口
 * @author Chase
 *
 */
public interface ActionConstants {
	
	String ADD_INPUT = "addInput";
	String EDIT_INPUT = "editInput";
	String REMOVE_INPUT = "removeInput";
	String FIND_INPUT = "findInput";
	
	String ADD = "add";
	String CREATE = "create";
	String NEW = "new";
	
	String UPDATE = "update";
	String MARGRE = "update";
	String EDIT = "edit";
	
	String DELETE = "delete";
	String REMOVE = "remove";
	
	String FIND = "find";
	String FIND_ALL = "findAll";
	String FIND_BY_ID = "findById";
	String FIND_BY_LIKE = "findByLike";
	String FIND_BY_NAME = "findByName";
	
	String LIST = "list";
	String ITERATOR = "iterator";
	
	String LOAD = "load";
	String LOAD_BY_ID = "loadById";
	String LOAD_BY_LIKE = "loadByLike";
	String LOAD_BY_NAME = "loadByName";
	
	String SEARCH = "search";
	
	// 查询方法
	enum QUERY_METHOD {
		findAll,
		findById,
		findByLike,
		findByName,
		findBy
	}
	
	
}