package com.born.fcs.pm.ws.service.report;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.report.ProjectInfoBaseInfo;
import com.born.fcs.pm.ws.service.report.order.ProjectBaseInfoQueryOrder;

/**
 * 
 * 报表系统 - 基层定义报表 - 项目基本情况表
 *
 * @author lirz
 * 
 * 2017-2-9 下午3:41:07
 *
 */
@WebService
public interface ProjectBaseInfoService {

    /**
     * 基层定义报表 - 项目基本情况表
     * @param queryOrder
     * @return
     */
    ProjectInfoBaseInfo queryProjectBaseInfo(ProjectBaseInfoQueryOrder queryOrder);

}
