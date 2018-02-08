package com.bornsoft.pub.order.risk;

import java.util.List;

import com.bornsoft.pub.enums.UserTypeEnum;
import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/***
  * @Description: 被监控名录同步 
  * @author taibai@yiji.com 
  * @date  2016-8-6 下午3:36:53
  * @version V1.0
 */
public class SynWatchListOrder extends BornOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private List<WatchListInfo> list;
	
	public List<WatchListInfo> getList() {
		return list;
	}

	public void setList(List<WatchListInfo> list) {
		this.list = list;
	}
	
	/***
	 * @Description: 监控名录
	 * @author taibai@yiji.com
	 * @date 2016-8-6 下午3:18:03
	 * @version V1.0
	 */
	public static  class WatchListInfo extends BornInfoBase {
		
		/** 企业营业执照号码或身份证号码 */
		private String licenseNo;
		/** 企业名称或个人姓名 */
		private String customName;
		/** 用户类型 */
		private UserTypeEnum userType;
		/** 操作员账户名 */
		private String operator;

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

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}
		
		@Override
		public void validateOrder() throws BornApiException {
			super.validateOrder();
			ValidateParamUtil.hasTrue(userType!=null, "用户类型[userType]不合法");
			ValidateParamUtil.hasText(licenseNo, "证照号码[licenseNo]不能为空");
			ValidateParamUtil.hasText(this.customName, "客户名称[customName]不能为空");
		}
	}

	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTrue(list!=null && list.size()>0,"监控名录不能为空");
		for(WatchListInfo info : list){
			info.validateOrder();
		}
	}

	@Override
	public String getService() {
		return "synWatchList";
	}

}
