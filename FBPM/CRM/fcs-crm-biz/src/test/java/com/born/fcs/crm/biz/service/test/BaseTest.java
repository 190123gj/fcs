package com.born.fcs.crm.biz.service.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/fcscrm-common-dal-dao.xml", "/spring/fcscrm-common-dal-db.xml",
						"/spring/fcscrm-ws-server.xml", "/spring/fcscrm-service.xml",
						"/spring/integration-pm-cxf.xml", "/spring/integration-risk-cxf.xml",
						"/spring/fcscrm-integration.xml" })
public class BaseTest {
	static {
		System.setProperty("spring.profiles.active", "test");
	}
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
