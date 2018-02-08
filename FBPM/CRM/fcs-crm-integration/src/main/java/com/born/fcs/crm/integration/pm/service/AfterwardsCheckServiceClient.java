package com.born.fcs.crm.integration.pm.service;

import org.springframework.stereotype.Service;

import com.born.fcs.crm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.crm.integration.bpm.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.check.AfterwardsCheckInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckBaseInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckLitigationInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportContentInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCheckReportProjectInfo;
import com.born.fcs.pm.ws.info.check.FAfterwardsCustomerInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.check.AfterwardsEditionQueryOrder;
import com.born.fcs.pm.ws.order.check.AfterwordsCheckQueryOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckBaseOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckLitigationOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckQueryOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportContentOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCheckReportProjectOrder;
import com.born.fcs.pm.ws.order.check.FAfterwardsCustomerOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.check.AfterwardsCheckService;

@Service("afterwardsCheckServiceClient")
public class AfterwardsCheckServiceClient extends ClientAutowiredBaseService implements
																			AfterwardsCheckService {
	
	@Override
	public FAfterwardsCheckInfo findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FAfterwardsCheckInfo>() {
			@Override
			public FAfterwardsCheckInfo call() {
				return afterwardsCheckWebService.findByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final FAfterwardsCheckOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return afterwardsCheckWebService.save(order);
			}
		});
	}
	
	@Override
	public FAfterwardsCheckBaseInfo findBaseInfoByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FAfterwardsCheckBaseInfo>() {
			@Override
			public FAfterwardsCheckBaseInfo call() {
				return afterwardsCheckWebService.findBaseInfoByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveBaseInfo(final FAfterwardsCheckBaseOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return afterwardsCheckWebService.saveBaseInfo(order);
			}
		});
	}
	
	@Override
	public FAfterwardsCheckReportContentInfo findContentByFomrId(final long formId) {
		return callInterface(new CallExternalInterface<FAfterwardsCheckReportContentInfo>() {
			@Override
			public FAfterwardsCheckReportContentInfo call() {
				return afterwardsCheckWebService.findContentByFomrId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveContent(final FAfterwardsCheckReportContentOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return afterwardsCheckWebService.saveContent(order);
			}
		});
	}
	
	@Override
	public FAfterwardsCheckReportProjectInfo findContentProjectById(final long id) {
		return callInterface(new CallExternalInterface<FAfterwardsCheckReportProjectInfo>() {
			@Override
			public FAfterwardsCheckReportProjectInfo call() {
				return afterwardsCheckWebService.findContentProjectById(id);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveContentProject(final FAfterwardsCheckReportProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return afterwardsCheckWebService.saveContentProject(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<AfterwardsCheckInfo> list(final AfterwordsCheckQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<AfterwardsCheckInfo>>() {
			@Override
			public QueryBaseBatchResult<AfterwardsCheckInfo> call() {
				return afterwardsCheckWebService.list(queryOrder);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProjects(final ProjectQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() {
				return afterwardsCheckWebService.queryProjects(queryOrder);
			}
		});
	}
	
	@Override
	public FormBaseResult copy(final FAfterwardsCheckOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return afterwardsCheckWebService.copy(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FAfterwardsCheckInfo> queryAfterwardsCheckReport(	final FAfterwardsCheckQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FAfterwardsCheckInfo>>() {
			@Override
			public QueryBaseBatchResult<FAfterwardsCheckInfo> call() {
				return afterwardsCheckWebService.queryAfterwardsCheckReport(queryOrder);
			}
		});
	}
	
	@Override
	public FormBaseResult saveLitigation(final FAfterwardsCheckLitigationOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return afterwardsCheckWebService.saveLitigation(order);
			}
		});
	}
	
	@Override
	public FAfterwardsCheckLitigationInfo findLitigationByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FAfterwardsCheckLitigationInfo>() {
			@Override
			public FAfterwardsCheckLitigationInfo call() {
				return afterwardsCheckWebService.findLitigationByFormId(formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult checkContentProject(final long formId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return afterwardsCheckWebService.checkContentProject(formId);
			}
		});
	}
	
	@Override
	public FAfterwardsCheckReportContentInfo queryFinancilInfo(final String projectCode) {
		return callInterface(new CallExternalInterface<FAfterwardsCheckReportContentInfo>() {
			@Override
			public FAfterwardsCheckReportContentInfo call() {
				return afterwardsCheckWebService.queryFinancilInfo(projectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveCustomerInfo(FAfterwardsCustomerOrder order) {
		return null;
	}
	
	@Override
	public FAfterwardsCustomerInfo findCustomerByFomrId(long formId) {
		return null;
	}
	
	@Override
	public FcsBaseResult isEditionAvailable(AfterwardsEditionQueryOrder queryOrder) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public FAfterwardsCheckInfo queryLastEdition(FAfterwardsCheckQueryOrder queryOrder) {
		return null;
	}
}
