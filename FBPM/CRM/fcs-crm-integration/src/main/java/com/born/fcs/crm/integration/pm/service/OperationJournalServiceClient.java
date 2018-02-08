package com.born.fcs.crm.integration.pm.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.crm.integration.bpm.service.ClientAutowiredBaseService;

import com.born.fcs.pm.biz.service.common.OperationJournalService;
import com.born.fcs.pm.ws.info.common.OperationJournalInfo;
import com.born.fcs.pm.ws.order.common.OperationJournalAddOrder;
import com.born.fcs.pm.ws.order.common.OperationJournalQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.OperationJournalServiceResult;

@Service("operationJournalServiceClient")
public class OperationJournalServiceClient extends ClientAutowiredBaseService
																				implements
																				OperationJournalService,
																				InitializingBean {
	@Autowired
	OperationJournalService operationJournalWebService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	@Override
	public OperationJournalServiceResult addOperationJournalInfo(	final OperationJournalAddOrder operationJournalAddOrder) {
		return callInterface(new CallExternalInterface<OperationJournalServiceResult>() {
			
			@Override
			public OperationJournalServiceResult call() {
				return operationJournalWebService.addOperationJournalInfo(operationJournalAddOrder);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<OperationJournalInfo> queryOperationJournalInfo(final OperationJournalQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<OperationJournalInfo>>() {
			
			@Override
			public QueryBaseBatchResult<OperationJournalInfo> call() {
				return operationJournalWebService.queryOperationJournalInfo(queryOrder);
			}
		});
	}
	
}
