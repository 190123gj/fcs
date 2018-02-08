package com.born.fcs.rm.ws.info.report.outer;

import java.util.List;

import com.born.fcs.rm.ws.info.report.ReportInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * W2-（中担协）融资性担保机构资产负债情况
 * 
 * @author lirz
 * 
 * 2016-8-11 下午5:15:35
 */
public class GuaranteeDebtInfo extends ReportInfo {
	
	private static final long serialVersionUID = 4796354956821590102L;
	
	private String info = "W2-（中担协）融资性担保机构资产负债情况";
	private List<Money> ends;
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public List<Money> getEnds() {
		return ends;
	}
	
	public void setEnds(List<Money> ends) {
		this.ends = ends;
	}
	
}
