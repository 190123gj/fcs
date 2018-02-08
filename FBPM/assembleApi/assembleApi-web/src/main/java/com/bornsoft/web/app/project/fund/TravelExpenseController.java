package com.bornsoft.web.app.project.fund;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.fm.ws.order.payment.TravelExpenseDetailOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseOrder;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.bornsoft.api.service.app.JckFormService;
import com.bornsoft.api.service.app.ModuleEnum;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.JsonParseUtil;
import com.bornsoft.web.app.base.WorkflowBaseController;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("fundMg/travelExpense")
public class TravelExpenseController extends WorkflowBaseController {
	
	@Autowired
	private JckFormService jckFormService;
	
	
	@ResponseBody
	@RequestMapping("add.json")
	public JSONObject add(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			String reqJson = parseStreamToString(request);
			JSONObject json = JsonParseUtil.parseObject(reqJson,JSONObject.class);
			TravelExpenseOrder order = JsonParseUtil.parseObject(reqJson, TravelExpenseOrder.class);
			//数据校验以及预算金额查询
			if(order.getDetailList()==null||order.getDetailList().isEmpty()){
				toJSONResult(result, AppResultCodeEnum.FAILED, "至少需要填写一条报销信息");
				return result;
			}else{
				//校验前端计算金额
				checkAmount(order);
			}
			
			order.setFormCode(FormCodeEnum.TRAVEL_EXPENSE_APPLY);
			//特殊类型的转换要注意
			if (StringUtil.isNotBlank(json.getString("isOfficialCard"))) {
				order.setIsOfficialCard(BooleanEnum.getByCode(json.getString("isOfficialCard")));
			} else {
				order.setIsOfficialCard(BooleanEnum.NO);
			}
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			setSessionLocalInfo2Order(order);

			FormBaseResult formResult = travelExpenseServiceClient.save(order);
			if (formResult != null && formResult.isSuccess()) {
				addAttachfile(formResult.getMessage(), json,null, "差旅费报销申请单",
					CommonAttachmentTypeEnum.FORM_ATTACH);
				//自动提交
				json.put(JckFormService.MODULE, ModuleEnum.Fund.code());
				result = jckFormService.submit(formResult.getFormInfo().getFormId(), json);
			}else{
				toJSONResult(result, formResult, "", "");
			}
		}catch(BornApiException e){
			logger.error("费用支付申请单保存失败:",e);
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
		} catch (Exception e) {
			logger.error("保存差旅费报销单失败",e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "差旅费报销单保存失败");
		}
		return result;
	}


	private void checkAmount(TravelExpenseOrder order) {
		Money totalAmount = Money.zero();
		for(TravelExpenseDetailOrder detailOrder : order.getDetailList()){
			if(StringUtil.isBlank(detailOrder.getDeptId())){
				throw new BornApiException("部门ID不能为空");
			}
			Money amount = Money.zero()
					.add(detailOrder.getTrafficAmount()).add(detailOrder.getHotelAmount())
					.add(detailOrder.getMealsAmount()).add(detailOrder.getAllowanceAmount())
					.add(detailOrder.getOtherAmount());
			if(!amount.equals(detailOrder.getTotalAmount())){
				throw new BornApiException("小计金额计算有误：" + detailOrder.getTotalAmount());
			}
			totalAmount = totalAmount.add(detailOrder.getTotalAmount());
		}
		if(!totalAmount.equals(order.getAmount())){
			throw new BornApiException("总金额计算有误");
		}
		
	}
	
}
