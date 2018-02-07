package com.born.fcs.face.integration.fm.service.payment;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.info.payment.BankCategoryInfo;
import com.born.fcs.fm.ws.order.payment.BankCategoryOrder;
import com.born.fcs.fm.ws.order.payment.BankCategoryQueryOrder;
import com.born.fcs.fm.ws.service.payment.BankCategoryService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
/**
 * 银行种类客户端
 * @author lzb
 * 
 */
@Service("bankCategoryServiceClient")
public class BankCategoryServiceClient extends ClientAutowiredBaseService implements BankCategoryService {

	@Override
	public QueryBaseBatchResult<BankCategoryInfo> queryPage(
			final BankCategoryQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<BankCategoryInfo>>() {
			@Override
			public QueryBaseBatchResult<BankCategoryInfo> call() {
				return bankCategoryService.queryPage(order);
			}
		});
	}

	@Override
	public BankCategoryInfo queryById(final long categoryId) {
		return callInterface(new CallExternalInterface<BankCategoryInfo>() {
			@Override
			public BankCategoryInfo call() {
				return bankCategoryService.queryById(categoryId);
			}
		});
	}

	@Override
	public FcsBaseResult save(final BankCategoryOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return bankCategoryService.save(order);
			}
		});
	}

	@Override
	public FcsBaseResult deleteById(final long categoryId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return bankCategoryService.deleteById(categoryId);
			}
		});
	}

}
