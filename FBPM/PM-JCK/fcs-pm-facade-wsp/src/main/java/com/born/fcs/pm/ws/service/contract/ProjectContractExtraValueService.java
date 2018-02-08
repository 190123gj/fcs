/**
 * 
 */
package com.born.fcs.pm.ws.service.contract;

import javax.jws.WebService;

import com.born.fcs.pm.ws.order.contract.ProjectContractExtraValueBatchOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.contract.ProjectContractExtraValueInfoResult;

/**
 * @author jiajie
 * 
 */
@WebService
public interface ProjectContractExtraValueService {

	// 任务一 批量插入input数据到数据表【带修改能力】
	/**
	 * 批量插入input数据到数据表【name相同value不同就修改原值并记录在值修改信息记录表里】
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult extraValueSave(ProjectContractExtraValueBatchOrder order);

	// 任务二 批量获取extra的数据，入参数contract_item_id
	/**
	 * 批量获取extravalue的数据，入参数contract_item_id 单个合同id
	 * 
	 * @param id
	 * @return
	 */
	ProjectContractExtraValueInfoResult findByContractItemId(Long id);
}
