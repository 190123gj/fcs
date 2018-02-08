package com.born.fcs.pm.daointerface;

import java.util.List;
import java.util.Map;

import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.result.DataListItem;

public interface ReportDao {
	
	/**
	 * 查询生成Excel所需的数据对象List Map（String,String）
	 * @param sql
	 * @return
	 */
	List<DataListItem> query(String sql, Map<String, FcsField> fieldMap);
	
	int call(String sql);
	
	/**
	 * 查询总条数
	 * @param sql
	 * @return
	 */
	long queryCount(String sql);
	
}
