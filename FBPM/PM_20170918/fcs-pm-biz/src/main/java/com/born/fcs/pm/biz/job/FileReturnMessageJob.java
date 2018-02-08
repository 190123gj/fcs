package com.born.fcs.pm.biz.job;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.daointerface.ExtraDAO;
import com.born.fcs.pm.dataobject.FileNeedSendMessageProjectDO;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.FileTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;

import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.info.file.FileFormInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.service.council.CouncilService;
import com.born.fcs.pm.ws.service.file.FileService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 档案归还消息推送
 *
 * @author heh
 *
 * 2016-6-22
 */
@Service("fileReturnMessageJob")
public class FileReturnMessageJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	SiteMessageService siteMessageService;//消息推送
	@Autowired
	private CouncilService councilService;
	@Autowired
	private FileService fileService;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	private ExtraDAO extraDAO;
	
	// 每天0点0分触发
	@Scheduled(cron = "0 0 0 * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		// 获取系统时间
		Date today = new Date();
		try {
			logger.info("档案归还未归还消息推送开始在 " + DateUtil.simpleFormat(new Date()));
			List<FileFormInfo> fileList = fileService.needMessageFile();
			if (ListUtil.isNotEmpty(fileList)) {
				for (FileFormInfo fileFormInfo : fileList) {
					if (fileFormInfo != null) {
						int temp = DateUtil.calculateDecreaseDate(
							DateUtil.dtSimpleFormat(today),DateUtil.dtSimpleFormat(fileFormInfo.getExpectReturnTime()));
						logger.info("temp: " + temp);
						//归还日前一周、3天、当天分别提醒1次归还
						if (temp == 0||temp == 7||temp == 3) {
							//发给申请人
							MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
							String url = CommonUtil.getRedirectUrl("/projectMg/file/detailFileList.htm")+"&fileCode="+fileFormInfo.getFileCode();
							StringBuffer sb = new StringBuffer();
							sb.append("尊敬的用户");
							sb.append(fileFormInfo.getReceiveMan());
							sb.append(",您借出的档案(编号：");
							sb.append(fileFormInfo.getFileCode());
							if(temp==0)
							sb.append(") 今日应该归还了，快去归还吧！");
							else
							sb.append(") 应该归还了，快去归还吧！");
							//						sb.append(fileFormInfo.getFileCode());
							sb.append("<a href='" + url + "'>马上归还</a>");
							String content = sb.toString();
							messageOrder.setMessageContent(content);
							List<SimpleUserInfo> sendUserList = Lists.newArrayList();
							
							SysUser sysUser = bpmUserQueryService.findUserByUserId(fileFormInfo
								.getReceiveManId());//申请人
							if (sysUser != null) {
								SimpleUserInfo user = new SimpleUserInfo();
								user.setUserAccount(sysUser.getAccount());
								user.setUserId(sysUser.getUserId());
								user.setUserName(sysUser.getFullname());
								sendUserList.add(user);
							}
							messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
								.toArray(new SimpleUserInfo[sendUserList.size()]));
							siteMessageService.addMessageInfo(messageOrder);
							//发给档案管理员
							List<SysUser> sysUsers = bpmUserQueryService.findUserByRoleAlias("BusinessSys_"+sysParameterService.getSysParameterValue(SysParamEnum.SYS_PARAM_FILE_ADMINISTRATOR_ROLE_NAME
											.code()));
							if(sysUsers!=null&&sysUsers.size()>0){
								for(SysUser user:sysUsers){
									logger.info("案管理员: "+user.getFullname());
									MessageOrder messageOrder1 = MessageOrder.newSystemMessageOrder();
									String url1 = CommonUtil
											.getRedirectUrl("/projectMg/file/detailFileList.htm")
											+ "&fileCode=" + fileFormInfo.getFileCode();
									StringBuffer sb1 = new StringBuffer();
									sb1.append("尊敬的用户");
									sb1.append(user.getFullname());
									sb1.append(",由");
									sb1.append(fileFormInfo.getReceiveMan());
									sb1.append("借出的档案(编号：");
									sb1.append(fileFormInfo.getFileCode());
									sb1.append(") 应该归还了，快去催促归还吧！如已归还，请到系统中完成入库。");
									//						sb1.append(fileFormInfo.getFileCode());
									sb1.append("<a href='" + url1 + "'>查看详情</a>");
									String content1 = sb1.toString();
									messageOrder1.setMessageContent(content1);
									List<SimpleUserInfo> sendUserList1 = Lists.newArrayList();
										SimpleUserInfo user1 = new SimpleUserInfo();
										user1.setUserAccount(user.getAccount());
										user1.setUserId(user.getUserId());
										user1.setUserName(user.getFullname());
										sendUserList1.add(user1);
									messageOrder1.setSendUsers((SimpleUserInfo[]) sendUserList1
											.toArray(new SimpleUserInfo[sendUserList1.size()]));
									siteMessageService.addMessageInfo(messageOrder1);
								}
							}else{
								logger.info("当前没有档案管理员 ");
							}

						}
					}
				}
			}
			logger.info("档案归还未归还消息推送结束在 " + DateUtil.simpleFormat(new Date()));
		} catch (Exception e) {
			logger.error("档案归还未归还消息推送异常---异常原因：" + e.getMessage(), e);
		}
		try {
			logger.info("档案归档提醒消息推送开始在 " + DateUtil.simpleFormat(new Date()));
			// 基础卷：第一次放款后一个月之内归档，第一次放款后第15个自然日提醒，第25个自然日提醒第二次（已归档则不再提醒），
			// 第30个自然提提醒第三次（已归档则不再提醒）
			String type= FileTypeEnum.CREDIT_BUSSINESS.code();
			int num=0;
			List<FileNeedSendMessageProjectDO> projectList=extraDAO.FileNeedMessageProject(type,null);
			if(projectList!=null&&projectList.size()>0){
				for(FileNeedSendMessageProjectDO DO:projectList){
					//基础卷开始发消息
					int days = DateUtil.calculateDecreaseDate(DateUtil.dtSimpleFormat(DO.getActualLoanTime()),DateUtil.dtSimpleFormat(today));
                    if(days==15||days==25||days==30){
						sendMessage(DO,FileTypeEnum.CREDIT_BUSSINESS);
						num++;
					}
				}
			}
			logger.info("档案归档提醒基础卷提醒数" + num);
			//授后卷：2/5/8/11自然月   各个自然月 5、10号提醒两次
			type= FileTypeEnum.CREDIT_BEFORE_MANAGEMENT.code();
			projectList=extraDAO.FileNeedMessageProject(type,null);
			int month = Calendar.getInstance().get(Calendar.MONTH)+1;
			int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
			num=0;
			if(projectList!=null&&projectList.size()>0){
				for(FileNeedSendMessageProjectDO DO:projectList){
					//授后卷开始发消息
					if(month==2||month==5||month==8||month==11){
						if(day==5||day==10){
						sendMessage(DO,FileTypeEnum.CREDIT_BEFORE_MANAGEMENT);
							num++;
						}
					}
				}
			}
			logger.info("档案归档提醒授后卷提醒数" + num);
            // 权证卷：放款申请以后，第5-7个自然日提醒归档，提醒三次
			type= FileTypeEnum.DOCUMENT_OF_TITLE.code();
			List<String> statusList=Lists.newArrayList();
			statusList.add(FormStatusEnum.BACK.code());
			statusList.add(FormStatusEnum.DRAFT.code());
			statusList.add(FormStatusEnum.DELETED.code());
			statusList.add(FormStatusEnum.DENY.code());
			statusList.add(FormStatusEnum.CANCEL.code());
			projectList=extraDAO.FileNeedMessageProject(type,statusList);
			num=0;
			if(projectList!=null&&projectList.size()>0){
				for(FileNeedSendMessageProjectDO DO:projectList){
					int days = DateUtil.calculateDecreaseDate(DateUtil.dtSimpleFormat(DO.getSubmitTime()),DateUtil.dtSimpleFormat(today));
					//权证卷开始发消息
					if(days==5||days==6||days==7){
							sendMessage(DO,FileTypeEnum.DOCUMENT_OF_TITLE);
						num++;
					}
				}
			}
			logger.info("档案归档提醒权证卷提醒数" + num);
			logger.info("档案归档提醒消息推送结束在 " + DateUtil.simpleFormat(new Date()));
		}catch (Exception e){
			logger.error("档案归档提醒消息推送异常---异常原因：" + e.getMessage(), e);
		}
		

	}

	//发送消息
	public void sendMessage(FileNeedSendMessageProjectDO projectDO,FileTypeEnum type) {
		if(!StringUtil.equals(projectDO.getPhases(),ProjectPhasesEnum.FINISH_PHASES.code())) {
			MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
			StringBuffer sb = new StringBuffer();
			sb.append("尊敬的用户");
			sb.append(projectDO.getBusiManagerName() + ",");
			if (type == FileTypeEnum.CREDIT_BEFORE_MANAGEMENT) {
				sb.append("请及时对项目");
			} else {
				sb.append("您的项目");
			}
			sb.append(projectDO.getProjectCode());
			sb.append("项目名称");
			sb.append(projectDO.getProjectName());
			if (type == FileTypeEnum.CREDIT_BUSSINESS) {
				sb.append("已于");
				sb.append(DateUtil.dtSimpleFormat(projectDO.getActualLoanTime()));
				sb.append("放款完成,请及时进行基础卷归档。");
			}
			if (type == FileTypeEnum.CREDIT_BEFORE_MANAGEMENT) {
				sb.append("进行授后管理卷归档。");
			}
			if (type == FileTypeEnum.DOCUMENT_OF_TITLE) {
				sb.append("已于");
				sb.append(DateUtil.simpleDateFormatmdhChinese(projectDO.getSubmitTime()));
				sb.append("提交放款申请，请及时进行权证卷归档。");
			}
			String content = sb.toString();
			messageOrder.setMessageContent(content);
			List<SimpleUserInfo> sendUserList = Lists.newArrayList();
			SimpleUserInfo user = new SimpleUserInfo();
			user.setUserAccount(projectDO.getBusiManagerAccount());
			user.setUserId(projectDO.getBusiManagerId());
			user.setUserName(projectDO.getBusiManagerName());
			sendUserList.add(user);
			messageOrder.setSendUsers((SimpleUserInfo[]) sendUserList
					.toArray(new SimpleUserInfo[sendUserList.size()]));
			siteMessageService.addMessageInfo(messageOrder);
		}
	}


}
