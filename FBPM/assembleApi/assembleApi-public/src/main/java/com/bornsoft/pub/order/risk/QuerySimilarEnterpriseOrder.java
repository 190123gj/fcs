package com.bornsoft.pub.order.risk;

import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/***
  * @Description: 类似企业信息查询 
  * @author taibai@yiji.com 
  * @date  2016-8-6 下午3:45:01
  * @version V1.0
 */
public class QuerySimilarEnterpriseOrder extends BornOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/** 证件号码 **/
	private String licenseNo;
	/** 企业名称或个人姓名 **/
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
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTextV2(customName, "customName");
		ValidateParamUtil.hasTextV2(licenseNo, "licenseNo");
	}

	@Override
	public String getService() {
		return "querySimilarEnterprise";
	}

}
