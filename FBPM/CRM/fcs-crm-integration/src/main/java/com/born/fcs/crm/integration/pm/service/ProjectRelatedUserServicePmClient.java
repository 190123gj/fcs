package com.born.fcs.crm.integration.pm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.crm.integration.bpm.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.managerbchange.ChangeManagerbPhaseEnum;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;

@Service("projectRelatedUserServicePmClient")
public class ProjectRelatedUserServicePmClient extends ClientAutowiredBaseService implements
																					ProjectRelatedUserService {
	@Autowired
	ProjectRelatedUserService projectRelatedUserPmWebService;
	
	@Override
	public QueryBaseBatchResult<ProjectRelatedUserInfo> queryPage(	final ProjectRelatedUserQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectRelatedUserInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectRelatedUserInfo> call() {
				return projectRelatedUserPmWebService.queryPage(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult setRelatedUser(final ProjectRelatedUserOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRelatedUserPmWebService.setRelatedUser(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult setCouncilJudges(final String projectCode, final List<Long> judgeIds,
											final long councilId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRelatedUserPmWebService.setCouncilJudges(projectCode, judgeIds,
					councilId);
			}
		});
	}
	
	@Override
	public FcsBaseResult setRiskTeamMember(final String projectCode, final List<Long> memberIds) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRelatedUserPmWebService.setRiskTeamMember(projectCode, memberIds);
			}
		});
	}
	
	@Override
	public FcsBaseResult manualGrant(final String projectCode, final String remark,
										final SimpleUserInfo... users) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRelatedUserPmWebService.manualGrant(projectCode, remark, users);
			}
		});
	}
	
	@Override
	public FcsBaseResult manualRevoke(final String projectCode, final long... userIds) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return projectRelatedUserPmWebService.manualRevoke(projectCode, userIds);
			}
		});
	}
	
	@Override
	public FcsBaseResult revokeFlowRelated(final String projectCode, final long userId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return projectRelatedUserPmWebService.revokeFlowRelated(projectCode, userId);
			}
		});
	}
	
	@Override
	public ProjectRelatedUserInfo getBusiManager(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserPmWebService.getBusiManager(projectCode);
			}
		});
	}
	

	
	@Override
	public ProjectRelatedUserInfo getBusiManagerb(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserPmWebService.getBusiManagerb(projectCode);
			}
		});
	}
	
	@Override
	public ProjectRelatedUserInfo getBusiManagerbByPhase(final String projectCode,
															final ChangeManagerbPhaseEnum phase) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserPmWebService.getBusiManagerbByPhase(projectCode,phase);
			}
		});
	}
	
	@Override
	public ProjectRelatedUserInfo getRiskManager(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserPmWebService.getRiskManager(projectCode);
			}
		});
	}
	
	@Override
	public ProjectRelatedUserInfo getOrignalRiskManager(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserPmWebService.getOrignalRiskManager(projectCode);
			}
		});
	}
	
	@Override
	public ProjectRelatedUserInfo getLegalManager(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserPmWebService.getLegalManager(projectCode);
			}
		});
	}
	
	@Override
	public ProjectRelatedUserInfo getFinancialManager(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectRelatedUserInfo>() {
			@Override
			public ProjectRelatedUserInfo call() {
				return projectRelatedUserPmWebService.getFinancialManager(projectCode);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getBusiDirector(final String projectCode) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserPmWebService.getBusiDirector(projectCode);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getDirector(final String deptCode) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserPmWebService.getDirector(deptCode);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getFgfz(final String deptCode) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserPmWebService.getFgfz(deptCode);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getDeptRoleUser(final String deptCode, final String roleName) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserPmWebService.getDeptRoleUser(deptCode, roleName);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getRoleDeptUser(final long deptId, final String roleName) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserPmWebService.getRoleDeptUser(deptId, roleName);
			}
		});
	}
	
	@Override
	public boolean isBelong2Dept(final long userId, final String deptCode) {
		return callInterface(new CallExternalInterface<Boolean>() {
			@Override
			public Boolean call() {
				return projectRelatedUserPmWebService.isBelong2Dept(userId, deptCode);
			}
		});
	}
	
	@Override
	public boolean isBelongToDept(final UserDetailInfo userDetail, final String deptCode) {
		return callInterface(new CallExternalInterface<Boolean>() {
			@Override
			public Boolean call() {
				return projectRelatedUserPmWebService.isBelongToDept(userDetail, deptCode);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getDeptPosUser(final String deptCode, final String posName) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserPmWebService.getDeptPosUser(deptCode, posName);
			}
		});
	}
	
	@Override
	public List<SimpleUserInfo> getPosDeptUser(final long deptId, final String posName) {
		return callInterface(new CallExternalInterface<List<SimpleUserInfo>>() {
			@Override
			public List<SimpleUserInfo> call() {
				return projectRelatedUserPmWebService.getPosDeptUser(deptId, posName);
			}
		});
	}
}
