package com.born.fcs.face.integration.risk.service;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.bornsoft.facade.api.apix.ApixCreditInvestigationFacade;
import com.bornsoft.pub.order.apix.ApixDishonestQueryOrder;
import com.bornsoft.pub.order.apix.ApixLocationBasedOrder;
import com.bornsoft.pub.order.apix.ApixValidateBankCardOrder;
import com.bornsoft.pub.order.apix.ApixValidateIdCardOrder;
import com.bornsoft.pub.order.apix.ApixValidateMobileOrder;
import com.bornsoft.pub.result.apix.ApixDishonestQueryResult;
import com.bornsoft.pub.result.apix.ApixLocationBasedResult;
import com.bornsoft.pub.result.apix.ApixValidateBankCardResult;
import com.bornsoft.pub.result.apix.ApixValidateIdCardResult;
import com.bornsoft.pub.result.apix.ApixValidateMobileResult;

@Service("apixCreditInvestigationFacadeClient")
public class ApixCreditInvestigationFacadeClient extends ClientAutowiredBaseService implements
																					ApixCreditInvestigationFacade {
	/** 失信查询 */
	@Override
	public ApixDishonestQueryResult dishonestQuery(final ApixDishonestQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<ApixDishonestQueryResult>() {
			
			@Override
			public ApixDishonestQueryResult call() {
				return apixCreditInvestigationFacade.dishonestQuery(queryOrder);
			}
		});
	}
	
	@Override
	public ApixLocationBasedResult locateByBaseStation(final ApixLocationBasedOrder arg0) {
		return callInterface(new CallExternalInterface<ApixLocationBasedResult>() {
			
			@Override
			public ApixLocationBasedResult call() {
				return apixCreditInvestigationFacade.locateByBaseStation(arg0);
			}
		});
	}
	
	@Override
	public ApixValidateBankCardResult validateBankCard(final ApixValidateBankCardOrder arg0) {
		return callInterface(new CallExternalInterface<ApixValidateBankCardResult>() {
			
			@Override
			public ApixValidateBankCardResult call() {
				return apixCreditInvestigationFacade.validateBankCard(arg0);
			}
		});
	}
	
	/**
	 * 实名校验
	 * */
	@Override
	public ApixValidateIdCardResult validateIdCard(final ApixValidateIdCardOrder arg0) {
		return callInterface(new CallExternalInterface<ApixValidateIdCardResult>() {
			
			@Override
			public ApixValidateIdCardResult call() {
				return apixCreditInvestigationFacade.validateIdCard(arg0);
			}
		});
	}
	
	/**
	 * 电话校验
	 * */
	@Override
	public ApixValidateMobileResult validateMobile(final ApixValidateMobileOrder arg0) {
		return callInterface(new CallExternalInterface<ApixValidateMobileResult>() {
			
			@Override
			public ApixValidateMobileResult call() {
				return apixCreditInvestigationFacade.validateMobile(arg0);
			}
		});
	}
	
}
