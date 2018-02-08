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
import com.born.fcs.rm.dal.daointerface.RegularCustomerBaseInfoDAO;
import com.born.fcs.rm.dal.daointerface.RegularCustomerBaseInfoMonthDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectBaseInfoDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectBaseInfoMonthDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectChannelInfoDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectChannelInfoMonthDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectExtraListInfoDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectExtraListInfoMonthDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectImpelInfoDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectImpelInfoMonthDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectIncomeInfoDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectIncomeInfoMonthDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectRiskInfoDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectRiskInfoMonthDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectRunInfoDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectRunInfoMonthDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectStoreInfoDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectStoreInfoMonthDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectSubBusiTypeInfoDAO;
import com.born.fcs.rm.dal.daointerface.RegularProjectSubBusiTypeInfoMonthDAO;
import com.born.fcs.rm.dal.daointerface.ReportDAO;
import com.born.fcs.rm.dal.daointerface.ReportRuleDAO;
import com.born.fcs.rm.dal.daointerface.ReportSqlDao;
import com.born.fcs.rm.dal.daointerface.SubmissionDAO;
import com.born.fcs.rm.dal.daointerface.SubmissionDataDAO;
import com.born.fcs.rm.dal.daointerface.TestDAO;
import com.born.fcs.rm.dal.daointerface.handwriting.RegularReportDAO;
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
	
	@Autowired
	protected RegularReportDAO regularReportDAO;
	
	@Autowired
	protected RegularCustomerBaseInfoDAO regularCustomerBaseInfoDAO;
	
	@Autowired
	protected RegularProjectBaseInfoDAO regularProjectBaseInfoDAO;
	
	@Autowired
	protected RegularProjectImpelInfoDAO regularProjectImpelInfoDAO;
	
	@Autowired
	protected RegularProjectStoreInfoDAO regularProjectStoreInfoDAO;
	
	@Autowired
	protected RegularProjectRunInfoDAO regularProjectRunInfoDAO;
	
	@Autowired
	protected RegularProjectIncomeInfoDAO regularProjectIncomeInfoDAO;
	
	@Autowired
	protected RegularProjectRiskInfoDAO regularProjectRiskInfoDAO;
	
	@Autowired
	protected RegularCustomerBaseInfoMonthDAO regularCustomerBaseInfoMonthDAO;
	
	@Autowired
	protected RegularProjectBaseInfoMonthDAO regularProjectBaseInfoMonthDAO;
	
	@Autowired
	protected RegularProjectImpelInfoMonthDAO regularProjectImpelInfoMonthDAO;
	
	@Autowired
	protected RegularProjectStoreInfoMonthDAO regularProjectStoreInfoMonthDAO;
	
	@Autowired
	protected RegularProjectRunInfoMonthDAO regularProjectRunInfoMonthDAO;
	
	@Autowired
	protected RegularProjectIncomeInfoMonthDAO regularProjectIncomeInfoMonthDAO;
	
	@Autowired
	protected RegularProjectRiskInfoMonthDAO regularProjectRiskInfoMonthDAO;
	
	@Autowired
	protected RegularProjectExtraListInfoDAO regularProjectExtraListInfoDAO;
	
	@Autowired
	protected RegularProjectExtraListInfoMonthDAO regularProjectExtraListInfoMonthDAO;
	
	@Autowired
	protected RegularProjectChannelInfoDAO regularProjectChannelInfoDAO;
	
	@Autowired
	protected RegularProjectChannelInfoMonthDAO regularProjectChannelInfoMonthDAO;
	
	@Autowired
	protected RegularProjectSubBusiTypeInfoDAO regularProjectSubBusiTypeInfoDAO;
	
	@Autowired
	protected RegularProjectSubBusiTypeInfoMonthDAO regularProjectSubBusiTypeInfoMonthDAO;
	
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
