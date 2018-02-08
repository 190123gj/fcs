package com.bornsoft.pub.order.risk;

import java.util.List;

import com.bornsoft.pub.enums.CreditLevelEnum;
import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
  * @Description:   客户信用等级同步接口
  * @author taibai@yiji.com 
  * @date  2016-8-6 下午3:06:20
  * @version V1.0
 */
public class SynCustomLevelOrder extends BornOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private List<CustomLevelInfo> list;
	
	public static class CustomLevelInfo extends BornInfoBase{
		private String licenseNo;
		private String customName;
		/** 评级结果 */
		private CreditLevelEnum creditLevel;
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
		
		public CreditLevelEnum getCreditLevel() {
			return creditLevel;
		}
		public void setCreditLevel(CreditLevelEnum creditLevel) {
			this.creditLevel = creditLevel;
		}
		@Override
		public void validateOrder() throws BornApiException {
			ValidateParamUtil.hasTextV2(customName, "customName");
			ValidateParamUtil.hasTrue(creditLevel!=null, "creditLevel");
		}
		
	}

	public List<CustomLevelInfo> getList() {
		return list;
	}

	public void setList(List<CustomLevelInfo> list) {
		this.list = list;
	}

	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTrue(list!=null, "用户等级不能为空");
		for(CustomLevelInfo info : list){
			info.validateOrder();
		}
	}

	@Override
	public String getService() {
		return "synCustomLevel";
	}

}
