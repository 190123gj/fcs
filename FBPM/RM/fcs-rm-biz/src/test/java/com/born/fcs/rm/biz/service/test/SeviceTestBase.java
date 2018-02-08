package com.born.fcs.rm.biz.service.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/fcsrm-common-dal-dao.xml", "/spring/fcsrm-common-dal-db.xml",
						"/spring/integration-cxf.xml", "/spring/fcsrm-service.xml","/spring/integration-pm-cxf.xml"})
public class SeviceTestBase {
	static {
		System.setProperty("spring.profiles.active", "dev");
	}
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
