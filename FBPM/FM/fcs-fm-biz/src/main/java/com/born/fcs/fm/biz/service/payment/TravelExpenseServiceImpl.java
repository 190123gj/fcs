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
import com.born.fcs.fm.dal.dataobject.FormDO;
import com.born.fcs.fm.dal.dataobject.FormPayChangeDetailDO;
import com.born.fcs.fm.dal.dataobject.FormTravelExpenseDO;
import com.born.fcs.fm.dal.dataobject.FormTravelExpenseDetailDO;
import com.born.fcs.fm.dal.queryCondition.TravelExpenseQueryCondition;
import com.born.fcs.fm.dataobject.FormTravelExpenseFormDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.risk.service.KingdeeTogetheFacadeClient;
import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.BranchPayStatusEnum;
import com.born.fcs.fm.ws.enums.ChangeSourceTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.SpeCostCategory;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.forecast.SysForecastParamAllInfo;
import com.born.fcs.fm.ws.info.payment.FormPayChangeDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseInfo;
import com.born.fcs.fm.ws.order.common.SyncKingdeeBasicDataOrder;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.order.payment.ConfirmBranchPayOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseDetailOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseQueryOrder;
import com.born.fcs.fm.ws.result.bank.BankMessageResult;
import com.born.fcs.fm.ws.result.forecast.SysForecastParamResult;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.fm.ws.service.payment.BudgetService;
import com.born.fcs.fm.ws.service.payment.TravelExpenseService;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.common.FormRelatedUserOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.bornsoft.pub.order.kingdee.KingdeeTravelExpenseOrder;
import com.bornsoft.pub.order.kingdee.KingdeeTravelExpenseOrder.TravelExpenseInfo;
import com.bornsoft.pub.result.kingdee.KingdeeTravelExpenseResult;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("travelExpenseService")
public class TravelExpenseServiceImpl extends BaseFormAutowiredDomainService implements
																			TravelExpenseService {
	
	@Autowired
	protected BudgetService budgetService;
	
	@Autowired
	ForecastService forecastService;
	
	@Autowired
	private KingdeeTogetheFacadeClient kingdeeTogetheFacadeClient;
	
	@Autowired
	BankMessageService bankMessageService;
	
	@Autowired
	FormRelatedUserService formRelatedUserService;
	
	private void setChangeDetail(TravelExpenseOrder order) {
		//		List<FormPayChangeDetailDO> changeDOs = new ArrayList<FormPayChangeDetailDO>();
		
		FormPayChangeDetailDO changeDO = new FormPayChangeDetailDO();
		changeDO.setSourceType(ChangeSourceTypeEnum.TRAVEL.code());
		changeDO.setUserId(order.getUserId());
		changeDO.setUserName(order.getUserName());
		changeDO.setDocumentType("input");
		FormTravelExpenseDO expenseDO = formTravelExpenseDAO.findById(order.getTravelId());
		if (expenseDO.getAmount() != order.getAmount()) {
			changeDO.setDocumentName("amount");
			changeDO.setDocumentDescribe("金额合计");
			changeDO.setDocumentValueOld(expenseDO.getAmount().toStandardString());
			changeDO.setDocumentValueNew(order.getAmount().toStandardString());
			//				changeDOs.add(changeDO);
			formPayChangeDetailDAO.insert(changeDO);
			
		}
		// 事由
		if (!expenseDO.getReasons().equals(order.getReasons())) {
			changeDO.setDocumentName("reasons");
			changeDO.setDocumentDescribe("事由");
			changeDO.setDocumentValueOld(expenseDO.getReasons());
			changeDO.setDocumentValueNew(order.getReasons());
			//				changeDOs.add(changeDO);
			formPayChangeDetailDAO.insert(changeDO);
		}
		
		for (TravelExpenseDetailOrder detailOrder : order.getDetailList()) {
			FormTravelExpenseDetailDO detailDO = formTravelExpenseDetailDAO.findById(detailOrder
				.getDetailId());
			changeDO.setSourceId(detailDO.getDetailId());
			// 1. 交通费
			if (detailDO.getTrafficAmount() != detailOrder.getTrafficAmount()) {
				changeDO.setDocumentName("trafficAmount");
				changeDO.setDocumentDescribe("交通费");
				changeDO.setDocumentValueOld(detailDO.getTrafficAmount().toStandardString());
				changeDO.setDocumentValueNew(detailOrder.getTrafficAmount().toStandardString());
				//				changeDOs.add(changeDO);
				formPayChangeDetailDAO.insert(changeDO);
			}
			
			// 2. 住宿费
			if (detailDO.getHotelAmount() != detailOrder.getHotelAmount()) {
				changeDO.setDocumentName("hotelAmount");
				changeDO.setDocumentDescribe("住宿费");
				changeDO.setDocumentValueOld(detailDO.getHotelAmount().toStandardString());
				changeDO.setDocumentValueNew(detailOrder.getHotelAmount().toStandardString());
				//				changeDOs.add(changeDO);
				formPayChangeDetailDAO.insert(changeDO);
			}
			
			// 3. 税金
			if (detailDO.getTaxAmount() != detailOrder.getTaxAmount()) {
				changeDO.setDocumentName("taxAmount");
				changeDO.setDocumentDescribe("税金");
				changeDO.setDocumentValueOld(detailDO.getTaxAmount().toStandardString());
				changeDO.setDocumentValueNew(detailOrder.getTaxAmount().toStandardString());
				//				changeDOs.add(changeDO);
				formPayChangeDetailDAO.insert(changeDO);
			}
			
			// 4. 误餐费
			if (detailDO.getMealsAmount() != detailOrder.getMealsAmount()) {
				changeDO.setDocumentName("mealsAmount");
				changeDO.setDocumentDescribe("误餐费");
				changeDO.setDocumentValueOld(detailDO.getMealsAmount().toStandardString());
				changeDO.setDocumentValueNew(detailOrder.getMealsAmount().toStandardString());
				//				changeDOs.add(changeDO);
				formPayChangeDetailDAO.insert(changeDO);
			}
			
			// 5. 出差补助
			if (detailDO.getAllowanceAmount() != detailOrder.getAllowanceAmount()) {
				changeDO.setDocumentName("allowanceAmount");
				changeDO.setDocumentDescribe("出差补助");
				changeDO.setDocumentValueOld(detailDO.getAllowanceAmount().toStandardString());
				changeDO.setDocumentValueNew(detailOrder.getAllowanceAmount().toStandardString());
				//				changeDOs.add(changeDO);
				formPayChangeDetailDAO.insert(changeDO);
			}
			
			// 6. 其他费用
			if (detailDO.getOtherAmount() != detailOrder.getOtherAmount()) {
				changeDO.setDocumentName("otherAmount");
				changeDO.setDocumentDescribe("其他费用");
				changeDO.setDocumentValueOld(detailDO.getOtherAmount().toStandardString());
				changeDO.setDocumentValueNew(detailOrder.getOtherAmount().toStandardString());
				//				changeDOs.add(changeDO);
				formPayChangeDetailDAO.insert(changeDO);
			}
			
			// 7. 小计
			if (detailDO.getTotalAmount() != detailOrder.getTotalAmount()) {
				changeDO.setDocumentName("totalAmount");
				changeDO.setDocumentDescribe("小计");
				changeDO.setDocumentValueOld(detailDO.getTotalAmount().toStandardString());
				changeDO.setDocumentValueNew(detailOrder.getTotalAmount().toStandardString());
				//				changeDOs.add(changeDO);
				formPayChangeDetailDAO.insert(changeDO);
			}
		}
	}
	
	@Override
	public FormBaseResult save(final TravelExpenseOrder order) {
		return commonFormSaveProcess(order, "差旅费报销单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				List<TravelExpenseDetailOrder> detailOrders = order.getDetailList();
				if (detailOrders == null || detailOrders.isEmpty()) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "报销明细不存在");
				}
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				
				FormInfo form = (FormInfo) FcsFmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				// 20161104 添加判断，若为onlyChangeDetailList 只修改明细
				if (BooleanEnum.YES == order.getOnlyChangeDetailList()) {
					for (TravelExpenseDetailOrder detailOrder : order.getDetailList()) {
						FormTravelExpenseDetailDO detailDO = formTravelExpenseDetailDAO
							.findById(detailOrder.getDetailId());
						// 能修改种类和金额，金额只能比以前的小
						if (detailOrder.getTrafficAmount().greaterThan(detailDO.getTrafficAmount())) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, "金额不能比之前的大！");
						}
						detailDO.setTrafficAmount(detailOrder.getTrafficAmount());
						//2
						if (detailOrder.getHotelAmount().greaterThan(detailDO.getHotelAmount())) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, "金额不能比之前的大！");
						}
						detailDO.setHotelAmount(detailOrder.getHotelAmount());
						//3
						if (detailOrder.getTaxAmount().greaterThan(detailDO.getTaxAmount())) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, "金额不能比之前的大！");
						}
						detailDO.setTaxAmount(detailOrder.getTaxAmount());
						//4
						if (detailOrder.getMealsAmount().greaterThan(detailDO.getMealsAmount())) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, "金额不能比之前的大！");
						}
						detailDO.setMealsAmount(detailOrder.getMealsAmount());
						//5
						if (detailOrder.getAllowanceAmount().greaterThan(
							detailDO.getAllowanceAmount())) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, "金额不能比之前的大！");
						}
						detailDO.setAllowanceAmount(detailOrder.getAllowanceAmount());
						//6
						if (detailOrder.getOtherAmount().greaterThan(detailDO.getOtherAmount())) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, "金额不能比之前的大！");
						}
						detailDO.setOtherAmount(detailOrder.getOtherAmount());
						//7
						if (detailOrder.getTotalAmount().greaterThan(detailDO.getTotalAmount())) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.INCOMPLETE_REQ_PARAM, "金额不能比之前的大！");
						}
						detailDO.setTotalAmount(detailOrder.getTotalAmount());
						formTravelExpenseDetailDAO.update(detailDO);
					}
					FormTravelExpenseDO expenseDO = formTravelExpenseDAO.findById(order
						.getTravelId());
					// 更定主表金额
					if (order.getAmount().greaterThan(Money.zero())) {
						expenseDO.setAmount(order.getAmount());
					}
					// 更定报销事由
					if (StringUtil.isNotBlank(order.getReasons())) {
						expenseDO.setReasons(order.getReasons());
					}
					formTravelExpenseDAO.update(expenseDO);
					
					setChangeDetail(order);
					return null;
					
				}
				
				checkDeptBalance(order);//check部门余额
				
				FormBaseResult result = (FormBaseResult) FcsFmDomainHolder.get().getAttribute(
					"result");
				
				FormTravelExpenseDO formTravelExpense = formTravelExpenseDAO.findByFormId(form
					.getFormId());
				if (formTravelExpense == null) {//新增
					if (order.getFormId() != null && order.getFormId() > 0) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
					}
					
					formTravelExpense = new FormTravelExpenseDO();
					BeanCopier.staticCopy(order, formTravelExpense, UnBoxingConverter.getInstance());
					if (order.getIsOfficialCard() != null) {
						formTravelExpense.setIsOfficialCard(order.getIsOfficialCard().code());
					}
					formTravelExpense.setDeptName(order.getExpenseDeptName());
					formTravelExpense.setFormId(form.getFormId());
					formTravelExpense.setBillNo(genBillNo());
					formTravelExpense.setVoucherStatus(VoucherStatusEnum.NOT_SEND.code());
					if (StringUtil.isBlank(formTravelExpense.getAccountStatus())) {
						formTravelExpense.setAccountStatus(AccountStatusEnum.DRAFT.code());
					}
					formTravelExpense.setRawAddTime(now);
					long travelId = formTravelExpenseDAO.insert(formTravelExpense);
					FormTravelExpenseInfo info = new FormTravelExpenseInfo();
					info.setTravelId(travelId);
					info.setBillNo(formTravelExpense.getBillNo());
					result.setReturnObject(info);
					
					for (TravelExpenseDetailOrder detailOrder : detailOrders) {
						FormTravelExpenseDetailDO formTravelExpenseDetail = new FormTravelExpenseDetailDO();
						BeanCopier.staticCopy(detailOrder, formTravelExpenseDetail,
							UnBoxingConverter.getInstance());
						formTravelExpenseDetail.setTravelId(travelId);
						formTravelExpenseDetail.setRawAddTime(now);
						formTravelExpenseDetailDAO.insert(formTravelExpenseDetail);
					}
					result.setMessage(formTravelExpense.getBillNo());
				} else {//更新
					long travelId = formTravelExpense.getTravelId();
					if (order.getExpenseDeptId() != null) {
						formTravelExpense.setExpenseDeptId(order.getExpenseDeptId());
					}
					formTravelExpense.setDeptName(order.getExpenseDeptName());
					formTravelExpense.setDeptHead(order.getDeptHead());
					formTravelExpense.setApplicationTime(order.getApplicationTime());
					formTravelExpense.setRelationForm(order.getRelationForm());
					formTravelExpense.setTravelers(order.getTravelers());
					formTravelExpense.setReasons(order.getReasons());
					formTravelExpense.setPayeeId(order.getPayeeId());
					formTravelExpense.setPayee(order.getPayee());
					formTravelExpense.setBank(order.getBank());
					formTravelExpense.setBankAccount(order.getBankAccount());
					formTravelExpense.setAmount(order.getAmount());
					formTravelExpense.setAttachmentsNum(order.getAttachmentsNum());
					if (order.getIsOfficialCard() != null) {
						formTravelExpense.setIsOfficialCard(order.getIsOfficialCard().code());
					}
					if (StringUtil.isBlank(formTravelExpense.getAccountStatus())) {
						formTravelExpense.setAccountStatus(AccountStatusEnum.DRAFT.code());
					}
					formTravelExpenseDAO.update(formTravelExpense);
					FormTravelExpenseInfo info = new FormTravelExpenseInfo();
					info.setTravelId(travelId);
					info.setBillNo(formTravelExpense.getBillNo());
					result.setReturnObject(info);
					
					List<FormTravelExpenseDetailDO> oleDetailDOs = formTravelExpenseDetailDAO
						.findByTravelId(travelId);
					for (TravelExpenseDetailOrder detailOrder : detailOrders) {
						if (detailOrder.getDetailId() == null) {
							//新增明细
							FormTravelExpenseDetailDO formTravelExpenseDetail = new FormTravelExpenseDetailDO();
							BeanCopier.staticCopy(detailOrder, formTravelExpenseDetail,
								UnBoxingConverter.getInstance());
							formTravelExpenseDetail.setTravelId(travelId);
							formTravelExpenseDetail.setRawAddTime(now);
							formTravelExpenseDetailDAO.insert(formTravelExpenseDetail);
						} else {
							//更新明细
							for (int i = 0; i < oleDetailDOs.size(); i++) {
								FormTravelExpenseDetailDO updateDetailDO = oleDetailDOs.get(i);
								if (detailOrder.getDetailId() == updateDetailDO.getDetailId()) {
									updateDetailDO.setStartTime(detailOrder.getStartTime());
									updateDetailDO.setEndTime(detailOrder.getEndTime());
									updateDetailDO.setDays(detailOrder.getDays());
									updateDetailDO.setStartPlace(detailOrder.getStartPlace());
									updateDetailDO.setEndPlace(detailOrder.getEndPlace());
									updateDetailDO.setAllowanceAmount(detailOrder
										.getAllowanceAmount());
									updateDetailDO.setHotelAmount(detailOrder.getHotelAmount());
									updateDetailDO.setMealsAmount(detailOrder.getMealsAmount());
									updateDetailDO.setOtherAmount(detailOrder.getOtherAmount());
									updateDetailDO.setTaxAmount(detailOrder.getTaxAmount());
									updateDetailDO.setTrafficAmount(detailOrder.getTrafficAmount());
									updateDetailDO.setTotalAmount(detailOrder.getTotalAmount());
									updateDetailDO.setDeptId(detailOrder.getDeptId());
									updateDetailDO.setDeptName(detailOrder.getDeptName());
									formTravelExpenseDetailDAO.update(updateDetailDO);
									
									//移除已更新的数据，剩下需要删除的数据
									oleDetailDOs.remove(i);
									break;
								}
							}
						}
						
					}
					
					//删除明细
					for (FormTravelExpenseDetailDO deletDo : oleDetailDOs) {
						formTravelExpenseDetailDAO.deleteById(deletDo.getDetailId());
					}
					
					result.setMessage(formTravelExpense.getBillNo());
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
	
	/**
	 * 生成单据号
	 * @param busiType
	 * @return
	 */
	private String genBillNo() {
		TravelExpenseQueryCondition condition = new TravelExpenseQueryCondition();
		condition.setBillNo("CLF" + "%");
		FormTravelExpenseDO DO = formTravelExpenseDAO.findByUp();
		long count = 0;
		if (DO != null) {
			String billNo = DO.getBillNo();
			//CLF000437
			count = Long.valueOf(billNo.replaceAll("CLF", ""));
		}
		
		String billNo = "CLF" + StringUtil.alignRight((count + 1) + "", 6, '0');
		if (formTravelExpenseDAO.findByBillNo(billNo) != null) {
			// 20170803 若+1已存在。使用+2
			billNo = "CLF" + StringUtil.alignRight((count + 2) + "", 6, '0');
			
			if (formTravelExpenseDAO.findByBillNo(billNo) != null) {
				// 若+2已存在。使用+3
				billNo = "CLF" + StringUtil.alignRight((count + 3) + "", 6, '0');
				
			}
		}
		return billNo;
	}
	
	@Override
	public QueryBaseBatchResult<FormTravelExpenseInfo> queryPage(TravelExpenseQueryOrder order) {
		QueryBaseBatchResult<FormTravelExpenseInfo> batchResult = new QueryBaseBatchResult<FormTravelExpenseInfo>();
		
		try {
			TravelExpenseQueryCondition condition = getQueryCondition(order);
			//			long totalCount = formTravelExpenseDAO.findByConditionCount(condition);
			//			PageComponent component = new PageComponent(order, totalCount);
			//			
			//			List<FormTravelExpenseDO> list = formTravelExpenseDAO.findByCondition(condition);
			//			List<FormTravelExpenseInfo> pageList = new ArrayList<FormTravelExpenseInfo>(list.size());
			//			for (FormTravelExpenseDO DO : list) {
			//				FormTravelExpenseInfo info = new FormTravelExpenseInfo();
			//				setFormTravelExpenseInfo(DO, info, order.isDetail());
			//				pageList.add(info);
			//			}
			
			long totalCount = busiDAO.findTravelExpenseByConditionCount(condition);
			PageComponent component = new PageComponent(order, totalCount);
			List<FormTravelExpenseFormDO> list = busiDAO.findTravelExpenseByCondition(condition);
			List<FormTravelExpenseInfo> pageList = new ArrayList<FormTravelExpenseInfo>(list.size());
			for (FormTravelExpenseFormDO DO : list) {
				FormTravelExpenseInfo info = new FormTravelExpenseInfo();
				setFormTravelExpenseInfo(DO, info, order.isDetail());
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询差旅费报销单列表失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FormTravelExpenseInfo queryByFormId(long formId) {
		FormTravelExpenseDO formTravelExpenseDO = formTravelExpenseDAO.findByFormId(formId);
		if (formTravelExpenseDO != null) {
			FormTravelExpenseInfo formTravelExpenseInfo = new FormTravelExpenseInfo();
			setFormTravelExpenseInfo(formTravelExpenseDO, formTravelExpenseInfo, true);
			return formTravelExpenseInfo;
		}
		return null;
	}
	
	@Override
	public FormTravelExpenseInfo queryById(long travelId) {
		FormTravelExpenseDO formTravelExpenseDO = formTravelExpenseDAO.findById(travelId);
		if (formTravelExpenseDO != null) {
			FormTravelExpenseInfo formTravelExpenseInfo = new FormTravelExpenseInfo();
			setFormTravelExpenseInfo(formTravelExpenseDO, formTravelExpenseInfo, true);
			return formTravelExpenseInfo;
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private void setFormTravelExpenseInfo(FormTravelExpenseDO DO, FormTravelExpenseInfo info,
											boolean isDetail) {
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		if (StringUtil.isNotBlank(DO.getIsOfficialCard())) {
			info.setIsOfficialCard(BooleanEnum.getByCode(DO.getIsOfficialCard()));
		}
		if (StringUtil.isNotBlank(DO.getVoucherStatus()))
			info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
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
			List<FormTravelExpenseDetailDO> detailDOs = formTravelExpenseDetailDAO
				.findByTravelId(DO.getTravelId());
			if (detailDOs != null && detailDOs.size() > 0) {
				List<FormTravelExpenseDetailInfo> detailList = new ArrayList<FormTravelExpenseDetailInfo>(
					detailDOs.size());
				for (FormTravelExpenseDetailDO detailDO : detailDOs) {
					FormTravelExpenseDetailInfo detailInfo = new FormTravelExpenseDetailInfo();
					BeanCopier.staticCopy(detailDO, detailInfo, UnBoxingConverter.getInstance());
					
					if (StringUtil.isNotBlank(detailDO.getDeptId())
						&& info.getApplicationTime() != null) {
						Money balance = budgetService.queryBalanceByDeptCategoryId(
							Long.valueOf(detailDO.getDeptId()),
							SpeCostCategory.TRAVEL.categoryId(), info.getApplicationTime(), false);
						detailInfo.setBalanceAmount(balance == null ? Money.zero() : balance);
					}
					
					detailList.add(detailInfo);
				}
				info.setDetailList(detailList);
			}
			// 20161117 添加修改记录
			
			List<FormPayChangeDetailDO> changes = formPayChangeDetailDAO.findByTypeAndSourceId(
				ChangeSourceTypeEnum.TRAVEL.code(), DO.getTravelId());
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
	private void setFormTravelExpenseInfo(FormTravelExpenseFormDO DO, FormTravelExpenseInfo info,
											boolean isDetail) {
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		if (StringUtil.isNotBlank(DO.getIsOfficialCard())) {
			info.setIsOfficialCard(BooleanEnum.getByCode(DO.getIsOfficialCard()));
		}
		if (StringUtil.isNotBlank(DO.getVoucherStatus()))
			info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
		FormDO formDO = formDAO.findByFormId(DO.getFormId());
		if (StringUtil.isNotBlank(formDO.getStatus()))
			info.setFormStatus(FormStatusEnum.getByCode(formDO.getStatus()));
		
		info.setBranchPayStatus(BranchPayStatusEnum.getByCode(DO.getBranchPayStatus()));
		
		info.setAccountStatus(AccountStatusEnum.getByCode(DO.getAccountStatus()));
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
			List<FormTravelExpenseDetailDO> detailDOs = formTravelExpenseDetailDAO
				.findByTravelId(DO.getTravelId());
			if (detailDOs != null && detailDOs.size() > 0) {
				List<FormTravelExpenseDetailInfo> detailList = new ArrayList<FormTravelExpenseDetailInfo>(
					detailDOs.size());
				for (FormTravelExpenseDetailDO detailDO : detailDOs) {
					FormTravelExpenseDetailInfo detailInfo = new FormTravelExpenseDetailInfo();
					BeanCopier.staticCopy(detailDO, detailInfo, UnBoxingConverter.getInstance());
					
					if (StringUtil.isNotBlank(detailDO.getDeptId())
						&& info.getApplicationTime() != null) {
						Money balance = budgetService.queryBalanceByDeptCategoryId(
							Long.valueOf(detailDO.getDeptId()),
							SpeCostCategory.TRAVEL.categoryId(), info.getApplicationTime(), false);
						detailInfo.setBalanceAmount(balance == null ? Money.zero() : balance);
					}
					
					detailList.add(detailInfo);
				}
				info.setDetailList(detailList);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private TravelExpenseQueryCondition getQueryCondition(TravelExpenseQueryOrder order) {
		TravelExpenseQueryCondition condition = new TravelExpenseQueryCondition();
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
			
			if (StringUtil.isNotBlank(order.getBranchPayStatus())) {
				condition.setBranchPayStatus(order.getBranchPayStatus());
			}
			
			condition.setSearchUserIdList(order.getSearchUserIdList());
			condition.setDeptIdList(order.getDeptIdList());
			condition.setLoginUserId(order.getLoginUserId());
			condition.setDraftUserId(order.getDraftUserId());
		}
		return condition;
	}
	
	@Override
	public FcsBaseResult sync2FinancialSys(FormTravelExpenseInfo travelInfo) {
		FcsBaseResult result = createResult();
		try {
			
			logger.info("差旅费报销单同步至财务系统开始  ,receiptInfo : {}", travelInfo);
			if (travelInfo == null) {
				result.setSuccess(false);
				result.setMessage("差旅费报销单不存在");
				return result;
			}
			
			// 同步传输数据到财务系统 根据结果改变同步状态
			FormDO form = formDAO.findByFormId(travelInfo.getFormId());
			if (form == null) {
				result.setSuccess(false);
				result.setMessage("差旅费报销单不存在");
				return result;
			}
			
			KingdeeTravelExpenseOrder order = new KingdeeTravelExpenseOrder();
			order.setBizNo(travelInfo.getBillNo());
			
			//XXX 经办人是表单提交人？
			order.setDealMan(String.valueOf(form.getUserId()));
			order.setDealManName(form.getUserName());
			order.setDealManDeptCode(form.getDeptCode());
			
			order.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
			order.setGwk(travelInfo.getIsOfficialCard() == BooleanEnum.NO ? false : true);
			
			//CostCategoryDO clf = costCategoryDAO.findById(1);//差旅费
			List<FormTravelExpenseDetailInfo> ds = travelInfo.getDetailList();
			BankMessageResult bankResult = bankMessageService.findByAccount(travelInfo
				.getPayBankAccount());
			BankMessageInfo payBank = bankResult.getBankMessageInfo();
			if (ds != null) {
				List<TravelExpenseInfo> travelExpenseInfos = new ArrayList<TravelExpenseInfo>(
					ds.size());
				for (FormTravelExpenseDetailInfo dinfo : ds) {
					
					//Money taxAmount = dinfo.getTaxAmount();
					//Money taxRatioAmount = Money.zero();
					//TravelExpenseInfo lastOne = null;
					
					String credit = null;
					if (payBank != null)
						credit = payBank.getAtCode();//贷方科目为支付银行对应的科目
						
					//报销部门
					Org bxDept = bpmUserQueryService.findDeptById(NumberUtil.parseLong(dinfo
						.getDeptId()));
					String bxDeptCode = null;
					if (bxDept != null)
						bxDeptCode = bxDept.getCode();
					
					if (dinfo.getTotalAmount().greaterThan(ZERO_MONEY)) {
						
						//feeDetail
						if (dinfo.getTrafficAmount().greaterThan(ZERO_MONEY)) {
							TravelExpenseInfo traffic = new TravelExpenseInfo();
							traffic.setFeeType("交通费");
							traffic.setBxDeptCode(bxDeptCode);
							traffic.setAmount(dinfo.getTrafficAmount().toString());
							traffic.setTaxAmount("0.00");
							traffic.setCredit(credit);
							travelExpenseInfos.add(traffic);
						}
						
						if (dinfo.getHotelAmount().greaterThan(ZERO_MONEY)) {
							TravelExpenseInfo hotel = new TravelExpenseInfo();
							hotel.setFeeType("住宿费");
							hotel.setBxDeptCode(bxDeptCode);
							hotel.setAmount(dinfo.getHotelAmount().toString());
							//差旅费才有税金
							hotel.setTaxAmount(dinfo.getTaxAmount().toString());
							hotel.setCredit(credit);
							travelExpenseInfos.add(hotel);
						}
						
						if (dinfo.getMealsAmount().greaterThan(ZERO_MONEY)) {
							TravelExpenseInfo meal = new TravelExpenseInfo();
							meal.setFeeType("误餐费");
							meal.setBxDeptCode(bxDeptCode);
							meal.setAmount(dinfo.getMealsAmount().toString());
							meal.setTaxAmount("0.00");
							meal.setCredit(credit);
							travelExpenseInfos.add(meal);
						}
						
						if (dinfo.getAllowanceAmount().greaterThan(ZERO_MONEY)) {
							
							TravelExpenseInfo allowance = new TravelExpenseInfo();
							allowance.setFeeType("出差补助");
							allowance.setBxDeptCode(bxDeptCode);
							allowance.setAmount(dinfo.getAllowanceAmount().toString());
							allowance.setTaxAmount("0.00");
							allowance.setCredit(credit);
							travelExpenseInfos.add(allowance);
						}
						
						if (dinfo.getOtherAmount().greaterThan(ZERO_MONEY)) {
							TravelExpenseInfo other = new TravelExpenseInfo();
							other.setFeeType("其他");
							other.setBxDeptCode(bxDeptCode);
							other.setAmount(dinfo.getOtherAmount().toString());
							other.setTaxAmount("0.00");
							other.setCredit(credit);
							travelExpenseInfos.add(other);
						}
						
						//同步报销部门
						SyncKingdeeBasicDataOrder syncDeptOrder = new SyncKingdeeBasicDataOrder();
						syncDeptOrder.setDept(bxDept);
						kingdeeTogetheFacadeClient.syncBasicData(syncDeptOrder);
					}
				}
				order.setFeeDetail(travelExpenseInfos);
			}
			
			//同步经办人
			SyncKingdeeBasicDataOrder syncBasicOrder = new SyncKingdeeBasicDataOrder();
			syncBasicOrder.setDeptId(form.getDeptId());
			kingdeeTogetheFacadeClient.syncBasicData(syncBasicOrder);
			
			//同步至金碟
			KingdeeTravelExpenseResult kingdeeResult = kingdeeTogetheFacadeClient
				.travelExpense(order);
			
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			updateOrder.setBillNo(travelInfo.getBillNo());
			updateOrder.setVoucherSyncSendTime(getSysdate());
			
			if (kingdeeResult != null
				&& CommonResultEnum.EXECUTE_SUCCESS == kingdeeResult.getResultCode()) {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_SUCCESS);
			} else {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED);
			}
			logger.info("差旅费报销单同步金蝶结果:", kingdeeResult);
			updateVoucher(updateOrder);
			
			result.setSuccess(true);
			result.setMessage("同步成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("同步出错");
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			try {
				updateOrder.setBillNo(travelInfo.getBillNo());
				updateOrder.setVoucherSyncSendTime(getSysdate());
				updateOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED);
				updateVoucher(updateOrder);
			} catch (Exception e1) {
				logger.error("差旅费报销单同步到财务系统出错后,更定状态为发送失败，失败！+order=" + updateOrder, e1);
			}
			logger.error("差旅费报销单同步到财务系统出错：{}", e);
		}
		
		return result;
	}
	
	@Override
	public FcsBaseResult updateVoucher(final UpdateVoucherOrder order) {
		return commonProcess(order, "更新凭证号同步状态", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				FormTravelExpenseDO travelDO = formTravelExpenseDAO.findByBillNo(order.getBillNo());
				if (travelDO == null) {
					throw ExceptionFactory
						.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "差旅费报销单不存在");
				}
				
				//同步状态
				if (order.getVoucherStatus() != null) {
					if (order.getVoucherStatus() == VoucherStatusEnum.SYNC_DELETE
						&& StringUtil.equals(order.getVoucherNo(), travelDO.getVoucherNo())) {
						travelDO.setVoucherStatus(VoucherStatusEnum.SYNC_DELETE.code());
					} else if (order.getVoucherStatus() != VoucherStatusEnum.SYNC_DELETE) {
						travelDO.setVoucherStatus(order.getVoucherStatus().code());
					}
				}
				
				//凭证号
				travelDO.setVoucherNo(order.getVoucherNo());
				
				//同步发送时间
				if (order.getVoucherSyncSendTime() != null)
					travelDO.setVoucherSyncSendTime(order.getVoucherSyncSendTime());
				//同步完成时间
				if (order.getVoucherSyncFinishTime() != null)
					travelDO.setVoucherSyncFinishTime(order.getVoucherSyncFinishTime());
				
				formTravelExpenseDAO.update(travelDO);
				
				return null;
			}
		}, null, null);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public List<FormTravelExpenseDetailInfo> findApprlInfoByDeptId(String deptId,
																	Date applyTimeStart,
																	Date applyTimeEnd) {
		
		String timeStart = applyTimeStart == null ? null : DateUtil.simpleFormat(applyTimeStart);
		String timeEnd = applyTimeEnd == null ? null : DateUtil.simpleFormat(applyTimeEnd);
		List<FormTravelExpenseDetailInfo> detailList = null;
		List<FormTravelExpenseDetailDO> detailDOs = formTravelExpenseDetailDAO
			.findApprlInfoByDeptId(deptId, timeStart, timeEnd);
		if (detailDOs != null && detailDOs.size() > 0) {
			detailList = new ArrayList<FormTravelExpenseDetailInfo>(detailDOs.size());
			for (FormTravelExpenseDetailDO detailDO : detailDOs) {
				FormTravelExpenseDetailInfo detailInfo = new FormTravelExpenseDetailInfo();
				BeanCopier.staticCopy(detailDO, detailInfo, UnBoxingConverter.getInstance());
				detailList.add(detailInfo);
			}
		}
		
		return detailList;
	}
	
	private void checkDeptBalance(TravelExpenseOrder order) {
		List<TravelExpenseDetailOrder> detailOrders = order.getDetailList();
		if (detailOrders == null || detailOrders.isEmpty()) {
			return;
		}
		
		HashMap<String, Money> checkMap = new HashMap<String, Money>();
		for (TravelExpenseDetailOrder detailOrder : detailOrders) {
			String deptIdNm = detailOrder.getDeptId() + "," + detailOrder.getDeptName();
			Money dMoney = checkMap.get(deptIdNm);
			if (dMoney == null) {
				checkMap.put(deptIdNm, detailOrder.getTotalAmount());
			} else {
				checkMap.put(deptIdNm, dMoney.add(detailOrder.getTotalAmount()));
			}
		}
		for (String deptIdNm : checkMap.keySet()) {
			String[] deptIdNmArr = deptIdNm.split(",");
			if (deptIdNmArr.length == 2) {
				String deptId = deptIdNmArr[0];
				String deptNm = deptIdNmArr[1];
				Money expense = checkMap.get(deptIdNm);
				Money balance = budgetService.queryBalanceByDeptCategoryId(Long.valueOf(deptId),
					SpeCostCategory.TRAVEL.categoryId(), order.getApplicationTime(), true);
				if (balance != null && expense.compareTo(balance) > 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE, "部门【" + deptNm
																						+ "】预算不足");
				}
			}
		}
	}
	
	@Override
	public FormBaseResult updatePayBank(TravelExpenseOrder order) {
		FormBaseResult result = createResult();
		FormTravelExpenseDO formTravelExpense = formTravelExpenseDAO
			.findByFormId(order.getFormId());
		formTravelExpense.setPayBank(order.getPayBank());
		formTravelExpense.setPayBankAccount(order.getPayBankAccount());
		formTravelExpenseDAO.update(formTravelExpense);
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public FcsBaseResult syncForecast(long formId, boolean afterAudit) {
		FcsBaseResult result = createResult();
		try {
			//非公务卡支付才预测
			FormTravelExpenseDO apply = formTravelExpenseDAO.findByFormId(formId);
			Money applyAmount = Money.zero();
			if (apply != null
				&& !StringUtil.equals(apply.getIsOfficialCard(), BooleanEnum.IS.code())) {
				applyAmount = apply.getAmount();
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
				forecastAccountOrder.setForecastMemo("差旅费报销（" + apply.getBillNo() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.OUT_ZFGLXT);
				forecastAccountOrder.setFundDirection(FundDirectionEnum.OUT);
				forecastAccountOrder.setOrderNo(apply.getBillNo());
				forecastAccountOrder.setSystemForm(SystemEnum.FM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(apply.getExpenseDeptId()));
				forecastAccountOrder.setUsedDeptName(apply.getDeptName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.TRAVEL_EXPENSE);
				logger.info("差旅费资金流出预测 , projectCode：{}, forecastAccountOrder：{} ",
					apply.getBillNo(), forecastAccountOrder);
				forecastService.add(forecastAccountOrder);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("同步预测数据出错");
			logger.error("同步差旅费预测数据出错：formId{} {}", formId, e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult confirmBranchPay(final ConfirmBranchPayOrder order) {
		return commonProcess(order, "分支机构确认付款(差旅费)", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				
				FormTravelExpenseDO expenseDO = formTravelExpenseDAO.findByBillNo(order.getBillNo());
				
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
				
				formTravelExpenseDAO.update(expenseDO);
				
				return null;
			}
		}, null, null);
	}
}
