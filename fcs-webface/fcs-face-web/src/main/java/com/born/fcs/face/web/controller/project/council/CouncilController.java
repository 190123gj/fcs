package com.born.fcs.face.web.controller.project.council;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.exception.ExceptionFactory;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.DataCodeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.basicmaintain.SysDataDictionaryInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.council.CouncilApplyInfo;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.info.council.CouncilJudgeInfo;
import com.born.fcs.pm.ws.info.council.CouncilParticipantInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteResultInfo;
import com.born.fcs.pm.ws.info.council.CouncilTypeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.info.user.UserExtraMessageInfo;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.council.CouncilDelOrder;
import com.born.fcs.pm.ws.order.council.CouncilOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteOrder;
import com.born.fcs.pm.ws.order.council.CouncilProjectVoteQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilTypeQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilVoteProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.user.UserExtraMessageResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("projectMg/meetingMg")
public class CouncilController extends BaseController {
	
	final static String vm_path = "/projectMg/assistSys/meetingMg/";
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "startTime" };
	}
	
	/**
	 * 设置会议地点
	 * @param model
	 */
	private List<JSONObject> getCouncilPlace() {
		List<SysDataDictionaryInfo> councilPlaces = sysDataDictionaryServiceClient
			.findByDataCode(DataCodeEnum.COUNCIL_PLACE);
		List<JSONObject> places = Lists.newArrayList();
		if (ListUtil.isNotEmpty(councilPlaces)) {
			for (SysDataDictionaryInfo data : councilPlaces) {
				JSONObject json = new JSONObject();
				json.put("code", data.getDataValue());
				json.put("message", data.getDataValue());
				places.add(json);
			}
		}
		return places;
	}
	
	/**
	 * 会议列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("councilList.htm")
	public String councilList(CouncilQueryOrder order, Model model) {
		QueryBaseBatchResult<CouncilInfo> batchResult = councilListQuery(order, model);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		model.addAttribute("isRiskSecretary", isRiskSecretary());
		return vm_path + "councilList.vm";
	}
	
	@RequestMapping("showCouncil.htm")
	public String showCouncil(Long councilId, Model model) {
		
		//List<CouncilPlaceEnum> places = CouncilPlaceEnum.getAllEnum();
		List<JSONObject> places = getCouncilPlace();
		CouncilInfo councilInfo = councilServiceClient.queryCouncilById(councilId);
		CouncilTypeInfo councilTypeInfo = councilTypeServiceClient.findById(councilInfo
			.getCouncilType());
		
		List<CouncilJudgeInfo> judges = councilTypeServiceClient
			.findCouncilJudgesByCouncilType(councilTypeInfo.getTypeId());
		
		model.addAttribute("councilTypeInfo", councilTypeInfo);
		model.addAttribute("councilInfo", councilInfo);
		model.addAttribute("places", places);
		model.addAttribute("judges", judges);
		return vm_path + "viewCouncil.vm";//
	}
	
	@RequestMapping("modifyCouncil.htm")
	public String modifyCouncil(Long councilId, Model model) {
		//List<CouncilPlaceEnum> places = CouncilPlaceEnum.getAllEnum();
		List<JSONObject> places = getCouncilPlace();
		CouncilInfo councilInfo = councilServiceClient.queryCouncilById(councilId);
		CouncilTypeInfo councilTypeInfo = councilTypeServiceClient.findById(councilInfo
			.getCouncilType());
		
		List<CouncilJudgeInfo> judges = councilTypeServiceClient
			.findCouncilJudgesByCouncilType(councilTypeInfo.getTypeId());
		
		model.addAttribute("councilInfo", councilInfo);
		model.addAttribute("places", places);
		model.addAttribute("edit", true);
		model.addAttribute("councilTypeInfo", councilTypeInfo);
		model.addAttribute("judges", judges);
		return vm_path + "addCouncil.vm";//
	}
	
	/**
	 * 去新增会议
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("toAddCouncil.htm")
	public String toAddCouncil(String applyIds, Long councilTypeId, Model model) {
		
		// 添加判断，有可能是从左方侧边栏过来，
		if (StringUtil.isBlank(applyIds) && (councilTypeId == null || councilTypeId <= 0)) {
			CouncilInfo councilInfo = new CouncilInfo();
			// 添加无属性新增会议的标记
			model.addAttribute("notChooseAdd", BooleanEnum.YES.code());
			
			// // 添加会议民称列表
			CouncilTypeQueryOrder order = new CouncilTypeQueryOrder();
			// 添加会议类型列表
			// 总经理秘书只能看总经理办公会，风控委秘书不能看总经理办公会
			List<CouncilTypeEnum> enums = new ArrayList<CouncilTypeEnum>();
			if (isRiskSecretary()) {
				enums.add(CouncilTypeEnum.PROJECT_REVIEW);
				enums.add(CouncilTypeEnum.RISK_HANDLE);
				model.addAttribute("councilTypes", enums);
				if (order.getTypeCode() == null) {
					order.setTypeCode(CouncilTypeEnum.PROJECT_REVIEW.code());
				}
			}
			if (isManagerSecretary() || isManagerSecretaryXH()) {
				enums.add(CouncilTypeEnum.GM_WORKING);
				model.addAttribute("councilTypes", enums);
				//model.addAttribute("councilTypes", CouncilTypeEnum.getAllEnum());
				if (order.getTypeCode() == null) {
					order.setTypeCode(CouncilTypeEnum.GM_WORKING.code());
				}
			}
			
			List<String> councilTypeCodes = new ArrayList<>();
			List<String> companyNames = new ArrayList<>();
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
			
			order.setPageSize(10000L);
			QueryBaseBatchResult<CouncilTypeInfo> batchResult = councilTypeServiceClient
				.query(order);
			
			model.addAttribute("types", batchResult.getPageList());
			//			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			//				// 获取参会评委
			//				List<CouncilJudgeInfo> judges = councilTypeServiceClient
			//					.findCouncilJudgesByCouncilType(batchResult.getPageList().get(0).getTypeId());
			//				model.addAttribute("judges", judges);
			//				councilInfo.setJudges(judges);
			//			}
			// 获取列席人员
			
			// 添加会议地点
			//List<CouncilPlaceEnum> places = CouncilPlaceEnum.getAllEnum();
			List<JSONObject> places = getCouncilPlace();
			model.addAttribute("places", places);
			// 获取会议编号
			//			String councilCode = councilApplyServiceClient.getAvailableCouncilCodeSeq();
			//			councilInfo.setCouncilCode(councilCode);
			councilInfo.setCouncilTypeCode(CouncilTypeEnum.PROJECT_REVIEW);
			model.addAttribute("councilInfo", councilInfo);
			return vm_path + "addCouncil.vm";//
		}
		String[] all = applyIds.split(",");
		
		List<CouncilApplyInfo> list = Lists.newArrayList();
		List<CouncilParticipantInfo> participants = Lists.newArrayList();
		CouncilApplyInfo firstApplyInfo = councilApplyServiceClient.queryCouncilApplyByApplyId(Long
			.parseLong(all[0]));
		
		if (firstApplyInfo == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM, "未选择申请上会项目");
		}
		
		for (String applyId : all) {
			CouncilApplyInfo councilApplyInfo = councilApplyServiceClient
				.queryCouncilApplyByApplyId(Long.parseLong(applyId));
			
			if (!firstApplyInfo.getCouncilCode().equals(councilApplyInfo.getCouncilCode())) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
					"选择的项目的申请上会类型不一致");
			}
			list.add(councilApplyInfo);
			// 列席人员
			setParticipants(participants, councilApplyInfo);
			
		}
		removeDuplicate(participants);
		
		// Long councilTypeId = firstApplyInfo.getCouncilType();
		if (councilTypeId == null || councilTypeId == 0) { // test
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM, "请选择上会类型");
		}
		
		CouncilTypeInfo councilTypeInfo = councilTypeServiceClient.findById(councilTypeId);
		List<CouncilJudgeInfo> judges = councilTypeServiceClient
			.findCouncilJudgesByCouncilType(councilTypeId);
		
		// 添加判断，是否信惠
		// 判定是否全是信汇
		CompanyNameEnum company = CompanyNameEnum.XINHUI;
		for (CouncilApplyInfo info : list) {
			if (CompanyNameEnum.XINHUI != info.getCompanyName()) {
				company = CompanyNameEnum.NORMAL;
				break;
			}
		}
		String councilCode = councilApplyServiceClient.getAvailableCouncilCodeSeq(company,
			councilTypeInfo.getTypeCode());
		
		List<CouncilProjectInfo> projects = getProjects(list);
		CouncilInfo councilInfo = new CouncilInfo();
		councilInfo.setCouncilCode(councilCode);
		councilInfo.setJudges(judges);
		councilInfo.setParticipants(participants);
		councilInfo.setProjects(projects);
		councilInfo.setCouncilType(councilTypeInfo.getTypeId());
		councilInfo.setCouncilTypeName(councilTypeInfo.getTypeName());
		councilInfo.setCouncilTypeCode(councilTypeInfo.getTypeCode());
		//List<CouncilPlaceEnum> places = CouncilPlaceEnum.getAllEnum();
		List<JSONObject> places = getCouncilPlace();
		model.addAttribute("councilInfo", councilInfo);
		model.addAttribute("places", places);
		model.addAttribute("edit", true);
		model.addAttribute("councilTypeInfo", councilTypeInfo);
		model.addAttribute("judges", judges);
		return vm_path + "addCouncil.vm";//
	}
	
	/**
	 * 保存会议
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("addCouncil.json")
	@ResponseBody
	public JSONObject addCouncil(CouncilOrder order, Model model) {
		String tipPrefix = " 新增会议";
		JSONObject result = new JSONObject();
		
		try {
			setFields(order);
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = councilServiceClient.saveCouncil(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	private void setFields(CouncilOrder order) {
		List<CouncilProjectInfo> projects = Lists.newArrayList();
		List<CouncilJudgeInfo> judges = Lists.newArrayList();
		List<CouncilParticipantInfo> participants = Lists.newArrayList();
		if (StringUtil.isNotEmpty(order.getApplyIds())) {
			String projectsKey[] = order.getProjectsKey().split(",");
			String applyId[] = order.getApplyIds().split(",");
			for (int i = 0; i < applyId.length; i++) {
				CouncilProjectInfo project = new CouncilProjectInfo();
				CouncilApplyInfo applyInfo = councilApplyServiceClient
					.queryCouncilApplyByApplyId(Long.parseLong(applyId[i]));
				project.setProjectCode(applyInfo.getProjectCode());
				project.setProjectName(applyInfo.getProjectName());
				project.setApplyId(applyInfo.getApplyId());
				if (StringUtil.isNotEmpty(projectsKey[i])) {
					project.setId(Long.parseLong(projectsKey[i]));
				}
				projects.add(project);
			}
			order.setProjects(projects);
		} else {
			throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM, "未选择项目");
		}
		if (order.getJudges() != null && order.getJudges().size() > 0) {
			for (CouncilJudgeInfo info : order.getJudges()) {
				if (info.getJudgeId() != 0) {
					CouncilJudgeInfo judge = new CouncilJudgeInfo();
					judge.setJudgeId(info.getJudgeId());
					judge.setJudgeName(info.getJudgeName());
					judge.setId(info.getId());
					judges.add(judge);
				}
				
			}
			order.setJudges(judges);
		}
		if (StringUtil.isNotEmpty(order.getParticipantsId())) {
			String participantsKey[] = order.getParticipantsKey().split(",");
			String[] participantsId = order.getParticipantsId().split(",");
			String[] participantsName = order.getParticipantsName().split(",");
			for (int i = 0; i < participantsId.length; i++) {
				CouncilParticipantInfo participant = new CouncilParticipantInfo();
				participant.setParticipantId(Long.parseLong(participantsId[i]));
				participant.setParticipantName(participantsName[i]);
				if (StringUtil.isNotEmpty(participantsKey[i])) {
					participant.setId(Long.parseLong(participantsKey[i]));
				}
				participants.add(participant);
			}
			order.setParticipants(participants);
		}
	}
	
	/**
	 * 修改参会项目
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("changeCouncilProjects.json")
	@ResponseBody
	public JSONObject changeCouncilProjects(Long councilId, String applyIds, String projectsKey,
											Model model) {
		String tipPrefix = " 修改参会项目";
		JSONObject result = new JSONObject();
		
		try {
			
			String[] all = applyIds.split(",");
			String[] projectsIds = projectsKey.split(",");
			List<CouncilApplyInfo> list = Lists.newArrayList();
			List<CouncilParticipantInfo> participants = Lists.newArrayList();
			List<CouncilProjectInfo> projects = Lists.newArrayList();
			CouncilApplyInfo firstApplyInfo = councilApplyServiceClient
				.queryCouncilApplyByApplyId(Long.parseLong(all[0]));
			
			for (int i = 0; i < all.length; i++) {
				CouncilApplyInfo councilApplyInfo = councilApplyServiceClient
					.queryCouncilApplyByApplyId(Long.parseLong(all[i]));
				
				if (!firstApplyInfo.getCouncilCode().equals(councilApplyInfo.getCouncilCode())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
						"选择的项目的申请上会类型不一致");
				}
				list.add(councilApplyInfo);
				// 列席人员
				setParticipants(participants, councilApplyInfo);
				CouncilProjectInfo project = new CouncilProjectInfo();
				project.setApplyId(councilApplyInfo.getApplyId());
				project.setProjectCode(councilApplyInfo.getProjectCode());
				project.setProjectName(councilApplyInfo.getProjectName());
				if (StringUtil.isNotEmpty(projectsIds[i])) {
					project.setId(Long.parseLong(projectsIds[i]));
				}
				projects.add(project);
				
			}
			removeDuplicate(participants);
			
			CouncilInfo councilInfo = councilServiceClient.queryCouncilById(councilId);
			CouncilOrder councilOrder = new CouncilOrder();
			BeanCopier.staticCopy(councilInfo, councilOrder);
			councilOrder.setCouncilTypeCode(councilInfo.getCouncilTypeCode().code());
			councilOrder.setStatus(councilInfo.getStatus().code());
			
			councilOrder.setParticipants(participants);// 重置列席人员
			
			//List<CouncilProjectInfo> projects = getProjects(list);// 重置参会项目
			councilOrder.setProjects(projects);
			setSessionLocalInfo2Order(councilOrder);
			FcsBaseResult saveResult = councilServiceClient.saveCouncil(councilOrder);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 某次上会的项目列表(各项目投票结果)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("councilProjectList.htm")
	public String councilProjectList(Long councilId, Model model) {
		
		List<CouncilProjectVoteResultInfo> councilProjects = councilProjectServiceClient
			.queryProjectVoteResultByCouncilId(councilId);
		model.addAttribute("councilProjects", councilProjects);
		return vm_path + "councilProjectList.vm";
	}
	
	@RequestMapping("endCouncil.json")
	@ResponseBody
	public JSONObject endCouncil(CouncilOrder order, String oldCouncil, Model model) {
		String tipPrefix = " 结束会议";
		JSONObject result = new JSONObject();
		try {
			
			if (BooleanEnum.YES.code().equals(oldCouncil)) {
				FcsBaseResult baseResult = councilServiceClient.endOldCouncil(order);
				return toJSONResult(baseResult, tipPrefix);
			} else {
				FcsBaseResult baseResult = councilServiceClient.endCouncil(order.getCouncilId());
				return toJSONResult(baseResult, tipPrefix);
			}
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 所有上会的项目列表(各项目投票结果)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("allCouncilProjectList.htm")
	public String allCouncilProjectList(CouncilVoteProjectQueryOrder order, Model model) {
		
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (!isRiskSecretary()) {
			order.setJudgeId(sessionLocal.getUserId());
		}
		QueryBaseBatchResult<CouncilProjectVoteResultInfo> councilProjects = councilProjectServiceClient
			.queryProjectVoteResult(order);
		CouncilInfo councilInfo = councilServiceClient.queryCouncilById(order.getCouncilId());
		
		FCouncilSummaryInfo summary = councilSummaryServiceClient
			.queryCouncilSummaryByCouncilId(order.getCouncilId());
		if (summary != null) {
			councilInfo.setSummary(summary);
			councilInfo.setSummaryForm(formServiceClient.findByFormId(summary.getFormId()));
		}
		// 判定当前用户是否是会议的主持人
		Long compereId = councilInfo.getCompereId();
		if (compereId != null && compereId == sessionLocal.getUserId()) {
			model.addAttribute("isCompere", true);
		}
		
		model.addAttribute("council", councilInfo);
		model.addAttribute("isRiskSecretary", isRiskSecretary());
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(councilProjects));
		
		return vm_path + "councilProjectList.vm";
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
		String tipPrefix = " 投票";
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
					result = toJSONResult(fcsBaseResult, tipPrefix);
					return result;
				}
				boolean checked = findAndCheckKey(key);
				if (!checked) {
					FcsBaseResult fcsBaseResult = new FcsBaseResult();
					fcsBaseResult.setMessage("此证书key与用户数据不匹配！");
					result = toJSONResult(fcsBaseResult, tipPrefix);
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
			
			result = toJSONResult(saveResult, tipPrefix);
			
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
		
	}
	
	/**
	 * 催促投票
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("urgeVote.json")
	@ResponseBody
	public JSONObject urgeVote(CouncilProjectVoteOrder order, Model model) {
		String tipPrefix = " 催促投票";
		JSONObject result = new JSONObject();
		try {
			
			FcsBaseResult saveResult = councilProjectVoteServiceClient.urgeVote(order);
			
			result = toJSONResult(saveResult, tipPrefix);
			
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
		
	}
	
	/**
	 * 所有上会的项目列表(各项目投票结果)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("viewVote.htm")
	public String viewVote(CouncilProjectVoteQueryOrder order, Model model) {
		
		QueryBaseBatchResult<CouncilProjectVoteInfo> projectVotes = councilProjectVoteServiceClient
			.queryCouncilProjectVote(order);
		
		CouncilVoteProjectQueryOrder queryProjectOrder = new CouncilVoteProjectQueryOrder();
		queryProjectOrder.setCouncilId(order.getCouncilId());
		queryProjectOrder.setProjectCode(order.getProjectCode());
		
		// 查询project fprojectservice
		ProjectInfo ProjectInfo = projectServiceClient.queryByCode(order.getProjectCode(), false);
		QueryBaseBatchResult<CouncilProjectVoteResultInfo> batchResult = councilProjectServiceClient
			.queryProjectVoteResult(queryProjectOrder);
		
		CouncilProjectVoteResultInfo councilProjectVote = new CouncilProjectVoteResultInfo();
		if (batchResult.getPageList().size() > 0) {
			councilProjectVote = batchResult.getPageList().get(0);
			
		}
		// 会议信息
		CouncilInfo councilInfo = councilServiceClient.queryCouncilById(order.getCouncilId());
		model.addAttribute("council", councilInfo);
		
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(projectVotes));
		model.addAttribute("councilProjectVote", councilProjectVote);
		model.addAttribute("isRiskSecretary", isRiskSecretary());
		model.addAttribute("project", ProjectInfo);
		
		return vm_path + "view_vote.vm";
	}
	
	/**
	 * 列席人员
	 * 
	 * @param participants
	 * @param councilApplyInfo
	 */
	private void setParticipants(List<CouncilParticipantInfo> participants,
									CouncilApplyInfo councilApplyInfo) {
		CouncilParticipantInfo busMger = new CouncilParticipantInfo();
		//		ProjectInfo project = projectServiceClient.queryDetailInfo(councilApplyInfo
		//			.getProjectCode());
		//		
		//		// 列席人员规则：系统自动带出对应的业务经理（A角）、风险经理
		//		if (project != null) {
		//			busMger.setParticipantId(project.getBusiManagerId());
		//			busMger.setParticipantName(project.getBusiManagerName());
		//			participants.add(busMger);
		//		}
		//		
		//		FProjectGuaranteeEntrustedInfo projectGuaran = projectSetupServiceClient
		//			.queryGuaranteeEntrustedProjectByCode(councilApplyInfo.getProjectCode());
		//		if (projectGuaran != null && projectGuaran.getRiskManagerId() != 0) {
		//			CouncilParticipantInfo riskMger = new CouncilParticipantInfo();
		//			riskMger.setParticipantId(projectGuaran.getRiskManagerId());
		//			riskMger.setParticipantName(projectGuaran.getRiskManagerName());
		//			participants.add(riskMger);
		//		}
		// 业务经理A角
		ProjectRelatedUserInfo busiManager = projectRelatedUserServiceClient
			.getBusiManager(councilApplyInfo.getProjectCode());
		if (busiManager != null) {
			
			if (!checkHasData(participants, busiManager.getUserId())) {
				busMger.setParticipantId(busiManager.getUserId());
				busMger.setParticipantName(busiManager.getUserName());
				participants.add(busMger);
			}
		}
		// 风险经理
		CouncilParticipantInfo riskMger = new CouncilParticipantInfo();
		ProjectRelatedUserInfo riskManager = projectRelatedUserServiceClient
			.getRiskManager(councilApplyInfo.getProjectCode());
		if (riskManager != null) {
			
			if (!checkHasData(participants, riskManager.getUserId())) {
				riskMger.setParticipantId(riskManager.getUserId());
				riskMger.setParticipantName(riskManager.getUserName());
				participants.add(riskMger);
			}
		}
		//  业务总监
		List<SimpleUserInfo> users = projectRelatedUserServiceClient
			.getBusiDirector(councilApplyInfo.getProjectCode());
		if (ListUtil.isNotEmpty(users)) {
			for (SimpleUserInfo user : users) {
				CouncilParticipantInfo busiDirector = new CouncilParticipantInfo();
				if (!checkHasData(participants, user.getUserId())) {
					busiDirector.setParticipantId(user.getUserId());
					busiDirector.setParticipantName(user.getUserName());
					participants.add(busiDirector);
				}
			}
		}
		
		// 业务经理B角
		CouncilParticipantInfo busiMger = new CouncilParticipantInfo();
		ProjectRelatedUserInfo busiManagerb = projectRelatedUserServiceClient
			.getBusiManagerb(councilApplyInfo.getProjectCode());
		if (busiManagerb != null) {
			
			if (!checkHasData(participants, busiManagerb.getUserId())) {
				busiMger.setParticipantId(busiManagerb.getUserId());
				busiMger.setParticipantName(busiManagerb.getUserName());
				participants.add(busiMger);
			}
		}
		
		// 法务经理 getLegalManager
		CouncilParticipantInfo legalMger = new CouncilParticipantInfo();
		ProjectRelatedUserInfo legalManagerUser = projectRelatedUserServiceClient
			.getLegalManager(councilApplyInfo.getProjectCode());
		if (legalManagerUser != null) {
			
			if (!checkHasData(participants, legalManagerUser.getUserId())) {
				legalMger.setParticipantId(legalManagerUser.getUserId());
				legalMger.setParticipantName(legalManagerUser.getUserName());
				participants.add(legalMger);
			}
		}
		
	}
	
	private boolean checkHasData(List<CouncilParticipantInfo> participants, Long userId) {
		boolean hasThisData = false;
		for (CouncilParticipantInfo participant : participants) {
			if (userId == participant.getParticipantId()) {
				hasThisData = true;
				break;
			}
		}
		return hasThisData;
	}
	
	private List<CouncilProjectInfo> getProjects(List<CouncilApplyInfo> councilApplyInfos) {
		List<CouncilProjectInfo> list = Lists.newArrayList();
		for (CouncilApplyInfo info : councilApplyInfos) {
			CouncilProjectInfo project = new CouncilProjectInfo();
			project.setApplyId(info.getApplyId());
			project.setProjectCode(info.getProjectCode());
			project.setProjectName(info.getProjectName());
			list.add(project);
		}
		return list;
	}
	
	private void removeDuplicate(List<CouncilParticipantInfo> list) {
		HashSet<CouncilParticipantInfo> h = new HashSet<CouncilParticipantInfo>(list);
		list.clear();
		list.addAll(h);
	}
	
	/**
	 * 根据原列席人员和projectcodes拼接出新列席人员
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("findParticipants.json")
	@ResponseBody
	public JSONObject findParticipants(String participantsId, String participantsName,
										String participantsKey, String projectsCode
	//	                                   List<CouncilParticipantInfo> participants,
	//										List<String> projectCodes
	) {
		String tipPrefix = "获取新列席人员列表";
		JSONObject result = new JSONObject();
		JSONObject json = new JSONObject();
		// 将空转换为"";
		participantsId = StringUtil.isBlank(participantsId) ? "" : participantsId;
		participantsName = StringUtil.isBlank(participantsName) ? "" : participantsName;
		participantsKey = StringUtil.isBlank(participantsKey) ? "" : participantsKey;
		// 列席人员表单独抓出来，以免重复
		List<CouncilParticipantInfo> participants = new ArrayList<CouncilParticipantInfo>();
		// 将原数据放入列席人员中
		// 因为要删除被删掉的项目的各项列席人员很麻烦，所以采用每次更新都删掉所有列席人员。
		// 只给与最基本的查询数据的方式。因此此处不再加入老数据里的列席人员，全部清空处理
		//		if (StringUtil.isNotBlank(participantsKey)) {
		//			String[] keys = participantsKey.split(",");
		//			String[] ids = participantsId.split(",");
		//			String[] names = participantsName.split(",");
		//			for (int i = 0; i < keys.length; i++) {
		//				CouncilParticipantInfo info = new CouncilParticipantInfo();
		//				info.setParticipantId(Long.parseLong(ids[i]));
		//				info.setParticipantName(names[i]);
		//				info.setId(Long.parseLong(keys[i]));
		//				participants.add(info);
		//			}
		//			
		//		}
		
		//		if (ListUtil.isEmpty(participants)) {
		//			participants = new ArrayList<CouncilParticipantInfo>();
		//		}
		//		if (ListUtil.isEmpty(projectCodes)) {
		//			json.put("success", true);
		//			json.put("message", tipPrefix + "成功");
		//			json.put("data", participants);
		//		}
		if (StringUtil.isBlank(projectsCode)) {
			json.put("success", true);
			json.put("message", tipPrefix + "成功");
			json.put("participantsId", participantsId);
			json.put("participantsName", participantsName);
			json.put("participantsKey", participantsKey);
			return json;
		}
		
		String[] projectCodes = projectsCode.split(",");
		
		try {
			for (String code : projectCodes) {
				CouncilApplyInfo councilApplyInfo = new CouncilApplyInfo();
				councilApplyInfo.setProjectCode(code);
				setParticipants(participants, councilApplyInfo);
			}
			// 设置回传参数
			String returnParticipantsId = "";
			String returnparticipantsName = "";
			String returnParticipantsKey = "";
			// 将查询出来的列席人员放置到新列表中
			for (CouncilParticipantInfo info : participants) {
				if (info == null) {
					break;
				}
				returnParticipantsId = addString(returnParticipantsId,
					String.valueOf(info.getParticipantId()));
				returnparticipantsName = addString(returnparticipantsName,
					info.getParticipantName());
				returnParticipantsKey = addString(returnParticipantsKey,
					String.valueOf(info.getId()));
			}
			json.put("participantsId", returnParticipantsId);
			json.put("participantsName", returnparticipantsName);
			json.put("participantsKey", returnParticipantsKey);
			//			json.put("code", participants);
			//			result = toStandardResult(json, tipPrefix);
			
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return json;
		
	}
	
	/**
	 * 给列席人员字符串拼接新内容
	 * @param str
	 * @param info
	 */
	private String addString(String str, String str2) {
		
		if (StringUtil.isNotBlank(str)) {
			str += ",";
		}
		str += str2;
		return str;
	}
	
	/**
	 * 根据会议类型寻找参会评委
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("findCouncilJudgesFromCouncilType.json")
	@ResponseBody
	public JSONObject findCouncilJudgesFromCouncilType(Long councilTypeId) {
		String tipPrefix = "获取参会评委列表";
		JSONObject result = new JSONObject();
		JSONObject json = new JSONObject();
		if (councilTypeId <= 0) {
			json.put("success", false);
			json.put("message", tipPrefix + "失败:会议id必须大于0!");
		}
		
		try {
			List<CouncilJudgeInfo> judges = councilTypeServiceClient
				.findCouncilJudgesByCouncilType(councilTypeId);
			if (ListUtil.isEmpty(judges)) {
				judges = new ArrayList<CouncilJudgeInfo>();
			}
			json.put("judges", judges);
			result = toStandardResult(json, tipPrefix);
			
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
		
	}
	
	/**
	 * 删除会议
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteCouncil.json")
	@ResponseBody
	public JSONObject deleteCouncil(CouncilDelOrder order) {
		String tipPrefix = "删除会议";
		JSONObject result = new JSONObject();
		try {
			FcsBaseResult fcsBaseResult = councilServiceClient.delCouncil(order);
			result = toJSONResult(fcsBaseResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
		
	}
	
	/**
	 * 检测key
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("checkJudgeKey.json")
	@ResponseBody
	public JSONObject checkJudgeKey(String key) {
		String tipPrefix = "检测评委key";
		JSONObject result = new JSONObject();
		if (StringUtil.isBlank(key)) {
			result.put("code", "0");
			result.put("message", "请先插入key并安装证书控件！");
		}
		try {
			//  校验key
			boolean checked = findAndCheckKey(key);
			if (checked) {
				result.put("code", "1");
			} else {
				result.put("code", "0");
				result.put("message", "此证书key与用户数据不匹配！");
			}
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
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
	
	/**
	 * 主持人本次不议后风控委秘书的后续操作
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("saveCompereMessage.htm")
	@ResponseBody
	public JSONObject saveCompereMessage(CouncilProjectOrder order) {
		String tipPrefix = "本次不议后续操作";
		JSONObject result = new JSONObject();
		try {
			FcsBaseResult fcsBaseResult = councilProjectServiceClient
				.saveCompereAfterMessage(order);
			result = toJSONResult(fcsBaseResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
		
	}
	
	/**
	 * 查询会议的下一个会议编号，
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("getNextCouncilCode.json")
	@ResponseBody
	public JSONObject getNextCouncilCode(String applyIds, String typeCode) {
		String tipPrefix = "查询会议的下一个会议编号";
		JSONObject result = new JSONObject();
		try {
			CompanyNameEnum company = CompanyNameEnum.XINHUI;
			String[] ids = applyIds.split(",");
			for (String applyId : ids) {
				CouncilApplyInfo councilApplyInfo = councilApplyServiceClient
					.queryCouncilApplyByApplyId(Long.parseLong(applyId));
				if (CompanyNameEnum.XINHUI != councilApplyInfo.getCompanyName()) {
					company = CompanyNameEnum.NORMAL;
					break;
				}
				
			}
			String councilCode = councilApplyServiceClient.getAvailableCouncilCodeSeq(company,
				CouncilTypeEnum.getByCode(typeCode));
			result.put("success", true);
			result.put("message", tipPrefix + "成功");
			result.put("councilCode", councilCode);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
		
	}
}
