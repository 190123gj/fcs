package com.born.fcs.face.web.controller.project.basicmaintain;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.enums.FinancialProductInterestTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductSellStatusEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTypeEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FinancialProductInfo;
import com.born.fcs.pm.ws.order.basicmaintain.FinancialProductOrder;
import com.born.fcs.pm.ws.order.basicmaintain.FinancialProductQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 理财产品维护
 * @author wuzj
 */
@Controller
@RequestMapping("projectMg/basicmaintain/financialProduct")
public class FinancialProductController extends BaseController {
	
	private final String vm_path = "/projectMg/financialMg/";
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "price" };
	}
	
	@RequestMapping("form.htm")
	public String form(Long productId, Model model) {
		
		if (!DataPermissionUtil.isFinancialZjlc() && !DataPermissionUtil.isXinHuiBusiManager()) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.NO_ACCESS, "无权新增");
		}
		
		model.addAttribute("productTypeList", FinancialProductTypeEnum.getAllEnum());
		model.addAttribute("termTypeList", FinancialProductTermTypeEnum.getAllEnum());
		model.addAttribute("interestTypeList", FinancialProductInterestTypeEnum.getAllEnum());
		model.addAttribute("interestSettlementWayList", InterestSettlementWayEnum.getAllEnum());
		
		if (productId != null && productId > 0) {
			FinancialProductInfo product = financialProductServiceClient.findById(productId);
			model.addAttribute("product", product);
		}
		
		return vm_path + "productNew.vm";
	}
	
	@RequestMapping("view.htm")
	public String view(long productId, Model model) {
		FinancialProductInfo product = financialProductServiceClient.findById(productId);
		model.addAttribute("product", product);
		return vm_path + "productView.vm";
	}
	
	@RequestMapping("list.htm")
	public String list(FinancialProductQueryOrder order, Model model) {
		QueryBaseBatchResult<FinancialProductInfo> qResult = financialProductServiceClient
			.query(order);
		model.addAttribute("queryOrder", order);
		model.addAttribute("isFinancialPersonnel", DataPermissionUtil.isFinancialZjlc()
													|| DataPermissionUtil.isXinHuiBusiManager());
		model.addAttribute("productTypeList", FinancialProductTypeEnum.getAllEnum());
		model.addAttribute("statusList", FinancialProductSellStatusEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(qResult));
		return vm_path + "productList.vm";
	}
	
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(FinancialProductOrder order) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			FcsBaseResult result = financialProductServiceClient.save(order);
			
			jsonObject = toJSONResult(jsonObject, result, "理财产品保存成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("保存理财产品出错", e);
		}
		
		return jsonObject;
	}
	
	@RequestMapping("calculateProductTermType.htm")
	@ResponseBody
	public JSONObject calculateProductTermType(Integer timeLimit, String timeUnit) {
		JSONObject jsonObject = new JSONObject();
		try {
			FinancialProductTermTypeEnum termType = financialProductServiceClient
				.calculateProductTermType(timeLimit, timeUnit);
			jsonObject.put("success", true);
			jsonObject.put("message", "计算成功");
			JSONObject termTypeJson = new JSONObject();
			termTypeJson.put("code", termType.code());
			termTypeJson.put("message", termType.message());
			jsonObject.put("data", termTypeJson);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", "出错");
		}
		
		return jsonObject;
	}
	
	@RequestMapping("changeStatus.htm")
	@ResponseBody
	public JSONObject changeStatus(long productId, FinancialProductSellStatusEnum status) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			
			FinancialProductOrder order = new FinancialProductOrder();
			order.setProductId(productId);
			order.setSellStatus(status.getCode());
			
			FcsBaseResult result = financialProductServiceClient.changeSellStatus(order);
			
			jsonObject = toJSONResult(jsonObject, result, "产品状态修改成功", null);
			
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("message", e);
			logger.error("修改产品状态出错", e);
		}
		
		return jsonObject;
	}
}
