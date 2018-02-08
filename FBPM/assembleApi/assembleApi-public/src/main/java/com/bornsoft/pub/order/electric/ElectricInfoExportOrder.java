package com.bornsoft.pub.order.electric;

import com.bornsoft.pub.enums.CoreExportTypeEnum;
import com.bornsoft.pub.order.common.CoreExportDataOrder;
import com.bornsoft.pub.order.electric.ElectricQueryOrder.QueryModeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;


/**
  * @Description: 电量信息查询 
  * @author taibai@yiji.com 
  * @date  2016-8-15 下午4:38:15
  * @version V1.0
 */
public class ElectricInfoExportOrder extends CoreExportDataOrder {

	/**
	 */
	private static final long serialVersionUID = 1L;
	/** 证照号码 */
	private String licenseNo;
	/** 机构名称*/
	private String customName;
	/** 查询条件 */
	private String conditions;
	/** 查询模式 */
	private QueryModeEnum queryMode;
	
	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}


	public QueryModeEnum getQueryMode() {
		return queryMode;
	}

	public void setQueryMode(QueryModeEnum queryMode) {
		this.queryMode = queryMode;
	}

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
		ValidateParamUtil.hasTextV2(this.licenseNo, "licenseNo");
		ValidateParamUtil.hasTextV2(this.customName, "customName");
		ValidateParamUtil.hasTrue(queryMode!=null, "查询模式必填");
		queryMode.checkConditions(conditions);
		
	}
	
	@Override
	public CoreExportTypeEnum getExportType() {
		return CoreExportTypeEnum.POWER_MONTH;
	}
	@Override
	public boolean isRedirect() {
		return false;
	}

}
