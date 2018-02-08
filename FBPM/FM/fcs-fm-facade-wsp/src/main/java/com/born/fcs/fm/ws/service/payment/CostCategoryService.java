package com.born.fcs.fm.ws.service.payment;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.order.payment.CostCategoryOrder;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
/**
 * 费用种类Service
 * 
 * @author lzb
 * 
 */
@WebService
public interface CostCategoryService {

	/**
	 * 查询列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<CostCategoryInfo> queryPage(CostCategoryQueryOrder order);
	
	
	/**
	 * 根据ID查询
	 * @param formId
	 * @return
	 */
	CostCategoryInfo queryById(long categoryId);
	
	
	/**
	 * 保存
	 * @param order
	 * @return
	 */
	FcsBaseResult save(CostCategoryOrder order);
	
	/**
	 * 删除
	 * @param order
	 * @return
	 */
	FcsBaseResult deleteById(long categoryId);

}
