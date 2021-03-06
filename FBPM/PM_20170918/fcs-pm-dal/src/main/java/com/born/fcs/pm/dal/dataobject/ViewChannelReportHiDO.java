/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

// auto generated imports
import java.util.Date;
import com.yjf.common.lang.util.money.Money;

/**
 * A data object class directly models database table <tt>view_channel_report_his</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/view_channel_report_his.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
public class ViewChannelReportHiDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private Date projectDate;

	private long projectId;

	private String projectCode;

	private String projectName;

	private String customName1;

	private String phasesStatus;

	private String phases;

	private String customerName;

	private String enterpriseType;

	private String customerLevel;

	private String scale;

	private String provinceCode;

	private String provinceName;

	private String cityCode;

	private String cityName;

	private String industryCode;

	private String industryName;

	private String busiType;

	private String busiTypeName;

	private Date startTime;

	private Date endTime;

	private Money contractAmount = new Money(0, 0);

	private Money blance = new Money(0, 0);

	private Money accumulatedIssueAmount = new Money(0, 0);

	private Money loanedAmount = new Money(0, 0);

	private Money usedAmount = new Money(0, 0);

	private Money repayedAmount = new Money(0, 0);

	private Money chargedAmount = new Money(0, 0);

	private Money refundAmount = new Money(0, 0);

	private Money releasedAmount = new Money(0, 0);

	private Money amount = new Money(0, 0);

	private String fanGuaranteeMethord;

	private long channelId;

	private String channelCode;

	private String channelName;

	private String channelType;

	private String subChannelName;

	private String channelRelation;

	private Date setupDate;

	private Date customerAddTime;

	private Date rawUpdateTime;

	private long num;

    //========== getters and setters ==========

	public Date getProjectDate() {
		return projectDate;
	}
	
	public void setProjectDate(Date projectDate) {
		this.projectDate = projectDate;
	}

	public long getProjectId() {
		return projectId;
	}
	
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCustomName1() {
		return customName1;
	}
	
	public void setCustomName1(String customName1) {
		this.customName1 = customName1;
	}

	public String getPhasesStatus() {
		return phasesStatus;
	}
	
	public void setPhasesStatus(String phasesStatus) {
		this.phasesStatus = phasesStatus;
	}

	public String getPhases() {
		return phases;
	}
	
	public void setPhases(String phases) {
		this.phases = phases;
	}

	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEnterpriseType() {
		return enterpriseType;
	}
	
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}
	
	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getScale() {
		return scale;
	}
	
	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getProvinceCode() {
		return provinceCode;
	}
	
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}
	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}
	
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getIndustryCode() {
		return industryCode;
	}
	
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getIndustryName() {
		return industryName;
	}
	
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}

	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Money getContractAmount() {
		return contractAmount;
	}
	
	public void setContractAmount(Money contractAmount) {
		if (contractAmount == null) {
			this.contractAmount = new Money(0, 0);
		} else {
			this.contractAmount = contractAmount;
		}
	}

	public Money getBlance() {
		return blance;
	}
	
	public void setBlance(Money blance) {
		if (blance == null) {
			this.blance = new Money(0, 0);
		} else {
			this.blance = blance;
		}
	}

	public Money getAccumulatedIssueAmount() {
		return accumulatedIssueAmount;
	}
	
	public void setAccumulatedIssueAmount(Money accumulatedIssueAmount) {
		if (accumulatedIssueAmount == null) {
			this.accumulatedIssueAmount = new Money(0, 0);
		} else {
			this.accumulatedIssueAmount = accumulatedIssueAmount;
		}
	}

	public Money getLoanedAmount() {
		return loanedAmount;
	}
	
	public void setLoanedAmount(Money loanedAmount) {
		if (loanedAmount == null) {
			this.loanedAmount = new Money(0, 0);
		} else {
			this.loanedAmount = loanedAmount;
		}
	}

	public Money getUsedAmount() {
		return usedAmount;
	}
	
	public void setUsedAmount(Money usedAmount) {
		if (usedAmount == null) {
			this.usedAmount = new Money(0, 0);
		} else {
			this.usedAmount = usedAmount;
		}
	}

	public Money getRepayedAmount() {
		return repayedAmount;
	}
	
	public void setRepayedAmount(Money repayedAmount) {
		if (repayedAmount == null) {
			this.repayedAmount = new Money(0, 0);
		} else {
			this.repayedAmount = repayedAmount;
		}
	}

	public Money getChargedAmount() {
		return chargedAmount;
	}
	
	public void setChargedAmount(Money chargedAmount) {
		if (chargedAmount == null) {
			this.chargedAmount = new Money(0, 0);
		} else {
			this.chargedAmount = chargedAmount;
		}
	}

	public Money getRefundAmount() {
		return refundAmount;
	}
	
	public void setRefundAmount(Money refundAmount) {
		if (refundAmount == null) {
			this.refundAmount = new Money(0, 0);
		} else {
			this.refundAmount = refundAmount;
		}
	}

	public Money getReleasedAmount() {
		return releasedAmount;
	}
	
	public void setReleasedAmount(Money releasedAmount) {
		if (releasedAmount == null) {
			this.releasedAmount = new Money(0, 0);
		} else {
			this.releasedAmount = releasedAmount;
		}
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

	public String getFanGuaranteeMethord() {
		return fanGuaranteeMethord;
	}
	
	public void setFanGuaranteeMethord(String fanGuaranteeMethord) {
		this.fanGuaranteeMethord = fanGuaranteeMethord;
	}

	public long getChannelId() {
		return channelId;
	}
	
	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	public String getChannelCode() {
		return channelCode;
	}
	
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelType() {
		return channelType;
	}
	
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getSubChannelName() {
		return subChannelName;
	}
	
	public void setSubChannelName(String subChannelName) {
		this.subChannelName = subChannelName;
	}

	public String getChannelRelation() {
		return channelRelation;
	}
	
	public void setChannelRelation(String channelRelation) {
		this.channelRelation = channelRelation;
	}

	public Date getSetupDate() {
		return setupDate;
	}
	
	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}

	public Date getCustomerAddTime() {
		return customerAddTime;
	}
	
	public void setCustomerAddTime(Date customerAddTime) {
		this.customerAddTime = customerAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

	public long getNum() {
		return num;
	}
	
	public void setNum(long num) {
		this.num = num;
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
