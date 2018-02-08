package com.born.fcs.crm.ws.service.info;

import java.io.Serializable;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.born.fcs.pm.ws.info.financialkpi.FinancialKpiInfo;
import com.born.fcs.pm.ws.info.finvestigation.FinancialReviewKpiInfo;
import com.yjf.common.lang.util.StringUtil;

/**
 * 客户财务信息 实际值 pm 中 DataFinancialHelper 表单列名
 * 
 * */
public class CustomerFinanInfo implements Serializable {
	private static final long serialVersionUID = 7013344419059766740L;
	protected static ScriptEngine calculatingStr = new ScriptEngineManager()
		.getEngineByName("JavaScript");
	/** 项目管理中是否有财务信息 */
	private boolean haveFinaInfo = false;
	/** 净资产总额（亿元）= 资产总计-负债总计 */
	private String jzcze;
	
	/** 资产总计 */
	private String zczj;
	/** 负债总计 */
	private String fzzj;
	/** 资产负债率（%） */
	private String zcfzl;
	/** 流动比率= 流动资产/流动负债 */
	private String ldbl;
	
	/** 速动比率 =（流动资产-存货）/流动负债 */
	private String sdbl;
	
	/** 已获利息倍数 */
	private String yhlxbs;
	
	/** 现金流量情况 */
	private String xjllqk;
	
	/** 应收账款周转次数（次/年） yszkzzcs =data1/((data5+data6)/2) */
	private String yszkzzcs;
	/** 本年 主营业务收入 */
	private String data1;
	/** 上年主营业务收入 */
	private String data2;
	/** 上上年 主营业务收入 */
	private String data3;
	/** 上上上年 主营业务收入 */
	private String data4;
	
	/** 资产负债表中本期应收账款 **/
	private String data5;
	/** 资产负债表中:上一年应收账款 **/
	private String data6;
	
	/** 存货周转次数（次/年） cdzzcs=data7/((data8+data9)/2) */
	private String cdzzcs;
	/** 减：主营业务成本（融资成本） */
	private String data7;
	/** 资产负债表中本年存货 */
	private String data8;
	/** 资产负债表中上一年存货 */
	private String data9;
	
	/** 流动资产周转次数（次/年） ldzczzcs= data1/((data10+data11)/2) */
	private String ldzczzcs;
	/** 资产负债表中本期流动资产合计 */
	private String data10;
	/** 资产负债表中上一期流动资产合计 */
	private String data11;
	
	/** 净资产收益率（%） */
	private String jzcsyl;
	
	/** 总资产报酬率（%）= (data14 + data19)/((zczj+data20)/2) */
	private String zzcbcl;
	
	/** 主营业务利润率（%） zyywlrl = (data1-data12-data13)/data1×100% */
	private String zyywlrl;
	/** 主营业务成本 */
	private String data12;
	/** 主营业务税金及附加 */
	private String data13;
	
	/** 年主营业务收入规模及变动趋势 nzyywsr=（data1-data2）/data2*100% */
	private String nzyywsr;
	/** 近三年变化趋势 平均 */
	private String nzyywsr_avg;
	/** 第一年变化趋势 */
	private String nzyywsr_first;
	
	/** 年利润总额规模及变动趋势 nlrzegm=min[（本年度利润总额-上年度利润总额）/上年度利润总额*100% ] */
	private String nlrzegm;
	
	/** 近三年变化趋势 平均 */
	private String nlrzegm_avg;
	
	/** 第一年变化趋势 */
	private String nlrzegm_first;
	
	/** 本年度利润总额 */
	private String data14;
	/** 上年度利润总额 */
	private String data15;
	/** 上上本年度利润总额 */
	private String data16;
	/** 上上上本年度利润总额 */
	private String data17;
	/** 流动负债合计 */
	private String data18;
	
	/** 财务费用 */
	private String data19;
	/** 上一年资产总计 */
	private String data20;
	
	/** 所有者权益 */
	private String data21;
	/** 上一年所有者权益 */
	private String data22;
	
	/** 净利润 */
	private String data23;
	
