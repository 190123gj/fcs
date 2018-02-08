package com.born.fcs.pm.biz.service.project.asyn;

import java.util.List;
import java.util.Map;

import com.born.fcs.pm.dal.dataobject.ProjectChannelRelationDO;
import com.born.fcs.pm.dal.dataobject.ProjectChannelRelationHiDO;

/**
 * 渠道信息更新时处理数据
 * */

public interface ProjectChannelDataSetService {
	/** 历史表数据 */
	boolean hisSet(ProjectChannelRelationHiDO his, Map<String, List<String>> dMap);
	
	/** 处理时时数据 */
	boolean set(ProjectChannelRelationDO relationDO, Map<String, List<String>> dMap);
}
