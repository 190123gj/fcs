package com.bornsoft.jck.dal.ibatis;

import java.util.List;

import com.bornsoft.jck.dal.daointerface.MobileInfoDAO;
import com.bornsoft.jck.dal.dataobject.MobileInfoDO;

public class IbatisMobileInfoDAO extends IbatisMobileInfoDAOAbstract implements MobileInfoDAO{

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<MobileInfoDO> queryMobileInfoList(MobileInfoDO realInfo) {
		if(realInfo==null){
			throw new RuntimeException("查询条件不能为空");
		}
		return super.getSqlMapClientTemplate().queryForList("MS-MOBILE-INFO-SELECT-DYNAMIC", realInfo);
	}


}