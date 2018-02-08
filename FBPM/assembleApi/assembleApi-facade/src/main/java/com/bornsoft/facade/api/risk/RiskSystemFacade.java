package com.bornsoft.facade.api.risk;

import javax.jws.WebService;

import com.bornsoft.pub.order.risk.QuerySimilarEnterpriseOrder;
import com.bornsoft.pub.order.risk.SynCustomLevelOrder;
import com.bornsoft.pub.order.risk.SynOperatorInfoOrder;
import com.bornsoft.pub.order.risk.SynRiskInfoOrder;
import com.bornsoft.pub.order.risk.SynWatchListOrder;
import com.bornsoft.pub.order.risk.SynsCustomInfoOrder;
import com.bornsoft.pub.order.risk.VerifyOrganizationOrder;
import com.bornsoft.pub.result.risk.QuerySimilarEnterpriseResult;
import com.bornsoft.pub.result.risk.VerifyOrganizationResult;
import com.bornsoft.utils.base.BornSynResultBase;


/**
  * @Description: 风险监控系统访问接口 
  * @author taibai@yiji.com 
  * @date  2016-8-13 下午5:07:46
  * @version V1.0
 */
@WebService
public interface RiskSystemFacade {
	/**
	 * 操作员名录同步
	 * @param order
	 * @return
	 */
	public BornSynResultBase synOperatorInfo(SynOperatorInfoOrder order);
	
	/**
	 * 被监控名录同步
	 * @param order
	 * @return
	 */
	public BornSynResultBase synWatchList(SynWatchListOrder order);
	
	/**
	 * 客户评级信息同步
	 * @param order
	 * @return
	 */
	public BornSynResultBase synCustomLevel(SynCustomLevelOrder order);
	
	/**
	 * 风险信息同步
	 * @param order
	 * @return
	 */
	public BornSynResultBase synRiskInfo(SynRiskInfoOrder order);
	
	/**
	 * 客户基础信息补充
	 * @param order
	 * @return
	 */
	public BornSynResultBase synCustomInfo(SynsCustomInfoOrder order);
	
	/**
	 * 机构校验
	 * @param order
	 * @return
	 */
	public VerifyOrganizationResult verifyOrganizationInfo(VerifyOrganizationOrder order);
	
	/**
	 * 类似企业信息查询
	 * @param order
	 * @return
	 */
	public QuerySimilarEnterpriseResult querySimilarEnterprise(QuerySimilarEnterpriseOrder order);
	
	
}
