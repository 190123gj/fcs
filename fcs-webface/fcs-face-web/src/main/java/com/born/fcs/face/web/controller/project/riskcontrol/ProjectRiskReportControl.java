package com.born.fcs.face.web.controller.project.riskcontrol;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.face.web.bean.ProjectBean;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.projectRiskReport.ProjectRiskReportInfo;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyQueryOrder;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportDeleteOrder;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportProcessOrder;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * Created by wqh on 2016/9/20.
 */
@Controller
@RequestMapping("projectMg/projectRiskReport")
public class ProjectRiskReportControl extends WorkflowBaseController {
    private static final String VM_PATH = "/projectMg/riskControl/riskReport/";

    @Override
    protected String[] getDateInputNameArray() {
        return new String[]{"startTimeBegin", "startTimeEnd", "occurTime"};
    }


    /**
     * 成立风险处置小组
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("list.htm")
    public String list(HttpServletRequest request, Model model, ProjectRiskReportQueryOrder queryOrder) {
        try {
            setSessionLocalInfo2Order(queryOrder);
            QueryBaseBatchResult<ProjectRiskReportInfo> batchResult = projectRiskReportClient.queryProjectRiskReportInfo(queryOrder);
            model.addAttribute("page", PageUtil.getCovertPage(batchResult));
            model.addAttribute("queryConditions", queryOrder);
        } catch (Exception e) {
            logger.error("查询出错");
        }
        return VM_PATH + "list.vm";
    }



    /**
     * 成立风险处置小组
     *
     * @param request
     * @return
     */
    @RequestMapping("info.htm")
    public String info(HttpServletRequest request, Model model) {
        String reportId = request.getParameter("reportId");
        String type = request.getParameter("type");
        ProjectRiskReportInfo teamInfo = null;
        if (StringUtil.isNotEmpty(reportId)) {
            teamInfo = projectRiskReportClient.findByReportId(NumberUtil.parseLong(reportId));
            type = teamInfo.getReportType();
        } else {
            teamInfo = new ProjectRiskReportInfo();
        }
        model.addAttribute("info", teamInfo);
        model.addAttribute("type",type);
        if(StringUtil.equals(type,"final")){
            return VM_PATH + "infoFinal.vm";
        }else if(StringUtil.equals(type,"month")){
            return VM_PATH + "infoMonth.vm";
        }else{
            return VM_PATH + "infoMajor.vm";
        }

    }





    /**
     * 成立风险处置小组
     *
     * @param request
     * @return
     */
    @RequestMapping("toAdd.htm")
    public String toAdd(HttpServletRequest request, Model model) {
        String reportId = request.getParameter("reportId");
        String type = request.getParameter("type");
        ProjectRiskReportInfo teamInfo = null;
        if (StringUtil.isNotEmpty(reportId)) {
            teamInfo = projectRiskReportClient.findByReportId(NumberUtil.parseLong(reportId));
            type = teamInfo.getReportType();
        } else {
            teamInfo = new ProjectRiskReportInfo();
        }
        model.addAttribute("info", teamInfo);
        model.addAttribute("type",type);
        if(StringUtil.equals(type,"final")){
              return VM_PATH + "addFinal.vm";
        }else if(StringUtil.equals(type,"month")){
            return VM_PATH + "addMonth.vm";
        }else{
            return VM_PATH + "addMajor.vm";
        }

    }