	/** 包后 */
	public void initDataBH(Map<String, FinancialKpiInfo> map) {
		if (map != null) {
			this.haveFinaInfo = true;
			this.data18 = getValueBh("balance_total_current_liability", 0, map);
			this.zczj = getValueBh("balance_total_capital", 0, map);
			this.fzzj = getValueBh("balance_total_liability", 0, map);
			this.zcfzl = getValueBh("assetliability_ratio", 0, map);
			this.ldbl = getValueBh("iquidity_ratio", 0, map);
			this.sdbl = getValueBh("quick_ratio", 0, map);
			this.xjllqk = getValueBh("cashflow_the_net_cash_flow", 0, map);
			this.data1 = getValueBh("profit_main_business_income", 0, map);
			this.data2 = getValueBh("profit_main_business_income", 1, map);
			this.data3 = getValueBh("profit_main_business_income", 2, map);
			this.data4 = getValueBh("profit_main_business_income", 3, map);
			this.data5 = getValueBh("balance_receivables", 0, map);
			
			this.data6 = getValueBh("balance_receivables", 1, map);
			this.data7 = getValueBh("profit_main_business_cost", 0, map);
			this.data8 = getValueBh("balance_inventory", 0, map);
			
			this.data9 = getValueBh("balance_inventory", 1, map);
			this.data10 = getValueBh("balance_total_flow_capital", 0, map);
			this.data11 = getValueBh("balance_total_flow_capital", 1, map);
			
			this.jzcsyl = getValueBh("return_on_equity", 0, map);
			
			//			this.zzcbcl = getValue("rate_of_return_on_total_assets", 0, map);
			
			this.data12 = getValueBh("profit_main_business_cost", 0, map);
			
			this.data13 = getValueBh("profit_tax_and_extra_charges_of_main_business", 0, map);
			
			this.data14 = getValueBh("profit_total_profit", 0, map);
			
			this.data15 = getValueBh("profit_total_profit", 1, map);
			this.data16 = getValueBh("profit_total_profit", 2, map);
			this.data17 = getValueBh("profit_total_profit", 3, map);
			
			this.data19 = getValueBh("profit_financial_cost", 0, map);
			
			this.data20 = getValueBh("balance_total_capital", 1, map);
			
			this.data21 = getValueBh("balance_total_shareholders_equitytotal", 0, map);
			this.data22 = getValueBh("balance_total_shareholders_equitytotal", 1, map);
			
			this.data23 = getValueBh("profit_retained_profits", 0, map);
			
		}
	}
	
	/** 精致 */
	public void initDataZJ(Map<String, FinancialReviewKpiInfo> map) {
		if (map != null) {
			this.haveFinaInfo = true;
			this.data18 = getValue("balance_total_current_liability", 0, map);
			this.zczj = getValue("balance_total_capital", 0, map);
			this.fzzj = getValue("balance_total_liability", 0, map);
			this.zcfzl = getValue("assetliability_ratio", 0, map);
			this.ldbl = getValue("iquidity_ratio", 0, map);
			this.sdbl = getValue("quick_ratio", 0, map);
			this.xjllqk = getValue("cashflow_the_net_cash_flow", 0, map);
			this.data1 = getValue("profit_main_business_income", 0, map);
			this.data2 = getValue("profit_main_business_income", 1, map);
			this.data3 = getValue("profit_main_business_income", 2, map);
			this.data4 = getValue("profit_main_business_income", 3, map);
			this.data5 = getValue("balance_receivables", 0, map);
			
			this.data6 = getValue("balance_receivables", 1, map);
			this.data7 = getValue("profit_main_business_cost", 0, map);
			this.data8 = getValue("balance_inventory", 0, map);
			
			this.data9 = getValue("balance_inventory", 1, map);
			this.data10 = getValue("balance_total_flow_capital", 0, map);
			this.data11 = getValue("balance_total_flow_capital", 1, map);
			
			this.jzcsyl = getValue("return_on_equity", 0, map);
			
			//			this.zzcbcl = getValue("rate_of_return_on_total_assets", 0, map);
			
			this.data12 = getValue("profit_main_business_cost", 0, map);
			
			this.data13 = getValue("profit_tax_and_extra_charges_of_main_business", 0, map);
			
			this.data14 = getValue("profit_total_profit", 0, map);
			
			this.data15 = getValue("profit_total_profit", 1, map);
			this.data16 = getValue("profit_total_profit", 2, map);
			this.data17 = getValue("profit_total_profit", 3, map);
			
			this.data19 = getValue("profit_financial_cost", 0, map);
			
			this.data20 = getValue("balance_total_capital", 1, map);
			
			this.data21 = getValue("balance_total_shareholders_equitytotal", 0, map);
			this.data22 = getValue("balance_total_shareholders_equitytotal", 1, map);
			
			this.data23 = getValue("profit_retained_profits", 0, map);
			
		}
	}
	
