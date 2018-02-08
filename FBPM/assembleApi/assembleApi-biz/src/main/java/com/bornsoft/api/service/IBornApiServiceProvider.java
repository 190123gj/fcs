package com.bornsoft.api.service;

/**
 * @Description: 服务提供者
 * @author xiaohui@yiji.com
 * @date 2015-11-10 下午2:01:33 
 * @version V1.0
 */
public interface IBornApiServiceProvider {
	
	/**
	 * 服务名称空间
	 */
	public String getServiceNameSpace();

	/**
	 * 发现服务
	 *
	 * @param name 服务名称
	 */
	public IBornApiService findBornApiService(String name);
}
