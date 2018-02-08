/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:49:08 创建
 */
package com.born.fcs.fm.biz.service.innerLoan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.FormInnerLoanDO;
import com.born.fcs.fm.dataobject.FormInnerLoanFormDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.ws.enums.FormInnerLoanInterestTypeEnum;
import com.born.fcs.fm.ws.enums.FormInnerLoanTypeEnum;
import com.born.fcs.fm.ws.info.innerLoan.FormInnerLoanFormInfo;
import com.born.fcs.fm.ws.info.innerLoan.FormInnerLoanInfo;
import com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanOrder;
import com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanQueryOrder;
import com.born.fcs.fm.ws.result.innerLoan.FormInnerLoanResult;
import com.born.fcs.fm.ws.service.innerLoan.FormInnerLoanService;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Service("formInnerLoanService")
public class FormInnerLoanServiceImpl extends BaseFormAutowiredDomainService implements
																			FormInnerLoanService {
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.innerLoan.FormInnerLoanService#save(com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanOrder)
	 */
	@Override
	public FormBaseResult save(final FormInnerLoanOrder order) {
		
		return commonFormSaveProcess(order, "保存内部借款单", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				FormInfo form = (FormInfo) FcsFmDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
				}
				
				FormInnerLoanDO innerLoan = formInnerLoanDAO.findByFormId(form.getFormId());
				if (innerLoan == null) {
					innerLoan = new FormInnerLoanDO();
					convertOrder2DO(order, innerLoan);
					innerLoan.setFormId(form.getFormId());
					innerLoan.setRawAddTime(now);
					innerLoan.setBillNo(genBillNo());
					formInnerLoanDAO.insert(innerLoan);
				} else {
					order.setId(innerLoan.getId());
					
					convertOrder2DO(order, innerLoan);
					innerLoan.setFormId(form.getFormId());
					formInnerLoanDAO.update(innerLoan);
				}
				
				return null;
			}
		}, null, null);
	}
	
	private void convertOrder2DO(FormInnerLoanOrder order, FormInnerLoanDO loanDO) {
		MiscUtil.copyPoObject(loanDO, order);
		if (order.getApplyDeptId() != null) {
			loanDO.setApplyDeptId(order.getApplyDeptId());
		}
		if (order.getApplyUserId() != null) {
			loanDO.setApplyUserId(order.getApplyUserId());
		}
		if (order.getFormInnerLoanInterestType() != null) {
			loanDO.setFormInnerLoanInterestType(order.getFormInnerLoanInterestType().code());
		}
		if (order.getInnerLoanType() != null) {
			loanDO.setInnerLoanType(order.getInnerLoanType().code());
		}
	}
	
	/**
	 * @param id
	 * @return
	 * @see com.born.fcs.fm.ws.service.innerLoan.FormInnerLoanService#findById(java.lang.Long)
	 */
	@Override
	public FormInnerLoanResult findById(Long id) {
		logger.info("进入FormInnerLoanServiceImpl的findById方法，入参：" + id);
		FormInnerLoanResult result = new FormInnerLoanResult();
		if (id <= 0) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			logger.error("主键不能为空！");
			return result;
		}
		try {
			FormInnerLoanDO innerLoanDO = formInnerLoanDAO.findById(id);
			FormInnerLoanInfo info = new FormInnerLoanInfo();
			convertDO2Info(innerLoanDO, info);
			result.setFormInnerLoanInfo(info);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("查询内部借款单信息失败，原因:" + e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	private void convertDO2Info(FormInnerLoanDO innerLoanDO, FormInnerLoanInfo info) {
		MiscUtil.copyPoObject(info, innerLoanDO);
		info.setFormInnerLoanInterestType(FormInnerLoanInterestTypeEnum.getByCode(innerLoanDO
			.getFormInnerLoanInterestType()));
		info.setInnerLoanType(FormInnerLoanTypeEnum.getByCode(innerLoanDO.getInnerLoanType()));
	}
	
	/**
	 * @param id
	 * @return
	 * @see com.born.fcs.fm.ws.service.innerLoan.FormInnerLoanService#findByFormId(java.lang.Long)
	 */
	@Override
	public FormInnerLoanResult findByFormId(Long id) {
		logger.info("进入FormInnerLoanServiceImpl的findByFormId方法，入参：" + id);
		FormInnerLoanResult result = new FormInnerLoanResult();
		if (id <= 0) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			logger.error("formid不能为空！");
			return result;
		}
		try {
			FormInnerLoanDO innerLoanDO = formInnerLoanDAO.findByFormId(id);
			FormInnerLoanInfo info = new FormInnerLoanInfo();
			convertDO2Info(innerLoanDO, info);
			result.setFormInnerLoanInfo(info);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("查询内部借款单信息失败，原因:" + e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.innerLoan.FormInnerLoanService#queryInnerLoan(com.born.fcs.fm.ws.order.innerLoan.FormInnerLoanQueryOrder)
	 */
	@Override
	public QueryBaseBatchResult<FormInnerLoanInfo> queryInnerLoan(FormInnerLoanQueryOrder order) {
		logger.info("进入FormInnerLoanServiceImpl的queryInnerLoan方法，入参：" + order);
		QueryBaseBatchResult<FormInnerLoanInfo> result = new QueryBaseBatchResult<FormInnerLoanInfo>();
		
		try {
			FormInnerLoanDO queryDO = new FormInnerLoanDO();
			BeanCopier.staticCopy(order, queryDO);
			
			long totalCount = 0;
			// 查询totalcount
			totalCount = formInnerLoanDAO.findByConditionCount(queryDO, order.getBackTimeEnd());
			
			PageComponent component = new PageComponent(order, totalCount);
			// 查询list
			List<FormInnerLoanDO> list = Lists.newArrayList();
			list = formInnerLoanDAO.findByCondition(queryDO, order.getBackTimeEnd(),
				component.getFirstRecord(), component.getPageSize());
			
			List<FormInnerLoanInfo> pageList = new ArrayList<FormInnerLoanInfo>();
			for (FormInnerLoanDO item : list) {
				FormInnerLoanInfo info = new FormInnerLoanInfo();
				convertDO2Info(item, info);
				
				pageList.add(info);
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			result.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询内部借款单信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<FormInnerLoanFormInfo> searchForm(FormInnerLoanQueryOrder order) {
		QueryBaseBatchResult<FormInnerLoanFormInfo> batchResult = new QueryBaseBatchResult<FormInnerLoanFormInfo>();
		try {
			FormInnerLoanFormDO innerLoanForm = new FormInnerLoanFormDO();
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("f.form_id");
				order.setSortOrder("desc");
			}
			BeanCopier.staticCopy(order, innerLoanForm);
			if (order.getInnerLoanType() != null) {
				innerLoanForm.setInnerLoanType(order.getInnerLoanType().code());
			}
			if (order.getFormStatus() != null) {
				innerLoanForm.setFormStatus(order.getFormStatus().code());
			}
			if (innerLoanForm.getBackTime() != null) {
				innerLoanForm.setBackTime(DateUtil.getStartTimeOfTheDate(innerLoanForm
					.getBackTime()));
			}
			if (innerLoanForm.getBackTimeEnd() != null) {
				innerLoanForm.setBackTimeEnd(DateUtil.getStartTimeOfTheDate(innerLoanForm
					.getBackTimeEnd()));
			}
			
			long totalCount = busiDAO.searchInnerLoanFormCount(innerLoanForm);
			PageComponent component = new PageComponent(order, totalCount);
			List<FormInnerLoanFormDO> dataList = busiDAO.searchInnerLoanForm(innerLoanForm);
			
			List<FormInnerLoanFormInfo> list = Lists.newArrayList();
			for (FormInnerLoanFormDO DO : dataList) {
				FormInnerLoanFormInfo info = new FormInnerLoanFormInfo();
				BeanCopier.staticCopy(DO, info);
				info.setFormInnerLoanInterestType(FormInnerLoanInterestTypeEnum.getByCode(DO
					.getFormInnerLoanInterestType()));
				info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(DO.getFormStatus()));
				info.setInnerLoanType(FormInnerLoanTypeEnum.getByCode(DO.getInnerLoanType()));
				
				list.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询收款单失败 {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	/**
	 * 生成单据号
	 * @param busiType
	 * @return
	 */
	private String genBillNo() {
		//7、差旅费报销单/费用支付申请单单据号更改：字母（缩写）+6位流水号；
		
		FormInnerLoanDO condition = new FormInnerLoanDO();
		condition.setBillNo("NBJK" + "%");
		long count = formInnerLoanDAO.findByConditionCount(condition, null);
		String billNo = "NBJK" + StringUtil.alignRight((count + 1) + "", 6, '0');
		return billNo;
	}
}
