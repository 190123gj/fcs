package com.born.fcs.pm.ws.service.forCrm;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.forCrm.IndirectCustomerInfo;
import com.born.fcs.pm.ws.info.forCrm.ViewChannelProjectAllPhasInfo;
import com.born.fcs.pm.ws.order.forCrm.IndirectCustomerQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 提供crm使用接口
 * @author zhaohaibing
 * */
@WebService
public interface CrmUseService {
	/**
	 * 查询渠道是否有项目在使用
	 * @param id 渠道Id
	 * */
	FcsBaseResult channalUsed(Long id);
	
	/**
	 * 查询历史间接客户
	 * */
	QueryBaseBatchResult<IndirectCustomerInfo> queryIndirectCustomer(	IndirectCustomerQueryOrder order);
	
	/**
	 * 通过项目编号和阶段 查询渠道信息
	 * @param projectCode 项目编号
	 * @param phas 阶段
	 * */
	ViewChannelProjectAllPhasInfo queryChannelByprojectCodeAndPhas(String projectCode, long phas);
	
}
