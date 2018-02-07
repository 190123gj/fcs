package com.born.fcs.face.web.controller.asset.assesscompany;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.am.ws.info.assesscompany.AssetsAssessCompanyInfo;
import com.born.fcs.am.ws.order.assesscompany.AssetsAssessCompanyOrder;
import com.born.fcs.am.ws.order.assesscompany.AssetsAssessCompanyQueryOrder;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Controller
@RequestMapping("assetMg/assessCompany")
public class AssetsAssessCompanyController extends BaseController {
	
	final static String vm_path = "/assetMg/assessmentManage/";
	
	/**
	 * 评估公司列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(AssetsAssessCompanyQueryOrder order, Model model) {
		
		QueryBaseBatchResult<AssetsAssessCompanyInfo> batchResult = assetsAssessCompanyServiceClient
			.query(order);
		
		List<AssetsAssessCompanyInfo> listCompanyInfo = batchResult.getPageList();
		for (AssetsAssessCompanyInfo info : listCompanyInfo) {
			String temp = "";
			if (info.getQualityLand() != null) {
				temp += "土地（" + info.getQualityLand() + "）;";
			}
			if (info.getQualityHouse() != null) {
				temp += "房产（" + info.getQualityHouse() + "）;";
			}
			if (info.getQualityAssets() != null) {
				temp += "资产（" + info.getQualityAssets() + "）;";
			}
			temp = temp.substring(0, temp.length() - 1);
			info.setQualityAssets(temp);
		}
		
		List<String> cities = assetsAssessCompanyServiceClient.findCities();
		model.addAttribute("cities", cities);
		//是否系统管理员
		boolean isAdmin = DataPermissionUtil.isSystemAdministrator();
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 去新增评估公司
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("toAdd.htm")
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
		return vm_path + "addCompany.vm";
	}
	
	/**
	 * 保存评估公司
	 *
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(AssetsAssessCompanyOrder order, Model model) {
		String tipPrefix = " 新增评估公司";
		JSONObject result = new JSONObject();
		try {
			// 初始化Form验证信息
			//			order.setRegisteredCapital(order.getRegisteredCapital().multiply(10000));//转化成万元
			String cityCode = order.getCityCode();
			String cityName = order.getCity();
			if ("市辖区".equals(order.getCity()) || "县".equals(order.getCity())
				|| "请选择".equals(order.getCity())) {
				order.setCityCode(order.getProvinceCode());
				order.setCity(order.getProvinceName());
				order.setProvinceCode(cityCode);
				order.setProvinceName(cityName);
			}
			setSessionLocalInfo2Order(order);
			FcsBaseResult saveResult = assetsAssessCompanyServiceClient.save(order);
			if (saveResult.isSuccess()) {
				result.put("success", true);
				result.put("message", "保存成功");
			} else {
				result.put("success", false);
				result.put("message", saveResult.getMessage());
			}
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 查看评估公司
	 *
	 * @param companyId
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(Long id, Model model) {
		String tipPrefix = "查看评估公司信息";
		try {
			AssetsAssessCompanyInfo assessCompanyInfo = assetsAssessCompanyServiceClient
				.findById(id);
			String cityCode = assessCompanyInfo.getCityCode();
			String cityName = assessCompanyInfo.getCity();
			if ("市辖区".equals(assessCompanyInfo.getProvinceName())
				|| "县".equals(assessCompanyInfo.getProvinceName())
				|| "请选择".equals(assessCompanyInfo.getProvinceName())) {
				assessCompanyInfo.setCityCode(assessCompanyInfo.getProvinceCode());
				assessCompanyInfo.setCity(assessCompanyInfo.getProvinceName());
				assessCompanyInfo.setProvinceCode(cityCode);
				assessCompanyInfo.setProvinceName(cityName);
			}
			model.addAttribute("assessCompanyInfo", assessCompanyInfo);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "viewCompany.vm";
	}
	
	/**
	 * 编辑评估公司
	 *
	 * @param companyId
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(Long id, Model model) {
		String tipPrefix = "编辑评估公司信息";
		try {
			AssetsAssessCompanyInfo assessCompanyInfo = assetsAssessCompanyServiceClient
				.findById(id);
			String cityCode = assessCompanyInfo.getCityCode();
			String cityName = assessCompanyInfo.getCity();
			if ("市辖区".equals(assessCompanyInfo.getProvinceName())
				|| "县".equals(assessCompanyInfo.getProvinceName())
				|| "请选择".equals(assessCompanyInfo.getProvinceName())) {
				assessCompanyInfo.setCityCode(assessCompanyInfo.getProvinceCode());
				assessCompanyInfo.setCity(assessCompanyInfo.getProvinceName());
				assessCompanyInfo.setProvinceCode(cityCode);
				assessCompanyInfo.setProvinceName(cityName);
			}
			model.addAttribute("assessCompanyInfo", assessCompanyInfo);
			model.addAttribute("isEdit", "true");//是否编辑
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "addCompany.vm";
	}
	
	/**
	 * 删除
	 *
	 * @param companyId
	 * @param model
	 * @return
	 */
	@RequestMapping("delete.htm")
	@ResponseBody
	public JSONObject delete(Long id, Model model) {
		String tipPrefix = "删除评估公司数据";
		JSONObject result = new JSONObject();
		AssetsAssessCompanyOrder order = new AssetsAssessCompanyOrder();
		order.setId(id);
		setSessionLocalInfo2Order(order);
		try {
			FcsBaseResult delResult = assetsAssessCompanyServiceClient.deleteById(order);
			if (delResult.isSuccess()) {
				result.put("success", true);
				result.put("message", "删除成功");
			} else {
				result.put("success", false);
				result.put("message", delResult.getMessage());
			}
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		return result;
	}
	
	/**
	 * 检查评估公司名称是否重复
	 *
	 * @param companyName
	 * @param model
	 * @return
	 */
	@RequestMapping("checkCompanyName.htm")
	@ResponseBody
	public JSONObject checkCompanyName(String companyName, Model model) {
		String tipPrefix = "评估公司名称是否重复";
		JSONObject result = new JSONObject();
		try {
			long count = assetsAssessCompanyServiceClient.findByCompanyNameCount(companyName);
			if (count > 0) {
				result.put("success", true);
				result.put("message", "评估公司名称已存在");
				
			} else {
				result.put("success", false);
				result.put("message", "评估公司名称可用");
			}
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		return result;
	}
}
