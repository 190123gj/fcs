
/**新增表 2016-10-11*/ 
CREATE TABLE `change_list` (
   `change_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `customer_user_id` bigint(20) NOT NULL COMMENT '客户ID',
   `oper_name` varchar(200) NOT NULL COMMENT '修改人姓名',
   `oper_id` bigint(20) DEFAULT NULL COMMENT '修改人Id',
   `form_id` bigint(20) DEFAULT NULL COMMENT '表单Id',
   `change_type` varchar(200) NOT NULL COMMENT '修改来源',
   `data_type` varchar(200) DEFAULT NULL COMMENT '数据类型',
   `raw_add_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
   `raw_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`change_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='修改记录列表';
/**新增表 2016-10-11*/  
CREATE TABLE `change_detail` (
   `detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `change_id` bigint(20) NOT NULL COMMENT '修改记录Id',
   `lable_name` varchar(200) DEFAULT NULL COMMENT '字段名',
   `lable_key` varchar(200) DEFAULT NULL COMMENT '字段码',
   `old_value` varchar(2000) DEFAULT NULL COMMENT '旧值',
   `new_value` varchar(2000) DEFAULT NULL COMMENT '新值',
   `detail_type` varchar(100) DEFAULT NULL COMMENT '类型',
   PRIMARY KEY (`detail_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=384 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='修改详情记录';

 
CREATE TABLE `change_detail` (
  `detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `change_id` bigint(20) NOT NULL COMMENT '修改记录Id',
  `lable_name` varchar(200) DEFAULT NULL COMMENT '字段名',
  `lable_key` varchar(200) DEFAULT NULL COMMENT '字段码',
  `old_value` varchar(2000) DEFAULT NULL COMMENT '旧值',
  `new_value` varchar(2000) DEFAULT NULL COMMENT '新值',
  `detail_type` varchar(100) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='修改详情记录';


CREATE TABLE `change_list` (
  `change_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `customer_user_id` bigint(20) NOT NULL COMMENT '客户ID',
  `oper_name` varchar(200) NOT NULL COMMENT '修改人姓名',
  `oper_id` bigint(20) DEFAULT NULL COMMENT '修改人Id',
  `form_id` bigint(20) DEFAULT NULL COMMENT '表单Id',
  `change_type` varchar(200) NOT NULL COMMENT '修改来源',
  `data_type` varchar(200) DEFAULT NULL COMMENT '数据类型',
  `raw_add_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`change_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='修改记录列表';

CREATE TABLE `channal_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `channel_code` varchar(200) DEFAULT NULL COMMENT '渠道编号',
  `channel_name` varchar(200) NOT NULL COMMENT '渠道名称',
  `channel_type` varchar(50) DEFAULT NULL COMMENT '渠道分类',
  `institutional_attributes` varchar(50) DEFAULT NULL COMMENT '金融机构属性',
  `address` varchar(500) DEFAULT NULL COMMENT '联系地址',
  `contact_person1` varchar(50) DEFAULT NULL COMMENT '联系人一',
  `contact_mobile1` varchar(50) DEFAULT NULL COMMENT '联系人一电话',
  `contact_person2` varchar(50) DEFAULT NULL COMMENT '联系人二',
  `contact_mobile2` varchar(50) DEFAULT NULL COMMENT '联系人二电话',
  `leadings` varchar(200) DEFAULT NULL COMMENT '牵头行',
  `loss_allocation_rate` double(14,4) DEFAULT NULL COMMENT '损失分摊比例',
  `bond_rate` double(14,4) DEFAULT NULL COMMENT '保证金比例',
  `credit_amount` bigint(20) DEFAULT NULL COMMENT '授信额度',
  `credit_amount_used` bigint(20) DEFAULT NULL COMMENT '已用额度',
  `is_credit_amount` enum('IS','NO') DEFAULT 'NO' COMMENT '判断授信额度',
  `single_limit` bigint(20) DEFAULT NULL COMMENT '单笔限额',
  `is_single_limit` enum('IS','NO') DEFAULT 'NO' COMMENT '判断单笔限额',
  `times` double(14,4) DEFAULT NULL COMMENT '不超过净资产的几倍',
  `is_times` enum('IS','NO') DEFAULT 'NO' COMMENT '判断倍数',
  `percent` double(14,4) DEFAULT NULL COMMENT '不超过净资产的百分比',
  `is_percent` enum('IS','NO') DEFAULT 'NO' COMMENT '判断百分比',
  `credit_start_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '授信起始日',
  `credit_end_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '授信截止日',
  `compensatory_limit` int(11) DEFAULT NULL COMMENT '代偿期限',
  `day_type` varchar(20) DEFAULT NULL COMMENT '自然日,工作日',
  `straddle_year` enum('IS','NO') DEFAULT 'NO' COMMENT '是否跨年',
  `enclosure_url` varchar(1000) DEFAULT NULL COMMENT '附件链接',
  `status` varchar(20) DEFAULT NULL COMMENT '渠道状态',
  `input_person` varchar(200) NOT NULL COMMENT '录入人',
  `is_temporary` enum('IS','NO') DEFAULT 'NO' COMMENT '是否暂存数据',
  `is_remind` enum('IS','NO') DEFAULT 'NO' COMMENT '到期提醒',
  `is_history` enum('IS','NO') NOT NULL DEFAULT 'NO' COMMENT '是否历史数据',
  `raw_add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='渠道信息表';

/*Table structure for table `channal_info_history` */


CREATE TABLE `channal_info_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `channel_code` varchar(200) DEFAULT NULL COMMENT '渠道编号',
  `channel_name` varchar(200) DEFAULT NULL COMMENT '渠道名称',
  `channel_type` varchar(50) DEFAULT NULL COMMENT '渠道分类',
  `institutional_attributes` varchar(50) DEFAULT NULL COMMENT '金融机构属性',
  `address` varchar(500) DEFAULT NULL COMMENT '联系地址',
  `contact_person1` varchar(50) DEFAULT NULL COMMENT '联系人一',
  `contact_mobile1` varchar(50) DEFAULT NULL COMMENT '联系人一电话',
  `contact_person2` varchar(50) DEFAULT NULL COMMENT '联系人二',
  `contact_mobile2` varchar(50) DEFAULT NULL COMMENT '联系人二电话',
  `leadings` varchar(200) DEFAULT NULL COMMENT '牵头行',
  `loss_allocation_rate` double(14,4) DEFAULT NULL COMMENT '损失分摊比例',
  `bond_rate` double(14,4) DEFAULT NULL COMMENT '保证金比例',
  `credit_amount` bigint(20) DEFAULT NULL COMMENT '授信额度',
  `credit_amount_used` bigint(20) DEFAULT NULL COMMENT '已用额度',
  `is_credit_amount` enum('IS','NO') DEFAULT 'NO' COMMENT '判断授信额度',
  `single_limit` bigint(20) DEFAULT NULL COMMENT '单笔限额',
  `is_single_limit` enum('IS','NO') DEFAULT 'NO' COMMENT '判断单笔限额',
  `times` double(14,4) DEFAULT NULL COMMENT '不超过净资产的几倍',
  `is_times` enum('IS','NO') DEFAULT 'NO' COMMENT '判断倍数',
  `percent` double(14,4) DEFAULT NULL COMMENT '不超过净资产的百分比',
  `is_percent` enum('IS','NO') DEFAULT 'NO' COMMENT '判断百分比',
  `credit_start_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '授信起始日',
  `credit_end_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '授信截止日',
  `compensatory_limit` int(11) DEFAULT NULL COMMENT '代偿期限',
  `day_type` varchar(20) DEFAULT NULL COMMENT '自然日,工作日',
  `straddle_year` enum('IS','NO') DEFAULT 'NO' COMMENT '是否跨年',
  `enclosure_url` varchar(1000) DEFAULT NULL COMMENT '附件链接',
  `status` varchar(20) DEFAULT NULL COMMENT '渠道状态',
  `input_person` varchar(200) DEFAULT NULL COMMENT '录入人',
  `is_temporary` enum('IS','NO') DEFAULT 'NO' COMMENT '是否暂存数据',
  `raw_add_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='渠道信息表历史数据';

/*Table structure for table `company_ownership_structure` */



CREATE TABLE `company_ownership_structure` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `customer_id` varchar(50) NOT NULL COMMENT '查询Id',
  `perents` varchar(200) DEFAULT NULL COMMENT '所属公司',
  `shareholders_name` varchar(200) DEFAULT NULL COMMENT '主要股东名称',
  `amount` bigint(20) DEFAULT NULL COMMENT '出资额',
  `methord` varchar(200) DEFAULT NULL COMMENT '出资方式',
  `equity` double(14,3) DEFAULT NULL COMMENT '股权比例',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` varchar(200) DEFAULT NULL COMMENT '存储状态',
  `perent_id` bigint(20) DEFAULT '0' COMMENT '正式数据id',
  `child_id` bigint(20) DEFAULT '0' COMMENT '暂存数据',
  `is_temporary` enum('IS','NO') DEFAULT 'NO' COMMENT '是否暂存数据',
  `raw_add_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `NewIndex1` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='公司股权结构';

/*Table structure for table `company_qualification` */


CREATE TABLE `company_qualification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `customer_id` varchar(100) NOT NULL COMMENT '客户查询ID',
  `qualification_name` varchar(100) NOT NULL COMMENT '资质证书名',
  `qualification_code` varchar(100) DEFAULT NULL COMMENT '资质证书编号',
  `exper_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '证书有效期',
  `raw_add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='公司客户获得资质证书';

/*Table structure for table `customer_base_info` */


CREATE TABLE `customer_base_info` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `customer_id` varchar(50) NOT NULL COMMENT '客户Id',
  `customer_name` varchar(200) NOT NULL COMMENT '姓名/企业名称',
  `sex` enum('M','W','X') DEFAULT 'X' COMMENT '性别：M:男；W女；X：未知',
  `resours_from` varchar(200) DEFAULT NULL COMMENT '客户来源',
  `cert_type` varchar(200) DEFAULT NULL COMMENT '证件类型',
  `busi_license_no` varchar(200) DEFAULT NULL COMMENT '营业执照号',
  `cert_no` varchar(200) DEFAULT NULL COMMENT '证件号码',
  `is_distribution` enum('IS','NO') DEFAULT 'NO' COMMENT '是否分配',
  `customer_manager` varchar(200) DEFAULT NULL COMMENT '客户经理',
  `customer_manager_id` bigint(20) DEFAULT NULL COMMENT '客户经理ID',
  `director` varchar(50) DEFAULT NULL COMMENT '业务总监',
  `dep_id` bigint(20) DEFAULT NULL COMMENT '部门Id',
  `dep_name` varchar(200) DEFAULT NULL COMMENT '部门名',
  `director_id` bigint(20) DEFAULT NULL COMMENT '业务总监ID',
  `input_person` varchar(200) DEFAULT NULL COMMENT '录入人',
  `input_person_id` bigint(20) DEFAULT NULL COMMENT '录入人ID',
  `customer_level` varchar(200) DEFAULT NULL COMMENT '客户评级',
  `industry_code` varchar(200) DEFAULT NULL COMMENT '行业编码',
  `country_code` varchar(50) DEFAULT NULL COMMENT '所属区域 - 国家编码',
  `country_name` varchar(50) DEFAULT NULL COMMENT '所属区域 - 国家名',
  `province_code` varchar(50) DEFAULT NULL COMMENT '所属区域 - 省编码',
  `province_name` varchar(50) DEFAULT NULL COMMENT '所属区域 - 省编名',
  `city_code` varchar(50) DEFAULT NULL COMMENT '所属区域 - 市编码',
  `city_name` varchar(50) DEFAULT NULL COMMENT '所属区域 - 市编名',
  `county_code` varchar(50) DEFAULT NULL COMMENT '所属区域 - 区域编码',
  `county_name` varchar(50) DEFAULT NULL COMMENT '所属区域 - 区域名',
  `channal_id` bigint(20) DEFAULT NULL COMMENT '渠道id',
  `channal_name` varchar(50) DEFAULT NULL COMMENT '渠道名',
  `channal_type` varchar(20) DEFAULT NULL COMMENT '渠道类型',
  `enterprise_type` varchar(200) DEFAULT NULL COMMENT '企业类型/性质',
  `status` varchar(200) DEFAULT NULL COMMENT '用户状态',
  `is_temporary` enum('IS','NO') DEFAULT 'NO' COMMENT '是否暂存数据',
  `project_status` varchar(50) DEFAULT NULL COMMENT '立项状态',
  `evalue_status` varchar(50) DEFAULT NULL COMMENT '评级状态',
  `customer_type` varchar(200) NOT NULL COMMENT '用户类型',
  `raw_add_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=483 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户基本信息';

/*Table structure for table `customer_company_detail` */


CREATE TABLE `customer_company_detail` (
  `customer_id` varchar(20) NOT NULL COMMENT '查询ID',
  `loan_card_no` varchar(200) DEFAULT NULL COMMENT '贷款卡号',
  `final_year_check` varchar(200) DEFAULT NULL COMMENT '最后年检年度',
  `industry_code` varchar(200) DEFAULT NULL COMMENT '行业编码',
  `industry_name` varchar(200) DEFAULT NULL COMMENT '所属行业名字',
  `industry_all_name` varchar(200) DEFAULT NULL COMMENT '行业全称',
  `is_local_gov_platform` enum('IS','NO') DEFAULT 'NO' COMMENT '是否地方政府融资平台企业',
  `is_export_oriented_economy` enum('IS','NO') DEFAULT 'NO' COMMENT '是否外向型经济客户',
  `is_group` enum('IS','NO') DEFAULT 'NO' COMMENT '是否集团客户',
  `is_listed_company` enum('IS','NO') DEFAULT 'NO' COMMENT '是否上市公司',
  `enterprise_type` varchar(200) DEFAULT NULL COMMENT '企业性质',
  `register_capital` bigint(20) DEFAULT NULL COMMENT '注册资本',
  `actual_capital` bigint(20) DEFAULT NULL COMMENT '实收资本',
  `established_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '成立时间',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `subordinate_relationship` varchar(100) DEFAULT NULL COMMENT '隶属关系',
  `company_address` varchar(200) DEFAULT NULL COMMENT '企业地址',
  `scale` varchar(200) DEFAULT NULL COMMENT '企业规模',
  `staff_num` bigint(20) DEFAULT '0' COMMENT '员工人数',
  `contact_man` varchar(200) DEFAULT NULL COMMENT '联系人',
  `contact_no` varchar(200) DEFAULT NULL COMMENT '联系电话',
  `busi_scope` varchar(1000) DEFAULT NULL COMMENT '经营范围',
  `major_product` varchar(200) DEFAULT NULL COMMENT '主导产品',
  `relation` varchar(200) DEFAULT NULL COMMENT '客户与我公司关系',
  `legal_persion` varchar(200) DEFAULT NULL COMMENT '法定代表人',
  `legal_persion_cert_no` varchar(200) DEFAULT NULL COMMENT '法定代表人身份证号码',
  `legal_persion_address` varchar(200) DEFAULT NULL COMMENT '法定代表人地址',
  `actual_control_man` varchar(200) DEFAULT NULL COMMENT '实际控制人',
  `actual_control_man_cert_no` varchar(200) DEFAULT NULL COMMENT '实际控制人身份证',
  `actual_control_man_cert_type` varchar(50) DEFAULT NULL COMMENT '实际控制人证件类型',
  `actual_control_man_address` varchar(200) DEFAULT NULL COMMENT '实际控制人地址',
  `bank_account` varchar(200) DEFAULT NULL COMMENT '基本账户开户行',
  `account_no` varchar(200) DEFAULT NULL COMMENT '账号',
  `settle_bank_account1` varchar(200) DEFAULT NULL COMMENT '主要结算账户开户行1',
  `settle_account_no1` varchar(200) DEFAULT NULL COMMENT '账号',
  `settle_bank_account2` varchar(200) DEFAULT NULL COMMENT '主要结算账户开户行2',
  `settle_account_no2` varchar(200) DEFAULT NULL COMMENT '账号',
  `settle_bank_account3` varchar(200) DEFAULT NULL COMMENT '其他结算账户开户行',
  `settle_account_no3` varchar(200) DEFAULT NULL COMMENT '账号',
  `total_asset` bigint(20) DEFAULT '0' COMMENT '总资产',
  `net_asset` bigint(20) DEFAULT '0' COMMENT '净资产',
  `asset_liability_ratio` decimal(12,4) DEFAULT '0.0000' COMMENT '资产负债率',
  `liquidity_ratio` decimal(12,4) DEFAULT '0.0000' COMMENT '流动比率',
  `quick_ratio` decimal(12,4) DEFAULT '0.0000' COMMENT '速动比率',
  `sales_proceeds_last_year` bigint(20) DEFAULT '0' COMMENT '去年销售收入',
  `total_profit_last_year` bigint(20) DEFAULT '0' COMMENT '去年利润总额',
  `sales_proceeds_this_year` bigint(20) DEFAULT '0' COMMENT '今年销售收入',
  `total_profit_this_year` bigint(20) DEFAULT '0' COMMENT '今年利润总额',
  `busi_license_no` varchar(200) DEFAULT NULL COMMENT '营业执照号',
  `busi_license_url` varchar(200) DEFAULT NULL COMMENT '营业执照图片',
  `is_one_cert` varchar(200) DEFAULT NULL COMMENT '是否三证合一',
  `org_code` varchar(200) DEFAULT NULL COMMENT '组织机构代码',
  `org_code_url` varchar(200) DEFAULT NULL COMMENT '组织机构图片',
  `tax_certificate_no` varchar(200) DEFAULT NULL COMMENT '税务登记证',
  `tax_certificate_url` varchar(200) DEFAULT NULL COMMENT '税务登记证图片',
  `local_tax_cert_no` varchar(200) DEFAULT NULL COMMENT '地税证号码',
  `local_tax_cert_url` varchar(200) DEFAULT NULL COMMENT '地税证拍照',
  `apply_scanning_url` varchar(200) DEFAULT NULL COMMENT '客户申请书扫描件',
  `local_finance` text COMMENT '地方财政情况',
  `info1` varchar(200) DEFAULT NULL COMMENT '备用字段1',
  `info2` varchar(200) DEFAULT NULL COMMENT '备用字段2',
  `info3` varchar(200) DEFAULT NULL COMMENT '备用字段3',
  `raw_add_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='公司客户详情';

/*Table structure for table `customer_info_for_evalue` */


CREATE TABLE `customer_info_for_evalue` (
  `formId` bigint(20) NOT NULL COMMENT '表单Id',
  `customer_id` varchar(50) NOT NULL COMMENT '客户Id',
  `customer_name` varchar(20) DEFAULT NULL COMMENT '客户名',
  `loan_card_no` varchar(100) DEFAULT NULL COMMENT '贷款卡号',
  `actual_capital` bigint(20) DEFAULT NULL COMMENT '注册实收资本',
  `subordinate_relationship` varchar(200) DEFAULT NULL COMMENT '隶属关系',
  `sales_proceeds_last_year` bigint(20) DEFAULT NULL COMMENT '销售额（去年',
  `account_no` varchar(200) DEFAULT NULL COMMENT '基本账户行',
  `is_group` enum('IS','NO') DEFAULT NULL COMMENT '是否集团客户',
  `is_listed_company` enum('IS','NO') DEFAULT NULL COMMENT '是否上市公司 ',
  `change_person` varchar(200) DEFAULT NULL COMMENT '修改人',
  `change_person_id` bigint(20) DEFAULT NULL COMMENT '修改人Id',
  `year` varchar(20) DEFAULT NULL COMMENT '评级年限',
  `raw_add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`formId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='暂存评价时修改的个人信息';

/*Table structure for table `customer_person_detail` */


CREATE TABLE `customer_person_detail` (
  `customer_id` varchar(200) NOT NULL COMMENT '查询Id',
  `customer_name` varchar(200) NOT NULL COMMENT '姓名',
  `customer_name_px` varchar(200) DEFAULT NULL COMMENT '姓名拼音缩写',
  `citizen_type` varchar(200) DEFAULT NULL COMMENT '公民类型:境内,境外',
  `nation` varchar(200) DEFAULT NULL COMMENT '民族',
  `birthDay` varchar(200) DEFAULT NULL COMMENT '出生日期',
  `marital_status` varchar(200) DEFAULT NULL COMMENT '婚姻状况',
  `mobile` varchar(200) DEFAULT NULL COMMENT '联系电话',
  `mobile_bond` enum('IS','NO') DEFAULT 'NO' COMMENT '电话绑定:IS:已验证；NO:未验证',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `email_bond` enum('IS','NO') DEFAULT 'NO' COMMENT '邮箱绑定:IS:已验证；NO:未验证',
  `fix` varchar(200) DEFAULT NULL COMMENT '传真',
  `county_code` varchar(50) DEFAULT NULL COMMENT '区域编码',
  `county_name` varchar(100) DEFAULT NULL COMMENT '区域名',
  `address` varchar(200) DEFAULT NULL COMMENT '固定住址',
  `origin_place` varchar(200) DEFAULT NULL COMMENT '籍贯',
  `registered_address` varchar(200) DEFAULT NULL COMMENT '户口所在地',
  `postcode` varchar(200) DEFAULT NULL COMMENT '邮政编码',
  `company` varchar(200) DEFAULT NULL COMMENT '现工作单位',
  `job` varchar(200) DEFAULT NULL COMMENT '职务',
  `technical` varchar(200) DEFAULT NULL COMMENT '技术职称',
  `customer_job_type` varchar(200) DEFAULT NULL COMMENT '客户类型:个人，个体工商',
  `relation` varchar(200) DEFAULT NULL COMMENT '客户与我公司关系',
  `spo_realName` varchar(200) DEFAULT NULL COMMENT '配偶姓名',
  `spo_sex` varchar(200) DEFAULT NULL COMMENT '配偶性别',
  `spo_citizen_type` varchar(200) DEFAULT NULL COMMENT '配偶公民类型',
  `spo_nation` varchar(200) DEFAULT NULL COMMENT '配偶民族',
  `spo_cert_type` varchar(200) DEFAULT NULL COMMENT '配偶证件类型',
  `spo_cert_no` varchar(200) DEFAULT NULL COMMENT '配偶证件号码',
  `spo_marital_status` varchar(200) DEFAULT NULL COMMENT '配偶婚姻状况',
  `spo_education` varchar(200) DEFAULT NULL COMMENT '配偶学历',
  `spo_birthDay` varchar(200) DEFAULT NULL COMMENT '配偶出生日期',
  `spo_mobile` varchar(200) DEFAULT NULL COMMENT '配偶联系电话',
  `spo_email` varchar(200) DEFAULT NULL COMMENT '配偶电子邮箱',
  `spo_fix` varchar(200) DEFAULT NULL COMMENT '配偶传真',
  `spo_address` varchar(200) DEFAULT NULL COMMENT '配偶固定住址',
  `spo_origin_place` varchar(200) DEFAULT NULL COMMENT '配偶籍贯',
  `spo_registered_address` varchar(200) DEFAULT NULL COMMENT '配偶户口所在地',
  `spo_postcode` varchar(200) DEFAULT NULL COMMENT '配偶邮政编码',
  `spo_company` varchar(200) DEFAULT NULL COMMENT '配偶现工作单位',
  `spo_job` varchar(200) DEFAULT NULL COMMENT '配偶职务',
  `spo_technical` varchar(200) DEFAULT NULL COMMENT '配偶技术职称',
  `spo_customer_type` varchar(200) DEFAULT NULL COMMENT '配偶客户类型:个人，个体工商',
  `total_asset` bigint(20) DEFAULT '0' COMMENT '总资产',
  `family_asset` bigint(20) DEFAULT '0' COMMENT '家庭财产',
  `total_loan` bigint(20) DEFAULT '0' COMMENT '总负债',
  `total_outcome` bigint(20) DEFAULT '0' COMMENT '年总支出',
  `bank_account` varchar(200) DEFAULT NULL COMMENT '开户行',
  `account_no` varchar(200) DEFAULT NULL COMMENT '账号',
  `account_holder` varchar(200) DEFAULT NULL COMMENT '开户人',
  `bank_account_wages` varchar(200) DEFAULT NULL COMMENT '本人工资代发账户:开户行',
  `account_no_wages` varchar(200) DEFAULT NULL COMMENT '本人工资代发账户:账号',
  `account_holder_wages` varchar(200) DEFAULT NULL COMMENT '本人工资代发账户:开户人',
  `spo_bank_account_wages` varchar(200) DEFAULT NULL COMMENT '配偶工资代发账户:开户行',
  `spo_account_no_wages` varchar(200) DEFAULT NULL COMMENT '配偶工资代发账户:账号',
  `spo_account_holder_wages` varchar(200) DEFAULT NULL COMMENT '配偶工资代发账户:开户人',
  `house` varchar(1000) DEFAULT NULL COMMENT '房产',
  `car` varchar(1000) DEFAULT NULL COMMENT '车辆',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `info1` varchar(200) DEFAULT NULL COMMENT '备用字段1',
  `info2` varchar(200) DEFAULT NULL COMMENT '备用字段2',
  `info3` varchar(200) DEFAULT NULL COMMENT '备用字段3',
  `cert_img` varchar(1000) DEFAULT NULL COMMENT '证件照',
  `cert_img_font` varchar(1000) DEFAULT NULL COMMENT '证件照正面',
  `cert_img_back` varchar(1000) DEFAULT NULL COMMENT '证件照反面',
  `raw_add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='个人客户详情';

/*Table structure for table `evaluating_base` */


CREATE TABLE `evaluating_base` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `form_id` bigint(20) DEFAULT NULL COMMENT '表单Id',
  `customer_id` varchar(100) NOT NULL COMMENT '客户Id',
  `year` varchar(20) DEFAULT NULL COMMENT '哪一年评级',
  `evalueting_id` bigint(20) DEFAULT NULL COMMENT '评价Id',
  `evalueting_key` varchar(100) DEFAULT NULL COMMENT '固定评价区分是哪项',
  `actual_value` varchar(200) DEFAULT NULL COMMENT '指标实际值',
  `this_score` varchar(10) DEFAULT NULL COMMENT '该项评分',
  `memo` varchar(500) DEFAULT NULL COMMENT '备注',
  `evalue_type` varchar(50) NOT NULL COMMENT '指标类型',
  `evalue_type_child` varchar(50) DEFAULT NULL COMMENT '指标类型-小类型',
  `input_person` varchar(100) DEFAULT NULL COMMENT '操作人',
  `input_person_id` bigint(20) DEFAULT NULL COMMENT '操作人Id',
  `step` varchar(100) NOT NULL DEFAULT '0' COMMENT '评级步骤',
  `raw_add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8578 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='一般指标评价结果';

/*Table structure for table `evaluating_base_set` */


CREATE TABLE `evaluating_base_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `level1_id` bigint(20) DEFAULT NULL COMMENT '1级指标',
  `level1_name` varchar(200) NOT NULL COMMENT '1级指标名',
  `level1_description` varchar(200) DEFAULT NULL COMMENT '1级指标说明',
  `level1_score` varchar(5) DEFAULT NULL COMMENT '1级指标总分',
  `level2_id` bigint(20) DEFAULT NULL COMMENT '2级指标Id',
  `level2_name` varchar(200) DEFAULT NULL COMMENT '2级指标名',
  `level2_description` varchar(200) DEFAULT NULL COMMENT '2级指标说明',
  `level2_score` varchar(5) DEFAULT NULL COMMENT '2级指标总分',
  `level3_id` bigint(20) DEFAULT NULL COMMENT '3级指标Id',
  `level3_name` varchar(200) DEFAULT NULL COMMENT '3级指标名',
  `level3_description` varchar(200) DEFAULT NULL COMMENT '3级指标说明',
  `level3_score` varchar(5) DEFAULT NULL COMMENT '3级指标总分',
  `level4_id` bigint(20) DEFAULT NULL COMMENT '4级指标Id',
  `level4_name` varchar(200) DEFAULT NULL COMMENT '4级指标名',
  `level4_description` varchar(200) DEFAULT NULL COMMENT '4级指标说明',
  `level4_score` varchar(5) DEFAULT NULL COMMENT '4级指标总分',
  `evaluating_content` varchar(200) DEFAULT NULL COMMENT '评价内容',
  `evaluating_result` varchar(200) DEFAULT NULL COMMENT '评价结果',
  `score` varchar(5) DEFAULT NULL COMMENT '得分',
  `standard_value` varchar(100) DEFAULT NULL COMMENT '标准值',
  `compare_method` varchar(20) DEFAULT NULL COMMENT '比较方式（大于，等于，小于..）',
  `compare_method2` varchar(20) DEFAULT NULL COMMENT '比较方式2',
  `compare_method3` varchar(20) DEFAULT NULL COMMENT '比较方式3',
  `compare_standard_value` varchar(50) DEFAULT NULL COMMENT '与标准值比较值',
  `evaluating_standard_value` varchar(50) DEFAULT NULL COMMENT '评分标准值',
  `compare_evaluating_standard_value` varchar(50) DEFAULT NULL COMMENT '与评分标准值比较值',
  `calculating_formula` varchar(200) DEFAULT NULL COMMENT '计算公式',
  `level` varchar(5) DEFAULT NULL COMMENT '指标等级',
  `perent_level` bigint(20) DEFAULT NULL COMMENT '父类指标id',
  `type` varchar(200) DEFAULT NULL COMMENT '指标类型',
  `status` varchar(50) DEFAULT NULL COMMENT '指标状态',
  `order_num1` bigint(10) DEFAULT '0' COMMENT '一级排序',
  `order_num2` bigint(10) DEFAULT NULL COMMENT '二级排序',
  `order_num3` bigint(10) DEFAULT NULL COMMENT '三级排序',
  `order_num4` bigint(10) DEFAULT NULL COMMENT '四级排序',
  `perent_id` bigint(20) DEFAULT '0' COMMENT '正式数据id',
  `child_id` bigint(20) DEFAULT '0' COMMENT '暂存数据',
  `is_temporary` enum('IS','NO') DEFAULT 'NO' COMMENT '是否暂存数据',
  `raw_add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2515 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='一般评价指标配置';

/*Table structure for table `evaluating_financial_set` */


CREATE TABLE `evaluating_financial_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `year` varchar(50) DEFAULT NULL COMMENT '配置年限',
  `string1` varchar(50) DEFAULT NULL COMMENT '字段1',
  `string2` varchar(50) DEFAULT NULL COMMENT '字段2',
  `string3` varchar(50) DEFAULT NULL COMMENT '字段3',
  `string4` varchar(50) DEFAULT NULL COMMENT '字段4',
  `string5` varchar(50) DEFAULT NULL COMMENT '字段5',
  `string6` varchar(50) DEFAULT NULL COMMENT '字段6',
  `string7` varchar(50) DEFAULT NULL COMMENT '字段7',
  `string8` varchar(50) DEFAULT NULL COMMENT '字段8',
  `string9` varchar(50) DEFAULT NULL COMMENT '字段9',
  `string10` varchar(50) DEFAULT NULL COMMENT '字段10',
  `string11` varchar(50) DEFAULT NULL COMMENT '字段11',
  `string12` varchar(50) DEFAULT NULL COMMENT '字段12',
  `string13` varchar(50) DEFAULT NULL COMMENT '字段13',
  `string14` varchar(50) DEFAULT NULL COMMENT '字段14',
  `string15` varchar(50) DEFAULT NULL COMMENT '字段15',
  `string16` varchar(50) DEFAULT NULL COMMENT '字段16',
  `string17` varchar(50) DEFAULT NULL COMMENT '字段17',
  `string18` varchar(50) DEFAULT NULL COMMENT '字段18',
  `string19` varchar(50) DEFAULT NULL COMMENT '字段19',
  `string20` varchar(50) DEFAULT NULL COMMENT '字段20',
  `string21` varchar(50) DEFAULT NULL COMMENT '字段21',
  `calculating_formula` varchar(200) DEFAULT NULL COMMENT '计算公式',
  `type_child` varchar(50) NOT NULL COMMENT '改类型下小分类',
  `type` varchar(50) NOT NULL COMMENT '财务指标类型',
  `row_add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `raw_update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='财务指标配置';

/*Table structure for table `evaluating_level` */


CREATE TABLE `evaluating_level` (
  `evalue_key` varchar(100) NOT NULL COMMENT 'userId与formId拼接',
  `user_id` bigint(20) NOT NULL COMMENT '客户Id',
  `year` varchar(100) NOT NULL COMMENT '评级年',
  `level` varchar(100) DEFAULT NULL COMMENT '当年等级',
  `form_id` bigint(100) DEFAULT NULL COMMENT '表单Id',
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `raw_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`evalue_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='评级等级保存';

/*Table structure for table `evalueting_list` */


CREATE TABLE `evalueting_list` (
  `form_id` bigint(20) NOT NULL COMMENT '审核表单Id',
  `project_code` varchar(100) DEFAULT NULL COMMENT '关联项目Id',
  `customer_id` varchar(200) NOT NULL COMMENT '客户Id',
  `customer_name` varchar(100) NOT NULL COMMENT '客户名称',
  `busi_license_no` varchar(50) NOT NULL COMMENT '营业执照号码',
  `level` varchar(50) DEFAULT NULL COMMENT '客户等级',
  `year` varchar(20) NOT NULL COMMENT '评级年限:如 2016',
  `audit_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '审定日期',
  `operator` varchar(200) DEFAULT NULL COMMENT '操作人',
  `audit_status` varchar(100) DEFAULT NULL COMMENT '评级状态',
  `evalueting_type` varchar(100) DEFAULT NULL COMMENT '评价方式：内部/外部',
  `is_prosecute` enum('IS','NO') NOT NULL DEFAULT 'NO' COMMENT '被公司提起或准备提起法律诉讼的客户',
  `evalueting_institutions` varchar(100) DEFAULT NULL COMMENT '外部_评级机构',
  `evalueting_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '外部_评级日期',
  `audit_img` varchar(1500) DEFAULT NULL COMMENT '外部_评级图片',
  `audit_opinion1` varchar(500) DEFAULT NULL COMMENT '审核意见1',
  `audit_opinion2` varchar(500) DEFAULT NULL COMMENT '审核意见2',
  `audit_opinion3` varchar(500) DEFAULT NULL COMMENT '审核意见3',
  `audit_opinion4` varchar(500) DEFAULT NULL COMMENT '审核意见4',
  `audit_opinion5` varchar(500) DEFAULT NULL COMMENT '审核意见5',
  `audit_opinion6` varchar(500) DEFAULT NULL COMMENT '审核意见6',
  `edit_status` varchar(100) DEFAULT NULL COMMENT '评级完整性',
  `raw_add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='评级结果';

/*Table structure for table `form` */


CREATE TABLE `form` (
  `form_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '表单ID',
  `form_code` varchar(50) DEFAULT NULL COMMENT '表单编码',
  `form_name` varchar(128) DEFAULT NULL COMMENT '表单名称',
  `form_url` varchar(255) DEFAULT NULL COMMENT '表单地址',
  `act_inst_id` bigint(20) DEFAULT NULL COMMENT 'ACT流程实例ID',
  `act_def_id` varchar(128) DEFAULT NULL COMMENT 'ACT流程定义ID',
  `def_id` bigint(20) DEFAULT NULL COMMENT '流程定义ID',
  `run_id` bigint(20) DEFAULT NULL COMMENT '流程runId(流程实例)',
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务id',
  `status` varchar(10) DEFAULT NULL COMMENT '状态',
  `detail_status` varchar(128) DEFAULT NULL COMMENT 'bpm详细状态',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `user_account` varchar(50) DEFAULT NULL COMMENT '创建人账号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '创建人名字',
  `user_mobile` varchar(20) DEFAULT NULL COMMENT '用户电话',
  `user_email` varchar(50) DEFAULT NULL COMMENT '创建人邮箱',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '创建部门ID',
  `dept_code` varchar(30) DEFAULT NULL COMMENT '部门编号',
  `dept_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `dept_path` varchar(256) DEFAULT NULL COMMENT '部门路径',
  `dept_path_name` varchar(500) DEFAULT NULL COMMENT '部门路径名称',
  `check_status` varchar(30) DEFAULT NULL COMMENT '校验状态 1表示通过 0表示不通过  多个表的校验如1111100000011',
  `submit_time` datetime DEFAULT NULL COMMENT '表单提交时间',
  `finish_time` datetime DEFAULT NULL COMMENT '表单审核完成时间',
  `related_project_code` varchar(512) DEFAULT NULL COMMENT '相关项目编号,多条用逗号隔开XXXX,xxxx1',
  `task_user_data` text COMMENT '当前代办任务人员',
  `trace` text COMMENT '执行轨迹',
  `remark` text COMMENT '备注',
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`form_id`),
  KEY `form_act_def_inst_id_i` (`act_def_id`,`act_inst_id`)
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=2730 COMMENT='表单';

/*Table structure for table `form_related_user` */


CREATE TABLE `form_related_user` (
  `related_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `form_id` bigint(20) DEFAULT NULL COMMENT '表单ID',
  `form_code` varchar(128) DEFAULT NULL COMMENT '表单code',
  `task_id` bigint(20) DEFAULT '0' COMMENT '任务ID',
  `task_name` varchar(128) DEFAULT NULL COMMENT '任务名称',
  `task_opinion` varchar(10) DEFAULT NULL COMMENT '任务意见',
  `task_start_time` datetime DEFAULT NULL COMMENT '任务接收时间',
  `task_end_time` datetime DEFAULT NULL COMMENT '任务结束时间',
  `exe_status` varchar(20) DEFAULT NULL COMMENT '任务执行状态',
  `project_code` varchar(50) DEFAULT NULL COMMENT '相关项目编号',
  `user_type` varchar(20) DEFAULT 'OTHER' COMMENT '人员类型 (流程候选人、执行人、提交人...)',
  `user_id` bigint(20) DEFAULT NULL COMMENT '人员ID',
  `user_account` varchar(50) DEFAULT NULL COMMENT '人员账号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '人员名称',
  `user_mobile` varchar(20) DEFAULT NULL COMMENT '人员手机',
  `user_email` varchar(128) DEFAULT NULL COMMENT '人员email',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '当前部门ID',
  `dept_code` varchar(50) DEFAULT NULL COMMENT '部门编号',
  `dept_name` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `dept_path` varchar(1024) DEFAULT NULL COMMENT '部门路径',
  `dept_path_name` text COMMENT '部门路径名',
  `remark` text COMMENT '备注',
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '新增时间',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`related_id`),
  KEY `form_related_user_form_id_i` (`form_id`),
  KEY `form_related_user_project_code_i` (`project_code`),
  KEY `form_related_user_task_id_i` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=604 DEFAULT CHARSET=utf8 COMMENT='表单相关人员';

/*Table structure for table `list_data_save` */


CREATE TABLE `list_data_save` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `data_type` varchar(200) NOT NULL COMMENT '类型',
  `description` varchar(200) DEFAULT NULL COMMENT '描述该集合用途',
  `str1` varchar(200) DEFAULT NULL COMMENT 'String类型1',
  `str2` varchar(200) DEFAULT NULL COMMENT 'String类型2',
  `str3` varchar(200) DEFAULT NULL COMMENT 'String类型3',
  `str4` varchar(200) DEFAULT NULL COMMENT 'String类型4',
  `str5` varchar(200) DEFAULT NULL COMMENT 'String类型5',
  `str6` varchar(200) DEFAULT NULL COMMENT 'String类型6',
  `str7` varchar(200) DEFAULT NULL COMMENT 'String类型7',
  `str8` varchar(200) DEFAULT NULL COMMENT 'String类型8',
  `integer1` varchar(200) DEFAULT NULL COMMENT 'String类型9',
  `double1` varchar(200) DEFAULT NULL COMMENT 'String类型10',
  `raw_add_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='存放一些List类型数据';

/*Table structure for table `personal_company` */


CREATE TABLE `personal_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `customer_id` varchar(50) NOT NULL COMMENT '用户查询Id',
  `per_company` varchar(200) DEFAULT NULL COMMENT '公司名称 ',
  `per_regist_amount` bigint(20) DEFAULT NULL COMMENT '注册资本',
  `per_regist_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '注册时间',
  `per_link_person` varchar(200) DEFAULT NULL COMMENT '联系人',
  `per_address` varchar(200) DEFAULT NULL COMMENT '经营地址',
  `per_link_mobile` varchar(200) DEFAULT NULL COMMENT '联系电话',
  `per_memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `per_status` varchar(20) DEFAULT NULL COMMENT '存储状态',
  `perent_id` bigint(20) DEFAULT '0' COMMENT '正式数据id',
  `child_id` bigint(20) DEFAULT '0' COMMENT '暂存数据',
  `per_is_temporary` enum('IS','NO') DEFAULT 'NO' COMMENT '是否暂存数据',
  `raw_add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `raw_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 MIN_ROWS=1 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='个人用户旗下公司信息';

/*Table structure for table `project_related_user` */


CREATE TABLE `project_related_user` (
  `related_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_code` varchar(50) DEFAULT NULL COMMENT '项目编号',
  `user_type` varchar(20) DEFAULT 'OTHER' COMMENT '人员类型 (业务经理、法务经理、其他...等)',
  `user_id` bigint(20) DEFAULT NULL COMMENT '当前人员ID',
  `user_account` varchar(50) DEFAULT NULL COMMENT '当前人员账号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '当前人员名称',
  `user_mobile` varchar(20) DEFAULT NULL COMMENT '当前人员手机',
  `user_email` varchar(128) DEFAULT NULL COMMENT '当前人员email',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '当前部门ID',
  `dept_code` varchar(50) DEFAULT NULL COMMENT '部门编号',
  `dept_name` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `dept_path` varchar(1024) DEFAULT NULL COMMENT '部门路径',
  `dept_path_name` text COMMENT '部门路径名',
  `transfer_time` datetime DEFAULT NULL COMMENT '转交时间',
  `transfer_related_id` bigint(20) DEFAULT NULL COMMENT '转交ID -> related_id',
  `remark` text COMMENT '备注',
  `is_current` varchar(10) DEFAULT 'IS' COMMENT '是否是当前人员(方便保存历史记录)',
  `is_del` varchar(10) DEFAULT 'NO' COMMENT '是否删除(删除后不再有权限)',
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '新增时间',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`related_id`),
  KEY `project_related_user_project_code_i` (`project_code`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8 COMMENT='项目相关人员';

################ 2016-10-18 以上已执行 ####################

## 2016-10-19 渠道合同表修改
DROP TABLE IF EXISTS `channal_contract`;

CREATE TABLE `channal_contract` (
   `form_id` BIGINT(20) NOT NULL COMMENT '主键',
   `contract` VARCHAR(5000) DEFAULT NULL COMMENT '合同类容',
   `contract_return` VARCHAR(5000) DEFAULT NULL COMMENT '回传合同',
   `contract_no` VARCHAR(16) DEFAULT NULL COMMENT '合同编号',
   `channal_code` VARCHAR(16) DEFAULT NULL COMMENT '渠道编号',
   `channal_name` VARCHAR(500) DEFAULT NULL COMMENT '渠道名称',
   `channal_type` VARCHAR(100) DEFAULT NULL COMMENT '渠道分类',
   `info1` VARCHAR(2000) DEFAULT NULL COMMENT '备用字段1',
   `info2` VARCHAR(2000) DEFAULT NULL COMMENT '备用字段2',
   `raw_add_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
   `raw_update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`form_id`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='渠道合同';
 ################ 2016-10-19 以上已执行 ####################
 
 ##风险提示信息保存
 CREATE TABLE `cutomer_info_verify_message` (
   `error_key` varchar(200) NOT NULL COMMENT '错误提示查询主键',
   `customer_id` bigint(20) DEFAULT NULL COMMENT '客户Id',
   `mobile_message` varchar(200) DEFAULT NULL COMMENT '手机号校验提示',
   `card_message` varchar(200) DEFAULT NULL COMMENT '实名校验提示',
   `message_type` varchar(200) DEFAULT NULL COMMENT '提示类型',
   `raw_add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
   `raw_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`error_key`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='客户信息校验提示信息记录'
 ################ 2016-10-24 以上已执行 ####################
 
##移交分配 2016-11-01 
 DROP TABLE IF EXISTS `customer_relation`;
 CREATE TABLE `customer_relation` (
   `relation_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关系ID',
   `user_id` bigint(20) NOT NULL COMMENT '客户Id',
   `customer_manager_id` bigint(20) DEFAULT NULL COMMENT '客户经理Id',
   `customer_manager` varchar(200) DEFAULT NULL COMMENT '客户经理',
   `director_id` bigint(20) DEFAULT NULL COMMENT '总监Id',
   `director` varchar(200) DEFAULT NULL COMMENT '总监',
   `dep_id` bigint(20) NOT NULL COMMENT '部门id',
   `dep_name` varchar(200) DEFAULT NULL COMMENT '部门名',
   `dep_path` varchar(2000) DEFAULT NULL COMMENT '包含部门',
   `oper_id` bigint(20) NOT NULL COMMENT '操作人Id',
   `oper_name` varchar(200) DEFAULT NULL COMMENT '操作人名',
   `raw_add_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
   `raw_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`relation_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='客户关联关系';
 
################ 2016-11-11前 以上已执行 ####################
 
##2016-11-14 业务区域维护 
CREATE TABLE `bus_region` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增Id',
   `dep_name` varchar(128) NOT NULL COMMENT '部门名',
   `dep_path` varchar(500) NOT NULL COMMENT '部门完整编号',
   `code` varchar(255) NOT NULL COMMENT '区域编号',
   `name` varchar(50) NOT NULL COMMENT '区域名',
   `status` enum('IS','NO') DEFAULT NULL COMMENT '启用状态',
   `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
   `raw_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='业务区域维护'; 
 
 ##########################################11-18##########################################
 
 ## 2017-5-10 企业客户速动流动比率
 alter table `customer_company_detail` 
   change `asset_liability_ratio_last_year` `asset_liability_ratio_last_year` decimal(20,4) NULL  comment '去年资产负债率', 
   change asset_liability_ratio asset_liability_ratio decimal(20,4) DEFAULT '0.0000' COMMENT '资产负债率',
   change `liquidity_ratio` `liquidity_ratio` decimal(20,4) default '0.00' NULL  comment '流动比率', 
   change `quick_ratio` `quick_ratio` decimal(20,4) default '0.00' NULL  comment '速动比率';
   
##########################################2017-5-15#################################################   

##客户-股权结构新增货币单位
ALTER TABLE company_ownership_structure
  ADD COLUMN amount_type VARCHAR(50) DEFAULT '元' COMMENT '货币' AFTER amount;