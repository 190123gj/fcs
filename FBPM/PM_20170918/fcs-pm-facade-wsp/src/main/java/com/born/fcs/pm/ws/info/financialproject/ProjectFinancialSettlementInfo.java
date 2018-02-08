package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/** 理财结息 */
public class ProjectFinancialSettlementInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 5512090690229181686L;
	/** 主键 */
	private long settlementId;
	/** 项目百编号 */
	private String projectCode;
	/** 立项编号 */
	private String originalCode;
	/** 产品ID */
	private long productId;
	/** 产品名称 */
	private String productName;
	/** 结息金额 */
	private Money settlementAmount = new Money(0, 0);
	/** 结息时间 */
	private Date settlementTime;
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	
	public long getSettlementId() {
		return this.settlementId;
	}
	
	public void setSettlementId(long settlementId) {
		this.settlementId = settlementId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getOriginalCode() {
		return this.originalCode;
	}
	
	public void setOriginalCode(String originalCode) {
		this.originalCode = originalCode;
	}
	
	public long getProductId() {
		return this.productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Money getSettlementAmount() {
		return this.settlementAmount;
	}
	
	public void setSettlementAmount(Money settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	
	public Date getSettlementTime() {
		return this.settlementTime;
	}
	
	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
