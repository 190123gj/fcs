package com.born.fcs.pm.biz.service.brokerbusiness;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FBrokerBusinessDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Service("brokerBusinessProcessService")
public class BrokerBusinessProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	CouncilApplyService councilApplyService;
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		List<FlowVarField> vars = Lists.newArrayList();
		
		try {
			FBrokerBusinessDO apply = FBrokerBusinessDAO.findByFormId(formInfo.getFormId());
			if (apply != null) {
				FlowVarField flowVarField = new FlowVarField();
				flowVarField.setVarName("haveContract");
				flowVarField.setVarType(FlowVarTypeEnum.STRING);
				if (StringUtil.isNotEmpty(apply.getContractUrl())) {
					flowVarField.setVarVal("Y");
				} else {
					flowVarField.setVarVal("N");
				}
				
				//设置法务专员 
				FlowVarField legalMan = new FlowVarField();
				legalMan.setVarName("LawManagerID");
				legalMan.setVarType(FlowVarTypeEnum.STRING);
				ProjectRelatedUserInfo userInfo = projectRelatedUserService.getLegalManager(apply
					.getProjectCode());
				if (userInfo == null) {
					legalMan.setVarVal("0");
				} else {
					legalMan.setVarVal(String.valueOf(userInfo.getUserId()));
				}
				
				vars.add(legalMan);
				vars.add(flowVarField);
			}
		} catch (Exception e) {
			logger.error("经纪业务设置流程变量出错：{}", e);
		}
		//经纪业务申请
		return vars;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		
		FcsBaseResult result = createResult();
		try {
			
			//经纪业务申请
			FBrokerBusinessDO apply = FBrokerBusinessDAO.findByFormId(order.getFormInfo()
				.getFormId());
			try {
				SimpleFormOrder submitOrder = (SimpleFormOrder) FcsPmDomainHolder.get()
					.getAttribute("submitFormOrder");
				if (submitOrder != null) {
					submitOrder.setRelatedProjectCode(apply.getProjectCode());
				}
			} catch (Exception e) {
				logger.error("获取提交Order出错：{}", e);
			}
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(apply.getCustomerName() + "-经纪业务申请单");
			}
			
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("自定义任务名称出错");
			logger.error("设置自定义任务名称出错：{}", e);
		}
		
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		FBrokerBusinessDO apply = null;
		try {
			apply = FBrokerBusinessDAO.findByFormId(formInfo.getFormId());
			logger.info("修改经纪业务状态 ：{}", apply);
			//不需要上会的直接通过 
			apply.setStatus(FormChangeApplyStatusEnum.AUDITING.code());
			FBrokerBusinessDAO.update(apply);
			
			//保存业务经理到相关人员表
			ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
			BeanCopier.staticCopy(formInfo, relatedUser);
			relatedUser.setProjectCode(apply.getProjectCode());
			relatedUser.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
			relatedUser.setRemark("经纪业务经理");
			projectRelatedUserService.setRelatedUser(relatedUser);
		} catch (Exception e) {
			logger.error("修改经纪业务状态 ：{},", apply, e);
		}
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		try {
			FormInfo formInfo = order.getFormInfo();
			Map<String, Object> customizeMap = order.getCustomizeMap();
			if (customizeMap != null) {
				//选择法务经理
				String chooseLegalManager = (String) customizeMap.get("chooseLegalManager");
				if (BooleanEnum.YES.code().equals(chooseLegalManager)) {
					
					logger.info("设置经纪业务法务专员{}", customizeMap);
					
					long legalManagerId = NumberUtil.parseLong((String) customizeMap
						.get("legalManagerId"));
					String legalManagerAccount = (String) customizeMap.get("legalManagerAccount");
					String legalManagerName = (String) customizeMap.get("legalManagerName");
					
					//保存法务经理到相关人员表
					ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
					relatedUser.setProjectCode(formInfo.getRelatedProjectCode());
					relatedUser.setUserType(ProjectRelatedUserTypeEnum.LEGAL_MANAGER);
					relatedUser.setRemark("法务部负责人选择法务专员");
					relatedUser.setUserId(legalManagerId);
					relatedUser.setUserAccount(legalManagerAccount);
					relatedUser.setUserName(legalManagerName);
					projectRelatedUserService.setRelatedUser(relatedUser);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("经纪业务流程审核前处理出错：{}", e);
		}
		return result;
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		FBrokerBusinessDO apply = null;
		try {
			apply = FBrokerBusinessDAO.findByFormId(formInfo.getFormId());
			logger.info("修改经纪业务状态 ：{}", apply);
			//不需要上会的直接通过 
			apply.setStatus(FormChangeApplyStatusEnum.DENY.code());
			FBrokerBusinessDAO.update(apply);
		} catch (Exception e) {
			logger.error("修改经纪业务状态 ：{},", apply, e);
		}
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		FBrokerBusinessDO apply = null;
		try {
			apply = FBrokerBusinessDAO.findByFormId(formInfo.getFormId());
			logger.info("经纪业务审核完成处理开始 ：{}", apply);
			//需要上会的
			if (BooleanEnum.IS.code().equals(apply.getIsNeedCouncil())) {
				//进入待上会列表
				CouncilApplyOrder order = new CouncilApplyOrder();
				order.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
				order.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
				order.setProjectCode(apply.getProjectCode());
				order.setProjectName(apply.getCustomerName() + "经纪业务");
				order.setCustomerId(0);
				order.setCustomerName(apply.getCustomerName());
				order.setApplyManId(formInfo.getUserId());
				order.setApplyManName(formInfo.getUserName());
				order.setApplyDeptId(formInfo.getDeptId());
				order.setApplyDeptName(formInfo.getDeptName());
				order.setApplyTime(getSysdate());
				order.setStatus(CouncilApplyStatusEnum.WAIT.code());
				FcsBaseResult cResult = councilApplyService.saveCouncilApply(order);
				logger.info("经纪业务写入待上会列表结果：{}", cResult);
				//上会中
				apply.setStatus(FormChangeApplyStatusEnum.COUNCIL_WAITING.code());
			} else {
				//不需要上会的直接通过 
				apply.setStatus(FormChangeApplyStatusEnum.APPROVAL.code());
			}
			FBrokerBusinessDAO.update(apply);
			logger.info("经纪业务审核完成处理结束 ：{}", apply);
		} catch (Exception e) {
			logger.error("经纪业务审核完成处理出错：{},", apply, e);
		}
	}
}
