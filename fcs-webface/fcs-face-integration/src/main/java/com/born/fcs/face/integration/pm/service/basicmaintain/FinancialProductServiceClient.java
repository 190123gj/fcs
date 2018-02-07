package com.born.fcs.face.integration.pm.service.basicmaintain;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.info.common.FinancialProductInfo;
import com.born.fcs.pm.ws.order.basicmaintain.FinancialProductOrder;
import com.born.fcs.pm.ws.order.basicmaintain.FinancialProductQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.basicmaintain.FinancialProductService;

/**
 * 
 * @author wuzj
 *
 */
@Service("financialProductServiceClient")
public class FinancialProductServiceClient extends ClientAutowiredBaseService implements
																				FinancialProductService {
	
	@Override
	public FinancialProductInfo findById(final long productId) {
		return callInterface(new CallExternalInterface<FinancialProductInfo>() {
			
			@Override
			public FinancialProductInfo call() {
				return financialProductWebService.findById(productId);
			}
		});
	}
	
	@Override
	public FinancialProductInfo findByUnique(final String productName) {
		return callInterface(new CallExternalInterface<FinancialProductInfo>() {
			
			@Override
			public FinancialProductInfo call() {
				return financialProductWebService.findByUnique(productName);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final FinancialProductOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return financialProductWebService.save(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult changeSellStatus(final FinancialProductOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return financialProductWebService.changeSellStatus(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FinancialProductInfo> query(final FinancialProductQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FinancialProductInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FinancialProductInfo> call() {
				return financialProductWebService.query(order);
			}
		});
	}
	
	@Override
	public FinancialProductTermTypeEnum calculateProductTermType(final int timeLimit,
																	final String timeUnit) {
		return callInterface(new CallExternalInterface<FinancialProductTermTypeEnum>() {
			
			@Override
			public FinancialProductTermTypeEnum call() {
				return financialProductWebService.calculateProductTermType(timeLimit, timeUnit);
			}
		});
	}
}
