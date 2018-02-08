package com.born.fcs.rm.ws.info.report;

import com.born.fcs.rm.ws.info.submission.SubmissionInfo;

/**
 * 
 * W1-（中担协）融资性担保机构基本情况
 * 
 * @author lirz
 * 
 * 2016-8-5 下午4:53:44
 */
public class GuaranteeBaseInfo extends ReportInfo {
	
	private static final long serialVersionUID = 7700039179420819431L;
	
	private SubmissionInfo submission;
	
	public SubmissionInfo getSubmission() {
		return submission;
	}
	
	public void setSubmission(SubmissionInfo submission) {
		this.submission = submission;
	}
	
}
