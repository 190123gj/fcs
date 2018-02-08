package com.born.fcs.pm.ws.info.assist;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.InternalOpinionExTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 内审人员
 *
 * @author wuzj
 */
public class FInternalOpinionExchangeUserInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -1993047689342396326L;
	/** 主键 */
	private long id;
	/** 表单ID */
	private long formId;
	/** 审计人员ID */
	private long userId;
	/** 审计人员账号 */
	private String userAccount;
	/** 审计人员姓名 */
	private String userName;
	/** 审计人员邮箱 */
	private String userEmail;
	/** 审计人员电话 */
	private String userMobile;
	/** 类型 */
	private InternalOpinionExTypeEnum exType;
	/** 审计意见反馈时间 */
	private Date feedbackTime;
	/** 反馈意见 */
	private String feedback;
	/** 是否已通知 */
	private BooleanEnum isNotified;
	/** 是否负责人 */
	private BooleanEnum isPrincipal;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserEmail() {
		return this.userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getUserMobile() {
		return this.userMobile;
	}
	
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	
	public InternalOpinionExTypeEnum getExType() {
		return this.exType;
	}
	
	public void setExType(InternalOpinionExTypeEnum exType) {
		this.exType = exType;
	}
	
	public Date getFeedbackTime() {
		return this.feedbackTime;
	}
	
	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	
	public String getFeedback() {
		return this.feedback;
	}
	
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	public BooleanEnum getIsNotified() {
		return this.isNotified;
	}
	
	public void setIsNotified(BooleanEnum isNotified) {
		this.isNotified = isNotified;
	}
	
	public BooleanEnum getIsPrincipal() {
		return this.isPrincipal;
	}
	
	public void setIsPrincipal(BooleanEnum isPrincipal) {
		this.isPrincipal = isPrincipal;
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
}
