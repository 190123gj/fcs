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
import com.born.fcs.fm.dal.dataobject.ExpenseCxDetailDO;
import com.born.fcs.fm.dal.dataobject.FormDO;
import com.born.fcs.fm.dal.dataobject.FormLabourCapitalDO;
import com.born.fcs.fm.dal.dataobject.FormLabourCapitalDetailDO;
import com.born.fcs.fm.dal.dataobject.FormPayChangeDetailDO;
import com.born.fcs.fm.dal.queryCondition.LabourCapitalQueryCondition;
import com.born.fcs.fm.dataobject.FormLabourCapitalDetailAllFormDO;
import com.born.fcs.fm.dataobject.FormLabourCapitalFormDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.integration.risk.service.KingdeeTogetheFacadeClient;
import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.ChangeSourceTypeEnum;
import com.born.fcs.fm.ws.enums.CostDirectionEnum;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.forecast.SysForecastParamAllInfo;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalDetailAllInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormLabourCapitalInfo;
import com.born.fcs.fm.ws.info.payment.FormPayChangeDetailInfo;
import com.born.fcs.fm.ws.order.common.SyncKingdeeBasicDataOrder;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.order.payment.LabourCapitalDetailOrder;
import com.born.fcs.fm.ws.order.payment.LabourCapitalOrder;
import com.born.fcs.fm.ws.order.payment.LabourCapitalQueryOrder;
import com.born.fcs.fm.ws.result.bank.BankMessageResult;
import com.born.fcs.fm.ws.result.forecast.SysForecastParamResult;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.fm.ws.service.payment.BudgetService;
import com.born.fcs.fm.ws.service.payment.CostCategoryService;
import com.born.fcs.fm.ws.service.payment.LabourCapitalService;
import com.born.fcs.pm.util.BusinessNumberUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
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

