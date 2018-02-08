package com.bornsoft.jck.dal.ibatis;

import java.util.List;

import com.bornsoft.jck.dal.daointerface.CardInfoDAO;
import com.bornsoft.jck.dal.dataobject.CardInfoDO;

public class IbatisCardInfoDAO extends IbatisCardInfoDAOAbstract implements CardInfoDAO{

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<CardInfoDO> queryCardInfo(CardInfoDO cardInfo) {
		if(cardInfo==null){
			throw new RuntimeException("查询条件不能为空");
		}
		return super.getSqlMapClientTemplate().queryForList("MS-CARD-INFO-SELECT-DYNAMIC", cardInfo);
	}


}