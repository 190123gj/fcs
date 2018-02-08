package com.born.fcs.rm.biz.service.report.inner;

import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.pm.PmReportServiceClient;
import com.born.fcs.rm.ws.info.report.inner.MainIndexDataInfo;
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
 * 主要业务指标情况一览表 -出表一
 *
 * @author heh
 *
 * 2016年12月19日10:57:05
 */
@Service("mainIndexService")
public class MainIndexService extends BaseAutowiredDomainService implements
        ReportProcessService {

    @Autowired
    private PmReportServiceClient pmReportServiceClient;

    @Override
    public FcsBaseResult save(ReportOrder order) {
        return null;
    }

    @Override
    public Object findByAccountPeriod(ReportQueryOrder order) {
        QueryBaseBatchResult<MainIndexDataInfo> result = new QueryBaseBatchResult<MainIndexDataInfo>();
        List<MainIndexDataInfo> data = new ArrayList<MainIndexDataInfo>();
        try {
            String hasAllData="IS";
            //发生额
            occurAmount(order, data);
            //解保额
            releaseAmount(order, data);
            //担保在保余额
            balanceAmount(order, data,"D");
            //保费收入
            if(!premiumAmount(order, data)){
            hasAllData="NO";
            }
            //贷款余额
            balanceAmount(order, data,"W");
            //委贷本年
            incomeAmount(order, data);

            MainIndexDataInfo dept1 = null; //业务1部
            MainIndexDataInfo dept1hn = null; //湖南代表处
            MainIndexDataInfo dept2 = null; //业务2部
            MainIndexDataInfo dept2yn = null; //云南代表处

            int hnIndex = 0;
            int ynIndex = 0;
            int index = 0;
            for (MainIndexDataInfo info : data) {
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
//            //湖南代表处累加到一部，云南代表处
            for (MainIndexDataInfo info : data) {
                if (info.getDept().indexOf("一部") != -1 && dept1hn != null) {
                    info.getIncomeYear().addTo(dept1hn.getIncomeYear());
                    info.getBalanceEnding().addTo(dept1hn.getBalanceEnding());
                    info.getIncreaseYear().addTo(dept1hn.getIncreaseYear());
                    info.getOccurMonth().addTo(dept1hn.getOccurMonth());
                    info.getOccurYear().addTo(dept1hn.getOccurYear());
                    info.getReleaseMonth().addTo(dept1hn.getReleaseMonth());
                    info.getReleaseYear().addTo(dept1hn.getReleaseYear());
                    info.getEntrustedAmount().addTo(dept1hn.getEntrustedAmount());
                    info.getBalanceInitial().addTo(dept1hn.getBalanceInitial());
                    info.getPremiumYear().addTo(dept1hn.getPremiumYear());
                    info.getPremiumMonth().addTo(dept1hn.getPremiumMonth());
                } else if (info.getDept().indexOf("二部") != -1 && dept2yn != null) {
                    info.getIncomeYear().addTo(dept2yn.getIncomeYear());
                    info.getBalanceEnding().addTo(dept2yn.getBalanceEnding());
                    info.getIncreaseYear().addTo(dept2yn.getIncreaseYear());
                    info.getOccurMonth().addTo(dept2yn.getOccurMonth());
                    info.getOccurYear().addTo(dept2yn.getOccurYear());
                    info.getReleaseMonth().addTo(dept2yn.getReleaseMonth());
                    info.getReleaseYear().addTo(dept2yn.getReleaseYear());
                    info.getEntrustedAmount().addTo(dept2yn.getEntrustedAmount());
                    info.getBalanceInitial().addTo(dept2yn.getBalanceInitial());
                    info.getPremiumYear().addTo(dept2yn.getPremiumYear());
                    info.getPremiumMonth().addTo(dept2yn.getPremiumMonth());
                }
            }
            //只有湖南代表处新建一部
            if (dept1hn != null && dept1 == null) {
                dept1 = new MainIndexDataInfo();
                BeanCopier.staticCopy(dept1hn, dept1);
                dept1.setDept("业务发展一部");
                data.add(hnIndex, dept1);
            }
            //只有云南代表处新建二部
            if (dept2yn != null && dept2 == null) {
                dept2 = new MainIndexDataInfo();
                BeanCopier.staticCopy(dept2yn, dept2);
                dept2.setDept("业务发展二部");
                data.add(ynIndex, dept2);
            }

            //重新排序
            Collections.sort(data, new Comparator<MainIndexDataInfo>() {
                @Override
                public int compare(MainIndexDataInfo o1, MainIndexDataInfo o2) {
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
            result.setSortCol(hasAllData);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("主要业务指标情况一览表出错");
            logger.error("查询主要业务指标情况一览表出错：{}", e);
        }

        return result;
    }

    /**
     * 发生额
     * @param order
     * @param data
     */
    private void occurAmount(ReportQueryOrder order, List<MainIndexDataInfo> data) {

        String sql = "SELECT p.dept_code,p.dept_name,ifNULL(SUM(r.occur_amount),0) amount "
                + " FROM ${pmDbTitle}.view_project_actual_occer_detail r JOIN ${pmDbTitle}.project_data_info p ON r.project_code = p.project_code  where "
                + "  r.occur_date >= '${loanStartTime}' AND occur_date <= '${loanEndTime}' "
                + " AND (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%') GROUP BY p.dept_name "
                + " ORDER BY p.dept_code";
        sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);

        if (data == null)
            data = new ArrayList<MainIndexDataInfo>();

        String thisYearTotalSql = sql.replaceAll("\\$\\{loanStartTime\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{loanEndTime\\}",
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
                MainIndexDataInfo dataInfo = null;
                for (MainIndexDataInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new MainIndexDataInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //本年累计发生额
                dataInfo.setOccurYear(amount);
                thisYearTotal.addTo(amount);
            }
        }

        //本月sql
        sql = sql.replaceAll("\\$\\{loanStartTime\\}", order.getReportMonthStartDay()).replaceAll(
                "\\$\\{loanEndTime\\}", order.getReportMonthEndDay());

        //查询
        queyOrder.setSql(sql);
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money thisMonthTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money amount = toMoney(itm.getMap().get("amount"));
                MainIndexDataInfo dataInfo = null;
                for (MainIndexDataInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new MainIndexDataInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //本月发生额
                dataInfo.setOccurMonth(amount);
                thisMonthTotal.addTo(amount);
            }
        }

        MainIndexDataInfo total = null;
        for (MainIndexDataInfo info : data) {
            if (StringUtil.equals(info.getDept(), "合计")) {
                total = info;
                break;
            }
        }
        if (total == null) {
            total = new MainIndexDataInfo();
            total.setDept("合计");
            data.add(total);
        }

        total.setOccurYear(thisYearTotal);
        total.setOccurMonth(thisMonthTotal);
    }

    /**
     * 解保额
     * @param order
     * @param data
     */
    private void releaseAmount(ReportQueryOrder order, List<MainIndexDataInfo> data) {

        String sql = "SELECT dept_code,dept_name,SUM(re_amount) amount FROM  ${pmDbTitle}.project_data_info p join " +
                " ( SELECT project_code, SUM(repay_amount) re_amount FROM  ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_time >= '${releaseStartTime}'  AND repay_time <= '${releaseEndTime}' GROUP BY project_code\n) r on p.project_code=r.project_code" +
                " where (p.busi_type LIKE '1%' OR p.busi_type LIKE '2%' OR p.busi_type LIKE '3%')"+
                " GROUP BY p.dept_name ORDER BY dept_code" ;

        sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);

        if (data == null)
            data = new ArrayList<MainIndexDataInfo>();

        String thisYearTotalSql = sql.replaceAll("\\$\\{releaseStartTime\\}",
                order.getReportYearStartDay()).replaceAll("\\$\\{releaseEndTime\\}",
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
                MainIndexDataInfo dataInfo = null;
                for (MainIndexDataInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new MainIndexDataInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //本年累计解保额
                dataInfo.setReleaseYear(amount);
                thisYearTotal.addTo(amount);
            }
        }

        //本月sql
        sql = sql.replaceAll("\\$\\{releaseStartTime\\}", order.getReportMonthStartDay())
                .replaceAll("\\$\\{releaseEndTime\\}", order.getReportMonthEndDay());

        //查询
        queyOrder.setSql(sql);
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money thisMonthTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money amount = toMoney(itm.getMap().get("amount"));
                MainIndexDataInfo dataInfo = null;
                for (MainIndexDataInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new MainIndexDataInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //本月解保额
                dataInfo.setReleaseMonth(amount);
                thisMonthTotal.addTo(amount);
            }
        }

        MainIndexDataInfo total = null;
        for (MainIndexDataInfo info : data) {
            if (StringUtil.equals(info.getDept(), "合计")) {
                total = info;
                break;
            }
        }
        if (total == null) {
            total = new MainIndexDataInfo();
            total.setDept("合计");
            data.add(total);
        }
        total.setReleaseYear(thisYearTotal);
        total.setReleaseMonth(thisMonthTotal);
    }

    /**
     * 在保余额
     * @param order
     * @param data
     */
    private void balanceAmount(ReportQueryOrder order, List<MainIndexDataInfo> data,String busiType) {

        String sql = "SELECT  dept_code,dept_name,SUM(ba.balance) amount FROM ${pmDbTitle}.project_data_info p "
                + "	JOIN (SELECT occer.project_code, (oc_amount - IFNULL(re_amount, 0)) balance FROM (SELECT project_code, SUM(occur_amount) oc_amount FROM ${pmDbTitle}.view_project_actual_occer_detail WHERE occur_date <= '${reportMonthEndDay}' GROUP BY project_code) occer "
                + "LEFT JOIN (SELECT project_code, SUM(repay_amount) re_amount FROM ${pmDbTitle}.view_project_repay_detail WHERE repay_type IN ('解保', '诉保解保', '代偿') AND repay_time <= '${reportMonthEndDay}' GROUP BY project_code) repay ON repay.project_code = occer.project_code) ba ON p.project_code = ba.project_code WHERE  busiTypeStr GROUP BY dept_name ORDER BY dept_code";
        sql = sql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);

        //历史数据
        String hisSql = sql;
        hisSql = hisSql.replaceAll("\\$\\{pmDbTitle\\}", pmDbTitle);

        if (data == null)
            data = new ArrayList<MainIndexDataInfo>();

        //期初余额
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        if("D".equals(busiType)){
            sql=sql.replaceAll("busiTypeStr","(busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%')");
        }else{
            sql=sql.replaceAll("busiTypeStr","(busi_type LIKE '4%')");
        }
        if("D".equals(busiType)){
            hisSql=hisSql.replaceAll("busiTypeStr","(busi_type LIKE '1%' OR busi_type LIKE '2%' OR busi_type LIKE '3%')");
        }else{
            hisSql=hisSql.replaceAll("busiTypeStr","(busi_type LIKE '4%')");
        }
            //查询报告期第一天数据
            queyOrder.setSql(sql.replaceAll("\\$\\{reportMonthEndDay\\}",
                    order.getReportLastYearEndDay()));
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money beginTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money amount = toMoney(itm.getMap().get("amount"));
                MainIndexDataInfo dataInfo = null;
                for (MainIndexDataInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new MainIndexDataInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //期初余额
                if("D".equals(busiType)) {
                    dataInfo.setBalanceInitial(amount);
                }else{
                    dataInfo.setEntrustedAmount(amount);
                }
                beginTotal.addTo(amount);
            }
        }

        //期末余额
            queyOrder.setSql(hisSql.replaceAll("\\$\\{reportMonthEndDay\\}", order.getReportMonthEndDay()));
        dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money endTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money amount = toMoney(itm.getMap().get("amount"));
                MainIndexDataInfo dataInfo = null;
                for (MainIndexDataInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new MainIndexDataInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //期末在保余额
                if("D".equals(busiType)){
                    dataInfo.setBalanceEnding(amount);
                }else{
                    dataInfo.setEntrustedAmount(amount);
                }
                endTotal.addTo(amount);
            }
        }

        MainIndexDataInfo total = null;
        for (MainIndexDataInfo info : data) {
            if (StringUtil.equals(info.getDept(), "合计")) {
                total = info;
                break;
            }
        }
        if (total == null) {
            total = new MainIndexDataInfo();
            total.setDept("合计");
            data.add(total);
        }
        total.setBalanceInitial(beginTotal);
        if("D".equals(busiType)){
            total.setBalanceEnding(endTotal);
        }else{
            total.setEntrustedAmount(endTotal);
        }
    }


    /**
     * 保费
     * @param order
     * @param data
     */
    private boolean premiumAmount(ReportQueryOrder order, List<MainIndexDataInfo> data) {

        //来自数据报送：融资性担保机构基本情况
        String sql = "SELECT d.data2 dept_code,d.data3 dept_name,d.data4 premium_year,d.data5 premium_month"
                + " FROM ${rmDbTitle}.submission s JOIN ${rmDbTitle}.submission_data d ON s.submission_id = d.submission_id	"
                + " WHERE s.report_code = 'BFSRCWB'  AND (s.reporter_status = 'SUBMITTED' OR s.reporter_status = 'IN_USE')"
                + " AND s.report_year = '${reportYear}' AND s.report_month = '${reportMonth}' ORDER BY d.data2 ";

        sql = sql.replaceAll("\\$\\{rmDbTitle\\}", rmDbTitle).replaceAll("\\$\\{reportYear\\}",
                order.getReportYear() + "").replaceAll("\\$\\{reportMonth\\}",order.getReportMonth() + "");

        if (data == null)
            data = new ArrayList<MainIndexDataInfo>();

        //年度目标
        PmReportDOQueryOrder queyOrder = new PmReportDOQueryOrder();
        HashMap<String, FcsField> fieldMap = new HashMap<>();
        queyOrder.setFieldMap(fieldMap);
        queyOrder.setSql(sql);
        List<DataListItem> dataResult = pmReportServiceClient.doQuery(queyOrder);
        Money premiumYearTotal = Money.zero();
        Money premiumMonthTotal = Money.zero();
        Money incomeTotal = Money.zero();
        if (dataResult != null) {
            for (DataListItem itm : dataResult) {
                String deptName = String.valueOf(itm.getMap().get("dept_name"));
                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
                Money premiumYear = new Money(String.valueOf(itm.getMap().get("premium_year")));
                Money premiumMonth = new Money(String.valueOf(itm.getMap().get("premium_month")));
                MainIndexDataInfo dataInfo = null;
                for (MainIndexDataInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new MainIndexDataInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }

                dataInfo.setPremiumYear(premiumYear);
                dataInfo.setPremiumMonth(premiumMonth);

                premiumYearTotal.addTo(premiumYear);
                premiumMonthTotal.addTo(premiumMonth);
            }
        }else {
            return false;
        }

        MainIndexDataInfo total = null;
        for (MainIndexDataInfo info : data) {
            if (StringUtil.equals(info.getDept(), "合计")) {
                total = info;
                break;
            }
        }
        if (total == null) {
            total = new MainIndexDataInfo();
            total.setDept("合计");
            data.add(total);
        }
        total.setPremiumYear(premiumYearTotal);
        total.setPremiumMonth(premiumMonthTotal);
        return true;
    }


    /**
     * 委贷收入
     * @param order
     * @param data
     */
    private void incomeAmount(ReportQueryOrder order, List<MainIndexDataInfo> data) {

        String sql = "SELECT  dept_code,dept_name,SUM(income_confirmed_amount) amount FROM "
                + "(SELECT SUBSTRING(income_period, 1, 4) year,SUBSTRING(income_period, 6, 2) month,p.dept_code,p.dept_name,d.income_confirmed_amount "
                + "	FROM ${fmDbTitle}.income_confirm c JOIN ${fmDbTitle}.income_confirm_detail d  ON c.income_id = c.income_id AND d.confirm_status = 'IS_CONFIRM' "
                + "	JOIN ${pmDbTitle}.project p ON p.project_code = c.project_code where (p.busi_type like '4%')) t";

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
            data = new ArrayList<MainIndexDataInfo>();

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
                MainIndexDataInfo dataInfo = null;
                for (MainIndexDataInfo info : data) {
                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
                        dataInfo = info;
                        break;
                    }
                }
                if (dataInfo == null) {
                    dataInfo = new MainIndexDataInfo();
                    dataInfo.setDept(deptName);
                    dataInfo.setDeptCode(deptCode);
                    data.add(dataInfo);
                }
                //本年累计确认收入
                dataInfo.setIncomeYear(amount);
                thisYearTotal.addTo(amount);
            }
        }

