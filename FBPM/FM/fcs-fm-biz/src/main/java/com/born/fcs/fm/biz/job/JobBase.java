package com.born.fcs.fm.biz.job;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import com.born.fcs.fm.biz.job.inter.ProcessJobService;
import com.born.fcs.pm.util.StringUtil;
import com.yjf.common.lang.ip.IPUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public abstract class JobBase implements InitializingBean, ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected static boolean isRun = false;
	
	@Value("${fcs.fm.task.timer.runIp}")
	protected String runIpString;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtil.isNotEmpty(runIpString)) {
			String[] notRunIpArray = runIpString.split(";");
			for (int i = 0; i < notRunIpArray.length; i++) {
				if (getHasIpAddress(notRunIpArray[i])) {
					isRun = true;
				}
			}
		}
	}
	
	@Override
	public void changeIsRun(boolean isRun) {
		logger.info("修改定时任务机制,修改后机制为：{}", isRun);
		JobBase.isRun = isRun;
	}
	
	public boolean getHasIpAddress(String strIpAddress) {
		Collection<InetAddress> inetAddresses;
		inetAddresses = IPUtil.getAllHostIPV4Address();
		Iterator<InetAddress> it = inetAddresses.iterator();
		while (it.hasNext()) {
			InetAddress inetAddress = it.next();
			
			if (strIpAddress.equals(inetAddress.getHostAddress())) {
				return true;
				
			}
			logger.info("networkInterface:" + inetAddress.getHostName() + ":"
						+ inetAddress.getHostAddress() + ":");
		}
		
		return false;
	}
}
