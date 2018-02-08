package com.born.fcs.pm.biz.service.check;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.crm.ws.service.enums.ChangeTypeEnum;
import com.born.fcs.crm.ws.service.order.CustomerDetailOrder;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.daointerface.FAfterwardsCreditConditionDAO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckBaseDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckLitigationDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckReportContentDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCreditConditionDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCustomerDO;
import com.born.fcs.pm.dal.dataobject.FFinancialKpiDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.DataFinancialHelper;
import com.born.fcs.pm.util.HttpClientUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CaseStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.KpiTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.expireproject.MessageAlertOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectResult;
import com.born.fcs.pm.ws.service.check.AfterwardsCheckService;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.expireproject.MessageAlertService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 保后检查报告流程处理
 * 
 * @author lirz
 * 
 * 2016-7-7 下午2:38:01
 */
@Service("afterwardsCheckProcessService")
public class AfterwardsCheckProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	private MessageAlertService messageAlertService;
	
	@Autowired
	CommonAttachmentService commonAttachmentService;
	
	@Autowired
	AfterwardsCheckService afterwardsCheckService;
	
	@Autowired
	ProjectCreditConditionService projectCreditConditionService;
	@Autowired
	protected FAfterwardsCreditConditionDAO FAfterwardsCreditConditionDAO;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		List<FlowVarField> fields = Lists.newArrayList();
		FAfterwardsCheckLitigationDO litigation = FAfterwardsCheckLitigationDAO
			.findByFormId(formInfo.getFormId());
		if (null != litigation) {
			FlowVarField lawManagerID = new FlowVarField();
			lawManagerID.setVarName("LawManagerID");
			lawManagerID.setVarType(FlowVarTypeEnum.STRING);
			lawManagerID.setVarVal("0");
			fields.add(lawManagerID);
			//法务
			ProjectRelatedUserInfo legalManger = projectRelatedUserService
				.getLegalManager(litigation.getProjectCode());
			if (null != legalManger) {
				lawManagerID.setVarVal(legalManger.getUserId() + "");
			}
		}
		
		return fields;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		
		FAfterwardsCustomerDO doObj = FAfterwardsCustomerDAO.findByFormId(order.getFormInfo()
			.getFormId());
		//如果修改了客户资料，得验证通过
		if (null != doObj && !StringUtil.equals(doObj.getStatus(), "1")) {
			result.setFcsResultEnum(FcsResultEnum.DO_ACTION_STATUS_ERROR);
			result.setMessage("客户资料验证不通过");
			result.setSuccess(false);
			return result;
		}
		
		FAfterwardsCheckDO checkDO = FAfterwardsCheckDAO.findByFormId(order.getFormInfo()
			.getFormId());
		
		//自定义待办任务名称
		WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get().getAttribute(
			"startOrder");
		if (startOrder != null) {
			startOrder.setCustomTaskName(checkDO.getProjectName() + "-保后检查报告审核");
		}
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 启动流程后业务处理
		FAfterwardsCheckDO checkDO = FAfterwardsCheckDAO.findByFormId(formInfo.getFormId());
		ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
		changeOrder.setPhase(ProjectPhasesEnum.AFTERWARDS_PHASES);
		changeOrder.setPhaseStatus(ProjectPhasesStatusEnum.AUDITING);
		changeOrder.setProjectCode(checkDO.getProjectCode());
		changeOrder.setStatus(ProjectStatusEnum.NORMAL);
		ProjectResult presult = projectService.changeProjectStatus(changeOrder);
		if (!presult.isSuccess()) {
			logger.error("更新项目状态出错(提交保后检查报告)：" + presult.getMessage());
		}
		
		//设置需要更新的access token
		FAfterwardsCustomerDO customer = FAfterwardsCustomerDAO.findByFormId(formInfo
			.getFormId());
		if (null != customer) {
			SimpleUserInfo userInfo = new SimpleUserInfo();
			userInfo.setUserId(customer.getUserId());
			userInfo.setUserName(customer.getUserName());
			userInfo.setUserAccount(customer.getUserAccount());
			
			//重新生成访问密钥
			String accessToken = getAccessToken(userInfo);
			customer.setAccessToken(accessToken);
			FAfterwardsCustomerDAO.update(customer);
		}
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAfterwardsCheckLitigationDO litigation = FAfterwardsCheckLitigationDAO
			.findByFormId(formId);
		if (null != litigation) {
			CaseStatusEnum caseStatus = CaseStatusEnum.getByCode(litigation.getCaseStatus());
			if (caseStatus == CaseStatusEnum.WIN || caseStatus == CaseStatusEnum.WITHDRAW) {
				//胜诉/客户撤诉 项目结束
				ProjectDO project = projectDAO.findByProjectCode(litigation.getProjectCode());
				//项目进入尽职调查阶段
				project.setPhases(ProjectPhasesEnum.FINISH_PHASES.code());
				project.setStatus(ProjectStatusEnum.NORMAL.code());
				project.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
				project.setEndTime(getSysdate());
				projectDAO.update(project);
				
				//项目有到期时间预测退换客户保证金
				projectService.syncForecastDeposit(project.getProjectCode());
			}
		}
		
		//首次风险等级评定与保后同步
		FAfterwardsCheckDO checkDO = FAfterwardsCheckDAO.findByFormId(formInfo.getFormId());
		ProjectDO projectDO = projectDAO.findByProjectCode(checkDO.getProjectCode());
		if (!ProjectPhasesEnum.FINISH_PHASES.code().equals(projectDO.getPhases())) {
			MessageAlertOrder order = new MessageAlertOrder();
			order.setProjectCode(checkDO.getProjectCode());
			order.setCustomerName(projectDO.getCustomerName());
			order.setAlertPhrase("RISK_LEVEL");
			messageAlertService.add(order);
		}
		
		FAfterwardsCheckDO FAfterwardsCheck = new FAfterwardsCheckDO();
		FAfterwardsCheck.setProjectCode(checkDO.getProjectCode());
		FAfterwardsCheck.setEdition(checkDO.getEdition());
		FAfterwardsCheckDAO.updateIsLasetByProjectCode(FAfterwardsCheck);
		
		checkDO.setIsLastest(BooleanEnum.YES.code());
		FAfterwardsCheckDAO.update(checkDO);
		
		// 授信条件记录下来(记录历史)
		saveConditions(checkDO.getProjectCode(), formId);
		
		//更新客户资料
		updateCustomerInfo(formId);
		//更新客户资产等信息
		syncCustomer2Crm(checkDO.getCustomerId(), formInfo);
	}
	
	private void updateCustomerInfo(long formId) {
		String prefix = "保后同步客户资料[" + formId + "]：";
		try {
			FAfterwardsCustomerDO doObj = FAfterwardsCustomerDAO.findByFormId(formId);
			if (null == doObj) {
				logger.info(prefix + "无需更新");
				return;
			}
			SimpleUserInfo userInfo = new SimpleUserInfo();
			userInfo.setUserId(doObj.getUserId());
			userInfo.setUserName(doObj.getUserName());
			userInfo.setUserAccount(doObj.getUserAccount());
			
//			2.获取财务报表的数据，同步到过去
			
			String intranetUrl = sysParameterService
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_INTRANET_URL.code());
			if (StringUtil.isBlank(intranetUrl)) {
				intranetUrl = sysParameterService
					.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL.code());
			}
			String formData = com.born.fcs.pm.util.StringUtil.decodeURI(doObj.getFormData());
			formData = formData
					+ "&loadMenu=NO&isFormChangeApply=IS&accessToken="
					+ doObj.getAccessToken();
			String exeUrl = intranetUrl + "/customerMg/customer/change/submit.json";
			String result = HttpClientUtil.post(exeUrl, formData, 120000);
			if (null != result) {
				logger.info(prefix + result);
			} else {
				logger.info(prefix + "返回结果为空");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(prefix + "异常 {}", e);
		}
	}
	
	private void syncCustomer2Crm(long customerId, FormInfo formInfo) {
		try {
			//成立时间、 法定代表人、 企业类型 页面无法修改 不同步
			//客户原始数据
			CustomerDetailOrder customerDetailOrder = getCustomerInfo(customerId);
			//修改来源设置
			customerDetailOrder.setChangeType(ChangeTypeEnum.BH);
			//修改人设置
			customerDetailOrder.setOperId(formInfo.getUserId());
			customerDetailOrder.setOperName(formInfo.getUserName());
			
			
			//1.获取财务报表的数据，同步到过去
			addFinancialInfo(customerDetailOrder, formInfo.getFormId());
			
			//同步到CRM
			logger.info("同步保后客户数据到CRM :{}", customerDetailOrder);
			
			FcsBaseResult syncResult = customerServiceClient
				.updateByUserId(customerDetailOrder);
			//				FcsBaseResult syncResult = companyCustomerServiceClient.update(fullInfo);
			logger.info("同步保后客户数据到CRM结果 :{}", syncResult);
				
		} catch (Exception e) {
			logger.error("同步保后客户数据到CRM出错{}", e);
		}
	}
	
	private void addFinancialInfo(CustomerDetailOrder order, long formId) {
		Map<String, FFinancialKpiDO> map = new HashMap<>();
		List<FFinancialKpiDO> list = FFinancialKpiDAO.findByFormIdAndKpitype(formId, KpiTypeEnum.FINANCIAL_TABLE.code());
		if (ListUtil.isNotEmpty(list)) {
			for (FFinancialKpiDO kpi : list) {
				map.put(kpi.getKpiCode(), kpi);
			}
		}
		
		//总资产
		String kpiCode = DataFinancialHelper.BALANCE_TOTAL_CAPITAL;
		Money totalAsset = queryFinancialKpi(map.get(kpiCode));
		if (null != totalAsset) {
			order.setTotalAsset(totalAsset); //设置总资产
		}
		//总负债
		kpiCode = DataFinancialHelper.BALANCE_TOTAL_LIABILITY;
		Money balance = queryFinancialKpi(map.get(kpiCode));
		if (null != totalAsset && null != balance) {
			order.setNetAsset(totalAsset.subtract(balance)); //设置净资产
			
			//资产负债率 = 负债合计/资产总计
			double rate = balance.getCent() * 100d / totalAsset.getCent();
			order.setAssetLiabilityRatio(rate); //设置资产负债率
		}
		//去年总资产
		kpiCode = DataFinancialHelper.BALANCE_TOTAL_CAPITAL;
		totalAsset = queryFinancialKpi(map.get(kpiCode), 1);
		if (null != totalAsset) {
			order.setTotalAssetLastYear(totalAsset); //设置去年总资产
		}
		//去年总负债
		kpiCode = DataFinancialHelper.BALANCE_TOTAL_LIABILITY;
		balance = queryFinancialKpi(map.get(kpiCode), 1);
		if (null != totalAsset && null != balance) {
			order.setNetAssetLastYear(totalAsset.subtract(balance)); //设置去年净资产
			
			//去年资产负债率 = 去年负债合计/去年资产总计
			double rate = balance.getCent() * 100d / totalAsset.getCent();
			order.setAssetLiabilityRatioLastYear(rate); //设置去年资产负债率
		}
		
		//流动比率=流动资产合计/流动负债合计
		Money m1 = queryFinancialKpi(map.get(DataFinancialHelper.BALANCE_TOTAL_FLOW_CAPITAL));
		Money m2 = queryFinancialKpi(map.get(DataFinancialHelper.BALANCE_TOTAL_CURRENT_LIABILITY));
		if (null != m1 && null != m2) {
			double rate = m1.getCent() * 100d / m2.getCent();
			order.setLiquidityRatio(rate); //设置流动比率
			
			//速动比率=（流动资产合计-待摊费用-存货）/流动负债合计
			Money m3 = queryFinancialKpi(map.get(DataFinancialHelper.BALANCE_UNAMORTIZED_EXPENSE));
			Money m4 = queryFinancialKpi(map.get(DataFinancialHelper.BALANCE_INVENTORY));
			if (null != m3 && null != m4) {
				rate = (m1.subtract(m3).subtract(m4)).getCent() * 100d / m2.getCent();
				order.setQuickRatio(rate); //设置速动比率
			}
		}
		
		map = new HashMap<>();
		list = FFinancialKpiDAO.findByFormIdAndKpitype(formId, KpiTypeEnum.PROFIT_TABLE.code());
		if (ListUtil.isNotEmpty(list)) {
			for (FFinancialKpiDO kpi : list) {
				map.put(kpi.getKpiCode(), kpi);
			}
		}
		
		//一、主营业务收入
		kpiCode = DataFinancialHelper.PROFIT_MAIN_BUSINESS_INCOME;
		Money income = queryFinancialKpi(map.get(kpiCode), 1);
		if (null != income) {
			order.setSalesProceedsLastYear(income); //设置去年销售收入
		}
		//四、利润总额
		kpiCode = DataFinancialHelper.PROFIT_TOTAL_PROFIT;
		income = queryFinancialKpi(map.get(kpiCode), 1);
		if (null != income) {
			order.setTotalProfitLastYear(income); //设置去年利润总额
		}
		//一、主营业务收入
		kpiCode = DataFinancialHelper.PROFIT_MAIN_BUSINESS_INCOME;
		income = queryFinancialKpi(map.get(kpiCode));
		if (null != income) {
			order.setSalesProceedsThisYear(income); //设置今年销售收入
		}
		//四、利润总额
		kpiCode = DataFinancialHelper.PROFIT_TOTAL_PROFIT;
		income = queryFinancialKpi(map.get(kpiCode));
		if (null != income) {
			order.setTotalProfitThisYear(income); //设置去今利润总额
		}
		
	}
	
	private Money queryFinancialKpi(FFinancialKpiDO kpi) {
		return queryFinancialKpi(kpi, 0);
	}
	
	private Money queryFinancialKpi(FFinancialKpiDO kpi, int index) {
		try {
			if (null != kpi) {
				if (index == 0) {
					return Money.amout(kpi.getKpiValue1());
				}
				if (index == 1) {
					return Money.amout(kpi.getKpiValue2());
				}
			}
		} catch (Exception e) {
			
		}
		return null;
	}
	
	private void saveConditions(String projectCode, long formId) {
		try {
			ProjectCreditConditionQueryOrder queryOrder = new ProjectCreditConditionQueryOrder();
			// 授信条件落实情况的显示 (条件：审核通过)
//			queryOrder.setStatus(CreditCheckStatusEnum.CHECK_PASS.code());
			queryOrder.setIsConfirm(BooleanEnum.IS.code());
			queryOrder.setProjectCode(projectCode);
			queryOrder.setPageNumber(1L);
			queryOrder.setPageSize(999L);
			QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionService
					.queryCreditCondition(queryOrder);
			if (null == batchResult || ListUtil.isEmpty(batchResult.getPageList())) {
				return;
			}
			
			int sortOrder = 1;
			Date now = getSysdate();
			for (ProjectCreditConditionInfo condition : batchResult.getPageList()) {
				FAfterwardsCreditConditionDO doObj = new FAfterwardsCreditConditionDO();
				doObj.setItemDesc(condition.getItemDesc());
				doObj.setFormId(formId);
				doObj.setType(null != condition.getType() ? condition.getType().code() : "");
				doObj.setConfirmManName(condition.getConfirmManName());
				doObj.setConfirmDate(condition.getConfirmDate());
				doObj.setContractNo(condition.getContractNo());
				doObj.setSortOrder(sortOrder++);
				doObj.setRawAddTime(now);
				FAfterwardsCreditConditionDAO.insert(doObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保后通过：保存授信落实条件历史数据异常[" + formId + "]");
		}
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		try {
			
			Map<String, Object> customizeMap = order.getCustomizeMap();
			logger.info("保后报告流程审核前处理开始 ：{}", customizeMap);
			long formId = order.getFormInfo().getFormId();
			Object editRiskReport = customizeMap.get("auditOpinion");
			if (null != editRiskReport) {
				//诉讼保函 法务填写风险意见
				FAfterwardsCheckLitigationDO litigation = FAfterwardsCheckLitigationDAO
					.findByFormId(formId);
				if (null != litigation) {
					litigation.setAuditOpinion(editRiskReport.toString());
					FAfterwardsCheckLitigationDAO.update(litigation);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("保后报告流程审核前处理出错{}", e);
		}
		return result;
	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		/** 删掉表单附件 start */
		if (formInfo.getFormCode() == FormCodeEnum.AFTERWARDS_CHECK_LITIGATION) {
			commonAttachmentService.deleteByBizNoModuleType(formInfo.getFormId() + "",
				CommonAttachmentTypeEnum.LEGAL_DOCUMENTS);
		} else {
			FAfterwardsCheckBaseDO base = FAfterwardsCheckBaseDAO
				.findByFormId(formInfo.getFormId());
			if (base != null && base.getBaseId() > 0) {
				commonAttachmentService.deleteByBizNoModuleType(base.getBaseId() + "",
					CommonAttachmentTypeEnum.CUSTOMER_OPINION,
					CommonAttachmentTypeEnum.AFTER_DATA_COLLECT);
			}
			FAfterwardsCheckReportContentDO content = FAfterwardsCheckReportContentDAO
				.findByFormId(formInfo.getFormId());
			if (content != null && content.getContentId() > 0) {
				commonAttachmentService.deleteByBizNoModuleType(content.getContentId() + "",
					CommonAttachmentTypeEnum.CONTENT_ATTACHMENT,
					CommonAttachmentTypeEnum.TAX_CERTIFICATE,
					CommonAttachmentTypeEnum.BANK_STATEMENT);
			}
			
			//删除房地产开发类在建项目附件
			commonAttachmentService.deleteByBizNoModuleType(formInfo.getFormId() + "",
				CommonAttachmentTypeEnum.AFTER_REAL_ESTATE_PROJECTING);
		}
		/** 删掉表单附件 end */
	}
}
