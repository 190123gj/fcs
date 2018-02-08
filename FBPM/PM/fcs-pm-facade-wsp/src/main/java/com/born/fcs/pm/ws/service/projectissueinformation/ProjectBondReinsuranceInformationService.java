package com.born.fcs.pm.ws.service.projectissueinformation;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.projectissueinformation.ProjectBondReinsuranceInformationInfo;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectBondReinsuranceInformationOrder;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectBondReinsuranceInformationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 发债类项目-分保信息Service
 *
 * @author Ji
 */
@WebService
public interface ProjectBondReinsuranceInformationService {
	/**
	 * 发债类项目分保信息
	 * @param id
	 * @return
	 */
	ProjectBondReinsuranceInformationInfo findById(long id);
	
	/**
	 * 保存发债类项目分保信息
	 * @param order
	 * @return
	 */
	FcsBaseResult save(ProjectBondReinsuranceInformationOrder order);
	
	/**
	 * 分页查询发债类项目分保信息
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectBondReinsuranceInformationInfo> query(	ProjectBondReinsuranceInformationQueryOrder order);
	
	/**
	 * 根据项目编号查询
	 * @param projectCode
	 * @return
	 */
	List<ProjectBondReinsuranceInformationInfo> findByProjectCode(String projectCode);
	
}
