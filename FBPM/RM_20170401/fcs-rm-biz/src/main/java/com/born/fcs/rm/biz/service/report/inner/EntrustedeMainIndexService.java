package com.born.fcs.rm.biz.service.report.inner;

import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.info.report.inner.EntrustedeMainIndexInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * 内部报表<br />
 * 综合汇总表<br />
 * 委贷业务主要指标汇总表
 *
 * @author heh
 *
 * 2016年12月22日14:25:54
 */
@Service("entrustedeMainIndexService")
public class EntrustedeMainIndexService extends BaseAutowiredDomainService implements
        ReportProcessService {

    @Autowired
    private PmReportServiceClient pmReportServiceClient;

    @Override
    public FcsBaseResult save(ReportOrder order) {
        return null;
    }

    @Override
    public Object findByAccountPeriod(ReportQueryOrder order) {
        QueryBaseBatchResult<EntrustedeMainIndexInfo> result = new QueryBaseBatchResult<EntrustedeMainIndexInfo>();
        List<EntrustedeMainIndexInfo> data = new ArrayList<EntrustedeMainIndexInfo>();
        try {
            //期初余额
            balanceAmount(order, data);
            //发生额
            occurAmount(order, data);
            //还款金额
            paymentsAmount(order, data);
            //收入
            incomeAmount(order, data);
            //年度目标
            yearGoal(order, data);

            EntrustedeMainIndexInfo dept1 = null; //业务1部
            EntrustedeMainIndexInfo dept1hn = null; //湖南代表处
            EntrustedeMainIndexInfo dept2 = null; //业务2部
            EntrustedeMainIndexInfo dept2yn = null; //云南代表处

            int hnIndex = 0;
            int ynIndex = 0;
            int index = 0;
            for (EntrustedeMainIndexInfo info : data) {
                if (info.getDept().indexOf("湖南") != -1) {
                    info.setDept("其中：" + info.getDept());
                    dept1hn = info;
                    hnIndex = index;
                } else if (info.getDept().indexOf("云南") != -1) {
                    info.setDept("其中：" + info.getDept());
                    dept2yn = info;
                    ynIndex = index;
                } else if (info.getDept().indexOf("一部") != -1) {
                    dept1 = info;
                } else if (info.getDept().indexOf("二部") != -1) {
                    dept2 = info;
                }
                index++;
            }
            boolean reportAll = true;
            //湖南代表处累加到一部，云南代表处
            for (EntrustedeMainIndexInfo info : data) {
                if (info.getIncomeGoal().getCent() == 0 )
                    reportAll = false;
                if (info.getDept().indexOf("一部") != -1 && dept1hn != null) {
                    info.getBalanceInitial().addTo(dept1hn.getBalanceInitial());
                    info.getBalanceInitialYear().addTo(dept1hn.getBalanceInitialYear());
                    info.getIncomeGoal().addTo(dept1hn.getIncomeGoal());
                    info.getIncomeMonth().addTo(dept1hn.getIncomeMonth());
                    info.getIncomeYear().addTo(dept1hn.getIncomeYear());
                    info.getOccurMonth().addTo(dept1hn.getOccurMonth());
                    info.getPaymentsMonth().addTo(dept1hn.getPaymentsMonth());
                    info.getPaymentsYear().addTo(dept1hn.getPaymentsYear());
                    info.getOccurYear().addTo(dept1hn.getOccurYear());
                } else if (info.getDept().indexOf("二部") != -1 && dept2yn != null) {
                    info.getBalanceInitial().addTo(dept2yn.getBalanceInitial());
                    info.getBalanceInitialYear().addTo(dept2yn.getBalanceInitialYear());
                    info.getIncomeGoal().addTo(dept2yn.getIncomeGoal());
                    info.getIncomeMonth().addTo(dept2yn.getIncomeMonth());
                    info.getIncomeYear().addTo(dept2yn.getIncomeYear());
                    info.getOccurMonth().addTo(dept2yn.getOccurMonth());
                    info.getPaymentsMonth().addTo(dept2yn.getPaymentsMonth());
                    info.getPaymentsYear().addTo(dept2yn.getPaymentsYear());
                    info.getOccurYear().addTo(dept2yn.getOccurYear());
                }

            }
            //只有湖南代表处新建一部
            if (dept1hn != null && dept1 == null) {
                dept1 = new EntrustedeMainIndexInfo();
                BeanCopier.staticCopy(dept1hn, dept1);
                dept1.setDept("业务发展一部");
                data.add(hnIndex, dept1);
            }
            //只有云南代表处新建二部
            if (dept2yn != null && dept2 == null) {
                dept2 = new EntrustedeMainIndexInfo();
                BeanCopier.staticCopy(dept2yn, dept2);
                dept2.setDept("业务发展二部");
                data.add(ynIndex, dept2);
            }

            //重新排序
            Collections.sort(data, new Comparator<EntrustedeMainIndexInfo>() {
                @Override
                public int compare(EntrustedeMainIndexInfo o1, EntrustedeMainIndexInfo o2) {
                    if (o1.getDeptCode() == null)
                        return 1;
                    if (o2.getDeptCode() == null)
                        return -1;
                    return o1.getDeptCode().compareToIgnoreCase(o2.getDeptCode());
                }
            });

            result.setSuccess(true);
            result.setPageList(data);
            result.setTotalCount(data.size());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("担保业务主要指标汇总表出错");
            logger.error("查询担保业务主要指标汇总表出错：{}", e);
        }

        return result;
    }

    /**
     * 发生额
     * @param order
     * @param data
     */
    private void occurAmount(ReportQueryOrder order, List<EntrustedeMainIndexInfo> data) {

        String sql = "SELECT p.dept_code,p.dept_name,SUM(r.actual_amount) amount "
                + "FROM ${pmDbTitle}.f_loan_use_apply_receipt r JOIN ${pmDbTitle}.project p ON r.project_code = p.project_code "
                + "WHERE p.busi_type like '4%' AND r.apply_type IN ('BOTH', 'LOAN') "
                + "AND r.actual_loan_time >= '${loanStartTime}' AND r.actual_loan_time <= '${loanEndTime}' GROUP BY p.dept_name ORDER BY p.dept_code";
        sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);

        if (data == null)
            data = new ArrayList<EntrustedeMainIndexInfo>();

        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        //本年
        String thisYearTotalSql=sql;
        //本月sql
        sql = sql.replaceAll("\\$\\{loanStartTime\\}", order.getReportMonthStartDay()).replaceAll(
                "\\$\\{loanEndTime\\}", order.getReportMonthEndDay());
//今天减一天，不是今天月末最后一天
        //查询
        queyOrder.setSql(sql);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money thisMonthTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money amount = toMoney(itm.getMap().get("amount"));
                EntrustedeMainIndexInfo dataInfo = null;
                for (EntrustedeMainIndexInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new EntrustedeMainIndexInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //本月发生额
                dataInfo.setOccurMonth(amount);
                thisMonthTotal.addTo(amount);
            }
        }
        //本年累计
         thisYearTotalSql = thisYearTotalSql.replaceAll("\\$\\{loanStartTime\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
                order.getReportMonthEndDay());
        queyOrder = new PmReportDOQueryOrder();
        queyOrder.setSql(thisYearTotalSql);
        queyOrder.setFieldMap(fieldMap);
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money thisYearTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money amount = toMoney(itm.getMap().get("amount"));
                EntrustedeMainIndexInfo dataInfo = null;
                for (EntrustedeMainIndexInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new EntrustedeMainIndexInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //本年累计发生额
                dataInfo.setOccurYear(amount);
                thisYearTotal.addTo(amount);
            }
        }
        EntrustedeMainIndexInfo total = null;
        for (EntrustedeMainIndexInfo info : data) {
            if (StringUtil.equals(info.getDept(), "合计")) {
                total = info;
                break;
            }
        }
        if (total == null) {
            total = new EntrustedeMainIndexInfo();
            total.setDept("合计");
            data.add(total);
        }
        total.setOccurMonth(thisMonthTotal);
        total.setOccurYear(thisYearTotal);
    }

    /**
     * 还款额
     * @param order
     * @param data
     */
    private void paymentsAmount(ReportQueryOrder order, List<EntrustedeMainIndexInfo> data) {

        String sql = "  SELECT p.dept_code,p.dept_name,SUM(d.pay_amount)  amount\n" +
                "  FROM  ${pmDbTitle}.form f JOIN ${pmDbTitle}.f_finance_affirm ffa ON f.form_id = ffa.form_id \n" +
                "      JOIN ${pmDbTitle}.project p ON ffa.project_code=p.project_code\n" +
                "    JOIN ${pmDbTitle}.f_finance_affirm_detail d  ON ffa.affirm_id = d.affirm_id \n" +
                "  WHERE EXISTS \n" +
                "    (SELECT  f1.form_id  FROM ${pmDbTitle}.form f1 \n" +
                "      JOIN ${pmDbTitle}.f_finance_affirm ffa1  ON f1.form_id = ffa1.form_id \n" +
                "      JOIN ${pmDbTitle}.f_finance_affirm_detail d1  ON ffa1.affirm_id = d1.affirm_id \n" +
                "    WHERE ffa1.affirm_form_type = 'CHARGE_NOTIFICATION' \n" +
                "      AND d1.fee_type = 'ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL' \n" +
                "      AND f1.status = 'APPROVAL' \n" +
                "      AND f.form_id=f1.form_id AND f.finish_time >= '${finishStartTime}' AND f.finish_time <= '${finishEndTime}') AND p.dept_id>0 GROUP BY p.dept_code  ORDER BY f.dept_code";
        sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);

        if (data == null)
            data = new ArrayList<EntrustedeMainIndexInfo>();

        String thisYearTotalSql = sql.replaceAll("\\$\\{finishStartTime\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{finishEndTime\\}",
                order.getReportMonthEndDay());

        //本年累计
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(thisYearTotalSql);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money thisYearTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money amount = toMoney(itm.getMap().get("amount"));
                EntrustedeMainIndexInfo dataInfo = null;
                for (EntrustedeMainIndexInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new EntrustedeMainIndexInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //本年累计还款金额
                dataInfo.setPaymentsYear(amount);
                thisYearTotal.addTo(amount);
            }
        }

        //本月sql
        sql = sql.replaceAll("\\$\\{finishStartTime\\}", order.getReportMonthStartDay())
                .replaceAll("\\$\\{finishEndTime\\}", order.getReportMonthEndDay());

        //查询
        queyOrder.setSql(sql);
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money thisMonthTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money amount = toMoney(itm.getMap().get("amount"));
                EntrustedeMainIndexInfo dataInfo = null;
                for (EntrustedeMainIndexInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new EntrustedeMainIndexInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //本月还款金额
                dataInfo.setPaymentsMonth(amount);
                thisMonthTotal.addTo(amount);
            }
        }

        EntrustedeMainIndexInfo total = null;
        for (EntrustedeMainIndexInfo info : data) {
            if (StringUtil.equals(info.getDept(), "合计")) {
                total = info;
                break;
            }
        }
        if (total == null) {
            total = new EntrustedeMainIndexInfo();
            total.setDept("合计");
            data.add(total);
        }
        total.setPaymentsYear(thisYearTotal);
        total.setPaymentsMonth(thisMonthTotal);
    }

    /**
     * 期初在保余额
     * @param order
     * @param data
     */
    private void balanceAmount(ReportQueryOrder order, List<EntrustedeMainIndexInfo> data) {
        String sql = "SELECT  dept_code,dept_name,SUM(ba.balance) amount FROM ${pmDbTitle}.project_data_info p "
                + "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_actual_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
                + "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE  busi_type like '4%'AND dept_id > 0  GROUP BY dept_name ORDER BY dept_code";
        sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);

        String hisSql=sql;
        if (data == null)
            data = new ArrayList<EntrustedeMainIndexInfo>();
        //上月期初余额
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
            //查询报告期第一天数据
            queyOrder.setSql(hisSql.replaceAll("\\$\\{reportMonthEndDay\\}",
                    order.getReportLastMonthEndDay()));

        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money beginTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money amount = toMoney(itm.getMap().get("amount"));
                EntrustedeMainIndexInfo dataInfo = null;
                for (EntrustedeMainIndexInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new EntrustedeMainIndexInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //期初余额
                dataInfo.setBalanceInitial(amount);
                beginTotal.addTo(amount);
            }
        }

        //查询报告期年初第一天数据
        queyOrder.setSql(hisSql.replaceAll("\\$\\{reportMonthEndDay\\}",
                order.getReportLastYearEndDay()));

        dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money beginTotalYear = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money amount = toMoney(itm.getMap().get("amount"));
                EntrustedeMainIndexInfo dataInfo = null;
                for (EntrustedeMainIndexInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new EntrustedeMainIndexInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //年初期初余额
                dataInfo.setBalanceInitialYear(amount);
                beginTotalYear.addTo(amount);
            }
        }

        EntrustedeMainIndexInfo total = null;
        for (EntrustedeMainIndexInfo info : data) {
            if (StringUtil.equals(info.getDept(), "合计")) {
                total = info;
                break;
            }
        }
        if (total == null) {
            total = new EntrustedeMainIndexInfo();
            total.setDept("合计");
            data.add(total);
        }
        total.setBalanceInitial(beginTotal);
        total.setBalanceInitialYear(beginTotalYear);
    }

    /**
     * 年度目标
     * @param order
     * @param data
     */
    private void yearGoal(ReportQueryOrder order, List<EntrustedeMainIndexInfo> data) {

        String sql = "SELECT d.data2 dept_code,d.data3 dept_name,d.data4 increase_amount,d.data5 balance_amount,d.data6 income_amount "
                + "FROM ${rmDbTitle}.submission s JOIN ${rmDbTitle}.submission_data d ON s.submission_id = d.submission_id	"
                + "WHERE s.report_code = 'ANNUAL_OBJECTIVE' AND d.data1 = '委贷业务' AND s.reporter_status IN ('IN_USE','SUBMITTED') "
                + "AND s.report_year = '${reportYear}' ORDER BY d.data2 ";

        sql = sql.replaceAll("\\$\\{rmDbTitle\\}", rmDbTitle).replaceAll("\\$\\{reportYear\\}",
                order.getReportYear() + "");

        if (data == null)
            data = new ArrayList<EntrustedeMainIndexInfo>();

        //年度目标
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(sql);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money incomeTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money incomeAmount = new Money(String.valueOf(itm.getMap().get("income_amount")));
                EntrustedeMainIndexInfo dataInfo = null;
                for (EntrustedeMainIndexInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new EntrustedeMainIndexInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }


                dataInfo.setIncomeGoal(incomeAmount);

                incomeTotal.addTo(incomeAmount);
            }
        }

        EntrustedeMainIndexInfo total = null;
        for (EntrustedeMainIndexInfo info : data) {
            if (StringUtil.equals(info.getDept(), "合计")) {
                total = info;
                break;
            }
        }
        if (total == null) {
            total = new EntrustedeMainIndexInfo();
            total.setDept("合计");
            data.add(total);
        }
        total.setIncomeGoal(incomeTotal);
    }

    /**
     * 收入
     * @param order
     * @param data
     */
    private void incomeAmount(ReportQueryOrder order, List<EntrustedeMainIndexInfo> data) {

        String sql = "SELECT  dept_code,dept_name,SUM(income_confirmed_amount) amount FROM "
                + "(SELECT SUBSTRING(income_period, 1, 4) year,SUBSTRING(income_period, 6, 2) month,p.dept_code,p.dept_name,d.income_confirmed_amount "
                + "	FROM ${fmDbTitle}.income_confirm c JOIN ${fmDbTitle}.income_confirm_detail d  ON c.income_id = d.income_id AND d.confirm_status = 'IS_CONFIRM' "
                + "	JOIN ${pmDbTitle}.project p ON p.project_code = c.project_code WHERE p.busi_type like '4%') t";

        sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle).replaceAll("\\$\\{fmDbTitle\\}",
                fmDbTitle);
        //本年累计sql
        String thisYearSql = sql
                + " WHERE year = '${reportYear}' AND month <= '${reportMonth}' GROUP BY dept_name ORDER BY t.dept_code";
        thisYearSql = thisYearSql.replaceAll("\\$\\{reportYear\\}", order.getReportYear() + "").replaceAll("\\$\\{reportMonth\\}",
                (order.getReportMonth() < 10 ? "0" : "" + order.getReportMonth()));;
        //本月sql
        String thisMonthSql = sql
                + " WHERE year = '${reportYear}' AND month = '${reportMonth}'  GROUP BY dept_name ORDER BY t.dept_code";
        thisMonthSql = thisMonthSql.replaceAll("\\$\\{reportYear\\}", order.getReportYear() + "")
                .replaceAll("\\$\\{reportMonth\\}",
                        (order.getReportMonth() < 10 ? "0" : "" + order.getReportMonth()));

        if (data == null)
            data = new ArrayList<EntrustedeMainIndexInfo>();

        //本年累计
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(thisYearSql);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money thisYearTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money amount = toMoney(itm.getMap().get("amount"));
                EntrustedeMainIndexInfo dataInfo = null;
                for (EntrustedeMainIndexInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new EntrustedeMainIndexInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //本年累计确认收入
                dataInfo.setIncomeYear(amount);
                thisYearTotal.addTo(amount);
            }
        }

        //查询
        queyOrder.setSql(thisMonthSql);
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money thisMonthTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money amount = toMoney(itm.getMap().get("amount"));
                EntrustedeMainIndexInfo dataInfo = null;
                for (EntrustedeMainIndexInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new EntrustedeMainIndexInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //本月确认收入
                dataInfo.setIncomeMonth(amount);
                thisMonthTotal.addTo(amount);
            }
        }

        EntrustedeMainIndexInfo total = null;
        for (EntrustedeMainIndexInfo info : data) {
            if (StringUtil.equals(info.getDept(), "合计")) {
                total = info;
                break;
            }
        }
        if (total == null) {
            total = new EntrustedeMainIndexInfo();
            total.setDept("合计");
            data.add(total);
        }
        total.setIncomeYear(thisYearTotal);
        total.setIncomeMonth(thisMonthTotal);
    }


    //转化Money
    private Money toMoney(Object fen) {
        try {
            String s = String.valueOf(fen);
            return Money.amout(s).divide(100);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Money.zero();
    }

    //转化Money
    private Money toMoneyW(Object wan) {
        try {
            String s = String.valueOf(wan);
            return Money.amout(s).multiply(10000);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Money.zero();
    }

    /**
     * 查询当月第一天
     * @return
     */
    public String getReportMonthStartDay(int reportYear,int reportMonth) {
        return getReportYearMonth(reportYear,reportMonth) + "-01";
    }

    public String getReportYearMonth(int reportYear,int reportMonth) {
        return reportYear + (reportMonth < 10 ? "-0" : "-") + reportMonth;
    }
}
