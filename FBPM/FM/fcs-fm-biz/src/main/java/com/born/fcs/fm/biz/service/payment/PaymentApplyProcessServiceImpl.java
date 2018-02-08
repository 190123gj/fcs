package com.born.fcs.fm.biz.service.payment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.biz.job.AsynchronousTaskJob;
import com.born.fcs.fm.biz.service.base.BaseProcessService;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.bpm.result.WorkflowResult;
import com.born.fcs.fm.ws.enums.FormSourceEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.ReceiptPaymentFormStatusEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.info.common.ReceiptPaymentFormInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentFeeInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentInfo;
import com.born.fcs.fm.ws.order.bank.BankTradeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.common.UpdateReceiptPaymentFormStatusOrder;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.payment.PaymentApplyService;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("paymentApplyProcessService")
public class PaymentApplyProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	PaymentApplyService paymentApplyService;
	
	@Autowired
	ReceiptPaymentFormService receiptPaymentFormService;
	
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	
	@Autowired
	BankMessageService bankMessageService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		List<FlowVarField> fields = new ArrayList<FlowVarField>();
		try {
			FlowVarField isSimple = new FlowVarField();
			isSimple.setVarName("isSimple");
			isSimple.setVarType(FlowVarTypeEnum.STRING);
			isSimple.setVarVal("0");
			FormPaymentInfo paymentInfo = paymentApplyService.findPaymentByFormId(
				formInfo.getFormId(), false);
			if (paymentInfo != null && StringUtil.equals("IS", paymentInfo.getIsSimple())) {
				isSimple.setVarVal("1");
			}
			fields.add(isSimple);
		} catch (Exception e) {
			logger.error("设置流程参数出错:{}", e);
		}
		return fields;
	}
	
	/**
	 * 流程通过 同步信息到财务系统生成凭证号
	 */
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		try {
			FormPaymentInfo paymentInfo = paymentApplyService.findPaymentByFormId(
				formInfo.getFormId(), true);
			logger.info("完成付款单处理：{}", paymentInfo);
			
			if (paymentInfo.getFormSource() == FormSourceEnum.FORM) {
				//修改源单据状态为已经处理
				UpdateReceiptPaymentFormStatusOrder updateOrder = new UpdateReceiptPaymentFormStatusOrder();
				updateOrder.setSourceForm(paymentInfo.getSourceForm());
				updateOrder.setSourceFormId(paymentInfo.getSourceFormId());
				updateOrder.setStatus(ReceiptPaymentFormStatusEnum.PROCESSED);
				receiptPaymentFormService.updateStatus(updateOrder);
			}
			//同步至财务系统
			paymentApplyService.sync2FinancialSys(paymentInfo);
			
			//增加相应银行账户金额
			List<FormPaymentFeeInfo> feeList = paymentInfo.getFeeList();
			if (ListUtil.isNotEmpty(feeList)) {
				logger.info("付款单审核通过增加相应银行账号金额：{}", feeList);
				for (FormPaymentFeeInfo feeInfo : feeList) {
					//收款
					if (StringUtil.isNotBlank(feeInfo.getReceiptAccount())) {
						BankTradeOrder inOrder = new BankTradeOrder();
						inOrder.setAccountNo(feeInfo.getReceiptAccount());
						inOrder.setAmount(feeInfo.getAmount());
						inOrder.setFundDirection(FundDirectionEnum.IN);
						if (feeInfo.getPaymentDate() != null) {
							inOrder.setTradeTime(feeInfo.getPaymentDate());//付款时间
						} else {
							inOrder.setTradeTime(paymentInfo.getPaymentDate());//付款时间
						}
						inOrder.setOutBizNo(paymentInfo.getBillNo());
						inOrder.setOutBizDetailNo(String.valueOf(feeInfo.getId()));
						inOrder.setRemark("付款单收款账户 " + paymentInfo.getBillNo() + "："
											+ feeInfo.getFeeType().message());
						//银行账号不存在的时候忽略
						inOrder.setIgnoreAccountIfNotExist(true);
						bankMessageService.trade(inOrder);
					}
					
					//付款
					BankTradeOrder outOrder = new BankTradeOrder();
					outOrder.setAccountNo(feeInfo.getPaymentAccount());
					outOrder.setAmount(feeInfo.getAmount());
					outOrder.setFundDirection(FundDirectionEnum.OUT);
					if (feeInfo.getPaymentDate() != null) {
						outOrder.setTradeTime(feeInfo.getPaymentDate());//付款时间
					} else {
						outOrder.setTradeTime(paymentInfo.getPaymentDate());//付款时间
					}
					outOrder.setOutBizNo(paymentInfo.getBillNo());
					outOrder.setOutBizDetailNo(String.valueOf(feeInfo.getId()));
					outOrder.setRemark("付款单付款账户 " + paymentInfo.getBillNo() + "："
										+ feeInfo.getFeeType().message());
					bankMessageService.trade(outOrder);
				}
				
			}
			
			//查询待处理的单据 20161209 添加还款时间单据
			if (StringUtil.isNotBlank(paymentInfo.getSourceFormId())
				&& paymentInfo.getSourceForm() != null
				&& paymentInfo.getSourceFormId().indexOf("_") < 0) {
				ReceiptPaymentFormInfo rpInfo = receiptPaymentFormService.queryBySourceFormId(
					paymentInfo.getSourceForm(), paymentInfo.getSourceFormId(), false);
				if (rpInfo != null && SourceFormEnum.INNER_LOAN == rpInfo.getSourceForm()) {
					ReceiptPaymentFormOrder receiptPaymentOrder = new ReceiptPaymentFormOrder();
					MiscUtil.copyPoObject(receiptPaymentOrder, rpInfo);
					receiptPaymentOrder.setSourceForm(SourceFormEnum.INNER_LOAN_IN);
					receiptPaymentOrder
						.setSourceFormId(receiptPaymentOrder.getSourceFormId() + "_");
					// 增加费用明细
					List<ReceiptPaymentFormFeeOrder> feeOrderList = new ArrayList<ReceiptPaymentFormFeeOrder>();
					for (FormPaymentFeeInfo fInfo : paymentInfo.getFeeList()) {
						ReceiptPaymentFormFeeOrder fOrder = new ReceiptPaymentFormFeeOrder();
						// 新生成的单据只统计内部借款单信息
						if (SubjectCostTypeEnum.INTERNAL_FUND_LENDING != fInfo.getFeeType()) {
							continue;
						}
						fOrder.setFeeType(SubjectCostTypeEnum.INNER_ACCOUNT_LENDING);
						fOrder.setAmount(fInfo.getAmount());
						fOrder.setRemark("内部借款单还款后续单据！");
						feeOrderList.add(fOrder);
					}
					if (ListUtil.isNotEmpty(feeOrderList)) {
						receiptPaymentOrder.setFeeOrderList(feeOrderList);
						receiptPaymentFormService.add(receiptPaymentOrder);
					}
				}
			}
		} catch (Exception e) {
			logger.error("完成付款单处理出错：{}", e);
		}
		
	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		try {
			FormPaymentInfo paymentInfo = paymentApplyService.findPaymentByFormId(
				formInfo.getFormId(), false);
			logger.info("删除付款单处理：{}", paymentInfo);
			if (paymentInfo.getFormSource() == FormSourceEnum.FORM) {
				UpdateReceiptPaymentFormStatusOrder updateOrder = new UpdateReceiptPaymentFormStatusOrder();
				updateOrder.setSourceForm(paymentInfo.getSourceForm());
				updateOrder.setSourceFormId(paymentInfo.getSourceFormId());
				updateOrder.setStatus(ReceiptPaymentFormStatusEnum.NOT_PROCESS);
				receiptPaymentFormService.updateStatus(updateOrder);
			}
		} catch (Exception e) {
			logger.error("删除付款单处理出错：{}", e);
		}
		
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			FormPaymentInfo paymentInfo = paymentApplyService.findPaymentByFormId(
				formInfo.getFormId(), false);
			logger.info("终止付款单处理：{}", paymentInfo);
			if (paymentInfo.getFormSource() == FormSourceEnum.FORM) {
				UpdateReceiptPaymentFormStatusOrder updateOrder = new UpdateReceiptPaymentFormStatusOrder();
				updateOrder.setSourceForm(paymentInfo.getSourceForm());
				updateOrder.setSourceFormId(paymentInfo.getSourceFormId());
				updateOrder.setStatus(ReceiptPaymentFormStatusEnum.NOT_PROCESS);
				receiptPaymentFormService.updateStatus(updateOrder);
			}
		} catch (Exception e) {
			logger.error("终止付款单处理出错：{}", e);
		}
		
	}
	
	@Override
	public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			FormPaymentInfo paymentInfo = paymentApplyService.findPaymentByFormId(
				formInfo.getFormId(), false);
			logger.info("终止付款单处理：{}", paymentInfo);
			if (paymentInfo.getFormSource() == FormSourceEnum.FORM) {
				UpdateReceiptPaymentFormStatusOrder updateOrder = new UpdateReceiptPaymentFormStatusOrder();
				updateOrder.setSourceForm(paymentInfo.getSourceForm());
				updateOrder.setSourceFormId(paymentInfo.getSourceFormId());
				updateOrder.setStatus(ReceiptPaymentFormStatusEnum.NOT_PROCESS);
				receiptPaymentFormService.updateStatus(updateOrder);
			}
		} catch (Exception e) {
			logger.error("终止付款单处理出错：{}", e);
		}
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		//自定义待办任务名称
		WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsFmDomainHolder.get().getAttribute(
			"startOrder");
		if (startOrder != null) {
			FormInfo form = order.getFormInfo();
			FormPaymentInfo info = paymentApplyService.findPaymentByFormId(form.getFormId(), false);
			//		单据号：xxx-	费用支付(劳资类)审核流程-吴佳苏
			StringBuilder sb = new StringBuilder();
			sb.append("单据号：");
			sb.append(info.getBillNo());
			sb.append("-");
			sb.append(form.getFormCode().message());
			sb.append("流程-");
			sb.append(form.getUserName());
			startOrder.setCustomTaskName(sb.toString());
		}
		result.setSuccess(true);
		return result;
	}
	
}
