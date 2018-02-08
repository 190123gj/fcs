package com.born.fcs.pm.ws.order.finvestigation.declare;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.DateUtil;

/**
 * 
 * 尽调申明-调查人员
 *
 * @author lirz
 * 
 * 2016-9-14 下午5:41:36
 *
 */
public class FInvestigationPersonOrder extends ValidateOrderBase{

    private static final long serialVersionUID = 3140686434947201501L;

	//========== properties ==========

	private long id;

	private long formId;

	private Date investigateDate;

	private String investigatePlace;

	private long mainInvestigatorId;

	private String mainInvestigatorAccount;

	private String mainInvestigatorName;

	private String assistInvestigatorId;

	private String assistInvestigatorName;

	private String receptionPersion;

	private int sortOrder;

	private Date rawAddTime;

	private Date rawUpdateTime;

	public boolean isNull() {
		return null == investigateDate
				&& isNull(investigatePlace)
				&& mainInvestigatorId == 0L
				&& isNull(assistInvestigatorId)
				&& isNull(receptionPersion)
				;
	}
	
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

	public Date getInvestigateDate() {
		return investigateDate;
	}
	
	public void setInvestigateDate(Date investigateDate) {
		this.investigateDate = investigateDate;
	}
	
	public void setInvestigateDateStr(String investigateDateStr) {
		this.investigateDate = DateUtil.strToDtSimpleFormat(investigateDateStr);
	}

	public String getInvestigatePlace() {
		return investigatePlace;
	}
	
	public void setInvestigatePlace(String investigatePlace) {
		this.investigatePlace = investigatePlace;
	}

	public long getMainInvestigatorId() {
		return mainInvestigatorId;
	}
	
	public void setMainInvestigatorId(long mainInvestigatorId) {
		this.mainInvestigatorId = mainInvestigatorId;
	}

	public String getMainInvestigatorAccount() {
		return mainInvestigatorAccount;
	}
	
	public void setMainInvestigatorAccount(String mainInvestigatorAccount) {
		this.mainInvestigatorAccount = mainInvestigatorAccount;
	}

	public String getMainInvestigatorName() {
		return mainInvestigatorName;
	}
	
	public void setMainInvestigatorName(String mainInvestigatorName) {
		this.mainInvestigatorName = mainInvestigatorName;
	}

	public String getAssistInvestigatorId() {
		return assistInvestigatorId;
	}
	
	public void setAssistInvestigatorId(String assistInvestigatorId) {
		this.assistInvestigatorId = assistInvestigatorId;
	}

	public String getAssistInvestigatorName() {
		return assistInvestigatorName;
	}
	
	public void setAssistInvestigatorName(String assistInvestigatorName) {
		this.assistInvestigatorName = assistInvestigatorName;
	}

	public String getReceptionPersion() {
		return receptionPersion;
	}
	
	public void setReceptionPersion(String receptionPersion) {
		this.receptionPersion = receptionPersion;
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
