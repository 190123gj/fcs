/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午4:36:38 创建
 */
package com.born.fcs.pm.biz.job;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.dal.daointerface.FInternalOpinionExchangeUserDAO;
import com.born.fcs.pm.dal.dataobject.FInternalOpinionExchangeUserDO;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.InternalOpinionExTypeEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.FormService;
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
@Service("internalOpinionNotifyJob")
public class InternalOpinionNotifyJob extends JobBase implements ProcessJobService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FInternalOpinionExchangeUserDAO FInternalOpinionExchangeUserDAO;
	
	@Autowired
	private SiteMessageService siteMessageService;
	
	@Autowired
	private FormService formService;
	
	//反馈时间格式都是 yyyy-MM-dd
	//所以每天凌晨0点到1点开始跑一遍就可以了
	@Scheduled(cron = "0 0/5 0-1 * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		try {
			List<FInternalOpinionExchangeUserDO> userList = FInternalOpinionExchangeUserDAO
				.findNeedNofity(20, 1);
			logger.info("扫描到还未填写反馈意见的审计人员数量 : {}", userList == null ? 0 : userList.size());
			if (ListUtil.isNotEmpty(userList)) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
				
				for (FInternalOpinionExchangeUserDO user : userList) {
					
					logger.info("扫描到还未填写反馈意见的审计人员  : {}", user);
					
					FormInfo form = formService.findByFormId(user.getFormId());
					
					if (form != null) {
						
						//反馈的类型
						InternalOpinionExTypeEnum exType = InternalOpinionExTypeEnum.getByCode(user
							.getExType());
						
						//单据地址
						String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/internalOpinionExchange/view.htm&formId="
											+ form.getFormId();
						
						//开始通知
						String messageContent = "由内审人员发起的" + exType.message()
												+ "内审意见交换流程，据要求反馈意见时间仅剩一天，请在"
												+ sdf.format(user.getFeedbackTime())
												+ "前反馈意见，<a href='" + messageUrl + "'>查看单据</a>";
						
						messageOrder.setMessageTitle(exType.message() + "反馈意见填写提醒");
						messageOrder.setMessageSubject(messageOrder.getMessageTitle());
						messageOrder.setMessageContent(messageContent);
						SimpleUserInfo simpleUser = new SimpleUserInfo();
						BeanCopier.staticCopy(user, simpleUser);
						messageOrder.setSendUsers(new SimpleUserInfo[] { simpleUser });
						FcsBaseResult result = siteMessageService.addMessageInfo(messageOrder);
						logger.info("通知还未填写反馈意见的审计人员  : {}", result);
						if (result.isSuccess()) {
							user.setIsNotified(BooleanEnum.IS.code());
							FInternalOpinionExchangeUserDAO.update(user);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.info("通知还未填写反馈意见的审计人员任务异常 {} ", e);
		}
		
	}
}
