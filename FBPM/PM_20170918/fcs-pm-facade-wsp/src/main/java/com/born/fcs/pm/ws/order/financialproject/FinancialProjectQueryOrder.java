package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;

/**
 * 理财项目查询Order
 *
 * @author wuzj
 *
 */
public class FinancialProjectQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -7405547247747810034L;
	
	/** 状态列表 */
	private List<String> statusList;
	/** 产品ID */
	private Long productId;
	/** 产品类型 */
	private String productType;
	/** 产品名称 */
	private String productName;
	/** 期限类型 */
	private String termType;
	/** 发行机构 */
	private String issueInstitution;
	/** 创建人 */
	private Long createUserId;
	private String createUserAccount;
	private String createUserName;
	/** 状态 */
	private String status;
	/** 是否可提前赎回 YES/NO */
	private String canRedeem;
	/** 是否有持有份数 IS/NO */
	private BooleanEnum hasHoldNum;
	/** 是否有原始持有份数 IS/NO */
	private BooleanEnum hasOrignalHoldNum;
	/** 持有份数 */
	private Double holdNum;
	//查询该时间点到期的项目
	private Date actualExpireDate;
	/** 购买时间 */
	private Date buyDateStart;
	private Date buyDateEnd;
	/** 到期时间 */
	private Date expireDateStart;
	
	private Date expireDateEnd;
	
	/** 是否滚动 */
	private BooleanEnum isRoll;
	/** 是否开放 */
	private BooleanEnum isOpen;
	/** 结息方式 */
	private InterestSettlementWayEnum interestSettlementWay;
	/** 是否选择可申请合同项目 YES */
	private String chooseForContract;
	
	public List<String> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	
	public Long getProductId() {
		return this.productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getProductType() {
		return this.productType;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getTermType() {
		return this.termType;
	}
	
	public void setTermType(String termType) {
		this.termType = termType;
	}
	
	public String getIssueInstitution() {
		return this.issueInstitution;
	}
	
	public void setIssueInstitution(String issueInstitution) {
		this.issueInstitution = issueInstitution;
	}
	
	public Long getCreateUserId() {
		return this.createUserId;
	}
	
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getCreateUserAccount() {
		return this.createUserAccount;
	}
	
	public void setCreateUserAccount(String createUserAccount) {
		this.createUserAccount = createUserAccount;
	}
	
	public String getCreateUserName() {
		return this.createUserName;
	}
	
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCanRedeem() {
		return this.canRedeem;
	}
	
	public void setCanRedeem(String canRedeem) {
		this.canRedeem = canRedeem;
	}
	
	public BooleanEnum getHasHoldNum() {
		return this.hasHoldNum;
	}
	
	public void setHasHoldNum(BooleanEnum hasHoldNum) {
		this.hasHoldNum = hasHoldNum;
	}
	
	public BooleanEnum getHasOrignalHoldNum() {
		return this.hasOrignalHoldNum;
	}
	
	public void setHasOrignalHoldNum(BooleanEnum hasOrignalHoldNum) {
		this.hasOrignalHoldNum = hasOrignalHoldNum;
	}
	
	public Double getHoldNum() {
		return this.holdNum;
	}
	
	public void setHoldNum(Double holdNum) {
		this.holdNum = holdNum;
	}
	
	public Date getActualExpireDate() {
		return this.actualExpireDate;
	}
	
	public void setActualExpireDate(Date actualExpireDate) {
		this.actualExpireDate = actualExpireDate;
	}
	
	public Date getBuyDateStart() {
		return this.buyDateStart;
	}
	
	public void setBuyDateStart(Date buyDateStart) {
		this.buyDateStart = buyDateStart;
	}
	
	public Date getBuyDateEnd() {
		return this.buyDateEnd;
	}
	
	public void setBuyDateEnd(Date buyDateEnd) {
		this.buyDateEnd = buyDateEnd;
	}
	
	public Date getExpireDateStart() {
		return this.expireDateStart;
	}
	
	public void setExpireDateStart(Date expireDateStart) {
		this.expireDateStart = expireDateStart;
	}
	
	public Date getExpireDateEnd() {
		return this.expireDateEnd;
	}
	
	public void setExpireDateEnd(Date expireDateEnd) {
		this.expireDateEnd = expireDateEnd;
	}
	
	public BooleanEnum getIsRoll() {
		return this.isRoll;
	}
	
	public void setIsRoll(BooleanEnum isRoll) {
		this.isRoll = isRoll;
	}
	
	public BooleanEnum getIsOpen() {
		return this.isOpen;
	}
	
	public void setIsOpen(BooleanEnum isOpen) {
		this.isOpen = isOpen;
	}
	
	public InterestSettlementWayEnum getInterestSettlementWay() {
		return this.interestSettlementWay;
	}
	
	public void setInterestSettlementWay(InterestSettlementWayEnum interestSettlementWay) {
		this.interestSettlementWay = interestSettlementWay;
	}
	
	public String getChooseForContract() {
		return this.chooseForContract;
	}
	
	public void setChooseForContract(String chooseForContract) {
		this.chooseForContract = chooseForContract;
	}
}
