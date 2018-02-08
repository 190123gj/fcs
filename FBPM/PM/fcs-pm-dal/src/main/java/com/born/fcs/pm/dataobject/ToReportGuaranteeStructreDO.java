package com.born.fcs.pm.dataobject;

import com.yjf.common.lang.util.money.Money;

import java.io.Serializable;

/**
 * 报表系统--担保业务结构汇总表
 *
 * @author heh
 */
public class ToReportGuaranteeStructreDO implements Serializable {


    /** 担保合同金额 **/
    private Money contractAmount=new Money(0, 0);
    /** 本年累计发生额 当年1月1日起至报告期月度最末一天止累计放款金额 **/
    private Money actualAmount=new Money(0, 0);
    /** 本年累计解保额 融资担保业务取报告期当年1月1日起至报告期月度最末一天止解保申请单审核通过的解保金额合计 **/
    private Money releasedAmount=new Money(0, 0);
    /** 期末在保余额 当前部门当前业务类型报告期期末最后一天的累计授信金额-累计解保金额 **/
    private Money releasingAmount=new Money(0, 0);

    private long deptId;

    private String deptCode;

    private String deptName;

    private String remark;

    public Money getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Money contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Money getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Money actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Money getReleasedAmount() {
        return releasedAmount;
    }

    public void setReleasedAmount(Money releasedAmount) {
        this.releasedAmount = releasedAmount;
    }

    public Money getReleasingAmount() {
        return releasingAmount;
    }

    public void setReleasingAmount(Money releasingAmount) {
        this.releasingAmount = releasingAmount;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
