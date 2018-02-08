package com.bornsoft.pub.order.kingdee;

import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * @Description: 同步职工信息
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午10:50:20
 * @version:     v1.0
 */
public class KingdeeClerkaOrder extends BornOrderBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 职员编号 **/
	private String userCode;
	/** 用户名称 **/
	private String userName;
	
	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTextV2(userCode, "userCode");
		ValidateParamUtil.hasTextV2(userName, "userName");
	}
	
	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}
	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
