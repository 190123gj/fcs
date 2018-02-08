package com.bornsoft.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.bornsoft.api.service.base.IAutoInjectBean;

/**
 * @Description: SpringBeanCreateHelper
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午4:13:59 
 * @version V1.0
 */
class SpringBeanCreateHelper implements BeanFactoryAware {

	private AutowireCapableBeanFactory beanFactory = null;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (AutowireCapableBeanFactory) beanFactory;
	}

	/**
	 * 创建一个从springFactory中自动填充的Bean.
	 */
	public final <T extends IAutoInjectBean> T createAutoInjectBean(Class<T> xx_t) {
		T x = BeanUtils.instantiate(xx_t);
		createAutoInjectBean(x);
		return x;
	}

	/**
	 * 创建一个从springFactory中自动填充的Bean.
	 */
	public final <T extends IAutoInjectBean> T createAutoInjectBean(T x) {
		this.beanFactory.autowireBeanProperties(x, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, true);
		return x;
	}
}
