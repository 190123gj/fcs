package com.born.fcs.pm.ws.info.common;

import java.util.ArrayList;
import java.util.List;

import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;

public class AttachmentModuleType {
	CommonAttachmentTypeEnum moduleType;
	List<CommonAttachmentInfo> attachmentInfos = new ArrayList<CommonAttachmentInfo>();
	
	public CommonAttachmentTypeEnum getModuleType() {
		return moduleType;
	}
	
	public void setModuleType(CommonAttachmentTypeEnum moduleType) {
		this.moduleType = moduleType;
	}
	
	public List<CommonAttachmentInfo> getAttachmentInfos() {
		return attachmentInfos;
	}
	
	public void setAttachmentInfos(List<CommonAttachmentInfo> attachmentInfos) {
		this.attachmentInfos = attachmentInfos;
	}
	
	public String getHiddenUrls() {
		if (null == this.attachmentInfos || this.attachmentInfos.size() <= 0) {
			return "";
		}
		StringBuilder urls = new StringBuilder();
		for (CommonAttachmentInfo attach : this.attachmentInfos) {
			urls.append(attach.getFileName()).append(",").append(attach.getFilePhysicalPath())
				.append(",").append(attach.getRequestPath()).append(";");
		}
		urls.deleteCharAt(urls.length() - 1);
		
		return urls.toString();
	}
	
	@Override
	public String toString() {
		return this.moduleType.code() + "-" + getHiddenUrls();
	}
}
