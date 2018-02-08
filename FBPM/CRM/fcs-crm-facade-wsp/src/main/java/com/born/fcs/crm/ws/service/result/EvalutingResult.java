package com.born.fcs.crm.ws.service.result;

import java.util.List;
import java.util.Map;

import com.born.fcs.crm.ws.service.info.CustomerFinanInfo;
import com.born.fcs.crm.ws.service.info.EvaluetingInfo;
import com.yjf.common.lang.result.ResultBase;

/**
 * 分级指标评价结果查询
 * */
public class EvalutingResult extends ResultBase {
	private static final long serialVersionUID = 2393694656481126651L;
	/** 评价结果集 */
	private List<EvaluetingInfo> evalutingList;
	/** 评价结果转化成 Map<evaluetingId,thisScore> */
	private Map<String, EvaluetingInfo> evalutingMap;
	
	/** 评价结果统计 */
	private Map<String, Object> evalueCount;
	
	/** 客户财务实际值 */
	private CustomerFinanInfo customerFinanInfo;
	
	public List<EvaluetingInfo> getEvalutingList() {
		return this.evalutingList;
	}
	
	public void setEvalutingList(List<EvaluetingInfo> evalutingList) {
		this.evalutingList = evalutingList;
	}
	
	public Map<String, EvaluetingInfo> getEvalutingMap() {
		return this.evalutingMap;
	}
	
	public void setEvalutingMap(Map<String, EvaluetingInfo> evalutingMap) {
		this.evalutingMap = evalutingMap;
	}
	
	public Map<String, Object> getEvalueCount() {
		return this.evalueCount;
	}
	
	public void setEvalueCount(Map<String, Object> evalueCount) {
		this.evalueCount = evalueCount;
	}
	
	public CustomerFinanInfo getCustomerFinanInfo() {
		return this.customerFinanInfo;
	}
	
	public void setCustomerFinanInfo(CustomerFinanInfo customerFinanInfo) {
		this.customerFinanInfo = customerFinanInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EvalutingResult [evalutingList=");
		builder.append(evalutingList);
		builder.append(", evalutingMap=");
		builder.append(evalutingMap);
		builder.append(", evalueCount=");
		builder.append(evalueCount);
		builder.append(", customerFinanInfo=");
		builder.append(customerFinanInfo);
		builder.append("]");
		return builder.toString();
	}
	
}
