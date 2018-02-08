package com.born.fcs.am.biz.service.base;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.born.fcs.am.biz.exception.ExceptionFactory;
import com.born.fcs.am.biz.exception.FcsPmBizException;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.domain.context.FcsPmDomainContext;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.domain.exception.FcsPmDomainException;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.born.fcs.pm.ws.order.common.OperationJournalAddOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.base.CheckBeforeProcessService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.util.money.Money;
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
public class BaseAutowiredDomainService extends BaseAutowiredDAOService {
	
	/** 事务模板 */
	@Autowired
	protected TransactionTemplate transactionTemplate;
	
	@Autowired
	protected SysParameterService sysParameterServiceClient;
	
	@Autowired
	protected ThreadPoolTaskExecutor taskExecutor;
	
	public static final String YRD_HOLDER_RESULT_KEY = "result";
	
	public static Money ZERO_MONEY = Money.zero();
	
	protected void checkOrder(Order order) {
		logger.info("[order={}]", order);
		
		if (null == order) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE,
				"order must not be null");
		}
		
		try {
			order.check();
		} catch (IllegalArgumentException ex) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
				"请求参数异常--" + ex.getLocalizedMessage());
			
		}
	}
	
	protected void setYrdException(TransactionStatus status, FcsBaseResult result,
									FcsPmBizException eex, String errorMessage) {
		if (status != null)
			status.setRollbackOnly();
		result.setSuccess(false);
		result.setFcsResultEnum(eex.getResultCode());
		result.setMessage(errorMessage);
		logger.error(eex.getLocalizedMessage() + " ==errMesaage=" + eex.getErrorMsg()
						+ " == result  =" + result);
	}
	
	protected void setFcsPmDomainException(TransactionStatus status, FcsBaseResult result,
											FcsPmDomainException eex, String errorMessage) {
		status.setRollbackOnly();
		result.setSuccess(false);
		result.setFcsResultEnum(FcsResultEnum.getByCode(eex.getDomainResult().getCode()));
		result.setMessage(errorMessage);
		logger.error(eex.getLocalizedMessage() + " ==errMesaage=" + eex.getErrorMsg(), eex);
	}
	
	protected void setUnknownException(FcsBaseResult result, Throwable ex) {
		logger.error(ex.getLocalizedMessage(), ex);
		result.setSuccess(false);
		result.setFcsResultEnum(FcsResultEnum.UN_KNOWN_EXCEPTION);
	}
	
	protected void setDbException(TransactionStatus status, FcsBaseResult result, Throwable e) {
		logger.error(e.getLocalizedMessage(), e);
		status.setRollbackOnly();
		result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		result.setSuccess(false);
	}
	
	protected FcsBaseResult commonProcess(	final Order order,
											final String processBizName,
											final CheckBeforeProcessService checkBeforeProcessService,
											final BeforeProcessInvokeService beforeProcessInvokeService,
											final ProcessInvokeService processInvokeService,
											final AfterProcessInvokeService successProcessInvokeService) {
		logger.info("-进入{} " + this.getClass().getName()
					+ "  commonProcess processBizName={} order={} ", processBizName, order);
		
		final Date nowDate = getSysdate();
		boolean isClear = false;
		if (FcsPmDomainHolder.get() == null) {
			FcsPmDomainHolder.set(new FcsPmDomainContext<Order>(nowDate, order, null));
			isClear = true;
		}
		
		FcsBaseResult result = null;
		if (FcsPmDomainHolder.get().getAttribute("result") == null) {
			result = createResult();
			FcsPmDomainHolder.get().addAttribute("result", result);
		} else {
			result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute("result");
		}
		
		try {
			
			checkOrder(order);
			if (checkBeforeProcessService != null)
				checkBeforeProcessService.check();
			result = transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
				
				@Override
				public FcsBaseResult doInTransaction(TransactionStatus status) {
					FcsBaseResult result = null;
					if (FcsPmDomainHolder.get().getAttribute("result") == null) {
						result = createResult();
					} else {
						result = (FcsBaseResult) FcsPmDomainHolder.get().getAttribute("result");
					}
					try {
						// 激活领域模型
						Domain domain = null;
						FcsPmDomainHolder.get().addAttribute("result", result);
						if (beforeProcessInvokeService != null) {
							domain = beforeProcessInvokeService.before();
							logger.info("beforeProcessInvokeService.before():" + domain);
						}
						
						if (domain != null) {
							FcsPmDomainHolder.get().setDomain(domain);
						}
						if (processInvokeService != null) {
							processInvokeService.process(domain);
							logger.info("processInvokeService.process():" + domain);
						}
						
						if (result.getFcsResultEnum() == FcsResultEnum.UN_KNOWN_EXCEPTION) {
							result.setSuccess(true);
						}
						
						if (order instanceof ProcessOrder) {
							addOperationJournalInfo((ProcessOrder) order, processBizName,
								processBizName, order.toString());
						}
					} catch (FcsPmBizException eex) {
						setYrdException(status, result, eex, eex.getErrorMsg());
						
					} catch (FcsPmDomainException e) {
						setFcsPmDomainException(status, result, e, e.getErrorMsg());
					} catch (Exception e) {
						setDbException(status, result, e);
					} catch (Throwable e) {
						setDbException(status, result, e);
					}
					
					return result;
				}
			});
		} catch (FcsPmBizException eex) {
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
					successProcessInvokeService.after(FcsPmDomainHolder.get().getDomain());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
		}
		if (isClear) {
			FcsPmDomainHolder.clear();
		}
		logger.info("-处理结束{}  commonProcess processBizName={} result={} ", this.getClass()
			.getName(), processBizName, result);
		
		return result;
	}
	
	protected FcsBaseResult commonProcess(	final Order order,
											final String processBizName,
											final BeforeProcessInvokeService beforeProcessInvokeService,
											final ProcessInvokeService processInvokeService,
											final AfterProcessInvokeService successProcessInvokeService) {
		return commonProcess(order, processBizName, null, beforeProcessInvokeService,
			processInvokeService, successProcessInvokeService);
	}
	
	protected FcsBaseResult checkProcess(final Order order, final String processBizName,
											final ProcessInvokeService processInvokeService) {
		logger.info("-进入{} " + this.getClass().getName()
					+ "  commonProcess processBizName={} order={} ", processBizName, order);
		FcsBaseResult result = null;
		final Date nowDate = getSysdate();
		FcsPmDomainHolder.set(new FcsPmDomainContext<Order>(nowDate, order, null));
		try {
			checkOrder(order);
			result = createResult();
			processInvokeService.process(null);
			result.setSuccess(true);
			return result;
		} catch (FcsPmBizException eex) {
			logger.error(eex.getLocalizedMessage(), eex);
			result.setSuccess(false);
			result.setFcsResultEnum(eex.getResultCode());
			result.setMessage(processBizName + "异常[" + eex.getErrorMsg() + "]");
		} catch (Exception ex) {
			setUnknownException(result, ex);
		} catch (Throwable e) {
			setUnknownException(result, e);
		}
		FcsPmDomainHolder.clear();
		logger.info("-进入{} " + this.getClass().getName()
					+ "  commonProcess processBizName={} result={} ", processBizName, result);
		return result;
	}
	
	protected void addOperationJournalInfo(ProcessOrder opOrder, String permissionName,
											String operationContent, String memo) {
		try {
			OperationJournalAddOrder order = new OperationJournalAddOrder();
			order.setMemo(memo);
			
			if (opOrder == null) {
				order.setOperatorId(-1);
				order.setOperatorName("系统自动");
				order.setOperatorIp("127.0.0.1");
			} else {
				order.setOperatorId(opOrder.getUserId() == null ? 0 : opOrder.getUserId());
				String userName = opOrder.getUserName();
				if (StringUtil.isNotBlank(opOrder.getUserAccount())) {
					userName = userName + "[" + opOrder.getUserAccount() + "]";
				}
				order.setOperatorName(userName);
				order.setOperatorIp(opOrder.getUserIp());
			}
			order.setBaseModuleName("项目管理");
			order.setPermissionName(permissionName);
			order.setOperationContent(operationContent);
			
			// operationJournalService.addOperationJournalInfo(order);
		} catch (Exception e) {
			logger.error("添加操作日志失败,失败原因：{}", e.getMessage(), e);
		}
	}
	
	protected FcsBaseResult simpleSaveTemplate(String processBizName, String paramNames,
												ProcessInvokeService processInvokeService) {
		FcsBaseResult baseResult = createResult();
		try {
			processInvokeService.process(null);
			baseResult.setSuccess(true);
			addOperationJournalInfo(null, processBizName, processBizName, paramNames);
		} catch (FcsPmBizException eex) {
			setYrdException(null, baseResult, eex, eex.getErrorMsg());
			
		} catch (Exception e) {
			setUnknownException(baseResult, e);
		} catch (Throwable e) {
			setUnknownException(baseResult, e);
		}
		return baseResult;
	}
	
	/**
	 * 默认返回结果类型
	 * 
	 * @return
	 */
	protected FcsBaseResult createResult() {
		return new FcsBaseResult();
	}
	
	/**
	 * 获取授信结束日期
	 * 
	 * @param startDate 授信开始日期
	 * @param timeUnit 单位
	 * @param timeLimit 时长
	 * @return
	 */
	protected Date calculateExpireDate(Date startDate, TimeUnitEnum timeUnit, int timeLimit) {
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		if (timeUnit == TimeUnitEnum.YEAR) {
			c.add(Calendar.YEAR, timeLimit);
		} else if (timeUnit == TimeUnitEnum.MONTH) {
			c.add(Calendar.MONTH, timeLimit);
		} else if (timeUnit == TimeUnitEnum.DAY) {
			c.add(Calendar.DAY_OF_MONTH, timeLimit);
		}
		return c.getTime();
	}
	
	/**
	 * 获取web访问地址
	 * 
	 * @return
	 */
	protected String getFaceWebUrl() {
		String faceUrl = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL.code());
		if (faceUrl != null && faceUrl.endsWith("/")) {
			faceUrl = faceUrl.substring(0, faceUrl.length() - 1);
		}
		return faceUrl;
	}
	
}
