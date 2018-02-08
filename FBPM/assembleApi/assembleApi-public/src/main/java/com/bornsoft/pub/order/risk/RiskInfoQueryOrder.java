package com.bornsoft.pub.order.risk;

import com.bornsoft.utils.base.BornOutOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/***
  * @Description: 企业风险信息综合查询【跳转】
  * @author taibai@yiji.com 
  * @date  2016-8-6 下午4:00:09
  * @version V1.0
 */
public class RiskInfoQueryOrder extends BornOutOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private String operator;
	private String licenseNo;	
	private String customName;	
	
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

	@Override
	public String getService() {
		return "queryRiskInfo";
	}
	

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTextV2(this.licenseNo, "licenseNo");
		ValidateParamUtil.hasTextV2(this.customName, "customName");
	}

	@Override
	public boolean isRedirect() {
		return false;
	}

}
