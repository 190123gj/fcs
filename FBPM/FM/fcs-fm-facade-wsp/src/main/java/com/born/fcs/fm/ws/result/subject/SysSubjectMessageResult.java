/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:16:44 创建
 */
package com.born.fcs.fm.ws.result.subject;

import com.born.fcs.fm.ws.info.subject.SysSubjectMessageInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class SysSubjectMessageResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 1L;
	
	private SysSubjectMessageInfo subjectInfo;
	
	public SysSubjectMessageInfo getSubjectInfo() {
		return this.subjectInfo;
	}
	
	public void setSubjectInfo(SysSubjectMessageInfo subjectInfo) {
		this.subjectInfo = subjectInfo;
	}
	
}
