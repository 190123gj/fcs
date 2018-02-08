package com.bornsoft.jck.dal.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bornsoft.jck.dal.daointerface.ApixVeryLogDAO;
import com.bornsoft.jck.dal.dataobject.ApixVeryLogDO;
import com.bornsoft.jck.dal.dataobject.ApixVeryLogReport;

public class IbatisApixVeryLogDAO extends IbatisApixVeryLogDAOAbstract implements ApixVeryLogDAO{

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<ApixVeryLogDO> queryApiLogForPage(String serviceName,String startTime,String endTime,int pageNum,int pageSize) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("serviceName", serviceName);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("pageSize", pageSize);
		param.put("recordIndex", pageSize*(pageNum-1));
		return super.getSqlMapClientTemplate().queryForList("MS-APIX-VERY-LOG-SELECT-QUERY-FOR-PAGE", param);
	}
	@SuppressWarnings("deprecation")
	@Override
	public int countApiLogForPage(String serviceName, String startTime,
			String endTime) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("serviceName", serviceName);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		return (Integer) super.getSqlMapClientTemplate().queryForObject("MS-APIX-VERY-LOG-SELECT-COUNT-FOR-PAGE", param);
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<ApixVeryLogReport> queryApiLogReport(String serviceName,
			String startTime, String endTime) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("serviceName", serviceName);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		return super.getSqlMapClientTemplate().queryForList("MS-APIX-VERY-LOG-REPORT-QUERY", param);
	}
	

}