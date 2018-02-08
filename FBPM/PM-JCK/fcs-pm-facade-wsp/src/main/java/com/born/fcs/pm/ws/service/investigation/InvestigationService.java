package com.born.fcs.pm.ws.service.investigation;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCsRationalityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationFinancialDataExplainInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationFinancialReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationLitigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMabilityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMainlyReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationMajorEventInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationOpabilityReviewInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationProjectStatusInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationRiskInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationUnderwritingInfo;
import com.born.fcs.pm.ws.info.finvestigation.InvestigationInfo;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCreditSchemeOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationCsRationalityReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationFinancialDataExplainOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationFinancialReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationLitigationOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMabilityReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMainlyReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationMajorEventOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationOpabilityReviewOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationProjectStatusOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationRiskOrder;
import com.born.fcs.pm.ws.order.finvestigation.FInvestigationUnderwritingOrder;
import com.born.fcs.pm.ws.order.finvestigation.InvestigationQueryOrder;
import com.born.fcs.pm.ws.order.finvestigation.base.InvestigationBaseOrder;
import com.born.fcs.pm.ws.order.finvestigation.declare.DeclareBaseOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * @author lirz
 * 
 * 2016-3-8 下午2:00:29
 */
@WebService
public interface InvestigationService {
	
	/**
	 * 更新尽调信息
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult updateInvestigation(InvestigationBaseOrder order);
	
	/**
	 * 保存尽职调查申明(新增/修改)
	 * 
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult saveInvestigation(FInvestigationOrder order);
	
	/**
	 * 
	 * 更新数据
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult update(DeclareBaseOrder order);
	
	/**
	 * 
	 * 显示尽职调查报告(针对复议预生成的提报告)
	 * 
	 * @param formId
	 * @return
	 */
	FcsBaseResult updateToShow(long formId);
	
	/**
	 * 根据表单id查找尽职调查申明
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FInvestigationInfo findInvestigationByFormId(long formId);
	
	/**
	 * 保存(新增/修改) 授信方案
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult saveInvestigationCreditScheme(FInvestigationCreditSchemeOrder order);
	
	/**
	 * 根据表单id查找授信方案
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FInvestigationCreditSchemeInfo findInvestigationCreditSchemeByFormId(long formId);
	
	/**
	 * 保存(新增/修改) 客户主体评价
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult saveInvestigationMainlyReview(FInvestigationMainlyReviewOrder order);
	
	/**
	 * 根据表单id查找 客户主体评价
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FInvestigationMainlyReviewInfo findInvestigationMainlyReviewByFormId(long formId);
	
	/**
	 * 保存(新增/修改) 客户管理能力评价
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult saveFInvestigationMabilityReview(FInvestigationMabilityReviewOrder order);
	
	/**
	 * 根据表单id查找 客户管理能力评价
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FInvestigationMabilityReviewInfo findFInvestigationMabilityReviewByFormId(long formId);
	
	/**
	 * 保存(新增/修改) 客户运营能力评价
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult saveFInvestigationOpabilityReview(FInvestigationOpabilityReviewOrder order);
	
	/**
	 * 根据表单id查找 客户运营能力评价
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FInvestigationOpabilityReviewInfo findFInvestigationOpabilityReviewByFormId(long formId);
	
	/**
	 * 保存(新增/修改) 重大事项调查
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult saveFInvestigationMajorEvent(FInvestigationMajorEventOrder order);
	
	/**
	 * 根据表单id查找 重大事项调查
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FInvestigationMajorEventInfo findFInvestigationMajorEventByFormId(long formId);
	
	/**
	 * 保存(新增/修改) 项目情况调查
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult saveFInvestigationProjectStatus(FInvestigationProjectStatusOrder order);
	
	/**
	 * 根据表单id查找 项目情况调查
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FInvestigationProjectStatusInfo findFInvestigationProjectStatusByFormId(long formId);
	
	/**
	 * 保存(新增/修改) 授信方案主要事项合理性评价
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult saveFInvestigationCsRationalityReview(	FInvestigationCsRationalityReviewOrder order);
	
	/**
	 * 根据表单id查找 授信方案主要事项合理性评价
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FInvestigationCsRationalityReviewInfo findFInvestigationCsRationalityReviewByFormId(long formId);
	
	/**
	 * 保存(新增/修改) 风险分析 结论意见
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult saveFInvestigationRisk(FInvestigationRiskOrder order);
	
	/**
	 * 根据表单id查找 风险分析 结论意见
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FInvestigationRiskInfo findFInvestigationRiskByFormId(long formId);
	
	/**
	 * 保存(新增/修改) 客户财务评价
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult saveFInvestigationFinancialReview(FInvestigationFinancialReviewOrder order);
	
	/**
	 * 根据表单id查找 客户财务评价
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	
	FInvestigationFinancialReviewInfo findFInvestigationFinancialReviewByFormId(long formId);
	
	/**
	 * 保存(新增/修改) 客户财务评价 财务数据解释说明
	 * @param order 不能为null
	 * @return 成功/失败
	 * @deprecated 动态添加，不采用单独保存的形式
	 */
	@Deprecated
	FcsBaseResult saveFInvestigationFinancialDataExplain(	FInvestigationFinancialDataExplainOrder order);
	
	/**
	 * 根据财务数据id 查找财务数据解释说明
	 * @param formId 表单id
	 * @return 没有数据返回null
	 * @deprecated 动态添加，不采用单独保存的形式后不采用单独查询
	 */
	@Deprecated
	FInvestigationFinancialDataExplainInfo findFInvestigationFinancialDataExplainById(long id);
	
	/**
	 * 查询尽职调查报告列表
	 * 
	 * @param queryOrder 查询条件
	 * @return
	 */
	QueryBaseBatchResult<InvestigationInfo> queryInvestigation(InvestigationQueryOrder queryOrder);
	
	/**
	 * 保存(新增/修改) 诉讼担保项目调查报告
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult saveFInvestigationLitigation(FInvestigationLitigationOrder order);
	
	/**
	 * 根据表单id查找 诉讼担保项目调查报告
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FInvestigationLitigationInfo findFInvestigationLitigationByFormId(long formId);
	
	/**
	 * 保存(新增/修改) 承销项目情况表
	 * @param order 不能为null
	 * @return 成功/失败
	 */
	FormBaseResult saveFInvestigationUnderwriting(FInvestigationUnderwritingOrder order);
	
	/**
	 * 根据表单id查找 承销项目情况表
	 * @param formId 表单id
	 * @return 没有数据返回null
	 */
	FInvestigationUnderwritingInfo findFInvestigationUnderwritingByFormId(long formId);
	
	/**
	 * 查询可创建尽职调查申明的项目列表
	 * 
	 * @param queryOrder 查询条件
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> querySelectableProject(InvestigationQueryOrder queryOrder);
	
	/**
	 * 根据项目编号查询未删除的尽职调查formId
	 * @param projectCode
	 * @return
	 */
	long queryInvestigationFormIdByProjectCode(String projectCode);
	
	/**
	 * 复制尽职调查报告
	 * 
	 * @param projectCode
	 */
	void copyInvestigation(String projectCode);
}
