/**
 * www.yiji.com Inc.
 * Copyright (c) 2013 All Rights Reserved.
 */
package com.bornsoft.jck.dal.daointerface;

import com.bornsoft.jck.dal.dataobject.AppLoginInfoDO;


public interface AppLoginInfoDAO extends AppLoginInfoDAOAbstract{

	/**
	 * 查询用户登录记录
	 * @param userName
	 * @return
	 */
	AppLoginInfoDO queryLoginInfo(String userName);
	
}