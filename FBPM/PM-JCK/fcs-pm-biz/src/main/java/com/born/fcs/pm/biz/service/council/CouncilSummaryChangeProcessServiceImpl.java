package com.born.fcs.pm.biz.service.council;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectBindOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetStatusOrder;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.exception.FcsPmBizException;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectCreditConditionDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CreditConditionTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuarantorInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectPledgeAssetInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplyInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordInfo;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.formchange.FormChangeProcessService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

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
					
					//XXX 同步授信条件
					syncCreditCondition(summaryProject.getSpId(), projectDO);
					
					result.setSuccess(true);
				} catch (Exception e) {
					logger.error("项目批复签报后处理失败");
					result.setSuccess(false);
					result.setMessage("项目批复签报后处理失败");
					if (status != null)
						status.setRollbackOnly();
				}
				
				return result;
			}
		});
		
	}
	
	/**
	 * 同步授信条件
	 * @param spId
	 * @param project
	 */
	private void syncCreditCondition(long spId, ProjectDO project) {
		
		// XXX 写入授信前提条件
		projectCreditConditionDAO.deleteByProjectCode(project.getProjectCode());
		
		FCouncilSummaryProjectCreditConditionInfo ccInfo = councilSummaryService
			.queryCreditConditionBySpId(spId, false);
		
		//同步关联信息到资产
		List<AssetStatusOrder> listOrder = Lists.newArrayList();
		
		if (ccInfo.getPledges() != null) {
			for (FCouncilSummaryProjectPledgeAssetInfo pledge : ccInfo.getPledges()) {
				ProjectCreditConditionDO ccd = new ProjectCreditConditionDO();
				ccd.setAssetId(pledge.getAssetsId());
				ccd.setProjectCode(project.getProjectCode());
				ccd.setIsConfirm(BooleanEnum.NO.code());
				ccd.setItemId(pledge.getId());
				ccd.setType(CreditConditionTypeEnum.PLEDGE.code());
				ccd.setStatus("NOT_APPLY");
				ccd.setReleaseStatus("WAITING");
				StringBuffer sbf = new StringBuffer();
				sbf.append("资产类型：").append(pledge.getAssetType()).append("； 权利人：")
					.append(pledge.getOwnershipName()).append("； 抵押顺位：")
					.append(StringUtil.equals(pledge.getSynPosition(), "FIRST") ? "第一顺位" : "第二顺位")
					.append("； 是否后置抵押：")
					.append(StringUtil.equals(pledge.getSynPosition(), "YES") ? "是" : "否")
					.append("已抵债金额：").append(MoneyUtil.formatWithUnit(pledge.getDebtedAmount()));
				ccd.setItemDesc(sbf.toString());
				projectCreditConditionDAO.insert(ccd);
				
				//构建资产项目关联的Order
				AssetStatusOrder as = new AssetStatusOrder();
				as.setAssetId(pledge.getAssetsId());
				as.setStatus(AssetStatusEnum.QUASI_PLEDGE);
				listOrder.add(as);
			}
		}
		
		if (ccInfo.getMortgages() != null) {
			for (FCouncilSummaryProjectPledgeAssetInfo mortgage : ccInfo.getMortgages()) {
				ProjectCreditConditionDO ccd = new ProjectCreditConditionDO();
				ccd.setAssetId(mortgage.getAssetsId());
				ccd.setProjectCode(project.getProjectCode());
				ccd.setIsConfirm(BooleanEnum.NO.code());
				ccd.setItemId(mortgage.getId());
				ccd.setType(CreditConditionTypeEnum.MORTGAGE.code());
				ccd.setStatus("NOT_APPLY");
				ccd.setReleaseStatus("WAITING");
				StringBuffer sbf = new StringBuffer();
				sbf.append("资产类型：")
					.append(mortgage.getAssetType())
					.append("； 权利人：")
					.append(mortgage.getOwnershipName())
					.append("； 质押顺位：")
					.append(StringUtil.equals(mortgage.getSynPosition(), "FIRST") ? "第一顺位" : "第二顺位")
					.append("； 是否后置抵押：")
					.append(StringUtil.equals(mortgage.getSynPosition(), "YES") ? "是" : "否")
					.append("已抵债金额：").append(MoneyUtil.formatWithUnit(mortgage.getDebtedAmount()));
				ccd.setItemDesc(sbf.toString());
				projectCreditConditionDAO.insert(ccd);
				
				//构建资产项目关联的Order
				AssetStatusOrder as = new AssetStatusOrder();
				as.setAssetId(mortgage.getAssetsId());
				as.setStatus(AssetStatusEnum.QUASI_MORTGAGE);
				listOrder.add(as);
			}
		}
		
		if (ccInfo.getGuarantors() != null) {
			for (FCouncilSummaryProjectGuarantorInfo guarantor : ccInfo.getGuarantors()) {
				ProjectCreditConditionDO ccd = new ProjectCreditConditionDO();
				ccd.setProjectCode(project.getProjectCode());
				ccd.setIsConfirm(BooleanEnum.NO.code());
				ccd.setItemId(guarantor.getId());
				ccd.setType(CreditConditionTypeEnum.GUARANTOR.code());
				ccd.setStatus("NOT_APPLY");
				ccd.setReleaseStatus("WAITING");
				StringBuffer sbf = new StringBuffer();
				sbf.append("保证人：")
					.append(guarantor.getGuarantor())
					.append("；保证类型:")
					.append(
						guarantor.getGuarantorType() == null ? "无" : guarantor.getGuarantorType()
							.message()).append("； 保证额度：")
					.append(guarantor.getGuaranteeAmount().toStandardString()).append("元")
					.append("； 担保方式：").append(guarantor.getGuaranteeWay());
				ccd.setItemDesc(sbf.toString());
				projectCreditConditionDAO.insert(ccd);
			}
		}
		
		try {
			///XXX 同步资产项目关系
			AssetRelationProjectBindOrder bindOrder = new AssetRelationProjectBindOrder();
			BeanCopier.staticCopy(project, bindOrder);
			bindOrder.setDelOld(true);
			bindOrder.setAssetList(listOrder);
			logger.info("同步资产项目关系 {} ", bindOrder);
			pledgeAssetServiceClient.assetRelationProject(bindOrder);
		} catch (Exception e) {
			logger.error("同步资产项目关系出错 {} ", e);
		}
	}
}
