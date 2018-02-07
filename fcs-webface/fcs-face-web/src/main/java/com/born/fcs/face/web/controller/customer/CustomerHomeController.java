package com.born.fcs.face.web.controller.customer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.crm.ws.service.enums.ChangeTypeEnum;
import com.born.fcs.crm.ws.service.info.ChannalInfo;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.info.CustomerDetailInfo;
import com.born.fcs.crm.ws.service.order.CustomerDetailOrder;
import com.born.fcs.crm.ws.service.order.DistributionOrder;
import com.born.fcs.crm.ws.service.order.ListOrder;
import com.born.fcs.crm.ws.service.order.TransferAllocationOrder;
import com.born.fcs.crm.ws.service.order.query.ChannalQueryOrder;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("customerMg")
public class CustomerHomeController extends BaseController {
	
	final static String vm_path = "/customerMg/";
	
	@RequestMapping("index.htm")
	public String mainIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		return vm_path + "index.vm";
	}
	
	//	@RequestMapping("customer/list.htm")
	//	public String customerList(CustomerQueryOrder queryOrder, Long orgId, Model model) {
	//		
	//		if (orgId != null) {
	//			List<Long> deptIdList = new ArrayList<>();
	//			deptIdList.add(orgId);
	//			queryOrder.setDeptIdList(deptIdList);
	//		}
	//		QueryBaseBatchResult<CustomerBaseInfo> batchResult = customerServiceClient.list(queryOrder);
	//		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
	//		model.addAttribute("queryOrder", queryOrder);
	//		model.addAttribute("orgId", orgId);
	//		model.addAttribute("depQuery", true);
	//		setCustomerEnums(model);
	//		
	//		if (CustomerTypeEnum.ENTERPRISE.code().equals(queryOrder.getCustomerType())) {
	//			return "/customerMg/customer/company/list.vm";
	//		} else {
	//			return "/customerMg/customer/personal/list.vm";
	//		}
	//		
	//	}
	
	/** 客户信息签报申请 */
	@RequestMapping("customer/changeApply.htm")
	public String applyChangeUserInfo(Long customerId, HttpServletRequest request,
										HttpSession session, Model model) {
		
		FormCheckCanChangeOrder checkOrder = new FormCheckCanChangeOrder();
		checkOrder.setCustomerId(customerId);
		checkOrder.setChangeForm(FormChangeApplyEnum.CUSTOMER_DATA);
		checkCanApplyChange(checkOrder);
		setFormChangeApplyInfo(request, session, model);
		CustomerDetailInfo info = customerServiceClient.queryByUserId(customerId);
		//签报允许修改
		info.setProjectStatus(BooleanEnum.NO.code());
		model.addAttribute("customerBaseInfo", info);
		if (info != null && StringUtil.isNotBlank(info.getChannalType())) {
			ChannalQueryOrder queryChannalOrder = new ChannalQueryOrder();
			queryChannalOrder.setChannelType(info.getChannalType());
			queryChannalOrder.setStatus("on");
			QueryBaseBatchResult<ChannalInfo> channalList = channalClient.list(queryChannalOrder);
			model.addAttribute("channalList", channalList);
		}
		if (ListUtil.isNotEmpty(info.getReqList())) {
			model.addAttribute("companyInfos", info.getReqList());
		}
		setCustomerEnums(model);
		model.addAttribute("changeType", ChangeTypeEnum.QB);
		if (StringUtil.equals(info.getCustomerType(), CustomerTypeEnum.ENTERPRISE.code())) {
			return vm_path + "customer/company/changeApply.vm";
			
		} else {
			return vm_path + "customer/personal/changeApply.vm";
			
		}
	}
	
	/** 客户信息签报成功修改提交 */
	@ResponseBody
	@RequestMapping("customer/change/submit.json")
	public JSONObject changeSubmit(HttpServletRequest request, ListOrder listOrder, Model model) {
		CustomerDetailOrder customerDetailOrder = new CustomerDetailOrder();
		WebUtil.setPoPropertyByRequest(customerDetailOrder, request);
		if (listOrder != null) {
			if (ListUtil.isNotEmpty(listOrder.getReqList()))
				customerDetailOrder.setReqList(listOrder.getReqList());
			if (ListUtil.isNotEmpty(listOrder.getCompanyQualification()))
				customerDetailOrder.setCompanyQualification(listOrder.getCompanyQualification());
			if (ListUtil.isNotEmpty(listOrder.getCompanyOwnershipStructure()))
				customerDetailOrder.setCompanyOwnershipStructure(listOrder
					.getCompanyOwnershipStructure());
		}
		String changeType = request.getParameter("changeType");
		if (StringUtil.isNotBlank(changeType)) {
			customerDetailOrder.setChangeType(ChangeTypeEnum.getByCode(changeType));
		} else {
			customerDetailOrder.setChangeType(ChangeTypeEnum.QB);
		}
		setRelation(request, customerDetailOrder);
		//签报完成后状态改成已上会
		customerDetailOrder.setProjectStatus(BooleanEnum.IS.code());
		FcsBaseResult result = customerServiceClient.updateByUserId(customerDetailOrder);
		return toJSONResult(result, "客户更新成功", null);
		
	}
	
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, CustomerQueryOrder queryOrder, Model model) {
		QueryBaseBatchResult<CustomerBaseInfo> batchResult = personalCustomerClient
			.list(queryOrder);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("queryOrder", queryOrder);
		return vm_path + "/customer/personal/list.vm";
	}
	
	/** 分配客户 ：管理员 业务总监 **/
	@ResponseBody
	@RequestMapping("customer/distribution.json")
	public JSONObject distribution(HttpServletRequest request) {
		TransferAllocationOrder transferAllocationOrder = new TransferAllocationOrder();
		WebUtil.setPoPropertyByRequest(transferAllocationOrder, request);
		transferAllocationOrder.setType("FP");
		return transferAllocation(transferAllocationOrder, "分配");
		
		//		return distributionOrTransfer(distributionOrder, "分配");
		
	}
	
	/**
	 * 移交客户 ：客户经理
	 * **/
	@ResponseBody
	@RequestMapping("customer/transfer.json")
	public JSONObject transfer(HttpServletRequest request) {
		TransferAllocationOrder transferAllocationOrder = new TransferAllocationOrder();
		WebUtil.setPoPropertyByRequest(transferAllocationOrder, request);
		transferAllocationOrder.setType("YJ");
		return transferAllocation(transferAllocationOrder, "移交");
		//		return distributionOrTransfer(distributionOrder, "移交");
	}
	
	/** 移交分配 新 */
	private JSONObject transferAllocation(TransferAllocationOrder transferAllocationOrder,
											String mes) {
		logger.info("客户" + mes + "请求参数:{}", transferAllocationOrder);
		JSONObject josn = new JSONObject();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		
		if (transferAllocationOrder != null) {
			if (sessionLocal != null) {
				transferAllocationOrder.setOperId(sessionLocal.getUserId());
				transferAllocationOrder.setOperName(sessionLocal.getUserName());
			}
			
			//业务总监只能分配给自己部门的人
			if ("分配".equals(mes)
				&& (DataPermissionUtil.isBusiFZR() || DataPermissionUtil.isXinHuiFzr())) {
				String operDepPath = sessionLocal.getUserInfo().getPrimaryOrg().getPath();
				if (StringUtil.isNotBlank(transferAllocationOrder.getDepPath())
					&& transferAllocationOrder.getDepPath().indexOf(operDepPath) == -1) {
					josn.put("success", false);
					josn.put("message", "只能分配给本部门的人");
					return josn;
				}
			}
			
			UserDetailInfo userDetail = null;
			if (transferAllocationOrder.getCustomerManagerId() > 0) {
				logger.info("客户" + mes + "查询客户经理信息id={}",
					transferAllocationOrder.getCustomerManagerId());
				userDetail = bpmUserQueryService.findUserDetailByUserId(transferAllocationOrder
					.getCustomerManagerId());
			} else if (transferAllocationOrder.getDirectorId() > 0) {
				logger.info("客户" + mes + "查询总监信息id={}", transferAllocationOrder.getDirectorId());
				userDetail = bpmUserQueryService.findUserDetailByUserId(transferAllocationOrder
					.getDirectorId());
			}
			if (userDetail != null && userDetail.getPrimaryOrg() != null) {
				transferAllocationOrder.setDepName(userDetail.getPrimaryOrg().getName());
				transferAllocationOrder.setDepId(userDetail.getPrimaryOrg().getId());
				transferAllocationOrder.setDepPath(userDetail.getPrimaryOrg().getPath());
				logger.info("客户" + mes + "调用crm接口{}", transferAllocationOrder);
				FcsBaseResult result = customerServiceClient
					.transferAllocation(transferAllocationOrder);
				if (result.isSuccess()) {
					josn.put("success", true);
					josn.put("message", mes + "成功");
				} else {
					josn.put("success", false);
					josn.put("message", StringUtil.defaultIfBlank(result.getMessage(), mes + "失败"));
				}
			} else {
				josn.put("success", false);
				josn.put("message", "客户部门编号不能为空");
			}
			
		} else {
			josn.put("success", false);
			josn.put("message", mes + "失败，数据不能为空");
		}
		logger.info("客户" + mes + "结束result={}", josn.toJSONString());
		return josn;
		
	}
	
	/**
	 * @param userId 接收人Id
	 * @param depId 客户部门
	 * @param provinceCode 客户所属省份
	 * */
	@ResponseBody
	@RequestMapping("customer/userRole.json")
	public JSONObject transfer(long userId, Long depId, String provinceCode) {
		JSONObject json = new JSONObject();
		String thisRole = "";
		String message = "";
		boolean result = false;
		UserDetailInfo userDetail = bpmUserQueryService.findUserDetailByUserId(userId);
		boolean coperAll = true;
		if (DataPermissionUtil.isBusiFZR(userDetail)
			|| DataPermissionUtil.isBusinessFgfz(userDetail)) {
			coperAll = false;
		}
		json = validataRegion(userDetail.getPrimaryOrg().getPath(), provinceCode, coperAll);
		if (DataPermissionUtil.isBusiMenber(userId) && !json.getBooleanValue("success")) {
			return json;
		}
		if (DataPermissionUtil.isBusiFZR(userDetail) || DataPermissionUtil.isXinHuiFzr(userDetail)) {
			thisRole = "director";
			result = true;
			message = "选择的是业务总监！";
			if (DataPermissionUtil.isBusiFZR()) {
				result = false;
				message = "角色不符合！";
			}
		} else if (DataPermissionUtil.isBusiManager(userDetail)
					|| DataPermissionUtil.isXinHuiBusiManager(userId)) {
			thisRole = "khjl";
			result = true;
			message = "选择的是客户经理！";
			//移交 判断部门
			if (userDetail != null && depId != null) {
				Org org = bpmUserQueryService.findDeptByOrgId(depId);
				if (!userDetail.isBelong2Dept(org.getId())) {
					result = false;
					message = "不是同一个部门！！";//选择的客户经理不是同一个部门！
				}
			}
			if (DataPermissionUtil.isSystemAdministrator()) {
				result = false;
				message = "系统管理员不能直接分配公海客户给客户经理！";//
			}
			if ((DataPermissionUtil.isBusiFZR() && DataPermissionUtil.isBusinessFgfz(userDetail))
				|| (DataPermissionUtil.isXinHuiFzr() && DataPermissionUtil.isXinHuiFGFZ(userDetail))) {
				result = false;
				message = "总监不能分配给分管副总！";//
			}
			
		} else {
			message = "角色不符合！";
		}
		
		json.put("role", thisRole);
		json.put("success", result);
		json.put("message", message);
		return json;
	}
	
	/** 渠道选择 */
	@ResponseBody
	@RequestMapping("customer/selectChannal.json")
	public JSONObject selectChannal(ChannalQueryOrder queryOrder) {
		JSONObject json = new JSONObject();
		if (StringUtil.isBlank(queryOrder.getStatus())) {
			queryOrder.setStatus("on");
		}
		QueryBaseBatchResult<ChannalInfo> result = channalClient.list(queryOrder);
		
		if (result != null && result.isSuccess()) {
			json.put("list", result.getPageList());
			json.put("success", true);
			json.put("message", "渠道查询成功");
		} else {
			json.put("success", false);
			json.put("message", "渠道查询失败");
		}
		return json;
	}
	
	private JSONObject distributionOrTransfer(DistributionOrder distributionOrder, String mes) {
		JSONObject josn = new JSONObject();
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		//业务总监只能分配给自己部门的人
		//		if ("分配".equals(mes) && DataPermissionUtil.isBusiFZR()) {
		//			if (sessionLocal.getUserInfo().getPrimaryOrg().getOrgId() != distributionOrder
		//				.getDepId()) {
		//				josn.put("success", false);
		//				josn.put("message", "只能分配给本部门的人");
		//				return josn;
		//			}
		//		}
		
		UserDetailInfo userDetail = null;
		if (distributionOrder.getCustomerManagerId() > 0) {
			userDetail = bpmUserQueryService.findUserDetailByUserId(distributionOrder
				.getCustomerManagerId());
		} else if (distributionOrder.getDirectorId() > 0) {
			userDetail = bpmUserQueryService.findUserDetailByUserId(distributionOrder
				.getDirectorId());
		}
		if (userDetail != null && userDetail.getPrimaryOrg() != null) {
			distributionOrder.setDepPath(userDetail.getPrimaryOrg().getPath());
		}
		if ("分配".equals(mes)
			&& (DataPermissionUtil.isBusiFZR() || DataPermissionUtil.isXinHuiFzr())) {
			String operDepPath = sessionLocal.getUserInfo().getPrimaryOrg().getPath();
			if (StringUtil.isNotBlank(distributionOrder.getDepPath())
				&& distributionOrder.getDepPath().indexOf(operDepPath) == -1) {
				josn.put("success", false);
				josn.put("message", "只能分配给本部门的人");
				return josn;
			}
		}
		
		FcsBaseResult result = personalCustomerClient.distribution(distributionOrder);
		if (result.isSuccess()) {
			josn.put("success", true);
			josn.put("message", mes + "成功");
		} else {
			josn.put("success", false);
			josn.put("message", StringUtil.defaultIfBlank(result.getMessage(), mes + "失败"));
		}
		return josn;
	}
	
	/** 将关系拼成一个字段 */
	protected void setRelation(HttpServletRequest request, CustomerDetailOrder order) {
		String[] ralations = request.getParameterValues("relation");
		if (ralations != null && ralations.length > 0) {
			String ralation = ralations[0];
			for (int i = 1; i < ralations.length; i++) {
				ralation += "," + ralations[i];
			}
			order.setRelation(ralation);
		}
		
	}
	
	private JSONObject validataRegion(String depPath, String provinceCode, boolean coperAll) {
		logger.info("移交分配业务区域判断入参：depPath={},provinceCode={}", depPath, provinceCode);
		JSONObject json = new JSONObject();
		if (StringUtil.isBlank(provinceCode)) {
			json.put("success", true);
			json.put("message", "无限制！");
		} else {
			//区域启用状态
			FcsBaseResult result = busyRegionClient.queryStatus(null);
			if (result.isSuccess()) {
				if (StringUtil.isBlank(depPath)) {
					json.put("success", false);
					json.put("message", "接收人部门不能为空");
				} else {
					Map<String, String> map = busyRegionClient.busyRegMap(depPath, coperAll);
					if (map != null && (map.containsKey(provinceCode) || map.containsKey("China"))) {
						//包含该区域
						json.put("success", true);
						json.put("message", "包含该区域！");
					} else {
						json.put("success", false);
						json.put("message", "客户不属于该部门的业务区域！");
					}
				}
			} else {
				//区域限制未启用
				json.put("success", true);
				json.put("message", "未启用区域限制！");
			}
			
		}
		logger.info("移交分配业务区域判断出参：json={}", json);
		return json;
	}
}
