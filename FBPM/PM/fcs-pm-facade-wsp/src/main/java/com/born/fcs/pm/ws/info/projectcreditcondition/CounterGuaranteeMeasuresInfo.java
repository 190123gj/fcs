package com.born.fcs.pm.ws.info.projectcreditcondition;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 反担保措施表
 * @author heh
 */
public class CounterGuaranteeMeasuresInfo extends BaseToStringInfo {

    private static final long serialVersionUID = 5360897173689863463L;

    private String deptName;

    private String busiManagerName;

    private String projectName;

    private String customerName;

    private String busiTypeName;

    private String type;

    private String itemDesc;

    private Money evaluationPrice;

    private Money clearingAmount;

    private String ownershipName;


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getBusiManagerName() {
        return busiManagerName;
    }

    public void setBusiManagerName(String busiManagerName) {
        this.busiManagerName = busiManagerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBusiTypeName() {
        return busiTypeName;
    }

    public void setBusiTypeName(String busiTypeName) {
        this.busiTypeName = busiTypeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Money getEvaluationPrice() {
        return evaluationPrice;
    }

    public void setEvaluationPrice(Money evaluationPrice) {
        this.evaluationPrice = evaluationPrice;
    }

    public Money getClearingAmount() {
        return clearingAmount;
    }

    public void setClearingAmount(Money clearingAmount) {
        this.clearingAmount = clearingAmount;
    }

    public String getOwnershipName() {
        return ownershipName;
    }

    public void setOwnershipName(String ownershipName) {
        this.ownershipName = ownershipName;
    }
}