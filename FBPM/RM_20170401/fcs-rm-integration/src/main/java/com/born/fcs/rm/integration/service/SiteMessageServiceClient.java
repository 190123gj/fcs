package com.born.fcs.rm.integration.service;

import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.ws.enums.MessageReceivedStatusEnum;
import com.born.fcs.pm.ws.info.common.MessageInfo;
import com.born.fcs.pm.ws.info.common.MessageReceivedInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.MyMessageOrder;
import com.born.fcs.pm.ws.order.common.QueryMessageOrder;
import com.born.fcs.pm.ws.order.common.QueryReceviedMessageOrder;
import com.born.fcs.pm.ws.order.common.SendMessageOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.MessageResult;
import com.born.fcs.rm.integration.common.CallExternalInterface;
import com.born.fcs.rm.integration.common.impl.ClientAutowiredBaseService;

public class SiteMessageServiceClient extends ClientAutowiredBaseService implements
																		SiteMessageService {
	
	@Override
	public void execute(Object[] objects) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public FcsBaseResult addMessageInfo(final MessageOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return siteMessageWebService.addMessageInfo(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateMessageInfo(final MessageOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return siteMessageWebService.updateMessageInfo(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult sendUserMessageInfo(final SendMessageOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return siteMessageWebService.sendUserMessageInfo(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<MessageReceivedInfo> findReceviedMessage(	final QueryReceviedMessageOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<MessageReceivedInfo>>() {
			
			@Override
			public QueryBaseBatchResult<MessageReceivedInfo> call() {
				return siteMessageWebService.findReceviedMessage(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<MessageReceivedInfo> loadUnReadMyMessage(final long userId) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<MessageReceivedInfo>>() {
			
			@Override
			public QueryBaseBatchResult<MessageReceivedInfo> call() {
				return siteMessageWebService.loadUnReadMyMessage(userId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<MessageInfo> findMessage(final QueryMessageOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<MessageInfo>>() {
			
			@Override
			public QueryBaseBatchResult<MessageInfo> call() {
				return siteMessageWebService.findMessage(order);
			}
		});
	}
	
	@Override
	public MessageResult deleteReceivedMessageInfo(final MyMessageOrder myMessageOrder) {
		return callInterface(new CallExternalInterface<MessageResult>() {
			
			@Override
			public MessageResult call() {
				return siteMessageWebService.deleteReceivedMessageInfo(myMessageOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteMessageInfo(final long messageId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return siteMessageWebService.deleteMessageInfo(messageId);
			}
		});
	}
	
	@Override
	public MessageResult readMessageInfo(final MyMessageOrder myMessageOrder) {
		return callInterface(new CallExternalInterface<MessageResult>() {
			
			@Override
			public MessageResult call() {
				return siteMessageWebService.readMessageInfo(myMessageOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateReceivedMessageStatus(final long userId,
														final MessageReceivedStatusEnum status) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return siteMessageWebService.updateReceivedMessageStatus(userId, status);
			}
		});
	}
	
	@Override
	public MessageResult readMessageAndUpStatus(final MyMessageOrder myMessageOrder) {
		return callInterface(new CallExternalInterface<MessageResult>() {
			
			@Override
			public MessageResult call() {
				return siteMessageWebService.readMessageAndUpStatus(myMessageOrder);
			}
		});
	}
	
	@Override
	public long loadUnReadMyMessageCount(final long userId) {
		return callInterface(new CallExternalInterface<Long>() {
			
			@Override
			public Long call() {
				return siteMessageWebService.loadUnReadMyMessageCount(userId);
			}
		});
	}
}