	/** 获取值 */
	private String getValue(String dataName, int valueNum, Map<String, FinancialReviewKpiInfo> map) {
		String value = "";
		try {
			FinancialReviewKpiInfo info = map.get(dataName);
			if (valueNum == 0) {
				value = info != null ? info.getKpiValue() : "";
			} else if (valueNum == 1) {
				value = info != null ? info.getKpiValue1() : "";
			} else if (valueNum == 2) {
				value = info != null ? info.getKpiValue2() : "";
			} else if (valueNum == 3) {
				value = info != null ? info.getKpiValue3() : "";
			}
			
			value = value.replaceAll(",", "");
		} catch (Exception e) {
			value = "";
		}
		
		return checkValue(value);
	}
	
	/** 获取值 */
	private String getValueBh(String dataName, int valueNum, Map<String, FinancialKpiInfo> map) {
		String value = "";
		try {
			FinancialKpiInfo info = map.get(dataName);
			if (valueNum == 0) {
				value = info != null ? info.getKpiValue1() : "";
			} else if (valueNum == 1) {
				value = info != null ? info.getKpiValue2() : "";
			} else if (valueNum == 2) {
				value = info != null ? info.getKpiValue3() : "";
			} else if (valueNum == 3) {
				value = info != null ? info.getKpiValue4() : "";
			}
			
			value = value.replaceAll(",", "");
		} catch (Exception e) {
			value = "";
		}
		
		return checkValue(value);
	}
	
	public String getData19() {
		return this.data19;
	}
	
	public void setData19(String data19) {
		this.data19 = data19;
	}
	
	public boolean isHaveFinaInfo() {
		return this.haveFinaInfo;
	}
	
	public void setHaveFinaInfo(boolean haveFinaInfo) {
		this.haveFinaInfo = haveFinaInfo;
	}
	
	public String getJzcze() {
		try {
			//单位 亿
			if (StringUtil.isBlank(getZczj()) || StringUtil.isBlank(getFzzj())) {
				return "";
			}
			String cal = "(" + getZczj() + "-" + getFzzj() + ")/100000000".replaceAll("--", "+");
			return cal(cal);
		} catch (Exception e) {
			
		}
		return "";
	}
	
	public void setJzcze(String jzcze) {
		this.jzcze = jzcze;
	}
	
	public String getZcfzl() {
		try {
			if (StringUtil.isBlank(getFzzj()) || StringUtil.isBlank(getZczj())
				|| (boolean) calculatingStr.eval(getZczj() + "==0")) {
				return "";
			}
			
			String cal = getFzzj() + "/" + getZczj() + "*100";
			return cal(cal);
		} catch (Exception e) {
			
		}
		return "";
	}
	
	public void setZcfzl(String zcfzl) {
		this.zcfzl = zcfzl;
	}
	
	public String getLdbl() {
		
		try {
			if (StringUtil.isBlank(getData10()) || StringUtil.isBlank(getData18())
				|| (boolean) calculatingStr.eval(getData18() + "==0")) {
				return "";
			}
			String cal = getData10() + "/" + getData18();
			return cal(cal);
		} catch (Exception e) {
			
		}
		return "0";
		
	}
	
	public void setLdbl(String ldbl) {
		this.ldbl = ldbl;
	}
	
	public String getSdbl() {
		try {
			if (StringUtil.isBlank(getData10()) || StringUtil.isBlank(getData8())
				|| StringUtil.isBlank(getData18())
				|| (boolean) calculatingStr.eval(getData18() + "==0")) {
				return "";
			}
			String cal = "(" + getData10() + "-" + getData8() + ")/" + getData18();
			return cal(cal);
		} catch (Exception e) {
			
		}
		
		return "0";
		
	}
	
	public void setSdbl(String sdbl) {
		this.sdbl = sdbl;
	}
	
	public String getYhlxbs() {
		return this.yhlxbs;
	}
	
	public void setYhlxbs(String yhlxbs) {
		this.yhlxbs = yhlxbs;
	}
	
	public String getXjllqk() {
		return this.xjllqk;
	}
	
	public void setXjllqk(String xjllqk) {
		this.xjllqk = xjllqk;
	}
	
