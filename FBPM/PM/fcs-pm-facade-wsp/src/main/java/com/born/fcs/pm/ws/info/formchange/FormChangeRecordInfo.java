package com.born.fcs.pm.ws.info.formchange;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.FormChangeRecordStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 签报修改记录
 * @author wuzj
 */
public class FormChangeRecordInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 2472091427053917529L;
	
	/** 记录ID */
	private long recordId;
	
	/** 签报申请formId */
	private long applyFormId;
	
	/** 页面索引 */
	private int tabIndex;
	
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
	
	/** 页面内容（编辑页面） */
	private String formContent;
	
	/** 原页面内容 */
	private String originalPageContent;
	
	/** 当前会话ID（同一个会话只保存一次） */
	private String sessionId;
	
	/** 信任访问密钥 */
	private String accessToken;
	
	/** 状态 */
	private FormChangeRecordStatusEnum status;
	
	/** 修改人ID */
	private long userId;
	
	/** 修改人账号 */
	private String userAccount;
	
	/** 修改人名 */
	private String userName;
	
	/** 修改ip */
	private String userIp;
	
	/** 修改人部门 */
	private long deptId;
	
	/** 修改人部门名 */
	private String deptName;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	/** 修改详情 */
	List<FormChangeRecordDetailInfo> detailList;
	
	private String detailData;
	
	private String exeResult;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FormChangeRecordInfo [");
		sb.append("recordId=").append(recordId);
		sb.append("applyFormId=").append(applyFormId);
		sb.append("detailData=").append(detailData);
		sb.append("tabIndex=").append(tabIndex);
		sb.append("tabDesc=").append(tabDesc);
		sb.append("formDesc=").append(formDesc);
		sb.append("recordId=").append(recordId);
		sb.append("detailData=").append(detailData);
		sb.append("]");
		return sb.toString();
	}
	
	public long getRecordId() {
		return this.recordId;
	}
	
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}
	
	public long getApplyFormId() {
		return this.applyFormId;
	}
	
	public void setApplyFormId(long applyFormId) {
		this.applyFormId = applyFormId;
	}
	
	public int getTabIndex() {
		return this.tabIndex;
	}
	
	public void setTabIndex(int tabIndex) {
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
	
	public String getUserIp() {
		return this.userIp;
	}
	
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	public long getDeptId() {
		return this.deptId;
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
	
	public FormChangeRecordStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(FormChangeRecordStatusEnum status) {
		this.status = status;
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
	
	public String getPageContent() {
		return this.pageContent;
	}
	
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
	
	public String getOriginalPageContent() {
		return this.originalPageContent;
	}
	
	public void setOriginalPageContent(String originalPageContent) {
		this.originalPageContent = originalPageContent;
	}
	
	public String getTabDesc() {
		return this.tabDesc;
	}
	
	public void setTabDesc(String tabDesc) {
		this.tabDesc = tabDesc;
	}
	
	public List<FormChangeRecordDetailInfo> getDetailList() {
		return this.detailList;
	}
	
	public void setDetailList(List<FormChangeRecordDetailInfo> detailList) {
		this.detailList = detailList;
	}
	
	public String getExeResult() {
		return this.exeResult;
	}
	
	public void setExeResult(String exeResult) {
		this.exeResult = exeResult;
	}
	
	public String getDetailData() {
		return this.detailData;
	}
	
	public void setDetailData(String detailData) {
		this.detailData = detailData;
	}
	
	public String getFormContent() {
		return this.formContent;
	}
	
	public void setFormContent(String formContent) {
		this.formContent = formContent;
	}
}
