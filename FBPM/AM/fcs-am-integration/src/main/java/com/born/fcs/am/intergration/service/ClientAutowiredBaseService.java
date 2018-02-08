package com.born.fcs.am.intergration.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.pm.biz.service.common.MailService;
import com.born.fcs.pm.biz.service.common.OperationJournalService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.ws.service.common.FormMessageTempleteService;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.common.SubsystemDockProjectService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.born.fcs.pm.ws.service.forcall.ForAmService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.born.fcs.pm.ws.service.riskHandleTeam.RiskHandleTeamService;
import com.born.fcs.pm.ws.service.sms.SMSService;

public class ClientAutowiredBaseService extends ClientBaseSevice {
	
	@Autowired
	protected SiteMessageService siteMessageWebService;
	@Autowired
	protected MailService mailService;
	@Autowired
	protected SysParameterService sysParameterWebService;
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserPmWebService;
	
	@Autowired
	protected FormRelatedUserService formRelatedUserPmWebService;
	
	@Autowired
	protected SMSService sMSWebService;
	
	@Autowired
	protected FormMessageTempleteService formMessageTempleteWebService;
	
	@Autowired
	protected ProjectService projectWebService;
	@Autowired
	protected ProjectCreditConditionService projectCreditConditionWebService;
	@Autowired
	protected OperationJournalService operationJournalWebService;
	
	@Autowired
	protected CouncilApplyService councilApplyWebService;
	@Autowired
	protected CouncilProjectService councilProjectWebService;
	
	// 子系统对接项目信息
	@Autowired
	protected SubsystemDockProjectService subsystemDockProjectWebService;
	//调用pm
	@Autowired
	protected ForAmService pmWebService;
	@Autowired
	protected RiskHandleTeamService riskHandleTeamService;
}
