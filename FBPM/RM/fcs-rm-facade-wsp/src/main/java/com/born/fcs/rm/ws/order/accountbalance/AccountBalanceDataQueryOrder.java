package com.born.fcs.rm.ws.order.accountbalance;

import com.born.fcs.pm.ws.base.QueryPageBase;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.rm.ws.enums.VersionEnum;

/**
 * 
 * 科目余额数据查询
 * 
 * @author lirz
 * 
 * 2016-8-4 下午3:33:55
 */
public class AccountBalanceDataQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 2077702653145045528L;
	
	private int reportYear;
	private int reportMonth;
	private String version = VersionEnum.NOW.code();
	private String codeFull;
	private String code;
	private String name;
	
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
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCodeFull() {
		return codeFull;
	}
	
	public void setCodeFull(String codeFull) {
		this.codeFull = codeFull;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
