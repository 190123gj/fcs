/**
 * 
 */
package com.born.fcs.pm.ws.result.contract;

import java.util.List;

import com.born.fcs.pm.ws.info.contract.ProjectContractExtraValueInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * @author jiajie
 * 
 */
public class ProjectContractExtraValueInfoResult extends FcsBaseResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ProjectContractExtraValueInfo> projectContractExtraValue;

	public List<ProjectContractExtraValueInfo> getProjectContractExtraValue() {
		return projectContractExtraValue;
	}

	public void setProjectContractExtraValue(
			List<ProjectContractExtraValueInfo> projectContractExtraValue) {
		this.projectContractExtraValue = projectContractExtraValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectContractExtraValueInfoResult [projectContractExtraValue=");
		builder.append(projectContractExtraValue);
		builder.append("]");
		return builder.toString();
	}

}
