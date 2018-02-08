package com.born.fcs.pm.ws.order.common;

import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChannelRelationEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;

/**
 * 项目渠道关系查询Order
 * @author wuzj
 */
public class ProjectChannelRelationQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 162001910470714322L;
	/** 主键 */
	private long id;
	/** 外部唯一 */
	private String bizNo;
	/** 阶段 */
	private ProjectPhasesEnum phases;
	/** 项目编号 */
	private String projectCode;
	/** 关系 */
	private ChannelRelationEnum channelRelation;
	/** 渠道ID */
	private long channelId;
	/** 渠道编码 */
	private String channelCode;
	/** 渠道名称 */
	private String channelName;
	/** 二级渠道ID */
	private long subChannelId;
	/** 二级渠道编码 */
	private String subChannelCode;
	/** 二级渠道名称 */
	private String subChannelName;
	/** 是否最新 YES/NO */
	private BooleanEnum latest;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public long getChannelId() {
		return this.channelId;
	}
	
	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}
	
	public String getChannelCode() {
		return this.channelCode;
	}
	
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getChannelName() {
		return this.channelName;
	}
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public long getSubChannelId() {
		return this.subChannelId;
	}
	
	public void setSubChannelId(long subChannelId) {
		this.subChannelId = subChannelId;
	}
	
	public String getSubChannelCode() {
		return this.subChannelCode;
	}
	
	public void setSubChannelCode(String subChannelCode) {
		this.subChannelCode = subChannelCode;
	}
	
	public String getSubChannelName() {
		return this.subChannelName;
	}
	
	public void setSubChannelName(String subChannelName) {
		this.subChannelName = subChannelName;
	}
	
	public BooleanEnum getLatest() {
		return this.latest;
	}
	
	public void setLatest(BooleanEnum latest) {
		this.latest = latest;
	}
}
