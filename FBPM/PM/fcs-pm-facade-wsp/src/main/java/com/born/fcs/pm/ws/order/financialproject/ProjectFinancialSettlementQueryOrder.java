package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;

/** 结息查询 */
public class ProjectFinancialSettlementQueryOrder extends QueryPageBase {
	private static final long serialVersionUID = 2384908242957034684L;
	/** 项目编号 */
	private String projectCode;
	/** 立项编号 */
	private String originalCode;
	/** 产品ID */
	private long productId;
	/** 产品名称 */
	private String productName;
	/** 结息时间 */
	private Date settlementTimeStart;
	private Date settlementTimeEnd;
	
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
	
	public Date getSettlementTimeStart() {
		return this.settlementTimeStart;
	}
	
	public void setSettlementTimeStart(Date settlementTimeStart) {
		this.settlementTimeStart = settlementTimeStart;
	}
	
	public Date getSettlementTimeEnd() {
		return this.settlementTimeEnd;
	}
	
	public void setSettlementTimeEnd(Date settlementTimeEnd) {
		this.settlementTimeEnd = settlementTimeEnd;
	}
	
}
