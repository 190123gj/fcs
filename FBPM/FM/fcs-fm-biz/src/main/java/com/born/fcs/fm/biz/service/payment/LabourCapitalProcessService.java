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
import com.born.fcs.fm.dal.dataobject.FormLabourCapitalDO;
import com.born.fcs.fm.dal.dataobject.FormLabourCapitalDetailDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.bpm.result.WorkflowResult;
import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.ReportCompanyEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalInfo;
import com.born.fcs.fm.ws.order.bank.BankTradeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.fm.ws.order.payment.LabourCapitalOrder;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.fm.ws.service.payment.CostCategoryService;
import com.born.fcs.fm.ws.service.payment.LabourCapitalService;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.common.FormExecuteInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Service("labourCapitalProcessService")
public class LabourCapitalProcessService extends BaseProcessService {
	
	@Autowired
	LabourCapitalService labourCapitalService;
	
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	
	@Autowired
	BankMessageService bankMessageService;
	
	@Autowired
	ForecastService forecastService;
	
	@Autowired
	CostCategoryService costCategoryService;
	
	@Autowired
	ReceiptPaymentFormService receiptPaymentFormService;
	
	@Override
	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		Map<String, Object> customizeMap = order.getCustomizeMap();
		String isFYFG = (String) customizeMap.get("isFYFG");
		String expenseType = (String) customizeMap.get("expenseType");
		if ("true".equals(isFYFG)) {
			if (StringUtil.isNotBlank(expenseType)) {
				// 冲销 donothing 20161228
				//				String[] costs = expenseType.split(",");
				//				String expenseId = (String) customizeMap.get("expenseApplicationId");
				//				String bankAccount = (String) customizeMap.get("bankAccount");
				//				String bank = (String) customizeMap.get("bank");
				//				//				String accountName = (String)customizeMap.get("accountName");
				//				//				String accountCode = (String)customizeMap.get("accountCode");
				//				String fyAmount = (String) customizeMap.get("fyAmount");
				//				
				//				Money allfyAmount = Money.zero();
				//				Money kcxAmount = Money.zero();
				//				Money cxAmount = Money.zero();
				//				List<ExpenseCxDetailDO> cxDetailDOs = expenseCxDetailDAO.findByExpenseId(
				//					Long.valueOf(expenseId), null, null);
				//				if (cxDetailDOs != null) {
				//					for (ExpenseCxDetailDO cxDetail : cxDetailDOs) {
				//						if (cxDetail.getFromDetailId() > 0) {
				//							FormLabourCapitalDetailDO expenseDetailDO = formLabourCapitalDetailDAO
				//								.findById(cxDetail.getFromDetailId());
				//							if (expenseDetailDO != null) {
				//								cxAmount = cxAmount.add(expenseDetailDO.getFpAmount().add(
				//									expenseDetailDO.getXjAmount()));
				//								Money subkcxAmount = expenseDetailDO.getAmount().subtract(
				//									expenseDetailDO.getFpAmount()
				//										.add(expenseDetailDO.getXjAmount()));
				//								kcxAmount = kcxAmount.add(subkcxAmount);
				//								Money leftAmount = subkcxAmount.subtract(cxDetail.getFpAmount()
				//									.add(cxDetail.getXjAmount()));
				//								if (leftAmount.compareTo(Money.zero()) < 0) {
				//									result.setMessage("【" + cxDetail.getCategory() + "】可冲销金额不足！");
				//									return result;
				//								}
				//							}
				//						} else {
				//							//							cxAmount = cxAmount.add(cxDetail.getFyAmount());
				//						}
				//					}
				//				}
				//				
				//				for (int i = 0; i < costs.length; i++) {
				//					if (StringUtil.isNotBlank(fyAmount)) {
				//						if (StringUtil.isNotBlank(fyAmount.split(",")[i])) {
				//							allfyAmount = allfyAmount.add(new Money(fyAmount.split(",")[i]));
				//						}
				//					}
				//				}
				//				
				//				//				if(kcxAmount.compareTo(allfyAmount) < 0){
				//				//					result.setMessage("可冲销金额不足！");
				//				//					return result;
				//				//				}
				//				
				//				//				if(allfyAmount.compareTo(cxAmount) != 0){
				//				//					result.setMessage("费用金额必须等于冲销金额！");
				//				//					return result;
				//				//				}
				//				
				//				ExpenseCxDetailDO expenseCxDetail = new ExpenseCxDetailDO();
				//				for (int i = 0; i < costs.length; i++) {
				//					long categoryId = Long.valueOf(costs[i]);
				//					CostCategoryDO category = costCategoryDAO.findById(categoryId);
				//					expenseCxDetail.setExpenseApplicationId(Long.valueOf(expenseId));
				//					expenseCxDetail.setCategory(category.getName());
				//					expenseCxDetail.setCategoryId(categoryId);
				//					expenseCxDetail.setAccountCode(category.getAccountCode());
				//					expenseCxDetail.setAccountName(category.getAccountName());
				//					
				//					if (StringUtil.isNotBlank(bank)) {
				//						if (bank.split(",").length > i) {
				//							expenseCxDetail.setBank(bank.split(",")[i]);
				//						}
				//						
				//					}
				//					if (StringUtil.isNotBlank(bankAccount)) {
				//						if (bankAccount.split(",").length > i) {
				//							expenseCxDetail.setBankAccount(bankAccount.split(",")[i]);
				//						}
				//						
				//					}
				//					
				//					if (StringUtil.isNotBlank(fyAmount)) {
				//						if (fyAmount.split(",").length > i
				//							&& StringUtil.isNotBlank(fyAmount.split(",")[i])) {
				//							expenseCxDetail.setFyAmount(new Money(fyAmount.split(",")[i]));
				//						}
				//					}
				//					expenseCxDetail.setFromType("type2");
				//					expenseCxDetail.setRawAddTime(new Date());
				//					expenseCxDetailDAO.insert(expenseCxDetail);
				//					result.setSuccess(true);
				//				}
				
			} else {
				String payBankAccount = (String) customizeMap.get("bankAccount");
				String payBank = (String) customizeMap.get("bank");
				if (StringUtil.isBlank(payBankAccount) || StringUtil.isBlank(payBank)) {
					result.setMessage("未设置付款账户信息");
				} else {
					LabourCapitalOrder uporder = new LabourCapitalOrder();
					uporder.setFormId(order.getFormInfo().getFormId());
					uporder.setPayBank(payBank);
					uporder.setPayBankAccount(payBankAccount);
					labourCapitalService.updatePayBank(uporder);
					result.setSuccess(true);
				}
			}
			
		} else {
			return super.doNextBeforeProcess(order);
		}
		
