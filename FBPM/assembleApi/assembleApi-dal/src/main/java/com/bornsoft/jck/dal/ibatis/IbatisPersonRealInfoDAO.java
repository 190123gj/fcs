package com.bornsoft.jck.dal.ibatis;

import java.util.List;

import com.bornsoft.jck.dal.daointerface.PersonRealInfoDAO;
import com.bornsoft.jck.dal.dataobject.PersonRealInfoDO;

public class IbatisPersonRealInfoDAO extends IbatisPersonRealInfoDAOAbstract implements PersonRealInfoDAO{

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<PersonRealInfoDO> queryRealInfo(PersonRealInfoDO realInfo) {
		if(realInfo==null){
			throw new RuntimeException("查询条件不能为空");
		}
		return super.getSqlMapClientTemplate().queryForList("MS-PERSON-REAL-INFO-SELECT-DYNAMIC", realInfo);
	}

}