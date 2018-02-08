package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 项目评审会 - 授信条件 - 保证人
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectGuarantorOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 7262132246671219850L;
	
	private long id;
	
	private long spId;
	
	private String guarantorType; //保证类型
	
	private String guarantor;
	
	private String guaranteeAmountStr;
	
	private String guaranteeWay;
	
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(guarantor) && isNull(guarantorType) && isNull(guaranteeAmountStr)
				&& isNull(guaranteeWay);
	}
	
	public Money getGuaranteeAmount() {
		if (isNull(this.guaranteeAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.guaranteeAmountStr);
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getSpId() {
		return this.spId;
	}
	
	public void setSpId(long spId) {
		this.spId = spId;
	}
	
	public String getGuarantorType() {
		return this.guarantorType;
	}
	
	public void setGuarantorType(String guarantorType) {
		this.guarantorType = guarantorType;
	}
	
	public String getGuarantor() {
		return this.guarantor;
	}
	
	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}
	
	public String getGuaranteeAmountStr() {
		return this.guaranteeAmountStr;
	}
	
	public void setGuaranteeAmountStr(String guaranteeAmountStr) {
		this.guaranteeAmountStr = guaranteeAmountStr;
	}
	
	public String getGuaranteeWay() {
		return this.guaranteeWay;
	}
	
	public void setGuaranteeWay(String guaranteeWay) {
		this.guaranteeWay = guaranteeWay;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
}
