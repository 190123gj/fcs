/**
 * www.yiji.com Inc.
 * Copyright (c) 2013 All Rights Reserved.
 */
package com.bornsoft.jck.dal.daointerface;

import java.util.List;

import com.bornsoft.jck.dal.dataobject.MobileInfoDO;


public interface MobileInfoDAO extends MobileInfoDAOAbstract{

	List<MobileInfoDO> queryMobileInfoList(MobileInfoDO realInfo);
	
}