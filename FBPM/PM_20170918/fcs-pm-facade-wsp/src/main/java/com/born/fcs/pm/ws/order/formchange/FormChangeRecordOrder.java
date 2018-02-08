package com.born.fcs.pm.ws.order.formchange;

import com.born.fcs.pm.ws.enums.FormChangeApplyTypeEnum;

/**
 * 签报记录
 * 
 * @author wuzj
 */
public class FormChangeRecordOrder extends FormChangeApplyOrder {
	
	private static final long serialVersionUID = 5362903944320123648L;
	
	/** 记录ID */
	private Long recordId;
	
	/** 签报申请formId */
	private Long applyFormId;
	
	/** 页面索引 */
	private Integer tabIndex;
	
	/** 页面描述 */
	private String tabDesc;
	
	/** 表单请求地址 */
	private String exeUrl;
	
	/** 表单描述 */
	private String formDesc;
	
	/** 表单数据 */
	private String formData;
	
	/** 原始表单数据 */
	private String originalFormData;
	
	/** 页面内容 */
	private String pageContent;
	
	/** 编辑页面内容 */
	private String formContent;
	
	/** 原页面内容 */
	private String originalPageContent;
	
	/** 当前会话ID（同一个会话只保存一次） */
	private String sessionId;
	
	/** 信任访问密钥 */
	private String accessToken;
	
	/** 修改明细数据 */
	private String detailData;
	
	private String exeResult;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FormChangeRecordOrder [");
		sb.append("projectCode=").append(getProjectCode());
		sb.append("applyId=").append(getApplyId());
		sb.append("applyType=").append(getApplyType());
		sb.append("changeForm=").append(getChangeForm());
		sb.append("changeFormId=").append(getChangeFormId());
		sb.append("recordId=").append(recordId);
		sb.append("applyFormId=").append(applyFormId);
		sb.append("tabIndex=").append(tabIndex);
		sb.append("tabDesc=").append(tabDesc);
		sb.append("formDesc=").append(formDesc);
		sb.append("recordId=").append(recordId);
		sb.append("detailData=").append(detailData);
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public void check() {
		if (getApplyType() == FormChangeApplyTypeEnum.FORM) {
			validateNotNull(formData, "表单数据");
			validateNotNull(originalFormData, "原始表单数据");
		}
	}
	
	public Long getRecordId() {
		return this.recordId;
	}
	
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	public Long getApplyFormId() {
		return this.applyFormId;
	}
	
	public void setApplyFormId(Long applyFormId) {
		this.applyFormId = applyFormId;
	}
	
	public Integer getTabIndex() {
		return this.tabIndex;
	}
	
	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}
	
	public String getExeUrl() {
		return this.exeUrl;
	}
	
	public void setExeUrl(String exeUrl) {
		this.exeUrl = exeUrl;
	}
	
	public String getFormDesc() {
		return this.formDesc;
	}
	
	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}
	
	public String getFormData() {
		return this.formData;
	}
	
	public void setFormData(String formData) {
		this.formData = formData;
	}
	
	public String getOriginalFormData() {
		return this.originalFormData;
	}
	
	public void setOriginalFormData(String originalFormData) {
		this.originalFormData = originalFormData;
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
	
	public String getUserIp() {
		return this.userIp;
	}
	
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getSessionId() {
		return this.sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getPageContent() {
		return this.pageContent;
	}
	
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
	
	public String getFormContent() {
		return this.formContent;
	}
	
	public void setFormContent(String formContent) {
		this.formContent = formContent;
	}
	
	public String getOriginalPageContent() {
		return this.originalPageContent;
	}
	
	public void setOriginalPageContent(String originalPageContent) {
		this.originalPageContent = originalPageContent;
	}
	
	public String getDetailData() {
		return this.detailData;
	}
	
	public void setDetailData(String detailData) {
		this.detailData = detailData;
	}
	
	public String getTabDesc() {
		return this.tabDesc;
	}
	
	public void setTabDesc(String tabDesc) {
		this.tabDesc = tabDesc;
	}
	
	public String getExeResult() {
		return this.exeResult;
	}
	
	public void setExeResult(String exeResult) {
		this.exeResult = exeResult;
	}
	
}