    /**
     * 成立风险处置小组
     *
     * @param request
     * @param processOrder
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public JSONObject save(HttpServletRequest request, ProjectRiskReportProcessOrder processOrder) {
        String tipPrefix = " 新增汇报";
        JSONObject result = new JSONObject();
        try {
            setSessionLocalInfo2Order(processOrder);
            FcsBaseResult saveResult = projectRiskReportClient.save(processOrder);
            return toJSONResult(saveResult, tipPrefix);
        } catch (Exception e) {
            result = toJSONResult(tipPrefix, e);
            logger.error(tipPrefix, e);
        }
        return result;
    }



    @RequestMapping("queryProjectInfo.json")
    @ResponseBody
    public JSONObject queryProjectInfo(HttpServletRequest request){
        String tipPrefix = " 删除风险项目汇报";
        JSONObject result = new JSONObject();
        try {
            String projectCode = request.getParameter("projectCode");
            String userId = request.getParameter("userId");
            String  reportType = request.getParameter("reportType");
            ProjectSimpleDetailInfo detailInfo = projectServiceClient.querySimpleDetailInfo(projectCode);
            result.put("timeLimit",detailInfo.getTimeLimit());
            result.put("timeUnit",detailInfo.getTimeUnit());
            if(detailInfo.getTimeUnit() != null){
                result.put("timeUnitText",detailInfo.getTimeUnit().getMessage());
            }
            result.put("amount",detailInfo.getAmount().toStandardString());
            ProjectRelatedUserInfo userInfo = projectRelatedUserServiceClient.getRiskManager(projectCode);
            if(userInfo != null){
                result.put("riskManagerId",userInfo.getUserId());
                result.put("riskManagerName",userInfo.getUserName());
            }
            FCapitalAppropriationApplyQueryOrder queryOrder = new FCapitalAppropriationApplyQueryOrder();
            queryOrder.setProjectCode(projectCode);
            queryOrder.setFormStatus(FormStatusEnum.APPROVAL.getCode());
            List<String> appropriateReasonList = new ArrayList<String>();
            appropriateReasonList.add(PaymentMenthodEnum.COMPENSATORY_PRINCIPAL.getCode());
            queryOrder.setAppropriateReasonList(appropriateReasonList);
            List<FCapitalAppropriationApplyInfo> applyInfos = fCapitalAppropriationApplyServiceClient.query(queryOrder).getPageList();
            if(ListUtil.isNotEmpty(applyInfos)){
                JSONArray jsonArray = new JSONArray();
                for(FCapitalAppropriationApplyInfo applyInfo  : applyInfos){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("compAmount",applyInfo.getAppropriateAmount().toStandardString());
                    jsonObject.put("compDate", DateUtil.dtSimpleFormat(applyInfo.getFinishTime()));
                    jsonArray.add(jsonObject);
                }
                result.put("list",jsonArray.toJSONString());
            }

            ProjectBean projectBean =  getProjectBean(projectCode);

            result.put("loanBank",projectBean.getCapitalSubChannelName());
            result.put("guaranteeFee",projectBean.getGuaranteeRate());

            CompanyCustomerInfo cInfo = companyCustomerClient
                    .queryByUserId(NumberUtil.parseLong(userId), null);
            if(cInfo != null){
                EnterpriseNatureEnum et = EnterpriseNatureEnum.getByCode(cInfo
                        .getEnterpriseType());
                result.put("enterpriseType",
                        et == null ? cInfo.getEnterpriseType() : et.message());
            }


            ProjectRiskReportQueryOrder projectRiskReportQueryOrder = new ProjectRiskReportQueryOrder();
            projectRiskReportQueryOrder.setProjectCode(projectCode);
            projectRiskReportQueryOrder.setSortCol("raw_add_time");
            projectRiskReportQueryOrder.setSortOrder("DESC");
            projectRiskReportQueryOrder.setReportType(reportType);
            List <ProjectRiskReportInfo> list  = projectRiskReportClient.queryProjectRiskReportInfo(projectRiskReportQueryOrder).getPageList();
                if(ListUtil.isNotEmpty(list)) {
                    ProjectRiskReportInfo info = list.get(0);
                    if(StringUtil.equalsIgnoreCase("month",reportType)){
                        info.setReprot1(info.getReprot4());
                        info.setReprot4("");
                    }
                    if (info != null) {
                        result.put("projectRiskReportInfo",info);
                    }
             }
            return toStandardResult(result, tipPrefix);
        } catch (Exception e) {
            result = toJSONResult(tipPrefix, e);
            logger.error(tipPrefix, e);
        }

        return result;
    }


    /**
     * 成立风险处置小组
     *
     * @param request
     * @param deleteOrder
     * @return
     */
    @RequestMapping("delete.json")
    @ResponseBody
    public JSONObject delete(HttpServletRequest request, ProjectRiskReportDeleteOrder deleteOrder) {
        String tipPrefix = " 删除风险项目汇报";
        JSONObject result = new JSONObject();
        try {
            FcsBaseResult baseResult = projectRiskReportClient.deleteByReportId(deleteOrder);
            return toJSONResult(baseResult, tipPrefix);
        } catch (Exception e) {
            result = toJSONResult(tipPrefix, e);
            logger.error(tipPrefix, e);
        }

        return result;
    }
}
