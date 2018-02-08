package com.born.fcs.pm.dal.common;

import java.util.HashMap;
import java.util.Map;

import com.born.fcs.pm.dal.dataobject.ProjectDO;

public class ProjectDataModifyCache {
	private static Map<String, Long> projectDataModifyMap = new HashMap<String, Long>();
	
	public synchronized static void addModify(String projectCode, ProjectDO projectDO) {
		projectDataModifyMap.put(projectCode, new Long(0));
	}
	
	public synchronized static void addModifyTime(String projectCode) {
		Long value = projectDataModifyMap.get(projectCode);
		if (value != null) {
			value = new Long(value.longValue() + 1);
			projectDataModifyMap.put(projectCode, value);
		}
		
	}
	
	public synchronized static void commitModify(String projectCode) {
		projectDataModifyMap.remove(projectCode);
	}
	
	public synchronized static Map<String, Long> getModifyMap() {
		Map<String, Long> tempProjectDataModifyMap = new HashMap<String, Long>();
		tempProjectDataModifyMap.putAll(projectDataModifyMap);
		return tempProjectDataModifyMap;
	}
}
