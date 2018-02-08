package com.born.fcs.fm.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * @Filename SeviceTestBase.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-7-5</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/fcsfm-common-dal-dao.xml", "/spring/fcsfm-common-dal-db.xml",
						"/spring/integration-cxf.xml", "/spring/fcsfm-service.xml" })
public class SeviceTestBase {
	static {
		System.setProperty("spring.profiles.active", "dev");
	}
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
