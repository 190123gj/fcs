package com.born.fcs.face.integration.crm.service.busyRegion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.BusyRegionService;
import com.born.fcs.crm.ws.service.info.BusyRegionInfo;
import com.born.fcs.crm.ws.service.order.BusyRegionOrder;
import com.born.fcs.crm.ws.service.order.query.BusyRegionQueryOrder;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;

@Service("busyRegionClient")
public class BusyRegionClient extends ClientAutowiredBaseService implements BusyRegionMap,
																BusyRegionService {
	@Autowired
	private BusyRegionService busyRegionService;
	//部门区域
	private static Map<String, Map<String, String>> depRegion = new HashMap<String, Map<String, String>>();
	//启用状态
	private static Boolean used = null;
	
	@Override
	public FcsBaseResult save(final BusyRegionOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				queryStatus(null);
				if (used != null) {
					order.setStatus(used ? "IS" : "NO");
				} else {
					order.setStatus("NO");
				}
				//				FcsBaseResult result = busyRegionService.save(order);
				//				if (result.isSuccess()) {
				//					Map<String, String> region = new HashMap<String, String>();
				//					if (ListUtil.isNotEmpty(order.getRegion())) {
				//						for (region regin : order.getRegion()) {
				//							region.put(regin.getCode(), regin.getName());
				//						}
				//					}				
				//					depRegion.put(order.getDepPath(), region);
				//				}
				return busyRegionService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<BusyRegionInfo> list(final BusyRegionQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<BusyRegionInfo>>() {
			
			@Override
			public QueryBaseBatchResult<BusyRegionInfo> call() {
				return busyRegionService.list(order);
			}
		});
	}
	
	@Override
	public Map<String, String> busyRegMap(String depPath, boolean coperAll) {
		//		if (depRegion.containsKey(depPath)) {
		//			return depRegion.get(depPath);
		//		} else {
		List<BusyRegionInfo> result = null;
		if (coperAll) {
			//全匹配
			result = queryAllByDepPath(depPath);
		} else {
			//模糊查找
			result = queryByDepPath(depPath);
		}
		Map<String, String> map = new HashMap<>();
		if (ListUtil.isNotEmpty(result)) {
			for (BusyRegionInfo info : result) {
				map.put(info.getCode(), info.getName());
				if ("China".equals(info.getCode())) {
					map = new HashMap<>();
					map.put(info.getCode(), info.getName());
					break;
				}
			}
			
		}
		//		depRegion.put(depPath, map);
		return map;
		
		//		}
	}
	
	@Override
	public FcsBaseResult changeStatus(final String depPath, final BooleanEnum status) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				FcsBaseResult result = busyRegionService.changeStatus(depPath, status);
				
				//				if (result != null && result.isSuccess()) {
				//					used = (status == BooleanEnum.IS ? true : false);
				//				}
				return result;
			}
		});
	}
	
	@Override
	public FcsBaseResult queryStatus(final String depPath) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				FcsBaseResult result = busyRegionService.queryStatus(depPath);
				if (result != null) {
					used = result.isSuccess();
				}
				return result;
			}
		});
	}
	
	@Override
	public List<BusyRegionInfo> queryByDepPath(final String depPath) {
		return callInterface(new CallExternalInterface<List<BusyRegionInfo>>() {
			
			@Override
			public List<BusyRegionInfo> call() {
				
				return busyRegionService.queryByDepPath(depPath);
			}
		});
	}
	
	@Override
	public List<BusyRegionInfo> queryAllByDepPath(final String depPath) {
		return callInterface(new CallExternalInterface<List<BusyRegionInfo>>() {
			
			@Override
			public List<BusyRegionInfo> call() {
				
				return busyRegionService.queryAllByDepPath(depPath);
			}
		});
	}
	
}
