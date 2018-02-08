package com.born.fcs.crm.biz.service.channal;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.crm.biz.service.Date.SqlDateService;
import com.born.fcs.crm.biz.service.base.BaseProcessService;
import com.born.fcs.crm.dal.daointerface.ChannalContractDAO;
import com.born.fcs.crm.dal.dataobject.ChannalContractDO;
import com.born.fcs.crm.domain.context.FcsCrmDomainHolder;
import com.born.fcs.crm.integration.bpm.result.WorkflowResult;
import com.born.fcs.crm.integration.pm.service.ProjectRelatedUserServicePmClient;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

@Service("channalContractProcessService")
public class ChannalContractProcessService extends BaseProcessService {
	@Autowired
	protected ChannalContractDAO channalContractDAO;
	@Autowired
	protected SqlDateService sqlDateService;
	@Autowired
	protected ProjectRelatedUserServicePmClient projectRelatedUserServiceClient;
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {//自定义待办任务名称
		FcsBaseResult result = new FcsBaseResult();
		WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsCrmDomainHolder.get().getAttribute(
			"startOrder");
		String channalName = "";
		if (startOrder != null) {
			FormInfo form = order.getFormInfo();
			if (form != null) {
				ChannalContractDO contractInfo = channalContractDAO.findById(order.getFormInfo()
					.getFormId());
				
				if (contractInfo != null) {
					channalName = contractInfo.getChannalName();
				}
			}
			
			if (StringUtil.isNotBlank(channalName)) {
				startOrder.setCustomTaskName(channalName + "-渠道合同审批流程");
			}
		}
		result.setSuccess(true);
		result.setMessage("自定义渠道合同流程名成功");
		return result;
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		List<FlowVarField> vars = Lists.newArrayList();
		try {
			Map<String, Object> customizeMap = (Map<String, Object>) FcsCrmDomainHolder.get()
				.getAttribute("customizeMap");
			List<FlowVarField> list = Lists.newArrayList();
			FlowVarField field = new FlowVarField();
			field.setVarName("lawManager");
			field.setVarType(FlowVarTypeEnum.STRING);
			if (customizeMap != null && !customizeMap.isEmpty()
				&& customizeMap.get("legalManagerId") != null) {
				String legalManagerId = (String) customizeMap.get("legalManagerId");
				field.setVarVal(String.valueOf(legalManagerId));
			} else {
				field.setVarVal("0");
			}
			
			list.add(field);
			return list;
		} catch (Exception e) {
			logger.error("评级审核设置流程变量出错：", e);
		}
		return vars;
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
	}
}
