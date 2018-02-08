/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:53:10 创建
 */
package com.born.fcs.fm.biz.service.bank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.job.AccountAmountDetailJob;
import com.born.fcs.fm.biz.job.AsynchronousTaskJob;
import com.born.fcs.fm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.BankMessageDO;
import com.born.fcs.fm.dal.dataobject.BankTradeDO;
import com.born.fcs.fm.dal.dataobject.FormPaymentFeeDO;
import com.born.fcs.fm.dal.dataobject.FormReceiptFeeDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.SubjectAccountTypeEnum;
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
import com.born.fcs.pm.biz.service.common.AsynchronousService;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Service("bankMessageService")
public class BankMessageServiceImpl extends BaseAutowiredDomainService implements
																		BankMessageService,
																		AsynchronousService {
	@Autowired
	private AsynchronousTaskJob asynchronousTaskJob;
	
	@Autowired
	private AccountAmountDetailJob accountAmountDetailJob;
	
	@Override
	public FcsBaseResult save(final BankMessageOrder order) {
		final AsynchronousService asy = this;
		return commonProcess(order, "保存银行账户信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				Date now = FcsFmDomainHolder.get().getSysDate();
				saveBankMessage(order, now);
				return null;
			}
			
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				String doCount = (String) FcsFmDomainHolder.get().getAttribute("countAccount");
				if (StringUtil.isNotBlank(doCount) && "count".equals(doCount)) {
					// 触发异步计算
					asynchronousTaskJob.addAsynchronousService(asy, new Object[1]);
				}
				return null;
			}
		});
	}
	
	@Override
	public FcsBaseResult saveAll(final BankMessageBatchOrder order) {
		final AsynchronousService asy = this;
		return commonProcess(order, "批量保存银行账户信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				Date now = FcsFmDomainHolder.get().getSysDate();
				if (order != null && ListUtil.isNotEmpty(order.getOrders())) {
					for (BankMessageOrder bankMessageOrder : order.getOrders()) {
						bankMessageOrder.setSaveByAccount(true);
						saveBankMessage(bankMessageOrder, now);
					}
				}
				return null;
			}
			
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				String doCount = (String) FcsFmDomainHolder.get().getAttribute("countAccount");
				if (StringUtil.isNotBlank(doCount) && "count".equals(doCount)) {
					// 触发异步计算
					asynchronousTaskJob.addAsynchronousService(asy, new Object[1]);
				}
				return null;
			}
		});
	}
	
	private void saveBankMessage(final BankMessageOrder order, Date now) {
		//查询是否已经存在
		BankMessageDO bankDO = null;
		if (order.isSaveByAccount()) {
			bankDO = bankMessageDAO.findByAccount(order.getAccountNo());
		} else {
			bankDO = bankMessageDAO.findById(order.getBankId());
			// 若入参银行帐号和原本的银行帐号不相同，需要判定银行帐号是否重复
			if (bankDO == null || StringUtil.notEquals(order.getAccountNo(), bankDO.getAccountNo())) {
				BankMessageDO bankDO2 = bankMessageDAO.findByAccount(order.getAccountNo());
				if (bankDO2 != null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DATABASE_EXCEPTION,
						"此银行帐号已存在！");
				}
			}
		}
		// 添加判断 20161103 所有部门默认对公唯一
		// 添加判断，所有默认对公账户唯一
		if (BooleanEnum.YES == order.getDefaultCompanyAccount()) {
			//			bankMessageDAO.updateDefaultCompanyAccount(BooleanEnum.NO.code());
			bankMessageDAO.updateDefaultCompanyAccountByDeptId(BooleanEnum.NO.code(),
				order.getDeptId());
		}
		// 添加判断，所有默认对私账户唯一
		if (BooleanEnum.YES == order.getDefaultPersonalAccount()) {
			//			bankMessageDAO.updateDefaultPersonalAccount(BooleanEnum.NO.code());
			bankMessageDAO.updateDefaultPersonalAccountByDeptId(BooleanEnum.NO.code(),
				order.getDeptId());
		}
		if (bankDO == null) {
			
			bankDO = new BankMessageDO();
			//					BeanCopier.staticCopy(order, bankDO);
			convertOrder2DO(order, bankDO);
			bankDO.setRawAddTime(now);
			bankDO.setStatus(SubjectStatusEnum.NORMAL.code());
			bankMessageDAO.insert(bankDO);
		} else {
			
			Money oldAmount = bankDO.getAmount();
			Money newAmount = order.getAmount();
			
			//					BeanCopier.staticCopy(order, bankDO);
			convertOrder2DO(order, bankDO);
			if (order.isSaveByAccount()) {
				bankMessageDAO.updateByAccount(bankDO);
			} else {
				bankMessageDAO.update(bankDO);
			}
			//如果改变了余额,则记录一笔交易明细
			if (!oldAmount.equals(newAmount)) {
				
				BankTradeDO trade = new BankTradeDO();
				trade.setAccountNo(bankDO.getAccountNo());
				trade.setRemark("银行账户信息维护");
				
				if (newAmount.greaterThan(oldAmount)) {
					trade.setFundDirection(FundDirectionEnum.IN.code());
					trade.setAmount(newAmount.subtract(oldAmount));
				} else {
					trade.setFundDirection(FundDirectionEnum.OUT.code());
					trade.setAmount(oldAmount.subtract(newAmount));
				}
				
				trade.setTradeNo(BusinessNumberUtil.gainNumber());
				trade.setTradeTime(now);
				trade.setRawAddTime(now);
				bankTradeDAO.insert(trade);
				FcsFmDomainHolder.get().addAttribute("countAccount", "count");
			}
		}
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.bank.FcsBaseResult#insert(java.lang.Long)
	 */
	@Override
	public FcsBaseResult insert(BankMessageOrder order) {
		logger.info("进入BankMessageServiceImpl的insert方法，入参：" + order);
		FcsBaseResult result = new FcsBaseResult();
		try {
			BankMessageDO bankDO = new BankMessageDO();
			convertOrder2DO(order, bankDO);
			bankDO.setStatus(SubjectStatusEnum.NORMAL.code());
			bankMessageDAO.insert(bankDO);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("插入银行账户信息失败，原因:" + e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	private void convertOrder2DO(BankMessageOrder order, BankMessageDO bankDO) {
		MiscUtil.copyPoObject(bankDO, order);
		if (order.getAccountType() != null) {
			bankDO.setAccountType(order.getAccountType().code());
		}
		if (order.getStatus() != null) {
			bankDO.setStatus(order.getStatus().code());
		}
		
		if (order.getDefaultCompanyAccount() != null) {
			bankDO.setDefaultCompanyAccount(order.getDefaultCompanyAccount().code());
		}
		if (order.getDefaultPersonalAccount() != null) {
			bankDO.setDefaultPersonalAccount(order.getDefaultPersonalAccount().code());
		}
		if (order.getDepositAccount() != null) {
			bankDO.setDepositAccount(order.getDepositAccount().code());
		}
	}
	
	/**
	 * @param id
	 * @return
	 * @see com.born.fcs.fm.ws.service.bank.BankMessageService#findById(java.lang.Long)
	 */
	@Override
	public BankMessageResult findById(long bankId) {
		logger.info("进入BankMessageServiceImpl的findById方法，入参：{}", bankId);
		BankMessageResult result = new BankMessageResult();
		if (bankId <= 0) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			logger.error("主键不能为空！");
			return result;
		}
		try {
			BankMessageDO bankDO = bankMessageDAO.findById(bankId);
			BankMessageInfo info = new BankMessageInfo();
			convertDO2Info(bankDO, info);
			
			// 添加查询，判断该帐号是否已被使用
			String account = info.getAccountNo();
			boolean used = checkAccountUsed(account);
			if (used) {
				result.setUsed(BooleanEnum.YES);
			} else {
				result.setUsed(BooleanEnum.NO);
			}
			
			result.setBankMessageInfo(info);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("查询银行账户信息失败，原因: {}", e);
			result.setSuccess(false);
		}
		return result;
	}
	
	private boolean checkAccountUsed(String account) {
		boolean used = false;
		//1.收款单
		List<FormReceiptFeeDO> receiptFeeDOs = formReceiptFeeDAO.findByAccount(account);
		if (ListUtil.isNotEmpty(receiptFeeDOs)) {
			used = true;
		} else {
			List<FormPaymentFeeDO> formReFeeDOs = formPaymentFeeDAO.findByReceiptAccount(account);
			if (ListUtil.isNotEmpty(formReFeeDOs)) {
				used = true;
			} else {
				List<FormPaymentFeeDO> formFeeDOs = formPaymentFeeDAO.findByPaymentAccount(account);
				if (ListUtil.isNotEmpty(formFeeDOs)) {
					used = true;
				}
			}
		}
		return used;
	}
	
	@Override
	public BankMessageResult findByAccount(String accountNo) {
		logger.info("进入BankMessageServiceImpl的findByAccount方法，入参：{}", accountNo);
		BankMessageResult result = new BankMessageResult();
		if (StringUtil.isEmpty(accountNo)) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			logger.error("账号不能为空！");
			return result;
		}
		try {
			BankMessageDO bankDO = bankMessageDAO.findByAccount(accountNo);
			BankMessageInfo info = new BankMessageInfo();
			convertDO2Info(bankDO, info);
			
			// 添加查询，判断该帐号是否已被使用
			String account = info.getAccountNo();
			boolean used = checkAccountUsed(account);
			if (used) {
				result.setUsed(BooleanEnum.YES);
			} else {
				result.setUsed(BooleanEnum.NO);
			}
			
			result.setBankMessageInfo(info);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("查询银行账户信息失败，原因: {}", e);
			result.setSuccess(false);
		}
		return result;
	}
	
	private void convertDO2Info(BankMessageDO bankDO, BankMessageInfo info) {
		MiscUtil.copyPoObject(info, bankDO);
		info.setAccountType(SubjectAccountTypeEnum.getByCode(bankDO.getAccountType()));
		info.setStatus(SubjectStatusEnum.getByCode(bankDO.getStatus()));
		info.setDefaultCompanyAccount(BooleanEnum.getByCode(bankDO.getDefaultCompanyAccount()));
		info.setDefaultPersonalAccount(BooleanEnum.getByCode(bankDO.getDefaultPersonalAccount()));
		info.setDepositAccount(BooleanEnum.getByCode(bankDO.getDepositAccount()));
		
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.bank.BankMessageService#update(com.born.fcs.fm.ws.order.bank.BankMessageOrder)
	 */
	@Override
	public FcsBaseResult update(BankMessageOrder order) {
		logger.info("进入BankMessageServiceImpl的update方法，入参：" + order);
		FcsBaseResult result = new FcsBaseResult();
		try {
			if (order == null || order.getBankId() <= 0) {
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
				logger.error("主键不能为空！");
				return result;
			}
			BankMessageDO bankDO = new BankMessageDO();
			convertOrder2DO(order, bankDO);
			bankMessageDAO.update(bankDO);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("修改银行账户信息失败，原因:" + e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.bank.BankMessageService#querySubject(com.born.fcs.fm.ws.order.bank.BankMessageQueryOrder)
	 */
	@Override
	public QueryBaseBatchResult<BankMessageInfo> queryBankMessageInfo(BankMessageQueryOrder order) {
		logger.info("进入BankMessageServiceImpl的queryBankMessageInfo方法，入参：{}", order);
		QueryBaseBatchResult<BankMessageInfo> result = new QueryBaseBatchResult<BankMessageInfo>();
		
		try {
			BankMessageDO queryDO = new BankMessageDO();
			BeanCopier.staticCopy(order, queryDO);
			if (order.getDefaultCompanyAccount() != null) {
				queryDO.setDefaultCompanyAccount(order.getDefaultCompanyAccount().code());
			}
			if (order.getDefaultPersonalAccount() != null) {
				queryDO.setDefaultPersonalAccount(order.getDefaultPersonalAccount().code());
			}
			if (order.getDepositAccount() != null) {
				queryDO.setDepositAccount(order.getDepositAccount().code());
			}
			
			if (order.getAccountType() != null)
				queryDO.setAccountType(order.getAccountType().code());
			
			if (order.getStatus() != null)
				queryDO.setStatus(order.getStatus().code());
			
			long totalCount = 0;
			// 查询totalcount
			totalCount = bankMessageDAO.findByConditionCount(queryDO, order.getDeptIdList());
			
			PageComponent component = new PageComponent(order, totalCount);
			// 查询list
			List<BankMessageDO> list = Lists.newArrayList();
			list = bankMessageDAO.findByCondition(queryDO, order.getDeptIdList(),
				component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
				order.getSortOrder());
			
			List<BankMessageInfo> pageList = new ArrayList<BankMessageInfo>();
			for (BankMessageDO item : list) {
				BankMessageInfo info = new BankMessageInfo();
				convertDO2Info(item, info);
				pageList.add(info);
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			result.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询银行账户信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
	}
	
	/**
	 * @param order
	 * @return
	 */
	@Override
	public FcsBaseResult updateStatus(long bankId, SubjectStatusEnum status) {
		logger.info("进入BankMessageServiceImpl的updateStatus方法，入参：bankId : {},status {}", bankId,
			status);
		FcsBaseResult result = new FcsBaseResult();
		try {
			if (bankId <= 0) {
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
				logger.error("主键不能为空！");
				return result;
			}
			if (status == null) {
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
				logger.error("状态不能为空！");
				return result;
			}
			bankMessageDAO.updateStatus(status.code(), bankId);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("修改银行账户信息失败，原因:" + e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public synchronized FcsBaseResult trade(final BankTradeOrder order) {
		
		return commonProcess(order, "保存银行账户交易", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				
				//查询银行账户 加锁 修改余额
				BankMessageDO bank = bankMessageDAO.findByAccountForUpdate(order.getAccountNo());
				if (!order.isIgnoreAccountIfNotExist() && bank == null) {
					throw ExceptionFactory
						.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "银行账号信息不存在");
				}
				
				//XXX 银行账号流出资金金额不足判断(判断后 交易记录不存在了？)
				//				if (order.getFundDirection() == FundDirectionEnum.OUT
				//					&& order.getAmount().greaterThan(bank.getAmount())) {
				//					throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE, "银行账号余额不足");
				//				}
				if (bank != null) {
					if (order.getFundDirection() == FundDirectionEnum.OUT) {
						bank.getAmount().subtractFrom(order.getAmount()); //资金流出
					} else {
						bank.getAmount().addTo(order.getAmount()); //资金流入
					}
					
					BankTradeDO trade = new BankTradeDO();
					BeanCopier.staticCopy(order, trade);
					trade.setTradeNo(BusinessNumberUtil.gainNumber());
					trade.setFundDirection(order.getFundDirection() == null ? "" : order
						.getFundDirection().code());
					trade.setRawAddTime(now);
					
					bankTradeDAO.insert(trade);
					//更新金额
					bankMessageDAO.update(bank);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<BankTradeInfo> quryTrade(BankTradeQueryOrder order) {
		logger.info("进入BankMessageServiceImpl的quryTrade方法，入参：{}", order);
		QueryBaseBatchResult<BankTradeInfo> result = new QueryBaseBatchResult<BankTradeInfo>();
		
		try {
			BankTradeDO queryDO = new BankTradeDO();
			BeanCopier.staticCopy(order, queryDO);
			
			long totalCount = 0;
			// 查询totalcount
			totalCount = bankTradeDAO.findByConditionCount(queryDO, order.getTradeTimeStart(),
				order.getTradeTimeEnd());
			
			PageComponent component = new PageComponent(order, totalCount);
			// 查询list
			List<BankTradeDO> list = Lists.newArrayList();
			list = bankTradeDAO.findByCondition(queryDO, component.getFirstRecord(),
				component.getPageSize(), order.getSortCol(), order.getSortOrder(),
				order.getTradeTimeStart(), order.getTradeTimeEnd());
			
			List<BankTradeInfo> pageList = new ArrayList<BankTradeInfo>();
			for (BankTradeDO item : list) {
				BankTradeInfo info = new BankTradeInfo();
				BeanCopier.staticCopy(item, info);
				info.setFundDirection(FundDirectionEnum.getByCode(item.getFundDirection()));
				pageList.add(info);
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			result.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询银行账户交易信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
	}
	
	@Override
	public void execute(Object[] objects) {
		try {
			accountAmountDetailJob.doJob();
		} catch (Exception e) {
			logger.error("异步执行余额计算失败:" + e.getMessage(), e);
		}
	}
}
