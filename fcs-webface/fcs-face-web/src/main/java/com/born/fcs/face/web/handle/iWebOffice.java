package com.born.fcs.face.web.handle;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kinggrid.demo.web.iMsgServer2000;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class iWebOffice {
	
	protected static final Logger logger = LoggerFactory.getLogger(iWebOffice.class);
	private int mFileSize;
	private byte[] mFileBody;
	private String mFileName;
	private String mFileType;
	private String mFileDate;
	private String mFileID;
	private String mRecordID;
	private String mTemplate;
	private String mDateTime;
	private String mOption;
	private String mMarkName;
	private String mPassword;
	private String mMarkList;
	private String mBookmark;
	private String mDescript;
	private String mHostName;
	private String mMarkGuid;
	private String mDirectory;
	private String mFilePath;
	private String mUserName;
	
	private String mMyDefine1;
	
	private String mTableContent;
	private String Sql;
	// 打印控制
	private String mOfficePrints;
	private int mCopies;
	// 自定义信息传递
	private String mInfo;
	private String mIndex;
	private String mTotal;
	private iMsgServer2000 MsgObj;
	
	//	private iDBManager2000 DbaObj;
	
	// ==============================↓文档、模板管理代码【开始】↓==============================
	// 调出文档，将文档内容保存在mFileBody里，以便进行打包
	//	private boolean LoadFile() {
	//		boolean mResult = false;
	//		String Sql = "SELECT FileBody,FileSize FROM Document_File WHERE RecordID='" + mRecordID + "'";
	//		try {
	//			if (DbaObj.OpenConnection()) {
	//				try {
	//					ResultSet result = DbaObj.ExecuteQuery(Sql);
	//					if (result.next()) {
	//						try {
	//							mFileBody = result.getBytes("FileBody");
	//							if (result.wasNull()) {
	//								mFileBody = null;
	//							}
	//							mResult = true;
	//						} catch (Exception ex) {
	//							System.out.println(ex.toString());
	//						}
	//					}
	//					result.close();
	//				} catch (SQLException e) {
	//					System.out.println(e.getMessage());
	//					mResult = false;
	//				}
	//			}
	//		} finally {
	//			DbaObj.CloseConnection();
	//		}
	//		return (mResult);
	//	}
	
	// 保存文档，如果文档存在，则覆盖，不存在，则添加
	//	private boolean SaveFile() {
	//		boolean mResult = false;
	//		int iFileId = -1;
	//		String Sql = "SELECT * FROM Document_File WHERE RecordID='" + mRecordID + "'";
	//		try {
	//			if (DbaObj.OpenConnection()) {
	//				try {
	//					ResultSet result = DbaObj.ExecuteQuery(Sql);
	//					if (result.next()) {
	//						Sql = "update Document_File set RecordID=?,FileName=?,FileType=?,FileSize=?,FileDate=?,FileBody=?,FilePath=?,UserName=?,Descript=? WHERE RecordID='"
	//								+ mRecordID + "'";
	//					} else {
	//						Sql = "insert into Document_File (RecordID,FileName,FileType,FileSize,FileDate,FileBody,FilePath,UserName,Descript) values (?,?,?,?,?,?,?,?,? )";
	//					}
	//					result.close();
	//				} catch (SQLException e) {
	//					System.out.println(e.toString());
	//					mResult = false;
	//				}
	//				java.sql.PreparedStatement prestmt = null;
	//				try {
	//					prestmt = DbaObj.Conn.prepareStatement(Sql);
	//					prestmt.setString(1, mRecordID);
	//					prestmt.setString(2, mFileName);
	//					prestmt.setString(3, mFileType);
	//					prestmt.setInt(4, mFileSize);
	//					prestmt.setString(5, mFileDate);
	//					prestmt.setBytes(6, mFileBody);
	//					prestmt.setString(7, mFilePath);
	//					prestmt.setString(8, mUserName);
	//					prestmt.setString(9, mDescript); // "通用版本"
	//					prestmt.execute();
	//					prestmt.close();
	//					mResult = true;
	//				} catch (SQLException e) {
	//					System.out.println(e.toString());
	//					mResult = false;
	//				}
	//			}
	//		} finally {
	//			DbaObj.CloseConnection();
	//		}
	//		return (mResult);
	//	}
	
	// ==============================↓接收流、写回流代码【开始】↓==============================
	// 取得客户端发来的数据包
	private byte[] ReadPackage(HttpServletRequest request) {
		byte mStream[] = null;
		int totalRead = 0;
		int readBytes = 0;
		int totalBytes = 0;
		try {
			totalBytes = request.getContentLength();
			mStream = new byte[totalBytes];
			while (totalRead < totalBytes) {
				request.getInputStream();
				readBytes = request.getInputStream().read(mStream, totalRead,
					totalBytes - totalRead);
				totalRead += readBytes;
				continue;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return (mStream);
	}
	
	// 发送处理后的数据包
	private void SendPackage(HttpServletResponse response) {
		try {
			ServletOutputStream OutBinarry = response.getOutputStream();
			OutBinarry.write(MsgObj.MsgVariant());
			OutBinarry.flush();
			OutBinarry.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	// ==============================↑接收流、写回流代码【结束】↑==============================
	
	public void ExecuteRun(HttpServletRequest request, HttpServletResponse response,
							byte[] iwebOfficeParam) {
		//		DbaObj = new DBstep.iDBManager2000(); // 创建数据库对象
		MsgObj = new iMsgServer2000(); // 创建信息包对象
		mOption = "";
		mRecordID = "";
		mTemplate = "";
		mFileBody = null;
		mFileName = "";
		mFileType = "";
		mFileSize = 0;
		mFileID = "";
		mDateTime = "";
		mMarkName = "";
		mPassword = "";
		mMarkList = "";
		mBookmark = "";
		mMarkGuid = "";
		mDescript = "";
		// mCommand = "";
		// mContent = "";
		// mLabelName = "";
		// mImageName = "";
		mTableContent = "";
		mMyDefine1 = "";
		mOfficePrints = "0";
		mIndex = "";
		mTotal = "";
		mFilePath = request.getSession().getServletContext().getRealPath(""); // 取得服务器路径
		
		logger.info("FilePath:" + mFilePath);
		logger.info("ReadPackage");
		
		try {
			if (request.getMethod().equalsIgnoreCase("POST")) {
				// MsgObj.MsgVariant(ReadPackage(request));
				// //老版本后台类，不支持UTF-8编码自适应功能
				MsgObj.Load(request, iwebOfficeParam); // 8.1.0.2版后台类新增解析接口，可支持UTF-8编码自适应功能
				
				if (MsgObj.GetMsgByName("DBSTEP").equalsIgnoreCase("DBSTEP")) { // 判断是否是合法的信息包，或者数据包信息是否完整
					mOption = MsgObj.GetMsgByName("OPTION"); // 取得操作信息
					mUserName = MsgObj.GetMsgByName("USERNAME"); // 取得系统用户
					logger.info("OPTION:" + mOption); // 打印出调试信息
					if (mOption.equalsIgnoreCase("LOADFILE")) { // 下面的代码为打开服务器数据库里的文件
						mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
						mFileName = MsgObj.GetMsgByName("FILENAME"); // 取得文档名称
						// System.out.println(mFileName);
						mFileType = MsgObj.GetMsgByName("FILETYPE"); // 取得文档类型
						MsgObj.MsgTextClear(); // 清除文本信息
						if (MsgObj.MsgFileLoad(mFilePath + "\\Document\\" + mFileName)) { // 从文件夹调入文档
							// if (LoadFile()) { //从数据库调入文档
							// MsgObj.MsgFileBody(mFileBody); //将文件信息打包
							System.out.println("加载文档中：" + mFilePath + "\\Document\\" + mFileName);
							MsgObj.SetMsgByName("STATUS", "打开成功!"); // 设置状态信息
							MsgObj.MsgError(""); // 清除错误信息
						} else {
							MsgObj.MsgError("打开失败!"); // 设置错误信息
						}
					}
					
					else if (mOption.equalsIgnoreCase("SAVEFILE")) { // 下面的代码为保存文件在服务器的数据库里
						mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
						logger.info("RecordID:" + mRecordID);
						mFileName = MsgObj.GetMsgByName("FILENAME"); // 取得文档名称
						// System.out.println("FileName:"+mFileName);
						mFileType = MsgObj.GetMsgByName("FILETYPE"); // 取得文档类型
						logger.info("FileType:" + mFileType);
						mUserName = mUserName; // 取得保存用户名称
						mDescript = "通用版本"; // 版本说明
						mFileSize = MsgObj.MsgFileSize(); // 取得文档大小
						//						mFileDate = DbaObj.GetDateTime(); // 取得文档时间
						mFileBody = MsgObj.MsgFileBody(); // 取得文档内容
						
						String isEmpty = MsgObj.GetMsgByName("EMPTY"); // 是否是空内容文档的标识
						if (isEmpty.equalsIgnoreCase("TRUE")) {
							// 此时接收的文档中内容是空白的，请用日志记录保存时间、保存用户、记录编号等信息，用于将来发现文档内容丢失时排查用。
							logger.info("保存路径：" + mFilePath + "\\Document\\" + mFileName);
						}
						
						MsgObj.MsgTextClear(); // 清除文本信息
						if (MsgObj.MsgFileSave(mFilePath + "\\Document\\" + mFileName)) { // 保存文档内容到文件夹中
							// if (SaveFile()) { //保存文档内容到数据库中
							MsgObj.SetMsgByName("STATUS", "保存成功!"); // 设置状态信息
							MsgObj.MsgError(""); // 清除错误信息
						} else {
							MsgObj.MsgError("保存失败!"); // 设置错误信息
						}
						MsgObj.MsgFileClear(); // 清除文档内容
					}
				} else {
					MsgObj.MsgError("客户端发送数据包错误!");
					MsgObj.MsgTextClear();
					MsgObj.MsgFileClear();
				}
			} else {
				MsgObj.MsgError("请使用Post方法");
				MsgObj.MsgTextClear();
				MsgObj.MsgFileClear();
			}
			logger.info("SendPackage");
			logger.info("");
			// SendPackage(response); //老版后台类返回信息包数据方法
			MsgObj.Send(response); // 8.1.0.2新版后台类新增的功能接口，返回信息包数据
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
