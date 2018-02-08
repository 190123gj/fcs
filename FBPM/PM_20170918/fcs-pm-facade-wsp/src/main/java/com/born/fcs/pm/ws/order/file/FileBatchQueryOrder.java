package com.born.fcs.pm.ws.order.file;


public class FileBatchQueryOrder extends FileQueryOrder {

	private static final long serialVersionUID = -1990882803032805405L;

	private String projectCodes;

	private String fileCodes;

	private String customerNames;

	public String getProjectCodes() {
		return projectCodes;
	}

	public void setProjectCodes(String projectCodes) {
		this.projectCodes = projectCodes;
	}

	public String getFileCodes() {
		return fileCodes;
	}

	public void setFileCodes(String fileCodes) {
		this.fileCodes = fileCodes;
	}

	public String getCustomerNames() {
		return customerNames;
	}

	public void setCustomerNames(String customerNames) {
		this.customerNames = customerNames;
	}

}
