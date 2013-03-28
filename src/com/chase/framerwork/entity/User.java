package com.chase.framerwork.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 用户Entity
 * @author Chase
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="SYS_USER")
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 738083585258403211L;
	
	private String username;
	private String password;
	
	private Integer status;
	
}
