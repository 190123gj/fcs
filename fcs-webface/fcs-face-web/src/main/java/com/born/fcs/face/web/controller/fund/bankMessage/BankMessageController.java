/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:43:04 创建
 */
package com.born.fcs.face.web.controller.fund.bankMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.ExcelData;
import com.born.fcs.face.web.util.ExcelExportUtil;
import com.born.fcs.face.web.util.ExcelUtil;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.fm.ws.enums.SubjectAccountTypeEnum;
import com.born.fcs.fm.ws.enums.SubjectStatusEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.order.bank.BankMessageBatchOrder;
import com.born.fcs.fm.ws.order.bank.BankMessageOrder;
import com.born.fcs.fm.ws.order.bank.BankMessageQueryOrder;
import com.born.fcs.fm.ws.result.bank.BankMessageResult;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 银行账户信息维护
 * 
 * @author hjiajie
 * 
 */
@Controller
@RequestMapping("fundMg/bankMessage")
public class BankMessageController extends BaseController {
	final static String vm_path = "/fundMg/fundMgModule/basisDataManage/";
	
	/**
	 * 列表查询
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(BankMessageQueryOrder order, Model model) {
		if (StringUtil.isEmpty(order.getSortCol())) {
			order.setSortCol("raw_add_time");
			order.setSortOrder("DESC");
		}
		QueryBaseBatchResult<BankMessageInfo> batchResult = bankMessageServiceClient
			.queryBankMessageInfo(order);
		model.addAttribute("conditions", order);
		model.addAttribute("statusList", FormStatusEnum.getAllEnum());
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return vm_path + "list.vm";
	}
	
	/**
	 * 列表查询后导出
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("excelDownLoad.htm")
	public String excelDownLoad(HttpServletRequest request, HttpServletResponse response,
								BankMessageQueryOrder order, Model model) {
		order.setPageSize(9999);
		QueryBaseBatchResult<BankMessageInfo> batchResult = bankMessageServiceClient
			.queryBankMessageInfo(order);
		List<BankMessageInfo> infos = batchResult.getPageList();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if (ListUtil.isNotEmpty(infos)) {
			for (BankMessageInfo info : infos) {
				Map<String, Object> map = new HashMap<>();
				// 转换为模版需要的map
				map = MiscUtil.covertPoToMap(info);
				if (info.getAccountType() != null) {
					
					map.put("accountType", info.getAccountType().getMessage());
				}
				if (info.getStatus() != null) {
					
					map.put("status", info.getStatus().getMessage());
				}
				
				if (info.getDefaultCompanyAccount() != null) {
					
					map.put("defaultCompanyAccount", info.getDefaultCompanyAccount().getMessage());
				}
				
				if (info.getDefaultPersonalAccount() != null) {
					
					map.put("defaultPersonalAccount", info.getDefaultPersonalAccount().getMessage());
				}
				
				if (info.getDepositAccount() != null) {
					
					map.put("depositAccount", info.getDepositAccount().getMessage());
				}
				
				dataList.add(map);
			}
		}
		ExcelExportUtil.exportExcelFile(request, "bankMessage.xls", response, dataList,
			"bankMessageDownload");
		return null;
	}
	
	/**
	 * excel导入
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("excelParse.htm")
	@ResponseBody
	public JSONObject excelParse(HttpServletRequest request, HttpServletResponse response,
									Model model) {
		String tipPrefix = "excel导入";
		JSONObject json = new JSONObject();
		try {
			ExcelData excelData = ExcelUtil.uploadExcel(request);
			// 抓取数据生成order并存库
			int columns = excelData.getColumns(); //列数
			int rows = excelData.getRows(); //行数
			
			String[][] datas = excelData.getDatas(); //数据
			List<BankMessageOrder> bankOrders = new ArrayList<BankMessageOrder>(); // order
			//循环得到每一行的数据
			for (int i = 0; i < rows; i++) {
				// 第一行代表 表头，忽略
				if (i == 0) {
					continue;
				}
				// 每一行
				String[] rowData = datas[i];
				BankMessageOrder bankOrder = new BankMessageOrder();
				// 循环列，生成info
				boolean defaultCompanyAccountSet = false;
				boolean defaultPersonalAccountSet = false;
				for (int j = 0; j < columns; j++) {
					String str = rowData[j];
					
					if (j == 0) {
						// 第0行为 银行简称
						bankOrder.setBankCode(str);
					} else if (j == 1) {
						// 第1行为 开户银行
						bankOrder.setBankName(str);
					} else if (j == 2) {
						// 第2行为 资金类型
						if ("活期".equals(str)) {
							bankOrder.setAccountType(SubjectAccountTypeEnum.CURRENT);
						} else if ("定期".equals(str)) {
							bankOrder.setAccountType(SubjectAccountTypeEnum.REGULAR);
						} else if ("结构性存款".equals(str)) {
							bankOrder.setAccountType(SubjectAccountTypeEnum.STRUCTURAL);
						} else {
							json.put("success", false);
							json.put("message", "资金类型不匹配!");
							return json;
						}
					} else if (j == 3) {
						// 第3行为 账户号码
						bankOrder.setAccountNo(str);
					} else if (j == 4) {
						// 第4行为 户名
						bankOrder.setAccountName(str);
					} else if (j == 5) {
						// 第5行为 会计科目代码
						bankOrder.setAtCode(str);
					} else if (j == 6) {
						// 第6行为 会计科目名称
						bankOrder.setAtName(str);
					} else if (j == 7) {
						// 第7行为 账户余额
						bankOrder.setAmount(new Money(str));
					}
					//					else if (j == 8) {
					//						// 第8行为 保证金账号代码
					//						bankOrder.setCashDepositCode(str);
					//					}
					else if (j == 8) {
						// 第9行为 账户状态
						if ("正常".equals(str) || "NORMAL".equals(str)) {
							bankOrder.setStatus(SubjectStatusEnum.NORMAL);
						} else if ("停用".equals(str) || "BLOCK_UP".equals(str)) {
							bankOrder.setStatus(SubjectStatusEnum.BLOCK_UP);
						} else {
							//bankOrder.setStatus(SubjectStatusEnum.BLOCK_UP);
							json.put("success", false);
							json.put("message", "不存在的账户状态值!");
							return json;
						}
					} else if (j == 9) {
						// 第10行为 部门/公司编号 --> id 用户查不到，切换为部门名称
						
						//						bankOrder.setDeptId(new Long(str));
						// 
						Org org = bpmUserQueryService.findDeptByCode(str);
						if (org == null) {
							json.put("success", false);
							json.put("message", "不存在的部门!");
							return json;
						}
						bankOrder.setDeptId(org.getId());
						bankOrder.setDeptName(str);
					} else if (j == 10) {
						// 第十一，代表是否默认对公支付账户
						if ("是".equals(str)) {
							bankOrder.setDefaultCompanyAccount(BooleanEnum.YES);
							// 添加唯一判定
							if (defaultCompanyAccountSet) {
								// 若已添加过，提示无法添加多个
								json.put("success", false);
								json.put("message", "默认对公支付账户不能有多个!");
								return json;
							} else {
								defaultCompanyAccountSet = true;
							}
						} else if ("否".equals(str)) {
							bankOrder.setDefaultCompanyAccount(BooleanEnum.NO);
						} else {
							json.put("success", false);
							json.put("message", "是否默认对公支付账户值不正确!");
							return json;
						}
					} else if (j == 11) {
						// 第12，代表是否默认对私支付账户
						if ("是".equals(str)) {
							bankOrder.setDefaultPersonalAccount(BooleanEnum.YES);
							// 添加唯一判定
							if (defaultPersonalAccountSet) {
								// 若已添加过，提示无法添加多个
								json.put("success", false);
								json.put("message", "默认对私支付账户不能有多个!");
								return json;
							} else {
								defaultPersonalAccountSet = true;
							}
						} else if ("否".equals(str)) {
							bankOrder.setDefaultPersonalAccount(BooleanEnum.NO);
						} else {
							json.put("success", false);
							json.put("message", "是否默认对私支付账户不正确!");
							return json;
						}
					} else if (j == 12) {
						// 第13，代表是否保证金账户 
						if ("是".equals(str)) {
							bankOrder.setDepositAccount(BooleanEnum.YES);
						} else if ("否".equals(str)) {
							bankOrder.setDepositAccount(BooleanEnum.NO);
						} else {
							json.put("success", false);
							json.put("message", "是否保证金存出账户值不正确!");
							return json;
						}
					}
					
				}
				// 添加到order集合
				bankOrders.add(bankOrder);
				
			}
			//  调用群更新方法
			BankMessageBatchOrder order = new BankMessageBatchOrder();
			setSessionLocalInfo2Order(order);
			order.setOrders(bankOrders);
			FcsBaseResult saveResult = bankMessageServiceClient.saveAll(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			json.put("success", false);
			json.put("message", "上传异常" + e.getMessage());
		}
		
		return json;
	}
	
	/**
	 * 进入新增
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toSave.htm")
	public String toSave(HttpServletRequest request, Model model) {
		
		return vm_path + "applyAdd.vm";
	}
	
	/**
	 * 查看
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("view.htm")
	public String view(long id, HttpServletRequest request, Model model) {
		BankMessageResult result = bankMessageServiceClient.findById(id);
		if (result.isSuccess() && result.isExecuted()) {
			model.addAttribute("bankMessage", result.getBankMessageInfo());
		}
		return vm_path + "applyAdd.vm";
	}
	
	/**
	 * 编辑
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("edit.htm")
	public String edit(long bankId, HttpServletRequest request, Model model) {
		BankMessageResult result = bankMessageServiceClient.findById(bankId);
		if (result.isSuccess() && result.isExecuted()) {
			model.addAttribute("bankMessage", result.getBankMessageInfo());
			model.addAttribute("used", result.getUsed());
		}
		// 添加判断，银行账户id是否在收付款单据的子单据中存在，若存在，大部分信息不允许修改
		model.addAttribute("modify", "modify");
		return vm_path + "applyAdd.vm";
	}
	
	/**
	 * 保存
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("save.htm")
	@ResponseBody
	public JSONObject save(BankMessageOrder order, HttpServletRequest request, Model model) {
		String tipPrefix = "保存银行信息";
		JSONObject result = new JSONObject();
		try {
			
			FcsBaseResult saveResult = bankMessageServiceClient.save(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		return result;
	}
	
	/**
	 * 更改状态
	 * @param formId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("changeStatus.htm")
	@ResponseBody
	public JSONObject changeStatus(BankMessageOrder order, HttpServletRequest request, Model model) {
		String tipPrefix = "更改银行状态";
		JSONObject result = new JSONObject();
		try {
			if (order == null || order.getBankId() <= 0 || order.getStatus() == null) {
				result.put("success", false);
				result.put("message", tipPrefix + "出错[请求参数不能为空！]");
				return result;
			}
			FcsBaseResult saveResult = bankMessageServiceClient.updateStatus(order.getBankId(),
				order.getStatus());
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		return result;
	}
}
