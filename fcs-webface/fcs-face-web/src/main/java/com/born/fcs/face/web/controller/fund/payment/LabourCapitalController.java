package com.born.fcs.face.web.controller.fund.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.FundBaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalDetailAllInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalInfo;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.fm.ws.order.payment.LabourCapitalDetailOrder;
import com.born.fcs.fm.ws.order.payment.LabourCapitalOrder;
import com.born.fcs.fm.ws.order.payment.LabourCapitalQueryOrder;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.user.UserExtraMessageResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

@Controller
@RequestMapping("fundMg/labourCapital/")
public class LabourCapitalController extends FundBaseController {
	final static String vm_path = "/fundMg/paymentMg/labourCapital/";
	
	final static HashMap<String, FormCodeEnum> fmap = new HashMap<String, FormCodeEnum>();
	
	static {
		//		//劳资支付(其他)审批
		//		fmap.put("#其他#", FormCodeEnum.LABOUR_CAPITAL_QT);
		//劳资支付(劳资类)审核
		fmap.put("#工资#养老保险#失业保险#医疗保险#工伤保险#生育保险#住房公积金#补充医疗保险#企业年金#补充养老保险#外派劳务费#其他（劳资）#福利费（其他）#",
			FormCodeEnum.LABOUR_CAPITAL_LZ);
		//劳资支付(税金类)审批
		fmap.put("#房产税#车船使用税#土地使用税#印花税#其他（税金）#", FormCodeEnum.LABOUR_CAPITAL_SJ);
		
	}
	
