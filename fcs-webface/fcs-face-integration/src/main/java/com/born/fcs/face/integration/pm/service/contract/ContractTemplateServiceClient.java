package com.born.fcs.face.integration.pm.service.contract;

import com.born.fcs.pm.ws.info.contract.ContractTemplateInfo;
import com.born.fcs.pm.ws.order.contract.ContractTemplateOrder;
import com.born.fcs.pm.ws.order.contract.ContractTemplateQueryOrder;
import com.born.fcs.pm.ws.order.contract.FContractTemplateOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.service.contract.ContractTemplateService;
import com.born.fcs.pm.ws.service.contract.FContractTemplateService;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.creditrefrerenceapply.CreditRefrerenceApplyInfo;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyOrder;
import com.born.fcs.pm.ws.order.creditrefrerenceapply.CreditRefrerenceApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.creditrefrerenceapply.CreditRefrerenceApplyService;

import java.util.List;

/**
 *
 * @author heh
 *
 */
@Service("contractTemplateServiceClient")
public class ContractTemplateServiceClient extends ClientAutowiredBaseService implements
        ContractTemplateService {


    @Override
    public ContractTemplateInfo findById(final long id) {
        return callInterface(new CallExternalInterface<ContractTemplateInfo>() {

            @Override
            public ContractTemplateInfo call() {
                return contractTemplateWebService.findById(id);
            }
        });
    }

    @Override
    public ContractTemplateInfo findByFormId(final long formId) {
        return callInterface(new CallExternalInterface<ContractTemplateInfo>() {

            @Override
            public ContractTemplateInfo call() {
                return contractTemplateWebService.findByFormId(formId);
            }
        });
    }

    @Override
    public FcsBaseResult save(final ContractTemplateOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {

            @Override
            public FcsBaseResult call() {
                return contractTemplateWebService.save(order);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<ContractTemplateInfo> query(final ContractTemplateQueryOrder order) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<ContractTemplateInfo>>() {

            @Override
            public QueryBaseBatchResult<ContractTemplateInfo> call() {
                return contractTemplateWebService.query(order);
            }
        });
    }



    @Override
    public FcsBaseResult deleteById(final ContractTemplateOrder order) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {
            @Override
            public FcsBaseResult call() {
                return contractTemplateWebService.deleteById(order);
            }
        });
    }

    @Override
    public List<ContractTemplateInfo> queryTemplates(final String busiType) {
        return callInterface(new CallExternalInterface<List<ContractTemplateInfo>>() {
            @Override
            public List<ContractTemplateInfo> call() {
                return contractTemplateWebService.queryTemplates(busiType);
            }
        });
    }

}
