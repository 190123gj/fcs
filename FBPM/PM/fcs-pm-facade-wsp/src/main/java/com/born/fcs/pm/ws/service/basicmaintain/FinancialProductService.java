package com.born.fcs.pm.ws.service.basicmaintain;

import javax.jws.WebService;

import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.info.common.FinancialProductInfo;
import com.born.fcs.pm.ws.order.basicmaintain.FinancialProductOrder;
import com.born.fcs.pm.ws.order.basicmaintain.FinancialProductQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 理财产品数据维护
 *
 * @author wuzj
 */
@WebService
public interface FinancialProductService {
	
	/**
	 * 根据ID查询理财产品
	 * @param productId
	 * @return
	 */
	FinancialProductInfo findById(long productId);
	
	/**
	 * 根据3要素查询理财产品
	 * @param productName
	 * @param issueInstitution
	 * @param productType
	 * @return
	 */
	FinancialProductInfo findByUnique(String productName);
	
	/**
	 * 保存理财产品
	 * @param order
	 * @return
	 */
	FcsBaseResult save(FinancialProductOrder order);
	
	/**
	 * 修改销售状态
	 * @param productId
	 * @param sellStatus
	 * @return
	 */
	FcsBaseResult changeSellStatus(FinancialProductOrder order);
	
	/**
	 * 查询理财产品
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FinancialProductInfo> query(FinancialProductQueryOrder order);
	
	/**
	 * 计算理财产品属于短期还是中长期
	 * @param timeLimit
	 * @param timeUnit
	 * @return
	 */
	FinancialProductTermTypeEnum calculateProductTermType(int timeLimit, String timeUnit);
}
