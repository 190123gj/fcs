package com.bornsoft.pub.order.kingdee;

import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * @Description: 同步合同信息
 * @author:      xiaohui@yiji.com
 * @date         2016-9-1 上午10:51:59
 * @version:     v1.0
 */
public class KingdeeContractOrder extends BornOrderBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 合同编号 **/
	private String contractCode;
	/** 合同名称 **/
	private String contractName;
	
	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTextV2(contractCode, "contractCode");
		ValidateParamUtil.hasTextV2(contractName, "contractName");
	}
	
	/**
	 * @return the contractCode
	 */
	public String getContractCode() {
		return contractCode;
	}
	/**
	 * @param contractCode the contractCode to set
	 */
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	/**
	 * @return the contractName
	 */
	public String getContractName() {
		return contractName;
	}
	/**
	 * @param contractName the contractName to set
	 */
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
}
