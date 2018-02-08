package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;
import java.util.List;

import com.yjf.common.lang.util.money.Money;

/**
 * 客户运营能力评价
 * 
 * @author lirz
 * 
 * 2016-3-10 下午4:52:34
 */
public class FInvestigationOpabilityReviewInfo extends InvestigationBaseInfo {
	
	private static final long serialVersionUID = -3540665829840045234L;
	
	private long opReviewId;
	private String strategyMarketpos; //客户发展战略及市场定位
	private String industryEnv; //客户所在行业的宏观环境分析
	private String competitivenessRival; //客户核心竞争力评价、主要竞争对手基本情况
	private String explaination; //解释与说明
	//客户主要上下游情况（余额前五大）（城投类客户根据实际情况可不填写）
	//private List<FInvestigationOpabilityReviewUpdownStreamInfo> updownStreams;
	private List<FInvestigationOpabilityReviewUpdownStreamInfo> upStreams; //上游
	private List<FInvestigationOpabilityReviewUpdownStreamInfo> downStreams;//下游
	//客户主要产品结构、下游主要客户情况（城投类客户根据实际情况可不填写）
	private List<FInvestigationOpabilityReviewProductStructureInfo> productStructures;
	private String upDesc; //上游情况说明
	private String downDesc; //下游情况说明
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	//	//客户主要上下游情况（余额前五大）（城投类客户根据实际情况可不填写）
	//	public Money getTotalBalance() {
	//		if (null != updownStreams && updownStreams.size() > 0) {
	//			Money total = new Money(0L);
	//			for (FInvestigationOpabilityReviewUpdownStreamInfo us : updownStreams) {
	//				if (null != us) {
	//					total.addTo(us.getEndingBalance());
	//				}
	//			}
	//			return total;
	//		} else {
	//			return null;
	//		}
	//	}
	//	
	//客户主要上下游情况（余额前五大）（城投类客户根据实际情况可不填写）
	public Money getTotalUpBalance() {
		if (null != upStreams && upStreams.size() > 0) {
			Money total = new Money(0L);
			for (FInvestigationOpabilityReviewUpdownStreamInfo us : upStreams) {
				if (null != us) {
					total.addTo(us.getEndingBalance());
				}
			}
			return total;
		} else {
			return null;
		}
	}
	
	//客户主要上下游情况（余额前五大）（城投类客户根据实际情况可不填写）
	public Money getTotalDownBalance() {
		if (null != downStreams && downStreams.size() > 0) {
			Money total = new Money(0L);
			for (FInvestigationOpabilityReviewUpdownStreamInfo us : downStreams) {
				if (null != us) {
					total.addTo(us.getEndingBalance());
				}
			}
			return total;
		} else {
			return null;
		}
	}
	
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
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	//	public List<FInvestigationOpabilityReviewUpdownStreamInfo> getUpdownStreams() {
	//		return updownStreams;
	//	}
	//	
	//	public void setUpdownStreams(List<FInvestigationOpabilityReviewUpdownStreamInfo> updownStreams) {
	//		this.updownStreams = updownStreams;
	//	}
	
	public List<FInvestigationOpabilityReviewUpdownStreamInfo> getUpStreams() {
		return this.upStreams;
	}
	
	public void setUpStreams(List<FInvestigationOpabilityReviewUpdownStreamInfo> upStreams) {
		this.upStreams = upStreams;
	}
	
	public List<FInvestigationOpabilityReviewUpdownStreamInfo> getDownStreams() {
		return this.downStreams;
	}
	
	public void setDownStreams(List<FInvestigationOpabilityReviewUpdownStreamInfo> downStreams) {
		this.downStreams = downStreams;
	}
	
	public List<FInvestigationOpabilityReviewProductStructureInfo> getProductStructures() {
		return productStructures;
	}
	
	public void setProductStructures(	List<FInvestigationOpabilityReviewProductStructureInfo> productStructures) {
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
	
}
