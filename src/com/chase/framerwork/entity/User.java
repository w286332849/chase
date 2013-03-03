package com.chase.framerwork.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 用户实体
 * @author Chase
 *
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class User  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 738083585258403211L;
	
	private String username;
	private String password;
	
	private Integer status;
	
}
