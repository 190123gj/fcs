package com.born.fcs.crm.ws.service;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.info.CompanyOwnershipStructureInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 股东客户管理：股权结构
 * */
@WebService
public interface CompanyOwnershipStructureService {
	/** 添加股东 */
	FcsBaseResult add(CompanyOwnershipStructureInfo order);
	
	/** 更新股东信息 */
	FcsBaseResult updateById(CompanyOwnershipStructureInfo order);
	
	/** 批量更新股东信息 */
	FcsBaseResult updateByList(List<CompanyOwnershipStructureInfo> order, String customerId);
	
	//	/** 查询股东详情 */
	//	CompanyCustomerInfo queryById(long id);
	//	
	/** 删除 */
	FcsBaseResult delete(long id);
	
	/** 股东列表查询 */
	List<CompanyOwnershipStructureInfo> list(String customerId);
}
