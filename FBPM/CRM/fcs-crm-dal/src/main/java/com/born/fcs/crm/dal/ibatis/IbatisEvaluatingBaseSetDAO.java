/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.crm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.crm.dal.daointerface.EvaluatingBaseSetDAO;


// auto generated imports
import com.born.fcs.crm.dal.dataobject.EvaluatingBaseSetDO;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;
	import java.util.HashMap;
	
/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.crm.dal.daointerface.EvaluatingBaseSetDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/evaluating_base_set.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisEvaluatingBaseSetDAO extends SqlMapClientDaoSupport implements EvaluatingBaseSetDAO {
	/**
	 *  Insert one <tt>EvaluatingBaseSetDO</tt> object to DB table <tt>evaluating_base_set</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into evaluating_base_set(id,level1_id,level1_name,level1_description,level1_score,level2_id,level2_name,level2_description,level2_score,level3_id,level3_name,level3_description,level3_score,level4_id,level4_name,level4_description,level4_score,evaluating_content,evaluating_result,score,standard_value,compare_method,compare_method2,compare_method3,compare_standard_value,evaluating_standard_value,compare_evaluating_standard_value,calculating_formula,level,perent_level,type,status,order_num1,order_num2,order_num3,order_num4,perent_id,child_id,is_temporary,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param evaluatingBaseSet
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(EvaluatingBaseSetDO evaluatingBaseSet) throws DataAccessException {
    	if (evaluatingBaseSet == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-EVALUATING-BASE-SET-INSERT", evaluatingBaseSet);

        return evaluatingBaseSet.getId();
    }

	/**
	 *  Query DB table <tt>evaluating_base_set</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, level1_id, level1_name, level1_description, level1_score, level2_id, level2_name, level2_description, level2_score, level3_id, level3_name, level3_description, level3_score, level4_id, level4_name, level4_description, level4_score, evaluating_content, evaluating_result, score, standard_value, compare_method, compare_method2, compare_method3, compare_standard_value, evaluating_standard_value, compare_evaluating_standard_value, calculating_formula, level, perent_level, type, status, order_num1, order_num2, order_num3, order_num4, perent_id, child_id, is_temporary, raw_add_time, raw_update_time from evaluating_base_set where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return EvaluatingBaseSetDO
	 *	@throws DataAccessException
	 */	 
    public EvaluatingBaseSetDO findById(long id) throws DataAccessException {
        Long param = new Long(id);

        return (EvaluatingBaseSetDO) getSqlMapClientTemplate().queryForObject("MS-EVALUATING-BASE-SET-FIND-BY-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>evaluating_base_set</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from evaluating_base_set where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-EVALUATING-BASE-SET-DELETE-BY-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>evaluating_base_set</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from evaluating_base_set where (level1_id = ?)</tt>
	 *
	 *	@param level1Id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByLevel1Id(long level1Id) throws DataAccessException {
        Long param = new Long(level1Id);

        return getSqlMapClientTemplate().delete("MS-EVALUATING-BASE-SET-DELETE-BY-LEVEL-1-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>evaluating_base_set</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from evaluating_base_set where (level2_id = ?)</tt>
	 *
	 *	@param level2Id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByLevel2Id(long level2Id) throws DataAccessException {
        Long param = new Long(level2Id);

        return getSqlMapClientTemplate().delete("MS-EVALUATING-BASE-SET-DELETE-BY-LEVEL-2-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>evaluating_base_set</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from evaluating_base_set where (level3_id = ?)</tt>
	 *
	 *	@param level3Id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByLevel3Id(long level3Id) throws DataAccessException {
        Long param = new Long(level3Id);

        return getSqlMapClientTemplate().delete("MS-EVALUATING-BASE-SET-DELETE-BY-LEVEL-3-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>evaluating_base_set</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from evaluating_base_set where (level4_id = ?)</tt>
	 *
	 *	@param level4Id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByLevel4Id(long level4Id) throws DataAccessException {
        Long param = new Long(level4Id);

        return getSqlMapClientTemplate().delete("MS-EVALUATING-BASE-SET-DELETE-BY-LEVEL-4-ID", param);
    }

	/**
	 *  Update DB table <tt>evaluating_base_set</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update evaluating_base_set set level1_id=?, level1_name=?, level1_description=?, level1_score=?, level2_id=?, level2_name=?, level2_description=?, level2_score=?, level3_id=?, level3_name=?, level3_description=?, level3_score=?, level4_id=?, level4_name=?, level4_description=?, level4_score=?, evaluating_content=?, evaluating_result=?, score=?, standard_value=?, compare_method=?, compare_method2=?, compare_method3=?, compare_standard_value=?, evaluating_standard_value=?, compare_evaluating_standard_value=?, calculating_formula=?, level=?, perent_level=?, type=?, status=?, order_num1=?, order_num2=?, order_num3=?, order_num4=?, perent_id=?, child_id=?, is_temporary=? where (id = ?)</tt>
	 *
	 *	@param evaluatingBaseSet
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateById(EvaluatingBaseSetDO evaluatingBaseSet) throws DataAccessException {
    	if (evaluatingBaseSet == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-EVALUATING-BASE-SET-UPDATE-BY-ID", evaluatingBaseSet);
    }

	/**
	 *  Update DB table <tt>evaluating_base_set</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update evaluating_base_set set level1_name=?, level1_description=?, level1_score=?, order_num1=? where (level1_id = ?)</tt>
	 *
	 *	@param evaluatingBaseSet
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateByLevel1Id(EvaluatingBaseSetDO evaluatingBaseSet) throws DataAccessException {
    	if (evaluatingBaseSet == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-EVALUATING-BASE-SET-UPDATE-BY-LEVEL-1-ID", evaluatingBaseSet);
    }

	/**
	 *  Update DB table <tt>evaluating_base_set</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update evaluating_base_set set level2_name=?, level2_description=?, level2_score=?, standard_value=?, order_num2=? where (level2_id = ?)</tt>
	 *
	 *	@param evaluatingBaseSet
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateByLevel2IdForCtBz(EvaluatingBaseSetDO evaluatingBaseSet) throws DataAccessException {
    	if (evaluatingBaseSet == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-EVALUATING-BASE-SET-UPDATE-BY-LEVEL-2-ID-FOR-CT-BZ", evaluatingBaseSet);
    }

	/**
	 *  Update DB table <tt>evaluating_base_set</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update evaluating_base_set set level2_name=?, level2_description=?, level2_score=?, order_num2=? where (level2_id = ?)</tt>
	 *
	 *	@param evaluatingBaseSet
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateByLevel2Id(EvaluatingBaseSetDO evaluatingBaseSet) throws DataAccessException {
    	if (evaluatingBaseSet == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-EVALUATING-BASE-SET-UPDATE-BY-LEVEL-2-ID", evaluatingBaseSet);
    }

	/**
	 *  Update DB table <tt>evaluating_base_set</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update evaluating_base_set set level3_name=?, level3_description=?, level3_score=?, status=?, is_temporary=?, order_num3=? where (level3_id = ?)</tt>
	 *
	 *	@param evaluatingBaseSet
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateByLevel3Id(EvaluatingBaseSetDO evaluatingBaseSet) throws DataAccessException {
    	if (evaluatingBaseSet == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-EVALUATING-BASE-SET-UPDATE-BY-LEVEL-3-ID", evaluatingBaseSet);
    }

	/**
	 *  Query DB table <tt>evaluating_base_set</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, level1_id, level1_name, level1_description, level1_score, level2_id, level2_name, level2_description, level2_score, level3_id, level3_name, level3_description, level3_score, level4_id, level4_name, level4_description, level4_score, evaluating_content, evaluating_result, score, standard_value, compare_method, compare_method2, compare_method3, compare_standard_value, evaluating_standard_value, compare_evaluating_standard_value, calculating_formula, level, perent_level, type, status, order_num1, order_num2, order_num3, order_num4, perent_id, child_id, is_temporary, raw_add_time, raw_update_time from evaluating_base_set</tt>
	 *
	 *	@param evaluatingBaseSet
	 *	@param limitStart
	 *	@param pageSize
	 *	@param orderBy
	 *	@return List<EvaluatingBaseSetDO>
	 *	@throws DataAccessException
	 */	 
    public List<EvaluatingBaseSetDO> findWithCondition(EvaluatingBaseSetDO evaluatingBaseSet, long limitStart, long pageSize, String orderBy) throws DataAccessException {
    	if (evaluatingBaseSet == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("evaluatingBaseSet", evaluatingBaseSet);
        param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));
        param.put("orderBy", orderBy);

        return getSqlMapClientTemplate().queryForList("MS-EVALUATING-BASE-SET-FIND-WITH-CONDITION", param);

    }

	/**
	 *  Query DB table <tt>evaluating_base_set</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from evaluating_base_set</tt>
	 *
	 *	@param evaluatingBaseSet
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long countWithCondition(EvaluatingBaseSetDO evaluatingBaseSet) throws DataAccessException {
    	if (evaluatingBaseSet == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}


	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-EVALUATING-BASE-SET-COUNT-WITH-CONDITION", evaluatingBaseSet);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

	/**
	 *  Query DB table <tt>evaluating_base_set</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id from evaluating_base_set</tt>
	 *
	 *	@param evaluatingBaseSet
	 *	@return List<Long>
	 *	@throws DataAccessException
	 */	 
    public List<Long> findAllIds(EvaluatingBaseSetDO evaluatingBaseSet) throws DataAccessException {
    	if (evaluatingBaseSet == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}


        return getSqlMapClientTemplate().queryForList("MS-EVALUATING-BASE-SET-FIND-ALL-IDS", evaluatingBaseSet);

    }

	/**
	 *  Query DB table <tt>evaluating_base_set</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select sum(level1_score) from evaluating_base_set</tt>
	 *
	 *	@param evaluatingBaseSet
	 *	@return String
	 *	@throws DataAccessException
	 */	 
    public String countScoreByType(EvaluatingBaseSetDO evaluatingBaseSet) throws DataAccessException {
    	if (evaluatingBaseSet == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}


        return (String) getSqlMapClientTemplate().queryForObject("MS-EVALUATING-BASE-SET-COUNT-SCORE-BY-TYPE", evaluatingBaseSet);

    }

}