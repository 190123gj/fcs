package com.born.fcs.rm.biz.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.rm.biz.service.test.SeviceTestBase;
import com.born.fcs.rm.ws.service.report.ReportService;

public class TestRunRegularReport extends SeviceTestBase {
	
	@Autowired
	ReportService reportService;
	
	@Test
	public void testQueryProject() throws InterruptedException {
		logger.info("=============== {} ", DateUtil.simpleFormat(new Date()));
		reportService.runRegularReport("2017-05-31");
		logger.info("=============== {} ", DateUtil.simpleFormat(new Date()));
	}
}
