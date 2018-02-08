package com.bornsoft.web.app.project.fund;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.exception.FcsFaceBizException;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.enums.ReportCompanyEnum;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailAllInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationDetailOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationQueryOrder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.common.FormRelatedUserQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.bornsoft.api.service.app.DataPermissionUtil;
import com.bornsoft.api.service.app.JckFormService;
import com.bornsoft.api.service.app.ModuleEnum;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.AppUtils;
import com.bornsoft.utils.tool.BornApiRequestUtils;
import com.bornsoft.utils.tool.JsonParseUtil;
import com.bornsoft.web.app.base.WorkflowBaseController;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("fundMg/expenseApplication")
public class ExpenseApplicationController extends WorkflowBaseController {

	@Autowired
	private JckFormService jckFormService;
	
	final static HashMap<String, FormCodeEnum> fmap = new HashMap<String, FormCodeEnum>();
	
	final static List<String> notShowList = new ArrayList<String>();
	/** 劳资 */
	final static List<String> lzList = new ArrayList<String>();
	/** 税金 */
	final static List<String> sjList = new ArrayList<String>();
	/** 信惠无金额 */
	final static List<String> xhwjeList = new ArrayList<String>();
	
	static {
		//费用支付(无金额规则类)审核
		fmap.put("#公证费#", FormCodeEnum.EXPENSE_APPLICATION_NLIMIT);
		//费用支付(无金额规则类 税金类)审核
		//		fmap.put("#房产税#车船使用税#土地使用税#印花税#", FormCodeEnum.EXPENSE_APPLICATION_NLIMIT);
		//上面四类合并为一类
		fmap.put("#税金#", FormCodeEnum.EXPENSE_APPLICATION_NLIMIT);
		//费用支付(还款类)审批
		fmap.put("#还款#", FormCodeEnum.EXPENSE_APPLICATION_REFUND);
		//费用支付(党费)审批 
		fmap.put("#党建经费#", FormCodeEnum.EXPENSE_APPLICATION_DF);
		//费用支付(团委费)审批 
		fmap.put("#团委费#", FormCodeEnum.EXPENSE_APPLICATION_TF);
		//费用支付(借款类)审批流程
		fmap.put("#借款#", FormCodeEnum.EXPENSE_APPLICATION_LOAN);
		//费用支付(冲预付款类)审批
		fmap.put("#退预付款#冲预付款#", FormCodeEnum.EXPENSE_APPLICATION_CPREPAY);
		//费用支付(预付款类)审批
		fmap.put("#预付款#", FormCodeEnum.EXPENSE_APPLICATION_PREPAY);
		
		fmap.put("#工会经费#", FormCodeEnum.EXPENSE_APPLICATION_UNION_FUNDS);
		//费用支付(劳资类)审核
		fmap.put("#工资#养老保险#失业保险#医疗保险#工伤保险#生育保险#住房公积金#补充医疗保险#企业年金#补充养老保险#劳务费#福利费（其他）#",
			FormCodeEnum.EXPENSE_APPLICATION_LZ);
		//费用支付(金额规则类)审批
		fmap.put(
			"#职教经费#广告宣传费#业务招待费#办公费#会务费#水电费#物管费#租赁费#邮电通讯费#修理费#外事费#招聘费#车辆运行费#董事会经费#开办费#装修费#其他#福利费（食堂）#会员费#劳动保护费#审计评估费#咨询费#聘请律师及诉讼费#",
			FormCodeEnum.EXPENSE_APPLICATION_LIMIT);
		
		
		//		xhwjeList.add("审计评估费");
		//		xhwjeList.add("咨询费");
		xhwjeList.add("公证费");
		//		xhwjeList.add("聘请律师及诉讼费");
		
		lzList.add("工资");
		lzList.add("养老保险");
		lzList.add("失业保险");
		lzList.add("医疗保险");
		lzList.add("工伤保险");
		lzList.add("生育保险");
		lzList.add("住房公积金");
		lzList.add("补充医疗保险");
		lzList.add("企业年金");
		lzList.add("补充养老保险");
		lzList.add("劳务费");
		lzList.add("福利费（其他）");
		//		lzList.add("劳动保护费");
		//		sjList.add("房产税");
		//		sjList.add("车船使用税");
		//		sjList.add("土地使用税");
		//		sjList.add("印花税");
		sjList.add("税金");
		
		//		notShowList.add("工资");
		//		notShowList.add("养老保险");
		//		notShowList.add("失业保险");
		//		notShowList.add("医疗保险");
		//		notShowList.add("工伤保险");
		//		notShowList.add("生育保险");
		//		notShowList.add("住房公积金");
		//		notShowList.add("补充医疗保险");
		//		notShowList.add("企业年金");
		//		notShowList.add("补充养老保险");
		//		notShowList.add("外派劳务费");
		//		notShowList.add("其他（劳资）");
		//		notShowList.add("福利费（其他）");
		//		notShowList.add("房产税");
		//		notShowList.add("车船使用税");
		//		notShowList.add("土地使用税");
		//		notShowList.add("印花税");
		//		notShowList.add("其他（税金）");
	}
	//公务卡需要屏蔽的
	private static List<String>  officialCardDisabled =Arrays.asList("借款、还款、预付款、退预付款、冲预付款、工资、养老保险、失业保险、医疗保险、工伤保险、生育保险、住房公积金、补充医疗保险、企业年金、补充养老保险、外派劳务费、福利费（其他）".split("、"));
	//非公务卡需要屏蔽的
	private static List<String>  notOfficialCardDisabled =Arrays.asList("退预付款、冲预付款".split("、"));
	
