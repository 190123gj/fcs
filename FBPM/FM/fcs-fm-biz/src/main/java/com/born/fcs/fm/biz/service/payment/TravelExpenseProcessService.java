package com.born.fcs.fm.biz.service.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.job.AsynchronousTaskJob;
import com.born.fcs.fm.biz.service.base.BaseProcessService;
import com.born.fcs.fm.dal.dataobject.FormTravelExpenseDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.bpm.result.WorkflowResult;
import com.born.fcs.fm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.BranchPayStatusEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.ReportCompanyEnum;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseInfo;
import com.born.fcs.fm.ws.order.bank.BankTradeOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseOrder;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.fm.ws.service.payment.TravelExpenseService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.common.FormExecuteInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.FormRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.google.common.collect.Lists;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Service("travelExpenseProcessService")
public class TravelExpenseProcessService extends BaseProcessService {
	
	@Autowired
	TravelExpenseService travelExpenseService;
	
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	
	@Autowired
	BankMessageService bankMessageService;
	
	@Autowired
	ForecastService forecastService;
	
	@Autowired
	SiteMessageService siteMessageService;
	
	@Override
	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		Map<String, Object> customizeMap = order.getCustomizeMap();
		String isFYFG = (String) customizeMap.get("isFYFG");
		if ("true".equals(isFYFG)) {
			String payBankAccount = (String) customizeMap.get("bankAccount");
			String payBank = (String) customizeMap.get("bank");
			if (StringUtil.isBlank(payBankAccount) || StringUtil.isBlank(payBank)) {
				result.setMessage("未设置付款账户信息");
			} else {
				TravelExpenseOrder uporder = new TravelExpenseOrder();
				uporder.setFormId(order.getFormInfo().getFormId());
				uporder.setPayBank(payBank);
				uporder.setPayBankAccount(payBankAccount);
				travelExpenseService.updatePayBank(uporder);
				result.setSuccess(true);
			}
		} else {
			return super.doNextBeforeProcess(order);
		}
		return result;
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		List<FlowVarField> fields = Lists.newArrayList();
		
		try {
			
			FormTravelExpenseInfo te = travelExpenseService.queryByFormId(formInfo.getFormId());
			
			//取明细部门  增加info部门
			Set<String> deptIds = new HashSet<String>();
			List<String> deptNames = new ArrayList<String>();
			for (FormTravelExpenseDetailInfo detailInfo : te.getDetailList()) {
				if (StringUtil.isBlank(detailInfo.getDeptId())) {
					continue;
				}
				// 20161119  设计上一条子明细会有多个部门？、、、、？？？？？？？？？ 不明白
				for (String id : detailInfo.getDeptId().split(",")) {
					if (StringUtil.isNotBlank(id)) {
						deptIds.add(id);
					}
				}
				// 20161119 添加部门名
				deptNames.add(detailInfo.getDeptName());
			}
			
			// 是否财务部
			boolean onlyOneDeptid = true;
			for (String deptId : deptIds) {
				if (StringUtil.notEquals(String.valueOf(deptId),
					String.valueOf(te.getExpenseDeptId()))) {
					onlyOneDeptid = false;
					break;
				}
			}
			boolean allFGX = true;
			for (String deptName : deptNames) {
				//				if (!deptName.contains("分公司")) {
				if (!checkIsFenGongSi(deptName)) {
					allFGX = false;
					break;
				}
			}
			
			Org org = bpmUserQueryService.findDeptById(te.getExpenseDeptId());
			String department = String.valueOf(te.getExpenseDeptId());
			// 20160123 判断是否包含总公司，若包含总公司，走申请人的部门
			String CQJCK = "CQJCK";
			String paramCQJCK = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_CQJCK_COMPANY_CODE.code());
			if (StringUtil.isNotBlank(paramCQJCK)) {
				CQJCK = paramCQJCK;
			}
			Org orgCY = bpmUserQueryService.findDeptByCode(CQJCK);
			logger.info("orgCY查询结果：" + orgCY);
			
