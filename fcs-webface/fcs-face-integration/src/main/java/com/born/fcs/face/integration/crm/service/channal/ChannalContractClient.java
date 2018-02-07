package com.born.fcs.face.integration.crm.service.channal;

import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.ChannalContractService;
import com.born.fcs.crm.ws.service.info.ChannalContractInfo;
import com.born.fcs.crm.ws.service.order.ChannalContractOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalContractQueryOrder;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("channalContractClient")
public class ChannalContractClient extends ClientAutowiredBaseService implements
																		ChannalContractService {
	
	@Override
	public FcsBaseResult save(final ChannalContractOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return channalContractService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ChannalContractInfo> list(final ChannalContractQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ChannalContractInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ChannalContractInfo> call() {
				return channalContractService.list(queryOrder);
			}
		});
	}
	
	@Override
	public ChannalContractInfo info(final long formId) {
		return callInterface(new CallExternalInterface<ChannalContractInfo>() {
			
			@Override
			public ChannalContractInfo call() {
				return channalContractService.info(formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateStatus(final String contractNo, final ContractStatusEnum status) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return channalContractService.updateStatus(contractNo, status);
			}
		});
	}
	
	@Override
	public FcsBaseResult returnContract(final long formId, final String contractReturn) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return channalContractService.returnContract(formId, contractReturn);
			}
		});
	}
	
}
