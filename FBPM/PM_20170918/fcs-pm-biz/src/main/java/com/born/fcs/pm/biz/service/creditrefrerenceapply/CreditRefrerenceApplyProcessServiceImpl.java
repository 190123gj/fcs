package com.born.fcs.pm.biz.service.creditrefrerenceapply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.crm.ws.service.CompanyCustomerService;
import com.born.fcs.crm.ws.service.enums.ChangeTypeEnum;
import com.born.fcs.crm.ws.service.order.UpdateFromCreditOrder;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FCreditRefrerenceApplyDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.money.Money;

@Service("creditRefrerenceApplyProcessService")
public class CreditRefrerenceApplyProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	CompanyCustomerService companyCustomerServiceClient;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		List<FlowVarField> fields = Lists.newArrayList();
		//风险经理ID
		ProjectRelatedUserInfo riskManager = projectRelatedUserService.getRiskManager(formInfo
			.getRelatedProjectCode());
		FlowVarField riskManagerId = new FlowVarField();
		riskManagerId.setVarName("RiskManagerID");
		riskManagerId.setVarType(FlowVarTypeEnum.STRING);
		if (null != riskManager) {
			riskManagerId.setVarVal(riskManager.getUserId() + "");
			fields.add(riskManagerId);
		} else {
			riskManagerId.setVarVal("0");
		}
		return fields;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FCreditRefrerenceApplyDO DO = fCreditRefrerenceApplyDAO.findByFormId(order
				.getFormInfo().getFormId());
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(DO.getCompanyName() + "-征信查询申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("征信查询申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		FCreditRefrerenceApplyDO DO = fCreditRefrerenceApplyDAO.findByFormId(formInfo.getFormId());
		//客户id为0的不同步
		if (DO.getCustomerId() > 0) {
			ProjectSimpleDetailInfo projectInfo = projectService.querySimpleDetailInfo(DO
				.getProjectCode());
			//上会是否完成
			boolean isBeforeFinishCouncil = false;
			if (projectInfo.getPhases().code().equals("SET_UP_PHASES")
				|| projectInfo.getPhases().code().equals("INVESTIGATING_PHASES")
				|| projectInfo.getPhases().code().equals("COUNCIL_PHASES")) {
				isBeforeFinishCouncil = true;
			}
			//如果是上会完成前法定代表人、公司成立时间、注册资本、经营范围、地址可以修改，修改的信息要同步到客户信息表里
			if (isBeforeFinishCouncil) {
				//更新到客户关系系统
				UpdateFromCreditOrder order = new UpdateFromCreditOrder();
				order.setUserId(projectInfo.getCustomerId());
				order.setLegalPersion(DO.getLegalPersion());
				order.setEstablishedTime(DO.getEstablishedTime());
				order.setRegisterCapital(Money.amout(String.valueOf(DO.getRegisterCapital())));
				order.setBusiScope(DO.getBusiScope());
				order.setAddress(DO.getAddress());
				order.setLoanCardNo(DO.getLoanCardNo());
				order.setOrgCode(DO.getOrgCode());
				order.setTaxRegCertificateNo(DO.getTaxRegCertificateNo());
				order.setLocalTaxCertNo(DO.getLocalTaxCertNo());
				//征信查询客户修改留痕
				order.setOperId(formInfo.getUserId());
				order.setOperName(formInfo.getUserName());
				order.setChangeType(ChangeTypeEnum.ZX);
				companyCustomerServiceClient.updateFromCredit(order);
			}
		}
	}
	
}
