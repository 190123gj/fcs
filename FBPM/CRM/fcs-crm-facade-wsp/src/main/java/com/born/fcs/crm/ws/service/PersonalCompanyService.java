package com.born.fcs.crm.ws.service;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.info.PersonalCompanyInfo;
import com.born.fcs.crm.ws.service.order.PersonalCompanyOrder;
import com.born.fcs.crm.ws.service.order.query.PersonalCompanyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 个人公司
 * */
@WebService
public interface PersonalCompanyService {
	/** 添加公司 */
	FcsBaseResult add(PersonalCompanyOrder order);
	
	/** 更新公司信息 */
	FcsBaseResult updateById(PersonalCompanyOrder order);
	
	/** 批量更新公司信息 */
	FcsBaseResult updateByList(List<PersonalCompanyOrder> list, String customerId);
	
	/** 查询公司详情 */
	CompanyCustomerInfo queryById(long id);
	
	/** 删除 */
	FcsBaseResult delete(long id);
	
	/** 公司列表查询 */
	QueryBaseBatchResult<PersonalCompanyInfo> list(PersonalCompanyQueryOrder queryOrder);
}
