package com.born.fcs.pm.biz.service.fund;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.service.transfer.AssetsTransferApplicationService;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountChangeOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmOrder;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.fm.ws.service.incomeconfirm.IncomeConfirmService;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.job.AsynchronousTaskJob;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.biz.service.common.AsynchronousService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.dal.dataobject.ChargeNoticeCapitalApproproationDO;
import com.born.fcs.pm.dal.dataobject.FBrokerBusinessDO;
import com.born.fcs.pm.dal.dataobject.FChargeNotificationDO;
import com.born.fcs.pm.dal.dataobject.FChargeNotificationFeeDO;
import com.born.fcs.pm.dal.dataobject.FFinanceAffirmDO;
import com.born.fcs.pm.dal.dataobject.FFinanceAffirmDetailDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.ChargeBasisEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FinanceAffirmTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.financeaffirm.ProjectChargePayInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryListInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationAmountOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ChargeCapitalOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ChargeNoticeCapitalApproproationOrder;
import com.born.fcs.pm.ws.order.financeaffirm.FFinanceAffirmDetailOrder;
import com.born.fcs.pm.ws.order.financeaffirm.FFinanceAffirmOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ProjectChargePayQueryOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.financeaffirm.FinanceAffirmService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService;
import com.born.fcs.pm.ws.service.releaseproject.ReleaseProjectService;
import com.google.common.collect.Maps;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("chargeNotificationProcessService")
public class ChargeNotificationProcessServiceImpl extends BaseProcessService implements
																			AsynchronousService {
	
	@Autowired
	protected ReleaseProjectService releaseProjectService;
	
	@Autowired
	ReceiptPaymentFormService receiptPaymentFormServiceClient;
	@Autowired
	AssetsTransferApplicationService assetsTransferApplicationWebService;
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	@Autowired
	IncomeConfirmService incomeConfirmServiceClient;
	@Autowired
	FinanceAffirmService financeAffirmService;
	@Autowired
	ChargeNotificationService chargeNotificationService;
	@Autowired
	private SysParameterService sysParameterServiceClient;
	
	@Autowired
	CommonAttachmentService commonAttachmentService;
	@Autowired
	ProjectChannelRelationService projectChannelRelationService;
	@Autowired
	ProjectRecoveryService projectRecoveryService;
	@Autowired
	ForecastService forecastServiceClient;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		return new ArrayList<>(0);
	}
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO.findByFormId(formInfo
			.getFormId());
		vars.put("收费项目编号", fChargeNotificationDO.getProjectCode());
		vars.put("收费客户名称", fChargeNotificationDO.getCustomerName());
		return vars;
	}
	
	public FcsBaseResult startBeforeProcess(final WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO.findByFormId(order
				.getFormInfo().getFormId());
			//校验金额的合法性
			checkAmount(fChargeNotificationDO);
			
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(StringUtil.defaultIfBlank(fChargeNotificationDO.getCustomerName(),fChargeNotificationDO.getProjectName()) + "-收费通知单");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("收费通知单流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 启动流程后业务处理(BASE)
		FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO.findByFormId(formInfo
			.getFormId());
		
		fChargeNotificationDO.setStatus(FormStatusEnum.SUBMIT.code());
		fChargeNotificationDAO.update(fChargeNotificationDO);
	}
	
	@Override
	public void doNextAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 审核后业务处理(BASE)
		FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO.findByFormId(formInfo
			.getFormId());
		fChargeNotificationDO.setStatus(FormStatusEnum.AUDITING.code());
		fChargeNotificationDAO.update(fChargeNotificationDO);
		//		String projectCode = fChargeNotificationDO.getProjectCode();
		//		if (projectCode != null && !projectCode.startsWith("JJ")) {
		//			//委贷类项目 如果有 委贷本金收回 收费通知,则添加到可解保的列表中
		//			ProjectDO project = projectDAO
		//				.findByProjectCode(fChargeNotificationDO.getProjectCode());
		//			if (ProjectUtil.isEntrusted(project.getBusiType())) {
		//
		//			}
		//		}
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(final WorkFlowBeforeProcessOrder order) {
		return commonProcess(order, "收费通知审核", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO
					.findByFormId(order.getFormInfo().getFormId());
				Map<String, Object> customizeMap = order.getCustomizeMap();
				String isFinanceAffirm = (String) customizeMap.get("isFinanceAffirm");
				if ("IS".equals(isFinanceAffirm)) {
					saveAffirm(customizeMap, fChargeNotificationDO);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		// 结束流程后业务处理(BASE)
		FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO.findByFormId(formInfo
			.getFormId());
		//更新资产转让，更新为已收费
		if (fChargeNotificationDO.getProjectCode() != null) {
			List<FAssetsTransferApplicationInfo> listTransferInfo = assetsTransferApplicationWebService
				.findByProjectCode(fChargeNotificationDO.getProjectCode());
			if (listTransferInfo != null && listTransferInfo.size() > 0) {
				for (FAssetsTransferApplicationInfo fAssetsTransferApplicationInfo : listTransferInfo) {
					assetsTransferApplicationWebService
						.updateIsCharge(fAssetsTransferApplicationInfo.getId());
				}
			}
		}
		List<FChargeNotificationFeeDO> feeDOs = fChargeNotificationFeeDAO
			.findByNotificationId(fChargeNotificationDO.getNotificationId());
		Map<Long, FChargeNotificationFeeDO> map = new HashMap<Long, FChargeNotificationFeeDO>();
		for (FChargeNotificationFeeDO feeDO : feeDOs) {
			map.put(feeDO.getId(), feeDO);
		}
		FFinanceAffirmDO affirmDO = fFinanceAffirmDAO.findByFormId(fChargeNotificationDO
			.getFormId());
		List<FFinanceAffirmDetailDO> detailDOs = fFinanceAffirmDetailDAO.findByAffirmId(affirmDO
			.getAffirmId());
		try {
			
			fChargeNotificationDO.setStatus(FormStatusEnum.APPROVAL.code());
			final Date now = FcsPmDomainHolder.get().getSysDate();
			fChargeNotificationDO.setChargeTime(now);
			fChargeNotificationDAO.update(fChargeNotificationDO);
			
			//收费依据是项目 更新project表里的值
			if (StringUtil.equals(fChargeNotificationDO.getChargeBasis(),
				ChargeBasisEnum.PROJECT.code())) {
				ProjectInfo projectInfo = projectService.queryByCode(
					fChargeNotificationDO.getProjectCode(), false);
				ProjectDO projectDO = projectDAO.findByProjectCode(fChargeNotificationDO
					.getProjectCode());
				Money deposit = new Money(0, 0);//客户保证金
				Money back = new Money(0, 0);
				Money withdrawal = new Money(0, 0);
				Money chargedAmount = projectDO.getChargedAmount();
				for (FFinanceAffirmDetailDO feeDO : detailDOs) {
					if (feeDO.getFeeType().equals(FeeTypeEnum.GUARANTEE_DEPOSIT.code())) {//存入保证金
						deposit = deposit.add(feeDO.getPayAmount());
					}
					if (feeDO.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK.code())) {//存出保证金划回
						back = back.add(feeDO.getPayAmount());
						//更新f_finance_affirm_detail、charge_notice_capital_approproation表剩余金额
						FChargeNotificationFeeDO chargeNotificationFeeDO = map.get(feeDO
							.getDetailId());
						String affirmDetailIds = "";
						if (chargeNotificationFeeDO != null) {
							affirmDetailIds = chargeNotificationFeeDO.getAffirmDetailIds();
						}
						if (StringUtil.isNotBlank(affirmDetailIds)) {
							String firms[] = affirmDetailIds.split(";");
							List<Long> ids = Lists.newArrayList();
							for (String firm : firms) {
								if (StringUtil.isNotBlank(firm)) {
									Long id = Long.parseLong(firm.split(",")[1]);
									Money amount = new Money(firm.split(",")[0]);
									FFinanceAffirmDetailDO detailDO = fFinanceAffirmDetailDAO
										.findById(id);
									//更新charge_notice_capital_approproation表剩余金额表
									ChargeNoticeCapitalApproproationDO appDO = chargeNoticeCapitalApproproationDAO
										.findByTypeAndDetailIdAndPayId(
											FinanceAffirmTypeEnum.CHARGE_NOTIFICATION.code(),
											feeDO.getId(), id + "");
									appDO.setLeftAmount(detailDO.getReturnCustomerAmount()
										.subtract(amount));
									appDO.setIsApproval("IS");
									chargeNoticeCapitalApproproationDAO.update(appDO);
									//更新更新f_finance_affirm_detail表剩余金额表
									detailDO.setReturnCustomerAmount(detailDO
										.getReturnCustomerAmount().subtract(amount));
									fFinanceAffirmDetailDAO.update(detailDO);
									
								}
							}
						}
						//
					}
					if (feeDO.getFeeType().equals(
						FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL.code())) {//委贷本金回收
						withdrawal = withdrawal.add(feeDO.getPayAmount());
					}
					chargedAmount = chargedAmount.add(feeDO.getPayAmount());//更新project表里的已收费金额
				}
				projectDO.setCustomerDepositAmount(projectDO.getCustomerDepositAmount().addTo(
					deposit));
				projectDO.setSelfDepositAmount(projectDO.getSelfDepositAmount().subtract(back));
				projectDO.setRepayedAmount(projectDO.getRepayedAmount().addTo(withdrawal));
				projectDO.setChargedAmount(chargedAmount);
				if (ProjectUtil.isEntrusted(projectDO.getBusiType())
					&& withdrawal.greaterThan(ZERO_MONEY)) { //委贷项目 - 委贷本金回收
					projectDO.setReleasableAmount(projectDO.getReleasableAmount().add(withdrawal)); //添加到可解保金额
					logger.info("委贷本金收回，可解保金额：" + projectDO.getReleasableAmount() + " - "
								+ projectDO.getProjectCode());
					releaseProjectService.addEntrusted(projectDO.getProjectCode());
				}
				
				if (withdrawal.greaterThan(ZERO_MONEY)) {
					//XXX 更新渠道相关金额(目前没有区分资金渠道收回)
					ProjectChannelRelationAmountOrder channelAmountOrder = new ProjectChannelRelationAmountOrder();
					channelAmountOrder.setProjectCode(projectDO.getProjectCode());
					channelAmountOrder.setReleasableAmount(withdrawal);
					channelAmountOrder.setRepayedAmount(withdrawal);
					projectChannelRelationService.updateRelatedAmount(channelAmountOrder);
				}
				//客户经理通过收款通知单收回代偿相关费用，无代偿费用后，并且通过审核，项目状态变为正常，项目阶段（有在保余额还是保后阶段，无在保余额项目结束(有进行中的项目跟踪表的不管)）
				Money totalCompenPayAmount = Money.zero();
				Money totalCompenChargeAmount = Money.zero();
				//资金划付财务确认划付代偿金额
				ProjectChargePayQueryOrder order = new ProjectChargePayQueryOrder();
				order.setProjectCode(projectDO.getProjectCode());
				order.setAffirmFormType("CAPITAL_APPROPROATION_APPLY");
				List<String> feeTypeList = Lists.newArrayList();
				feeTypeList.add("COMPENSATORY_PRINCIPAL");
				feeTypeList.add("COMPENSATORY_INTEREST");
				feeTypeList.add("COMPENSATORY_PENALTY");
				feeTypeList.add("COMPENSATORY_LIQUIDATED_DAMAGES");
				feeTypeList.add("COMPENSATORY_OTHER");
				order.setFeeTypeList(feeTypeList);
				order.setPageSize(99999L);
				QueryBaseBatchResult<ProjectChargePayInfo> batchResult = financeAffirmService
					.queryProjectChargePayDetail(order);
				if (ListUtil.isNotEmpty(batchResult.getPageList())) {
					for (ProjectChargePayInfo payInfo : batchResult.getPageList()) {
						totalCompenPayAmount.addTo(payInfo.getPayAmount());
					}
				}
				//收费财务确认代偿金额
				order.setAffirmFormType("CHARGE_NOTIFICATION");
				feeTypeList = Lists.newArrayList();
				feeTypeList.add("COMPENSATORY_PRINCIPAL_WITHDRAWAL");
				feeTypeList.add("COMPENSATORY_INTEREST_WITHDRAWAL");
				feeTypeList.add("COMPENSATORY_DEDIT_WITHDRAWAL");
				feeTypeList.add("COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL");
				feeTypeList.add("COMPENSATORY_OTHER_WITHDRAWAL");
				order.setFeeTypeList(feeTypeList);
				batchResult = financeAffirmService.queryProjectChargePayDetail(order);
				if (ListUtil.isNotEmpty(batchResult.getPageList())) {
					for (ProjectChargePayInfo payInfo : batchResult.getPageList()) {
						totalCompenChargeAmount.addTo(payInfo.getPayAmount());
					}
				}
				if (totalCompenChargeAmount.greaterThan(totalCompenPayAmount)
					|| totalCompenChargeAmount.equals(totalCompenPayAmount)) {
					//项目状态变为正常，项目阶段（有在保余额还是保后阶段，无在保余额项目结束(有进行中的项目追偿跟踪表的不管)
					//查项目追偿列表
					ProjectRecoveryQueryOrder projectRecoveryQueryOrder = new ProjectRecoveryQueryOrder();
					projectRecoveryQueryOrder.setPageSize(9999L);
					projectRecoveryQueryOrder.setProjectCode(projectDO.getProjectCode());
					QueryBaseBatchResult<ProjectRecoveryListInfo> recoveryListInfo = projectRecoveryService
						.queryRecovery(projectRecoveryQueryOrder);
					boolean recoverying = false;
					if (ListUtil.isNotEmpty(recoveryListInfo.getPageList())) {
						for (ProjectRecoveryListInfo info : recoveryListInfo.getPageList()) {
							if (info.getRecoveryStatus() == ProjectRecoveryStatusEnum.RECOVERYING
								|| info.getRecoveryStatus() == ProjectRecoveryStatusEnum.CLOSEING
								|| info.getRecoveryStatus() == ProjectRecoveryStatusEnum.DRAFT) {
								recoverying = true;
								break;
							}
						}
					}
					//首先要确保之前是发生了在保额才能改项目状态
					boolean isEntrusted = ProjectUtil.isEntrusted(projectInfo.getBusiType());
					if ((isEntrusted && projectInfo.getLoanedAmount().greaterThan(Money.zero()) //委贷的 放款金额>0并且在保余额为0
					|| (!isEntrusted && projectInfo.getReleasableAmount().greaterThan(Money.zero()))) //其他的 可解保金额>0并且在保余额为0
						&& !projectInfo.getBalance().greaterThan(Money.zero()) && !recoverying) {
						projectDO.setPhases(ProjectPhasesEnum.FINISH_PHASES.code());
						projectDO.setStatus(ProjectStatusEnum.NORMAL.code());
					} else if (projectInfo.getBalance().greaterThan(Money.zero()) && !recoverying) {
						projectDO.setPhases(ProjectPhasesEnum.AFTERWARDS_PHASES.code());
						projectDO.setStatus(ProjectStatusEnum.NORMAL.code());
					}
				}
				projectDAO.update(projectDO);
				//客户保证金变化
				if (deposit.greaterThan(ZERO_MONEY)) {
					projectService.syncForecastDeposit(projectDO.getProjectCode());
				}
				
			} else {//经纪业务收费
				FBrokerBusinessDO applyDO = FBrokerBusinessDAO
					.findByProjectCode(fChargeNotificationDO.getProjectCode());
				Money chargeAmount = applyDO.getChargedAmount();
				for (FFinanceAffirmDetailDO feeDO : detailDOs) {
					chargeAmount = chargeAmount.add(feeDO.getPayAmount());
				}
				applyDO.setChargedAmount(chargeAmount);
				FBrokerBusinessDAO.update(applyDO);
			}
			sendMessage(fChargeNotificationDO);
		} catch (Exception e) {
			logger.error("收费流程通过处理出错：{}", e);
		}
		try {
			//将收费信息同步到收入确认表中
			if (fChargeNotificationDO.getProjectCode() != null) {
				ProjectDO projectDO1 = projectDAO.findByProjectCode(fChargeNotificationDO
					.getProjectCode());
				IncomeConfirmOrder order = new IncomeConfirmOrder();
				order.setProjectCode(fChargeNotificationDO.getProjectCode());
				order.setCustomerId(projectDO1.getCustomerId());
				order.setCustomerName(projectDO1.getCustomerName());
				order.setBusiType(projectDO1.getBusiType());
				order.setBusiTypeName(projectDO1.getBusiTypeName());
				order.setProjectName(projectDO1.getProjectName());
				order.setChargedAmount(projectDO1.getChargedAmount());
				order.setRelatedProjectCode(fChargeNotificationDO.getProjectCode());
				order.setNotConfirmedIncomeAmount(projectDO1.getChargedAmount());
				order.setCheckStatus(0);
				order.setCheckIndex(0);
				incomeConfirmServiceClient.save(order);
			}
		} catch (Exception e) {
			logger.error("收费信息同步到收入确认表出错：{}", e);
		}
		//异步同步单据到资金系统
		logger.info("添加收费数据到资金系统异步任务");
		asynchronousTaskJob.addAsynchronousService(this, new Object[] { formInfo,
																		fChargeNotificationDO,
																		feeDOs });
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 手动结束流程后业务处理(BASE)
	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO.findByFormId(formInfo
			.getFormId());
		if (StringUtil.equals(fChargeNotificationDO.getChargeBasis(),
			ChargeBasisEnum.PROJECT.code())) {
			List<FChargeNotificationFeeDO> feeDOs = fChargeNotificationFeeDAO
				.findByNotificationId(fChargeNotificationDO.getNotificationId());
			//			Money amount=Money.zero();
			//			for(FChargeNotificationFeeDO feeDO:feeDOs){
			//			if (feeDO.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK.code())) {//存出保证金划回
			//				String affirmDetailIds=feeDO.getAffirmDetailIds();
			//				if(StringUtil.isNotBlank(affirmDetailIds)){
			//					String firms[]=affirmDetailIds.split(";");
			//					for(String firm:firms) {
			//						if (StringUtil.isNotBlank(firm)) {
			//							Long id = Long.parseLong(firm.split(",")[1]);
			//							Money firmAmount = new Money(firm.split(",")[0]);
			//							FFinanceAffirmDetailDO DO=fFinanceAffirmDetailDAO.findById(id);
			//							//删除加钱
			//							amount=DO.getReturnCustomerAmount().add(firmAmount);
			//							DO.setReturnCustomerAmount(amount);
			//							fFinanceAffirmDetailDAO.update(DO);
			//						}
			//					}
			//
			//				}
			//			}
			//		}
			//附件管理附件删除
			commonAttachmentDAO.deleteByBizNoModuleType("PM_" + fChargeNotificationDO.getFormId(),
				CommonAttachmentTypeEnum.CHARGE_NOTIFICATION.code());
			commonAttachmentDAO.deleteByBizNoModuleType("PM_" + fChargeNotificationDO.getFormId(),
				CommonAttachmentTypeEnum.CHARGE_NOTIFICATION_BASE.code());
		}
	}
	
	@Override
	public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO.findByFormId(formInfo
			.getFormId());
		fChargeNotificationDO.setStatus(FormStatusEnum.BACK.code());
		fChargeNotificationDAO.update(fChargeNotificationDO);
	}
	
	/**
	 * 异步同步单据到资金系统
	 * @param objects
	 * @see com.born.fcs.pm.biz.service.common.AsynchronousService#execute(java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Object[] objects) {
		try {
			FormInfo formInfo = (FormInfo) objects[0];
			FChargeNotificationDO cn = (FChargeNotificationDO) objects[1];
			List<FChargeNotificationFeeDO> feeDOs = (List<FChargeNotificationFeeDO>) objects[2];
			
			//财务确认信息
			FFinanceAffirmDO affirmDO = fFinanceAffirmDAO.findByFormId(formInfo.getFormId());
			List<FFinanceAffirmDetailDO> feeList = null;
			
			ReceiptPaymentFormOrder rpOrder = new ReceiptPaymentFormOrder();
			BeanCopier.staticCopy(formInfo, rpOrder);
			BeanCopier.staticCopy(cn, rpOrder);
			rpOrder.setContractNo(cn.getContractCode());
			//rpOrder.setContractName(cn.getContractCode());
			rpOrder.setSourceForm(SourceFormEnum.CHARGE_NOTIFICATION);
			rpOrder.setSourceFormId(String.valueOf(formInfo.getFormId()));
			rpOrder.setSourceFormSys(SystemEnum.PM);
			if (affirmDO != null) { //有财务确认
				rpOrder.setRemark(affirmDO.getRemark());
				rpOrder.setAttach(affirmDO.getAttach());
				feeList = fFinanceAffirmDetailDAO.findByAffirmId(affirmDO.getAffirmId());
			}
			
			if (cn.getProjectCode() != null && cn.getProjectCode().startsWith("JJ")) {
				rpOrder.setProjectName("经纪业务");
			}
			List<ReceiptPaymentFormFeeOrder> feeOrderList = Lists.newArrayList();
			if (feeList != null) { //有财务确认信息
				for (FFinanceAffirmDetailDO fee : feeList) {
					ReceiptPaymentFormFeeOrder feeOrder = new ReceiptPaymentFormFeeOrder();
					feeOrder.setFeeType(SubjectCostTypeEnum.getByChargeFeeType(FeeTypeEnum
						.getByCode(fee.getFeeType())));
					feeOrder.setOccurTime(fee.getPayTime());
					feeOrder.setAmount(fee.getPayAmount());
					feeOrder.setAccount(fee.getPayeeAccountName());
					feeOrder.setDepositAccount(fee.getDepositAccount());
					feeOrder.setDepositRate(fee.getMarginRate());
					feeOrder.setDepositTime(fee.getDepositTime());
					feeOrder.setDepositPeriod(fee.getPeriod());
					feeOrder.setPeriodUnit(fee.getPeriodUnit());
					feeOrder.setAttach(fee.getAttach());
					for (FChargeNotificationFeeDO applyFee : feeDOs) {
						if (applyFee.getId() == fee.getDetailId()) {
							if (StringUtil.isNotBlank(applyFee.getRemark())) {
								feeOrder.setRemark(applyFee.getRemark());
							} else {
								String remark = "收费基数："
												+ applyFee.getChargeBase().toStandardString() + "元"
												+ "，收费费率：" + applyFee.getChargeRate() + "%";
								if (applyFee.getStartTime() != null
									&& applyFee.getEndTime() != null) {
									remark += "，计费期间："
												+ DateUtil.dtSimpleFormat(applyFee.getStartTime())
												+ "至"
												+ DateUtil.dtSimpleFormat(applyFee.getEndTime());
								}
								feeOrder.setRemark(remark);
							}
						}
					}
					
					feeOrderList.add(feeOrder);
				}
				rpOrder.setFeeOrderList(feeOrderList);
				receiptPaymentFormServiceClient.add(rpOrder);
				
				//更新资金预测
				try {
					
					for (FFinanceAffirmDetailDO fee : feeList) {
						//查询相关费用是否有预测
						ForecastFeeTypeEnum ffeeType = ForecastFeeTypeEnum.getByInFees(fee
							.getFeeType());
						if (ffeeType == null || ZERO_MONEY.greaterThan(fee.getPayAmount()))
							continue;
						FeeTypeEnum feeType = FeeTypeEnum.getByCode(fee.getFeeType());
						ForecastAccountChangeOrder changeForecastOrder = new ForecastAccountChangeOrder();
						changeForecastOrder.setFeeType(ffeeType);
						changeForecastOrder.setProjectCode(cn.getProjectCode());
						changeForecastOrder.setAmount(fee.getPayAmount());
						changeForecastOrder.setUserId(formInfo.getUserId());
						changeForecastOrder.setUserAccount(formInfo.getUserAccount());
						changeForecastOrder.setUserName(formInfo.getUserName());
						if (feeType == null) {
							changeForecastOrder.setForecastMemo("收费 "
																+ fee.getPayAmount()
																	.toStandardString() + "元");
						} else {
							changeForecastOrder.setForecastMemo("收费[ "
																+ feeType.getMessage()
																+ " ] "
																+ fee.getPayAmount()
																	.toStandardString() + "元");
						}
						forecastServiceClient.change(changeForecastOrder);
					}
					
				} catch (Exception e) {
					logger.error("收费后更新资金预测数据出错：{}", e);
				}
				
			} else if (feeDOs != null) { //无财务信息
				for (FChargeNotificationFeeDO applyFee : feeDOs) {
					ReceiptPaymentFormFeeOrder feeOrder = new ReceiptPaymentFormFeeOrder();
					feeOrder.setFeeType(SubjectCostTypeEnum.getByChargeFeeType(FeeTypeEnum
						.getByCode(applyFee.getFeeType())));
					feeOrder.setAmount(applyFee.getChargeAmount());
					if (StringUtil.isNotBlank(applyFee.getRemark())) {
						feeOrder.setRemark(applyFee.getRemark());
					} else {
						String remark = "收费基数：" + applyFee.getChargeBase().toStandardString() + "元"
										+ "，收费费率：" + applyFee.getChargeRate() + "%";
						if (applyFee.getStartTime() != null && applyFee.getEndTime() != null) {
							remark += "，计费期间：" + DateUtil.dtSimpleFormat(applyFee.getStartTime())
										+ "至" + DateUtil.dtSimpleFormat(applyFee.getEndTime());
						}
						feeOrder.setRemark(remark);
					}
					feeOrderList.add(feeOrder);
				}
				rpOrder.setFeeOrderList(feeOrderList);
				receiptPaymentFormServiceClient.add(rpOrder);
			}
		} catch (Exception e) {
			logger.error("发送收费数据到资金系统出错：{}", e);
		}
		
	}
	
	private void saveAffirm(Map<String, Object> map, FChargeNotificationDO fChargeNotificationDO) {
		FFinanceAffirmOrder order = new FFinanceAffirmOrder();
		//final Date now = FcsPmDomainHolder.get().getSysDate();
		order.setAffirmFormType(FinanceAffirmTypeEnum.CHARGE_NOTIFICATION.code());
		String formId = map.get("formId") == null ? "0" : (String) map.get("formId");
		String remark = map.get("remark") == null ? null : (String) map.get("remark");
		String amount = StringUtil.isBlank((String) map.get("amount")) ? "0.00" : (String) map
			.get("amount");
		String attach = map.get("outAttach") == null ? null : (String) map.get("outAttach");
		String projectCode = map.get("projectCode") == null ? null : (String) map
			.get("projectCode");
		String projectName = map.get("projectName") == null ? null : (String) map
			.get("projectName");
		order.setFormId(Long.parseLong(formId));
		order.setRemark(remark);
		order.setAttach(attach);
		order.setAmount(new Money(amount));
		order.setProjectCode(projectCode);
		order.setProjectName(projectName);
		int length = Integer.parseInt((String) map.get("length"));
		List<FFinanceAffirmDetailOrder> detailOrders = Lists.newArrayList();
		for (int i = 0; i < length; i++) {
			FFinanceAffirmDetailOrder detailDO = new FFinanceAffirmDetailOrder();
			detailDO.setFeeType(map.get("feeType" + i) == null ? null : (String) map.get("feeType"
																							+ i));
			detailDO.setDetailId(map.get("detailId" + i) == null ? 0 : Long.parseLong((String) map
				.get("detailId" + i)));
			detailDO.setPayAmount(StringUtil.isBlank((String) map.get("payAmount" + i)) ? Money
				.zero() : new Money((String) map.get("payAmount" + i)));
			detailDO
				.setReturnCustomerAmount(StringUtil.isBlank((String) map.get("payAmount" + i)) ? Money
					.zero() : new Money((String) map.get("payAmount" + i)));
			detailDO.setPayTime(map.get("payTime" + i) == null ? null : DateUtil.parse((String) map
				.get("payTime" + i)));
			detailDO.setDepositTime(StringUtil.isBlank((String) map.get("depositTime" + i)) ? null
				: DateUtil.parse((String) map.get("depositTime" + i)));
			detailDO.setPayeeAccountName(map.get("payeeAccountName" + i) == null ? null
				: (String) map.get("payeeAccountName" + i));
			detailDO.setDepositAccount(map.get("depositAccount" + i) == null ? null : (String) map
				.get("depositAccount" + i));
			detailDO.setMarginRate(StringUtil.isBlank((String) map.get("marginRate" + i)) ? 0
				: new Double((String) map.get("marginRate" + i)));
			detailDO.setPeriod(StringUtil.isBlank((String) map.get("period" + i)) ? 0 : NumberUtil
				.parseInt((String) map.get("period" + i)));
			detailDO.setPeriodUnit(map.get("periodUnit" + i) == null ? null : (String) map
				.get("periodUnit" + i));
			detailDO.setAttach(map.get("attach" + i) == null ? null : (String) map
				.get("attach" + i));
			detailOrders.add(detailDO);
			if (detailDO.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK.code())) {
				//				checkAmount(detailDO,fChargeNotificationDO);
				String affirmDetailIds = map.get("affirmDetailIds") == null ? null : (String) map
					.get("affirmDetailIds");
				FChargeNotificationFeeDO feeDO = fChargeNotificationFeeDAO.findById(detailDO
					.getDetailId());
				feeDO.setAffirmDetailIds(affirmDetailIds);
				fChargeNotificationFeeDAO.update(feeDO);
				setChargeCapitalApproproation(detailDO.getDetailId(), affirmDetailIds);
			}
		}
		order.setDetailOrders(detailOrders);
		financeAffirmService.save(order);
		SimpleUserInfo currentUser = (SimpleUserInfo) FcsPmDomainHolder.get().getAttribute(
			"currentUser");
		//保存附件
		if (StringUtil.isNotEmpty(attach)) {
			CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
			attachOrder.setProjectCode(order.getProjectCode());
			attachOrder.setBizNo("PM_" + formId + "_CWQR");
			attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
			attachOrder.setModuleType(CommonAttachmentTypeEnum.CHARGE_NOTIFICATION_FINANCE_AFFIRM);
			attachOrder.setUploaderId(currentUser.getUserId());
			attachOrder.setUploaderName(currentUser.getUserName());
			attachOrder.setUploaderAccount(currentUser.getUserAccount());
			attachOrder.setPath(attach);
			attachOrder.setRemark("收费通知单-财务确认附件");
			commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
		}
		for (FFinanceAffirmDetailOrder detailOrder : detailOrders) {
			if (StringUtil.isNotEmpty(detailOrder.getAttach())) {
				CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
				attachOrder.setProjectCode(order.getProjectCode());
				attachOrder.setBizNo("PM_" + formId + "_" + detailOrder.getDetailId() + "_CWQR");
				attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
				attachOrder
					.setModuleType(CommonAttachmentTypeEnum.CHARGE_NOTIFICATION_FINANCE_AFFIRM_DETAIL);
				attachOrder.setUploaderId(currentUser.getUserId());
				attachOrder.setUploaderName(currentUser.getUserName());
				attachOrder.setUploaderAccount(currentUser.getUserAccount());
				attachOrder.setPath(detailOrder.getAttach());
				attachOrder.setRemark("收费通知单-财务确认附件("
										+ FeeTypeEnum.getByCode(detailOrder.getFeeType()).message()
										+ ")");
				commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
			}
		}
	}
	
	private void setChargeCapitalApproproation(Long detailId, String affirmDetailIds) {
		ChargeCapitalOrder order = new ChargeCapitalOrder();
		List<ChargeNoticeCapitalApproproationOrder> itemOrders = Lists.newArrayList();
		order.setType(FinanceAffirmTypeEnum.CHARGE_NOTIFICATION);
		order.setDetailId(detailId);
		String firms[] = affirmDetailIds.split(";");
		for (String firm : firms) {
			if (com.born.fcs.pm.util.StringUtil.isNotBlank(firm)) {
				Long id = Long.parseLong(firm.split(",")[1]);
				Money firmAmount = new Money(firm.split(",")[0]);
				ChargeNoticeCapitalApproproationOrder itemOrder = new ChargeNoticeCapitalApproproationOrder();
				itemOrder.setPayId(id + "");
				itemOrder.setUseAmount(firmAmount);
				itemOrders.add(itemOrder);
			}
		}
		order.setItemOrder(itemOrders);
		financeAffirmService.saveChargeCapital(order);
		
	}
	
	private void checkAmount(FFinanceAffirmDetailOrder detailDO,
								FChargeNotificationDO fChargeNotificationDO) {
		//校验金额的合法性
		List<FChargeNotificationFeeDO> feeDOs = fChargeNotificationFeeDAO
			.findByNotificationId(fChargeNotificationDO.getNotificationId());
		for (FChargeNotificationFeeDO feeDO : feeDOs) {
			Map<Long, Money> moneyMap = new HashMap<Long, Money>();
			if (feeDO.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK.code())) {
				String affirmDetailIds = feeDO.getAffirmDetailIds();
				if (StringUtil.isNotBlank(affirmDetailIds)) {
					String firms[] = affirmDetailIds.split(";");
					List<Long> ids = Lists.newArrayList();
					for (String firm : firms) {
						if (StringUtil.isNotBlank(firm)) {
							Long id = Long.parseLong(firm.split(",")[1]);
							Money amount = new Money(firm.split(",")[0]);
							moneyMap.put(id, amount);
							ids.add(id);
						}
					}
					ProjectChargePayQueryOrder queryOrder = new ProjectChargePayQueryOrder();
					queryOrder.setPayIdList(ids);
					queryOrder.setProjectCode(fChargeNotificationDO.getProjectCode());
					queryOrder.setAffirmFormType("CAPITAL_APPROPROATION_APPLY");
					queryOrder.setFeeType("DEPOSIT_PAID");
					queryOrder.setSortCol("p.return_customer_amount");
					queryOrder.setSortOrder("ASC");
					QueryBaseBatchResult<ProjectChargePayInfo> baseBatchResult = financeAffirmService
						.queryProjectChargePayDetailChoose(queryOrder);
					Money total = Money.zero();
					if (ListUtil.isNotEmpty(baseBatchResult.getPageList())) {
						for (ProjectChargePayInfo payInfo : baseBatchResult.getPageList()) {
							Money expectAmount = moneyMap.get(payInfo.getPayId()) == null ? Money
								.zero() : moneyMap.get(payInfo.getPayId());
							total = total.add(payInfo.getReturnCustomerAmount().add(expectAmount));
						}
					}
					String detailIds = "";
					if (detailDO.getPayAmount().greaterThan(total)
						|| detailDO.getPayAmount().equals(total)) {//如果相等或者大于说明所有的都用完了
						if (ListUtil.isNotEmpty(baseBatchResult.getPageList())) {
							for (ProjectChargePayInfo payInfo : baseBatchResult.getPageList()) {
								Money expectAmount = moneyMap.get(payInfo.getPayId()) == null ? Money
									.zero() : moneyMap.get(payInfo.getPayId());
								Money amount = expectAmount.add(payInfo.getReturnCustomerAmount());
								if (amount.equals(Money.zero())) {
									continue;
								}
								detailIds = detailIds + amount + "," + payInfo.getPayId() + ";";
							}
						}
						feeDO.setAffirmDetailIds(detailIds.substring(0, detailIds.length() - 1));
						fChargeNotificationFeeDAO.update(feeDO);
						setChargeCapitalApproproation(feeDO.getId(),
							detailIds.substring(0, detailIds.length() - 1));
					} else if (total.greaterThan(detailDO.getPayAmount())) {//小于就重新分摊
						Money tempAmount = detailDO.getPayAmount();
						for (ProjectChargePayInfo payInfo : baseBatchResult.getPageList()) {
							Money tempAmount2 = tempAmount;
							Money expectAmount = moneyMap.get(payInfo.getPayId()) == null ? Money
								.zero() : moneyMap.get(payInfo.getPayId());
							Money leftAmount = expectAmount.add(payInfo.getReturnCustomerAmount());
							tempAmount = tempAmount.subtract(leftAmount);
							if (tempAmount.greaterThan(Money.zero())
								|| tempAmount.equals(Money.zero())) {//减完了的
								if (leftAmount.equals(Money.zero())) {
									continue;
								}
								detailIds = detailIds + leftAmount + "," + payInfo.getPayId() + ";";
							} else {//没用完直接存剩下的
								detailIds = detailIds + tempAmount2 + "," + payInfo.getPayId()
											+ ";";
								break;
							}
						}
						feeDO.setAffirmDetailIds(detailIds.substring(0, detailIds.length() - 1));
						fChargeNotificationFeeDAO.update(feeDO);
						setChargeCapitalApproproation(feeDO.getId(),
							detailIds.substring(0, detailIds.length() - 1));
					}
				}
			}
		}
	}
	
	private void checkAmount(FChargeNotificationDO fChargeNotificationDO) {
		//校验金额的合法性
		List<FChargeNotificationFeeDO> feeDOs = fChargeNotificationFeeDAO
			.findByNotificationId(fChargeNotificationDO.getNotificationId());
		for (FChargeNotificationFeeDO feeDO : feeDOs) {
			if (feeDO.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK.code())) {
				String affirmDetailIds = feeDO.getAffirmDetailIds();
				if (StringUtil.isNotBlank(affirmDetailIds)) {
					String firms[] = affirmDetailIds.split(";");
					List<Long> ids = Lists.newArrayList();
					for (String firm : firms) {
						if (StringUtil.isNotBlank(firm)) {
							Long id = Long.parseLong(firm.split(",")[1]);
							ids.add(id);
						}
					}
					ProjectChargePayQueryOrder queryOrder = new ProjectChargePayQueryOrder();
					queryOrder.setPayIdList(ids);
					queryOrder.setProjectCode(fChargeNotificationDO.getProjectCode());
					queryOrder.setAffirmFormType("CAPITAL_APPROPROATION_APPLY");
					queryOrder.setFeeType("DEPOSIT_PAID");
					queryOrder.setSortCol("p.return_customer_amount");
					queryOrder.setSortOrder("ASC");
					QueryBaseBatchResult<ProjectChargePayInfo> baseBatchResult = financeAffirmService
						.queryProjectChargePayDetailChoose(queryOrder);
					Money total = Money.zero();
					if (ListUtil.isNotEmpty(baseBatchResult.getPageList())) {
						for (ProjectChargePayInfo payInfo : baseBatchResult.getPageList()) {
							total = total.add(payInfo.getReturnCustomerAmount());
						}
					}
					if (feeDO.getChargeAmount().greaterThan(total)) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
							"所选存出保证金金额不足，不能提交!");
					}
					//					else {
					//						String detailIds = "";
					//						if (feeDO.getChargeAmount().equals(total)) {//如果相等说明所有的都用完了
					//							if (ListUtil.isNotEmpty(baseBatchResult.getPageList())) {
					//								for (ProjectChargePayInfo payInfo : baseBatchResult.getPageList()) {
					//									if (payInfo.getReturnCustomerAmount().equals(Money.zero())) {
					//										continue;
					//									}
					//									detailIds = detailIds + payInfo.getReturnCustomerAmount() + ","
					//											+ payInfo.getPayId() + ";";
					//								}
					//							}
					//							feeDO
					//									.setAffirmDetailIds(detailIds.substring(0, detailIds.length() - 1));
					//							fChargeNotificationFeeDAO.update(feeDO);
					//							setChargeCapitalApproproation(feeDO.getId(),
					//									detailIds.substring(0, detailIds.length() - 1));
					//						} else if (total.greaterThan(feeDO.getChargeAmount())) {//小于就重新分摊
					//							Money tempAmount = feeDO.getChargeAmount();
					//							for (ProjectChargePayInfo payInfo : baseBatchResult.getPageList()) {
					//								Money tempAmount2 = tempAmount;
					//								tempAmount = tempAmount.subtract(payInfo.getReturnCustomerAmount());
					//								if (tempAmount.greaterThan(Money.zero())
					//										|| tempAmount.equals(Money.zero())) {//减完了的
					//									if (payInfo.getReturnCustomerAmount().equals(Money.zero())) {
					//										continue;
					//									}
					//									detailIds = detailIds + payInfo.getReturnCustomerAmount() + ","
					//											+ payInfo.getPayId() + ";";
					//								} else {//没用完直接存剩下的
					//									detailIds = detailIds + tempAmount2 + "," + payInfo.getPayId()
					//											+ ";";
					//									break;
					//								}
					//							}
					//							feeDO
					//									.setAffirmDetailIds(detailIds.substring(0, detailIds.length() - 1));
					//							fChargeNotificationFeeDAO.update(feeDO);
					//							setChargeCapitalApproproation(feeDO.getId(),
					//									detailIds.substring(0, detailIds.length() - 1));
					//						}
					//					}
				}
			}
		}
	}
	
	// 发送消息
	public void sendMessage(FChargeNotificationDO applyDO) {
		MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
		StringBuffer sb = new StringBuffer();
		sb.append("项目编号" + applyDO.getProjectCode());
		sb.append("，项目名称" + applyDO.getProjectName());
		sb.append("有一笔收款通知单审核通过，请及时到资金管理系统做账务处理。");
		String content = sb.toString();
		messageOrder.setMessageContent(content);
		List<SimpleUserInfo> sendUserList = new ArrayList<SimpleUserInfo>();
		String sysParamValueCWYFG = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_CWYSG_ROLE_NAME.code()); // 财务应收岗 参数
		List<SysUser> listUserCWYFG = bpmUserQueryService.findUserByRoleAlias(sysParamValueCWYFG);
		if (ListUtil.isNotEmpty(listUserCWYFG)) {
			for (SysUser sysUser : listUserCWYFG) {
				if (sysUser != null) {
					SimpleUserInfo user = new SimpleUserInfo();
					user.setUserAccount(sysUser.getAccount());
					user.setUserId(sysUser.getUserId());
					user.setUserName(sysUser.getFullname());
					sendUserList.add(user);
				}
			}
		}
		messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
			.toArray(new SimpleUserInfo[sendUserList.size()]));
		siteMessageService.addMessageInfo(messageOrder);
		
	}
}
