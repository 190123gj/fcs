package com.born.fcs.face.web.controller.project.basicmaintain;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.info.basicmaintain.AssessCompanyInfo;
import com.born.fcs.pm.ws.info.common.RegionInfo;
import com.born.fcs.pm.ws.order.basicmaintain.AssessCompanyOrder;
import com.born.fcs.pm.ws.order.basicmaintain.AssessCompanyQueryOrder;
import com.born.fcs.pm.ws.order.common.RegionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("projectMg/basicmaintain/assessCompany")
public class AssessCompanyController extends BaseController {

    final static String vm_path = "/projectMg/assistSys/basicDataMg/";

    /**
     * 评估公司管理列表
     *
     * @param model
     * @return
     */
    @RequestMapping("assessCompanyList.htm")
    public String assessCompanyList(AssessCompanyQueryOrder order, Model model) {
//        order.setPageNumber(page);
//        order.setPageSize(size);
        QueryBaseBatchResult<AssessCompanyInfo> batchResult = assessCompanyServiceClient
                .query(order);
        List<String> cities=assessCompanyServiceClient.findCities();
        model.addAttribute("cities", cities);
        model.addAttribute("conditions", order);
        model.addAttribute("page", PageUtil.getCovertPage(batchResult));

        return vm_path + "assessList.vm";
    }
    /**
     * 新增评估公司
     *
     * @param model
     * @return
     */
    @RequestMapping("addAssessCompany.htm")
    public String addAssessCompany(HttpServletRequest request, HttpServletResponse response, Model model) {
        return vm_path + "assessAdd.vm";
    }

    /**
     * 保存评估公司
     *
     * @param order
     * @param model
     * @return
     */
    @RequestMapping("saveAssessCompany.htm")
    @ResponseBody
    public JSONObject saveAssessCompany(AssessCompanyOrder order, Model model) {
        String tipPrefix = " 新增评估公司";
        JSONObject result = new JSONObject();
        try {
            // 初始化Form验证信息

            String cityCode=order.getCityCode();
            String cityName=order.getCity();
            if("市辖区".equals(order.getCity())||"县".equals(order.getCity())||"请选择".equals(order.getCity())){
                order.setCityCode(order.getProvinceCode());
                order.setCity(order.getProvinceName());
                order.setProvinceCode(cityCode);
                order.setProvinceName(cityName);
            }
            if(order.getCompanyId() != null && order.getCompanyId() > 0){
                AssessCompanyInfo info=assessCompanyServiceClient.findById(order.getCompanyId());
                if(!order.getCompanyName().equals(info.getCompanyName())){
                    long count=assessCompanyServiceClient
                            .findByName(order.getCompanyName());
                    if (count>0) {
                        result.put("success", false);
                        result.put("message", "该公司已存在");
                        return result;
                    }
                }

            }
            setSessionLocalInfo2Order(order);
            FcsBaseResult saveResult = assessCompanyServiceClient
                    .save(order);
            if (saveResult.isSuccess()) {
                result.put("success", true);
                result.put("message", "新增成功");
            } else {
                result.put("success", false);
                result.put("message", saveResult.getMessage());
            }
        } catch (Exception e) {
            result = toJSONResult(tipPrefix, e);
            logger.error(tipPrefix, e);
        }

        return result;
    }

    /**
     * 查看评估公司
     *
     * @param companyId
     * @param model
     * @return
     */
    @RequestMapping("viewAssessCompany.htm")
    public String viewRiskReview(Long companyId, Model model) {
        String tipPrefix = "查看评估公司数据";
        try {
            AssessCompanyInfo assessCompanyInfo = assessCompanyServiceClient
                    .findById(companyId);
            String cityCode=assessCompanyInfo.getCityCode();
            String cityName=assessCompanyInfo.getCity();
            if("市辖区".equals(assessCompanyInfo.getProvinceName())||"县".equals(assessCompanyInfo.getProvinceName())||"请选择".equals(assessCompanyInfo.getProvinceName())){
                assessCompanyInfo.setCityCode(assessCompanyInfo.getProvinceCode());
                assessCompanyInfo.setCity(assessCompanyInfo.getProvinceName());
                assessCompanyInfo.setProvinceCode(cityCode);
                assessCompanyInfo.setProvinceName(cityName);
            }
            model.addAttribute("companyInfo", assessCompanyInfo);
        } catch (Exception e) {
            logger.error(tipPrefix, e);
        }

        return vm_path + "assessAdd.vm";
    }

    /**
     * 更新评估公司数据
     *
     * @param order
     * @param model
     * @return
     */
    @RequestMapping("updateAssessCompany.json")
    @ResponseBody
    public JSONObject updateAssessCompany(AssessCompanyOrder order, Model model) {

        String tipPrefix = "更新评估公司数据";
        JSONObject result = new JSONObject();
        try {
            setSessionLocalInfo2Order(order);
            FcsBaseResult saveResult = assessCompanyServiceClient
                    .save(order);
            if (saveResult.isSuccess()) {
                result.put("success", true);
                result.put("message", "更新成功");
            } else {
                result.put("success", false);
                result.put("message", saveResult.getMessage());
            }
        } catch (Exception e) {
            result = toJSONResult(tipPrefix, e);
            logger.error(tipPrefix, e);
        }

        return result;
    }

    /**
     * 查看评估公司
     *
     * @param companyId
     * @param model
     * @return
     */
    @RequestMapping("deleteAssessCompany.htm")
    @ResponseBody
    public JSONObject deleteAssessCompany(Long companyId, Model model) {
        String tipPrefix = "删除评估公司数据";
        JSONObject result = new JSONObject();
        AssessCompanyOrder order=new AssessCompanyOrder();
        order.setCompanyId(companyId);
        setSessionLocalInfo2Order(order);
        try {
            FcsBaseResult delResult=assessCompanyServiceClient
                    .deleteById(order);
            if (delResult.isSuccess()) {
                result.put("success", true);
                result.put("message", "删除成功");
            } else {
                result.put("success", false);
                result.put("message", delResult.getMessage());
            }
        } catch (Exception e) {
            logger.error(tipPrefix, e);
        }
        return result;
    }

    /**
     * 检查评估公司名称是否重复
     *
     * @param companyName
     * @param model
     * @return
     */
    @RequestMapping("checkCompanyName.htm")
    @ResponseBody
    public boolean checkCompanyName(String companyName, Model model) {
        String tipPrefix = "检查评估公司名称是否重复";
        try {
            long count=assessCompanyServiceClient
                    .findByName(companyName);
            if (count>0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error(tipPrefix, e);
        }
        return true;
    }
}
