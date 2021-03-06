package com.born.fcs.pm.biz.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.daointerface.ProjectDAO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.expireproject.MessageAlertInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.expireproject.MessageAlertOrder;
import com.born.fcs.pm.ws.order.expireproject.MessageAlertQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.expireproject.MessageAlertService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 保后，等级评定消息提醒
 * 
 * @author lirz
 * 
 * 2016-7-29 上午11:27:50
 * 
 */
@Service("messageAlertJob")
public class MessageAlertJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProjectRelatedUserService projectRelatedUserService;
	@Autowired
	private MessageAlertService messageAlertService;
	@Autowired
	private SiteMessageService siteMessageService;//消息推送
	@Autowired
	private ProjectDAO projectDAO;
	
	@Scheduled(cron = "0 0 3 * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("消息提醒任务(MA)开始在 " + DateUtil.simpleFormat(new Date()));
		try {
			//解保后权利凭证出库提醒 
			sendCounterGuaranteeMessage();
			//保后报告提醒
			sendAfterWardsMessage();
			//风险等级评定表提醒消息
			sendRiskLevelMessage();
			
			/*
			Date now = new Date();
			String [] dates1 = {"0515", "0815", "1115", "0215"}; //1,2,3,4
			if (!DateUtil.isLastWorkDayOfMonth(now)) {
				logger.info("处理消息提醒任务，结束于： " + DateUtil.simpleFormat(new Date()) + "，不是最后一个工作日");
				return;
			}
			Calendar c = Calendar.getInstance();
			c.setTime(now);
			int month = c.get(Calendar.MONTH);
			month++;
			int size = 0;
			DateFormat df = new SimpleDateFormat("MMdd");
			String s = df.format(now);
//			if (month == 3 || month == 6 || month == 9 || month == 12) {
			MessageAlertQueryOrder queryOrder = new MessageAlertQueryOrder();
			queryOrder.setActive(BooleanEnum.YES.code());
			QueryBaseBatchResult<MessageAlertInfo> batchResult = messageAlertService
					.queryList(queryOrder);
			if (null != batchResult && ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (MessageAlertInfo ma : batchResult.getPageList()) {
					ProjectDO projectDO = projectDAO.findByProjectCode(ma.getProjectCode());
					if (ProjectPhasesEnum.FINISH_PHASES.code().equals(projectDO.getPhases())) {
						MessageAlertOrder order = new MessageAlertOrder();
						order.setAlertId(ma.getAlertId());
						messageAlertService.stopAlert(order);
						continue;
					}
					
					StringBuilder sb = new StringBuilder();
					if (ProjectPhasesEnum.AFTERWARDS_PHASES.code().equals(ma.getAlertPhrase())) {
						for (int i=0; i<dates1.length; i++) {
							if (StringUtil.equals(s, dates1[i])) {
								//一季度一次
								sb.append("请提交“项目编号").append(ma.getProjectCode()).append("”，客户名称")
								.append(ma.getCustomerName()).append("”第").append(i+1)
								.append("季度保后检查报告。");
								break;
							}
						}
					} else if ("RISK_LEVEL".equals(ma.getAlertPhrase())) {
						//半年一次 且是最后一个工作日
						if ((month == 6 || month == 12) && DateUtil.isLastWorkDayOfMonth(now)) {
							sb.append("请提交“项目编号").append(ma.getProjectCode()).append("”，客户名称")
							.append(ma.getCustomerName()).append("”风险项目评定表。");
						}
					}
					
					if (StringUtil.isNotBlank(sb.toString())) {
						//客户经理
						ProjectRelatedUserInfo userInfo = projectRelatedUserService
								.getBusiManager(ma.getProjectCode());
						if (userInfo != null) {
							SimpleUserInfo[] users = new SimpleUserInfo[1];
							users[0] = userInfo.toSimpleUserInfo();
							MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
							messageOrder.setMessageContent(sb.toString());
							messageOrder.setSendUsers(users);
							siteMessageService.addMessageInfo(messageOrder);
							size++;
							MessageAlertOrder order = new MessageAlertOrder();
							order.setAlertId(ma.getAlertId());
							messageAlertService.updateSend(order);
						} else {
							logger.info("处理消息提醒任务，没有找到对应的客户经理： " + ma.getProjectCode() + ","
									+ ma.getAlertPhrase());
						}
					}
				}
			}
//			}
			*/
			logger.info("处理消息提醒任务完成(MA)。");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("处理消息提醒任务---异常(MA)原因：" + e.getMessage(), e);
		}
	}
	
	private void sendAfterWardsMessage() {
		Date now = new Date();
		String [] dates = {"0515", "0815", "1115", "0215"}; //1,2,3,4 提醒日期
		DateFormat df = new SimpleDateFormat("MMdd");
		String s = df.format(now);
		boolean send  = false;
		int index = 0;
		for (int i=0; i<dates.length; i++) {
			if (StringUtil.equals(s, dates[i])) {
				send  = true;
				index = i + 1;
				break;
			}
		}
		
		if (!send) {
			return;
		}
		
		MessageAlertQueryOrder queryOrder = new MessageAlertQueryOrder();
		queryOrder.setActive(BooleanEnum.YES.code());
		queryOrder.setAlertPhrase(ProjectPhasesEnum.AFTERWARDS_PHASES.code());
		QueryBaseBatchResult<MessageAlertInfo> batchResult = messageAlertService
			.queryList(queryOrder);
		if (null == batchResult || ListUtil.isEmpty(batchResult.getPageList())) {
			return;
		}
		
		for (MessageAlertInfo ma : batchResult.getPageList()) {
			ProjectDO projectDO = projectDAO.findByProjectCode(ma.getProjectCode());
			if (null == projectDO) {
				logger.info("未找到对应的项目：" + ma.getProjectCode());
				continue;
			}
			//客户经理
			ProjectRelatedUserInfo userInfo = projectRelatedUserService.getBusiManager(ma
				.getProjectCode());
			if (null == userInfo) {
				logger.info("未找到对应的客户经理：" + ma.getProjectCode());
				continue;
			}
			
			StringBuilder sb = new StringBuilder();
			//一季度一次
			sb.append("请提交“项目编号").append(ma.getProjectCode()).append("”，客户名称")
				.append(ma.getCustomerName()).append("”第").append(index).append("季度保后检查报告。");
			
			sendMsg(userInfo.toSimpleUserInfo(), sb.toString(), ma.getAlertId());
		}
	}
	
	private void sendRiskLevelMessage() {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		int month = c.get(Calendar.MONTH);
		month++;
		if (month != 6 && month != 12) {
			return;
		}
		
		if (!DateUtil.isLastWorkDayOfMonth(now)) {
			logger.info("处理消息提醒任务(风险项目评定表)，结束于： " + DateUtil.simpleFormat(new Date())
						+ "，不是最后一个工作日");
			return;
		}
		
		MessageAlertQueryOrder queryOrder = new MessageAlertQueryOrder();
		queryOrder.setActive(BooleanEnum.YES.code());
		queryOrder.setAlertPhrase("RISK_LEVEL");
		QueryBaseBatchResult<MessageAlertInfo> batchResult = messageAlertService
			.queryList(queryOrder);
		if (null == batchResult || ListUtil.isEmpty(batchResult.getPageList())) {
			return;
		}
		
		for (MessageAlertInfo ma : batchResult.getPageList()) {
			ProjectDO projectDO = projectDAO.findByProjectCode(ma.getProjectCode());
			if (null == projectDO) {
				logger.info("未找到对应的项目：" + ma.getProjectCode());
				continue;
			}
			//客户经理
			ProjectRelatedUserInfo userInfo = projectRelatedUserService.getBusiManager(ma
				.getProjectCode());
			if (null == userInfo) {
				logger.info("未找到对应的客户经理：" + ma.getProjectCode());
				continue;
			}
			
			StringBuilder sb = new StringBuilder();
			//半年一次
			sb.append("请提交“项目编号").append(ma.getProjectCode()).append("”，客户名称")
				.append(ma.getCustomerName()).append("”风险项目评定表。");
			
			sendMsg(userInfo.toSimpleUserInfo(), sb.toString(), ma.getAlertId());
		}
	}
	
	private void sendCounterGuaranteeMessage() {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (day != 1 && day != 15) {
			//每半月只有1号和15号发送消息
			return;
		}
		MessageAlertQueryOrder queryOrder = new MessageAlertQueryOrder();
		queryOrder.setActive(BooleanEnum.YES.code());
		queryOrder.setAlertPhrase("COUNTER_GUARANTEE");
		QueryBaseBatchResult<MessageAlertInfo> batchResult = messageAlertService
			.queryList(queryOrder);
		if (null == batchResult || ListUtil.isEmpty(batchResult.getPageList())) {
			return;
		}
		
		for (MessageAlertInfo ma : batchResult.getPageList()) {
			ProjectDO projectDO = projectDAO.findByProjectCode(ma.getProjectCode());
			if (null == projectDO) {
				logger.info("未找到对应的项目：" + ma.getProjectCode());
				continue;
			}
			//客户经理
			ProjectRelatedUserInfo userInfo = projectRelatedUserService.getBusiManager(ma
				.getProjectCode());
			if (null == userInfo) {
				logger.info("未找到对应的客户经理：" + ma.getProjectCode());
				continue;
			}
			
			StringBuilder sb = new StringBuilder();
			String url = CommonUtil.getRedirectUrl("/projectMg/file/detailFileList.htm");
			sb.append(projectDO.getProjectName()).append("(").append(projectDO.getProjectCode())
				.append(")").append("，该项目的解保申请单审核通过，是否需要权利凭证出库？").append("<a href='").append(url)
				.append("'>点击处理</a>");
			
			sendMsg(userInfo.toSimpleUserInfo(), sb.toString(), ma.getAlertId());
		}
	}
	
	private void sendMsg(SimpleUserInfo user, String msg, long maId) {
		SimpleUserInfo[] users = new SimpleUserInfo[1];
		users[0] = user;
		MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
		messageOrder.setMessageContent(msg);
		messageOrder.setSendUsers(users);
		siteMessageService.addMessageInfo(messageOrder);
		
		MessageAlertOrder order = new MessageAlertOrder();
		order.setAlertId(maId);
		messageAlertService.updateSend(order);
	}
	
}
