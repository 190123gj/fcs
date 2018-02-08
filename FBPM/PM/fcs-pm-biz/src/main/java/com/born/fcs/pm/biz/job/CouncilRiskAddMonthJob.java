package com.born.fcs.pm.biz.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.service.councilRisk.CouncilRiskService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("councilRiskAddMonthJob")
public class CouncilRiskAddMonthJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	CouncilRiskService councilRiskService;
	@Scheduled(cron = "0 0 9 26,27,28,29,30,31 * ? ")
	@Override
	public synchronized void doJob() throws Exception {
		if (!isRun)
			return;
		try {
			councilRiskService.addNoticeMonth();
		} catch (Exception e) {
			logger.error("成立风险处置小组跟踪月报 {}", e);
		}
		logger.info("成立风险处置小组跟踪月报结束在{} ", DateUtil.simpleFormat(new Date()));
	}
}
