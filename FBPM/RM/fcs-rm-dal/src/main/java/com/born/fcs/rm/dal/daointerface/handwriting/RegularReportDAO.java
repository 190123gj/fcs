package com.born.fcs.rm.dal.daointerface.handwriting;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.born.fcs.rm.dal.dataobject.RegularProjectChannelInfoDO;
import com.born.fcs.rm.dal.dataobject.RegularProjectImpelInfoDO;
import com.born.fcs.rm.dal.dataobject.RegularProjectStoreInfoDO;
import com.born.fcs.rm.dal.dataobject.RegularProjectSubBusiTypeInfoDO;
import com.born.fcs.rm.dal.dataobject.handwriting.RegularProjectMultiCapitalChannelInfoDO;
import com.born.fcs.rm.dal.dataobject.handwriting.RegularProjectSubBusiTypeDO;

/**
 * 基层定期报表
 * @author wuzj
 */
public interface RegularReportDAO {
	
	/**
	 * 项目基本情况
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectBase(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目基本情况-月表
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectBaseMonth(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 客户基本情况
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularCustomerBase(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 客户基本情况-月表
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularCustomerBaseMonth(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目推进情况数据
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	List<RegularProjectImpelInfoDO> selectRegularProjectImpel(Map<String, Object> param)
																						throws DataAccessException;
	
	/**
	 * 项目推进情况数据统计
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long countRegularProjectImpel(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目推进情况-月表
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectImpelMonth(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目储备情况数据
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	List<RegularProjectStoreInfoDO> selectRegularProjectStore(Map<String, Object> param)
																						throws DataAccessException;
	
	/**
	 * 项目储备情况数据统计
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long countRegularProjectStore(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目储备情况-月表
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectStoreMonth(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目运行情况
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectRun(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目运行情况-月表
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectRunMonth(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目收入情况
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectIncome(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目收入情况-月表
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectIncomeMonth(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目风险情况
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectRisk(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目风险情况-月表
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectRiskMonth(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目本月明细（收费计划、还款计划、本月发生、本月解保、本月代偿、本月回收）
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectExtraList(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目本月明细（收费计划、还款计划、本月发生、本月解保、本月代偿、本月回收）-月表
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectExtraListMonth(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目渠道相关数据(项目渠道/单资金渠道)
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectChannel(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 项目渠道相关数据-月表（包括资金渠道、项目渠道一起copy过来）
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectChannelMonth(Map<String, Object> param) throws DataAccessException;
	
	
	/**
	 * 查询项目渠道相关数据(多资金渠道)
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	List<RegularProjectMultiCapitalChannelInfoDO> selectRegularProjectMultiCapitalChannel(	Map<String, Object> param)
																														throws DataAccessException;
	
	/**
	 * 统计项目渠道相关数据(多资金渠道)
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long countRegularProjectMultiCapitalChannel(Map<String, Object> param)
																			throws DataAccessException;
	
	/**
	 * 查询客户资金渠道发生额情况
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	List<RegularProjectMultiCapitalChannelInfoDO> selectRegularCustomerChannelMultiCapitalChannelOccur(	Map<String, Object> param)
																																	throws DataAccessException;
	
	/**
	 * 统计客户资金渠道发生额情况
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long countRegularCustomerMultiCapitalChannelOccur(Map<String, Object> param)
																				throws DataAccessException;
	
	/**
	 * 更新客户资金渠道统计比例
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long updateRegularCustomerMultiCapitalChannelCountRate(RegularProjectChannelInfoDO param)
																								throws DataAccessException;
	
	/**
	 * 查询项目业务细分类相关数据
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	List<RegularProjectSubBusiTypeDO> selectRegularProjectSubBusiTypeInfo(Map<String, Object> param)
																									throws DataAccessException;
	
	/**
	 * 统计项目业务细分类相关数据
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long countRegularProjectSubBusiTypeInfo(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 查询客户业务细分类发生额情况
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	List<RegularProjectSubBusiTypeDO> selectRegularCustomerSubBusiTypeOccur(Map<String, Object> param)
																										throws DataAccessException;
	
	/**
	 * 统计客户业务细分类发生额情况
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long countRegularCustomerSubBusiTypeOccur(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 更新客户业务细分类统计比例
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long updateRegularCustomerSubBusiTypeOccur(RegularProjectSubBusiTypeInfoDO param)
																					throws DataAccessException;
	/**
	 * 项目业务细分类相关数据-月表
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long insertRegularProjectSubBusiTypeMonth(Map<String, Object> param) throws DataAccessException;
}
