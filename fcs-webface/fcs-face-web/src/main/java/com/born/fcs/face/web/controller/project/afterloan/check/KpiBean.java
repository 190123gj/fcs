package com.born.fcs.face.web.controller.project.afterloan.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KpiBean {
	Map<String, Map<String, String>> map=null;
	Map<String, List<String>> colSortList=new HashMap<String, List<String>>();
	public Map<String, Map<String, String>> getMap() {
		return map;
	}
	public void setMap(Map<String, Map<String, String>> map) {
		this.map = map;
	}
	public Map<String, List<String>> getColSortList() {
		return colSortList;
	}
	public void setColSortList(Map<String, List<String>> colSortList) {
		this.colSortList = colSortList;
	}
	
	
}
