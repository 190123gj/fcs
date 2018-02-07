package com.born.fcs.face.integration.pm.service.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;

@Service("projectRelatedUserServiceClient")
public class ProjectRelatedUserServiceClient extends ClientAutowiredBaseService implements
																				ProjectRelatedUserService {
	
	@Override
	public QueryBaseBatchResult<ProjectRelatedUserInfo> queryPage(	final ProjectRelatedUserQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectRelatedUserInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectRelatedUserInfo> call() {
				return projectRelatedUserWebService.queryPage(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult setRelatedUser(final ProjectRelatedUserOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRelatedUserWebService.setRelatedUser(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult setCouncilJudges(final String projectCode, final List<Long> judgeIds,
											final long councilId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRelatedUserWebService.setCouncilJudges(projectCode, judgeIds,
					councilId);
			}
		});
	}
	
	@Override
	public FcsBaseResult setRiskTeamMember(final String projectCode, final List<Long> memberIds) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRelatedUserWebService.setRiskTeamMember(projectCode, memberIds);
			}
		});
	}
	
	@Override
	public FcsBaseResult manualGrant(final String projectCode, final String remark,
										final SimpleUserInfo... users) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRelatedUserWebService.manualGrant(projectCode, remark, users);
			}
		});
	}
	
	@Override
	public FcsBaseResult manualRevoke(final String projectCode, final long... userIds) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return projectRelatedUserWebService.manualRevoke(projectCode, userIds);
			}
		});
	}
	
	@Override
	public FcsBaseResult revokeFlowRelated(final String projectCode, final long userId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return projectRelatedUserWebService.revokeFlowRelated(projectCode, userId);
			}
		});
	}
	
	@Override
	public ProjectRelatedUserInfo getBusiManager(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserWebService.getBusiManager(projectCode);
			}
		});
	}
	
	@Override
	public ProjectRelatedUserInfo getBusiManagerb(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserWebService.getBusiManagerb(projectCode);
			}
		});
	}
	
	@Override
	public ProjectRelatedUserInfo getBusiManagerbByPhase(final String projectCode,
															final ChangeManagerbPhaseEnum phase) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserWebService.getBusiManagerbByPhase(projectCode, phase);
			}
		});
	};
	
	@Override
	public ProjectRelatedUserInfo getRiskManager(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserWebService.getRiskManager(projectCode);
			}
		});
	}
	
	@Override
	public ProjectRelatedUserInfo getOrignalRiskManager(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserWebService.getOrignalRiskManager(projectCode);
			}
		});
	}
	
	@Override
	public ProjectRelatedUserInfo getLegalManager(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserWebService.getLegalManager(projectCode);
			}
		});
	}
	
	@Override
	public ProjectRelatedUserInfo getFinancialManager(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserWebService.getFinancialManager(projectCode);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getBusiDirector(final String projectCode) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserWebService.getBusiDirector(projectCode);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getDirector(final String deptCode) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserWebService.getDirector(deptCode);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getFgfz(final String deptCode) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserWebService.getFgfz(deptCode);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getDeptRoleUser(final String deptCode, final String roleName) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserWebService.getDeptRoleUser(deptCode, roleName);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getRoleDeptUser(final long deptId, final String roleName) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserWebService.getRoleDeptUser(deptId, roleName);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getDeptPosUser(final String deptCode, final String posName) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserWebService.getDeptPosUser(deptCode, posName);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getPosDeptUser(final long deptId, final String posName) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserWebService.getPosDeptUser(deptId, posName);
			}
		});
	}
	
	@Override
	public boolean isBelong2Dept(final long userId, final String deptCode) {
		return callInterface(new CallExternalInterface<Boolean>() {
			
			@Override
			public Boolean call() {
				return projectRelatedUserWebService.isBelong2Dept(userId, deptCode);
			}
		});
	}
	
	@Override
	public boolean isBelongToDept(final UserDetailInfo userDetail, final String deptCode) {
		return callInterface(new CallExternalInterface<Boolean>() {
			
			@Override
			public Boolean call() {
				return projectRelatedUserWebService.isBelongToDept(userDetail, deptCode);
			}
		});
	}
	
}
