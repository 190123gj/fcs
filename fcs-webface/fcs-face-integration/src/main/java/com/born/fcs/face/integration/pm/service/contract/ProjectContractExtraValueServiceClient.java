/**
 * 
 */
package com.born.fcs.face.integration.pm.service.contract;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.order.contract.ProjectContractExtraValueBatchOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.contract.ProjectContractExtraValueInfoResult;
import com.born.fcs.pm.ws.service.contract.ProjectContractExtraValueService;

/**
 * @author jiajie
 * 
 */
@Service("projectContractExtraValueService")
public class ProjectContractExtraValueServiceClient extends
		ClientAutowiredBaseService implements ProjectContractExtraValueService {

	@Override
	public FcsBaseResult extraValueSave(
			final ProjectContractExtraValueBatchOrder order) {

		return callInterface(new CallExternalInterface<FcsBaseResult>() {

			@Override
			public FcsBaseResult call() {
				return projectContractExtraValueWebService
						.extraValueSave(order);
			}
		});
	}

	@Override
	public ProjectContractExtraValueInfoResult findByContractItemId(
			final Long id) {
		return callInterface(new CallExternalInterface<ProjectContractExtraValueInfoResult>() {

			@Override
			public ProjectContractExtraValueInfoResult call() {
				return projectContractExtraValueWebService
						.findByContractItemId(id);
			}
		});
	}

}
