package com.born.fcs.pm.ws.service.council;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.council.FReCouncilApplyInfo;
import com.born.fcs.pm.ws.info.council.ReCouncilApplyFormInfo;
import com.born.fcs.pm.ws.order.council.FReCouncilApplyOrder;
import com.born.fcs.pm.ws.order.council.ReCouncilApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 项目复议申请
 *
 *
 * @author wuzj
 *
 */
@WebService
public interface RecouncilApplyService {
	
	/**
	 * 查询复议申请列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ReCouncilApplyFormInfo> queryForm(ReCouncilApplyQueryOrder order);
	
	/**
	 * 保存
	 * @param order
	 * @return
	 */
	FormBaseResult saveApply(FReCouncilApplyOrder order);
	
	/**
	 * 根据表单ID查询申请单
	 * @param formId
	 * @return
	 */
	FReCouncilApplyInfo queryApplyByFormId(long formId);
}
