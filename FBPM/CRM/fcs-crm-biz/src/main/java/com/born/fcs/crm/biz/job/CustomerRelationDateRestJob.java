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
//import com.born.fcs.crm.dal.daointerface.CustomerRelationDAO;
//import com.born.fcs.crm.dal.dataobject.CustomerBaseInfoDO;
//import com.born.fcs.crm.dal.dataobject.CustomerRelationDO;
//import com.born.fcs.crm.integration.bpm.BpmUserQueryService;
//import com.born.fcs.pm.util.DateUtil;
//import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
//
//@Service("customerRelationDateRestJob")
//public class CustomerRelationDateRestJob extends JobBase implements ProcessJobService {
//	
//	//客户关系清理
//	@Autowired
//	private BpmUserQueryService bpmUserQueryService;
//	@Autowired
//	private CustomerRelationDAO customerRelationDAO;
//	@Autowired
//	private CustomerBaseInfoDAO customerBaseInfoDAO;
//	
//	private static long doCount = 1;
//	
//	@Scheduled(cron = "0 0/5 *  * * ? ")
//	@Override
//	public void doJob() throws Exception {
//		//		if (!isRun)
//		//			return;
//		if (doCount > 1) {
//			return;
//		}
//		CustomerRelationDO customerRelation = new CustomerRelationDO();
//		long count = customerRelationDAO.countWithCondition(customerRelation);
//		if (count == 0) {
//			doCount = 2;
//			resetData();
//		} else {
//			doCount = 2;
//		}
//	}
//	
//	//新移交分配功能加上后，处理历史数据，生成新的关系数据
//	private void resetData() {
//		logger.info("客户关系整理：第" + doCount + "次执行开始," + DateUtil.simpleFormat(new Date()));
//		CustomerBaseInfoDO customerBaseInfo = new CustomerBaseInfoDO();
//		List<CustomerBaseInfoDO> list = customerBaseInfoDAO.findWithCondition(customerBaseInfo, 0,
//			1000, null, null, null, null, null, null, 0, null, null);
//		UserDetailInfo userDetail = null;
//		for (CustomerBaseInfoDO info : list) {
//			try {
//				if (info.getCustomerManagerId() > 0) {
//					userDetail = new UserDetailInfo();
//					//有客户经理
//					userDetail = bpmUserQueryService.findUserDetailByUserId(info
//						.getCustomerManagerId());
//					CustomerRelationDO customerRelation = new CustomerRelationDO();
//					
//					customerRelation.setUserId(info.getUserId());
//					
//					customerRelation.setOperId(0);
//					customerRelation.setOperName("自动处理");
//					
//					customerRelation.setCustomerManager(userDetail.getName());
//					customerRelation.setCustomerManagerId(userDetail.getId());
//					customerRelation.setDepId(userDetail.getPrimaryOrg().getId());
//					customerRelation.setDepName(userDetail.getPrimaryOrg().getName());
//					customerRelation.setDepPath(userDetail.getPrimaryOrg().getPath());
//					
//					customerRelation.setDirector(info.getDirector());
//					customerRelation.setDirectorId(info.getDirectorId());
//					
//					customerRelationDAO.insert(customerRelation);
//					
//				} else if (info.getDirectorId() > 0) {
//					userDetail = new UserDetailInfo();
//					//有总监
//					userDetail = bpmUserQueryService.findUserDetailByUserId(info.getDirectorId());
//					
//					CustomerRelationDO customerRelation = new CustomerRelationDO();
//					
//					customerRelation.setUserId(info.getUserId());
//					
//					customerRelation.setOperId(0);
//					customerRelation.setOperName("自动处理");
//					
//					//				customerRelation.setCustomerManager(userDetail.getName());
//					//				customerRelation.setCustomerManagerId(userDetail.getId());
//					customerRelation.setDepId(userDetail.getPrimaryOrg().getId());
//					customerRelation.setDepName(userDetail.getPrimaryOrg().getName());
//					customerRelation.setDepPath(userDetail.getPrimaryOrg().getPath());
//					
//					customerRelation.setDirector(userDetail.getName());
//					customerRelation.setDirectorId(userDetail.getId());
//					
//					customerRelationDAO.insert(customerRelation);
//					
//				}
//			} catch (Exception e) {
//				logger.error("出现异常：", e);
//			}
//			
//		}
//		logger.info("客户关系整理：第" + doCount + "执行次结束" + DateUtil.simpleFormat(new Date()));
//	}
//}
