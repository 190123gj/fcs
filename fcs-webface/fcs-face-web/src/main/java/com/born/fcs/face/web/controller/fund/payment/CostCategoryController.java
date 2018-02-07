package com.born.fcs.face.web.controller.fund.payment;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.order.payment.CostCategoryOrder;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("fundMg/costCategory/")
public class CostCategoryController extends BaseController {
	final static String vm_path = "fundMg/paymentMg/basicData/";
	
	@RequestMapping("list.htm")
	public String costCategoryList(HttpServletRequest request, Model model) {
		buildSystemNameDefaultUrl(request, model);
		CostCategoryQueryOrder order = new CostCategoryQueryOrder();
		setSessionLocalInfo2Order(order);
		order.setPageSize(100000);
		QueryBaseBatchResult<CostCategoryInfo> queryResult = costCategoryServiceClient.queryPage(order);
		model.addAttribute("page", PageUtil.getCovertPage(queryResult));
		model.addAttribute("conditions", order);
		String isEdit = request.getParameter("isEdit");
		if("true".equals(isEdit)){
			return vm_path + "costCategoryEdit.vm";
		}
		return vm_path + "costCategory.vm";
	}

	@ResponseBody
	@RequestMapping("add.json")
	public JSONObject add(CostCategoryOrder order, HttpServletRequest request, Model model) {
		FcsBaseResult result = new FcsBaseResult();
		List<CostCategoryOrder> processOrderList = new ArrayList<CostCategoryOrder>();
		String delIds = request.getParameter("delId");
		if(StringUtil.isNotBlank(delIds)){
			String[] ids = delIds.split(";");
			for(String id : ids){
				if(StringUtil.isNotBlank(id)){
					long categoryId = Long.valueOf(id);
					if(categoryId>0){
						CostCategoryOrder delOrder = new CostCategoryOrder();
						delOrder.setCategoryId(categoryId);
						delOrder.setDel(true);
						processOrderList.add(delOrder);
					}
				}
			}
		}
		List<CostCategoryOrder> orderList = order.getBatchList();
		processOrderList.addAll(orderList);
		if(processOrderList.isEmpty()){
			result.setMessage("没有需要处理的数据");
			return toJSONResult(result, "费用种类保存");
		}
		order.setBatchList(processOrderList);
		result = costCategoryServiceClient.save(order);
		return toJSONResult(result, "费用种类保存");
	}
	
	@ResponseBody
	@RequestMapping("delete.json")
	public JSONObject add(long categoryId, HttpServletRequest request, Model model) {
		FcsBaseResult result = costCategoryServiceClient.deleteById(categoryId);
		return toJSONResult(result, "费用种类删除");
	}
}
