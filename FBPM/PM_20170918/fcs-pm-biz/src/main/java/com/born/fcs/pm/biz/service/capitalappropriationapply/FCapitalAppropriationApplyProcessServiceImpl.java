package com.born.fcs.pm.biz.service.capitalappropriationapply;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.info.forecast.SysForecastParamAllInfo;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountChangeOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.result.forecast.SysForecastParamResult;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.job.AsynchronousTaskJob;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.biz.service.common.AsynchronousService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.dal.dataobject.ChargeNoticeCapitalApproproationDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyFeeCompensatoryChannelDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyFeeDO;
import com.born.fcs.pm.dal.dataobject.FFinanceAffirmDO;
import com.born.fcs.pm.dal.dataobject.FFinanceAffirmDetailDO;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FCapitalAppropriationApplyTypeEnum;
import com.born.fcs.pm.ws.enums.FinanceAffirmTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyFeeInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractItemInfo;
import com.born.fcs.pm.ws.info.financeaffirm.FFinanceAffirmDetailInfo;
import com.born.fcs.pm.ws.info.financeaffirm.FFinanceAffirmInfo;
import com.born.fcs.pm.ws.info.financeaffirm.ProjectChargePayInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeTansferInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationAmountOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ChargeCapitalOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ChargeNoticeCapitalApproproationOrder;
import com.born.fcs.pm.ws.order.financeaffirm.FFinanceAffirmDetailOrder;
import com.born.fcs.pm.ws.order.financeaffirm.FFinanceAffirmOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ProjectChargePayQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeTansferOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.capitalappropriationapply.FCapitalAppropriationApplyService;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.financeaffirm.FinanceAffirmService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectTransferService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 资金划付申请审核流程处理
 * 
 * @author Ji
 * 
 * 2016-5-23 下午17:58:00
 */
