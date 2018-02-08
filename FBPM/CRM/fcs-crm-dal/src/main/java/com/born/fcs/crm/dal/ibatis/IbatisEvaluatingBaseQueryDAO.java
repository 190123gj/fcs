package com.born.fcs.crm.dal.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.fcs.crm.dal.daointerface.EvaluatingBaseQueryDAO;
import com.born.fcs.crm.dal.dataobject.EvaluatingBaseQueryDO;

public class IbatisEvaluatingBaseQueryDAO extends SqlMapClientDaoSupport implements
																		EvaluatingBaseQueryDAO {
	
	@Override
	public List<EvaluatingBaseQueryDO> queryLeve4Concat(Map<String, Object> map)
																				throws DataAccessException {
		if (map == null) {
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		
		return getSqlMapClientTemplate().queryForList("level4_concat", map);
	}
	
	@Override
	public List<EvaluatingBaseQueryDO> queryLeve3Concat(Map<String, Object> map)
																				throws DataAccessException {
		if (map == null) {
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		
		return getSqlMapClientTemplate().queryForList("level3_concat", map);
	}
	
	@Override
	public List<EvaluatingBaseQueryDO> queryCityBz(Map<String, Object> map)
																			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("city_bz", map);
		
	}
	
	@Override
	public List<EvaluatingBaseQueryDO> queryCityCw(Map<String, Object> map)
																			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("city_cw", map);
		
	}
	
	@Override
	public List<EvaluatingBaseQueryDO> queryGyBz(Map<String, Object> map)
																			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("gy_bz", map);
	}
	
}
