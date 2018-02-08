/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.crm.biz.service.base;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.crm.dal.daointerface.ExtraDAO;
import com.born.fcs.crm.dal.daointerface.FormDAO;
import com.born.fcs.crm.dal.daointerface.FormRelatedUserDAO;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * @Filename BaseAutowiredDAOService
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-8</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class BaseAutowiredDAOService {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected ExtraDAO extraDAO;
	
	@Autowired
	protected FormDAO formDAO;
	
	@Autowired
	protected FormRelatedUserDAO formRelatedUserDAO;
	
	
	/**
	 * @return Date
	 */
	protected Date getSysdate() {
		try {
			Date sysDate = extraDAO.getSysdate();
			logger.info("系统时间：sysDate=" + sysDate);
			return sysDate;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return new Date();
	}
	
}
