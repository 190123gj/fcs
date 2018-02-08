package com.born.fcs.fm.ws.info.payment;

import java.util.Date;
import java.util.List;

import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

public class FormLabourCapitalInfo extends FormVOInfo {
	
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	//========== properties ==========
	
	private long labourCapitalId;
	
	private String billNo;
	
	private String voucherNo;
	
	private VoucherStatusEnum voucherStatus;
	
	private Date voucherSyncSendTime;
	
	private Date voucherSyncFinishTime;
	
	private String voucherSyncMessage;
	
	private long expenseDeptId;
	
	private String deptName;
	
	private String deptHead;
	
	private String reimburseReason;
	
	private BooleanEnum isOfficialCard;
	
	private Date applicationTime;
	
	private String relationForm;
	
	private long agentId;
	
	private String agent;
	
	private CostDirectionEnum direction;
	
	private long payeeId;
	
	private String payee;
	
	private long bankId;
	
	private String bank;
	
	private String bankAccount;
	
	private Money amount = new Money(0, 0);
	
	private String isReverse;
	
	private Money reamount = new Money(0, 0);
	
	private String cxids;
	
	private int attachmentsNum;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private String payBank;
	
	private String payBankAccount;
	
	private String shlc;
	
	private AccountStatusEnum accountStatus;
	
	private List<FormLabourCapitalDetailInfo> detailList;
	
	/** 修改记录 */
	private List<FormPayChangeDetailInfo> changeInfos;
	
	public List<FormPayChangeDetailInfo> getChangeInfos() {
		return this.changeInfos;
	}
	
	public void setChangeInfos(List<FormPayChangeDetailInfo> changeInfos) {
		this.changeInfos = changeInfos;
	}
	
	public String getPayBank() {
		return payBank;
	}
	
	public String getCxids() {
		return this.cxids;
	}
	
	public void setCxids(String cxids) {
		this.cxids = cxids;
	}
	
	public AccountStatusEnum getAccountStatus() {
		return this.accountStatus;
	}
	
	public void setAccountStatus(AccountStatusEnum accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}
	
	public String getPayBankAccount() {
		return payBankAccount;
	}
	
	public void setPayBankAccount(String payBankAccount) {
		this.payBankAccount = payBankAccount;
	}
	
	public String getReimburseReason() {
		return this.reimburseReason;
	}
	
	public void setReimburseReason(String reimburseReason) {
		this.reimburseReason = reimburseReason;
	}
	
	public String getShlc() {
		return shlc;
	}
	
	public void setShlc(String shlc) {
		this.shlc = shlc;
	}
	
	public String getIsReverse() {
		return isReverse;
	}
	
	public void setIsReverse(String isReverse) {
		this.isReverse = isReverse;
	}
	
	public long getAgentId() {
		return agentId;
	}
	
	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}
	
	public long getBankId() {
		return bankId;
	}
	
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	
	public int getAttachmentsNum() {
		return attachmentsNum;
	}
	
	public void setAttachmentsNum(int attachmentsNum) {
		this.attachmentsNum = attachmentsNum;
	}
	
	public Money getReamount() {
		return reamount;
	}
	
	public void setReamount(Money reamount) {
		this.reamount = reamount;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public long getPayeeId() {
		return payeeId;
	}
	
	public void setPayeeId(long payeeId) {
		this.payeeId = payeeId;
	}
	
	public long getLabourCapitalId() {
		return this.labourCapitalId;
	}
	
	public void setLabourCapitalId(long labourCapitalId) {
		this.labourCapitalId = labourCapitalId;
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
	
	public long getExpenseDeptId() {
		return expenseDeptId;
	}
	
	public void setExpenseDeptId(long expenseDeptId) {
		this.expenseDeptId = expenseDeptId;
	}
	
	public String getDeptHead() {
		return deptHead;
	}
	
	public void setDeptHead(String deptHead) {
		this.deptHead = deptHead;
	}
	
	public Date getApplicationTime() {
		return applicationTime;
	}
	
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	
	public String getRelationForm() {
		return relationForm;
	}
	
	public void setRelationForm(String relationForm) {
		this.relationForm = relationForm;
	}
	
	public String getAgent() {
		return agent;
	}
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	public CostDirectionEnum getDirection() {
		return direction;
	}
	
	public void setDirection(CostDirectionEnum direction) {
		this.direction = direction;
	}
	
	public String getPayee() {
		return payee;
	}
	
	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	public String getBank() {
		return bank;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getBankAccount() {
		return bankAccount;
	}
	
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		if (amount == null) {
			this.amount = new Money(0, 0);
		} else {
			this.amount = amount;
		}
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public VoucherStatusEnum getVoucherStatus() {
		return voucherStatus;
	}
	
	public void setVoucherStatus(VoucherStatusEnum voucherStatus) {
		this.voucherStatus = voucherStatus;
	}
	
	public BooleanEnum getIsOfficialCard() {
		return isOfficialCard;
	}
	
	public void setIsOfficialCard(BooleanEnum isOfficialCard) {
		this.isOfficialCard = isOfficialCard;
	}
	
	public List<FormLabourCapitalDetailInfo> getDetailList() {
		return this.detailList;
	}
	
	public void setDetailList(List<FormLabourCapitalDetailInfo> detailList) {
		this.detailList = detailList;
	}
	
}
