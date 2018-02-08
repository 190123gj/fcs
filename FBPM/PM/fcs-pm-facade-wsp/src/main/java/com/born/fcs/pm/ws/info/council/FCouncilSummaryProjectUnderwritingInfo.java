package com.born.fcs.pm.ws.info.council;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.ChargeTypeEnum;

/**
 * 会议纪要 - 项目评审会 - 承销业务项目批复
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectUnderwritingInfo extends FCouncilSummaryProjectInfo {
	
	private static final long serialVersionUID = -2490219693217608911L;
	
	/** 主键 */
	private long id;
	/** 会议纪要项目ID */
	private long spId;
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
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	//收费方式
	private List<FCouncilSummaryProjectChargeWayInfo> chargeWayList;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getSpId() {
		return this.spId;
	}
	
	public void setSpId(long spId) {
		this.spId = spId;
	}
	
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
	
	public void setOtherFeeType(ChargeTypeEnum otherFeeType) {
		this.otherFeeType = otherFeeType;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public List<FCouncilSummaryProjectChargeWayInfo> getChargeWayList() {
		return this.chargeWayList;
	}
	
	public void setChargeWayList(List<FCouncilSummaryProjectChargeWayInfo> chargeWayList) {
		this.chargeWayList = chargeWayList;
	}
	
}
