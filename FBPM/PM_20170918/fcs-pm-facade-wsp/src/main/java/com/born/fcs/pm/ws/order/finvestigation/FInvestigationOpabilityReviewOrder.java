package com.born.fcs.pm.ws.order.finvestigation;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.finvestigation.base.FInvestigationBaseOrder;

/**
 * 客户运营能力评价
 * 
 * @author lirz
 * 
 * 2016-3-10 下午4:52:34
 */
public class FInvestigationOpabilityReviewOrder extends FInvestigationBaseOrder {
	
	private static final long serialVersionUID = 4537882645129319428L;
	private long opReviewId;
	private String strategyMarketpos; //客户发展战略及市场定位
	private String industryEnv; //客户所在行业的宏观环境分析
	private String competitivenessRival; //客户核心竞争力评价、主要竞争对手基本情况
	private String explaination; //解释与说明
	//客户主要上下游情况（余额前五大）（城投类客户根据实际情况可不填写）
	//private List<FInvestigationOpabilityReviewUpdownStreamOrder> updownStreams;
	private List<FInvestigationOpabilityReviewUpdownStreamOrder> upStreams;//上游
	private List<FInvestigationOpabilityReviewUpdownStreamOrder> downStreams;//下游
	//客户主要产品结构、下游主要客户情况（城投类客户根据实际情况可不填写）
	private List<FInvestigationOpabilityReviewProductStructureOrder> productStructures;
	private String upDesc; //上游情况说明
	private String downDesc; //下游情况说明
	
	public long getOpReviewId() {
		return opReviewId;
	}
	
	public void setOpReviewId(long opReviewId) {
		this.opReviewId = opReviewId;
	}
	
	public String getStrategyMarketpos() {
		return strategyMarketpos;
	}
	
	public void setStrategyMarketpos(String strategyMarketpos) {
		this.strategyMarketpos = strategyMarketpos;
	}
	
	public String getIndustryEnv() {
		return industryEnv;
	}
	
	public void setIndustryEnv(String industryEnv) {
		this.industryEnv = industryEnv;
	}
	
	public String getCompetitivenessRival() {
		return competitivenessRival;
	}
	
	public void setCompetitivenessRival(String competitivenessRival) {
		this.competitivenessRival = competitivenessRival;
	}
	
	public String getExplaination() {
		return explaination;
	}
	
	public void setExplaination(String explaination) {
		this.explaination = explaination;
	}
	
	//	public List<FInvestigationOpabilityReviewUpdownStreamOrder> getUpdownStreams() {
	//		return updownStreams;
	//	}
	//	
	//	public void setUpdownStreams(List<FInvestigationOpabilityReviewUpdownStreamOrder> updownStreams) {
	//		this.updownStreams = updownStreams;
	//	}
	
	public List<FInvestigationOpabilityReviewUpdownStreamOrder> getUpStreams() {
		return this.upStreams;
	}
	
	public void setUpStreams(List<FInvestigationOpabilityReviewUpdownStreamOrder> upStreams) {
		this.upStreams = upStreams;
	}
	
	public List<FInvestigationOpabilityReviewUpdownStreamOrder> getDownStreams() {
		return this.downStreams;
	}
	
	public void setDownStreams(List<FInvestigationOpabilityReviewUpdownStreamOrder> downStreams) {
		this.downStreams = downStreams;
	}
	
	public List<FInvestigationOpabilityReviewProductStructureOrder> getProductStructures() {
		return productStructures;
	}
	
	public void setProductStructures(	List<FInvestigationOpabilityReviewProductStructureOrder> productStructures) {
		this.productStructures = productStructures;
	}
	
	public String getUpDesc() {
		return this.upDesc;
	}

	public void setUpDesc(String upDesc) {
		this.upDesc = upDesc;
	}

	public String getDownDesc() {
		return this.downDesc;
	}

	public void setDownDesc(String downDesc) {
		this.downDesc = downDesc;
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
