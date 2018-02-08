package com.born.fcs.crm.biz.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.job.inter.ProcessJobService;
import com.born.fcs.crm.biz.message.RemindMessageService;
import com.born.fcs.pm.util.DateUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("channalRemindJob")
public class ChannalRemindJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected RemindMessageService remindMessageService;
	
	// 每天凌晨两点执行
	@Scheduled(cron = "0 0 2 * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("客户管理：渠道授信两个月类到期提醒开始： " + DateUtil.simpleFormat(new Date()));
		try {
			remindMessageService.sendMessage();
		} catch (Exception e) {
			
		}
		logger.info("客户管理：渠道授信两个月类到期提醒结束： " + DateUtil.simpleFormat(new Date()));
	}
}
