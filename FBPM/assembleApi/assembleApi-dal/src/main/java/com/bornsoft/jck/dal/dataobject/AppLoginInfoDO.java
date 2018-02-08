package com.bornsoft.jck.dal.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 *                       
 * @create by CodeMaker
 *
 * @Description 自动生成DO
 *
 * @Version 1.0
 *
 * @Author jlcon
 *
 * @Email jianglu@yiji.com
 *       
 * @History
 *<li>Author: jlcon</li>
 *<li>Date: </li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class AppLoginInfoDO extends AppLoginInfoDOAbstract implements Serializable {

	private static final long	serialVersionUID		= 328652747372654460L;
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}