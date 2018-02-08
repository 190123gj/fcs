package com.born.fcs.fm.biz.job;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.biz.job.inter.ProcessJobService;
import com.born.fcs.fm.biz.service.incomeconfirm.IncomeConfirmServiceImpl;
import com.born.fcs.pm.biz.service.common.AsynchronousService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("asynchronousTaskJob")
public class AsynchronousTaskJob implements ProcessJobService {
	static List<AsynchronousService> serviceList = new ArrayList<AsynchronousService>();
	static List<Object[]> paramList = new ArrayList<Object[]>();
	protected final Logger logger = LoggerFactory.getLogger(AsynchronousTaskJob.class);
	
	// 因为pm已经执行过这个job了，此处不执行
	
	@Override
	@Scheduled(cron = "0 0/1 * * * ? ")
	public void doJob() throws Exception {
		int count = serviceList.size();
		if (count > 0) {
			logger.info("asynchronousTaskJob count=" + count + " paramListCount="
						+ paramList.size() + " date=" + new Date().toString());
		}
		try {
			for (int i = count - 1; i >= 0; i--) {
				AsynchronousService service = serviceList.get(i);
				Object[] objects = paramList.get(i);
				try {
					logger.info("异步任务开始：开始执行类={},参数={}", service.getClass(),
						Arrays.toString(objects));
					service.execute(objects);
					logger.info("异步任务结束：完成执行类={},参数={}", service.getClass(),
						Arrays.toString(objects));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				synchronized (serviceList) {
					serviceList.remove(i);
					paramList.remove(i);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	@Override
	public void changeIsRun(boolean isRun) {
	}
	
	public void addAsynchronousService(AsynchronousService asynchronousService, Object[] objects) {
		logger.info("异步任务加入 asynchronousService=", asynchronousService);
		if (asynchronousService != null) {
			synchronized (serviceList) {
				if (asynchronousService instanceof IncomeConfirmServiceImpl) {
					serviceList.add(asynchronousService);
					paramList.add(objects);
				} else {
					if (!serviceList.contains(asynchronousService)) {
						
						serviceList.add(asynchronousService);
						paramList.add(objects);
					}
				}
				
			}
		}
		
	}
}
