package com.born.fcs.pm.biz.service.base;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.exception.FcsPmBizException;
import com.born.fcs.pm.biz.service.common.DateSeqService;
import com.born.fcs.pm.biz.service.common.OperationJournalService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.dal.dataobject.ChargeRepayPlanDetailDO;
import com.born.fcs.pm.dal.dataobject.CouncilDO;
import com.born.fcs.pm.dal.dataobject.CouncilJudgeDO;
import com.born.fcs.pm.dal.dataobject.CouncilProjectDO;
import com.born.fcs.pm.dal.dataobject.CouncilProjectVoteDO;
import com.born.fcs.pm.dal.dataobject.CouncilTypeDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationFinancialReviewDO;
import com.born.fcs.pm.dal.dataobject.FProjectContractExtraValueDO;
import com.born.fcs.pm.dal.dataobject.FProjectContractExtraValueModifyDO;
import com.born.fcs.pm.domain.context.FcsPmDomainContext;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.domain.exception.FcsPmDomainException;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.PlanTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.VoteResultEnum;
import com.born.fcs.pm.ws.enums.VoteRuleTypeEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.contract.ProjectContractExtraValueInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanDetailInfo;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.born.fcs.pm.ws.order.common.OperationJournalAddOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractExtraValueBatchOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.base.CheckBeforeProcessService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
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
	protected OperationJournalService operationJournalService;
	
	@Autowired
	protected DateSeqService dateSeqService;
	
	@Autowired
	protected SysParameterService sysParameterService;
	
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
			
			if (opOrder == null || opOrder.getUserId() == null || opOrder.getUserId() <= 0) {
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
			
			operationJournalService.addOperationJournalInfo(order);
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
	 * 设置项目最终投票结果
	 * 
	 * @param councilProjectDO
	 * @return 返回错误message
	 * 原本是框架拦截报错，测试要求不报错所以不能throw异常，返回str判断。str=null即为success
	 */
	protected String setProjectVoteResult(CouncilProjectDO councilProjectDO) {
		
		// 添加判断，若风控委秘书执行了本次不议，不做结果计算
		if (StringUtil.equals(BooleanEnum.YES.code(), councilProjectDO.getRiskSecretaryQuit())) {
			return null;
		}
		// 20160926添加判断 主持人本次不议后[投票结果为本次不议]，无需执行投票结果计算
		if (StringUtil.equals(ProjectVoteResultEnum.END_QUIT.code(),
			councilProjectDO.getProjectVoteResult())) {
			return null;
		}
		
		int passCnt = councilProjectDO.getPassCount();
		int noPassCnt = councilProjectDO.getNotpassCount();
		int quitCnt = councilProjectDO.getQuitCount();
		int total = councilProjectDO.getJudgesCount();
		int voteCount = passCnt + noPassCnt + quitCnt;
		
		if (total == passCnt/** + quitCnt **/
		) {
			councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.END_PASS.code());
		}
		if (total == noPassCnt /** + quitCnt **/
		) {
			councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.END_NOPASS.code());
		}
		
		CouncilDO councilDO = councilDAO.findById(councilProjectDO.getCouncilId());
		if (councilDO == null) {
			return "未找到会议记录";
			//throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到会议记录");
		}
		// 新增判断 有评委未投票不能结束会议
		if (total != voteCount) {
			return "投票未全部完成，不能结束会议!";
		}
		// 理论上应该取会议记录中缓存的投票判定信息
		String ifVote = councilDO.getIfVote();
		String voteRuleType = councilDO.getVoteRuleType();
		int inderterminateNum = councilDO.getIndeterminateNum();
		int passNum = councilDO.getPassNum();
		int lessNum = councilDO.getLessNum();
		Double passRate = councilDO.getPassRate();
		Double indeterminateRate = councilDO.getIndeterminateRate();
		// 兼容会议记录中没有的时候，取会议类型的投票判定信息
		if (StringUtil.isBlank(councilDO.getIfVote())
			&& StringUtil.isBlank(councilDO.getVoteRuleType())) {
			// CouncilInfo councilInfo = councilService.queryCouncilById();
			CouncilTypeDO councilTypeDO = councilTypeDAO.findById(councilDO.getCouncilType());
			ifVote = councilTypeDO.getIfVote();
			voteRuleType = councilTypeDO.getVoteRuleType();
			inderterminateNum = councilTypeDO.getIndeterminateNum();
			passNum = councilTypeDO.getPassNum();
			lessNum = councilTypeDO.getLessNum();
			passRate = councilTypeDO.getPassRate();
			indeterminateRate = councilTypeDO.getIndeterminateRate();
		}
		if (voteCount < lessNum) {
			return "未满足最低投票人数，不能结束会议!";
			//throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL, "未满足最低投票人数，不能结束会议!");
			
		}
		if (BooleanEnum.IS.code().equals(ifVote)) {
			if (VoteRuleTypeEnum.COUNT.code().equals(voteRuleType)) {
				//				if (quitCnt >= inderterminateNum) {
				//					councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.END_QUIT.code());
				//				} else
				if (passCnt >= passNum) {
					councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.END_PASS.code());
				} else
				//if (total == passCnt + noPassCnt + quitCnt)
				{
					// 并非通过和不议且已经投票完毕，定义为未通过
					councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.END_NOPASS.code());
				}
			} else if (VoteRuleTypeEnum.RATE.code().equals(voteRuleType)) {
				// 用已投票的数量来计算投票的比率
				double passRateReal = (double) passCnt / (double) voteCount;
				double quitRateReal = (double) quitCnt / (double) voteCount;
				//				double passRateReal = (double) passCnt / (double) total;
				//				double quitRateReal = (double) quitCnt / (double) total;
				// rate已经很小了，再除以100可能出错，所以用算出来的实际比率乘以100来计算
				// 20160928 去掉了投票结果会本次不议的结果
				//				if (quitRateReal * 100 >= indeterminateRate) {
				//					councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.END_QUIT.code());
				//				} else 
				if (passRateReal * 100 >= passRate) {
					councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.END_PASS.code());
				} else
				//if (total == passCnt + noPassCnt + quitCnt) 
				{
					// 并非通过和不议且已经投票完毕，定义为未通过
					councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.END_NOPASS.code());
				}
				
			}
			
		}
		return null;
		
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
	
	protected void doProjectVote(CouncilProjectVoteOrder order) {
		doProjectVote(order, false);
	}
	
	protected void doProjectVote(CouncilProjectVoteOrder order, boolean councilReset) {
		// 风控委秘书的本次不议操作是将投票置为本次不议
		if (order.isRiskSecretary() && VoteResultEnum.QUIT.code().equals(order.getVoteResult())) {
			CouncilProjectDO councilProjectDO = councilProjectDAO
				.findByCouncilProjectCodeAndCouncilIdforUpdate(order.getProjectCode(),
					order.getCouncilId());
			// 存入本次不议和理由
			councilProjectDO.setRiskSecretaryQuit(BooleanEnum.YES.code());
			councilProjectDO.setRiskSecretaryQuitMark(order.getVoteRemark());
			// 本次不议不再改变会议投票结果
			//councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.END_QUIT.code());
			councilProjectDAO.update(councilProjectDO);
			
			// 风控委秘书和评委执行不同操作，无需进入评委操作判定
			return;
		}
		
		if (order.getId() <= 0) {
			// 通过其他信息确定是投哪一个
			CouncilProjectVoteDO vote = new CouncilProjectVoteDO();
			vote.setCouncilId(order.getCouncilId());
			vote.setProjectCode(order.getProjectCode());
			vote.setJudgeId(order.getJudgeId());
			List<CouncilProjectVoteDO> list = councilProjectVoteDAO.findByCondition(vote, 0, 0);
			if (list.size() > 0) {
				order.setId(list.get(0).getId());
			} else {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到投票记录");
			}
			
		}
		
		// 更新
		CouncilProjectVoteDO counciVotelDO = councilProjectVoteDAO.findById(order.getId());
		if (null == counciVotelDO) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到投票记录");
		}
		if (BooleanEnum.NO.code().equals(counciVotelDO.getVoteStatus()) || councilReset) {
			counciVotelDO.setVoteRemark(order.getVoteRemark());
			counciVotelDO.setVoteResult(order.getVoteResult());
			counciVotelDO.setOrgName(order.getOrgName());
			if (StringUtil.isNotEmpty(order.getVoteResult())) {
				counciVotelDO.setVoteResultDesc(VoteResultEnum.getByCode(order.getVoteResult())
					.message());
				
			}
			
			counciVotelDO.setVoteStatus(BooleanEnum.YES.code());// 设置为已投票
			councilProjectVoteDAO.update(counciVotelDO);
			
			/*
			 * CouncilProjectDO councilProjectDO = councilProjectDAO
			 * .findByCouncilProjectCodeAndCouncilId
			 * (counciVotelDO.getProjectCode(),
			 * counciVotelDO.getCouncilId());
			 */
			CouncilProjectDO councilProjectDO = councilProjectDAO.findByIdForUpdate(counciVotelDO
				.getCouncilProjectId());
			
			switch (VoteResultEnum.getByCode(order.getVoteResult())) {
				case NOTPASS:
					councilProjectDO.setNotpassCount(councilProjectDO.getNotpassCount() + 1);
					break;
				case PASS:
					councilProjectDO.setPassCount(councilProjectDO.getPassCount() + 1);
					break;
				case QUIT:
					councilProjectDO.setQuitCount(councilProjectDO.getQuitCount() + 1);
					break;
				default:
					;
			}
			// 前期不判断，只有风控委秘书点击了【会议结束】（所有人投票或者部分投票但是满足满足最低投票人数）或者所有人已投票完成
			// 才会计算结果
			
			// 维护主表投票主状态，若状态为未投票，先更新为投票中
			if (StringUtil.isBlank(councilProjectDO.getProjectVoteResult())
				|| ProjectVoteResultEnum.NOT_BEGIN.code().equals(
					councilProjectDO.getProjectVoteResult())) {
				councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.IN_VOTE.code());
			}
			// 若所有人已投票结束，执行一次投票结果算法
			int passCnt = councilProjectDO.getPassCount();
			int noPassCnt = councilProjectDO.getNotpassCount();
			int quitCnt = councilProjectDO.getQuitCount();
			int total = councilProjectDO.getJudgesCount();
			if (total == passCnt + noPassCnt + quitCnt) {
				String voteStr = setProjectVoteResult(councilProjectDO);
				// 原本是框架正常拦截，测试要求不报错，修改为str判断
				if (StringUtil.isNotBlank(voteStr)) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL, voteStr);
				}
			}
			
			// 添加判断 若主持人投票本次不议，则计为本次不议
			List<CouncilJudgeDO> judges = councilJudgeDAO.findByCouncilId(order.getCouncilId());
			for (CouncilJudgeDO judge : judges) {
				// 代表本人
				if (judge.getJudgeId() == order.getJudgeId()) {
					// 代表本人是主持人
					if (BooleanEnum.YES.code().equals(judge.getCompere())) {
						// 若本次不议
						if (VoteResultEnum.QUIT == VoteResultEnum.getByCode(order.getVoteResult())) {
							councilProjectDO.setProjectVoteResult(ProjectVoteResultEnum.END_QUIT
								.code());
						}
					} else {
						// 代表非主持人的本次不议，返回错误
						if (VoteResultEnum.QUIT == VoteResultEnum.getByCode(order.getVoteResult())) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAIL,
								"非主持人无法进行本次不议操作！");
						}
					}
				}
			}
			
			councilProjectDAO.update(councilProjectDO);
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "本项目你已经投过票了!");
		}
	}
	
	/***
	 * 合同额外信息表插入
	 * @param order
	 * @param now
	 */
	protected void projectContractExtraMessageSave(ProjectContractExtraValueBatchOrder order,
													Date now, boolean isChecker) {
		// 系统变量 是否更定过数据
		boolean dataChanged = false;
		// 获取数据库原始数据
		List<FProjectContractExtraValueDO> infosDO = fProjectContractExtraValueDAO
			.findByContractItemId(order.getContractItemId());
		// 获取新数据
		List<ProjectContractExtraValueInfo> infos = order.getProjectContractExtraValue();
		if (ListUtil.isEmpty(infosDO)) {
			// 数据库源数据为空代表全插入
			for (ProjectContractExtraValueInfo info : order.getProjectContractExtraValue()) {
				FProjectContractExtraValueDO infoDO = new FProjectContractExtraValueDO();
				MiscUtil.copyPoObject(infoDO, order);
				infoDO.setDocumentName(info.getDocumentName());
				infoDO.setDocumentDescribe(info.getDocumentDescribe());
				infoDO.setDocumentType("input");
				infoDO.setDocumentValue(info.getDocumentValue());
				infoDO.setDocumentModifyNum(0);
				infoDO.setRawAddTime(now);
				fProjectContractExtraValueDAO.insert(infoDO);
				dataChanged = true;
			}
		} else if (ListUtil.isEmpty(infos)) {
			// 新数据为空 ，咦，不做任何操作？
		} else {
			// 插入新数据，修改源数据
			for (ProjectContractExtraValueInfo info : order.getProjectContractExtraValue()) {
				if (info == null) {
					continue;
				}
				if (info.getDocumentValue() == null) {
					info.setDocumentValue("");
				}
				for (FProjectContractExtraValueDO infoDO : infosDO) {
					// 剔除双方都是空白的情况
					if (StringUtil.isBlank(infoDO.getDocumentValue())
						&& StringUtil.isBlank(info.getDocumentValue())) {
						continue;
					}
					if (info.getDocumentName().equals(infoDO.getDocumentName())
						&& !info.getDocumentValue().equals(infoDO.getDocumentValue())) {
						// name 相同，value不同就更新
						String valueOld = infoDO.getDocumentValue();
						String valueNew = info.getDocumentValue();
						infoDO.setDocumentValue(valueNew);
						infoDO.setDocumentModifyNum(info.getDocumentModifyNum() + 1);
						int changed = fProjectContractExtraValueDAO.update(infoDO);
						// 更新完毕之后在更新表插入数据
						if (changed > 0 && isChecker) {
							dataChanged = true;
							FProjectContractExtraValueModifyDO modifyDO = new FProjectContractExtraValueModifyDO();
							MiscUtil.copyPoObject(modifyDO, infoDO);
							modifyDO.setValueId(infoDO.getId());
							modifyDO.setDocumentValueOld(valueOld);
							modifyDO.setDocumentValueNew(valueNew);
							modifyDO.setRawAddTime(now);
							// 添加修改人信息
							modifyDO.setUserId(order.getUserId());
							modifyDO.setUserName(order.getUserName());
							fProjectContractExtraValueModifyDAO.insert(modifyDO);
						}
					}
				}
			}
		}
		if (!isChecker) {
			dataChanged = false;
		}
		FcsPmDomainHolder.get().addAttribute("dataChanged", dataChanged);
		
	}
	
	/**
	 * 获取web访问地址
	 * @return
	 */
	protected String getFaceWebUrl() {
		String faceUrl = sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL.code());
		if (faceUrl != null && faceUrl.endsWith("/")) {
			faceUrl = faceUrl.substring(0, faceUrl.length() - 1);
		}
		return faceUrl;
	}
	
	protected boolean isEqual(Date d1, Date d2) {
		long t1 = (null == d1) ? 0L : d1.getTime();
		long t2 = (null == d2) ? 0L : d2.getTime();
		return t1 == t2;
	}
	
	protected boolean isEqual(String s1, String s2) {
		return StringUtil.equals(s1, s2);
	}
	
	protected boolean isEqual(Long l1, long l2) {
		if (null == l1) {
			return 0L == l2;
		} else {
			return l1.longValue() == l2;
		}
	}
	
	protected boolean isEqual(Double d1, double d2) {
		BigDecimal b1 = new BigDecimal(0d);
		if (null != d1) {
			b1 = new BigDecimal(d1);
		}
		BigDecimal b2 = new BigDecimal(d2);
		return b1.equals(b2);
	}
	
	protected boolean isEqual(Money m1, Money m2) {
		return m1.equals(m2);
	}
	
	protected ChargeRepayPlanDetailInfo convertPlanDetailDO2Info(ChargeRepayPlanDetailDO DO) {
		if (DO == null)
			return null;
		ChargeRepayPlanDetailInfo info = new ChargeRepayPlanDetailInfo();
		BeanCopier.staticCopy(DO, info);
		info.setIsNotify(BooleanEnum.getByCode(DO.getIsNotify()));
		info.setPlanType(PlanTypeEnum.getByCode(DO.getPlanType()));
		return info;
	}
	
	protected FInvestigationFinancialReviewDO findFinancial(long formId) {
		List<FInvestigationFinancialReviewDO> reviews = FInvestigationFinancialReviewDAO
				.findByFormId(formId);
		if (ListUtil.isEmpty(reviews)) {
			return null;
		}

		for (FInvestigationFinancialReviewDO review : reviews) {
			if (BooleanEnum.IS.code().equals(review.getIsActive())) {
				return review;
			}
		}
		return reviews.get(0);
	}
}
