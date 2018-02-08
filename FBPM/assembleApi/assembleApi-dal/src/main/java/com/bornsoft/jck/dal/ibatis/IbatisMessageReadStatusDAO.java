package com.bornsoft.jck.dal.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bornsoft.jck.dal.daointerface.MessageReadStatusDAO;
import com.bornsoft.jck.dal.dataobject.MessageReadStatusDO;
import com.yjf.common.lang.util.StringUtil;

public class IbatisMessageReadStatusDAO extends IbatisMessageReadStatusDAOAbstract implements MessageReadStatusDAO{

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<MessageReadStatusDO> queryByUserName(String userName) {
		if(StringUtil.isBlank(userName)){
			throw new RuntimeException("查询条件不能为空");
		}
		Map<String,String> params = new HashMap<String, String>();
		params.put("userName", userName);
		return super.getSqlMapClientTemplate().queryForList("MS-MESSAGE-READ-STATUS-SELECT-BY-USER", params);
	}


}