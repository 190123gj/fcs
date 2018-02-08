/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午4:36:38 创建
 */
package com.born.fcs.pm.biz.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.biz.service.common.MailService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.dal.daointerface.CouncilDAO;
import com.born.fcs.pm.dal.dataobject.CouncilDO;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.order.council.CouncilQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.born.fcs.pm.ws.service.sms.SMSService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 每日一次的会议纪要填写通知
 * @author hjiajie
 * 
 */
@Service("endCouncilNoticeJob")
public class EndCouncilNoticeJob extends JobBase implements ProcessJobService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CouncilDAO councilDAO;
	
	@Autowired
	private BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	private SiteMessageService siteMessageService;
	
	@Autowired
	protected SysWebAccessTokenService sysWebAccessTokenService;
	
	@Autowired
	protected SysParameterService sysParameterService;
	
	@Autowired
	protected MailService mailService;
	
	@Autowired
	protected SMSService sMSService;
	
	// 每天的24点一次触发
	@Scheduled(cron = "0 0 0 * * ? ")
	//@Scheduled(cron = "0 0/1 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("处理会议记要填写通知开始在 " + DateUtil.simpleFormat(new Date()));
		try {
			/// 任务2.向风控委秘书发送站内信
			// 抓去需要发送站内信的会议
			CouncilQueryOrder councilQueryOrder = new CouncilQueryOrder();
			councilQueryOrder.setPageSize(10000L);
			
			List<CouncilDO> CouncilDOs = councilDAO.queryCouncilByCouncilSummary();
			if (ListUtil.isNotEmpty(CouncilDOs)) {
				for (CouncilDO councilDO : CouncilDOs) {
					
					// 尊敬的用户张三， （2015）第32期 会议已结束，快去填写会议纪要吧！
					// 获取风控委秘书
					String roleName = sysParameterService
						.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_SECRETARY_ROLE_NAME
							.code());
					
					List<SysUser> users = bpmUserQueryService.findUserByRoleAlias(roleName);
					for (SysUser user : users) {
						endCouncilNotice(councilDO, user);
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("处理会议记要填写通知任务异常---异常原因：" + e.getMessage(), e);
		}
		
		logger.info("处理会议记要填写通知任务结束在 " + DateUtil.simpleFormat(new Date()));
	}
	
	private void endCouncilNotice(CouncilDO council, SysUser sendUser) {
		//表单站内地址
		String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/meetingMg/summary/form.htm&councilId="
							+ council.getCouncilId();
		String forMessage = "<a href='" + messageUrl + "'>填写会议记要</a>";
		String subject = "会议结束";
		List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
		if (sendUser != null) {
			SimpleUserInfo userInfo = new SimpleUserInfo();
			userInfo.setUserId(sendUser.getUserId());
			userInfo.setUserName(sendUser.getFullname());
			userInfo.setUserAccount(sendUser.getAccount());
			userInfo.setEmail(sendUser.getEmail());
			userInfo.setMobile(sendUser.getMobile());
			notifyUserList.add(userInfo);
		}
		if (ListUtil.isNotEmpty(notifyUserList)) {
			
			SimpleUserInfo[] sendUsers = notifyUserList.toArray(new SimpleUserInfo[notifyUserList
				.size()]);
			//替换审核地址
			StringBuilder sb = new StringBuilder();
			sb.append("尊敬的用户");
			sb.append(sendUsers[0].getUserName());
			sb.append("， ");
			sb.append(council.getCouncilCode());
			sb.append(" 会议已结束，快去填写会议纪要吧！");
			String messageContent = sb.toString();
			messageContent += forMessage;
			if (StringUtil.isNotBlank(messageContent)) {
				MessageOrder ormessageOrderder = MessageOrder.newSystemMessageOrder();
				ormessageOrderder.setMessageSubject(subject);
				ormessageOrderder.setMessageTitle("填写会议记要通知");
				ormessageOrderder.setMessageContent(messageContent);
				ormessageOrderder.setSendUsers(sendUsers);
				siteMessageService.addMessageInfo(ormessageOrderder);
			}
			for (SimpleUserInfo userInfo : notifyUserList) {
				
				//发送邮件
				
				String accessToken = getAccessToken(userInfo);
				
				//站外审核地址
				String outSiteUrl = "<a href=\"" + getFaceWebUrl() + messageUrl + "&accessToken="
									+ accessToken + "\" target=\"_blank\">点击处理</a>";
				
				String mailContent = sb.toString();
				mailContent += outSiteUrl;
				if (StringUtil.isNotBlank(mailContent)) {
					//邮件地址
					List<String> mailAddress = Lists.newArrayList(userInfo.getEmail());
					SendMailOrder mailOrder = new SendMailOrder();
					mailOrder.setSendTo(mailAddress);
					mailOrder.setSubject(subject);
					mailOrder.setContent(mailContent);
					mailService.sendHtmlEmail(mailOrder);
				}
				if (StringUtil.isNotEmpty(userInfo.getMobile())) {
					
					String contentTxt = sb.toString();
					//发送短信
					sMSService.sendSMS(userInfo.getMobile(), contentTxt, null);
				}
			}
			
		}
	}
	
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
	
	/**
	 * 获取web访问地址
	 * @return
	 */
	protected String getFaceWebUrl() {
		String faceUrl = sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL.code());
		if (faceUrl != null && faceUrl.endsWith("/")) {
			faceUrl = faceUrl.substring(0, faceUrl.length() - 1);
		}
		return faceUrl;
	}
}
