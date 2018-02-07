package com.born.fcs.face.integration.pm.service.projectissueinformation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.FCreditConditionConfirmInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.FCreditConditionConfirmItemInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectIssueInformationInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmQueryOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionOrder;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectIssueInformationOrder;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectIssueInformationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectIssueInformationService;

/**
 * 
 * @author jil
 *
 */
@Service("projectIssueInformationServiceClient")
public class ProjectIssueInformationServiceClient extends ClientAutowiredBaseService implements ProjectIssueInformationService{
	
	@Override
	public ProjectIssueInformationInfo findById(final long id) {
		return callInterface(new CallExternalInterface<ProjectIssueInformationInfo>() {
			
			@Override
			public ProjectIssueInformationInfo call() {
				return projectIssueInformationWebService.findById(id);
			}
		});
	}

	@Override
	public FcsBaseResult save(final ProjectIssueInformationOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectIssueInformationWebService.save(order);
			}
		});
	}

	@Override
	public QueryBaseBatchResult<ProjectIssueInformationInfo> query(	final ProjectIssueInformationQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectIssueInformationInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectIssueInformationInfo> call() {
				return projectIssueInformationWebService.query(order);
			}
		});
	}

	@Override
	public QueryBaseBatchResult<ProjectInfo> queryIssueProject(final ProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() {
				return projectIssueInformationWebService.queryIssueProject(order);
			}
		});
	}

	@Override
	public List<ProjectIssueInformationInfo> findProjectIssueInformationByProjectCode(	final String projectCode) {
		return callInterface(new CallExternalInterface<List<ProjectIssueInformationInfo>>() {
			
			@Override
			public List<ProjectIssueInformationInfo> call() {
				return projectIssueInformationWebService.findProjectIssueInformationByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public int updateStatusByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return projectIssueInformationWebService.updateStatusByProjectCode(projectCode);
			}
		});
	}

	@Override
	public QueryBaseBatchResult<ProjectInfo> queryConsignmentSales(final ProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() {
				return projectIssueInformationWebService.queryConsignmentSales(order);
			}
		});
	}

	@Override
	public int updateStatusByEndTime(final String projectCode) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return projectIssueInformationWebService.updateStatusByEndTime(projectCode);
			}
		});
	}
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryBond(final ProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() {
				return projectIssueInformationWebService.queryBond(order);
			}
		});
	}
}
