package com.born.fcs.crm.biz.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.dal.dataobject.ChannalInfoDO;
import com.born.fcs.crm.integration.bpm.BpmUserQueryService;
import com.born.fcs.crm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SendMessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;

@Service("remindMessageService")
public class RemindMessageServiceImpl extends BaseAutowiredDAO implements RemindMessageService {
	
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	@Override
	public void sendMessage() {
		//1.查询2个月后将到期未发送提醒消息的渠道
		ChannalInfoDO channalInfo = new ChannalInfoDO();
		channalInfo.setStatus("on");
		channalInfo.setIsRemind(BooleanEnum.NO.code());
		channalInfo.setCreditEndDate(getNext2M());//查询大于传入的时间
		List<ChannalInfoDO> channalList = channalInfoDAO.findWithCondition(channalInfo, 0, 1000,
			null, null, null);
		if (ListUtil.isNotEmpty(channalList)) {
			//2.查询渠道所有渠道管理员
			List<SysUser> userList = bpmUserQueryService.findUserByRoleAlias("BusinessSys_scyxg");
			if (ListUtil.isNotEmpty(userList)) {
				//3.发送消息
				MessageOrder messageOrder = SendMessageOrder.newSystemMessageOrder();
				
				messageOrder.setMessageTitle("渠道授信即将到期提醒");
				
				SimpleUserInfo sendUser = null;
				List<SimpleUserInfo> sendUserList = Lists.newArrayList();
				for (SysUser user : userList) {
					sendUser = new SimpleUserInfo();
					sendUser.setUserId(user.getUserId());
					sendUser.setUserAccount(user.getAccount());
					sendUser.setUserName(user.getFullname());
					sendUser.setMobile(user.getMobile());
					sendUser.setEmail(user.getEmail());
					sendUserList.add(sendUser);
				}
				messageOrder.setSendUsers(sendUserList.toArray(new SimpleUserInfo[sendUserList
					.size()]));
				for (ChannalInfoDO info : channalList) {
					StringBuilder message = new StringBuilder();
					message.append("有渠道（渠道编号：");
					message.append(info.getChannelCode());
					message.append("，渠道名称：");
					message.append(info.getChannelName());
					message.append(" ）即将到达授信截止日，请及时处理 ");
					messageOrder.setMessageContent(message.toString());
					FcsBaseResult sendResult = siteMessageWebService.addMessageInfo(messageOrder);
					//4.发送成功后更改提醒状态
					if (sendResult.isSuccess()) {
						ChannalInfoDO channal = new ChannalInfoDO();
						channal.setId(info.getId());
						channal.setIsRemind(BooleanEnum.IS.code());
						channalInfoDAO.updateRemindStatus(channal);
					}
				}
				
			}
			
		}
		
	}
	
	/** 两个月后 */
	public static Date getNext2M() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.dtShort);
		Date tempDate = null;
		try {
			tempDate = DateUtil.shortstring2Date(sdf.format(new Date()));
			Calendar cad = Calendar.getInstance();
			cad.setTime(tempDate);
			cad.add(Calendar.MONTH, 2);
			return cad.getTime();
		} catch (ParseException e) {
			
		}
		return tempDate;
		
	}
}
