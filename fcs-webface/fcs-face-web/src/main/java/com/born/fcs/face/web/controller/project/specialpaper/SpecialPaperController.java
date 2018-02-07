package com.born.fcs.face.web.controller.project.specialpaper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.enums.SpecialPaperSourceEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationInfo;
import com.born.fcs.pm.ws.info.specialpaper.*;
import com.born.fcs.pm.ws.order.specialpaper.*;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("projectMg/specialPaper")
public class SpecialPaperController extends BaseController {
    final static String vm_path = "/projectMg/assistSys/specialtyPaper/";

    @Override
    protected String[] getDateInputDayNameArray() {
        return new String[] { "receiveDate","startDate","endDate","invalidDate","invalidReceiveDate"};
    }

    /**
     * 特殊纸登记
     *
     * @param order
     * @param model
     * @return
     */
    @RequestMapping("checkIn.htm")
    @ResponseBody
    public JSONObject checkIn(SpecialPaperNoListOrder order, Model model) {
        String tipPrefix = " 特殊纸登记";
        JSONObject result = new JSONObject();
        try {
            setSessionLocalInfo2Order(order);
            SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
            List<SpecialPaperNoOrder> noOrderList=order.getListOrder();
            if(noOrderList!=null&&noOrderList.size()>0){
                for(SpecialPaperNoOrder noOrder:noOrderList){
                    if(null!=noOrder.getId()&&noOrder.getId()>0){
                        SpecialPaperQueryOrder queryOrder=new SpecialPaperQueryOrder();
                        queryOrder.setParentId(noOrder.getId());
                        QueryBaseBatchResult<SpecialPaperNoInfo> batchResult = specialPaperServiceClient
                                .checkInList(queryOrder);
                        if(batchResult.getPageList().size()>0){
                            SpecialPaperNoInfo noInfo=batchResult.getPageList().get(0);
                            result.put("success", false);
                            result.put("message", noInfo.getStartNo()+"-"+noInfo.getEndNo()+"号段已被发放过，请重新编辑起止号");
                            return result;
                        }
                    }
                }
            }
            order.setKeepingManId(sessionLocal.getUserId());
            order.setKeepingManName(sessionLocal.getRealName());
            FcsBaseResult saveResult = specialPaperServiceClient
                    .checkIn(order);
            if (saveResult.isSuccess()) {
                result.put("success", true);
                result.put("message", "登记成功");
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
     * 特殊纸登记-新增
     *
     * @param model
     * @return
     */
    @RequestMapping("addCheckIn.htm")
    public String addCheckIn(String id, HttpServletRequest request, HttpServletResponse response,
                                Model model) {
        SpecialPaperNoInfo info =new SpecialPaperNoInfo();
        if(id!=null){
            info=specialPaperServiceClient.findById(Long.parseLong(id));
        }
        model.addAttribute("info", info);
        return vm_path + "SPregister.vm";
    }

    /**
     * 特殊纸部门发放
     *
     * @param order
     * @param model
     * @return
     */
    @RequestMapping("provideDept.htm")
    @ResponseBody
    public JSONObject provideDept(SpecialPaperProvideDeptOrder order, Model model) {
        String tipPrefix = " 特殊纸部门发放";
        JSONObject result = new JSONObject();
        try {
            SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
            order.setProvideManId(sessionLocal.getUserId());
            order.setProvideManName(sessionLocal.getRealName());
            setSessionLocalInfo2Order(order);
            FcsBaseResult saveResult = specialPaperServiceClient
                    .provideDept(order);
            if (saveResult.isSuccess()) {
                result.put("success", true);
                result.put("message", "特殊纸部门发放成功");
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
     * 特殊纸项目发放
     *
     * @param order
     * @param model
     * @return
     */
    @RequestMapping("provideProject.htm")
    @ResponseBody
    public JSONObject provideProject(SpecialPaperProvideProjectOrder order, Model model) {
        String tipPrefix = " 特殊纸项目发放";
        JSONObject result = new JSONObject();
        try {
            ProjectInfo projectInfo=projectServiceClient.queryByCode(order.getProjectCode(),false);
            order.setReceiptPlace(projectInfo.getCustomerName());
            SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
            order.setProvideManId(sessionLocal.getUserId());
            order.setProvideManName(sessionLocal.getRealName());
            setSessionLocalInfo2Order(order);
            FcsBaseResult saveResult = specialPaperServiceClient
                    .provideProject(order);
            if (saveResult.isSuccess()) {
                result.put("success", true);
                result.put("message", "特殊纸项目发放成功");
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
     * 特殊纸作废
     *
     * @param order
     * @param model
     * @return
     */
    @RequestMapping("invalid.htm")
    @ResponseBody
    public JSONObject invalid(SpecialPaperInvalidOrder order, Model model) {
        String tipPrefix = " 特殊纸作废";
        JSONObject result = new JSONObject();
        try {

            List<SpecialPaperNoOrder> listOrder=order.getListOrder();
            for(SpecialPaperNoOrder noOrder:listOrder){//校验已作废的编号
                SpecialPaperQueryOrder queryOrder=new SpecialPaperQueryOrder();
                queryOrder.setStartNo(noOrder.getStartNo()+"");
                queryOrder.setEndNo(noOrder.getEndNo()+"");
                queryOrder.setIsSaveInvlid("IS");
                List<SpecialPaperResultInfo> resultInfoList = specialPaperServiceClient
                        .checkSaveInvalid(queryOrder);
                if(resultInfoList!=null&&resultInfoList.size()>0){
                    result.put("success", false);
                    result.put("message", "已存在作废编号，不能重复作废");
                    return result;
                }
            }
            setSessionLocalInfo2Order(order);
            SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
            order.setKeepingManId(sessionLocal.getUserId());
            order.setKeepingManName(sessionLocal.getRealName());
            FcsBaseResult saveResult = specialPaperServiceClient
                    .invalid(order);
            if (saveResult.isSuccess()) {
                result.put("success", true);
                result.put("message", "特殊纸作废成功");
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
     * 特殊纸发放-特种纸管理员
     *
     * @param model
     * @return
     */
    @RequestMapping("addProvideDept.htm")
    public String addProvideDept(String id, HttpServletRequest request, HttpServletResponse response,
                             Model model) {
        SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
        SpecialPaperProvideDeptInfo info=new SpecialPaperProvideDeptInfo();
        info.setReceiveManId(sessionLocal.getUserId());
        info.setReceiveManName(sessionLocal.getRealName());
        model.addAttribute("info", info);
        return vm_path + "SPsectionAdmin.vm";
    }
    /**
     * 特殊纸发放-部门纸发放
     *
     * @param model
     * @return
     */
    @RequestMapping("addProvideProject.htm")
    public String addProvideProject(String id, HttpServletRequest request, HttpServletResponse response,
                             Model model) {
        SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
        SpecialPaperProvideProjectInfo info=new SpecialPaperProvideProjectInfo();
        info.setReceiveManId(sessionLocal.getUserId());
        info.setReceiveManName(sessionLocal.getRealName());
        model.addAttribute("info", info);
        return vm_path + "SPspecialtyPaperAdmin.vm";
    }
    /**
     * 特殊纸作废-新增
     *
     * @param model
     * @return
     */
    @RequestMapping("addInvalid.htm")
    public String addInvalid(String id, HttpServletRequest request, HttpServletResponse response,
                             Model model) {
        SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
        SpecialPaperInvalidInfo info=new SpecialPaperInvalidInfo();
        info.setKeepingManId(sessionLocal.getUserId());
        info.setKeepingManName(sessionLocal.getRealName());
        info.setReceiveManId(sessionLocal.getUserId());
        info.setReceiveManName(sessionLocal.getRealName());
        model.addAttribute("info", info);
        return vm_path + "SPcancel.vm";
    }

    /**
     * 特殊纸列表
     *
     * @param model
     * @return
     */
    @RequestMapping("list.htm")
    public String list(String type,SpecialPaperQueryOrder order, Model model) {
        if(type.equals(SpecialPaperSourceEnum.CHECKIN.code())){
            order.setSource(SpecialPaperSourceEnum.getByCode(type));
            QueryBaseBatchResult<SpecialPaperNoInfo> batchResult = specialPaperServiceClient
                    .checkInList(order);
            model.addAttribute("page", PageUtil.getCovertPage(batchResult));
        }else {
            order.setSource(SpecialPaperSourceEnum.getByCode(type));
            QueryBaseBatchResult<SpecialPaperResultInfo> batchResult = specialPaperServiceClient
                    .specialPaperList(order);
            model.addAttribute("page", PageUtil.getCovertPage(batchResult));
        }
        model.addAttribute("queryConditions", order);

        model.addAttribute("source",order.getSource().code());
        model.addAttribute("type",type);
        return vm_path + "list.vm";
    }

    @RequestMapping("chooseNo.htm")
    @ResponseBody
    public JSONObject chooseNo(String type,SpecialPaperQueryOrder order, HttpServletRequest request, Model model,
                                        HttpSession session) {
        String tipPrefix = "查询可用登记记录";
        JSONObject result = new JSONObject();
        try {
            JSONObject data = new JSONObject();
            JSONArray dataList = new JSONArray();
            if(SpecialPaperSourceEnum.CHECKIN.code().equals(type)){
                order.setSource(SpecialPaperSourceEnum.CHECKIN);
            }
            if(SpecialPaperSourceEnum.PROVIDE_DEPT.code().equals(type)){
                order.setSource(SpecialPaperSourceEnum.PROVIDE_DEPT);
            }
            order.setHasLeft("IS");
            QueryBaseBatchResult<SpecialPaperNoInfo> batchResult = specialPaperServiceClient
                    .checkInList(order);
            if(batchResult!=null){
            for(SpecialPaperNoInfo info:batchResult.getPageList()){
                        JSONObject json = new JSONObject();
                        json.put("id", info.getId());
                        json.put("startNo", info.getEndNo()-info.getLeftPaper()+1);
                        json.put("endNo", info.getEndNo());
                        json.put("leftPaper", info.getLeftPaper());
                        dataList.add(json);
            }
            }
            data.put("pageCount", batchResult.getPageCount());
            data.put("pageNumber", batchResult.getPageNumber());
            data.put("pageSize", batchResult.getPageSize());
            data.put("totalCount", batchResult.getTotalCount());
            data.put("dataList", dataList);
            result = toStandardResult(data, tipPrefix);
        } catch (Exception e) {
            result = toStandardResult(null, tipPrefix);
            logger.error("", e);
        }
        return  result;
    }

    /**
     * 校验起止号的合法性
     *
     * @param startNo
     * @param model
     * @return
     */
    @RequestMapping("checkNO.htm")
    @ResponseBody
    public boolean checkNO(String startNo,String endNo,String id,String type, Model model) {
        String tipPrefix = "校验起止号的合法性";
        JSONObject result = new JSONObject();
        try {
                SpecialPaperQueryOrder order=new SpecialPaperQueryOrder();
                order.setStartNo(startNo);
                order.setEndNo(endNo);
                order.setCheckNo("IS");
                order.setSource(SpecialPaperSourceEnum.CHECKIN);
                QueryBaseBatchResult<SpecialPaperNoInfo> batchResult = specialPaperServiceClient
                        .checkInList(order);
                if(id!=null&&!"".equals(id)&&!"0".equals(id)){
                    if (batchResult==null||(batchResult.getPageList().size()==1&&(batchResult.getPageList().get(0).getId()==Long.parseLong(id)))){
                        return true;
                    }
                }
                if (batchResult==null){
                    return true;
                }
                if (batchResult.getPageList().size() > 0) {
                    return false;
                } else {
                    return true;
                }

        } catch (Exception e) {
            logger.error(tipPrefix, e);
        }
        return true;
    }

    /**
     * 作废校验
     *
     * @param startNo
     * @param model
     * @return
     */
    @RequestMapping("checkInvalid.htm")
    @ResponseBody
    public JSONObject checkInvalid(String startNo,String endNo,String type, Model model) {
        String tipPrefix = "校验起止号的合法性";
        JSONObject result = new JSONObject();
        try {
                SpecialPaperQueryOrder order=new SpecialPaperQueryOrder();
                order.setStartNo(startNo);
                order.setEndNo(endNo);
                List<SpecialPaperResultInfo> infos=specialPaperServiceClient.checkInvalid(order);
                if(infos!=null&&infos.size()>0){
                    result.put("success",true);
                    result.put("info",infos.get(0));
                    result.put("receiveDate", DateUtil.simpleFormat(infos.get(0).getRawAddTime()).toString());
                }else{
                    result.put("success",false);
                    result.put("message","号码检验失败");
                }
        } catch (Exception e) {
            result.put("success",false);
            result.put("message","号码检验失败");
            logger.error(tipPrefix, e);
        }
        return result;
    }

    /**
     * 删除特殊纸登记记录
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("deleteCheckIn.htm")
    @ResponseBody
    public JSONObject deleteCheckIn(String id, Model model) {
        String tipPrefix = "删除特殊纸登记记录";
        JSONObject result = new JSONObject();
        try {
            if(id!=null&&Long.parseLong(id)>0) {
                SpecialPaperQueryOrder queryOrder = new SpecialPaperQueryOrder();
                queryOrder.setParentId(Long.parseLong(id));
                QueryBaseBatchResult<SpecialPaperNoInfo> batchResult = specialPaperServiceClient
                        .checkInList(queryOrder);
                if (batchResult.getPageList().size() > 0) {
                    result.put("success", false);
                    result.put("message", "该记录已有发放记录,不允许删除");
                    return result;
                }
            }
            SpecialPaperNoOrder order=new SpecialPaperNoOrder();
            order.setId(Long.parseLong(id));
            FcsBaseResult delResult= specialPaperServiceClient.deleteById(order);
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
}
