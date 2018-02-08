package com.born.fcs.crm.biz.service.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONArray;
import com.born.fcs.crm.biz.exception.ExceptionFactory;
import com.born.fcs.crm.biz.exception.FcsCrmBizException;
import com.born.fcs.crm.domain.context.FcsCrmDomainContext;
import com.born.fcs.crm.domain.context.FcsCrmDomainHolder;
import com.born.fcs.crm.domain.exception.FcsCrmDomainException;
import com.born.fcs.pm.biz.service.common.OperationJournalService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormExecuteInfo;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.born.fcs.pm.ws.order.common.OperationJournalAddOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.service.base.CheckBeforeProcessService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.util.ListUtil;
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
	protected OperationJournalService operationJournalServiceClient;
	
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
									FcsCrmBizException eex, String errorMessage) {
		if (status != null)
			status.setRollbackOnly();
		result.setSuccess(false);
		result.setFcsResultEnum(eex.getResultCode());
		result.setMessage(errorMessage);
		logger.error(eex.getLocalizedMessage() + " ==errMesaage=" + eex.getErrorMsg()
						+ " == result  =" + result);
	}
	
	protected void setFcsPmDomainException(TransactionStatus status, FcsBaseResult result,
											FcsCrmDomainException eex, String errorMessage) {
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
		if (FcsCrmDomainHolder.get() == null) {
			FcsCrmDomainHolder.set(new FcsCrmDomainContext<Order>(nowDate, order, null));
			isClear = true;
		}
		
		FcsBaseResult result = null;
		if (FcsCrmDomainHolder.get().getAttribute("result") == null) {
			result = createResult();
			FcsCrmDomainHolder.get().addAttribute("result", result);
		} else {
			result = (FcsBaseResult) FcsCrmDomainHolder.get().getAttribute("result");
		}
		
		try {
			
			checkOrder(order);
			if (checkBeforeProcessService != null)
				checkBeforeProcessService.check();
			result = transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
				
				@Override
				public FcsBaseResult doInTransaction(TransactionStatus status) {
					FcsBaseResult result = null;
					if (FcsCrmDomainHolder.get().getAttribute("result") == null) {
						result = createResult();
					} else {
						result = (FcsBaseResult) FcsCrmDomainHolder.get().getAttribute("result");
					}
					try {
						// 激活领域模型
						Domain domain = null;
						FcsCrmDomainHolder.get().addAttribute("result", result);
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
						
						if (order instanceof ProcessOrder) {
							addOperationJournalInfo((ProcessOrder) order, processBizName,
								processBizName, order.toString());
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
		FcsCrmDomainHolder.set(new FcsCrmDomainContext<Order>(nowDate, order, null));
		try {
			checkOrder(order);
			result = createResult();
			processInvokeService.process(null);
			result.setSuccess(true);
			return result;
		} catch (FcsCrmBizException eex) {
			logger.error(eex.getLocalizedMessage(), eex);
			result.setSuccess(false);
			result.setFcsResultEnum(eex.getResultCode());
			result.setMessage(processBizName + "异常[" + eex.getErrorMsg() + "]");
		} catch (Exception ex) {
			setUnknownException(result, ex);
		} catch (Throwable e) {
			setUnknownException(result, e);
		}
		FcsCrmDomainHolder.clear();
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
			order.setBaseModuleName("资金管理");
			order.setPermissionName(permissionName);
			order.setOperationContent(operationContent);
			
			operationJournalServiceClient.addOperationJournalInfo(order);
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
		} catch (FcsCrmBizException eex) {
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
	 * 获取web访问地址
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
	
	/**
	 * 解析表单执行信息
	 * @param taskUserData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FormExecuteInfo> parseTaskUserData(String taskUserData) {
		List<FormExecuteInfo> formExeInfo = null;
		if (StringUtil.isNotBlank(taskUserData)) {
			try {
				JSONArray taskUserDataArray = (JSONArray) JSONArray.parse(taskUserData);
				formExeInfo = Lists.newArrayList();
				for (Object object : taskUserDataArray) {
					Map<String, Object> dataMap = (Map<String, Object>) object;
					FormExecuteInfo exeInfo = new FormExecuteInfo();
					MiscUtil.setInfoPropertyByMap(dataMap, exeInfo);
					List<Map<String, Object>> candidateUserMap = (List<Map<String, Object>>) dataMap
						.get("candidateUserList");
					List<SimpleUserInfo> candidateUserList = Lists.newArrayList();
					if (candidateUserMap != null) {
						for (Map<String, Object> candidateUser : candidateUserMap) {
							SimpleUserInfo user = new SimpleUserInfo();
							MiscUtil.setInfoPropertyByMap(candidateUser, user);
							candidateUserList.add(user);
						}
					}
					exeInfo.setCandidateUserList(candidateUserList);
					formExeInfo.add(exeInfo);
				}
			} catch (Exception e) {
			}
		}
		return formExeInfo;
	}
	
	/**
	 * 获取任务处理URL
	 * @param taskUserData
	 * @param userId
	 * @return
	 */
	public String getProcessUrl(String taskUserData, long userId) {
		return getProcessUrl(parseTaskUserData(taskUserData), userId, false);
	}
	
	/**
	 * 获取任务处理跳转URL
	 * @param taskUserData
	 * @param userId
	 * @return
	 */
	public String getRedirectProcessUrl(String taskUserData, long userId) {
		return getProcessUrl(parseTaskUserData(taskUserData), userId, true);
	}
	
	/**
	 * 获取任务处理URL
	 * @param executeInfo
	 * @param userId
	 * @return
	 */
	public String getProcessUrl(List<FormExecuteInfo> executeInfo, long userId) {
		return getProcessUrl(executeInfo, userId, false);
	}
	
	/**
	 * 获取任务处理跳转的URL
	 * @param executeInfo
	 * @param userId
	 * @return
	 */
	public String getRedirectProcessUrl(List<FormExecuteInfo> executeInfo, long userId) {
		return getProcessUrl(executeInfo, userId, true);
	}
	
	/**
	 * 获取任务处理URL
	 * @param executeInfo
	 * @param userId
	 * @param isRedirect 是否跳转url
	 * @return
	 */
	public String getProcessUrl(List<FormExecuteInfo> executeInfo, long userId, boolean isRedirect) {
		String processUrl = "";
		if (userId > 0 && ListUtil.isNotEmpty(executeInfo)) {
			for (FormExecuteInfo exeInfo : executeInfo) {
				if (!exeInfo.isExecute()) {//还未执行
					if (exeInfo.getUserId() > 0 && userId == exeInfo.getUserId()) { //当前执行人直接返回url
						processUrl = isRedirect ? exeInfo.getRedirectTaskUrl() : exeInfo
							.getTaskUrl();
						break;
					} else if (exeInfo.getUserId() == 0
								&& ListUtil.isNotEmpty(exeInfo.getCandidateUserList())) {//没有执行人（查看候选人）
						for (SimpleUserInfo user : exeInfo.getCandidateUserList()) {
							if (user.getUserId() == userId) {
								processUrl = isRedirect ? exeInfo.getRedirectTaskUrl() : exeInfo
									.getTaskUrl();
								break;
							}
						}
						if (StringUtil.isNotBlank(processUrl)) {
							break;
						}
					}
				}
			}
		}
		return processUrl;
	}
	
}
