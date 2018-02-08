package com.born.fcs.fm.biz.service.payment;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.fm.biz.job.AsynchronousTaskJob;
import com.born.fcs.fm.biz.service.base.BaseProcessService;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.bpm.result.WorkflowResult;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.info.payment.FormTransferDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormTransferInfo;
import com.born.fcs.fm.ws.order.bank.BankTradeOrder;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.payment.FormTransferService;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.money.Money;

@Service("formTransferProcessService")
public class FormTransferProcessService extends BaseProcessService {

	@Autowired
	FormTransferService formTransferService;

	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;

	@Autowired
	BankMessageService bankMessageService;

	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {

		List<FlowVarField> fields = Lists.newArrayList();

		try {
			FormTransferInfo transferInfo = formTransferService.queryByFormId(formInfo.getFormId());
			
			//金额
			FlowVarField amount = new FlowVarField();
			amount.setVarName("money");
			amount.setVarType(FlowVarTypeEnum.STRING);
			amount.setVarVal(transferInfo.getAmount().toString());
			fields.add(amount);
			
			// FormTravelExpenseInfo te = travelExpenseService
			// .queryByFormId(formInfo.getFormId());
			//
			// //取明细部门
			// Set<String> deptIds = new HashSet<String>();
			// for(FormTravelExpenseDetailInfo detailInfo : te.getDetailList()){
			// if(StringUtil.isBlank(detailInfo.getDeptId())) continue;
			// for(String id : detailInfo.getDeptId().split(",")){
			// if(StringUtil.isNotBlank(id)){
			// deptIds.add(id);
			// }
			// }
			// }
			//
			// String fzrRole = sysParameterServiceClient
			// .getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME
			// .code());
			// String fgfzRole = sysParameterServiceClient
			// .getSysParameterValue(SysParamEnum.SYS_PARAM_FGFZ_ROLE_NAME
			// .code());
			// List<SimpleUserInfo> fzrList = new ArrayList<SimpleUserInfo>();
			// List<SimpleUserInfo> fgfzList = new ArrayList<SimpleUserInfo>();
			// for(String deptId : deptIds){
			// fzrList.addAll(projectRelatedUserService
			// .getRoleDeptUser(Long.valueOf(deptId), fzrRole));
			// fgfzList.addAll(projectRelatedUserService
			// .getRoleDeptUser(Long.valueOf(deptId), fgfzRole));
			// }
			//
			// // 去重
			// Set<String> fzrSet = new HashSet<String>(); // 部门负责人
			// Set<String> fgfzSet = new HashSet<String>(); // 分管副总
			// if (ListUtil.isNotEmpty(fzrList)) {
			// for (SimpleUserInfo fzr : fzrList) {
			// fzrSet.add(String.valueOf(fzr.getUserId()));
			// }
			// }
			//
			// if (ListUtil.isNotEmpty(fgfzList)) {
			// for (SimpleUserInfo fzr : fgfzList) {
			// fgfzSet.add(String.valueOf(fzr.getUserId()));
			// }
			// }
			//
			// String leader = "0";
			// for (String fzr : fzrSet) {
			// leader += fzr + ",";
			// }
			//
			// if (!leader.equals("0"))
			// leader = leader.substring(0, leader.length() - 1);
			//
			// FlowVarField flowVarField = new FlowVarField();
			// flowVarField.setVarName("leader");
			// flowVarField.setVarType(FlowVarTypeEnum.STRING);
			// flowVarField.setVarVal(leader);
			// fields.add(flowVarField);
			//
			// String master = "0";
			// for (String fgfz : fgfzSet) {
			// master += fgfz + ",";
			// }
			//
			// if (!master.equals("0"))
			// master = master.substring(0, master.length() - 1);
			//
			// flowVarField = new FlowVarField();
			// flowVarField.setVarName("master");
			// flowVarField.setVarType(FlowVarTypeEnum.STRING);
			// flowVarField.setVarVal(master);
			// fields.add(flowVarField);

		} catch (Exception e) {
			logger.error("{}", e);
		}

		return fields;
	}

