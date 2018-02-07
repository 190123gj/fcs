package com.born.fcs.face.integration.pm.service.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
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

@Service("basicDataServiceClient")
public class BasicDataServiceClient extends ClientAutowiredBaseService implements BasicDataService {
	
	@Override
	public QueryBaseBatchResult<IndustryInfo> queryIndustry(final IndustryQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<IndustryInfo>>() {
			
			@Override
			public QueryBaseBatchResult<IndustryInfo> call() {
				return basicDataWebService.queryIndustry(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<RegionInfo> queryRegion(final RegionQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<RegionInfo>>() {
			
			@Override
			public QueryBaseBatchResult<RegionInfo> call() {
				return basicDataWebService.queryRegion(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveRegion(final RegionOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return basicDataWebService.saveRegion(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult delRegion(final int id) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return basicDataWebService.delRegion(id);
			}
		});
	}
	
	@Override
	public List<EnterpriseScaleRuleInfo> queryEnterpriseScaleRule() {
		return callInterface(new CallExternalInterface<List<EnterpriseScaleRuleInfo>>() {
			
			@Override
			public List<EnterpriseScaleRuleInfo> call() {
				return basicDataWebService.queryEnterpriseScaleRule();
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<BusiTypeInfo> queryBusiType(final BusiTypeQueryOrder order) {
		
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<BusiTypeInfo>>() {
			
			@Override
			public QueryBaseBatchResult<BusiTypeInfo> call() {
				return basicDataWebService.queryBusiType(order);
			}
		});
	}
	
	@Override
	public BusiTypeInfo querySingleBusiType(final String code, final String customerType) {
		return callInterface(new CallExternalInterface<BusiTypeInfo>() {
			
			@Override
			public BusiTypeInfo call() {
				return basicDataWebService.querySingleBusiType(code, customerType);
			}
		});
	}
	
	//缓存
	private static SysConfigInfo sysConf = null;
	
	@Override
	public SysConfigInfo findSysConf() {
		if (sysConf == null) {
			sysConf = callInterface(new CallExternalInterface<SysConfigInfo>() {
				
				@Override
				public SysConfigInfo call() {
					return basicDataWebService.findSysConf();
				}
			});
		}
		return sysConf;
	}
	
	@Override
	public FcsBaseResult saveSysConf(final SysConfigOrder order) {
		sysConf = null; //清空
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return basicDataWebService.saveSysConf(order);
			}
		});
	}
	
	@Override
	public BusiTypeInfo querySingleBusiTypeByName(final String name, final String customerType) {
		return callInterface(new CallExternalInterface<BusiTypeInfo>() {
			
			@Override
			public BusiTypeInfo call() {
				return basicDataWebService.querySingleBusiTypeByName(name, customerType);
			}
		});
	}
}
