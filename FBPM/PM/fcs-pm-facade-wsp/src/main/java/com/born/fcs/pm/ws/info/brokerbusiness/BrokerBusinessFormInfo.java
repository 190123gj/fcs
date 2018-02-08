package com.born.fcs.pm.ws.info.brokerbusiness;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormChangeApplyStatusEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;

/**
 * 经纪业务信息
 * 
 * @author wuzj
 */
public class BrokerBusinessFormInfo extends FormVOInfo {
	
	private static final long serialVersionUID = -8323129401423621012L;
	/** 主键 */
	private long id;
	/** 项目编号 */
	private String projectCode;
	/** 客户名称 */
	private String customerName;
	/** 摘要 */
	private String summary;
	/** 合同地址 */
	private String contractUrl;
	/** 是否需要上会 */
	private BooleanEnum isNeedCouncil;
	/** 签报单/经纪业务状态 */
	private FormChangeApplyStatusEnum status;
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public BooleanEnum getIsNeedCouncil() {
		return isNeedCouncil;
	}
	
	public void setIsNeedCouncil(BooleanEnum isNeedCouncil) {
		this.isNeedCouncil = isNeedCouncil;
	}
	
	public FormChangeApplyStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(FormChangeApplyStatusEnum status) {
		this.status = status;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public String getContractUrl() {
		return contractUrl;
	}
	
	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}
	
}
