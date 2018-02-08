package com.born.fcs.crm.ws.service;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.info.VerifyMessageInfo;
import com.born.fcs.crm.ws.service.order.VerifyMessageOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 客户校验结果保存
 * */
@WebService
public interface VerifyMessageSaveService {
	/** 保存信息 */
	FcsBaseResult save(VerifyMessageOrder order);
	
	/** 查询信息 */
	VerifyMessageInfo queryById(String errorKey);
}
