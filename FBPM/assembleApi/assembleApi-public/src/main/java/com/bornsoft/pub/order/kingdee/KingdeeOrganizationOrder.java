package com.bornsoft.pub.order.kingdee;

import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * @Description: 同步组织架构
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午10:49:02
 * @version:     v1.0
 */
public class KingdeeOrganizationOrder extends BornOrderBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 部门编号 **/
	private String deptCode;
	/** 部门名称 **/
	private String deptName;
	/** 上级部门编号 **/
	private String parentDeptCode;
	/** 上级部门名称 **/
	private String parentDeptName;
	
	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTextV2(deptCode, "deptCode");
		ValidateParamUtil.hasTextV2(deptName, "deptName");
	}
	
	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}
	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * @return the parentDeptCode
	 */
	public String getParentDeptCode() {
		return parentDeptCode;
	}
	/**
	 * @param parentDeptCode the parentDeptCode to set
	 */
	public void setParentDeptCode(String parentDeptCode) {
		this.parentDeptCode = parentDeptCode;
	}
	/**
	 * @return the parentDeptName
	 */
	public String getParentDeptName() {
		return parentDeptName;
	}
	/**
	 * @param parentDeptName the parentDeptName to set
	 */
	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}
}
