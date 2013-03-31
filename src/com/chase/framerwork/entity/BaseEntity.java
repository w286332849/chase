package com.chase.framerwork.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

import com.chase.framerwork.util.DateUtil;

/**
 * Entity最父类,所有Entity都必须继承自该父类
 * @author Chase
 *
 */
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = -6718838800112233445L;
	
	public static final String CREATE_DATE_PROPERTY_NAME = "createDate";// "创建日期"属性名称
	public static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";// "修改日期"属性名称
	public static final String ON_SAVE_METHOD_NAME = "onSave";// "保存"方法名称
	public static final String ON_UPDATE_METHOD_NAME = "onUpdate";// "更新"方法名称

	protected Long id;// ID
	protected Date createDate;// 创建日期
	protected Date modifyDate;// 修改日期

	//@DocumentId
	//@GeneratedValue(generator = "uuid")
	//@GenericGenerator(name = "uuid", strategy = "uuid")
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "id", strategy = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	//@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	//@DateBridge(resolution = Resolution.MINUTE)
	@Column(updatable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	//@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	//@DateBridge(resolution = Resolution.MINUTE)
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	@Transient
	public void onSave() {}
	
	@Transient
	public void onUpdate() {}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		if (id == null || other.getId() == null) {
			return false;
		} else {
			return (id.equals(other.getId()));
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Transient
	public String getCreateDateStr(){
		return DateUtil.getInstance().dateToString(createDate, DateUtil.patternA);
	}
	
	@Transient
	public String getModifyDateStr(){
		return DateUtil.getInstance().dateToString(modifyDate, DateUtil.patternA);
	}
}