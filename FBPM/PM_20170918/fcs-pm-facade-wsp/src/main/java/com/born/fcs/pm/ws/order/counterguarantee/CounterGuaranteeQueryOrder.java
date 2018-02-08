package com.born.fcs.pm.ws.order.counterguarantee;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;

/**
 * 到期解保查询
 * 
 * @author lirz
 *
 * 2016-5-12 下午6:29:50
 */
public class CounterGuaranteeQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -8238754989181224564L;
	
	private FormStatusEnum formStatus; // 表单状态
	private ProjectPhasesEnum phases; //项目阶段
	
	public FormStatusEnum getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(FormStatusEnum formStatus) {
		this.formStatus = formStatus;
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
}
