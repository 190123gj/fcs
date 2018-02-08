package com.born.fcs.pm.ws.service.report.order;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.base.QueryPageBase;
import com.yjf.common.lang.util.DateUtil;

/**
 * 渠道报表查询
 * */
public class ChannelReportQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 2998941974210813197L;
	/** 查询报表类型 */
	private String types;
	/** 查询截止时间 */
	private Date projectDate;
	/** 包含业务类型 精确 */
	private List<String> busiTypeList;
	/** 包含渠道 */
	private List<String> channelCodeList;
	/** 是否需要分页 */
	private boolean needPage;
	
	/** 是否查历史表 */
	public boolean isHis() {
		if (this.projectDate == null) {
			return false;
		} else if (this.projectDate.before(DateUtil.getStartTimeOfTheDate(new Date()))) {
			return true;
		}
		return false;
	}
	
	public boolean isNeedPage() {
		return this.needPage;
	}
	
	public void setNeedPage(boolean needPage) {
		this.needPage = needPage;
	}
	
	public String getTypes() {
		return this.types;
	}
	
	public void setTypes(String types) {
		this.types = types;
	}
	
	public Date getProjectDate() {
		return this.projectDate;
	}
	
	public void setProjectDate(Date projectDate) {
		this.projectDate = projectDate;
	}
	
	public List<String> getBusiTypeList() {
		return this.busiTypeList;
	}
	
	public void setBusiTypeList(List<String> busiTypeList) {
		this.busiTypeList = busiTypeList;
	}
	
	public List<String> getChannelCodeList() {
		return this.channelCodeList;
	}
	
	public void setChannelCodeList(List<String> channelCodeList) {
		this.channelCodeList = channelCodeList;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChannelReportQueryOrder [types=");
		builder.append(types);
		builder.append(", projectDate=");
		builder.append(projectDate);
		builder.append(", busiTypeList=");
		builder.append(busiTypeList);
		builder.append(", channelCodeList=");
		builder.append(channelCodeList);
		builder.append(", needPage=");
		builder.append(needPage);
		builder.append("]");
		return builder.toString();
	}
	
}
