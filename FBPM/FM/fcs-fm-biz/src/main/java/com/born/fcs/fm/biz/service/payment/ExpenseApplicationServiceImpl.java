package com.born.fcs.fm.biz.service.payment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.biz.convert.UnBoxingConverter;
import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.CostCategoryDO;
import com.born.fcs.fm.dal.dataobject.ExpenseCxDetailDO;
import com.born.fcs.fm.dal.dataobject.FormDO;
import com.born.fcs.fm.dal.dataobject.FormExpenseApplicationDO;
import com.born.fcs.fm.dal.dataobject.FormExpenseApplicationDetailDO;
import com.born.fcs.fm.dal.dataobject.FormPayChangeDetailDO;
import com.born.fcs.fm.dal.queryCondition.CostCategoryQueryCondition;
import com.born.fcs.fm.dal.queryCondition.ExpenseApplyQueryCondition;
import com.born.fcs.fm.dataobject.FormExpenseApplicationDetailAllFormDO;
import com.born.fcs.fm.dataobject.FormExpenseApplicationFormDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.risk.service.KingdeeTogetheFacadeClient;
import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.BranchPayStatusEnum;
import com.born.fcs.fm.ws.enums.ChangeSourceTypeEnum;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.forecast.SysForecastParamAllInfo;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.ExpenseCxDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailAllInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.info.payment.FormPayChangeDetailInfo;
import com.born.fcs.fm.ws.order.common.SyncKingdeeBasicDataOrder;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.order.payment.ConfirmBranchPayOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationDetailOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationQueryOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseCxDetailOrder;
import com.born.fcs.fm.ws.result.bank.BankMessageResult;
import com.born.fcs.fm.ws.result.forecast.SysForecastParamResult;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.fm.ws.service.payment.BudgetService;
import com.born.fcs.fm.ws.service.payment.CostCategoryService;
import com.born.fcs.fm.ws.service.payment.ExpenseApplicationService;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.common.FormRelatedUserOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.bornsoft.pub.order.kingdee.KingdeeExpensesRequisitionsOrder;
import com.bornsoft.pub.order.kingdee.KingdeeExpensesRequisitionsOrder.ExpensesRequisitionsInfo;
import com.bornsoft.pub.result.kingdee.KingdeeExpensesRequisitionsResult;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("expenseApplicationService")
public class ExpenseApplicationServiceImpl extends BaseFormAutowiredDomainService implements
																					ExpenseApplicationService {
	
	@Autowired
	protected BudgetService budgetService;
	
	@Autowired
	protected CostCategoryService costCategoryService;
	
	@Autowired
	private KingdeeTogetheFacadeClient kingdeeTogetheFacadeClient;
	
	@Autowired
	ForecastService forecastService;
	
	@Autowired
	BankMessageService bankMessageService;
	
	@Autowired
	FormRelatedUserService formRelatedUserService;
	
	@Override
	public QueryBaseBatchResult<FormExpenseApplicationInfo> queryPage(	ExpenseApplicationQueryOrder order) {
		QueryBaseBatchResult<FormExpenseApplicationInfo> batchResult = new QueryBaseBatchResult<FormExpenseApplicationInfo>();
		
		try {
			ExpenseApplyQueryCondition condition = getQueryCondition(order);
			
			long totalCount = busiDAO.findExpenseByConditionCount(condition);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<FormExpenseApplicationFormDO> list = busiDAO.findExpenseByCondition(condition);
			
			List<FormExpenseApplicationInfo> pageList = new ArrayList<FormExpenseApplicationInfo>(
				list.size());
			for (FormExpenseApplicationFormDO DO : list) {
				FormExpenseApplicationInfo info = new FormExpenseApplicationInfo();
				setFormExpenseApplicationInfo(DO, info, order.isDetail());
				pageList.add(info);
			}
			
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询费用支付申请单列表失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FormExpenseApplicationInfo queryByFormId(long formId) {
		FormExpenseApplicationDO formExpenseApplication = formExpenseApplicationDAO
			.findByFormId(formId);
		if (formExpenseApplication != null) {
			FormExpenseApplicationInfo formExpenseApplicationInfo = new FormExpenseApplicationInfo();
			setFormExpenseApplicationInfo(formExpenseApplication, formExpenseApplicationInfo, true);
			return formExpenseApplicationInfo;
		}
		return null;
	}
	
	@Override
	public FormExpenseApplicationInfo queryById(long expenseApplicationId) {
		FormExpenseApplicationDO formExpenseApplication = formExpenseApplicationDAO
			.findById(expenseApplicationId);
		if (formExpenseApplication != null) {
			FormExpenseApplicationInfo formExpenseApplicationInfo = new FormExpenseApplicationInfo();
			setFormExpenseApplicationInfo(formExpenseApplication, formExpenseApplicationInfo, true);
			return formExpenseApplicationInfo;
		}
		return null;
	}
	
	private void setChangeDetail(ExpenseApplicationOrder order) {
		//		List<FormPayChangeDetailDO> changeDOs = new ArrayList<FormPayChangeDetailDO>();
		
		FormPayChangeDetailDO changeDO = new FormPayChangeDetailDO();
		changeDO.setSourceType(ChangeSourceTypeEnum.EXPENSE.code());
		changeDO.setUserId(order.getUserId());
		changeDO.setUserName(order.getUserName());
		changeDO.setDocumentType("input");
		
		FormExpenseApplicationDO expenseDO = formExpenseApplicationDAO.findById(order
			.getExpenseApplicationId());
		//1. 金额
		if (expenseDO.getAmount() != order.getAmount()) {
			changeDO.setDocumentName("amount");
			changeDO.setDocumentDescribe("金额合计");
			changeDO.setDocumentValueOld(expenseDO.getAmount().toStandardString());
			changeDO.setDocumentValueNew(order.getAmount().toStandardString());
			//				changeDOs.add(changeDO);
			formPayChangeDetailDAO.insert(changeDO);
			
		}
		// 报销事由
		if (!expenseDO.getReimburseReason().equals(order.getReimburseReason())) {
			changeDO.setDocumentName("reimburseReason");
			changeDO.setDocumentDescribe("报销事由");
			changeDO.setDocumentValueOld(expenseDO.getReimburseReason());
			changeDO.setDocumentValueNew(order.getReimburseReason());
			//				changeDOs.add(changeDO);
			formPayChangeDetailDAO.insert(changeDO);
		}
		
		for (ExpenseApplicationDetailOrder detailOrder : order.getDetailList()) {
			FormExpenseApplicationDetailDO detailDO = formExpenseApplicationDetailDAO
				.findById(detailOrder.getDetailId());
			changeDO.setSourceId(detailDO.getDetailId());
			//1. 费用类型
			if (StringUtil.notEquals(detailDO.getExpenseType(), detailOrder.getExpenseType())) {
				changeDO.setDocumentName("expenseType");
				changeDO.setDocumentDescribe("费用类型");
				changeDO.setDocumentValueOld(detailDO.getExpenseType());
				changeDO.setDocumentValueNew(detailOrder.getExpenseType());
				//				changeDOs.add(changeDO);
				formPayChangeDetailDAO.insert(changeDO);
			}
			// 2. 金额
			if (detailDO.getAmount() != detailOrder.getAmount()) {
				changeDO.setDocumentName("amount");
				changeDO.setDocumentDescribe("金额");
				changeDO.setDocumentValueOld(detailDO.getAmount().toStandardString());
				changeDO.setDocumentValueNew(detailOrder.getAmount().toStandardString());
				//				changeDOs.add(changeDO);
				formPayChangeDetailDAO.insert(changeDO);
			}
			
			// 3. 税额
			if (detailDO.getTaxAmount() != detailOrder.getTaxAmount()) {
				changeDO.setDocumentName("taxAmount");
				changeDO.setDocumentDescribe("税费");
				changeDO.setDocumentValueOld(detailDO.getTaxAmount().toStandardString());
				changeDO.setDocumentValueNew(detailOrder.getTaxAmount().toStandardString());
				//				changeDOs.add(changeDO);
				formPayChangeDetailDAO.insert(changeDO);
			}
		}
	}
	
	@Override
	public FormBaseResult save(final ExpenseApplicationOrder order) {
		return commonFormSaveProcess(order, "费用支付申请单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				if (order.getDetailList() == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"费用支付申请单明细不存在");
				}
				
				// 20161104 添加判断，若为onlyChangeDetailList 只修改明细
				if (BooleanEnum.YES == order.getOnlyChangeDetailList()) {
					for (ExpenseApplicationDetailOrder detailOrder : order.getDetailList()) {
						FormExpenseApplicationDetailDO detailDO = formExpenseApplicationDetailDAO
							.findById(detailOrder.getDetailId());
						// 能修改种类和金额，金额只能比以前的小
						
						detailDO.setExpenseType(detailOrder.getExpenseType());
						
						if (detailOrder.getAmount().greaterThan(detailDO.getAmount())) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, "金额不能比之前的大！");
						}
						detailDO.setAmount(detailOrder.getAmount());
						
						if (detailOrder.getTaxAmount().greaterThan(detailDO.getTaxAmount())) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, "金额不能比之前的大！");
						}
						detailDO.setTaxAmount(detailOrder.getTaxAmount());
						formExpenseApplicationDetailDAO.update(detailDO);
					}
					// 更定主表金额
					FormExpenseApplicationDO expenseDO = formExpenseApplicationDAO.findById(order
						.getExpenseApplicationId());
					if (order.getAmount().greaterThan(Money.zero())) {
						expenseDO.setAmount(order.getAmount());
					}
					// 更定报销事由
					if (StringUtil.isNotBlank(order.getReimburseReason())) {
						expenseDO.setReimburseReason(order.getReimburseReason());
					}
					formExpenseApplicationDAO.update(expenseDO);
					
					setChangeDetail(order);
					return null;
					
				}
				
				checkDeptBalance(order);//预算check
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				
				FormInfo form = (FormInfo) FcsFmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				List<ExpenseCxDetailOrder> cxDetailOrders = order.getCxDetailList();
				if (cxDetailOrders != null) {
					Money cxAmount = Money.zero();
					for (ExpenseCxDetailOrder cxdetailOrder : cxDetailOrders) {
						cxAmount = cxAmount.add(cxdetailOrder.getXjAmount().add(
							cxdetailOrder.getFpAmount()));
					}
					if (cxAmount.compareTo(Money.zero()) <= 0) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"冲销金额不能为0");
					}
					
					if (form.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_REFUND) {
						for (ExpenseCxDetailOrder cxdetailOrder : cxDetailOrders) {
							if (cxdetailOrder.getFpAmount().compareTo(Money.zero()) > 0) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
									"借款只可现金冲销");
							}
						}
					}
					if (form.getFormCode() != FormCodeEnum.EXPENSE_APPLICATION_CPREPAY) {
						for (ExpenseCxDetailOrder cxdetailOrder : cxDetailOrders) {
							FormExpenseApplicationDetailDO detailDO = formExpenseApplicationDetailDAO
								.findById(cxdetailOrder.getFromDetailId());
							if (detailDO.getAmount().compareTo(
								detailDO
									.getFpAmount()
									.add(detailDO.getXjAmount())
									.add(
										cxdetailOrder.getFpAmount()
											.add(cxdetailOrder.getXjAmount()))) < 0) {
								CostCategoryInfo costInfo = costCategoryService.queryById(Long
									.valueOf(detailDO.getExpenseType()));
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
									"【" + costInfo.getName() + "】剩余可冲销金额不足");
							}
						}
					}
					
				}
				
				// 20161116 新版预付款不需要这个判定了
				//				else {
				//					if (form.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_CPREPAY) {
				//						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
				//							"无预付款信息");
				//					}
				//					if (form.getFormCode() == FormCodeEnum.EXPENSE_APPLICATION_REFUND) {
				//						throw ExceptionFactory
				//							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "无借款信息");
				//					}
				//				}
				
				FormBaseResult result = (FormBaseResult) FcsFmDomainHolder.get().getAttribute(
					"result");
				
				// 20161114添加判断。若选择了公务卡，删除掉对公对私判定
				if (BooleanEnum.IS == order.getIsOfficialCard()) {
					order.setDirection(null);
				}
				
				FormExpenseApplicationDO formExpenseApplication = formExpenseApplicationDAO
					.findByFormId(form.getFormId());
				if (formExpenseApplication == null) {
					formExpenseApplication = new FormExpenseApplicationDO();
					BeanCopier.staticCopy(order, formExpenseApplication,
						UnBoxingConverter.getInstance());
					formExpenseApplication.setReamount(order.getReamount());
					if (order.getIsOfficialCard() != null) {
						formExpenseApplication.setIsOfficialCard(order.getIsOfficialCard().code());
					}
					if (order.getDirection() != null) {
						formExpenseApplication.setDirection(order.getDirection().code());
					}
					formExpenseApplication.setDeptName(order.getExpenseDeptName());
					formExpenseApplication.setFormId(form.getFormId());
					
					formExpenseApplication.setBillNo(genBillNo(order.getDetailList()));
					formExpenseApplication.setVoucherStatus(VoucherStatusEnum.NOT_SEND.code());
					if (StringUtil.isBlank(formExpenseApplication.getAccountStatus())) {
						formExpenseApplication.setAccountStatus(AccountStatusEnum.DRAFT.code());
					}
					formExpenseApplication.setRawAddTime(now);
					long expenseApplicationId = formExpenseApplicationDAO
						.insert(formExpenseApplication);
					FormExpenseApplicationInfo info = new FormExpenseApplicationInfo();
					info.setExpenseApplicationId(expenseApplicationId);
					info.setBillNo(formExpenseApplication.getBillNo());
					result.setReturnObject(info);
					
					List<ExpenseApplicationDetailOrder> detailOrders = order.getDetailList();
					for (ExpenseApplicationDetailOrder detailOrder : detailOrders) {
						FormExpenseApplicationDetailDO detail = new FormExpenseApplicationDetailDO();
						BeanCopier.staticCopy(detailOrder, detail, UnBoxingConverter.getInstance());
						detail.setExpenseApplicationId(expenseApplicationId);
						detail.setRawAddTime(now);
						formExpenseApplicationDetailDAO.insert(detail);
					}
					
					if (cxDetailOrders != null) {
						for (ExpenseCxDetailOrder cxdetailOrder : cxDetailOrders) {
							ExpenseCxDetailDO expenseCxDetail = new ExpenseCxDetailDO();
							BeanCopier.staticCopy(cxdetailOrder, expenseCxDetail,
								UnBoxingConverter.getInstance());
							
							if (expenseCxDetail.getFromDetailId() > 0) {
								setCategory(expenseCxDetail);
							}
							
							expenseCxDetail.setExpenseApplicationId(expenseApplicationId);
							expenseCxDetail.setFromType(expenseCxDetail.getFromApplicationId() == 0 ? "type2"
								: "type1");
							//							expenseCxDetail.setFpAmount(expenseCxDetail.getXjAmount().compareTo(
							//								Money.zero()) > 0 ? Money.zero() : expenseCxDetail.getFpAmount());
							expenseCxDetail.setRawAddTime(now);
							expenseCxDetailDAO.insert(expenseCxDetail);
						}
					}
					
					result.setMessage(formExpenseApplication.getBillNo());
				} else {
					long expenseApplicationId = formExpenseApplication.getExpenseApplicationId();
					formExpenseApplication.setAttachmentsNum(order.getAttachmentsNum());
					formExpenseApplication.setExpenseDeptId(order.getExpenseDeptId());
					formExpenseApplication.setDeptName(order.getExpenseDeptName());
					formExpenseApplication.setDeptHead(order.getDeptHead());
					formExpenseApplication.setApplicationTime(order.getApplicationTime());
					formExpenseApplication.setRelationForm(order.getRelationForm());
					formExpenseApplication.setAgent(order.getAgent());
					if (order.getDirection() != null) {
						formExpenseApplication.setDirection(order.getDirection().code());
						if (order.getDirection() == CostDirectionEnum.PRIVATE) {
							order.setBankId(0);
						} else {
							order.setPayeeId(0);
						}
					}
					formExpenseApplication.setBankId(order.getBankId());
					formExpenseApplication.setPayeeId(order.getPayeeId());
					formExpenseApplication.setPayee(order.getPayee());
					formExpenseApplication.setBank(order.getBank());
					formExpenseApplication.setBankAccount(order.getBankAccount());
					formExpenseApplication.setAmount(order.getAmount());
					formExpenseApplication.setIsReverse(order.getIsReverse());
					formExpenseApplication.setReamount(order.getReamount());
					formExpenseApplication.setCxids(order.getCxids());
					formExpenseApplication.setReimburseReason(order.getReimburseReason());
					if (order.getIsOfficialCard() != null) {
						formExpenseApplication.setIsOfficialCard(order.getIsOfficialCard().code());
					}
					if (StringUtil.isBlank(formExpenseApplication.getAccountStatus())) {
						formExpenseApplication.setAccountStatus(AccountStatusEnum.DRAFT.code());
					}
					formExpenseApplicationDAO.update(formExpenseApplication);
					FormExpenseApplicationInfo info = new FormExpenseApplicationInfo();
					info.setExpenseApplicationId(formExpenseApplication.getExpenseApplicationId());
					info.setBillNo(formExpenseApplication.getBillNo());
					result.setReturnObject(info);
					
					/** 明细 **/
					List<FormExpenseApplicationDetailDO> oleDetailDOs = formExpenseApplicationDetailDAO
						.findByExpenseApplicationId(expenseApplicationId);
					List<ExpenseApplicationDetailOrder> detailOrders = order.getDetailList();
					for (ExpenseApplicationDetailOrder detailOrder : detailOrders) {
						if (detailOrder.getDetailId() == 0) {
							//新增
							FormExpenseApplicationDetailDO detail = new FormExpenseApplicationDetailDO();
							BeanCopier.staticCopy(detailOrder, detail,
								UnBoxingConverter.getInstance());
							detail.setExpenseApplicationId(expenseApplicationId);
							detail.setRawAddTime(now);
							formExpenseApplicationDetailDAO.insert(detail);
						} else {
							//更新
							for (int i = 0; i < oleDetailDOs.size(); i++) {
								FormExpenseApplicationDetailDO updateDetailDO = oleDetailDOs.get(i);
								if (detailOrder.getDetailId() == updateDetailDO.getDetailId()) {
									updateDetailDO.setDeptId(detailOrder.getDeptId());
									updateDetailDO.setDeptName(detailOrder.getDeptName());
									updateDetailDO.setAmount(detailOrder.getAmount());
									updateDetailDO.setExpenseType(detailOrder.getExpenseType());
									updateDetailDO.setTaxAmount(detailOrder.getTaxAmount());
									updateDetailDO.setDeptId(detailOrder.getDeptId());
									formExpenseApplicationDetailDAO.update(updateDetailDO);
									
									//移除已更新的数据，剩下需要删除的数据
									oleDetailDOs.remove(i);
									break;
								}
							}
						}
					}
					
					//删除
					for (FormExpenseApplicationDetailDO deletDo : oleDetailDOs) {
						formExpenseApplicationDetailDAO.deleteById(deletDo.getDetailId());
					}
					
					/** 冲销明细 **/
					List<ExpenseCxDetailDO> oleCxDetailDOs = expenseCxDetailDAO.findByExpenseId(
						expenseApplicationId, null, null);
					List<ExpenseCxDetailOrder> cxdetailOrders = order.getCxDetailList();
					if (cxdetailOrders != null) {
						for (ExpenseCxDetailOrder detailOrder : cxdetailOrders) {
							if (detailOrder.getDetailId() == 0) {
								//新增
								ExpenseCxDetailDO detail = new ExpenseCxDetailDO();
								BeanCopier.staticCopy(detailOrder, detail,
									UnBoxingConverter.getInstance());
								if (detail.getFromDetailId() > 0) {
									setCategory(detail);
								}
								detail.setExpenseApplicationId(expenseApplicationId);
								detail.setFromType(detail.getFromApplicationId() == 0 ? "type2"
									: "type1");
								//								detail.setFpAmount(detail.getXjAmount().compareTo(Money.zero()) > 0 ? Money
								//									.zero() : detail.getFpAmount());
								detail.setRawAddTime(now);
								expenseCxDetailDAO.insert(detail);
							} else {
								//更新
								for (int i = 0; i < oleCxDetailDOs.size(); i++) {
									ExpenseCxDetailDO updateDetailDO = oleCxDetailDOs.get(i);
									if (detailOrder.getDetailId() == updateDetailDO.getDetailId()) {
										if (updateDetailDO.getFromDetailId() > 0) {
											setCategory(updateDetailDO);
										}
										updateDetailDO.setFpAmount(detailOrder.getFpAmount());
										updateDetailDO.setXjAmount(detailOrder.getXjAmount());
										//										updateDetailDO.setFpAmount(updateDetailDO.getXjAmount()
										//											.compareTo(Money.zero()) > 0 ? Money.zero()
										//											: updateDetailDO.getFpAmount());
										expenseCxDetailDAO.update(updateDetailDO);
										
										//移除已更新的数据，剩下需要删除的数据
										oleCxDetailDOs.remove(i);
										break;
									}
								}
							}
						}
					}
					
					//删除
					if (oleCxDetailDOs != null) {
						for (ExpenseCxDetailDO deletDo : oleCxDetailDOs) {
							expenseCxDetailDAO.deleteById(deletDo.getDetailId());
						}
					}
					
					result.setMessage(formExpenseApplication.getBillNo());
				}
				
				// 20161114 添加功能，已冲销的id需要更定为已冲销
				if (ListUtil.isNotEmpty(order.getReverseIds())) {
					for (String str : order.getReverseIds()) {
						FormExpenseApplicationDetailDO detailDO = formExpenseApplicationDetailDAO
							.findById(Long.valueOf(str));
						if (detailDO != null) {
							detailDO.setReverse(BooleanEnum.YES.code());
							formExpenseApplicationDetailDAO.update(detailDO);
						}
						
					}
				}
				
				//保存一下表单的相关人员
				FormRelatedUserOrder relatedUser = new FormRelatedUserOrder();
				relatedUser.setFormId(form.getFormId());
				relatedUser.setFormCode(form.getFormCode());
				relatedUser.setTaskId(0);
				relatedUser.setUserType(FormRelatedUserTypeEnum.OTHER);
				relatedUser.setRemark("【" + form.getFormName() + "】" + "表单暂存人");
				relatedUser.setUserId(form.getUserId());
				relatedUser.setUserAccount(form.getUserAccount());
				relatedUser.setUserName(form.getUserName());
				relatedUser.setUserMobile(form.getUserMobile());
				relatedUser.setUserEmail(form.getUserEmail());
				relatedUser.setExeStatus(ExeStatusEnum.NOT_TASK);
				formRelatedUserService.setRelatedUser(relatedUser);
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			@Override
			public Domain after(Domain domain) {
				return null;
			}
		});
	}
	
	private void setCategory(ExpenseCxDetailDO expenseCxDetail) {
		if (expenseCxDetail.getFromDetailId() > 0) {
			FormExpenseApplicationDetailDO detailDO = formExpenseApplicationDetailDAO
				.findById(expenseCxDetail.getFromDetailId());
			if (detailDO != null) {
				CostCategoryInfo costInfo = costCategoryService.queryById(Long.valueOf(detailDO
					.getExpenseType()));
				expenseCxDetail.setCategory(costInfo.getName());
				expenseCxDetail.setCategoryId(costInfo.getCategoryId());
				expenseCxDetail.setAccountCode(costInfo.getAccountCode());
				expenseCxDetail.setAccountName(costInfo.getAccountName());
			}
		}
	}
	
	/** 劳资 */
	final static List<String> lzList = new ArrayList<String>();
	/** 税金 */
	final static List<String> sjList = new ArrayList<String>();
	static {
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
		sjList.add("房产税");
		sjList.add("车船使用税");
		sjList.add("土地使用税");
		sjList.add("印花税");
	}
	
	/**
	 * 生成单据号
	 * @param busiType
	 * @return
	 */
	private String genBillNo(List<ExpenseApplicationDetailOrder> orders) {
		//7、差旅费报销单/费用支付申请单单据号更改：字母（缩写）+6位流水号；
		String str = "BX";
		
		boolean allLZSJ = true;
		CostCategoryQueryCondition costCondition = new CostCategoryQueryCondition();
		List<String> statusList = new ArrayList<>();
		statusList.add(CostCategoryStatusEnum.NORMAL.code());
		costCondition.setStatusList(statusList);
		costCondition.setPageSize(10000L);
		List<CostCategoryDO> catgoryDOs = costCategoryDAO.findByCondition(costCondition);
		for (ExpenseApplicationDetailOrder order : orders) {
			String expenseName = "";
			if (ListUtil.isNotEmpty(catgoryDOs)) {
				for (CostCategoryDO catgoryDO : catgoryDOs) {
					if (StringUtil.equals(String.valueOf(catgoryDO.getCategoryId()),
						order.getExpenseType())) {
						expenseName = catgoryDO.getName();
						break;
					}
				}
			}
			if (lzList.contains(expenseName) || sjList.contains(expenseName)) {
				// 代表是劳资税金类别
				continue;
			} else {
				// 代表不是
				allLZSJ = false;
				break;
			}
		}
		if (allLZSJ) {
			str = "ZF";
		}
		
		// 若是劳资类或税金
		ExpenseApplyQueryCondition condition = new ExpenseApplyQueryCondition();
		condition.setBillNo(str + "%");
		long count = formExpenseApplicationDAO.findByConditionCount(condition);
		String billNo = str + StringUtil.alignRight((count + 1) + "", 6, '0');
		return billNo;
	}
	
	@SuppressWarnings("deprecation")
	private void setFormExpenseApplicationInfo(FormExpenseApplicationDO DO,
												FormExpenseApplicationInfo info, boolean isDetail) {
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		if (StringUtil.isNotBlank(DO.getIsOfficialCard())) {
			info.setIsOfficialCard(BooleanEnum.getByCode(DO.getIsOfficialCard()));
		}
		if (StringUtil.isNotBlank(DO.getDirection())) {
			info.setDirection(CostDirectionEnum.getByCode(DO.getDirection()));
		}
		if (StringUtil.isNotBlank(DO.getVoucherStatus()))
			info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
		
		info.setBranchPayStatus(BranchPayStatusEnum.getByCode(DO.getBranchPayStatus()));
		
		FormDO formDO = formDAO.findByFormId(DO.getFormId());
		if (StringUtil.isNotBlank(formDO.getStatus()))
			info.setFormStatus(FormStatusEnum.getByCode(formDO.getStatus()));
		info.setFormDeptName(formDO.getDeptName());
		info.setFormUserName(formDO.getUserName());
		info.setFormUserId(formDO.getUserId());
		info.setFormCode(FormCodeEnum.getByCode(formDO.getFormCode()));
		info.setActDefId(formDO.getActDefId());
		info.setActInstId(formDO.getActInstId());
		info.setFormUrl(formDO.getFormUrl());
		info.setRunId(formDO.getRunId());
		info.setTaskId(formDO.getTaskId());
		info.setTaskUserData(formDO.getTaskUserData());
		info.setRawUpdateTime(formDO.getRawUpdateTime());
		
		if (isDetail) {
			List<FormExpenseApplicationDetailDO> detailDOs = formExpenseApplicationDetailDAO
				.findByExpenseApplicationId(DO.getExpenseApplicationId());
			if (detailDOs != null && detailDOs.size() > 0) {
				List<FormExpenseApplicationDetailInfo> detailList = new ArrayList<FormExpenseApplicationDetailInfo>(
					detailDOs.size());
				for (FormExpenseApplicationDetailDO detailDO : detailDOs) {
					FormExpenseApplicationDetailInfo detailInfo = new FormExpenseApplicationDetailInfo();
					BeanCopier.staticCopy(detailDO, detailInfo, UnBoxingConverter.getInstance());
					
					if (StringUtil.isNotBlank(detailDO.getExpenseType())
						&& info.getApplicationTime() != null) {
						Money balance = budgetService.queryBalanceByDeptCategoryId(
							detailDO.getDeptId(), Long.valueOf(detailDO.getExpenseType()),
							info.getApplicationTime(), false);
						detailInfo.setBalanceAmount(balance == null ? Money.zero() : balance);
					}
					
					detailList.add(detailInfo);
				}
				info.setDetailList(detailList);
			}
			
			List<ExpenseCxDetailDO> cxDetailDOs = expenseCxDetailDAO.findByExpenseId(
				DO.getExpenseApplicationId(), null, null);
			if (cxDetailDOs != null && cxDetailDOs.size() > 0) {
				List<ExpenseCxDetailInfo> detailList = new ArrayList<ExpenseCxDetailInfo>(
					cxDetailDOs.size());
				for (ExpenseCxDetailDO detailDO : cxDetailDOs) {
					ExpenseCxDetailInfo detailInfo = new ExpenseCxDetailInfo();
					BeanCopier.staticCopy(detailDO, detailInfo, UnBoxingConverter.getInstance());
					if (detailInfo.getFromApplicationId() > 0) {
						FormExpenseApplicationDO applicationDO = formExpenseApplicationDAO
							.findById(detailInfo.getFromApplicationId());
						if (applicationDO != null) {
							detailInfo.setBillno(applicationDO.getBillNo());
							detailInfo.setApplyDate(DateUtil.dtSimpleFormat(applicationDO
								.getApplicationTime()));
						}
						
						FormExpenseApplicationDetailDO adetailDO = formExpenseApplicationDetailDAO
							.findById(detailInfo.getFromDetailId());
						if (adetailDO != null) {
							CostCategoryInfo costInfo = costCategoryService.queryById(Long
								.valueOf(adetailDO.getExpenseType()));
							detailInfo.setCategory(costInfo == null ? "" : costInfo.getName());
							detailInfo.setBxAmount(adetailDO.getAmount());
							detailInfo.setFyAmount(adetailDO.getAmount()
								.subtract(adetailDO.getFpAmount())
								.subtract(adetailDO.getXjAmount()));
						}
					}
					detailList.add(detailInfo);
				}
				info.setCxdetailList(detailList);
			}
			
			// 20161117 添加修改记录
			
			List<FormPayChangeDetailDO> changes = formPayChangeDetailDAO.findByTypeAndSourceId(
				ChangeSourceTypeEnum.EXPENSE.code(), DO.getExpenseApplicationId());
			List<FormPayChangeDetailInfo> changeInfos = new ArrayList<FormPayChangeDetailInfo>();
			if (changes != null && ListUtil.isNotEmpty(changes)) {
				for (FormPayChangeDetailDO changeDO : changes) {
					FormPayChangeDetailInfo changeInfo = new FormPayChangeDetailInfo();
					MiscUtil.copyPoObject(changeInfo, changeDO);
					changeInfo.setSourceType(ChangeSourceTypeEnum.getByCode(changeDO
						.getSourceType()));
					changeInfos.add(changeInfo);
				}
				info.setChangeInfos(changeInfos);
			}
			
		}
	}
	
	@SuppressWarnings("deprecation")
	private void setFormExpenseApplicationInfo(FormExpenseApplicationFormDO DO,
												FormExpenseApplicationInfo info, boolean isDetail) {
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		if (StringUtil.isNotBlank(DO.getIsOfficialCard())) {
			info.setIsOfficialCard(BooleanEnum.getByCode(DO.getIsOfficialCard()));
		}
		if (StringUtil.isNotBlank(DO.getDirection())) {
			info.setDirection(CostDirectionEnum.getByCode(DO.getDirection()));
		}
		if (StringUtil.isNotBlank(DO.getVoucherStatus()))
			info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
		
		info.setAccountStatus(AccountStatusEnum.getByCode(DO.getAccountStatus()));
		
		info.setBranchPayStatus(BranchPayStatusEnum.getByCode(DO.getBranchPayStatus()));
		
		FormDO formDO = formDAO.findByFormId(DO.getFormId());
		if (StringUtil.isNotBlank(formDO.getStatus()))
			info.setFormStatus(FormStatusEnum.getByCode(formDO.getStatus()));
		info.setFormDeptName(formDO.getDeptName());
		info.setFormUserName(formDO.getUserName());
		info.setFormUserId(formDO.getUserId());
		info.setActDefId(formDO.getActDefId());
		info.setActInstId(formDO.getActInstId());
		info.setFormUrl(formDO.getFormUrl());
		info.setRunId(formDO.getRunId());
		info.setTaskId(formDO.getTaskId());
		info.setTaskUserData(formDO.getTaskUserData());
		info.setRawUpdateTime(formDO.getRawUpdateTime());
		
		if (isDetail) {
			List<FormExpenseApplicationDetailDO> detailDOs = formExpenseApplicationDetailDAO
				.findByExpenseApplicationId(DO.getExpenseApplicationId());
			if (detailDOs != null && detailDOs.size() > 0) {
				List<FormExpenseApplicationDetailInfo> detailList = new ArrayList<FormExpenseApplicationDetailInfo>(
					detailDOs.size());
				for (FormExpenseApplicationDetailDO detailDO : detailDOs) {
					FormExpenseApplicationDetailInfo detailInfo = new FormExpenseApplicationDetailInfo();
					BeanCopier.staticCopy(detailDO, detailInfo, UnBoxingConverter.getInstance());
					
					if (StringUtil.isNotBlank(detailDO.getExpenseType())
						&& info.getApplicationTime() != null) {
						Money balance = budgetService.queryBalanceByDeptCategoryId(
							detailDO.getDeptId(), Long.valueOf(detailDO.getExpenseType()),
							info.getApplicationTime(), false);
						detailInfo.setBalanceAmount(balance == null ? Money.zero() : balance);
					}
					
					detailList.add(detailInfo);
				}
				info.setDetailList(detailList);
			}
			
			List<ExpenseCxDetailDO> cxDetailDOs = expenseCxDetailDAO.findByExpenseId(
				DO.getExpenseApplicationId(), null, null);
			if (cxDetailDOs != null && cxDetailDOs.size() > 0) {
				List<ExpenseCxDetailInfo> detailList = new ArrayList<ExpenseCxDetailInfo>(
					cxDetailDOs.size());
				for (ExpenseCxDetailDO detailDO : cxDetailDOs) {
					ExpenseCxDetailInfo detailInfo = new ExpenseCxDetailInfo();
					BeanCopier.staticCopy(detailDO, detailInfo, UnBoxingConverter.getInstance());
					if (detailInfo.getFromApplicationId() > 0) {
						FormExpenseApplicationDO applicationDO = formExpenseApplicationDAO
							.findById(detailInfo.getFromApplicationId());
						if (applicationDO != null) {
							detailInfo.setBillno(applicationDO.getBillNo());
							detailInfo.setApplyDate(DateUtil.dtSimpleFormat(applicationDO
								.getApplicationTime()));
						}
						
						FormExpenseApplicationDetailDO adetailDO = formExpenseApplicationDetailDAO
							.findById(detailInfo.getFromDetailId());
						if (adetailDO != null) {
							CostCategoryInfo costInfo = costCategoryService.queryById(Long
								.valueOf(adetailDO.getExpenseType()));
							detailInfo.setCategory(costInfo == null ? "" : costInfo.getName());
							detailInfo.setBxAmount(adetailDO.getAmount());
							detailInfo.setFyAmount(adetailDO.getAmount()
								.subtract(adetailDO.getFpAmount())
								.subtract(adetailDO.getXjAmount()));
						}
					}
					detailList.add(detailInfo);
				}
				info.setCxdetailList(detailList);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private ExpenseApplyQueryCondition getQueryCondition(ExpenseApplicationQueryOrder order) {
		ExpenseApplyQueryCondition condition = new ExpenseApplyQueryCondition();
		if (order != null) {
			BeanCopier.staticCopy(order, condition, UnBoxingConverter.getInstance());
			condition.setIsOfficialCard(order.getOfficialCard());
			if (StringUtil.isNotBlank(order.getBillNo()))
				condition.setBillNo("%" + order.getBillNo() + "%");
			if (order.getProcess() != null)
				condition.setProcess(order.getProcess().code());
			if (order.getAmountStart() != null)
				condition.setAmountStart(order.getAmountStart().getCent());
			if (order.getAmountEnd() != null)
				condition.setAmountEnd(order.getAmountEnd().getCent());
			if (order.getFormStatusList() != null) {
				condition
					.setFormStatusList(new ArrayList<String>(order.getFormStatusList().size()));
				for (FormStatusEnum status : order.getFormStatusList()) {
					condition.getFormStatusList().add(status.code());
				}
			}
			if (order.getExcFormStatusList() != null) {
				condition.setExcFormStatusList(new ArrayList<String>(order.getExcFormStatusList()
					.size()));
				for (FormStatusEnum status : order.getExcFormStatusList()) {
					condition.getExcFormStatusList().add(status.code());
				}
			}
			if (order.getAccountStatus() != null) {
				condition.setAccountStatus(order.getAccountStatus().code());
			}
			
			condition.setSearchUserIdList(order.getSearchUserIdList());
			condition.setExpenseTypes(order.getExpenseTypes());
			condition.setDeptIdList(order.getDeptIdList());
			condition.setLoginUserId(order.getLoginUserId());
			condition.setDraftUserId(order.getDraftUserId());
		}
		return condition;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public List<FormExpenseApplicationDetailInfo> findSublInfoByDeptCategory(	long deptId,
																				long categoryId,
																				Date applyTimeStart,
																				Date applyTimeEnd) {
		
		String timeStart = applyTimeStart == null ? null : DateUtil.simpleFormat(applyTimeStart);
		String timeEnd = applyTimeEnd == null ? null : DateUtil.simpleFormat(applyTimeEnd);
		List<FormExpenseApplicationDetailInfo> detailList = null;
		List<FormExpenseApplicationDetailDO> detailDOs = formExpenseApplicationDetailDAO
			.findSublInfoByDeptCategory(deptId, categoryId, timeStart, timeEnd);
		if (detailDOs != null && detailDOs.size() > 0) {
			detailList = new ArrayList<FormExpenseApplicationDetailInfo>(detailDOs.size());
			for (FormExpenseApplicationDetailDO detailDO : detailDOs) {
				FormExpenseApplicationDetailInfo detailInfo = new FormExpenseApplicationDetailInfo();
				BeanCopier.staticCopy(detailDO, detailInfo, UnBoxingConverter.getInstance());
				detailList.add(detailInfo);
			}
		}
		
		return detailList;
	}
	
	private void checkDeptBalance(ExpenseApplicationOrder order) {
		List<ExpenseApplicationDetailOrder> detailOrders = order.getDetailList();
		if (detailOrders == null || detailOrders.isEmpty()) {
			return;
		}
		
		HashMap<String, Money> checkMap = new HashMap<String, Money>();
		for (ExpenseApplicationDetailOrder detailOrder : detailOrders) {
			String deptIdNm = detailOrder.getDeptId() + "," + detailOrder.getDeptName() + ","
								+ detailOrder.getExpenseType();
			Money dMoney = checkMap.get(deptIdNm);
			if (dMoney == null) {
				checkMap.put(deptIdNm, detailOrder.getAmount());
			} else {
				checkMap.put(deptIdNm, dMoney.add(detailOrder.getAmount()));
			}
		}
		for (String deptIdNm : checkMap.keySet()) {
			String[] deptIdNmArr = deptIdNm.split(",");
			if (deptIdNmArr.length == 3) {
				String deptId = deptIdNmArr[0];
				String deptNm = deptIdNmArr[1];
				String categoryId = deptIdNmArr[2];
				Money expense = checkMap.get(deptIdNm);
				Money balance = budgetService.queryBalanceByDeptCategoryId(Long.valueOf(deptId),
					Long.valueOf(categoryId), order.getApplicationTime(), true);
				if (balance != null && expense.compareTo(balance) > 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE, "部门【" + deptNm
																						+ "】预算不足");
				}
			}
		}
	}
	
	@Override
	public FormBaseResult updatePayBank(ExpenseApplicationOrder order) {
		FormBaseResult result = createResult();
		FormExpenseApplicationDO formExpenseApplication = formExpenseApplicationDAO
			.findByFormId(order.getFormId());
		formExpenseApplication.setPayBank(order.getPayBank());
		formExpenseApplication.setPayBankAccount(order.getPayBankAccount());
		formExpenseApplicationDAO.update(formExpenseApplication);
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public FcsBaseResult syncForecast(long formId, boolean afterAudit) {
		FcsBaseResult result = createResult();
		try {
			//非公务卡支付才预测
			FormExpenseApplicationDO apply = formExpenseApplicationDAO.findByFormId(formId);
			Money applyAmount = Money.zero();
			Money repayAmount = Money.zero();
			if (apply != null
				&& !StringUtil.equals(apply.getIsOfficialCard(), BooleanEnum.IS.code())) {
				//还款，不需要预测
				//冲预付款，仅预测报销 金额大于预付款金额的情况，预测的数据为报销金额-预付款金额
				List<FormExpenseApplicationDetailDO> feeList = formExpenseApplicationDetailDAO
					.findByExpenseApplicationId(apply.getExpenseApplicationId());
				if (feeList != null) {
					for (FormExpenseApplicationDetailDO fee : feeList) {
						CostCategoryInfo feeType = costCategoryService.queryById(NumberUtil
							.parseLong(fee.getExpenseType()));
						if (feeType != null && "还款".equals(feeType.getName())) {
							//do nothing 还款不需要预测
							repayAmount.addTo(fee.getAmount());
						} else {
							// 实际要支付的金额 = 报销金额 - 冲销金额
							applyAmount.addTo(fee.getAmount().subtract(
								fee.getFpAmount().subtract(fee.getXjAmount())));
						}
					}
				}
			}
			
			if (afterAudit && applyAmount.getCent() == 0) { //审核后如果不预测就要删掉提交的时候预测的(不考虑之前没预测的情况)
				ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
				forecastAccountOrder.setOrderNo(apply.getBillNo());
				forecastAccountOrder.setSystemForm(SystemEnum.FM);
				forecastService.delete(forecastAccountOrder);
			}
			
			if (applyAmount.greaterThan(ZERO_MONEY)) {
				//查询预测规则  没查询到用默认规则
				SysForecastParamAllInfo rule = new SysForecastParamAllInfo();
				SysForecastParamResult ruleResult = forecastService.findAll();
				if (ruleResult != null && ruleResult.isSuccess()) {
					rule = ruleResult.getParamAllInfo();
				}
				TimeUnitEnum forcastTimeType = rule.getOutXfglxtTimeType();
				int forcastTime = NumberUtil.parseInt(rule.getOutXfglxtTime());
				if (afterAudit) {
					forcastTimeType = rule.getOutXfglxtOtherTimeType();
					forcastTime = NumberUtil.parseInt(rule.getOutXfglxtOtherTime());
				}
				Calendar calendar = Calendar.getInstance();
				if (forcastTimeType == TimeUnitEnum.YEAR) {
					calendar.add(Calendar.YEAR, forcastTime);
				} else if (forcastTimeType == TimeUnitEnum.MONTH) {
					calendar.add(Calendar.MONTH, forcastTime);
				} else if (forcastTimeType == TimeUnitEnum.DAY) {
					calendar.add(Calendar.DAY_OF_MONTH, forcastTime);
				}
				ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
				forecastAccountOrder.setForecastStartTime(calendar.getTime());
				forecastAccountOrder.setAmount(applyAmount);
				forecastAccountOrder.setForecastMemo("费用支付（" + apply.getBillNo() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.OUT_ZFGLXT);
				forecastAccountOrder.setFundDirection(FundDirectionEnum.OUT);
				forecastAccountOrder.setOrderNo(apply.getBillNo());
				forecastAccountOrder.setSystemForm(SystemEnum.FM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(apply.getExpenseDeptId()));
				forecastAccountOrder.setUsedDeptName(apply.getDeptName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.EXPENSE);
				logger.info("费用支付资金流出预测 , projectCode：{}, forecastAccountOrder：{} ",
					apply.getBillNo(), forecastAccountOrder);
				forecastService.add(forecastAccountOrder);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("同步预测数据出错");
			logger.error("同步费用支付预测数据出错：formId{} {}", formId, e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult sync2FinancialSys(FormExpenseApplicationInfo expenseInfo) {
		FcsBaseResult result = createResult();
		try {
			
			logger.info("费用申请单同步至财务系统开始  ,receiptInfo : {}", expenseInfo);
			if (expenseInfo == null) {
				result.setSuccess(false);
				result.setMessage("费用申请单不存在");
				return result;
			}
			
			//同步传输数据到财务系统 根据结果改变同步状态
			FormDO formDO = formDAO.findByFormId(expenseInfo.getFormId());
			if (formDO == null) {
				result.setSuccess(false);
				result.setMessage("费用申请单不存在");
				logger.error("费用申请单不存在!");
				return result;
			}
			
			//经办人
			UserDetailInfo dealMan = bpmUserQueryService.findUserDetailByUserId(expenseInfo
				.getAgentId());
			KingdeeExpensesRequisitionsOrder order = new KingdeeExpensesRequisitionsOrder();
			order.setBizNo(expenseInfo.getBillNo());
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			order.setDealMan(String.valueOf(dealMan.getId()));
			order.setDealManName(dealMan.getName());
			//order.setDealManDeptCode(dealMan.getPrimaryOrg().getCode());
			// 20170113 要求传入页面嗮选的申请部门替代经办人部门,如果是总部，就走经办人部门
			// 判断是否为总部
			String CQJCK = "CQJCK";
			String paramCQJCK = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_CQJCK_COMPANY_CODE.code());
			if (StringUtil.isNotBlank(paramCQJCK)) {
				CQJCK = paramCQJCK;
			}
			Org orgCY = bpmUserQueryService.findDeptByCode(CQJCK);
			logger.info("orgCY查询结果：" + orgCY);
			if (orgCY != null && expenseInfo.getExpenseDeptId() == orgCY.getId()) {
				// 是总部就选中经办人
				order.setDealManDeptCode(dealMan.getPrimaryOrg().getCode());
			} else {
				// 不是总部就选中申请部门
				Org org = bpmUserQueryService.findDeptById(expenseInfo.getExpenseDeptId());
				order.setDealManDeptCode(org.getCode());
			}
			
			//收款人名称
			order.setTransferName(expenseInfo.getPayee());
			order.setGwk(expenseInfo.getIsOfficialCard() == BooleanEnum.NO ? false : true);
			
			BankMessageResult bankResult = bankMessageService.findByAccount(expenseInfo
				.getPayBankAccount());
			
			BankMessageInfo payBank = bankResult.getBankMessageInfo();
			String bankAtCode = "";
			if (payBank != null)
				bankAtCode = payBank.getAtCode();
			
			List<FormExpenseApplicationDetailInfo> ds = expenseInfo.getDetailList();
			if (ds != null) {
				List<ExpensesRequisitionsInfo> eRequisitionsInfos = new ArrayList<ExpensesRequisitionsInfo>(
					ds.size());
				for (FormExpenseApplicationDetailInfo dinfo : ds) {
					ExpensesRequisitionsInfo eReqInfo = new ExpensesRequisitionsInfo();
					eReqInfo.setAmount(dinfo.getAmount().toString());
					eReqInfo.setTaxAmount(dinfo.getTaxAmount().toString());
					// TODO
					CostCategoryInfo feeType = costCategoryService.queryById(NumberUtil
						.parseLong(dinfo.getExpenseType()));
					if (feeType != null) {
						eReqInfo.setFeeType(feeType.getName());
						//						if (StringUtil.equals(feeType.getName(), "还款")) {
						//							eReqInfo.setCredit(feeType.getAccountCode());//贷方科目
						//							//eReqInfo.setDebit(bankAtCode);//借方科目
						//						} else {
						//							//eReqInfo.setDebit(feeType.getAccountCode());//借方科目
						//							eReqInfo.setCredit(bankAtCode); //贷方科目
						//						}
					}
					eReqInfo.setCredit(bankAtCode); //贷方科目
					
					//部门
					Org bxOrg = bpmUserQueryService.findDeptById(Long.valueOf(dinfo.getDeptId()));
					eReqInfo.setBxDeptCode(bxOrg != null ? bxOrg.getCode() : "");
					
					//同步基础数据
					SyncKingdeeBasicDataOrder syncBasicOrder = new SyncKingdeeBasicDataOrder();
					syncBasicOrder.setDept(bxOrg);
					kingdeeTogetheFacadeClient.syncBasicData(syncBasicOrder);
					
					eRequisitionsInfos.add(eReqInfo);
				}
				order.setFeeDetail(eRequisitionsInfos);
			}
			
			SyncKingdeeBasicDataOrder syncBasicOrder = new SyncKingdeeBasicDataOrder();
			//同步基础数据
			syncBasicOrder.setDept(dealMan.getPrimaryOrg());
			kingdeeTogetheFacadeClient.syncBasicData(syncBasicOrder);
			
			KingdeeExpensesRequisitionsResult kingdeeResult = kingdeeTogetheFacadeClient
				.expensesRequisitions(order);
			
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			updateOrder.setBillNo(expenseInfo.getBillNo());
			updateOrder.setVoucherSyncSendTime(getSysdate());
			if (kingdeeResult != null
				&& CommonResultEnum.EXECUTE_SUCCESS == kingdeeResult.getResultCode()) {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_SUCCESS);
			} else {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED);
			}
			logger.info("费用报销单同步结果:", kingdeeResult);
			updateVoucher(updateOrder);
			
			result.setSuccess(true);
			result.setMessage("同步成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("同步出错");
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			try {
				updateOrder.setBillNo(expenseInfo.getBillNo());
				updateOrder.setVoucherSyncSendTime(getSysdate());
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED);
				updateVoucher(updateOrder);
			} catch (Exception e1) {
				logger.error("费用报销单同步到财务系统出错后,更定状态为发送失败，失败！+order=" + updateOrder, e1);
			}
			logger.error("费用报销单同步到财务系统出错：{}", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult updateVoucher(final UpdateVoucherOrder order) {
		return commonProcess(order, "更新凭证号同步状态", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				FormExpenseApplicationDO expenseDO = formExpenseApplicationDAO.findByBillNo(order
					.getBillNo());
				
				if (expenseDO == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "费用报销单不存在");
				}
				
				//同步状态
				if (order.getVoucherStatus() != null) {
					if (order.getVoucherStatus() == VoucherStatusEnum.SYNC_DELETE
						&& StringUtil.equals(order.getVoucherNo(), expenseDO.getVoucherNo())) {
						expenseDO.setVoucherStatus(VoucherStatusEnum.SYNC_DELETE.code());
					} else if (order.getVoucherStatus() != VoucherStatusEnum.SYNC_DELETE) {
						expenseDO.setVoucherStatus(order.getVoucherStatus().code());
					}
				}
				
				//凭证号
				expenseDO.setVoucherNo(order.getVoucherNo());
				
				//同步发送时间
				if (order.getVoucherSyncSendTime() != null)
					expenseDO.setVoucherSyncSendTime(order.getVoucherSyncSendTime());
				//同步完成时间
				if (order.getVoucherSyncFinishTime() != null)
					expenseDO.setVoucherSyncFinishTime(order.getVoucherSyncFinishTime());
				
				formExpenseApplicationDAO.update(expenseDO);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FcsBaseResult confirmBranchPay(final ConfirmBranchPayOrder order) {
		return commonProcess(order, "分支机构确认付款", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				
				FormExpenseApplicationDO expenseDO = formExpenseApplicationDAO.findByBillNo(order
					.getBillNo());
				
				if (expenseDO == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "费用报销单不存在");
				}
				
				if (StringUtil.equals(expenseDO.getBranchPayStatus(),
					BranchPayStatusEnum.NOT_BRANCH.code())
					|| StringUtil.equals(expenseDO.getBranchPayStatus(),
						BranchPayStatusEnum.AUDITING.code())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"当前不可确认");
				}
				
				if (StringUtil.equals(expenseDO.getBranchPayStatus(),
					BranchPayStatusEnum.PAID.code())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"单据已确认");
				}
				
				expenseDO.setBranchPayStatus(BranchPayStatusEnum.PAID.code());
				if (order.getBranchPayTime() == null) {
					expenseDO.setBranchPayTime(now);
				} else {
					expenseDO.setBranchPayTime(order.getBranchPayTime());
				}
				
				formExpenseApplicationDAO.update(expenseDO);
				
				return null;
			}
		}, null, null);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryPageAll(	ExpenseApplicationQueryOrder order) {
		QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> batchResult = new QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo>();
		
		try {
			ExpenseApplyQueryCondition condition = getQueryCondition(order);
			
			HashMap totalMap = busiDAO.findExpenseByConditionCountReport(condition);
			long totalCount = NumberUtil.parseLong(String.valueOf(totalMap.get("totalCount")));
			Object totalAmountVal = totalMap.get("totalAmount");
			Money totalAmount = Money.cent(totalAmountVal == null ? 0 : NumberUtil
				.parseLong(totalAmountVal.toString()));
			
			PageComponent component = new PageComponent(order, totalCount);
			List<FormExpenseApplicationDetailAllFormDO> list = busiDAO
				.findExpenseByConditionReport(condition);
			List<FormExpenseApplicationDetailAllInfo> pageList = new ArrayList<FormExpenseApplicationDetailAllInfo>(
				list.size());
			for (FormExpenseApplicationDetailAllFormDO DO : list) {
				FormExpenseApplicationDetailAllInfo info = new FormExpenseApplicationDetailAllInfo();
				//setFormExpenseApplicationInfo(DO, info, order.isDetail());
				MiscUtil.copyPoObject(info, DO);
				info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
				info.setIsOfficialCard(BooleanEnum.getByCode(DO.getIsOfficialCard()));
				info.setDirection(CostDirectionEnum.getByCode(DO.getDirection()));
				info.setAccountstatus(AccountStatusEnum.getByCode(DO.getAccountStatus()));
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setReverse(BooleanEnum.getByCode(DO.getReverse()));
				info.setBranchPayStatus(BranchPayStatusEnum.getByCode(DO.getBranchPayStatus()));
				pageList.add(info);
			}
			
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
			//总金额返回
			batchResult.setReturnObject(totalAmount);
		} catch (Exception e) {
			logger.error("查询费用支付申请单列表失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
}
