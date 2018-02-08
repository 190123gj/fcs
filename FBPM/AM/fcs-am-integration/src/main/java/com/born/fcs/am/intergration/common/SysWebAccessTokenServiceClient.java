package com.born.fcs.am.intergration.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.am.intergration.service.CallExternalInterface;
import com.born.fcs.am.intergration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.SysWebAccessTokenInfo;
import com.born.fcs.pm.ws.order.common.SysWebAccessTokenOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;

@Service("sysWebAccessTokenServiceClient")
public class SysWebAccessTokenServiceClient extends ClientAutowiredBaseService
		implements SysWebAccessTokenService {
	@Autowired
	SysWebAccessTokenService sysWebAccessTokenWebService;

	@Override
	public FcsBaseResult addUserAccessToken(final SysWebAccessTokenOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {

			@Override
			public FcsBaseResult call() {
				return sysWebAccessTokenWebService.addUserAccessToken(order);
			}
		});
	}

	@Override
	public SysWebAccessTokenInfo query(final String accessToken) {
		return callInterface(new CallExternalInterface<SysWebAccessTokenInfo>() {

			@Override
			public SysWebAccessTokenInfo call() {
				return sysWebAccessTokenWebService.query(accessToken);
			}
		});
	}

	@Override
	public FcsBaseResult use(final String accessToken) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {

			@Override
			public FcsBaseResult call() {
				return sysWebAccessTokenWebService.use(accessToken);
			}
		});
	}

	@Override
	public FcsBaseResult active(final String accessToken) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {

			@Override
			public FcsBaseResult call() {
				return sysWebAccessTokenWebService.active(accessToken);
			}
		});
	}

}
