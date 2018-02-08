package com.born.fcs.pm.ws.service.riskwarning;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.riskwarning.RiskWarningSignalInfo;
import com.born.fcs.pm.ws.order.riskwarning.RiskWarningSignalQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 风险预警信号
 * 
 * @author lirz
 *
 * 2016-4-18 下午5:08:33
 */
@WebService
public interface RiskWarningSignalService {
	
	/**
	 * 查询预警信号
	 * 
	 * @param queryOrder 查询条件order
	 * @return
	 */
	QueryBaseBatchResult<RiskWarningSignalInfo> findByCondition(RiskWarningSignalQueryOrder queryOrder);
	
	/**
	 * 
	 * 查询全部公司类特别信号
	 * 
	 * @return
	 */
	QueryBaseBatchResult<RiskWarningSignalInfo> findCompanySpecial();
	
	/**
	 * 
	 * 查询全部公司类一般信号
	 * 
	 * @return
	 */
	QueryBaseBatchResult<RiskWarningSignalInfo> findCompanyNomal();
}
