package com.born.fcs.pm.ws.service.financeaffirm;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.financeaffirm.ProjectChargePayInfo;
import com.born.fcs.pm.ws.order.financeaffirm.ChargeCapitalOrder;
import com.born.fcs.pm.ws.order.financeaffirm.FFinanceAffirmOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ProjectChargePayQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 资金划付、收费通知财务确认Service
 *
 * @author Ji
 */
@WebService
public interface FinanceAffirmService {
	
	/**
	 * 财务确认保存
	 * @param order
	 * @return
	 */
	FcsBaseResult save(FFinanceAffirmOrder order);
	
	/**
	 * 查询项目收费/划付明细
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectChargePayInfo> queryProjectChargePayDetail(	ProjectChargePayQueryOrder order);

	/**
	 * 查询项目收费/划付明细选择
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectChargePayInfo> queryProjectChargePayDetailChoose(	ProjectChargePayQueryOrder order);

	/**
	 * 收费通知单与资金划付关联
	 * @param order
	 * @return
	 */
	FcsBaseResult saveChargeCapital(ChargeCapitalOrder order);
}
