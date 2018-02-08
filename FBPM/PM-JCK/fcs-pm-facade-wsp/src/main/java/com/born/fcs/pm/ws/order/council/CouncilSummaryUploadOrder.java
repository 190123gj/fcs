package com.born.fcs.pm.ws.order.council;

import java.util.List;

import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 上传会议纪要
 * 
 * @author wuzj
 */
public class CouncilSummaryUploadOrder extends FormOrderBase {

	private static final long serialVersionUID = 3451145963433412992L;

	/** 会议ID */
	private long councilId;

	/** 评审结果 */
	List<CouncilProjectInfo> voteInfo;

	private String summaryUrl;

	public String getSummaryUrl() {
		return summaryUrl;
	}

	public void setSummaryUrl(String summaryUrl) {
		this.summaryUrl = summaryUrl;
	}

	public long getCouncilId() {
		return this.councilId;
	}

	public void setCouncilId(long councilId) {
		this.councilId = councilId;
	}

	public List<CouncilProjectInfo> getVoteInfo() {
		return voteInfo;
	}

	public void setVoteInfo(List<CouncilProjectInfo> voteInfo) {
		this.voteInfo = voteInfo;
	}

}
