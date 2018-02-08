package com.born.fcs.pm.ws.order.contract;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 *
 * 授信前管理-合同管理-合同作废
 *
 * @author heh
 *
 */
public class ProjectContractItemInvalidOrder extends FormOrderBase {

    private static final long serialVersionUID = -189269373109462477L;
    private long id;

    private String projectCode;

    private String projectName;

    private long customerId;

    private String customerName;

    private String contractCode;

    private String contractName;

    private String invalidReason;

    private String withdrawAll;

    private String remark;

    private long cnt;

    public long getCnt() {
        return cnt;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public String getWithdrawAll() {
        return withdrawAll;
    }

    public void setWithdrawAll(String withdrawAll) {
        this.withdrawAll = withdrawAll;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
