package com.bornsoft.utils.enums;

/**
 * @Description: Mime类型 
 * @author taibai@yiji.com
 * @date 2017-1-17 上午11:36:12
 * @version V1.0
 */
public enum MimeType {
	TextHtml("text/html", "HTML格式"),
	/**
	 * 纯文本格式
	 */
	TextPlain("text/plain", "纯文本格式"),
	TextXml("text/xml", "XML格式"), 
	ImageGif("image/gif", "gif图片格式"), 
	ImageJpeg("image/jpeg", "jpg图片格式"), 
	ImagePng("image/png", "png图片格式"),

	ApplicationXhtml("application/xhtml+xml", "XHTML格式"), 
	ApplicationXml("application/xml", "XML数据格式"), 
	ApplicationJson("application/json","JSON数据格式"), 
	ApplicationPdf("application/pdf", "pdf格式"), 
	ApplicationWord("application/msword", " Word文档格式"), 
	ApplicationStream("application/octet-stream", "二进制流数据（如常见的文件下载）"),
	/**
	 * 表单默认的提交数据的格式,form表单数据被编码为key/value格式发送到服务器
	 */
	ApplicationFormUrlEncoded("application/x-www-form-urlencoded","表单默认的提交数据格式"), 
	MultipartData("multipart/form-data", "表单默认的提交数据格式"),

	;
	private String code;
	private String message;

	MimeType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String code() {
		return code;
	}

	public String message() {
		return message;
	}
}
