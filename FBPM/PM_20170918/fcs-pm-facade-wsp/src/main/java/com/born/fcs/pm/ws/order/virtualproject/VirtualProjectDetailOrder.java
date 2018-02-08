package com.born.fcs.pm.ws.order.virtualproject;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 虚拟项目-所选项目
 *
 * @author wuzj
 */
public class VirtualProjectDetailOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -3286333928394044782L;
	
	/** 主键 */
	private long detailId;
	/** 所选项目编号 */
	private String projectCode;
	/** 备注 */
	private String remark;
	/** 附件 */
	private String attach;
	
	@Override
	public void check() {
		validateHasText(projectCode, "项目编号");
	}
	
	public long getDetailId() {
		return this.detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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
}
