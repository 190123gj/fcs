package com.born.fcs.fm.ws.info.payment;

import java.util.Date;
import java.util.List;

import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.BranchPayStatusEnum;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

public class FormTravelExpenseInfo extends FormVOInfo {
	
	private static final long serialVersionUID = 4894584897300138960L;
	/** 主键 */
	private Long travelId;
	/** 单据号 */
	private String billNo;
	/** 凭证号 */
	private String voucherNo;
	private VoucherStatusEnum voucherStatus;
	private Date voucherSyncSendTime;
	private Date voucherSyncFinishTime;
	private String voucherSyncMessage;
	
	/** 报销部门ID */
	private Long expenseDeptId;
	/** 部门负责人 */
	private String deptHead;
	/** 公务卡支付 */
	private BooleanEnum isOfficialCard;
	/** 申请日期 */
	private Date applicationTime;
	/** 关联出差申请单 */
	private String relationForm;
	/** 出差人员,多人用逗号隔开 */
	private String travelers;
	/** 出差事由 */
	private String reasons;
	/** 收款人 */
	private String payee;
	/** 开户银行 */
	private String bank;
	/** 银行账号 */
	private String bankAccount;
	
	private String payBank;
	
	private String payBankAccount;
	
	/** 金额 */
	private Money amount = new Money(0, 0);
	/** 附件数 */
	private Integer attachmentsNum;
	/** 附件 */
	private String attachments;
	
	private AccountStatusEnum accountStatus;
	
	/** 创建时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	private String deptName;
	private long payeeId;
	
	/** 报销明细 */
	private List<FormTravelExpenseDetailInfo> detailList;
	
	/** 修改记录 */
	private List<FormPayChangeDetailInfo> changeInfos;
	
	/** 分支机构付款待确认时间 */
	private Date branchWaitPayTime;
	/** 分支机构付款确认时间 */
	private Date branchPayTime;
	/** 分支机构付款状态 */
	private BranchPayStatusEnum branchPayStatus;
	
	public String getPayBank() {
		return payBank;
	}
	
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}
	
	public AccountStatusEnum getAccountStatus() {
		return this.accountStatus;
	}
	
	public void setAccountStatus(AccountStatusEnum accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public List<FormPayChangeDetailInfo> getChangeInfos() {
		return this.changeInfos;
	}
	
	public void setChangeInfos(List<FormPayChangeDetailInfo> changeInfos) {
		this.changeInfos = changeInfos;
	}
	
	public String getPayBankAccount() {
		return payBankAccount;
	}
	
	public void setPayBankAccount(String payBankAccount) {
		this.payBankAccount = payBankAccount;
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
	
	public String getDeptHead() {
		return deptHead;
	}
	
	public void setDeptHead(String deptHead) {
		this.deptHead = deptHead;
	}
	
	public Long getTravelId() {
		return travelId;
	}
	
	public void setTravelId(Long travelId) {
		this.travelId = travelId;
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
	
	public Long getExpenseDeptId() {
		return expenseDeptId;
	}
	
	public void setExpenseDeptId(Long expenseDeptId) {
		this.expenseDeptId = expenseDeptId;
	}
	
	public BooleanEnum getIsOfficialCard() {
		return isOfficialCard;
	}
	
	public void setIsOfficialCard(BooleanEnum isOfficialCard) {
		this.isOfficialCard = isOfficialCard;
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
	
	public String getTravelers() {
		return travelers;
	}
	
	public void setTravelers(String travelers) {
		this.travelers = travelers;
	}
	
	public String getReasons() {
		return reasons;
	}
	
	public void setReasons(String reasons) {
		this.reasons = reasons;
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
		this.amount = amount;
	}
	
	public Integer getAttachmentsNum() {
		return attachmentsNum;
	}
	
	public void setAttachmentsNum(Integer attachmentsNum) {
		this.attachmentsNum = attachmentsNum;
	}
	
	public String getAttachments() {
		return attachments;
	}
	
	public void setAttachments(String attachments) {
		this.attachments = attachments;
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
	
	public List<FormTravelExpenseDetailInfo> getDetailList() {
		return detailList;
	}
	
	public void setDetailList(List<FormTravelExpenseDetailInfo> detailList) {
		this.detailList = detailList;
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

	public Date getBranchWaitPayTime() {
		return this.branchWaitPayTime;
	}

	public void setBranchWaitPayTime(Date branchWaitPayTime) {
		this.branchWaitPayTime = branchWaitPayTime;
	}

	public Date getBranchPayTime() {
		return this.branchPayTime;
	}

	public void setBranchPayTime(Date branchPayTime) {
		this.branchPayTime = branchPayTime;
	}

	public BranchPayStatusEnum getBranchPayStatus() {
		return this.branchPayStatus;
	}

	public void setBranchPayStatus(BranchPayStatusEnum branchPayStatus) {
		this.branchPayStatus = branchPayStatus;
	}
	
}
