package com.born.fcs.face.integration.fm.service.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.enums.SubjectStatusEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.bank.BankTradeInfo;
import com.born.fcs.fm.ws.order.bank.BankMessageBatchOrder;
import com.born.fcs.fm.ws.order.bank.BankMessageOrder;
import com.born.fcs.fm.ws.order.bank.BankMessageQueryOrder;
import com.born.fcs.fm.ws.order.bank.BankTradeOrder;
import com.born.fcs.fm.ws.order.bank.BankTradeQueryOrder;
import com.born.fcs.fm.ws.result.bank.BankMessageResult;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("bankMessageServiceClient")
public class BankMessageServiceClient extends ClientAutowiredBaseService implements
																		BankMessageService {
	@Autowired
	BankMessageService bankMessageWebService;
	
	@Override
	public FcsBaseResult save(final BankMessageOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return bankMessageWebService.save(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult insert(final BankMessageOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return bankMessageWebService.insert(order);
			}
		});
	}
	
	@Override
	public BankMessageResult findById(final long bankId) {
		return callInterface(new CallExternalInterface<BankMessageResult>() {
			
			@Override
			public BankMessageResult call() {
				return bankMessageWebService.findById(bankId);
			}
		});
	}
	
	@Override
	public BankMessageResult findByAccount(final String accountNo) {
		return callInterface(new CallExternalInterface<BankMessageResult>() {
			
			@Override
			public BankMessageResult call() {
				return bankMessageWebService.findByAccount(accountNo);
			}
		});
	}
	
	@Override
	public FcsBaseResult update(final BankMessageOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return bankMessageWebService.update(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<BankMessageInfo> queryBankMessageInfo(	final BankMessageQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<BankMessageInfo>>() {
			
			@Override
			public QueryBaseBatchResult<BankMessageInfo> call() {
				return bankMessageWebService.queryBankMessageInfo(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateStatus(final long bankId, final SubjectStatusEnum status) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return bankMessageWebService.updateStatus(bankId, status);
			}
		});
	}
	
	@Override
	public FcsBaseResult trade(final BankTradeOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return bankMessageWebService.trade(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<BankTradeInfo> quryTrade(final BankTradeQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<BankTradeInfo>>() {
			
			@Override
			public QueryBaseBatchResult<BankTradeInfo> call() {
				return bankMessageWebService.quryTrade(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveAll(final BankMessageBatchOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return bankMessageWebService.saveAll(order);
			}
		});
	}
	
}
