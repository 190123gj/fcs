package com.born.fcs.pm.ws.order.assist;

import java.util.Date;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.InternalOpinionExTypeEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;

public class FInternalOpinionExchangeOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 4197367796487129470L;
	
	/** 类型 */
	private InternalOpinionExTypeEnum exType;
	
	/** 审计部门ID */
	private String deptIds;
	
	/** 审计部门名称 */
	private String deptNames;
	
	/** 意见反馈时间 */
	private Date feedbackTime;
	
	/** 说明 */
	private String remark;
	
	/** 审计人员 id,id2,id3 */
	private String userIds;
	
	/** 审计人员 name1,name2,name3 */
	private String userNames;
	
	/** 《合规检查报告》送达表是否需要整改 */
	private BooleanEnum needFeedback;
	
	@Override
	public void check() {
		if (checkStatus == 1) {
			validateNotNull(deptIds, "审计部门");
			validateNotNull(userIds, "审计人员");
			validateNotNull(exType, "审计类型");
			Assert.isTrue((needFeedback == BooleanEnum.YES && feedbackTime != null)
							|| needFeedback == BooleanEnum.NO, "反馈时间不能为空");
		}
	}
	
	public InternalOpinionExTypeEnum getExType() {
		return this.exType;
	}
	
	public void setExType(InternalOpinionExTypeEnum exType) {
		this.exType = exType;
	}
	
	public String getDeptIds() {
		return this.deptIds;
	}
	
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	
	public String getDeptNames() {
		return this.deptNames;
	}
	
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	
	public Date getFeedbackTime() {
		return this.feedbackTime;
	}
	
	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getUserIds() {
		return this.userIds;
	}
	
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	
	public String getUserNames() {
		return this.userNames;
	}
	
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	
	public BooleanEnum getNeedFeedback() {
		return this.needFeedback;
	}
	
	public void setNeedFeedback(BooleanEnum needFeedback) {
		this.needFeedback = needFeedback;
	}
	
}
