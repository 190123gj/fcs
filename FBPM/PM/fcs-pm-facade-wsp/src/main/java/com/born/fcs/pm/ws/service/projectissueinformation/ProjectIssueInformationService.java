package com.born.fcs.pm.ws.service.projectissueinformation;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectIssueInformationInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectIssueInformationOrder;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectIssueInformationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 承销/发债类项目发行信息
 *
 * @author Ji
 */
@WebService
public interface ProjectIssueInformationService {
	/**
	 * 根据ID查询承销/发债类项目发行信息
	 * @param id
	 * @return
	 */
	ProjectIssueInformationInfo findById(long id);
	
	/**
	 * 保存承销/发债类项目发行信息
	 * @param order
	 * @return
	 */
	FcsBaseResult save(ProjectIssueInformationOrder order);
	
	/**
	 * 分页查询承销/发债类项目发行信息
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectIssueInformationInfo> query(ProjectIssueInformationQueryOrder order);
	
	/**
	 * 分页查询承销/发债类项目发行信息
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> queryIssueProject(ProjectQueryOrder order);
	
	//	/**
	//	 * 根据项目编号、客户名称、业务类型查询承销/发债类项目发行信息
	//	 * @param projectCode 项目编号
	//	 * @param projectCode 客户名称
	//	 * @param projectCode 业务类型
	//	 * @return
	//	 */
	//	ProjectIssueInformationInfo queryProjectIssueInformationByCondition(String projectCode,
	//																		String customerName,
	//																		String busiTypeName);
	/**
	 * 根据项目编号查询所有的项目的发行信息
	 * @param projectCode
	 * @return
	 */
	List<ProjectIssueInformationInfo> findProjectIssueInformationByProjectCode(String projectCode);
	
	/**
	 * 根据项目编号实时计算发售状态(发债类项目可用)
	 * @param projectCode
	 * @return
	 */
	int updateStatusByProjectCode(String projectCode);
	
	/**
	 * 新增时去重分页查询承销项目信息
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> queryConsignmentSales(ProjectQueryOrder order);
	
	/**
	 * 新增时去重分页查询发债项目信息
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectInfo> queryBond(ProjectQueryOrder order);
	
	/**
	 * 根据当前和项目截止日期更新状态
	 * @param projectCode
	 * @return
	 */
	int updateStatusByEndTime(String projectCode);
}
