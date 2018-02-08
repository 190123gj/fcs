package com.bornsoft.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 系统异常 | 成功页 | 同步跳转   | 异步跳转
 * @author:      xiaohui@yiji.com
 * @date         2016-1-18 下午1:36:30
 * @version:     v1.0
 */
@Controller
@RequestMapping("system")
public class SystemCommonController  {

	/**
	 * 跳转公共的失败页
	 * @param orderNo
	 * @param description
	 * @return
	 */
	@RequestMapping("failure")
	public ModelAndView execute(String orderNo, String description) {
		ModelAndView mav = new ModelAndView("common_failure.vm");
		mav.addObject("orderNo", orderNo).addObject("description", description);
		return mav;
	}
	
	/**
	 * 跳转公共的成功页
	 * @param orderNo
	 * @param description
	 * @return
	 */
	@RequestMapping("success")
	public ModelAndView success(String orderNo, String description) {
		ModelAndView mav = new ModelAndView("common_success.vm");
		mav.addObject("resultMessage", description);
		return mav;
	}
	
}