			if (orgCY != null
				&& (deptIds.contains(String.valueOf(orgCY.getId())) || te.getExpenseDeptId() == orgCY
					.getId())) {
				logger.info("有总部！");
				org = bpmUserQueryService.findDeptById(formInfo.getDeptId());
				if (org.getName().contains("财务部")) {
					department = "caiwu";
				} else if (checkIsFenGongSi(org.getName())) {
					department = "fengongsi";
				} else if (org.getName().contains("人力资源部")) {
					department = "rlzyb";
				} else if (StringUtil.equals(org.getCode(), ReportCompanyEnum.XINHUI.getDeptCode())) {
					// 发起人是信惠
					department = "xh";
				}
			} else {
				//  info和明细都为财务部传财务部，明细全部包含分公司，传入分公司
				if (onlyOneDeptid && org.getName().contains("财务部")) {
					department = "caiwu";
					//			} else if (org.getName().contains("分公司")) {
				} else if (allFGX) {
					department = "fengongsi";
				} else if (StringUtil.equals(org.getCode(), ReportCompanyEnum.XINHUI.getDeptCode())) {
					// 发起人是信惠
					department = "xh";
				}
			}
			FlowVarField flowVarField0 = new FlowVarField();
			flowVarField0.setVarName("department");//报销部门 
			flowVarField0.setVarType(FlowVarTypeEnum.STRING);
			flowVarField0.setVarVal(department);
			fields.add(flowVarField0);
			
