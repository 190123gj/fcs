package com.bornsoft.api.service.facade.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bornsoft.facade.api.common.CoreExportDataFacade;
import com.bornsoft.pub.order.common.CoreExportDataOrder;
import com.bornsoft.pub.result.common.CoreExportDataResult;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.utils.tool.InstallCommonResultUtil;
import com.bornsoft.utils.tool.PropertiesUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.util.StringUtils;

/**
 * 导出数据接口实现类
 * @Title: EzExportDataFacadeImpl.java 
 * @Package com.bornsoft.bornfinance.service.facade.impl.bankroll 
 * @author xiaohui@yiji.com   
 * @date 2014-12-17 下午5:26:49 
 * @version V1.0
 */
@Service("coreExportDataFacade")
public class CoreExportDataFacadeImpl implements CoreExportDataFacade{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static String tempPath = PropertiesUtil.getProperty("capitalpool.export.temp.dir");
	private static String savePath = PropertiesUtil.getProperty("capitalpool.export.save.dir");
	private static String viewPath = PropertiesUtil.getProperty("capitalpool.export.view.dir");


	/**
	 * 导出数据报表实现方法
	 * @param dataOrder 导出order 
	 * @return result 结果类
	 */
	@Override
	public CoreExportDataResult exportData(CoreExportDataOrder dataOrder) {
		CoreExportDataResult result = new CoreExportDataResult();
		result.setOrderNo(dataOrder.getOrderNo());
		try {
			logger.info("申请导出报表，入参order={}", dataOrder);

			//检查参数
			dataOrder.validateOrder();

			//复制相关模板
			String tempFilePath = copyTemplete(dataOrder);

			//查询数据并写入excel
			writeData(dataOrder, tempFilePath);

			//移动文件并删除临时文件
			String downloadPath = moveFileAndDelSource(tempFilePath);

			//拼接结果值
			result.setDownloadUrl(downloadPath);
			InstallCommonResultUtil.installDefaultSuccessResult(dataOrder, result);
		} catch (Exception ex) {
			logger.error("导出数据报表异常:", ex);
			InstallCommonResultUtil.installDefaultFailureResult(dataOrder, result ,ex);
		}
		logger.info("申请导出报表，出参result={}", result);
		return result;
	}

	/**
	 * 移动临时文件并删除临时文件
	 * @param tempFilePath
	 * @return 显示地址
	 * @throws Exception
	 */
	private String moveFileAndDelSource(String tempFilePath) throws Exception{
		File srcFile = null;
		try{
			srcFile = new File(tempFilePath);
			File destFile = new File(savePath + File.separator + srcFile.getName());
			FileUtils.copyFile(srcFile, destFile);
		}catch(Exception ex){
			logger.error("移动临时文件异常:", ex);
			throw new BornApiException("移动临时文件异常!");
		}

		try {
			if(! srcFile.delete()){
				logger.warn("删除临时文件 " + tempFilePath + " 异常!");
			}
		} catch (Exception ex) {
			logger.warn("删除临时文件 " + tempFilePath + " 异常!");
		}
		return viewPath + File.separator + srcFile.getName();
	}

	/**
	 * 查询数据并写入excel
	 * @param dataOrder
	 * @param tempFilePath
	 */
	private void writeData(CoreExportDataOrder dataOrder, String tempFilePath) throws Exception{
		List<?> list = null;
		String [] fields = null;
		writeExcel(list, fields, tempFilePath);
	}


