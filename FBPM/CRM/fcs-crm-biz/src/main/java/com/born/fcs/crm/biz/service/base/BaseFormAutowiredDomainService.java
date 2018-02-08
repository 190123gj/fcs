package com.born.fcs.crm.biz.service.base;

import java.util.Date;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.born.fcs.crm.biz.convert.UnBoxingConverter;
import com.born.fcs.crm.biz.exception.ExceptionFactory;
import com.born.fcs.crm.biz.exception.FcsCrmBizException;
import com.born.fcs.crm.dal.dataobject.FormDO;
import com.born.fcs.crm.dal.dataobject.FormRelatedUserDO;
import com.born.fcs.crm.domain.context.FcsCrmDomainContext;
import com.born.fcs.crm.domain.context.FcsCrmDomainHolder;
import com.born.fcs.crm.domain.exception.FcsCrmDomainException;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.service.base.CheckBeforeProcessService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.Order;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import com.yjf.common.service.base.ProcessInvokeService;

/**
 * @Filename BaseAutowiredService.java
 * @Description
 * @Version 1.0
 * @Author qichunhai
 * @Email qchunhai@yiji.com
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-2</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class BaseFormAutowiredDomainService extends BaseAutowiredDomainService {
	
	/**
	 * 表单数据存储的通用处理
	 * @param order
	 * @param processBizName
	 * @param beforeProcessInvokeService
	 * @param processInvokeService
	 * @param successProcessInvokeService
	 * @return
	 */
	protected FormBaseResult commonFormSaveProcess(	final FormOrderBase order,
													final String processBizName,
													final BeforeProcessInvokeService beforeProcessInvokeService,
													final ProcessInvokeService processInvokeService,
													final AfterProcessInvokeService successProcessInvokeService) {
		return commonFormSaveProcess(order, processBizName, null, beforeProcessInvokeService,
			processInvokeService, successProcessInvokeService);
	}
	
	/**
	 * 涉及到Form表单保存的通用处理
	 * @param order
	 * @param processBizName
	 * @param checkBeforeProcessService
	 * @param beforeProcessInvokeService
	 * @param processInvokeService
	 * @param successProcessInvokeService
	 * @return
	 */
	protected FormBaseResult commonFormSaveProcess(	final FormOrderBase order,
													final String processBizName,
													final CheckBeforeProcessService checkBeforeProcessService,
													final BeforeProcessInvokeService beforeProcessInvokeService,
													final ProcessInvokeService processInvokeService,
													final AfterProcessInvokeService successProcessInvokeService) {
		
		logger.info("-进入{} " + this.getClass().getName()
					+ "  commonFormSaveProcess processBizName={} order={} ", processBizName, order);
		
		final Date nowDate = getSysdate();
		boolean isClear = false;
		if (FcsCrmDomainHolder.get() == null) {
			FcsCrmDomainHolder.set(new FcsCrmDomainContext<Order>(nowDate, order, null));
			isClear = true;
		}
		
		FormBaseResult result = null;
		if (FcsCrmDomainHolder.get().getAttribute("result") == null) {
			result = createResult();
		} else {
			result = (FormBaseResult) FcsCrmDomainHolder.get().getAttribute("result");
		}
		
		try {
			
			checkOrder(order);
			if (checkBeforeProcessService != null)
				checkBeforeProcessService.check();
			result = transactionTemplate.execute(new TransactionCallback<FormBaseResult>() {
				
				@SuppressWarnings("deprecation")
				@Override
				public FormBaseResult doInTransaction(TransactionStatus status) {
					FormBaseResult result = null;
					if (FcsCrmDomainHolder.get().getAttribute("result") == null) {
						result = createResult();
						FcsCrmDomainHolder.get().addAttribute("result", result);
					} else {
						result = (FormBaseResult) FcsCrmDomainHolder.get().getAttribute("result");
					}
					
					try {
						// 激活领域模型
						Domain domain = null;
						FcsCrmDomainHolder.get().addAttribute("result", result);
						
						//签报不修改表单信息
						if (order.getIsFormChangeApply() != BooleanEnum.IS) {
							
							//处理Form表单信息
							Long formId = order.getFormId();
							FormDO form = null;
							if (formId != null && formId > 0) {
								form = formDAO.findByFormId(formId);
								if (form == null) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.WRONG_REQ_PARAM, "表单ID异常");
								}
							}
							
							//新增form
							if (form == null) {
								
								form = new FormDO();
								FormCodeEnum formCode = order.getFormCode();
								if (formCode == null) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.INCOMPLETE_REQ_PARAM, "请确认表单编码");
								}
								
								BeanCopier.staticCopy(order, form, UnBoxingConverter.getInstance());
								form.setFormCode(formCode.getCode());
								form.setFormName(formCode.getMessage());
								form.setCheckStatus(String.valueOf(order.getCheckStatus()));
								//								form.setCheckStatus(formCode.defaultCheckStatus());
								//								if (StringUtil.isNotBlank(order.getDefaultCheckStatus()))
								//									form.setCheckStatus(order.getDefaultCheckStatus());
								form.setRawAddTime(nowDate);
								form.setStatus(FormStatusEnum.DRAFT.code());
								form.setFormUrl(formCode.getViewUrl());
								//								String checkStatus = form.getCheckStatus();
								//								char[] sta = checkStatus.toCharArray();
								//								
								//								Integer s = order.getCheckStatus();
								//								
								//								if (s == null)
								//									s = 0;
								//								if (s > 0)
								//									s = 1;
								//								
								//								//验证状态改变
								//								int i = getCheckIndex(order, sta);
								//								if (i != -1) { //-1表示当前存储不验证
								//									if (s != Integer.valueOf(String.valueOf(sta[i]))) {
								//										sta[i] = String.valueOf(s).charAt(0);
								//										form.setCheckStatus(String.valueOf(sta));
								//									}
								//								}
								formId = formDAO.insert(form);
								FormRelatedUserDO formRelatedUser = new FormRelatedUserDO();
								BeanCopier.staticCopy(form, formRelatedUser);
								formRelatedUserDAO.insert(formRelatedUser);
								form.setFormId(formId);
								
							} else { //修改form验证状态
							
								//								String checkStatus = form.getCheckStatus();
								//								char[] sta = checkStatus.toCharArray();
								//								
								//								//验证状态改变
								//								Integer s = order.getCheckStatus();
								//								
								//								if (s == null)
								//									s = 0;
								//								if (s < 0 || s > 1)
								//									s = 1;
								//								
								//								//验证状态改变
								//								int i = getCheckIndex(order, sta);
								
								boolean hasChange = false;
								//如果传入了相关项目编号,同时更新相关项目编号到表单
								if ((StringUtil.isBlank(form.getRelatedProjectCode()) && StringUtil
									.isNotBlank(order.getRelatedProjectCode()))) {
									form.setRelatedProjectCode(order.getRelatedProjectCode());
									hasChange = true;
								}
								if (StringUtil.equals("1", String.valueOf(order.getCheckStatus()))) {
									form.setCheckStatus("1");
									hasChange = true;
								}
								
								//								//-1表示当前存储不验证，或者验证状态有变
								//								if (i != -1 && s != Integer.valueOf(String.valueOf(sta[i]))) {
								//									sta[i] = String.valueOf(s).charAt(0);
								//									form.setCheckStatus(String.valueOf(sta));
								//									hasChange = true;
								//								}
								
								if (hasChange)
									formDAO.update(form);
								
							}
							
							//转换成info
							FormInfo formInfo = new FormInfo();
							BeanCopier.staticCopy(form, formInfo);
							formInfo.setFormCode(FormCodeEnum.getByCode(form.getFormCode()));
							formInfo.setStatus(FormStatusEnum.getByCode(form.getStatus()));
							//表单信息
							FcsCrmDomainHolder.get().addAttribute("form", formInfo);
							result.setFormInfo(formInfo);
						}
						if (beforeProcessInvokeService != null) {
							domain = beforeProcessInvokeService.before();
							logger.info("beforeProcessInvokeService.before():" + domain);
						}
						
						if (domain != null) {
							FcsCrmDomainHolder.get().setDomain(domain);
						}
						if (processInvokeService != null) {
							processInvokeService.process(domain);
							logger.info("processInvokeService.process():" + domain);
						}
						
						if (result.getFcsResultEnum() == FcsResultEnum.UN_KNOWN_EXCEPTION) {
							result.setSuccess(true);
						}
						
						if (order instanceof ProcessOrder
							&& order.getIsFormChangeApply() != BooleanEnum.IS) {
							addOperationJournalInfo(order, processBizName, processBizName,
								order.toString());
						}
						
					} catch (FcsCrmBizException eex) {
						setYrdException(status, result, eex, eex.getErrorMsg());
						
					} catch (FcsCrmDomainException e) {
						setFcsPmDomainException(status, result, e, e.getErrorMsg());
					} catch (Exception e) {
						setDbException(status, result, e);
					} catch (Throwable e) {
						setDbException(status, result, e);
					}
					
					return result;
				}
				
				/**
				 * 验证位置参数异常
				 * @param order
				 * @param sta
				 */
				private int getCheckIndex(final FormOrderBase order, char[] sta) {
					Integer i = order.getCheckIndex();
					if (i == null) {
						i = 0;
						order.setCheckIndex(0);
					}
					if (i + 1 > sta.length) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.FORM_CHECK_INDEX_ERROR, "请确认表单验证位置参数");
					}
					return i;
				}
				
			});
		} catch (FcsCrmBizException eex) {
			logger.error(eex.getErrorMsg(), eex);
			result.setSuccess(false);
			result.setFcsResultEnum(eex.getResultCode());
			result.setMessage(processBizName + "异常[" + eex.getErrorMsg() + "]");
			
		} catch (Exception ex) {
			setUnknownException(result, ex);
		} catch (Throwable e) {
			setUnknownException(result, e);
		}
		if (result.isSuccess()) {
			try {
				if (successProcessInvokeService != null) {
					successProcessInvokeService.after(FcsCrmDomainHolder.get().getDomain());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
		}
		if (isClear) {
			FcsCrmDomainHolder.clear();
		}
		logger.info("-处理结束{}  commonFormSaveProcess processBizName={} result={}", this.getClass()
			.getName(), processBizName, result);
		return result;
	}
	
	/**
	 * 默认返回结果类型
	 * @return
	 */
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
}
