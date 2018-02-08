package com.born.fcs.pm.biz.service.council;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.exception.FcsPmBizException;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplyInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordInfo;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.formchange.FormChangeProcessService;

/**
 * 项目批复签报后处理
 * 
 * @author wuzj
 */
@Service("councilSummaryChangeProcessService")
public class CouncilSummaryChangeProcessServiceImpl extends BaseAutowiredDomainService implements
																						FormChangeProcessService {
	
	@Autowired
	CouncilSummaryService councilSummaryService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	ProjectContractService projectContractService;
	
	@Autowired
	PledgeAssetService pledgeAssetServiceClient;
	
	@Autowired
	CouncilSummaryProcessServiceImpl councilSummaryProcessService;
	
	@Override
	public FcsBaseResult checkCanChange(FormCheckCanChangeOrder checkOrder) {
		FcsBaseResult result = createResult();
		try {
			ProjectInfo project = checkOrder.getProject();
			if (project == null && StringUtil.isNotEmpty(checkOrder.getProjectCode())) {
				project = projectService.queryByCode(checkOrder.getProjectCode(), false);
			}
			
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
			}
			
			if (project.getIsApproval() != BooleanEnum.IS) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
					"项目未通过，批复不存在");
			}
			
			if (project.getIsApprovalDel() == BooleanEnum.IS) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
					"批复已作废");
			}
			
			if (project.getIsRedoProject() == BooleanEnum.IS
				&& StringUtil.isBlank(project.getSpCode())) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
					"风险处置会重新授信项目，批复不存在");
			}
			
			//			if (StringUtil.isNotBlank(project.getContractNo())) {
			//				throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
			//					"合同已签订,不能进行签报");
			//			}
			//			
			result.setSuccess(true);
			result.setMessage("可以签报");
			
		} catch (FcsPmBizException be) {
			result.setSuccess(false);
			result.setFcsResultEnum(be.getResultCode());
			result.setMessage(be.getErrorMsg());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("检查批复是否可签报出错");
			logger.error("检查批复是否可签报出错:{}", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult processChange(final FormChangeApplyInfo applyInfo,
										final FormChangeRecordInfo record) {
		
		return transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
			@Override
			public FcsBaseResult doInTransaction(TransactionStatus status) {
				FcsBaseResult result = createResult();
				
				try {
					
					ProjectDO projectDO = projectDAO.findByProjectCode(applyInfo.getProjectCode());
					
					FCouncilSummaryProjectDO summaryProject = FCouncilSummaryProjectDAO
						.findById(projectDO.getSpId());
					projectDO.setIsMaximumAmount(summaryProject.getIsMaximumAmount() == null ? BooleanEnum.NO
						.code() : summaryProject.getIsMaximumAmount());
					
					//部分信息同步到project
					projectDO.setAmount(summaryProject.getAmount());
					projectDO.setTimeLimit(summaryProject.getTimeLimit());
					if (summaryProject.getTimeUnit() != null)
						projectDO.setTimeUnit(summaryProject.getTimeUnit());
					
					projectDAO.update(projectDO);
					
					// 同步授信条件
					councilSummaryProcessService.syncCreditCondition(summaryProject.getSpId(),
						projectDO);
					
					result.setSuccess(true);
				} catch (Exception e) {
					logger.error("项目批复签报后处理失败{}", e);
					result.setSuccess(false);
					result.setMessage("项目批复签报后处理失败");
					if (status != null)
						status.setRollbackOnly();
				}
				
				return result;
			}
		});
		
	}
	
}