	//对公类型需要屏蔽的
	private static List<String>  publicDisabled = Arrays.asList("借款、还款".split("、"));
	//对私类型需要屏蔽的
	private static List<String>  privateDisabled = Arrays.asList("预付款".split("、"));
	
	//对私冲销可以选择的类型
	@SuppressWarnings("unused")
	private static List<String>  privateCX = Arrays.asList("还款".split("、"));
	
	@ResponseBody
	@RequestMapping("init.json")
	public JSONObject init(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			result.put("agentId", getLocalSession().getUserId());
			result.put("agent", getLocalSession().getRealName());
			if(getLocalSession().getUserDetailInfo()!=null && getLocalSession().getUserDetailInfo().getPrimaryOrg()!=null){
				long expenseDeptId = getLocalSession().getUserDetailInfo().getPrimaryOrg().getId();
				result.put("expenseDeptId", expenseDeptId);
				result.put("expenseDeptName", getLocalSession().getUserDetailInfo().getPrimaryOrg().getName());
				result.put("deptId", result.getLong("expenseDeptId"));
				result.put("deptName", result.getString("expenseDeptName"));
				result.put("deptHead", getDeptHead(expenseDeptId, null, new JSONObject()).getString("name"));
			}
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("报销单初始化失败",e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "报销单初始化失败");
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("getCostTypeList.json")
	public JSONObject getCostTypeList(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			//是否公务卡
			String isOfficialCard = request.getParameter("isOfficialCard");
			if(StringUtils.isBlank(isOfficialCard)){
				isOfficialCard = BooleanEnum.NO.code();
			}
			List<CostCategoryInfo> costList = 
					getCostCategoryList(null, true);
			//收费方向
			String direct = BornApiRequestUtils.getParameter(request, "direction");
			
			JSONArray dataList = new JSONArray();
			for(CostCategoryInfo info : costList){
				if(BooleanEnum.IS.code().equals(isOfficialCard) && officialCardDisabled.contains(info.getName())){
					continue;
				}
				if((StringUtils.isBlank(isOfficialCard) || BooleanEnum.NO.code().equals(isOfficialCard)) && notOfficialCardDisabled.contains(info.getName())){
					continue;
				}
				if(CostDirectionEnum.PUBLIC.code().equals(direct) && publicDisabled.contains(info.getName())){
					continue;
				}
				if(CostDirectionEnum.PRIVATE.code().equals(direct) && privateDisabled.contains(info.getName())){
					continue;
				}
				JSONObject e = new JSONObject();
				e.put("id", info.getCategoryId());
				e.put("name", info.getName());
				dataList.add(e);
			}
			result.put("dataList", dataList);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("获取收费种类失败",e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "获取收费种类失败");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("add.json")
	public JSONObject save(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		
		try {
			String reqJson = parseStreamToString(request);
			JSONObject json = JsonParseUtil.parseObject(reqJson,JSONObject.class);
			ExpenseApplicationOrder order = JsonParseUtil.parseObject(reqJson, ExpenseApplicationOrder.class);
			//特殊类型要特殊处理
			if (StringUtil.isNotBlank(json.getString("isOfficialCard"))) {
				order.setIsOfficialCard(BooleanEnum.getByCode(json.getString("isOfficialCard")));
			} else {
				order.setIsOfficialCard(BooleanEnum.NO);
			}
			if (StringUtil.isNotBlank(json.getString("direction"))) {
				order.setDirection(CostDirectionEnum.getByCode(json.getString("direction")));
			} else {
				order.setDirection(CostDirectionEnum.PUBLIC);
			}

			if (order.getDirection() == CostDirectionEnum.PRIVATE) {
				order.setBank(json.getString("bank1"));
				order.setBankAccount(json.getString("bankAccount1"));
				order.setPayee(json.getString("payee1"));
			}
			//冲销
			if(checkBoolean(order.getIsReverse())){
				order.setIsReverse(BooleanEnum.YES.code());
				// 获取冲销id 
				String clid = json.getString("clid");
				if(StringUtils.isNotBlank(clid)){
					order.setCxids(clid);
					order.setReverseIds(Arrays.asList(clid.split(",")));
					if(order.getDirection() == CostDirectionEnum.PUBLIC &&
							order.getReamount()==null || order.getReamount().compareTo(Money.zero())<=0){
						toJSONResult(result, AppResultCodeEnum.FAILED, "对公冲销总金额未填写");
						return result;
					} 
				}else if(order.getDirection() == CostDirectionEnum.PUBLIC){
					toJSONResult(result, AppResultCodeEnum.FAILED, "请选择待冲销信息");
					return result;
				}
			
			}else{
				order.setIsReverse(BooleanEnum.NO.code());
				order.setCxids("");
				order.setReverseIds(new ArrayList<String>());
			}
			
			//数据校验以及预算金额查询
			if(order.getDetailList()==null||order.getDetailList().isEmpty()){
				toJSONResult(result, AppResultCodeEnum.FAILED, "至少需要填写一条报销信息");
				return result;
			}else{
				//校验前端计算金额
				checkAmount(order);
			}
			
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			setSessionLocalInfo2Order(order);
			//冲销未实现【还款和预冲销已去除】
			setFormCode(order);
			FormBaseResult formResult = expenseApplicationServiceClient.save(order);
			if (formResult != null && formResult.isSuccess()) {
				addAttachfile(formResult.getMessage(),json,null, "费用支付申请单附件",
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
			logger.error("费用支付申请单保存失败:",e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "费用支付申请单保存失败");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("checkExpenseType.json")
	public JSONObject checkExpenseType(HttpServletRequest request,String expenseTypes,CostDirectionEnum direction) {
		JSONObject result = new JSONObject();
		try {
			if(StringUtil.isNotBlank(expenseTypes)){
				String [] expenseTypeArr = expenseTypes.split(",");
				List<ExpenseApplicationDetailOrder> details = new ArrayList<>();
				for(String expenseId : expenseTypeArr){
					ExpenseApplicationDetailOrder ex = new ExpenseApplicationDetailOrder();
					ex.setExpenseType(expenseId);
					details.add(ex);
				}
				BooleanEnum  isOfficialCard = BooleanEnum.getByCode(request.getParameter("isOfficialCard"));
				if(isOfficialCard == null){
					isOfficialCard = BooleanEnum.NO;
				}
				checkExpenseType(direction,isOfficialCard,details);
			}
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		}catch(FcsFaceBizException e){
			logger.error("校验费用种类失败",e);
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
		} catch (Exception e) {
			logger.error("校验费用种类失败",e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "校验费用种类失败");
		}
		return result;
	}
	
	/**
	 * 查询冲销信息
	 * @param formId 表单formid
	 * @param payee 收款人
	 * @param direction 费用方向 PRIVATE PUBLIC
	 * @param getLoginUser 是否查看更多
	 * @return
	 */
	@ResponseBody
	@RequestMapping("expenseApplicationReverse.json")
	public Object expenseApplicationReverse(String expenseApplicationId, String payee,
											String direction, boolean isMore) {
		JSONObject result = new JSONObject();
		try {
			// 抓取 还款冲销信息和预付款冲销信息
			long jkId = 0;
			long yfkId = 0;
			long hkId = 0;
			@SuppressWarnings("unused")
			long tyfkId = 0;
			CostCategoryQueryOrder costOrder = new CostCategoryQueryOrder();
			costOrder.setStatusList(new ArrayList<CostCategoryStatusEnum>());
			costOrder.getStatusList().add(CostCategoryStatusEnum.NORMAL);
			costOrder.setPageSize(10000);
			QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
				.queryPage(costOrder);
			for (CostCategoryInfo cInfo : batchResult.getPageList()) {
				if ("借款".equals(cInfo.getName())) {
					jkId = cInfo.getCategoryId();
				} else if ("预付款".equals(cInfo.getName())) {
					yfkId = cInfo.getCategoryId();
				} else if ("还款".equals(cInfo.getName())) {
					hkId = cInfo.getCategoryId();
				} else if ("退预付款".equals(cInfo.getName())) {
					tyfkId = cInfo.getCategoryId();
				}
			}
			JSONArray data = new JSONArray();
			
			// 对公抓去预付款信息 对私抓取借款信息
			if (CostDirectionEnum.PUBLIC.code().equals(direction)) {
				// 预付款
				Money yfkAllAmount = Money.zero();
				Money tyfkAllAmount = Money.zero();
				Money waitReverseAmount = Money.zero();
				if (yfkId > 0) {
					ExpenseApplicationQueryOrder queryOrder = new ExpenseApplicationQueryOrder();
					queryOrder.setPageSize(10000);
					queryOrder.setExpenseType(String.valueOf(yfkId));
					if (!isMore) {
						queryOrder.setPayee(payee);
					}
					queryOrder.setAgent(String.valueOf(ShiroSessionUtils.getSessionLocal()
						.getUserId()));
					// 查询审核通过的
					List<FormStatusEnum> formStatuss = new ArrayList<FormStatusEnum>();
					formStatuss.add(FormStatusEnum.APPROVAL);
					queryOrder.setFormStatusList(formStatuss);
					QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryResult = expenseApplicationServiceClient
						.queryPageAll(queryOrder);
					if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
						
						for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
							// 待冲销是全部金额，已冲销是选中且审核通过
							yfkAllAmount.addTo(info.getAmount());
							//  判断已被冲销的为已冲销金额,未冲销的为等待冲销金额
							if (BooleanEnum.YES == info.getReverse()
								&& FormStatusEnum.APPROVAL == info.getFormStatus()) {
								// 已冲销
								tyfkAllAmount.addTo(info.getAmount());
							} else {
								// 待冲销 
								waitReverseAmount.addTo(info.getAmount());
								
								JSONObject json = new JSONObject();
								json.put("detailId", info.getDetailId());
								json.put("applicationTime",
									DateUtil.dtSimpleFormat(info.getApplicationTime()));
								json.put("billNo", info.getBillNo());
								json.put("expenseApplicationId", info.getExpenseApplicationId());
								for (CostCategoryInfo cost : batchResult.getPageList()) {
									if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
										&& String.valueOf(cost.getCategoryId()).equals(
											info.getExpenseType())) {
										json.put("expenseType", cost.getName());
										break;
									}
								}
								json.put("amount", AppUtils.toString(info.getAmount()));
								data.add(json);
							}
							
						}
					}
					
					result.put("borrowList", data);
					result.put("waitReverseAmount", AppUtils.toString(waitReverseAmount));
					result.put("totalAmount", AppUtils.toString(yfkAllAmount));
					result.put("usedAmount", AppUtils.toString(tyfkAllAmount));
					
					toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
					
				}
			} else if (CostDirectionEnum.PRIVATE.code().equals(direction)) {
				// 借款
				Money jkAllAmount = Money.zero();
				Money hkAllAmount = Money.zero();
				Money waitReverseAmount = Money.zero();
				if (jkId > 0) {
					ExpenseApplicationQueryOrder queryOrder = new ExpenseApplicationQueryOrder();
					queryOrder.setPageSize(10000);
					queryOrder.setExpenseType(String.valueOf(jkId));
					queryOrder.setPayee(payee);
					queryOrder.setAgent(String.valueOf(ShiroSessionUtils.getSessionLocal()
						.getUserId()));
					// 查询审核通过的
					List<FormStatusEnum> formStatuss = new ArrayList<FormStatusEnum>();
					formStatuss.add(FormStatusEnum.APPROVAL);
					queryOrder.setFormStatusList(formStatuss);
					QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryResult = expenseApplicationServiceClient
						.queryPageAll(queryOrder);
					if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
						for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
							jkAllAmount.addTo(info.getAmount());
							JSONObject json = new JSONObject();
							json.put("detailId", info.getDetailId());
							json.put("applicationTime",
								DateUtil.dtSimpleFormat(info.getApplicationTime()));
							json.put("billNo", info.getBillNo());
							json.put("expenseApplicationId", info.getExpenseApplicationId());
							for (CostCategoryInfo cost : batchResult.getPageList()) {
								if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
									&& String.valueOf(cost.getCategoryId()).equals(
										info.getExpenseType())) {
									json.put("expenseType", cost.getName());
									break;
								}
							}
							json.put("amount",AppUtils.toString(info.getAmount()));
							data.add(json);
						}
					}
					// 还款
					queryOrder.setExpenseType(String.valueOf(hkId));
					formStatuss.clear();
					formStatuss.add(FormStatusEnum.DRAFT);
					formStatuss.add(FormStatusEnum.SUBMIT);
					//					formStatuss.add(FormStatusEnum.CANCEL);
					formStatuss.add(FormStatusEnum.AUDITING);
					//					formStatuss.add(FormStatusEnum.BACK);
					formStatuss.add(FormStatusEnum.APPROVAL);
					queryOrder.setFormStatusList(formStatuss);
					queryResult = expenseApplicationServiceClient.queryPageAll(queryOrder);
					if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
						for (FormExpenseApplicationDetailAllInfo info : queryResult.getPageList()) {
							
							// 剔除自己的还款数据  发现是自己的数据，不计算入已还款
							if (StringUtil.equals(expenseApplicationId,
								String.valueOf(info.getExpenseApplicationId()))) {
								
							}
							hkAllAmount.addTo(info.getAmount());
						}
					}
					if (StringUtil.isNotEmpty(expenseApplicationId)) {
						FormExpenseApplicationInfo expenseInfo = expenseApplicationServiceClient
							.queryById(Long.valueOf(expenseApplicationId));
						/// 审核中且自己，才将金额加回去，用于兼容审核页面的修改
						if (FormStatusEnum.DRAFT == expenseInfo.getFormStatus()
							|| FormStatusEnum.SUBMIT == expenseInfo.getFormStatus()
							|| FormStatusEnum.AUDITING == expenseInfo.getFormStatus()
							|| FormStatusEnum.APPROVAL == expenseInfo.getFormStatus()) {
							
							// 20161123 剔除自己的还款数据  发现是自己的数据，不计算入已还款 减去自己的待还款记录
							for (FormExpenseApplicationDetailInfo detailInfo : expenseInfo
								.getDetailList()) {
								if (StringUtil.equals(String.valueOf(hkId),
									detailInfo.getExpenseType())) {
									hkAllAmount.subtractFrom(detailInfo.getAmount());
								}
							}
						}
					}
					waitReverseAmount = jkAllAmount.subtract(hkAllAmount);
				}
				
				result.put("borrowList", data);
				result.put("waitReverseAmount", AppUtils.toString(waitReverseAmount));
				result.put("totalAmount",AppUtils.toString(jkAllAmount));
				result.put("usedAmount", AppUtils.toString(hkAllAmount));
				
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			}else{
				toJSONResult(result, AppResultCodeEnum.FAILED, "费用方向非法: " + direction);	
			}
			
		} catch (Exception e) {
			logger.error("查询冲销信息出错", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询冲销信息失败");
		}
		return result;
	}
	
