package com.born.fcs.pm.ws.service.contract;

import com.born.fcs.pm.ws.order.contract.FContractTemplateOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;

import javax.jws.WebService;

/**
 * 需要走合同模板管理维护
 *
 * @author heh
 */
@WebService
public interface FContractTemplateService {
    /**
     * 保存需要走流程的合同模板
     * @param order
     * @return
     */
    FormBaseResult save(FContractTemplateOrder order);
}
