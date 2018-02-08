package com.born.fcs.pm.biz.service.stampapply;

import java.util.List;
import java.util.Map;

import com.born.fcs.pm.ws.enums.StampSourceEnum;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FProjectContractItemDO;
import com.born.fcs.pm.dal.dataobject.FStampApplyDO;
import com.born.fcs.pm.dal.dataobject.FStampApplyFileDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 用印管理审批流程处理
 *
 * @author heh
 *
 * 2016-4-23 下午1:46:05
 */
@Service("stampApplyProcessService")
public class StampApplyProcessServiceImpl extends BaseProcessService {

	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FStampApplyDO stampApply = fStampApplyDAO.findByFormId(formInfo.getFormId());
		vars.put("申请单编号", stampApply.getApplyCode());
		return vars;
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		FStampApplyDO stampApply = fStampApplyDAO.findByFormId(formInfo.getFormId());
		if (null == stampApply) {
			return null;
		}
		List<FStampApplyFileDO> fileList = fStampApplyFileDAO
			.findByApplyId(stampApply.getApplyId());
		boolean hasG = false;
		boolean hasF = false;
		String contractSeal = "N";//是否合同用印
		for (FStampApplyFileDO DO : fileList) {
			if(DO.getSource().equals(StampSourceEnum.CONTRACT_NOTSTANDARD.code())||DO.getSource().equals(StampSourceEnum.CONTRACT_STANDARD.code())||DO.getSource().equals(StampSourceEnum.CONTRACT_OTHER.code())){
				contractSeal="Y";
				break;
			}
		}
		String sealType = "F";//F:法人章 G:公章 B:公章、法人章
		for (FStampApplyFileDO DO : fileList) {
			if (DO.getLegalChapterNum() != 0 && DO.getCachetNum() != 0) {
				sealType = "B";
				hasF = true;
				hasG = true;
				break;
			}
			if (DO.getCachetNum() != 0) {
				hasG = true;
			}
			if (DO.getLegalChapterNum() != 0) {
				hasF = true;
			}
		}
		if (hasG && hasF) {
			sealType = "B";
		} else if (hasG && !hasF) {
			sealType = "G";
		} else if (hasF && !hasG) {
			sealType = "F";
		}
		List<FlowVarField> fields = Lists.newArrayList();
		FlowVarField sealID = new FlowVarField();
		FlowVarField isContractSeal = new FlowVarField();
		isContractSeal.setVarName("contractSeal");
		isContractSeal.setVarType(FlowVarTypeEnum.STRING);
		isContractSeal.setVarVal(contractSeal);
		fields.add(isContractSeal);
		sealID.setVarName("sealType");
		sealID.setVarType(FlowVarTypeEnum.STRING);
		sealID.setVarVal(sealType);
		fields.add(sealID);
		return fields;
	}

	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FStampApplyDO applyDO = fStampApplyDAO.findByFormId(order.getFormInfo().getFormId());
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(applyDO.getProjectName() + "-用印申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("用印申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 启动流程后业务处理(BASE)
		FStampApplyDO applyDO = fStampApplyDAO.findByFormId(formInfo.getFormId());
		List<FStampApplyFileDO> fileDOs = fStampApplyFileDAO.findByApplyId(applyDO.getApplyId());
		for (FStampApplyFileDO fileDO : fileDOs) {
			if (fileDO.getContractCode() != null||fileDO.getSource().equals(StampSourceEnum.PROJECT_WRIT.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_OTHER.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_OTHER.code())) {
				FProjectContractItemDO itemDO = fProjectContractItemDAO.findByContractCode(fileDO
					.getContractCode());
				itemDO.setContractStatus(ContractStatusEnum.SEALING.code());
				fProjectContractItemDAO.update(itemDO);
			}
		}
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public void doNextAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 审核后业务处理(BASE)
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 手动结束流程后业务处理(BASE)
		FStampApplyDO applyDO = fStampApplyDAO.findByFormId(formInfo.getFormId());
		List<FStampApplyFileDO> fileDOs = fStampApplyFileDAO.findByApplyId(applyDO.getApplyId());
		for (FStampApplyFileDO fileDO : fileDOs) {
			if (fileDO.getContractCode() != null||fileDO.getSource().equals(StampSourceEnum.PROJECT_WRIT.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_OTHER.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_OTHER.code())) {
				FProjectContractItemDO itemDO = fProjectContractItemDAO.findByContractCode(fileDO
					.getContractCode());
				if(fileDO.getSource().equals(StampSourceEnum.PROJECT_WRIT.code())||fileDO.getSource().equals(StampSourceEnum.LETTER_NOTSTANDARD.code())
						||fileDO.getSource().equals(StampSourceEnum.LETTER_STANDARD.code())
						||fileDO.getSource().equals(StampSourceEnum.LETTER_OTHER.code())){
					itemDO.setContractStatus(ContractStatusEnum.APPROVAL.code());
				}else {
					itemDO.setContractStatus(ContractStatusEnum.CONFIRMED.code());
				}
				fProjectContractItemDAO.update(itemDO);
			}
		}
	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		FStampApplyDO applyDO = fStampApplyDAO.findByFormId(formInfo.getFormId());
		List<FStampApplyFileDO> fileDOs = fStampApplyFileDAO.findByApplyId(applyDO.getApplyId());
		for (FStampApplyFileDO fileDO : fileDOs) {
			if (fileDO.getContractCode() != null||fileDO.getSource().equals(StampSourceEnum.PROJECT_WRIT.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_OTHER.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_OTHER.code())) {
				FProjectContractItemDO itemDO = fProjectContractItemDAO.findByContractCode(fileDO
						.getContractCode());
				if(fileDO.getSource().equals(StampSourceEnum.PROJECT_WRIT.code())||fileDO.getSource().equals(StampSourceEnum.LETTER_NOTSTANDARD.code())
						||fileDO.getSource().equals(StampSourceEnum.LETTER_STANDARD.code())
						||fileDO.getSource().equals(StampSourceEnum.LETTER_OTHER.code())){
					itemDO.setContractStatus(ContractStatusEnum.APPROVAL.code());
				}else {
					itemDO.setContractStatus(ContractStatusEnum.CONFIRMED.code());
				}
				fProjectContractItemDAO.update(itemDO);
			}
		}
	}
	
	@Override
	public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 结束流程后业务处理(BASE)
		FStampApplyDO applyDO = fStampApplyDAO.findByFormId(formInfo.getFormId());
		List<FStampApplyFileDO> fileDOs = fStampApplyFileDAO.findByApplyId(applyDO.getApplyId());
		for (FStampApplyFileDO fileDO : fileDOs) {
			if (fileDO.getContractCode() != null||fileDO.getSource().equals(StampSourceEnum.PROJECT_WRIT.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_OTHER.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_OTHER.code())) {
				FProjectContractItemDO itemDO = fProjectContractItemDAO.findByContractCode(fileDO
					.getContractCode());
				itemDO.setContractStatus(ContractStatusEnum.SEAL.code());
				fProjectContractItemDAO.update(itemDO);
			}
		}
		
	}
}
