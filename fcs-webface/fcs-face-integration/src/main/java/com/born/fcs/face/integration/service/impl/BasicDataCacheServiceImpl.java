package com.born.fcs.face.integration.service.impl;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.crm.ws.service.enums.ChanalTypeEnum;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.order.query.ChannalQueryOrder;
import com.born.fcs.face.integration.crm.service.channal.ChannalClient;
import com.born.fcs.face.integration.service.BasicDataCacheService;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.info.common.BusiTypeInfo;
import com.born.fcs.pm.ws.info.common.EnterpriseScaleRuleInfo;
import com.born.fcs.pm.ws.info.common.IndustryInfo;
import com.born.fcs.pm.ws.info.common.RegionInfo;
import com.born.fcs.pm.ws.order.common.BusiTypeQueryOrder;
import com.born.fcs.pm.ws.order.common.IndustryQueryOrder;
import com.born.fcs.pm.ws.order.common.RegionQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.BasicDataService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("basicDataCacheService")
public class BasicDataCacheServiceImpl implements BasicDataCacheService {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BasicDataService basicDataServiceClient;
	
	@Autowired
	ChannalClient channalClient;
	
	//根节点
	private final static String ROOT = "root";
	
	//行业信息缓存
	private static Map<String, List<IndustryInfo>> intustryMap = Maps.newHashMap();
	
	//行业
	private static Map<String, IndustryInfo> singleIndustryMap = Maps.newHashMap();
	
	//行政区域缓存
	private static Map<String, List<RegionInfo>> regionMap = Maps.newHashMap();
	
	//企业规模计算规则缓存
	private static Map<String, List<EnterpriseScaleRuleInfo>> enterpriseScaleRuleMap = Maps
		.newHashMap();
	
	//业务类型
	private static Map<String, List<BusiTypeInfo>> busiTypeMap = Maps.newHashMap();
	
	//业务类型
	private static Map<String, BusiTypeInfo> singleBusiTypeMap = Maps.newHashMap();
	
	//渠道
	private static Map<String, List<ChannalInfo>> channelMap = Maps.newHashMap();
	
	@Override
	public void clearCache() {
		if (intustryMap != null) {
			intustryMap.clear();
		}
		if (singleIndustryMap != null) {
			singleIndustryMap.clear();
		}
		if (regionMap != null) {
			regionMap.clear();
		}
		if (enterpriseScaleRuleMap != null) {
			enterpriseScaleRuleMap.clear();
		}
		if (busiTypeMap != null) {
			busiTypeMap.clear();
		}
		if (singleBusiTypeMap != null) {
			singleBusiTypeMap.clear();
		}
		
		if (channelMap != null) {
			channelMap.clear();
		}
	}
	
	@Override
	public List<IndustryInfo> queryIndustry(String parentCode) {
		
		if (StringUtil.isEmpty(parentCode)) {
			parentCode = ROOT;
		}
		
		List<IndustryInfo> data = intustryMap.get(parentCode);
		if (data != null) {
			logger.info("从缓存中获取行业信息...");
			return data;
		} else {
			logger.info("从接口获取行业信息...");
			IndustryQueryOrder order = new IndustryQueryOrder();
			order.setParentCode(parentCode);
			order.setPageSize(999);
			QueryBaseBatchResult<IndustryInfo> result = basicDataServiceClient.queryIndustry(order);
			data = result.getPageList();
			intustryMap.put(parentCode, data);
			return data;
		}
		
	}
	
	@Override
	public IndustryInfo querySingleIndustry(String code) {
		
		IndustryInfo data = singleIndustryMap.get(code);
		if (data != null) {
			logger.info("从缓存中获取行业信息...");
			return data;
		} else {
			logger.info("从接口获取行业信息...");
			IndustryQueryOrder order = new IndustryQueryOrder();
			order.setCode(code);
			order.setPageSize(1);
			QueryBaseBatchResult<IndustryInfo> result = basicDataServiceClient.queryIndustry(order);
			if (result != null && result.isSuccess() && result.getTotalCount() > 0) {
				data = result.getPageList().get(0);
				singleIndustryMap.put(code, data);
			}
			return data;
		}
		
	}
	
