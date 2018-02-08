package com.born.fcs.crm.biz.service.evaluating.process;

import com.born.fcs.crm.ws.service.order.EvaluetingOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;

/** 评级审核流程 */
public interface EvaluetingAuditService {
	
	/** 保存信息 */
	FormBaseResult save(EvaluetingOrder order);
	
	/** 审核完成 */
	FormBaseResult auditResult(EvaluetingOrder order);
	
}