//        //查询
//        queyOrder.setSql(thisMonthSql);
//        dataResult = pmReportServiceClient.doQuery(queyOrder);
//        Money thisMonthTotal = Money.zero();
//        if (dataResult != null) {
//            for (DataListItem itm : dataResult) {
//                String deptName = String.valueOf(itm.getMap().get("dept_name"));
//                String deptCode = String.valueOf(itm.getMap().get("dept_code"));
//                Money amount = toMoney(itm.getMap().get("amount"));
//                MainIndexDataInfo dataInfo = null;
//                for (MainIndexDataInfo info : data) {
//                    if (StringUtil.equals(info.getDeptCode(), deptCode)) {
//                        dataInfo = info;
//                        break;
//                    }
//                }
//                if (dataInfo == null) {
//                    dataInfo = new MainIndexDataInfo();
//                    dataInfo.setDept(deptName);
//                    dataInfo.setDeptCode(deptCode);
//                    data.add(dataInfo);
//                }
//                //本月确认收入
//                dataInfo.setIncomeMonth(amount);
//                thisMonthTotal.addTo(amount);
//            }
//        }

        MainIndexDataInfo total = null;
        for (MainIndexDataInfo info : data) {
            if (StringUtil.equals(info.getDept(), "合计")) {
                total = info;
                break;
            }
        }
        if (total == null) {
            total = new MainIndexDataInfo();
            total.setDept("合计");
            data.add(total);
        }
        total.setIncomeYear(thisYearTotal);
//        total.setIncomeMonth(thisMonthTotal);
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
}
