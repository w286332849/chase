package com.chase.framerwork.vo;

import lombok.Data;
/**
 * Vo最父类,所有Vo都必须继承自该父类
 * @author Chase
 *
 */
@Data
public class BaseVo {
	
	private String id;
	private String createData;
	private String updateDete;
	
	
}
