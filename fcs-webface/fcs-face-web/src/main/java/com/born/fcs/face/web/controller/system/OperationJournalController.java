package com.born.fcs.face.web.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.UserDetailsService;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.order.common.OperationJournalQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.StringUtil;

/**
 * 操作日志controller
 * @author ji
 * 
 */
@Controller
@RequestMapping("systemMg/operationJournal")
public class OperationJournalController extends BaseController {
	
	private final String vm_path = "/systemMg/operationJournal/";
	
	@RequestMapping("list.htm")
	public String list(OperationJournalQueryOrder order, Model model) {
		model.addAttribute("queryOrder", order);
		model.addAttribute("page",
			PageUtil.getCovertPage(operationJournalServiceClient.queryOperationJournalInfo(order)));
		return vm_path + "operationJournalList.vm";
	}
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@RequestMapping("loginLog.htm")
	public String loginLog(QueryPageBase pages, HttpServletRequest request, Model model) {
		
		String loginName = request.getParameter("loginName");
		String loginIp = request.getParameter("loginIp");
		String startLoginDate = request.getParameter("startLoginDate");
		String endLoginDate = request.getParameter("endLoginDate");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pageNumber", pages.getPageNumber());
		jsonObject.put("pageSize", pages.getPageSize());
		if (StringUtil.isNotBlank(loginName))
			jsonObject.put("loginName", loginName);
		if (StringUtil.isNotBlank(loginIp))
			jsonObject.put("loginIp", loginIp);
		if (StringUtil.isNotBlank(startLoginDate))
			jsonObject.put("startLoginDate", startLoginDate);
		if (StringUtil.isNotBlank(endLoginDate))
			jsonObject.put("endLoginDate", endLoginDate);
		
		try {
			String str = userDetailsService.searchLoginLog(jsonObject.toJSONString());
			if (StringUtil.isNotBlank(str)) {
				JSONObject result = JSONObject.parseObject(str);
				model.addAttribute("result", result);
				PageComponent component = new PageComponent(pages,
					result.getLongValue("totalCount"));
				QueryBaseBatchResult<CustomerBaseInfo> pr = new QueryBaseBatchResult<CustomerBaseInfo>();
				pr.initPageParam(component);
				model.addAttribute("page", PageUtil.getCovertPage(pr));
			}
			
		} catch (Exception e) {
			logger.error("查询登陆日志异常：", e);
		}
		
		model.addAttribute("queryOrder", WebUtil.getRequestMap(request));
		return vm_path + "loginLog.vm";
	}
}