@Service("labourCapitalService")
public class LabourCapitalServiceImpl extends BaseFormAutowiredDomainService implements
																			LabourCapitalService {
	
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
	public QueryBaseBatchResult<FormLabourCapitalInfo> queryPage(LabourCapitalQueryOrder order) {
		QueryBaseBatchResult<FormLabourCapitalInfo> batchResult = new QueryBaseBatchResult<FormLabourCapitalInfo>();
		
		try {
			LabourCapitalQueryCondition condition = getQueryCondition(order);
			
			long totalCount = busiDAO.findLabourCapitalByConditionCount(condition);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<FormLabourCapitalFormDO> list = busiDAO.findLabourCapitalByCondition(condition);
			
			List<FormLabourCapitalInfo> pageList = new ArrayList<FormLabourCapitalInfo>(list.size());
			for (FormLabourCapitalFormDO DO : list) {
				FormLabourCapitalInfo info = new FormLabourCapitalInfo();
				setFormLabourCapitalInfo(DO, info, order.isDetail());
				pageList.add(info);
			}
			
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询劳资及税金申请单列表失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public FormLabourCapitalInfo queryByFormId(long formId) {
		FormLabourCapitalDO formLabourCapital = formLabourCapitalDAO.findByFormId(formId);
		if (formLabourCapital != null) {
			FormLabourCapitalInfo FormLabourCapitalInfo = new FormLabourCapitalInfo();
			setFormLabourCapitalInfo(formLabourCapital, FormLabourCapitalInfo, true);
			return FormLabourCapitalInfo;
		}
		return null;
	}
	
	@Override
	public FormLabourCapitalInfo queryById(long LabourCapitalId) {
		FormLabourCapitalDO formLabourCapital = formLabourCapitalDAO.findById(LabourCapitalId);
		if (formLabourCapital != null) {
			FormLabourCapitalInfo FormLabourCapitalInfo = new FormLabourCapitalInfo();
			setFormLabourCapitalInfo(formLabourCapital, FormLabourCapitalInfo, true);
			return FormLabourCapitalInfo;
		}
		return null;
	}
	
	private void setChangeDetail(LabourCapitalOrder order) {
		//		List<FormPayChangeDetailDO> changeDOs = new ArrayList<FormPayChangeDetailDO>();
		
		FormPayChangeDetailDO changeDO = new FormPayChangeDetailDO();
		changeDO.setSourceType(ChangeSourceTypeEnum.LABOUR_CAPITAL.code());
		changeDO.setUserId(order.getUserId());
		changeDO.setUserName(order.getUserName());
		changeDO.setDocumentType("input");
		
		FormLabourCapitalDO expenseDO = formLabourCapitalDAO.findById(order.getLabourCapitalId());
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
		
		for (LabourCapitalDetailOrder detailOrder : order.getDetailList()) {
			FormLabourCapitalDetailDO detailDO = formLabourCapitalDetailDAO.findById(detailOrder
				.getDetailId());
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
	public FormBaseResult save(final LabourCapitalOrder order) {
		return commonFormSaveProcess(order, "劳资及税金申请单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				if (order.getDetailList() == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
						"劳资及税金申请单明细不存在");
				}
				
				// 20161104 添加判断，若为onlyChangeDetailList 只修改明细
				if (BooleanEnum.YES == order.getOnlyChangeDetailList()) {
					for (LabourCapitalDetailOrder detailOrder : order.getDetailList()) {
						FormLabourCapitalDetailDO detailDO = formLabourCapitalDetailDAO
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
						formLabourCapitalDetailDAO.update(detailDO);
					}
					// 更定主表金额
					FormLabourCapitalDO expenseDO = formLabourCapitalDAO.findById(order
						.getLabourCapitalId());
					if (order.getAmount().greaterThan(Money.zero())) {
						expenseDO.setAmount(order.getAmount());
					}
					// 更定报销事由
					if (StringUtil.isNotBlank(order.getReimburseReason())) {
						expenseDO.setReimburseReason(order.getReimburseReason());
					}
					formLabourCapitalDAO.update(expenseDO);
					
					setChangeDetail(order);
					return null;
					
				}
				
				checkDeptBalance(order);//预算check
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				
				FormInfo form = (FormInfo) FcsFmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				FormBaseResult result = (FormBaseResult) FcsFmDomainHolder.get().getAttribute(
					"result");
				
				//				// 20161114添加判断。若选择了公务卡，删除掉对公对私判定
				//				if (BooleanEnum.IS == order.getIsOfficialCard()) {
				//					order.setDirection(null);
				//				}
				// 设定为非公务卡，对公
				order.setIsOfficialCard(BooleanEnum.NO);
				order.setDirection(CostDirectionEnum.PUBLIC);
				
				FormLabourCapitalDO formLabourCapital = formLabourCapitalDAO.findByFormId(form
					.getFormId());
				if (formLabourCapital == null) {
					formLabourCapital = new FormLabourCapitalDO();
					BeanCopier.staticCopy(order, formLabourCapital, UnBoxingConverter.getInstance());
					formLabourCapital.setReamount(order.getReamount());
					if (order.getIsOfficialCard() != null) {
						formLabourCapital.setIsOfficialCard(order.getIsOfficialCard().code());
					}
					if (order.getDirection() != null) {
						formLabourCapital.setDirection(order.getDirection().code());
					}
					formLabourCapital.setDeptName(order.getExpenseDeptName());
					formLabourCapital.setFormId(form.getFormId());
					formLabourCapital.setBillNo(genBillNo());
					formLabourCapital.setVoucherStatus(VoucherStatusEnum.NOT_SEND.code());
					if (StringUtil.isBlank(formLabourCapital.getAccountStatus())) {
						formLabourCapital.setAccountStatus(AccountStatusEnum.DRAFT.code());
					}
					formLabourCapital.setRawAddTime(now);
					long LabourCapitalId = formLabourCapitalDAO.insert(formLabourCapital);
					FormLabourCapitalInfo info = new FormLabourCapitalInfo();
					info.setLabourCapitalId(LabourCapitalId);
					info.setBillNo(formLabourCapital.getBillNo());
					result.setReturnObject(info);
					
					List<LabourCapitalDetailOrder> detailOrders = order.getDetailList();
					for (LabourCapitalDetailOrder detailOrder : detailOrders) {
						FormLabourCapitalDetailDO detail = new FormLabourCapitalDetailDO();
						BeanCopier.staticCopy(detailOrder, detail, UnBoxingConverter.getInstance());
						detail.setLabourCapitalId(LabourCapitalId);
						detail.setRawAddTime(now);
						formLabourCapitalDetailDAO.insert(detail);
					}
					
					result.setMessage(formLabourCapital.getBillNo());
				} else {
					long LabourCapitalId = formLabourCapital.getLabourCapitalId();
					formLabourCapital.setAttachmentsNum(order.getAttachmentsNum());
					formLabourCapital.setExpenseDeptId(order.getExpenseDeptId());
					formLabourCapital.setDeptName(order.getExpenseDeptName());
					formLabourCapital.setDeptHead(order.getDeptHead());
					formLabourCapital.setApplicationTime(order.getApplicationTime());
					formLabourCapital.setRelationForm(order.getRelationForm());
					formLabourCapital.setAgent(order.getAgent());
					if (order.getDirection() != null) {
						formLabourCapital.setDirection(order.getDirection().code());
						if (order.getDirection() == CostDirectionEnum.PRIVATE) {
							order.setBankId(0);
						} else {
							order.setPayeeId(0);
						}
					}
					formLabourCapital.setBankId(order.getBankId());
					formLabourCapital.setPayeeId(order.getPayeeId());
					formLabourCapital.setPayee(order.getPayee());
					formLabourCapital.setBank(order.getBank());
					formLabourCapital.setBankAccount(order.getBankAccount());
					formLabourCapital.setAmount(order.getAmount());
					formLabourCapital.setIsReverse(order.getIsReverse());
					formLabourCapital.setReamount(order.getReamount());
					formLabourCapital.setCxids(order.getCxids());
					formLabourCapital.setReimburseReason(order.getReimburseReason());
					if (order.getIsOfficialCard() != null) {
						formLabourCapital.setIsOfficialCard(order.getIsOfficialCard().code());
					}
					if (StringUtil.isBlank(formLabourCapital.getAccountStatus())) {
						formLabourCapital.setAccountStatus(AccountStatusEnum.DRAFT.code());
					}
					formLabourCapitalDAO.update(formLabourCapital);
					FormLabourCapitalInfo info = new FormLabourCapitalInfo();
					info.setLabourCapitalId(formLabourCapital.getLabourCapitalId());
					info.setBillNo(formLabourCapital.getBillNo());
					result.setReturnObject(info);
					
					/** 明细 **/
					List<FormLabourCapitalDetailDO> oleDetailDOs = formLabourCapitalDetailDAO
						.findByLabourCapitalId(LabourCapitalId);
					List<LabourCapitalDetailOrder> detailOrders = order.getDetailList();
					for (LabourCapitalDetailOrder detailOrder : detailOrders) {
						if (detailOrder.getDetailId() == 0) {
							//新增
							FormLabourCapitalDetailDO detail = new FormLabourCapitalDetailDO();
							BeanCopier.staticCopy(detailOrder, detail,
								UnBoxingConverter.getInstance());
							detail.setLabourCapitalId(LabourCapitalId);
							detail.setRawAddTime(now);
							formLabourCapitalDetailDAO.insert(detail);
						} else {
							//更新
							for (int i = 0; i < oleDetailDOs.size(); i++) {
								FormLabourCapitalDetailDO updateDetailDO = oleDetailDOs.get(i);
								if (detailOrder.getDetailId() == updateDetailDO.getDetailId()) {
									updateDetailDO.setDeptId(detailOrder.getDeptId());
									updateDetailDO.setDeptName(detailOrder.getDeptName());
									updateDetailDO.setAmount(detailOrder.getAmount());
									updateDetailDO.setExpenseType(detailOrder.getExpenseType());
									updateDetailDO.setTaxAmount(detailOrder.getTaxAmount());
									updateDetailDO.setDeptId(detailOrder.getDeptId());
									formLabourCapitalDetailDAO.update(updateDetailDO);
									
									//移除已更新的数据，剩下需要删除的数据
									oleDetailDOs.remove(i);
									break;
								}
							}
						}
					}
					
					//删除
					for (FormLabourCapitalDetailDO deletDo : oleDetailDOs) {
						formLabourCapitalDetailDAO.deleteById(deletDo.getDetailId());
					}
					
					result.setMessage(formLabourCapital.getBillNo());
				}
				
				// 20161114 添加功能，已冲销的id需要更定为已冲销
				if (ListUtil.isNotEmpty(order.getReverseIds())) {
					for (String str : order.getReverseIds()) {
						FormLabourCapitalDetailDO detailDO = formLabourCapitalDetailDAO
							.findById(Long.valueOf(str));
						if (detailDO != null) {
							detailDO.setReverse(BooleanEnum.YES.code());
							formLabourCapitalDetailDAO.update(detailDO);
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
			FormLabourCapitalDetailDO detailDO = formLabourCapitalDetailDAO
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
	
	/**
	 * 生成单据号
	 * @param busiType
	 * @return
	 */
	private String genBillNo() {
		//7、差旅费报销单/劳资及税金申请单单据号更改：字母（缩写）+6位流水号；
		
		LabourCapitalQueryCondition condition = new LabourCapitalQueryCondition();
		condition.setBillNo("LZSJ" + "%");
		long count = formLabourCapitalDAO.findByConditionCount(condition);
		String billNo = "LZSJ" + StringUtil.alignRight((count + 1) + "", 6, '0');
		return billNo;
	}
	
	@SuppressWarnings("deprecation")
	private void setFormLabourCapitalInfo(FormLabourCapitalDO DO, FormLabourCapitalInfo info,
											boolean isDetail) {
		BeanCopier.staticCopy(DO, info, UnBoxingConverter.getInstance());
		if (StringUtil.isNotBlank(DO.getIsOfficialCard())) {
			info.setIsOfficialCard(BooleanEnum.getByCode(DO.getIsOfficialCard()));
		}
		if (StringUtil.isNotBlank(DO.getDirection())) {
			info.setDirection(CostDirectionEnum.getByCode(DO.getDirection()));
		}
		if (StringUtil.isNotBlank(DO.getVoucherStatus()))
			info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
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
			List<FormLabourCapitalDetailDO> detailDOs = formLabourCapitalDetailDAO
				.findByLabourCapitalId(DO.getLabourCapitalId());
			if (detailDOs != null && detailDOs.size() > 0) {
				List<FormLabourCapitalDetailInfo> detailList = new ArrayList<FormLabourCapitalDetailInfo>(
					detailDOs.size());
				for (FormLabourCapitalDetailDO detailDO : detailDOs) {
					FormLabourCapitalDetailInfo detailInfo = new FormLabourCapitalDetailInfo();
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
			
			// 20161117 添加修改记录
			
			List<FormPayChangeDetailDO> changes = formPayChangeDetailDAO.findByTypeAndSourceId(
				ChangeSourceTypeEnum.LABOUR_CAPITAL.code(), DO.getLabourCapitalId());
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
	private void setFormLabourCapitalInfo(FormLabourCapitalFormDO DO, FormLabourCapitalInfo info,
											boolean isDetail) {
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
			List<FormLabourCapitalDetailDO> detailDOs = formLabourCapitalDetailDAO
				.findByLabourCapitalId(DO.getLabourCapitalId());
			if (detailDOs != null && detailDOs.size() > 0) {
				List<FormLabourCapitalDetailInfo> detailList = new ArrayList<FormLabourCapitalDetailInfo>(
					detailDOs.size());
				for (FormLabourCapitalDetailDO detailDO : detailDOs) {
					FormLabourCapitalDetailInfo detailInfo = new FormLabourCapitalDetailInfo();
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
			
		}
	}
	
	@SuppressWarnings("deprecation")
	private LabourCapitalQueryCondition getQueryCondition(LabourCapitalQueryOrder order) {
		LabourCapitalQueryCondition condition = new LabourCapitalQueryCondition();
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
		}
		return condition;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public List<FormLabourCapitalDetailInfo> findSublInfoByDeptCategory(long deptId,
																		long categoryId,
																		Date applyTimeStart,
																		Date applyTimeEnd) {
		
		String timeStart = applyTimeStart == null ? null : DateUtil.simpleFormat(applyTimeStart);
		String timeEnd = applyTimeEnd == null ? null : DateUtil.simpleFormat(applyTimeEnd);
		List<FormLabourCapitalDetailInfo> detailList = null;
		List<FormLabourCapitalDetailDO> detailDOs = formLabourCapitalDetailDAO
			.findSublInfoByDeptCategory(deptId, categoryId, timeStart, timeEnd);
		if (detailDOs != null && detailDOs.size() > 0) {
			detailList = new ArrayList<FormLabourCapitalDetailInfo>(detailDOs.size());
			for (FormLabourCapitalDetailDO detailDO : detailDOs) {
				FormLabourCapitalDetailInfo detailInfo = new FormLabourCapitalDetailInfo();
				BeanCopier.staticCopy(detailDO, detailInfo, UnBoxingConverter.getInstance());
				detailList.add(detailInfo);
			}
		}
		
		return detailList;
	}
	
	private void checkDeptBalance(LabourCapitalOrder order) {
		List<LabourCapitalDetailOrder> detailOrders = order.getDetailList();
		if (detailOrders == null || detailOrders.isEmpty()) {
			return;
		}
		
		HashMap<String, Money> checkMap = new HashMap<String, Money>();
		for (LabourCapitalDetailOrder detailOrder : detailOrders) {
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
	public FormBaseResult updatePayBank(LabourCapitalOrder order) {
		FormBaseResult result = createResult();
		FormLabourCapitalDO formLabourCapital = formLabourCapitalDAO
			.findByFormId(order.getFormId());
		formLabourCapital.setPayBank(order.getPayBank());
		formLabourCapital.setPayBankAccount(order.getPayBankAccount());
		formLabourCapitalDAO.update(formLabourCapital);
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public FcsBaseResult syncForecast(long formId, boolean afterAudit) {
		FcsBaseResult result = createResult();
		try {
			//非公务卡支付才预测
			FormLabourCapitalDO apply = formLabourCapitalDAO.findByFormId(formId);
			Money applyAmount = Money.zero();
			Money repayAmount = Money.zero();
			if (apply != null
				&& !StringUtil.equals(apply.getIsOfficialCard(), BooleanEnum.IS.code())) {
				//还款，不需要预测
				//冲预付款，仅预测报销 金额大于预付款金额的情况，预测的数据为报销金额-预付款金额
				List<FormLabourCapitalDetailDO> feeList = formLabourCapitalDetailDAO
					.findByLabourCapitalId(apply.getLabourCapitalId());
				if (feeList != null) {
					for (FormLabourCapitalDetailDO fee : feeList) {
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
				forecastAccountOrder.setForecastMemo("劳资及税金（" + apply.getBillNo() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.OUT_ZFGLXT);
				forecastAccountOrder.setFundDirection(FundDirectionEnum.OUT);
				forecastAccountOrder.setOrderNo(apply.getBillNo());
				forecastAccountOrder.setSystemForm(SystemEnum.FM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(apply.getExpenseDeptId()));
				forecastAccountOrder.setUsedDeptName(apply.getDeptName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.LABOUR_EXPENSE);
				logger.info("劳资及税金资金流出预测 , projectCode：{}, forecastAccountOrder：{} ",
					apply.getBillNo(), forecastAccountOrder);
				forecastService.add(forecastAccountOrder);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("同步预测数据出错");
			logger.error("同步劳资及税金预测数据出错：formId{} {}", formId, e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult sync2FinancialSys(FormLabourCapitalInfo expenseInfo) {
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
			order.setDealManDeptCode(dealMan.getPrimaryOrg().getCode());
			//收款人名称
			order.setTransferName(expenseInfo.getPayee());
			order.setGwk(expenseInfo.getIsOfficialCard() == BooleanEnum.NO ? false : true);
			
			BankMessageResult bankResult = bankMessageService.findByAccount(expenseInfo
				.getPayBankAccount());
			
			BankMessageInfo payBank = bankResult.getBankMessageInfo();
			String bankAtCode = "";
			if (payBank != null)
				bankAtCode = payBank.getAtCode();
			
			List<FormLabourCapitalDetailInfo> ds = expenseInfo.getDetailList();
			if (ds != null) {
				List<ExpensesRequisitionsInfo> eRequisitionsInfos = new ArrayList<ExpensesRequisitionsInfo>(
					ds.size());
				for (FormLabourCapitalDetailInfo dinfo : ds) {
					ExpensesRequisitionsInfo eReqInfo = new ExpensesRequisitionsInfo();
					eReqInfo.setAmount(dinfo.getAmount().toString());
					eReqInfo.setTaxAmount(dinfo.getTaxAmount().toString());
					// TODO
					CostCategoryInfo feeType = costCategoryService.queryById(NumberUtil
						.parseLong(dinfo.getExpenseType()));
					if (feeType != null) {
						// 20161228 其他（劳资）更定为其他
						eReqInfo.setFeeType(feeType.getName());
						if ("其他（劳资）".equals(feeType.getName())) {
							eReqInfo.setFeeType("其他");
						}
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
				
				FormLabourCapitalDO expenseDO = formLabourCapitalDAO.findByBillNo(order.getBillNo());
				
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
				
				formLabourCapitalDAO.update(expenseDO);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<FormLabourCapitalDetailAllInfo> queryPageAll(	LabourCapitalQueryOrder order) {
		QueryBaseBatchResult<FormLabourCapitalDetailAllInfo> batchResult = new QueryBaseBatchResult<FormLabourCapitalDetailAllInfo>();
		
		try {
			LabourCapitalQueryCondition condition = getQueryCondition(order);
			
			long totalCount = busiDAO.findLabourCapitalByConditionCountReport(condition);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<FormLabourCapitalDetailAllFormDO> list = busiDAO
				.findLabourCapitalByConditionReport(condition);
			
			List<FormLabourCapitalDetailAllInfo> pageList = new ArrayList<FormLabourCapitalDetailAllInfo>(
				list.size());
			for (FormLabourCapitalDetailAllFormDO DO : list) {
				FormLabourCapitalDetailAllInfo info = new FormLabourCapitalDetailAllInfo();
				//setFormLabourCapitalInfo(DO, info, order.isDetail());
				MiscUtil.copyPoObject(info, DO);
				info.setVoucherStatus(VoucherStatusEnum.getByCode(DO.getVoucherStatus()));
				info.setIsOfficialCard(BooleanEnum.getByCode(DO.getIsOfficialCard()));
				info.setDirection(CostDirectionEnum.getByCode(DO.getDirection()));
				info.setAccountstatus(AccountStatusEnum.getByCode(DO.getAccountStatus()));
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setReverse(BooleanEnum.getByCode(DO.getReverse()));
				pageList.add(info);
			}
			
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询劳资及税金申请单列表失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
}
