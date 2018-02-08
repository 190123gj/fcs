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
//import com.born.fcs.crm.dal.daointerface.ChannalInfoDAO;
//import com.born.fcs.crm.dal.dataobject.ChannalInfoDO;
//import com.born.fcs.crm.ws.service.ChannalService;
//import com.born.fcs.crm.ws.service.ListDataSaveService;
//import com.born.fcs.crm.ws.service.info.ListDataInfo;
//import com.born.fcs.pm.util.DateUtil;
//import com.born.fcs.pm.ws.enums.BooleanEnum;
//import com.yjf.common.lang.util.StringUtil;
//import com.yjf.common.log.Logger;
//import com.yjf.common.log.LoggerFactory;
//
//@Service("channalDataRestJob")
//public class ChannalDataRestJob extends JobBase implements ProcessJobService {
//	protected final Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private ListDataSaveService listDataSaveService;
//	@Autowired
//	private ChannalInfoDAO channalInfoDAO;
//	@Autowired
//	private ChannalService channalService;
//	
//	@Scheduled(cron = "0 10,20,30,40,50 1,2,3,16,20,21,22,23 * * ? ")
//	@Override
//	public void doJob() throws Exception {
//		//		if (!isRun)
//		//			return;
//		logger.info("清理渠道编号开始： " + DateUtil.simpleFormat(new Date()));
//		
//		ChannalInfoDO channalInfo = new ChannalInfoDO();
//		channalInfo.setIsHistory(BooleanEnum.NO.code());
//		List<ChannalInfoDO> list = channalInfoDAO.findWithCondition(channalInfo, 0, 1000, null,
//			null, null);
//		for (ChannalInfoDO dos : list) {
//			if (StringUtil.isNotBlank(dos.getChannelCode()) && dos.getChannelCode().length() == 6) {
//				continue;
//			}
//			try {
//				channalInfo = new ChannalInfoDO();
//				channalInfo.setChannelCode(dos.getChannelCode());
//				List<ChannalInfoDO> sameList = channalInfoDAO.findWithCondition(channalInfo, 0,
//					1000, null, null, null);
//				String channalCode = channalService.createChannalCode();
//				for (ChannalInfoDO dos1 : sameList) {
//					String oldType = "channal" + dos1.getChannelCode() + dos1.getId();
//					String newType = "channal" + channalCode + dos1.getId();
//					dos1.setChannelCode(channalCode);
//					channalInfoDAO.updateById(dos1);
//					//联系人更新
//					try {
//						List<ListDataInfo> mobileList = listDataSaveService.list(oldType);
//						for (ListDataInfo infos : mobileList) {
//							infos.setDataType(newType);
//						}
//						listDataSaveService.updateByList(mobileList, 0, null);
//					} catch (Exception e) {
//						logger.error("更新渠道编号-修改联系人异常：", e);
//					}
//					
//				}
//			} catch (Exception e) {
//				logger.error("清洗渠道编号异常：", e);
//			}
//			
//		}
//		logger.info("清理渠道编号结束：" + DateUtil.simpleFormat(new Date()));
//		
//	}
//	
//}
