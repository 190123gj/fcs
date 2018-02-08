package com.born.fcs.pm.biz.service.council;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectBindOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetStatusOrder;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.CouncilProjectDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationDO;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.ProjectCreditConditionDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.CreditConditionTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuarantorInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectPledgeAssetInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilJudgeService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

/**
 * 会议纪要流程相关处理
 * 
 * @author wuzj
 */
@Service("councilSummaryProcessService")
public class CouncilSummaryProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	CouncilSummaryService councilSummaryService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	PledgeAssetService pledgeAssetServiceClient;
	
	@Autowired
	CouncilJudgeService councilJudgeService;
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		try {
			FCouncilSummaryInfo summary = councilSummaryService
				.queryCouncilSummaryByFormId(formInfo.getFormId());
			vars.put("会议编号", summary.getCouncilCode());
			//			String customMessage = "";
			//			if (formInfo.getStatus() == FormStatusEnum.APPROVAL) {
			//				String fileName = URLEncoder.encode(summary.getCouncilCode(), "utf-8");
			//				customMessage = "您可以申请用印了！<a href=\"/projectMg/index.htm?systemNameDefautUrl=/projectMg/stampapply/addStampApply.htm&fileName="
			//								+ fileName + "&fileContent=" + fileName + "\">申请用印</a>，";
			//			}
			//			vars.put("自定义信息", customMessage);
		} catch (Exception e) {
			logger.error("获取通知参数出错", e);
		}
		return vars;
	}
	
	//结果通知人员
	@Override
	public List<SimpleUserInfo> resultNotifyUserList(FormInfo formInfo) {
		List<SimpleUserInfo> list = Lists.newArrayList();
		try {
			//默认表单提交人员
			SimpleUserInfo user = new SimpleUserInfo();
			BeanCopier.staticCopy(formInfo, user);
			user.setMobile(formInfo.getUserMobile());
			user.setEmail(formInfo.getUserEmail());
			list.add(user);
			//同时发给评委
			FCouncilSummaryInfo summary = councilSummaryService
				.queryCouncilSummaryByFormId(formInfo.getFormId());
			if (summary != null) {
				List<CouncilJudgeInfo> judges = councilJudgeService.queryByCouncilId(summary
					.getCouncilId());
				for (CouncilJudgeInfo judge : judges) {
					user = new SimpleUserInfo();
					user.setUserId(judge.getJudgeId());
					user.setUserName(judge.getJudgeName());
					list.add(user);
				}
			}
		} catch (Exception e) {
			logger.error("获取结果通知人员列表出错：{}", e);
		}
		return list;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FCouncilSummaryInfo summary = councilSummaryService.queryCouncilSummaryByFormId(order
				.getFormInfo().getFormId());
			
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(summary.getCouncil().getCouncilTypeName() + "-会议纪要");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("会议纪要流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		//流程启动后 项目进入审核中
		FCouncilSummaryInfo summary = councilSummaryService.queryCouncilSummaryByFormId(formInfo
			.getFormId());
		
		//项目评审会
		if (summary.getCouncilType() == CouncilTypeEnum.PROJECT_REVIEW) {
			List<FCouncilSummaryProjectInfo> summaryProjects = councilSummaryService
				.queryProjectCsBySummaryId(summary.getSummaryId());
			
			ChangeProjectStatusOrder order = new ChangeProjectStatusOrder();
			for (FCouncilSummaryProjectInfo summaryProject : summaryProjects) {
				//已通过的项目
				if (summaryProject.getVoteResult() == ProjectVoteResultEnum.END_PASS
					&& summaryProject.getOneVoteDown() != BooleanEnum.YES) {
					order.setProjectCode(summaryProject.getProjectCode());
					order.setPhase(ProjectPhasesEnum.COUNCIL_PHASES);
					order.setPhaseStatus(ProjectPhasesStatusEnum.AUDITING);
					projectService.changeProjectStatus(order);
				}
			}
		}
	}
	
	@Override
	public void endFlowProcess(final FormInfo formInfo, final WorkflowResult workflowResult) {
		try {
			logger.info("会议纪要审核通过处理开始 formId:{} time:{}", formInfo.getFormId(), new Date());
			
			FcsBaseResult result = transactionTemplate
				.execute(new TransactionCallback<FcsBaseResult>() {
					@Override
					public FcsBaseResult doInTransaction(TransactionStatus status) {
						FcsBaseResult result = createResult();
						try {
							Date now = getSysdate();
							
							//流程结束后 项目进入合同阶段
							FCouncilSummaryInfo summary = councilSummaryService
								.queryCouncilSummaryByFormId(formInfo.getFormId());
							
							//项目评审会
							if (summary.getCouncilType() == CouncilTypeEnum.PROJECT_REVIEW) {
								
								List<FCouncilSummaryProjectDO> summaryProjects = FCouncilSummaryProjectDAO
									.findBySummaryId(summary.getSummaryId());
								
								for (FCouncilSummaryProjectDO summaryProject : summaryProjects) {
									
									summaryProject.setApprovalTime(now);
									
									//理财项目处理
									if (ProjectUtil.isFinancial(summaryProject.getProjectCode())) {
										
										ProjectFinancialDO projectFinancial = projectFinancialDAO
											.findByCode(summaryProject.getProjectCode());
										
										FProjectFinancialDO fproject = FProjectFinancialDAO
											.findByProjectCode(summaryProject.getProjectCode());
										
										boolean hasChange = false;
										if (ProjectVoteResultEnum.END_PASS.code().equals(
											summaryProject.getVoteResult())
											&& BooleanEnum.NO.code().equals(
												summaryProject.getOneVoteDown())) {
											
											//理财项目进入待购买状态
											projectFinancial
												.setStatus(FinancialProjectStatusEnum.CAPITAL_APPLY_WAITING
													.code());
											
											//修改立项的上会状态
											fproject
												.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_APPROVAL
													.code());
											
											hasChange = true;
										} else if ((ProjectVoteResultEnum.END_NOPASS.code().equals(
											summaryProject.getVoteResult()) || BooleanEnum.YES
											.code().equals(summaryProject.getOneVoteDown()))) { //不通过
										
											//理财项目进入待购买状态
											projectFinancial
												.setStatus(FinancialProjectStatusEnum.FAILED.code());
											
											//修改立项的上会状态
											fproject
												.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_DENY
													.code());
											hasChange = true;
										}
										if (hasChange) {
											projectFinancialDAO.update(projectFinancial);
											FProjectFinancialDAO.update(fproject);
											logger.info("理财项目立项上会状态：{}",
												fproject.getCouncilStatus());
											logger.info("理财项目状态：{}", projectFinancial.getStatus());
										}
										
										//授信项目处理
									} else {
										
										ProjectDO projectDO = projectDAO
											.findByProjectCode(summaryProject.getProjectCode());
										projectDO.setSpId(summaryProject.getSpId());
										projectDO.setSpCode(summaryProject.getSpCode());
										projectDO.setIsApprovalDel(BooleanEnum.NO.code());
										projectDO.setApprovalTime(now);
										projectDO.setIsMaximumAmount(summaryProject
											.getIsMaximumAmount() == null ? BooleanEnum.NO.code()
											: summaryProject.getIsMaximumAmount());
										
										//承销项目需要修改尽职调查的上会状态
										FInvestigationDO investigation = null;
										if (ProjectUtil.isUnderwriting(projectDO.getBusiType())) {
											
											//查询上会的申请ID
											CouncilProjectDO councilProject = councilProjectDAO
												.findByCouncilProjectCodeAndCouncilId(
													projectDO.getProjectCode(),
													summary.getCouncilId());
											
											//根据上会的ID查询到尽职调查
											if (councilProject != null) {
												investigation = FInvestigationDAO
													.findByProjectCouncilApplyId(
														projectDO.getProjectCode(),
														councilProject.getApplyId());
											}
										}
										
										if (ProjectVoteResultEnum.END_PASS.code().equals(
											summaryProject.getVoteResult())
											&& BooleanEnum.NO.code().equals(
												summaryProject.getOneVoteDown())) {
											
											//承销尽职调查的上会状态变成通过
											if (investigation != null)
												investigation
													.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_APPROVAL
														.code());
											
											projectDO.setIsApproval(BooleanEnum.IS.code());
											projectDO.setPhases(ProjectPhasesEnum.CONTRACT_PHASES
												.code());
											projectDO
												.setPhasesStatus(ProjectPhasesStatusEnum.WAITING
													.code());
											
											//部分信息同步到project
											projectDO.setAmount(summaryProject.getAmount());
											projectDO.setTimeLimit(summaryProject.getTimeLimit());
											if (summaryProject.getTimeUnit() != null)
												projectDO.setTimeUnit(summaryProject.getTimeUnit());
											logger
												.info(
													"会议纪要通过后同步项目信息  setIsApproval {},setPhases {},setPhasesStatus {},setAmount {},setTimeLimit {},setTimeUnit {}",
													BooleanEnum.IS,
													ProjectPhasesEnum.CONTRACT_PHASES,
													ProjectPhasesStatusEnum.WAITING,
													summaryProject.getAmount(),
													summaryProject.getTimeLimit(),
													summaryProject.getTimeUnit());
											
											//XXX 同步授信条件
											syncCreditCondition(summaryProject.getSpId(), projectDO);
											
											//未通过或者一票否决的
										} else if ((ProjectVoteResultEnum.END_NOPASS.code().equals(
											summaryProject.getVoteResult()) || BooleanEnum.YES
											.code().equals(summaryProject.getOneVoteDown()))) {
											//承销尽职调查的上会状态变成未通过
											if (investigation != null)
												investigation
													.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_DENY
														.code());
											
											//未通过只可复议一次
											if (projectDO.getLastRecouncilTime() == null) {
												projectDO.setIsRecouncil(BooleanEnum.IS.code());
											}
											
											//TODO 取消资产关联
											AssetRelationProjectBindOrder bindOrder = new AssetRelationProjectBindOrder();
											BeanCopier.staticCopy(projectDO, bindOrder);
											bindOrder.setDelOld(true);
											bindOrder.setAssetList(null);
											logger.info("解除资产项目关系 {} ", bindOrder);
											pledgeAssetServiceClient
												.assetRelationProject(bindOrder);
											
										}
										projectDAO.update(projectDO);
										
										//如果是承销 修改承销的上会状态
										if (investigation != null) {
											FInvestigationDAO.update(investigation);
										}
									}
									
									//更新 approvalTime
									FCouncilSummaryProjectDAO.update(summaryProject);
								} //循环结束
							}
							result.setSuccess(true);
						} catch (Exception e) {
							result.setSuccess(false);
							if (status != null)
								status.setRollbackOnly();
						}
						return result;
					}
				});
			if (result.isSuccess()) {
				logger.info("会议纪要审核通过处理结束 formId:{} time:{}", formInfo.getFormId(), new Date());
			} else {
				throw ExceptionFactory.newFcsException(result.getFcsResultEnum(), "会议纪要通过处理失败");
			}
		} catch (Exception e) {
			logger.error("会议纪要通过处理出错 : {}", e);
		}
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