		return result;
		//		return super.doNextBeforeProcess(order);
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		List<FlowVarField> fields = Lists.newArrayList();
		
		try {
			
			FormLabourCapitalInfo info = labourCapitalService.queryByFormId(formInfo.getFormId());
			
			// 取费用类型
			CostCategoryQueryOrder order = new CostCategoryQueryOrder();
			order.setStatusList(new ArrayList<CostCategoryStatusEnum>());
			order.getStatusList().add(CostCategoryStatusEnum.NORMAL);
			order.setPageSize(10000);
			QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryService
				.queryPage(order);
			String expenseTypes = "";
			//取明细部门  增加info部门
			Set<Long> deptIds = new HashSet<Long>();
			List<String> deptNames = new ArrayList<String>();
			for (FormLabourCapitalDetailInfo detailInfo : info.getDetailList()) {
				if (detailInfo.getDeptId() > 0) {
					deptIds.add(detailInfo.getDeptId());
					deptNames.add(detailInfo.getDeptName());
				}
				String expenseType = detailInfo.getExpenseType();
				if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
					for (CostCategoryInfo cInfo : batchResult.getPageList()) {
						if (String.valueOf(cInfo.getCategoryId()).equals(expenseType)) {
							expenseType = cInfo.getName();
						}
					}
				}
				// 凭借expenseTypes
				if (StringUtil.isBlank(expenseTypes)) {
					expenseTypes = expenseType;
				} else {
					expenseTypes += ",";
					expenseTypes += expenseType;
				}
			}
			
