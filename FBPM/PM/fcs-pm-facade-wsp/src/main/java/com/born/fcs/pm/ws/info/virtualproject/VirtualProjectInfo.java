package com.born.fcs.pm.ws.info.virtualproject;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.VirtualProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 虚拟项目
 *
 * @author wuzj
 */
public class VirtualProjectInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -6910171474341167387L;
	
	/** 主键 */
	private long virtualId;
	/** 虚拟项目编号 */
	private String projectCode;
	/** 虚拟项目名称 */
	private String projectName;
	/** 虚拟客户[-1,-2,-3] */
	private long customerId;
	/** 虚拟客户名称 */
	private String customerName;
	/** 授信金额[所选项目授信金额合计] */
	private Money amount = new Money(0, 0);
	/** 业务类型[默认其他] */
	private String busiType;
	/** 业务类型名称 */
	private String busiTypeName;
	/** 申请用户ID */
	private long applyUserId;
	/** 申请用户账号 */
	private String applyUserAccount;
	/** 申请用户名称 */
	private String applyUserName;
	/** 申请部门ID */
	private long applyDeptId;
	/** 申请部门名称 */
	private String applyDeptName;
	/** 方案 */
	private String scheme;
	/** 备注 */
	private String remark;
	/** 附件 */
	private String attach;
	/** 状态[IN_USE不直接从数据库直接查询，关联动态查询] */
	private VirtualProjectStatusEnum status;
	/** 提交时间 */
	private Date submitTime;
	/** 已发起的表单名称 */
	private String formNames;
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	/** 所选项目 */
	private List<VirtualProjectDetailInfo> detailList;
	
	public String detailProjectCodes() {
		String projectCodes = null;
		if (detailList != null) {
			for (VirtualProjectDetailInfo detail : detailList) {
				if (projectCodes == null) {
					projectCodes = detail.getProjectCode();
				} else {
					projectCodes += "," + detail.getProjectCode();
				}
			}
		}
		return projectCodes;
	}
	
	public Money sumDetailAmount() {
		Money money = Money.zero();
		if (detailList != null) {
			for (VirtualProjectDetailInfo detail : detailList) {
				money.addTo(detail.getAmount());
			}
		}
		return money;
	}
	
	public Money sumDetailBalance() {
		Money money = Money.zero();
		if (detailList != null) {
			for (VirtualProjectDetailInfo detail : detailList) {
				money.addTo(detail.getBalance());
			}
		}
		return money;
	}
	
	public long getVirtualId() {
		return this.virtualId;
	}
	
	public void setVirtualId(long virtualId) {
		this.virtualId = virtualId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public long getApplyUserId() {
		return this.applyUserId;
	}
	
	public void setApplyUserId(long applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	public String getApplyUserAccount() {
		return this.applyUserAccount;
	}
	
	public void setApplyUserAccount(String applyUserAccount) {
		this.applyUserAccount = applyUserAccount;
	}
	
	public String getApplyUserName() {
		return this.applyUserName;
	}
	
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	
	public long getApplyDeptId() {
		return this.applyDeptId;
	}
	
	public void setApplyDeptId(long applyDeptId) {
		this.applyDeptId = applyDeptId;
	}
	
	public String getApplyDeptName() {
		return this.applyDeptName;
	}
	
	public void setApplyDeptName(String applyDeptName) {
		this.applyDeptName = applyDeptName;
	}
	
	public String getScheme() {
		return this.scheme;
	}
	
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAttach() {
		return this.attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public VirtualProjectStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(VirtualProjectStatusEnum status) {
		this.status = status;
	}
	
	public Date getSubmitTime() {
		return this.submitTime;
	}
	
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	
	public String getFormNames() {
		return this.formNames;
	}
	
	public void setFormNames(String formNames) {
		this.formNames = formNames;
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
	
	public List<VirtualProjectDetailInfo> getDetailList() {
		return this.detailList;
	}
	
	public void setDetailList(List<VirtualProjectDetailInfo> detailList) {
		this.detailList = detailList;
	}
	
}
