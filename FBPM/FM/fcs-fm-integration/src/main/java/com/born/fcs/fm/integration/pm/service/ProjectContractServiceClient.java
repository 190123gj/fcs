package com.born.fcs.fm.integration.pm.service;

import java.util.List;

import com.born.fcs.pm.ws.info.contract.*;
import com.born.fcs.pm.ws.order.contract.ProjectContractItemInvalidOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.fm.integration.bpm.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.order.contract.ProjectContractItemOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.contract.ProjectContractCheckMessageResult;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;

/**
 * 
 * @author heh
 * 
 */
@Service("projectContractServiceClient")
public class ProjectContractServiceClient extends ClientAutowiredBaseService implements
																			ProjectContractService {
	
	@Autowired
	ProjectContractService projectContractWebService;
	
	@Override
	public ProjectContrctInfo findById(final long id) {
		return callInterface(new CallExternalInterface<ProjectContrctInfo>() {
			
			@Override
			public ProjectContrctInfo call() {
				return projectContractWebService.findById(id);
			}
		});
	}
	
	@Override
	public ProjectContractItemInfo findContractItemById(final long contractId) {
		return callInterface(new CallExternalInterface<ProjectContractItemInfo>() {
			
			@Override
			public ProjectContractItemInfo call() {
				return projectContractWebService.findContractItemById(contractId);
			}
		});
	}
	
	@Override
	public ProjectContrctInfo findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<ProjectContrctInfo>() {
			
			@Override
			public ProjectContrctInfo call() {
				return projectContractWebService.findByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final ProjectContractOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectContractWebService.save(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveContractBack(final ProjectContractItemOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectContractWebService.saveContractBack(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveCourtRuling(final ProjectContractItemOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectContractWebService.saveCourtRuling(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectContractResultInfo> query(	final ProjectContractQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectContractResultInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectContractResultInfo> call() {
				return projectContractWebService.query(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateContractStatus(final ProjectContractItemOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectContractWebService.updateContractStatus(order);
			}
		});
	}
	
	@Override
	public long checkISConfirmAll(final String projectCode) {
		return callInterface(new CallExternalInterface<Long>() {
			@Override
			public Long call() {
				return projectContractWebService.checkISConfirmAll(projectCode);
			}
		});
	}
	
	@Override
	public List<ProjectContractResultInfo> searchIsConfirmAll(final String projectCode) {
		return callInterface(new CallExternalInterface<List<ProjectContractResultInfo>>() {
			@Override
			public List<ProjectContractResultInfo> call() {
				return projectContractWebService.searchIsConfirmAll(projectCode);
			}
		});
	}
	
	@Override
	public ProjectContractItemInfo findByContractCode(final String contractCode) {
		return callInterface(new CallExternalInterface<ProjectContractItemInfo>() {
			
			@Override
			public ProjectContractItemInfo call() {
				return projectContractWebService.findByContractCode(contractCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveStandardContractContent(final ProjectContractItemOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectContractWebService.saveStandardContractContent(order);
			}
		});
	}
	
	@Override
	public ProjectContractCheckMessageResult findCheckMessageByContractItemId(	final ProjectContractItemOrder order) {
		return callInterface(new CallExternalInterface<ProjectContractCheckMessageResult>() {
			
			@Override
			public ProjectContractCheckMessageResult call() {
				return projectContractWebService.findCheckMessageByContractItemId(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateComtractItemUrl(final ProjectContractItemOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectContractWebService.updateComtractItemUrl(order);
			}
		});
	}

	@Override
	public ProjectContractItemInfo findByContractCodeProjectCreditConditionUse(final String contractCode) {
		return callInterface(new CallExternalInterface<ProjectContractItemInfo>() {

			@Override
			public ProjectContractItemInfo call() {
				return projectContractWebService.findByContractCodeProjectCreditConditionUse(contractCode);
			}
		});
	}

	@Override
	public ProjectContractItemInvalidInfo findContractInvalidById(final long id) {
		return callInterface(new CallExternalInterface<ProjectContractItemInvalidInfo>() {

			@Override
			public ProjectContractItemInvalidInfo call() {
				return projectContractWebService.findContractInvalidById(id);
			}
		});
	}

	@Override
	public ProjectContractItemInvalidInfo findContractInvalidByFormId(final long formId) {
		return callInterface(new CallExternalInterface<ProjectContractItemInvalidInfo>() {

			@Override
			public ProjectContractItemInvalidInfo call() {
				return projectContractWebService.findContractInvalidByFormId(formId);
			}
		});
	}

	@Override
	public FormBaseResult saveContractInvalid(final ProjectContractItemInvalidOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {

			@Override
			public FormBaseResult call() {
				return projectContractWebService.saveContractInvalid(order);
			}
		});
	}

	@Override
	public QueryBaseBatchResult<ProjectContractInvalidResultInfo> queryInvalidList(final ProjectContractQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectContractInvalidResultInfo>>() {

			@Override
			public QueryBaseBatchResult<ProjectContractInvalidResultInfo> call() {
				return projectContractWebService.queryInvalidList(order);
			}
		});
	}

	@Override
	public FcsBaseResult modfiyCnt(ProjectContractOrder order) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
