/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.rm.biz.service.base;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.rm.dal.daointerface.AccountBalanceDAO;
import com.born.fcs.rm.dal.daointerface.AccountBalanceDataDAO;
import com.born.fcs.rm.dal.daointerface.ExtraDAO;
import com.born.fcs.rm.dal.daointerface.ProjectBaseDAO;
import com.born.fcs.rm.dal.daointerface.ProjectCustomerDAO;
import com.born.fcs.rm.dal.daointerface.ProjectItemDAO;
import com.born.fcs.rm.dal.daointerface.ReportDAO;
import com.born.fcs.rm.dal.daointerface.ReportRuleDAO;
import com.born.fcs.rm.dal.daointerface.ReportSqlDao;
import com.born.fcs.rm.dal.daointerface.SubmissionDAO;
import com.born.fcs.rm.dal.daointerface.SubmissionDataDAO;
import com.born.fcs.rm.dal.daointerface.TestDAO;
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
	protected TestDAO testDAO;
	@Autowired
	protected SubmissionDAO submissionDAO;
	@Autowired
	protected SubmissionDataDAO submissionDataDAO;
	@Autowired
	protected AccountBalanceDAO accountBalanceDAO;
	@Autowired
	protected AccountBalanceDataDAO accountBalanceDataDAO;
	@Autowired
	protected ReportDAO reportDAO;
	@Autowired
	protected ProjectCustomerDAO projectCustomerDAO;
	@Autowired
	protected ProjectBaseDAO projectBaesDAO;
	@Autowired
	protected ProjectItemDAO projectItemDAO;
	@Autowired
	protected ReportRuleDAO reportRuleDao;
	@Autowired
	protected ReportSqlDao reportSqlDao;
	
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
