package com.born.fcs.pm.biz.service.capitalappropriationapply;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyDO;
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyFeeDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.capitalappropriationapply.FCapitalAppropriationApplyService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectTransferService;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 资金划付申请审核流程处理
 * 
 * @author Ji
 *
 * 2016-5-23 下午17:58:00
 */
@Service("fCapitalAppropriationApplyProcessService")
public class FCapitalAppropriationApplyProcessServiceImpl extends BaseProcessService {
	@Autowired
	FinancialProjectTransferService financialProjectTransferService;
	
	@Autowired
	FinancialProjectService financialProjectService;
	@Autowired
	FCapitalAppropriationApplyService fCapitalAppropriationApplyService;
	
	@Autowired
	CouncilSummaryService councilSummaryService;
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
			.getFormId());
		if (StringUtil.equals(applyDO.getProjectCode(), "FINANCIAL_PRODUCT")) {
			ProjectFinancialInfo project = financialProjectService.queryByCode(applyDO
				.getProjectCode());
			vars.put("项目编号", project.getProjectCode());
			vars.put("产品名称", project.getProductName());
		} else {
			vars.put("产品名称", "");
		}
		return vars;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			long formId = order.getFormInfo().getFormId();
			FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO
				.findByFormId(formId);
			if (applyDO == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金划付申请单未找到");
			}
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(applyDO.getProjectName() + "-资金划付申请单");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("资金划付申请单流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
			.getFormId());
		if (applyDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金划付申请单未找到");
		}
		if (applyDO.getProjectType().equals("FINANCIAL_PRODUCT") && applyDO.getOutBizNo() <= 0) {//理财类项目
			financialProjectService.changeStatus(applyDO.getProjectCode(),
				FinancialProjectStatusEnum.CAPITAL_APPLY_AUDITING);
		}
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		//划付申请单
		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
			.getFormId());
		if (applyDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金划付申请单未找到");
		}
		
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
			.getFormId());
		if (applyDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金划付申请单未找到");
		}
		if (applyDO.getProjectType().equals("FINANCIAL_PRODUCT")) {//理财类项目 非回购
			if (applyDO.getOutBizNo() > 0) {
				financialProjectService.changeStatus(applyDO.getProjectCode(),
					FinancialProjectStatusEnum.BUY_BACK_APPLY_DENY);
			} else {
				financialProjectService.changeStatus(applyDO.getProjectCode(),
					FinancialProjectStatusEnum.CAPITAL_APPLY_WAITING);
			}
		}
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
			.getFormId());
		if (applyDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金划付申请单未找到");
		}
		List<FCapitalAppropriationApplyFeeDO> applyFeeDO = FCapitalAppropriationApplyFeeDAO
			.findByApplyId(applyDO.getApplyId());
		//本次申请金额总和
		Money sumAmont = new Money(0);
		for (FCapitalAppropriationApplyFeeDO fCapitalAppropriationApplyFeeDO : applyFeeDO) {
			sumAmont = sumAmont.add(fCapitalAppropriationApplyFeeDO.getAppropriateAmount());
		}
		//是否超额
		List<FlowVarField> fields = Lists.newArrayList();
		FlowVarField overfull = new FlowVarField();
		overfull.setVarName("money");
		overfull.setVarType(FlowVarTypeEnum.STRING);
		overfull.setVarVal(sumAmont.toString());
		fields.add(overfull);
		
		return fields;
	}
	
	//删除
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		FCapitalAppropriationApplyDO applyDO = FCapitalAppropriationApplyDAO.findByFormId(formInfo
			.getFormId());
		if (applyDO == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "资金划付申请单未找到");
		}
		List<FCapitalAppropriationApplyFeeDO> listAppFeeDO = FCapitalAppropriationApplyFeeDAO
			.findByApplyId(applyDO.getApplyId());
		FCapitalAppropriationApplyDAO.deleteByFormId(formInfo.getFormId());
		for (FCapitalAppropriationApplyFeeDO applyFeeDO : listAppFeeDO) {
			FCapitalAppropriationApplyFeeDAO.deleteById(applyFeeDO.getId());
		}
		if (applyDO.getProjectType().equals("FINANCIAL_PRODUCT")) {//理财类项目 非回购
			if (applyDO.getOutBizNo() > 0) {
				financialProjectService.changeStatus(applyDO.getProjectCode(),
					FinancialProjectStatusEnum.BUY_BACK_WAITING);
			} else {
				financialProjectService.changeStatus(applyDO.getProjectCode(),
					FinancialProjectStatusEnum.CAPITAL_APPLY_WAITING);
			}
		}
		
	}
	
}
