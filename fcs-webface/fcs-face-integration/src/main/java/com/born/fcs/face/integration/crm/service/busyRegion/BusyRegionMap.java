package com.born.fcs.face.integration.crm.service.busyRegion;

import java.util.Map;

public interface BusyRegionMap {
	/**
	 * 查询部门业务区域
	 * @param depPath 部门全路径
	 * @param coperAll true 全匹配 ;false 模糊匹配
	 * 
	 * */
	Map<String, String> busyRegMap(String depPath, boolean coperAll);
}