			String fzrRole = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());
			String fgfzRole = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FGFZ_ROLE_NAME.code());
			List<SimpleUserInfo> fzrList = new ArrayList<SimpleUserInfo>();
			List<SimpleUserInfo> fgfzList = new ArrayList<SimpleUserInfo>();
			
			// 会签始终需要Info主表的申请部门
			deptIds.add(String.valueOf(te.getExpenseDeptId()));
			
			// 20161206 会签判定时将总部更定为申请人部门
			List<String> newDeptIds = new ArrayList<String>();
			for (String deptId : deptIds) {
				String orgCYId = String.valueOf(orgCY.getId());
				if (orgCY != null && StringUtil.equals(deptId, orgCYId)) {
					//					deptId = formInfo.getDeptId();
					newDeptIds.add(String.valueOf(formInfo.getDeptId()));
				} else {
					newDeptIds.add(deptId);
				}
			}
			
			for (String deptId : newDeptIds) {
				//fzrList.addAll(getRoleDeptUserIterates(Long.valueOf(deptId), fzrRole));
				List<SysUser> sysus = bpmUserQueryService.findChargeByOrgId(new Long(deptId));
				if (sysus != null && ListUtil.isNotEmpty(sysus)) {
					if (ListUtil.isNotEmpty(sysus)) {
						for (SysUser user : sysus) {
							SimpleUserInfo simpleUser = new SimpleUserInfo();
							simpleUser.setUserId(user.getUserId());
							simpleUser.setUserName(user.getFullname());
							simpleUser.setUserAccount(user.getAccount());
							simpleUser.setEmail(user.getEmail());
							simpleUser.setMobile(user.getMobile());
							fzrList.add(simpleUser);
						}
					}
				}
				fgfzList.addAll(getRoleDeptUserIterates(Long.valueOf(deptId), fgfzRole));
			}
			
			// 去重
			Set<String> fzrSet = new HashSet<String>(); // 部门负责人
			Set<String> fgfzSet = new HashSet<String>(); // 分管副总
			if (ListUtil.isNotEmpty(fzrList)) {
				for (SimpleUserInfo fzr : fzrList) {
					fzrSet.add(String.valueOf(fzr.getUserId()));
				}
			}
			
			if (ListUtil.isNotEmpty(fgfzList)) {
				for (SimpleUserInfo fzr : fgfzList) {
					fgfzSet.add(String.valueOf(fzr.getUserId()));
				}
			}
			
			String leader = "0";
			for (String fzr : fzrSet) {
				leader += fzr + ",";
			}
			
			if (!leader.equals("0"))
				leader = leader.substring(0, leader.length() - 1);
			
			FlowVarField flowVarField = new FlowVarField();
			flowVarField.setVarName("leader");
			flowVarField.setVarType(FlowVarTypeEnum.STRING);
			flowVarField.setVarVal(leader);
			fields.add(flowVarField);
			
			String master = "0";
			for (String fgfz : fgfzSet) {
				master += fgfz + ",";
			}
			
			if (!master.equals("0")) {
				master = master.substring(0, master.length() - 1);
			} else {
				throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE, "明细部门负责人未设置");
			}
			
			FlowVarField flowVarField2 = new FlowVarField();
			flowVarField2.setVarName("master");
			flowVarField2.setVarType(FlowVarTypeEnum.STRING);
			flowVarField2.setVarVal(master);
			fields.add(flowVarField2);
			
			FlowVarField flowVarField3 = new FlowVarField();
			flowVarField3.setVarName("money");
			flowVarField3.setVarType(FlowVarTypeEnum.DOUBLE);
			flowVarField3.setVarVal(te.getAmount().toStandardString().replace(",", ""));
			fields.add(flowVarField3);
			
		} catch (Exception e) {
			logger.error("{}", e);
			throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE, e.getMessage());
		}
		
		return fields;
	}
	
	private List<SimpleUserInfo> getRoleDeptUserIterates(long deptId, String fzrRole) {
		List<SimpleUserInfo> roleDeptUser = projectRelatedUserService.getRoleDeptUser(deptId,
			fzrRole);
		if (roleDeptUser == null || roleDeptUser.size() == 0) { // 如果没有，就找其上级部门
			Org org = bpmUserQueryService.findDeptById(deptId);
			if (org != null && org.getParentId() > 0) {
				roleDeptUser = getRoleDeptUserIterates(org.getParentId(), fzrRole);
			}
		}
		return roleDeptUser;
	}
	
	/**
	 * 流程通过 同步信息到财务系统生成凭证号
	 */
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		try {
			FormTravelExpenseInfo travelInfo = travelExpenseService.queryByFormId(formInfo
				.getFormId());
			logger.info("完成差旅费报销单处理：{}", travelInfo);
			
			//同步至财务系统
			travelExpenseService.sync2FinancialSys(travelInfo);
			
			//预测数据
			travelExpenseService.syncForecast(formInfo.getFormId(), true);
			
			//异步增加相应银行账户金额
			//			logger.info("添加到异步任务执行银行金额付款增加：{}", travelInfo);
			//			asynchronousTaskJob.addAsynchronousService(new AsynchronousService() {
			//				@Override
			//				public void execute(Object[] objects) {
			//					FormTravelExpenseInfo travelInfo = (FormTravelExpenseInfo) objects[0];
			//					List<FormTravelExpenseDetailInfo> feeList = travelInfo.getDetailList();
			//					if (ListUtil.isNotEmpty(feeList)) {
			//						for (FormTravelExpenseDetailInfo feeInfo : feeList) {
			//							BankTradeOrder tradeOrder = new BankTradeOrder();
			//							tradeOrder.setAccountNo(travelInfo.getBankAccount());
			//							tradeOrder.setAmount(feeInfo.getTotalAmount());
			//							tradeOrder.setFundDirection(FundDirectionEnum.OUT);
			//							tradeOrder.setTradeTime(travelInfo.getRawUpdateTime());//付款时间
			//							tradeOrder.setOutBizNo(travelInfo.getBillNo());
			//							tradeOrder.setOutBizDetailNo(String.valueOf(feeInfo.getDetailId()));
			//							tradeOrder.setRemark("差旅费报销");
			//							bankMessageService.trade(tradeOrder);
			//						}
			//					}
			//				}
			//			}, new Object[] { travelInfo });
			if (travelInfo.getAmount().greaterThan(Money.zero())) {
				Date now = FcsFmDomainHolder.get().getSysDate();
				BankTradeOrder inOrder = new BankTradeOrder();
				inOrder.setAccountNo(travelInfo.getPayBankAccount());
				inOrder.setAmount(travelInfo.getAmount());
				inOrder.setFundDirection(FundDirectionEnum.OUT);
				inOrder.setTradeTime(now);//付款时间
				inOrder.setOutBizNo(travelInfo.getBillNo());
				inOrder.setOutBizDetailNo(String.valueOf(travelInfo.getTravelId()));
				inOrder.setRemark("费用支付收款账户普通处理 " + travelInfo.getBillNo() + "：");
				bankMessageService.trade(inOrder);
			}
			
			logger.info("设定为打款通过，formid=：" + formInfo.getFormId());
			FormTravelExpenseDO tracelInfo = formTravelExpenseDAO
				.findByFormId(formInfo.getFormId());
			tracelInfo.setAccountStatus(AccountStatusEnum.PAYED.code());
			formTravelExpenseDAO.update(tracelInfo);
			logger.info("设定为打款通过 成功，info=：" + tracelInfo);
			
		} catch (Exception e) {
			logger.error("完成差旅费报销单处理出错：{}", e);
		}
		
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//预测数据
		travelExpenseService.syncForecast(formInfo.getFormId(), false);
		logger.info("设定为审核中，formid=：" + formInfo.getFormId());
		FormTravelExpenseDO tracelInfo = formTravelExpenseDAO.findByFormId(formInfo.getFormId());
		tracelInfo.setAccountStatus(AccountStatusEnum.CHECKING.code());
		formTravelExpenseDAO.update(tracelInfo);
		logger.info("设定为审核中成功，info=：" + tracelInfo);
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		noPassForecast(formInfo);
	}
	
	@Override
	public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		noPassForecast(formInfo);
		// 20161216 作废的单据 单据状态设定为作废
		FormTravelExpenseDO expenseDO = formTravelExpenseDAO.findByFormId(formInfo.getFormId());
		expenseDO.setAccountStatus(AccountStatusEnum.END.code());
		formTravelExpenseDAO.update(expenseDO);
	}
	
	/***
	 * 流程未审核通过预测数据变更
	 * @param formInfo
	 */
	private void noPassForecast(FormInfo formInfo) {
		try {
			FormTravelExpenseDO apply = formTravelExpenseDAO.findByFormId(formInfo.getFormId());
			ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
			forecastAccountOrder.setOrderNo(apply.getBillNo());
			forecastAccountOrder.setSystemForm(SystemEnum.FM);
			forecastService.delete(forecastAccountOrder);
		} catch (Exception e) {
			logger.error("差旅费报销处理未通过表单预测数据出错：{}", e);
		}
	}
	
	@Override
	public void doNextAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 审核后业务处理
		List<FormExecuteInfo> exeList = formInfo.getFormExecuteInfo();
		if (ListUtil.isNotEmpty(exeList)) {
			String taskUrl = "";
			for (FormExecuteInfo exeInfo : exeList) {
				if (StringUtil.isNotEmpty(exeInfo.getTaskUrl())) {
					taskUrl = exeInfo.getTaskUrl();
					break;
				}
			}
			if (taskUrl.contains("/fundMg/travelExpense/audit/canAccount.htm")) {//下一步的url
				logger.info("设定为待打款，formid=：" + formInfo.getFormId());
				//TODO 待打款 【 审核中 待打款  已打款】
				FormTravelExpenseDO tracelInfo = formTravelExpenseDAO.findByFormId(formInfo
					.getFormId());
				tracelInfo.setAccountStatus(AccountStatusEnum.WAIT_PAY.code());
				// 20161221 设定待打款时间
				tracelInfo.setWaitPayTime(FcsFmDomainHolder.get().getSysDate());
				formTravelExpenseDAO.update(tracelInfo);
				logger.info("设定为待打款成功，info=：" + tracelInfo);
			}
			
			//金额规则类,分支机构分管副总审核后发消息给分支机构支付岗
			if (formInfo.getFormCode() == FormCodeEnum.TRAVEL_EXPENSE_APPLY
				&& taskUrl.contains("afterFgfz=YES")) {
				FormTravelExpenseDO tracelInfo = formTravelExpenseDAO.findByFormId(formInfo
						.getFormId());
				sendTofzjgAfterFgfz(formInfo, tracelInfo);
			}
		}
	}
	
	/**
	 * 分管副总审批后通知分支机构支付岗确认
	 * @param formInfo
	 * @param expenseInfo
	 */
	private void sendTofzjgAfterFgfz(FormInfo formInfo, FormTravelExpenseDO expenseInfo) {
		
		if (expenseInfo == null || !isFzjg(expenseInfo.getDeptName()))
			return;
		
		//未付款的再更新
		//未付款的再更新
		if (!StringUtil.equals(expenseInfo.getBranchPayStatus(), BranchPayStatusEnum.PAID.code())) {
			//设置待付款时间
			expenseInfo.setBranchWaitPayTime(getSysdate());
			//设置成未付款
			expenseInfo.setBranchPayStatus(BranchPayStatusEnum.NOT_PAY.code());
			formTravelExpenseDAO.update(expenseInfo);
		}
		
		//表单站内地址
		String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/fundMg/travelExpense/travellist.htm?from=query&billNo="
							+ expenseInfo.getBillNo();
		String forMessage = "<a href='" + messageUrl + "'>确认付款</a>";
		
		//分支机构支付岗角色名
		String fzjgZfgRole = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FZJGZFQRG_ROLE_NAME.code());
		
		//分支机构支付岗人员
		List<SimpleUserInfo> fzjgZfgUsers = projectRelatedUserService.getRoleDeptUser(
			expenseInfo.getExpenseDeptId(), fzjgZfgRole);
		
		if (ListUtil.isNotEmpty(fzjgZfgUsers)) {
			
			for (SimpleUserInfo user : fzjgZfgUsers) {
				FormRelatedUserOrder formRelatedUserOrder = new FormRelatedUserOrder();
				BeanCopier.staticCopy(user, formRelatedUserOrder);
				formRelatedUserOrder.setUserMobile(user.getMobile());
				formRelatedUserOrder.setUserEmail(user.getEmail());
				formRelatedUserOrder.setUserType(FormRelatedUserTypeEnum.OTHER);
				formRelatedUserOrder.setFormCode(formInfo.getFormCode());
				formRelatedUserOrder.setFormId(formInfo.getFormId());
				formRelatedUserOrder.setExeStatus(ExeStatusEnum.NOT_TASK);
				formRelatedUserOrder.setRemark("分支机构支付岗");
				formRelatedUserService.setRelatedUser(formRelatedUserOrder);
			}
			
			SimpleUserInfo[] sendUsers = fzjgZfgUsers.toArray(new SimpleUserInfo[fzjgZfgUsers
				.size()]);
			//替换审核地址
			// 由XX（部门）X XX（单据的发起人姓名）发起的费用支付申请单号XXX，可执行付款操作，点击确认付款。
			StringBuilder sb = new StringBuilder();
			sb.append("由 ");
			sb.append(formInfo.getDeptName());
			sb.append(" ");
			sb.append(formInfo.getUserName());
			sb.append("  发起的差旅费报销单号 ");
			sb.append(expenseInfo.getBillNo());
			sb.append(" 可执行付款操作，点击 ");
			String messageContent = sb.toString();
			messageContent += forMessage;
			if (StringUtil.isNotBlank(messageContent)) {
				MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
				messageOrder.setMessageSubject("确认付款提醒");
				messageOrder.setMessageTitle("确认付款提醒");
				messageOrder.setMessageContent(messageContent);
				messageOrder.setSendUsers(sendUsers);
				siteMessageService.addMessageInfo(messageOrder);
			}
		}
	}
	
	/**
	 * 判断是否分支机构
	 * @param deptName
	 * @return
	 */
	private boolean isFzjg(String deptName) {
		boolean match = false;
		String fzjgNames = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FZJG_DEPT_NAME.code());
		if (StringUtil.isNotBlank(fzjgNames)) {
			String[] fzjgNameArr = fzjgNames.split(",");
			for (String fzjgName : fzjgNameArr) {
				if (StringUtil.equals(deptName, fzjgName)) {
					match = true;
					break;
				}
			}
		}
		return match;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		//自定义待办任务名称
		WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsFmDomainHolder.get().getAttribute(
			"startOrder");
		if (startOrder != null) {
			FormInfo form = order.getFormInfo();
			FormTravelExpenseInfo info = travelExpenseService.queryByFormId(form.getFormId());
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
