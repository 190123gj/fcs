package com.born.fcs.crm.ws.service;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.info.ChannalContractInfo;
import com.born.fcs.crm.ws.service.order.ChannalContractOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalContractQueryOrder;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 渠道合同
 * */
@WebService
public interface ChannalContractService {
	
	/** 保存合同 */
	FcsBaseResult save(ChannalContractOrder order);
	
	/** 列表查询 */
	QueryBaseBatchResult<ChannalContractInfo> list(ChannalContractQueryOrder queryOrder);
	
	/** 合同详情查询 */
	ChannalContractInfo info(long formId);
	
	/**
	 * 修改合同表单状态
	 * @param contractNo 合同编号
	 * @param status 状态 INVALID： 这里当做 “已使用”
	 * */
	FcsBaseResult updateStatus(String contractNo, ContractStatusEnum status);
	
	/**
	 * 合同回传
	 * @param contractReturn 合同Url
	 * @param formId 合同表单号
	 */
	FcsBaseResult returnContract(long formId, String contractReturn);
	
}
