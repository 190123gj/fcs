package com.born.fcs.pm.biz.job;

import java.util.Date;

import com.born.fcs.pm.ws.service.councilRisk.CouncilRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.util.DateUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("councilRiskJob")
public class CouncilRiskJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	CouncilRiskService councilRiskService;
	@Scheduled(cron = "0 0 9 20,21,22,23,24,25 * ? ")
	@Override
	public synchronized void doJob() throws Exception {
		if (!isRun)
			return;
		try {
			councilRiskService.noticeMonth();
		} catch (Exception e) {
			logger.error("成立风险处置小组开月会 {}", e);
		}
		logger.info("成立风险处置小组开月会结束在{} ", DateUtil.simpleFormat(new Date()));
	}
}
