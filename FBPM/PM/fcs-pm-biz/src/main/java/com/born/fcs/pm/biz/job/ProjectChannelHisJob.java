package com.born.fcs.pm.biz.job;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.project.asyn.ProjectChannelAsynService;
import com.born.fcs.pm.util.DateUtil;

@Service("projectChannelHisJob")
public class ProjectChannelHisJob extends JobBase {
	@Autowired
	private ProjectChannelAsynService projectChannelAsynService;
	
	/** 上次同步日期 */
	private static Date nextDate = null;
	
	@Scheduled(cron = "0 0 0,2,3 * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		try {
			
			Date now = new Date();
			Calendar nowCalendar = Calendar.getInstance();
			nowCalendar.setTime(now);
			nowCalendar.add(Calendar.HOUR_OF_DAY, -5);
			now = DateUtil.getStartTimeOfTheDate(nowCalendar.getTime());
			logger.info("渠道批量数据同步开始:project_date={}", now);
			if (nextDate != null) {
				long diff = now.getTime() - nextDate.getTime();
				long hours = diff / (1000 * 60 * 60);
				if (hours < 6) {
					logger.info("渠道批量数据同步结束,今天已同步过，不在重复处理:project_date={}", now);
					return;
				}
			}
			
			projectChannelAsynService.makeProjectChannelHisByDay(now);
			nextDate = now;
			logger.info("渠道批量数据同步结束");
		} catch (Exception e) {
			logger.error("渠道数据库备份出错:{}", e);
		}
	}
	
}