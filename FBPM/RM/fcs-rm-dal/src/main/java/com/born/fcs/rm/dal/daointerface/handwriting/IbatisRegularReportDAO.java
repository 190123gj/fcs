package com.born.fcs.rm.dal.daointerface.handwriting;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.fcs.rm.dal.dataobject.RegularProjectChannelInfoDO;
import com.born.fcs.rm.dal.dataobject.RegularProjectImpelInfoDO;
import com.born.fcs.rm.dal.dataobject.RegularProjectStoreInfoDO;
import com.born.fcs.rm.dal.dataobject.RegularProjectSubBusiTypeInfoDO;
import com.born.fcs.rm.dal.dataobject.handwriting.RegularProjectMultiCapitalChannelInfoDO;
import com.born.fcs.rm.dal.dataobject.handwriting.RegularProjectSubBusiTypeDO;

@SuppressWarnings("deprecation")
public class IbatisRegularReportDAO extends SqlMapClientDaoSupport implements RegularReportDAO {
	
	@Override
	public long insertRegularProjectBase(Map<String, Object> param) throws DataAccessException {
		return (long) getSqlMapClientTemplate()
			.update("RR-REGULAR-PROJECT-BASE-INFO-INSERT", param);
	}
	
	@Override
	public long insertRegularProjectBaseMonth(Map<String, Object> param) throws DataAccessException {
		return (long) getSqlMapClientTemplate().update("RR-REGULAR-PROJECT-BASE-INFO-MONTH-INSERT",
			param);
	}
	
	@Override
	public long insertRegularCustomerBase(Map<String, Object> param) throws DataAccessException {
		return (long) getSqlMapClientTemplate().update("RR-REGULAR-CUSTOMER-BASE-INFO-INSERT",
			param);
	}
	
