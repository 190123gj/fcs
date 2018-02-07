package com.born.fcs.face.web.controller.project.council;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.info.council.CouncilApplyInfo;
import com.born.fcs.pm.ws.order.council.CouncilApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("projectMg/meetingMg")
public class CouncilApplyController extends BaseController {
	
	final static String vm_path = "/projectMg/assistSys/meetingMg/";
	
	/**
	 * 待上会项目列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("awaitCouncilProjectList.htm")
	public String awaitCouncilProjectList(CouncilApplyQueryOrder order, Model model) {
		
		List<String> companyNames = new ArrayList<String>();
		
		order.setStatus(CouncilApplyStatusEnum.WAIT);
		// 总经理秘书只能看总经理办公会，风控委秘书不能看总经理办公会
		List<String> councilTypeCodes = new ArrayList<String>();
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
		QueryBaseBatchResult<CouncilApplyInfo> batchResult = councilApplyServiceClient
			.queryCouncilApply(order);
		
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		return vm_path + "awaitProjectList.vm";
	}
	
	@RequestMapping("toAddMeeting.htm")
	public String toAddMeeting(CouncilApplyQueryOrder order, Model model) {
		
		QueryBaseBatchResult<CouncilApplyInfo> batchResult = councilApplyServiceClient
			.queryCouncilApply(order);
		
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		return vm_path + "addMeeting.vm";
	}
	
}
