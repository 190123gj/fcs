package com.born.fcs.pm.biz.service.project.asyn;

import java.util.Date;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 项目关联渠道信息数据备份
 * */
public interface ProjectChannelAsynService {
	
	/**
	 * 每天晚上批量处理数据
	 * @return
	 */
	FcsBaseResult makeProjectChannelHisByDay(Date now);
	
	/**
	 * 项目有修改的时候更新渠道信息
	 * 
	 * 这里只更新新增/在保的客户和笔数
	 * */
	FcsBaseResult asyChannelData(String projectCode);
}
