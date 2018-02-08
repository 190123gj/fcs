package com.born.fcs.crm.ws.service;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.info.EvaluetingListAuditInfo;
import com.born.fcs.crm.ws.service.order.EvaluetingOrder;
import com.born.fcs.crm.ws.service.order.query.EvaluetingListQueryOrder;
import com.born.fcs.crm.ws.service.order.query.EvaluetingQueryOrder;
import com.born.fcs.crm.ws.service.result.EvalutingResult;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 评价服务
 * */

@WebService
public interface EvaluetingService {
	
	/** 评级列表查询 */
	public QueryBaseBatchResult<EvaluetingListAuditInfo> list(EvaluetingListQueryOrder order);
	
	/** 根据表单Id查询 */
	EvaluetingListAuditInfo queryByFormId(long formId);
	
	/** 指标评价保存 */
	public FcsBaseResult baseEvalueting(EvaluetingOrder order);
	
	/** 评价结果查询 */
	public EvalutingResult queryEvalueResult(EvaluetingQueryOrder order);
	
	/** 审核时复制原所有数据存为审核记录 */
	public FcsBaseResult aditMakeData(EvaluetingQueryOrder order);
	
	/**
	 * 评价结果统计
	 * 
	 * */
	public EvalutingResult evalueCount(EvaluetingQueryOrder order);
	
	/** 从项目系统中获取财务实际值信息 */
	public EvalutingResult financeInfoFromPro(long userId);
	
}
