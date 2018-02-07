package com.born.fcs.face.integration.pm.service.notice;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.notice.ConsentIssueNoticeInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.notice.ConsentIssueNoticeOrder;
import com.born.fcs.pm.ws.order.notice.ConsentIssueNoticeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.notice.ConsentIssueNoticeService;


/**
 * 
 * @author jil
 *
 */
@Service("consentIssueNoticeServiceClient")
public class ConsentIssueNoticeServiceClient extends ClientAutowiredBaseService implements
																					ConsentIssueNoticeService {

	@Override
	public ConsentIssueNoticeInfo findById(final long noticeId) {
		return callInterface(new CallExternalInterface<ConsentIssueNoticeInfo>() {
			
			@Override
			public ConsentIssueNoticeInfo call() {
				return consentIssueNoticeWebService.findById(noticeId);
			}
		});
	}

	@Override
	public FcsBaseResult save(final ConsentIssueNoticeOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return consentIssueNoticeWebService.save(order);
			}
		});
	}

	@Override
	public QueryBaseBatchResult<ConsentIssueNoticeInfo> query(final ConsentIssueNoticeQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ConsentIssueNoticeInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ConsentIssueNoticeInfo> call() {
				return consentIssueNoticeWebService.query(order);
			}
		});
	}

	@Override
	public int deleteById(final long noticeId) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return consentIssueNoticeWebService.deleteById(noticeId);
			}
		});
	}

	@Override
	public FcsBaseResult uploadReceipt(final ConsentIssueNoticeOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return consentIssueNoticeWebService.uploadReceipt(order);
			}
		});
	}

	@Override
	public QueryBaseBatchResult<ProjectInfo> queryConsentIssueNotice(final ProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() {
				return consentIssueNoticeWebService.queryConsentIssueNotice(order);
			}
		});
	}

	
	
}
