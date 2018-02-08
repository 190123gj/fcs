package com.bornsoft.pub.order.risk;

import com.bornsoft.pub.enums.UserTypeEnum;
import com.bornsoft.utils.base.BornOrderBase;

/***
  * @Description: 机构信息校验 
  * @author taibai@yiji.com 
  * @date  2016-8-6 下午3:42:15
  * @version V1.0
 */
public class VerifyOrganizationOrder extends BornOrderBase {
	/**
	 */
	private static final long serialVersionUID = 1L;
	/** 证件号码 */
	private String licenseNo;
	/** 企业名称或个人姓名 */
	private String customName;
	/** 企业（B）、个人（P）**/
	private UserTypeEnum userType;

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	public UserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}

	@Override
	public String getService() {
		return "verifyOrganizationInfo";
	}

}
