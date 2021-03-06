/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.pm.dal.daointerface.FCouncilSummaryProjectPledgeAssetDAO;


// auto generated imports
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectPledgeAssetDO;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;
	import java.util.HashMap;
	
/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.pm.dal.daointerface.FCouncilSummaryProjectPledgeAssetDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_council_summary_project_pledge_asset.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisFCouncilSummaryProjectPledgeAssetDAO extends SqlMapClientDaoSupport implements FCouncilSummaryProjectPledgeAssetDAO {
	/**
	 *  Insert one <tt>FCouncilSummaryProjectPledgeAssetDO</tt> object to DB table <tt>f_council_summary_project_pledge_asset</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_council_summary_project_pledge_asset(id,sp_id,type,type_desc,assets_id,asset_type,ownership_name,evaluation_price,pledge_rate,syn_position,postposition,debted_amount,asset_remark,remark,sort_order,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FCouncilSummaryProjectPledgeAsset
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FCouncilSummaryProjectPledgeAssetDO FCouncilSummaryProjectPledgeAsset) throws DataAccessException {
    	if (FCouncilSummaryProjectPledgeAsset == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-F-COUNCIL-SUMMARY-PROJECT-PLEDGE-ASSET-INSERT", FCouncilSummaryProjectPledgeAsset);

        return FCouncilSummaryProjectPledgeAsset.getId();
    }

	/**
	 *  Update DB table <tt>f_council_summary_project_pledge_asset</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_council_summary_project_pledge_asset set sp_id=?, type=?, type_desc=?, assets_id=?, asset_type=?, ownership_name=?, evaluation_price=?, pledge_rate=?, syn_position=?, postposition=?, debted_amount=?, asset_remark=?, remark=?, sort_order=? where (id = ?)</tt>
	 *
	 *	@param FCouncilSummaryProjectPledgeAsset
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FCouncilSummaryProjectPledgeAssetDO FCouncilSummaryProjectPledgeAsset) throws DataAccessException {
    	if (FCouncilSummaryProjectPledgeAsset == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-F-COUNCIL-SUMMARY-PROJECT-PLEDGE-ASSET-UPDATE", FCouncilSummaryProjectPledgeAsset);
    }

	/**
	 *  Update DB table <tt>f_council_summary_project_pledge_asset</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_council_summary_project_pledge_asset set ownership_name=?, asset_remark=?, evaluation_price=?, pledge_rate=? where (assets_id = ?)</tt>
	 *
	 *	@param FCouncilSummaryProjectPledgeAsset
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateByAssertsId(FCouncilSummaryProjectPledgeAssetDO FCouncilSummaryProjectPledgeAsset) throws DataAccessException {
    	if (FCouncilSummaryProjectPledgeAsset == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-F-COUNCIL-SUMMARY-PROJECT-PLEDGE-ASSET-UPDATE-BY-ASSERTS-ID", FCouncilSummaryProjectPledgeAsset);
    }

	/**
	 *  Query DB table <tt>f_council_summary_project_pledge_asset</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_council_summary_project_pledge_asset where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return FCouncilSummaryProjectPledgeAssetDO
	 *	@throws DataAccessException
	 */	 
    public FCouncilSummaryProjectPledgeAssetDO findById(long id) throws DataAccessException {
        Long param = new Long(id);

        return (FCouncilSummaryProjectPledgeAssetDO) getSqlMapClientTemplate().queryForObject("MS-F-COUNCIL-SUMMARY-PROJECT-PLEDGE-ASSET-FIND-BY-ID", param);

    }

	/**
	 *  Query DB table <tt>f_council_summary_project_pledge_asset</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_council_summary_project_pledge_asset where (sp_id = ?) order by sort_order ASC</tt>
	 *
	 *	@param spId
	 *	@return List<FCouncilSummaryProjectPledgeAssetDO>
	 *	@throws DataAccessException
	 */	 
    public List<FCouncilSummaryProjectPledgeAssetDO> findBySpId(long spId) throws DataAccessException {
        Long param = new Long(spId);

        return getSqlMapClientTemplate().queryForList("MS-F-COUNCIL-SUMMARY-PROJECT-PLEDGE-ASSET-FIND-BY-SP-ID", param);

    }

	/**
	 *  Query DB table <tt>f_council_summary_project_pledge_asset</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_council_summary_project_pledge_asset where ((sp_id = ?) AND (type = ?)) order by sort_order ASC</tt>
	 *
	 *	@param spId
	 *	@param type
	 *	@return List<FCouncilSummaryProjectPledgeAssetDO>
	 *	@throws DataAccessException
	 */	 
    public List<FCouncilSummaryProjectPledgeAssetDO> findBySpIdAndType(long spId, String type) throws DataAccessException {
        Map param = new HashMap();

        param.put("spId", new Long(spId));
        param.put("type", type);

        return getSqlMapClientTemplate().queryForList("MS-F-COUNCIL-SUMMARY-PROJECT-PLEDGE-ASSET-FIND-BY-SP-ID-AND-TYPE", param);

    }

	/**
	 *  Delete records from DB table <tt>f_council_summary_project_pledge_asset</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_council_summary_project_pledge_asset where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-F-COUNCIL-SUMMARY-PROJECT-PLEDGE-ASSET-DELETE-BY-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>f_council_summary_project_pledge_asset</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_council_summary_project_pledge_asset where (sp_id = ?)</tt>
	 *
	 *	@param spId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteBySpId(long spId) throws DataAccessException {
        Long param = new Long(spId);

        return getSqlMapClientTemplate().delete("MS-F-COUNCIL-SUMMARY-PROJECT-PLEDGE-ASSET-DELETE-BY-SP-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>f_council_summary_project_pledge_asset</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_council_summary_project_pledge_asset where ((sp_id = ?) AND (type = ?))</tt>
	 *
	 *	@param spId
	 *	@param type
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteBySpIdAndType(long spId, String type) throws DataAccessException {
        Map param = new HashMap();

        param.put("spId", new Long(spId));
        param.put("type", type);

        return getSqlMapClientTemplate().delete("MS-F-COUNCIL-SUMMARY-PROJECT-PLEDGE-ASSET-DELETE-BY-SP-ID-AND-TYPE", param);
    }

}