@Service("fCapitalAppropriationApplyProcessService")
public class FCapitalAppropriationApplyProcessServiceImpl extends BaseProcessService implements
																					AsynchronousService {
	@Autowired
	FinancialProjectTransferService financialProjectTransferService;
	@Autowired
	FinancialProjectService financialProjectService;
	@Autowired
	FinancialProjectSetupService financialProjectSetupService;
	@Autowired
	FCapitalAppropriationApplyService fCapitalAppropriationApplyService;
	@Autowired
	CouncilSummaryService councilSummaryService;
	@Autowired
	ReceiptPaymentFormService receiptPaymentFormServiceClient;
	@Autowired
	ForecastService forecastServiceClient;
	@Autowired
	FormService formService;
	@Autowired
	ProjectContractService projectContractService;
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	@Autowired
	FinanceAffirmService financeAffirmService;
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	private SysParameterService sysParameterServiceClient;
	@Autowired
	CommonAttachmentService commonAttachmentService;
	@Autowired
	ProjectChannelRelationService projectChannelRelationService;
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
			.getFormId());
		if (StringUtil.equals(applyDO.getProjectType(), "FINANCIAL_PRODUCT")) {
			if (applyDO.getOutBizNo() > 0) { //回购
				ProjectFinancialInfo project = financialProjectService.queryByCode(applyDO
					.getProjectCode());
				vars.put("项目编号", project.getProjectCode());
				vars.put("产品名称", project.getProductName());
			} else { //购买
				FProjectFinancialInfo project = financialProjectSetupService
					.queryByProjectCode(applyDO.getProjectCode());
				vars.put("项目编号", project.getProjectCode());
				vars.put("产品名称", project.getProductName());
			}
		} else {
			vars.put("产品名称", "");
		}
		return vars;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			long formId = order.getFormInfo().getFormId();
			FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO
				.findByFormId(formId);
			if (applyDO == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金划付申请单未找到");
			}
			//校验金额的合法性
			List<FCapitalAppropriationApplyFeeDO> feeDOs = FCapitalAppropriationApplyFeeDAO
				.findByApplyId(applyDO.getApplyId());
			
			for (FCapitalAppropriationApplyFeeDO feeDO : feeDOs) {
				if (feeDO.getAppropriateReason().equals(
					PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code())) {
					String affirmDetailIds = feeDO.getFinanceAffirmDetailId();
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
						queryOrder.setProjectCode(applyDO.getProjectCode());
						queryOrder.setAffirmFormType("CHARGE_NOTIFICATION");
						queryOrder.setFeeType("GUARANTEE_DEPOSIT");
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
						if (feeDO.getAppropriateAmount().greaterThan(total)) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
								"所选存入保证金金额不足，不能提交!");
						}
						//						} else {
						//							String detailIds = "";
						//							if (feeDO.getAppropriateAmount().equals(total)) {//如果相等说明所有的都用完了
						//								if (ListUtil.isNotEmpty(baseBatchResult.getPageList())) {
						//									for (ProjectChargePayInfo payInfo : baseBatchResult
						//										.getPageList()) {
						//										if (payInfo.getReturnCustomerAmount().equals(Money.zero())) {
						//											continue;
						//										}
						//										detailIds = detailIds + payInfo.getReturnCustomerAmount()
						//													+ "," + payInfo.getPayId() + ";";
						//									}
						//								}
						//								feeDO.setFinanceAffirmDetailId(detailIds);
						//								FCapitalAppropriationApplyFeeDAO.update(feeDO);
						//								setChargeCapitalApproproation(feeDO.getId(),
						//									detailIds.substring(0, detailIds.length() - 1));
						//							} else if (total.greaterThan(feeDO.getAppropriateAmount())) {//小于就重新分摊
						//								Money tempAmount = feeDO.getAppropriateAmount();
						//								for (ProjectChargePayInfo payInfo : baseBatchResult.getPageList()) {
						//									Money tempAmount2 = tempAmount;
						//									tempAmount = tempAmount.subtract(payInfo
						//										.getReturnCustomerAmount());
						//									if (tempAmount.greaterThan(Money.zero())
						//										|| tempAmount.equals(Money.zero())) {//减完了的
						//										if (payInfo.getReturnCustomerAmount().equals(Money.zero())) {
						//											continue;
						//										}
						//										detailIds = detailIds + payInfo.getReturnCustomerAmount()
						//													+ "," + payInfo.getPayId() + ";";
						//									} else {//没用完直接存剩下的
						//										detailIds = detailIds + tempAmount2 + ","
						//													+ payInfo.getPayId() + ";";
						//										break;
						//									}
						//								}
						//								feeDO.setFinanceAffirmDetailId(detailIds);
						//								FCapitalAppropriationApplyFeeDAO.update(feeDO);
						//								setChargeCapitalApproproation(feeDO.getId(),
						//									detailIds.substring(0, detailIds.length() - 1));
						//							}
						//						}
						//					}
					}
				}
			}
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder
					.setCustomTaskName(applyDO.getProjectName()
										+ "-"
										+ (StringUtil.equals(applyDO.getIsSimple(), "IS") ? "被扣划冻结申请单"
											: "资金划付申请单"));
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("资金划付申请单流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 启动流程后业务处理(BASE)
		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
			.getFormId());
		if (applyDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金划付申请单未找到");
		}
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(final WorkFlowBeforeProcessOrder order) {
		return commonProcess(order, "资金划付审核前处理", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				Map<String, Object> customizeMap = order.getCustomizeMap();
				String financeAffirm = (String) customizeMap.get("financeAffirm");
				if ("YES".equals(financeAffirm)) {
					saveAffirm(customizeMap);
				}
				
				//选择法务经理
				String chooseLegalManager = (String) customizeMap.get("chooseLegalManager");
				if (BooleanEnum.YES.code().equals(chooseLegalManager)) {
					
					FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO
						.findByFormId(order.getFormInfo().getFormId());
					
					if (applyDO == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"资金划付申请单未找到");
					}
					long legalManagerId = NumberUtil.parseLong((String) customizeMap
						.get("legalManagerId"));
					String legalManagerAccount = (String) customizeMap.get("legalManagerAccount");
					String legalManagerName = (String) customizeMap.get("legalManagerName");
					
					//保存法务经理到相关人员表
					ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
					relatedUser.setProjectCode(applyDO.getProjectCode());
					relatedUser.setUserType(ProjectRelatedUserTypeEnum.LEGAL_MANAGERB);
					relatedUser.setRemark("被扣划冻结选择法务经理");
					relatedUser.setUserId(legalManagerId);
					relatedUser.setUserAccount(legalManagerAccount);
					relatedUser.setUserName(legalManagerName);
					projectRelatedUserService.setRelatedUser(relatedUser);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
			.getFormId());
		if (applyDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金划付申请单未找到");
		}
		List<FCapitalAppropriationApplyFeeDO> applyFeeDO = FCapitalAppropriationApplyFeeDAO
			.findByApplyId(applyDO.getApplyId());
		//本次申请金额总和
		Money sumAmont = new Money(0);
		for (FCapitalAppropriationApplyFeeDO fCapitalAppropriationApplyFeeDO : applyFeeDO) {
			sumAmont = sumAmont.add(fCapitalAppropriationApplyFeeDO.getAppropriateAmount());
		}
		//是否超额
		List<FlowVarField> fields = Lists.newArrayList();
		FlowVarField overfull = new FlowVarField();
		overfull.setVarName("money");
		overfull.setVarType(FlowVarTypeEnum.STRING);
		overfull.setVarVal(sumAmont.toString());
		fields.add(overfull);
		
		if (FCapitalAppropriationApplyTypeEnum.NOT_FINANCIAL_PRODUCT.code().equals(
			applyDO.getProjectType())) {
			//是否有相应签报和会议纪要
			FlowVarField proof = new FlowVarField();
			
			String isQbOrHyjy = "NO";
			if (applyFeeDO != null && applyFeeDO.size() > 0) {
				for (FCapitalAppropriationApplyFeeDO fCapitalAppropriationApplyFeeDO : applyFeeDO) {
					if (fCapitalAppropriationApplyFeeDO.getFormChange() != null
						|| fCapitalAppropriationApplyFeeDO.getRiskCouncilSummary() != null
						|| fCapitalAppropriationApplyFeeDO.getProjectApproval() != null) {
						isQbOrHyjy = "IS";
						break;
					}
				}
			}
			proof.setVarName("proof");
			proof.setVarType(FlowVarTypeEnum.STRING);
			proof.setVarVal(isQbOrHyjy);
			fields.add(proof);
		}
		
		if (applyDO != null && StringUtil.equals("IS", applyDO.getIsSimple())) {
			FlowVarField department = new FlowVarField();
			department.setVarName("department");
			department.setVarType(FlowVarTypeEnum.STRING);
			department.setVarVal("0");
			String busiDepts = sysParameterService
				.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_DEPARTMENT.code());
			if (StringUtil.isNotBlank(busiDepts)) {
				String[] busiDeptArr = busiDepts.split(",");
				for (String busiDept : busiDeptArr) {
					if (StringUtil.equals(busiDept, formInfo.getDeptCode())) {
						department.setVarVal("1");
						break;
					}
				}
			}
			fields.add(department);
		}
		
		return fields;
	}
	
	//删除
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
			.getFormId());
		if (applyDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金划付申请单未找到");
		}
		List<FCapitalAppropriationApplyFeeDO> listAppFeeDO = FCapitalAppropriationApplyFeeDAO
			.findByApplyId(applyDO.getApplyId());
		FCapitalAppropriationApplyDAO.deleteByFormId(formInfo.getFormId());
		for (FCapitalAppropriationApplyFeeDO applyFeeDO : listAppFeeDO) {
			FCapitalAppropriationApplyFeeDAO.deleteById(applyFeeDO.getId());
			FCapitalAppropriationApplyFeeCompensatoryChannelDAO.deleteByFeeId(applyFeeDO.getId());//删除代偿渠道明细			
		}
		//附件管理附件删除
		commonAttachmentDAO.deleteByBizNoModuleType("PM_" + applyDO.getFormId(),
			CommonAttachmentTypeEnum.CAPITAL_APPROPRIATION.code());
		
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		//划付申请单
		FCapitalAppropriationApplyInfo applyInfo = fCapitalAppropriationApplyService
			.findCapitalAppropriationApplyByFormId(formInfo.getFormId());
		//		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
		//			.getFormId());
		if (applyInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金划付申请单未找到");
		}
		//划付明细
		//		List<FCapitalAppropriationApplyFeeDO> ListFeeDO = FCapitalAppropriationApplyFeeDAO
		//			.findByApplyId(applyDO.getApplyId());
		List<FCapitalAppropriationApplyFeeInfo> feeList = fCapitalAppropriationApplyService
			.findByApplyId(applyInfo.getApplyId());
		
		FFinanceAffirmDO affirmDO = fFinanceAffirmDAO.findByFormId(formInfo.getFormId());//财务确认信息
		List<FFinanceAffirmDetailDO> detailList = fFinanceAffirmDetailDAO.findByAffirmId(affirmDO
			.getAffirmId());
		if (ListUtil.isNotEmpty(feeList) && affirmDO != null) {
			for (FCapitalAppropriationApplyFeeInfo feeInfo : feeList) {
				
				FFinanceAffirmDetailDO detialDO = fFinanceAffirmDetailDAO
					.findByAffirmIdAndDetailId(affirmDO.getAffirmId(), feeInfo.getId());
				if (PaymentMenthodEnum.FINANCIAL_PRODUCT == feeInfo.getAppropriateReason()) {//理财产品购买
					if (detialDO != null) {
						sendMessageForFinanceProduct(formInfo, feeInfo.getAppropriateAmount(),
							detialDO.getPayAmount());
					}
				}
			}
			sendMessageForm(formInfo);
		}
		if (affirmDO != null) {
			if (applyInfo.getProjectType() == FCapitalAppropriationApplyTypeEnum.FINANCIAL_PRODUCT) {//理财类项目
				if (applyInfo.getOutBizNo() > 0) { //转让回购划付
					ProjectFinancialTradeTansferOrder tOrder = new ProjectFinancialTradeTansferOrder();
					tOrder.setApplyId(applyInfo.getOutBizNo());
					tOrder.setIsConfirm(BooleanEnum.YES);
					financialProjectTransferService.saveTrade(tOrder);
				} else {
					
					//立项购买划付
					//				List<FCapitalAppropriationApplyFeeDO> feeList = FCapitalAppropriationApplyFeeDAO
					//					.findByApplyId(applyDO.getApplyId());
					Money applyAmount = Money.zero();
					if (ListUtil.isNotEmpty(feeList)) {
						for (FCapitalAppropriationApplyFeeInfo fee : feeList) {
							FFinanceAffirmDetailDO detialDO = fFinanceAffirmDetailDAO
								.findByAffirmIdAndDetailId(affirmDO.getAffirmId(), fee.getId());
							if (detialDO != null) {
								//applyAmount.addTo(fee.getAppropriateAmount());
								applyAmount.addTo(detialDO.getPayAmount());
							}
						}
					}
					FcsBaseResult initResult = financialProjectService.initPorjectRecord(
						applyInfo.getProjectCode(), applyAmount);
					if (!initResult.isSuccess()) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
							"生成理财产品购买记录出错");
					}
				}
			} else {//非理财类项目
				ProjectDO project = projectDAO.findByProjectCode(applyInfo.getProjectCode());
				
				Money selfDepositAmount = project.getSelfDepositAmount();//原有自家保证金
				Money customerDepositAmount = project.getCustomerDepositAmount();//原有客户保证金
				Money compPrincipalAmount = project.getCompPrincipalAmount();//原有已代偿本金
				Money compInterestAmount = project.getCompInterestAmount();//原有已代偿利息
				Money refundAmount = project.getRefundAmount();//原有已退费金额
				
				for (FCapitalAppropriationApplyFeeInfo fee : feeList) {
					FFinanceAffirmDetailDO detialDO = fFinanceAffirmDetailDAO
						.findByAffirmIdAndDetailId(affirmDO.getAffirmId(), fee.getId());
					if (detialDO != null) {
						//存出保证金
						if (fee.getAppropriateReason() == PaymentMenthodEnum.DEPOSIT_PAID) {
							selfDepositAmount = selfDepositAmount.add(detialDO.getPayAmount());
						}
						//退还客户保证金
						if (fee.getAppropriateReason() == PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND) {
							customerDepositAmount = customerDepositAmount.subtract(detialDO
								.getPayAmount());
							//更新f_finance_affirm_detail、charge_notice_capital_approproation表剩余金额
							String affirmDetailIds = fee.getFinanceAffirmDetailId();
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
												FinanceAffirmTypeEnum.CAPITAL_APPROPROATION_APPLY
													.code(), fee.getId(), id + "");
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
						}
						//代偿本金
						if (fee.getAppropriateReason() == PaymentMenthodEnum.COMPENSATORY_PRINCIPAL) {
							compPrincipalAmount = compPrincipalAmount.add(detialDO.getPayAmount());
							//在资金划付申请单中已经做了代偿本金/代偿利息，并且审核通过后，状态更改为追偿中
							project.setPhases(ProjectPhasesEnum.RECOVERY_PHASES.code());
							project.setStatus(ProjectStatusEnum.RECOVERY.code());
							
							//XXX 更新渠道相关金额(目前没有区分资金渠道代偿)
							if (ProjectUtil.isBankFinancing(project.getBusiType())) {
								List<FCapitalAppropriationApplyFeeCompensatoryChannelDO> channels = FCapitalAppropriationApplyFeeCompensatoryChannelDAO
									.findByFeeId(fee.getId());
								for (FCapitalAppropriationApplyFeeCompensatoryChannelDO channel : channels) {
									ProjectChannelRelationAmountOrder channelAmountOrder = new ProjectChannelRelationAmountOrder();
									channelAmountOrder.setProjectCode(project.getProjectCode());
									channelAmountOrder.setChannelCode(channel
										.getCapitalChannelCode());
									channelAmountOrder.setCompAmount(channel
										.getLiquidityLoanAmount()
										.add(channel.getAcceptanceBillAmount())
										.add(
											channel.getCreditLetterAmount().add(
												channel.getFixedAssetsFinancingAmount())));
									channelAmountOrder.setCompLiquidityLoanAmount(channel
										.getLiquidityLoanAmount());
									channelAmountOrder.setCompAcceptanceBillAmount(channel
										.getAcceptanceBillAmount());
									channelAmountOrder.setCompCreditLetterAmount(channel
										.getCreditLetterAmount());
									channelAmountOrder.setCompFixedAssetsFinancingAmount(channel
										.getFixedAssetsFinancingAmount());
									projectChannelRelationService
										.updateRelatedAmount(channelAmountOrder);
								}
							} else {
								ProjectChannelRelationAmountOrder channelAmountOrder = new ProjectChannelRelationAmountOrder();
								channelAmountOrder.setProjectCode(project.getProjectCode());
								channelAmountOrder.setCompAmount(detialDO.getPayAmount());
								projectChannelRelationService
									.updateRelatedAmount(channelAmountOrder);
							}
						}
						
						//代偿利息
						if (fee.getAppropriateReason() == PaymentMenthodEnum.COMPENSATORY_INTEREST) {
							compInterestAmount = compInterestAmount.add(detialDO.getPayAmount());
							project.setPhases(ProjectPhasesEnum.RECOVERY_PHASES.code());
							project.setStatus(ProjectStatusEnum.RECOVERY.code());
						}
						//退费
						if (fee.getAppropriateReason() == PaymentMenthodEnum.REFUND) {
							refundAmount = refundAmount.subtract(detialDO.getPayAmount());
						}
						
					}
					project.setCompPrincipalAmount(compPrincipalAmount);
					project.setCompInterestAmount(compInterestAmount);
					project.setSelfDepositAmount(selfDepositAmount);
					project.setCustomerDepositAmount(customerDepositAmount);
					project.setRefundAmount(refundAmount);
					projectDAO.update(project); //更新项目信息
					
					//客户保证金变化 2017-07-31 通过后面改变预测接口修改
					//					if (customerDepositAmount.greaterThan(ZERO_MONEY)) {
					//						projectService.syncForecastDeposit(project.getProjectCode());
					//					}
				}
			}
		}
		//将信息同步到资金系统
		asynchronousDataToFundSys(applyInfo.getFormId(), applyInfo, detailList);
		
	}
	
	/**
	 * see line 238 异步同步单据到资金系统
	 * @param objects
	 * @see com.born.fcs.pm.biz.service.common.AsynchronousService#execute(java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Object[] objects) {
		try {
			ProjectInfo project = null;
			FormInfo formInfo = (FormInfo) objects[0];
			FCapitalAppropriationApplyInfo applyInfo = (FCapitalAppropriationApplyInfo) objects[1];
			List<FFinanceAffirmDetailDO> feeList = (List<FFinanceAffirmDetailDO>) objects[2];
			List<FCapitalAppropriationApplyFeeInfo> detailFees = fCapitalAppropriationApplyService
				.findByApplyId(applyInfo.getApplyId());
			ReceiptPaymentFormOrder rpOrder = new ReceiptPaymentFormOrder();
			BeanCopier.staticCopy(formInfo, rpOrder);
			BeanCopier.staticCopy(applyInfo, rpOrder);
			
			rpOrder.setSourceForm(SourceFormEnum.CAPITAL_APPROPRIATION);
			if (formInfo.getFormCode() == FormCodeEnum.FCAPITAL_APPROPRIATION_APPLY_COMP) {
				rpOrder.setSourceForm(SourceFormEnum.CAPITAL_APPROPRIATION_COMP);
			}
			rpOrder.setSourceFormId(String.valueOf(formInfo.getFormId()));
			rpOrder.setSourceFormSys(SystemEnum.PM);
			rpOrder.setIsSimple(applyInfo.getIsSimple());
			
			//财务确认信息
			FFinanceAffirmInfo confirmInfo = applyInfo.getFinanceAffirmInfo();
			List<FFinanceAffirmDetailInfo> confirmFeeList = confirmInfo.getFeeList();
			
			rpOrder.setAttach(confirmInfo.getAttach());
			rpOrder.setRemark(confirmInfo.getRemark());
			
			String applyTitle = "资金划付";
			if (StringUtil.equals(applyInfo.getIsSimple(), "IS")) {
				applyTitle = "被扣划冻结";
			}
			
			//理财项目相关划付
			if (applyInfo.getProjectType() == FCapitalAppropriationApplyTypeEnum.FINANCIAL_PRODUCT) {
				
				if (applyInfo.getOutBizNo() > 0) { //理财产品回购
					ProjectFinancialInfo fp = financialProjectService.queryByCode(applyInfo
						.getProjectCode());
					if (fp != null) {
						rpOrder.setProjectName(fp.getProductName());
						rpOrder.setProductName(fp.getProductName());
					}
					ProjectFinancialTradeTansferInfo trade = financialProjectTransferService
						.queryTradeByApplyId(applyInfo.getOutBizNo());
					if (trade != null) {
						rpOrder.setTransferName(trade.getTransferTo());
					}
					//rpOrder.setRemark("理财产品回购划付");
				} else { //理财产品购买
					FProjectFinancialInfo fp = financialProjectSetupService
						.queryByProjectCode(applyInfo.getProjectCode());
					if (fp != null) {
						rpOrder.setProjectName(fp.getProductName());
						rpOrder.setProductName(fp.getProductName());
					}
					//rpOrder.setRemark("理财产品购买划付");
				}
				
			} else { //授信业务相关划付
				project = projectService.queryByCode(applyInfo.getProjectCode(), false);
				if (project != null) {
					rpOrder.setContractNo(project.getContractNo());
					ProjectContractItemInfo contract = projectContractService
						.findByContractCode(project.getContractNo());
					if (contract != null) {
						rpOrder.setContractName(contract.getContractName());
					}
					rpOrder.setCustomerId(project.getCustomerId());
					rpOrder.setCustomerName(project.getCustomerName());
				}
			}
			
			//存出保证金
			FFinanceAffirmDetailInfo depositFee = null;
			//代偿金额
			Money compAmount = Money.zero();
			Date compTime = null; //代偿时间
			//资金费用明细
			List<ReceiptPaymentFormFeeOrder> feeOrderList = Lists.newArrayList();
			for (FFinanceAffirmDetailInfo fee : confirmFeeList) {
				PaymentMenthodEnum feeType = fee.getMenthodType();
				ReceiptPaymentFormFeeOrder feeOrder = new ReceiptPaymentFormFeeOrder();
				feeOrder.setOccurTime(fee.getPayTime());
				feeOrder.setFeeType(SubjectCostTypeEnum.getByPayFeeType(feeType));
				feeOrder.setAmount(fee.getPayAmount());
				feeOrder.setAccount(fee.getPayeeAccountName());
				feeOrder.setDepositAccount(fee.getDepositAccount());
				feeOrder.setDepositRate(fee.getMarginRate());
				feeOrder.setDepositTime(fee.getDepositTime());
				feeOrder.setDepositPeriod(fee.getPeriod());
				feeOrder.setPeriodUnit(fee.getPeriodUnit());
				feeOrder.setAttach(fee.getAttach());
				//申请单费用明细
				FCapitalAppropriationApplyFeeInfo applyFee = null;
				for (FCapitalAppropriationApplyFeeInfo af : detailFees) {
					if (af.getId() == fee.getDetailId()) {
						applyFee = af;
						break;
					}
				}
				if (StringUtil.isBlank(applyFee.getComType())
					|| StringUtil.equals(applyFee.getComType(), "被扣划")) {
					
					feeOrderList.add(feeOrder);
					
					if (feeType == PaymentMenthodEnum.COMPENSATORY_PRINCIPAL
						|| feeType == PaymentMenthodEnum.COMPENSATORY_INTEREST
						|| feeType == PaymentMenthodEnum.COMPENSATORY_PENALTY
						|| feeType == PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES
						|| feeType == PaymentMenthodEnum.COMPENSATORY_OTHER) {
						if (feeType == PaymentMenthodEnum.COMPENSATORY_PRINCIPAL)
							compTime = fee.getPayTime();
						compAmount.addTo(fee.getPayAmount());
					}
					
					if (feeType == PaymentMenthodEnum.DEPOSIT_PAID) {
						depositFee = fee;
					}
				}
			}
			
			rpOrder.setFeeOrderList(feeOrderList);
			if (ListUtil.isNotEmpty(feeOrderList))
				receiptPaymentFormServiceClient.add(rpOrder);
			
			//同步预测数据
			if (project != null && compAmount.greaterThan(ZERO_MONEY)) {
				
				//查询预测规则  没查询到用默认规则
				SysForecastParamAllInfo rule = new SysForecastParamAllInfo();
				SysForecastParamResult result = forecastServiceClient.findAll();
				if (result != null && result.isSuccess()) {
					rule = result.getParamAllInfo();
				}
				TimeUnitEnum forcastTimeType = rule.getInDckshTimeType();
				int forcastTime = NumberUtil.parseInt(rule.getInDckshTime());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(compTime);
				if (forcastTimeType == TimeUnitEnum.YEAR) {
					calendar.add(Calendar.YEAR, forcastTime);
				} else if (forcastTimeType == TimeUnitEnum.MONTH) {
					calendar.add(Calendar.MONTH, forcastTime);
				} else if (forcastTimeType == TimeUnitEnum.DAY) {
					calendar.add(Calendar.DAY_OF_MONTH, forcastTime);
				}
				ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
				forecastAccountOrder.setForecastStartTime(calendar.getTime());
				forecastAccountOrder.setAmount(compAmount);
				forecastAccountOrder.setForecastMemo("代偿款收回（" + project.getProjectCode() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.IN_DCKSH);
				forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
				forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
												+ ForecastFeeTypeEnum.COMPBACK.code() + "_"
												+ applyInfo.getApplyId());
				forecastAccountOrder.setSystemForm(SystemEnum.PM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
				forecastAccountOrder.setUsedDeptName(project.getDeptName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.COMPBACK);
				forecastAccountOrder.setProjectCode(project.getProjectCode());
				forecastAccountOrder.setCustomerId(project.getCustomerId());
				forecastAccountOrder.setCustomerName(project.getCustomerName());
				logger.info("代偿款收回资金流入预测,projectCode：{}, forecastAccountOrder：{} ",
					project.getProjectCode(), forecastAccountOrder);
				forecastServiceClient.add(forecastAccountOrder);
			}
			
			//存出保证金预测
			if (project != null && depositFee != null) {
				String forcastTimeType = depositFee.getPeriodUnit();
				int forcastTime = depositFee.getPeriod();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(depositFee.getDepositTime());
				if (StringUtil.equals(forcastTimeType, "年")) {
					calendar.add(Calendar.YEAR, forcastTime);
				} else if (StringUtil.equals(forcastTimeType, "月")) {
					calendar.add(Calendar.MONTH, forcastTime);
				} else if (StringUtil.equals(forcastTimeType, "日")) {
					calendar.add(Calendar.DAY_OF_MONTH, forcastTime);
				}
				
				ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
				forecastAccountOrder.setForecastStartTime(calendar.getTime());
				forecastAccountOrder.setAmount(depositFee.getPayAmount());
				forecastAccountOrder.setForecastMemo("存出保证金划回（" + project.getProjectCode() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.IN_DEPOSITS_DRAW_BACK);
				forecastAccountOrder.setFundDirection(FundDirectionEnum.IN);
				forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
												+ ForecastFeeTypeEnum.DEPOSITS_DRAW_BACK.code()
												+ "_" + depositFee.getId());
				forecastAccountOrder.setSystemForm(SystemEnum.PM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
				forecastAccountOrder.setUsedDeptName(project.getDeptName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.DEPOSITS_DRAW_BACK);
				forecastAccountOrder.setProjectCode(project.getProjectCode());
				forecastAccountOrder.setCustomerId(project.getCustomerId());
				forecastAccountOrder.setCustomerName(project.getCustomerName());
				logger.info("存出保证金划回资金流入预测,projectCode：{}, forecastAccountOrder：{} ",
					project.getProjectCode(), forecastAccountOrder);
				forecastServiceClient.add(forecastAccountOrder);
			}
			
			//更新资金预测
			try {
				
				for (FFinanceAffirmDetailDO fee : feeList) {
					//查询相关费用是否有预测
					ForecastFeeTypeEnum ffeeType = ForecastFeeTypeEnum.getByOutFees(fee
						.getFeeType());
					if (ffeeType == null || ZERO_MONEY.greaterThan(fee.getPayAmount()))
						continue;
					
					//申请单费用明细
					FCapitalAppropriationApplyFeeInfo applyFee = null;
					if (detailFees != null) {
						for (FCapitalAppropriationApplyFeeInfo af : detailFees) {
							if (af.getId() == fee.getDetailId()) {
								applyFee = af;
								break;
							}
						}
					}
					if (StringUtil.isNotBlank(applyFee.getComType())
						&& !StringUtil.equals(applyFee.getComType(), "被扣划")) {
						continue;
					}
					
					PaymentMenthodEnum feeType = PaymentMenthodEnum.getByCode(fee.getFeeType());
					ForecastAccountChangeOrder changeForecastOrder = new ForecastAccountChangeOrder();
					changeForecastOrder.setFeeType(ffeeType);
					changeForecastOrder.setProjectCode(confirmInfo.getProjectCode());
					changeForecastOrder.setAmount(fee.getPayAmount());
					changeForecastOrder.setUserId(formInfo.getUserId());
					changeForecastOrder.setUserAccount(formInfo.getUserAccount());
					changeForecastOrder.setUserName(formInfo.getUserName());
					if (feeType == null) {
						changeForecastOrder.setForecastMemo(applyTitle
															+ fee.getPayAmount().toStandardString()
															+ "元");
					} else {
						changeForecastOrder.setForecastMemo(applyTitle + "[ "
															+ feeType.getMessage() + " ] "
															+ fee.getPayAmount().toStandardString()
															+ "元");
					}
					forecastServiceClient.change(changeForecastOrder);
				}
				
			} catch (Exception e) {
				logger.error(applyTitle + "更新资金预测数据出错：{}", e);
			}
			
		} catch (Exception e) {
			logger.error("发送资金划付/被代扣冻结数据到资金系统出错：{}", e);
		}
	}
	
	private void asynchronousDataToFundSys(long formId, FCapitalAppropriationApplyInfo applyInfo,
											List<FFinanceAffirmDetailDO> detailList) {
		FormInfo formInfo = formService.findByFormId(formId);
		logger.info("添加资金划付数据到资金系统异步任务");
		asynchronousTaskJob.addAsynchronousService(this, new Object[] { formInfo, applyInfo,
																		detailList });
	}
	
	private void saveAffirm(Map<String, Object> map) {
		FFinanceAffirmOrder order = new FFinanceAffirmOrder();
		//final Date now = FcsPmDomainHolder.get().getSysDate();
		order.setAffirmFormType(FinanceAffirmTypeEnum.CAPITAL_APPROPROATION_APPLY.code());
		String formId = map.get("formId") == null ? null : (String) map.get("formId");
		String remark = map.get("remark") == null ? null : (String) map.get("remark");
		String amount = map.get("amount1") == Money.zero() ? null : (String) map.get("amount1");
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
		int length = Integer.parseInt((String) map.get("length"));
		List<FFinanceAffirmDetailOrder> detailOrders = Lists.newArrayList();
		for (int i = 0; i < length; i++) {
			FFinanceAffirmDetailOrder detailDO = new FFinanceAffirmDetailOrder();
			detailDO.setFeeType(map.get("feeType" + i) == null ? null : (String) map.get("feeType"
																							+ i));
			detailDO.setDetailId(map.get("detailId" + i) == null ? 0 : Long.parseLong((String) map
				.get("detailId" + i)));
			detailDO.setPayAmount(map.get("payAmount" + i) == Money.zero() ? null : new Money(
				(String) map.get("payAmount" + i)));
			detailDO.setReturnCustomerAmount(map.get("payAmount" + i) == Money.zero() ? null
				: new Money((String) map.get("payAmount" + i)));
			detailDO.setPayTime(map.get("payTime" + i) == null ? null : DateUtil.parse((String) map
				.get("payTime" + i)));
			detailDO.setDepositTime(map.get("depositTime" + i) == null ? null : DateUtil
				.parse((String) map.get("depositTime" + i)));
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
			//重新分割存入保证金
			//			if (detailDO.getFeeType() != null
			//				&& detailDO.getFeeType().equals(PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code())) {
			//				FCapitalAppropriationApplyFeeDO feeDO = FCapitalAppropriationApplyFeeDAO
			//					.findById(detailDO.getDetailId());
			//				if (feeDO != null && StringUtil.isNotEmpty(feeDO.getFinanceAffirmDetailId())) {
			//					setChargeCapitalApproproation1(projectCode, detailDO.getPayAmount(),
			//						detailDO.getDetailId(), feeDO.getFinanceAffirmDetailId());
			//				}
			//			}
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
			attachOrder
				.setModuleType(CommonAttachmentTypeEnum.CAPITAL_APPROPRIATION_FINANCE_AFFIRM);
			attachOrder.setUploaderId(currentUser.getUserId());
			attachOrder.setUploaderName(currentUser.getUserName());
			attachOrder.setUploaderAccount(currentUser.getUserAccount());
			attachOrder.setPath(attach);
			attachOrder.setRemark("资金划付申请单-财务确认附件");
			commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
		}
		if (ListUtil.isNotEmpty(detailOrders)) {
			for (FFinanceAffirmDetailOrder detailOrder : detailOrders) {
				if (StringUtil.isNotEmpty(detailOrder.getAttach())) {
					CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
					attachOrder.setProjectCode(order.getProjectCode());
					attachOrder
						.setBizNo("PM_" + formId + "_" + detailOrder.getDetailId() + "_CWQR");
					attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
					attachOrder
						.setModuleType(CommonAttachmentTypeEnum.CAPITAL_APPROPRIATION_FINANCE_AFFIRM_DETAIL);
					attachOrder.setUploaderId(currentUser.getUserId());
					attachOrder.setUploaderName(currentUser.getUserName());
					attachOrder.setUploaderAccount(currentUser.getUserAccount());
					attachOrder.setPath(detailOrder.getAttach());
					if (detailOrder.getFeeType() != null) {
						attachOrder.setRemark("资金划付申请单-财务确认附件("
												+ PaymentMenthodEnum.getByCode(
													detailOrder.getFeeType()).message() + ")");
					}
					commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
				}
			}
		}
	}
	
	private void setChargeCapitalApproproation(Long detailId, String affirmDetailIds) {
		ChargeCapitalOrder order = new ChargeCapitalOrder();
		List<ChargeNoticeCapitalApproproationOrder> itemOrders = Lists.newArrayList();
		order.setType(FinanceAffirmTypeEnum.CAPITAL_APPROPROATION_APPLY);
		order.setDetailId(detailId);
		if (affirmDetailIds != null) {
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
		
	}
	
	/**
	 * 保存到收费资金划付关联表
	 * @param
	 */
	
	private void setChargeCapitalApproproation1(String projectCode, Money payAmount, Long detailId,
												String affirmDetailIds) {
		String affirmIds = "";
		
		if (StringUtil.isNotBlank(affirmDetailIds)) {
			String firms[] = affirmDetailIds.split(";");
			List<Long> ids = Lists.newArrayList();
			for (String firm : firms) {
				if (StringUtil.isNotBlank(firm)) {
					Long id = Long.parseLong(firm.split(",")[1]);
					ids.add(id);
				}
			}
			//删除
			chargeNoticeCapitalApproproationDAO.deleteByTypeAndDetailId(
				FinanceAffirmTypeEnum.CAPITAL_APPROPROATION_APPLY.code(), detailId);
			
			ProjectChargePayQueryOrder queryOrder = new ProjectChargePayQueryOrder();
			queryOrder.setPayIdList(ids);
			queryOrder.setProjectCode(projectCode);
			queryOrder.setAffirmFormType("CHARGE_NOTIFICATION");
			queryOrder.setFeeType("GUARANTEE_DEPOSIT");
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
			if (payAmount.greaterThan(total)) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
					"所选存入保证金金额不足，不能提交!");
			} else {
				String detailIds = "";
				if (payAmount.equals(total)) {//如果相等说明所有的都用完了
					if (ListUtil.isNotEmpty(baseBatchResult.getPageList())) {
						for (ProjectChargePayInfo payInfo : baseBatchResult.getPageList()) {
							detailIds = detailIds + payInfo.getReturnCustomerAmount() + ","
										+ payInfo.getPayId() + ";";
						}
					}
					affirmDetailIds = detailIds.substring(0, detailIds.length() - 1);
				} else if (total.greaterThan(payAmount)) {//小于就重新分摊
					Money tempAmount = payAmount;
					for (ProjectChargePayInfo payInfo : baseBatchResult.getPageList()) {
						Money tempAmount2 = tempAmount;
						tempAmount = tempAmount.subtract(payInfo.getReturnCustomerAmount());
						if (tempAmount.greaterThan(Money.zero()) || tempAmount.equals(Money.zero())) {//减完了的
							if (payInfo.getReturnCustomerAmount().equals(Money.zero())) {
								continue;
							}
							detailIds = detailIds + payInfo.getReturnCustomerAmount() + ","
										+ payInfo.getPayId() + ";";
						} else {//没用完直接存剩下的
							detailIds = detailIds + tempAmount2 + "," + payInfo.getPayId() + ";";
							break;
						}
					}
					affirmDetailIds = detailIds.substring(0, detailIds.length() - 1);
					affirmIds = affirmDetailIds;
				}
			}
		}
		ChargeCapitalOrder order = new ChargeCapitalOrder();
		List<ChargeNoticeCapitalApproproationOrder> itemOrders = Lists.newArrayList();
		order.setType(FinanceAffirmTypeEnum.CAPITAL_APPROPROATION_APPLY);
		order.setDetailId(detailId);
		if (affirmIds != null) {//affirmIds  收费通知单，财务确认的id
			String firms[] = affirmIds.split(";");
			for (String firm : firms) {
				if (StringUtil.isNotBlank(firm)) {
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
	}
	
	// 发送消息
	public void sendMessageForFinanceProduct(FormInfo formInfo, Money amount, Money actualPayAmount) {
		
		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
			.getFormId());
		FProjectFinancialDO financialDO = FProjectFinancialDAO.findByProjectCode(applyDO
			.getProjectCode());
		MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
		StringBuffer sb = new StringBuffer();
		//				String url = CommonUtil
		//					.getRedirectUrl("/projectMg/fCapitalAppropriationApply/viewCapitalAppropriationApply.htm")
		//								+ "?formId=" + formId;
		sb.append("项目编号:" + financialDO.getProjectCode());
		sb.append("，产品名称：" + financialDO.getProductName());
		sb.append("，申请划付金额：" + amount.toStandardString() + "元，实际付款金额："
					+ actualPayAmount.toStandardString() + "元，资金划付申请单审核已通过。");
		//				sb.append("<a href='" + url + "'>查看详情</a>");
		String content = sb.toString();
		messageOrder.setMessageContent(content);
		List<SimpleUserInfo> sendUserList = new ArrayList<SimpleUserInfo>();
		SimpleUserInfo user = new SimpleUserInfo();
		
		user.setUserAccount(formInfo.getUserAccount());
		user.setUserId(formInfo.getUserId());
		user.setUserName(formInfo.getUserName());
		sendUserList.add(user);
		messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
			.toArray(new SimpleUserInfo[sendUserList.size()]));
		siteMessageService.addMessageInfo(messageOrder);
		
	}
	
	// 发送消息
	public void sendMessageForm(FormInfo formInfo) {
		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
			.getFormId());
		FProjectFinancialDO financialDO = null;
		//		ProjectFinancialDO financialDO = null;
		if (FCapitalAppropriationApplyTypeEnum.FINANCIAL_PRODUCT.code().equals(
			applyDO.getProjectType())) {
			financialDO = FProjectFinancialDAO.findByProjectCode(applyDO.getProjectCode());
		}
		
		MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
		StringBuffer sb = new StringBuffer();
		//				String url = CommonUtil
		//					.getRedirectUrl("/projectMg/fCapitalAppropriationApply/viewCapitalAppropriationApply.htm")
		//								+ "?formId=" + formId;
		sb.append("项目编号:" + applyDO.getProjectCode());
		if (financialDO == null) {
			sb.append("，项目名称:" + applyDO.getProjectName());
		} else {
			sb.append("，产品名称:" + financialDO.getProductName());
		}
		sb.append("，有一笔资金划付申请单审核通过，请及时到资金管理系统做账务处理。");
		//				sb.append("<a href='" + url + "'>查看详情</a>");
		String content = sb.toString();
		messageOrder.setMessageContent(content);
		List<SimpleUserInfo> sendUserList = new ArrayList<SimpleUserInfo>();
		
		String sysParamValueCWYFG = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_CWYFG_ROLE_NAME.code()); // 财务应付岗 参数
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
