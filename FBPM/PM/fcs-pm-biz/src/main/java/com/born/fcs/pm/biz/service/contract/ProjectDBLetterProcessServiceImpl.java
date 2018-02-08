package com.born.fcs.pm.biz.service.contract;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.setup.FProjectLgLitigationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.*;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.*;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.setup.ProjectSetupService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 担保函/保证合同审批流程处理
 *
 * @author heh
 *
 * 2017-5-9 14:50:10
 */
@Service("projectDBLetterProcessService")
public class ProjectDBLetterProcessServiceImpl extends BaseProcessService {

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
        List<FlowVarField> fields = Lists.newArrayList();
        //法务专员
        ProjectRelatedUserInfo legalManger = projectRelatedUserService.getLegalManager(projectContract
                .getProjectCode());
        //风险经理
        ProjectRelatedUserInfo riskManger = projectRelatedUserService.getRiskManager(projectContract
                .getProjectCode());
        String isHavelegalCounsel = "0";
        String riskManagerId = "0";
        if (null != legalManger) {
            isHavelegalCounsel = Long.toString(legalManger.getUserId());
        }
        if(null!=riskManger){
            riskManagerId=Long.toString(riskManger.getUserId());
        }
        ProjectInfo projectInfo=projectService.queryByCode(projectContract.getProjectCode(),false);
        if(ProjectUtil.isLitigation(projectInfo.getBusiType())){//诉讼保函项目风险经理去立项时的法务经理
            FProjectLgLitigationInfo litigationInfo=projectSetupService.queryLgLitigationProjectByCode(projectInfo.getProjectCode());
            riskManagerId=litigationInfo.getLegalManagerId()+"";
        }
        FlowVarField legalCounsel = new FlowVarField();
        legalCounsel.setVarName("legalCounsel");
        legalCounsel.setVarType(FlowVarTypeEnum.STRING);
        legalCounsel.setVarVal(isHavelegalCounsel);
        fields.add(legalCounsel);
        FlowVarField riskM = new FlowVarField();
        riskM.setVarName("RiskManagerID");
        riskM.setVarType(FlowVarTypeEnum.STRING);
        riskM.setVarVal(riskManagerId);
        fields.add(riskM);
        return fields;

    }

    @Override
    public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
        FcsBaseResult result = new FcsBaseResult();
        try {
            FProjectContractDO DO = fProjectContractDAO.findByFormId(order.getFormInfo()
                    .getFormId());
            //自定义待办任务名称
            WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
                    .getAttribute("startOrder");
            if (startOrder != null) {
                startOrder.setCustomTaskName(DO.getProjectName() + "-担保函/保证合同审核");
            }
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("担保函/保证合同审核流程启动前置处理出错 ： {}", e);
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
        return commonProcess(order, "担保函/保证合同审核选择法务人员", new BeforeProcessInvokeService() {
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
                    relatedUser.setRemark("担保函/保证合同审核选择法务人员");
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
        updateContractStatus(formInfo, formInfo.getStatus().code());
    }

    @Override
    public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        //提交人终止作废表单后业务处理(BASE)
        updateContractStatus(formInfo, "END");
    }

    @Override
    public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        // 手动结束流程后业务处理(BASE)
        updateContractStatus(formInfo, "DENY");
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
