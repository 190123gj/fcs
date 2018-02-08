package com.born.fcs.fm.ws.order.common;

import java.util.Date;

import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/***
 * 更新凭证Order
 * @author wuzj
 */
public class UpdateVoucherOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -3473687725987677536L;
	
	/** 单据编号 */
	private String billNo;
	/** 凭证号 */
	private String voucherNo;
	/** 凭证号发送状态 */
	private VoucherStatusEnum voucherStatus;
	/** 凭证同步发送时间 */
	private Date voucherSyncSendTime;
	/** 凭证同步完成时间 */
	private Date voucherSyncFinishTime;
	/** 凭证同步结果 */
	private String voucherSyncMessage;
	
	@Override
	public void check() {
		validateNotNull(billNo, "单据号");
	}
	
	public String getBillNo() {
		return billNo;
	}
	
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public String getVoucherNo() {
		return voucherNo;
	}
	
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	public VoucherStatusEnum getVoucherStatus() {
		return voucherStatus;
	}
	
	public void setVoucherStatus(VoucherStatusEnum voucherStatus) {
		this.voucherStatus = voucherStatus;
	}
	
	public Date getVoucherSyncSendTime() {
		return voucherSyncSendTime;
	}
	
	public void setVoucherSyncSendTime(Date voucherSyncSendTime) {
		this.voucherSyncSendTime = voucherSyncSendTime;
	}
	
	public Date getVoucherSyncFinishTime() {
		return voucherSyncFinishTime;
	}
	
	public void setVoucherSyncFinishTime(Date voucherSyncFinishTime) {
		this.voucherSyncFinishTime = voucherSyncFinishTime;
	}
	
	public String getVoucherSyncMessage() {
		return voucherSyncMessage;
	}
	
	public void setVoucherSyncMessage(String voucherSyncMessage) {
		this.voucherSyncMessage = voucherSyncMessage;
	}
	
}
