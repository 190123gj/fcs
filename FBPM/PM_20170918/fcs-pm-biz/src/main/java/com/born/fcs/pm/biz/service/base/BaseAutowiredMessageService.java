/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午10:53:08 创建
 */
package com.born.fcs.pm.biz.service.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.pm.biz.service.common.MailService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.born.fcs.pm.ws.service.sms.SMSService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class BaseAutowiredMessageService extends BaseAutowiredDomainService {
	
	@Autowired
	protected SiteMessageService siteMessageService;
	
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	protected BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	protected SysWebAccessTokenService sysWebAccessTokenService;
	
	@Autowired
	protected MailService mailService;
	
	@Autowired
	protected SMSService sMSService;
	
	/**
	 * 获取站外访问密钥
	 * @param userInfo
	 * @return
	 */
	protected String getAccessToken(SimpleUserInfo userInfo) {
		SysWebAccessTokenOrder tokenOrder = new SysWebAccessTokenOrder();
		BeanCopier.staticCopy(userInfo, tokenOrder);
		tokenOrder.setRemark("站外访问链接");
		FcsBaseResult tokenResult = sysWebAccessTokenService.addUserAccessToken(tokenOrder);
		if (tokenResult != null && tokenResult.isSuccess()) {
			return tokenResult.getUrl();
		} else {
			return "";
		}
	}
}
