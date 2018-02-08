package com.born.fcs.pm.biz.service.council;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
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
import com.born.fcs.crm.ws.service.ChannalService;
import com.born.fcs.crm.ws.service.enums.ChanalTypeEnum;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.PersonalCustomerInfo;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.info.forecast.SysForecastParamAllInfo;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.result.forecast.SysForecastParamResult;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.CouncilProjectDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectBondDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectEntrustedDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectGuaranteeDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectLgLitigationDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectUnderwritingDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationDO;
import com.born.fcs.pm.dal.dataobject.FProjectContractItemDO;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialTansferApplyDO;
import com.born.fcs.pm.dal.dataobject.ProjectCreditConditionDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.integration.crm.service.customer.CompanyCustomerServiceClient;
import com.born.fcs.pm.integration.crm.service.customer.PersonalCustomerServiceClient;
import com.born.fcs.pm.integration.risk.service.RiskSystemFacadeClient;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.CreditConditionTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.OneVoteResultEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;
import com.born.fcs.pm.ws.info.council.CouncilParticipantInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuarantorInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectPledgeAssetInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectTextCreditConditionInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.project.ProjectRedoOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
import com.born.fcs.pm.ws.service.council.CouncilJudgeService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
import com.bornsoft.pub.enums.RiskTypeEnum;
import com.bornsoft.pub.order.risk.SynRiskInfoOrder;
import com.bornsoft.pub.order.risk.SynRiskInfoOrder.RiskInfo;
import com.bornsoft.utils.base.BornSynResultBase;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

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
	
	@Autowired
	private RiskSystemFacadeClient riskSystemFacadeClient;
	
	@Autowired
	private CompanyCustomerServiceClient companyCustomerServiceClient;
	
	@Autowired
	private PersonalCustomerServiceClient personalCustomerServiceClient;
	
	@Autowired
	ForecastService forecastServiceClient;
	
	@Autowired
	ChannalService channelClient;
	
	@Autowired
	FinancialProjectSetupService financialProjectSetupService;
	@Autowired
	ProjectContractService projectContractService;
	@Autowired
	ProjectChannelRelationService projectChannelRelationService;
	
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
			
			//审核通过后同时发给评委、列席人员、业务经理
			if (formInfo.getStatus() == FormStatusEnum.APPROVAL) {
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
				
				//评委
				CouncilInfo council = summary.getCouncil();
				if (ListUtil.isNotEmpty(council.getJudges())) {
					for (CouncilJudgeInfo judge : council.getJudges()) {
						user = new SimpleUserInfo();
						user.setUserId(judge.getJudgeId());
						user.setUserName(judge.getJudgeName());
						list.add(user);
					}
				}
				
				//列席人员
				if (ListUtil.isNotEmpty(council.getParticipants())) {
					for (CouncilParticipantInfo participant : council.getParticipants()) {
						user = new SimpleUserInfo();
						user.setUserId(participant.getParticipantId());
						user.setUserName(participant.getParticipantName());
						list.add(user);
					}
				}
				
				//业务经理
				if (summary.getCouncilType() == CouncilTypeEnum.PROJECT_REVIEW) {
					List<FCouncilSummaryProjectInfo> projects = councilSummaryService
						.queryProjectCsBySummaryId(summary.getSummaryId());
					if (ListUtil.isNotEmpty(projects)) {
						for (FCouncilSummaryProjectInfo project : projects) {
							ProjectRelatedUserInfo busiManager = projectRelatedUserService
								.getBusiManager(project.getProjectCode());
							if (busiManager != null)
								list.add(busiManager.toSimpleUserInfo());
						}
					}
				} else {
					List<FCouncilSummaryRiskHandleInfo> projects = councilSummaryService
						.queryRiskHandleCsBySummaryId(summary.getSummaryId());
					if (ListUtil.isNotEmpty(projects)) {
						for (FCouncilSummaryRiskHandleInfo project : projects) {
							ProjectRelatedUserInfo busiManager = projectRelatedUserService
								.getBusiManager(project.getProjectCode());
							if (busiManager != null)
								list.add(busiManager.toSimpleUserInfo());
						}
					}
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
					&& summaryProject.getOneVoteDown() == OneVoteResultEnum.PASS) {
					if (!ProjectUtil.isFinancial(summaryProject.getProjectCode())) {
						ProjectInfo project = projectService.queryByCode(
							summaryProject.getProjectCode(), false);
						if (project != null
							&& (project.getLastRecouncilTime() == null || project.getIsApproval() != BooleanEnum.IS)) {
							order.setProjectCode(summaryProject.getProjectCode());
							order.setPhase(ProjectPhasesEnum.COUNCIL_PHASES);
							order.setPhaseStatus(ProjectPhasesStatusEnum.AUDITING);
							projectService.changeProjectStatus(order);
						}
					}
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
							if (summary.getCouncilType() == CouncilTypeEnum.RISK_HANDLE) {
								logger.info("预备风险处置会选择");
								logger.info("风险处置会选择");
								SynRiskInfoOrder order = new SynRiskInfoOrder();
								List<RiskInfo> riskList = new ArrayList<RiskInfo>();
								List<FCouncilSummaryRiskHandleInfo> risks = councilSummaryService
									.queryRiskHandleCsBySummaryId(summary.getSummaryId());
								
								for (FCouncilSummaryRiskHandleInfo risk : risks) {
									if (BooleanEnum.IS == risk.getIsExtend()) {
										RiskInfo info = new RiskInfo();
										info.setRiskType(RiskTypeEnum.ROLLEDOVER);
										fullRiskInfo(risk, info);
										riskList.add(info);
										logger.info("风险处置会选择:展期+1");
									}
									if (BooleanEnum.IS == risk.getIsComp()) {
										RiskInfo info = new RiskInfo();
										info.setRiskType(RiskTypeEnum.COMPENSATORY);
										fullRiskInfo(risk, info);
										riskList.add(info);
										logger.info("风险处置会选择:代偿+1");
									}
									
									if (BooleanEnum.IS == risk.getIsRedo()) {//重新授信
										logger.info("重新授信:{}", risk);
										ProjectRedoOrder redoOrder = new ProjectRedoOrder();
										redoOrder.setProjectCode(risk.getProjectCode());
										redoOrder.setHandleId(risk.getHandleId());
										FcsBaseResult redoResult = projectService
											.redoProject(redoOrder);
										logger.info("重新授信结果:{} {}", redoResult, risk);
									}
								}
								
								logger.info("准备进入发送风险处置会判定");
								if (ListUtil.isNotEmpty(riskList)) {
									order.setList(riskList);
									// 外部订单号
									order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
									logger.info("风险处置会入参:" + order);
									BornSynResultBase basere = riskSystemFacadeClient
										.synRiskInfo(order);
									logger.info("风险处置会调用结果:" + basere);
								}
								
								//同步到资金预测 
								syscCompInfoForecast(risks);
								
							} else
							
							//项目评审会
							if (summary.getCouncilType() == CouncilTypeEnum.PROJECT_REVIEW) {
								
								List<FCouncilSummaryProjectDO> summaryProjects = FCouncilSummaryProjectDAO
									.findBySummaryId(summary.getSummaryId());
								
								for (FCouncilSummaryProjectDO summaryProject : summaryProjects) {
									
									//表示已经生成了批复（已经提交过了）
									if (summaryProject.getApprovalTime() != null)
										continue;
									
									processProject(now, summary, summaryProject);
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
	
	private void fullRiskInfo(FCouncilSummaryRiskHandleInfo risk, RiskInfo info) {
		try {
			// 处置方式为代偿 展期 逾期，同步风险监控
			// 获取项目
			ProjectInfo project = projectService.queryByCode(risk.getProjectCode(), false);
			// 获取客户
			if (CustomerTypeEnum.ENTERPRISE == risk.getCustomerType()) {
				CompanyCustomerInfo customerInfo = companyCustomerServiceClient.queryByUserId(
					risk.getCustomerId(), null);
				info.setLicenseNo(customerInfo.getBusiLicenseNo());
				info.setCustomName(customerInfo.getCustomerName());
			} else {
				PersonalCustomerInfo customerInfo = personalCustomerServiceClient
					.queryByUserId(risk.getCustomerId());
				info.setLicenseNo(customerInfo.getCertNo());
				info.setCustomName(customerInfo.getCustomerName());
			}
			info.setCreditAmount(project.getAmount());
			info.setCreditStartTime(DateUtil.dtSimpleFormat(project.getStartTime()));
			info.setCreditEndTime(DateUtil.dtSimpleFormat(project.getEndTime()));
		} catch (Exception e) {
			logger.error("填充风险信息出错：{}", e);
		}
	}
	
	public void processProject(Date now, FCouncilSummaryInfo summary,
								FCouncilSummaryProjectDO summaryProject) {
		
		summaryProject.setApprovalTime(now);
		if (StringUtil.isBlank(summaryProject.getOneVoteDown())) {//没发表意见默认通过 
			summaryProject.setOneVoteDown(OneVoteResultEnum.PASS.code());
		}
		//理财项目处理
		if (ProjectUtil.isFinancial(summaryProject.getProjectCode())) {
			
			FProjectFinancialDO fproject = FProjectFinancialDAO.findByProjectCode(summaryProject
				.getProjectCode());
			
			boolean pass = ProjectVoteResultEnum.END_PASS.code().equals(
				summaryProject.getVoteResult())
							&& OneVoteResultEnum.PASS.code()
								.equals(summaryProject.getOneVoteDown());
			
			boolean noPass = (ProjectVoteResultEnum.END_NOPASS.code().equals(
				summaryProject.getVoteResult()) || OneVoteResultEnum.NO_PASS.code().equals(
				summaryProject.getOneVoteDown()));
			
			if (fproject != null) {
				
				//立项
				boolean hasChange = false;
				if (pass) {
					
					//修改立项的上会状态
					fproject.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_APPROVAL.code());
					hasChange = true;
					//同步到资金预测
					financialProjectSetupService.syncForecast(fproject.getProjectCode());
					
				} else if (noPass) { //不通过
				
					//修改立项的上会状态
					fproject.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_DENY.code());
					hasChange = true;
				}
				if (hasChange) {
					FProjectFinancialDAO.update(fproject);
					logger.info("理财项目立项上会状态：{}", fproject.getCouncilStatus());
				}
			} else { //转让
			
				//上会选择的项目
				CouncilProjectDO councilProject = councilProjectDAO
					.findByCouncilProjectCodeAndCouncilId(summaryProject.getProjectCode(),
						summary.getCouncilId());
				
				if (councilProject != null) {
					
					//项目所有转让申请信息
					List<FProjectFinancialTansferApplyDO> transferList = FProjectFinancialTansferApplyDAO
						.findByProjectCode(summaryProject.getProjectCode());
					
					//找到是那一笔转让上会的
					for (FProjectFinancialTansferApplyDO transfer : transferList) {
						if (councilProject.getApplyId() == transfer.getCouncilApplyId()) {
							//根据会议结果改变状态
							if (pass) {
								transfer.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_APPROVAL
									.code());
								FProjectFinancialTansferApplyDAO.update(transfer);
							} else if (noPass) {
								transfer.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_DENY
									.code());
								FProjectFinancialTansferApplyDAO.update(transfer);
							}
						}
					}
				}
			}
			
			//授信项目处理
		} else {
			
			ProjectDO projectDO = projectDAO.findByProjectCode(summaryProject.getProjectCode());
			projectDO.setIsApprovalDel(BooleanEnum.NO.code());
			projectDO
				.setIsMaximumAmount(summaryProject.getIsMaximumAmount() == null ? BooleanEnum.NO
					.code() : summaryProject.getIsMaximumAmount());
			
			//过会复议的项目通过才更改相关编号
			if (projectDO.getLastRecouncilTime() == null
				|| (projectDO.getLastRecouncilTime() != null
					&& StringUtil.equals(projectDO.getIsApproval(), "IS")
					&& ProjectVoteResultEnum.END_PASS.code().equals(summaryProject.getVoteResult()) && (OneVoteResultEnum.PASS
					.code().equals(summaryProject.getOneVoteDown()) || OneVoteResultEnum.RE_COUNCIL
					.code().equals(summaryProject.getOneVoteDown())))) {
				projectDO.setSpId(summaryProject.getSpId());
				projectDO.setSpCode(summaryProject.getSpCode());
				projectDO.setApprovalTime(now);
			}
			
			//承销项目需要修改尽职调查的上会状态
			FInvestigationDO investigation = null;
			if (ProjectUtil.isUnderwriting(projectDO.getBusiType())) {
				
				//查询上会的申请ID
				CouncilProjectDO councilProject = councilProjectDAO
					.findByCouncilProjectCodeAndCouncilId(projectDO.getProjectCode(),
						summary.getCouncilId());
				
				//根据上会的ID查询到尽职调查
				if (councilProject != null) {
					investigation = FInvestigationDAO.findByProjectCouncilApplyId(
						projectDO.getProjectCode(), councilProject.getApplyId());
				}
			}
			
			if (ProjectVoteResultEnum.END_PASS.code().equals(summaryProject.getVoteResult())
				&& OneVoteResultEnum.PASS.code().equals(summaryProject.getOneVoteDown())) {
				
				//承销尽职调查的上会状态变成通过
				if (investigation != null)
					investigation
						.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_APPROVAL.code());
				
				//部分信息同步到project
				projectDO.setAmount(summaryProject.getAmount());
				projectDO.setTimeLimit(summaryProject.getTimeLimit());
				if (summaryProject.getTimeUnit() != null)
					projectDO.setTimeUnit(summaryProject.getTimeUnit());
				projectDO.setLoanPurpose(summaryProject.getLoanPurpose());
				
				//过会复议的不改变阶段
				if (projectDO.getLastRecouncilTime() == null
					|| !StringUtil.equals(projectDO.getIsApproval(), "IS")) {
					projectDO.setPhases(ProjectPhasesEnum.CONTRACT_PHASES.code());
					projectDO.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
					projectDO.setStatus(ProjectStatusEnum.NORMAL.code());
					logger
						.info(
							"会议纪要通过后同步项目信息 projectCode {} setIsApproval {},setPhases {},setPhasesStatus {},setAmount {},setTimeLimit {},setTimeUnit {}",
							summaryProject.getProjectCode(), BooleanEnum.IS,
							ProjectPhasesEnum.CONTRACT_PHASES, ProjectPhasesStatusEnum.WAITING,
							summaryProject.getAmount(), summaryProject.getTimeLimit(),
							summaryProject.getTimeUnit());
				} else if (StringUtil
					.equals(projectDO.getStatus(), ProjectStatusEnum.FAILED.code())) {
					projectDO.setStatus(ProjectStatusEnum.NORMAL.code());
				}
				projectDO.setIsApproval(BooleanEnum.IS.code());
				projectDO.setIsApprovalDel(BooleanEnum.NO.code());
				
				// 同步授信条件
				syncCreditCondition(summaryProject.getSpId(), projectDO);
				
				//上会通过同步到资金系统
				syncTofinancialSys(summaryProject.getSpId(), projectDO);
				
				//未通过或者一票否决的
			} else if ((ProjectVoteResultEnum.END_NOPASS.code().equals(
			
			summaryProject.getVoteResult()) || !OneVoteResultEnum.PASS.code().equals(
				summaryProject.getOneVoteDown()))) {
				
				//承销尽职调查的上会状态变成未通过
				if (investigation != null)
					investigation.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_DENY.code());
				
				//未通过只可复议一次
				//if (projectDO.getLastRecouncilTime() == null) {
				projectDO.setIsRecouncil(BooleanEnum.IS.code());
				//}
				
				// 取消资产关联
				AssetRelationProjectBindOrder bindOrder = new AssetRelationProjectBindOrder();
				BeanCopier.staticCopy(projectDO, bindOrder);
				bindOrder.setDelOld(true);
				bindOrder.setAssetList(null);
				logger.info("解除资产项目关系 {} ", bindOrder);
				pledgeAssetServiceClient.assetRelationProject(bindOrder);
				
			}
			projectDAO.update(projectDO);
			
			//如果是承销 修改承销的上会状态
			if (investigation != null) {
				FInvestigationDAO.update(investigation);
			}
		}
		
		//更新 approvalTime
		FCouncilSummaryProjectDAO.update(summaryProject);
	}
	
	/**
	 * 同步代偿款划出预测
	 * 预测时间为风险处置会的会议纪要中的代偿截止时间，（会议纪要审核通过后开始预测）；代偿金额为会议纪要中代偿的总金额（在会议纪要中一共有5种金额
	 * ：代偿本金、代偿利息、违约金、罚息、其他）；
	 * @param risk
	 */
	private void syscCompInfoForecast(List<FCouncilSummaryRiskHandleInfo> risks) {
		if (ListUtil.isNotEmpty(risks)) {
			for (FCouncilSummaryRiskHandleInfo risk : risks) {
				if (risk.getIsComp() == BooleanEnum.IS) {
					ProjectInfo project = projectService.queryByCode(risk.getProjectCode(), false);
					ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
					Money outMoney = Money.zero();
					outMoney.addTo(risk.getCompPrincipal());
					//					outMoney.addTo(risk.getCompInterest());
					//					outMoney.addTo(risk.getCompPenalty());
					//					outMoney.addTo(risk.getCompPenaltyInterest());
					//					outMoney.addTo(risk.getCompOther());
					forecastAccountOrder.setForecastStartTime(risk.getCompEndTime());
					forecastAccountOrder.setAmount(outMoney);
					forecastAccountOrder.setForecastMemo("代偿款划出（" + risk.getProjectCode() + "）");
					forecastAccountOrder.setForecastType(ForecastTypeEnum.OUT_DCKHC);
					forecastAccountOrder.setFundDirection(FundDirectionEnum.OUT);
					forecastAccountOrder.setOrderNo(risk.getProjectCode() + "_"
													+ ForecastFeeTypeEnum.COMP.code() + "_"
													+ risk.getSummaryId() + "_"
													+ risk.getHandleId());
					forecastAccountOrder.setSystemForm(SystemEnum.PM);
					forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
					forecastAccountOrder.setUsedDeptName(project.getDeptName());
					forecastAccountOrder.setProjectCode(project.getProjectCode());
					forecastAccountOrder.setCustomerId(project.getCustomerId());
					forecastAccountOrder.setCustomerName(project.getCustomerName());
					forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.COMP);
					logger.info("代偿款划出资金流出预测,projectCode：{}, forecastAccountOrder：{} ",
						risk.getProjectCode(), forecastAccountOrder);
					forecastServiceClient.add(forecastAccountOrder);
				}
			}
		}
	}
	
	/**
	 * 流出： 保证金划出：预测时间为会议纪要审核通过+配置天数，金额为在渠道管理中的保证金比例*授信金额；（是否预测：渠道为银行的，要求缴纳保证的 ）
	 * 
	 * 流入：
	 * 担保费收取：收取时间为会议纪要审核通过+配置天数，金额计算方式：拟授信金额*费率*授信期限，该处根据不同的授信期限有不同的费用预估计算公式：
	 * 授信期限单位为年：授信金额*费率*授信期限（X年） 授信期限单位为月：授信金额*费率/12个月*授信期限（X月）
	 * 授信期限单位为日：授信金额*费率/365*授信期限（X日）
	 * @param project
	 */
	public void syncTofinancialSys(long spId, ProjectDO project) {
		try {
			
			logger.error("同步预测数据到资金系统：proejctCode {} busiType {} {} ，{}", project.getProjectCode(),
				project.getBusiType(), project.getBusiTypeName());
			
			//查询预测规则  没查询到用默认规则
			SysForecastParamAllInfo rule = new SysForecastParamAllInfo();
			SysForecastParamResult result = forecastServiceClient.findAll();
			if (result != null && result.isSuccess()) {
				rule = result.getParamAllInfo();
			}
			
			String busiType = project.getBusiType();
			if (busiType.startsWith("11")) {//银行融资担保
				syncYhrzdbForecast(spId, project, rule);
			} else if (busiType.startsWith("12")) {//债券融资担保
				syncZqrzdbForecast(spId, project, rule);
			} else if (busiType.startsWith("13")) { //金融产品担保
				syncJrcpdbForecast(spId, project, rule);
			} else if (busiType.startsWith("2")) {//非融资担保业务
				syncFrzdbForecast(spId, project, rule);
			} else if (busiType.startsWith("3")) {//再担保业务
				syncZdbForecast(spId, project, rule);
			} else if (busiType.startsWith("4")) {//委托贷款业务
				syncWtdkForecast(spId, project, rule);
			} else if (busiType.startsWith("5")) { //承销业务
				syncCxForecast(spId, project, rule);
			} else if (busiType.startsWith("6")) { //小贷业务
				syncXdForecast(spId, project, rule);
			} else if (busiType.startsWith("7")) { //其他
				syncOtherForecast(spId, project, rule);
			}
		} catch (Exception e) {
			logger.error("同步到资金系统出错：proejctCode {} busiType {} {} ，{}", project.getProjectCode(),
				project.getBusiType(), project.getBusiTypeName(), e);
		}
	}
	
	/***
	 * 资金预测 - 银行融资担保
	 * @param spId
	 * @param project
	 * @param rule
	 */
	private void syncYhrzdbForecast(long spId, ProjectDO project, SysForecastParamAllInfo rule) {
		syncDbForecast(spId, project, rule, ForecastTypeEnum.OUT_YHRZDB, ForecastTypeEnum.IN_YHRZDB);
	}
	
	/***
	 * 资金预测 - 金融产品担保
	 * @param spId
	 * @param project
	 * @param rule
	 */
	private void syncJrcpdbForecast(long spId, ProjectDO project, SysForecastParamAllInfo rule) {
		syncDbForecast(spId, project, rule, ForecastTypeEnum.OUT_JRCPDB, ForecastTypeEnum.IN_JRCPDB);
	}
	
	/***
	 * 资金预测 - 非融资担保业务
	 * @param spId
	 * @param project
	 * @param rule
	 */
	private void syncFrzdbForecast(long spId, ProjectDO project, SysForecastParamAllInfo rule) {
		
		if (ProjectUtil.isLitigation(project.getBusiType())) {
			FCouncilSummaryProjectLgLitigationDO detail = FCouncilSummaryProjectLgLitigationDAO
				.findBySpId(spId);
			if (detail == null) {
				return;
			}
			
			//流入 收取担保费
			Money guaranteeMoney = Money.zero();
			if (StringUtil.equals(detail.getGuaranteeFeeType(), ChargeTypeEnum.AMOUNT.code())) {
				guaranteeMoney = Money.amout(String.valueOf(detail.getGuaranteeFee()));
			} else { //根据期限算担保费
				if (StringUtil.equals(project.getTimeUnit(), TimeUnitEnum.YEAR.code())) {
					guaranteeMoney = project.getAmount().multiply(detail.getGuaranteeFee())
						.divide(100);
				}
			}
			if (guaranteeMoney.greaterThan(ZERO_MONEY)) {
				ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
				Calendar calendar = Calendar.getInstance();
				if (rule.getInFrzdbywTimeType() == TimeUnitEnum.YEAR) {
					calendar.add(Calendar.YEAR, NumberUtil.parseInt(rule.getInFrzdbywTime()));
				} else if (rule.getInFrzdbywTimeType() == TimeUnitEnum.MONTH) {
					calendar.add(Calendar.MONTH, NumberUtil.parseInt(rule.getInFrzdbywTime()));
				} else if (rule.getInFrzdbywTimeType() == TimeUnitEnum.DAY) {
					calendar.add(Calendar.DAY_OF_MONTH,
						NumberUtil.parseInt(rule.getInFrzdbywTime()));
				}
				forecastAccountOrder.setForecastStartTime(calendar.getTime());
				forecastAccountOrder.setAmount(guaranteeMoney);
				forecastAccountOrder.setForecastMemo("收取担保费（" + project.getProjectCode() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.IN_FRZDBYW);
				forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
				forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
												+ ForecastFeeTypeEnum.GUARANTEE_FEE.code());
				forecastAccountOrder.setSystemForm(SystemEnum.PM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
				forecastAccountOrder.setUsedDeptName(project.getDeptName());
				forecastAccountOrder.setProjectCode(project.getProjectCode());
				forecastAccountOrder.setCustomerId(project.getCustomerId());
				forecastAccountOrder.setCustomerName(project.getCustomerName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.GUARANTEE_FEE);
				logger.info("诉保上会通过后资金流入预测 ,projectCode：{}, forecastAccountOrder：{} ",
					project.getProjectCode(), forecastAccountOrder);
				forecastServiceClient.add(forecastAccountOrder);
			}
			
			//收取客户保证金
			syncForecastDeposit(detail.getDepositType(), project, detail.getDeposit(), rule,
				ForecastTypeEnum.IN_FRZDBYW);
		} else {
			syncDbForecast(spId, project, rule, ForecastTypeEnum.OUT_FRZDBYW,
				ForecastTypeEnum.IN_FRZDBYW);
		}
	}
	
	/***
	 * 资金预测 - 再担保业务
	 * @param spId
	 * @param project
	 * @param rule
	 */
	private void syncZdbForecast(long spId, ProjectDO project, SysForecastParamAllInfo rule) {
		syncDbForecast(spId, project, rule, ForecastTypeEnum.OUT_ZDBYW, ForecastTypeEnum.IN_ZDBYW);
	}
	
	/***
	 * 资金预测 - 债权融资担保
	 * @param spId
	 * @param project
	 * @param rule
	 */
	private void syncZqrzdbForecast(long spId, ProjectDO project, SysForecastParamAllInfo rule) {
		
		FCouncilSummaryProjectBondDO detail = FCouncilSummaryProjectBondDAO.findBySpId(spId);
		if (detail == null) {
			return;
		}
		//查询渠道信息
		//		ChanalTypeEnum channelType = null;
		//		ChannalInfo channelInfo = null;
		//		if (detail.getCapitalChannelId() > 0) {
		//			channelInfo = channelClient.queryById(detail.getCapitalChannelId());
		//			if (channelInfo != null) {
		//				channelType = ChanalTypeEnum.getByCode(channelInfo.getChannelType());
		//			}
		//		}
		
		//所有资金渠道
		List<ProjectChannelRelationInfo> capitalChannels = projectChannelRelationService
			.queryCapitalChannel(project.getProjectCode());
		Money outMoney = Money.zero(); //存出保证金
		if (ListUtil.isNotEmpty(capitalChannels)) {
			for (ProjectChannelRelationInfo pr : capitalChannels) {
				ChanalTypeEnum channelType = ChanalTypeEnum.getByCode(pr.getChannelType());
				if (channelType == ChanalTypeEnum.YH && pr.getChannelId() > 0) {
					ChannalInfo channelInfo = channelClient.queryById(pr.getChannelId());
					if (channelInfo != null) {
						outMoney.addTo(project.getAmount().multiply(channelInfo.getBondRate())
							.divide(100));
					}
				}
			}
		}
		
		//流出 保证金划出
		//		if (channelInfo != null && channelType == ChanalTypeEnum.YH) {
		//			Money outMoney = project.getAmount().multiply(channelInfo.getBondRate()).divide(100);
		if (outMoney.greaterThan(ZERO_MONEY)) {
			ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
			Calendar calendar = Calendar.getInstance();
			if (rule.getOutZjrzdbTimeType() == TimeUnitEnum.YEAR) {
				calendar.add(Calendar.YEAR, NumberUtil.parseInt(rule.getOutXjrzdbTime()));
			} else if (rule.getOutZjrzdbTimeType() == TimeUnitEnum.MONTH) {
				calendar.add(Calendar.MONTH, NumberUtil.parseInt(rule.getOutXjrzdbTime()));
			} else if (rule.getOutZjrzdbTimeType() == TimeUnitEnum.DAY) {
				calendar.add(Calendar.DAY_OF_MONTH, NumberUtil.parseInt(rule.getOutXjrzdbTime()));
			}
			forecastAccountOrder.setForecastStartTime(calendar.getTime());
			forecastAccountOrder.setAmount(outMoney);
			forecastAccountOrder.setForecastMemo("保证金划出（" + project.getProjectCode() + "）");
			forecastAccountOrder.setForecastType(ForecastTypeEnum.OUT_ZJRZDB);
			forecastAccountOrder.setFundDirection(FundDirectionEnum.OUT);
			forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
											+ ForecastFeeTypeEnum.DEPOSIT_PAY.code());
			forecastAccountOrder.setSystemForm(SystemEnum.PM);
			forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
			forecastAccountOrder.setUsedDeptName(project.getDeptName());
			forecastAccountOrder.setProjectCode(project.getProjectCode());
			forecastAccountOrder.setCustomerId(project.getCustomerId());
			forecastAccountOrder.setCustomerName(project.getCustomerName());
			forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.DEPOSIT_PAY);
			logger.info("债权融资担保上会通过后资金流出预测,projectCode：{}, forecastAccountOrder：{} ",
				project.getProjectCode(), forecastAccountOrder);
			forecastServiceClient.add(forecastAccountOrder);
		}
		//}
		
		//流入 收取担保费
		Money guaranteeMoney = Money.zero();
		if (StringUtil.equals(detail.getGuaranteeFeeType(), ChargeTypeEnum.AMOUNT.code())) {
			guaranteeMoney = Money.amout(String.valueOf(detail.getGuaranteeFee()));
		} else { //根据期限算担保费
			if (StringUtil.equals(project.getTimeUnit(), TimeUnitEnum.YEAR.code())) {
				guaranteeMoney = project.getAmount().multiply(detail.getGuaranteeFee()).divide(100)
					.multiply(project.getTimeLimit());
			} else if (StringUtil.equals(project.getTimeUnit(), TimeUnitEnum.MONTH.code())) {
				guaranteeMoney = project.getAmount().multiply(detail.getGuaranteeFee()).divide(100)
					.multiply(project.getTimeLimit()).divide(12);
			} else if (StringUtil.equals(project.getTimeUnit(), TimeUnitEnum.DAY.code())) {
				guaranteeMoney = project.getAmount().multiply(detail.getGuaranteeFee()).divide(100)
					.multiply(project.getTimeLimit()).divide(365);
			}
		}
		if (guaranteeMoney.greaterThan(ZERO_MONEY)) {
			ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
			Calendar calendar = Calendar.getInstance();
			if (rule.getInZjrzdbTimeType() == TimeUnitEnum.YEAR) {
				calendar.add(Calendar.YEAR, NumberUtil.parseInt(rule.getInZjrzdbtime()));
			} else if (rule.getInZjrzdbTimeType() == TimeUnitEnum.MONTH) {
				calendar.add(Calendar.MONTH, NumberUtil.parseInt(rule.getInZjrzdbtime()));
			} else if (rule.getInZjrzdbTimeType() == TimeUnitEnum.DAY) {
				calendar.add(Calendar.DAY_OF_MONTH, NumberUtil.parseInt(rule.getInZjrzdbtime()));
			}
			forecastAccountOrder.setForecastStartTime(calendar.getTime());
			forecastAccountOrder.setAmount(guaranteeMoney);
			forecastAccountOrder.setForecastMemo("收取担保费（" + project.getProjectCode() + "）");
			forecastAccountOrder.setForecastType(ForecastTypeEnum.IN_ZJRZDB);
			forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
			forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
											+ ForecastFeeTypeEnum.GUARANTEE_FEE.code());
			forecastAccountOrder.setSystemForm(SystemEnum.PM);
			forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
			forecastAccountOrder.setUsedDeptName(project.getDeptName());
			forecastAccountOrder.setProjectCode(project.getProjectCode());
			forecastAccountOrder.setCustomerId(project.getCustomerId());
			forecastAccountOrder.setCustomerName(project.getCustomerName());
			forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.GUARANTEE_FEE);
			logger.info("债权融资担保上会通过后资金流入预测 ,projectCode：{}, forecastAccountOrder：{} ",
				project.getProjectCode(), forecastAccountOrder);
			forecastServiceClient.add(forecastAccountOrder);
		}
		
		//收取客户保证金
		syncForecastDeposit(detail.getDepositType(), project, detail.getDeposit(), rule,
			ForecastTypeEnum.IN_ZJRZDB);
	}
	
	/***
	 * 资金预测 - 委托贷款业务
	 * @param spId
	 * @param project
	 * @param rule
	 */
	private void syncWtdkForecast(long spId, ProjectDO project, SysForecastParamAllInfo rule) {
		
		// 用款事由：为"委贷放款" 
		Money outMoney = project.getAmount();
		if (outMoney.greaterThan(ZERO_MONEY)) {
			ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
			Calendar calendar = Calendar.getInstance();
			if (rule.getOutWtdkywTimeType() == TimeUnitEnum.YEAR) {
				calendar.add(Calendar.YEAR, NumberUtil.parseInt(rule.getOutWtdkywTime()));
			} else if (rule.getOutWtdkywTimeType() == TimeUnitEnum.MONTH) {
				calendar.add(Calendar.MONTH, NumberUtil.parseInt(rule.getOutWtdkywTime()));
			} else if (rule.getOutWtdkywTimeType() == TimeUnitEnum.DAY) {
				calendar.add(Calendar.DAY_OF_MONTH, NumberUtil.parseInt(rule.getOutWtdkywTime()));
			}
			forecastAccountOrder.setForecastStartTime(calendar.getTime());
			forecastAccountOrder.setAmount(outMoney);
			forecastAccountOrder.setForecastMemo("委贷放款（" + project.getProjectCode() + "）");
			forecastAccountOrder.setForecastType(ForecastTypeEnum.OUT_WTDKYW);
			forecastAccountOrder.setFundDirection(FundDirectionEnum.OUT);
			forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
											+ ForecastFeeTypeEnum.WD_LOAN.code());
			forecastAccountOrder.setSystemForm(SystemEnum.PM);
			forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
			forecastAccountOrder.setUsedDeptName(project.getDeptName());
			forecastAccountOrder.setProjectCode(project.getProjectCode());
			forecastAccountOrder.setCustomerId(project.getCustomerId());
			forecastAccountOrder.setCustomerName(project.getCustomerName());
			forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.WD_LOAN);
			logger.info("委托贷款业务上会通过后资金流出预测,projectCode：{}, forecastAccountOrder：{} ",
				project.getProjectCode(), forecastAccountOrder);
			forecastServiceClient.add(forecastAccountOrder);
		}
		// 流入事由：为“收取委贷利息”  委贷项目的还款计划中的时间为委贷本金收回时间(放到还款计划上报)
		
		FCouncilSummaryProjectEntrustedDO detail = FCouncilSummaryProjectEntrustedDAO
			.findBySpId(spId);
		//收取客户保证金
		if (detail != null) {
			syncForecastDeposit(detail.getDepositType(), project, detail.getDeposit(), rule,
				ForecastTypeEnum.IN_WTDKYW);
		}
	}
	
	/***
	 * 资金预测 - 承销业务
	 * @param spId
	 * @param project
	 * @param rule
	 */
	private void syncCxForecast(long spId, ProjectDO project, SysForecastParamAllInfo rule) {
		
		FCouncilSummaryProjectUnderwritingDO detail = FCouncilSummaryProjectUnderwritingDAO
			.findBySpId(spId);
		if (detail == null) {
			return;
		}
		
		// 流入事由为“收取承销费”
		Money underwritingMoney = Money.zero();
		if (StringUtil.equals(detail.getUnderwritingFeeType(), ChargeTypeEnum.AMOUNT.code())) {
			underwritingMoney = Money.amout(String.valueOf(detail.getUnderwritingFee()));
		} else {
			underwritingMoney = project.getAmount().multiply(detail.getUnderwritingFee())
				.divide(100);
		}
		if (underwritingMoney.greaterThan(ZERO_MONEY)) {
			ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
			Calendar calendar = Calendar.getInstance();
			if (rule.getInCxywTimeType() == TimeUnitEnum.YEAR) {
				calendar.add(Calendar.YEAR, NumberUtil.parseInt(rule.getInCxywTime()));
			} else if (rule.getInCxywTimeType() == TimeUnitEnum.MONTH) {
				calendar.add(Calendar.MONTH, NumberUtil.parseInt(rule.getInCxywTime()));
			} else if (rule.getInCxywTimeType() == TimeUnitEnum.DAY) {
				calendar.add(Calendar.DAY_OF_MONTH, NumberUtil.parseInt(rule.getInCxywTime()));
			}
			forecastAccountOrder.setForecastStartTime(calendar.getTime());
			forecastAccountOrder.setAmount(underwritingMoney);
			forecastAccountOrder.setForecastMemo("收取承销费（" + project.getProjectCode() + "）");
			forecastAccountOrder.setForecastType(ForecastTypeEnum.IN_ZJRZDB);
			forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
			forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
											+ ForecastFeeTypeEnum.CXFEE.code());
			forecastAccountOrder.setSystemForm(SystemEnum.PM);
			forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
			forecastAccountOrder.setUsedDeptName(project.getDeptName());
			forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.CXFEE);
			forecastAccountOrder.setProjectCode(project.getProjectCode());
			forecastAccountOrder.setCustomerId(project.getCustomerId());
			forecastAccountOrder.setCustomerName(project.getCustomerName());
			logger.info("承销业务上会通过后资金流入预测 ,projectCode：{}, forecastAccountOrder：{} ",
				project.getProjectCode(), forecastAccountOrder);
			forecastServiceClient.add(forecastAccountOrder);
		}
	}
	
	/***
	 * 资金预测 - 小贷业务
	 * @param spId
	 * @param project
	 * @param rule
	 */
	private void syncXdForecast(long spId, ProjectDO project, SysForecastParamAllInfo rule) {
		syncDbForecast(spId, project, rule, ForecastTypeEnum.OUT_XDYW, ForecastTypeEnum.IN_XDYW);
	}
	
	/***
	 * 资金预测 - 其他
	 * @param spId
	 * @param project
	 * @param rule
	 */
	private void syncOtherForecast(long spId, ProjectDO project, SysForecastParamAllInfo rule) {
		syncDbForecast(spId, project, rule, ForecastTypeEnum.OUT_QTYW, ForecastTypeEnum.IN_QTYW);
	}
	
	/**
	 * 资金预测担保类
	 * @param spId
	 * @param project
	 * @param rule
	 */
	private void syncDbForecast(long spId, ProjectDO project, SysForecastParamAllInfo rule,
								ForecastTypeEnum out, ForecastTypeEnum in) {
		
		FCouncilSummaryProjectGuaranteeDO detail = FCouncilSummaryProjectGuaranteeDAO
			.findBySpId(spId);
		if (detail == null) {
			return;
		}
		
		//查询渠道信息
		//		ChanalTypeEnum channelType = null;
		//		ChannalInfo channelInfo = null;
		//		if (detail.getCapitalChannelId() > 0) {
		//			channelInfo = channelClient.queryById(detail.getCapitalChannelId());
		//			if (channelInfo != null) {
		//				channelType = ChanalTypeEnum.getByCode(channelInfo.getChannelType());
		//			}
		//		}
		
		//流出 保证金划出
		int forcastTime = 0;
		TimeUnitEnum forcastTimeType = null;
		try {
			forcastTime = NumberUtil.parseInt((String) PropertyUtils.getProperty(rule,
				out.getForecastTime()));
			forcastTimeType = (TimeUnitEnum) PropertyUtils.getProperty(rule,
				out.getForecastTimeType());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		//所有资金渠道
		List<ProjectChannelRelationInfo> capitalChannels = projectChannelRelationService
			.queryCapitalChannel(project.getProjectCode());
		Money outMoney = Money.zero(); //存出保证金
		if (ListUtil.isNotEmpty(capitalChannels)) {
			for (ProjectChannelRelationInfo pr : capitalChannels) {
				ChanalTypeEnum channelType = ChanalTypeEnum.getByCode(pr.getChannelType());
				if (channelType == ChanalTypeEnum.YH && pr.getChannelId() > 0) {
					ChannalInfo channelInfo = channelClient.queryById(pr.getChannelId());
					if (channelInfo != null) {
						outMoney.addTo(project.getAmount().multiply(channelInfo.getBondRate())
							.divide(100));
					}
				}
			}
		}
		
		if (outMoney.greaterThan(ZERO_MONEY)) {
			ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
			Calendar calendar = Calendar.getInstance();
			if (forcastTimeType == TimeUnitEnum.YEAR) {
				calendar.add(Calendar.YEAR, forcastTime);
			} else if (forcastTimeType == TimeUnitEnum.MONTH) {
				calendar.add(Calendar.MONTH, forcastTime);
			} else if (forcastTimeType == TimeUnitEnum.DAY) {
				calendar.add(Calendar.DAY_OF_MONTH, forcastTime);
			}
			forecastAccountOrder.setForecastStartTime(calendar.getTime());
			forecastAccountOrder.setAmount(outMoney);
			forecastAccountOrder.setForecastMemo("保证金划出（" + project.getProjectCode() + "）");
			forecastAccountOrder.setForecastType(out);
			forecastAccountOrder.setFundDirection(FundDirectionEnum.OUT);
			forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
											+ ForecastFeeTypeEnum.DEPOSIT_PAY.code());
			forecastAccountOrder.setSystemForm(SystemEnum.PM);
			forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
			forecastAccountOrder.setUsedDeptName(project.getDeptName());
			forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.DEPOSIT_PAY);
			forecastAccountOrder.setProjectCode(project.getProjectCode());
			forecastAccountOrder.setCustomerId(project.getCustomerId());
			forecastAccountOrder.setCustomerName(project.getCustomerName());
			logger.info("{}上会通过后资金流出预测,projectCode：{}, forecastAccountOrder：{} ", out.message(),
				project.getProjectCode(), forecastAccountOrder);
			forecastServiceClient.add(forecastAccountOrder);
		}
		
		//流入
		try {
			forcastTime = NumberUtil.parseInt((String) PropertyUtils.getProperty(rule,
				in.getForecastTime()));
			forcastTimeType = (TimeUnitEnum) PropertyUtils.getProperty(rule,
				in.getForecastTimeType());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		// 收取担保费
		Money guaranteeMoney = Money.zero();
		if (StringUtil.equals(detail.getGuaranteeFeeType(), ChargeTypeEnum.AMOUNT.code())) {
			guaranteeMoney = Money.amout(String.valueOf(detail.getGuaranteeFee()));
		} else { //根据期限算担保费
			if (StringUtil.equals(project.getTimeUnit(), TimeUnitEnum.YEAR.code())) {
				guaranteeMoney = project.getAmount().multiply(detail.getGuaranteeFee()).divide(100)
					.multiply(project.getTimeLimit());
			} else if (StringUtil.equals(project.getTimeUnit(), TimeUnitEnum.MONTH.code())) {
				guaranteeMoney = project.getAmount().multiply(detail.getGuaranteeFee()).divide(100)
					.multiply(project.getTimeLimit()).divide(12);
			} else if (StringUtil.equals(project.getTimeUnit(), TimeUnitEnum.DAY.code())) {
				guaranteeMoney = project.getAmount().multiply(detail.getGuaranteeFee()).divide(100)
					.multiply(project.getTimeLimit()).divide(365);
			}
		}
		if (guaranteeMoney.greaterThan(ZERO_MONEY)) {
			ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
			Calendar calendar = Calendar.getInstance();
			if (forcastTimeType == TimeUnitEnum.YEAR) {
				calendar.add(Calendar.YEAR, forcastTime);
			} else if (forcastTimeType == TimeUnitEnum.MONTH) {
				calendar.add(Calendar.MONTH, forcastTime);
			} else if (forcastTimeType == TimeUnitEnum.DAY) {
				calendar.add(Calendar.DAY_OF_MONTH, forcastTime);
			}
			forecastAccountOrder.setForecastStartTime(calendar.getTime());
			forecastAccountOrder.setAmount(guaranteeMoney);
			forecastAccountOrder.setForecastMemo("收取担保费（" + project.getProjectCode() + "）");
			forecastAccountOrder.setForecastType(in);
			forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
			forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
											+ ForecastFeeTypeEnum.GUARANTEE_FEE.code());
			forecastAccountOrder.setSystemForm(SystemEnum.PM);
			forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
			forecastAccountOrder.setUsedDeptName(project.getDeptName());
			forecastAccountOrder.setProjectCode(project.getProjectCode());
			forecastAccountOrder.setCustomerId(project.getCustomerId());
			forecastAccountOrder.setCustomerName(project.getCustomerName());
			forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.GUARANTEE_FEE);
			logger.info("{}上会通过后资金流入预测,projectCode：{}, forecastAccountOrder：{} ", in.message(),
				project.getProjectCode(), forecastAccountOrder);
			forecastServiceClient.add(forecastAccountOrder);
		}
		
		//收取客户保证金
		syncForecastDeposit(detail.getDepositType(), project, detail.getDeposit(), rule, in);
	}
	
	/**
	 * 收取客户保证金
	 * @param depositType
	 * @param amount
	 * @param depositFee
	 * @param rule
	 * @param in
	 */
	private void syncForecastDeposit(String depositType, ProjectDO project, double depositFee,
										SysForecastParamAllInfo rule, ForecastTypeEnum in) {
		// 收取客户保证金
		Money deposit = Money.zero();
		if (StringUtil.equals(depositType, ChargeTypeEnum.AMOUNT.code())) {
			deposit = Money.amout(String.valueOf(depositFee));
		} else { //根据期限算担保费
			deposit = project.getAmount().multiply(depositFee).divide(100);
		}
		
		int forcastTime = 0;
		TimeUnitEnum forcastTimeType = null;
		try {
			forcastTime = NumberUtil.parseInt((String) PropertyUtils.getProperty(rule,
				in.getForecastTime()));
			forcastTimeType = (TimeUnitEnum) PropertyUtils.getProperty(rule,
				in.getForecastTimeType());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		if (deposit.greaterThan(ZERO_MONEY)) {
			
			ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
			Calendar calendar = Calendar.getInstance();
			if (forcastTimeType == TimeUnitEnum.YEAR) {
				calendar.add(Calendar.YEAR, forcastTime);
			} else if (forcastTimeType == TimeUnitEnum.MONTH) {
				calendar.add(Calendar.MONTH, forcastTime);
			} else if (forcastTimeType == TimeUnitEnum.DAY) {
				calendar.add(Calendar.DAY_OF_MONTH, forcastTime);
			}
			forecastAccountOrder.setForecastStartTime(calendar.getTime());
			forecastAccountOrder.setAmount(deposit);
			forecastAccountOrder.setForecastMemo("收取客户保证金（" + project.getProjectCode() + "）");
			forecastAccountOrder.setForecastType(in);
			forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
			forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
											+ ForecastFeeTypeEnum.DEPOSIT_CHARGE.code());
			forecastAccountOrder.setSystemForm(SystemEnum.PM);
			forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
			forecastAccountOrder.setUsedDeptName(project.getDeptName());
			forecastAccountOrder.setProjectCode(project.getProjectCode());
			forecastAccountOrder.setCustomerId(project.getCustomerId());
			forecastAccountOrder.setCustomerName(project.getCustomerName());
			forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.DEPOSIT_CHARGE);
			logger.info("{}上会通过后收取客户保证金资金流入预测 , projectCode：{}, forecastAccountOrder：{} ",
				in.message(), project.getProjectCode(), forecastAccountOrder);
			forecastServiceClient.add(forecastAccountOrder);
		}
	}
	
	/**
	 * 同步授信条件
	 * @param spId
	 * @param project
	 */
	public void syncCreditCondition(long spId, ProjectDO project) {
		
		Date now = getSysdate();
		
		//旧数据
		List<ProjectCreditConditionDO> oldList = projectCreditConditionDAO
			.findByProjectCode(project.getProjectCode());
		
		Map<Long, ProjectCreditConditionDO> pledgeMap = new HashMap<>();
		Map<Long, ProjectCreditConditionDO> mortgagesMap = new HashMap<>();
		Map<Long, ProjectCreditConditionDO> guarantorMap = new HashMap<>();
		Map<Long, ProjectCreditConditionDO> textMap = new HashMap<>();
		if (ListUtil.isNotEmpty(oldList)) {
			for (ProjectCreditConditionDO condition : oldList) {
				if (CreditConditionTypeEnum.PLEDGE.code().equals(condition.getType())) {
					pledgeMap.put(condition.getAssetId(), condition);
				}
				if (CreditConditionTypeEnum.MORTGAGE.code().equals(condition.getType())) {
					mortgagesMap.put(condition.getAssetId(), condition);
				}
				if (CreditConditionTypeEnum.GUARANTOR.code().equals(condition.getType())) {
					guarantorMap.put(condition.getItemId(), condition);
				}
				if (CreditConditionTypeEnum.TEXT.code().equals(condition.getType())) {
					textMap.put(condition.getItemId(), condition);
				}
			}
		}
		
		FCouncilSummaryProjectCreditConditionInfo ccInfo = councilSummaryService
			.queryCreditConditionBySpId(spId, false);
		
		//同步关联信息到资产
		List<AssetStatusOrder> listOrder = Lists.newArrayList();
		
		if (ccInfo.getPledges() != null) {
			for (FCouncilSummaryProjectPledgeAssetInfo pledge : ccInfo.getPledges()) {
				
				StringBuffer sbf = new StringBuffer();
				
				if (StringUtil.isNotEmpty(pledge.getAssetRemark())) {
					sbf.append("关键信息：").append(pledge.getAssetRemark() + "；");
				}
				
				sbf.append("资产类型：").append(pledge.getAssetType()).append("； 权利人：")
					.append(pledge.getOwnershipName()).append("； 抵押顺位：")
					.append(StringUtil.equals(pledge.getSynPosition(), "FIRST") ? "第一顺位" : "第二顺位")
					.append("； 是否后置抵押：")
					.append(StringUtil.equals(pledge.getPostposition(), "YES") ? "是" : "否")
					.append("； 已抵债金额：").append(MoneyUtil.formatWithUnit(pledge.getDebtedAmount()));
				
				ProjectCreditConditionDO exists = pledgeMap.get(pledge.getAssetsId());
				if (exists == null) {
					ProjectCreditConditionDO ccd = new ProjectCreditConditionDO();
					ccd.setAssetId(pledge.getAssetsId());
					ccd.setProjectCode(project.getProjectCode());
					ccd.setIsConfirm(BooleanEnum.NO.code());
					ccd.setItemId(pledge.getId());
					ccd.setType(CreditConditionTypeEnum.PLEDGE.code());
					ccd.setStatus("NOT_APPLY");
					ccd.setReleaseStatus("WAITING");
					ccd.setItemDesc(sbf.toString());
					ccd.setRemark(pledge.getAssetRemark());
					ccd.setRawAddTime(now);
					logger.info("项目授信授信条件：{}", ccd);
					projectCreditConditionDAO.insert(ccd);
				} else {
					exists.setAssetId(pledge.getAssetsId());
					exists.setItemDesc(sbf.toString());
					exists.setRemark(pledge.getAssetRemark());
					logger.info("项目授信授信条件：{}", exists);
					projectCreditConditionDAO.update(exists);
					
					pledgeMap.remove(exists.getAssetId());
				}
				
				//构建资产项目关联的Order
				AssetStatusOrder as = new AssetStatusOrder();
				as.setAssetId(pledge.getAssetsId());
				as.setStatus(AssetStatusEnum.QUASI_PLEDGE);
				listOrder.add(as);
			}
		}
		
		if (ccInfo.getMortgages() != null) {
			for (FCouncilSummaryProjectPledgeAssetInfo mortgage : ccInfo.getMortgages()) {
				
				StringBuffer sbf = new StringBuffer();
				if (StringUtil.isNotEmpty(mortgage.getAssetRemark())) {
					sbf.append("关键信息：").append(mortgage.getAssetRemark() + "；");
				}
				
				sbf.append("资产类型：")
					.append(mortgage.getAssetType())
					.append("； 权利人：")
					.append(mortgage.getOwnershipName())
					.append("； 质押顺位：")
					.append(StringUtil.equals(mortgage.getSynPosition(), "FIRST") ? "第一顺位" : "第二顺位")
					.append("； 是否后置抵押：")
					.append(StringUtil.equals(mortgage.getPostposition(), "YES") ? "是" : "否")
					.append("； 已抵债金额：")
					.append(MoneyUtil.formatWithUnit(mortgage.getDebtedAmount()));
				
				ProjectCreditConditionDO exists = mortgagesMap.get(mortgage.getAssetsId());
				if (exists == null) {
					ProjectCreditConditionDO ccd = new ProjectCreditConditionDO();
					ccd.setAssetId(mortgage.getAssetsId());
					ccd.setProjectCode(project.getProjectCode());
					ccd.setIsConfirm(BooleanEnum.NO.code());
					ccd.setItemId(mortgage.getId());
					ccd.setType(CreditConditionTypeEnum.MORTGAGE.code());
					ccd.setStatus("NOT_APPLY");
					ccd.setReleaseStatus("WAITING");
					ccd.setItemDesc(sbf.toString());
					ccd.setRemark(mortgage.getAssetRemark());
					ccd.setRawAddTime(now);
					logger.info("项目授信授信条件：{}", ccd);
					projectCreditConditionDAO.insert(ccd);
				} else {
					exists.setAssetId(mortgage.getAssetsId());
					exists.setItemDesc(sbf.toString());
					exists.setRemark(mortgage.getAssetRemark());
					logger.info("项目授信授信条件：{}", exists);
					projectCreditConditionDAO.update(exists);
					mortgagesMap.remove(exists.getAssetId());
				}
				
				//构建资产项目关联的Order
				AssetStatusOrder as = new AssetStatusOrder();
				as.setAssetId(mortgage.getAssetsId());
				as.setStatus(AssetStatusEnum.QUASI_MORTGAGE);
				listOrder.add(as);
			}
		}
		
		if (ccInfo.getGuarantors() != null) {
			for (FCouncilSummaryProjectGuarantorInfo guarantor : ccInfo.getGuarantors()) {
				StringBuffer sbf = new StringBuffer();
				sbf.append("保证人：")
					.append(guarantor.getGuarantor())
					.append("；保证类型：")
					.append(
						guarantor.getGuarantorType() == null ? "无" : guarantor.getGuarantorType()
							.message()).append("； 保证额度：")
					.append(guarantor.getGuaranteeAmount().toStandardString()).append("元");
				
				ProjectCreditConditionDO exists = guarantorMap.get(guarantor.getId());
				if (exists == null) {
					ProjectCreditConditionDO ccd = new ProjectCreditConditionDO();
					ccd.setProjectCode(project.getProjectCode());
					ccd.setIsConfirm(BooleanEnum.NO.code());
					ccd.setItemId(guarantor.getId());
					ccd.setType(CreditConditionTypeEnum.GUARANTOR.code());
					ccd.setStatus("NOT_APPLY");
					ccd.setReleaseStatus("WAITING");
					ccd.setItemDesc(sbf.toString());
					ccd.setRawAddTime(now);
					logger.info("项目授信授信条件：{}", ccd);
					projectCreditConditionDAO.insert(ccd);
				} else {
					exists.setItemDesc(sbf.toString());
					logger.info("项目授信授信条件：{}", exists);
					projectCreditConditionDAO.update(exists);
					guarantorMap.remove(exists.getItemId());
				}
			}
			
		}
		
		if (ccInfo.getTextCreditCondition() != null) {
			for (FCouncilSummaryProjectTextCreditConditionInfo textInfo : ccInfo
				.getTextCreditCondition()) {
				ProjectCreditConditionDO exists = textMap.get(textInfo.getId());
				if (exists == null) {
					ProjectCreditConditionDO ccd = new ProjectCreditConditionDO();
					ccd.setProjectCode(project.getProjectCode());
					ccd.setIsConfirm(BooleanEnum.NO.code());
					ccd.setItemId(textInfo.getId());
					ccd.setType(CreditConditionTypeEnum.TEXT.code());
					ccd.setStatus("NOT_APPLY");
					ccd.setReleaseStatus("WAITING");
					ccd.setItemDesc(textInfo.getContent());
					ccd.setRawAddTime(now);
					logger.info("项目授信授信条件：{}", ccd);
					projectCreditConditionDAO.insert(ccd);
				} else {
					exists.setItemDesc(textInfo.getContent());
					logger.info("项目授信授信条件：{}", exists);
					projectCreditConditionDAO.update(exists);
					textMap.remove(exists.getItemId());
				}
			}
			
		}
		
		Map<String, FProjectContractItemDO> contracts = Maps.newHashMap();
		for (long key : pledgeMap.keySet()) {
			ProjectCreditConditionDO condition = pledgeMap.get(key);
			if (condition != null) {
				projectCreditConditionDAO.deleteById(condition.getId());
				//取消合同关联关系
				FProjectContractItemDO contract = fProjectContractItemDAO
					.findByContractCode(condition.getContractNo());
				if (contract != null) {
					contract.setCreditMeasure(resetCreditMeasure(contract.getCreditMeasure(),
						condition.getId()));
					contracts.put(contract.getContractCode(), contract);
				}
			}
		}
		for (long key : mortgagesMap.keySet()) {
			ProjectCreditConditionDO condition = mortgagesMap.get(key);
			if (condition != null) {
				projectCreditConditionDAO.deleteById(condition.getId());
				//取消合同关联关系
				FProjectContractItemDO contract = fProjectContractItemDAO
					.findByContractCode(condition.getContractNo());
				if (contract != null) {
					contract.setCreditMeasure(resetCreditMeasure(contract.getCreditMeasure(),
						condition.getId()));
					contracts.put(contract.getContractCode(), contract);
				}
			}
		}
		for (long key : guarantorMap.keySet()) {
			ProjectCreditConditionDO condition = guarantorMap.get(key);
			if (condition != null) {
				projectCreditConditionDAO.deleteById(condition.getId());
				//取消合同关联关系
				FProjectContractItemDO contract = fProjectContractItemDAO
					.findByContractCode(condition.getContractNo());
				if (contract != null) {
					contract.setCreditMeasure(resetCreditMeasure(contract.getCreditMeasure(),
						condition.getId()));
					contracts.put(contract.getContractCode(), contract);
				}
			}
		}
		
		for (long key : textMap.keySet()) {
			ProjectCreditConditionDO condition = textMap.get(key);
			if (condition != null) {
				projectCreditConditionDAO.deleteById(condition.getId());
				//取消合同关联关系
				FProjectContractItemDO contract = fProjectContractItemDAO
					.findByContractCode(condition.getContractNo());
				if (contract != null) {
					contract.setCreditMeasure(resetCreditMeasure(contract.getCreditMeasure(),
						condition.getId()));
					contracts.put(contract.getContractCode(), contract);
				}
			}
		}
		
		if (!contracts.isEmpty()) {
			for (String contractCode : contracts.keySet()) {
				FProjectContractItemDO contract = contracts.get(contractCode);
				if (contract != null)
					fProjectContractItemDAO.update(contract);
			}
		}
		try {
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
	
	/**
	 * 重新设置授信措施
	 * @param creditMeasure
	 * @param itemId
	 * @return
	 */
	private String resetCreditMeasure(String creditMeasure, long itemId) {
		if (creditMeasure == null)
			return null;
		String[] credits = creditMeasure.split(",");
		String newCreditMeasure = null;
		if (credits != null) {
			for (String item : credits) {
				if (StringUtil.isBlank(item))
					continue;
				if (!item.equals(itemId)) {
					if (newCreditMeasure == null) {
						newCreditMeasure = String.valueOf(itemId);
					} else {
						newCreditMeasure += "," + itemId;
					}
				}
			}
		}
		return newCreditMeasure;
	}
}
