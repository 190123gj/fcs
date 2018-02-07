package com.born.fcs.face.web.controller.project.council;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionInstitutionInfo;
import com.born.fcs.pm.ws.info.council.CouncilTypeInfo;
import com.born.fcs.pm.ws.order.council.CouncilApplyQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilTypeOrder;
import com.born.fcs.pm.ws.order.council.CouncilTypeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("projectMg/meetingMg")
public class CouncilTypeController extends BaseController {
	
	final static String vm_path = "/projectMg/assistSys/meetingMg/";
	
	/**
	 * 会议类型列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("councilTypeList.htm")
	public String councilTypeList(CouncilTypeQueryOrder order, Model model) {
		if (StringUtil.isEmpty(order.getSortCol())) {
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
		}
		
		List<String> councilTypeCodes = new ArrayList<>();
		List<String> companyNames = new ArrayList<>();
		if (isRiskSecretary()) {
			councilTypeCodes.add(CouncilTypeEnum.PROJECT_REVIEW.code());
			councilTypeCodes.add(CouncilTypeEnum.RISK_HANDLE.code());
			companyNames.add(CompanyNameEnum.NORMAL.code());
		}
		if (isManagerSecretary()) {
			councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
			companyNames.add(CompanyNameEnum.NORMAL.code());
		}
		if (isManagerSecretaryXH()) {
			councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
			companyNames.add(CompanyNameEnum.XINHUI.code());
		}
		order.setCompanyNames(companyNames);
		order.setCouncilTypeCodes(councilTypeCodes);
		QueryBaseBatchResult<CouncilTypeInfo> batchResult = councilTypeServiceClient.query(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		return vm_path + "councilTypeList.vm";
	}
	
	/**
	 * 去新增会议类型
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("toAddCouncilType.htm")
	public String toAddCouncil(CouncilApplyQueryOrder order, Model model) {
		return vm_path + "addCouncilType.vm";
	}
	
	/**
	 * 保存新增会议类型
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveCouncilType.htm")
	@ResponseBody
	public JSONObject saveCouncilType(HttpServletRequest request, HttpServletResponse response,
										CouncilTypeOrder order, Model model) {
		String tipPrefix = " 保存新增会议类型";
		JSONObject result = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			setSessionLocalInfo2Order(order);
			if (isManagerSecretaryXH()) {
				order.setCompanyName(CompanyNameEnum.XINHUI);
			}
			if (isManagerSecretary()) {
				order.setCompanyName(CompanyNameEnum.NORMAL);
			}
			
			FcsBaseResult saveResult = councilTypeServiceClient.save(order);
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
	 * 查看会议类型详情
	 * 
	 * @param companyId
	 * @param model
	 * @return
	 */
	@RequestMapping("viewCouncilType.htm")
	public String viewDecision(Long id, Model model) {
		String tipPrefix = "查看编辑会议类型详情";
		try {
			// 添加为空的判断，用于兼容脏数据
			CouncilTypeInfo councilTypeInfo = councilTypeServiceClient.findById(id);
			if (councilTypeInfo != null && councilTypeInfo.getTypeId() > 0) {
				
				model.addAttribute("conditions", councilTypeInfo);
				DecisionInstitutionInfo decisionInfo = decisionInstitutionServiceClient
					.findById(councilTypeInfo.getDecisionInstitutionId());
				if (decisionInfo != null && decisionInfo.getInstitutionId() > 0) {
					
					model.addAttribute("institutionMembers", decisionInfo.getInstitutionMembers());
					if (StringUtil.isNotBlank(decisionInfo.getInstitutionMembers())) {
						
						String[] menbers = decisionInfo.getInstitutionMembers().split(",");
						model.addAttribute("decisionMembersSize", menbers.length);
					}
				}
			}
			
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "councilTypeView.vm";
	}
	
	/**
	 * 编辑会议类型
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("editCouncilType.htm")
	public String editCouncilType(Long id, Model model) {
		String tipPrefix = " 编辑会议类型";
		try {
			// 添加为空的判断，用于兼容脏数据
			CouncilTypeInfo councilTypeInfo = councilTypeServiceClient.findById(id);
			if (councilTypeInfo != null && councilTypeInfo.getTypeId() > 0) {
				model.addAttribute("conditions", councilTypeInfo);
				
				DecisionInstitutionInfo decisionInfo = decisionInstitutionServiceClient
					.findById(councilTypeInfo.getDecisionInstitutionId());
				if (decisionInfo != null && decisionInfo.getInstitutionId() > 0) {
					model.addAttribute("institutionMembers", decisionInfo.getInstitutionMembers());
					if (StringUtil.isNotBlank(decisionInfo.getInstitutionMembers())) {
						
						String[] menbers = decisionInfo.getInstitutionMembers().split(",");
						model.addAttribute("decisionMembersSize", menbers.length);
					}
				}
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return vm_path + "addCouncilType.vm";
	}
	
	/**
	 * 删除会议类型
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("delete.htm")
	@ResponseBody
	public JSONObject deleteCouncilType(Long typeId, Model model) {
		JSONObject result = new JSONObject();
		if (typeId == null) {
			result.put("false", false);
			result.put("message", "会议类型不存在");
		} else {
			int num = councilTypeServiceClient.deleteById(typeId);
			if (num > 0) {
				result.put("success", true);
				result.put("message", "删除成功");
			} else {
				result.put("false", false);
				result.put("message", "删除失败,该会议类型已被使用!");
			}
		}
		return result;
	}
	
	/**
	 * 新增编辑时检查会议名称是否重名
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("checkCouncilName.htm")
	@ResponseBody
	public JSONObject checkCouncilName(String typeName, String typeCode, Model model) {
		String tipPrefix = " 决策机构是否重名";
		JSONObject result = new JSONObject();
		try {
			CouncilTypeInfo councilTypeInfo = councilTypeServiceClient.findCouncilTypeByTypeName(
				typeName, typeCode);
			if (councilTypeInfo != null) {
				result.put("success", true);
				result.put("message", "会议名称已存在");
			} else {
				result.put("success", false);
				result.put("message", "会议名称可用");
			}
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
}
