package com.born.fcs.rm.integration.common.impl;

import com.born.fcs.pm.ws.service.report.ToReportService;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.pm.biz.service.common.OperationJournalService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.ws.service.common.FormMessageTempleteService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.sms.SMSService;


public class ClientAutowiredBaseService extends ClientBaseSevice {
	
	@Autowired
	protected SiteMessageService siteMessageWebService;
	
	@Autowired
	protected SysParameterService sysParameterWebService;
	
	@Autowired
	protected SMSService sMSWebService;
	
	@Autowired
	protected FormMessageTempleteService formMessageTempleteWebService;
	
	@Autowired
	protected ProjectService projectWebService;
	
	@Autowired
	protected OperationJournalService operationJournalWebService;

	@Autowired
	protected ToReportService toReportWebService;
}