			// 是否财务部
			boolean onlyOneDeptid = true;
			for (Long deptId : deptIds) {
				if (deptId != info.getExpenseDeptId()) {
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
			boolean allRLZYB = true;
			for (String deptName : deptNames) {
				if (!deptName.contains("人力资源部")) {
					allRLZYB = false;
					break;
				}
			}
			boolean hasMother = false;
			Org org = bpmUserQueryService.findDeptById(info.getExpenseDeptId());
			String department = String.valueOf(info.getExpenseDeptId());
			// 20161206 判断是否包含总公司，若包含总公司，走申请人的部门
			String CQJCK = "CQJCK";
			String paramCQJCK = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_CQJCK_COMPANY_CODE.code());
			if (StringUtil.isNotBlank(paramCQJCK)) {
				CQJCK = paramCQJCK;
			}
			Org orgCY = bpmUserQueryService.findDeptByCode(CQJCK);
			logger.info("orgCY查询结果：" + orgCY);
			if (orgCY != null
				&& (deptIds.contains(orgCY.getId()) || info.getExpenseDeptId() == orgCY.getId())) {
				hasMother = true;
				logger.info("有总部！");
				org = bpmUserQueryService.findDeptById(formInfo.getDeptId());
				if (org.getName().contains("财务部")) {
					department = "caiwu";
				} else if (checkIsFenGongSi(org.getName())) {
					department = "fengongsi";
				} else if (org.getName().contains("人力资源部")) {
					department = "fengongsi";
				}
			} else {
				//  info和明细都为财务部传财务部，明细全部包含分公司，传入分公司
				if (onlyOneDeptid && org.getName().contains("财务部")) {
					department = "caiwu";
					//			} else if (org.getName().contains("分公司")) {
				} else if (allFGX) {
					department = "fengongsi";
				} else if (allRLZYB) {
					department = "rlzyb";
				}
			}
			
			FlowVarField flowVarField = new FlowVarField();
			flowVarField.setVarName("department");//报销部门 
			flowVarField.setVarType(FlowVarTypeEnum.STRING);
			flowVarField.setVarVal(department);
			fields.add(flowVarField);
			
			logger.info("获取费用类型为：" + expenseTypes);
			FlowVarField flowVarFieldExpenseTypes = new FlowVarField();
			flowVarFieldExpenseTypes.setVarName("expenseTypes");
			flowVarFieldExpenseTypes.setVarType(FlowVarTypeEnum.STRING);
			flowVarFieldExpenseTypes.setVarVal(expenseTypes);
			fields.add(flowVarFieldExpenseTypes);
			
			String fzrRole = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());
			String fgfzRole = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FGFZ_ROLE_NAME.code());
			List<SimpleUserInfo> fzrList = new ArrayList<SimpleUserInfo>();
			List<SimpleUserInfo> fgfzList = new ArrayList<SimpleUserInfo>();
			
			// 会签始终需要Info主表的申请部门
			deptIds.add(info.getExpenseDeptId());
			
			// 20161206 会签判定时将总部更定为申请人部门
			List<Long> newDeptIds = new ArrayList<Long>();
			for (Long deptId : deptIds) {
				if (orgCY != null && deptId == orgCY.getId()) {
					//					deptId = formInfo.getDeptId();
					newDeptIds.add(formInfo.getDeptId());
				} else {
					newDeptIds.add(deptId);
				}
			}
			Org orgXH = bpmUserQueryService.findDeptByCode(ReportCompanyEnum.XINHUI.getDeptCode());
			for (Long deptId : newDeptIds) {
				fzrList.addAll(getRoleDeptUserIterates(deptId, fzrRole));
				if (StringUtil.equals(String.valueOf(deptId), String.valueOf(orgXH.getId()))) {
					fgfzList.addAll(projectRelatedUserService.getFgfz(ReportCompanyEnum.XINHUI
						.getDeptCode()));
				} else {
					fgfzList.addAll(getRoleDeptUserIterates(deptId, fgfzRole));
				}
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
			
			FlowVarField flowVarField1 = new FlowVarField();
			//  部门负责人需要加入 info部门负责人
			flowVarField1.setVarName("departmentHead");
			flowVarField1.setVarType(FlowVarTypeEnum.STRING);
			flowVarField1.setVarVal(leader);
			fields.add(flowVarField1);
			
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
			//  报销部门 需要加入 info部门
			flowVarField2.setVarName("master");
			flowVarField2.setVarType(FlowVarTypeEnum.STRING);
			flowVarField2.setVarVal(master);
			fields.add(flowVarField2);
			
			FlowVarField flowVarField3 = new FlowVarField();
			flowVarField3.setVarName("money");
			flowVarField3.setVarType(FlowVarTypeEnum.DOUBLE);
			flowVarField3.setVarVal(info.getAmount().toStandardString().replace(",", ""));
			fields.add(flowVarField3);
		} catch (Exception e) {
			logger.error("{}", e);
			throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE, e.getMessage());
		}
		
		return fields;
	}
	
	/**
	 * 流程通过 同步信息到财务系统生成凭证号
	 */
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		try {
			FormLabourCapitalInfo info = labourCapitalService.queryByFormId(formInfo.getFormId());
			logger.info("完成劳资及税金处理：{}", info);
			
			//同步至财务系统 由付款单处理
			//labourCapitalService.sync2FinancialSys(info);
			// TODO 添加付款单记录
			
			ReceiptPaymentFormOrder receiptPaymentOrder = new ReceiptPaymentFormOrder();
			receiptPaymentOrder.setSourceForm(SourceFormEnum.LABOUR_CAPITAL);
			receiptPaymentOrder.setSourceFormId(String.valueOf(formInfo.getFormId()));
			receiptPaymentOrder.setSourceFormSys(SystemEnum.FM);
			
			receiptPaymentOrder.setUserId(formInfo.getUserId());
			receiptPaymentOrder.setUserAccount(formInfo.getUserAccount());
			receiptPaymentOrder.setUserName(formInfo.getUserName());
			//			receiptPaymentOrder.setDeptId(formInfo.getDeptId());
			//			receiptPaymentOrder.setDeptCode(formInfo.getDeptCode());
			//			receiptPaymentOrder.setDeptName(formInfo.getDeptName());
			
			Org org = bpmUserQueryService.findDeptById(new Long(info.getExpenseDeptId()));
			receiptPaymentOrder.setDeptId(org.getId());
			receiptPaymentOrder.setDeptCode(org.getCode());
			receiptPaymentOrder.setDeptName(org.getName());
			
			receiptPaymentOrder.setRemark(info.getReimburseReason());
			
			// 增加费用明细
			List<ReceiptPaymentFormFeeOrder> feeOrderList = new ArrayList<ReceiptPaymentFormFeeOrder>();
			ReceiptPaymentFormFeeOrder formFeeOrder = new ReceiptPaymentFormFeeOrder();
			formFeeOrder.setFeeType(SubjectCostTypeEnum.INTERNAL_FUND_LENDING);
			formFeeOrder.setAmount(info.getAmount());
			formFeeOrder.setRemark("劳资及税金申请单");
			feeOrderList.add(formFeeOrder);
			receiptPaymentOrder.setFeeOrderList(feeOrderList);
			receiptPaymentFormService.add(receiptPaymentOrder);
			
			logger.info("设定为打款通过，formid=：" + formInfo.getFormId());
			// 待打款 【 审核中 待打款  已打款】
			FormLabourCapitalDO expenseInfo = formLabourCapitalDAO.findByFormId(formInfo
				.getFormId());
			expenseInfo.setAccountStatus(AccountStatusEnum.PAYED.code());
			formLabourCapitalDAO.update(expenseInfo);
			logger.info("设定为打款通过 成功，info=：" + expenseInfo);
			
			//同步预测数据
			labourCapitalService.syncForecast(formInfo.getFormId(), true);
			
			Date now = FcsFmDomainHolder.get().getSysDate();
			// 对公冲销
			// 总计金额减
			if (expenseInfo.getAmount().greaterThan(Money.zero())) {
				BankTradeOrder inOrder = new BankTradeOrder();
				inOrder.setAccountNo(expenseInfo.getPayBankAccount());
				inOrder.setAmount(expenseInfo.getAmount());
				inOrder.setFundDirection(FundDirectionEnum.OUT);
				inOrder.setTradeTime(now);//付款时间
				inOrder.setOutBizNo(expenseInfo.getBillNo());
				inOrder.setOutBizDetailNo(String.valueOf(expenseInfo.getLabourCapitalId()));
				inOrder.setRemark("劳资及税金收款账户 普通处理" + expenseInfo.getBillNo() + "：");
				bankMessageService.trade(inOrder);
			}
			
		} catch (Exception e) {
			logger.error("完成劳资及税金处理出错：{}", e);
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
			FormLabourCapitalInfo info = labourCapitalService.queryByFormId(form.getFormId());
			//		单据号：xxx-	劳资及税金(劳资类)审核流程-吴佳苏
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
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		labourCapitalService.syncForecast(formInfo.getFormId(), false);
		logger.info("设定为审核中，formid=：" + formInfo.getFormId());
		FormLabourCapitalDO expenseInfo = formLabourCapitalDAO.findByFormId(formInfo.getFormId());
		expenseInfo.setAccountStatus(AccountStatusEnum.CHECKING.code());
		formLabourCapitalDAO.update(expenseInfo);
		logger.info("设定审核中成功，info=：" + expenseInfo);
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
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		noPassForecast(formInfo);
		FormLabourCapitalInfo info = labourCapitalService.queryByFormId(formInfo.getFormId());
		logger.info("终止处理：{}", info);
		if (StringUtil.isNotBlank(info.getCxids())) {
			String[] strs = info.getCxids().split(",");
			for (String str : strs) {
				FormLabourCapitalDetailDO detailDO = formLabourCapitalDetailDAO.findById(Long
					.valueOf(str));
				if (StringUtil.equals(detailDO.getReverse(), BooleanEnum.YES.code())) {
					detailDO.setReverse(BooleanEnum.NO.code());
					formLabourCapitalDetailDAO.update(detailDO);
					logger.info("已更定为未冲销，id=：" + detailDO.getDetailId());
				}
			}
		}
	}
	
	@Override
	public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		noPassForecast(formInfo);
		FormLabourCapitalInfo info = labourCapitalService.queryByFormId(formInfo.getFormId());
		logger.info("作废处理：{}", info);
		if (StringUtil.isNotBlank(info.getCxids())) {
			String[] strs = info.getCxids().split(",");
			for (String str : strs) {
				FormLabourCapitalDetailDO detailDO = formLabourCapitalDetailDAO.findById(Long
					.valueOf(str));
				if (StringUtil.equals(detailDO.getReverse(), BooleanEnum.YES.code())) {
					detailDO.setReverse(BooleanEnum.NO.code());
					formLabourCapitalDetailDAO.update(detailDO);
					logger.info("已更定为未冲销，id=：" + detailDO.getDetailId());
				}
			}
		}
		// 20161216 作废的单据 单据状态设定为作废
		FormLabourCapitalDO expenseDO = formLabourCapitalDAO.findById(info.getLabourCapitalId());
		expenseDO.setAccountStatus(AccountStatusEnum.END.code());
		formLabourCapitalDAO.update(expenseDO);
	}
	
	@Override
	public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//撤销表单后业务处理(BASE)
		FormLabourCapitalInfo info = labourCapitalService.queryByFormId(formInfo.getFormId());
		logger.info("撤回处理：{}", info);
		if (StringUtil.isNotBlank(info.getCxids())) {
			String[] strs = info.getCxids().split(",");
			for (String str : strs) {
				FormLabourCapitalDetailDO detailDO = formLabourCapitalDetailDAO.findById(Long
					.valueOf(str));
				if (StringUtil.equals(detailDO.getReverse(), BooleanEnum.YES.code())) {
					detailDO.setReverse(BooleanEnum.NO.code());
					formLabourCapitalDetailDAO.update(detailDO);
					logger.info("已更定为未冲销，id=：" + detailDO.getDetailId());
				}
			}
		}
	}
	
	/***
	 * 流程未审核通过预测数据变更
	 * @param formInfo
	 */
	private void noPassForecast(FormInfo formInfo) {
		try {
			FormLabourCapitalDO apply = formLabourCapitalDAO.findByFormId(formInfo.getFormId());
			ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
			forecastAccountOrder.setOrderNo(apply.getBillNo());
			forecastAccountOrder.setSystemForm(SystemEnum.FM);
			forecastService.delete(forecastAccountOrder);
		} catch (Exception e) {
			logger.error("劳资及税金处理未通过表单预测数据出错：{}", e);
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
			if (taskUrl.contains("/fundMg/labourCapital/audit/canAccount.htm")) {//下一步的url
				logger.info("设定为待打款，formid=：" + formInfo.getFormId());
				//TODO 待打款 【 审核中 待打款  已打款】
				FormLabourCapitalDO expenseInfo = formLabourCapitalDAO.findByFormId(formInfo
					.getFormId());
				expenseInfo.setAccountStatus(AccountStatusEnum.WAIT_PAY.code());
				// 20161221 设定待打款时间
				expenseInfo.setWaitPayTime(FcsFmDomainHolder.get().getSysDate());
				formLabourCapitalDAO.update(expenseInfo);
				logger.info("设定为待打款成功，info=：" + expenseInfo);
			}
		}
		// 20161129 添加动作 驳回之后【判断状态】将子项设定为no
		if (FormStatusEnum.BACK == formInfo.getStatus()) {
			
			FormLabourCapitalInfo info = labourCapitalService.queryByFormId(formInfo.getFormId());
			logger.info("驳回处理：{}", info);
			if (StringUtil.isNotBlank(info.getCxids())) {
				String[] strs = info.getCxids().split(",");
				for (String str : strs) {
					FormLabourCapitalDetailDO detailDO = formLabourCapitalDetailDAO.findById(Long
						.valueOf(str));
					if (StringUtil.equals(detailDO.getReverse(), BooleanEnum.YES.code())) {
						detailDO.setReverse(BooleanEnum.NO.code());
						formLabourCapitalDetailDAO.update(detailDO);
						logger.info("已更定为未冲销，id=：" + detailDO.getDetailId());
					}
				}
			}
		}
		
	}
	
}
