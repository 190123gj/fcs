package com.born.fcs.pm.integration.am.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.info.transferee.FAssetsTransfereeApplicationInfo;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationQueryOrder;
import com.born.fcs.am.ws.order.transferee.FAssetsTransfereeApplicationOrder;
import com.born.fcs.am.ws.order.transferee.FAssetsTransfereeApplicationQueryOrder;
import com.born.fcs.am.ws.service.transferee.AssetsTransfereeApplicationService;
import com.born.fcs.pm.integration.common.CallExternalInterface;
import com.born.fcs.pm.integration.common.impl.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * @author jil
 *
 */
@Service("assetsTransfereeApplicationServiceClient")
public class AssetsTransfereeApplicationServiceClient extends ClientAutowiredBaseService implements
																						AssetsTransfereeApplicationService {
	
	@Autowired
	AssetsTransfereeApplicationService assetsTransfereeApplicationWebService;
	
	@Override
	public FAssetsTransfereeApplicationInfo findById(final long id) {
		return callInterface(new CallExternalInterface<FAssetsTransfereeApplicationInfo>() {
			
			@Override
			public FAssetsTransfereeApplicationInfo call() {
				return assetsTransfereeApplicationWebService.findById(id);
			}
		});
	}
	
	@Override
	public FAssetsTransfereeApplicationInfo findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FAssetsTransfereeApplicationInfo>() {
			
			@Override
			public FAssetsTransfereeApplicationInfo call() {
				return assetsTransfereeApplicationWebService.findByFormId(formId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FAssetsTransfereeApplicationInfo> query(final FAssetsTransfereeApplicationQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FAssetsTransfereeApplicationInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FAssetsTransfereeApplicationInfo> call() {
				return assetsTransfereeApplicationWebService.query(order);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final FAssetsTransfereeApplicationOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return assetsTransfereeApplicationWebService.save(order);
			}
		});
	}
	
	@Override
	public int updateIsColseMessage(final long id) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return assetsTransfereeApplicationWebService.updateIsColseMessage(id);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FAssetsTransferApplicationInfo> queryTransferProject(	final FAssetsTransferApplicationQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FAssetsTransferApplicationInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FAssetsTransferApplicationInfo> call() {
				return assetsTransfereeApplicationWebService.queryTransferProject(order);
			}
		});
	}
	
	@Override
	public long findByOuterProjectNameCount(final String projectName) {
		return callInterface(new CallExternalInterface<Long>() {
			
			@Override
			public Long call() {
				return assetsTransfereeApplicationWebService
					.findByOuterProjectNameCount(projectName);
			}
		});
	}
	
}
