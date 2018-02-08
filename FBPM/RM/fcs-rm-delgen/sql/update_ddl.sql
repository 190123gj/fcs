## 报表规则设置
CREATE TABLE `report_rule` (
  `report_id` BIGINT(10) NOT NULL AUTO_INCREMENT COMMENT '报表id',
  `report_name` VARCHAR(200) NOT NULL COMMENT '报表名称',
  `sql_str` VARCHAR(10000) NOT NULL COMMENT '报表SQL主体',
  `access_roles` VARCHAR(50) DEFAULT NULL COMMENT '可访问改报表的角色列表',
  `note` VARCHAR(1000) DEFAULT NULL COMMENT '报表内容备注',
  `filter1_name` VARCHAR(100) DEFAULT NULL COMMENT '查询条件1名称',
  `filter1_type` VARCHAR(50) DEFAULT NULL COMMENT '查询条件1页面录入值的数据类型',
  `filter1_sql` VARCHAR(500) DEFAULT NULL COMMENT '查询条件1对应的SQL',
  `filter1_options` VARCHAR(1000) DEFAULT NULL COMMENT '查询条件1下拉框选项值',
  `filter2_name` VARCHAR(100) DEFAULT NULL COMMENT '查询条件2名称',
  `filter2_type` VARCHAR(50) DEFAULT NULL COMMENT '查询条件2页面录入值的数据类型',
  `filter2_sql` VARCHAR(500) DEFAULT NULL COMMENT '查询条件2对应的SQL',
  `filter2_options` VARCHAR(1000) DEFAULT NULL COMMENT '查询条件2下拉框选项值',
  `filter3_name` VARCHAR(100) DEFAULT NULL COMMENT '查询条件3名称',
  `filter3_type` VARCHAR(50) DEFAULT NULL COMMENT '查询条件3页面录入值的数据类型',
  `filter3_sql` VARCHAR(500) DEFAULT NULL COMMENT '查询条件3对应的SQL',
  `filter3_options` VARCHAR(1000) DEFAULT NULL COMMENT '查询条件3下拉框选项值',
  `filter4_name` VARCHAR(100) DEFAULT NULL COMMENT '查询条件4名称',
  `filter4_type` VARCHAR(50) DEFAULT NULL COMMENT '查询条件4页面录入值的数据类型',
  `filter4_sql` VARCHAR(500) DEFAULT NULL COMMENT '查询条件4对应的SQL',
  `filter4_options` VARCHAR(1000) DEFAULT NULL COMMENT '查询条件4下拉框选项值',
  `filter5_name` VARCHAR(100) DEFAULT NULL COMMENT '查询条件5名称',
  `filter5_type` VARCHAR(50) DEFAULT NULL COMMENT '查询条件5页面录入值的数据类型',
  `filter5_sql` VARCHAR(500) DEFAULT NULL COMMENT '查询条件5对应的SQL',
  `filter5_options` VARCHAR(1000) DEFAULT NULL COMMENT '查询条件5下拉框选项值',
  `filter6_name` VARCHAR(100) DEFAULT NULL COMMENT '查询条件6名称',
  `filter6_type` VARCHAR(50) DEFAULT NULL COMMENT '查询条件6页面录入值的数据类型',
  `filter6_sql` VARCHAR(500) DEFAULT NULL COMMENT '查询条件6对应的SQL',
  `filter6_options` VARCHAR(1000) DEFAULT NULL COMMENT '查询条件6下拉框选项值',
  `sort_order` INT(11) NOT NULL DEFAULT '0' COMMENT '排列顺序',
  PRIMARY KEY (`report_id`)
) ENGINE=INNODB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8


#####################################################

 ##线上已经执行,进出口测试环境未执行
alter TABLE regular_project_base_info  
  ADD COLUMN customer_outer_level varchar(50) DEFAULT NULL COMMENT '外部评级' after customer_name;
  
ALTER TABLE regular_project_base_info_month
	ADD COLUMN customer_outer_level varchar(50) DEFAULT NULL COMMENT '外部评级' after customer_name;
	
ALTER TABLE regular_project_store_info
  ADD COLUMN council_type varchar(50) DEFAULT NULL COMMENT '会议类型' AFTER conucil_finish_date,
  ADD COLUMN full_sign varchar(10) DEFAULT NULL COMMENT '是否全签' AFTER council_type;	
  
ALTER TABLE regular_project_store_info_month
  ADD COLUMN council_type varchar(50) DEFAULT NULL COMMENT '会议类型' AFTER conucil_finish_date,
  ADD COLUMN full_sign varchar(10) DEFAULT NULL COMMENT '是否全签' AFTER council_type;
  
