package com.born.fcs.pm.biz.service.contract;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.dal.dataobject.*;

import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.*;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.setup.FProjectLgLitigationInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.setup.ProjectSetupService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 函、通知书审批流程处理
 *
 * @author heh
 *
 * 2016-9-21 16:32:44
 */
@Service("projectLetterProcessService")
public class ProjectLetterProcessServiceImpl extends BaseProcessService {

    @Autowired
    protected ProjectRelatedUserService projectRelatedUserService;
    @Autowired
    protected ProjectSetupService projectSetupService;
    @Autowired
    protected CommonAttachmentService commonAttachmentService;

    @Override
    public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
        FProjectContractDO projectContract = fProjectContractDAO.findByFormId(formInfo.getFormId());
        if (null == projectContract) {
            return null;
        }
//        List<FlowVarField> fields = Lists.newArrayList();
//        //法务专员
//        ProjectRelatedUserInfo legalManger = projectRelatedUserService.getLegalManager(projectContract
//                .getProjectCode());
//        //风险经理
//        ProjectRelatedUserInfo riskManger = projectRelatedUserService.getRiskManager(projectContract
//                .getProjectCode());
//        String isHavelegalCounsel = "0";
//        String riskManagerId = "0";
//        if (null != legalManger) {
//            isHavelegalCounsel = Long.toString(legalManger.getUserId());
//        }
//        if(null!=riskManger){
//            riskManagerId=Long.toString(riskManger.getUserId());
//        }
//        //是否非制式
//        String contractType="ZS";
//        List<FProjectContractItemDO> itemDOs = fProjectContractItemDAO
//                .findByFormContractId(projectContract.getContractId());
//        //将合同编码写入project_credit_condition表
//        for(FProjectContractItemDO itemDO:itemDOs){
//            if(itemDO.getContractType().equals(ContractTypeEnum.OTHER.code())){
//           contractType="FZS";
//            }
//        }
//        ProjectInfo projectInfo=projectService.queryByCode(projectContract.getProjectCode(),false);
//        if(ProjectUtil.isLitigation(projectInfo.getBusiType())){//诉讼保函项目风险经理去立项时的法务经理
//            FProjectLgLitigationInfo litigationInfo=projectSetupService.queryLgLitigationProjectByCode(projectInfo.getProjectCode());
//            riskManagerId=litigationInfo.getLegalManagerId()+"";
//        }
//        FlowVarField legalCounsel = new FlowVarField();
//        legalCounsel.setVarName("lawManagerID");
//        legalCounsel.setVarType(FlowVarTypeEnum.STRING);
//        legalCounsel.setVarVal(isHavelegalCounsel);
//        fields.add(legalCounsel);
//        FlowVarField riskM = new FlowVarField();
//        riskM.setVarName("riskManagerID");
//        riskM.setVarType(FlowVarTypeEnum.STRING);
//        riskM.setVarVal(riskManagerId);
//        fields.add(riskM);
//        FlowVarField contractT = new FlowVarField();
//        contractT.setVarName("contractType");
//        contractT.setVarType(FlowVarTypeEnum.STRING);
//        contractT.setVarVal(contractType);
//        fields.add(contractT);
//        return fields;

        //改成与签报一致
        //departmentLeader 部门负责人
        //companyLeader 公司领导
        //committeeMember 委员会
        //manager 经办人

        List<FlowVarField> fields = Lists.newArrayList();

        //自定义参数
        Map<String, Object> customizeMap = (Map<String, Object>) FcsPmDomainHolder.get()
                .getAttribute("customizeMap");

