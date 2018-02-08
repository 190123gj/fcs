package com.born.fcs.pm.biz.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.project.asyn.ProjectDataAsynchronousService;

@Service("projectDataAsynchronousJob")
public class ProjectDataAsynchronousJob extends JobBase {
	@Autowired
	private ProjectDataAsynchronousService projectDataAsynchronousService;
	
	@Scheduled(cron = "0 0 0,2,4 * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		try {
			logger.info("批量数据同步开始");
			projectDataAsynchronousService.makeProjectDataByDay();
			logger.info("批量数据同步结束");
		} catch (Exception e) {
			logger.error("数据库备份出错:{}", e);
		}
	}
}
