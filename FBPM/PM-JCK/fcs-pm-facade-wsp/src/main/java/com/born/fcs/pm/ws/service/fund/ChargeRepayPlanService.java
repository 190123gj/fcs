package com.born.fcs.pm.ws.service.fund;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanDetailInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanInfo;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanDetailQueryOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@WebService
public interface ChargeRepayPlanService {
	
	/**
	 * 保存收费还款计划
	 * @param order
	 * @return
	 */
	FcsBaseResult savePlan(ChargeRepayPlanOrder order);
	
	/**
	 * 查询收费还款计划
	 * @param planId
	 * @return
	 */
	ChargeRepayPlanInfo queryPlanById(long planId);
	
	/**
	 * 查询计划明细
	 * @param projectCode
	 * @return
	 */
	QueryBaseBatchResult<ChargeRepayPlanDetailInfo> queryPlanDetail(ChargeRepayPlanDetailQueryOrder order);
	
	/**
	 * 查询收费还款计划
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ChargeRepayPlanInfo> queryPlan(ChargeRepayPlanQueryOrder order);
}
