/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:34:03 创建
 */
package com.born.fcs.face.integration.fm.service.subject;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.info.subject.SysSubjectMessageInfo;
import com.born.fcs.fm.ws.order.subject.SysSubjectMessageBatchOrder;
import com.born.fcs.fm.ws.order.subject.SysSubjectMessageOrder;
import com.born.fcs.fm.ws.result.subject.SysSubjectMessageResult;
import com.born.fcs.fm.ws.service.subject.SysSubjectMessageService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Service("sysSubjectMessageServiceClient")
public class SysSubjectMessageServiceClient extends ClientAutowiredBaseService implements
																				SysSubjectMessageService {
	
	/**
	 * @param id
	 * @return
	 * @see com.born.fcs.fm.ws.service.subject.SysSubjectMessageService#findById(java.lang.Long)
	 */
	@Override
	public SysSubjectMessageResult findById(final Long id) {
		return callInterface(new CallExternalInterface<SysSubjectMessageResult>() {
			@Override
			public SysSubjectMessageResult call() {
				return sysSubjectMessageWebService.findById(id);
			}
		});
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.subject.SysSubjectMessageService#update(com.born.fcs.fm.ws.order.subject.SysSubjectMessageOrder)
	 */
	@Override
	public FcsBaseResult update(final SysSubjectMessageOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return sysSubjectMessageWebService.update(order);
			}
		});
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.subject.SysSubjectMessageService#querySubject(com.born.fcs.fm.ws.order.subject.SysSubjectMessageOrder)
	 */
	@Override
	public QueryBaseBatchResult<SysSubjectMessageInfo> querySubject(final SysSubjectMessageOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<SysSubjectMessageInfo>>() {
			@Override
			public QueryBaseBatchResult<SysSubjectMessageInfo> call() {
				return sysSubjectMessageWebService.querySubject(order);
			}
		});
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.subject.SysSubjectMessageService#update(com.born.fcs.fm.ws.order.subject.SysSubjectMessageBatchOrder)
	 */
	@Override
	public FcsBaseResult updateAll(final SysSubjectMessageBatchOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return sysSubjectMessageWebService.updateAll(order);
			}
		});
	}
	
}
