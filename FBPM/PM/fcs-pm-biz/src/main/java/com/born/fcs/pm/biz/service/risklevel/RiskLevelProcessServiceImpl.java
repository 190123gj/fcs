package com.born.fcs.pm.biz.service.risklevel;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FRiskLevelDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.CheckPhaseEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * 风险评级流程处理
 * 
 * @author lirz
 *
 * 2016-5-19 上午9:36:44
 */
@Service("riskLevelProcessService")
public class RiskLevelProcessServiceImpl extends BaseProcessService {
	
	private static final String FLAG = "项目风险等级评级流程处理：";
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		FRiskLevelDO doObj = FRiskLevelDAO.findByFormId(formInfo.getFormId());
		if (null == doObj) {
			return null;
		}
		
		List<FlowVarField> fields = Lists.newArrayList();
		//风险经理ID
		ProjectRelatedUserInfo riskManager = projectRelatedUserService.getRiskManager(doObj
			.getProjectCode());
		FlowVarField riskManagerId = new FlowVarField();
		riskManagerId.setVarName("RiskManagerID");
		riskManagerId.setVarType(FlowVarTypeEnum.STRING);
		if (null != riskManager) {
			riskManagerId.setVarVal(riskManager.getUserId() + "");
		} else {
			riskManagerId.setVarVal("0");
			logger.info("项目风险等级评级，没有找到风险经理：" + doObj.getProjectCode());
		}
		fields.add(riskManagerId);
		
		FRiskLevelDO riskLevel = FRiskLevelDAO.findByFormId(formInfo.getFormId());
		FlowVarField same = new FlowVarField();
		same.setVarName("same");
		same.setVarType(FlowVarTypeEnum.STRING);
		same.setVarVal("N"); 
		if (riskLevel.getReevaluationId() > 0
			&& StringUtil.isNotBlank(riskLevel.getEvaluationLevel())
			&& StringUtil.isNotBlank(riskLevel.getReevaluationLevel())
			&& StringUtil.equals(riskLevel.getEvaluationLevel(), riskLevel.getReevaluationLevel())) {
			same.setVarVal("Y");
		}
		fields.add(same);
		
		FlowVarField fgSame = new FlowVarField();
		fgSame.setVarName("fgSame");
		fgSame.setVarType(FlowVarTypeEnum.STRING);
		fgSame.setVarVal("N");
//		if (StringUtil.isNotBlank(riskLevel.getCheckLevel1())
//			&& StringUtil.isNotBlank(riskLevel.getCheckLevel2())
//			&& StringUtil.equals(riskLevel.getCheckLevel1(), riskLevel.getCheckLevel2())) {
//			fgSame.setVarVal("Y");
//		}
		Object obj = FcsPmDomainHolder.get().getAttribute("customizeMap");
		if (null != obj) {
			Map<String, Object> customizeMap = (Map<String, Object>)obj;
			Object checkLevel2 = customizeMap.get("checkLevel2");
			if (null != checkLevel2 && StringUtil.isNotBlank(checkLevel2.toString())) {
				String check2 = checkLevel2.toString();
				if (StringUtil.isNotBlank(riskLevel.getCheckLevel1())
					&& StringUtil.isNotBlank(check2)
					&& StringUtil.equals(riskLevel.getCheckLevel1(), check2)) {
					fgSame.setVarVal("Y");
				}
			}
		}
		fields.add(fgSame);
		
		
		return fields;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		FRiskLevelDO doObj = FRiskLevelDAO.findByFormId(order.getFormInfo().getFormId());
		
		//自定义待办任务名称
		WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get().getAttribute(
			"startOrder");
		if (startOrder != null) {
			startOrder.setCustomTaskName(doObj.getProjectName() + "-项目风险等级评定");
		}
		result.setSuccess(true);
		return result;
	}
	
//	@Override
//	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
//		FcsBaseResult result = createResult();
//		try {
//			
//			Map<String, Object> customizeMap = order.getCustomizeMap();
//			logger.info("项目风险等级评定流程审核前处理开始 ：{}", customizeMap);
//			long formId = order.getFormInfo().getFormId();
//			
//			Object checkLevel1 = customizeMap.get("checkLevel1");
//			if (null != checkLevel1 && StringUtil.isNotBlank(checkLevel1.toString())) {
//				FRiskLevelDO doObj = FRiskLevelDAO.findByFormId(formId);
//				doObj.setCheckLevel1(checkLevel1.toString());
//				FRiskLevelDAO.update(doObj);
//			}
//			Object checkLevel2 = customizeMap.get("checkLevel2");
//			if (null != checkLevel2 && StringUtil.isNotBlank(checkLevel2.toString())) {
//				FRiskLevelDO doObj = FRiskLevelDAO.findByFormId(formId);
//				doObj.setCheckLevel2(checkLevel2.toString());
//				FRiskLevelDAO.update(doObj);
//			}
//			
//			result.setSuccess(true);
//		} catch (Exception e) {
//			result.setSuccess(false);
//			logger.error("尽职调查流程审核前处理出错{}", e);
//		}
//		return result;
//	}
	
	@Override
	public void doNextAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 审核后业务处理(BASE)
		try {
			Map<String, Object> customizeMap = workflowResult.getCustomizeMap();
			logger.info(FLAG + "流程审核后处理开始 ：{}", customizeMap);
			long formId = formInfo.getFormId();
			
			Object checkLevel1 = customizeMap.get("checkLevel1");
			if (null != checkLevel1 && StringUtil.isNotBlank(checkLevel1.toString())) {
				FRiskLevelDO doObj = FRiskLevelDAO.findByFormId(formId);
				doObj.setCheckLevel1(checkLevel1.toString());
				doObj.setCheckLevel(checkLevel1.toString());
				FRiskLevelDAO.update(doObj);
			}
			Object checkLevel2 = customizeMap.get("checkLevel2");
			if (null != checkLevel2 && StringUtil.isNotBlank(checkLevel2.toString())) {
				FRiskLevelDO doObj = FRiskLevelDAO.findByFormId(formId);
				doObj.setCheckLevel2(checkLevel2.toString());
				doObj.setCheckLevel(checkLevel2.toString());
				FRiskLevelDAO.update(doObj);
			}
			Object checkLevel3 = customizeMap.get("checkLevel3");
			if (null != checkLevel3 && StringUtil.isNotBlank(checkLevel3.toString())) {
				FRiskLevelDO doObj = FRiskLevelDAO.findByFormId(formId);
				doObj.setCheckLevel3(checkLevel3.toString());
				doObj.setCheckLevel(checkLevel3.toString());
				FRiskLevelDAO.update(doObj);
			}
			
			//驳回 返回初评阶段
			Object backToStart = customizeMap.get("backToStart");
			if (null != backToStart && "YES".equals(backToStart.toString())) {
				FRiskLevelDO doObj = FRiskLevelDAO.findByFormId(formId);
				doObj.setCheckPhase(CheckPhaseEnum.E.code());
				FRiskLevelDAO.update(doObj);
			}
		} catch (Exception e) {
			logger.error(FLAG + "审核后处理出错{}", e);
			e.printStackTrace();
		}
	}
}