	public String getYszkzzcs() {//=data1/((data5+data6)/2)
		try {
			
			if (StringUtil.isBlank(getData1()) || StringUtil.isBlank(getData5())
				|| StringUtil.isBlank(getData6())) {
				return "";
			}
			
			if ((boolean) calculatingStr.eval("(data5+data6)==0".replaceAll("data5", getData5())
				.replace("data6", getData6()))) {
				return "";
			}
			String cal = "data1/((data5+data6)/2)".replace("data1", getData1())
				.replace("data5", getData5()).replace("data6", getData6()).replaceAll(",", "");//计算公式
			return cal(cal);
		} catch (Exception e) {
			
		}
		
		return "";
	}
	
	public void setYszkzzcs(String yszkzzcs) {
		this.yszkzzcs = yszkzzcs;
	}
	
	public String getCdzzcs() {
		try {
			if (StringUtil.isBlank(getData7()) || StringUtil.isBlank(getData8())
				|| StringUtil.isBlank(getData9())) {
				return "";
			}
			if ((boolean) calculatingStr.eval("(data8+data9)==0".replaceAll("data8", getData8())
				.replace("data9", getData9()))) {
				return "";
			}
			String cal = "data7/((data8+data9)/2)".replace("data7", getData7())
				.replace("data8", getData8()).replace("data9", getData9()).replaceAll(",", "");
			return cal(cal);
		} catch (Exception e) {
			
		}
		return "";
	}
	
	public void setCdzzcs(String cdzzcs) {
		this.cdzzcs = cdzzcs;
	}
	
	public String getLdzczzcs() {
		
		try {
			if (StringUtil.isBlank(getData1()) || StringUtil.isBlank(getData10())
				|| StringUtil.isBlank(getData11())) {
				return "";
			}
			if ((boolean) calculatingStr.eval("(data10+data11)==0"
				.replaceAll("data10", getData10()).replace("data11", getData11()))) {
				return "";
			}
			String cal = "data1 /((data10+data11)/2)".replace("data1 ", getData1())
				.replace("data10", getData10()).replace("data11", getData11()).replaceAll(",", "");
			return cal(cal);
		} catch (Exception e) {
			
		}
		return "";
		
	}
	
	public void setLdzczzcs(String ldzczzcs) {
		this.ldzczzcs = ldzczzcs;
	}
	
	public String getJzcsyl() {
		try {
			if (StringUtil.isBlank(getData21()) || StringUtil.isBlank(getData22())) {
				return "";
			}
			if ((boolean) calculatingStr.eval("(data21+data22)==0"
				.replaceAll("data21", getData21()).replace("data22", getData22()))) {
				return "";
			}
			if ((boolean) calculatingStr.eval("(data21+data22)>0".replaceAll("data21", getData21())
				.replaceAll("data22", getData22()))) {
				String cal = "data23/((data21+data22)/2)*100".replaceAll("data22", getData22())
					.replaceAll("data21", getData21()).replaceAll("data23", getData23());
				return cal(cal);
			} else {
				return "0";
			}
			
		} catch (Exception e) {
			
		}
		return cdzzcs;
	}
	
	public String getData21() {
		return this.data21;
	}
	
	public void setData21(String data21) {
		this.data21 = data21;
	}
	
	public String getData22() {
		return this.data22;
	}
	
	public void setData22(String data22) {
		this.data22 = data22;
	}
	
	public String getData23() {
		return this.data23;
	}
	
	public void setData23(String data23) {
		this.data23 = data23;
	}
	
	public void setJzcsyl(String jzcsyl) {
		this.jzcsyl = jzcsyl;
	}
	
	public String getZzcbcl() {
		try {
			if (StringUtil.isBlank(getData14()) || StringUtil.isBlank(getData19())
				|| StringUtil.isBlank(getData20()) || StringUtil.isBlank(getZczj())) {
				return "";
			}
			String a = "(zczj+data20)==0".replaceAll("zczj", getZczj()).replace("data20",
				getData20());
			if ((boolean) calculatingStr.eval(a)) {
				return "";
			}
			String cal = "(data14 + data19)/((zczj+data20)/2)*100"
				.replaceAll("data14 ", getData14()).replace("data19", getData19())
				.replace("zczj", getZczj()).replace("data20", getData20()).replaceAll(",", "");
			return cal(cal);
		} catch (Exception e) {
			
		}
		return "";
		
	}
	
