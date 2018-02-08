package com.bornsoft.pub.interfaces;

import javax.jws.WebService;

import com.bornsoft.pub.order.kingdee.KingdeeVoucherNoRecevieOrder;
import com.bornsoft.utils.base.BornSynResultBase;


/**
  * @Description: 业务系统风险监控系统接口
  * @author taibai@yiji.com 
  * @date  2016-8-13 下午5:07:46
  * @version V1.0
 */
@WebService
public interface IKingdeeSystemService {
	
	/**
	 * 权利凭证号接收
	 * @param riskInfo
	 * @return
	 */
	public BornSynResultBase recieveRiskInfo(KingdeeVoucherNoRecevieOrder riskInfo);

}


