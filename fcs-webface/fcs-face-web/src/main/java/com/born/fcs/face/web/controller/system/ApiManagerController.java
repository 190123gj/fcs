package com.born.fcs.face.web.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.bornsoft.facade.api.report.OutApiRequestLogFacade;
import com.bornsoft.facade.info.OutApiRequestLogInfo;
import com.bornsoft.facade.info.OutApiRequestLogReportInfo;
import com.bornsoft.facade.info.SmsInfo;
import com.bornsoft.facade.order.OutApiRequestLogQueryOrder;
import com.bornsoft.facade.order.SmsSendQueryOrder;
import com.bornsoft.facade.result.OutApiRequestLogQueryResult;
import com.bornsoft.facade.result.OutApiRequestLogReportResult;
import com.bornsoft.facade.result.SmsSendQueryResult;
import com.yjf.common.lang.util.StringUtil;

/**
 *
 * @Description 站内消息管理
 *
 */
@Controller
@RequestMapping("/systemMg/apiManager")
public class ApiManagerController extends BaseController {
	
	private final String vm_path = "/systemMg/api/";
	
	@Autowired
	private OutApiRequestLogFacade outApiRequestLogFacade;
	
	/**
	 * 接口调用列表
	 * @param queryOrder
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("invokeList.htm")
	public String messageList(OutApiRequestLogQueryOrder queryOrder, HttpServletRequest request, Model model) {
		queryOrder.setPageNum(Integer.valueOf(StringUtil.defaultIfBlank(request.getParameter("pageNumber"), "1")));
		if(StringUtil.isNotBlank(queryOrder.getStartTime())){
			queryOrder.setStartTime(queryOrder.getStartTime()+" 00:00:00");
		}
		if(StringUtil.isNotBlank(queryOrder.getEndTime())){
			queryOrder.setEndTime(queryOrder.getEndTime()+" 23:59:59");
		}
		OutApiRequestLogQueryResult result = outApiRequestLogFacade.queryOutApiInvokeLogList(queryOrder);
		QueryBaseBatchResult<OutApiRequestLogInfo> page = new  QueryBaseBatchResult<OutApiRequestLogInfo>();
		page.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		page.setMessage("");
		page.setPageCount(result.getPageCount());
		page.setPageList(result.getDataList());
		page.setPageNumber(result.getPageNum());
		page.setPageSize(result.getPageSize());
		page.setTotalCount(result.getTotalCount());
		page.setSuccess(true);
		model.addAttribute("page", PageUtil.getCovertPage(page));
		model.addAttribute("queryOrder", queryOrder);
		return vm_path + "invokeList.vm";
	}
	
	/**
	 * 外部接口统计报表
	 * @param queryOrder
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("invokeReport.htm")
	public String invokeReport(OutApiRequestLogQueryOrder queryOrder, HttpServletRequest request, Model model) {
		OutApiRequestLogReportResult result = outApiRequestLogFacade.outApiInvokeLogReport(queryOrder);
		queryOrder.setPageNum(Integer.valueOf(StringUtil.defaultIfBlank(request.getParameter("pageNumber"), "1")));
		if(StringUtil.isNotBlank(queryOrder.getStartTime())){
			queryOrder.setStartTime(queryOrder.getStartTime()+" 00:00:00");
		}
		if(StringUtil.isNotBlank(queryOrder.getEndTime())){
			queryOrder.setEndTime(queryOrder.getEndTime()+" 23:59:59");
		}
		QueryBaseBatchResult<OutApiRequestLogReportInfo> page = new  QueryBaseBatchResult<OutApiRequestLogReportInfo>();
		page.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		page.setMessage("");
		page.setPageCount(1);
		page.setPageList(result.getDataList());
		page.setPageNumber(1);
		page.setPageSize(result.getDataList().size());
		page.setTotalCount(result.getDataList().size());
		page.setSuccess(true);
		model.addAttribute("page", PageUtil.getCovertPage(page));
		model.addAttribute("queryOrder", queryOrder);
		return vm_path + "invokeReport.vm";
	}
	
	
	/**
	 * 短信调用记录
	 * @param queryOrder
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("smsSendRecordQuery.htm")
	public String smsSendRecordQuery(SmsSendQueryOrder queryOrder, HttpServletRequest request, Model model) {
		queryOrder.setPageNum(Integer.valueOf(StringUtil.defaultIfBlank(request.getParameter("pageNumber"), "1")));
		if(StringUtil.isNotBlank(queryOrder.getStartTime())){
			queryOrder.setStartTime(queryOrder.getStartTime()+" 00:00:00");
		}
		if(StringUtil.isNotBlank(queryOrder.getEndTime())){
			queryOrder.setEndTime(queryOrder.getEndTime()+" 23:59:59");
		}
		SmsSendQueryResult result = outApiRequestLogFacade.smsSendQueryPage(queryOrder);
		QueryBaseBatchResult<SmsInfo> page = new  QueryBaseBatchResult<SmsInfo>();
		page.setFcsResultEnum(FcsResultEnum.EXECUTE_SUCCESS);
		page.setMessage("");
		page.setPageCount(result.getPageCount());
		page.setPageList(result.getDataList());
		page.setPageNumber(result.getPageNum());
		page.setPageSize(result.getPageSize());
		page.setTotalCount(result.getTotalCount());
		page.setSuccess(true);
		model.addAttribute("page", PageUtil.getCovertPage(page));
		model.addAttribute("queryOrder", queryOrder);
		return vm_path + "smsSendList.vm";
	}
	
}
