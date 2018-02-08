package com.bornsoft.pub.interfaces;

import javax.jws.WebService;

import com.bornsoft.pub.order.risk.RiskInfoRecOrder;
import com.bornsoft.utils.base.BornSynResultBase;


/**
  * @Description: 业务系统风险监控系统接口
  * @author taibai@yiji.com 
  * @date  2016-8-13 下午5:07:46
  * @version V1.0
 */
@WebService
public interface IRiskSystemService {
	
	/**
	 * 接收风险消息
	 * @param riskInfo
	 * @return
	 */
	public BornSynResultBase recieveRiskInfo(RiskInfoRecOrder riskInfo);

}


