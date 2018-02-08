package com.born.fcs.pm.ws.service.report;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.report.ToReportInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryListInfo;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.recovery.ProjectRecoveryResult;

import java.util.Date;
import java.util.List;

/**
 *
 * 报表系统抓取项目管理的数据
 * @author heh
 *
 */
@WebService
public interface ToReportService {

    /**
     *
     * 融资担保业务  在保余额合计(已放款金额-已解保金额)
     *
     * @return
     */
    String getReleasingAmount(int year, int month);

    /**
     *
     * 预计解保情况汇总表、预计收入汇总表（根据还款计划的数据，取查询月需要还款的金额
     * 统计范围为：在保的担保、委贷类项目）
     *
     * @param deptCode 部门编号
     * @param type 收费、还款 CHARGE_PLAN、REPAY_PLAN
     * @return
     */
    List<String> getRepayOrChargeAmount(String deptCode, String type, int year,int month);

    /**
     * 预计发生情况汇总表
     * 根据批复中的授信金额 已批未放的担保、委贷类项目  全签(所有合同都已回传) 未签(有没有回传合同的项目)
     * @param deptCodes 部门编号s
     * @return
     */
    List<ToReportInfo> getExpectEvent(String deptCodes, int year, int month);

    /**
     * 项目推动情况汇总表
     * @return
     */
    List<ToReportInfo> getProjectProcess(int year, int month);

    /**
     * 担保业务结构汇总表
     * @return
     */
    List<ToReportInfo> getReportGuaranteeStructre(int year, int month);

    /**
     *
     * 担保业务结构汇总表  年初在保余额
     *
     * @return
     */
    List<ToReportInfo> getReportGuaranteeStructreBalanceAmount(int year, int month);


    /**
     *
     * 担保业务结构汇总表  合同金额
     *
     * @return
     */
    List<ToReportInfo> getReportGuaranteeStructreContractAmount(int year, int month);

}
