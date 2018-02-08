package com.born.fcs.crm.dal.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.born.fcs.crm.dal.dataobject.EvaluatingBaseQueryDO;

public interface EvaluatingBaseQueryDAO {
	
	/**
	 * 合并level4
	 * @param type 指标类型
	 * @Param level 等级
	 * @param status
	 * */
	public List<EvaluatingBaseQueryDO> queryLeve4Concat(Map<String, Object> map)
																				throws DataAccessException;
	
	/**
	 * 合并level3
	 * @param type 指标类型
	 * @Param level 等级
	 * @param status
	 * */
	public List<EvaluatingBaseQueryDO> queryLeve3Concat(Map<String, Object> map)
																				throws DataAccessException;
	
	/**
	 * 城市开发类标准值部分
	 * @param type 指标类型
	 * @Param level 等级
	 * @param status
	 */
	public List<EvaluatingBaseQueryDO> queryCityBz(Map<String, Object> map)
																			throws DataAccessException;
	
	/**
	 * 城市开发类财务部分
	 * @param type 指标类型
	 * @Param level 等级
	 * @param status
	 */
	public List<EvaluatingBaseQueryDO> queryCityCw(Map<String, Object> map)
																			throws DataAccessException;
	
	/**
	 * 公用事业标准部分
	 * @param type 指标类型
	 * @Param level 等级
	 * @param status
	 */
	public List<EvaluatingBaseQueryDO> queryGyBz(Map<String, Object> map)
																			throws DataAccessException;
}
