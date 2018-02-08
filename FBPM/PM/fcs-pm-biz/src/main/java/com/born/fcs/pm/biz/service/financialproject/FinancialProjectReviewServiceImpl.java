package com.born.fcs.pm.biz.service.financialproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialReviewDO;
import com.born.fcs.pm.dataobject.FinancialProjectReviewFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialReviewInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectReviewFormInfo;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialReviewFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialReviewOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialRiskReviewOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectReviewService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("financialProjectReviewService")
public class FinancialProjectReviewServiceImpl extends BaseFormAutowiredDomainService implements
																						FinancialProjectReviewService {
	
	@Autowired
	FinancialProjectService financialProjectService;
	
	@Autowired
	FinancialProjectSetupService financialProjectSetupService;
	
	@Override
	public QueryBaseBatchResult<FinancialProjectReviewFormInfo> queryPage(	ProjectFinancialReviewFormQueryOrder order) {
		QueryBaseBatchResult<FinancialProjectReviewFormInfo> batchResult = new QueryBaseBatchResult<FinancialProjectReviewFormInfo>();
		
		try {
			FinancialProjectReviewFormDO projectForm = new FinancialProjectReviewFormDO();
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("f.form_id");
			}
			
			if (StringUtil.isBlank(order.getSortOrder())) {
				order.setSortOrder("DESC");
			}
			
			projectForm.setReviewId(order.getReviewId());
			projectForm.setProductName(order.getProductName());
			projectForm.setIssueInstitution(order.getIssueInstitution());
			projectForm.setFormStatus(order.getFormStatus());
			projectForm.setProjectCode(order.getProjectCode());
			projectForm.setLoginUserId(order.getLoginUserId());
			projectForm.setDraftUserId(order.getDraftUserId());
			projectForm.setDeptIdList(order.getDeptIdList());
			
			projectForm.setFormStatusList(order.getFormStatusList());
			
			long totalCount = busiDAO.searchFinancialProjectReviewFormCount(projectForm);
			PageComponent component = new PageComponent(order, totalCount);
			
			//查询的分页参数
			projectForm.setSortCol(order.getSortCol());
			projectForm.setSortOrder(order.getSortOrder());
			projectForm.setLimitStart(component.getFirstRecord());
			projectForm.setPageSize(component.getPageSize());
			
			List<FinancialProjectReviewFormDO> list = busiDAO
				.searchFinancialProjectReviewForm(projectForm);
			
			List<FinancialProjectReviewFormInfo> pageList = new ArrayList<FinancialProjectReviewFormInfo>(
				list.size());
			for (FinancialProjectReviewFormDO DO : list) {
				FinancialProjectReviewFormInfo info = new FinancialProjectReviewFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setProject(financialProjectSetupService.queryByProjectCode(info
					.getProjectCode()));
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询理财项目送审申请列表失败 {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FProjectFinancialReviewInfo queryByFormId(long formId) {
		FProjectFinancialReviewDO review = FProjectFinancialReviewDAO.findByFormId(formId);
		if (review != null) {
			FProjectFinancialReviewInfo info = new FProjectFinancialReviewInfo();
			BeanCopier.staticCopy(review, info);
			return info;
		}
		return null;
	}
	
	@Override
	public FormBaseResult save(final ProjectFinancialReviewOrder order) {
		
		order.setFormCode(FormCodeEnum.FINANCING_REVIEW);
		
		return commonFormSaveProcess(order, "保存理财项目送审申请单", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
				
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				boolean isUpdate = false;
				FProjectFinancialReviewDO review = FProjectFinancialReviewDAO.findByFormId(order
					.getFormId());
				if (review == null) {
					review = new FProjectFinancialReviewDO();
					review.setRawAddTime(now);
				} else {
					isUpdate = true;
				}
				review.setFormId(form.getFormId());
				review.setProjectCode(order.getProjectCode());
				review.setInvestigation(order.getInvestigation());
				review.setInvestigationAttach(order.getInvestigationAttach());
				review.setRiskReview(order.getRiskReview());
				review.setRiskReviewAttach(order.getRiskReviewAttach());
				if (isUpdate) {
					FProjectFinancialReviewDAO.update(review);
				} else {
					FProjectFinancialReviewDAO.insert(review);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult saveRiskReview(final ProjectFinancialRiskReviewOrder order) {
		return commonProcess(order, "保存理财项目风险审查报告", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				FProjectFinancialReviewDO review = FProjectFinancialReviewDAO.findByFormId(order
					.getFormId());
				if (review == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "申请单不存在");
				}
				review.setRiskReview(order.getRiskReview());
				review.setRiskReviewAttach(order.getRiskReviewAttach());
				FProjectFinancialReviewDAO.update(review);
				return null;
			}
		}, null, null);
	}
}
