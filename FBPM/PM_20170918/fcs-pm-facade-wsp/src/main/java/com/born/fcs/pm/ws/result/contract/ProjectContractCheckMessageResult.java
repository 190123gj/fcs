/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午4:17:48 创建
 */
package com.born.fcs.pm.ws.result.contract;

import java.util.ArrayList;
import java.util.List;

import com.born.fcs.pm.ws.info.contract.ProjectContractCheckInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractExtraValueModifyInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectContractCheckMessageResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 1L;
	
	private List<ProjectContractCheckInfo> allCheckMessage = new ArrayList<>();
	private List<ProjectContractCheckInfo> ownerCheckMessage = new ArrayList<>();
	private List<ProjectContractCheckInfo> othersCheckMessage = new ArrayList<>();
	
	private List<ProjectContractExtraValueModifyInfo> modifyInfos = new ArrayList<ProjectContractExtraValueModifyInfo>();
	
	public List<ProjectContractCheckInfo> getAllCheckMessage() {
		return this.allCheckMessage;
	}
	
	public void setAllCheckMessage(List<ProjectContractCheckInfo> allCheckMessage) {
		this.allCheckMessage = allCheckMessage;
	}
	
	public List<ProjectContractCheckInfo> getOwnerCheckMessage() {
		return this.ownerCheckMessage;
	}
	
	public void setOwnerCheckMessage(List<ProjectContractCheckInfo> ownerCheckMessage) {
		this.ownerCheckMessage = ownerCheckMessage;
	}
	
	public List<ProjectContractCheckInfo> getOthersCheckMessage() {
		return this.othersCheckMessage;
	}
	
	public List<ProjectContractExtraValueModifyInfo> getModifyInfos() {
		return this.modifyInfos;
	}
	
	public void setModifyInfos(List<ProjectContractExtraValueModifyInfo> modifyInfos) {
		this.modifyInfos = modifyInfos;
	}
	
	public void setOthersCheckMessage(List<ProjectContractCheckInfo> othersCheckMessage) {
		this.othersCheckMessage = othersCheckMessage;
	}
	
}
