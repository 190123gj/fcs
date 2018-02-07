package com.born.fcs.face.integration.pm.service.user;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.user.UserExtraMessageInfo;
import com.born.fcs.pm.ws.order.user.UserExtraMessageAddOrder;
import com.born.fcs.pm.ws.order.user.UserExtraMessageOrder;
import com.born.fcs.pm.ws.order.user.UserExtraMessageQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.user.UserExtraMessageResult;
import com.born.fcs.pm.ws.service.user.UserExtraMessageService;

/**
 * 用户额外信息查询
 * @author jiaije
 * 
 */
@Service("userExtraMessageServiceClient")
public class UserExtraMessageServiceClient extends ClientAutowiredBaseService implements
																				UserExtraMessageService {
	
	@Override
	public UserExtraMessageResult findByUserAccount(final String userAccount) {
		return callInterface(new CallExternalInterface<UserExtraMessageResult>() {
			
			@Override
			public UserExtraMessageResult call() {
				return userExtraMessageService.findByUserAccount(userAccount);
			}
		});
	}
	
	@Override
	public UserExtraMessageResult findByUserId(final Long userId) {
		return callInterface(new CallExternalInterface<UserExtraMessageResult>() {
			
			@Override
			public UserExtraMessageResult call() {
				return userExtraMessageService.findByUserId(userId);
			}
		});
	}
	
	@Override
	public UserExtraMessageResult findById(final Long id) {
		return callInterface(new CallExternalInterface<UserExtraMessageResult>() {
			
			@Override
			public UserExtraMessageResult call() {
				return userExtraMessageService.findById(id);
			}
		});
	}
	
	@Override
	public UserExtraMessageResult insert(final UserExtraMessageAddOrder order) {
		return callInterface(new CallExternalInterface<UserExtraMessageResult>() {
			
			@Override
			public UserExtraMessageResult call() {
				return userExtraMessageService.insert(order);
			}
		});
	}
	
	@Override
	public UserExtraMessageResult insertOrUpdate(final UserExtraMessageAddOrder order) {
		return callInterface(new CallExternalInterface<UserExtraMessageResult>() {
			
			@Override
			public UserExtraMessageResult call() {
				return userExtraMessageService.insertOrUpdate(order);
			}
		});
	}
	
	@Override
	public UserExtraMessageResult update(final UserExtraMessageOrder order) {
		return callInterface(new CallExternalInterface<UserExtraMessageResult>() {
			
			@Override
			public UserExtraMessageResult call() {
				return userExtraMessageService.update(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<UserExtraMessageInfo> queryUserExtraMessage(final UserExtraMessageQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<UserExtraMessageInfo>>() {
			
			@Override
			public QueryBaseBatchResult<UserExtraMessageInfo> call() {
				return userExtraMessageService.queryUserExtraMessage(order);
			}
		});
	}
	
}
