package com.bornsoft.web.app.customer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.PersonalCustomerInfo;
import com.born.fcs.crm.ws.service.order.CustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.ListOrder;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.bornsoft.api.service.app.DataPermissionUtil;
import com.bornsoft.api.service.app.util.WebUtil;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.web.app.base.BaseController;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 客户管理-个人
 * */
@Controller
@RequestMapping("/customerMg/personalCustomer")
public class PersonalCustomerController extends BaseController {
	String VM_PATH = "/customerMg/customer/personal/";

	@ResponseBody
	@RequestMapping("info.json")
	public JSONObject getInfo(String customerId, Boolean isUpdate, Model model) {
		PersonalCustomerInfo info = personalCustomerClient
				.queryById(customerId);
		JSONObject result = toJSONResult(AppResultCodeEnum.SUCCESS, "查询成功");
		result.put("customerInfo",  GsonUtil.toJSONObject(info));
		return result;
	}
	

	@ResponseBody
	@RequestMapping("add.json")
	public JSONObject add(HttpServletRequest request, ListOrder listOrder) {
		try {
			CustomerDetailOrder order = new CustomerDetailOrder();
			WebUtil.setPoPropertyByRequest(order, request);
			//校验客户是否存在
			checkExist(CustomerTypeEnum.PERSIONAL, order.getCertNo(), order.getCustomerName());
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			Org org = sessionLocal.getUserDetailInfo().getPrimaryOrg();
			if (DataPermissionUtil.isBusiFZR() || DataPermissionUtil.isXinHuiFzr()) {
				//业务总监添加
				order.setDepId(org.getId());
				order.setDepName(org.getName());
				order.setDirectorId(sessionLocal.getUserId());
				order.setDirector(sessionLocal.getRealName());
//			order.setIsDistribution(BooleanEnum.NO.code());
				order.setDepPath(org.getPath());
			}
			if (DataPermissionUtil.isBusiManager() || DataPermissionUtil.isXinHuiBusiManager()) {
				//客户经理添加
				order.setDepId(org.getId());
				order.setDepName(org.getName());
				order.setCustomerManagerId(sessionLocal.getUserId());
				order.setCustomerManager(sessionLocal.getRealName());
				order.setDepPath(org.getPath());
//			order.setIsDistribution(BooleanEnum.IS.code());
			}
			
			if (listOrder != null && ListUtil.isNotEmpty(listOrder.getReqList())) {
				order.setReqList(listOrder.getReqList());
			}
			setRelation(request, order);
			order.setInputPersonId(sessionLocal.getUserId());
			order.setInputPerson(sessionLocal.getRealName());
			order.setCustomerType(CustomerTypeEnum.PERSIONAL.code());
			logger.info("添加个人客户,order={}",order);
			FcsBaseResult result = customerServiceClient.add(order);
			synWatchList(order);
			verifyMsgSave(request, order.getCustomerName() + result.getKeyId());
			return toJSONResult(result, "个人客户添加成功", null);
		} catch (Exception e) {
			logger.error("个人客户添加失败", e);
			return toJSONResult(new JSONObject(), AppResultCodeEnum.FAILED, e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping("update.json")
	public JSONObject update(HttpServletRequest request, ListOrder listOrder,
			Model model) {
		try {
			CustomerDetailOrder order = new CustomerDetailOrder();
			WebUtil.setPoPropertyByRequest(order, request);
			
			if(StringUtil.isNotBlank(order.getCustomerId())){
				PersonalCustomerInfo info = personalCustomerClient
						.queryById(order.getCustomerId());
				if(StringUtil.notEquals(info.getCustomerName(), order.getCustomerName())
						|| StringUtil.notEquals(info.getCertNo(), order.getCertNo())){
					//校验客户是否存在
					checkExist(CustomerTypeEnum.PERSIONAL, order.getCertNo(), order.getCustomerName());
				}
			}else{
				//校验客户是否存在
				checkExist(CustomerTypeEnum.PERSIONAL, order.getCertNo(), order.getCustomerName());
			}
			
			if (ListUtil.isNotEmpty(listOrder.getReqList())) {
				order.setReqList(listOrder.getReqList());
			}
			setRelation(request, order);
			order.setCustomerType(CustomerTypeEnum.PERSIONAL.code());
			FcsBaseResult result = customerServiceClient.updateByUserId(order);
			return toJSONResult(result, "个人客户更新成功", null);
		} catch (BornApiException e) {
			logger.error("个人客户更新失败", e);
			return toJSONResult(new JSONObject(), AppResultCodeEnum.FAILED, e.getMessage());
		}

	}

	/** 删除个人客户 **/
	@ResponseBody
	@RequestMapping("delete.json")
	public JSONObject delete(Long userId, Model model) {
		FcsBaseResult result = customerServiceClient.delete(userId);
		return toJSONResult(result, "个人客户删除成功", null);

	}


	private void setRelation(HttpServletRequest request,
			CustomerDetailOrder order) {
		String[] ralations = request.getParameterValues("relation");
		if (ralations != null && ralations.length > 0) {
			String ralation = ralations[0];
			for (int i = 1; i < ralations.length; i++) {
				ralation += "," + ralations[i];
			}
			order.setRelation(ralation);
		}

	}

}