	/**
	 * 流程通过 同步信息到财务系统生成凭证号
	 */
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {

		try {
			FormTransferInfo transferInfo = formTransferService
					.queryByFormId(formInfo.getFormId());
			logger.info("完成转账单处理：{}", transferInfo);

			// 同步至财务系统
			formTransferService.sync2FinancialSys(transferInfo);

			// 异步增加相应银行账户金额
			// logger.info("添加到异步任务执行银行金额付款增加：{}", travelInfo);
			// asynchronousTaskJob.addAsynchronousService(new
			// AsynchronousService() {
			// @Override
			// public void execute(Object[] objects) {
			// FormTravelExpenseInfo travelInfo = (FormTravelExpenseInfo)
			// objects[0];
			// List<FormTravelExpenseDetailInfo> feeList =
			// travelInfo.getDetailList();
			// if (ListUtil.isNotEmpty(feeList)) {
			// for (FormTravelExpenseDetailInfo feeInfo : feeList) {
			// BankTradeOrder tradeOrder = new BankTradeOrder();
			// tradeOrder.setAccountNo(travelInfo.getBankAccount());
			// tradeOrder.setAmount(feeInfo.getTotalAmount());
			// tradeOrder.setFundDirection(FundDirectionEnum.OUT);
			// tradeOrder.setTradeTime(travelInfo.getRawUpdateTime());//付款时间
			// tradeOrder.setOutBizNo(travelInfo.getBillNo());
			// tradeOrder.setOutBizDetailNo(String.valueOf(feeInfo.getDetailId()));
			// tradeOrder.setRemark("差旅费报销");
			// bankMessageService.trade(tradeOrder);
			// }
			// }
			// }
			// }, new Object[] { travelInfo });

			// 加的是一张卡，减的可能是N张卡

			Date now = FcsFmDomainHolder.get().getSysDate();
			if (transferInfo.getAmount().greaterThan(Money.zero())) {
				BankTradeOrder inOrder = new BankTradeOrder();
				inOrder.setAccountNo(transferInfo.getBankAccount());
				inOrder.setAmount(transferInfo.getAmount());
				inOrder.setFundDirection(FundDirectionEnum.IN);
				inOrder.setTradeTime(now);// 付款时间
				inOrder.setOutBizNo(transferInfo.getBillNo());
				inOrder.setOutBizDetailNo(String.valueOf(transferInfo
						.getTransferId()));
				inOrder.setRemark("转账单据 普通处理" + transferInfo.getBillNo() + "：");
				bankMessageService.trade(inOrder);
				for (FormTransferDetailInfo feeInfo : transferInfo
						.getDetailList()) {
					inOrder = new BankTradeOrder();
					inOrder.setAccountNo(feeInfo.getBankAccount());
					inOrder.setAmount(feeInfo.getAmount());
					inOrder.setFundDirection(FundDirectionEnum.OUT);
					inOrder.setTradeTime(now);// 付款时间
					inOrder.setOutBizNo(transferInfo.getBillNo() + "_"
							+ feeInfo.getDetailId());
					inOrder.setOutBizDetailNo(String.valueOf(feeInfo
							.getDetailId()));
					inOrder.setRemark("转账单据明细 普通处理" + feeInfo.getDetailId()
							+ "：");
					bankMessageService.trade(inOrder);
				}
			}

		} catch (Exception e) {
			logger.error("完成差旅费报销单处理出错：{}", e);
		}

	}

	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		// 自定义待办任务名称
		WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsFmDomainHolder
				.get().getAttribute("startOrder");
		if (startOrder != null) {
			FormInfo form = order.getFormInfo();
			FormTransferInfo info = formTransferService.queryByFormId(form
					.getFormId());
			// 单据号：xxx- 费用支付(劳资类)审核流程-吴佳苏
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
