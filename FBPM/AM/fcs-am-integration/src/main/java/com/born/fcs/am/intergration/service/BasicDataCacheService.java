package com.born.fcs.am.intergration.service;

import java.util.List;

import com.born.fcs.pm.ws.info.common.BusiTypeInfo;
import com.born.fcs.pm.ws.info.common.EnterpriseScaleRuleInfo;
import com.born.fcs.pm.ws.info.common.IndustryInfo;
import com.born.fcs.pm.ws.info.common.RegionInfo;

public interface BasicDataCacheService extends SysClearWebCacheService {

	/**
	 * 根据父节点查询行业
	 * 
	 * @param parentCode
	 * @return
	 */
	List<IndustryInfo> queryIndustry(String parentCode);

	/**
	 * 根据父节点查询地区
	 * 
	 * @param parentCode
	 * @return
	 */
	List<RegionInfo> queryRegion(String parentCode);

	/**
	 * 查询业务类型
	 * 
	 * @param customerType
	 * @param formCode
	 * @param parentId
	 * @return
	 */
	List<BusiTypeInfo> queryBusiType(String customerType, String formCode,
			int parentId);

	/**
	 * 查询单个业务类型
	 * 
	 * @param customerType
	 * @param code
	 * @return
	 */
	BusiTypeInfo querySingleBusiType(String code, String customerType);

	/**
	 * 查询企业规模计算
	 * 
	 * @return
	 */
	List<EnterpriseScaleRuleInfo> queryEnterpriseScaleRule();
}
