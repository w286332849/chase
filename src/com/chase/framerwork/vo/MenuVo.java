package com.chase.framerwork.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单Vo
 * @author Chase
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MenuVo extends BaseVo{
	
	private String name;
	
	private String url;
	
	private Integer status;
	
	private Integer isLeaf;
	
	private Integer isParent;
	
	private Long orderSN;
	
	private Long pid;
	
	
}
