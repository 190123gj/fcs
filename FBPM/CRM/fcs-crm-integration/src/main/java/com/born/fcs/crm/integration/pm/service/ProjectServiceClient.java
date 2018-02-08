package com.born.fcs.crm.integration.pm.service;

import org.springframework.stereotype.Service;

import com.born.fcs.crm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.crm.integration.bpm.service.ClientAutowiredBaseService;
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
import com.born.fcs.pm.ws.service.common.ProjectService;

@Service("projectServiceClient")
public class ProjectServiceClient extends ClientAutowiredBaseService implements ProjectService {
	
	/** 项目管理中customerId对应客户系统中的userId */
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProject(final ProjectQueryOrder projectQueryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() {
				return projectWebService.queryProject(projectQueryOrder);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectApprovaInfo> queryProjectApproval(	final ProjectApprovalQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectApprovaInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectApprovaInfo> call() {
				return projectWebService.queryProjectApproval(order);
			}
		});
	}
	
	@Override
	public ProjectInfo queryByCode(final String projectCode, final boolean forUpdate) {
		return callInterface(new CallExternalInterface<ProjectInfo>() {
			
			@Override
			public ProjectInfo call() {
				return projectWebService.queryByCode(projectCode, forUpdate);
			}
		});
	}
	
	@Override
	public ProjectResult saveProject(final ProjectOrder order) {
		return callInterface(new CallExternalInterface<ProjectResult>() {
			
			@Override
			public ProjectResult call() {
				return projectWebService.saveProject(order);
			}
		});
	}
	
	@Override
	public ProjectResult changeProjectStatus(final ChangeProjectStatusOrder order) {
		return callInterface(new CallExternalInterface<ProjectResult>() {
			
			@Override
			public ProjectResult call() {
				return projectWebService.changeProjectStatus(order);
			}
		});
	}
	
	@Override
	public ProjectSimpleDetailInfo querySimpleDetailInfo(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectSimpleDetailInfo>() {
			
			@Override
			public ProjectSimpleDetailInfo call() {
				return projectWebService.querySimpleDetailInfo(projectCode);
			}
		});
	}
	
	@Override
	public ProjectSimpleDetailInfo getSimpleDetailInfo(final ProjectInfo info) {
		return callInterface(new CallExternalInterface<ProjectSimpleDetailInfo>() {
			
			@Override
			public ProjectSimpleDetailInfo call() {
				return projectWebService.getSimpleDetailInfo(info);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectSimpleDetailInfo> queryProjectSimpleDetail(	final ProjectQueryOrder projectQueryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectSimpleDetailInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectSimpleDetailInfo> call() {
				return projectWebService.queryProjectSimpleDetail(projectQueryOrder);
			}
		});
	}
	
	@Override
	public int updateIsContinueByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return projectWebService.updateIsContinueByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult pauseProject(final String projectCode) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectWebService.pauseProject(projectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult restartProject(final String projectCode) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectWebService.restartProject(projectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult transferProject(final TransferProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectWebService.transferProject(order);
			}
		});
	}
	
	@Override
	public ProjectBatchResult getMainCountMessage() {
		return callInterface(new CallExternalInterface<ProjectBatchResult>() {
			
			@Override
			public ProjectBatchResult call() {
				return projectWebService.getMainCountMessage();
			}
		});
	}
	
	@Override
	public FcsBaseResult updateByProjectCode(final UpdateProjectBaseOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectWebService.updateByProjectCode(order);
			}
		});
	}
	
	@Override
	public ProjectCandoResult getCando(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectCandoResult>() {
			
			@Override
			public ProjectCandoResult call() {
				return projectWebService.getCando(projectCode);
			}
		});
	}
	
	@Override
	public ProjectCandoResult getCandoByProject(final ProjectInfo project) {
		return callInterface(new CallExternalInterface<ProjectCandoResult>() {
			
			@Override
			public ProjectCandoResult call() {
				return projectWebService.getCandoByProject(project);
			}
		});
	}
	
	@Override
	public FcsBaseResult redoProject(ProjectRedoOrder order) {
		return null;
	}
	
	@Override
	public FcsBaseResult syncForecastDeposit(String projectCode) {
		return null;
	}
}