        if (customizeMap != null) {

            String leaderTaskNodeId = sysParameterService
                    .getSysParameterValue(SysParamEnum.FORM_CHANGE_LEADER_TASK_NODE_ID.code());
            String deptTaskNodeId = sysParameterService
                    .getSysParameterValue(SysParamEnum.FORM_CHANGE_SIGN_DEPARTMENT_TASK_NODE_ID.code());
            String wyhTaskNode = sysParameterService
                    .getSysParameterValue(SysParamEnum.FORM_CHANGE_WYH_TASK_NODE.code());
            String wyhTaskNodeId = null;
            if (com.yjf.common.lang.util.StringUtil.isNotBlank(wyhTaskNode)) {
                //wyhTaskNode.replaceAll("，", ",");
                String[] taskNodeAndWyh = wyhTaskNode.split(",");
                wyhTaskNodeId = taskNodeAndWyh[0];
            }

            String selNodeId = (String) customizeMap.get("selNodeId");
            String selManager = (String) customizeMap.get("selManager");

            if (com.yjf.common.lang.util.StringUtil.equals(selNodeId, deptTaskNodeId)) { //选择会签部门
                String signDepts = (String) customizeMap.get("signDepts");
                if (com.yjf.common.lang.util.StringUtil.isBlank(signDepts)) {
                    throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
                            "请选择会签部门");
                }

                String[] deptArr = signDepts.split(",");
                //				String fzrRole = sysParameterService
                //					.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code());

                //负责人去重
                Set<String> fzrUserId = new HashSet<String>();
                for (String deptCode : deptArr) {
                    //					List<SimpleUserInfo> fzrList = projectRelatedUserService.getDeptRoleUser(
                    //						deptCode, fzrRole);
                    List<SysUser> fzrList = bpmUserQueryService.findChargeByOrgCode(deptCode);
                    if (ListUtil.isNotEmpty(fzrList)) {
                        for (SysUser userInfo : fzrList) {
                            fzrUserId.add(String.valueOf(userInfo.getUserId()));
                        }
                    }
                }

                //部门负责人流程变量
                String departmentLeader = "";
                if (!fzrUserId.isEmpty()) {
                    int index = 0;
                    for (String userId : fzrUserId) {
                        if (index == 0) {
                            departmentLeader = userId;
                        } else {
                            departmentLeader += "," + userId;
                        }
                        index++;
                    }
                    FlowVarField flowVarField = new FlowVarField();
                    flowVarField.setVarName("departmentLeader");
                    flowVarField.setVarType(FlowVarTypeEnum.STRING);
                    flowVarField.setVarVal(departmentLeader);
                    fields.add(flowVarField);
                }
            } else if (com.yjf.common.lang.util.StringUtil.equals(selNodeId, leaderTaskNodeId)) { //选择公司领导
                //公司领导流程变量
                String leader = (String) customizeMap.get("leader");
                if (com.yjf.common.lang.util.StringUtil.isBlank(leader)) {
                    throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
                            "请选择相关领导");
                }
                FlowVarField flowVarField = new FlowVarField();
                flowVarField.setVarName("companyLeader");
                flowVarField.setVarType(FlowVarTypeEnum.STRING);
                flowVarField.setVarVal(leader);
                fields.add(flowVarField);

            } else if (com.yjf.common.lang.util.StringUtil.equals(selNodeId, wyhTaskNodeId)) { //选择委员会评委

                //评委流程变量
                String signMembers = (String) customizeMap.get("signMembers");
                if (com.yjf.common.lang.util.StringUtil.isBlank(signMembers)) {
                    throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
                            "请选择相关人员");
                }
                FlowVarField flowVarField = new FlowVarField();
                flowVarField.setVarName("committeeMember");
                flowVarField.setVarType(FlowVarTypeEnum.STRING);
                flowVarField.setVarVal(signMembers);
                fields.add(flowVarField);

            } else if (com.yjf.common.lang.util.StringUtil.equals(selManager, "YES")) { //选择经办人

                JSONObject json = getPrincipalAndOperator(formInfo.getRemark());
                JSONArray operator = json.getJSONArray("operator");

                //还要包括本次选择的人员
                String managerId = (String) customizeMap.get("managerId");
                long mId = NumberUtil.parseLong(managerId);

                String manager = "0";
                if (operator != null && !operator.isEmpty()) {
                    //去重
                    Set<String> managerUserId = new HashSet<String>();
                    for (Object userId : operator) {
                        managerUserId.add(userId.toString());
                    }

                    //包含本次选择的
                    if (mId > 0)
                        managerUserId.add(String.valueOf(mId));

                    int index = 0;
                    for (String userId : managerUserId) {
                        if (index == 0) {
                            manager = userId;
                        } else {
                            manager += "," + userId;
                        }
                        index++;
                    }
                } else { //本次选择的
                    manager = String.valueOf(mId);
                }
                FlowVarField flowVarField = new FlowVarField();
                flowVarField.setVarName("manager");
                flowVarField.setVarType(FlowVarTypeEnum.STRING);
                flowVarField.setVarVal(manager);
                fields.add(flowVarField);
            } else {
                JSONObject json = getPrincipalAndOperator(formInfo.getRemark());
                JSONArray principal = json.getJSONArray("principal");
                String departmentLeader1 = "";
                if (principal != null && !principal.isEmpty()) {
                    //去重
                    Set<String> fzrUserId = new HashSet<String>();
                    for (Object userId : principal) {
                        fzrUserId.add(userId.toString());
                    }
                    int index = 0;
                    for (String userId : fzrUserId) {
                        if (index == 0) {
                            departmentLeader1 = userId;
                        } else {
                            departmentLeader1 += "," + userId;
                        }
                        index++;
                    }
                    FlowVarField flowVarField = new FlowVarField();
                    flowVarField.setVarName("departmentLeader1");
                    flowVarField.setVarType(FlowVarTypeEnum.STRING);
                    flowVarField.setVarVal(departmentLeader1);
                    fields.add(flowVarField);
                }
            }
        }

        return fields;
    }

    /***
     * 获取经办人和负责人 {'principal':[userId,userId],'operator':[userId,userId]}
     * @param principalAndOperatorJson
     * @return
     */
    private JSONObject getPrincipalAndOperator(String principalAndOperatorJson) {
        JSONObject json = new JSONObject();
        try {
            if (com.yjf.common.lang.util.StringUtil.isNotBlank(principalAndOperatorJson)) {
                json = (JSONObject) JSONObject.parse(principalAndOperatorJson);
            }
        } catch (Exception e) {
            logger.error("解析负责人经办人出错 {}", e);
        }
        return json;
    }

    @Override
    public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
        FcsBaseResult result = new FcsBaseResult();
        try {
            FProjectContractDO DO = fProjectContractDAO.findByFormId(order.getFormInfo()
                    .getFormId());
            ProjectDO projectDO=projectDAO.findByProjectCode(DO.getProjectCode());
            List<FProjectContractItemDO> itemDOs = fProjectContractItemDAO
                    .findByFormContractId(DO.getContractId());
            for(FProjectContractItemDO itemDO:itemDOs){
                if(DO.getApplyType().equals(ProjectContractTypeEnum.PROJECT_LETTER.code())&& ProjectUtil.isLitigation(projectDO.getBusiType()) &&itemDO.getLetterType().equals("CONTRACT")&&
                        itemDO.getContractName() != null
                        && itemDO.getContractName().equals("诉讼保全担保函")&&(projectDO.getContractAmount().equals(Money.zero())|| StringUtil.isBlank(projectDO.getContractNo()))){
                    throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "诉讼保全担保函没有回传主合同，不能提交!");
                }
            }
            //自定义待办任务名称
            WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
                    .getAttribute("startOrder");
            if (startOrder != null) {
                startOrder.setCustomTaskName(DO.getProjectName() + "-文书/函/通知书审核");
            }
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("函、通知书审核流程启动前置处理出错 ： {}", e);
        }
        return result;
    }

    @Override
    public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        // 启动流程后业务处理(BASE)
        updateContractStatus(formInfo, "SUBMIT");
    }

    @Override
    public FcsBaseResult doNextBeforeProcess(final WorkFlowBeforeProcessOrder order) {
        return commonProcess(order, "函、通知书审核选择法务人员", new BeforeProcessInvokeService() {
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
                    relatedUser.setRemark("函、通知书审核选择法务人员");
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
        Map<String, Object> customizeMap = workflowResult.getCustomizeMap();
        if (customizeMap != null) {

            String leaderTaskNodeId = sysParameterService
                    .getSysParameterValue(SysParamEnum.FORM_CHANGE_LEADER_TASK_NODE_ID.code());
            String deptTaskNodeId = sysParameterService
                    .getSysParameterValue(SysParamEnum.FORM_CHANGE_SIGN_DEPARTMENT_TASK_NODE_ID.code());

            String selNodeId = (String) customizeMap.get("selNodeId");
            String selManager = (String) customizeMap.get("selManager");

            //选择领导或者选择部门清空 选择了经办人的负责人和经办人信息
            if (com.yjf.common.lang.util.StringUtil.equals(selNodeId, leaderTaskNodeId)
                    || com.yjf.common.lang.util.StringUtil.equals(selNodeId, deptTaskNodeId)) {
                FormDO formDO = formDAO.findByFormId(formInfo.getFormId());
                formDO.setRemark(null);
                formDAO.update(formDO);
            } else if (com.yjf.common.lang.util.StringUtil.equals(selManager, "YES")) { //选择经办人
                //当前操作人
                SimpleUserInfo currentUser = (SimpleUserInfo) FcsPmDomainHolder.get().getAttribute(
                        "currentUser");
                String managerId = (String) customizeMap.get("managerId");
                long mId = NumberUtil.parseLong(managerId);
                if (mId > 0) { //只记录选择的
                    JSONObject json = getPrincipalAndOperator(formInfo.getRemark());
                    JSONArray principal = json.getJSONArray("principal");
                    JSONArray operator = json.getJSONArray("operator");

                    if (principal == null)
                        principal = new JSONArray();
                    if (operator == null)
                        operator = new JSONArray();

                    principal.add(currentUser.getUserId());
                    operator.add(mId);
                    json.put("principal", principal);
                    json.put("operator", operator);
                    //记录选了经办人的负责人和经办人
                    FormDO formDO = formDAO.findByFormId(formInfo.getFormId());
                    formDO.setRemark(json.toString());
                    formDAO.update(formDO);
                }
            }
        }
    }

    @Override
    public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        // 结束流程后业务处理(BASE)
        updateContractStatus(formInfo, formInfo.getStatus().code());
        FProjectContractDO projectContract = fProjectContractDAO.findByFormId(formInfo.getFormId());
        List<FProjectContractItemDO> itemDOs = fProjectContractItemDAO
                .findByFormContractId(projectContract.getContractId());
        //将合同编码写入project_credit_condition表
        for(FProjectContractItemDO itemDO:itemDOs){
            if(null!=itemDO.getCreditMeasure()&&itemDO.getLetterType().equals(LetterTypeEnum.CONTRACT.code())){
                ProjectCreditConditionDO infoDO = projectCreditConditionDAO.findById(Long.parseLong(itemDO.getCreditMeasure()));
                infoDO.setContractNo(itemDO.getContractCode());
                projectCreditConditionDAO.update(infoDO);
            }
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
        updateContractStatus(formInfo,"DELETED");
        FProjectContractDO projectContract = fProjectContractDAO.findByFormId(formInfo.getFormId());
        List<FProjectContractItemDO> itemDOs = fProjectContractItemDAO
                .findByFormContractId(projectContract.getContractId());
        //删除附件
        for(FProjectContractItemDO DO:itemDOs) {
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

}