########################################	


ALTER TABLE regular_project_store_info
  ADD COLUMN contract_status VARCHAR(20) DEFAULT NULL COMMENT '合同发起情况（未发起、未全签、已全签）' AFTER full_sign,
  ADD COLUMN credit_status VARCHAR(20) DEFAULT NULL COMMENT '授信落实情况(未发起、未全部落实、全部落实)' AFTER contract_status;

ALTER TABLE regular_project_store_info_month
  ADD COLUMN contract_status VARCHAR(20) DEFAULT NULL COMMENT '合同发起情况（未发起、未全签、已全签）' AFTER full_sign,
  ADD COLUMN credit_status VARCHAR(20) DEFAULT NULL COMMENT '授信落实情况(未发起、未全部落实、全部落实)' AFTER contract_status;
  

ALTER TABLE regular_project_base_info
	ADD COLUMN busi_type_detail VARCHAR(200) DEFAULT NULL COMMENT '银行融资担保细分类明细' AFTER busi_type_name,
    ADD COLUMN project_channgel_type VARCHAR(50) DEFAULT NULL COMMENT '项目渠道类型' AFTER project_channel,
    ADD COLUMN capital_channel_type VARCHAR(200) DEFAULT NULL COMMENT '资金渠道类型（多个逗号隔开）' AFTER capital_channel,
    ADD COLUMN project_channel_code VARCHAR(20) DEFAULT NULL COMMENT '项目渠道编码' AFTER project_channel,
  	ADD COLUMN capital_channel_code VARCHAR(200) DEFAULT NULL COMMENT '资金渠道编码（多个逗号隔开）' AFTER capital_channel,
  	ADD COLUMN contract_amount BIGINT(20) DEFAULT NULL COMMENT '合同金额' AFTER amount;
	
ALTER TABLE regular_project_base_info_month
	ADD COLUMN busi_type_detail VARCHAR(200) DEFAULT NULL COMMENT '银行融资担保细分类明细' AFTER busi_type_name,
    ADD COLUMN project_channgel_type VARCHAR(50) DEFAULT NULL COMMENT '项目渠道类型' AFTER project_channel,
    ADD COLUMN capital_channel_type VARCHAR(200) DEFAULT NULL COMMENT '资金渠道类型（多个逗号隔开）' AFTER capital_channel,
    ADD COLUMN project_channel_code VARCHAR(20) DEFAULT NULL COMMENT '项目渠道编码' AFTER project_channel,
  	ADD COLUMN capital_channel_code VARCHAR(200) DEFAULT NULL COMMENT '资金渠道编码（多个逗号隔开）' AFTER capital_channel,
  	ADD COLUMN contract_amount BIGINT(20) DEFAULT NULL COMMENT '合同金额' AFTER amount;
  	
########################################  	

ALTER TABLE regular_project_channel_info
  ADD COLUMN customer_count_rate DECIMAL(10, 2) DEFAULT 0 COMMENT '多资金渠道时计数比例（客户维度-同一个客户同一个渠道值相同，存在多个项目明细上，统计时去重一下）,单资金渠道=1' AFTER count_rate;
  
ALTER TABLE regular_project_channel_info_month
  ADD COLUMN customer_count_rate DECIMAL(10, 2) DEFAULT 0 COMMENT '多资金渠道时计数比例（客户维度-同一个客户同一个渠道值相同，存在多个项目明细上，统计时去重一下）,单资金渠道=1' AFTER count_rate;
  

ALTER TABLE `regular_project_base_info`   
  ADD COLUMN `is_his_ptoject` VARCHAR(10) NULL  COMMENT '是否历史项目(是/否)' AFTER `release_amount`,
  ADD COLUMN is_belong2nc VARCHAR(10) DEFAULT NULL COMMENT '是否南川分公司项目' AFTER is_his_ptoject;
  
ALTER TABLE `regular_project_base_info_month`   
  ADD COLUMN `is_his_ptoject` VARCHAR(10) NULL  COMMENT '是否历史项目(是/否)' AFTER `release_amount`,
  ADD COLUMN is_belong2nc VARCHAR(10) DEFAULT NULL COMMENT '是否南川分公司项目' AFTER is_his_ptoject;
  
################################################  
  
ALTER TABLE regular_project_extra_list_info
  ADD COLUMN actual_occur_date DATE DEFAULT NULL COMMENT '实际业务时间' AFTER occur_date;  
  
ALTER TABLE regular_project_extra_list_info_month
  ADD COLUMN actual_occur_date DATE DEFAULT NULL COMMENT '实际业务时间' AFTER occur_date;  
  