package com.bornsoft.utils.base;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.bornsoft.utils.exception.BornApiException;

public class BornInfoBase {
	
	public void validateOrder() throws BornApiException {
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
