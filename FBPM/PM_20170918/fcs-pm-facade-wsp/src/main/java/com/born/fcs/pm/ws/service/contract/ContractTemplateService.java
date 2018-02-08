package com.born.fcs.pm.ws.service.contract;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.contract.ContractTemplateInfo;
import com.born.fcs.pm.ws.order.contract.ContractTemplateOrder;
import com.born.fcs.pm.ws.order.contract.ContractTemplateQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import java.util.List;

/**
 * 合同模板管理维护
 *
 * @author heh
 */
@WebService
public interface ContractTemplateService {

    /**
     * 根据ID查询合同模板信息
     * @param id
     * @return
     */
    ContractTemplateInfo findById(long id);

    /**
     * 根据ID查询合同模板信息
     * @param formId
     * @return
     */
    ContractTemplateInfo findByFormId(long formId);

    /**
     * 保存合同模板
     * @param order
     * @return
     */
    FcsBaseResult save(ContractTemplateOrder order);


    /**
     * 查询合同模板信息
     * @param order
     * @return
     */
    QueryBaseBatchResult<ContractTemplateInfo> query(ContractTemplateQueryOrder order);



    /**
     * 根据ID删除合同模板
     * @param order
     * @return
     */
    FcsBaseResult deleteById(ContractTemplateOrder order);

    /**
     * 根据反担保措施查询合同模板
     * @param busiType
     * @return
     */
    List<ContractTemplateInfo> queryTemplates(String busiType);

}