	@Override
	public List<RegionInfo> queryRegion(String parentCode) {
		
		if (StringUtil.isEmpty(parentCode)) {
			parentCode = ROOT;
		}
		
		//		List<RegionInfo> data = regionMap.get(parentCode);
		//		if (data != null) {
		//			logger.info("从缓存中获取区域信息...");
		//			return data;
		//		} else {
		logger.info("从接口中获取区域信息...");
		RegionQueryOrder order = new RegionQueryOrder();
		order.setParentCode(parentCode);
		order.setPageSize(999);
		QueryBaseBatchResult<RegionInfo> result = basicDataServiceClient.queryRegion(order);
		List<RegionInfo> data = result.getPageList();
		regionMap.put(parentCode, data);
		return data;
		//		}
	}
	
	@Override
	public List<EnterpriseScaleRuleInfo> queryEnterpriseScaleRule() {
		List<EnterpriseScaleRuleInfo> data = enterpriseScaleRuleMap.get("rules");
		if (data != null) {
			logger.info("从缓存中获取企业规模规则信息...");
			return data;
		} else {
			logger.info("从接口中获取企业规模规则信息...");
			data = basicDataServiceClient.queryEnterpriseScaleRule();
			enterpriseScaleRuleMap.put("rules", data);
			return data;
		}
	}
	
	@Override
	public List<BusiTypeInfo> queryBusiType(String customerType, String formCode, int parentId) {
		
		String key = StringUtil.nullToEmpty(customerType) + StringUtil.nullToEmpty(formCode)
						+ parentId;
		
		List<BusiTypeInfo> data = busiTypeMap.get(key);
		if (data != null) {
			logger.info("从缓存中获取业务类型信息...");
			return data;
		} else {
			logger.info("从接口中获取业务类型信息...");
			BusiTypeQueryOrder order = new BusiTypeQueryOrder();
			order.setCustomerType(customerType);
			order.setParentId(parentId);
			order.setId(0);
			order.setSetupFormCode(formCode);
			order.setPageSize(999);
			order.setIsDel(BooleanEnum.NO);
			QueryBaseBatchResult<BusiTypeInfo> batchResult = basicDataServiceClient
				.queryBusiType(order);
			data = batchResult.getPageList();
			Ordering<BusiTypeInfo> ordering = Ordering.natural().nullsFirst().onResultOf(
					new Function<BusiTypeInfo, Integer>() {
						public Integer apply(BusiTypeInfo foo) {
							return foo.getSortOrder();
						}
					});
			data = ordering.sortedCopy(data);
			busiTypeMap.put(key, data);
			return data;
		}
	}
	
	@Override
	public BusiTypeInfo querySingleBusiType(String code, String customerType) {
		
		if (StringUtil.isEmpty(customerType)) {
			customerType = CustomerTypeEnum.ENTERPRISE.code();
		}
		String key = customerType + code;
		
		BusiTypeInfo busiType = singleBusiTypeMap.get(key);
		if (busiType != null) {
			logger.info("从缓存中获取业务类型信息...");
			return busiType;
		} else {
			logger.info("从接口中获取业务类型信息...");
			busiType = basicDataServiceClient.querySingleBusiType(code, customerType);
			singleBusiTypeMap.put(key, busiType);
			return busiType;
		}
	}
	
	@Override
	public List<ChannalInfo> queryChannelByType(ChanalTypeEnum type) {
		if (type == null) {
			return null;
		}
		//		List<ChannalInfo> channels = channelMap.get(type.code());
		//		if (channels != null) {
		//			logger.info("从缓存中获取渠道信息...");
		//			return channels;
		//		} else {
		logger.info("从接口中获取渠道信息...");
		ChannalQueryOrder order = new ChannalQueryOrder();
		order.setChannelType(type.code());
		order.setPageSize(999);
		order.setStatus("on");
		QueryBaseBatchResult<ChannalInfo> batchaResult = channalClient.list(order);
		if (batchaResult != null && batchaResult.isSuccess()) {
			channelMap.put(type.code(), batchaResult.getPageList());
			return batchaResult.getPageList();
		} else {
			return null;
		}
		//		}
	}
}
