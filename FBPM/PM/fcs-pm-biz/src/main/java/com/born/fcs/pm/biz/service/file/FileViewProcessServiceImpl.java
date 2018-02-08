package com.born.fcs.pm.biz.service.file;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.yjf.common.lang.util.StringUtil;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FFileDO;
import com.born.fcs.pm.dal.dataobject.FFileInputListDO;
import com.born.fcs.pm.dal.dataobject.FFileOutputDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.FileStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjf.common.lang.util.ListUtil;

/**
 * 文档出库流程处理
 *
 * @author heh
 *
 * 2016-4-23 下午1:46:05
 */
@Service("fileViewApplyProcessService")
public class FileViewProcessServiceImpl extends BaseProcessService {

	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FFileOutputDO outputDO = fileOutputDAO.findByFormId(formInfo.getFormId());
		vars.put("档案编号", outputDO.getFileCode());
		return vars;
	}

	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		//业务跨部门传分管副总 其他传0
		String fgfzId = "0";
		FFileOutputDO outputDO = fileOutputDAO.findByFormId(formInfo.getFormId());
		FFileDO fileDO = fileDAO.findById(outputDO.getFileId());
		ProjectDO projectDO=projectDAO.findByProjectCode(fileDO.getProjectCode());
		//获取所有业务部
        String busiDeptCodes=sysParameterService.getSysParameterValue(SysParamEnum.SYS_PARAM_BUSINESS_DEPARTMENT.code());
		String deptCodes[]=busiDeptCodes.split(",");
		//申请部门的顶级部门编号
		Org applyOrg =bpmUserQueryService.findDeptByOrgCode(formInfo.getDeptCode());
		//档案所在的顶级业务部门
		Org fileOrg =bpmUserQueryService.findDeptByOrgCode(projectDO.getDeptCode());
		String applyTopDeptCode=applyOrg.getCode();
		boolean isBusiDept=false;
		for(String code:deptCodes){
			if(StringUtil.isNotBlank(code)){
				Org busiOrg =bpmUserQueryService.findDeptByOrgCode(code);
				String busiTopCode=busiOrg.getCode();
				if(busiTopCode.equals(applyTopDeptCode)){
					isBusiDept=true;
					break;
				}
			}
		}
		if(isBusiDept&&!applyTopDeptCode.equals(fileOrg.getCode())){
		List<SimpleUserInfo> userInfos = projectRelatedUserService.getFgfz(projectDO.getDeptCode());
		if (ListUtil.isNotEmpty(userInfos)) {
			fgfzId = userInfos.get(0).getUserId() + "";
			}
		}
		List<FlowVarField> fields = Lists.newArrayList();
		FlowVarField sealID = new FlowVarField();
		sealID.setVarName("fgfzId");
		sealID.setVarType(FlowVarTypeEnum.STRING);
		sealID.setVarVal(fgfzId);
		fields.add(sealID);
		return fields;
	}

	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FFileOutputDO DO = fileOutputDAO.findByFormId(order.getFormInfo().getFormId());
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
					.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(DO.getProjectName() + "-查阅申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("查阅申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}

	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 启动流程后业务处理(BASE)
		final Date nowDate = FcsPmDomainHolder.get().getSysDate();
		FFileOutputDO DO = fileOutputDAO.findByFormId(formInfo.getFormId());
		String ids[] = DO.getOutputDetailIds().split(",");
//		for (String id : ids) {
//			FFileInputListDO listDO = fileInputListDAO.findById(Long.parseLong(id));
//			listDO.setStatus(FileStatusEnum.WAITING_OUTPUT.code());
//			fileInputListDAO.update(listDO);
//		}
		FFileDO fileDO = fileDAO.findById(DO.getFileId());
		DO.setHandOverManId(fileDO.getReceiveManId());
		DO.setHandOverMan(fileDO.getReceiveMan());
		DO.setHandOverTime(nowDate);
		DO.setHandOverDept(fileDO.getReceiveDept());
		fileOutputDAO.update(DO);
	}

	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 结束流程后业务处理(BASE)
		final Date nowDate = FcsPmDomainHolder.get().getSysDate();
		SimpleUserInfo currentUser = (SimpleUserInfo) FcsPmDomainHolder.get().getAttribute(
				"currentUser");
		FFileOutputDO DO = fileOutputDAO.findByFormId(formInfo.getFormId());
		DO.setReceiveManId(workflowResult.getCreatorId());
		DO.setReceiveMan(workflowResult.getCreator());
		DO.setReceiveDept(workflowResult.getStartOrgName());
		DO.setReceiveTime(nowDate);
		fileOutputDAO.update(DO);
	}

	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 手动结束流程后业务处理(BASE)
	}
}
