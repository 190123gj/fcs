package com.born.fcs.am.intergration.service;

import org.springframework.stereotype.Service;

import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormMessageTypeEnum;
import com.born.fcs.pm.ws.info.common.FormMessageTempleteInfo;
import com.born.fcs.pm.ws.order.common.FormMessageTempleteOrder;
import com.born.fcs.pm.ws.order.common.FormMessageTempleteQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormMessageTempleteService;

@Service("formMessageTempleteServiceClient")
public class FormMessageTempleteServiceClient extends
		ClientAutowiredBaseService implements FormMessageTempleteService {

	@Override
	public void clearCache() {
		formMessageTempleteWebService.clearCache();
	}

	@Override
	public QueryBaseBatchResult<FormMessageTempleteInfo> queryPage(
			final FormMessageTempleteQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormMessageTempleteInfo>>() {

			@Override
			public QueryBaseBatchResult<FormMessageTempleteInfo> call() {
				return formMessageTempleteWebService.queryPage(order);
			}
		});
	}

	@Override
	public FormMessageTempleteInfo findByFormCodeAndType(
			final FormCodeEnum formCode, final FormMessageTypeEnum type) {
		return callInterface(new CallExternalInterface<FormMessageTempleteInfo>() {

			@Override
			public FormMessageTempleteInfo call() {
				return formMessageTempleteWebService.findByFormCodeAndType(
						formCode, type);
			}
		});
	}

	@Override
	public FormMessageTempleteInfo findByTempleteId(final long templeteId) {
		return callInterface(new CallExternalInterface<FormMessageTempleteInfo>() {

			@Override
			public FormMessageTempleteInfo call() {
				return formMessageTempleteWebService
						.findByTempleteId(templeteId);
			}
		});
	}

	@Override
	public FcsBaseResult saveTemplete(final FormMessageTempleteOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {

			@Override
			public FcsBaseResult call() {
				return formMessageTempleteWebService.saveTemplete(order);
			}
		});
	}

	@Override
	public FcsBaseResult deleteByTempleteId(final long templeteId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {

			@Override
			public FcsBaseResult call() {
				return formMessageTempleteWebService
						.deleteByTempleteId(templeteId);
			}
		});
	}

}
