package com.bornsoft.api.service.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bornsoft.facade.api.report.OutApiRequestLogFacade;
import com.bornsoft.facade.info.OutApiRequestLogInfo;
import com.bornsoft.facade.info.OutApiRequestLogReportInfo;
import com.bornsoft.facade.info.SmsInfo;
import com.bornsoft.facade.order.OutApiRequestLogQueryOrder;
import com.bornsoft.facade.order.SmsSendQueryOrder;
import com.bornsoft.facade.result.OutApiRequestLogQueryResult;
import com.bornsoft.facade.result.OutApiRequestLogReportResult;
import com.bornsoft.facade.result.SmsSendQueryResult;
import com.bornsoft.jck.dal.daointerface.ApixVeryLogDAO;
import com.bornsoft.jck.dal.daointerface.SmsSendRecordDAO;
import com.bornsoft.jck.dal.dataobject.ApixVeryLogDO;
import com.bornsoft.jck.dal.dataobject.ApixVeryLogReport;
import com.bornsoft.jck.dal.dataobject.SmsSendRecordDO;
import com.bornsoft.pub.enums.ApixServiceEnum;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.bornsoft.utils.tool.InstallCommonResultUtil;

@Service("outApiRequestLogFacadeImplApi")
public class OutApiRequestLogFacadeImpl implements OutApiRequestLogFacade{
	@Autowired
	private ApixVeryLogDAO apixVeryLogDAO;
	@Autowired
	private SmsSendRecordDAO smsSendRecordDAO;

	@Override
	public OutApiRequestLogQueryResult queryOutApiInvokeLogList(
			OutApiRequestLogQueryOrder order) {
		OutApiRequestLogQueryResult result = new OutApiRequestLogQueryResult();
		int totalCount = apixVeryLogDAO.countApiLogForPage(order.getServiceName(), order.getStartTime(), order.getEndTime());
		
		List<OutApiRequestLogInfo> dataList = new ArrayList<>();
		if(totalCount>0){
			List<ApixVeryLogDO>  list = apixVeryLogDAO.queryApiLogForPage(order.getServiceName(), order.getStartTime(), order.getEndTime(),order.getPageNum(),order.getPageSize());
			for(ApixVeryLogDO daoInfo : list){
				OutApiRequestLogInfo logInfo = new OutApiRequestLogInfo();
				logInfo.setLogId(daoInfo.getLogId());
				logInfo.setReqParam(daoInfo.getReqParam());
				logInfo.setResultCode(daoInfo.getVeryCode());
				logInfo.setResultMessage(daoInfo.getVeryMsg());
				logInfo.setRowAddTime(daoInfo.getRowAddTime());
				logInfo.setService(ApixServiceEnum.getByCode(daoInfo.getServiceName()));
				logInfo.setOrderNo(daoInfo.getOrderNo());
				dataList.add(logInfo);
			}
		}
		result.setPageNum(order.getPageNum());
		result.setPageSize(order.getPageSize());
		result.setDataList(dataList);
		result.calculate(totalCount, order.getPageSize());
		InstallCommonResultUtil.installCommonResult("查询成功",CommonResultEnum.EXECUTE_SUCCESS, result, order);
		return result;
	}
	
	@Override
	public OutApiRequestLogReportResult outApiInvokeLogReport(
			OutApiRequestLogQueryOrder order) {
		OutApiRequestLogReportResult result = new OutApiRequestLogReportResult();
		List<OutApiRequestLogReportInfo> dataList = new ArrayList<>();
		List<ApixVeryLogReport>  list = apixVeryLogDAO.queryApiLogReport(order.getServiceName(), order.getStartTime(), order.getEndTime());
		for(ApixVeryLogReport daoInfo : list){
			OutApiRequestLogReportInfo logInfo = new OutApiRequestLogReportInfo();
			logInfo.setCount(daoInfo.getCount());
			logInfo.setService(ApixServiceEnum.getByCode(daoInfo.getServiceName()));
			dataList.add(logInfo);
		}
		result.setDataList(dataList);
		InstallCommonResultUtil.installCommonResult("查询成功",CommonResultEnum.EXECUTE_SUCCESS, result, order);
		return result;
	}
	
	@Override
	public SmsSendQueryResult smsSendQueryPage(
			SmsSendQueryOrder order) {
		SmsSendQueryResult result = new SmsSendQueryResult();
		int totalCount = smsSendRecordDAO.smsSendRecordCount(order.getMobile(), order.getStartTime(), order.getEndTime());
		
		List<SmsInfo> dataList = new ArrayList<>();
		if(totalCount>0){
			List<SmsSendRecordDO>  list = smsSendRecordDAO.smsSendRecordQuery(order.getMobile(), order.getStartTime(), order.getEndTime(), order.getPageSize(), order.getPageNum());
			for(SmsSendRecordDO daoInfo : list){
				SmsInfo logInfo = new SmsInfo();
				logInfo.setChannel(daoInfo.getChannel());
				logInfo.setContent(daoInfo.getContent());
				logInfo.setMobile(daoInfo.getMobile());
				logInfo.setRid(daoInfo.getRid());
				logInfo.setRowAddTime(daoInfo.getRowAddTime());
				logInfo.setStatus(daoInfo.getStatus());
				dataList.add(logInfo);
			}
		}
		result.setPageNum(order.getPageNum());
		result.setPageSize(order.getPageSize());
		result.setDataList(dataList);
		result.calculate(totalCount, order.getPageSize());
		InstallCommonResultUtil.installCommonResult("查询成功",CommonResultEnum.EXECUTE_SUCCESS, result, order);
		return result;
	}
	
}
