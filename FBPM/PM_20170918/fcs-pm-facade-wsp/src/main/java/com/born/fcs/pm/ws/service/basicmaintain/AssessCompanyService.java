package com.born.fcs.pm.ws.service.basicmaintain;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.basicmaintain.AssessCompanyInfo;
import com.born.fcs.pm.ws.order.basicmaintain.AssessCompanyOrder;
import com.born.fcs.pm.ws.order.basicmaintain.AssessCompanyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import java.util.List;

/**
 * 评估公司管理数据维护
 *
 * @author heh
 */
@WebService
public interface AssessCompanyService {

    /**
     * 根据ID查询评估公司管理数据
     * @param companyId
     * @return
     */
    AssessCompanyInfo findById(long companyId);

    /**
     * 保存评估公司管理数据
     * @param order
     * @return
     */
    FcsBaseResult save(AssessCompanyOrder order);


    /**
     * 查询估公司管理数据
     * @param order
     * @return
     */
    QueryBaseBatchResult<AssessCompanyInfo> query(AssessCompanyQueryOrder order);


    /**
     * 根据ID删除公司管理数据
     * @param order
     * @return
     */
    FcsBaseResult deleteById( AssessCompanyOrder order);

    /**
     * 得到数据库里的城市
     * @return
     */
    List<String> findCities();

    /**
     * 根据公司名称查数据
     * @param companyName
     * @return
     */
    long findByName(String companyName);
}
