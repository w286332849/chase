package com.chase.framerwork.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户Vo
 * @author Chase
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserVo extends BaseVo {
	
	private String username;
	private String password;
	private String confirmPassword;
	private Integer status;
	
}
