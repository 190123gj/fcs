package com.born.fcs.pm.test.council;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.pm.biz.service.project.asyn.ProjectChannelAsynService;
import com.born.fcs.pm.biz.service.project.asyn.ProjectDataAsynchronousService;
import com.born.fcs.pm.test.SeviceTestBase;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.field.FcsFunctionEnum;
import com.born.fcs.pm.ws.service.report.order.LoadReportProjectOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;
import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;

public class ProjectReportServiceTest extends SeviceTestBase {
	@Autowired
	PmReportService reportService;
	@Autowired
	ProjectDataAsynchronousService projectDataAsynchronousService;
	
	//	id	主键
	//	project_id	主键
	//	project_code	项目编号
	//	project_name	项目名称
	//	customer_id	对应客户ID
	//	customer_name	对应客户名称
	//	customer_type	客户类型
	//	enterprise_type	客户性质、企业性质
	//	customer_level	客户等级
	//	scale	客户规模
	//	province_code	客户地区省
	//	province_name	客户地区省
	//	city_code	客户地区市
	//	city_name	客户地区市
	//	county_code	客户地区编码
	//	county_name	客户地区
	//	busi_type	业务类型
	//	busi_type_name	业务类型名称
	//	industry_code	所属行业
	//	industry_name	行业名称
	//	capital_channel_id	资金渠道一级
	//	capital_channel_name	资金渠道一级
	//	channal_type	渠道类型
	//	capital_sub_channel_name	资金渠道二级
	//	time_limit	期限
	//	time_unit	期限单位
	//	start_time	开始时间
	//	end_time	结束时间
	//	amount	金额
	//	accumulated_issue_amount	累计已发行金额(承销/发债信息维护)
	//	loaned_amount	已放款金额
	//	used_amount	已用款金额
	//	repayed_amount	已还款金额
	//	charged_amount	已收费金额
	//	refund_amount	已退费金额
	//	is_maximum_amount	是否最高额授信
	//	released_amount	已解保金额
	//	releasable_amount	总的可解保金额
	//	releasing_amount	解保中的金额
	//	customer_deposit_amount	客户保证金
	//	self_deposit_amount	自家保证金
	//	comp_principal_amount	已代偿本金
	//	comp_interest_amount	已代偿利息
	//	guarantee_amount	担保费
	//	guarantee_fee_rate	担保费率
	//	blance	再保余额
	//	contract_no	合同编号
	//	contract_time	合同签订时间
	//	is_approval	项目是否已经批复
	//	is_approval_del	项目批复是否作废
	//	approval_time	会议纪要通过时间
	//	busi_manager_id	客户经理ID
	//	busi_manager_account	客户经理账号
	//	busi_manager_name	客户经理名称
	//	busi_managerb_id	客户经理B
	//	busi_managerb_account	客户经理B账号
	//	busi_managerb_name	客户经理B名称
	//	dept_id	所属部门ID
	//	dept_code	部门编号
	//	dept_name	所属部门名称
	//	dept_path	部门路径
	//	dept_path_name	部门路径名称
	//	phases	项目阶段
	//	phases_status	项目阶段状态
	//	status	项目状态(正常、暂缓、未成立、完成)
	//	is_continue	是否手动停止继续发售(承销/发债发售信息维护)
	//	is_recouncil	是否可复议
	//	last_recouncil_time	上次复议时间
	//	setup_date	立项时间
	//	set_up_year	立项时间-年
	//	set_up_month	立项时间-月
	//	set_up_day	立项时间-日
	//	apply_date	立项申请时间
	//	apply_date_year	立项申请时间-年
	//	apply_date_month	立项申请时间-月
	//	apply_date_day	立项申请时间-日
	//	survey_date	调查时间
	//	survey_date_year	调查时间-年
	//	survey_date_month	调查时间-月
	//	survey_date_day	调查时间-日
	//	on_will_date	上会时间
	//	on_will_date_year	上会时间-年
	//	on_will_date_month	上会时间-月
	//	on_will_date_day	上会时间-日
	//	is_on_will_pass	上会通过标记
	//	first_lending_date	首次放款时间
	//	raw_add_time	创建时间
	//	custom_amount1	自定义金额1
	//	custom_amount2	自定义金额2
	//	custom_amount3	自定义金额3
	//	custom_name1	自定义字符1
	//	custom_name2	自定义字符2
	//	custom_name3	自定义字符3
	//	raw_update_time	修改时间
	
	@Before
	public void init() {
		
	}
	
	@Test
	public void testReport() {
		LoadReportProjectOrder loadReportProjectOrder = new LoadReportProjectOrder();
		List<FcsField> fcsFields = Lists.newArrayList();
		fcsFields.add(new FcsField("channal_type", "渠道类型", null));
		fcsFields.add(new FcsField("id", "数量", FcsFunctionEnum.COUNT));
		fcsFields.add(new FcsField("customer_id", "数量", FcsFunctionEnum.COUNT_DISTINCT));
		fcsFields.add(new FcsField("blance", "再保余额", FcsFunctionEnum.SUM));
		loadReportProjectOrder.setFcsFields(fcsFields);
		
		ReportDataResult dataResult = reportService.getReporData(loadReportProjectOrder);
		if (ListUtil.isNotEmpty(dataResult.getDataList())) {
			for (DataListItem dataList : dataResult.getDataList())
				for (FcsField field : dataResult.getFcsFields()) {
					logger.info(field.getName() + "=" + dataList.getMap().get(field.getColName())
								+ " dataType=" + field.getDataTypeEnum());
				}
			logger.info("=======================================================================");
		}
	}
	
	@Test
	public void testMakeDateReport() {
		projectDataAsynchronousService.makeProjectDataByDay();
	}
	
	@Test
	public void testMakeDateReport1() {
		projectDataAsynchronousService.makeProjectData("2016-0100-122-023");
	}
	
	@Autowired
	ProjectChannelAsynService projectChannelAsynService;
	
	@Test
	public void tessss() {
		Date date1 = DateUtil.getStartTimeOfTheDate(DateUtil.parse("2016-10-31"));
		Date date2 = DateUtil.getStartTimeOfTheDate(DateUtil.parse("2016-11-30"));
		
		projectChannelAsynService.makeProjectChannelHisByDay(date1);
		projectChannelAsynService.makeProjectChannelHisByDay(date2);
		
	}
	
}
