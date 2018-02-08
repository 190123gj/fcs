package com.born.fcs.pm.biz.service.financialproject;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductSellStatusEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.order.basicmaintain.FinancialProductOrder;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.basicmaintain.FinancialProductService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Service("financialProjectSetupProcessService")
public class FinancialProjectSetupProcessService extends BaseProcessService {
	
	@Autowired
	FinancialProjectSetupService financialProjectSetupService;
	
	@Autowired
	FinancialProductService financialProductService;
	
	@Autowired
	CouncilApplyService councilApplyService;
	
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FProjectFinancialInfo apply = financialProjectSetupService.queryByFormId(formInfo
			.getFormId());
		vars.put("项目编号", apply.getProjectCode());
		vars.put("产品名称", apply.getProductName());
		String customVar = "";
		if (formInfo.getStatus() == FormStatusEnum.APPROVAL) {
			customVar = "快去购买吧!";
		}
		vars.put("自定义信息", customVar);
		return vars;
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		List<FlowVarField> fields = Lists.newArrayList();
		try {
			FProjectFinancialInfo project = financialProjectSetupService.queryByFormId(formInfo
				.getFormId());
			//是否上会
			FlowVarField whether = new FlowVarField();
			whether.setVarName("Whether");
			whether.setVarType(FlowVarTypeEnum.STRING);
			whether.setVarVal(project.getCouncilType() == ProjectCouncilEnum.SELF_PR ? "1" : "0");
			fields.add(whether);
			//上会类型
			//			FlowVarField councilType = new FlowVarField();
			//			councilType.setVarName("councilType");
			//			councilType.setVarType(FlowVarTypeEnum.STRING);
			//			councilType.setVarVal(project.getCouncilType() == null ? "" : project.getCouncilType()
			//				.code());
			//			fields.add(councilType);
		} catch (Exception e) {
			logger.error("设置流程参数出错：{}", e);
		}
		return fields;
	}
	
	/**
	 * 是否属于信惠人员
	 * @return
	 */
	private boolean isBelong2Xinhui(long userId) {
		boolean isXinHui = false;
		UserDetailInfo userDetail = bpmUserQueryService.findUserDetailByUserId(userId);
		Org org = bpmUserQueryService.findDeptByCode(sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code()));
		if (userDetail != null && org != null && userDetail.isBelong2Dept(org.getId())) {
			isXinHui = true;
		}
		return isXinHui;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FProjectFinancialInfo project = financialProjectSetupService.queryByFormId(order
				.getFormInfo().getFormId());
			
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(project.getProductName() + "-理财项目立项申请");
			}
			result.setSuccess(true);
			
			FormDO form = (FormDO) FcsPmDomainHolder.get().getAttribute("formDO");
			if (form != null) {
				form.setRelatedProjectCode(project.getProjectCode());
			}
			
			try {
				SimpleFormOrder submitOrder = (SimpleFormOrder) FcsPmDomainHolder.get()
					.getAttribute("submitFormOrder");
				if (submitOrder != null)
					submitOrder.setRelatedProjectCode(project.getProjectCode());
				
			} catch (Exception e) {
				logger.error("获取提交Order出错：{}", e);
			}
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("理财项目立项流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			FProjectFinancialDO project = FProjectFinancialDAO.findByFormId(formInfo.getFormId());
			if (project.getProductId() == 0) {
				logger.info("理财产品立项新增理财产品：{}", project);
				FinancialProductOrder productOrder = new FinancialProductOrder();
				BeanCopier.staticCopy(project, productOrder, UnBoxingConverter.getInstance());
				productOrder.setProductId(0l);
				productOrder.setSellStatus(FinancialProductSellStatusEnum.SELLING.code());
				FcsBaseResult result = financialProductService.save(productOrder);
				if (result.isSuccess()) {
					project.setProductId(result.getKeyId());
					FProjectFinancialDAO.update(project);
				}
			}
			
			//同步到资金系统
		} catch (Exception e) {
			logger.error("理财产品立项提交后处理出错：{}", e);
		}
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		try {
			final Map<String, Object> customizeMap = order.getCustomizeMap();
			if (customizeMap != null) {
				if ("YES".equals((String) customizeMap.get("chooseCouncil"))) { //选择是否上会及上会类型
					logger.info("理财产品立项选择上会类型处理开始：{}", customizeMap);
					String councilType = (String) customizeMap.get("councilType");
					String isNeedCouncil = (String) customizeMap.get("isNeedCouncil");
					FProjectFinancialDO apply = FProjectFinancialDAO.findByFormId(order
						.getFormInfo().getFormId());
					if (BooleanEnum.NO.equals(isNeedCouncil)) {//不需要上会
						apply.setCouncilType(null);
					} else { //需要上会
						apply.setCouncilType(councilType);
					}
					FProjectFinancialDAO.update(apply);
					logger.info("理财产品立项选择上会类型处理结束：{}", customizeMap);
				}
				
				//选择风险经理
				String selectRiskManager = (String) customizeMap.get("chooseRiskManager");
				if (BooleanEnum.YES.code().equals(selectRiskManager)) {
					long riskManagerId = NumberUtil.parseLong((String) customizeMap
						.get("riskManagerId"));
					String riskManagerAccount = (String) customizeMap.get("riskManagerAccount");
					String riskManagerName = (String) customizeMap.get("riskManagerName");
					
					FProjectFinancialDO apply = FProjectFinancialDAO.findByFormId(order
						.getFormInfo().getFormId());
					//保存风险到相关人员表
					ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
					relatedUser.setProjectCode(apply.getProjectCode());
					relatedUser.setUserType(ProjectRelatedUserTypeEnum.RISK_MANAGER);
					relatedUser.setRemark("立项审核,选择风险经理");
					relatedUser.setUserId(riskManagerId);
					relatedUser.setUserAccount(riskManagerAccount);
					relatedUser.setUserName(riskManagerName);
					projectRelatedUserService.setRelatedUser(relatedUser);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("选择上会类型出错");
			logger.error("理财产品立项选择上会类型出错 {} {}", order, e);
		}
		return result;
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		try {
			FProjectFinancialDO project = FProjectFinancialDAO.findByFormId(formInfo.getFormId());
			//上会处理
			ProjectCouncilEnum councilType = ProjectCouncilEnum.getByCode(project.getCouncilType());
			if (councilType == null) {//不上会的
				financialProjectSetupService.syncForecast(project.getProjectCode());
			} else if (councilType == ProjectCouncilEnum.SELF_PR
						&& !isBelong2Xinhui(project.getCreateUserId())) { //选择了上会、总部（非信惠）
				logger.info("理财产品立项选择上会处理开始（总部评审会） {} {} ", project.getProjectCode(), councilType);
				project.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_SENDING.code());
				FProjectFinancialDAO.update(project);
				logger.info("理财项目立项进入送审流程  {} , {}", project.getProjectCode());
			} else { //选择了上会(信惠)
				logger.info("理财产品立项选择上会处理开始 （信惠/总经理办公会）{} {} ", project.getProjectCode(),
					councilType);
				CouncilApplyOrder order = new CouncilApplyOrder();
				order.setProjectCode(project.getProjectCode());
				order.setProjectName(project.getProductName());
				order.setCustomerId(0);
				order.setCustomerName("-");
				order.setApplyManId(project.getCreateUserId());
				order.setApplyManName(project.getCreateUserName());
				order.setApplyDeptId(project.getDeptId());
				order.setApplyDeptName(project.getDeptName());
				order.setApplyTime(getSysdate());
				order.setStatus(CouncilApplyStatusEnum.WAIT.code());
				if (councilType == ProjectCouncilEnum.SELF_MOTHER_GW) {//总经理办公会+母公司办公会
					order.setMotherCompanyApply(BooleanEnum.YES);
					order.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
					order.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
					order.setMotherCouncilCode(CouncilTypeEnum.GM_WORKING.code());
				} else if (councilType == ProjectCouncilEnum.SELF_GW_MONTHER_PR) { //总经理办公会+母公司项目评审会
					order.setMotherCompanyApply(BooleanEnum.YES);
					order.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
					order.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
					order.setMotherCouncilCode(CouncilTypeEnum.PROJECT_REVIEW.code());
				} else if (councilType == ProjectCouncilEnum.SELF_PR) { //项目评审会
					order.setMotherCompanyApply(BooleanEnum.NO);
					order.setCouncilCode(CouncilTypeEnum.PROJECT_REVIEW.code());
					order.setCouncilTypeDesc(CouncilTypeEnum.PROJECT_REVIEW.message());
				} else {//总经理办公会
					order.setMotherCompanyApply(BooleanEnum.NO);
					order.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
					order.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
				}
				FcsBaseResult sResult = councilApplyService.saveCouncilApply(order);
				logger.info("理财项目立项进入待上会列表  （信惠）{} , {}", project.getProjectCode(), sResult);
				if (sResult.isSuccess()) {
					//记录上会的申请ID
					project.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_WAITING.code());
					project.setCouncilApplyId(sResult.getKeyId());
					FProjectFinancialDAO.update(project);
				}
			}
		} catch (Exception e) {
			logger.error("理财产品立项流程结束处理出错：{}", e);
		}
	}
}
