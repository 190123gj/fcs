package com.born.fcs.crm.biz.riskMessage;

import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.biz.service.customer.BaseAutowiredDAO;
import com.born.fcs.crm.integration.bpm.BpmUserQueryService;
import com.born.fcs.crm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.crm.integration.pm.service.SMSServiceClient;
import com.born.fcs.crm.ws.service.CustomerService;
import com.born.fcs.crm.ws.service.info.CustomerBaseInfo;
import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.info.sms.SMSInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SendMessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.bornsoft.pub.enums.UserTypeEnum;
import com.bornsoft.pub.interfaces.IRiskSystemService;
import com.bornsoft.pub.order.risk.RiskInfoRecOrder;
import com.bornsoft.pub.order.risk.RiskInfoRecOrder.RiskInfoEntity;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Service("iRiskSystemService")
public class RiskMessageService extends BaseAutowiredDAO implements IRiskSystemService {
	@Autowired
	CustomerService customerService;
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	private SMSServiceClient sMSServiceClient;
	
	@Override
	public BornSynResultBase recieveRiskInfo(RiskInfoRecOrder riskInfo) {
		BornSynResultBase result = new BornSynResultBase();
		logger.info("风险消息:{}", riskInfo);
		if (riskInfo != null && ListUtil.isNotEmpty(riskInfo.getList())) {
			for (RiskInfoEntity info : riskInfo.getList()) {
				if (StringUtil.isBlank(info.getLicenseNo()) && null != info.getUserType()) {
					logger.info("风险消息预警失败：客户信息不全：info={}，", info);
					continue;
				}
				CustomerQueryOrder queryOrder = new CustomerQueryOrder();
				if (UserTypeEnum.BUSINESS == info.getUserType()) {
					queryOrder.setBusiLicenseNo(info.getLicenseNo());
					queryOrder.setCustomerType(CustomerTypeEnum.ENTERPRISE.code());
				} else if (UserTypeEnum.PERSONAL == info.getUserType()) {
					queryOrder.setCertNo(info.getLicenseNo());
					queryOrder.setCustomerType(CustomerTypeEnum.PERSIONAL.code());
				}
				
				QueryBaseBatchResult<CustomerBaseInfo> customerQuery = customerService
					.list(queryOrder);
				if (customerQuery.isSuccess() && ListUtil.isNotEmpty(customerQuery.getPageList())) {
					//					CustomerBaseInfo customerInfo = customerQuery.getPageList().get(0);
					//					if (customerInfo == null || customerInfo.getCustomerManagerId() == 0) {
					//						logger.info("风险消息预警失败：未找到客户经理 ,info={}", info);
					//						continue;
					//					}
					//					
					//					if (user == null) {
					//						logger.info("风险消息预警失败：bpm中没找到客户经理 ,info={}", info);
					//						continue;
					//					}
					//3.发送消息
					MessageOrder messageOrder = SendMessageOrder.newSystemMessageOrder();
					
					messageOrder.setMessageTitle("风险系统提醒");
					
					boolean send = false;
					SimpleUserInfo sendUser = null;
					List<SimpleUserInfo> sendUserList = Lists.newArrayList();
					String mobileNumbers = "";
					for (CustomerBaseInfo infos : customerQuery.getPageList()) {
						if (infos.getCustomerManagerId() == 0) {
							continue;
						}
						SysUser user = bpmUserQueryService.findUserByUserId(infos
							.getCustomerManagerId());
						if (null == user) {
							logger.info("风险消息预警失败：bpm中没找到客户经理 ,info={},CustomerManagerId={}", info,
								infos.getCustomerManagerId());
						} else {
							sendUser = new SimpleUserInfo();
							sendUser.setUserId(user.getUserId());
							sendUser.setUserAccount(user.getAccount());
							sendUser.setUserName(user.getFullname());
							sendUser.setMobile(user.getMobile());
							sendUser.setEmail(user.getEmail());
							sendUserList.add(sendUser);
							send = true;
							if (StringUtil.isNotBlank(user.getMobile())) {
								if (StringUtil.isNotBlank(mobileNumbers))
									mobileNumbers += "," + user.getMobile();
								else
									mobileNumbers += user.getMobile();
							}
						}
						
					}
					if (send) {
						messageOrder.setSendUsers(sendUserList
							.toArray(new SimpleUserInfo[sendUserList.size()]));
						StringBuilder message = new StringBuilder();
						message.append("您关注的客户有新的动态消息了，<a href=");
						message.append(getUtl(info.getDetailUrl()));
						message.append(">查看动态详情");
						message.append("</a>");
						messageOrder.setMessageContent(message.toString());
						logger.info("风险消息开始调用pm接口推送：messageOrder={}", messageOrder);
						FcsBaseResult sendResult = siteMessageWebService
							.addMessageInfo(messageOrder);
						if (sendResult.isSuccess()) {
							SMSInfo smsResult = null;
							if (StringUtil.isNotBlank(mobileNumbers)) {
								smsResult = sMSServiceClient.sendSMSWithSwitch(mobileNumbers,
									message.toString(), false);
							}
							logger.info("风险消息推送成功后发送短信结果：mobileNumbers={},sms={},smsResult={}",
								mobileNumbers, message.toString(), smsResult);
							result.setResultMessage("消息发送成功");
							result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
						} else {
							result.setResultMessage(StringUtil.defaultIfBlank(
								result.getResultMessage(), "消息发送成功"));
							result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
						}
						
						logger.info("风险预警消息发送结果：sendResult={}", sendResult);
					} else {
						logger.info("风险消息推送：没找到客户经理");
						result.setResultMessage("消息发送失败：没找到客户经理");
						result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
					}
					
				} else {
					result.setResultMessage("消息发送失败：无此客户");
					result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
				}
				
			}
		}
		
		logger.info("风险消息发送结果：{}", result);
		return result;
	}
	
	private String getUtl(String url) {
		try {
			if (StringUtil.isNotBlank(url)) {
				String urls = "/riskMg/page/loginRiskSystem.htm?toUrl=";
				String eUrl = url.substring(url.indexOf("/#/"));
				return urls + URLEncoder.encode(eUrl);
			}
		} catch (Exception e) {
			logger.info("风险消息推送,处理url异常：url={}", url);
			
		}
		
		return url;
	}
}
