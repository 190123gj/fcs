/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.dal.daointerface;

// auto generated imports
import com.born.fcs.am.dal.dataobject.AssetsAssessCompanyDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * A dao interface provides methods to access database table <tt>assets_assess_company</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/assets_assess_company.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
 @SuppressWarnings("rawtypes") 
public interface AssetsAssessCompanyDAO {
	/**
	 *  Insert one <tt>AssetsAssessCompanyDO</tt> object to DB table <tt>assets_assess_company</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into assets_assess_company(company_name,quality_land,quality_house,quality_assets,city_code,city,country_code,country_name,province_code,province_name,county_code,county_name,contact_addr,registered_capital,status,attach,remark,work_situation,attachment,technical_level,evaluation_efficiency,cooperation_situation,service_attitude,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param assetsAssessCompany
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(AssetsAssessCompanyDO assetsAssessCompany) throws DataAccessException;

	/**
	 *  Update DB table <tt>assets_assess_company</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update assets_assess_company set company_name=?, quality_land=?, quality_house=?, quality_assets=?, city_code=?, city=?, country_code=?, country_name=?, province_code=?, province_name=?, county_code=?, county_name=?, contact_addr=?, registered_capital=?, status=?, attach=?, remark=?, work_situation=?, attachment=?, technical_level=?, evaluation_efficiency=?, cooperation_situation=?, service_attitude=? where (id = ?)</tt>
	 *
	 *	@param assetsAssessCompany
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(AssetsAssessCompanyDO assetsAssessCompany) throws DataAccessException;

	/**
	 *  Query DB table <tt>assets_assess_company</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from assets_assess_company where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return AssetsAssessCompanyDO
	 *	@throws DataAccessException
	 */	 
    public AssetsAssessCompanyDO findById(long id) throws DataAccessException;

	/**
	 *  Query DB table <tt>assets_assess_company</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from assets_assess_company where (company_name = ?)</tt>
	 *
	 *	@param assetsAssessCompany
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByCompanyNameCount(AssetsAssessCompanyDO assetsAssessCompany) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>assets_assess_company</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from assets_assess_company where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException;

	/**
	 *  Query DB table <tt>assets_assess_company</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select distinct aac.city from assets_assess_company aac order by aac.city ASC</tt>
	 *
	 *	@return List<String>
	 *	@throws DataAccessException
	 */	 
    public List<String> findCities() throws DataAccessException;

	/**
	 *  Query DB table <tt>assets_assess_company</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from assets_assess_company where (1 = 1)</tt>
	 *
	 *	@param assetsAssessCompany
	 *	@param qualityLandList
	 *	@param qualityHouseList
	 *	@param qualityAssetsList
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(AssetsAssessCompanyDO assetsAssessCompany, List qualityLandList, List qualityHouseList, List qualityAssetsList) throws DataAccessException;

	/**
	 *  Query DB table <tt>assets_assess_company</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from assets_assess_company where (1 = 1)</tt>
	 *
	 *	@param assetsAssessCompany
	 *	@param limitStart
	 *	@param pageSize
	 *	@param sortCol
	 *	@param sortOrder
	 *	@param qualityLandList
	 *	@param qualityHouseList
	 *	@param qualityAssetsList
	 *	@return List<AssetsAssessCompanyDO>
	 *	@throws DataAccessException
	 */	 
    public List<AssetsAssessCompanyDO> findByCondition(AssetsAssessCompanyDO assetsAssessCompany, long limitStart, long pageSize, String sortCol, String sortOrder, List qualityLandList, List qualityHouseList, List qualityAssetsList) throws DataAccessException;

}