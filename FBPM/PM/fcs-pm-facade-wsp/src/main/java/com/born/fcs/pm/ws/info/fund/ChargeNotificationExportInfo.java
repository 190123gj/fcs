package com.born.fcs.pm.ws.info.fund;


import com.born.fcs.pm.ws.enums.ChargeBasisEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

import java.util.Date;

/**
 * 收费通知单导出
 *
 * @author heh
 *
 */
public class ChargeNotificationExportInfo extends BaseToStringInfo {
    private static final long serialVersionUID = 5949299002984354656L;

    private String projectCode;

    private String projectName;

    private String customerName;

    private String busiType;

    private String busiTypeName;

    private ChargeBasisEnum chargeBasis;

    private int timeLimit;

    private String timeUnit;

    private Date startTime;

    private Date endTime;

    private Date payTime;

    private Money amount = new Money(0, 0);

    private Money loanedAmount = new Money(0, 0);

    private Money releasedAmount = new Money(0, 0);

    private Money releasableAmount = new Money(0, 0);

    private Money compPrincipalAmount = new Money(0, 0);

    private Money contractAmount = new Money(0, 0);

    private Money chargeBase = new Money(0, 0);

    private Money chargeAmount = new Money(0, 0);

    private double chargeRate;

    private String chargeName;

    private double chargeFee;

    private String institutionName;

    private String contractCode;

    private String contractName;

    private String feeTypeDesc;

    private ChargeTypeEnum chargeType;

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public ChargeBasisEnum getChargeBasis() {
        return chargeBasis;
    }

    public void setChargeBasis(ChargeBasisEnum chargeBasis) {
        this.chargeBasis = chargeBasis;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
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

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Money getLoanedAmount() {
        return loanedAmount;
    }

    public void setLoanedAmount(Money loanedAmount) {
        this.loanedAmount = loanedAmount;
    }

    public Money getReleasedAmount() {
        return releasedAmount;
    }

    public void setReleasedAmount(Money releasedAmount) {
        this.releasedAmount = releasedAmount;
    }

    public Money getReleasableAmount() {
        return releasableAmount;
    }

    public void setReleasableAmount(Money releasableAmount) {
        this.releasableAmount = releasableAmount;
    }

    public Money getCompPrincipalAmount() {
        return compPrincipalAmount;
    }

    public void setCompPrincipalAmount(Money compPrincipalAmount) {
        this.compPrincipalAmount = compPrincipalAmount;
    }

    public Money getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Money contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Money getChargeBase() {
        return chargeBase;
    }

    public void setChargeBase(Money chargeBase) {
        this.chargeBase = chargeBase;
    }

    public Money getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Money chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public double getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(double chargeRate) {
        this.chargeRate = chargeRate;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public double getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(double chargeFee) {
        this.chargeFee = chargeFee;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getFeeTypeDesc() {
        return feeTypeDesc;
    }

    public void setFeeTypeDesc(String feeTypeDesc) {
        this.feeTypeDesc = feeTypeDesc;
    }

    public ChargeTypeEnum getChargeType() {
        return chargeType;
    }

    public void setChargeType(ChargeTypeEnum chargeType) {
        this.chargeType = chargeType;
    }
}
