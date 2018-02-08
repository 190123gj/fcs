package com.born.fcs.pm.biz.job;

import java.util.Date;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.JobBase;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.service.councilRisk.CouncilRiskService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("councilRiskBeginJob")
public class CouncilRiskBeginJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	CouncilRiskService councilRiskService;

	@Scheduled(cron = "0 0/1 * * * ? ")
	@Override
	public synchronized  void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("处理风险管控小组会议相关任务开始在 " + DateUtil.simpleFormat(new Date()));
		try {
			councilRiskService.startCouncilRisk();
		} catch (Exception e) {
			logger.error("处理风险管控小组会议相关任务异常---异常原因：" + e.getMessage(), e);
		}

		logger.info("处理风险管控小组会议相关任务结束在 " + DateUtil.simpleFormat(new Date()));
	}

}
