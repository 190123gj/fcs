package com.born.fcs.pm.ws.order.virtualproject;

import java.util.List;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 虚拟项目
 *
 * @author wuzj
 */
public class VirtualProjectOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -6714588283771335382L;
	
	/** 主键 */
	private long virtualId;
	/** 虚拟客户[-1,-2,-3] */
	private long customerId;
	/** 项目名称 */
	private String projectName;
	/** 虚拟客户名称 */
	private String customerName;
	/** 业务类型[默认其他] */
	private String busiType;
	/** 业务类型名称 */
	private String busiTypeName;
	/** 方案 */
	private String scheme;
	/** 备注 */
	private String remark;
	/** 附件 */
	private String attach;
	/** 是否提交 IS/NO */
	private BooleanEnum isSubmit;
	/** 申请部门 */
	private long applyDeptId;
	private String applyDeptName;
	/** 所选项目 */
	private List<VirtualProjectDetailOrder> detailOrder;
	
	@Override
	public void check() {
		if (isSubmit == BooleanEnum.IS) {
			Assert.isTrue(detailOrder != null && detailOrder.size() > 0, "请选择项目");
			validateHasText(scheme, "方案不能为空");
			validateHasText(projectName, "项目名称不能为空");
			Assert.isTrue(customerId != 0, "请选虚拟客户");
		}
	}
	
	public long getVirtualId() {
		return this.virtualId;
	}
	
	public void setVirtualId(long virtualId) {
		this.virtualId = virtualId;
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
	
	public BooleanEnum getIsSubmit() {
		return this.isSubmit;
	}
	
	public void setIsSubmit(BooleanEnum isSubmit) {
		this.isSubmit = isSubmit;
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
	
	public List<VirtualProjectDetailOrder> getDetailOrder() {
		return this.detailOrder;
	}
	
	public void setDetailOrder(List<VirtualProjectDetailOrder> detailOrder) {
		this.detailOrder = detailOrder;
	}
	
}
