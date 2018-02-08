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
import com.born.fcs.fm.dal.dataobject.CostCategoryDO;
import com.born.fcs.fm.dal.dataobject.ExpenseCxDetailDO;
import com.born.fcs.fm.dal.dataobject.FormExpenseApplicationDO;
import com.born.fcs.fm.dal.dataobject.FormExpenseApplicationDetailDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.bpm.result.WorkflowResult;
import com.born.fcs.fm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.BranchPayStatusEnum;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.ReportCompanyEnum;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.order.bank.BankTradeOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationOrder;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.fm.ws.service.payment.CostCategoryService;
import com.born.fcs.fm.ws.service.payment.ExpenseApplicationService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
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
import com.born.fcs.pm.ws.order.common.FormRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.google.common.collect.Lists;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Service("expenseApplyProcessService")
public class ExpenseApplyProcessService extends BaseProcessService {

	@Autowired
	ExpenseApplicationService expenseApplicationService;

	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;

	@Autowired
	BankMessageService bankMessageService;

	@Autowired
	ForecastService forecastService;

	@Autowired
	CostCategoryService costCategoryService;

	@Autowired
	SiteMessageService siteMessageService;

	@Override
	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		Map<String, Object> customizeMap = order.getCustomizeMap();
		String isFYFG = (String) customizeMap.get("isFYFG");
		String expenseType = (String) customizeMap.get("expenseType");
		if ("true".equals(isFYFG)) {
			if (StringUtil.isNotBlank(expenseType)) {
				String[] costs = expenseType.split(",");
				String expenseId = (String) customizeMap
						.get("expenseApplicationId");
				String bankAccount = (String) customizeMap.get("bankAccount");
				String bank = (String) customizeMap.get("bank");
				// String accountName = (String)customizeMap.get("accountName");
				// String accountCode = (String)customizeMap.get("accountCode");
				String fyAmount = (String) customizeMap.get("fyAmount");

				Money allfyAmount = Money.zero();
				Money kcxAmount = Money.zero();
				Money cxAmount = Money.zero();
				List<ExpenseCxDetailDO> cxDetailDOs = expenseCxDetailDAO
						.findByExpenseId(Long.valueOf(expenseId), null, null);
				if (cxDetailDOs != null) {
					for (ExpenseCxDetailDO cxDetail : cxDetailDOs) {
						if (cxDetail.getFromDetailId() > 0) {
							FormExpenseApplicationDetailDO expenseDetailDO = formExpenseApplicationDetailDAO
									.findById(cxDetail.getFromDetailId());
							if (expenseDetailDO != null) {
								cxAmount = cxAmount.add(expenseDetailDO
										.getFpAmount().add(
												expenseDetailDO.getXjAmount()));
								Money subkcxAmount = expenseDetailDO
										.getAmount()
										.subtract(
												expenseDetailDO.getFpAmount()
														.add(expenseDetailDO
																.getXjAmount()));
								kcxAmount = kcxAmount.add(subkcxAmount);
								Money leftAmount = subkcxAmount
										.subtract(cxDetail.getFpAmount().add(
												cxDetail.getXjAmount()));
								if (leftAmount.compareTo(Money.zero()) < 0) {
									result.setMessage("【"
											+ cxDetail.getCategory()
											+ "】可冲销金额不足！");
									return result;
								}
							}
						} else {
							// cxAmount = cxAmount.add(cxDetail.getFyAmount());
						}
					}
				}

				for (int i = 0; i < costs.length; i++) {
					if (StringUtil.isNotBlank(fyAmount)) {
						if (StringUtil.isNotBlank(fyAmount.split(",")[i])) {
							allfyAmount = allfyAmount.add(new Money(fyAmount
									.split(",")[i]));
						}
					}
				}

				// if(kcxAmount.compareTo(allfyAmount) < 0){
				// result.setMessage("可冲销金额不足！");
				// return result;
				// }

				// if(allfyAmount.compareTo(cxAmount) != 0){
				// result.setMessage("费用金额必须等于冲销金额！");
				// return result;
				// }

				ExpenseCxDetailDO expenseCxDetail = new ExpenseCxDetailDO();
				for (int i = 0; i < costs.length; i++) {
					long categoryId = Long.valueOf(costs[i]);
					CostCategoryDO category = costCategoryDAO
							.findById(categoryId);
					expenseCxDetail.setExpenseApplicationId(Long
							.valueOf(expenseId));
					expenseCxDetail.setCategory(category.getName());
					expenseCxDetail.setCategoryId(categoryId);
					expenseCxDetail.setAccountCode(category.getAccountCode());
					expenseCxDetail.setAccountName(category.getAccountName());

					if (StringUtil.isNotBlank(bank)) {
						if (bank.split(",").length > i) {
							expenseCxDetail.setBank(bank.split(",")[i]);
						}

					}
					if (StringUtil.isNotBlank(bankAccount)) {
						if (bankAccount.split(",").length > i) {
							expenseCxDetail.setBankAccount(bankAccount
									.split(",")[i]);
						}

					}

					if (StringUtil.isNotBlank(fyAmount)) {
						if (fyAmount.split(",").length > i
								&& StringUtil
										.isNotBlank(fyAmount.split(",")[i])) {
							expenseCxDetail.setFyAmount(new Money(fyAmount
									.split(",")[i]));
						}
					}
					expenseCxDetail.setFromType("type2");
					expenseCxDetail.setRawAddTime(new Date());
					expenseCxDetailDAO.insert(expenseCxDetail);
					result.setSuccess(true);
				}

			} else {
				String payBankAccount = (String) customizeMap
						.get("bankAccount");
				String payBank = (String) customizeMap.get("bank");
				if (StringUtil.isBlank(payBankAccount)
						|| StringUtil.isBlank(payBank)) {
					result.setMessage("未设置付款账户信息");
				} else {
					ExpenseApplicationOrder uporder = new ExpenseApplicationOrder();
					uporder.setFormId(order.getFormInfo().getFormId());
					uporder.setPayBank(payBank);
					uporder.setPayBankAccount(payBankAccount);
					expenseApplicationService.updatePayBank(uporder);
					result.setSuccess(true);
				}
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

			FormExpenseApplicationInfo info = expenseApplicationService
					.queryByFormId(formInfo.getFormId());

			// 取费用类型
			CostCategoryQueryOrder order = new CostCategoryQueryOrder();
			order.setStatusList(new ArrayList<CostCategoryStatusEnum>());
			order.getStatusList().add(CostCategoryStatusEnum.NORMAL);
			order.setPageSize(10000);
			QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryService
					.queryPage(order);
			String expenseTypes = "";
			// 取明细部门 增加info部门
			Set<Long> deptIds = new HashSet<Long>();
			List<String> deptNames = new ArrayList<String>();
			for (FormExpenseApplicationDetailInfo detailInfo : info
					.getDetailList()) {
				if (detailInfo.getDeptId() > 0) {
					deptIds.add(detailInfo.getDeptId());
					deptNames.add(detailInfo.getDeptName());
				}
				String expenseType = detailInfo.getExpenseType();
				if (batchResult != null
						&& ListUtil.isNotEmpty(batchResult.getPageList())) {
					for (CostCategoryInfo cInfo : batchResult.getPageList()) {
						if (String.valueOf(cInfo.getCategoryId()).equals(
								expenseType)) {
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
				// if (!deptName.contains("分公司")) {
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
					.getSysParameterValue(SysParamEnum.SYS_PARAM_CQJCK_COMPANY_CODE
							.code());
			if (StringUtil.isNotBlank(paramCQJCK)) {
				CQJCK = paramCQJCK;
			}
			Org orgCY = bpmUserQueryService.findDeptByCode(CQJCK);
			logger.info("orgCY查询结果：" + orgCY);
			if (orgCY != null
					&& (deptIds.contains(orgCY.getId()) || info
							.getExpenseDeptId() == orgCY.getId())) {
				hasMother = true;
				logger.info("有总部！");
				org = bpmUserQueryService.findDeptById(formInfo.getDeptId());
				if (org.getName().contains("财务部")) {
					department = "caiwu";
				} else if (checkIsFenGongSi(org.getName())) {
					department = "fengongsi";
				} else if (org.getName().contains("人力资源部")) {
					department = "rlzyb";
				}
			} else {
				// info和明细都为财务部传财务部，明细全部包含分公司，传入分公司
				if (onlyOneDeptid && org.getName().contains("财务部")) {
					department = "caiwu";
					// } else if (org.getName().contains("分公司")) {
				} else if (allFGX) {
					department = "fengongsi";
				} else if (allRLZYB) {
					department = "rlzyb";
				}
			}

			FlowVarField flowVarField = new FlowVarField();
			flowVarField.setVarName("department");// 报销部门
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
					.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME
							.code());
			String fgfzRole = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_FGFZ_ROLE_NAME
							.code());
			List<SimpleUserInfo> fzrList = new ArrayList<SimpleUserInfo>();
			List<SimpleUserInfo> fgfzList = new ArrayList<SimpleUserInfo>();

			// 会签始终需要Info主表的申请部门
			deptIds.add(info.getExpenseDeptId());

			// 20161206 会签判定时将总部更定为申请人部门
			List<Long> newDeptIds = new ArrayList<Long>();
			for (Long deptId : deptIds) {
				if (orgCY != null && deptId == orgCY.getId()) {
					// deptId = formInfo.getDeptId();
					newDeptIds.add(formInfo.getDeptId());
				} else {
					newDeptIds.add(deptId);
				}
			}
			Org orgXH = bpmUserQueryService
					.findDeptByCode(ReportCompanyEnum.XINHUI.getDeptCode());
			for (Long deptId : newDeptIds) {
				// fzrList.addAll(getRoleDeptUserIterates(deptId, fzrRole));
				List<SysUser> sysus = bpmUserQueryService
						.findChargeByOrgId(deptId);
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
				if (StringUtil.equals(String.valueOf(deptId),
						String.valueOf(orgXH.getId()))) {
					fgfzList.addAll(projectRelatedUserService
							.getFgfz(ReportCompanyEnum.XINHUI.getDeptCode()));
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
			// 部门负责人需要加入 info部门负责人
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
				throw ExceptionFactory.newFcsException(
						FcsResultEnum.EXECUTE_FAILURE, "明细部门分管副总未设置");
			}

			FlowVarField flowVarField2 = new FlowVarField();
			// 报销部门 需要加入 info部门
			flowVarField2.setVarName("master");
			flowVarField2.setVarType(FlowVarTypeEnum.STRING);
			flowVarField2.setVarVal(master);
			fields.add(flowVarField2);

			FlowVarField flowVarField3 = new FlowVarField();
			flowVarField3.setVarName("money");
			flowVarField3.setVarType(FlowVarTypeEnum.DOUBLE);
			flowVarField3.setVarVal(info.getAmount().toStandardString()
					.replace(",", ""));
			fields.add(flowVarField3);

			// 是否分支机构
			FlowVarField isBranch = new FlowVarField();
			isBranch.setVarName("isBranch");
			isBranch.setVarType(FlowVarTypeEnum.STRING);
			isBranch.setVarVal(isFzjg(info.getDeptName()) ? "IS" : "NO");
			fields.add(isBranch);

		} catch (Exception e) {
			logger.error("{}", e);
			throw ExceptionFactory.newFcsException(
					FcsResultEnum.EXECUTE_FAILURE, e.getMessage());
		}

		return fields;
	}

	/**
	 * 流程通过 同步信息到财务系统生成凭证号
	 */
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {

		try {
			FormExpenseApplicationInfo info = expenseApplicationService
					.queryByFormId(formInfo.getFormId());
			logger.info("完成费用支付处理：{}", info);

			// 冲销费用金额
			List<ExpenseCxDetailDO> cxDetailDOs = expenseCxDetailDAO
					.findByExpenseId(info.getExpenseApplicationId(), null, null);
			if (cxDetailDOs != null) {
				for (ExpenseCxDetailDO cxDetail : cxDetailDOs) {
					FormExpenseApplicationDetailDO expenseDetailDO = formExpenseApplicationDetailDAO
							.findById(cxDetail.getFromDetailId());
					if (expenseDetailDO != null) {
						expenseDetailDO.setFpAmount(expenseDetailDO
								.getFpAmount().add(cxDetail.getFpAmount()));
						expenseDetailDO.setXjAmount(expenseDetailDO
								.getXjAmount().add(cxDetail.getXjAmount()));
						formExpenseApplicationDetailDAO.update(expenseDetailDO);
					}
				}
			}
			// List<ExpenseCxDetailDO> cxDetailDOs =
			// expenseCxDetailDAO.findByExpenseId(info.getExpenseApplicationId(),
			// null, null);
			// HashMap<Long, FormExpenseApplicationDetailDO> emap = new
			// HashMap<Long, FormExpenseApplicationDetailDO>();
			// if(cxDetailDOs!=null){
			// Money cxAmount = Money.zero();
			// for(ExpenseCxDetailDO cxDetail : cxDetailDOs){
			// if(cxDetail.getFromDetailId()==0){
			// cxAmount = cxAmount.add(cxDetail.getFyAmount());
			// }else{
			// FormExpenseApplicationDetailDO expenseDetailDO =
			// formExpenseApplicationDetailDAO.findById(cxDetail.getFromDetailId());
			// if(expenseDetailDO!=null){
			// emap.put(cxDetail.getFromDetailId(), expenseDetailDO);
			// }
			// }
			// }
			//
			// for(ExpenseCxDetailDO cxDetail : cxDetailDOs){
			// if(cxDetail.getFromDetailId()>0){
			//
			// if(cxAmount.compareTo(Money.zero()) > 0 &&
			// expenseDetailDO!=null){
			// if(cxAmount.compareTo(cxDetail.getFpAmount())>0){
			// cxAmount = cxAmount.subtract(cxDetail.getFpAmount());
			// expenseDetailDO.setFpAmount(expenseDetailDO.getFpAmount().add(cxDetail.getFpAmount()));
			// }else{
			// cxAmount = Money.zero();
			// expenseDetailDO.setFpAmount(expenseDetailDO.getFpAmount().add(cxAmount));
			// }
			//
			// if(cxAmount.compareTo(cxDetail.getXjAmount())>0){
			// cxAmount = cxAmount.subtract(cxDetail.getXjAmount());
			// expenseDetailDO.setXjAmount(expenseDetailDO.getXjAmount().add(cxDetail.getXjAmount()));
			// }else{
			// cxAmount = Money.zero();
			// expenseDetailDO.setFpAmount(expenseDetailDO.getFpAmount().add(cxAmount));
			// }
			//
			//
			// formExpenseApplicationDetailDAO.update(expenseDetailDO);
			// }
			// }
			// }
			// }

			// 同步至财务系统
			expenseApplicationService.sync2FinancialSys(info);

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

			logger.info("设定为打款通过，formid=：" + formInfo.getFormId());
			// 待打款 【 审核中 待打款 已打款】
			FormExpenseApplicationDO expenseInfo = formExpenseApplicationDAO
					.findByFormId(formInfo.getFormId());
			expenseInfo.setAccountStatus(AccountStatusEnum.PAYED.code());
			formExpenseApplicationDAO.update(expenseInfo);
			logger.info("设定为打款通过 成功，info=：" + expenseInfo);

			// 同步预测数据
			expenseApplicationService.syncForecast(formInfo.getFormId(), true);

			Date now = FcsFmDomainHolder.get().getSysDate();
			// 对私冲销
			// / 2、选择冲销后，在财务确认环节，确认的银行账户为收款账户，审核通过后，在该账户加还款金额到银行账户余额中；
			// 对公冲销：
			// 1、当选择冲销时，报销金额大于预付款金额，则财务确认环节，确认的银行账户为付款账户，在审核通过后
			// （考虑到新加待付款的情况，该处为财务最后确认的环节为审核通过），在该银行账户余额扣减报销金额减去预付款金额的差额；
			// 2、当冲销时，费用种类有退预付款的时候，在财务确认环节为收款的银行账户，审核通过后，将该费用种类的金额加到财务确认的收款银行账户的余额中；

			// 判断需要冲销
			if (BooleanEnum.YES.code().equals(expenseInfo.getIsReverse())) {
				if (CostDirectionEnum.PUBLIC.code().equals(
						expenseInfo.getDirection())) {

					// 1.报销金额减去冲销金额 减去差额
					Money offAmount = Money.zero();
					if (expenseInfo.getReamount().greaterThan(Money.zero())) {
						offAmount = expenseInfo.getAmount().subtract(
								expenseInfo.getReamount());
					}
					if (offAmount.greaterThan(Money.zero())) {
						BankTradeOrder inOrder = new BankTradeOrder();
						inOrder.setAccountNo(expenseInfo.getPayBankAccount());
						inOrder.setAmount(offAmount);
						inOrder.setFundDirection(FundDirectionEnum.OUT);
						inOrder.setTradeTime(now);// 付款时间
						inOrder.setOutBizNo(expenseInfo.getBillNo());
						inOrder.setOutBizDetailNo(String.valueOf(expenseInfo
								.getExpenseApplicationId()));
						inOrder.setRemark("费用支付对公收款账户冲销处理 "
								+ expenseInfo.getBillNo() + "：");
						bankMessageService.trade(inOrder);
					}
					// 2.退预付款金额
					List<FormExpenseApplicationDetailDO> detailInfos = formExpenseApplicationDetailDAO
							.findByExpenseApplicationId(expenseInfo
									.getExpenseApplicationId());

					// 取费用类型
					CostCategoryQueryOrder order = new CostCategoryQueryOrder();
					order.setStatusList(new ArrayList<CostCategoryStatusEnum>());
					order.getStatusList().add(CostCategoryStatusEnum.NORMAL);
					order.setPageSize(10000);
					QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryService
							.queryPage(order);

					if (detailInfos != null && ListUtil.isNotEmpty(detailInfos)) {
						Money tyfkAmount = Money.zero();
						for (FormExpenseApplicationDetailDO detailDO : detailInfos) {
							String expenseType = detailDO.getExpenseType();
							if (batchResult != null
									&& ListUtil.isNotEmpty(batchResult
											.getPageList())) {
								for (CostCategoryInfo cInfo : batchResult
										.getPageList()) {
									if (String.valueOf(cInfo.getCategoryId())
											.equals(expenseType)) {
										expenseType = cInfo.getName();
										break;
									}
								}
							}
							if ("冲预付款".equals(expenseType)
									|| "退预付款".equals(expenseType)) {
								tyfkAmount.addTo(detailDO.getAmount());
							}
						}
						if (tyfkAmount.greaterThan(Money.zero())) {
							BankTradeOrder inOrder = new BankTradeOrder();
							inOrder.setAccountNo(expenseInfo
									.getPayBankAccount());
							inOrder.setAmount(tyfkAmount);
							inOrder.setFundDirection(FundDirectionEnum.IN);
							inOrder.setTradeTime(now);// 付款时间
							inOrder.setOutBizNo(expenseInfo.getBillNo());
							inOrder.setOutBizDetailNo(String
									.valueOf(expenseInfo
											.getExpenseApplicationId()));
							inOrder.setRemark("费用支付对私收款账户冲销处理 "
									+ expenseInfo.getBillNo() + "：");
							bankMessageService.trade(inOrder);
						}
					}

				} else if (CostDirectionEnum.PRIVATE.code().equals(
						expenseInfo.getDirection())) {
					logger.info("费用支付申请单审核通过增加相应银行账号金额：{}", expenseInfo);
					// 收款
					if (expenseInfo.getAmount().greaterThan(Money.zero())) {
						BankTradeOrder inOrder = new BankTradeOrder();
						inOrder.setAccountNo(expenseInfo.getPayBankAccount());
						inOrder.setAmount(expenseInfo.getAmount());
						inOrder.setFundDirection(FundDirectionEnum.IN);
						inOrder.setTradeTime(now);// 付款时间
						inOrder.setOutBizNo(expenseInfo.getBillNo());
						inOrder.setOutBizDetailNo(String.valueOf(expenseInfo
								.getExpenseApplicationId()));
						inOrder.setRemark("费用支付对私收款账户冲销处理 "
								+ expenseInfo.getBillNo() + "：");
						bankMessageService.trade(inOrder);
					}
				}
			} else {
				// 判断是分公司时抓取 分公司固定帐号
				boolean isFenGongSi = false;
				Set<Long> deptIds = new HashSet<Long>();

				List<String> deptNames = new ArrayList<String>();
				for (FormExpenseApplicationDetailInfo detailInfo : info
						.getDetailList()) {
					if (detailInfo.getDeptId() > 0) {
						deptIds.add(detailInfo.getDeptId());
						deptNames.add(detailInfo.getDeptName());
					}
				}
				boolean allFGX = true;
				for (String deptName : deptNames) {
					if (!checkIsFenGongSi(deptName)) {
						allFGX = false;
						break;
					}
				}
				String CQJCK = "CQJCK";
				Org orgCY = bpmUserQueryService.findDeptByCode(CQJCK);
				logger.info("orgCY查询结果：" + orgCY);
				if (orgCY != null
						&& (deptIds.contains(orgCY.getId()) || info
								.getExpenseDeptId() == orgCY.getId())) {
					logger.info("有总部！");
					Org org = bpmUserQueryService.findDeptById(formInfo
							.getDeptId());
					if (checkIsFenGongSi(org.getName())) {
						isFenGongSi = true;
					}
				} else {
					// info和明细都为财务部传财务部，明细全部包含分公司，传入分公司
					if (allFGX) {
						isFenGongSi = true;
					}
				}

				// 分公司按照子项分别计算
				if (isFenGongSi) {
					for (FormExpenseApplicationDetailInfo detailInfo : info
							.getDetailList()) {
						if (detailInfo.getAmount().greaterThan(Money.zero())) {
							BankTradeOrder inOrder = new BankTradeOrder();
							String accountNo = "";
							// 根据分公司名获取accountNo
							if (detailInfo.getDeptName().contains("分公司")) {
								accountNo = sysParameterServiceClient
										.getSysParameterValue(SysParamEnum.SYS_PARAM_FZJG_BF_ACCOUNT_NO
												.code());
							} else if (detailInfo.getDeptName().contains(
									"四川代表处")) {
								accountNo = sysParameterServiceClient
										.getSysParameterValue(SysParamEnum.SYS_PARAM_FZJG_SC_ACCOUNT_NO
												.code());
							} else if (detailInfo.getDeptName().contains(
									"云南代表处")) {
								accountNo = sysParameterServiceClient
										.getSysParameterValue(SysParamEnum.SYS_PARAM_FZJG_YN_ACCOUNT_NO
												.code());
							} else if (detailInfo.getDeptName().contains(
									"湖南代表处")) {
								accountNo = sysParameterServiceClient
										.getSysParameterValue(SysParamEnum.SYS_PARAM_FZJG_HN_ACCOUNT_NO
												.code());
							}
							inOrder.setAccountNo(accountNo);
							inOrder.setAmount(detailInfo.getAmount());
							inOrder.setFundDirection(FundDirectionEnum.OUT);
							inOrder.setTradeTime(now);// 付款时间
							inOrder.setOutBizNo(expenseInfo.getBillNo());
							inOrder.setOutBizDetailNo(String
									.valueOf(expenseInfo
											.getExpenseApplicationId()));
							inOrder.setRemark("费用支分支机构付收款账户 普通处理"
									+ expenseInfo.getBillNo() + "：");
							bankMessageService.trade(inOrder);
						}
					}

				} else {

					// 20161118 只有对公冲销和不冲销需要计算普通金额减
					// 总计金额减
					if (expenseInfo.getAmount().greaterThan(Money.zero())) {
						BankTradeOrder inOrder = new BankTradeOrder();
						// 20170322 分公司情况下固定帐号 分公司按照子项分别计算
						// if (isFenGongSi) {
						// inOrder.setAccountNo(fzjgdkzh);
						// } else {
						inOrder.setAccountNo(expenseInfo.getPayBankAccount());
						// }
						inOrder.setAmount(expenseInfo.getAmount());
						inOrder.setFundDirection(FundDirectionEnum.OUT);
						inOrder.setTradeTime(now);// 付款时间
						inOrder.setOutBizNo(expenseInfo.getBillNo());
						inOrder.setOutBizDetailNo(String.valueOf(expenseInfo
								.getExpenseApplicationId()));
						inOrder.setRemark("费用支付收款账户 普通处理"
								+ expenseInfo.getBillNo() + "：");
						inOrder.setIgnoreAccountIfNotExist(true);
						bankMessageService.trade(inOrder);
					}
				}

				// 2017-09-20 分支机构借款流程 分管副总审核取消、发送确认付款消息改到流程结束后
				if (formInfo.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_LOAN) {
					sendTofzjgAfterFgfz(formInfo, expenseInfo);
				}
			}

		} catch (Exception e) {
			logger.error("完成费用支付处理出错：{}", e);
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
			// FormExpenseApplicationInfo info =
			// expenseApplicationService.queryByFormId(form
			// .getFormId());
			FormExpenseApplicationDO expenseInfo = formExpenseApplicationDAO
					.findByFormId(form.getFormId());
			// 单据号：xxx- 费用支付(劳资类)审核流程-吴佳苏
			StringBuilder sb = new StringBuilder();
			sb.append("单据号：");
			sb.append(expenseInfo.getBillNo());
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
	public void startAfterProcess(FormInfo formInfo,
			WorkflowResult workflowResult) {
		expenseApplicationService.syncForecast(formInfo.getFormId(), false);
		logger.info("设定为审核中，formid=：" + formInfo.getFormId());
		FormExpenseApplicationDO expenseInfo = formExpenseApplicationDAO
				.findByFormId(formInfo.getFormId());
		expenseInfo.setAccountStatus(AccountStatusEnum.CHECKING.code());
		// 分支机构金额规则类
		if (formInfo.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_LIMIT
				&& isFzjg(expenseInfo.getDeptName())) {
			expenseInfo.setBranchPayStatus(BranchPayStatusEnum.AUDITING.code());
		} else {
			expenseInfo.setBranchPayStatus(BranchPayStatusEnum.NOT_BRANCH
					.code());
		}
		formExpenseApplicationDAO.update(expenseInfo);
		logger.info("设定审核中成功，info=：" + expenseInfo);

	}

	private List<SimpleUserInfo> getRoleDeptUserIterates(long deptId,
			String fzrRole) {
		List<SimpleUserInfo> roleDeptUser = projectRelatedUserService
				.getRoleDeptUser(deptId, fzrRole);
		if (roleDeptUser == null || roleDeptUser.size() == 0) { // 如果没有，就找其上级部门
			Org org = bpmUserQueryService.findDeptById(deptId);
			if (org != null && org.getParentId() > 0) {
				roleDeptUser = getRoleDeptUserIterates(org.getParentId(),
						fzrRole);
			}
		}
		return roleDeptUser;
	}

	@Override
	public void manualEndFlowProcess(FormInfo formInfo,
			WorkflowResult workflowResult) {
		noPassForecast(formInfo);
		// FormExpenseApplicationInfo info =
		// expenseApplicationService.queryByFormId(formInfo
		// .getFormId());
		FormExpenseApplicationDO expenseInfo = formExpenseApplicationDAO
				.findByFormId(formInfo.getFormId());
		if (formInfo.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_LIMIT
				&& isFzjg(expenseInfo.getDeptName())
				&& !StringUtil.equals(expenseInfo.getBranchPayStatus(),
						BranchPayStatusEnum.PAID.code())) {
			expenseInfo.setBranchPayStatus(BranchPayStatusEnum.CANCEL.code());
			formExpenseApplicationDAO.update(expenseInfo);
			logger.info("终止后分支机构付款状态处理：{}", expenseInfo);
		}

		logger.info("终止处理：{}", expenseInfo);
		if (StringUtil.isNotBlank(expenseInfo.getCxids())) {
			String[] strs = expenseInfo.getCxids().split(",");
			for (String str : strs) {
				FormExpenseApplicationDetailDO detailDO = formExpenseApplicationDetailDAO
						.findById(Long.valueOf(str));
				if (StringUtil.equals(detailDO.getReverse(),
						BooleanEnum.YES.code())) {
					detailDO.setReverse(BooleanEnum.NO.code());
					formExpenseApplicationDetailDAO.update(detailDO);
					logger.info("已更定为未冲销，id=：" + detailDO.getDetailId());
				}
			}
		}
	}

	@Override
	public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		noPassForecast(formInfo);
		// FormExpenseApplicationInfo info =
		// expenseApplicationService.queryByFormId(formInfo
		// .getFormId());
		FormExpenseApplicationDO expenseInfo = formExpenseApplicationDAO
				.findByFormId(formInfo.getFormId());
		if (formInfo.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_LIMIT
				&& isFzjg(expenseInfo.getDeptName())
				&& !StringUtil.equals(expenseInfo.getBranchPayStatus(),
						BranchPayStatusEnum.PAID.code())) {
			expenseInfo.setBranchPayStatus(BranchPayStatusEnum.CANCEL.code());
			formExpenseApplicationDAO.update(expenseInfo);
			logger.info("作废后分支机构付款状态处理：{}", expenseInfo);
		}
		logger.info("作废处理：{}", expenseInfo);
		if (StringUtil.isNotBlank(expenseInfo.getCxids())) {
			String[] strs = expenseInfo.getCxids().split(",");
			for (String str : strs) {
				FormExpenseApplicationDetailDO detailDO = formExpenseApplicationDetailDAO
						.findById(Long.valueOf(str));
				if (StringUtil.equals(detailDO.getReverse(),
						BooleanEnum.YES.code())) {
					detailDO.setReverse(BooleanEnum.NO.code());
					formExpenseApplicationDetailDAO.update(detailDO);
					logger.info("已更定为未冲销，id=：" + detailDO.getDetailId());
				}
			}
		}
		// 20161216 作废的单据 单据状态设定为作废
		FormExpenseApplicationDO expenseDO = formExpenseApplicationDAO
				.findById(expenseInfo.getExpenseApplicationId());
		expenseDO.setAccountStatus(AccountStatusEnum.END.code());
		formExpenseApplicationDAO.update(expenseDO);
	}

	@Override
	public void cancelAfterProcess(FormInfo formInfo,
			WorkflowResult workflowResult) {
		// 撤销表单后业务处理(BASE)
		// FormExpenseApplicationInfo info =
		// expenseApplicationService.queryByFormId(formInfo
		// .getFormId());
		FormExpenseApplicationDO expenseInfo = formExpenseApplicationDAO
				.findByFormId(formInfo.getFormId());

		if (formInfo.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_LIMIT
				&& isFzjg(expenseInfo.getDeptName())
				&& !StringUtil.equals(expenseInfo.getBranchPayStatus(),
						BranchPayStatusEnum.PAID.code())) {
			expenseInfo.setBranchPayStatus(BranchPayStatusEnum.AUDITING.code());
			formExpenseApplicationDAO.update(expenseInfo);
			logger.info("撤销后分支机构付款状态处理：{}", expenseInfo);
		}

		logger.info("撤回处理：{}", expenseInfo);
		if (StringUtil.isNotBlank(expenseInfo.getCxids())) {
			String[] strs = expenseInfo.getCxids().split(",");
			for (String str : strs) {
				FormExpenseApplicationDetailDO detailDO = formExpenseApplicationDetailDAO
						.findById(Long.valueOf(str));
				if (StringUtil.equals(detailDO.getReverse(),
						BooleanEnum.YES.code())) {
					detailDO.setReverse(BooleanEnum.NO.code());
					formExpenseApplicationDetailDAO.update(detailDO);
					logger.info("已更定为未冲销，id=：" + detailDO.getDetailId());
				}
			}
		}
	}

	/***
	 * 流程未审核通过预测数据变更
	 * 
	 * @param formInfo
	 */
	private void noPassForecast(FormInfo formInfo) {
		try {
			FormExpenseApplicationDO apply = formExpenseApplicationDAO
					.findByFormId(formInfo.getFormId());
			ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
			forecastAccountOrder.setOrderNo(apply.getBillNo());
			forecastAccountOrder.setSystemForm(SystemEnum.FM);
			forecastService.delete(forecastAccountOrder);
		} catch (Exception e) {
			logger.error("费用支付处理未通过表单预测数据出错：{}", e);
		}
	}

	@Override
	public void doNextAfterProcess(FormInfo formInfo,
			WorkflowResult workflowResult) {
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
			if (taskUrl
					.contains("/fundMg/expenseApplication/audit/canAccount.htm")) {// 下一步的url
				logger.info("设定为待打款，formid=：" + formInfo.getFormId());
				// 待打款 【 审核中 待打款 已打款】
				FormExpenseApplicationDO expenseInfo = formExpenseApplicationDAO
						.findByFormId(formInfo.getFormId());
				expenseInfo.setAccountStatus(AccountStatusEnum.WAIT_PAY.code());
				// 20161221 设定待打款时间
				expenseInfo
						.setWaitPayTime(FcsFmDomainHolder.get().getSysDate());
				formExpenseApplicationDAO.update(expenseInfo);
				logger.info("设定为待打款成功，info=：" + expenseInfo);
			}
			// 判断是分公司财务应付岗审批时。发送站内信
			if (taskUrl
					.contains("/fundMg/expenseApplication/audit/canFenGongSi.htm")) {// 下一步的url
				// 由XX（部门）X
				// XX（单据的发起人姓名）发起的费用支付申请单号XXX已由XXX审核通过（取对应分支机构的负责人名字），可执行付款操作，点击查看详情。
				// form.getdeptname form.getuserName 由申请部门的分管副总审核通过
				logger.info("发送分公司分支机构支付岗站内信，formid=：" + formInfo.getFormId());
				FormExpenseApplicationDO expenseInfo = formExpenseApplicationDAO
						.findByFormId(formInfo.getFormId());

				sendTofzjg(formInfo, expenseInfo, false);

				logger.info("发送分公司分支机构支付岗站内信结束，info=：" + expenseInfo);
			}

			// 金额规则类,分支机构分管副总审核后发消息给分支机构支付岗
			if (formInfo.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_LIMIT
					&& taskUrl.contains("afterFgfz=YES")) {
				FormExpenseApplicationDO expenseInfo = formExpenseApplicationDAO
						.findByFormId(formInfo.getFormId());
				sendTofzjgAfterFgfz(formInfo, expenseInfo);
			}
		}
		// 20161129 添加动作 驳回之后【判断状态】将子项设定为no
		if (FormStatusEnum.BACK == formInfo.getStatus()) {

			FormExpenseApplicationDO expenseInfo = formExpenseApplicationDAO
					.findByFormId(formInfo.getFormId());

			if (formInfo.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_LIMIT
					&& isFzjg(expenseInfo.getDeptName())
					&& !StringUtil.equals(expenseInfo.getBranchPayStatus(),
							BranchPayStatusEnum.PAID.code())) {
				expenseInfo.setBranchPayStatus(BranchPayStatusEnum.AUDITING
						.code());
				formExpenseApplicationDAO.update(expenseInfo);
				logger.info("驳回后分支机构付款状态处理：{}", expenseInfo);
			}

			logger.info("驳回处理：{}", expenseInfo);
			if (StringUtil.isNotBlank(expenseInfo.getCxids())) {
				String[] strs = expenseInfo.getCxids().split(",");
				for (String str : strs) {
					FormExpenseApplicationDetailDO detailDO = formExpenseApplicationDetailDAO
							.findById(Long.valueOf(str));
					if (StringUtil.equals(detailDO.getReverse(),
							BooleanEnum.YES.code())) {
						detailDO.setReverse(BooleanEnum.NO.code());
						formExpenseApplicationDetailDAO.update(detailDO);
						logger.info("已更定为未冲销，id=：" + detailDO.getDetailId());
					}
				}
			}
		}
	}

	/**
	 * 判断是否分支机构
	 * 
	 * @param deptName
	 * @return
	 */
	private boolean isFzjg(String deptName) {
		boolean match = false;
		String fzjgNames = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FZJG_DEPT_NAME
						.code());
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

	/**
	 * 分管副总审批后通知分支机构支付岗确认
	 * 
	 * @param formInfo
	 * @param expenseInfo
	 */
	private void sendTofzjgAfterFgfz(FormInfo formInfo,
			FormExpenseApplicationDO expenseInfo) {

		if (expenseInfo == null || !isFzjg(expenseInfo.getDeptName()))
			return;

		// 未付款的再更新
		if (!StringUtil.equals(expenseInfo.getBranchPayStatus(),
				BranchPayStatusEnum.PAID.code())) {
			// 设置待付款时间
			expenseInfo.setBranchWaitPayTime(getSysdate());
			// 设置成未付款
			expenseInfo.setBranchPayStatus(BranchPayStatusEnum.NOT_PAY.code());
			formExpenseApplicationDAO.update(expenseInfo);
		}
		// 表单站内地址
		String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/fundMg/expenseApplication/ealist.htm?from=query&billNo="
				+ expenseInfo.getBillNo();
		String forMessage = "<a href='" + messageUrl + "'>确认付款</a>";

		// 分支机构支付岗角色名
		String fzjgZfgRole = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FZJGZFQRG_ROLE_NAME
						.code());

		// 分支机构支付岗人员
		List<SimpleUserInfo> fzjgZfgUsers = projectRelatedUserService
				.getRoleDeptUser(expenseInfo.getExpenseDeptId(), fzjgZfgRole);

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

			SimpleUserInfo[] sendUsers = fzjgZfgUsers
					.toArray(new SimpleUserInfo[fzjgZfgUsers.size()]);
			// 替换审核地址
			// 由XX（部门）X XX（单据的发起人姓名）发起的费用支付申请单号XXX，可执行付款操作，点击确认付款。
			StringBuilder sb = new StringBuilder();
			sb.append("由 ");
			sb.append(formInfo.getDeptName());
			sb.append(" ");
			sb.append(formInfo.getUserName());
			sb.append("  发起的费用支付申请单号 ");
			sb.append(expenseInfo.getBillNo());
			sb.append(" 可执行付款操作，点击 ");
			String messageContent = sb.toString();
			messageContent += forMessage;
			if (StringUtil.isNotBlank(messageContent)) {
				MessageOrder messageOrder = MessageOrder
						.newSystemMessageOrder();
				messageOrder.setMessageSubject("确认付款提醒");
				messageOrder.setMessageTitle("确认付款提醒");
				messageOrder.setMessageContent(messageContent);
				messageOrder.setSendUsers(sendUsers);
				siteMessageService.addMessageInfo(messageOrder);
			}
		}
	}

	/**
	 * 
	 * @param formInfo
	 * @param expenseInfo
	 * @param sendAfterEnd
	 *            是否流程结束后发送
	 */
	private void sendTofzjg(FormInfo formInfo,
			FormExpenseApplicationDO expenseInfo, boolean sendAfterEnd) {
		// 表单站内地址
		String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/fundMg/expenseApplication/view.htm&formId="
				+ formInfo.getFormId();
		String forMessage = "<a href='" + messageUrl + "'>查看详情</a>";
		String subject = "审批通过提醒";
		List<SimpleUserInfo> notifyUserList = Lists.newArrayList();

		// 查询申请人所在部门的分管副总
		List<SimpleUserInfo> fgfzList = null;
		if (!sendAfterEnd) {
			fgfzList = new ArrayList<SimpleUserInfo>();
			String fgfzRole = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_FGFZ_ROLE_NAME
							.code());
			fgfzList.addAll(getRoleDeptUserIterates(formInfo.getDeptId(),
					fgfzRole));
			logger.info("fgfzList:" + fgfzList);
		}

		// 分支机构支付岗人员
		String fzjgzfqrgRole = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FZJGZFQRG_ROLE_NAME
						.code());

