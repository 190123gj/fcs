package com.born.fcs.face.integration.fm.service.payment;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.order.payment.CostCategoryOrder;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.fm.ws.service.payment.CostCategoryService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
/**
 * 费用种类客户端
 * @author lzb
 * 
 */
@Service("costCategoryServiceClient")
public class CostCategoryServiceClient extends ClientAutowiredBaseService implements CostCategoryService {

	@Override
	public QueryBaseBatchResult<CostCategoryInfo> queryPage(
			final CostCategoryQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CostCategoryInfo>>() {
			@Override
			public QueryBaseBatchResult<CostCategoryInfo> call() {
				return costCategoryService.queryPage(order);
			}
		});
	}

	@Override
	public CostCategoryInfo queryById(final long categoryId) {
		return callInterface(new CallExternalInterface<CostCategoryInfo>() {
			@Override
			public CostCategoryInfo call() {
				return costCategoryService.queryById(categoryId);
			}
		});
	}

	@Override
	public FcsBaseResult save(final CostCategoryOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return costCategoryService.save(order);
			}
		});
	}

	@Override
	public FcsBaseResult deleteById(final long categoryId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return costCategoryService.deleteById(categoryId);
			}
		});
	}

}
