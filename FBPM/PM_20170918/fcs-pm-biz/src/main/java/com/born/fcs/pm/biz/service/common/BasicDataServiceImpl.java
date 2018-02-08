/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.biz.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.daointerface.BusiTypeDAO;
import com.born.fcs.pm.dal.daointerface.EnterpriseScaleRuleDAO;
import com.born.fcs.pm.dal.daointerface.IndustryDAO;
import com.born.fcs.pm.dal.daointerface.RegionDAO;
import com.born.fcs.pm.dal.daointerface.SysConfigDAO;
import com.born.fcs.pm.dal.dataobject.BusiTypeDO;
import com.born.fcs.pm.dal.dataobject.EnterpriseScaleRuleDO;
import com.born.fcs.pm.dal.dataobject.IndustryDO;
import com.born.fcs.pm.dal.dataobject.RegionDO;
import com.born.fcs.pm.dal.dataobject.SysConfigDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ScaleRuleKpiTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.basicmaintain.SysConfigInfo;
import com.born.fcs.pm.ws.info.common.BusiTypeInfo;
import com.born.fcs.pm.ws.info.common.EnterpriseScaleRuleInfo;
import com.born.fcs.pm.ws.info.common.IndustryInfo;
import com.born.fcs.pm.ws.info.common.RegionInfo;
import com.born.fcs.pm.ws.order.basicmaintain.SysConfigOrder;
import com.born.fcs.pm.ws.order.common.BusiTypeQueryOrder;
import com.born.fcs.pm.ws.order.common.IndustryQueryOrder;
import com.born.fcs.pm.ws.order.common.RegionOrder;
import com.born.fcs.pm.ws.order.common.RegionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.BasicDataService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("basicDataService")
public class BasicDataServiceImpl extends BaseAutowiredDomainService implements BasicDataService {
	
	@Autowired
	protected IndustryDAO industryDAO;
	
	@Autowired
	protected RegionDAO regionDAO;
	
	@Autowired
	protected EnterpriseScaleRuleDAO enterpriseScaleRuleDAO;
	
	@Autowired
	protected BusiTypeDAO busiTypeDAO;
	
	@Autowired
	SysConfigDAO sysConfigDAO;
	
	@Override
	public QueryBaseBatchResult<IndustryInfo> queryIndustry(IndustryQueryOrder order) {
		
		QueryBaseBatchResult<IndustryInfo> baseBatchResult = new QueryBaseBatchResult<IndustryInfo>();
		
		IndustryDO queryCondition = new IndustryDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		long totalSize = industryDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<IndustryDO> pageList = industryDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
			order.getSortOrder());
		
		List<IndustryInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (IndustryDO industry : pageList) {
				IndustryInfo info = new IndustryInfo();
				BeanCopier.staticCopy(industry, info);
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<RegionInfo> queryRegion(RegionQueryOrder order) {
		
		QueryBaseBatchResult<RegionInfo> baseBatchResult = new QueryBaseBatchResult<RegionInfo>();
		
		RegionDO queryCondition = new RegionDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("sort_order");
			order.setSortOrder("asc");
		}
		
		long totalSize = regionDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<RegionDO> pageList = regionDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
			order.getSortOrder());
		
