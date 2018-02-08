/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午7:59:22 创建
 */
package com.born.fcs.fm.ws.info.forecast;

import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 所有预测信息集合
 * @author hjiajie
 * 
 */
public class SysForecastParamAllInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1L;
	
	//////流出
	/** 银行融资担保 **/
	//用款事由为保证金划出
	private String outYhrzdbTime = "30";
	private TimeUnitEnum outYhrzdbTimeType = TimeUnitEnum.DAY;
	/** 债权融资担保 **/
	//用款事由为保证金划出
	private String outXjrzdbTime = "30";
	private TimeUnitEnum outZjrzdbTimeType = TimeUnitEnum.DAY;
	/** 金融产品担保 **/
	//用款事由为保证金划出
	private String outJrcpdbTime = "30";
	private TimeUnitEnum outJrcpdbTimeType = TimeUnitEnum.DAY;
	/** 非融资担保业务 **/
	//用款事由为保证金划出
	private String outFrzdbywTime = "30";
	private TimeUnitEnum outFrzdbywTimeType = TimeUnitEnum.DAY;
	/** 再担保业务 **/
	//用款事由为保证金划出
	private String outZdbywTime = "30";
	private TimeUnitEnum outZdbywTimeType = TimeUnitEnum.DAY;
	/** 委托贷款业务 **/
	//用款事由为“委贷放款”
	private String outWtdkywTime = "15";
	private TimeUnitEnum outWtdkywTimeType = TimeUnitEnum.DAY;
	/** 小贷业务 **/
	//用款事由为保证金划出
	private String outXdywTime = "30";
	private TimeUnitEnum outXdywTimeType = TimeUnitEnum.DAY;
	/** 流出其他业务 **/
	//用款事由为保证金划出
	private String outQtywTime = "30";
	private TimeUnitEnum outqtywTimeType = TimeUnitEnum.DAY;
	/** 理财业务 **/
	//立项申请单中“拟购买时间”为计划用款时间；产品转让时“回购时间”为计划用款时间
	private String outLcywTime;
	private TimeUnitEnum outLcywTimeType;
	/** 支付管理系统 **/
	//计划用款时间
	private String outXfglxtTime = "10";
	private TimeUnitEnum outXfglxtTimeType = TimeUnitEnum.DAY;
	private String outXfglxtOtherTime = "3";
	private TimeUnitEnum outXfglxtOtherTimeType = TimeUnitEnum.DAY;
	/** 退费申请单 **/
	//计划用款时间，审核未通过，则计划取消
	private String outTfsqdTime = "15";
	private TimeUnitEnum outTfsqdTimeType = TimeUnitEnum.DAY;
	private String outTfsqdOtherTime = "3";
	private TimeUnitEnum outTfsqdOtherTimeType = TimeUnitEnum.DAY;
	/** 资产受让 **/
	//计划用款时间
	private String outZcsqTime;
	private TimeUnitEnum outZcsqTimeType;
	private String outZcsqOtherTime;
	private TimeUnitEnum outZcsqOtherTimeType;
	/** 受托清收 **/
	//计划用款时间
	private String outStqsTime;
	private TimeUnitEnum outStqsTimeType;
	/** 代偿款划出 **/
	//风险处置会会议纪要中的代偿截止时间
	private String outDckhcTime;
	private TimeUnitEnum outDckhcTimeType;
	/** 退还客户保证金 **/
	//项目到期日为退还客户保证金的预测时间
	private String outThkhbzjTime;
	private TimeUnitEnum outThkhbzjTimeType;
	
	///////// 流入
	/** 银行融资担保 **/
	//流入事由为“收取担保费”
	private String inYhrzdbTime = "30";
	private TimeUnitEnum inYhrzdbTimeType = TimeUnitEnum.DAY;
	/** 债权融资担保 **/
	//流入事由为“收取担保费”
	private String inZjrzdbtime = "60";
	private TimeUnitEnum inZjrzdbTimeType = TimeUnitEnum.DAY;
	/** 金融产品担保 **/
	//流入事由为“收取担保费”
	private String inJrcpdbTime = "30";
	private TimeUnitEnum inJrcpdbTimeType = TimeUnitEnum.DAY;
	/** 非融资担保业务 **/
	//流入事由为“收取担保费”
	private String inFrzdbywTime = "30";
	private TimeUnitEnum inFrzdbywTimeType = TimeUnitEnum.DAY;
	/** 再担保业务 **/
	//流入事由为“收取担保费”
	private String inZdbywTime = "30";
	private TimeUnitEnum inZdbywTimeType = TimeUnitEnum.DAY;
	/** 委托贷款业务 **/
	//流入事由为“收取委贷利息”，委贷项目的还款计划中的时间为委贷本金收回时间
	private String inWtdkywTime = "30";
	private TimeUnitEnum inWtdkywTimeType = TimeUnitEnum.DAY;
	/** 承销业务 **/
	//流入事由为“收取承销费”
	private String inCxywTime = "60";
	private TimeUnitEnum inCxywTimeType = TimeUnitEnum.DAY;
	/** 小贷业务 **/
	//流入事由为“收取承销费”
	private String inXdywTime = "30";
	private TimeUnitEnum inXdywTimeType = TimeUnitEnum.DAY;
	/** 理财业务 **/
	//理财产品到期时间为预测资金流入时间；理财产品转让时间为预测资金流入时间
	private String inLcyTime;
	private TimeUnitEnum inLcywTimeType;
	/** 经纪业务 **/
	//流入事由为“收费”
	private String inJjywTime = "30";
	private TimeUnitEnum inJjywTimeType = TimeUnitEnum.DAY;
	/** 其他业务 **/
	//流入事由为“收费”
	private String inQtywTime = "30";
	private TimeUnitEnum inQtywTimeType = TimeUnitEnum.DAY;
	/** 代偿款收回 **/
	//流入事由为“代偿款收回”
	private String inDckshTime = "3";
	private TimeUnitEnum inDckshTimeType = TimeUnitEnum.YEAR;
	
	/** 资产转让 **/
	//流入事由为“资产转让”
	private String inXczrTime = "10";
	private TimeUnitEnum inXczrTimeType = TimeUnitEnum.DAY;
	
	public String getOutYhrzdbTime() {
		return this.outYhrzdbTime;
	}
	
	public String getInXczrTime() {
		return this.inXczrTime;
	}
	
	public void setInXczrTime(String inXczrTime) {
		this.inXczrTime = inXczrTime;
	}
	
	public TimeUnitEnum getInXczrTimeType() {
		return this.inXczrTimeType;
	}
	
	public void setInXczrTimeType(TimeUnitEnum inXczrTimeType) {
		this.inXczrTimeType = inXczrTimeType;
	}
	
	public void setOutYhrzdbTime(String outYhrzdbTime) {
		this.outYhrzdbTime = outYhrzdbTime;
	}
	
	public TimeUnitEnum getOutYhrzdbTimeType() {
		return this.outYhrzdbTimeType;
	}
	
	public void setOutYhrzdbTimeType(TimeUnitEnum outYhrzdbTimeType) {
		this.outYhrzdbTimeType = outYhrzdbTimeType;
	}
	
	public String getOutXjrzdbTime() {
		return this.outXjrzdbTime;
	}
	
	public void setOutXjrzdbTime(String outXjrzdbTime) {
		this.outXjrzdbTime = outXjrzdbTime;
	}
	
	public TimeUnitEnum getOutZjrzdbTimeType() {
		return this.outZjrzdbTimeType;
	}
	
	public void setOutZjrzdbTimeType(TimeUnitEnum outZjrzdbTimeType) {
		this.outZjrzdbTimeType = outZjrzdbTimeType;
	}
	
	public String getOutJrcpdbTime() {
		return this.outJrcpdbTime;
	}
	
	public void setOutJrcpdbTime(String outJrcpdbTime) {
		this.outJrcpdbTime = outJrcpdbTime;
	}
	
	public TimeUnitEnum getOutJrcpdbTimeType() {
		return this.outJrcpdbTimeType;
	}
	
	public void setOutJrcpdbTimeType(TimeUnitEnum outJrcpdbTimeType) {
		this.outJrcpdbTimeType = outJrcpdbTimeType;
	}
	
	public String getOutFrzdbywTime() {
		return this.outFrzdbywTime;
	}
	
	public void setOutFrzdbywTime(String outFrzdbywTime) {
		this.outFrzdbywTime = outFrzdbywTime;
	}
	
	public TimeUnitEnum getOutFrzdbywTimeType() {
		return this.outFrzdbywTimeType;
	}
	
	public void setOutFrzdbywTimeType(TimeUnitEnum outFrzdbywTimeType) {
		this.outFrzdbywTimeType = outFrzdbywTimeType;
	}
	
	public String getOutZdbywTime() {
		return this.outZdbywTime;
	}
	
	public void setOutZdbywTime(String outZdbywTime) {
		this.outZdbywTime = outZdbywTime;
	}
	
	public TimeUnitEnum getOutZdbywTimeType() {
		return this.outZdbywTimeType;
	}
	
	public void setOutZdbywTimeType(TimeUnitEnum outZdbywTimeType) {
		this.outZdbywTimeType = outZdbywTimeType;
	}
	
	public String getOutWtdkywTime() {
		return this.outWtdkywTime;
	}
	
	public void setOutWtdkywTime(String outWtdkywTime) {
		this.outWtdkywTime = outWtdkywTime;
	}
	
	public TimeUnitEnum getOutWtdkywTimeType() {
		return this.outWtdkywTimeType;
	}
	
	public void setOutWtdkywTimeType(TimeUnitEnum outWtdkywTimeType) {
		this.outWtdkywTimeType = outWtdkywTimeType;
	}
	
	public String getOutXdywTime() {
		return this.outXdywTime;
	}
	
	public void setOutXdywTime(String outXdywTime) {
		this.outXdywTime = outXdywTime;
	}
	
	public TimeUnitEnum getOutXdywTimeType() {
		return this.outXdywTimeType;
	}
	
	public void setOutXdywTimeType(TimeUnitEnum outXdywTimeType) {
		this.outXdywTimeType = outXdywTimeType;
	}
	
	public String getOutQtywTime() {
		return this.outQtywTime;
	}
	
	public void setOutQtywTime(String outQtywTime) {
		this.outQtywTime = outQtywTime;
	}
	
	public TimeUnitEnum getOutqtywTimeType() {
		return this.outqtywTimeType;
	}
	
	public void setOutqtywTimeType(TimeUnitEnum outqtywTimeType) {
		this.outqtywTimeType = outqtywTimeType;
	}
	
	public String getOutLcywTime() {
		return this.outLcywTime;
	}
	
	public void setOutLcywTime(String outLcywTime) {
		this.outLcywTime = outLcywTime;
	}
	
	public TimeUnitEnum getOutLcywTimeType() {
		return this.outLcywTimeType;
	}
	
	public void setOutLcywTimeType(TimeUnitEnum outLcywTimeType) {
		this.outLcywTimeType = outLcywTimeType;
	}
	
	public String getOutXfglxtTime() {
		return this.outXfglxtTime;
	}
	
	public void setOutXfglxtTime(String outXfglxtTime) {
		this.outXfglxtTime = outXfglxtTime;
	}
	
	public TimeUnitEnum getOutXfglxtTimeType() {
		return this.outXfglxtTimeType;
	}
	
	public void setOutXfglxtTimeType(TimeUnitEnum outXfglxtTimeType) {
		this.outXfglxtTimeType = outXfglxtTimeType;
	}
	
	public String getOutTfsqdTime() {
		return this.outTfsqdTime;
	}
	
	public void setOutTfsqdTime(String outTfsqdTime) {
		this.outTfsqdTime = outTfsqdTime;
	}
	
	public TimeUnitEnum getOutTfsqdTimeType() {
		return this.outTfsqdTimeType;
	}
	
	public void setOutTfsqdTimeType(TimeUnitEnum outTfsqdTimeType) {
		this.outTfsqdTimeType = outTfsqdTimeType;
	}
	
	public String getOutZcsqTime() {
		return this.outZcsqTime;
	}
	
	public void setOutZcsqTime(String outZcsqTime) {
		this.outZcsqTime = outZcsqTime;
	}
	
	public TimeUnitEnum getOutZcsqTimeType() {
		return this.outZcsqTimeType;
	}
	
	public void setOutZcsqTimeType(TimeUnitEnum outZcsqTimeType) {
		this.outZcsqTimeType = outZcsqTimeType;
	}
	
	public String getOutStqsTime() {
		return this.outStqsTime;
	}
	
	public void setOutStqsTime(String outStqsTime) {
		this.outStqsTime = outStqsTime;
	}
	
	public TimeUnitEnum getOutStqsTimeType() {
		return this.outStqsTimeType;
	}
	
	public void setOutStqsTimeType(TimeUnitEnum outStqsTimeType) {
		this.outStqsTimeType = outStqsTimeType;
	}
	
	public String getOutDckhcTime() {
		return this.outDckhcTime;
	}
	
	public void setOutDckhcTime(String outDckhcTime) {
		this.outDckhcTime = outDckhcTime;
	}
	
	public TimeUnitEnum getOutDckhcTimeType() {
		return this.outDckhcTimeType;
	}
	
	public void setOutDckhcTimeType(TimeUnitEnum outDckhcTimeType) {
		this.outDckhcTimeType = outDckhcTimeType;
	}
	
	public String getOutThkhbzjTime() {
		return this.outThkhbzjTime;
	}
	
	public void setOutThkhbzjTime(String outThkhbzjTime) {
		this.outThkhbzjTime = outThkhbzjTime;
	}
	
	public TimeUnitEnum getOutThkhbzjTimeType() {
		return this.outThkhbzjTimeType;
	}
	
	public void setOutThkhbzjTimeType(TimeUnitEnum outThkhbzjTimeType) {
		this.outThkhbzjTimeType = outThkhbzjTimeType;
	}
	
	public String getInYhrzdbTime() {
		return this.inYhrzdbTime;
	}
	
	public void setInYhrzdbTime(String inYhrzdbTime) {
		this.inYhrzdbTime = inYhrzdbTime;
	}
	
	public TimeUnitEnum getInYhrzdbTimeType() {
		return this.inYhrzdbTimeType;
	}
	
	public void setInYhrzdbTimeType(TimeUnitEnum inYhrzdbTimeType) {
		this.inYhrzdbTimeType = inYhrzdbTimeType;
	}
	
	public String getInZjrzdbtime() {
		return this.inZjrzdbtime;
	}
	
	public void setInZjrzdbtime(String inZjrzdbtime) {
		this.inZjrzdbtime = inZjrzdbtime;
	}
	
	public TimeUnitEnum getInZjrzdbTimeType() {
		return this.inZjrzdbTimeType;
	}
	
	public void setInZjrzdbTimeType(TimeUnitEnum inZjrzdbTimeType) {
		this.inZjrzdbTimeType = inZjrzdbTimeType;
	}
	
	public String getInJrcpdbTime() {
		return this.inJrcpdbTime;
	}
	
	public void setInJrcpdbTime(String inJrcpdbTime) {
		this.inJrcpdbTime = inJrcpdbTime;
	}
	
	public TimeUnitEnum getInJrcpdbTimeType() {
		return this.inJrcpdbTimeType;
	}
	
	public void setInJrcpdbTimeType(TimeUnitEnum inJrcpdbTimeType) {
		this.inJrcpdbTimeType = inJrcpdbTimeType;
	}
	
	public String getInFrzdbywTime() {
		return this.inFrzdbywTime;
	}
	
	public void setInFrzdbywTime(String inFrzdbywTime) {
		this.inFrzdbywTime = inFrzdbywTime;
	}
	
	public TimeUnitEnum getInFrzdbywTimeType() {
		return this.inFrzdbywTimeType;
	}
	
	public void setInFrzdbywTimeType(TimeUnitEnum inFrzdbywTimeType) {
		this.inFrzdbywTimeType = inFrzdbywTimeType;
	}
	
	public String getInZdbywTime() {
		return this.inZdbywTime;
	}
	
	public void setInZdbywTime(String inZdbywTime) {
		this.inZdbywTime = inZdbywTime;
	}
	
	public TimeUnitEnum getInZdbywTimeType() {
		return this.inZdbywTimeType;
	}
	
	public void setInZdbywTimeType(TimeUnitEnum inZdbywTimeType) {
		this.inZdbywTimeType = inZdbywTimeType;
	}
	
	public String getInWtdkywTime() {
		return this.inWtdkywTime;
	}
	
	public void setInWtdkywTime(String inWtdkywTime) {
		this.inWtdkywTime = inWtdkywTime;
	}
	
	public TimeUnitEnum getInWtdkywTimeType() {
		return this.inWtdkywTimeType;
	}
	
	public void setInWtdkywTimeType(TimeUnitEnum inWtdkywTimeType) {
		this.inWtdkywTimeType = inWtdkywTimeType;
	}
	
	public String getInCxywTime() {
		return this.inCxywTime;
	}
	
	public void setInCxywTime(String inCxywTime) {
		this.inCxywTime = inCxywTime;
	}
	
	public TimeUnitEnum getInCxywTimeType() {
		return this.inCxywTimeType;
	}
	
	public void setInCxywTimeType(TimeUnitEnum inCxywTimeType) {
		this.inCxywTimeType = inCxywTimeType;
	}
	
	public String getInXdywTime() {
		return this.inXdywTime;
	}
	
	public void setInXdywTime(String inXdywTime) {
		this.inXdywTime = inXdywTime;
	}
	
	public TimeUnitEnum getInXdywTimeType() {
		return this.inXdywTimeType;
	}
	
	public void setInXdywTimeType(TimeUnitEnum inXdywTimeType) {
		this.inXdywTimeType = inXdywTimeType;
	}
	
	public String getInLcyTime() {
		return this.inLcyTime;
	}
	
	public void setInLcyTime(String inLcyTime) {
		this.inLcyTime = inLcyTime;
	}
	
	public TimeUnitEnum getInLcywTimeType() {
		return this.inLcywTimeType;
	}
	
	public void setInLcywTimeType(TimeUnitEnum inLcywTimeType) {
		this.inLcywTimeType = inLcywTimeType;
	}
	
	public String getInJjywTime() {
		return this.inJjywTime;
	}
	
	public void setInJjywTime(String inJjywTime) {
		this.inJjywTime = inJjywTime;
	}
	
	public TimeUnitEnum getInJjywTimeType() {
		return this.inJjywTimeType;
	}
	
	public void setInJjywTimeType(TimeUnitEnum inJjywTimeType) {
		this.inJjywTimeType = inJjywTimeType;
	}
	
	public String getInQtywTime() {
		return this.inQtywTime;
	}
	
	public void setInQtywTime(String inQtywTime) {
		this.inQtywTime = inQtywTime;
	}
	
	public TimeUnitEnum getInQtywTimeType() {
		return this.inQtywTimeType;
	}
	
	public void setInQtywTimeType(TimeUnitEnum inQtywTimeType) {
		this.inQtywTimeType = inQtywTimeType;
	}
	
	public String getInDckshTime() {
		return this.inDckshTime;
	}
	
	public void setInDckshTime(String inDckshTime) {
		this.inDckshTime = inDckshTime;
	}
	
	public TimeUnitEnum getInDckshTimeType() {
		return this.inDckshTimeType;
	}
	
	public void setInDckshTimeType(TimeUnitEnum inDckshTimeType) {
		this.inDckshTimeType = inDckshTimeType;
	}
	
	public String getOutXfglxtOtherTime() {
		return this.outXfglxtOtherTime;
	}
	
	public void setOutXfglxtOtherTime(String outXfglxtOtherTime) {
		this.outXfglxtOtherTime = outXfglxtOtherTime;
	}
	
	public TimeUnitEnum getOutXfglxtOtherTimeType() {
		return this.outXfglxtOtherTimeType;
	}
	
	public void setOutXfglxtOtherTimeType(TimeUnitEnum outXfglxtOtherTimeType) {
		this.outXfglxtOtherTimeType = outXfglxtOtherTimeType;
	}
	
	public String getOutTfsqdOtherTime() {
		return this.outTfsqdOtherTime;
	}
	
	public void setOutTfsqdOtherTime(String outTfsqdOtherTime) {
		this.outTfsqdOtherTime = outTfsqdOtherTime;
	}
	
	public TimeUnitEnum getOutTfsqdOtherTimeType() {
		return this.outTfsqdOtherTimeType;
	}
	
	public void setOutTfsqdOtherTimeType(TimeUnitEnum outTfsqdOtherTimeType) {
		this.outTfsqdOtherTimeType = outTfsqdOtherTimeType;
	}
	
	public String getOutZcsqOtherTime() {
		return this.outZcsqOtherTime;
	}
	
	public void setOutZcsqOtherTime(String outZcsqOtherTime) {
		this.outZcsqOtherTime = outZcsqOtherTime;
	}
	
	public TimeUnitEnum getOutZcsqOtherTimeType() {
		return this.outZcsqOtherTimeType;
	}
	
	public void setOutZcsqOtherTimeType(TimeUnitEnum outZcsqOtherTimeType) {
		this.outZcsqOtherTimeType = outZcsqOtherTimeType;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SysForecastParamAllInfo [outYhrzdbTime=");
		builder.append(outYhrzdbTime);
		builder.append(", outYhrzdbTimeType=");
		builder.append(outYhrzdbTimeType);
		builder.append(", outXjrzdbTime=");
		builder.append(outXjrzdbTime);
		builder.append(", outZjrzdbTimeType=");
		builder.append(outZjrzdbTimeType);
		builder.append(", outJrcpdbTime=");
		builder.append(outJrcpdbTime);
		builder.append(", outJrcpdbTimeType=");
		builder.append(outJrcpdbTimeType);
		builder.append(", outFrzdbywTime=");
		builder.append(outFrzdbywTime);
		builder.append(", outFrzdbywTimeType=");
		builder.append(outFrzdbywTimeType);
		builder.append(", outZdbywTime=");
		builder.append(outZdbywTime);
		builder.append(", outZdbywTimeType=");
		builder.append(outZdbywTimeType);
		builder.append(", outWtdkywTime=");
		builder.append(outWtdkywTime);
		builder.append(", outWtdkywTimeType=");
		builder.append(outWtdkywTimeType);
		builder.append(", outXdywTime=");
		builder.append(outXdywTime);
		builder.append(", outXdywTimeType=");
		builder.append(outXdywTimeType);
		builder.append(", outQtywTime=");
		builder.append(outQtywTime);
		builder.append(", outqtywTimeType=");
		builder.append(outqtywTimeType);
		builder.append(", outLcywTime=");
		builder.append(outLcywTime);
		builder.append(", outLcywTimeType=");
		builder.append(outLcywTimeType);
		builder.append(", outXfglxtTime=");
		builder.append(outXfglxtTime);
		builder.append(", outXfglxtTimeType=");
		builder.append(outXfglxtTimeType);
		builder.append(", outXfglxtOtherTime=");
		builder.append(outXfglxtOtherTime);
		builder.append(", outXfglxtOtherTimeType=");
		builder.append(outXfglxtOtherTimeType);
		builder.append(", outTfsqdTime=");
		builder.append(outTfsqdTime);
		builder.append(", outTfsqdTimeType=");
		builder.append(outTfsqdTimeType);
		builder.append(", outTfsqdOtherTime=");
		builder.append(outTfsqdOtherTime);
		builder.append(", outTfsqdOtherTimeType=");
		builder.append(outTfsqdOtherTimeType);
		builder.append(", outZcsqTime=");
		builder.append(outZcsqTime);
		builder.append(", outZcsqTimeType=");
		builder.append(outZcsqTimeType);
		builder.append(", outZcsqOtherTime=");
		builder.append(outZcsqOtherTime);
		builder.append(", outZcsqOtherTimeType=");
		builder.append(outZcsqOtherTimeType);
		builder.append(", outStqsTime=");
		builder.append(outStqsTime);
		builder.append(", outStqsTimeType=");
		builder.append(outStqsTimeType);
		builder.append(", outDckhcTime=");
		builder.append(outDckhcTime);
		builder.append(", outDckhcTimeType=");
		builder.append(outDckhcTimeType);
		builder.append(", outThkhbzjTime=");
		builder.append(outThkhbzjTime);
		builder.append(", outThkhbzjTimeType=");
		builder.append(outThkhbzjTimeType);
		builder.append(", inYhrzdbTime=");
		builder.append(inYhrzdbTime);
		builder.append(", inYhrzdbTimeType=");
		builder.append(inYhrzdbTimeType);
		builder.append(", inZjrzdbtime=");
		builder.append(inZjrzdbtime);
		builder.append(", inZjrzdbTimeType=");
		builder.append(inZjrzdbTimeType);
		builder.append(", inJrcpdbTime=");
		builder.append(inJrcpdbTime);
		builder.append(", inJrcpdbTimeType=");
		builder.append(inJrcpdbTimeType);
		builder.append(", inFrzdbywTime=");
		builder.append(inFrzdbywTime);
		builder.append(", inFrzdbywTimeType=");
		builder.append(inFrzdbywTimeType);
		builder.append(", inZdbywTime=");
		builder.append(inZdbywTime);
		builder.append(", inZdbywTimeType=");
		builder.append(inZdbywTimeType);
		builder.append(", inWtdkywTime=");
		builder.append(inWtdkywTime);
		builder.append(", inWtdkywTimeType=");
		builder.append(inWtdkywTimeType);
		builder.append(", inCxywTime=");
		builder.append(inCxywTime);
		builder.append(", inCxywTimeType=");
		builder.append(inCxywTimeType);
		builder.append(", inXdywTime=");
		builder.append(inXdywTime);
		builder.append(", inXdywTimeType=");
		builder.append(inXdywTimeType);
		builder.append(", inLcyTime=");
		builder.append(inLcyTime);
		builder.append(", inLcywTimeType=");
		builder.append(inLcywTimeType);
		builder.append(", inJjywTime=");
		builder.append(inJjywTime);
		builder.append(", inJjywTimeType=");
		builder.append(inJjywTimeType);
		builder.append(", inQtywTime=");
		builder.append(inQtywTime);
		builder.append(", inQtywTimeType=");
		builder.append(inQtywTimeType);
		builder.append(", inDckshTime=");
		builder.append(inDckshTime);
		builder.append(", inDckshTimeType=");
		builder.append(inDckshTimeType);
		builder.append("]");
		return builder.toString();
	}
	
}