		// 抓取分支机构支付确认岗人员
		List<SimpleUserInfo> roleDeptUser = projectRelatedUserService
				.getRoleDeptUser(formInfo.getDeptId(), fzjgzfqrgRole);
		logger.info("roleDeptUser:" + roleDeptUser);

		if (ListUtil.isNotEmpty(roleDeptUser)) {

			for (SimpleUserInfo simpleUserInfo : roleDeptUser) {
				sendMessageTofzjgzfqrg(formInfo, expenseInfo, forMessage,
						subject, notifyUserList, fgfzList, simpleUserInfo);
			}

		}

	}

	private void sendMessageTofzjgzfqrg(FormInfo formInfo,
			FormExpenseApplicationDO expenseInfo, String forMessage,
			String subject, List<SimpleUserInfo> notifyUserList,
			List<SimpleUserInfo> fgfzList, SimpleUserInfo simpleUserInfo) {
		SysUser sendUser;
		sendUser = bpmUserQueryService.findUserByUserId(simpleUserInfo
				.getUserId());
		if (sendUser != null) {
			SimpleUserInfo userInfo = new SimpleUserInfo();
			userInfo.setUserId(sendUser.getUserId());
			userInfo.setUserName(sendUser.getFullname());
			userInfo.setUserAccount(sendUser.getAccount());
			userInfo.setEmail(sendUser.getEmail());
			userInfo.setMobile(sendUser.getMobile());
			notifyUserList.add(userInfo);
		}
		if (ListUtil.isNotEmpty(notifyUserList)) {

			SimpleUserInfo[] sendUsers = notifyUserList
					.toArray(new SimpleUserInfo[notifyUserList.size()]);
			// 替换审核地址
			// 由XX（部门）X
			// XX（单据的发起人姓名）发起的费用支付申请单号XXX已由XXX审核通过（取对应分支机构的负责人名字），可执行付款操作，点击查看详情。

			StringBuilder sb = new StringBuilder();
			sb.append("由 ");
			sb.append(formInfo.getDeptName());
			sb.append(" ");
			sb.append(formInfo.getUserName());
			sb.append("  发起的费用支付申请单号 ");
			sb.append(expenseInfo.getBillNo());
			if (ListUtil.isNotEmpty(fgfzList)) {
				sb.append(" 已由   ");
				sb.append(fgfzList.get(0).getUserName());
			}
			sb.append(" 审核通过  ，可执行付款操作，点击查看详情。 ");

			String messageContent = sb.toString();
			messageContent += forMessage;
			if (StringUtil.isNotBlank(messageContent)) {
				MessageOrder messageOrder = MessageOrder
						.newSystemMessageOrder();
				messageOrder.setMessageSubject(subject);
				if (ListUtil.isNotEmpty(fgfzList)) {
					messageOrder.setMessageTitle("费用支付分管副总审批通过提醒");
				} else {
					messageOrder.setMessageTitle("费用支付审批通过提醒");
				}
				messageOrder.setMessageContent(messageContent);
				messageOrder.setSendUsers(sendUsers);
				siteMessageService.addMessageInfo(messageOrder);
			}
		}
	}

}
