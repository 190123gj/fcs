package com.born.fcs.pm.biz.service.contract;

import java.util.List;
import java.util.Map;

import com.born.fcs.pm.dal.dataobject.*;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.ContractTemplateStatusEnum;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 合同模板审批流程处理
 *
 * @author heh
 *
 * 2016-9-21 16:32:44
 */
@Service("contractTemplateProcessService")
public class ContractTemplateProcessServiceImpl extends BaseProcessService {

    @Override
    public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {

        ContractTemplateDO DO = contractTemplateDAO.findByFormId(formInfo
                .getFormId());

        if (null == DO) {
            return null;
        }
        List<FlowVarField> fields = Lists.newArrayList();
        FlowVarField legalCounsel = new FlowVarField();
        legalCounsel.setVarName("lawManager");
        legalCounsel.setVarType(FlowVarTypeEnum.STRING);
        legalCounsel.setVarVal(DO.getLegalManagerId()+"");
        fields.add(legalCounsel);
        return fields;
    }

    @Override
    public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
        FcsBaseResult result = new FcsBaseResult();
        try {
            ContractTemplateDO DO = contractTemplateDAO.findByFormId(order.getFormInfo()
                    .getFormId());
            //自定义待办任务名称
            WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
                    .getAttribute("startOrder");
            if (startOrder != null) {
                startOrder.setCustomTaskName(DO.getName() + "-合同模板审核");
            }
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("合同模板审核流程启动前置处理出错 ： {}", e);
        }
        return result;
    }

    @Override
    public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        // 启动流程后业务处理(BASE)
        updateStatus(formInfo, "SUBMIT");
    }

    @Override
    public FcsBaseResult doNextBeforeProcess(final WorkFlowBeforeProcessOrder order) {
        return commonProcess(order, "合同模板法务经理上传合同", new BeforeProcessInvokeService() {
            @Override
            public Domain before() {
                FormInfo formInfo = order.getFormInfo();
                Map<String, Object> customizeMap = order.getCustomizeMap();
                String isLegalManager = (String) customizeMap.get("isLegalManager");
                if ("IS".equals(isLegalManager)) {
                    ContractTemplateDO DO = contractTemplateDAO.findByFormId(formInfo
                            .getFormId());
                    String templateFile = (String) customizeMap.get("templateFile");
                    DO.setTemplateFile(templateFile);
                    contractTemplateDAO.update(DO);
                }
                return null;
            }
        }, null, null);
    }

    @Override
    public void doNextAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        // 审核后业务处理(BASE)
        updateStatus(formInfo, formInfo.getStatus().code());
    }

    @Override
    public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        // 结束流程后业务处理(BASE)
        ContractTemplateDO DO = contractTemplateDAO.findByFormId(formInfo.getFormId());
        DO.setStatus("IN_USE");
        contractTemplateDAO.update(DO);
        // 作废修订前的合同模板
        if(DO.getParentId()>0){
            ContractTemplateDO old= contractTemplateDAO.findById(DO.getParentId());
            if(old!=null){
                old.setStatus(ContractTemplateStatusEnum.INVALID.code());
                contractTemplateDAO.update(old);
            }
        }

    }

    @Override
    public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        // 手动结束流程后业务处理(BASE)

    }

    @Override
    public void deleteAfterProcess(FormInfo formInfo) {
        updateStatus(formInfo,"DELETED");
        ContractTemplateDO DO=contractTemplateDAO.findByFormId(formInfo.getFormId());
        //更新改被修订合同的状态
        if(DO.getParentId()>0){
            ContractTemplateDO old=contractTemplateDAO.findById(DO.getParentId());
            if(old!=null) {
                old.setRevised(null);
                contractTemplateDAO.update(old);
            }
        }
    }

    @Override
    public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        //提交人终止作废表单后业务处理(BASE)
        updateStatus(formInfo, "END");
    }

    @Override
    public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        updateStatus(formInfo, "CANCEL");
    }

    public void updateStatus(FormInfo formInfo, String status) {
        ContractTemplateDO DO = contractTemplateDAO.findByFormId(formInfo.getFormId());
        if(DO!=null){
            DO.setStatus(status);
            contractTemplateDAO.update(DO);
        }
    }

}
