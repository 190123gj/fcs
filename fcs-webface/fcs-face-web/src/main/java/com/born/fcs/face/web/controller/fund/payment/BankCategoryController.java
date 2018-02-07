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
import com.born.fcs.fm.ws.info.payment.BankCategoryInfo;
import com.born.fcs.fm.ws.order.payment.BankCategoryOrder;
import com.born.fcs.fm.ws.order.payment.BankCategoryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("fundMg/bankCategory/")
public class BankCategoryController extends BaseController {
	final static String vm_path = "fundMg/paymentMg/basicData/";
	
	@RequestMapping("list.htm")
	public String bankCategoryList(HttpServletRequest request, Model model) {
		buildSystemNameDefaultUrl(request, model);
		BankCategoryQueryOrder order = new BankCategoryQueryOrder();
		setSessionLocalInfo2Order(order);
		order.setPageSize(100000);
		QueryBaseBatchResult<BankCategoryInfo> queryResult = bankCategoryServiceClient.queryPage(order);
		model.addAttribute("page", PageUtil.getCovertPage(queryResult));
		model.addAttribute("conditions", order);
		String isEdit = request.getParameter("isEdit");
		if("true".equals(isEdit)){
			return vm_path + "bankCategoryEdit.vm";
		}
		return vm_path + "bankCategory.vm";
	}

	@ResponseBody
	@RequestMapping("save.json")
	public JSONObject save(HttpServletRequest request, Model model, String delId) {
		BankCategoryOrder order = new BankCategoryOrder();
		FcsBaseResult result = new FcsBaseResult();
		List<BankCategoryOrder> processOrderList = new ArrayList<BankCategoryOrder>();
		if(StringUtil.isNotBlank(delId)){
			String[] ids = delId.split(";");
			for(String id : ids){
				if(StringUtil.isNotBlank(id)){
					long categoryId = Long.valueOf(id);
					if(categoryId>0){
						BankCategoryOrder delOrder = new BankCategoryOrder();
						delOrder.setCategoryId(categoryId);
						delOrder.setDel(true);
						processOrderList.add(delOrder);
					}
				}
			}
		}
		String[] categoryIds = request.getParameterValues("categoryId");
		String[] areas = request.getParameterValues("area");
		String[] bankCategorys = request.getParameterValues("bankCategory");
		String[] bankNames = request.getParameterValues("bankName");
		List<BankCategoryOrder> orderList = new ArrayList<BankCategoryOrder>(categoryIds.length);
		for(int i=0; i<categoryIds.length; i++){
			long categoryId = Long.valueOf(categoryIds[i]);
			BankCategoryOrder bcOrder = new BankCategoryOrder();
			bcOrder.setCategoryId(categoryId);
			bcOrder.setArea(areas[i]);
			bcOrder.setBankCategory(bankCategorys[i]);
			bcOrder.setBankName(bankNames[i]);
			orderList.add(bcOrder);
		}
		processOrderList.addAll(orderList);
		if(processOrderList.isEmpty()){
			result.setMessage("没有需要处理的数据");
			return toJSONResult(result, "费用种类保存");
		}
		order.setBatchList(processOrderList);
		result = bankCategoryServiceClient.save(order);
		return toJSONResult(result, "费用种类保存");
	}
	
	@ResponseBody
	@RequestMapping("delete.json")
	public JSONObject add(long categoryId, HttpServletRequest request, Model model) {
		FcsBaseResult result = bankCategoryServiceClient.deleteById(categoryId);
		return toJSONResult(result, "费用种类删除");
	}
}
