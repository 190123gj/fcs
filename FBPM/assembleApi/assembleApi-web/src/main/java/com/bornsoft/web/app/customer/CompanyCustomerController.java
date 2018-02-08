package com.bornsoft.web.app.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.info.CompanyCustomerInfo;
import com.born.fcs.crm.ws.service.order.CustomerBaseOrder;
import com.born.fcs.crm.ws.service.order.CustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.ListOrder;
import com.born.fcs.crm.ws.service.order.VerifyMessageOrder;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.bornsoft.api.service.app.DataPermissionUtil;
import com.bornsoft.api.service.app.util.WebUtil;
import com.bornsoft.pub.enums.UserTypeEnum;
import com.bornsoft.pub.order.risk.SynWatchListOrder;
import com.bornsoft.pub.order.risk.SynWatchListOrder.WatchListInfo;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.web.app.base.BaseController;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 客户管理-企业
 * */
@Controller
@RequestMapping("/customerMg/companyCustomer")
public class CompanyCustomerController extends BaseController {
	
	String VM_PATH = "/customerMg/customer/company/";
	
	@ResponseBody
	@RequestMapping("info.json")
	public JSONObject getInfo(String customerId) {
		CompanyCustomerInfo info = companyCustomerClient.queryById(customerId,null);
		JSONObject result = toJSONResult(AppResultCodeEnum.SUCCESS, "查询");
		result.put("customerInfo", GsonUtil.toJSONObject(info));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("add.json")
	public JSONObject add(HttpServletRequest request, ListOrder listOrder) {
		try {
			CustomerDetailOrder order = new CustomerDetailOrder();
			WebUtil.setPoPropertyByRequest(order, request);
			if(StringUtil.isBlank(order.getAddress())){
				order.setAddress(request.getParameter("companyAddress"));
			}
			if (ListUtil.isNotEmpty(listOrder.getCompanyQualification())) {
				order.setCompanyQualification(listOrder.getCompanyQualification());
			}
			if (ListUtil.isNotEmpty(listOrder.getCompanyOwnershipStructure())) {
				order.setCompanyOwnershipStructure(listOrder.getCompanyOwnershipStructure());
			}
			//客户信息校验
			checkExist(CustomerTypeEnum.ENTERPRISE, order.getBusiLicenseNo(), order.getCustomerName());
			
			setRelation(request, order);
			order.setInputPerson(ShiroSessionUtils.getSessionLocal().getRealName());
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			Org org = sessionLocal.getUserDetailInfo().getPrimaryOrg();
			if (DataPermissionUtil.isBusiFZR() || DataPermissionUtil.isXinHuiFzr()) {
				//业务总监添加
				order.setDepId(org.getId());
				order.setDepName(org.getName());
				order.setDirectorId(sessionLocal.getUserId());
				order.setDirector(sessionLocal.getRealName());
				order.setIsDistribution(BooleanEnum.NO.code());
				order.setDepPath(org.getPath());
			}
			if (DataPermissionUtil.isBusiManager() || DataPermissionUtil.isXinHuiBusiManager()) {
				//客户经理添加
				order.setDepId(org.getId());
				order.setDepName(org.getName());
				order.setCustomerManagerId(sessionLocal.getUserId());
				order.setCustomerManager(sessionLocal.getRealName());
				order.setIsDistribution(BooleanEnum.IS.code());
				order.setDepPath(org.getPath());
			}
			
			order.setIsOneCert(checkBoolean(order.getIsOneCert())?BooleanEnum.IS.code():BooleanEnum.NO.code());
			order.setInputPersonId(sessionLocal.getUserId());
			order.setInputPerson(sessionLocal.getRealName());
			order.setCustomerType(CustomerTypeEnum.ENTERPRISE.code());
			logger.info("添加企业客户,order={}",order);
			FcsBaseResult result = customerServiceClient.add(order);
			synWatchList(order);
			verifyMsgSave(request, order.getCustomerName() + result.getKeyId());
			return toJSONResult(result, "企业客户添加成功", null);
		} catch (Exception e) {
			logger.error("企业客户添加失败 ",e);
			return toJSONResult(new JSONObject(), AppResultCodeEnum.FAILED, e.getMessage());
		}
		
	}

	@ResponseBody
	@RequestMapping("update.json")
	public JSONObject update(HttpServletRequest request, ListOrder listOrder, Model model) {
		
		try {
			CustomerDetailOrder order = new CustomerDetailOrder();
			WebUtil.setPoPropertyByRequest(order, request);
			
			if(StringUtil.isNotBlank(order.getCustomerId())){
				CompanyCustomerInfo info = companyCustomerClient
						.queryById(order.getCustomerId(),null);
				if(StringUtil.notEquals(info.getCustomerName(), order.getCustomerName())
						|| StringUtil.notEquals(info.getBusiLicenseNo(), order.getBusiLicenseNo())){
					//校验客户是否存在
					checkExist(CustomerTypeEnum.ENTERPRISE, order.getBusiLicenseNo(), order.getCustomerName());
				}
			}else{
				//校验客户是否存在
				checkExist(CustomerTypeEnum.ENTERPRISE, order.getBusiLicenseNo(), order.getCustomerName());
			}
			
			if (ListUtil.isNotEmpty(listOrder.getCompanyQualification())) {
				order.setCompanyQualification(listOrder.getCompanyQualification());
			}
			if (ListUtil.isNotEmpty(listOrder.getCompanyOwnershipStructure())) {
				order.setCompanyOwnershipStructure(listOrder.getCompanyOwnershipStructure());
			}
			order.setIsOneCert(checkBoolean(order.getIsOneCert())?BooleanEnum.IS.code():BooleanEnum.NO.code());
			setRelation(request, order);
			order.setCustomerType(CustomerTypeEnum.ENTERPRISE.code());
			FcsBaseResult result = customerServiceClient.updateByUserId(order);
			synWatchList(order);
			verifyMsgSave(request, order.getCustomerName() + order.getUserId());
			return toJSONResult(result, "企业客户更新成功", null);
		} catch (Exception e) {
			logger.error("客户信息更新失败", e);
			return toJSONResult(new JSONObject(), AppResultCodeEnum.FAILED, e.getMessage());
		}
		
	}

	
	/** 被监控名录同步接口 */
	public BornSynResultBase synWatchList(CustomerBaseOrder customerBaseOrder) {
		try {
			SynWatchListOrder order = new SynWatchListOrder();
			List<WatchListInfo> list = new ArrayList<>();
			WatchListInfo info = new WatchListInfo();
			info.setCustomName(customerBaseOrder.getCustomerName());
			info.setOperator(ShiroSessionUtils.getSessionLocal().getUserName());
			if (CustomerTypeEnum.PERSIONAL.code().equals(customerBaseOrder.getCustomerType())) {
				info.setUserType(UserTypeEnum.PERSONAL);
				info.setLicenseNo(customerBaseOrder.getCertNo());
			} else {
				info.setUserType(UserTypeEnum.BUSINESS);
				info.setLicenseNo(customerBaseOrder.getBusiLicenseNo());
			}
			list.add(info);
			order.setList(list);
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			BornSynResultBase result = riskSystemFacadeApi.synWatchList(order);
			logger.info("被监控名录同步接口：同步客户={},result={}", info, result);
		} catch (Exception e) {
			logger.info("被监控名录同步接口异常：", e);
			
		}
		return null;
		
	}
	
	/** 风险验证消息保存 */
	public void verifyMsgSave(HttpServletRequest request, String errorKey) {
		VerifyMessageOrder order = new VerifyMessageOrder();
		try {
			String[] messages = request.getParameterValues("messageInfo");
			if(messages!=null){
				String message = "";
				int i = 1;
				for (String s : messages) {
					if (i == 1) {
						message += s;
						i++;
					} else {
						message += "&" + s;
					}
					
				}
				order.setCardMessage(message);
				order.setErrorKey(errorKey);
				FcsBaseResult result = verifyMessageSaveClient.save(order);
				logger.info("风险验证消息保存结果：order={},result={}", order, result);
			}else{
				logger.info("未找到风险消息不进行保存");
			}

		} catch (Exception e) {
			logger.info("风险验证消息保存结果：order={},e:", order, e);
		}
	}
	
	/** 删除个人客户 **/
	@ResponseBody
	@RequestMapping("delete.json")
	public JSONObject delete(Long userId, Model model) {
		FcsBaseResult result = customerServiceClient.delete(userId);
		return toJSONResult(result, "客户删除成功", null);
		
	}
	
	private void setRelation(HttpServletRequest request, CustomerDetailOrder order) {
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
