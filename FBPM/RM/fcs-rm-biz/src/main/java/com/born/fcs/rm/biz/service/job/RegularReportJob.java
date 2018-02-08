package com.born.fcs.rm.biz.service.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.rm.ws.service.report.ReportService;

@Service("regularReportJob")
public class RegularReportJob extends JobBase {
	
	@Autowired
	ReportService reportService;
	
	@Scheduled(cron = "0 0 2 * * ? ")
	//@Scheduled(cron = "0 0/1 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		//logger.info("run.........");
		reportService.runRegularReport(null);
	}
}
