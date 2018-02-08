/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午11:09:18 创建
 */
package com.born.fcs.pm.biz.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.dal.daointerface.ProjectRecoveryDAO;
import com.born.fcs.pm.dal.daointerface.ProjectRecoveryLitigationBeforePreservationPrecautionDAO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationBeforePreservationPrecautionDO;
import com.born.fcs.pm.daointerface.ExtraDAO;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Service("litigationBeforePreservationPrecautionJob")
public class LitigationBeforePreservationPrecautionJob extends noticeJobBase implements
																			ProcessJobService {
	
	@Autowired
	private ProjectRecoveryLitigationBeforePreservationPrecautionDAO precautionDAO;
	
	@Autowired
	private ExtraDAO extraDAO;
	
	@Autowired
	private ProjectRecoveryDAO projectRecoveryDAO;
	
	@Autowired
	private ProjectService projectService;
	
	// 每天的24点07分一次触发
	@Scheduled(cron = "0 7 0 * * ? ")
	//	@Scheduled(cron = "0 0/5 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("处理保全措施通知任务开始在 " + DateUtil.simpleFormat(new Date()));
		try {
			Date now = extraDAO.getSysdate();
			// 优先执行 30天需要打开通知进行短信轰炸的
			thirdtyDayNotice(now);
			
			// 保全措施结束45天内通知
			int limitDays = 45;
			
			Calendar nowc = Calendar.getInstance();
			nowc.setTime(now);
			nowc.add(Calendar.DAY_OF_YEAR, limitDays);
			Date endDate = nowc.getTime();
			now = DateUtil.getStartTimeOfTheDate(now);
			endDate = DateUtil.getEndTimeOfTheDate(endDate);
			
			// 查询所有需要发送的数据
			List<ProjectRecoveryLitigationBeforePreservationPrecautionDO> infoDOs = precautionDAO
				.findNoticeByEndTime(now, endDate);
			// 发送数据
			for (ProjectRecoveryLitigationBeforePreservationPrecautionDO infoDO : infoDOs) {
				//  保全措施结束45天内，向该项目的法务经理发送系统消息，
				//项目编号xxxx项目名称xxxx,该项目提交的诉前保全将于xxxx-xx-xx到期，+点击关闭该消息通知
				
				// 查询该项目的追偿法务经理
				//				List<SysUser> users = new ArrayList<SysUser>();
				
				// 用鹳狸猿做测试
				//				String roleName = "BusinessSys_admin";
				//				
				//				List<SysUser> users = bpmUserQueryService.findUserByRoleAlias(roleName);
				//				for (SysUser user : users) {
				//					//sendNotice(infoDO, user);
				//				}
				ProjectRecoveryDO recoveryDO = projectRecoveryDAO.findById(infoDO
					.getProjectRecoveryId());
				if (recoveryDO == null) {
					logger.error("未查询到子Id为" + infoDO.getId() + "的追偿数据！");
					continue;
				}
				String projectCode = recoveryDO.getProjectCode();
				ProjectRelatedUserQueryOrder relatedOrder = new ProjectRelatedUserQueryOrder();
				relatedOrder.setProjectCode(projectCode);
				relatedOrder.setUserType(ProjectRelatedUserTypeEnum.LEGAL_MANAGER);
				QueryBaseBatchResult<ProjectRelatedUserInfo> relatedResult = projectRelatedUserService
					.queryPage(relatedOrder);
				
				List<Long> userIds = new ArrayList<Long>();
				if (relatedResult != null && ListUtil.isNotEmpty(relatedResult.getPageList())) {
					for (ProjectRelatedUserInfo relatedInfo : relatedResult.getPageList()) {
						userIds.add(relatedInfo.getUserId());
					}
				}
				for (Long userId : userIds) {
					sendNotice(recoveryDO, infoDO, userId);
				}
				
			}
		} catch (Exception e) {
			logger.error("处理开庭通知任务异常---异常原因：" + e.getMessage(), e);
		}
		
	}
	
	private void thirdtyDayNotice(Date now) {
		
		// 代表30天时重新发起
		// 设置时间，开始结束时间分别是今天往前推30天的0点和24点
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, 30);
		Date thirdtyDaysBrfore = cal.getTime();
		Date startDate = DateUtil.getStartTimeOfTheDate(thirdtyDaysBrfore);
		Date endDate = DateUtil.getEndTimeOfTheDate(thirdtyDaysBrfore);
		precautionDAO.updateEndNoticeLimit(startDate, endDate);
	}
	
	private void sendNotice(ProjectRecoveryDO recoveryDO,
							ProjectRecoveryLitigationBeforePreservationPrecautionDO info,
							Long userId) {
		//表单站内地址
		if (info == null) {
			return;
		}
		ProjectInfo project = projectService.queryByCode(recoveryDO.getProjectCode(), false);
		if (project == null) {
			return;
		}
		String messageUrlClean = "/projectMg/recovery/endPrecautionNotice.htm?id=" + info.getId();
		String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/recovery/endPrecautionNotice.htm?id="
							+ info.getId();
		String forMessage = "<a  href='javascript:void(0);' class='fnDoAjax' ajaxurl='"
							+ messageUrlClean + "'>点击关闭该消息通知</a>";
		String subject = "到期预告";
		List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
		SysUser sendUser = null;
		sendUser = bpmUserQueryService.findUserByUserId(userId);
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
			//项目编号xxxx项目名称xxxx,该项目提交的诉前保全将于xxxx-xx-xx到期+点击关闭该消息通知
			StringBuilder sb = new StringBuilder();
			sb.append("项目编号 ");
			sb.append(project.getProjectCode());
			sb.append("，项目名称 ");
			sb.append(project.getProjectName());
			sb.append(" ，该项目提交的诉前保全将于");
			sb.append(DateUtil.formatDay(info.getPreservationTimeEnd()));
			sb.append(" 到期。");
			String messageContent = sb.toString();
			messageContent += forMessage;
			if (StringUtil.isNotBlank(messageContent)) {
				MessageOrder ormessageOrderder = MessageOrder.newSystemMessageOrder();
				ormessageOrderder.setMessageSubject(subject);
				ormessageOrderder.setMessageTitle("保全措施到期提醒");
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
}
