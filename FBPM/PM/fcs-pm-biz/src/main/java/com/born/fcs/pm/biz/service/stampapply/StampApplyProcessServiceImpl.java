package com.born.fcs.pm.biz.service.stampapply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.dal.dataobject.StampConfigureDO;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.integration.crm.service.channal.ChannalContractClient;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.StampSourceEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.stampapply.StampConfigureListInfo;
import com.google.common.collect.Maps;

import com.yjf.common.lang.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ChannalContractClient channalContractClient;

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
		String fractionFalg="1"; //是否超过合同申请份数 ==0超过，!=0未超过
		for (FStampApplyFileDO DO : fileList) {
			if(DO.getSource().equals(StampSourceEnum.CONTRACT_NOTSTANDARD.code())||DO.getSource().equals(StampSourceEnum.CONTRACT_STANDARD.code())||DO.getSource().equals(StampSourceEnum.CONTRACT_OTHER.code())){
				contractSeal="Y";
				FProjectContractItemDO contractItemDO=fProjectContractItemDAO.findByContractCode(DO.getContractCode());
				int cachetNum=0;
				int legalChapterNum=0;
				//查找该合同以前申请过的用印记录
				List<FStampApplyFileDO> fileDOs=fStampApplyFileDAO.findByContractCode(contractItemDO.getContractCode());
				for(FStampApplyFileDO fileDO:fileDOs){
					if(fileDO.getInvalid()==null){
						cachetNum=cachetNum+fileDO.getCachetNum();
						legalChapterNum=legalChapterNum+fileDO.getLegalChapterNum();
					}
				}
				if(cachetNum>Long.parseLong(contractItemDO.getCnt())||legalChapterNum>Long.parseLong(contractItemDO.getCnt())){
					fractionFalg="0";
					break;
				}
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
		//对应公司
		Map<String,String> gzMap=new HashMap<String, String>();
		Map<String,String> frzMap=new HashMap<String, String>();
		if(StringUtil.isNotEmpty(stampApply.getOrgNames())){
			String orgNames[]=stampApply.getOrgNames().split(",");
			for(String orgName:orgNames){
				StampConfigureDO configureDO=stampConfigureDAO.findByOrgName(orgName);
				if(configureDO==null){
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到["+orgName+"]印章配置信息");
				}
				if(hasG)
				gzMap.put(configureDO.getGzRoleCode(),configureDO.getGzRoleCode());
				if(hasF)
				frzMap.put(configureDO.getFrzRoleCode(),configureDO.getFrzRoleCode());
			}
		}
		//公法人章人员
		Map<Long,Long> gfrzMap=new HashMap<Long,Long>();
		for(String key:gzMap.keySet()){
			List<SysUser> gzList = bpmUserQueryService.findUserByRoleAlias(key);
			if(ListUtil.isNotEmpty(gzList)){
				for(SysUser sysUser:gzList){
					gfrzMap.put(sysUser.getUserId(),sysUser.getUserId());
				}
			}else{
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"未找到["+key+"]角色对应公章人员");
			}
		}
		for(String key:frzMap.keySet()){
			List<SysUser> frzList = bpmUserQueryService.findUserByRoleAlias(key);
			if(ListUtil.isNotEmpty(frzList)){
				for(SysUser sysUser:frzList){
					gfrzMap.put(sysUser.getUserId(),sysUser.getUserId());
				}
			}else{
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"未找到["+key+"]角色对应法人章人员");
			}
		}

		//拼接成字符串
		String gzfrzAudit="";
		for(Long key:gfrzMap.keySet()){
			gzfrzAudit=gzfrzAudit+key+",";
		}
		gzfrzAudit=gzfrzAudit.substring(0,gzfrzAudit.length()-1);
		List<FlowVarField> fields = Lists.newArrayList();
		FlowVarField sealID = new FlowVarField();
		FlowVarField isContractSeal = new FlowVarField();
		FlowVarField YZAudit = new FlowVarField();
		FlowVarField Fraction = new FlowVarField();
		isContractSeal.setVarName("contractSeal");
		isContractSeal.setVarType(FlowVarTypeEnum.STRING);
		isContractSeal.setVarVal(contractSeal);
		fields.add(isContractSeal);
		sealID.setVarName("sealType");
		sealID.setVarType(FlowVarTypeEnum.STRING);
		sealID.setVarVal(sealType);
		fields.add(sealID);
		YZAudit.setVarName("YZAudit");
		YZAudit.setVarType(FlowVarTypeEnum.STRING);
		YZAudit.setVarVal(gzfrzAudit);
		fields.add(YZAudit);
		Fraction.setVarName("Fraction");
		Fraction.setVarType(FlowVarTypeEnum.STRING);
		Fraction.setVarVal(fractionFalg);
		fields.add(Fraction);
		return fields;
	}

	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FStampApplyDO applyDO = fStampApplyDAO.findByFormId(order.getFormInfo().getFormId());
			if(StringUtil.isBlank(applyDO.getOrgNames())){
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"请选择对应的印章公司");
			}
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				if("-".equals(applyDO.getProjectCode()))
				startOrder.setCustomTaskName(applyDO.getCustomerName() + "(渠道合同)-用印申请");
				else
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
			if (fileDO.getSource().equals(StampSourceEnum.CONTRACT_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CONTRACT_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CONTRACT_OTHER.code())
					||fileDO.getSource().equals(StampSourceEnum.PROJECT_WRIT.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.LETTER_OTHER.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CLETTER_OTHER.code())) {
				FProjectContractItemDO itemDO = fProjectContractItemDAO.findByContractCode(fileDO
					.getContractCode());
				if(fileDO.getSource().equals(StampSourceEnum.CONTRACT_STANDARD.code())
						||fileDO.getSource().equals(StampSourceEnum.CONTRACT_NOTSTANDARD.code())
						||fileDO.getSource().equals(StampSourceEnum.CONTRACT_OTHER.code())){
						if(itemDO.getContractStatus().equals(ContractStatusEnum.CONFIRMED.code())){
							itemDO.setContractStatus(ContractStatusEnum.SEALING.code());
							fProjectContractItemDAO.update(itemDO);
						}
				}else {
					itemDO.setContractStatus(ContractStatusEnum.SEALING.code());
					fProjectContractItemDAO.update(itemDO);
				}
			}
			else if(fileDO.getSource().equals(StampSourceEnum.CHANNEL_CONTRACT.code())){//更新渠道合同状态
				channalContractClient.updateStatus(fileDO.getContractCode(),ContractStatusEnum.SEALING);
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
			if (fileDO.getSource().equals(StampSourceEnum.CONTRACT_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CONTRACT_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CONTRACT_OTHER.code())
					||fileDO.getSource().equals(StampSourceEnum.PROJECT_WRIT.code())
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
				}else if (fileDO.getSource().equals(StampSourceEnum.CONTRACT_STANDARD.code())
						||fileDO.getSource().equals(StampSourceEnum.CONTRACT_NOTSTANDARD.code())
						||fileDO.getSource().equals(StampSourceEnum.CONTRACT_OTHER.code())) {
					//合同判断是不是用印中的状态
					if (itemDO.getContractStatus().equals(ContractStatusEnum.SEALING.code())) {
						itemDO.setContractStatus(ContractStatusEnum.CONFIRMED.code());
					}
				}else{

					itemDO.setContractStatus(ContractStatusEnum.CONFIRMED.code());
				}
				fProjectContractItemDAO.update(itemDO);
			}else if(fileDO.getSource().equals(StampSourceEnum.CHANNEL_CONTRACT.code())){//更新渠道合同状态
				channalContractClient.updateStatus(fileDO.getContractCode(),ContractStatusEnum.APPROVAL);
			}
		}
	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		FStampApplyDO applyDO = fStampApplyDAO.findByFormId(formInfo.getFormId());
		List<FStampApplyFileDO> fileDOs = fStampApplyFileDAO.findByApplyId(applyDO.getApplyId());
		for (FStampApplyFileDO fileDO : fileDOs) {
			if (fileDO.getSource().equals(StampSourceEnum.CONTRACT_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CONTRACT_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CONTRACT_OTHER.code())
					||fileDO.getSource().equals(StampSourceEnum.PROJECT_WRIT.code())
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
				}else if (fileDO.getSource().equals(StampSourceEnum.CONTRACT_STANDARD.code())
						||fileDO.getSource().equals(StampSourceEnum.CONTRACT_NOTSTANDARD.code())
						||fileDO.getSource().equals(StampSourceEnum.CONTRACT_OTHER.code())) {
					//合同判断是不是用印中的状态
					if (itemDO.getContractStatus().equals(ContractStatusEnum.SEALING.code())) {
						itemDO.setContractStatus(ContractStatusEnum.CONFIRMED.code());
					}
				}else{
					itemDO.setContractStatus(ContractStatusEnum.CONFIRMED.code());
				}
				fProjectContractItemDAO.update(itemDO);
			}else if(fileDO.getSource().equals(StampSourceEnum.CHANNEL_CONTRACT.code())){//更新渠道合同状态
				channalContractClient.updateStatus(fileDO.getContractCode(),ContractStatusEnum.APPROVAL);
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
			if (fileDO.getSource().equals(StampSourceEnum.PROJECT_WRIT.code())
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
			}else if (fileDO.getSource().equals(StampSourceEnum.CONTRACT_STANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CONTRACT_NOTSTANDARD.code())
					||fileDO.getSource().equals(StampSourceEnum.CONTRACT_OTHER.code())) {
				FProjectContractItemDO itemDO = fProjectContractItemDAO.findByContractCode(fileDO
						.getContractCode());
				//合同判断是不是用印中的状态
				if (itemDO.getContractStatus().equals(ContractStatusEnum.SEALING.code())) {
					itemDO.setContractStatus(ContractStatusEnum.SEAL.code());
					fProjectContractItemDAO.update(itemDO);
				}
			} else if(fileDO.getSource().equals(StampSourceEnum.CHANNEL_CONTRACT.code())){//更新渠道合同状态
				channalContractClient.updateStatus(fileDO.getContractCode(),ContractStatusEnum.SEAL);
			}
		}
		
	}
}
