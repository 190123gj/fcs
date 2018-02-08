package com.born.fcs.pm.ws.result.common;

import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

public class CommonAttachmentResult extends FcsBaseResult {
	
	private static final long serialVersionUID = -8894712705496167696L;
	CommonAttachmentInfo attachmentInfo;
	
	public CommonAttachmentInfo getAttachmentInfo() {
		return this.attachmentInfo;
	}
	
	public void setAttachmentInfo(CommonAttachmentInfo attachmentInfo) {
		this.attachmentInfo = attachmentInfo;
	}
	
}
