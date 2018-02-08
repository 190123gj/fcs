package com.born.fcs.fm.ws.order.payment;

import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.money.Money;

public class ExpenseApplicationOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 1L;
	
	private long expenseApplicationId;
	
	private String billNo;
	
	private String voucherNo;
	
	private VoucherStatusEnum voucherStatus;
	
	private Date voucherSyncSendTime;
	
	private Date voucherSyncFinishTime;
	
	private String voucherSyncMessage;
	
	private long expenseDeptId;
	
	private String expenseDeptName;
	
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
	
	private List<ExpenseApplicationDetailOrder> detailList;
	
	private List<ExpenseCxDetailOrder> cxDetailList;
	
	private BooleanEnum onlyChangeDetailList;
	
	/**
	 * 已冲销的id
	 */
	private List<String> reverseIds;
	
	@Override
	public void check() {
		Assert.isTrue(expenseDeptId > 0, "申请部门不能为空");
		//		validateNotNull(applicationTime, "申请日期");
		//		validateNotNull(deptHead, "部门负责人");
		//		validateNotNull(agent, "经办人");
		//		validateNotNull(bankAccount, "银行账号");
		//		validateNotNull(payee, "收款人");
		//		validateNotNull(detailList, "申请明细");
		Assert.isTrue(detailList.size() > 0, "申请明细不能为空");
		for (ExpenseApplicationDetailOrder detailOrder : detailList) {
			detailOrder.check();
		}
	}
	
	public BooleanEnum getOnlyChangeDetailList() {
		return this.onlyChangeDetailList;
	}
	
	public List<String> getReverseIds() {
		return this.reverseIds;
	}
	
	public void setReverseIds(List<String> reverseIds) {
		this.reverseIds = reverseIds;
	}
	
	public String getCxids() {
		return this.cxids;
	}
	
	public void setCxids(String cxids) {
		this.cxids = cxids;
	}
	
	public void setOnlyChangeDetailList(BooleanEnum onlyChangeDetailList) {
		this.onlyChangeDetailList = onlyChangeDetailList;
	}
	
	public List<ExpenseCxDetailOrder> getCxDetailList() {
		return cxDetailList;
	}
	
	public void setCxDetailList(List<ExpenseCxDetailOrder> cxDetailList) {
		this.cxDetailList = cxDetailList;
	}
	
	public String getReimburseReason() {
		return this.reimburseReason;
	}
	
	public void setReimburseReason(String reimburseReason) {
		this.reimburseReason = reimburseReason;
	}
	
	public String getPayBank() {
		return payBank;
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
	
	public Integer getAttachmentsNum() {
		return attachmentsNum;
	}
	
	public void setAttachmentsNum(Integer attachmentsNum) {
		this.attachmentsNum = attachmentsNum;
	}
	
	public Money getReamount() {
		return reamount;
	}
	
	public void setReamount(Money reamount) {
		this.reamount = reamount;
	}
	
	public String getExpenseDeptName() {
		return expenseDeptName;
	}
	
	public void setExpenseDeptName(String expenseDeptName) {
		this.expenseDeptName = expenseDeptName;
	}
	
	public long getPayeeId() {
		return payeeId;
	}
	
	public void setPayeeId(long payeeId) {
		this.payeeId = payeeId;
	}
	
	public long getExpenseApplicationId() {
		return expenseApplicationId;
	}
	
	public void setExpenseApplicationId(long expenseApplicationId) {
		this.expenseApplicationId = expenseApplicationId;
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
	
	public List<ExpenseApplicationDetailOrder> getDetailList() {
		return detailList;
	}
	
	public void setDetailList(List<ExpenseApplicationDetailOrder> detailList) {
		this.detailList = detailList;
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
	
	public void setAttachmentsNum(int attachmentsNum) {
		this.attachmentsNum = attachmentsNum;
	}
	
}
