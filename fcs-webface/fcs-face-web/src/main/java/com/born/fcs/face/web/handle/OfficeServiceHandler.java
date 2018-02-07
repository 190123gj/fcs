package com.born.fcs.face.web.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;

public class OfficeServiceHandler {
	
	@ResponseBody
	public void clientHandler(HttpServletRequest request, HttpServletResponse response)
																						throws Exception {
		iWebOffice officeServer = new iWebOffice();
		byte[] iwebOfficeParam = (byte[]) request.getAttribute("iwebofficeParam");
		officeServer.ExecuteRun(request, response, iwebOfficeParam);
		//		  out.clear();                                                                    //用于解决JSP页面中“已经调用getOutputStream()”问题
		//		  out=pageContext.pushBody();                                                     //用于解决JSP页面中“已经调用getOutputStream()”问题
	}
	
}
