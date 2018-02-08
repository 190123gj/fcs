package com.born.fcs.pm.ws.service.financialproject;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialReviewInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectReviewFormInfo;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialReviewFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialReviewOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialRiskReviewOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 理财项目送审service
 *
 *
 * @author wuzj
 *
 */
@WebService
public interface FinancialProjectReviewService {
	
	/**
	 * 查询送审表单列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FinancialProjectReviewFormInfo> queryPage(	ProjectFinancialReviewFormQueryOrder order);
	
	/**
	 * 根据表单ID查询送审申请单
	 * @param formId
	 * @return
	 */
	FProjectFinancialReviewInfo queryByFormId(long formId);
	
	/**
	 * 保存送审申请单
	 * @param order
	 * @return
	 */
	FormBaseResult save(ProjectFinancialReviewOrder order);
	
	/**
	 * 保存风险审查报告
	 * @param order
	 * @return
	 */
	FcsBaseResult saveRiskReview(ProjectFinancialRiskReviewOrder order);
	
}
