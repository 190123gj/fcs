package com.born.fcs.rm.biz.service.report.inner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born.fcs.pm.util.MoneyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.report.ToReportInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.rm.integration.service.SysParameterServiceClient;
import com.born.fcs.rm.integration.service.pm.ToReportServiceClient;
import com.born.fcs.rm.ws.info.report.inner.ExpectReleaseInfo;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportProcessService;
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import com.yjf.common.lang.util.money.Money;

/**
 *
 * 内部报表<br />
 * 综合汇总表<br />
 * 项目推动情况汇总表
 *
 * @author heh
 *
 * 2016年9月1日 16:02:44
 */
@Service("projectProcessService")
public class ProjectProcessServiceImpl extends BaseAutowiredDomainService implements
																			ReportProcessService {
	
	@Autowired
	ToReportServiceClient toReportServiceClient;
	
	@Autowired
	SysParameterServiceClient sysParameterServiceClient;
	
	@Autowired
	SubmissionService submissionService;
	
	private Map<String, ToReportInfo> map = new HashMap<>();
	
	private final String cos[] = { "1、本月接触项目(个)", "2、本月接触项目金额(万元)", "3、本月受理项目(个)",
									"4、本月受理项目金额(万元)", "5、本月提交上会项目(个)", "6、本月提交上会项目金额(万元)",
									"1、本月上会项目(个)", "2、本月上会项目金额(万元)", "3、本月过会项目(个)",
									"4、本月过会项目金额(万元)", "5、累计已批未放款项目(个)", "(1)三月内已批未放项目(不含保本基金)(个)",
									"其中：网络金融平台项目(招财宝+网金社)(个)", "公募债项目(个)", "(2)三月以上已批未放项目(不含保本基金)(个)",
									"其中：网络金融平台项目(招财宝+网金社)(个)", "公募债项目(个)", "(3)保本基金担保(个)", "6、累计已批未放项目(万元)",
									"(1)三月内已批未放项目(万元)", "其中：网络金融平台项目(招财宝+网金社)(万元)", "公募债项目(万元)",
									"(2)三月以上已批未放项目(万元)", "其中：网络金融平台项目(招财宝+网金社)(万元)", "公募债项目(万元)", "(3)保本基金担保(万元)",
									"1、本月放款项目(个)", "其中:新增(个)", "续贷(个)", "2、本月放款金额(万元)", "其中:新增(万元)", "续贷(万元)" };
	private final String type[] = { "本月接触项目", "本月接触项目", "本月受理项目", "本月受理项目", "本月提交上会项目", "本月提交上会项目",
									"本月上会项目", "本月上会项目", "本月过会项目", "本月过会项目", "累计已批未放项目",
									"三个月内已批未放项目", "其中网络金融平台项目(三个月内)", "公募债项目(三个月以内)", "三个月以上批未放项目",
									"其中网络金融平台项目(三个月以上)", "公募债项目(三个月以上)", "保本基金担保", "累计已批未放项目",
									"三个月内已批未放项目", "其中网络金融平台项目(三个月内)", "公募债项目(三个月以内)", "三个月以上批未放项目",
									"其中网络金融平台项目(三个月以上)", "公募债项目(三个月以上)", "保本基金担保", "本月放款项目", "新增",
									"续贷", "本月放款项目", "新增", "续贷" };
    private  int total;

    private  double moenyTotal;
	
	@Override
	public FcsBaseResult save(ReportOrder order) {
		return null;
	}
	
	@Override
	public Object findByAccountPeriod(ReportQueryOrder queryOrder) {
		map=new HashMap<>();
		QueryBaseBatchResult<ExpectReleaseInfo> batchResult = new QueryBaseBatchResult<>();
		//获取上报部门
		String deptCodes = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_ANNUAL_OBJECTIVE_DEPT_CODE.code());
		int year = queryOrder.getReportYear();
		int month = queryOrder.getReportMonth();
		List<ExpectReleaseInfo> list = new ArrayList<>();
		int deptLength = deptCodes.split(",").length;
		//统计数据
		List<ToReportInfo> toReportInfos = toReportServiceClient.getProjectProcess(year, month);
		//格式化数据
		for (ToReportInfo reportInfo : toReportInfos) {
			map.put(reportInfo.getData3() + "_" + reportInfo.getData5(), reportInfo);//部门编号_备注
		}
		//初始化部门信息
		String codes[] = deptCodes.split(",");
		ExpectReleaseInfo info = new ExpectReleaseInfo();
		String depts[] = new String[deptLength + 2];
		depts[0] = "部门";
		int d = 1;
		for (String code : codes) {
			String result = submissionService.findDeptInfoByDeptCode(code);
			HashMap<String, Object> resultMap = MiscUtil.parseJSON(result);
			Map<String, Object> sysOrgMap = (Map<String, Object>) resultMap.get("sysOrg");
			depts[d] = String.valueOf(sysOrgMap.get("orgName"));
			d++;
		}
		depts[d] = "合计";
        info.setDatas(depts);
		list.add(info);
		for (int i = 0; i < cos.length; i++) {
             info=new ExpectReleaseInfo();
			String data[] = new String[deptLength + 2];
            total=0;
            moenyTotal=0.00;
			data[0] = cos[i];
			int j = 1;
			for (String code : codes) {
				data = setValue(data, code, i, j,"NO");
				j++;
			}
            data = setValue(data,"", i, j,"IS");
            info.setDatas(data);
			list.add(info);
		}
		//
		batchResult.setPageList(list);
		batchResult.setSuccess(true);
		return batchResult;
	}
	
	private String[] setValue(String data[], String code, int i, int j,String isTotal) {
		switch (i) {
			case 0:
			case 2:
			case 4:
			case 6:
			case 8:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:
			case 26:
			case 27:
			case 28:
                if("IS".equals(isTotal)){
                    data[j]=total+"";
                }else {
                    ToReportInfo info = map.get(code + "_" + type[i]);
                    if (info != null) {
                        data[j] = info.getData1();
                    } else {
                        data[j] = "0";
                    }
                    total =total+ Integer.parseInt(data[j]);
                }
				break;
			case 1:
			case 3:
			case 5:
			case 7:
			case 9:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
			case 24:
			case 25:
			case 29:
			case 30:
			case 31:
                if("IS".equals(isTotal)){
					if(moenyTotal==0){
						data[j]="0.00";
					}else {
						data[j] = moenyTotal + "";
					}
                }else {
                    ToReportInfo info2 = map.get(code + "_" + type[i]);
                    if (info2 != null) {
                        data[j] = info2.getData2();
                    } else {
                        data[j] = "0.00";
                    }
                    moenyTotal =moenyTotal + Double.parseDouble(data[j]);
                }
				break;
		}
		return data;
	}
	
}
