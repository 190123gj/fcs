package com.born.fcs.pm.ws.service.riskreview;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.common.FormProjectInfo;
import com.born.fcs.pm.ws.info.riskreview.RiskReviewReportInfo;
import com.born.fcs.pm.ws.order.riskreview.FRiskReviewOrder;
import com.born.fcs.pm.ws.order.riskreview.FRiskReviewQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@WebService
public interface RiskReviewReportService {
	
	/**
	 * 保存风险审查报告
	 * @param order
	 * @return
	 */
	FormBaseResult saveRiskReview(FRiskReviewOrder order);
	
	/**
	 * 
	 * 保存风险审查报告
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult save(FRiskReviewOrder order);
	
	/**
	 * 保存风险审查报告内容
	 * @param order
	 * @return
	 */
	FcsBaseResult updateRiskReviewReportContent(FRiskReviewOrder order);
	
	/**
	 * 根据表单ID查询风险审查报告
	 * @param formId 对应的表单ID
	 * @return 风险审查报告
	 */
	RiskReviewReportInfo queryRiskReviewByFormId(long formId);
	
	/**
	 * 根据ID查询风险审查报告
	 * @param formId 对应的表单ID
	 * @return 风险审查报告
	 */
	RiskReviewReportInfo queryRiskReviewById(long id);
	
	/**
	 * 根据项目编号风险审查报告
	 * @param projectCode 对应项目编号
	 * @return 风险审查报告
	 */
	RiskReviewReportInfo queryRiskReviewByCode(String projectCode);
	
	/**
	 * 分页查询风险审查报告
	 * @param riskReviewQueryOrder
	 * @return 风险审查报告List
	 */
	QueryBaseBatchResult<FormProjectInfo> queryRiskReview(	FRiskReviewQueryOrder riskReviewQueryOrder);
	
}
