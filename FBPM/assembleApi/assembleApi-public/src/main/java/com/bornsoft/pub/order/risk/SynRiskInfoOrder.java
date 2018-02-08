package com.bornsoft.pub.order.risk;

import java.util.List;

import com.bornsoft.pub.enums.RiskTypeEnum;
import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.utils.tool.ValidateParamUtil;
import com.yjf.common.lang.util.money.Money;

/**
  * @Description: 客户风险信息同步接口 
  * @author taibai@yiji.com 
  * @date  2016-8-11 下午3:20:29
  * @version V1.0
 */
public class SynRiskInfoOrder extends BornOrderBase {
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private List<RiskInfo> list;
	
	public List<RiskInfo> getList() {
		return list;
	}

	public void setList(List<RiskInfo> list) {
		this.list = list;
	}
	
	public static class RiskInfo extends BornInfoBase{
		/*** 证件号码 */
		private String licenseNo;
		/*** 企业名称或个人姓名 */
		private String customName;
		/** 风险类型 **/
		private RiskTypeEnum riskType;
		/** 授信金额 **/
		private Money creditAmount;
		/** 授信期限(起始)[yyyy-MM-dd] **/
		private String creditStartTime;
		/** 授信期限(结束)[yyyy-MM-dd] **/
		private String creditEndTime;

		public String getLicenseNo() {
			return licenseNo;
		}

		public void setLicenseNo(String licenseNo) {
			this.licenseNo = licenseNo;
		}
		
		public String getCreditStartTime() {
			return creditStartTime;
		}

		public void setCreditStartTime(String creditStartTime) {
			this.creditStartTime = creditStartTime;
		}

		public String getCreditEndTime() {
			return creditEndTime;
		}

		public void setCreditEndTime(String creditEndTime) {
			this.creditEndTime = creditEndTime;
		}

		public String getCustomName() {
			return customName;
		}

		public void setCustomName(String customName) {
			this.customName = customName;
		}

		public RiskTypeEnum getRiskType() {
			return riskType;
		}

		public void setRiskType(RiskTypeEnum riskType) {
			this.riskType = riskType;
		}


		public Money getCreditAmount() {
			return creditAmount;
		}

		public void setCreditAmount(Money creditAmount) {
			this.creditAmount = creditAmount;
		}

		public void validateOrder() throws BornApiException {
			ValidateParamUtil.hasTrue(riskType != null, "风险类型[riskType]非法");
			ValidateParamUtil.hasTextV2(licenseNo, "licenseNo");
			ValidateParamUtil.hasTrue(creditAmount!=null && this.creditAmount.getCent()>0, "creditAmount");
			ValidateParamUtil.checkDateRange(this.creditStartTime, this.creditEndTime, DateUtils.PATTERN1);
			ValidateParamUtil.hasTextV2(this.customName, "customName");
		}
	}

	@Override
	public String getService() {
		return "synRiskInfo";
	}
	
	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTrue(list!=null&list.size()>0, "风险信息不能为空");
		for(RiskInfo info : list){
			info.validateOrder();
		}
	}
}
