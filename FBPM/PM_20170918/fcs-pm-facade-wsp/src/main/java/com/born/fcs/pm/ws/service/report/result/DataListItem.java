package com.born.fcs.pm.ws.service.report.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

public class DataListItem implements Serializable {
	
	private static final long serialVersionUID = -7495297734153925447L;
	
	HashMap<String, Object> map = new HashMap<String, Object>();
	List<ReportItem> valueList = Lists.newArrayList();
	
	public HashMap<String, Object> getMap() {
		return this.map;
	}
	
	public List<ReportItem> getValueList() {
		return this.valueList;
	}
	
	public void setValueList(List<ReportItem> valueList) {
		this.valueList = valueList;
		if (valueList != null) {
			for (ReportItem item : valueList) {
				map.put(item.key, item.getObject());
			}
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataListItem [map=");
		builder.append(map);
		builder.append(", valueList=");
		builder.append(valueList);
		builder.append("]");
		return builder.toString();
	}
	
}
