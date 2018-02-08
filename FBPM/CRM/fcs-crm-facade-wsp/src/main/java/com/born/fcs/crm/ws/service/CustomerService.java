package com.born.fcs.crm.ws.service;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.order.ResotrePublicOrder;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.crm.ws.service.order.CustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.TransferAllocationOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.crm.ws.service.result.ChangeResult;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/***
 * 
 * 综合接口，整合个人与企业
 * 
 * */
@WebService
public interface CustomerService {
	
	//	/**
	//	 * 检查是否可签报 不分企业或个人
	//	 * @param userId 待修改客户Id
	//	 * @param loginUserId 操作人id
	//	 * */
	//	FcsBaseResult checkCanChange(Long userId, Long loginUserId);
	//	
	/**
	 * 更新客户信息 不分企业或个人
	 * @param customerType 客户类型必填
	 * */
	FcsBaseResult updateByUserId(CustomerDetailOrder customerDetailOrder);
	
	/** 签报开始后改变客户状态 ：IS：处于签报中 不使用 */
	FcsBaseResult setChangeStatus(Long userId, BooleanEnum status);
	
	/**
	 * 客户列表查询
	 * @param customerType 客户类型 企业或个人
	 * */
	QueryBaseBatchResult<CustomerBaseInfo> list(CustomerQueryOrder queryOrder);
	
	/**
	 * 查询客户详情 ：不分企业或个人
	 * @return
	 */
	CustomerDetailInfo queryByUserId(Long userId);
	
	/**
	 * 新增客户 不分企业或个人
	 * @param customerType 客户类型必填
	 * */
	FcsBaseResult add(CustomerDetailOrder customerDetailOrder);
	
	/**
	 * 删除客户
	 * */
	FcsBaseResult delete(Long userId);
	
	/**
	 * 客户信息是否修改 修改就返回修改内容
	 * */
	ChangeResult isChange(CustomerDetailOrder customerDetailOrder);
	
	/***
	 * 按地域统计
	 * @return
	 */
	FcsBaseResult statisticsCustomerRegion();
	
	/**
	 * 按部门统计
	 * @return
	 */
	FcsBaseResult statisticsCustomerDept();
	
	/**
	 * 有客户的所有部门
	 * @return
	 */
	FcsBaseResult allCustomerDept();
	
	/**
	 * 综合移交分配，多客户经理
	 * */
	FcsBaseResult transferAllocation(TransferAllocationOrder transferAllocationOrder);
	
	/**
	 * 还原成公海客户
	 * @param order
	 * @return
	 */
	FcsBaseResult resotrePublic(ResotrePublicOrder order);
}
