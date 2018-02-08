package com.born.fcs.rm.ws.order.accountbalance;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.rm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 科目余额列表
 * 
 * @author lirz
 * 
 * 2016-8-4 下午3:33:55
 */
public class AccountBalanceOrder extends ValidateOrderBase{
	
	private static final long serialVersionUID = -3380566510122322570L;
	
	private long accountBalanceId;
	private int reportYear;
	private int reportMonth;
//	private String version;
	private long operatorId;
	private String operatorAccount;
	private String operatorName;
	
	private List<AccountBalanceDataOrder> datas;
	
	@Override
	public void check() {
		validateGreaterThan(reportYear, "会计期间-年");
		validateGreaterThan(reportMonth, "会计期间-月");
		validateHasText(operatorName, "操作人");
	}
	
	public long getAccountBalanceId() {
		return accountBalanceId;
	}
	
	public void setAccountBalanceId(long accountBalanceId) {
		this.accountBalanceId = accountBalanceId;
	}
	
	public int getReportYear() {
		return reportYear;
	}
	
	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}
	
	public int getReportMonth() {
		return reportMonth;
	}
	
	public void setReportMonth(int reportMonth) {
		this.reportMonth = reportMonth;
	}
	
	public long getOperatorId() {
		return operatorId;
	}
	
	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}
	
	public String getOperatorAccount() {
		return operatorAccount;
	}
	
	public void setOperatorAccount(String operatorAccount) {
		this.operatorAccount = operatorAccount;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public List<AccountBalanceDataOrder> getDatas() {
		return datas;
	}

	public void setDatas(List<AccountBalanceDataOrder> datas) {
		this.datas = datas;
	}

	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
