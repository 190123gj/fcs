package com.born.fcs.crm.ws.service.result;

import java.util.List;
import java.util.Map;

import com.born.fcs.crm.ws.service.info.FinancialSetInfo;
import com.born.fcs.crm.ws.service.info.ListDataInfo;
import com.yjf.common.lang.result.ResultBase;

/**
 * 财务指标配置查询
 * */
public class FinancialSetResult extends ResultBase {
	
	private static final long serialVersionUID = 4124662541482282710L;
	/** 结果集 */
	List<FinancialSetInfo> list;
	/** 查询结果转化成Map<typeChile,FinancialSetInfo> */
	Map<String, FinancialSetInfo> mapResult;
	/** 年主营业务收入规模及变动趋势 最近三个会计年度 */
	List<ListDataInfo> listData1;
	/** 年利润总额规模及变动趋势 最近三个会计年度 */
	List<ListDataInfo> listData2;
	
	public List<FinancialSetInfo> getList() {
		return this.list;
	}
	
	public void setList(List<FinancialSetInfo> list) {
		this.list = list;
	}
	
	public Map<String, FinancialSetInfo> getMapResult() {
		return this.mapResult;
	}
	
	public void setMapResult(Map<String, FinancialSetInfo> mapResult) {
		this.mapResult = mapResult;
	}
	
	public List<ListDataInfo> getListData1() {
		return this.listData1;
	}
	
	public void setListData1(List<ListDataInfo> listData1) {
		this.listData1 = listData1;
	}
	
	public List<ListDataInfo> getListData2() {
		return this.listData2;
	}
	
	public void setListData2(List<ListDataInfo> listData2) {
		this.listData2 = listData2;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FinancialSetResult [list=");
		builder.append(list);
		builder.append(", mapResult=");
		builder.append(mapResult);
		builder.append(", listData1=");
		builder.append(listData1);
		builder.append(", listData2=");
		builder.append(listData2);
		builder.append("]");
		return builder.toString();
	}
	
}