	/**
	 * 写入excel
	 * @param dataList
	 * @param fields
	 * @param tempFilePath
	 * @throws Exception
	 */
	private void writeExcel(List<?> dataList, String [] fields, String tempFilePath) throws Exception{
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;   	//工作薄
		HSSFSheet sheet = null;  	//sheet
		HSSFRow row;     			//行
		HSSFCell cell;   			//列
		FileInputStream inputStream = null;
		FileOutputStream fos  = null;

		/**write excel**/
		try {
			inputStream = new FileInputStream(new File(tempFilePath));
			fs = new POIFSFileSystem(inputStream);
			wb = new HSSFWorkbook(fs);  //实例化工作簿对象
			sheet = wb.getSheetAt(0);   //获取第一个电子文件信息

			for (int i = 1; i <= dataList.size(); i++) {
				row = sheet.getRow(i);
				if(row == null){
					row = sheet.createRow(i);
				}
				int j = 0;
				//获取充值类型
				for(String fieldName : fields){
					cell = row.getCell(j);
					if(cell == null){
						cell = row.createCell(j);
					}
					if ("id".equals(fieldName)) {
						cell.setCellValue(new HSSFRichTextString(String.valueOf(i)));
					} else {
						cell.setCellValue(new HSSFRichTextString(getValue(fieldName, dataList.get(i - 1))));
					}
					j++;
				}
			}
		} catch (Exception ex) {
			logger.error("write数据至Excel中异常:", ex);
			throw new BornApiException("write数据至Excel中失败!");
		} finally {
			if(inputStream != null){
				inputStream.close();
			}
		}

		/**save excel**/
		try{
			fos  = new FileOutputStream(new File(tempFilePath));
		}catch(Exception ex){
			logger.error("save数据至Excel中异常:", ex);
			throw new Exception("save数据至Excel中异常!");
		}finally{
			if(wb != null)
				wb.write(fos);
			if(fos != null)
				fos.close();
		}
	}

	/**
	 * 复制模板至指定位置
	 * @param dataOrder
	 * @return 临时文件所在完整目录
	 */
	private String copyTemplete(CoreExportDataOrder dataOrder) throws Exception{
		/**模板名称**/
		String templeteName = dataOrder.getExportType() + "Templete.xls";

		/**临时文件名称**/
		String tempFileName = "";//dataOrder.getPartnerId() + "_" + dataOrder.getOrderNo() + ".xls";

		/**临时文件所在完整目录**/
		String tempFilePath = tempPath + File.separator + tempFileName;

		/**开始复制**/
		InputStream is  = null;
		FileOutputStream fos = null;
		try{
			is = this.getClass().getClassLoader().getResourceAsStream(templeteName);

			File destFile = new File(tempFilePath);

			if(destFile.exists()){	//存在
				destFile.delete();
			} else {			    //不存在
				destFile.getParentFile().mkdirs();
			}
			destFile.createNewFile();
			fos = new FileOutputStream(destFile);
			byte b[] = new byte[4 * 1024];
			int pos = -1 ;
			while((pos = is.read(b)) != -1){  
				fos.write(b, 0, pos);
			}
			fos.flush();
		}catch(Exception ex){
			logger.error("复制失败:", ex);
			throw new BornApiException("复制Excel模板出错!");
		}finally{
			if(fos != null){
				fos.close();
			}
			if(is != null){
				is.close();
			}
		}
		return tempFilePath;
	}


	/**
	 * 反射获取值
	 * @Title: getValue
	 * @Description: TODO
	 * @param colName
	 * @param importDataBean
	 * @return
	 * @throws Exception
	 */
	public String getValue(String colName, Object cl) throws Exception{
		String value = "";
		if (StringUtils.isNotEmpty(colName)) {
			Object obj = null;
			String methodName = "get" + colName.substring(0, 1).toUpperCase() + colName.substring(1);
			Method method = cl.getClass().getMethod(methodName);
			method.setAccessible(true);
			obj = method.invoke(cl);
			if (obj == null) {
				value = ""; 
			} else if (obj.getClass() == Date.class) {
				value = DateUtils.toDefaultString((Date) obj);
			} else if (obj.getClass() ==  Integer.class){
				value = String.valueOf(obj);
			} else if (obj.getClass() == Money.class) {
				value = ((Money) obj).toString();
			} else {
				value = (String) obj;
			}
		} 
		return value;
	}
}
