/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.biz.service.base;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.am.dal.daointerface.AssessCompanyBusinessScopeDAO;
import com.born.fcs.am.dal.daointerface.AssessCompanyContactDAO;
import com.born.fcs.am.dal.daointerface.AssessCompanyEvaluateDAO;
import com.born.fcs.am.dal.daointerface.AssetRelationProjectDAO;
import com.born.fcs.am.dal.daointerface.AssetsAssessCompanyDAO;
import com.born.fcs.am.dal.daointerface.ExtraDAO;
import com.born.fcs.am.dal.daointerface.FAssessCompanyApplyDAO;
import com.born.fcs.am.dal.daointerface.FAssessCompanyApplyItemDAO;
import com.born.fcs.am.dal.daointerface.FAssetsTransferApplicationDAO;
import com.born.fcs.am.dal.daointerface.FAssetsTransfereeApplicationDAO;
import com.born.fcs.am.dal.daointerface.FormDAO;
import com.born.fcs.am.dal.daointerface.FormRelatedUserDAO;
import com.born.fcs.am.dal.daointerface.PledgeAssetDAO;
import com.born.fcs.am.dal.daointerface.PledgeImageCustomDAO;
import com.born.fcs.am.dal.daointerface.PledgeNetworkCustomDAO;
import com.born.fcs.am.dal.daointerface.PledgeTextCustomDAO;
import com.born.fcs.am.dal.daointerface.PledgeTypeAttributeDAO;
import com.born.fcs.am.dal.daointerface.PledgeTypeCommonDAO;
import com.born.fcs.am.dal.daointerface.PledgeTypeDAO;
import com.born.fcs.am.dal.daointerface.ProjectRelatedUserDAO;
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
	protected FormRelatedUserDAO formRelatedUserDAO;
	@Autowired
	protected ProjectRelatedUserDAO projectRelatedUserDAO;
	@Autowired
	protected ExtraDAO extraDAO;
	@Autowired
	protected FormDAO formDAO;
	@Autowired
	protected PledgeTypeDAO pledgeTypeDAO;
	@Autowired
	protected PledgeTextCustomDAO pledgeTextCustomDAO;
	@Autowired
	protected PledgeImageCustomDAO pledgeImageCustomDAO;
	@Autowired
	protected PledgeNetworkCustomDAO pledgeNetworkCustomDAO;
	// 资产
	@Autowired
	protected PledgeAssetDAO pledgeAssetDAO;
	@Autowired
	protected PledgeTypeAttributeDAO pledgeTypeAttributeDAO;
	@Autowired
	protected PledgeTypeCommonDAO pledgeTypeCommonDAO;
	
	@Autowired
	protected AssetRelationProjectDAO assetRelationProjectDAO;
	
	@Autowired
	protected FAssetsTransferApplicationDAO FAssetsTransferApplicationDAO;
	@Autowired
	protected FAssetsTransfereeApplicationDAO FAssetsTransfereeApplicationDAO;
	// 评估公司
	@Autowired
	protected AssetsAssessCompanyDAO assetsAssessCompanyDAO;
	@Autowired
	protected AssessCompanyContactDAO assessCompanyContactDAO;
	@Autowired
	protected AssessCompanyBusinessScopeDAO assessCompanyBusinessScopeDAO;
	
	@Autowired
	protected FAssessCompanyApplyDAO FAssessCompanyApplyDAO;
	
	@Autowired
	protected AssessCompanyEvaluateDAO assessCompanyEvaluateDAO;
	@Autowired
	protected FAssessCompanyApplyItemDAO FAssessCompanyApplyItemDAO;
	
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
