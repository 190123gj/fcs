package com.born.fcs.pm.biz.service.risklevel;

import java.util.List;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FRiskLevelDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

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
		if (null != riskManager) {
			FlowVarField riskManagerId = new FlowVarField();
			riskManagerId.setVarName("RiskManagerID");
			riskManagerId.setVarType(FlowVarTypeEnum.STRING);
			riskManagerId.setVarVal(riskManager.getUserId() + "");
			fields.add(riskManagerId);
		} else {
			FlowVarField riskManagerId = new FlowVarField();
			riskManagerId.setVarName("RiskManagerID");
			riskManagerId.setVarType(FlowVarTypeEnum.STRING);
			riskManagerId.setVarVal("10000000520056"); //风经1 cq-09
			fields.add(riskManagerId);
		}
		
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
}
