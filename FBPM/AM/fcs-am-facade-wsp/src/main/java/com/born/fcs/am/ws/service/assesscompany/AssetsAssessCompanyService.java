package com.born.fcs.am.ws.service.assesscompany;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.am.ws.info.assesscompany.AssessCompanyContactInfo;
import com.born.fcs.am.ws.info.assesscompany.AssetsAssessCompanyInfo;
import com.born.fcs.am.ws.order.assesscompany.AssetsAssessCompanyOrder;
import com.born.fcs.am.ws.order.assesscompany.AssetsAssessCompanyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 资产-评估公司
 *
 * @author jil
 */
@WebService
public interface AssetsAssessCompanyService {

	/**
	 * 根据ID查询评估公司
	 * 
	 * @param id
	 * @return
	 */
	AssetsAssessCompanyInfo findById(long id);

	/**
	 * 保存评估公司信息
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult save(AssetsAssessCompanyOrder order);

	/**
	 * 查询评估公司信息
	 * 
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<AssetsAssessCompanyInfo> query(
			AssetsAssessCompanyQueryOrder order);

	/**
	 * 根据ID删除评估公司
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult deleteById(AssetsAssessCompanyOrder order);

	/**
	 * 根据评估公司名称检查是否重名
	 * 
	 * @param companyName
	 * @return
	 */
	long findByCompanyNameCount(String companyName);

	/**
	 * 根据评估公司id 查询联系人
	 * 
	 * @param assessCompanyId
	 * @return
	 */
	List<AssessCompanyContactInfo> findContactByCompanyId(long assessCompanyId);

	/**
	 * 得到数据库里的城市
	 * 
	 * @return
	 */
	List<String> findCities();
}
