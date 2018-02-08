package com.bornsoft.facade.interfaces;

import javax.jws.WebService;

import com.bornsoft.facade.order.AppCheckUpdateOrder;
import com.bornsoft.facade.result.AppCheckUpdateResult;


/**
  * @Description: 业务系统APP接口
  * @author taibai@yiji.com 
  * @date  2016-8-13 下午5:07:46
  * @version V1.0
 */
@WebService
public interface IAppRelatedService {
	
	/**
	 * 接收风险消息
	 * @param riskInfo
	 * @return
	 */
	public AppCheckUpdateResult checkForUpdate(AppCheckUpdateOrder riskInfo);

}


