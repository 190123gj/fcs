package com.born.fcs.crm.ws.service;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.info.PersonalCustomerInfo;
import com.born.fcs.crm.ws.service.order.DistributionOrder;
import com.born.fcs.crm.ws.service.order.PersonalCustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.crm.ws.service.result.ValidateCustomerResult;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 个人客户管理：增加/修改/查询
 * */
@WebService
public interface PersonalCustomerService {
	/** 添加客户 */
	FcsBaseResult add(PersonalCustomerDetailOrder order);
	
	/**
	 * 校验客户名和证件号是否可用
	 * @param certNo 身份证
	 * @param customerName 客户名
	 * @param inputPerson 当前操作人
	 * */
	ValidateCustomerResult ValidateCustomer(String certNo, String customerName, Long inputPersonId);
	
	/**
	 * 查询客户项目
	 * @param userId 客户Id 对应pm中customerId
	 * */
	QueryBaseBatchResult<ProjectInfo> queryProject(ProjectQueryOrder order);
	
	/** 更新客户信息 */
	FcsBaseResult update(PersonalCustomerDetailOrder order);
	
	/**
	 * 查询客户详情
	 * @param customerId
	 * */
	PersonalCustomerInfo queryById(String customerId);
	
	/**
	 * 查询客户详情（其它系统使用）
	 * @param userId pm中对应customerId
	 * */
	PersonalCustomerInfo queryByUserId(Long userId);
	
	/** 通过证件号 查询个人客户详情 */
	PersonalCustomerInfo queryByCertNo(String certNo);
	
	/** 查询客户详情 */
	PersonalCustomerInfo queryByName(String customerName);
	
	/**
	 * 删除客户
	 * @param userId =pm中的customerId
	 * */
	FcsBaseResult delete(Long userId);
	
	/** 客户列表查询 */
	QueryBaseBatchResult<CustomerBaseInfo> list(CustomerQueryOrder queryOrder);
	
	/** 客户移交和分配 */
	FcsBaseResult distribution(DistributionOrder distributionOrder);
	
	/** 企业/个人 列表查询 */
	QueryBaseBatchResult<CustomerBaseInfo> customerList(CustomerQueryOrder queryOrder);
}
