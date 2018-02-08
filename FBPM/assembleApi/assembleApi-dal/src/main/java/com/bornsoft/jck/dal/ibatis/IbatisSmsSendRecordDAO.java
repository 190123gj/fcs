package com.bornsoft.jck.dal.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bornsoft.jck.dal.daointerface.SmsSendRecordDAO;
import com.bornsoft.jck.dal.dataobject.SmsSendRecordDO;

public class IbatisSmsSendRecordDAO extends IbatisSmsSendRecordDAOAbstract implements SmsSendRecordDAO{

	@SuppressWarnings("deprecation")
	@Override
	public List<SmsSendRecordDO> smsSendRecordQuery(String mobile, String startTime,
			String endTime,int pageSize, int pageNum) {
		Map<String,Object> param = new HashMap<>();
		param.put("mobile", mobile);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("pageSize", pageSize);
		param.put("recordIndex", pageSize*(pageNum-1));
		return super.getSqlMapClientTemplate().queryForList("MS-SMS-SEND-RECORD-QUERY-PAGE", param);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int smsSendRecordCount(String mobile, String startTime,
			String endTime) {
		Map<String,Object> param = new HashMap<>();
		param.put("mobile", mobile);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		return (Integer) super.getSqlMapClientTemplate().queryForObject("MS-SMS-SEND-RECORD-QUERY-COUNT", param);
	}
}