	/**
	 * 前端金额校验
	 * @param order
	 */
	private void checkAmount(ExpenseApplicationOrder order) {
		Money totalAmount = Money.zero();
		for(ExpenseApplicationDetailOrder detailOrder : order.getDetailList()){
			totalAmount = totalAmount.add(detailOrder.getAmount());
		}
		if(!totalAmount.equals(order.getAmount())){
			throw new BornApiException("总金额计算有误");
		}
		if(checkBoolean(order.getIsReverse())){
			if(order.getDirection() == CostDirectionEnum.PUBLIC && totalAmount.compareTo(order.getReamount())<0){
				throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"报销总金额不能小于冲销金额");
			}else if(order.getDirection() == CostDirectionEnum.PRIVATE &&  
					totalAmount.compareTo( order.getReamount())>0){
				throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"报销总金额不能大于剩余未冲销金额");
			}
		}
		if(order.getDirection() == CostDirectionEnum.PRIVATE){
			order.setReamount(Money.zero());
		}
		
	}

	/**
	 * 根据费用类型设置审核流程
	 * @param order
	 */
	private void setFormCode(ExpenseApplicationOrder order) {
		FormCodeEnum curFormCodeEnum = null;
		// 20170327劳资和税金的信惠判定。因为不会出现退预付款所以先行判定
		//  20170315添加判断 劳资类 添加信惠申请人判断
		
		final List<String> lzxhList = new ArrayList<String>();
		lzxhList.addAll(lzList);
		lzxhList.addAll(sjList);
		boolean isXJLZ = false;
		if (order.getFormId()!=null && order.getFormId() > 0) {
			
			FormExpenseApplicationInfo oldInfo = expenseApplicationServiceClient
				.queryByFormId(order.getFormId());
			if (FormCodeEnum.EXPENSE_APPLICATION_LZ_XH == oldInfo.getFormCode()) {
				isXJLZ = true;
			}
		}
		// 判定是信惠发起
		Org userOrg = ShiroSessionUtils.getSessionLocal().getUserDetailInfo().getPrimaryOrg();
		if (StringUtil.equals(userOrg.getCode(), ReportCompanyEnum.XINHUI.getDeptCode()) || isXJLZ) {
			//  判定是否无金额类别的超出
			boolean isXHSJ = false;
			boolean isWJE = false;
			// 判定全部属于劳资及税金
			boolean allLzxh = true;
			for (ExpenseApplicationDetailOrder detailOrder : order.getDetailList()) {
				CostCategoryInfo costInfo = costCategoryServiceClient.queryById(Long
					.valueOf(detailOrder.getExpenseType()));
				if (sjList.contains(costInfo.getName())) {
					isXHSJ = true;
					continue;
				} else if (lzList.contains(costInfo.getName())) {
					continue;
				} else if (xhwjeList.contains(costInfo.getName())) {
					isWJE = true;
					allLzxh = false;
					continue;
				} else {
					allLzxh = false;
				}
				//				if (!lzxhList.contains(costInfo.getName())) {
				//					allLzxh = false;
				//					break;
				//				}
			}
			////// 20170615 接漆老师电话需求：非劳资信惠且信惠发起都应该走入信惠流程。
			//			if (isXHSJ && isWJE) {
			//				// 添加判定 无金额规则类和信惠类税金不能同时存在xhwjeList
			//				throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
			//					"费用类型存在多个不同审核流程");
			//			}
			if (allLzxh) {
				curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_LZ_XH;
			} else {
				curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_JE_XH;
			}
			
		}
		if (curFormCodeEnum == null) {
			
			boolean expenseApplicationCprepayGet = false;
			for (ExpenseApplicationDetailOrder detailOrder : order.getDetailList()) {
				CostCategoryInfo costInfo = costCategoryServiceClient.queryById(Long
					.valueOf(detailOrder.getExpenseType()));
				boolean isExit = true;
				for (String key : fmap.keySet()) {
					if (key.contains("#" + costInfo.getName() + "#")) {
						FormCodeEnum keyFormCode = fmap.get(key);
						if (FormCodeEnum.EXPENSE_APPLICATION_CPREPAY == keyFormCode) {
							expenseApplicationCprepayGet = true;
							isExit = false;
							continue;
						}
						// 20161118添加判断 退预付款单独进入自己退预付款流程，退预付款和其他流程一起的时候进入其他流程
						
						if (curFormCodeEnum != null && curFormCodeEnum != keyFormCode) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
								"费用类型存在多个不同审核流程");
						}
						curFormCodeEnum = keyFormCode;
						isExit = false;
					}
				}
				
				if (isExit) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"费用类型【" + costInfo.getName() + "】无对应审核流程");
				}
				
				// 20170406 添加判断 无金额规则类的税金和其他不能在一起
				boolean isXHSJ = false;
				boolean isWJE = false;
				if (sjList.contains(costInfo.getName())) {
					isXHSJ = true;
					continue;
				} else if (xhwjeList.contains(costInfo.getName())) {
					isWJE = true;
					continue;
				}
				if (isXHSJ && isWJE) {
					// 添加判定 无金额规则类和信惠类税金不能同时存在xhwjeList
					throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"不同单据编号的费用类型不能同时申请");
				}
			}
			
			if (curFormCodeEnum == null) {
				
				// 20161118添加判断 退预付款单独进入自己退预付款流程，退预付款和其他流程一起的时候进入其他流程
				if (expenseApplicationCprepayGet) {
					curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_CPREPAY;
				} else {
					curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_LIMIT;
				}
			}
			
			// 20161221 添加判断 金额规则类 添加信惠申请人判断
			if (FormCodeEnum.EXPENSE_APPLICATION_LIMIT == curFormCodeEnum) {
				//				Org userOrg = ShiroSessionUtils.getSessionLocal().getUserDetailInfo().getPrimaryOrg();
				if (StringUtil.equals(userOrg.getCode(), ReportCompanyEnum.XINHUI.getDeptCode())) {
					curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_JE_XH;
				}
				//			if (projectRelatedUserServiceClient.isBelong2Dept(ShiroSessionUtils.getSessionLocal()
				//				.getUserId(), sysParameterServiceClient
				//				.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code()))) {
				//				curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_JE_XH;
				//				
				//			}
				// 2017-0328 金额规则类别，若是分公司。进入EXPENSE_APPLICATION_FZJG分支机构流程
				List<ExpenseApplicationDetailOrder> detailList = order.getDetailList();
				boolean allFGX = true;
				for (ExpenseApplicationDetailOrder detailOrder : detailList) {
					if (!checkIsFenGongSi(detailOrder.getDeptName())) {
						allFGX = false;
						break;
					}
				}
				if (allFGX) {
					curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_FZJG;
				}
			}
		}
		// 20170329 分支机构类还原为金额类,暂时不上线
		if (FormCodeEnum.EXPENSE_APPLICATION_FZJG == curFormCodeEnum) {
			curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_LIMIT;
		}
		order.setFormCode(curFormCodeEnum);
	}
	
	private boolean checkIsFenGongSi(String orgName) {
		return orgName.contains("分公司") || StringUtil.equals(orgName, "四川代表处")
				|| StringUtil.equals(orgName, "云南代表处") || StringUtil.equals(orgName, "湖南代表处");
	}
	
	
	/**
	 * 校验费用类型是否合法
	 * @param details
	 * @return
	 */
	private FormCodeEnum checkExpenseType(CostDirectionEnum direction, BooleanEnum isOfficeCard,List<ExpenseApplicationDetailOrder> details) {
		FormCodeEnum curFormCodeEnum = null;
		for (ExpenseApplicationDetailOrder detailOrder : details) {
			CostCategoryInfo costInfo = costCategoryServiceClient.queryById(Long
				.valueOf(detailOrder.getExpenseType()));
			if(isOfficeCard == BooleanEnum.IS && officialCardDisabled.contains(costInfo.getName())){
				throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"公务卡支付不能选择费用类型" + costInfo.getName());
			}
			if(isOfficeCard == BooleanEnum.NO && notOfficialCardDisabled.contains(costInfo.getName())){
				throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"非公务卡支付不能选择费用类型" + costInfo.getName());
			}
			
			if(CostDirectionEnum.PUBLIC.code().equals(direction) && publicDisabled.contains(costInfo.getName())){
				throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"对公不能选择费用类型" + costInfo.getName());
			}
			if(CostDirectionEnum.PRIVATE.code().equals(direction) && privateDisabled.contains(costInfo.getName())){
				throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"对私不能选择费用类型" + costInfo.getName());
			}
			
			boolean isExit = true;
			for (String key : fmap.keySet()) {
				if (key.contains("#" + costInfo.getName() + "#")) {
					FormCodeEnum keyFormCode = fmap.get(key);
					if (curFormCodeEnum != null && curFormCodeEnum != keyFormCode) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
							"费用类型存在多个不同审核流程");
					}
					curFormCodeEnum = keyFormCode;
					isExit = false;
				}
			}
			if (isExit)
				throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
					"费用类型【" + costInfo.getName() + "】无对应审核流程");
		}
		return curFormCodeEnum;
	}
	
	private List<CostCategoryInfo> getCostCategoryList(FormInfo formInfo, boolean isCheckXCZY) {
		
		FormCodeEnum limtFormCodeEnum = null;
		if (formInfo != null) {
			FormRelatedUserQueryOrder forder = new FormRelatedUserQueryOrder();
			forder.setFormId(formInfo.getFormId());
			long count = formRelatedUserServiceClient.queryCount(forder);
			if (count > 0) {//已提交，只能选择相同流程费用类型
				limtFormCodeEnum = formInfo.getFormCode();
			}
		}
		boolean isXCZY = true;//薪酬专员
		if (isCheckXCZY) {
			isXCZY = DataPermissionUtil.hasRole("xczy");//薪酬专员
		}
		List<CostCategoryInfo> retlist = new ArrayList<CostCategoryInfo>();
		CostCategoryQueryOrder order = new CostCategoryQueryOrder();
		order.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
			.queryPage(order);
		for (CostCategoryInfo info : batchResult.getPageList()) {
			if (info.getName().equals("差旅费"))
				continue;
			
			if (!isXCZY) {
				if ("工资、养老保险、失业保险、医疗保险、工伤保险、生育保险、住房公积金".contains(info.getName()))
					continue;
			}
			
			if (limtFormCodeEnum != null) {
				boolean isAdd = false;
				for (String key : fmap.keySet()) {
					if (key.contains("#" + info.getName() + "#")) {
						FormCodeEnum keyFormCode = fmap.get(key);
						if (limtFormCodeEnum == keyFormCode) {//已提交，只能选择相同流程费用类型
							isAdd = true;
						}
						break;
					}
				}
				if (!isAdd)
					continue;
			}
			
			retlist.add(info);
		}
		
		return retlist;
	}

}
