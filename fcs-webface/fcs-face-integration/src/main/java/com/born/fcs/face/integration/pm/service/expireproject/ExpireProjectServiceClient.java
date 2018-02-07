package com.born.fcs.face.integration.pm.service.expireproject;

import java.net.SocketTimeoutException;

import com.born.fcs.pm.ws.info.expireproject.ExpireProjectNoticeTemplateInfo;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectNoticeTemplateOrder;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.expireproject.ExpireFormProjectInfo;
import com.born.fcs.pm.ws.info.expireproject.ExpireProjectInfo;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectOrder;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.expireproject.ExpireProjectService;

/**
 * 
 * 过期逾期项目
 * 
 * @author lirz
 * 
 * 2016-4-14 下午3:26:13
 */
@Service("expireProjectServiceClient")
public class ExpireProjectServiceClient extends ClientAutowiredBaseService implements
																			ExpireProjectService {
	
	@Override
	public FcsBaseResult add(final ExpireProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return expireProjectWebService.add(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateToOverdue(final ExpireProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return expireProjectWebService.updateToOverdue(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateToDone(final ExpireProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return expireProjectWebService.updateToDone(order);
			}
		});
	}
	
	@Override
	public ExpireProjectInfo queryExpireProjectByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<ExpireProjectInfo>() {
			
			@Override
			public ExpireProjectInfo call() throws SocketTimeoutException {
				return expireProjectWebService.queryExpireProjectByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ExpireProjectInfo> queryExpireProjectInfo(	final ExpireProjectQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ExpireProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ExpireProjectInfo> call() throws SocketTimeoutException {
				return expireProjectWebService.queryExpireProjectInfo(queryOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateToExpire(final ExpireProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return expireProjectWebService.updateToExpire(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ExpireFormProjectInfo> queryExpireFormProjectInfo(	final ExpireProjectQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ExpireFormProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ExpireFormProjectInfo> call() throws SocketTimeoutException {
				return expireProjectWebService.queryExpireFormProjectInfo(queryOrder);
			}
		});
	}

	@Override
	public FcsBaseResult saveNoticeTemplate(final ExpireProjectNoticeTemplateOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {

			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return expireProjectWebService.saveNoticeTemplate(order);
			}
		});
	}

	@Override
	public ExpireProjectNoticeTemplateInfo findTemplateByExpireIdAndStatus(final String expireId,final String status) {
		return callInterface(new CallExternalInterface<ExpireProjectNoticeTemplateInfo>() {

			@Override
			public ExpireProjectNoticeTemplateInfo call() throws SocketTimeoutException {
				return expireProjectWebService.findTemplateByExpireIdAndStatus(expireId,status);
			}
		});
	}

	@Override
	public ExpireProjectNoticeTemplateInfo findTemplateById(final long templateId) {
		return callInterface(new CallExternalInterface<ExpireProjectNoticeTemplateInfo>() {

			@Override
			public ExpireProjectNoticeTemplateInfo call() throws SocketTimeoutException {
				return expireProjectWebService.findTemplateById(templateId);
			}
		});
	}

	@Override
	public ExpireProjectInfo queryExpireProjectByExpireId(final String expireId){
		return callInterface(new CallExternalInterface<ExpireProjectInfo>() {

			@Override
			public ExpireProjectInfo call() throws SocketTimeoutException {
				return expireProjectWebService.queryExpireProjectByExpireId(expireId);
			}
		});
	}

}
