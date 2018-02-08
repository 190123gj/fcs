package com.born.fcs.pm.ws.service.riskwarning;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.riskwarning.FRiskWarningInfo;
import com.born.fcs.pm.ws.info.riskwarning.RiskWarningInfo;
import com.born.fcs.pm.ws.order.riskwarning.FRiskWarningOrder;
import com.born.fcs.pm.ws.order.riskwarning.RiskWarningQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 风险预警
 * 
 * @author lirz
 *
 * 2016-4-15 下午5:09:43
 */
@WebService
public interface RiskWarningService {
	
	/**
	 * 保存风险预警(新增/修改)
	 * 
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult save(FRiskWarningOrder order);
	
	/**
	 * 查询风险预警信息
	 * 
	 * @param id 单据编号
	 * @return 风险预警对象
	 */
	FRiskWarningInfo findById(long id);
	
	/**
	 * 查询风险预警信息
	 * 
	 * @param formId 表单id
	 * @return 风险预警对象
	 */
	FRiskWarningInfo findByFormId(long formId);
	
	/**
	 * 根据条件查询列表
	 * 
	 * @param queryOrder 查询条件
	 * @return 结果集
	 */
	QueryBaseBatchResult<RiskWarningInfo> queryList(RiskWarningQueryOrder queryOrder);
}
