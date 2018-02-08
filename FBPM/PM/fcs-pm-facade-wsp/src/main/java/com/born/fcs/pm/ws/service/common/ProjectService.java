package com.born.fcs.pm.ws.service.common;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.common.ProjectApprovaInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.ProjectApprovalQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.common.TransferProjectOrder;
import com.born.fcs.pm.ws.order.project.ProjectRedoOrder;
import com.born.fcs.pm.ws.order.project.UpdateProjectBaseOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectCandoResult;
import com.born.fcs.pm.ws.result.common.ProjectResult;

/**
 * 项目数据
 * @author wuzj
 */
@WebService
public interface ProjectService {
	
	/**
	 * 分页查询项目信息
	 * @param projectQueryOrder
	 * @return 项目列表
	 */
	QueryBaseBatchResult<ProjectInfo> queryProject(ProjectQueryOrder projectQueryOrder);
	
	/**
	 * 分页查询项目信息（包括一些简单的详细信息）
	 * @param projectQueryOrder
	 * @return
	 */
	QueryBaseBatchResult<ProjectSimpleDetailInfo> queryProjectSimpleDetail(	ProjectQueryOrder projectQueryOrder);
	
	/**
	 * 分页查询项目批复
	 * @param order
	 * @return 项目列表
	 */
	QueryBaseBatchResult<ProjectApprovaInfo> queryProjectApproval(ProjectApprovalQueryOrder order);
	
	/**
	 * 根据项目编号查询项目信息
	 * @param projectCode 项目编号
	 * @param querySummaryDetail 会议纪要详情
	 * @return
	 */
	ProjectInfo queryByCode(String projectCode, boolean queryDetail);
	
	/**
	 * 根据项目编号查询常用信息
	 * @param projectCode
	 * @return
	 */
	ProjectSimpleDetailInfo querySimpleDetailInfo(String projectCode);
	
	/**
	 * 根据项目info查询项目详细信息
	 * @param info
	 * @return
	 */
	ProjectSimpleDetailInfo getSimpleDetailInfo(ProjectInfo info);
	
	/**
	 * 保存项目信息
	 * @param order
	 * @return
	 */
	ProjectResult saveProject(ProjectOrder order);
	
	/**
	 * 修改项目阶段/状态
	 * @param order
	 * @return
	 */
	ProjectResult changeProjectStatus(ChangeProjectStatusOrder order);
	
	/**
	 * 根据项目编号修改是否继续发售状态
	 * @param projectCode
	 * @return
	 */
	int updateIsContinueByProjectCode(String projectCode);
	
	/**
	 * 项目暂缓
	 * @param projectCode
	 * @return
	 */
	FcsBaseResult pauseProject(String projectCode);
	
	/**
	 * 重启项目
	 * @param projectCode
	 * @return
	 */
	FcsBaseResult restartProject(String projectCode);
	
	/**
	 * 项目移交
	 * @param order
	 * @return
	 */
	FcsBaseResult transferProject(TransferProjectOrder order);
	
	/**
	 * 获取管理员首页统计所需数据
	 * @return
	 */
	ProjectBatchResult getMainCountMessage();
	
	/**
	 * 更新项目信息
	 * @param order
	 * @return
	 */
	FcsBaseResult updateByProjectCode(UpdateProjectBaseOrder order);
	
	/**
	 * 项目可操作列表
	 * @return
	 */
	ProjectCandoResult getCando(String projectCode);
	
	/**
	 * 项目可操作列表
	 * @return
	 */
	ProjectCandoResult getCandoByProject(ProjectInfo project);
	
	/**
	 * 同步退换客户保证金预测数据(客户保证金数据有改变或者项目有到期时间后触发)
	 * @param formId
	 * @param afterAudit
	 * @return
	 */
	FcsBaseResult syncForecastDeposit(String projectCode);
	
	/**
	 * 项目重新授信
	 * @param order
	 * @return
	 */
	FcsBaseResult redoProject(ProjectRedoOrder order);
}
