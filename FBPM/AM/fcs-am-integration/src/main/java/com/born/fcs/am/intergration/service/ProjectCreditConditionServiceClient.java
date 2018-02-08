package com.born.fcs.am.intergration.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.CounterGuaranteeMeasuresInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.FCreditConditionConfirmInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.FCreditConditionConfirmItemInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditAssetAttachmentInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.counterguarantee.CreditConditionReleaseOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmQueryOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * @author jil
 * 
 */
@Service("projectCreditConditionServiceClient")
public class ProjectCreditConditionServiceClient extends ClientAutowiredBaseService implements
																					ProjectCreditConditionService {
	
	@Override
	public ProjectCreditConditionInfo findById(final long id) {
		return callInterface(new CallExternalInterface<ProjectCreditConditionInfo>() {
			
			@Override
			public ProjectCreditConditionInfo call() {
				return projectCreditConditionWebService.findById(id);
			}
		});
	}
	
	@Override
	public FCreditConditionConfirmInfo queryFCreditConditionConfirmByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FCreditConditionConfirmInfo>() {
			
			@Override
			public FCreditConditionConfirmInfo call() {
				return projectCreditConditionWebService
					.queryFCreditConditionConfirmByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveProjectCreditCondition(final FCreditConditionConfirmOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectCreditConditionWebService.saveProjectCreditCondition(order);
			}
		});
	}
	
	@Override
	public FCreditConditionConfirmInfo findFCreditConditionConfirmByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<FCreditConditionConfirmInfo>() {
			
			@Override
			public FCreditConditionConfirmInfo call() {
				return projectCreditConditionWebService
					.findFCreditConditionConfirmByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public List<ProjectCreditConditionInfo> findProjectCreditConditionByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<List<ProjectCreditConditionInfo>>() {
			
			@Override
			public List<ProjectCreditConditionInfo> call() {
				return projectCreditConditionWebService
					.findProjectCreditConditionByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public List<ProjectCreditConditionInfo> findProjectCreditConditionByProjectCodeAndStatus(	final String projectCode,
																								final String status) {
		return callInterface(new CallExternalInterface<List<ProjectCreditConditionInfo>>() {
			
			@Override
			public List<ProjectCreditConditionInfo> call() {
				return projectCreditConditionWebService
					.findProjectCreditConditionByProjectCodeAndStatus(projectCode, status);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveFCreditConditionConfirmItem(final ProjectCreditConditionOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectCreditConditionWebService.saveFCreditConditionConfirmItem(order);
			}
		});
	}
	
	@Override
	public List<FCreditConditionConfirmItemInfo> findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<List<FCreditConditionConfirmItemInfo>>() {
			
			@Override
			public List<FCreditConditionConfirmItemInfo> call() {
				return projectCreditConditionWebService.findByFormId(formId);
			}
		});
	}
	
	@Override
	public FCreditConditionConfirmInfo findCreditConditionByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FCreditConditionConfirmInfo>() {
			
			@Override
			public FCreditConditionConfirmInfo call() {
				return projectCreditConditionWebService.findCreditConditionByFormId(formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveCreditConditionRelease(final CreditConditionReleaseOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectCreditConditionWebService.saveCreditConditionRelease(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FCreditConditionConfirmInfo> query(	final FCreditConditionConfirmQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FCreditConditionConfirmInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FCreditConditionConfirmInfo> call() {
				return projectCreditConditionWebService.query(order);
			}
		});
	}
	
	@Override
	public List<FCreditConditionConfirmItemInfo> findFCreditConditionConfirmItemByProjectCodeAndStatus(	final String projectCode,
																										final String status) {
		return callInterface(new CallExternalInterface<List<FCreditConditionConfirmItemInfo>>() {
			
			@Override
			public List<FCreditConditionConfirmItemInfo> call() {
				return projectCreditConditionWebService
					.findFCreditConditionConfirmItemByProjectCodeAndStatus(projectCode, status);
			}
		});
	}
	
	@Override
	public List<FCreditConditionConfirmItemInfo> findByConfirmId(final long confirmId) {
		return callInterface(new CallExternalInterface<List<FCreditConditionConfirmItemInfo>>() {
			
			@Override
			public List<FCreditConditionConfirmItemInfo> call() {
				return projectCreditConditionWebService.findByConfirmId(confirmId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectCreditConditionInfo> queryCreditCondition(	final ProjectCreditConditionQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectCreditConditionInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectCreditConditionInfo> call() {
				return projectCreditConditionWebService.queryCreditCondition(queryOrder);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProjectAndCredit(final ProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() {
				return projectCreditConditionWebService.queryProjectAndCredit(order);
			}
		});
	}
	
	@Override
	public ProjectCreditConditionInfo findByProjectCodeAndItemIdAndType(final String projectCode,
																		final Long itemId,
																		final String type) {
		return callInterface(new CallExternalInterface<ProjectCreditConditionInfo>() {
			
			@Override
			public ProjectCreditConditionInfo call() {
				return projectCreditConditionWebService.findByProjectCodeAndItemIdAndType(
					projectCode, itemId, type);
			}
		});
	}
	
	@Override
	public List<ProjectCreditAssetAttachmentInfo> findByCreditId(final long creditId) {
		return callInterface(new CallExternalInterface<List<ProjectCreditAssetAttachmentInfo>>() {
			
			@Override
			public List<ProjectCreditAssetAttachmentInfo> call() {
				return projectCreditConditionWebService.findByCreditId(creditId);
			}
		});
	}
	
	@Override
	public Money findMarginAmountByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<Money>() {
			
			@Override
			public Money call() {
				return projectCreditConditionWebService.findMarginAmountByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CounterGuaranteeMeasuresInfo> measuresList(	FCreditConditionConfirmQueryOrder queryOrder) {
		// TODO Auto-generated method stub
		return null;
	}
}