	public String getData20() {
		return this.data20;
	}
	
	public void setData20(String data20) {
		this.data20 = data20;
	}
	
	public void setZzcbcl(String zzcbcl) {
		this.zzcbcl = zzcbcl;
	}
	
	public void setNlrzegm_first(String nlrzegm_first) {
		this.nlrzegm_first = nlrzegm_first;
	}
	
	public String getZyywlrl() {
		
		try {
			if (StringUtil.isBlank(getData1()) || StringUtil.isBlank(getData12())
				|| StringUtil.isBlank(getData12())
				|| (boolean) calculatingStr.eval(getData1() + "==0")) {
				return "";
			}
			String cal = "(data1 -data12-data13)/data1 *100".replaceAll("data1 ", getData1())
				.replace("data12", getData12()).replace("data13", getData13()).replaceAll(",", "");
			return cal(cal);
		} catch (Exception e) {
			
		}
		return "";
		
	}
	
	public void setZyywlrl(String zyywlrl) {
		this.zyywlrl = zyywlrl;
	}
	
	public String getNzyywsr_first() {
		try {
			if (StringUtil.isBlank(getData1()) || StringUtil.isBlank(getData2())
				|| (boolean) calculatingStr.eval(getData2() + "==0")) {
				return "";
			}
			double cal1 = (double) calculatingStr.eval("(data1-data2)/data2*100"
				.replaceAll("data1", getData1()).replaceAll("data2", getData2())
				.replaceAll(",", ""));
			
			return checkValue(String.valueOf(cal1).replace("NaN", "0").replace("Infinity", "0")
				.replace("-Infinity", "0"));
		} catch (Exception e) {
			
		}
		return "";
	}
	
	public void setNzyywsr_first(String nzyywsr_first) {
		this.nzyywsr_first = nzyywsr_first;
	}
	
	public String getNzyywsr() {
		try {
			if (StringUtil.isBlank(getData1()) || StringUtil.isBlank(getData2())
				|| StringUtil.isBlank(getData3()) || StringUtil.isBlank(getData4())
				|| (boolean) calculatingStr.eval(getData2() + "==0")
				|| (boolean) calculatingStr.eval(getData3() + "==0")
				|| (boolean) calculatingStr.eval(getData4() + "==0")) {
				return "";
			}
			double cal1 = (double) calculatingStr.eval("(data1-data2)/data2*100"
				.replaceAll("data1", getData1()).replaceAll("data2", getData2())
				.replaceAll(",", ""));
			double cal2 = (double) calculatingStr.eval("(data2-data3)/data3*100"
				.replaceAll("data2", getData2()).replaceAll("data3", getData3())
				.replaceAll(",", ""));
			double cal3 = (double) calculatingStr.eval("(data3-data4)/data4*100"
				.replaceAll("data3", getData3()).replaceAll("data4", getData4())
				.replaceAll(",", ""));
			return checkValue(String.valueOf(Math.min(Math.min(cal1, cal2), cal3))
				.replace("NaN", "0").replace("Infinity", "0").replace("-Infinity", "0"));
		} catch (Exception e) {
			
		}
		return "";
	}
	
	public void setNzyywsr(String nzyywsr) {
		this.nzyywsr = nzyywsr;
	}
	
	public String getNlrzegm_first() {
		
		try {
			if (StringUtil.isBlank(getData14()) || StringUtil.isBlank(getData15())
				|| StringUtil.isBlank(getData16()) || StringUtil.isBlank(getData17())
				|| (boolean) calculatingStr.eval(getData15() + "==0")) {
				return "";
			}
			double cal1 = (double) calculatingStr.eval("(data14-data15)/data15*100"
				.replaceAll("data14", getData14()).replaceAll("data15", getData15())
				.replaceAll(",", ""));
			
			return checkValue(String.valueOf(cal1).replace("NaN", "0").replace("Infinity", "0")
				.replace("-Infinity", "0"));
		} catch (Exception e) {
			
		}
		return "0";
		
	}
	
