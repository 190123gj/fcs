/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

// auto generated imports
import com.yjf.common.lang.util.money.Money;
import java.util.Date;

/**
 * A data object class directly models database table <tt>project_channel_relation</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/project_channel_relation.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
public class ProjectChannelRelationDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long id;

	private String bizNo;

	private String phases;

	private String projectCode;

	private String busiType;

	private String busiTypeName;

	private String channelRelation;

	private long channelId;

	private String channelCode;

	private String channelType;

	private String channelName;

	private long subChannelId;

	private String subChannelCode;

	private String subChannelType;

	private String subChannelName;

	private Money contractAmount = new Money(0, 0);

	private Money liquidityLoansAmount = new Money(0, 0);

	private Money financialAmount = new Money(0, 0);

	private Money acceptanceBillAmount = new Money(0, 0);

	private Money creditAmount = new Money(0, 0);

	private Money loanedAmount = new Money(0, 0);

	private Money loanLiquidityLoansAmount = new Money(0, 0);

	private Money loanFinancialAmount = new Money(0, 0);

	private Money loanAcceptanceBillAmount = new Money(0, 0);

	private Money loanCreditAmount = new Money(0, 0);

	private Money usedAmount = new Money(0, 0);

	private Money compAmount = new Money(0, 0);

	private Money compLiquidityLoansAmount = new Money(0, 0);

	private Money compFinancialAmount = new Money(0, 0);

	private Money compAcceptanceBillAmount = new Money(0, 0);

	private Money compCreditAmount = new Money(0, 0);

	private Money releasableAmount = new Money(0, 0);

	private Money releasedAmount = new Money(0, 0);

	private Money releaseLiquidityLoansAmount = new Money(0, 0);

	private Money releaseFinancialAmount = new Money(0, 0);

	private Money releaseAcceptanceBillAmount = new Money(0, 0);

	private Money releaseCreditAmount = new Money(0, 0);

	private Money repayedAmount = new Money(0, 0);

	private Money inAmount = new Money(0, 0);

	private double newCustomer;

	private double newProject;

	private double inCutomer;

	private double inProject;

	private String latest;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getBizNo() {
		return bizNo;
	}
	
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getPhases() {
		return phases;
	}
	
	public void setPhases(String phases) {
		this.phases = phases;
	}

	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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

	public String getChannelRelation() {
		return channelRelation;
	}
	
	public void setChannelRelation(String channelRelation) {
		this.channelRelation = channelRelation;
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

	public String getChannelType() {
		return channelType;
	}
	
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getChannelName() {
		return channelName;
	}
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public long getSubChannelId() {
		return subChannelId;
	}
	
	public void setSubChannelId(long subChannelId) {
		this.subChannelId = subChannelId;
	}

	public String getSubChannelCode() {
		return subChannelCode;
	}
	
	public void setSubChannelCode(String subChannelCode) {
		this.subChannelCode = subChannelCode;
	}

	public String getSubChannelType() {
		return subChannelType;
	}
	
	public void setSubChannelType(String subChannelType) {
		this.subChannelType = subChannelType;
	}

	public String getSubChannelName() {
		return subChannelName;
	}
	
	public void setSubChannelName(String subChannelName) {
		this.subChannelName = subChannelName;
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

	public Money getLiquidityLoansAmount() {
		return liquidityLoansAmount;
	}
	
	public void setLiquidityLoansAmount(Money liquidityLoansAmount) {
		if (liquidityLoansAmount == null) {
			this.liquidityLoansAmount = new Money(0, 0);
		} else {
			this.liquidityLoansAmount = liquidityLoansAmount;
		}
	}

	public Money getFinancialAmount() {
		return financialAmount;
	}
	
	public void setFinancialAmount(Money financialAmount) {
		if (financialAmount == null) {
			this.financialAmount = new Money(0, 0);
		} else {
			this.financialAmount = financialAmount;
		}
	}

	public Money getAcceptanceBillAmount() {
		return acceptanceBillAmount;
	}
	
	public void setAcceptanceBillAmount(Money acceptanceBillAmount) {
		if (acceptanceBillAmount == null) {
			this.acceptanceBillAmount = new Money(0, 0);
		} else {
			this.acceptanceBillAmount = acceptanceBillAmount;
		}
	}

	public Money getCreditAmount() {
		return creditAmount;
	}
	
	public void setCreditAmount(Money creditAmount) {
		if (creditAmount == null) {
			this.creditAmount = new Money(0, 0);
		} else {
			this.creditAmount = creditAmount;
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

	public Money getLoanLiquidityLoansAmount() {
		return loanLiquidityLoansAmount;
	}
	
	public void setLoanLiquidityLoansAmount(Money loanLiquidityLoansAmount) {
		if (loanLiquidityLoansAmount == null) {
			this.loanLiquidityLoansAmount = new Money(0, 0);
		} else {
			this.loanLiquidityLoansAmount = loanLiquidityLoansAmount;
		}
	}

	public Money getLoanFinancialAmount() {
		return loanFinancialAmount;
	}
	
	public void setLoanFinancialAmount(Money loanFinancialAmount) {
		if (loanFinancialAmount == null) {
			this.loanFinancialAmount = new Money(0, 0);
		} else {
			this.loanFinancialAmount = loanFinancialAmount;
		}
	}

	public Money getLoanAcceptanceBillAmount() {
		return loanAcceptanceBillAmount;
	}
	
	public void setLoanAcceptanceBillAmount(Money loanAcceptanceBillAmount) {
		if (loanAcceptanceBillAmount == null) {
			this.loanAcceptanceBillAmount = new Money(0, 0);
		} else {
			this.loanAcceptanceBillAmount = loanAcceptanceBillAmount;
		}
	}

	public Money getLoanCreditAmount() {
		return loanCreditAmount;
	}
	
	public void setLoanCreditAmount(Money loanCreditAmount) {
		if (loanCreditAmount == null) {
			this.loanCreditAmount = new Money(0, 0);
		} else {
			this.loanCreditAmount = loanCreditAmount;
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

	public Money getCompAmount() {
		return compAmount;
	}
	
	public void setCompAmount(Money compAmount) {
		if (compAmount == null) {
			this.compAmount = new Money(0, 0);
		} else {
			this.compAmount = compAmount;
		}
	}

	public Money getCompLiquidityLoansAmount() {
		return compLiquidityLoansAmount;
	}
	
	public void setCompLiquidityLoansAmount(Money compLiquidityLoansAmount) {
		if (compLiquidityLoansAmount == null) {
			this.compLiquidityLoansAmount = new Money(0, 0);
		} else {
			this.compLiquidityLoansAmount = compLiquidityLoansAmount;
		}
	}

	public Money getCompFinancialAmount() {
		return compFinancialAmount;
	}
	
	public void setCompFinancialAmount(Money compFinancialAmount) {
		if (compFinancialAmount == null) {
			this.compFinancialAmount = new Money(0, 0);
		} else {
			this.compFinancialAmount = compFinancialAmount;
		}
	}

	public Money getCompAcceptanceBillAmount() {
		return compAcceptanceBillAmount;
	}
	
	public void setCompAcceptanceBillAmount(Money compAcceptanceBillAmount) {
		if (compAcceptanceBillAmount == null) {
			this.compAcceptanceBillAmount = new Money(0, 0);
		} else {
			this.compAcceptanceBillAmount = compAcceptanceBillAmount;
		}
	}

	public Money getCompCreditAmount() {
		return compCreditAmount;
	}
	
	public void setCompCreditAmount(Money compCreditAmount) {
		if (compCreditAmount == null) {
			this.compCreditAmount = new Money(0, 0);
		} else {
			this.compCreditAmount = compCreditAmount;
		}
	}

	public Money getReleasableAmount() {
		return releasableAmount;
	}
	
	public void setReleasableAmount(Money releasableAmount) {
		if (releasableAmount == null) {
			this.releasableAmount = new Money(0, 0);
		} else {
			this.releasableAmount = releasableAmount;
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

	public Money getReleaseLiquidityLoansAmount() {
		return releaseLiquidityLoansAmount;
	}
	
	public void setReleaseLiquidityLoansAmount(Money releaseLiquidityLoansAmount) {
		if (releaseLiquidityLoansAmount == null) {
			this.releaseLiquidityLoansAmount = new Money(0, 0);
		} else {
			this.releaseLiquidityLoansAmount = releaseLiquidityLoansAmount;
		}
	}

	public Money getReleaseFinancialAmount() {
		return releaseFinancialAmount;
	}
	
	public void setReleaseFinancialAmount(Money releaseFinancialAmount) {
		if (releaseFinancialAmount == null) {
			this.releaseFinancialAmount = new Money(0, 0);
		} else {
			this.releaseFinancialAmount = releaseFinancialAmount;
		}
	}

	public Money getReleaseAcceptanceBillAmount() {
		return releaseAcceptanceBillAmount;
	}
	
	public void setReleaseAcceptanceBillAmount(Money releaseAcceptanceBillAmount) {
		if (releaseAcceptanceBillAmount == null) {
			this.releaseAcceptanceBillAmount = new Money(0, 0);
		} else {
			this.releaseAcceptanceBillAmount = releaseAcceptanceBillAmount;
		}
	}

	public Money getReleaseCreditAmount() {
		return releaseCreditAmount;
	}
	
	public void setReleaseCreditAmount(Money releaseCreditAmount) {
		if (releaseCreditAmount == null) {
			this.releaseCreditAmount = new Money(0, 0);
		} else {
			this.releaseCreditAmount = releaseCreditAmount;
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

	public Money getInAmount() {
		return inAmount;
	}
	
	public void setInAmount(Money inAmount) {
		if (inAmount == null) {
			this.inAmount = new Money(0, 0);
		} else {
			this.inAmount = inAmount;
		}
	}

	public double getNewCustomer() {
		return newCustomer;
	}
	
	public void setNewCustomer(double newCustomer) {
		this.newCustomer = newCustomer;
	}

	public double getNewProject() {
		return newProject;
	}
	
	public void setNewProject(double newProject) {
		this.newProject = newProject;
	}

	public double getInCutomer() {
		return inCutomer;
	}
	
	public void setInCutomer(double inCutomer) {
		this.inCutomer = inCutomer;
	}

	public double getInProject() {
		return inProject;
	}
	
	public void setInProject(double inProject) {
		this.inProject = inProject;
	}

	public String getLatest() {
		return latest;
	}
	
	public void setLatest(String latest) {
		this.latest = latest;
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