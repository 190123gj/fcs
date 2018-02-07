package com.born.fcs.face.integration.fm.service.payment;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.info.payment.FormTransferInfo;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.payment.FormTransferOrder;
import com.born.fcs.fm.ws.order.payment.FormTransferQueryOrder;
import com.born.fcs.fm.ws.service.payment.FormTransferService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
/**
 * 转账申请单客户端
 * @author lzb
 * 
 */
@Service("formTransferServiceClient")
public class FormTransferServiceClient extends ClientAutowiredBaseService implements FormTransferService {

	@Override
	public QueryBaseBatchResult<FormTransferInfo> queryPage(
			final FormTransferQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormTransferInfo>>() {
			@Override
			public QueryBaseBatchResult<FormTransferInfo> call() {
				return formTransferService.queryPage(order);
			}
		});
	}

	@Override
	public FormTransferInfo queryByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FormTransferInfo>() {
			@Override
			public FormTransferInfo call() {
				return formTransferService.queryByFormId(formId);
			}
		});
	}

	@Override
	public FormTransferInfo queryById(final long transferId) {
		return callInterface(new CallExternalInterface<FormTransferInfo>() {
			@Override
			public FormTransferInfo call() {
				return formTransferService.queryById(transferId);
			}
		});
	}

	@Override
	public FormBaseResult save(final FormTransferOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return formTransferService.save(order);
			}
		});
	}

	@Override
	public FcsBaseResult sync2FinancialSys(FormTransferInfo travelInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FcsBaseResult updateVoucher(UpdateVoucherOrder order) {
		// TODO Auto-generated method stub
		return null;
	}

}