	public String getNlrzegm() {
		
		try {
			if (StringUtil.isBlank(getData14()) || StringUtil.isBlank(getData15())
				|| StringUtil.isBlank(getData16()) || StringUtil.isBlank(getData17())
				|| (boolean) calculatingStr.eval(getData15() + "==0")
				|| (boolean) calculatingStr.eval(getData16() + "==0")
				|| (boolean) calculatingStr.eval(getData17() + "==0")) {
				return "";
			}
			double cal1 = (double) calculatingStr.eval("(data14-data15)/data15*100"
				.replaceAll("data14", getData14()).replaceAll("data15", getData15())
				.replaceAll(",", ""));
			double cal2 = (double) calculatingStr.eval("(data15-data16)/data16*100"
				.replaceAll("data15", getData15()).replaceAll("data16", getData16())
				.replaceAll(",", ""));
			double cal3 = (double) calculatingStr.eval("(data16-data17)/data17*100"
				.replaceAll("data16", getData16()).replaceAll("data17", getData17())
				.replaceAll(",", ""));
			return checkValue(String.valueOf(Math.min(Math.min(cal1, cal2), cal3))
				.replace("NaN", "0").replace("Infinity", "0").replace("-Infinity", "0"));
		} catch (Exception e) {
			
		}
		return "0";
		
	}
	
	public void setNlrzegm(String nlrzegm) {
		this.nlrzegm = nlrzegm;
	}
	
	public String getData1() {
		return this.data1;
	}
	
	public void setData1(String data1) {
		this.data1 = data1;
	}
	
	public String getData2() {
		return this.data2;
	}
	
	public void setData2(String data2) {
		this.data2 = data2;
	}
	
	public String getData3() {
		return this.data3;
	}
	
	public void setData3(String data3) {
		this.data3 = data3;
	}
	
	public String getData4() {
		return this.data4;
	}
	
	public void setData4(String data4) {
		this.data4 = data4;
	}
	
	public String getData5() {
		return this.data5;
	}
	
	public String getData18() {
		return this.data18;
	}
	
	public void setData18(String data18) {
		this.data18 = data18;
	}
	
	public void setData5(String data5) {
		this.data5 = data5;
	}
	
	public String getData6() {
		return this.data6;
	}
	
	public void setData6(String data6) {
		this.data6 = data6;
	}
	
	public String getData7() {
		return this.data7;
	}
	
	public void setData7(String data7) {
		this.data7 = data7;
	}
	
	public String getData8() {
		return this.data8;
	}
	
	public void setData8(String data8) {
		this.data8 = data8;
	}
	
	public String getData9() {
		return this.data9;
	}
	
	public void setData9(String data9) {
		this.data9 = data9;
	}
	
	public String getData10() {
		return this.data10;
	}
	
	public void setData10(String data10) {
		this.data10 = data10;
	}
	
	public String getData11() {
		return this.data11;
	}
	
	public void setData11(String data11) {
		this.data11 = data11;
	}
	
	public String getData12() {
		return this.data12;
	}
	
	public void setData12(String data12) {
		this.data12 = data12;
	}
	
	public String getData13() {
		return this.data13;
	}
	
	public void setData13(String data13) {
		this.data13 = data13;
	}
	
	public String getZczj() {
		return this.zczj;
	}
	
	public void setZczj(String zczj) {
		this.zczj = zczj;
	}
	
	public String getFzzj() {
		return this.fzzj;
	}
	
	public void setFzzj(String fzzj) {
		this.fzzj = fzzj;
	}
	
	public String getNzyywsr_avg() {
		try {
			if (StringUtil.isBlank(getData1()) || StringUtil.isBlank(getData2())
				|| StringUtil.isBlank(getData3()) || StringUtil.isBlank(getData4())
				|| (boolean) calculatingStr.eval(getData2() + "==0")
				|| (boolean) calculatingStr.eval(getData3() + "==0")
				|| (boolean) calculatingStr.eval(getData4() + "==0")) {
				return "";
			}
			String cal = "[(data1-data2)/data2*100+(data2-data3)/data3*100+(data3-data4)/data4*100]/3"
				.replaceAll("data1", getData1()).replaceAll("data2", getData2())
				.replaceAll("data3", getData3()).replaceAll("data4", getData4())
				.replaceAll(",", "");
			return cal(cal);
		} catch (Exception e) {
			
		}
		return "";
		
	}
	
	public void setNzyywsr_avg(String nzyywsr_avg) {
		this.nzyywsr_avg = nzyywsr_avg;
	}
	
