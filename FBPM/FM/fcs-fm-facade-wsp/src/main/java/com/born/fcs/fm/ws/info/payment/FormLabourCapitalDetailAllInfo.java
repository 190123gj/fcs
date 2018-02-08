package com.born.fcs.fm.ws.info.payment;

import java.util.Date;

import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.yjf.common.lang.util.money.Money;

public class FormLabourCapitalDetailAllInfo extends FormExpenseApplicationInfo {
	
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
	
	/** 主表部门名 */
	private String IndexDeptName;
	
	/** 主表部门负责人 */
	private String IndexDeptHead;
	
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
	
	/** 主表金额 */
	private Money indexAmount = new Money(0, 0);
	
	private String isReverse;
	
	private Money reamount = new Money(0, 0);
	
	private int attachmentsNum;
	
	private String payBank;
	
	private String payBankAccount;
	
	private String shlc;
	
	private AccountStatusEnum accountstatus;
	
	//------------------------------------------
	private long detailId;
	
	private String expenseType;
	
	private Money amount = new Money(0, 0);
	
	private BooleanEnum reverse;
	
	private Money taxAmount = new Money(0, 0);
	
	private Money fpAmount = new Money(0, 0);
	
	private Money xjAmount = new Money(0, 0);
	
	private long deptId;
	
	private String deptName;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private Money balanceAmount = new Money(0, 0);
	
	private Date waitPayTime;
	
	//	private List<FormExpenseApplicationDetailInfo> detailList;
	//	
	//	private List<ExpenseCxDetailInfo> cxdetailList;
	
	public String getPayBank() {
		return payBank;
	}
	
	public Date getWaitPayTime() {
		return this.waitPayTime;
	}
	
	public void setWaitPayTime(Date waitPayTime) {
		this.waitPayTime = waitPayTime;
	}
	
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}
	
	public String getPayBankAccount() {
		return payBankAccount;
	}
	
	public BooleanEnum getReverse() {
		return this.reverse;
	}
	
	public void setReverse(BooleanEnum reverse) {
		this.reverse = reverse;
	}
	
	public void setPayBankAccount(String payBankAccount) {
		this.payBankAccount = payBankAccount;
	}
	
	public AccountStatusEnum getAccountstatus() {
		return this.accountstatus;
	}
	
	public void setAccountstatus(AccountStatusEnum accountstatus) {
		this.accountstatus = accountstatus;
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
	
	public String getIndexDeptName() {
		return this.IndexDeptName;
	}
	
	public void setIndexDeptName(String indexDeptName) {
		IndexDeptName = indexDeptName;
	}
	
	public String getIndexDeptHead() {
		return this.IndexDeptHead;
	}
	
	public void setIndexDeptHead(String indexDeptHead) {
		IndexDeptHead = indexDeptHead;
	}
	
	public Money getIndexAmount() {
		return this.indexAmount;
	}
	
	public void setIndexAmount(Money indexAmount) {
		this.indexAmount = indexAmount;
	}
	
	public long getDetailId() {
		return this.detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public String getExpenseType() {
		return this.expenseType;
	}
	
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	
	public Money getTaxAmount() {
		return this.taxAmount;
	}
	
	public void setTaxAmount(Money taxAmount) {
		this.taxAmount = taxAmount;
	}
	
	public Money getFpAmount() {
		return this.fpAmount;
	}
	
	public void setFpAmount(Money fpAmount) {
		this.fpAmount = fpAmount;
	}
	
	public Money getXjAmount() {
		return this.xjAmount;
	}
	
	public void setXjAmount(Money xjAmount) {
		this.xjAmount = xjAmount;
	}
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public Money getBalanceAmount() {
		return this.balanceAmount;
	}
	
	public void setBalanceAmount(Money balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
}
