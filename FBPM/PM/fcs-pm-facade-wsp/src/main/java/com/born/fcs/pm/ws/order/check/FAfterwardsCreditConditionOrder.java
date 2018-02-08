package com.born.fcs.pm.ws.order.check;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.DateUtil;

/**
 * 
 * 保后 - 授信落实条件
 *
 * @author lirz
 * 
 * 2016-11-23 下午2:30:41
 *
 */
public class FAfterwardsCreditConditionOrder extends ValidateOrderBase{

    private static final long serialVersionUID = -7702845813346731558L;

	private long id;

	private long formId;

	private String itemDesc;

	private String type;

	private String confirmManName;

	private String confirmDateStr;

	private String contractNo;

	private int sortOrder;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getItemDesc() {
		return itemDesc;
	}
	
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getConfirmManName() {
		return confirmManName;
	}
	
	public void setConfirmManName(String confirmManName) {
		this.confirmManName = confirmManName;
	}

	public Date getConfirmDate() {
		return null == this.confirmDateStr ? null : DateUtil
			.strToDtSimpleFormat(this.confirmDateStr);
	}
	
	public String getConfirmDateStr() {
		return this.confirmDateStr;
	}
	
	public void setConfirmDateStr(String confirmDateStr) {
		this.confirmDateStr = confirmDateStr;
	}

	public String getContractNo() {
		return contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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


	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
