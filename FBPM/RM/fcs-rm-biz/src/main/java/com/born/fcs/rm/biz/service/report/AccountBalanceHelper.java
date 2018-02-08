package com.born.fcs.rm.biz.service.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born.fcs.rm.ws.info.accountbalance.AccountBalanceDataInfo;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 科目余额表工具
 * 
 * @author lirz
 *
 * 2016-8-20 上午11:36:37
 */
public class AccountBalanceHelper {
	
	private Map<String, AccountBalanceDataInfo> map = new HashMap<>();
	private Map<String, List<AccountBalanceDataInfo>> mapList = new HashMap<>();
	
	public AccountBalanceHelper(List<AccountBalanceDataInfo> datas, String... starts) {
		if (ListUtil.isEmpty(datas)) {
			return;
		}
		
		boolean hasStarts = false;
		if (null != starts && starts.length > 0) {
			hasStarts = true;
		}
		
		for (AccountBalanceDataInfo data : datas) {
			if (null == data) {
				continue;
			}
			
			String code = data.getCode();
			if (StringUtil.isBlank(code)) {
				map.put("total", data);
			} else {
				map.put(code, data);
				
				if (hasStarts) {
					for (String start : starts) {
						if (code.startsWith(start)) {
							List<AccountBalanceDataInfo> list = mapList.get(start);
							if (null == list) {
								list = new ArrayList<>();
								mapList.put(start, list);
							}
							list.add(data);
						}
					}
				}
			}
		}
	}
	
	/** 获取本期借方发生额和 */
	public Money caculateCurrentDebit(String... codes) {
		Money m = new Money(0L);
		if (null != codes && codes.length > 0) {
			for (String code : codes) {
				AccountBalanceDataInfo data = map.get(code);
				if (null != data) {
					m.addTo(data.getCurrentDebitAmount());
				}
			}
		}
		return m;
	}
	
	/** 本期贷方发生额 */
	public Money caculateCurrentCredit(String... codes) {
		Money m = new Money(0L);
		if (null != codes && codes.length > 0) {
			for (String code : codes) {
				AccountBalanceDataInfo data = map.get(code);
				if (null != data) {
					m.addTo(data.getCurrentCreditAmount());
				}
			}
		}
		return m;
	}
	
	/** 获取期末借方余额和 */
	public Money caculateEndingDebit(String... codes) {
		Money m = new Money(0L);
		if (null != codes && codes.length > 0) {
			for (String code : codes) {
				AccountBalanceDataInfo data = map.get(code);
				if (null != data) {
					m.addTo(data.getEndingDebitBalance());
				}
			}
		}
		return m;
	}
	
	/** 获取期初借方余额和 */
	public Money caculateInitialDebit(String... codes) {
		Money m = new Money(0L);
		if (null != codes && codes.length > 0) {
			for (String code : codes) {
				AccountBalanceDataInfo data = map.get(code);
				if (null != data) {
					m.addTo(data.getInitialDebitBalance());
				}
			}
		}
		return m;
	}
	
	/** 获取期末贷方余额和 */
	public Money caculateEndingCredit(String... codes) {
		Money m = new Money(0L);
		if (null != codes && codes.length > 0) {
			for (String code : codes) {
				AccountBalanceDataInfo data = map.get(code);
				if (null != data) {
					m.addTo(data.getEndingCreditBalance());
				}
			}
		}
		return m;
	}
	
	/** 获取期初贷方余额和 */
	public Money caculateInitialCredit(String... codes) {
		Money m = new Money(0L);
		if (null != codes && codes.length > 0) {
			for (String code : codes) {
				AccountBalanceDataInfo data = map.get(code);
				if (null != data) {
					m.addTo(data.getInitialCreditBalance());
				}
			}
		}
		return m;
	}
	
	/** 获取本年借方累计 */
	public Money caculateYearDebit(String... codes) {
		Money m = new Money(0L);
		if (null != codes && codes.length > 0) {
			for (String code : codes) {
				AccountBalanceDataInfo data = map.get(code);
				if (null != data) {
					m.addTo(data.getYearDebitAmount());
				}
			}
		}
		return m;
	}
	
	/** 获取本年贷方累计 */
	public Money caculateYearCredit(String... codes) {
		Money m = new Money(0L);
		if (null != codes && codes.length > 0) {
			for (String code : codes) {
				AccountBalanceDataInfo data = map.get(code);
				if (null != data) {
					m.addTo(data.getYearCreditAmount());
				}
			}
		}
		return m;
	}
	
	/** 担保机构总收入 */
	public Money caculateGuarantorsTotalIncome(String... codes) {
		Money m = new Money(0L);
		if (null != codes && codes.length > 0) {
			for (String code : codes) {
				AccountBalanceDataInfo data = map.get(code);
				if (null != data) {
					m.addTo(data.getEndingCreditBalance());
				}
			}
		}
		return m;
	}
	
	/**
	 * 计算期末总资产 1开头的借方-贷方，加和
	 * @return
	 */
	public Money caculateCapital() {
		Money m = new Money(0L);
		List<AccountBalanceDataInfo> list = mapList.get("1");
		if (ListUtil.isNotEmpty(list)) {
			for (AccountBalanceDataInfo info : list) {
				m.addTo(info.getEndingDebitBalance().subtract(info.getEndingCreditBalance()));
			}
		}
		return m;
	}
	
	/**
	 * 计算期末总负债 2开头的所有贷方余额-借方余额
	 * @return
	 */
	public Money caculateDebt() {
		Money m = new Money(0L);
		List<AccountBalanceDataInfo> list = mapList.get("2");
		if (ListUtil.isNotEmpty(list)) {
			for (AccountBalanceDataInfo info : list) {
				m.addTo(info.getEndingCreditBalance().subtract(info.getEndingDebitBalance()));
			}
		}
		return m;
	}
	
	/**
	 * 计算期初总资产 1开头的借方-贷方，加和
	 * @return
	 */
	public Money caculateCapitalInitial() {
		Money m = new Money(0L);
		List<AccountBalanceDataInfo> list = mapList.get("1");
		if (ListUtil.isNotEmpty(list)) {
			for (AccountBalanceDataInfo info : list) {
				m.addTo(info.getInitialDebitBalance().subtract(info.getInitialCreditBalance()));
			}
		}
		return m;
	}
	
	/**
	 * 计算期初总负债 2开头的所有贷方余额-借方余额
	 * @return
	 */
	public Money caculateDebtInitial() {
		Money m = new Money(0L);
		List<AccountBalanceDataInfo> list = mapList.get("2");
		if (ListUtil.isNotEmpty(list)) {
			for (AccountBalanceDataInfo info : list) {
				m.addTo(info.getInitialCreditBalance().subtract(info.getInitialDebitBalance()));
			}
		}
		return m;
	}
	
	/**
	 * 借方余额-贷方余额
	 * @param codes
	 * @return
	 */
	public Money caculateDebitSubCredit(String debitCode, String creditCode) {
		Money m = new Money(0L);
		AccountBalanceDataInfo debit = map.get(debitCode);
		if (null != debit) {
			m.addTo(debit.getEndingDebitBalance());
		}
		AccountBalanceDataInfo credit = map.get(creditCode);
		if (null != credit) {
			m.subtractFrom(credit.getEndingCreditBalance());
		}
		return m;
	}
	
	/**
	 * 根据科目代码找科目名称
	 * @param code
	 * @return
	 */
	public String getNameByCode(String code) {
		AccountBalanceDataInfo info = map.get(code);
		return info.getName();
	}
}
