//package com.born.fcs.crm.biz.job;
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import com.born.fcs.crm.biz.job.inter.ProcessJobService;
//import com.born.fcs.crm.dal.daointerface.CustomerBaseInfoDAO;
//import com.born.fcs.crm.dal.daointerface.CustomerCompanyDetailDAO;
//import com.born.fcs.crm.dal.daointerface.CustomerPersonDetailDAO;
//import com.born.fcs.crm.dal.daointerface.CustomerRelationListDAO;
//import com.born.fcs.crm.dal.dataobject.CustomerBaseInfoDO;
//import com.born.fcs.crm.dal.dataobject.CustomerCompanyDetailDO;
//import com.born.fcs.crm.dal.dataobject.CustomerPersonDetailDO;
//import com.born.fcs.crm.dal.dataobject.CustomerRelationListDO;
//import com.born.fcs.crm.ws.service.order.query.CustomerQueryOrder;
//import com.born.fcs.pm.util.DateUtil;
//import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
//import com.yjf.common.lang.util.ListUtil;
//import com.yjf.common.lang.util.StringUtil;
//
//@Service("customerMobileSetJob")
//public class CustomerMobileSetJob extends JobBase implements ProcessJobService {
//	
//	@Autowired
//	private CustomerRelationListDAO customerRelationListDAO;
//	@Autowired
//	private CustomerBaseInfoDAO customerBaseInfoDAO;
//	@Autowired
//	private CustomerCompanyDetailDAO customerCompanyDetailDAO;
//	@Autowired
//	private CustomerPersonDetailDAO customerPersonDetailDAO;
//	
//	private static long doCount = 1;
//	
//	@Scheduled(cron = "0 0/5 *  * * ? ")
//	@Override
//	public void doJob() throws Exception {
//		
//		if (doCount > 1) {
//			return;
//		}
//		doCount = 2;
//		resetData();
//		
//	}
//	
//	//新移交分配功能加上后，处理历史数据，生成新的关系数据
//	private void resetData() {
//		logger.info("客户联系电话整理，执行开始：" + DateUtil.simpleFormat(new Date()));
//		CustomerQueryOrder queryOrder = new CustomerQueryOrder();
//		CustomerRelationListDO customerRelationListDO = new CustomerRelationListDO();
//		List<CustomerRelationListDO> list = customerRelationListDAO.findWithCondition(
//			customerRelationListDO, 0, 9999, queryOrder.getCustomerLevelList(),
//			queryOrder.getIndustryCodeList(), queryOrder.getCityCodeList(),
//			queryOrder.getBeginDate(), queryOrder.getEndDate(), queryOrder.getLikeCustomerName(),
//			queryOrder.getLoginUserId(), queryOrder.getDeptIdList(), queryOrder.getLikeNameOrId(),
//			"NO");
//		
//		if (ListUtil.isNotEmpty(list)) {
//			
//			for (CustomerBaseInfoDO info : list) {
//				boolean needUpdate = false;
//				try {
//					if (CustomerTypeEnum.ENTERPRISE.code().equals(info.getCustomerType())) {
//						CustomerCompanyDetailDO infos = customerCompanyDetailDAO
//							.findByCustomerId(info.getCustomerId());
//						if (infos != null && StringUtil.isNotBlank(infos.getContactNo())) {
//							needUpdate = true;
//							info.setContactMobile(infos.getContactNo());
//						}
//					} else {
//						CustomerPersonDetailDO infos = customerPersonDetailDAO
//							.findByCustomerId(info.getCustomerId());
//						if (infos != null && StringUtil.isNotBlank(infos.getMobile())) {
//							needUpdate = true;
//							info.setContactMobile(infos.getMobile());
//						}
//					}
//					if (needUpdate)
//						customerBaseInfoDAO.updateByUserId(info);
//				} catch (Exception e) {
//					logger.error("出现异常：", e);
//				}
//				
//			}
//		}
//		
//		logger.info("客户联系电话整理，执行结束：" + DateUtil.simpleFormat(new Date()));
//	}
//}