	public String getNlrzegm_avg() {
		try {
			if (StringUtil.isBlank(getData14()) || StringUtil.isBlank(getData15())
				|| StringUtil.isBlank(getData16()) || StringUtil.isBlank(getData17())
				|| (boolean) calculatingStr.eval(getData15() + "==0")
				|| (boolean) calculatingStr.eval(getData16() + "==0")
				|| (boolean) calculatingStr.eval(getData17() + "==0")) {
				return "";
			}
			String cal = "[(data14-data15)/data15*100+(data15-data16)/data16*100+(data16-data17)/data17*100]/3"
				.replaceAll("data14", getData14()).replaceAll("data15", getData15())
				.replaceAll("data16", getData16()).replaceAll("data17", getData17())
				.replaceAll(",", "");
			return cal(cal);
		} catch (Exception e) {
			
		}
		return "";
		
	}
	
	public void setNlrzegm_avg(String nlrzegm_avg) {
		this.nlrzegm_avg = nlrzegm_avg;
	}
	
	public String getData14() {
		return this.data14;
	}
	
	public void setData14(String data14) {
		this.data14 = data14;
	}
	
	public String getData15() {
		return this.data15;
	}
	
	public void setData15(String data15) {
		this.data15 = data15;
	}
	
	public String getData16() {
		return this.data16;
	}
	
	public void setData16(String data16) {
		this.data16 = data16;
	}
	
	public String getData17() {
		return this.data17;
	}
	
	public void setData17(String data17) {
		this.data17 = data17;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerFinanInfo [haveFinaInfo=");
		builder.append(haveFinaInfo);
		builder.append(", jzcze=");
		builder.append(jzcze);
		builder.append(", zcfzl=");
		builder.append(zcfzl);
		builder.append(", ldbl=");
		builder.append(ldbl);
		builder.append(", sdbl=");
		builder.append(sdbl);
		builder.append(", yhlxbs=");
		builder.append(yhlxbs);
		builder.append(", xjllqk=");
		builder.append(xjllqk);
		builder.append(", yszkzzcs=");
		builder.append(yszkzzcs);
		builder.append(", data1=");
		builder.append(data1);
		builder.append(", data2=");
		builder.append(data2);
		builder.append(", data3=");
		builder.append(data3);
		builder.append(", data4=");
		builder.append(data4);
		builder.append(", data5=");
		builder.append(data5);
		builder.append(", data6=");
		builder.append(data6);
		builder.append(", cdzzcs=");
		builder.append(cdzzcs);
		builder.append(", data7=");
		builder.append(data7);
		builder.append(", data8=");
		builder.append(data8);
		builder.append(", data9=");
		builder.append(data9);
		builder.append(", ldzczzcs=");
		builder.append(ldzczzcs);
		builder.append(", data10=");
		builder.append(data10);
		builder.append(", data11=");
		builder.append(data11);
		builder.append(", jzcsyl=");
		builder.append(jzcsyl);
		builder.append(", zzcbcl=");
		builder.append(zzcbcl);
		builder.append(", zyywlrl=");
		builder.append(zyywlrl);
		builder.append(", data12=");
		builder.append(data12);
		builder.append(", data13=");
		builder.append(data13);
		builder.append(", nzyywsr=");
		builder.append(nzyywsr);
		builder.append(", nzyywsr_avg=");
		builder.append(nzyywsr_avg);
		builder.append(", nlrzegm=");
		builder.append(nlrzegm);
		builder.append(", nlrzegm_avg=");
		builder.append(nlrzegm_avg);
		builder.append(", data14=");
		builder.append(data14);
		builder.append(", data15=");
		builder.append(data15);
		builder.append(", data16=");
		builder.append(data16);
		builder.append(", data17=");
		builder.append(data17);
		builder.append("]");
		return builder.toString();
	}
	
	/** 计算 */
	private String cal(String cal) {
		
		try {
			return checkValue(String.valueOf(calculatingStr.eval(cal)).replace("NaN", "0")
				.replace("Infinity", "0").replace("-Infinity", "0"));
		} catch (ScriptException e) {
		}
		return "";
		
	}
	
	/** 处理结果值 */
	public static String checkValue(String s) {
		
		try {
			if (StringUtil.isBlank(s)) {
				return "";
			}
			double d = Double.parseDouble(s);
			double rs = Math.round(d * 100) / 100.0;
			s = String.valueOf(rs);
		} catch (Exception e) {
			System.out.println(s);
		}
		
		return s;
		
	}
	
}
