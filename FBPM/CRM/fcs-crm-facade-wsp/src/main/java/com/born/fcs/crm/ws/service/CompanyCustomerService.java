package com.born.fcs.crm.ws.service;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.order.CompanyCustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.UpdateFromCreditOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.crm.ws.service.result.ValidateCustomerResult;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 公司客户管理：增加/修改/查询
 * */
@WebService
public interface CompanyCustomerService {
	/** 添加客户 */
	FcsBaseResult add(CompanyCustomerDetailOrder order);
	
	/** 更新客户信息 */
	FcsBaseResult update(CompanyCustomerDetailOrder order);
	
	/** 校验客户是否可用 */
	ValidateCustomerResult ValidateCustomer(String busiLicenseNo, String customerName,
											Long inputPersonId);
	
	/** 征信查询回写数据 */
	FcsBaseResult updateFromCredit(UpdateFromCreditOrder order);
	
	/**
	 * 查询客户详情
	 * @param customerId
	 * */
	CompanyCustomerInfo queryById(String customerId, Long formId);
	
	/**
	 * 查询客户详情(其它系统使用)
	 * @param userId = customerId(pm系统)，
	 * */
	CompanyCustomerInfo queryByUserId(Long userId, Long formId);
	
	/** 查询客户详情 */
	CompanyCustomerInfo queryByName(String customerName);
	
	/** 通过营业执照号 查询客户详情 */
	CompanyCustomerInfo queryByBusiLicenseNo(String busiLicenseNo);
	
	/** 删除客户 */
	FcsBaseResult delete(Long userId);
	
	/** 删除资质证书 */
	FcsBaseResult deleteQualificationById(long id);
	
	/** 删除股东信息 */
	FcsBaseResult deleteOwnershipById(long id);
	
	/** 客户列表查询 */
	QueryBaseBatchResult<CustomerBaseInfo> list(CustomerQueryOrder queryOrder);
}
