package com.born.fcs.face.web.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.FrontAutowiredBaseController;
import com.born.fcs.pm.ws.order.setup.FProjectCounterGuaranteeOrder;

@Controller
@RequestMapping("/test")
public class TestController extends FrontAutowiredBaseController {
	
	/**
	 * 项目未完全搭建起来前，用于UED同事调试VM页面
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("toVm.htm")
	String toVm(String page, Model model) {
		return "" + page;
	}
	
	@RequestMapping("listParam.htm")
	@ResponseBody
	/**
	 * <form action="http://127.0.0.1:8096/test/listParam.htm" method="post"> 
	
			<input name="guarantorOrder[0].guarantor" value="guarantor0"/>
			<input name="guarantorOrder[0].legalPersion" value="legalPersion0"/>
		
			
			<input name="guarantorOrder[1].guarantor" value="guarantor1"/>
			<input name="guarantorOrder[1].legalPersion" value="legalPersion1"/>
		
			<input type="submit"/>
		</form>
	 * @param order
	 * @return
	 */
	public String testList(FProjectCounterGuaranteeOrder order) {
		logger.info("order : {}", order);
		return JSONObject.toJSONString(order);
	}
	
	@RequestMapping("listParam2.htm")
	@ResponseBody
	/**
	 * <form action="http://127.0.0.1:8096/test/listParam2.htm" method="post"> 
		
			<input name="code" value="code"/>
			<input name="name" value="name"/>
		
			<input name="testOrder1[0].code1" value="code1_0"/>
			<input name="testOrder1[0].name1" value="name1_0"/>
			<input name="testOrder1[1].code1" value="code1_1"/>
			<input name="testOrder1[1].name1" value="name1_1"/>
			
			<input name="testOrder1[0].testOrder2[0].code2" value="code2_0"/>
			<input name="testOrder1[0].testOrder2[0].name2" value="name2_0"/>
			<input name="testOrder1[0].testOrder2[1].code2" value="code2_1"/>
			<input name="testOrder1[0].testOrder2[1].name2" value="name2_1"/>
		
			<input type="submit"/>
		</form>
	 * @param order
	 * @return
	 */
	public String testList2(TestOrder order) {
		logger.info("order : {}", order);
		return JSONObject.toJSONString(order);
	}
}
