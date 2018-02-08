package com.born.fcs.pm.biz.service.fund;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.service.transfer.AssetsTransferApplicationService;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.pm.biz.job.AsynchronousTaskJob;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.biz.service.common.AsynchronousService;
import com.born.fcs.pm.dal.dataobject.FBrokerBusinessDO;
import com.born.fcs.pm.dal.dataobject.FChargeNotificationDO;
import com.born.fcs.pm.dal.dataobject.FChargeNotificationFeeDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.ChargeBasisEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.releaseproject.ReleaseProjectService;
import com.google.common.collect.Maps;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

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
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(fChargeNotificationDO.getCustomerName() + "-收费通知单");
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
		try {
			
			fChargeNotificationDO.setStatus(FormStatusEnum.APPROVAL.code());
			final Date now = FcsPmDomainHolder.get().getSysDate();
			fChargeNotificationDO.setChargeTime(now);
			fChargeNotificationDAO.update(fChargeNotificationDO);
			
			//收费依据是项目 更新project表里的值
			if (StringUtil.equals(fChargeNotificationDO.getChargeBasis(),
				ChargeBasisEnum.PROJECT.code())) {
				ProjectDO projectDO = projectDAO.findByProjectCode(fChargeNotificationDO
					.getProjectCode());
				Money deposit = new Money(0, 0);
				Money back = new Money(0, 0);
				Money withdrawal = new Money(0, 0);
				Money chargedAmount = projectDO.getChargedAmount();
				for (FChargeNotificationFeeDO feeDO : feeDOs) {
					if (feeDO.getFeeType().equals(FeeTypeEnum.GUARANTEE_DEPOSIT.code())) {//存入保证金
						deposit = deposit.add(feeDO.getChargeAmount());
					}
					if (feeDO.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK.code())) {//存出保证金划回
						back = back.add(feeDO.getChargeAmount());
					}
					if (feeDO.getFeeType().equals(
						FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL.code())) {//委贷本金回收
						withdrawal = withdrawal.add(feeDO.getChargeAmount());
					}
					chargedAmount = chargedAmount.add(feeDO.getChargeAmount());//更新project表里的已收费金额
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
				projectDAO.update(projectDO);
			} else {//经纪业务收费
				FBrokerBusinessDO applyDO = FBrokerBusinessDAO
					.findByProjectCode(fChargeNotificationDO.getProjectCode());
				Money chargeAmount = applyDO.getChargedAmount();
				for (FChargeNotificationFeeDO feeDO : feeDOs) {
					chargeAmount = chargeAmount.add(feeDO.getChargeAmount());
				}
				applyDO.setChargedAmount(chargeAmount);
				FBrokerBusinessDAO.update(applyDO);
			}
		} catch (Exception e) {
			logger.error("收费流程通过处理出错：{}", e);
		}
		
		//XXX 异步同步单据到资金系统
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
	public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO.findByFormId(formInfo
			.getFormId());
		fChargeNotificationDO.setStatus(FormStatusEnum.BACK.code());
		fChargeNotificationDAO.update(fChargeNotificationDO);
	}
	
	/**
	 * line 188 //XXX 异步同步单据到资金系统
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
			ReceiptPaymentFormOrder rpOrder = new ReceiptPaymentFormOrder();
			BeanCopier.staticCopy(formInfo, rpOrder);
			BeanCopier.staticCopy(cn, rpOrder);
			rpOrder.setContractNo(cn.getContractCode());
			rpOrder.setSourceForm(SourceFormEnum.CHARGE_NOTIFICATION);
			rpOrder.setSourceFormId(formInfo.getFormId());
			rpOrder.setSourceFormSys(SystemEnum.PM);
			if (cn.getProjectCode() != null && cn.getProjectCode().startsWith("JJ")) {
				rpOrder.setProjectName("经纪业务");
			}
			List<ReceiptPaymentFormFeeOrder> feeOrderList = Lists.newArrayList();
			for (FChargeNotificationFeeDO fee : feeDOs) {
				ReceiptPaymentFormFeeOrder feeOrder = new ReceiptPaymentFormFeeOrder();
				feeOrder.setFeeType(SubjectCostTypeEnum.getByChargeFeeType(FeeTypeEnum
					.getByCode(fee.getFeeType())));
				feeOrder.setAmount(fee.getChargeAmount());
				if (StringUtil.isNotBlank(fee.getRemark())) {
					feeOrder.setRemark(fee.getRemark());
				} else {
					String remark = "收费基数：" + MoneyUtil.getMoneyByw(fee.getChargeBase()) + "万元"
									+ "，收费费率：" + fee.getChargeRate() + "%";
					if (fee.getStartTime() != null && fee.getEndTime() != null) {
						remark += "，计费期间：" + DateUtil.dtSimpleFormat(fee.getStartTime()) + "至"
									+ DateUtil.dtSimpleFormat(fee.getEndTime());
					}
					feeOrder.setRemark(remark);
				}
				feeOrderList.add(feeOrder);
			}
			rpOrder.setFeeOrderList(feeOrderList);
			receiptPaymentFormServiceClient.add(rpOrder);
		} catch (Exception e) {
			logger.error("发送收费数据到资金系统出错：{}", e);
		}
	}
}
