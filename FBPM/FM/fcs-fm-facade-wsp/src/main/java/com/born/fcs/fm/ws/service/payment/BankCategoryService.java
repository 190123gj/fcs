package com.born.fcs.fm.ws.service.payment;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.payment.BankCategoryInfo;
import com.born.fcs.fm.ws.order.payment.BankCategoryOrder;
import com.born.fcs.fm.ws.order.payment.BankCategoryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
/**
 * 银行种类Service
 * 
 * @author lzb
 * 
 */
@WebService
public interface BankCategoryService {

	/**
	 * 查询列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<BankCategoryInfo> queryPage(BankCategoryQueryOrder order);
	
	
	/**
	 * 根据ID查询
	 * @param formId
	 * @return
	 */
	BankCategoryInfo queryById(long categoryId);
	
	
	/**
	 * 保存
	 * @param order
	 * @return
	 */
	FcsBaseResult save(BankCategoryOrder order);
	
	/**
	 * 删除
	 * @param order
	 * @return
	 */
	FcsBaseResult deleteById(long categoryId);

}
