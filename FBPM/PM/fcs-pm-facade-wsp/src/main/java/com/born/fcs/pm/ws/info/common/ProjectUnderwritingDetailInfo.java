package com.born.fcs.pm.ws.info.common;

import com.born.fcs.pm.ws.enums.ChargeTypeEnum;

/**
 * 承销项目详情
 * @author wuzj
 */
public class ProjectUnderwritingDetailInfo extends ProjectInfo {
	
	private static final long serialVersionUID = 3642145662566166602L;
	
	/** 交易所ID */
	private long bourseId;
	/** 交易所名称 */
	private String bourseName;
	/** 发行利率 */
	private double releaseRate;
	/** 交易所费 */
	private double bourseFee;
	/** 交易所费用类型 */
	private ChargeTypeEnum bourseFeeType;
	/** 律所费 */
	private double lawFirmFee;
	/** 律所费用类型 */
	private ChargeTypeEnum lawFirmFeeType;
	/** 会所费 */
	private double clubFee;
	/** 会所费用类型 */
	private ChargeTypeEnum clubFeeType;
	/** 承销费 */
	private double underwritingFee;
	/** 承销费用类型 */
	private ChargeTypeEnum underwritingFeeType;
	/** 其他费用 */
	private double otherFee;
	/** 其他费用类型 */
	private ChargeTypeEnum otherFeeType;
	
	public long getBourseId() {
		return this.bourseId;
	}
	
	public void setBourseId(long bourseId) {
		this.bourseId = bourseId;
	}
	
	public String getBourseName() {
		return this.bourseName;
	}
	
	public void setBourseName(String bourseName) {
		this.bourseName = bourseName;
	}
	
	public double getReleaseRate() {
		return this.releaseRate;
	}
	
	public void setReleaseRate(double releaseRate) {
		this.releaseRate = releaseRate;
	}
	
	public double getBourseFee() {
		return this.bourseFee;
	}
	
	public void setBourseFee(double bourseFee) {
		this.bourseFee = bourseFee;
	}
	
	public ChargeTypeEnum getBourseFeeType() {
		return this.bourseFeeType;
	}
	
	public String getBourseFeeTypeMessage() {
		return this.bourseFeeType == null ? "" : bourseFeeType.message();
	}
	
	public void setBourseFeeType(ChargeTypeEnum bourseFeeType) {
		this.bourseFeeType = bourseFeeType;
	}
	
	public double getLawFirmFee() {
		return this.lawFirmFee;
	}
	
	public void setLawFirmFee(double lawFirmFee) {
		this.lawFirmFee = lawFirmFee;
	}
	
	public ChargeTypeEnum getLawFirmFeeType() {
		return this.lawFirmFeeType;
	}
	
	public String getLawFirmFeeTypeMessage() {
		return this.lawFirmFeeType == null ? "" : lawFirmFeeType.message();
	}
	
	public void setLawFirmFeeType(ChargeTypeEnum lawFirmFeeType) {
		this.lawFirmFeeType = lawFirmFeeType;
	}
	
	public double getClubFee() {
		return this.clubFee;
	}
	
	public void setClubFee(double clubFee) {
		this.clubFee = clubFee;
	}
	
	public ChargeTypeEnum getClubFeeType() {
		return this.clubFeeType;
	}
	
	public String getClubFeeTypeMessage() {
		return this.clubFeeType == null ? "" : clubFeeType.message();
	}
	
	public void setClubFeeType(ChargeTypeEnum clubFeeType) {
		this.clubFeeType = clubFeeType;
	}
	
	public double getUnderwritingFee() {
		return this.underwritingFee;
	}
	
	public void setUnderwritingFee(double underwritingFee) {
		this.underwritingFee = underwritingFee;
	}
	
	public ChargeTypeEnum getUnderwritingFeeType() {
		return this.underwritingFeeType;
	}
	
	public String getUnderwritingFeeTypeMessage() {
		return this.underwritingFeeType == null ? "" : underwritingFeeType.message();
	}
	
	public void setUnderwritingFeeType(ChargeTypeEnum underwritingFeeType) {
		this.underwritingFeeType = underwritingFeeType;
	}
	
	public double getOtherFee() {
		return this.otherFee;
	}
	
	public void setOtherFee(double otherFee) {
		this.otherFee = otherFee;
	}
	
	public ChargeTypeEnum getOtherFeeType() {
		return this.otherFeeType;
	}
	
	public String getOtherFeeTypeMessage() {
		return this.otherFeeType == null ? "" : otherFeeType.message();
	}
	
	public void setOtherFeeType(ChargeTypeEnum otherFeeType) {
		this.otherFeeType = otherFeeType;
	}
	
}
