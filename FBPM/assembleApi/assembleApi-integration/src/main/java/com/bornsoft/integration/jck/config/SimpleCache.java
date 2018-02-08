package com.bornsoft.integration.jck.config;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.bornsoft.utils.tool.DateUtils;

/**
 * @Description: APP 简易缓存 
 * @author taibai@yiji.com
 * @date 2016-11-3 下午3:31:42
 * @version V1.0
 */
public  class SimpleCache<T> {
	/**更新时间**/
	private Date updateTime;
	/**缓存数据*/
	private T data;
	/**过期时间[分钟]*/
	private int timeout;
	
	public SimpleCache(int timeout) {
		this.timeout = timeout;
	}
	
	public SimpleCache(T data) {
		super();
		timeout = 3;
		updateCache(data);
	}
	
	/**
	 * 过期时间【单位：分钟】
	 * @param data
	 * @param timeout
	 */
	SimpleCache(T data,int timeout) {
		super();
		this.timeout = timeout;
		updateCache(data);
	}
	
	public Date getUpDateTime() {
		return updateTime;
	}
	/**
	 * 如果资源过期则自动清空
	 * @return
	 */
	public T getData() {
		if(isOutOfDate()){
			this.data = null;
			return null;
		}else{
			return data;
		}
	}

	public void updateCache(T data) {
		this.data = data;
		this.updateTime = new Date();
	}
	
	public boolean isOutOfDate(){
		return DateUtils.getIntervalMinute(updateTime, new Date()) >= timeout;
	}
	
	public boolean isEmpty(){
		return data == null;
	}
	
	public static enum CacheType{
		AddressList;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}