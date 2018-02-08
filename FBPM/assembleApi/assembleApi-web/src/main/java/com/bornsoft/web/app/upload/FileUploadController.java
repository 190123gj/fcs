package com.bornsoft.web.app.upload;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.util.AppConstantsUtil;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.web.app.base.BaseController;
import com.bornsoft.web.app.util.FileUploadUtils;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Controller
@RequestMapping("upload")
public class FileUploadController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(FileUploadController.class);
	private static List<String> fileTypeList = new ArrayList<String>();
	static {
		fileTypeList.add(".jpg");
		fileTypeList.add(".jpeg");
		fileTypeList.add(".bmp");
		fileTypeList.add(".png");
		fileTypeList.add(".pdf");
		fileTypeList.add(".docx");
		fileTypeList.add(".doc");
		fileTypeList.add(".xls");
		fileTypeList.add(".xlsx");
		fileTypeList.add(".apk");
		fileTypeList.add(".rar");
		fileTypeList.add(".zip");

		fileTypeList.add(".mp4");
		fileTypeList.add(".avi");
		fileTypeList.add(".3gp");
		fileTypeList.add(".wmv");
		fileTypeList.add(".mp3");
	};

	protected void validateFikeExsit(String fileName) {
		try {
			String urlString = null;
			if (StringUtil.isNotBlank(fileName)) {
				if (fileName.startsWith("/")) {
					urlString = AppConstantsUtil.getHostHttpUrl() + fileName;
				} else {
					urlString = fileName;
				}
			}
			URL url = new URL(urlString);
			URLConnection urlConnection = url.openConnection();
			if (urlConnection.getContentLengthLong() > 0) {
				logger.info("文件上传成功文件名={}，长度={}", urlString,
						urlConnection.getContentLengthLong());
			} else {
				logger.info("文件上传失败文件名={}，长度={}", urlString,
						urlConnection.getContentLengthLong());
			}
		} catch (Exception e) {
			logger.error("检验文件存在异常={}", e, e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "imagesUpload.json")
	public JSONObject imagesUpload(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws IOException {
		JSONObject result = null;
		try {
			String[] pathArray = null;
			File file = null;
			String oldFileName = "";
			// 上传图片
			ServletFileUpload fileUpload = new ServletFileUpload(
					new DiskFileItemFactory());
			fileUpload.setHeaderEncoding("utf-8");
			List<FileItem> fileList = null;
			fileList = fileUpload.parseRequest(request);
			Iterator<FileItem> it = fileList.iterator();
			String name = "";
			String extName = "";
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) {
					// 解析文件
					name = item.getName();
					oldFileName = name;
					if (name == null || name.trim().equals("")) {
						continue;
					}
					// 得到文件的扩展名
					if (name.lastIndexOf(".") >= 0) {
						extName = name.substring(name.lastIndexOf("."));
					}
					if (!fileTypeList.contains(extName.toLowerCase())) {
						return toJSONResult(AppResultCodeEnum.FAILED,
								"文件上传失败(文件类型不正确)！");
					}
					if (".pdf".equalsIgnoreCase(extName)) {
						pathArray = FileUploadUtils.getStaticFilesPdfPath(
								request, name);
					} else {
						pathArray = FileUploadUtils.getStaticFilesImgPath(
								request, name);
					}
					String savePath = pathArray[0];

					file = new File(savePath);
					item.write(file);
					// png压缩后图片失真？
					if (".jpg".equalsIgnoreCase(extName)
							|| ".bmp".equalsIgnoreCase(extName)) {
						boolean pass = this.compressPic(savePath, savePath);
						if (!pass) {
							return toJSONResult(AppResultCodeEnum.FAILED,
									"文件压缩异常");
						}
					}
				}
			}

			result = toJSONResult(AppResultCodeEnum.SUCCESS, "上传成功");
			JSONObject data = new JSONObject();
			data.put("msg", pathArray[1]);
			data.put("serverPath", pathArray[0]);
			data.put("url", pathArray[1]);
			data.put("oldFileName", oldFileName);
			data.put("fileName", file.getName());
			result.put("data", data);
			logger.info("出参={}",result);
		} catch (Exception e) {
			logger.error("上传附件失败");
			result = toJSONResult(AppResultCodeEnum.FAILED, e.getMessage());
		}
		return result;
	}

	public boolean compressPic(String srcFilePath, String descFilePath) {
		ImageIO.setUseCache(false);
		File file = null;
		BufferedImage src = null;
		FileOutputStream out = null;
		ImageWriter imgWrier;
		ImageWriteParam imgWriteParams;

		// 指定写图片的方式为 jpg
		imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
		imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
				null);
		// 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
		imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		// 这里指定压缩的程度，参数qality是取值0~1范围内，
		imgWriteParams.setCompressionQuality(1);
		imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
		ColorModel colorModel = ColorModel.getRGBdefault();
		// 指定压缩时使用的色彩模式
		imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
				colorModel, colorModel.createCompatibleSampleModel(16, 16)));

		try {
			if (StringUtil.isBlank(srcFilePath)) {
				return false;
			} else {
				file = new File(srcFilePath);
				src = ImageIO.read(file);
				out = new FileOutputStream(descFilePath);

				imgWrier.reset();
				// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
				// OutputStream构造
				imgWrier.setOutput(ImageIO.createImageOutputStream(out));
				// 调用write方法，就可以向输入流写图片
				imgWrier.write(null, new IIOImage(src, null, null),
						imgWriteParams);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
}
