package com.born.fcs.rm.ws.service.accountbalance;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceDataInfo;
import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceInfo;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceDataQueryOrder;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceOrder;
import com.born.fcs.rm.ws.order.accountbalance.AccountBalanceQueryOrder;

/**
 * 
 * 科目余额
 * 
 * @author lirz
 *
 * 2016-8-4 下午3:51:52
 */
@WebService
public interface AccountBalanceService {

	/**
	 * 
	 * 保存科目余额表
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult save(AccountBalanceOrder order);

	/**
	 * 
	 * 查看详情
	 * 
	 * @param id
	 * @return
	 */
	AccountBalanceInfo findById(long id);

	/**
	 * 
	 * 查询列表
	 * 
	 * @param queryOrder
	 * @return
	 */
    QueryBaseBatchResult<AccountBalanceInfo> queryList(AccountBalanceQueryOrder queryOrder);
    
    /**
     *  查询科目余额数据
     *  
     * @param queryOrder
     * @return
     */
    List<AccountBalanceDataInfo> queryDatas(AccountBalanceDataQueryOrder queryOrder);
    
    /**
     * 查询科目余额数据(1条)
     * @param queryOrder
     * @return
     */
    AccountBalanceDataInfo queryData(AccountBalanceDataQueryOrder queryOrder);

}
