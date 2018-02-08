package com.born.fcs.pm.biz.job;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.biz.service.common.AsynchronousService;
import com.born.fcs.pm.biz.service.project.asyn.ProjectChannelAsynService;
import com.born.fcs.pm.biz.service.project.asyn.ProjectDataAsynchronousService;
import com.born.fcs.pm.dal.common.ProjectDataModifyCache;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("asynchronousTaskJob")
public class AsynchronousTaskJob implements ProcessJobService {
	static List<AsynchronousService> serviceList = new ArrayList<AsynchronousService>();
	static List<Object[]> paramList = new ArrayList<Object[]>();
	protected final Logger logger = LoggerFactory.getLogger(AsynchronousTaskJob.class);
	
	@Autowired
	ProjectDataAsynchronousService projectDataAsynchronousService;
	
	@Autowired
	ProjectChannelAsynService projectChannelAsynService;
	
	@Override
	@Scheduled(cron = "0 0/1 * * * ? ")
	public void doJob() throws Exception {
		int count = serviceList.size();
		if (count > 0) {
			logger.info("asynchronousTaskJob " + new Date().toString());
		}
		for (int i = count - 1; i >= 0; i--) {
			AsynchronousService service = serviceList.get(i);
			Object[] objects = paramList.get(i);
			try {
				logger.info("异步任务开始：开始执行类={},参数={}", service.getClass(), Arrays.toString(objects));
				service.execute(objects);
				logger.info("异步任务结束：完成执行类={},参数={}", service.getClass(), Arrays.toString(objects));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			synchronized (serviceList) {
				serviceList.remove(i);
				paramList.remove(i);
			}
		}
		Map<String, Long> temp = ProjectDataModifyCache.getModifyMap();
		if (temp != null && temp.size() > 0) {
			Iterator<Map.Entry<String, Long>> iterator = temp.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Long> entry = iterator.next();
				String projectCode = entry.getKey();
				Long time = entry.getValue();
				if (time.longValue() < 50) {
					FcsBaseResult baseResult = projectDataAsynchronousService
						.makeProjectData(projectCode);
					if (baseResult.isSuccess()) {
						ProjectDataModifyCache.commitModify(projectCode);
						projectChannelAsynService.asyChannelData(projectCode);
					} else {
						ProjectDataModifyCache.addModifyTime(projectCode);
					}
				} else {
					logger.error("项目编码={},数据同步失败，同步次数超过50", projectCode);
					ProjectDataModifyCache.commitModify(projectCode);
				}
				
			}
		}
	}
	
	@Override
	public void changeIsRun(boolean isRun) {
	}
	
	public void addAsynchronousService(AsynchronousService asynchronousService, Object[] objects) {
		if (asynchronousService != null) {
			synchronized (serviceList) {
				serviceList.add(asynchronousService);
				paramList.add(objects);
			}
		}
	}
}
