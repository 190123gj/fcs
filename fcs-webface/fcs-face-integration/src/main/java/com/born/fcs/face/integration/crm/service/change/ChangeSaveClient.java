package com.born.fcs.face.integration.crm.service.change;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.ChangeSaveService;
import com.born.fcs.crm.ws.service.info.ChangeListInfo;
import com.born.fcs.crm.ws.service.order.ChangeListOrder;
import com.born.fcs.crm.ws.service.order.query.ChangeListQueryOrder;
import com.born.fcs.crm.ws.service.result.ChangeResult;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("changeSaveClient")
public class ChangeSaveClient extends ClientAutowiredBaseService implements ChangeSaveService {
	
	@Autowired
	private ChangeSaveService changeSaveService;
	
	@Override
	public FcsBaseResult save(final ChangeListOrder changeListOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return changeSaveService.save(changeListOrder);
			}
		});
	}
	
	@Override
	public ChangeListInfo queryChange(final Long changeId) {
		return callInterface(new CallExternalInterface<ChangeListInfo>() {
			
			@Override
			public ChangeListInfo call() {
				return changeSaveService.queryChange(changeId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ChangeListInfo> list(final ChangeListQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ChangeListInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ChangeListInfo> call() {
				return changeSaveService.list(queryOrder);
			}
		});
	}
	
	@Override
	public ChangeResult createChangeOrder(final Object newInfo, final Object oldInfo,
											final HashMap<String, String> lableNames,
											final Boolean onlyContaint) {
		return callInterface(new CallExternalInterface<ChangeResult>() {
			
			@Override
			public ChangeResult call() {
				return changeSaveService.createChangeOrder(newInfo, oldInfo, lableNames,
					onlyContaint);
			}
		});
	}
	
}
