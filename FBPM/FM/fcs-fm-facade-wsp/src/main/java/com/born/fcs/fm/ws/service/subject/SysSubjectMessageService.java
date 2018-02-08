/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:18:29 创建
 */
package com.born.fcs.fm.ws.service.subject;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.subject.SysSubjectMessageInfo;
import com.born.fcs.fm.ws.order.subject.SysSubjectMessageBatchOrder;
import com.born.fcs.fm.ws.order.subject.SysSubjectMessageOrder;
import com.born.fcs.fm.ws.result.subject.SysSubjectMessageResult;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@WebService
public interface SysSubjectMessageService {
	
	/** 单查 */
	SysSubjectMessageResult findById(Long id);
	
	/** 单改 */
	FcsBaseResult update(SysSubjectMessageOrder order);
	
	/** 列查 */
	QueryBaseBatchResult<SysSubjectMessageInfo> querySubject(SysSubjectMessageOrder order);
	
	/** 列改 */
	FcsBaseResult updateAll(SysSubjectMessageBatchOrder order);
}