		List<RegionInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (RegionDO region : pageList) {
				RegionInfo info = new RegionInfo();
				BeanCopier.staticCopy(region, info);
				info.setHasChildren(BooleanEnum.getByCode(region.getHasChildren()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public FcsBaseResult saveRegion(final RegionOrder order) {
		return commonProcess(order, "保存行政区域", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				RegionDO region = null;
				if (order.getId() > 0) {
					region = regionDAO.findById(order.getId());
					if (region == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到行政区域");
					}
					//修改
					if (!StringUtil.equals(region.getCode(), order.getCode())) {
						RegionDO exist = regionDAO.findByCode(order.getCode());
						if (exist != null) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DO_ACTION_STATUS_ERROR, "编码已存在");
						}
					}
				} else {
					RegionDO exist = regionDAO.findByCode(order.getCode());
					if (exist != null) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "编码已存在");
					}
					//新增
					region = new RegionDO();
				}
				
				BeanCopier.staticCopy(order, region);
				//设置level等属性
				if ("root".equals(order.getParentCode())) {
					region.setLevel(1);
				} else {
					RegionDO parent = regionDAO.findByCode(order.getParentCode());
					if (parent == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "上级未找到");
					}
					region.setLevel(parent.getLevel() + 1);
				}
				if (region.getId() > 0) {
					regionDAO.update(region);
				} else {
					regionDAO.insert(region);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult delRegion(int id) {
		FcsBaseResult result = createResult();
		try {
			logger.info("删除行政区域：id {}", id);
			regionDAO.deleteById(id);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("删除行政区域出错");
		}
		
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<BusiTypeInfo> queryBusiType(BusiTypeQueryOrder order) {
		
		QueryBaseBatchResult<BusiTypeInfo> baseBatchResult = new QueryBaseBatchResult<BusiTypeInfo>();
		
		BusiTypeDO queryCondition = new BusiTypeDO();
		
		if (order != null) {
			BeanCopier.staticCopy(order, queryCondition);
			queryCondition.setId(order.getId());
			queryCondition.setParentId(order.getParentId());
			if (order.getIsDel() != null)
				queryCondition.setIsDel(order.getIsDel().code());
		}
		
		long totalSize = busiTypeDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<BusiTypeDO> pageList = busiTypeDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
			order.getSortOrder());
		
		List<BusiTypeInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (BusiTypeDO busiType : pageList) {
				BusiTypeInfo info = new BusiTypeInfo();
				BeanCopier.staticCopy(busiType, info);
				info.setCustomerType(CustomerTypeEnum.getByCode(busiType.getCustomerType()));
				info.setHasChildren(BooleanEnum.getByCode(busiType.getHasChildren()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public BusiTypeInfo querySingleBusiType(String code, String customerType) {
		
		BusiTypeDO busiType = new BusiTypeDO();
		busiType.setCode(code);
		busiType.setCustomerType(customerType);
		List<BusiTypeDO> bt = busiTypeDAO.findByCondition(busiType, 0, 1, null, null);
		if (ListUtil.isEmpty(bt)) {
			return null;
		}
		
		busiType = bt.get(0);
		
		BusiTypeInfo info = new BusiTypeInfo();
		BeanCopier.staticCopy(busiType, info);
		info.setCustomerType(CustomerTypeEnum.getByCode(busiType.getCustomerType()));
		
		return info;
	}
	
	@Override
	public BusiTypeInfo querySingleBusiTypeByName(String name, String customerType) {
		BusiTypeDO busiType = new BusiTypeDO();
		busiType.setName(name);
		busiType.setCustomerType(customerType);
		List<BusiTypeDO> bt = busiTypeDAO.findByCondition(busiType, 0, 1, null, null);
		if (ListUtil.isEmpty(bt)) {
			return null;
		}
		busiType = bt.get(0);
		BusiTypeInfo info = new BusiTypeInfo();
		BeanCopier.staticCopy(busiType, info);
		info.setCustomerType(CustomerTypeEnum.getByCode(busiType.getCustomerType()));
		return info;
	}
	
	@Override
	public List<EnterpriseScaleRuleInfo> queryEnterpriseScaleRule() {
		
		List<EnterpriseScaleRuleDO> data = enterpriseScaleRuleDAO.findByAll();
		List<EnterpriseScaleRuleInfo> result = Lists.newArrayList();
		if (data != null) {
			for (EnterpriseScaleRuleDO DO : data) {
				EnterpriseScaleRuleInfo info = new EnterpriseScaleRuleInfo();
				BeanCopier.staticCopy(DO, info);
				info.setKpiType(ScaleRuleKpiTypeEnum.getByCode(DO.getKpiType()));
				result.add(info);
			}
		}
		
		return result;
	}
	
	@Override
	public SysConfigInfo findSysConf() {
		SysConfigDO conf = sysConfigDAO.findOne();
		if (conf != null) {
			SysConfigInfo info = new SysConfigInfo();
			BeanCopier.staticCopy(conf, info);
			return info;
		}
		return null;
	}
	
	@Override
	public FcsBaseResult saveSysConf(final SysConfigOrder order) {
		return commonProcess(order, "保存系统配置", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				Date now = FcsPmDomainHolder.get().getSysDate();
				SysConfigDO conf = sysConfigDAO.findOne();
				if (conf == null)
					conf = new SysConfigDO();
				BeanCopier.staticCopy(order, conf);
				if (conf.getConfigId() == 0) {
					conf.setRawAddTime(now);
					sysConfigDAO.insert(conf);
				} else {
					sysConfigDAO.update(conf);
				}
				return null;
			}
		}, null, null);
	}
}
