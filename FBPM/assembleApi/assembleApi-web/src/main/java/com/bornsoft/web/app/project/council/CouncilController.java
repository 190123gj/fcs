package com.bornsoft.web.app.project.council;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.OneVoteResultEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.VoteResultEnum;
import com.born.fcs.pm.ws.info.council.CouncilApplyInfo;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteResultInfo;
import com.born.fcs.pm.ws.info.user.UserExtraMessageInfo;
import com.born.fcs.pm.ws.order.council.CouncilApplyQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteOrder;
import com.born.fcs.pm.ws.order.council.CouncilQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilVoteProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.user.UserExtraMessageResult;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.utils.tool.ReflectUtils;
import com.bornsoft.web.app.base.BaseController;
import com.bornsoft.web.app.util.ViewShowUtil;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("projectMg/meetingMg")
public class CouncilController extends BaseController {
	
	final static String vm_path = "/projectMg/assistSys/meetingMg/";
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "startTime" };
	}
	
	public static enum CouncilStatus {
		WAIT("wait"),
		HAS("has");
		private final String code;
		
		private CouncilStatus(String code) {
			this.code = code;
		}
	}
	
	/**
	 * 会议列表
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("councilCount.json")
	public JSONObject councilCount(boolean justTotal) {
		logger.info("统计会议列表..");
		JSONObject result = new JSONObject();
		try {
			if (justTotal) {
				//待参加+已参加
				int total = councilCount(new CouncilQueryOrder(), CouncilStatus.WAIT.code)
					.getInteger(TOTAL);
				//总数
				result.put(TOTAL, total);
			} else {
				//已参加
				int done = councilList(new CouncilQueryOrder(), CouncilStatus.HAS.code)
					.getJSONObject("page").getInteger("totalCount");
				result.put("done", done);
				//待参加
				int wait = councilList(new CouncilQueryOrder(), CouncilStatus.WAIT.code)
					.getJSONObject("page").getInteger("totalCount");
				result.put("wait", wait);
				//总数
				result.put(TOTAL, done + wait);
			}
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("统计会议列表失败: ", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "查询");
		}
		logger.info("统计会议列表，出参={}", result);
		return result;
	}
	
	/**
	 * 会议统计
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryCouncilCount.json")
	public JSONObject councilCount(CouncilQueryOrder order, String councilStatus) {
		logger.info("查询会议条数,入参={}", order);
		JSONObject result = null;
		try {
			List<String> councilTypeCodes = new ArrayList<>();
			List<String> companyNames = new ArrayList<String>();
			if (isRiskSecretary()) {
				councilTypeCodes.add(CouncilTypeEnum.PROJECT_REVIEW.code());
				councilTypeCodes.add(CouncilTypeEnum.RISK_HANDLE.code());
				companyNames.add(CompanyNameEnum.NORMAL.code());
			}
			if (isManagerSecretary()) {
				councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
				companyNames.add(CompanyNameEnum.NORMAL.code());
			}
			if (isManagerSecretaryXH()) {
				councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
				companyNames.add(CompanyNameEnum.XINHUI.code());
			}
			order.setCompanyNames(companyNames);
			// 非风险经理/非总经理秘书 只能看与自己相关的项目
			if (!isRiskSecretary() && !isManagerSecretary() && !isManagerSecretaryXH()) {
				SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
				if (sessionLocal != null) {
					order.setRelatveId(sessionLocal.getUserId());
				}
			}
			List<String> statusList = null;
			//简单处理，已结束会议=已参会议
			if (CouncilStatus.HAS.code.equals(councilStatus)) {
				statusList = Arrays.asList(new String[] { CouncilStatusEnum.BREAK_UP.code() });
			} else {
				statusList = Arrays.asList(new String[] { CouncilStatusEnum.IN_PROGRESS.code(),
															CouncilStatusEnum.NOT_BEGIN.code() });
			}
			order.setStatuss(statusList);
			order.setCouncilTypeCodes(councilTypeCodes);
			Long count = councilServiceClient.queryCouncilCount(order);
			if (count == null) {
				result = toJSONResult(AppResultCodeEnum.FAILED, "获取待参会议条数失败");
			} else {
				result = toJSONResult(AppResultCodeEnum.SUCCESS, "");
				result.put(TOTAL, count);
			}
		} catch (Exception e) {
			logger.error("获取待参会议条数失败:", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "获取待参会议条数失败");
		}
		logger.info("获取待参会议条数，出参={}", result);
		return result;
	}
	
	/**
	 * 会议列表
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("councilList.json")
	public JSONObject councilList(CouncilQueryOrder order, String councilStatus) {
		logger.info("查询会议列表,入参={}", order);
		JSONObject result = null;
		try {
			List<String> councilTypeCodes = new ArrayList<>();
			List<String> companyNames = new ArrayList<String>();
			if (isRiskSecretary()) {
				councilTypeCodes.add(CouncilTypeEnum.PROJECT_REVIEW.code());
				councilTypeCodes.add(CouncilTypeEnum.RISK_HANDLE.code());
				companyNames.add(CompanyNameEnum.NORMAL.code());
			}
			if (isManagerSecretary()) {
				councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
				companyNames.add(CompanyNameEnum.NORMAL.code());
			}
			if (isManagerSecretaryXH()) {
				councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
				companyNames.add(CompanyNameEnum.XINHUI.code());
			}
			order.setCompanyNames(companyNames);
			// 非风险经理/非总经理秘书 只能看与自己相关的项目
			if (!isRiskSecretary() && !isManagerSecretary() && !isManagerSecretaryXH()) {
				SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
				if (sessionLocal != null) {
					order.setRelatveId(sessionLocal.getUserId());
				}
			}
			List<String> statusList = null;
			//简单处理，已结束会议=已参会议
			if (CouncilStatus.HAS.code.equals(councilStatus)) {
				statusList = Arrays.asList(new String[] { CouncilStatusEnum.BREAK_UP.code() });
			} else {
				statusList = Arrays.asList(new String[] { CouncilStatusEnum.IN_PROGRESS.code(),
															CouncilStatusEnum.NOT_BEGIN.code() });
			}
			order.setStatuss(statusList);
			order.setCouncilTypeCodes(councilTypeCodes);
			QueryBaseBatchResult<CouncilInfo> batchResult = councilServiceClient
				.queryCouncil(order);
			JSONObject page = makePage(batchResult);
			JSONArray dataList = new JSONArray();
			page.put("result", dataList);
			for (CouncilInfo c : batchResult.getPageList()) {
				JSONObject entity = new JSONObject();
				dataList.add(entity);
				entity.put("councilId", c.getCouncilId());
				entity.put("councilCode", c.getCouncilCode());
				entity.put("councilTypeCode", c.getCouncilTypeCode().message());
				entity.put("startTime", DateUtils.toString(c.getStartTime(), DateUtils.PATTERN2));
				entity.put("projectsName", c.getProjectsName());
				
				entity.put("councilPlace", c.getCouncilPlace());
				entity.put("status", c.getStatus().message());
				entity.put("judgesName", c.getJudgesName());
				
				entity.put("judgeCount", c.getJudges().size());
				boolean canVote = false;
				if (c.getStatus() == CouncilStatusEnum.NOT_BEGIN) {
					if (c.getCouncilTypeCode().code().equals("PROJECT_REVIEW")
						|| c.getCouncilTypeCode().code().equals("RISK_HANDLE")) {
						if (ViewShowUtil.findJusgeIn(c.getJudges(), ShiroSessionUtils
							.getSessionLocal().getUserId())
							|| isRiskSecretary()) {
							canVote = true;
						}
					}
				} else if (c.getStatus() == CouncilStatusEnum.IN_PROGRESS) {
					if (c.getCouncilTypeCode().code().equals("PROJECT_REVIEW")
						|| c.getCouncilTypeCode().code().equals("RISK_HANDLE")) {
						if (ViewShowUtil.findJusgeIn(c.getJudges(), ShiroSessionUtils
							.getSessionLocal().getUserId())
							|| isRiskSecretary()) {
							if (!ViewShowUtil.checkVoteAllDown(c.getProjects())) {
								canVote = true;
							}
						}
					}
				}
				entity.put("canVote", canVote);
			}
			result = toJSONResult(batchResult);
			result.put("page", page);
			
		} catch (Exception e) {
			logger.error("查询会议列表失败: ", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "查询");
		}
		logger.info("查询会议列表，出参={}", result);
		return result;
	}
	
	private static GsonBuilder gson = null;
	static {
		gson = GsonUtil.createNewBuilder(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes fa) {
				String name = fa.getName();
				return name.equals("listData") || name.equals("summary") || name.equals("projects")
						|| name.equals("council") || name.equals("councilTypeInfo")
						|| name.equals("summaryForm") || name.equals("participants")
						|| name.equals("projects") || name.equals("councilTypeInfo");
			}
			
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		});
		gson.registerTypeAdapter(ProjectVoteResultEnum.class,
			new TypeAdapter<ProjectVoteResultEnum>() {
				
				@Override
				public ProjectVoteResultEnum read(JsonReader reader) throws IOException {
					if (reader.peek() == JsonToken.NULL) {
						reader.nextNull();
						return null;
					} else {
						return ProjectVoteResultEnum.getByCode(reader.nextString());
					}
				}
				
				@Override
				public void write(JsonWriter writer, ProjectVoteResultEnum value)
																					throws IOException {
					if (value == null) {
						writer.nullValue();
						return;
					} else {
						writer.value(value.message());
					}
				}
			});
		gson.registerTypeAdapter(TimeUnitEnum.class, new TypeAdapter<TimeUnitEnum>() {
			
			@Override
			public TimeUnitEnum read(JsonReader reader) throws IOException {
				if (reader.peek() == JsonToken.NULL) {
					reader.nextNull();
					return null;
				} else {
					return TimeUnitEnum.getByCode(reader.nextString());
				}
			}
			
			@Override
			public void write(JsonWriter writer, TimeUnitEnum value) throws IOException {
				if (value == null) {
					writer.nullValue();
					return;
				} else {
					writer.value(value.message());
				}
			}
		});
	}
	
	public JSONObject parseToJson(Object obj) {
		String jsonStr = gson.create().toJson(obj);
		return JSONObject.parseObject(jsonStr);
	}
	
	/**
	 * 待上会项目列表
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("awaitCouncilProjectList.json")
	public JSONObject awaitCouncilProjectList(CouncilApplyQueryOrder order) {
		logger.info("查询待上会列表，入参={}", ReflectUtils.toString(order));
		JSONObject result = new JSONObject();
		try {
			List<String> companyNames = new ArrayList<String>();
			
			order.setStatus(CouncilApplyStatusEnum.WAIT);
			// 总经理秘书只能看总经理办公会，风控委秘书不能看总经理办公会
			List<String> councilTypeCodes = new ArrayList<String>();
			if (isRiskSecretary()) {
				councilTypeCodes.add(CouncilTypeEnum.PROJECT_REVIEW.code());
				councilTypeCodes.add(CouncilTypeEnum.RISK_HANDLE.code());
				companyNames.add(CompanyNameEnum.NORMAL.code());
			}
			if (isManagerSecretary()) {
				councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
				companyNames.add(CompanyNameEnum.NORMAL.code());
			}
			if (isManagerSecretaryXH()) {
				councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
				companyNames.add(CompanyNameEnum.XINHUI.code());
			}
			order.setCompanyNames(companyNames);
			order.setCouncilTypeCodes(councilTypeCodes);
			QueryBaseBatchResult<CouncilApplyInfo> batchResult = councilApplyServiceClient
				.queryCouncilApply(order);
			JSONObject page = makePage(result, batchResult);
			JSONArray dataList = new JSONArray();
			page.put("result", dataList);
			for (CouncilApplyInfo c : batchResult.getPageList()) {
				JSONObject entity = new JSONObject();
				dataList.add(entity);
				entity.put("applyId", c.getApplyId());
				entity.put("applyTime", DateUtils.toString(c.getApplyTime(), DateUtils.PATTERN2));
				entity.put("projectCode", c.getProjectCode());
				entity.put("projectName", c.getProjectName());
				entity.put("applyManName", c.getApplyManName());
				entity.put("applyDeptName", c.getApplyDeptName());
				entity.put("councilTypeDesc", c.getCouncilTypeDesc());
			}
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("查询待上会列表失败： ", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "查询待上会列表失败");
		}
		return result;
	}
	
	/**
	 * 所有上会的项目列表(各项目投票结果)
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("allCouncilProjectList.json")
	public JSONObject allCouncilProjectList(CouncilVoteProjectQueryOrder order) {
		
		logger.info("查询待投票列表，入参={}", ReflectUtils.toString(order));
		JSONObject result = null;
		try {
			String url = sysParameterServiceClient.getSysParameterValue(ApiSystemParamEnum.FACE_URL
				.code());
			if (StringUtil.isBlank(url)) {
				throw new BornApiException("未配置face访问地址");
			}
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (!isRiskSecretary()) {
				order.setJudgeId(sessionLocal.getUserId());
			}
			CouncilInfo councilInfo = councilServiceClient.queryCouncilById(order.getCouncilId());
			QueryBaseBatchResult<CouncilProjectVoteResultInfo> councilProjects = councilProjectServiceClient
				.queryProjectVoteResult(order);
			JSONObject page = makePage(councilProjects);
			JSONArray pageList = new JSONArray();
			page.put("result", pageList);
			String token = getAccessToken();
			for (CouncilProjectVoteResultInfo info : councilProjects.getPageList()) {
				JSONObject entity = new JSONObject();
				pageList.add(entity);
				entity.put("id", info.getId());
				entity.put("councilId", info.getCouncilId());
				entity.put("projectCode", info.getProjectCode());
				entity.put("projectName", info.getProjectName());
				entity.put("loanType",
					ProjectUtil.isFinancial(info.getProjectCode()) ? "-" : info.getBusiTypeName());
				entity.put("amount", info.getAmount().toStandardString());
				entity.put("chargeRate", StringUtils.defaultIfBlank(info.getChargeRate(), "0"));
				entity.put("timeUnit", info.getTimeUnit() != null ? info.getTimeUnit().message()
					: "");
				entity.put("timeLimit", info.getTimeLimit());
				entity.put("customerName", StringUtils.defaultIfBlank(info.getCustomerName(), "-"));
				
				Long compereId = councilInfo.getCompereId();
				if (compereId != null && compereId == sessionLocal.getUserId()) {
					entity.put("isHost", true);
				} else {
					entity.put("isHost", false);
				}
				
				boolean canVote = false;
				if (info.getVoteResult() == VoteResultEnum.UNKNOWN) {
					if (info.getProjectVoteResult() == null
						|| info.getProjectVoteResult() == ProjectVoteResultEnum.NOT_BEGIN
						|| ProjectVoteResultEnum.IN_VOTE == info.getProjectVoteResult()) {
						if (info.getRiskSecretaryQuit() != BooleanEnum.YES) {
							canVote = true;
						}
					}
				}
				entity.put("canVote", canVote);//FIXME
				//
				ProjectVoteResultEnum voteResult = info.getProjectVoteResult();
				if (councilInfo.getStatus() == CouncilStatusEnum.NOT_BEGIN) {
					entity.put("projectVoteResult", "投票中");
					
				} else if (info.getOneVoteDown() == OneVoteResultEnum.NO_PASS
							|| info.getOneVoteDown() == OneVoteResultEnum.RE_COUNCIL) {
					entity.put("projectVoteResult", info.getOneVoteDown().message());
					
				} else if (info.getOneVoteDown() == OneVoteResultEnum.RE_COUNCIL) {
					entity.put("projectVoteResult", "复议");
				} else if (info.getRiskSecretaryQuit() == BooleanEnum.YES) {
					entity.put("projectVoteResult", "本次不议");
				} else {
					entity.put("projectVoteResult", voteResult != null ? voteResult.message() : "");
				}
				if (voteResult != ProjectVoteResultEnum.NOT_BEGIN
					&& voteResult != ProjectVoteResultEnum.IN_VOTE) {
					entity.put("voteRatio",
						"其中" + info.getPassCount() + "人同意," + (info.getNotpassCount()) + "人不同意,"
								+ info.getQuitCount() + "人本次不议");
				}
				//项目详情
				String tmpUrl = url + "/projectMg/viewProjectAllMessage.htm?projectCode="
								+ info.getProjectCode() + "&accessToken=" + token;
				entity.put("url", tmpUrl);
			}
			page.put("result", pageList);
			
			result = toJSONResult(councilProjects);
			result.put("isRiskSecretary", isRiskSecretary());
			result.put("page", page);
		} catch (Exception e) {
			logger.error("查询待投票列表失败： ", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "查询待投票");
		}
		logger.info("查询待投票列表，出参={}", result);
		return result;
	}
	
	/**
	 * 投票
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("doVote.json")
	@ResponseBody
	public JSONObject doVote(CouncilProjectVoteOrder order, String key, Model model) {
		JSONObject result = new JSONObject();
		try {
			// 校验key
			String checkJudgeKey = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_JUDGE_KEY_CHECK_SIGN.code());
			// 默认不校验
			if (BooleanEnum.YES.code().equals(checkJudgeKey)) {
				if (StringUtil.isBlank(key)) {
					FcsBaseResult fcsBaseResult = new FcsBaseResult();
					fcsBaseResult.setMessage("请先插入KEY或选用支持证书的浏览器！");
					result = toJSONResult(fcsBaseResult);
					return result;
				}
				boolean checked = findAndCheckKey(key);
				if (!checked) {
					FcsBaseResult fcsBaseResult = new FcsBaseResult();
					fcsBaseResult.setMessage("此证书key与用户数据不匹配！");
					result = toJSONResult(fcsBaseResult);
					return result;
				}
			}
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null) {
				order.setUserId(sessionLocal.getUserId());
				order.setUserAccount(sessionLocal.getUserName());
				order.setUserName(sessionLocal.getRealName());
				order.setUserIp(sessionLocal.getRemoteAddr());
			}
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null && userInfo.getPrimaryOrg() != null) {
				// 添加用户主部门
				order.setOrgName(sessionLocal.getUserInfo().getPrimaryOrg().getOrgName());
			}
			/*
			 * order.setId(0); test order.setJudgeId(1);
			 */
			// 判定是否风控委秘书
			order.setRiskSecretary(isRiskSecretary());
			order.setJudgeId(order.getUserId());/* 当前投票人 */
			//  风控委秘书的本次不议 更定会议且投票为本次不议
			FcsBaseResult saveResult = councilProjectVoteServiceClient
				.updateCouncilProjectVote(order);
			
			result = toJSONResult(saveResult);
		} catch (Exception e) {
			logger.error("投票失败：", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "投票失败:" + e.getMessage());
		}
		
		return result;
		
	}
	
	/**
	 * 校验key是否和评委key相同
	 * @return
	 */
	private boolean findAndCheckKey(String key) {
		//  查询数据库参数-是否校验评委key
		String checkJudgeKey = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_JUDGE_KEY_CHECK_SIGN.code());
		// 用户测试查看key 
		logger.info("当前key盾的key值为：" + key);
		// 默认不校验
		if (BooleanEnum.YES.code().equals(checkJudgeKey)) {
			// 若传入校验码为空，直接false
			if (StringUtil.isBlank(key)) {
				logger.info("未传入校验码！");
				return false;
			}
			//  查询用户key
			String userKey = "";
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null) {
				Long userId = sessionLocal.getUserId();
				UserExtraMessageResult result = userExtraMessageServiceClient.findByUserId(userId);
				if (result.isExecuted() && result.isSuccess()) {
					UserExtraMessageInfo info = result.getUserExtraMessageInfo();
					if (info != null && info.getId() > 0) {
						userKey = info.getUserJudgeKey();
					}
				}
			}
			if (StringUtil.isBlank(userKey)) {
				logger.info("未查询到评委key值！");
				return false;
			}
			if (StringUtil.equals(userKey, key)) {
				logger.info(sessionLocal.getUserName() + "key校验通过！");
				return true;
			} else {
				logger.info("key校验码错误！");
				return false;
			}
		} else {
			return true;
		}
		
	}
}
