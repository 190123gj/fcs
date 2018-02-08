package com.bornsoft.pub.order.risk;

import java.util.List;

import com.bornsoft.pub.enums.UserTypeEnum;
import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornOutOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/***
  * @Description: 风险信息接收 
  * @author taibai@yiji.com 
  * @date  2016-8-6 下午5:27:12
  * @version V1.0
 */
public class RiskInfoRecOrder extends BornOutOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private List<RiskInfoEntity> list;
	
	public static class RiskInfoEntity extends BornInfoBase{
		/** 证照号码 */
		private String licenseNo;
		/** 用户类型 */
		private UserTypeEnum userType;
		/** 风险内容 */
		private String riskContent;
		/** 详情页 */
		private String detailUrl;
		/** 来源网站 */
		private String sourceSite;
		/** 操作员帐号 */
		private String operator;
		
		public String getLicenseNo() {
			return licenseNo;
		}

		public void setLicenseNo(String licenseNo) {
			this.licenseNo = licenseNo;
		}
		
		public String getSourceSite() {
			return sourceSite;
		}

		public void setSourceSite(String sourceSite) {
			this.sourceSite = sourceSite;
		}

		public UserTypeEnum getUserType() {
			return userType;
		}

		public void setUserType(UserTypeEnum userType) {
			this.userType = userType;
		}

		public String getRiskContent() {
			return riskContent;
		}

		public void setRiskContent(String riskContent) {
			this.riskContent = riskContent;
		}

		public String getDetailUrl() {
			return detailUrl;
		}

		public void setDetailUrl(String detailUrl) {
			this.detailUrl = detailUrl;
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
			ValidateParamUtil.hasTextV2(licenseNo, "licenseNo");
			ValidateParamUtil.hasTrue(userType!=null, "userType");
			ValidateParamUtil.hasTextV2(riskContent, "riskContent");
			ValidateParamUtil.hasTextV2(operator, "operator");
			
		}
	}
	
	public List<RiskInfoEntity> getList() {
		return list;
	}



	public void setList(List<RiskInfoEntity> list) {
		this.list = list;
	}


	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		if(list==null || list.size()==0){
			throw new BornApiException("没有风险信息");
		}
		for(RiskInfoEntity info : list){
			info.validateOrder();
		}
	}

}

