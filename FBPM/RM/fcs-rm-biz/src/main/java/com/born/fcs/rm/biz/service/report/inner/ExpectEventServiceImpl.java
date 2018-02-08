package com.born.fcs.rm.biz.service.report.inner;

import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.report.ToReportInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.SysParameterServiceClient;
import com.born.fcs.rm.integration.service.pm.ToReportServiceClient;
import com.born.fcs.rm.ws.info.report.inner.ExpectReleaseInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 内部报表<br />
 * 综合汇总表<br />
 * 预计发生情况汇总表
 *
 * @author heh
 *
 * 2016年8月27日 15:41:33
 */
@Service("expectEventService")
public class ExpectEventServiceImpl extends BaseAutowiredDomainService implements
        ReportProcessService {

    @Autowired
    ToReportServiceClient toReportServiceClient;

    @Autowired
    SysParameterServiceClient sysParameterServiceClient;

    @Autowired
    SubmissionService submissionService;

    @Override
    public FcsBaseResult save(ReportOrder order) {
        return null;
    }

    @Override
    public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
        QueryBaseBatchResult<ExpectReleaseInfo> batchResult = new QueryBaseBatchResult<>();
        //获取上报部门
        String deptCodes = sysParameterServiceClient.getSysParameterValue(
                SysParamEnum.SYS_PARAM_ANNUAL_OBJECTIVE_DEPT_CODE.code());
        int year = queryOrder.getReportYear();
        int month = queryOrder.getReportMonth();
        //统计数据
        Map<String,ToReportInfo> map=new HashMap<>();
            for (int i = 1; i <= month; i++) {
                List<ToReportInfo> toReportInfos = toReportServiceClient.getExpectEvent(deptCodes,year, i);
                if(toReportInfos!=null&&toReportInfos.size()>0){
                    for(ToReportInfo toReportInfo:toReportInfos){
                        map.put(toReportInfo.getData1()+"_"+i+"_"+toReportInfo.getData3()+"_"+toReportInfo.getData4(),toReportInfo);
                    }
                }
            }
        //格式化数据
        List<ExpectReleaseInfo> infos = new ArrayList<>();
        List<ExpectReleaseInfo> infosW = new ArrayList<>();
        String codes[]=deptCodes.split(",");
        for(String code:codes){
            String result = submissionService.findDeptInfoByDeptCode(code);
            HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
            Map<String, Object> sysOrgMap = (Map<String, Object>) resultMap.get("sysOrg");
            ExpectReleaseInfo infoD = new ExpectReleaseInfo();//担保
            ExpectReleaseInfo infoW = new ExpectReleaseInfo();//委贷
            infoD.setDept(String.valueOf(sysOrgMap.get("orgName")));
            infoW.setDept(String.valueOf(sysOrgMap.get("orgName")));
            for (int i = 1; i <= month; i++) {
                ToReportInfo reportInfoAllD=map.get(code+"_"+i+"_D_ALL_SIGN");
                ToReportInfo reportInfoNotAllD=map.get(code+"_"+i+"_D_NOT_ALL_SIGN");
                ToReportInfo reportInfoAllW=map.get(code+"_"+i+"_W_ALL_SIGN");
                ToReportInfo reportInfoNotAllW=map.get(code+"_"+i+"_W_NOT_ALL_SIGN");
                if(reportInfoAllD!=null){
                    infoD = toExpectRelaseInfo(infoD,reportInfoAllD,i,"ALL");
                }
                if(reportInfoNotAllD!=null){
                    infoD = toExpectRelaseInfo(infoD,reportInfoNotAllD,i,"NOTALL");
                }
                if(reportInfoAllW!=null){
                    infoW = toExpectRelaseInfo(infoW,reportInfoAllW,i,"ALL");
                }
                if(reportInfoNotAllW!=null){
                    infoW = toExpectRelaseInfo(infoW,reportInfoNotAllW,i,"NOTALL");
                }
            }
            infoD.setBusiType("担保");
            infos.add(infoD);
            infoW.setBusiType("委贷");
            infosW.add(infoW);
        }

        //合计
        infos.add(getTotalInfo(infos,"担保"));
        if (infosW.size() > 0) {
            for (ExpectReleaseInfo itemInfo : infosW) {
                infos.add(itemInfo);
            }
            infos.add(getTotalInfo(infosW,"委贷"));
        }
        //没有数据数据的业务部门不显示
        List<ExpectReleaseInfo> returnInfos = new ArrayList<>();
        if(ListUtil.isNotEmpty(infos)){
            for(ExpectReleaseInfo info:infos){
                if(!isEmpty(info)){
                    returnInfos.add(info);
                }
            }
        }
        batchResult.setPageList(returnInfos);
        batchResult.setSuccess(true);
        return batchResult;
    }

    public boolean isEmpty(ExpectReleaseInfo info) {
        return 	  "0.00".equals(info.getData1() )
                &&"0.00".equals(info.getData2() )
                &&"0.00".equals(info.getData3() )
                &&"0.00".equals(info.getData4() )
                &&"0.00".equals(info.getData5() )
                &&"0.00".equals(info.getData6() )
                &&"0.00".equals(info.getData7() )
                &&"0.00".equals(info.getData8() )
                &&"0.00".equals(info.getData9() )
                &&"0.00".equals(info.getData10())
                &&"0.00".equals(info.getData11())
                &&"0.00".equals(info.getData12())
                &&"0.00".equals(info.getData13())
                &&"0.00".equals(info.getData14())
                &&"0.00".equals(info.getData15())
                &&"0.00".equals(info.getData16())
                &&"0.00".equals(info.getData17())
                &&"0.00".equals(info.getData18())
                &&"0.00".equals(info.getData19())
                &&"0.00".equals(info.getData20())
                &&"0.00".equals(info.getData21())
                &&"0.00".equals(info.getData22())
                &&"0.00".equals(info.getData23())
                &&"0.00".equals(info.getData24())
                ;
    }

    private ExpectReleaseInfo toExpectRelaseInfo(ExpectReleaseInfo info,ToReportInfo reportInfo, int month,String type) {

        switch (month) {
            case 1:
                if(type.equals("ALL")){
                    info.setData1(reportInfo.getData5());
                }else{
                    info.setData2(reportInfo.getData5());
                }
                break;
            case 2:
                if(type.equals("ALL")){
                    info.setData3(reportInfo.getData5());
                }else{
                    info.setData4(reportInfo.getData5());
                }
                break;
            case 3:
                if(type.equals("ALL")){
                    info.setData5(reportInfo.getData5());
                }else{
                    info.setData6(reportInfo.getData5());
                }
                break;
            case 4:
                if(type.equals("ALL")){
                    info.setData7(reportInfo.getData5());
                }else{
                    info.setData8(reportInfo.getData5());
                }
                break;
            case 5:
                if(type.equals("ALL")){
                    info.setData9(reportInfo.getData5());
                }else{
                    info.setData10(reportInfo.getData5());
                }
                break;
            case 6:
                if(type.equals("ALL")){
                    info.setData11(reportInfo.getData5());
                }else{
                    info.setData12(reportInfo.getData5());
                }
                break;
            case 7:
                if(type.equals("ALL")){
                    info.setData13(reportInfo.getData5());
                }else{
                    info.setData14(reportInfo.getData5());
                }
                break;
            case 8:
                if(type.equals("ALL")){
                    info.setData15(reportInfo.getData5());
                }else{
                    info.setData16(reportInfo.getData5());
                }
                break;
            case 9:
                if(type.equals("ALL")){
                    info.setData17(reportInfo.getData5());
                }else{
                    info.setData18(reportInfo.getData5());
                }
                break;
            case 10:
                if(type.equals("ALL")){
                    info.setData19(reportInfo.getData5());
                }else{
                    info.setData20(reportInfo.getData5());
                }
                break;
            case 11:
                if(type.equals("ALL")){
                    info.setData21(reportInfo.getData5());
                }else{
                    info.setData22(reportInfo.getData5());
                }
                break;
            case 12:
                if(type.equals("ALL")){
                    info.setData23(reportInfo.getData5());
                }else{
                    info.setData24(reportInfo.getData5());
                }
                break;
            default:
                break;
        }
        Money total=  new Money(info.getTotal());
        total=total.add(new Money(reportInfo.getData5()));
        info.setTotal(total.toString());
        return info;
    }

    private ExpectReleaseInfo getTotalInfo(List<ExpectReleaseInfo> infos,String busiType) {
        ExpectReleaseInfo info = new ExpectReleaseInfo();
        if (infos.size() > 0) {
            Money totalData1 = new Money(0.00);
            Money totalData2 = new Money(0.00);
            Money totalData3 = new Money(0.00);
            Money totalData4 = new Money(0.00);
            Money totalData5 = new Money(0.00);
            Money totalData6 = new Money(0.00);
            Money totalData7 = new Money(0.00);
            Money totalData8 = new Money(0.00);
            Money totalData9 = new Money(0.00);
            Money totalData10 = new Money(0.00);
            Money totalData11 = new Money(0.00);
            Money totalData12 = new Money(0.00);
            Money totalData13 = new Money(0.00);
            Money totalData14 = new Money(0.00);
            Money totalData15 = new Money(0.00);
            Money totalData16 = new Money(0.00);
            Money totalData17 = new Money(0.00);
            Money totalData18 = new Money(0.00);
            Money totalData19 = new Money(0.00);
            Money totalData20 = new Money(0.00);
            Money totalData21 = new Money(0.00);
            Money totalData22 = new Money(0.00);
            Money totalData23 = new Money(0.00);
            Money totalData24 = new Money(0.00);
            Money total = new Money(0.00);
            for (ExpectReleaseInfo itemInfo : infos) {
                totalData1 = totalData1.add(new Money(itemInfo.getData1()));
                totalData2 = totalData2.add(new Money(itemInfo.getData2()));
                totalData3 = totalData3.add(new Money(itemInfo.getData3()));
                totalData4 = totalData4.add(new Money(itemInfo.getData4()));
                totalData5 = totalData5.add(new Money(itemInfo.getData5()));
                totalData6 = totalData6.add(new Money(itemInfo.getData6()));
                totalData7 = totalData7.add(new Money(itemInfo.getData7()));
                totalData8 = totalData8.add(new Money(itemInfo.getData8()));
                totalData9 = totalData9.add(new Money(itemInfo.getData9()));
                totalData10 = totalData10.add(new Money(itemInfo.getData10()));
                totalData11 = totalData11.add(new Money(itemInfo.getData11()));
                totalData12 = totalData12.add(new Money(itemInfo.getData12()));
                totalData13 = totalData13.add(new Money(itemInfo.getData13()));
                totalData14 = totalData14.add(new Money(itemInfo.getData14()));
                totalData15 = totalData15.add(new Money(itemInfo.getData15()));
                totalData16 = totalData16.add(new Money(itemInfo.getData16()));
                totalData17 = totalData17.add(new Money(itemInfo.getData17()));
                totalData18 = totalData18.add(new Money(itemInfo.getData18()));
                totalData19 = totalData19.add(new Money(itemInfo.getData19()));
                totalData20 = totalData20.add(new Money(itemInfo.getData20()));
                totalData21 = totalData21.add(new Money(itemInfo.getData21()));
                totalData22 = totalData22.add(new Money(itemInfo.getData22()));
                totalData23 = totalData23.add(new Money(itemInfo.getData23()));
                totalData24 = totalData24.add(new Money(itemInfo.getData24()));
            }
            total = total.add(totalData1).add(totalData2).add(totalData3).add(totalData4)
                    .add(totalData5).add(totalData6).add(totalData7).add(totalData8).add(totalData9)
                    .add(totalData10).add(totalData11).add(totalData12)
                    .add(totalData13)
                    .add(totalData14)
                    .add(totalData15)
                    .add(totalData16)
                    .add(totalData17)
                    .add(totalData18)
                    .add(totalData19)
                    .add(totalData20)
                    .add(totalData21)
                    .add(totalData22)
                    .add(totalData23)
                    .add(totalData24);
            info.setDept("合计");
            info.setData1(totalData1.toString());
            info.setData2(totalData2.toString());
            info.setData3(totalData3.toString());
            info.setData4(totalData4.toString());
            info.setData5(totalData5.toString());
            info.setData6(totalData6.toString());
            info.setData7(totalData7.toString());
            info.setData8(totalData8.toString());
            info.setData9(totalData9.toString());
            info.setData10(totalData10.toString());
            info.setData11(totalData11.toString());
            info.setData12(totalData12.toString());
            info.setData13(totalData13.toString());
            info.setData14(totalData14.toString());
            info.setData15(totalData15.toString());
            info.setData16(totalData16.toString());
            info.setData17(totalData17.toString());
            info.setData18(totalData18.toString());
            info.setData19(totalData19.toString());
            info.setData20(totalData20.toString());
            info.setData21(totalData21.toString());
            info.setData22(totalData22.toString());
            info.setData23(totalData23.toString());
            info.setData24(totalData24.toString());
            info.setTotal(total.toString());

        }
        info.setBusiType(busiType);
        return info;
    }
}
