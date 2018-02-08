package com.born.fcs.pm.ws.order.file;

import com.born.fcs.pm.ws.enums.FileStatusEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

public class FileBatchApplyOrder extends ProcessOrder {

	private static final long serialVersionUID = 2624407093193079304L;

	private String fileIds;
	private String status = FileStatusEnum.INPUT.code();
	
	private long formId;
	
	@Override
	public void check() {
		super.check();
		if (formId <= 0) {
			validateHasText(fileIds, "档案ID");
			validateHasText(status, "档案状态");
		}
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}

}
