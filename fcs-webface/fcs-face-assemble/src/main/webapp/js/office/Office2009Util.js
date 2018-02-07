/**
 * 获取到显示word控件
 * @returns
 */
function getWebObject() {
	return document.getElementById("WebOffice");
}

/**
 * 远程加载文件[与后台发生交互]
 */
function loadWord() {
	getWebObject().WebOpen(true);
}

/**
 * 打开本地文件
 */
function loadLocalWord() {
	getWebObject().WebOpenLocal();
}

/**
 * 远程保存文件[与后台发生交互]
 */
function saveWord() {
	getWebObject().MaxSize=200*1024;
	if(!getWebObject().WebSave(true)){
		StatusMsg(getWebObject().Status);
		return false;
	}else{
		StatusMsg(getWebObject().Status);
		window.opener.top.location.hash = 'refresh';
		window.self.close();
		return true;
	}
}
function StatusMsg(mString){
	alert(mString);
}

/**
 * 保存文件至本地
 */
function saveLocalWord() {
	getWebObject().WebSaveLocal();
}

/**
 * 显示|隐藏痕迹
 * @param mValue
 */
function showRevision(mValue) {
	getWebObject().WebShow(mValue);
}

/**
 * 打印文档
 */
function printWord() {
	getWebObject().WebOpenPrint();
}

/**
 * 设置初始化信息
 * @param userName   操作的用户名
 * @param webUrl	 请求URL
 * @param fileName   文件名称
 * @param fileSuffix 文件后缀
 * @param reayOnly	 是否只读
 */
function setBaseParam(userName, webUrl, fileName, fileSuffix, readOnly) {
	var webObject = getWebObject();
	webObject.UserName = userName;
	webObject.WebUrl = webUrl;
	webObject.FileName = fileName;
	webObject.FileType = fileSuffix;
	webObject.ShowToolBar = 0;
	//	webObject.WebToolsVisible("Reviewing", false);
	//	webObject.WebToolsVisible("Track Changes", false);
	
	// 20170922 设置可以复制
	webObject.CopyType = true;

	
	if ("true" == readOnly) {
		// 0,0 代表只读不可复制，4,0代表只读可复制
		webObject.EditType = "4,0";
	} else {
		webObject.EditType = "-1,0,1,1,0,0,1,0"; //"-1,0,1,1,0,0,1,0";
	}
	this.loadWord();
}


/**
 * 保存文件至本地,设定为可修改
 */
function saveLocalWord2() {
	//1 设定为可修改
	var webObject2 = getWebObject();
		webObject2.EditType = "-1,0,1,1,0,0,1,0"; //"-1,0,1,1,0,0,1,0";
	//this.loadWord();
	// 下载
	getWebObject().WebSaveLocal();
	// 设定为不可修改  4，0代表 只读，可拷贝
	webObject2.EditType = "4,0";
	//this.loadWord();
	
}