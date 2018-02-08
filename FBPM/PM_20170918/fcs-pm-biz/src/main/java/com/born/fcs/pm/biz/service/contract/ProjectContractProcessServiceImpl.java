package com.born.fcs.pm.biz.service.contract;

import java.util.List;
import java.util.Map;

import com.born.fcs.pm.ws.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FProjectContractDO;
import com.born.fcs.pm.dal.dataobject.FProjectContractItemDO;
import com.born.fcs.pm.dal.dataobject.ProjectCreditConditionDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractResultInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 合同审批流程处理
 *
 * @author heh
 *
 * 2016-4-23 下午1:46:05
 */
@Service("projectContractProcessService")
public class ProjectContractProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	protected CommonAttachmentService commonAttachmentService;
	
	@Autowired
	protected ProjectContractService projectContractService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		FProjectContractDO projectContract = fProjectContractDAO.findByFormId(formInfo.getFormId());
		
		if (null == projectContract) {
			return null;
		}
		
		List<FlowVarField> fields = Lists.newArrayList();
		//法务专员
		
		ProjectRelatedUserInfo legalManger = projectRelatedUserService
			.getLegalManager(projectContract.getProjectCode());
		String isHavelegalCounsel = "0";
		if (null != legalManger) {
			isHavelegalCounsel = Long.toString(legalManger.getUserId());
		}
		FlowVarField legalCounsel = new FlowVarField();
		legalCounsel.setVarName("legalCounsel");
		legalCounsel.setVarType(FlowVarTypeEnum.STRING);
		legalCounsel.setVarVal(isHavelegalCounsel);
		fields.add(legalCounsel);
		return fields;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FProjectContractDO DO = fProjectContractDAO.findByFormId(order.getFormInfo()
				.getFormId());
			checkIsHaveMain(DO);
			
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(DO.getProjectName() + "-合同申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("合同申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 启动流程后业务处理(BASE)
		updateContractStatus(formInfo, "SUBMIT");
		FProjectContractDO projectContract = fProjectContractDAO.findByFormId(formInfo.getFormId());
		ProjectDO project = projectDAO.findByProjectCodeForUpdate(projectContract.getProjectCode());
		List<FProjectContractItemDO> itemDOs = fProjectContractItemDAO
			.findByFormContractId(projectContract.getContractId());
		boolean ishaveMain = false;
		for (FProjectContractItemDO itemDO : itemDOs) {
			if (itemDO.getIsMain() != null && itemDO.getIsMain().equals("IS")) {
				ishaveMain = true;
				break;
			}
		}
		if (ishaveMain&&!StringUtil.equals(project.getIsRedoProject(),"IS")) {
			project.setPhases(ProjectPhasesEnum.CONTRACT_PHASES.code());
			project.setStatus(ProjectStatusEnum.NORMAL.code());
			project.setPhasesStatus(ProjectPhasesStatusEnum.AUDITING.code());
			projectDAO.update(project);
		}
		
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(final WorkFlowBeforeProcessOrder order) {
		return commonProcess(order, "合同审核选择法务人员", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				FormInfo formInfo = order.getFormInfo();
				Map<String, Object> customizeMap = order.getCustomizeMap();
				String needLegalManager = (String) customizeMap.get("needLegalManager");
				if ("IS".equals(needLegalManager)) {
					FProjectContractDO projectContract = fProjectContractDAO.findByFormId(formInfo
						.getFormId());
					//选择法务经理
					long legalManagerId = NumberUtil.parseLong((String) customizeMap
						.get("legalManagerId"));
					String legalManagerAccount = (String) customizeMap.get("legalManagerAccount");
					String legalManagerName = (String) customizeMap.get("legalManagerName");
					//保存法务经理到相关人员表
					ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
					relatedUser.setProjectCode(formInfo.getRelatedProjectCode());
					relatedUser.setUserType(ProjectRelatedUserTypeEnum.LEGAL_MANAGER);
					relatedUser.setProjectCode(projectContract.getProjectCode());
					relatedUser.setRemark("合同审核选择法务人员");
					relatedUser.setUserId(legalManagerId);
					relatedUser.setUserAccount(legalManagerAccount);
					relatedUser.setUserName(legalManagerName);
					projectRelatedUserService.setRelatedUser(relatedUser);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public void doNextAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 审核后业务处理(BASE)
		updateContractStatus(formInfo, formInfo.getStatus().code());
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 结束流程后业务处理(BASE)
		updateContractStatus(formInfo, "CHECKED");
		FProjectContractDO projectContract = fProjectContractDAO.findByFormId(formInfo.getFormId());
		List<FProjectContractItemDO> itemDOs = fProjectContractItemDAO
			.findByFormContractId(projectContract.getContractId());
		ProjectDO project = projectDAO.findByProjectCode(projectContract.getProjectCode());
		//        for(FProjectContractItemDO itemDo:itemDOs){
		//           if(itemDo.getIsMain().equals("IS")){
		//           project.setContractNo(itemDo.getContractCode());
		//           }
		//       }
		boolean ishaveMain = false;
		//将合同编码写入project_credit_condition表
		for (FProjectContractItemDO itemDO : itemDOs) {
			if (itemDO.getIsMain() != null && itemDO.getIsMain().equals("IS")) {
				ishaveMain = true;
			}
			if (null != itemDO.getCreditMeasure()) {
				String creditIds[] = itemDO.getCreditMeasure().split(",");
				for (String creditId : creditIds) {
					if (StringUtil.isNotEmpty(creditId)) {
						ProjectCreditConditionDO infoDO = projectCreditConditionDAO.findById(Long
							.parseLong(creditId));
						infoDO.setContractNo(itemDO.getContractCode());
						projectCreditConditionDAO.update(infoDO);
					}
				}
			}
		}
		if (ishaveMain&&!StringUtil.equals(project.getIsRedoProject(),"IS")) {
			project.setPhases(ProjectPhasesEnum.CONTRACT_PHASES.code());
			project.setStatus(ProjectStatusEnum.NORMAL.code());
			project.setPhasesStatus(ProjectPhasesStatusEnum.APPROVAL.code());
			projectDAO.update(project);
		}
	}
	
	@Override
	public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//提交人终止作废表单后业务处理(BASE)
		updateContractStatus(formInfo, "END");
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 手动结束流程后业务处理(BASE)
		//        updateContractStatus(formInfo,"CHECKED");
		//        FProjectContractDO projectContract = fProjectContractDAO.findByFormId(formInfo.getFormId());
		//        ProjectDO project = projectDAO.findByProjectCodeForUpdate(projectContract
		//                .getProjectCode());
		//        project.setPhases(ProjectPhasesEnum.CONTRACT_PHASES.code());
		//        project.setStatus(ProjectStatusEnum.NORMAL.code());
		//        project.setPhasesStatus(ProjectPhasesStatusEnum.APPROVAL.code());
		//        projectDAO.update(project);
	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		updateContractStatus(formInfo, "DELETED");
		FProjectContractDO projectContract = fProjectContractDAO.findByFormId(formInfo.getFormId());
		ProjectDO project = projectDAO.findByProjectCodeForUpdate(projectContract.getProjectCode());
		List<FProjectContractItemDO> itemDOs = fProjectContractItemDAO
			.findByFormContractId(projectContract.getContractId());
		boolean ishaveMain = false;
		for (FProjectContractItemDO itemDO : itemDOs) {
			if (itemDO.getIsMain() != null && itemDO.getIsMain().equals("IS")) {
				ishaveMain = true;
				break;
			}
		}
		if (ishaveMain&&!StringUtil.equals(project.getIsRedoProject(),"IS")) {
			project.setPhases(ProjectPhasesEnum.CONTRACT_PHASES.code());
			project.setStatus(ProjectStatusEnum.NORMAL.code());
			project.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
			projectDAO.update(project);
		}
		//删除附件
		for (FProjectContractItemDO DO : itemDOs) {
			String bizNo = "PM_" + projectContract.getFormId() + "_" + DO.getId();
			if (StringUtil.isNotEmpty(DO.getReferAttachment())) {
				bizNo = bizNo + "_CKFJ";
				commonAttachmentService.deleteByBizNoModuleType(bizNo,
					CommonAttachmentTypeEnum.CONTRACT_REFER);
			}
			if (StringUtil.isNotEmpty(DO.getFileUrl())) {
				bizNo = bizNo + "_HT";
				commonAttachmentService.deleteByBizNoModuleType(bizNo,
					CommonAttachmentTypeEnum.CONTRACT_REFER_ATTACHMENT);
			}
			if (StringUtil.isNotEmpty(DO.getCourtRulingUrl())) {
				bizNo = bizNo + "_CDS";
				commonAttachmentService.deleteByBizNoModuleType(bizNo,
					CommonAttachmentTypeEnum.CONTRACT_COURT_RULINGURL);
			}
			if (StringUtil.isNotEmpty(DO.getContractScanUrl())) {
				bizNo = bizNo + "_HCWJ";
				commonAttachmentService.deleteByBizNoModuleType(bizNo,
					CommonAttachmentTypeEnum.CONTRACT_SCANURL);
			}
		}
	}
	
	@Override
	public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		updateContractStatus(formInfo, "CANCEL");
	}
	
	public void updateContractStatus(FormInfo formInfo, String status) {
		FProjectContractDO projectContract = fProjectContractDAO.findByFormId(formInfo.getFormId());
		List<FProjectContractItemDO> itemDos = fProjectContractItemDAO
			.findByFormContractId(projectContract.getContractId());
		for (FProjectContractItemDO itemDO : itemDos) {
			itemDO.setContractStatus(status);
			fProjectContractItemDAO.update(itemDO);
		}
		
	}
	
	private void checkIsHaveMain(FProjectContractDO DO) {
		List<FProjectContractItemDO> itemDos = fProjectContractItemDAO.findByFormContractId(DO
			.getContractId());
		//本张单子是否有主合同
		boolean thisHaveMain = false;
		boolean isFree = "IS".equals(DO.getFreeFlow()) ? true : false;
		Long mainId = 0l;
		for (FProjectContractItemDO itemDO : itemDos) {
			if ("IS".equals(itemDO.getIsMain())) {
				thisHaveMain = true;
				mainId = itemDO.getId();
				break;
			}
		}
		ProjectContractQueryOrder order = new ProjectContractQueryOrder();
		order.setPageSize(9999l);
		order.setProjectCode(DO.getProjectCode());
		order.setIsMain("IS");
		//order.setExceptContractStatus(ContractStatusEnum.INVALID);
		QueryBaseBatchResult<ProjectContractResultInfo> batchResult = projectContractService
			.query(order);
		if (thisHaveMain) {//如果本张单子有主合同就校验是否有存在其他主合同
			if (batchResult != null) {
				List<ProjectContractResultInfo> infoLists = batchResult.getPageList();
				if (ListUtil.isNotEmpty(infoLists)) {
					for (ProjectContractResultInfo info : infoLists) {
						if(info.getContractStatus()!=ContractStatusEnum.END) {
							if (mainId != info.getId()) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
										"该项目已经有主合同，不能提交!");
							}
						}
					}
					
				}
			}
		}
	}
	
}
