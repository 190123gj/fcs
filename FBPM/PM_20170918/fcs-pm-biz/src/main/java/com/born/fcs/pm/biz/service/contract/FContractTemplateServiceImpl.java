package com.born.fcs.pm.biz.service.contract;

import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.ContractTemplateDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.contract.FContractTemplateOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.service.contract.FContractTemplateService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("fContractTemplateService")
public class FContractTemplateServiceImpl extends BaseFormAutowiredDomainService implements FContractTemplateService
         {
             @Override
             public FormBaseResult save(final FContractTemplateOrder order) {
                 return commonFormSaveProcess(order, "保存走流程合同模板", new BeforeProcessInvokeService() {

                     @SuppressWarnings("deprecation")
                     @Override
                     public Domain before() {
                         ContractTemplateDO contract = null;
                         final Date now = FcsPmDomainHolder.get().getSysDate();
                         if (order.getTemplateId() != null && order.getTemplateId() > 0) {
                             contract = contractTemplateDAO.findById(order.getTemplateId());
                             if (contract == null) {
                                 throw ExceptionFactory
                                         .newFcsException(FcsResultEnum.HAVE_NOT_DATA, "合同模板不存在");
                             }
                         }

                         if (contract == null) { //新增
                             FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
                             contract = new ContractTemplateDO();
                             BeanCopier.staticCopy(order, contract, UnBoxingConverter.getInstance());
                             contract.setRawAddTime(now);
                                 contract.setStatus("DRAFT");
                             contract.setFormId(formInfo.getFormId());
                             contractTemplateDAO.insert(contract);
                             //更新被修订合同的状态
                             if(order.getParentId()>0){
                                 ContractTemplateDO  old = contractTemplateDAO.findById(order.getParentId());
                                 old.setRevised("IS");
                                 contractTemplateDAO.update(old);
                             }
                         } else { //修改
                             contract = contractTemplateDAO.findById(order.getTemplateId());
                             String status=contract.getStatus();
                             BeanCopier.staticCopy(order, contract, UnBoxingConverter.getInstance());
                                 contract.setStatus(status);
                             contractTemplateDAO.update(contract);
                         }

                         return null;
                     }
                 }, null, null);

             }
         }
