package com.born.fcs.rm.dal.daointerface;

import java.util.List;
import java.util.Map;

public interface ReportSqlDao {
	
	/**
	 * 查询生成Excel所需的数据对象List Map（String,String）
	 * @param sql
	 * @return
	 */
	List<Map<String, String>> query(String sql);
	
	int call(String sql);
	
	/**
	 * 查询总条数
	 * @param sql
	 * @return
	 */
	long queryCount(String sql);
	
}