	@RequestMapping("ealist.htm")
	public String ealist(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		LabourCapitalQueryOrder order = getLabourCapitalQueryOrder(request, model);
		if (!StringUtil.equals(request.getParameter("from"), "query") && order.getAuditor() == 0) {
			SessionLocal session = ShiroSessionUtils.getSessionLocal();
			order.setAuditor(session.getUserId());
		}
		QueryBaseBatchResult<FormLabourCapitalDetailAllInfo> queryResult = labourCapitalServiceClient
			.queryPageAll(order);
		CostCategoryQueryOrder order2 = new CostCategoryQueryOrder();
		order2.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order2.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order2.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
			.queryPage(order2);
		if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())
			&& batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
			for (FormLabourCapitalDetailAllInfo info : queryResult.getPageList()) {
				String expenseType = info.getExpenseType();
				
				for (CostCategoryInfo category : batchResult.getPageList()) {
					if (expenseType.equals(String.valueOf(category.getCategoryId()))) {
						expenseType = category.getName();
					}
				}
				info.setExpenseType(expenseType);
			}
		}
		
		model.addAttribute("page", PageUtil.getCovertPage(queryResult));
		model.addAttribute("conditions", order);
		
		return vm_path + "list.vm";
	}
	
	@RequestMapping("add.htm")
	public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
		buildSystemNameDefaultUrl(request, model);
		String travelId = request.getParameter("id");
		if (StringUtil.isNotBlank(travelId)) {
			FormLabourCapitalInfo info = labourCapitalServiceClient.queryById(Long
				.valueOf(travelId));
			model.addAttribute("info", info);
			
			queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		
		// 抓取经办人的银行帐号信息
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserExtraMessageResult userResult = userExtraMessageServiceClient
				.findByUserId(sessionLocal.getUserId());
			if (userResult.isSuccess() && userResult.isExecuted()
				&& userResult.getUserExtraMessageInfo() != null) {
				model.addAttribute("localUserExtra", userResult.getUserExtraMessageInfo());
			}
		}
		
		model.addAttribute("categoryList", getCostCategoryList(null, true));
		model.addAttribute("now", new Date());
		return vm_path + "addForm.vm";
	}
	
	@ResponseBody
	@RequestMapping("save.json")
	public JSONObject save(HttpServletRequest request, Model model) {
		LabourCapitalOrder order = new LabourCapitalOrder();
		WebUtil.setPoPropertyByRequest(order, request);
		String onlyChange = request.getParameter("onlyChangeDetailList");
		if (StringUtil.isNotBlank(onlyChange)) {
			order.setOnlyChangeDetailList(BooleanEnum.getByCode(onlyChange));
		}
		if (StringUtil.isNotBlank(request.getParameter("officialCard"))) {
			order.setIsOfficialCard(BooleanEnum.getByCode(request.getParameter("officialCard")));
		} else {
			order.setIsOfficialCard(BooleanEnum.NO);
		}
		if (StringUtil.isNotBlank(request.getParameter("direction"))) {
			order.setDirection(CostDirectionEnum.getByCode(request.getParameter("direction")));
		} else {
			order.setDirection(CostDirectionEnum.PUBLIC);
		}
		
		if (order.getDirection() == CostDirectionEnum.PRIVATE) {
			order.setBank(request.getParameter("bank1"));
			order.setBankAccount(request.getParameter("bankAccount1"));
			order.setPayee(request.getParameter("payee1"));
		}
		
		order.setCheckIndex(0);
		order.setCheckStatus(1);
		setSessionLocalInfo2Order(order);
		try {
			if (request.getParameterValues("expenseType") == null) {
				FormBaseResult result = new FormBaseResult();
				result.setMessage("费用类型不能为空！");
				return toJSONResult(result, "保存");
			}
			// 获取冲销id 
			String[] clid = request.getParameterValues("clid");
			String cxids = "";
			List<String> reverseIds = new ArrayList<String>();
			if (clid != null) {
				for (String str : clid) {
					reverseIds.add(str);
					if (StringUtil.isNotBlank(cxids)) {
						cxids += ",";
					}
					cxids += str;
				}
			}
			if (BooleanEnum.YES.code().equals(request.getParameter("isReverse"))) {
				order.setCxids(cxids);
				order.setReverseIds(reverseIds);
				//				if (StringUtil.isBlank(cxids)) {
				//					order.setIsReverse(BooleanEnum.NO.code());
				//				}
			} else {
				order.setIsReverse(BooleanEnum.NO.code());
				order.setCxids("");
				order.setReverseIds(new ArrayList<String>());
			}
			setDetailList(order, request);
		} catch (Exception e) {
			FormBaseResult result = new FormBaseResult();
			result.setMessage(e.getMessage());
			return toJSONResult(result, "保存");
		}
		
		FormBaseResult result = labourCapitalServiceClient.save(order);
		if (result != null && result.isSuccess()) {
			addAttachfile(result.getMessage(), request, null, "劳资及税金申请单附件",
				CommonAttachmentTypeEnum.FORM_ATTACH);
		}
		JSONObject json = toJSONResult(result, "保存");
		Object obj = null;
		if (result != null)
			obj = result.getReturnObject();
		
		if (obj != null) {
			FormLabourCapitalInfo info = (FormLabourCapitalInfo) obj;
			if (info != null) {
				json.put("id", info.getLabourCapitalId());
				json.put("billNo", info.getBillNo());
			}
		}
		return json;
	}
	
	@RequestMapping("view.htm")
	public String view(long formId, HttpSession session, HttpServletRequest request, Model model) {
		buildSystemNameDefaultUrl(request, model);
		FormInfo form = formServiceFmClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		model.addAttribute("form", form);
		
		FormLabourCapitalInfo info = labourCapitalServiceClient.queryByFormId(formId);
		setCxinfo(model, info, false);
		model.addAttribute("info", info);
		model.addAttribute("isview", true);
		boolean isFYFG = DataPermissionUtil.isFinancialYFG();
		model.addAttribute("isFYFG", isFYFG);
		queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		
		model.addAttribute("categoryList", getCostCategoryList(null, false));
		return vm_path + "viewAudit.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(long formId, HttpServletRequest request, HttpSession session, Model model) {
		buildSystemNameDefaultUrl(request, model);
		FormInfo form = formServiceFmClient.findByFormId(formId);
		if (form == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
		}
		model.addAttribute("form", form);
		
		FormLabourCapitalInfo expenseInfo = labourCapitalServiceClient.queryByFormId(formId);
		model.addAttribute("info", expenseInfo);
		
		queryCommonAttachmentData(model, String.valueOf(expenseInfo.getBillNo()),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		
		model.addAttribute("categoryList", getCostCategoryList(form, true));
		
		//		// 抓取 还款冲销信息和预付款冲销信息
		//		long jkId = 0;
		//		long yfkId = 0;
		//		long hkId = 0;
		//		long tyfkId = 0;
		//		CostCategoryQueryOrder costOrder = new CostCategoryQueryOrder();
		//		costOrder.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		//		costOrder.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		//		costOrder.setPageSize(10000);
		//		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
		//			.queryPage(costOrder);
		//		for (CostCategoryInfo cInfo : batchResult.getPageList()) {
		//			if ("借款".equals(cInfo.getName())) {
		//				jkId = cInfo.getCategoryId();
		//			} else if ("预付款".equals(cInfo.getName())) {
		//				yfkId = cInfo.getCategoryId();
		//			}
		//		}
		//		
		//		// 借款
		//		Money jkAllAmount = Money.zero();
		//		if (jkId > 0) {
		//			LabourCapitalQueryOrder queryOrder = new LabourCapitalQueryOrder();
		//			queryOrder.setExpenseType(String.valueOf(jkId));
		//			queryOrder.setPayee(expenseInfo.getPayee());
		//			QueryBaseBatchResult<FormLabourCapitalDetailAllInfo> queryResult = labourCapitalServiceClient
		//				.queryPageAll(queryOrder);
		//			if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
		//				model.addAttribute("jkList", queryResult.getPageList());
		//				for (FormLabourCapitalDetailAllInfo info : queryResult.getPageList()) {
		//					jkAllAmount.addTo(info.getAmount());
		//				}
		//			}
		//			// 还款
		//			queryOrder.setExpenseType(expenseType)
		//			labourCapitalServiceClient.queryPage(queryOrder);
		//			
		//		}
		//		
		//		
		//		
		//		// 预付款
		//		Money yfkAllAmount = Money.zero();
		//		if (yfkId > 0) {
		//			LabourCapitalQueryOrder queryOrder = new LabourCapitalQueryOrder();
		//			queryOrder.setExpenseType(String.valueOf(yfkId));
		//			QueryBaseBatchResult<FormLabourCapitalDetailAllInfo> queryResult = labourCapitalServiceClient
		//				.queryPageAll(queryOrder);
		//			if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
		//				model.addAttribute("jkList", queryResult.getPageList());
		//				for (FormLabourCapitalDetailAllInfo info : queryResult.getPageList()) {
		//					yfkAllAmount.addTo(info.getAmount());
		//				}
		//			}
		//		}
		getcxDetailInfo(expenseInfo, model, false);
		return vm_path + "addForm.vm";
	}
	
	/**
	 * 审核
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, Model model) {
		doAudit(formId, request, model);
		
		return vm_path + "viewAudit.vm";
	}
	
	/**
	 * 审核【在财务分管副总驳回后，能修改费用种类和金额，金额只能修改为比之前的金额小】
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/canModify.htm")
	public String auditCanModify(long formId, HttpServletRequest request, Model model) {
		model.addAttribute("onlyChangeDetailList", BooleanEnum.YES.code());
		doAudit(formId, request, model, true);
		
		return vm_path + "viewAudit.vm";
	}
	
	/**
	 * 审核【确认打款的识别】
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("audit/canAccount.htm")
	public String auditCanAccount(long formId, HttpServletRequest request, Model model) {
		doAudit(formId, request, model, false);
		
		return vm_path + "viewAudit.vm";
	}
	
	private void doAudit(long formId, HttpServletRequest request, Model model) {
		doAudit(formId, request, model, false);
	}
	
	private void doAudit(long formId, HttpServletRequest request, Model model,
							boolean getBankMessage) {
		model.addAttribute("_SYSNAME", "FM");
		FormInfo formInfo = formServiceFmClient.findByFormId(formId);
		if (formInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "申请单不存在");
		}
		model.addAttribute("form", formInfo);
		
		FormLabourCapitalInfo info = labourCapitalServiceClient.queryByFormId(formId);
		setCxinfo(model, info, true);
		model.addAttribute("info", info);
		// 获取默认支付银行信息
		model.addAttribute("defaultPayBank",
			findDefaultPaymentBankInfo(null == info ? 0L : info.getExpenseDeptId()));
		model.addAttribute("categoryList", getCostCategoryList(formInfo, false));
		getcxDetailInfo(info, model, true);
		
		queryCommonAttachmentData(model, String.valueOf(info.getBillNo()),
			CommonAttachmentTypeEnum.FORM_ATTACH);
		initWorkflow(model, formInfo, request.getParameter("taskId"));
		
		//		boolean isFYFG = DataPermissionUtil.isFinancialYFG();
		boolean isFYFG = false;
		if (getBankMessage) {
			isFYFG = true;
			model.addAttribute("isFYFG", isFYFG);
		}
		if (isFYFG
			&& getBankMessage
			&& (StringUtil.isBlank(info.getPayBankAccount()) && StringUtil.isBlank(info
				.getPayBank()))) {
			// 先判定是否有值,有值就不抓取默认值了
			if (StringUtil.isBlank(info.getPayBankAccount())
				&& StringUtil.isBlank(info.getPayBank())) {
				getPayBank(info);
			}
		}
	}
	
	/**
	 * 获取设置还款、冲预付费财务审核信息
	 * @param model
	 * @param info
	 */
	private void setCxinfo(Model model, FormLabourCapitalInfo info, boolean isAdd) {
		String expenseType = info.getDetailList().get(0).getExpenseType();
		CostCategoryInfo costInfo = costCategoryServiceClient.queryById(Long.valueOf(expenseType));
		if ("还款".equals(costInfo.getName())) {
			model.addAttribute("hk", true);
		}
		//		List<ExpenseCxDetailInfo> cxdetailList = info.getCxdetailList();
		//		if (cxdetailList != null) {
		//			List<ExpenseCxDetailInfo> cxdetailList1 = new ArrayList<ExpenseCxDetailInfo>();
		//			List<ExpenseCxDetailInfo> cxdetailList2 = new ArrayList<ExpenseCxDetailInfo>();
		//			Money cxMoney1 = Money.zero();//预付款总金额
		//			Money cxMoney2 = Money.zero();//费用金额
		//			Money xjMoney = Money.zero();//现金
		//			Money fpMoney = Money.zero();//发票
		//			Money yfkMoney = Money.zero();//财务-预付款
		//			Money yhMoney = Money.zero();//财务-银行存款
		//			for (ExpenseCxDetailInfo cxinfo : cxdetailList) {
		//				if (cxinfo.getFromApplicationId() > 0) {
		//					cxdetailList1.add(cxinfo);
		//					//					cxMoney1 = cxMoney1.add(cxinfo.getFpAmount().add(cxinfo.getXjAmount()));
		//					cxMoney1 = cxMoney1.add(cxinfo.getBxAmount());
		//					cxMoney2 = cxMoney2.add(cxinfo.getFpAmount().add(cxinfo.getXjAmount()));
		//					
		//					if (info.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_CPREPAY) {//费用支付(冲预付款类)审批
		//					
		//						if (cxinfo.getFyAmount().compareTo(cxinfo.getFpAmount()) == 0
		//							&& cxinfo.getXjAmount().compareTo(Money.zero()) == 0) {//预付款 = 发票冲销金额 
		//							yfkMoney = yfkMoney.add(cxinfo.getFpAmount());//财务-预付款  = 发票冲销金额
		//							
		//						} else if (cxinfo.getFyAmount().compareTo(cxinfo.getXjAmount()) == 0
		//									&& cxinfo.getFpAmount().compareTo(Money.zero()) == 0) {//预付款 = 现金冲销金额 
		//							yfkMoney = yfkMoney.add(cxinfo.getXjAmount());//财务-预付款  = 现金冲销金额
		//							yhMoney = yhMoney.add(cxinfo.getXjAmount());//财务-银行存款 = 现金冲销金额
		//							
		//						} else if (cxinfo.getFyAmount().compareTo(
		//							cxinfo.getFpAmount().add(cxinfo.getXjAmount())) == 0) {//预付款 = 发票冲销金额 + 现金冲销金额
		//							yfkMoney = yfkMoney.add(cxinfo.getFpAmount().add(cxinfo.getXjAmount()));//财务-预付款  = 发票冲销金额 + 现金冲销金额
		//							yhMoney = yhMoney.add(cxinfo.getXjAmount());//财务-银行存款 = 现金冲销金额
		//							
		//						} else if (cxinfo.getFyAmount().compareTo(cxinfo.getFpAmount()) < 0) {//预付款 < 发票冲销金额 
		//							yfkMoney = yfkMoney.add(cxinfo.getFyAmount());//财务-预付款  = 预付款
		//							yhMoney = yhMoney.add(cxinfo.getFpAmount().subtract(
		//								cxinfo.getFyAmount()));//财务-银行存款 = 发票冲销金额  - 预付款
		//						} else {
		//							yfkMoney = yfkMoney.add(cxinfo.getFpAmount().add(cxinfo.getXjAmount()));//财务-预付款  = 发票冲销金额 + 现金冲销金额
		//							yhMoney = yhMoney.add(cxinfo.getXjAmount());//财务-银行存款 = 现金冲销金额
		//						}
		//					} else {
		//						if (cxinfo.getXjAmount().compareTo(Money.zero()) > 0) {
		//							xjMoney = xjMoney.add(cxinfo.getXjAmount());
		//						}
		//						fpMoney = fpMoney.add(cxinfo.getFpAmount());
		//					}
		//					
		//				} else {
		//					if (StringUtil.isNotBlank(cxinfo.getAccountCode())) {
		//						model.addAttribute("hk", true);
		//					}
		//					cxdetailList2.add(cxinfo);
		//				}
		//			}
		//			if (!cxdetailList1.isEmpty()) {
		//				model.addAttribute("cxMoney1", cxMoney1);
		//				model.addAttribute("cxinfo1", cxdetailList1);
		//			}
		//			if (!cxdetailList2.isEmpty()) {
		//				model.addAttribute("cxinfo2", cxdetailList2);
		//			} else {
		//				if (!isAdd)
		//					return;
		//				
		//				if (info.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_CPREPAY) {//费用支付(冲预付款类)审批
		//					if (yhMoney.compareTo(Money.zero()) > 0) {
		//						CostCategoryInfo cost = getCostCategory("银行存款");
		//						if (cost != null) {
		//							ExpenseCxDetailInfo yhckInfo = new ExpenseCxDetailInfo();
		//							yhckInfo.setCategory(cost.getName());
		//							yhckInfo.setCategoryId(cost.getCategoryId());
		//							yhckInfo.setAccountCode(cost.getAccountCode());
		//							yhckInfo.setAccountName(cost.getAccountName());
		//							yhckInfo.setFyAmount(yhMoney);
		//							cxdetailList2.add(yhckInfo);
		//						}
		//						model.addAttribute("hk", true);
		//					}
		//					
		//					if (yfkMoney.compareTo(Money.zero()) > 0) {
		//						CostCategoryInfo cost = getCostCategory("预付款");
		//						if (cost != null) {
		//							ExpenseCxDetailInfo yfckInfo = new ExpenseCxDetailInfo();
		//							yfckInfo.setCategory(cost.getName());
		//							yfckInfo.setCategoryId(cost.getCategoryId());
		//							yfckInfo.setAccountCode(cost.getAccountCode());
		//							yfckInfo.setAccountName(cost.getAccountName());
		//							yfckInfo.setFyAmount(yfkMoney);
		//							//							yfckInfo.setFyAmount(fpMoney);
		//							cxdetailList2.add(yfckInfo);
		//						}
		//					}
		//				} else {
		//					if (xjMoney.compareTo(Money.zero()) > 0) {
		//						CostCategoryInfo cost = getCostCategory("银行存款");
		//						if (cost != null) {
		//							ExpenseCxDetailInfo yhckInfo = new ExpenseCxDetailInfo();
		//							yhckInfo.setCategory(cost.getName());
		//							yhckInfo.setCategoryId(cost.getCategoryId());
		//							yhckInfo.setAccountCode(cost.getAccountCode());
		//							yhckInfo.setAccountName(cost.getAccountName());
		//							yhckInfo.setFyAmount(xjMoney);
		//							cxdetailList2.add(yhckInfo);
		//						}
		//						model.addAttribute("hk", true);
		//					}
		//					
		//					if (fpMoney.compareTo(Money.zero()) > 0) {
		//						CostCategoryInfo cost = getCostCategory("预付款");
		//						if (cost != null) {
		//							ExpenseCxDetailInfo yfckInfo = new ExpenseCxDetailInfo();
		//							yfckInfo.setCategory(cost.getName());
		//							yfckInfo.setCategoryId(cost.getCategoryId());
		//							yfckInfo.setAccountCode(cost.getAccountCode());
		//							yfckInfo.setAccountName(cost.getAccountName());
		//							yfckInfo.setFyAmount(cxMoney2);
		//							//							yfckInfo.setFyAmount(fpMoney);
		//							cxdetailList2.add(yfckInfo);
		//						}
		//					}
		//				}
		//				
		//				model.addAttribute("cxinfo2", cxdetailList2);
		//			}
		//		} else {
		//			if (!isAdd)
		//				return;
		//			
		//			List<ExpenseCxDetailInfo> cxdetailListadd = new ArrayList<ExpenseCxDetailInfo>();
		//			
		//			if ("还款".equals(costInfo.getName())) {
		//				model.addAttribute("cxinfo2", cxdetailListadd);
		//				CostCategoryInfo cost = getCostCategory("银行存款");
		//				if (cost != null) {
		//					ExpenseCxDetailInfo yhckInfo = new ExpenseCxDetailInfo();
		//					yhckInfo.setCategory(cost.getName());
		//					yhckInfo.setCategoryId(cost.getCategoryId());
		//					yhckInfo.setAccountCode(cost.getAccountCode());
		//					yhckInfo.setAccountName(cost.getAccountName());
		//					yhckInfo.setFyAmount(Money.zero());
		//					cxdetailListadd.add(yhckInfo);
		//				}
		//				model.addAttribute("hk", true);
		//			}
		//			if ("冲预付款".equals(costInfo.getName())) {
		//				model.addAttribute("cxinfo2", cxdetailListadd);
		//				CostCategoryInfo cost = getCostCategory("预付款");
		//				if (cost != null) {
		//					ExpenseCxDetailInfo yfckInfo = new ExpenseCxDetailInfo();
		//					yfckInfo.setCategory(cost.getName());
		//					yfckInfo.setCategoryId(cost.getCategoryId());
		//					yfckInfo.setAccountCode(cost.getAccountCode());
		//					yfckInfo.setAccountName(cost.getAccountName());
		//					yfckInfo.setFyAmount(Money.zero());
		//					cxdetailListadd.add(yfckInfo);
		//				}
		//			}
		//			
		//		}
	}
	
	private void setDetailList(LabourCapitalOrder order, HttpServletRequest request) {
		String[] expenseTypes = request.getParameterValues("expenseType");
		order.setAmount(Money.zero());
		if (expenseTypes != null && expenseTypes.length > 0) {
			String[] detailIds = request.getParameterValues("detailId");
			String[] amounts = request.getParameterValues("damount");
			String[] taxAmounts = request.getParameterValues("taxAmount");
			String[] orgNames = request.getParameterValues("orgName");
			String[] orgIds = request.getParameterValues("orgId");
			
			ArrayList<LabourCapitalDetailOrder> detailOrderList = new ArrayList<LabourCapitalDetailOrder>(
				expenseTypes.length);
			for (int i = 0; i < expenseTypes.length; i++) {
				LabourCapitalDetailOrder detailOrder = new LabourCapitalDetailOrder();
				if (StringUtil.isNotBlank(detailIds[i]))
					detailOrder.setDetailId(Long.valueOf(detailIds[i]));
				if (StringUtil.isNotBlank(amounts[i]))
					detailOrder.setAmount(new Money(amounts[i]));
				if (StringUtil.isNotBlank(taxAmounts[i]))
					detailOrder.setTaxAmount(new Money(taxAmounts[i]));
				if (StringUtil.isNotBlank(orgIds[i]))
					detailOrder.setDeptId(Long.valueOf(orgIds[i]));
				detailOrder.setDeptName(orgNames[i]);
				detailOrder.setExpenseType(expenseTypes[i]);
				detailOrderList.add(detailOrder);
				
				order.setAmount(order.getAmount().add(detailOrder.getAmount()));
			}
			order.setDetailList(detailOrderList);
		}
		
		setFormCode(order);
	}
	
	/**
	 * 根据费用类型设置审核流程
	 * @param order
	 */
	private void setFormCode(LabourCapitalOrder order) {
		FormCodeEnum curFormCodeEnum = null;
		boolean expenseApplicationCprepayGet = false;
		for (LabourCapitalDetailOrder detailOrder : order.getDetailList()) {
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
			if (projectRelatedUserServiceClient.isBelong2Dept(ShiroSessionUtils.getSessionLocal()
				.getUserId(), sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_XINHUI_DEPT_CODE.code()))) {
				curFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_JE_XH;
				
			}
		}
		order.setFormCode(curFormCodeEnum);
	}
	
	private List<CostCategoryInfo> getCostCategoryList(FormInfo formInfo, boolean isCheckXCZY) {
		
		FormCodeEnum limtFormCodeEnum = null;
		if (formInfo != null) {
			//formServiceFmClient.findByFormId(formInfo.getFormId());
			
			//			FormRelatedUserQueryOrder forder = new FormRelatedUserQueryOrder();
			//			forder.setFormId(formInfo.getFormId());
			//			long count = formRelatedUserServiceClient.queryCount(forder);
			//			if (count > 0) {//已提交，只能选择相同流程费用类型
			//				limtFormCodeEnum = formInfo.getFormCode();
			//			}
			limtFormCodeEnum = formInfo.getFormCode();
			//			// 20161221 信惠类型是金额规则类别  TODO 若信惠流程支持多种，需要先抓取一条子记录，再获取formcode
			//			if (FormCodeEnum.EXPENSE_APPLICATION_JE_XH == limtFormCodeEnum) {
			//				limtFormCodeEnum = FormCodeEnum.EXPENSE_APPLICATION_LIMIT;
			//			}
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
		
		List<String> showList = ExpenseApplicationController.notShowList;
		for (CostCategoryInfo info : batchResult.getPageList()) {
			if (info.getName().equals("差旅费"))
				continue;
			
			// 20161228 只展示自己的类别
			if (!showList.contains(info.getName())) {
				continue;
			}
			
			// 20161102 所有人都可以申请
			//			if (!isXCZY) {
			//				if ("工资、养老保险、失业保险、医疗保险、工伤保险、生育保险、住房公积金".contains(info.getName()))
			//					continue;
			//			}
			
			if (limtFormCodeEnum != null) {
				boolean isAdd = false;
				for (String key : fmap.keySet()) {
					if (key.contains("#" + info.getName() + "#")) {
						FormCodeEnum keyFormCode = fmap.get(key);
						if (limtFormCodeEnum == keyFormCode) {//已提交，只能选择相同流程费用类型
							isAdd = true;
						}
						/// 20161118 添加退预付款在页面上，由页面判定是否展示
						else if (FormCodeEnum.EXPENSE_APPLICATION_CPREPAY == keyFormCode) {
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
	
	private CostCategoryInfo getCostCategory(String costName) {
		CostCategoryQueryOrder order = new CostCategoryQueryOrder();
		order.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
			.queryPage(order);
		for (CostCategoryInfo info : batchResult.getPageList()) {
			if (info.getName().equals(costName)) {
				return info;
			}
		}
		
		return null;
	}
	
	private void getcxDetailInfo(FormLabourCapitalInfo expenseInfo, Model model, boolean audit) {
		
		String expenseApplicationId = String.valueOf(expenseInfo.getLabourCapitalId());
		String payee = expenseInfo.getPayee();
		String direction = "";
		if (expenseInfo.getDirection() != null) {
			direction = expenseInfo.getDirection().code();
		}
		String cxids = expenseInfo.getCxids();
		String agent = String.valueOf(expenseInfo.getAgentId());
		List<String> idsList = new ArrayList<String>();
		if (StringUtil.isNotBlank(cxids)) {
			String[] strs = cxids.split(",");
			for (String s : strs) {
				idsList.add(s);
			}
		}
		long jkId = 0;
		long yfkId = 0;
		long hkId = 0;
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
			Money waitAuditReverseAmount = Money.zero();
			if (yfkId > 0) {
				LabourCapitalQueryOrder queryOrder = new LabourCapitalQueryOrder();
				queryOrder.setPageSize(10000);
				queryOrder.setExpenseType(String.valueOf(yfkId));
				if (!audit) {
					queryOrder.setPayee(payee);
				}
				//				queryOrder
				//					.setAgent(String.valueOf(ShiroSessionUtils.getSessionLocal().getUserId()));
				queryOrder.setAgent(agent);
				// 查询审核通过的
				List<FormStatusEnum> formStatuss = new ArrayList<FormStatusEnum>();
				formStatuss.add(FormStatusEnum.APPROVAL);
				queryOrder.setFormStatusList(formStatuss);
				QueryBaseBatchResult<FormLabourCapitalDetailAllInfo> queryResult = labourCapitalServiceClient
					.queryPageAll(queryOrder);
				List<FormLabourCapitalDetailAllInfo> waitReverseList = new ArrayList<FormLabourCapitalDetailAllInfo>();
				List<FormLabourCapitalDetailAllInfo> waitAuditReverseList = new ArrayList<FormLabourCapitalDetailAllInfo>();
				if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
					
					for (FormLabourCapitalDetailAllInfo info : queryResult.getPageList()) {
						// 20161119  审核的时候只需要抓取已被自己选中的记录
						// 20161122 审核时剩余未冲销金额应该锁定为已选取的记录
						if (idsList.contains(String.valueOf(info.getDetailId()))) {
							waitAuditReverseList.add(info);
							waitAuditReverseAmount.addTo(info.getAmount());
						}
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
							
							// 20161118 判断未冲销的记录是否有曾经选择的记录，有就加上已选择标记
							if (idsList.contains(String.valueOf(info.getDetailId()))) {
								info.setReverse(BooleanEnum.YES);
							}
							
							waitReverseList.add(info);
						}
						for (CostCategoryInfo cost : batchResult.getPageList()) {
							if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
								&& String.valueOf(cost.getCategoryId()).equals(
									info.getExpenseType())) {
								info.setExpenseType(cost.getName());
								break;
							}
						}
						
					}
				}
				// 20161122 审核时剩余未冲销金额应该锁定为已选取的记录
				if (audit) {
					model.addAttribute("waitReverseAmount", waitAuditReverseAmount);
				} else {
					model.addAttribute("waitReverseAmount", waitReverseAmount);
				}
				model.addAttribute("totalAmount", yfkAllAmount);
				model.addAttribute("usedAmount", tyfkAllAmount);
				model.addAttribute("cxPublicDetailInfos", waitReverseList);
				// 20161123 审核的时候需要有列表数据才展示,同时需要已选中冲销才展示
				if (BooleanEnum.YES.code().equals(expenseInfo.getIsReverse())) {
					model.addAttribute("cxPublicDetailAuditInfos", waitAuditReverseList);
				}
				
				// 若无需冲销，置为 不冲销
				if (!audit && !waitReverseAmount.greaterThan(Money.zero())) {
					expenseInfo.setIsReverse(BooleanEnum.NO.code());
				}
			}
		} else if (CostDirectionEnum.PRIVATE.code().equals(direction)) {
			// 借款
			Money jkAllAmount = Money.zero();
			Money hkAllAmount = Money.zero();
			Money waitReverseAmount = Money.zero();
			if (jkId > 0) {
				LabourCapitalQueryOrder queryOrder = new LabourCapitalQueryOrder();
				queryOrder.setPageSize(10000);
				queryOrder.setExpenseType(String.valueOf(jkId));
				queryOrder.setPayee(payee);
				//				queryOrder
				//					.setAgent(String.valueOf(ShiroSessionUtils.getSessionLocal().getUserId()));
				queryOrder.setAgent(agent);
				// 查询审核通过的
				List<FormStatusEnum> formStatuss = new ArrayList<FormStatusEnum>();
				formStatuss.add(FormStatusEnum.APPROVAL);
				queryOrder.setFormStatusList(formStatuss);
				QueryBaseBatchResult<FormLabourCapitalDetailAllInfo> queryResult = labourCapitalServiceClient
					.queryPageAll(queryOrder);
				List<FormLabourCapitalDetailAllInfo> waitAuditReverseList = new ArrayList<FormLabourCapitalDetailAllInfo>();
				if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
					for (FormLabourCapitalDetailAllInfo info : queryResult.getPageList()) {
						
						jkAllAmount.addTo(info.getAmount());
						for (CostCategoryInfo cost : batchResult.getPageList()) {
							if ((cost.getCategoryId() > 0 || cost.getCategoryId() == -1)
								&& String.valueOf(cost.getCategoryId()).equals(
									info.getExpenseType())) {
								info.setExpenseType(cost.getName());
								break;
							}
						}
					}
				}
				model.addAttribute("cxPrivateDetailInfos", queryResult.getPageList());
				// 20161123 审核的时候需要有列表数据才展示,同时需要已选中冲销才展示
				if (BooleanEnum.YES.code().equals(expenseInfo.getIsReverse())) {
					model.addAttribute("cxPrivateDetailAuditInfos", queryResult.getPageList());
					//				model.addAttribute("cxPrivateDetailAuditInfos", waitAuditReverseList);
				}
				// 还款
				queryOrder.setExpenseType(String.valueOf(hkId));
				formStatuss.clear();
				formStatuss.add(FormStatusEnum.DRAFT);
				formStatuss.add(FormStatusEnum.SUBMIT);
				//				formStatuss.add(FormStatusEnum.CANCEL);
				formStatuss.add(FormStatusEnum.AUDITING);
				//				formStatuss.add(FormStatusEnum.BACK);
				formStatuss.add(FormStatusEnum.APPROVAL);
				queryOrder.setFormStatusList(formStatuss);
				queryResult = labourCapitalServiceClient.queryPageAll(queryOrder);
				if (queryResult != null && ListUtil.isNotEmpty(queryResult.getPageList())) {
					for (FormLabourCapitalDetailAllInfo info : queryResult.getPageList()) {
						// 剔除自己的还款数据  发现是自己的数据，不计算入已还款
						hkAllAmount.addTo(info.getAmount());
						//						if (StringUtil.equals(expenseApplicationId,
						//							String.valueOf(info.getExpenseApplicationId()))) {
						//							
						//						} else {
						//							
						//						}
					}
				}
				/// 审核中且自己，才将金额加回去，用于兼容审核页面的修改
				if (FormStatusEnum.DRAFT == expenseInfo.getFormStatus()
					|| FormStatusEnum.SUBMIT == expenseInfo.getFormStatus()
					|| FormStatusEnum.AUDITING == expenseInfo.getFormStatus()
					|| FormStatusEnum.APPROVAL == expenseInfo.getFormStatus()) {
					// 20161123 剔除自己的还款数据  发现是自己的数据，不计算入已还款 减去自己的待还款记录
					for (FormLabourCapitalDetailInfo detailInfo : expenseInfo.getDetailList()) {
						if (StringUtil.equals(String.valueOf(hkId), detailInfo.getExpenseType())) {
							hkAllAmount.subtractFrom(detailInfo.getAmount());
						}
					}
				}
				
				waitReverseAmount = jkAllAmount.subtract(hkAllAmount);
			}
			
			model.addAttribute("waitReverseAmount", waitReverseAmount);
			model.addAttribute("totalAmount", jkAllAmount);
			model.addAttribute("usedAmount", hkAllAmount);
			
			// 若无需冲销，置为 不冲销
			if (!audit && !waitReverseAmount.greaterThan(Money.zero())) {
				expenseInfo.setIsReverse(BooleanEnum.NO.code());
			}
			
		}
	}
	
}
