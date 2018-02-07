package com.born.fcs.face.integration.am.service.transfer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.am.ws.info.transfer.FAssetsTransferApplicationInfo;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationOrder;
import com.born.fcs.am.ws.order.transfer.FAssetsTransferApplicationQueryOrder;
import com.born.fcs.am.ws.service.transfer.AssetsTransferApplicationService;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * @author jil
 *
 */
@Service("assetsTransferApplicationServiceClient")
public class AssetsTransferApplicationServiceClient extends ClientAutowiredBaseService implements
																						AssetsTransferApplicationService {
	
	@Override
	public FAssetsTransferApplicationInfo findById(final long id) {
		return callInterface(new CallExternalInterface<FAssetsTransferApplicationInfo>() {
			
			@Override
			public FAssetsTransferApplicationInfo call() {
				return assetsTransferApplicationWebService.findById(id);
			}
		});
	}
	
	@Override
	public FAssetsTransferApplicationInfo findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FAssetsTransferApplicationInfo>() {
			
			@Override
			public FAssetsTransferApplicationInfo call() {
				return assetsTransferApplicationWebService.findByFormId(formId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FAssetsTransferApplicationInfo> query(	final FAssetsTransferApplicationQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FAssetsTransferApplicationInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FAssetsTransferApplicationInfo> call() {
				return assetsTransferApplicationWebService.query(order);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final FAssetsTransferApplicationOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return assetsTransferApplicationWebService.save(order);
			}
		});
	}
	
	@Override
	public int updateIsColseMessage(final long id) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return assetsTransferApplicationWebService.updateIsColseMessage(id);
			}
		});
	}
	
	@Override
	public List<FAssetsTransferApplicationInfo> findByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<List<FAssetsTransferApplicationInfo>>() {
			
			@Override
			public List<FAssetsTransferApplicationInfo> call() {
				return assetsTransferApplicationWebService.findByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public int updateIsCharge(final long id) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return assetsTransferApplicationWebService.updateIsCharge(id);
			}
		});
	}
	
	@Override
	public FcsBaseResult syncForecast(long formId) {
		return null;
	}
}
