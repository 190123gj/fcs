package com.bornsoft.pub.interfaces;

import javax.jws.WebService;

import com.bornsoft.utils.base.MerchantInfo;

/**
 * 商户信息查询
 * @author taibai@yiji.com
 * @date 2016-1-27 下午2:15:14
 * @version V1.0
 */
@WebService
public interface MerchantFacade {
	
	/**
	 * 查询商户信息
	 * @param queryOrder
	 * @return
	 */
	public MerchantInfo getMerchantInfo(String merchantNo);
}
