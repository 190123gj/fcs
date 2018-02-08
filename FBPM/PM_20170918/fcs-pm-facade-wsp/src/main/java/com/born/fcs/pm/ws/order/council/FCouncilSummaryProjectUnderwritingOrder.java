package com.born.fcs.pm.ws.order.council;

import java.util.List;

/**
 * 会议纪要 - 项目评审会 - 承销业务项目批复
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectUnderwritingOrder extends FCouncilSummaryProjectOrder {
	
	private static final long serialVersionUID = 6635013641744944180L;
	
	//收费方式
	private List<FCouncilSummaryProjectChargeWayOrder> chargeWayOrder;
	/** 主键 */
	private Long id;
	/** 会议纪要项目ID */
	private Long spId;
	/** 交易所ID */
	private Long bourseId;
	/** 交易所名称 */
	private String bourseName;
	/** 发行利率 */
	private Double releaseRate;
	/** 交易所费 */
	private Double bourseFee;
	/** 交易所费用类型 */
	private String bourseFeeType;
	/** 律所费 */
	private Double lawFirmFee;
	/** 律所费用类型 */
	private String lawFirmFeeType;
	/** 会所费 */
	private Double clubFee;
	/** 会所费用类型 */
	private String clubFeeType;
	/** 承销费 */
	private Double underwritingFee;
	/** 承销费用类型 */
	private String underwritingFeeType;
	/** 其他费用 */
	private Double otherFee;
	/** 其他费用类型 */
	private String otherFeeType;
	
	public List<FCouncilSummaryProjectChargeWayOrder> getChargeWayOrder() {
		return this.chargeWayOrder;
	}
	
	public void setChargeWayOrder(List<FCouncilSummaryProjectChargeWayOrder> chargeWayOrder) {
		this.chargeWayOrder = chargeWayOrder;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSpId() {
		return this.spId;
	}
	
	public void setSpId(Long spId) {
		this.spId = spId;
	}
	
	public Long getBourseId() {
		return this.bourseId;
	}
	
	public void setBourseId(Long bourseId) {
		this.bourseId = bourseId;
	}
	
	public String getBourseName() {
		return this.bourseName;
	}
	
	public void setBourseName(String bourseName) {
		this.bourseName = bourseName;
	}
	
	public Double getReleaseRate() {
		return this.releaseRate;
	}
	
	public void setReleaseRate(Double releaseRate) {
		this.releaseRate = releaseRate;
	}
	
	public Double getBourseFee() {
		return this.bourseFee;
	}
	
	public void setBourseFee(Double bourseFee) {
		this.bourseFee = bourseFee;
	}
	
	public String getBourseFeeType() {
		return this.bourseFeeType;
	}
	
	public void setBourseFeeType(String bourseFeeType) {
		this.bourseFeeType = bourseFeeType;
	}
	
	public Double getLawFirmFee() {
		return this.lawFirmFee;
	}
	
	public void setLawFirmFee(Double lawFirmFee) {
		this.lawFirmFee = lawFirmFee;
	}
	
	public String getLawFirmFeeType() {
		return this.lawFirmFeeType;
	}
	
	public void setLawFirmFeeType(String lawFirmFeeType) {
		this.lawFirmFeeType = lawFirmFeeType;
	}
	
	public Double getClubFee() {
		return this.clubFee;
	}
	
	public void setClubFee(Double clubFee) {
		this.clubFee = clubFee;
	}
	
	public String getClubFeeType() {
		return this.clubFeeType;
	}
	
	public void setClubFeeType(String clubFeeType) {
		this.clubFeeType = clubFeeType;
	}
	
	public Double getUnderwritingFee() {
		return this.underwritingFee;
	}
	
	public void setUnderwritingFee(Double underwritingFee) {
		this.underwritingFee = underwritingFee;
	}
	
	public String getUnderwritingFeeType() {
		return this.underwritingFeeType;
	}
	
	public void setUnderwritingFeeType(String underwritingFeeType) {
		this.underwritingFeeType = underwritingFeeType;
	}
	
	public Double getOtherFee() {
		return this.otherFee;
	}
	
	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}
	
	public String getOtherFeeType() {
		return this.otherFeeType;
	}
	
	public void setOtherFeeType(String otherFeeType) {
		this.otherFeeType = otherFeeType;
	}
}
