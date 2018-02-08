package com.born.fcs.pm.biz.service.assist;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FInternalOpinionExchangeUserDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.assist.FInternalOpinionExchangeInfo;
import com.born.fcs.pm.ws.info.assist.FInternalOpinionExchangeUserInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.SimpleFormAuditOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.assist.InternalOpinionExchangeService;

/**
 * 内审意见交换
 * @author wuzj
 */
@Service("internalOpinionExchangeProcessService")
public class InternalOpinionExchangeProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	InternalOpinionExchangeService internalOpinionExchangeService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		List<FlowVarField> fields = Lists.newArrayList();
		try {
			
			//所有审计人员和负责人
			List<FInternalOpinionExchangeUserInfo> exchangeInfo = internalOpinionExchangeService
				.findUserByFormId(formInfo.getFormId());
			//去重
			Set<String> managerSet = new HashSet<String>(); //审计人员
			Set<String> fzrSet = new HashSet<String>(); //部门负责人
			for (FInternalOpinionExchangeUserInfo user : exchangeInfo) {
				if (user.getIsPrincipal() == BooleanEnum.IS) {
					fzrSet.add(String.valueOf(user.getUserId()));
				} else {
					managerSet.add(String.valueOf(user.getUserId()));
				}
			}
			
			//审计人员
			String userIds = "";
			for (String managerId : managerSet) {
				userIds += managerId + ",";
			}
			FlowVarField flowVarField = new FlowVarField();
			flowVarField.setVarName("ManagerID");
			flowVarField.setVarType(FlowVarTypeEnum.STRING);
			if (StringUtil.isNotBlank(userIds)) {
				userIds = userIds.substring(0, userIds.length() - 1);
				flowVarField.setVarVal(userIds);
			} else {
				flowVarField.setVarVal("0");
			}
			fields.add(flowVarField);
			
			//部门负责人
			userIds = "";
			for (String fzrId : fzrSet) {
				userIds += fzrId + ",";
			}
			flowVarField = new FlowVarField();
			flowVarField.setVarName("ManagerLeader");
			flowVarField.setVarType(FlowVarTypeEnum.STRING);
			if (StringUtil.isNotBlank(userIds)) {
				userIds = userIds.substring(0, userIds.length() - 1);
				flowVarField.setVarVal(userIds);
			} else {
				flowVarField.setVarVal("0");
			}
			fields.add(flowVarField);
			
			//是否整改
			flowVarField = new FlowVarField();
			flowVarField.setVarName("Abarbeitung");
			flowVarField.setVarType(FlowVarTypeEnum.STRING);
			FInternalOpinionExchangeInfo exInfo = internalOpinionExchangeService
				.findByFormId(formInfo.getFormId());
			flowVarField.setVarVal(exInfo.getNeedFeedback() == BooleanEnum.YES ? "Y" : "N");
			fields.add(flowVarField);
			
		} catch (Exception e) {
			logger.error("设置内审意见交换流程变量出错{}", e);
		}
		return fields;
		
	}
	
	//	@Override
	//	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
	//		FcsBaseResult result = createResult();
	//		try {
	//			//自定义待办任务名称
	//			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
	//				.getAttribute("startOrder");
	//			if (startOrder != null) {
	//				startOrder.setCustomTaskName(order.getFormInfo().getFormName());
	//			}
	//			result.setSuccess(true);
	//		} catch (Exception e) {
	//			result.setSuccess(false);
	//			result.setMessage("自定义代办任务名称出错");
	//			logger.error("自定义代办任务名称出错:{}", e);
	//		}
	//		return result;
	//	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		try {
			SimpleFormAuditOrder auditFormOrder = (SimpleFormAuditOrder) FcsPmDomainHolder.get()
				.getAttribute("auditFormOrder");
			if (auditFormOrder != null) {
				List<FInternalOpinionExchangeUserDO> users = FInternalOpinionExchangeUserDAO
					.findByFormIdAndUser(order.getFormInfo().getFormId(),
						auditFormOrder.getUserId());
				if (users != null) {
					for (FInternalOpinionExchangeUserDO user : users) {
						logger.info("写入审计人员意见 {} {}", user, auditFormOrder.getVoteContent());
						user.setFeedback(auditFormOrder.getVoteContent());
						FInternalOpinionExchangeUserDAO.update(user);
					}
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("保存审计人员意见出错");
			logger.error("保存审计人员意见出错{}", e);
		}
		
		return result;
	}
}
