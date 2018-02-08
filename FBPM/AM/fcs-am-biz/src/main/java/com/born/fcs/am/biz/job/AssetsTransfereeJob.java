package com.born.fcs.am.biz.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.am.biz.job.inter.ProcessJobService;
import com.born.fcs.am.intergration.bpm.BpmUserQueryService;
import com.born.fcs.am.intergration.bpm.service.client.user.SysUser;
import com.born.fcs.am.intergration.common.SiteMessageServiceClient;
import com.born.fcs.am.intergration.service.ProjectServiceClient;
import com.born.fcs.am.ws.info.transferee.FAssetsTransfereeApplicationInfo;
import com.born.fcs.am.ws.order.transferee.FAssetsTransfereeApplicationQueryOrder;
import com.born.fcs.am.ws.service.transferee.AssetsTransfereeApplicationService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 资产受让清收时间消息提醒
 * 
 * @author Ji
 *
 */
@Service("assetsTransfereeJob")
public class AssetsTransfereeJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AssetsTransfereeApplicationService assetsTransfereeApplicationService;
	@Autowired
	private SysParameterService sysParameterServiceClient;
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	ProjectServiceClient projectServiceClient;
	@Autowired
	SiteMessageServiceClient siteMessageServiceClient;
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserService;
	
	// 每天0点1分提示一次
	@Scheduled(cron = "0 1 0 * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		logger.info("处理资产受让清收时间任务开始在 " + DateUtil.simpleFormat(new Date()));
		try {
			FAssetsTransfereeApplicationQueryOrder queryOrder = new FAssetsTransfereeApplicationQueryOrder();
			queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
			queryOrder.setIsTrusteeLiquidate(BooleanEnum.IS.code());
			queryOrder.setPageSize(99999999999l);
			QueryBaseBatchResult<FAssetsTransfereeApplicationInfo> batchResult = assetsTransfereeApplicationService
				.query(queryOrder);
			List<FAssetsTransfereeApplicationInfo> listInfo = batchResult.getPageList();
			int temp = 0;// 记录当前时间和清收时间差值
			Date now = new Date();// 当前日期
			String nowTime = DateUtil.dtSimpleFormat(now);
			for (FAssetsTransfereeApplicationInfo info : listInfo) {
				String endTime = DateUtil.dtSimpleFormat(info.getLiquidateTime());// 清收时间
				temp = DateUtil.calculateDecreaseDate(endTime, nowTime);
				if (temp >= -29 && temp <= 0 && info.getIsCloseMessage() == BooleanEnum.NO) {
					sendMessageForm(info, temp);
				}
				if (temp == -29 || temp == -14) {
					sendMessageOther(info, temp);
				}
			}
			
			logger.info("处理资产受让清收时间任务");
		} catch (Exception e) {
			logger.error("处理项资产受让清收时间任务异常---异常原因：" + e.getMessage(), e);
		}
		
		logger.info("处理资产受让清收时间任务结束在 " + DateUtil.simpleFormat(new Date()));
	}
	
	// 发送消息
	public void sendMessageForm(FAssetsTransfereeApplicationInfo info, int temp) {
		
		MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
		StringBuffer sb = new StringBuffer();
		String closeMessageUrl = "/assetMg/transferee/closeMessage.htm" + "?formId="
									+ info.getFormId();
		
		String capitalUrl = CommonUtil
			.getRedirectUrl("/projectMg/fCapitalAppropriationApply/addCapitalAppropriationApply.htm")
							+ "?projectCode="
							+ info.getProjectCode()
							+ "&projectType=NOT_FINANCIAL_PRODUCT";
		sb.append("受让资产“项目名称");
		sb.append(info.getProjectName() + "”");
		sb.append("委托清收时间为" + DateUtil.dtSimpleFormat(info.getLiquidateTime()) + "，");
		sb.append("距离该时间剩余" + (-temp + 1) + "天");
		sb.append("您可以");
		sb.append("<a href='javascript:void(0);' class='fnDoAjax' ajaxurl='" + closeMessageUrl
					+ "'>关闭该系统通知</a>");
		if (info.getProjectCode() != null && !"".equals(info.getProjectCode())) {
			sb.append("或者");
			sb.append("<a href='" + capitalUrl + "'>发起资金划付申请单</a>");
		}
		String content = sb.toString();
		messageOrder.setMessageContent(content);
		List<SimpleUserInfo> sendUserList = new ArrayList<SimpleUserInfo>();
		SimpleUserInfo user = new SimpleUserInfo();
		
		user.setUserAccount(info.getFormUserAccount());
		user.setUserId(info.getFormUserId());
		user.setUserName(info.getFormUserName());
		sendUserList.add(user);
		messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
			.toArray(new SimpleUserInfo[sendUserList.size()]));
		siteMessageServiceClient.addMessageInfo(messageOrder);
	}
	
	// 发送消息
	public void sendMessageOther(FAssetsTransfereeApplicationInfo info, int temp) {
		
		MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
		StringBuffer sb = new StringBuffer();
		sb.append("受让资产“");
		sb.append("项目名称" + info.getProjectName() + "”");
		sb.append("清收时间为" + DateUtil.dtSimpleFormat(info.getLiquidateTime()) + "，");
		sb.append("距离该时间剩余" + (-temp + 1) + "天");
		String content = sb.toString();
		messageOrder.setMessageContent(content);
		List<SimpleUserInfo> sendUserList = new ArrayList<SimpleUserInfo>();
		
		String sysParamValueCWYSG = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_CWYSG_ROLE_NAME.code()); // 财务应收岗 参数
		String sysParamValueCWYFG = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_CWYFG_ROLE_NAME.code()); // 财务应付岗 参数
		List<SysUser> listUserCWYSG = bpmUserQueryService.findUserByRoleAlias(sysParamValueCWYSG);
		List<SysUser> listUserCWYFG = bpmUserQueryService.findUserByRoleAlias(sysParamValueCWYFG);
		if (listUserCWYSG != null) {
			for (SysUser sysUser : listUserCWYSG) {
				if (sysUser != null) {
					SimpleUserInfo user = new SimpleUserInfo();
					user.setUserAccount(sysUser.getAccount());
					user.setUserId(sysUser.getUserId());
					user.setUserName(sysUser.getFullname());
					sendUserList.add(user);
				}
			}
		}
		if (listUserCWYFG != null) {
			for (SysUser sysUser : listUserCWYFG) {
				if (sysUser != null) {
					SimpleUserInfo user = new SimpleUserInfo();
					user.setUserAccount(sysUser.getAccount());
					user.setUserId(sysUser.getUserId());
					user.setUserName(sysUser.getFullname());
					sendUserList.add(user);
				}
			}
		}
		if (info.getProjectCode() != null && "".equals(info.getProjectCode())) {
			ProjectSimpleDetailInfo projectInfo = projectServiceClient.querySimpleDetailInfo(info
				.getProjectCode());
			String deptCode = projectInfo.getDeptCode();// 部门编号
			
			List<SimpleUserInfo> listUserInfo = projectRelatedUserService.getDeptRoleUser(deptCode,
				sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_FGFZ_ROLE_NAME.code())); // 分管副总
			if (listUserInfo != null) {
				for (SimpleUserInfo simpleUserInfo : listUserInfo) {
					sendUserList.add(simpleUserInfo);
				}
			}
			List<SimpleUserInfo> listUserInfoYW = projectRelatedUserService.getDeptRoleUser(
				deptCode, sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code()));// 业务部部门负责人
			if (listUserInfoYW != null) {
				for (SimpleUserInfo simpleUserInfo : listUserInfoYW) {
					sendUserList.add(simpleUserInfo);
				}
			}
		}
		String sysParamValueZJL = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_ZJL_ROLE_NAME.code()); // 总经理 参数
		List<SysUser> listUserZJL = bpmUserQueryService.findUserByRoleAlias(sysParamValueZJL);
		if (listUserZJL != null) {
			for (SysUser sysUser : listUserZJL) {
				if (sysUser != null) {
					SimpleUserInfo user = new SimpleUserInfo();
					user.setUserAccount(sysUser.getAccount());
					user.setUserId(sysUser.getUserId());
					user.setUserName(sysUser.getFullname());
					sendUserList.add(user);
				}
			}
		}
		List<SimpleUserInfo> listUserInfoFW = projectRelatedUserService.getDeptRoleUser(
			sysParameterServiceClient.getSysParameterValue(SysParamEnum.SYS_PARAM_FW_DEPT_CODE
				.code()), sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code()));// 法务部门负责人
		if (listUserInfoFW != null) {
			for (SimpleUserInfo simpleUserInfo : listUserInfoFW) {
				sendUserList.add(simpleUserInfo);
			}
		}
		List<SimpleUserInfo> listUserInfoFX = projectRelatedUserService.getDeptRoleUser(
			sysParameterServiceClient.getSysParameterValue(SysParamEnum.SYS_PARAM_RISK_DEPT_CODE
				.code()), sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_BMFZR_ROLE_NAME.code()));// 风险部门负责人
		if (listUserInfoFX != null) {
			for (SimpleUserInfo simpleUserInfo : listUserInfoFX) {
				sendUserList.add(simpleUserInfo);
			}
		}
		messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
			.toArray(new SimpleUserInfo[sendUserList.size()]));
		siteMessageServiceClient.addMessageInfo(messageOrder);
	}
}
