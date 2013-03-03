package com.chase.framerwork.interceptor;


import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import com.chase.framerwork.entity.BaseEntity;
import com.chase.framerwork.util.ReflectionUtil;

/**
 * 拦截器 - 自动填充创建日期、修改日期
 */

@Component
public class EntityInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 7319416231145791577L;
	
	// 保存之前调用次方法自动填充创建日期和修改日期 
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if (entity instanceof BaseEntity) {
			for (int i = 0; i < propertyNames.length; i++) {
				if (BaseEntity.CREATE_DATE_PROPERTY_NAME.equals(propertyNames[i]) || BaseEntity.MODIFY_DATE_PROPERTY_NAME.equals(propertyNames[i])) {
					state[i] = new Date();
				}
			}
			ReflectionUtil.invokeSetterMethod(entity, BaseEntity.CREATE_DATE_PROPERTY_NAME, new Date());
		}
		return true;
	}

	// 修改之前调用此方法自动填充修改日期 
	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		if (entity instanceof BaseEntity) {
			for (int i = 0; i < propertyNames.length; i++) {
				if (BaseEntity.MODIFY_DATE_PROPERTY_NAME.equals(propertyNames[i])) {
					currentState[i] = new Date();
				}
			}
			ReflectionUtil.invokeSetterMethod(entity, BaseEntity.MODIFY_DATE_PROPERTY_NAME, new Date());
		}
		return true;
	}

}