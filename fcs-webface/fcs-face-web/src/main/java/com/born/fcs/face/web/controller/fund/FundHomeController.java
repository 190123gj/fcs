package com.born.fcs.face.web.controller.fund;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.fm.ws.enums.ReceiptPaymentFormStatusEnum;
import com.born.fcs.fm.ws.info.common.ReceiptPaymentFormInfo;
import com.born.fcs.fm.ws.info.forecast.AccountAmountDetailInfo;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormQueryOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountQueryOrder;
import com.born.fcs.fm.ws.result.forecast.AccountAmountDetailMainIndexResult;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("fundMg")
public class FundHomeController extends BaseController {
	final static String vm_path = "/fundMg/";
	
	final static String vm_path2 = "/fundMg/fundMgModule/index/";
	
	@RequestMapping("index.htm")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		return vm_path + "index.vm";
	}
	
	@RequestMapping("mainIndex.htm")
	public String mainIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		//buildSystemNameDefaultUrl(request, model);
		//		JSONArray jsons = new JSONArray();
		//		for (ProjectInfo info : result.getProjectInfos()) {
		//			JSONObject json = new JSONObject();
		//			json.put("phases", info.getPhases().code());
		//			json.put("phasesMessage", info.getPhases().message());
		//			json.put("count", info.getCount());
		//			jsons.add(json);
		//		}
		//		model.addAttribute("countJson", jsons.toJSONString().replaceAll("\"", "'"));
		/// 资金预测 
		ForecastAccountQueryOrder forecastQueryOrder = new ForecastAccountQueryOrder();
		forecastQueryOrder.setPageSize(999);
		AccountAmountDetailMainIndexResult forecasts = forecastServiceClient.queryMainIndex();
		if (forecasts != null && forecasts.isSuccess()) {
			// day 
			JSONArray jsons = new JSONArray();
			if (ListUtil.isNotEmpty(forecasts.getDayDetail())) {
				
				for (AccountAmountDetailInfo info : forecasts.getDayDetail()) {
					JSONObject json = new JSONObject();
					json.put("name", info.getDateStr());
					json.put("in", info.getForecastInAmount().divide(10000));
					json.put("out", info.getForecastOutAmount().divide(10000));
					json.put("last", info.getForecastLastAmount().divide(10000));
					jsons.add(json);
				}
				model.addAttribute("dayJson", jsons.toJSONString().replaceAll("\"", "'"));
			}
			// week
			jsons = new JSONArray();
			if (ListUtil.isNotEmpty(forecasts.getWeekDetail())) {
				for (AccountAmountDetailInfo info : forecasts.getWeekDetail()) {
					JSONObject json = new JSONObject();
					json.put("name", info.getDateStr());
					json.put("in", info.getForecastInAmount().divide(10000));
					json.put("out", info.getForecastOutAmount().divide(10000));
					json.put("last", info.getForecastLastAmount().divide(10000));
					jsons.add(json);
				}
				model.addAttribute("weekJson", jsons.toJSONString().replaceAll("\"", "'"));
			}
			
			// month
			jsons = new JSONArray();
			for (AccountAmountDetailInfo info : forecasts.getMonthDetail()) {
				JSONObject json = new JSONObject();
				json.put("name", info.getDateStr());
				json.put("in", info.getForecastInAmount().divide(10000));
				json.put("out", info.getForecastOutAmount().divide(10000));
				json.put("last", info.getForecastLastAmount().divide(10000));
				jsons.add(json);
			}
			model.addAttribute("monthJson", jsons.toJSONString().replaceAll("\"", "'"));
			
			// fourmohth
			
			jsons = new JSONArray();
			for (AccountAmountDetailInfo info : forecasts.getMonthDetail()) {
				JSONObject json = new JSONObject();
				json.put("name", info.getDateStr());
				json.put("in", info.getForecastInAmount().divide(10000));
				json.put("out", info.getForecastOutAmount().divide(10000));
				json.put("last", info.getForecastLastAmount().divide(10000));
				jsons.add(json);
			}
			model.addAttribute("foutMonthJson", jsons.toJSONString().replaceAll("\"", "'"));
			
			//---
			// 主要资金构成明细
			jsons = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("name", "可用资金总计");
			json.put("amount", forecasts.getBankAccountAmount().divide(10000));
			jsons.add(json);
			json = new JSONObject();
			json.put("name", "理财产品");
			json.put("amount", forecasts.getFinancialAmount().divide(10000));
			jsons.add(json);
			json = new JSONObject();
			json.put("name", "股权投资");
			json.put("amount", forecasts.getEquityInvestmentAmount().divide(10000));
			jsons.add(json);
			json = new JSONObject();
			json.put("name", "应收代偿款");
			json.put("amount", forecasts.getCompensatoryAmount().divide(10000));
			jsons.add(json);
			json = new JSONObject();
			json.put("name", "存出保证金");
			json.put("amount", forecasts.getRefundableDepositsAmount().divide(10000));
			jsons.add(json);
			json = new JSONObject();
			json.put("name", "委托贷款");
			json.put("amount", forecasts.getEntrustLoanAmount().divide(10000));
			jsons.add(json);
			model.addAttribute("amountJson", jsons.toJSONString().replaceAll("\"", "'"));
			model.addAttribute("totalAmount", forecasts.getTotalamount().divide(10000));
		}
		
		// 主要资金构成明细
		//		BankMessageQueryOrder bankMessageQueryOrder = new BankMessageQueryOrder();
		//		bankMessageQueryOrder.setAccountType(SubjectAccountTypeEnum.CURRENT);
		//		bankMessageServiceClient.queryBankMessageInfo(bankMessageQueryOrder);
		
		//待处理单据
		receiptPaymentList(new ReceiptPaymentFormQueryOrder(), model);
		
		return vm_path2 + "index.vm";
	}
	
	@RequestMapping("receiptPaymentList.htm")
	public String receiptPaymentList(ReceiptPaymentFormQueryOrder receiptPaymentFormQueryOrder,
										Model model) {
		if (receiptPaymentFormQueryOrder == null)
			receiptPaymentFormQueryOrder = new ReceiptPaymentFormQueryOrder();
		
		if (StringUtil.isEmpty(receiptPaymentFormQueryOrder.getSortCol())) {
			receiptPaymentFormQueryOrder.setSortCol("source_id");
			receiptPaymentFormQueryOrder.setSortOrder("DESC");
		}
		receiptPaymentFormQueryOrder.setStatus(ReceiptPaymentFormStatusEnum.NOT_PROCESS);
		QueryBaseBatchResult<ReceiptPaymentFormInfo> batchResult = receiptPaymentFormServiceClient
			.query(receiptPaymentFormQueryOrder);
		if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		}
		
		model.addAttribute("isCWYSG", DataPermissionUtil.isCWYSG());
		model.addAttribute("isCWYFG", DataPermissionUtil.isCWYFG());
		
		return vm_path2 + "receiptPaymentList.vm";
	}
	
	private class vmInfo extends BaseToStringInfo {
		private static final long serialVersionUID = 1L;
		private int count;
		private String name;
		private Money in;
		private Money out;
		private Money last;
		
		public int getCount() {
			return this.count;
		}
		
		public void setCount(int count) {
			this.count = count;
		}
		
		public String getName() {
			return this.name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public Money getIn() {
			return this.in;
		}
		
		public void setIn(Money in) {
			this.in = in;
		}
		
		public Money getOut() {
			return this.out;
		}
		
		public void setOut(Money out) {
			this.out = out;
		}
		
		public Money getLast() {
			return this.last;
		}
		
		public void setLast(Money last) {
			this.last = last;
		}
		
	}
	
	/***
	 * 获取七日基础信息[无金额]
	 * @return
	 */
	private List<vmInfo> getDayInfo() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List<vmInfo> dayInfo = new ArrayList<vmInfo>();
		
		for (int i = 0; i < 7; i++) {
			vmInfo vm = new vmInfo();
			vm.setCount(1);
			vm.setName(DateUtil.dtSimpleFormat(cal.getTime()));
			dayInfo.add(vm);
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		return dayInfo;
	}
}
