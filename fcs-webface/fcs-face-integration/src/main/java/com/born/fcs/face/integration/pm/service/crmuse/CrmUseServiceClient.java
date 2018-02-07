package com.born.fcs.face.integration.pm.service.crmuse;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.forCrm.IndirectCustomerInfo;
import com.born.fcs.pm.ws.info.forCrm.ViewChannelProjectAllPhasInfo;
import com.born.fcs.pm.ws.order.forCrm.IndirectCustomerQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.forCrm.CrmUseService;

/**
 * 表单客户端
 * @author wuzj
 * 
 */
@Service("crmUseServiceClient")
public class CrmUseServiceClient extends ClientAutowiredBaseService implements CrmUseService {
	
	@Override
	public FcsBaseResult channalUsed(final Long id) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return crmUseService.channalUsed(id);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<IndirectCustomerInfo> queryIndirectCustomer(final IndirectCustomerQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<IndirectCustomerInfo>>() {
			
			@Override
			public QueryBaseBatchResult<IndirectCustomerInfo> call() {
				return crmUseService.queryIndirectCustomer(order);
			}
		});
	}
	
	@Override
	public ViewChannelProjectAllPhasInfo queryChannelByprojectCodeAndPhas(final String projectCode,
																			final long phas) {
		return callInterface(new CallExternalInterface<ViewChannelProjectAllPhasInfo>() {
			
			@Override
			public ViewChannelProjectAllPhasInfo call() {
				return crmUseService.queryChannelByprojectCodeAndPhas(projectCode, phas);
			}
		});
	}
}
