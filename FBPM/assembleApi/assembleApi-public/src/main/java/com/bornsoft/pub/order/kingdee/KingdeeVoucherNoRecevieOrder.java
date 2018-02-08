package com.bornsoft.pub.order.kingdee;

import com.bornsoft.utils.base.BornOutOrderBase;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.ValidateParamUtil;

/**
 * @Description: 权利凭证号接收 
 * @author taibai@yiji.com
 * @date 2016-10-7 下午2:50:20
 * @version V1.0
 */
public class KingdeeVoucherNoRecevieOrder extends BornOutOrderBase {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/****单据号******/
	private String bizNo;	
	/****凭证号******/
	private String voucherNo;	
	/****是否删除******/
	private Boolean isDelete;
	/****凭证同步完成时间******/
	private String voucherSyncFinishTime;
	
	@Override
	public void validateOrder() throws BornApiException {
		super.validateOrder();
		ValidateParamUtil.hasTextV2(bizNo, "bizNo");
		ValidateParamUtil.hasTextV2(voucherNo, "voucherNo");
		ValidateParamUtil.hasTrue(isDelete!=null, "isDelete 不能为空");
	}
	public String getBizNo() {
		return bizNo;
	}
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	/**
	 * yyyy-MM-dd
	 * @return
	 */
	public String getVoucherSyncFinishTime() {
		return voucherSyncFinishTime;
	}
	public void setVoucherSyncFinishTime(String voucherSyncFinishTime) {
		this.voucherSyncFinishTime = voucherSyncFinishTime;
	}	
	

}
