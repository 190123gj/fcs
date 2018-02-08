package com.born.fcs.fm.biz.service.receipt;

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
import com.born.fcs.fm.ws.info.receipt.FormReceiptFeeInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptInfo;
import com.born.fcs.fm.ws.order.bank.BankTradeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.common.UpdateReceiptPaymentFormStatusOrder;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.receipt.ReceiptApplyService;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("receiptApplyProcessService")
public class ReceiptApplyProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	ReceiptApplyService receiptApplyService;
	
	@Autowired
	ReceiptPaymentFormService receiptPaymentFormService;
	
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	
	@Autowired
	BankMessageService bankMessageService;
	
	/**
	 * 流程通过 同步信息到财务系统生成凭证号
	 */
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		try {
			
			FormReceiptInfo receiptInfo = receiptApplyService.findReceiptByFormId(
				formInfo.getFormId(), true);
			logger.info("完成收款单处理：{}", receiptInfo);
			
			//修改源单据状态为已经处理
			if (receiptInfo.getFormSource() == FormSourceEnum.FORM) {
				UpdateReceiptPaymentFormStatusOrder updateOrder = new UpdateReceiptPaymentFormStatusOrder();
				updateOrder.setSourceForm(receiptInfo.getSourceForm());
				updateOrder.setSourceFormId(receiptInfo.getSourceFormId());
				updateOrder.setStatus(ReceiptPaymentFormStatusEnum.PROCESSED);
				receiptPaymentFormService.updateStatus(updateOrder);
			}
			
			//同步至财务系统
			receiptApplyService.sync2FinancialSys(receiptInfo);
			
			//增加相应银行账户金额
			List<FormReceiptFeeInfo> feeList = receiptInfo.getFeeList();
			if (ListUtil.isNotEmpty(feeList)) {
				logger.info("收款单审核通过增加相应银行账号金额：{}", feeList);
				for (FormReceiptFeeInfo feeInfo : feeList) {
					BankTradeOrder tradeOrder = new BankTradeOrder();
					tradeOrder.setAccountNo(feeInfo.getAccount());
					tradeOrder.setAmount(feeInfo.getAmount());
					tradeOrder.setFundDirection(FundDirectionEnum.IN);
					if (feeInfo.getReceiptDate() != null) {
						tradeOrder.setTradeTime(feeInfo.getReceiptDate());//收款时间
					} else {
						tradeOrder.setTradeTime(receiptInfo.getReceiptDate());//收款时间
					}
					tradeOrder.setOutBizNo(receiptInfo.getBillNo());
					tradeOrder.setOutBizDetailNo(String.valueOf(feeInfo.getId()));
					tradeOrder.setRemark("收款单 " + receiptInfo.getBillNo() + "："
											+ feeInfo.getFeeType().message());
					bankMessageService.trade(tradeOrder);
				}
			}
			
			//查询待处理的单据 20161209 添加还款时间单据
			if (StringUtil.isNotBlank(receiptInfo.getSourceFormId())
				&& receiptInfo.getSourceForm() != null
				&& receiptInfo.getSourceFormId().indexOf("_") < 0) {
				ReceiptPaymentFormInfo rpInfo = receiptPaymentFormService.queryBySourceFormId(
					receiptInfo.getSourceForm(), receiptInfo.getSourceFormId(), false);
				if (rpInfo != null && SourceFormEnum.INNER_LOAN_IN == rpInfo.getSourceForm()) {
					ReceiptPaymentFormOrder receiptPaymentOrder = new ReceiptPaymentFormOrder();
					MiscUtil.copyPoObject(receiptPaymentOrder, rpInfo);
					receiptPaymentOrder.setSourceForm(SourceFormEnum.INNER_LOAN);
					receiptPaymentOrder
						.setSourceFormId(receiptPaymentOrder.getSourceFormId() + "_");
					// 增加费用明细
					List<ReceiptPaymentFormFeeOrder> feeOrderList = new ArrayList<ReceiptPaymentFormFeeOrder>();
					for (FormReceiptFeeInfo fInfo : receiptInfo.getFeeList()) {
						ReceiptPaymentFormFeeOrder fOrder = new ReceiptPaymentFormFeeOrder();
						// 新生成的单据只统计内部借款单信息
						if (SubjectCostTypeEnum.INNER_ACCOUNT_LENDING != fInfo.getFeeType()) {
							continue;
						}
						fOrder.setFeeType(SubjectCostTypeEnum.INTERNAL_FUND_LENDING);
						fOrder.setAmount(fInfo.getAmount());
						fOrder.setRemark("内部借款单借款后续单据！");
						feeOrderList.add(fOrder);
					}
					if (ListUtil.isNotEmpty(feeOrderList)) {
						receiptPaymentOrder.setFeeOrderList(feeOrderList);
						receiptPaymentFormService.add(receiptPaymentOrder);
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("完成收款单处理出错：{}", e);
		}
		
	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		try {
			FormReceiptInfo receiptInfo = receiptApplyService.findReceiptByFormId(
				formInfo.getFormId(), false);
			logger.info("删除收款单处理：{}", receiptInfo);
			if (receiptInfo.getFormSource() == FormSourceEnum.FORM) {
				UpdateReceiptPaymentFormStatusOrder updateOrder = new UpdateReceiptPaymentFormStatusOrder();
				updateOrder.setSourceForm(receiptInfo.getSourceForm());
				updateOrder.setSourceFormId(receiptInfo.getSourceFormId());
				updateOrder.setStatus(ReceiptPaymentFormStatusEnum.NOT_PROCESS);
				receiptPaymentFormService.updateStatus(updateOrder);
			}
		} catch (Exception e) {
			logger.error("删除收款单处理出错：{}", e);
		}
		
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			FormReceiptInfo receiptInfo = receiptApplyService.findReceiptByFormId(
				formInfo.getFormId(), false);
			logger.info("终止收款单处理：{}", receiptInfo);
			if (receiptInfo.getFormSource() == FormSourceEnum.FORM) {
				UpdateReceiptPaymentFormStatusOrder updateOrder = new UpdateReceiptPaymentFormStatusOrder();
				updateOrder.setSourceForm(receiptInfo.getSourceForm());
				updateOrder.setSourceFormId(receiptInfo.getSourceFormId());
				updateOrder.setStatus(ReceiptPaymentFormStatusEnum.NOT_PROCESS);
				receiptPaymentFormService.updateStatus(updateOrder);
			}
		} catch (Exception e) {
			logger.error("终止收款单处理出错：{}", e);
		}
		
	}
	
	@Override
	public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			FormReceiptInfo receiptInfo = receiptApplyService.findReceiptByFormId(
				formInfo.getFormId(), false);
			logger.info("终止收款单处理：{}", receiptInfo);
			if (receiptInfo.getFormSource() == FormSourceEnum.FORM) {
				UpdateReceiptPaymentFormStatusOrder updateOrder = new UpdateReceiptPaymentFormStatusOrder();
				updateOrder.setSourceForm(receiptInfo.getSourceForm());
				updateOrder.setSourceFormId(receiptInfo.getSourceFormId());
				updateOrder.setStatus(ReceiptPaymentFormStatusEnum.NOT_PROCESS);
				receiptPaymentFormService.updateStatus(updateOrder);
			}
		} catch (Exception e) {
			logger.error("终止收款单处理出错：{}", e);
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
			FormReceiptInfo info = receiptApplyService.findReceiptByFormId(form.getFormId(), false);
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
