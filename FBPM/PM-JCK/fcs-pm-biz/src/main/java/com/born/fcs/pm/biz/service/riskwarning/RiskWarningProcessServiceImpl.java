package com.born.fcs.pm.biz.service.riskwarning;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FRiskWarningCreditDO;
import com.born.fcs.pm.dal.dataobject.FRiskWarningDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.enums.SignalLevelEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 风险预警审核流程处理
 * 
 * @author lirz
 * 
 * 2016-5-4 上午11:36:17
 */
@Service("riskWarningProcessService")
public class RiskWarningProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		//设置风险经理 获取客户相关项目的所有风险经理
		List<FlowVarField> fields = Lists.newArrayList();
		FlowVarField riskManagerId = new FlowVarField();
		riskManagerId.setVarName("RiskManagerID");
		riskManagerId.setVarType(FlowVarTypeEnum.STRING);
		riskManagerId.setVarVal("0"); //没有传0
		fields.add(riskManagerId);
		FRiskWarningDO riskWarning = FRiskWarningDAO.findByFormId(formInfo.getFormId());
		List<FRiskWarningCreditDO> creditDOs = FRiskWarningCreditDAO.findByWarningId(riskWarning
			.getWarningId());
		List<ProjectDO> projects = extraDAO.searchRiskWarningCustomerProjectList(riskWarning
			.getCustomerName());
		if (ListUtil.isEmpty(projects)) {
			return fields;
		}
		StringBuilder sb = new StringBuilder();
		Set<Long> userIdSet = new HashSet<>();
		for (FRiskWarningCreditDO project : creditDOs) {
			JSONObject jsonObject = MiscUtil.getJsonObjByParseJSON(project.getJosnData());
			if ("Y".equals(jsonObject.get("selectItem"))) {
				ProjectRelatedUserInfo relatedUser = projectRelatedUserService
					.getRiskManager(project.getProjectCode());
				if (null != relatedUser && relatedUser.getUserId() > 0) {
					if (!userIdSet.contains(relatedUser.getUserId())) {
						sb.append(relatedUser.getUserId() + ",");
						userIdSet.add(relatedUser.getUserId());
					}
				}
			}
		}
		if (StringUtil.isNotBlank(sb.toString())) {
			sb.deleteCharAt(sb.length() - 1);
			riskManagerId.setVarVal(sb.toString());
			return fields;
		}
		
		return fields;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		FRiskWarningDO riskWarning = FRiskWarningDAO.findByFormId(order.getFormInfo().getFormId());
		
		//自定义待办任务名称
		WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get().getAttribute(
			"startOrder");
		if (startOrder != null) {
			startOrder.setCustomTaskName(riskWarning.getCustomerName() + "-"
											+ riskWarning.getWarningBillType());
		}
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		FRiskWarningDO riskWarning = FRiskWarningDAO.findByFormId(formInfo.getFormId());
		if (riskWarning.getSrcWaningId() > 0) {
			FRiskWarningDO riskWarningDO = FRiskWarningDAO.findById(riskWarning.getSrcWaningId());
			if (riskWarningDO != null) {
				riskWarningDO.setSignalLevel(SignalLevelEnum.HAVE_LIFTED.code());
				FRiskWarningDAO.update(riskWarningDO);
			}
		}
	}
}
