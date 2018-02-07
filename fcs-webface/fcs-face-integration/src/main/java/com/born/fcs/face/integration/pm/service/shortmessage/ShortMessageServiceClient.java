package com.born.fcs.face.integration.pm.service.shortmessage;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.shortmessage.ShortMessageInfo;
import com.born.fcs.pm.ws.order.shortmessage.ShortMessageOrder;
import com.born.fcs.pm.ws.order.shortmessage.ShortMessageQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.shortmessage.ShortMessageService;

/**
 * 
 * @author jil
 * 
 */
@Service("shortMessageServiceClient")
public class ShortMessageServiceClient extends ClientAutowiredBaseService implements
																			ShortMessageService {
	
	@Override
	public FcsBaseResult save(final ShortMessageOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return shortMessageWebService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ShortMessageInfo> query(final ShortMessageQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ShortMessageInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ShortMessageInfo> call() {
				return shortMessageWebService.query(order);
			}
		});
	}
	
	@Override
	public ShortMessageInfo findById(final long id) {
		return callInterface(new CallExternalInterface<ShortMessageInfo>() {
			
			@Override
			public ShortMessageInfo call() {
				return shortMessageWebService.findById(id);
			}
		});
	}
}
