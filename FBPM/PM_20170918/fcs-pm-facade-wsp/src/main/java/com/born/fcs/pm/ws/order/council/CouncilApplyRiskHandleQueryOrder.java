package com.born.fcs.pm.ws.order.council;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;

/**
 * 项目上会申请 - 风险处置会/代偿项目上会
 * 
 * @author lirz
 * 
 * 2016-4-19 下午3:06:37
 */
public class CouncilApplyRiskHandleQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = 3355663868910490119L;
	
	private String isRepay; //是否代偿 (申报类别)
	private ProjectPhasesEnum phases; //项目阶段
	private String formStatus; //表单状态
	
	public String getIsRepay() {
		return isRepay;
	}
	
	public void setIsRepay(String isRepay) {
		this.isRepay = isRepay;
	}
	
	public ProjectPhasesEnum getPhases() {
		return phases;
	}

	public void setPhases(ProjectPhasesEnum phases) {
		this.phases = phases;
	}

	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
}
