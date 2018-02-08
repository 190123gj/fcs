package com.bornsoft.jck.dal.ibatis;

import java.util.HashMap;
import java.util.Map;

import com.bornsoft.jck.dal.daointerface.AppLoginInfoDAO;
import com.bornsoft.jck.dal.dataobject.AppLoginInfoDO;

public class IbatisAppLoginInfoDAO extends IbatisAppLoginInfoDAOAbstract implements AppLoginInfoDAO{

	@SuppressWarnings("deprecation")
	@Override
	public AppLoginInfoDO queryLoginInfo(String userName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", userName);
		return (AppLoginInfoDO) super.getSqlMapClientTemplate().queryForObject("MS-APP-LOGIN-INFO-QUERY-BY-USERNAME", paramMap);
	}


}