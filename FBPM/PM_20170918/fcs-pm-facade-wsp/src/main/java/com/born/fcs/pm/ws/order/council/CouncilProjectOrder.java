package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.enums.OneVoteResultEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilCompereMessageEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

public class CouncilProjectOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -5144796362221632343L;
	
	private long id;
	
	private long councilId;
	
	private String projectCode;
	/** 是否被一票否决 */
	private OneVoteResultEnum oneVoteResult;
	/** 被一票否决的原因 */
	private String oneVoteDownMark;
	
	/** 主持人本次不议后操作记录 */
	private ProjectCouncilCompereMessageEnum compereMessage;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getCouncilId() {
		return this.councilId;
	}
	
	public void setCouncilId(long councilId) {
		this.councilId = councilId;
	}
	
	public ProjectCouncilCompereMessageEnum getCompereMessage() {
		return this.compereMessage;
	}
	
	public void setCompereMessage(ProjectCouncilCompereMessageEnum compereMessage) {
		this.compereMessage = compereMessage;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getOneVoteDownMark() {
		return this.oneVoteDownMark;
	}
	
	public void setOneVoteDownMark(String oneVoteDownMark) {
		this.oneVoteDownMark = oneVoteDownMark;
	}
	
	public OneVoteResultEnum getOneVoteResult() {
		return this.oneVoteResult;
	}
	
	public void setOneVoteResult(OneVoteResultEnum oneVoteResult) {
		this.oneVoteResult = oneVoteResult;
	}
}
