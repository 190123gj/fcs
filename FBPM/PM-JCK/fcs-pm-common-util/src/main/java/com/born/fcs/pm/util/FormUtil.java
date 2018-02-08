package com.born.fcs.pm.util;

import java.util.List;
import java.util.Map;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONArray;
import com.born.fcs.pm.ws.info.common.FormExecuteInfo;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 表单工具
 *
 * @author wuzj
 */
public class FormUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FormUtil.class);
	
	private static final String EMPTY = "";
	
	/**
	 * 解析表单执行信息
	 * @param taskUserData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<FormExecuteInfo> parseTaskUserData(String taskUserData) {
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
				logger.error(e.getMessage(), e);
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
	public static String getProcessUrl(String taskUserData, long userId) {
		return getProcessUrl(parseTaskUserData(taskUserData), userId, false);
	}
	
	/**
	 * 获取任务处理跳转URL
	 * @param taskUserData
	 * @param userId
	 * @return
	 */
	public static String getRedirectProcessUrl(String taskUserData, long userId) {
		return getProcessUrl(parseTaskUserData(taskUserData), userId, true);
	}
	
	/**
	 * 获取任务处理URL
	 * @param executeInfo
	 * @param userId
	 * @return
	 */
	public static String getProcessUrl(List<FormExecuteInfo> executeInfo, long userId) {
		return getProcessUrl(executeInfo, userId, false);
	}
	
	/**
	 * 获取任务处理跳转的URL
	 * @param executeInfo
	 * @param userId
	 * @return
	 */
	public static String getRedirectProcessUrl(List<FormExecuteInfo> executeInfo, long userId) {
		return getProcessUrl(executeInfo, userId, true);
	}
	
	/**
	 * 获取任务处理URL
	 * @param executeInfo
	 * @param userId
	 * @param isRedirect 是否跳转url
	 * @return
	 */
	public static String getProcessUrl(List<FormExecuteInfo> executeInfo, long userId,
										boolean isRedirect) {
		String processUrl = EMPTY;
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
