package com.born.fcs.crm.integration.bpm.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.pm.biz.service.common.OperationJournalService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.ws.service.check.AfterwardsCheckService;
import com.born.fcs.pm.ws.service.common.FormMessageTempleteService;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.forCrm.CrmUseService;
import com.born.fcs.pm.ws.service.investigation.InvestigationService;
import com.born.fcs.pm.ws.service.sms.SMSService;

public class ClientAutowiredBaseService extends ClientBaseSevice {
	
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserPmWebService;
	
	@Autowired
	protected FormRelatedUserService formRelatedUserPmWebService;
	
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
	public InvestigationService investigationWebService;
	@Autowired
	protected AfterwardsCheckService afterwardsCheckWebService;
	
	@Autowired
	protected CrmUseService crmUseService;
}
