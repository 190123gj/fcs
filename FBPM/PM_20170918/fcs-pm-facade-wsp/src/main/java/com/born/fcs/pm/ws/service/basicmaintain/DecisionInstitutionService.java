package com.born.fcs.pm.ws.service.basicmaintain;

import javax.jws.WebService;


import com.born.fcs.pm.ws.info.basicmaintain.DecisionInstitutionInfo;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionInstitutionOrder;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionInstitutionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 决策机构维护
 *
 * @author wuxue
 */
@WebService
public interface DecisionInstitutionService {
	
	/**
	 * 根据ID查询决策机构名称
	 * @param institutionId
	 * @return
	 */
	DecisionInstitutionInfo findById(long institutionId);
	
	/**
	 * 保存决策机构名称
	 * @param order
	 * @return
	 */
	FcsBaseResult save(DecisionInstitutionOrder order);
	
//	/**
//	 * 修改销售状态
//	 * @param productId
//	 * @param sellStatus
//	 * @return
//	 */
//	FcsBaseResult changeSellStatus(FinancialProductOrder order);
	
	
	/**
	 * 查询决策机构名称
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<DecisionInstitutionInfo> query(DecisionInstitutionQueryOrder order);

	/**
	 * 根据决策机构名称 查找决策机构信息
	 * @param institutionName 决策机构名称
	 * @return 没有数据返回null
	 */
	DecisionInstitutionInfo findDecisionInstitutionByInstitutionName(String institutionName);
	/**
	 * 根据决策机构id删除决策机构
	 * @param institutionId
	 * @return
	 */
	 int deleteById(long institutionId);
}
