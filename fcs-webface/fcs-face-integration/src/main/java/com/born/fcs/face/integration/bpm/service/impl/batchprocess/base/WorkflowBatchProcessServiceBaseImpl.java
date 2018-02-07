package com.born.fcs.face.integration.bpm.service.impl.batchprocess.base;

import java.util.List;
import java.util.Map;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.face.integration.bpm.service.WorkflowBatchProcessService;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.info.common.FormExecuteInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 批量审核处理service默认实现
 * @author wuzj
 */
public class WorkflowBatchProcessServiceBaseImpl implements WorkflowBatchProcessService {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String[] nonSupportTask() {
		return new String[] {};
	}
	
	@Override
	public boolean isSupport(FormInfo form, SimpleUserInfo executor) {
		return true;
	}
	
	@Override
	public Map<String, Object> makeCustomizeMap(FormInfo form, SimpleUserInfo executor) {
		return Maps.newHashMap();
	}
	
	/**
	 * 获取当前人的任务地址
	 * @param form
	 * @param executor
	 * @return
	 */
	protected String getUserTaskUrl(FormInfo form, long executor) {
		String taskUrl = "";
		try {
			List<FormExecuteInfo> exeList = form.getFormExecuteInfo();
			if (ListUtil.isNotEmpty(exeList)) {
				for (FormExecuteInfo exeInfo : exeList) {
					if (StringUtil.isEmpty(exeInfo.getTaskUrl()))
						continue;
					if (exeInfo.getUserId() > 0) {
						if (exeInfo.getUserId() == executor) {
							taskUrl = exeInfo.getTaskUrl();
							break;
						}
					} else if (ListUtil.isNotEmpty(exeInfo.getCandidateUserList())) {
						for (SimpleUserInfo user : exeInfo.getCandidateUserList()) {
							if (user.getUserId() == executor) {
								taskUrl = exeInfo.getTaskUrl();
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取执行人任务url出错", e);
		}
		return taskUrl;
	}
}
