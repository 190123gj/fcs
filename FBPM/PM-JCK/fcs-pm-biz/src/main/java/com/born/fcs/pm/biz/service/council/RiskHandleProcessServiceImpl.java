package com.born.fcs.pm.biz.service.council;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FCouncilApplyRiskHandleDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.council.CouncilApplyRiskHandleService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.yjf.common.lang.util.StringUtil;

/**
 * 风险处置上会流程处理
 * 
 * @author lirz
 * 
 * 2016-5-10 下午5:57:53
 */
@Service("riskHandleProcessService")
public class RiskHandleProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	protected CouncilApplyService councilApplyService;
	@Autowired
	protected CouncilApplyRiskHandleService councilApplyRiskHandleService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		FCouncilApplyRiskHandleDO riskHandle = FCouncilApplyRiskHandleDAO.findByFormId(formInfo
			.getFormId());
		if (null == riskHandle) {
			return null;
		}
		
		List<FlowVarField> fields = Lists.newArrayList();
		if (StringUtil.isNotBlank(riskHandle.getProjectCode())) {
			String[] projectCodes = riskHandle.getProjectCode().split(",");
			String riskManagerIDs = "";
			Map<Long, Boolean> riskManagerMap = new HashMap<Long, Boolean>();
			for (String projectCode : projectCodes) {
				//风险经理ID
				ProjectRelatedUserInfo riskManager = projectRelatedUserService
					.getRiskManager(projectCode);
				if (riskManager != null) {
					if (!riskManagerMap.containsKey(riskManager.getUserId())) {
						
						if (riskManagerIDs.length() == 0) {
							riskManagerIDs = String.valueOf(riskManager.getUserId());
						} else {
							riskManagerIDs += "," + String.valueOf(riskManager.getUserId());
						}
						
						riskManagerMap.put(riskManager.getUserId(), Boolean.TRUE);
					}
				}
			}
			if (StringUtil.isNotBlank(riskManagerIDs)) {
				FlowVarField riskManagerId = new FlowVarField();
				riskManagerId.setVarName("RiskManagerID");
				riskManagerId.setVarType(FlowVarTypeEnum.STRING);
				riskManagerId.setVarVal(riskManagerIDs);
				fields.add(riskManagerId);
			} else {
				FlowVarField riskManagerId = new FlowVarField();
				riskManagerId.setVarName("RiskManagerID");
				riskManagerId.setVarType(FlowVarTypeEnum.STRING);
				riskManagerId.setVarVal("10000000520056"); //风经1 cq-09
				fields.add(riskManagerId);
			}
		}
		
		String daichang = "N";
		if ("Y".equals(riskHandle.getIsRepay())) {
			daichang = "Y";
		}
		FlowVarField isRepay = new FlowVarField();
		isRepay.setVarName("daichang");
		isRepay.setVarType(FlowVarTypeEnum.STRING);
		isRepay.setVarVal(daichang);
		fields.add(isRepay);
		
		return fields;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		FCouncilApplyRiskHandleDO riskWarning = FCouncilApplyRiskHandleDAO.findByFormId(order
			.getFormInfo().getFormId());
		if (StringUtil.isBlank(riskWarning.getProjectCode())) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM, "没有选择项目");
		}
		//		String[] projectCodes = riskWarning.getProjectCode().split(",");
		//		for (String projectCode : projectCodes) {
		//			CouncilApplyRiskHandleQueryOrder queryOrder = new CouncilApplyRiskHandleQueryOrder();
		//			queryOrder.setProjectCode(projectCode);
		//			queryOrder.setPageSize(999);
		//			QueryBaseBatchResult<CouncilApplyRiskHandleInfo> batchResult = councilApplyRiskHandleService
		//				.queryList(queryOrder);
		//			for (CouncilApplyRiskHandleInfo handleInfo : batchResult.getPageList()) {
		//				if (handleInfo.getFormStatus() == FormStatusEnum.AUDITING
		//					|| handleInfo.getFormStatus() == FormStatusEnum.BACK
		//					|| handleInfo.getFormStatus() == FormStatusEnum.SUBMIT) {
		//					ProjectInfo projectInfo = projectService.queryByCode(projectCode, false);
		//					throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
		//						"项目[" + projectInfo.getProjectCode() + "]" + projectInfo.getProjectName()
		//								+ ", 已经在上会申报中");
		//				}
		//			}
		//			CouncilApplyQueryOrder councilApplyQueryOrder = new CouncilApplyQueryOrder();
		//			councilApplyQueryOrder.setCouncilCode(CouncilTypeEnum.RISK_HANDLE.code());
		//			councilApplyQueryOrder.setProjectCode(projectCode);
		//			councilApplyService.queryCouncilApply(councilApplyQueryOrder);
		//		}
		//自定义待办任务名称
		WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get().getAttribute(
			"startOrder");
		if (startOrder != null) {
			startOrder.setCustomTaskName(riskWarning.getProjectName() + "-风险处置上会申报表");
		}
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//流程结束，申请上会
		FCouncilApplyRiskHandleDO riskHandle = FCouncilApplyRiskHandleDAO.findByFormId(formInfo
			.getFormId());
		if (StringUtil.isNotBlank(riskHandle.getProjectCode())) {
			String[] projectCodes = riskHandle.getProjectCode().split(",");
			for (String projectCode : projectCodes) {
				ProjectInfo projectInfo = projectService.queryByCode(projectCode, false);
				CouncilApplyOrder order = new CouncilApplyOrder();
				order.setCouncilTypeDesc(CouncilTypeEnum.RISK_HANDLE.message());
				order.setCouncilCode(CouncilTypeEnum.RISK_HANDLE.code());
				order.setFormId(formInfo.getFormId());
				order.setProjectCode(projectInfo.getProjectCode());
				order.setProjectName(projectInfo.getProjectName());
				order.setCustomerId(projectInfo.getCustomerId());
				order.setCustomerName(projectInfo.getCustomerName());
				order.setAmount(projectInfo.getAmount());
				order.setApplyManId(formInfo.getUserId());
				order.setApplyManName(formInfo.getUserName());
				order.setApplyDeptId(formInfo.getDeptId());
				order.setApplyDeptName(formInfo.getDeptName());
				order.setApplyTime(getSysdate());
				order.setStatus(CouncilApplyStatusEnum.WAIT.code());
				order.setTimeLimit(projectInfo.getTimeLimit());
				if (projectInfo.getTimeUnit() != null)
					order.setTimeUnit(projectInfo.getTimeUnit().code());
				FcsBaseResult result = councilApplyService.saveCouncilApply(order);
				if (!result.isSuccess()) {
					logger.info("写入上会数据异常(风险处置)：" + result);
				}
			}
		}
		
	}
	
}
