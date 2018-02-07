package com.born.fcs.face.web.controller.project.contract;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.born.fcs.pm.ws.info.contract.DbFieldInfo;
import com.born.fcs.pm.ws.info.contract.DbFieldListInfo;
import com.born.fcs.pm.ws.info.contract.DbTableInfo;
import com.born.fcs.pm.ws.order.contract.DbFieldOrder;
import com.born.fcs.pm.ws.order.contract.DbFieldQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("projectMg/contract")
public class DbFieldController extends BaseController {

    final static String vm_path = "/projectMg/assistSys/ContractMg/";



    /**
     * 常用数据库字段列字段
     *
     * @param model
     * @return
     */
    @RequestMapping("dbFieldList.htm")
    public String dbFieldList(DbFieldQueryOrder order, Model model) {
        QueryBaseBatchResult<DbFieldListInfo>	batchResult
                = dbFieldServiceClient.query(order);
        model.addAttribute("conditions", order);
        model.addAttribute("page", PageUtil.getCovertPage(batchResult));
        return vm_path + "list_Field.vm";
    }

    /**
     * 新增常用数据库字段
     *
     * @param model
     * @return
     */
    @RequestMapping("addDbField.htm")
    public String addDbField(HttpServletRequest request, HttpServletResponse response,
                             Model model) {
        DbFieldInfo Info = new DbFieldInfo();
        model.addAttribute("info", Info);
        return vm_path + "add_Field.vm";
    }

    /**
     * 保存常用数据库字段
     *
     * @param order
     * @param model
     * @return
     */
    @RequestMapping("saveDbField.htm")
    @ResponseBody
    public JSONObject saveDbField(DbFieldOrder order, Model model) {
        String tipPrefix = " 保存常用数据库字段";
        JSONObject jsonObject = new JSONObject();
        try {

            SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
            if (sessionLocal == null) {
                jsonObject.put("success", false);
                jsonObject.put("message", "您未登陆或登陆已失效");
                return jsonObject;
            }
            setSessionLocalInfo2Order(order);
            FcsBaseResult saveResult = dbFieldServiceClient.save(order);
            jsonObject = toJSONResult(jsonObject, saveResult, "保存常用数据库字段", null);
        } catch (Exception e) {
            jsonObject.put("success", false);
            jsonObject.put("message", e);
            logger.error("保存合同模板出错", e);
        }

        return jsonObject;
    }

    /**
     * 常用数据库字段详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("viewDbField.htm")
    public String viewDbField(Long id,  Model model) {
        String tipPrefix = "常用数据库字段详情";
        try {
            DbFieldInfo info = dbFieldServiceClient.findById(id);
            DbTableInfo tableInfo=dbTableServiceClient.findById(info.getTableId());
            info.setTableName(tableInfo.getTableName());
            model.addAttribute("info", info);
        } catch (Exception e) {
            logger.error(tipPrefix, e);
        }
        return vm_path  + "add_Field.vm";
    }



    /**
     * 删除常用数据库字段
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("deleteField.htm")
    @ResponseBody
    public JSONObject deleteField(Long id, Model model) {
        String tipPrefix = "删除常用数据库字段";
        DbFieldOrder order=new DbFieldOrder();
        order.setFieldId(id);
        JSONObject result = new JSONObject();
        try {
            FcsBaseResult delResult= dbFieldServiceClient.deleteById(order);
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
     * 检查字段名称是否重复
     *
     * @param tableId
     * @param fieldName
     * @param model
     * @return
     */
    @RequestMapping("checkFieldName.htm")
    @ResponseBody
    public JSONObject checkFieldName(long tableId,String fieldName, Model model) {
        String tipPrefix = "检查字段名称是否重复";
        JSONObject result = new JSONObject();
        try {
            long count=dbFieldServiceClient
                    .findByFieldName(tableId,fieldName);
            if (count>0) {
                result.put("success",true);
            } else {
                result.put("success",false);
            }
        } catch (Exception e) {
            logger.error(tipPrefix, e);
        }
        return result;
    }

    /**
     * 检查字段简称是否重复
     *
     * @param tableName
     * @param model
     * @return
     */
    @RequestMapping("checkFieldShortName.htm")
    @ResponseBody
    public boolean checkFieldShortName(long tableId,String tableName, Model model) {
        String tipPrefix = "检查字段简称是否重复";
        try {
            long count=dbFieldServiceClient
                    .findByFieldShortName(tableId,tableName);
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
