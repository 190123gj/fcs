package com.born.fcs.face.integration.pm.service.contract;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.order.contract.FContractTemplateOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.service.contract.FContractTemplateService;
import org.springframework.stereotype.Service;

@Service("fContractTemplateServiceClient")
public class FContractTemplateServiceClient extends ClientAutowiredBaseService implements
        FContractTemplateService {
    @Override
    public FormBaseResult save(final FContractTemplateOrder order) {
        return callInterface(new CallExternalInterface<FormBaseResult>() {
            @Override
            public FormBaseResult call() {
                return fContractTemplateWebService.save(order);
            }
        });
    }
}
