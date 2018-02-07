package com.born.fcs.face.web.controller.project.basicmaintain;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.pm.ws.info.basicmaintain.DecisionMemberInfo;

@Controller
@RequestMapping("projectMg/basicmaintain/decision")
public class DecisionMemberController extends BaseController {
	
	final static String vm_path = "/projectMg/basicmaintain/decision/";
	
	/**
	 * 决策人员列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("memberList.htm")
	public String decisionMemberList(String userName, Model model) {
		
		DecisionMemberInfo memberInfo = decisionMemberServiceClient
			.findDecisionMemberByUserName(userName);
		
		model.addAttribute("conditions", userName);
		model.addAttribute("memberInfo", memberInfo);
		
		return vm_path + "decision_add.vm";
	}
	
}
