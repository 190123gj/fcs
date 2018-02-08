package com.born.fcs.pm.ws.order.common;

import java.util.List;

import com.born.fcs.pm.ws.enums.ChannelRelationEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 项目渠道关系Order
 * @author wuzj
 */
public class ProjectChannelRelationBatchOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -857709641485895762L;
	
	/** 外部唯一 */
	private String bizNo;
	/** 阶段 */
	private ProjectPhasesEnum phases;
	/** 项目编号 */
	private String projectCode;
	/** 关系 */
	private ChannelRelationEnum channelRelation;
	/** 渠道 */
	private List<ProjectChannelRelationOrder> relations;
	
	@Override
	public void check() {
		validateNotNull(bizNo, "业务唯一主键");
		validateNotNull(phases, "项目阶段");
		validateNotNull(projectCode, "项目编号");
		validateNotNull(channelRelation, "渠道类型");
	}
	
	public String getBizNo() {
		return this.bizNo;
	}
	
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	
	public ProjectPhasesEnum getPhases() {
		return this.phases;
	}
	
	public void setPhases(ProjectPhasesEnum phases) {
		this.phases = phases;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public ChannelRelationEnum getChannelRelation() {
		return this.channelRelation;
	}
	
	public void setChannelRelation(ChannelRelationEnum channelRelation) {
		this.channelRelation = channelRelation;
	}
	
	public List<ProjectChannelRelationOrder> getRelations() {
		return this.relations;
	}
	
	public void setRelations(List<ProjectChannelRelationOrder> relations) {
		this.relations = relations;
	}
}