	@Override
	public long insertRegularCustomerBaseMonth(Map<String, Object> param)
																			throws DataAccessException {
		return (long) getSqlMapClientTemplate().update(
			"RR-REGULAR-CUSTOMER-BASE-INFO-MONTH-INSERT", param);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<RegularProjectImpelInfoDO> selectRegularProjectImpel(Map<String, Object> param)
																								throws DataAccessException {
		return getSqlMapClientTemplate()
			.queryForList("RR-REGULAR-PROJECT-IMPEL-INFO-SELECT", param);
	}
	
	@Override
	public long countRegularProjectImpel(Map<String, Object> param) throws DataAccessException {
		return (long) getSqlMapClientTemplate().queryForObject(
			"RR-REGULAR-PROJECT-IMPEL-INFO-COUNT", param);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<RegularProjectStoreInfoDO> selectRegularProjectStore(Map<String, Object> param)
																								throws DataAccessException {
		return getSqlMapClientTemplate()
			.queryForList("RR-REGULAR-PROJECT-STORE-INFO-SELECT", param);
	}
	
	@Override
	public long countRegularProjectStore(Map<String, Object> param) throws DataAccessException {
		return (long) getSqlMapClientTemplate().queryForObject(
			"RR-REGULAR-PROJECT-STORE-INFO-COUNT", param);
	}
	
	@Override
	public long insertRegularProjectStoreMonth(Map<String, Object> param)
																			throws DataAccessException {
		return (long) getSqlMapClientTemplate().update(
			"RR-REGULAR-PROJECT-STORE-INFO-MONTH-INSERT", param);
	}
	
	@Override
	public long insertRegularProjectImpelMonth(Map<String, Object> param)
																			throws DataAccessException {
		return (long) getSqlMapClientTemplate().update(
			"RR-REGULAR-PROJECT-IMPEL-INFO-MONTH-INSERT", param);
	}
	
	@Override
	public long insertRegularProjectRun(Map<String, Object> param) throws DataAccessException {
		return (long) getSqlMapClientTemplate().update("RR-REGULAR-PROJECT-RUN-INFO-INSERT", param);
	}
	
	@Override
	public long insertRegularProjectRunMonth(Map<String, Object> param) throws DataAccessException {
		return (long) getSqlMapClientTemplate().update("RR-REGULAR-PROJECT-RUN-INFO-MONTH-INSERT",
			param);
	}
	
	@Override
	public long insertRegularProjectIncome(Map<String, Object> param) throws DataAccessException {
		return (long) getSqlMapClientTemplate().update("RR-REGULAR-PROJECT-INCOME-INFO-INSERT",
			param);
	}
	
	@Override
	public long insertRegularProjectIncomeMonth(Map<String, Object> param)
																			throws DataAccessException {
		return (long) getSqlMapClientTemplate().update(
			"RR-REGULAR-PROJECT-INCOME-INFO-MONTH-INSERT", param);
	}
	
	@Override
	public long insertRegularProjectRisk(Map<String, Object> param) throws DataAccessException {
		return (long) getSqlMapClientTemplate()
			.update("RR-REGULAR-PROJECT-RISK-INFO-INSERT", param);
	}
	
	@Override
	public long insertRegularProjectRiskMonth(Map<String, Object> param) throws DataAccessException {
		return (long) getSqlMapClientTemplate().update("RR-REGULAR-PROJECT-RISK-INFO-MONTH-INSERT",
			param);
	}
	
	@Override
	public long insertRegularProjectExtraList(Map<String, Object> param) throws DataAccessException {
		return (long) getSqlMapClientTemplate().update("RR-REGULAR-PROJECT-EXTRA-LIST-INSERT",
			param);
	}
	
	@Override
	public long insertRegularProjectExtraListMonth(Map<String, Object> param)
																				throws DataAccessException {
		return (long) getSqlMapClientTemplate().update(
			"RR-REGULAR-PROJECT-EXTRA-LIST-MONTH-INSERT", param);
	}
	
	@Override
	public long insertRegularProjectChannel(Map<String, Object> param) throws DataAccessException {
		return (long) getSqlMapClientTemplate().update("RR-REGULAR-PROJECT-CHANNEL-INSERT", param);
	}
	
	@Override
	public long insertRegularProjectChannelMonth(Map<String, Object> param)
																			throws DataAccessException {
		return (long) getSqlMapClientTemplate().update("RR-REGULAR-PROJECT-CHANNEL-MONTH-INSERT",
			param);
	}
	
	@Override
	public long countRegularProjectMultiCapitalChannel(Map<String, Object> param)
																					throws DataAccessException {
		return (long) getSqlMapClientTemplate().queryForObject(
			"RR-REGULAR-PROJECT-MULTI-CAPITAL-CHANNEL-COUNT", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegularProjectMultiCapitalChannelInfoDO> selectRegularProjectMultiCapitalChannel(	Map<String, Object> param)
																																throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(
			"RR-REGULAR-PROJECT-MULTI-CAPITAL-CHANNEL-SEARCH", param);
	}
	
	@Override
	public long countRegularCustomerMultiCapitalChannelOccur(Map<String, Object> param)
																						throws DataAccessException {
		return (long) getSqlMapClientTemplate().queryForObject(
			"RR-REGULAR-CUSTOMER-MULTI-CAPITAL-CHANNEL-OCCUR-COUNT", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegularProjectMultiCapitalChannelInfoDO> selectRegularCustomerChannelMultiCapitalChannelOccur(	Map<String, Object> param)
																																			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(
			"RR-REGULAR-CUSTOMER-MULTI-CAPITAL-CHANNEL-OCCUR-SEARCH", param);
	}
	
	@Override
	public long updateRegularCustomerMultiCapitalChannelCountRate(RegularProjectChannelInfoDO param)
																									throws DataAccessException {
		return (long) getSqlMapClientTemplate().update(
			"RR-REGULAR-CUSTOMER-MULTI-CAPITAL-CHANNEL-COUNT-RATE-UPDATE", param);
	}
	
	@Override
	public long countRegularCustomerSubBusiTypeOccur(Map<String, Object> param)
																				throws DataAccessException {
		return (long) getSqlMapClientTemplate().queryForObject(
			"RR-REGULAR-CUSTOMER-SUB-BUSI-TYPE-OCCUR-COUNT", param);
	}
	
	@Override
	public long countRegularProjectSubBusiTypeInfo(Map<String, Object> param)
																				throws DataAccessException {
		return (long) getSqlMapClientTemplate().queryForObject(
			"RR-REGULAR-PROJECT-SUB-BUSI-TYPE-COUNT", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegularProjectSubBusiTypeDO> selectRegularCustomerSubBusiTypeOccur(	Map<String, Object> param)
																												throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(
			"RR-REGULAR-CUSTOMER-SUB-BUSI-TYPE-OCCUR-SEARCH", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegularProjectSubBusiTypeDO> selectRegularProjectSubBusiTypeInfo(	Map<String, Object> param)
																												throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("RR-REGULAR-PROJECT-SUB-BUSI-TYPE-SEARCH",
			param);
	}
	
	@Override
	public long updateRegularCustomerSubBusiTypeOccur(RegularProjectSubBusiTypeInfoDO param)
																							throws DataAccessException {
		return (long) getSqlMapClientTemplate().update(
			"RR-REGULAR-CUSTOMER-SUB-BUSI-TYPE-OCCUR-UPDATE", param);
	}
	
	@Override
	public long insertRegularProjectSubBusiTypeMonth(Map<String, Object> param)
																				throws DataAccessException {
		return (long) getSqlMapClientTemplate().update(
			"RR-REGULAR-PROJECT-SUB-BUSI-TYPE-MONTH-INSERT", param);
	}
}
