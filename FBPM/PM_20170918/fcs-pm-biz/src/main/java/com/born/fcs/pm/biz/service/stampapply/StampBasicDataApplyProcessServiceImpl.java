package com.born.fcs.pm.biz.service.stampapply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born.fcs.pm.dal.dataobject.*;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.google.common.collect.Maps;
import com.yjf.common.lang.util.ListUtil;

/**
 * 基础资料用印审核处理
 *
 * @author heh
 *
 * 2017年3月31日11:04:55
 */
@Service("stampBasicDataApplyProcessService")
public class StampBasicDataApplyProcessServiceImpl extends BaseProcessService {



	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FStampBasicDataApplyDO stampApply = fStampBasicDataApplyDAO.findByFormId(formInfo.getFormId());
		vars.put("申请单编号", stampApply.getApplyCode());
		return vars;
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		FStampBasicDataApplyDO stampApply = fStampBasicDataApplyDAO.findByFormId(formInfo.getFormId());
		if (null == stampApply) {
			return null;
		}
		List<FStampBasicDataApplyFileDO> fileList = fStampBasicDataApplyFileDAO
			.findByApplyId(stampApply.getApplyId());
		boolean hasG = false;
		boolean hasF = false;
		String sealType = "F";//F:法人章 G:公章 B:公章、法人章
		for (FStampBasicDataApplyFileDO DO : fileList) {
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
		FlowVarField YZAudit = new FlowVarField();
		sealID.setVarName("sealType");
		sealID.setVarType(FlowVarTypeEnum.STRING);
		sealID.setVarVal(sealType);
		fields.add(sealID);
		YZAudit.setVarName("YZAudit");
		YZAudit.setVarType(FlowVarTypeEnum.STRING);
		YZAudit.setVarVal(gzfrzAudit);
		fields.add(YZAudit);

		return fields;
	}

	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FStampBasicDataApplyDO applyDO = fStampBasicDataApplyDAO.findByFormId(order.getFormInfo().getFormId());
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(applyDO.getApplyCode() + "-基础资料用印申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("基础资料用印申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
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

	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {

	}
	
	@Override
	public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {

	}
}
