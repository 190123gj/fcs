package com.born.fcs.face.integration.pm.service.projectissueinformation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectBondReinsuranceInformationInfo;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectBondReinsuranceInformationOrder;
import com.born.fcs.pm.ws.order.projectissueinformation.ProjectBondReinsuranceInformationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectBondReinsuranceInformationService;

/**
 * 
 * @author jil
 *
 */
@Service("projectBondReinsuranceInformationServiceClient")
public class ProjectBondReinsuranceInformationServiceClient extends ClientAutowiredBaseService
																								implements
																								ProjectBondReinsuranceInformationService {
	
	@Override
	public ProjectBondReinsuranceInformationInfo findById(final long id) {
		return callInterface(new CallExternalInterface<ProjectBondReinsuranceInformationInfo>() {
			
			@Override
			public ProjectBondReinsuranceInformationInfo call() {
				return projectBondReinsuranceInformationWebService.findById(id);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final ProjectBondReinsuranceInformationOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectBondReinsuranceInformationWebService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectBondReinsuranceInformationInfo> query(	final ProjectBondReinsuranceInformationQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectBondReinsuranceInformationInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectBondReinsuranceInformationInfo> call() {
				return projectBondReinsuranceInformationWebService.query(order);
			}
		});
	}
	
	@Override
	public List<ProjectBondReinsuranceInformationInfo> findByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<List<ProjectBondReinsuranceInformationInfo>>() {
			
			@Override
			public List<ProjectBondReinsuranceInformationInfo> call() {
				return projectBondReinsuranceInformationWebService.findByProjectCode(projectCode);
			}
		});
	}
	
}
