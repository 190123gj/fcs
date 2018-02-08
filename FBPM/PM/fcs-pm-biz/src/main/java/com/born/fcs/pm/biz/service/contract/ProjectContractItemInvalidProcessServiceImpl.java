package com.born.fcs.pm.biz.service.contract;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.*;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.*;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.stampapply.StampApplyProjectResultInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.stampapply.StampApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.stampapply.StampApplyService;
import com.yjf.common.lang.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rop.thirdparty.com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同作废
 *
 * @author heh
 *
 * 2017年3月20日13:53:46
 */
@Service("projectContractInvalidProcessService")
public class ProjectContractItemInvalidProcessServiceImpl extends BaseProcessService {

    @Autowired
    protected StampApplyService stampApplyService;

    public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
        FProjectContractItemInvalidDO invalidDO=fProjectContractItemInvalidDAO.findByFormId(formInfo.getFormId());
        //查找该合同以前申请过的用印记录
        List<FStampApplyFileDO> fileList=fStampApplyFileDAO.findByContractCode(invalidDO.getContractCode());
        Map<String,String> orgMap=new HashMap<String, String>();
        Map<String,String> gzMap=new HashMap<String, String>();
        Map<String,String> frzMap=new HashMap<String, String>();
        for (FStampApplyFileDO DO : fileList) {
            FStampApplyDO applyDO=fStampApplyDAO.findById(DO.getApplyId());
            if(applyDO.getOrgNames()!=null){
                String orgNames[]=applyDO.getOrgNames().split(",");
                for(String orgName:orgNames){
                    StampConfigureDO configureDO=stampConfigureDAO.findByOrgName(orgName);
                    if(configureDO==null){
                        throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
                                "未找到["+orgName+"]印章配置信息");
                    }
                    orgMap.put(orgName,orgName);
                    if (DO.getCachetNum() != 0) {
                        gzMap.put(configureDO.getGzRoleCode(),configureDO.getGzRoleCode());
                    }
                    if (DO.getLegalChapterNum() != 0) {
                        frzMap.put(configureDO.getFrzRoleCode(),configureDO.getFrzRoleCode());
                    }
                }
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
        if(StringUtil.isNotBlank(gzfrzAudit)){
            gzfrzAudit=gzfrzAudit.substring(0,gzfrzAudit.length()-1);
        }
        List<FlowVarField> fields = Lists.newArrayList();
        FlowVarField YZAudit = new FlowVarField();
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
            FProjectContractItemInvalidDO DO = fProjectContractItemInvalidDAO.findByFormId(order.getFormInfo()
                    .getFormId());

            //自定义待办任务名称
            WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
                    .getAttribute("startOrder");
            if (startOrder != null) {
                startOrder.setCustomTaskName(DO.getProjectName() + "-合同作废申请");
            }
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error("合同作废申请流程启动前置处理出错 ： {}", e);
        }
        return result;
    }

    @Override
    public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {


    }




    @Override
    public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        // 结束流程后业务处理(BASE)
        //更新合同状态
        FProjectContractItemInvalidDO invalidDO=fProjectContractItemInvalidDAO.findByFormId(formInfo.getFormId());
        FProjectContractItemDO itemDO=fProjectContractItemDAO.findByContractCode(invalidDO.getContractCode());
        itemDO.setContractStatus(ContractStatusEnum.INVALID.code());
        fProjectContractItemDAO.update(itemDO);
        List<FStampApplyFileDO> fileList=fStampApplyFileDAO.findByContractCode(invalidDO.getContractCode());
        for (FStampApplyFileDO DO : fileList) {
            DO.setInvalid("IS");
            fStampApplyFileDAO.update(DO);
        }

    }

    @Override
    public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        //提交人终止作废表单后业务处理(BASE)

    }

    @Override
    public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
        // 手动结束流程后业务处理(BASE)

    }

    @Override
    public void deleteAfterProcess(FormInfo formInfo) {
        //更新合同状态
        FProjectContractItemInvalidDO invalidDO=fProjectContractItemInvalidDAO.findByFormId(formInfo.getFormId());
        FProjectContractItemDO itemDO=fProjectContractItemDAO.findByContractCode(invalidDO.getContractCode());
        itemDO.setContractStatus(ContractStatusEnum.SEAL.code());
        fProjectContractItemDAO.update(itemDO);
    }

    @Override
    public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
    }
}
