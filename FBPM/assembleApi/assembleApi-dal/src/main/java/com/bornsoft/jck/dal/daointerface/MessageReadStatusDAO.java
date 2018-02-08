/**
 * www.yiji.com Inc.
 * Copyright (c) 2013 All Rights Reserved.
 */
package com.bornsoft.jck.dal.daointerface;

import java.util.List;

import com.bornsoft.jck.dal.dataobject.MessageReadStatusDO;


public interface MessageReadStatusDAO extends MessageReadStatusDAOAbstract{

	List<MessageReadStatusDO> queryByUserName(String userName);
	
}