package com.born.fcs.pm.biz.service.riskreview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FRiskReviewReportDO;
import com.born.fcs.pm.dataobject.FormProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.FormProjectInfo;
import com.born.fcs.pm.ws.info.riskreview.RiskReviewReportInfo;
import com.born.fcs.pm.ws.order.riskreview.FRiskReviewOrder;
import com.born.fcs.pm.ws.order.riskreview.FRiskReviewQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.riskreview.RiskReviewReportService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("riskReviewReportService")
public class RiskReviewReportServiceImpl extends BaseFormAutowiredDomainService implements
																				RiskReviewReportService {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public FormBaseResult saveRiskReview(final FRiskReviewOrder order) {
		return commonFormSaveProcess(order, "保存风险审查报告", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				if (order.getId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					//保存
					FRiskReviewReportDO fRiskReviewReportDO = new FRiskReviewReportDO();
					BeanCopier.staticCopy(order, fRiskReviewReportDO);
					fRiskReviewReportDO.setFormId(formInfo.getFormId());
					long riskReviewid = FRiskReviewReportDAO.insert(fRiskReviewReportDO);
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(riskReviewid);
				} else {
					//更新
					FRiskReviewReportDO fRiskReviewReportDO = FRiskReviewReportDAO.findById(order
						.getId());
					if (null == fRiskReviewReportDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到风险审查报告");
					}
					
					BeanCopier.staticCopy(order, fRiskReviewReportDO);
					FRiskReviewReportDAO.update(fRiskReviewReportDO);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult save(final FRiskReviewOrder order) {
		return commonProcess(order, "保存风险审查报告", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				FRiskReviewReportDO doObj = FRiskReviewReportDAO.findByFormId(order.getFormId());
				if (null == doObj) {
					doObj = new FRiskReviewReportDO();
					BeanCopier.staticCopy(order, doObj);
					doObj.setFormId(order.getFormId());
					doObj.setRawAddTime(getSysdate());
					FRiskReviewReportDAO.insert(doObj);
				} else {
					if (doObj.getId() != order.getId()) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到风险审查报告");
					}
					BeanCopier.staticCopy(order, doObj);
					doObj.setFormId(order.getFormId());
					FRiskReviewReportDAO.update(doObj);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public RiskReviewReportInfo queryRiskReviewByFormId(long formId) {
		FRiskReviewReportDO fRiskReviewReportDO = FRiskReviewReportDAO.findByFormId(formId);
		if (null == fRiskReviewReportDO) {
			return null;
		}
		RiskReviewReportInfo info = new RiskReviewReportInfo();
		BeanCopier.staticCopy(fRiskReviewReportDO, info);
		return info;
	}
	
	@Override
	public RiskReviewReportInfo queryRiskReviewById(long id) {
		FRiskReviewReportDO fRiskReviewReportDO = FRiskReviewReportDAO.findById(id);
		RiskReviewReportInfo info = new RiskReviewReportInfo();
		BeanCopier.staticCopy(fRiskReviewReportDO, info);
		return info;
	}
	
	@Override
	public RiskReviewReportInfo queryRiskReviewByCode(String projectCode) {
		FRiskReviewReportDO fRiskReviewReportDO = FRiskReviewReportDAO
			.findByProjectCode(projectCode);
		RiskReviewReportInfo info = new RiskReviewReportInfo();
		BeanCopier.staticCopy(fRiskReviewReportDO, info);
		return info;
	}
	
	@Override
	public QueryBaseBatchResult<FormProjectInfo> queryRiskReview(	FRiskReviewQueryOrder queryOrder) {
		QueryBaseBatchResult<FormProjectInfo> batchResult = new QueryBaseBatchResult<>();
		
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("projectCode", queryOrder.getProjectCode());
			paramMap.put("projectName", queryOrder.getProjectName());
			paramMap.put("customerName", queryOrder.getCustomerName());
			paramMap.put("formStatus", queryOrder.getFormStatus());
			
			paramMap.put("loginUserId", queryOrder.getLoginUserId());
			paramMap.put("deptIdList", queryOrder.getDeptIdList());
			
			long totalCount = extraDAO.searchRiskReviewProjectCount(paramMap);
			if (totalCount > 0) {
				PageComponent component = new PageComponent(queryOrder, totalCount);
				paramMap.put("limitStart", component.getFirstRecord());
				paramMap.put("pageSize", component.getPageSize());
				paramMap.put("sortOrder", queryOrder.getSortCol());
				paramMap.put("sortCol", queryOrder.getSortOrder());
				
				List<FormProjectDO> list = extraDAO.searchRiskReviewProject(paramMap);
				
				List<FormProjectInfo> pageList = new ArrayList<>(list.size());
				for (FormProjectDO item : list) {
					FormProjectInfo info = new FormProjectInfo();
					BeanCopier.staticCopy(item, info);
					info.setFormStatus(FormStatusEnum.getByCode(item.getFormStatus()));
					pageList.add(info);
				}
				batchResult.setSuccess(true);
				batchResult.setPageList(pageList);
				batchResult.initPageParam(component);
			}
			
			batchResult.setSuccess(true);
		} catch (Exception e) {
			logger.error("查询风险审查报告失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public FcsBaseResult updateRiskReviewReportContent(FRiskReviewOrder order) {
		String newReportContent = order.getReportContent();
		String caseInfo = order.getCaseInfo();
		String auditOpinion = order.getAuditOpinion();
		String preserveContent = order.getPreserveContent();
		String synthesizeOpinion = order.getSynthesizeOpinion();
		FRiskReviewReportDO rRiskReviewReportDO = FRiskReviewReportDAO.findById(order.getId());
		BeanCopier.staticCopy(rRiskReviewReportDO, order, UnBoxingConverter.getInstance());
		order.setReportContent(newReportContent);
		order.setCaseInfo(caseInfo);
		order.setAuditOpinion(auditOpinion);
		order.setPreserveContent(preserveContent);
		order.setSynthesizeOpinion(synthesizeOpinion);
		return saveRiskReview(order);
		
	}
	
}
