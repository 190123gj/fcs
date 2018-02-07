package com.born.fcs.face.web.controller.project.basicmaintain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysUser;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionInstitutionInfo;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionMemberInfo;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionInstitutionOrder;
import com.born.fcs.pm.ws.order.basicmaintain.DecisionInstitutionQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("projectMg/assistSys/basicDataMg")
public class DecisionInstitutionController extends BaseController {
	
	final static String vm_path = "/projectMg/assistSys/basicDataMg/";
	
	@Autowired
	private SysParameterService sysParameterService;
	
	/**
	 * 决策机构列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("decisionList.htm")
	public String decisionList(DecisionInstitutionQueryOrder order, Model model) {
		QueryBaseBatchResult<DecisionInstitutionInfo> batchResult = decisionInstitutionServiceClient
			.query(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		return vm_path + "decisionList.vm";
	}
	
	/**
	 * 去新增决策机构
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("decisionAdd.htm")
	public String addDecision(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<DecisionMemberInfo> listDecisionMemberInfo = new ArrayList<DecisionMemberInfo>();
		model.addAttribute("listDecisionMemberInfo", listDecisionMemberInfo);
		return vm_path + "decisionAdd.vm";
	}
	
	/**
	 * 保存决策机构
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveDecision.htm")
	@ResponseBody
	public JSONObject saveDecisiom(String institutionId, String institutionName, String userName,
									String userId, Model model) {
		String tipPrefix = " 保存决策机构";
		JSONObject result = new JSONObject();
		try {
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				result.put("success", false);
				result.put("message", "您未登陆或登陆已失效");
				return result;
			}
			List<DecisionMemberInfo> listMemberInfo = new ArrayList<DecisionMemberInfo>();
			DecisionInstitutionOrder order = new DecisionInstitutionOrder();
			if (institutionId != null) {
				order.setInstitutionId(Long.parseLong(institutionId));
			}
			order.setInstitutionName(institutionName);
			order.setInstitutionMembers(userName);
			if (userName != null && userId != null) {//编辑时
				String[] userIdArr = userId.split(",");
				String[] userNameArr = userName.split(",");
				
				// 添加判定，如果存在风控委秘书或者总经理秘书，不允许添加【暂时屏蔽，看情况打开】
				
				List<String> userIds = Arrays.asList(userIdArr);
				// 获取并判定风控委秘书是否在选项中
				if (getRuleCheck(userIds, SysParamEnum.SYS_PARAM_RISK_SECRETARY_ROLE_NAME.code())) {
					result.put("success", false);
					result.put("message", "您选择了风控委秘书作为决策人员，请移除！");
					return result;
				}
				// 获取并判定总经理秘书是否在选项中
				if (getRuleCheck(userIds, SysParamEnum.SYS_PARAM_MANAGER_SECRETARY_ROLE_NAME.code())) {
					result.put("success", false);
					result.put("message", "您选择了总经理秘书作为决策人员，请移除！");
					return result;
				}
				
				for (int i = 0; i < userIdArr.length; i++) {
					DecisionMemberInfo memberInfo = new DecisionMemberInfo();
					memberInfo.setUserName(userNameArr[i]);
					memberInfo.setUserId(Long.parseLong(userIdArr[i]));
					listMemberInfo.add(memberInfo);
				}
			}
			
			order.setDecisionMembers(listMemberInfo);
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = decisionInstitutionServiceClient.save(order);
			
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
	
	private boolean getRuleCheck(List<String> userIds, String code) {
		boolean hasRiskSecretary = false;
		String roleName = sysParameterService.getSysParameterValue(code);
		List<SysUser> usersRiskSecretary = bpmUserQueryService.findUserByRoleAlias(roleName);
		for (SysUser sysUser : usersRiskSecretary) {
			if (userIds.contains(String.valueOf(sysUser.getUserId()))) {
				hasRiskSecretary = true;
				break;
			}
		}
		return hasRiskSecretary;
	}
	
	/**
	 * 查看决策机构详情
	 * 
	 * @param companyId
	 * @param model
	 * @return
	 */
	@RequestMapping("viewDecision.htm")
	public String viewDecision(Long id, Model model) {
		String tipPrefix = "查看决策机构详情";
		try {
			DecisionInstitutionInfo decisionInstitutionInfo = decisionInstitutionServiceClient
				.findById(id);
			List<DecisionMemberInfo> listDecisionMemberInfo = decisionMemberServiceClient
				.queryDecisionMemberInfo(id);
			String memberIds = "";
			String userId = "";
			for (DecisionMemberInfo decisionMemberInfo : listDecisionMemberInfo) {
				memberIds += decisionMemberInfo.getMemberId() + ",";
				userId += decisionMemberInfo.getUserId() + ",";
			}
			memberIds = memberIds.substring(0, memberIds.length() - 1);
			model.addAttribute("memberIds", memberIds);
			userId = userId.substring(0, userId.length() - 1);
			model.addAttribute("userId", userId);
			model.addAttribute("conditions", decisionInstitutionInfo);
			
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "decision_view.vm";
	}
	
	/**
	 * 编辑决策机构详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("editDecision.htm")
	public String editDecision(Long id, Model model) {
		String tipPrefix = "编辑决策机构详情";
		try {
			DecisionInstitutionInfo decisionInstitutionInfo = decisionInstitutionServiceClient
				.findById(id);
			List<DecisionMemberInfo> listDecisionMemberInfo = decisionMemberServiceClient
				.queryDecisionMemberInfo(id);
			String memberIds = "";
			String userId = "";
			for (DecisionMemberInfo decisionMemberInfo : listDecisionMemberInfo) {
				memberIds += decisionMemberInfo.getMemberId() + ",";
				userId += decisionMemberInfo.getUserId() + ",";
			}
			memberIds = memberIds.substring(0, memberIds.length() - 1);
			model.addAttribute("memberIds", memberIds);
			userId = userId.substring(0, userId.length() - 1);
			model.addAttribute("userId", userId);
			model.addAttribute("conditions", decisionInstitutionInfo);
			
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "decisionAdd.vm";
	}
	
	/**
	 * 删除决策机构
	 * 
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping("delete.htm")
	@ResponseBody
	public JSONObject deleteDecision(Long institutionId, Model model) {
		JSONObject result = new JSONObject();
		if (institutionId == null) {
			result.put("false", false);
			result.put("message", "决策机构不存在");
		} else {
			int num = decisionInstitutionServiceClient.deleteById(institutionId);
			if (num > 0) {
				result.put("success", true);
				result.put("message", "删除成功");
			} else {
				result.put("success", false);
				result.put("message", "删除失败,该决策机构已被使用!");
			}
		}
		return result;
	}
	
	/**
	 * 新增编辑时检查决策机构是否重名
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("checkInstitutionName.htm")
	@ResponseBody
	public JSONObject checkInstitutionName(String institutionName, Model model) {
		String tipPrefix = " 决策机构是否重名";
		JSONObject result = new JSONObject();
		try {
			DecisionInstitutionInfo institutionInfo = decisionInstitutionServiceClient
				.findDecisionInstitutionByInstitutionName(institutionName);
			if (institutionInfo != null) {
				result.put("success", true);
				result.put("message", "决策机构名称已存在");
			} else {
				result.put("success", false);
				result.put("message", "决策机构名称可用");
			}
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
}
