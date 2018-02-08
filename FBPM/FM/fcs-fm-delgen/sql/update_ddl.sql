CREATE TABLE `form_inner_loan` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `form_id` BIGINT(20) NOT NULL COMMENT '表单ID',
   `loan_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '借款金额',
   `use_time` DATETIME DEFAULT NULL COMMENT '用款时间',
   `back_time` DATETIME DEFAULT NULL COMMENT '还款时间',
   `interest_time` DATETIME DEFAULT NULL COMMENT '付息时间',
   `form_inner_loan_interest_type` VARCHAR(50) DEFAULT NULL COMMENT '付息方式',
   `interest_rate` VARCHAR(50) DEFAULT NULL COMMENT '利率',
   `protocol_code` VARCHAR(50) DEFAULT NULL COMMENT '协议编码',
   `creditor_id` VARCHAR(50) DEFAULT NULL COMMENT '债权人id', 
   `creditor_name` VARCHAR(50) DEFAULT NULL COMMENT '债权人', 
   `loan_reason` VARCHAR(1024) DEFAULT NULL COMMENT '借款用途', 
   `apply_user_id` BIGINT(20) DEFAULT NULL COMMENT '客户经理/经办人',
   `apply_user_account` VARCHAR(50) DEFAULT NULL COMMENT '客户经理/经办人账号',
   `apply_user_name` VARCHAR(50) DEFAULT NULL COMMENT '客户经理/经办人名字',
   `apply_dept_id` BIGINT(20) DEFAULT NULL COMMENT '客户经理/经办人部门ID',
   `apply_dept_code` VARCHAR(30) DEFAULT NULL COMMENT '客户经理/经办人部门编号',
   `apply_dept_name` VARCHAR(50) DEFAULT NULL COMMENT '客户经理/经办人部门名称',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (`id`),
    KEY `form_inner_loan_form_id_i` (`form_id`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='内部借款申请单';
 
 
 CREATE TABLE `form_payment` (
   `payment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `form_id` bigint(20) NOT NULL COMMENT '表单ID',
   `bill_no` varchar(50) NOT NULL COMMENT '单据号',
   `form_source` varchar(50) DEFAULT NULL COMMENT '单据来源（项目/其他）',
   `source_form` varchar(50) NOT NULL COMMENT '来源单据',
   `source_form_id` bigint(20) DEFAULT NULL COMMENT '来源单据ID',
   `source_form_sys` varchar(50) DEFAULT NULL COMMENT '来源单据系统',
   `project_code` varchar(50) DEFAULT NULL COMMENT '关联项目编号',
   `project_name` varchar(128) DEFAULT NULL COMMENT '关联项目名称',
   `customer_id` bigint(20) DEFAULT NULL COMMENT '对应客户ID',
   `customer_name` varchar(128) DEFAULT NULL COMMENT '对应客户名称',
   `customer_type` varchar(10) DEFAULT NULL COMMENT '客户类型',
   `payment_date` datetime DEFAULT NULL COMMENT '付款日期',
   `amount` bigint(20) DEFAULT NULL COMMENT '付款总金额',
   `voucher_no` varchar(50) DEFAULT NULL COMMENT '凭证号',
   `voucher_status` varchar(50) DEFAULT NULL COMMENT '凭证同步状态',
   `voucher_sync_send_time` datetime DEFAULT NULL COMMENT '同步金碟财务系统发送时间(正常情况是审核通过时间)',
   `voucher_sync_finish_time` datetime DEFAULT NULL COMMENT '凭证号返回时间',
   `voucher_sync_message` text COMMENT '凭证号同步结果',
   `apply_user_id` bigint(20) DEFAULT NULL COMMENT '客户经理/经办人',
   `apply_user_account` varchar(50) DEFAULT NULL COMMENT '客户经理/经办人账号',
   `apply_user_name` varchar(50) DEFAULT NULL COMMENT '客户经理/经办人名字',
   `apply_dept_id` bigint(20) DEFAULT NULL COMMENT '客户经理/经办人部门ID',
   `apply_dept_code` varchar(30) DEFAULT NULL COMMENT '客户经理/经办人部门编号',
   `apply_dept_name` varchar(50) DEFAULT NULL COMMENT '客户经理/经办人部门名称',
   `remark` text COMMENT '收款事由备注',
   `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
   `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (`payment_id`),
   KEY `form_payment_form_id_i` (`form_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='付款申请单';
 
 
 
 CREATE TABLE `form_payment_fee` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `form_payment_id` bigint(20) NOT NULL COMMENT '付款单表主键',
   `form_id` bigint(20) NOT NULL COMMENT '表单ID',
   `fee_type` varchar(50) DEFAULT NULL COMMENT '付款类型',
   `amount` bigint(20) DEFAULT NULL COMMENT '付款金额',
   `payment_account` varchar(50) DEFAULT NULL COMMENT '付款账户',
   `receipt_account` varchar(50) DEFAULT NULL COMMENT '收款账户',
   `at_code` varchar(50) DEFAULT NULL COMMENT '会计科目编号',
   `at_name` varchar(128) DEFAULT NULL COMMENT '会计科目名称',
   `remark` text COMMENT '备注',
   `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
   `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (`id`),
   KEY `form_payment_fee_form_id_i` (`form_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收款单-收款明细';
 
 CREATE TABLE `form_travel_expense` (
  `travel_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `form_id` BIGINT(20) NOT NULL COMMENT '表单ID',
  `bill_no` VARCHAR(32) NOT NULL COMMENT '单据号',
  `voucher_no` VARCHAR(32) DEFAULT NULL COMMENT '凭证号',
  `voucher_status` VARCHAR(50) DEFAULT NULL COMMENT '凭证同步状态',
  `voucher_sync_send_time` DATETIME DEFAULT NULL COMMENT '同步金碟财务系统发送时间(正常情况是审核通过时间)',
  `voucher_sync_finish_time` DATETIME DEFAULT NULL COMMENT '凭证号返回时间',
  `voucher_sync_message` TEXT COMMENT '凭证号同步结果',
  `expense_dept_id` BIGINT(20) DEFAULT NULL COMMENT '报销部门ID',
  `dept_name` VARCHAR(128) DEFAULT NULL COMMENT '部门名称',
  `dept_head` VARCHAR(32) DEFAULT NULL COMMENT '部门负责人',
  `is_official_card` VARCHAR(2) DEFAULT NULL COMMENT '公务卡支付',
  `application_time` DATETIME DEFAULT NULL COMMENT '申请日期',
  `relation_form` VARCHAR(32) DEFAULT NULL COMMENT '关联出差申请单',
  `travelers` VARCHAR(1024) DEFAULT NULL COMMENT '出差人员,多人用逗号隔开',
  `reasons` TEXT COMMENT '出差事由',
  `payee_id` BIGINT(20) DEFAULT NULL COMMENT '收款人ID',
  `payee` VARCHAR(32) DEFAULT NULL COMMENT '收款人',
  `bank` VARCHAR(128) DEFAULT NULL COMMENT '开户银行',
  `bank_account` VARCHAR(128) DEFAULT NULL COMMENT '银行账号',
  `amount` BIGINT(20) DEFAULT '0' COMMENT '金额',
  `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`travel_id`),
  UNIQUE KEY `bill_no` (`bill_no`),
  KEY `form_travel_exp_bill_no_i` (`bill_no`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='差旅费报销单'

CREATE TABLE `form_travel_expense_detail` (
  `detail_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `travel_id` BIGINT(20) NOT NULL COMMENT '主表主键',
  `start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
  `days` VARCHAR(20) DEFAULT NULL COMMENT '天数',
  `start_place` VARCHAR(1024) DEFAULT NULL COMMENT '开始地点',
  `end_place` VARCHAR(1024) DEFAULT NULL COMMENT '结束地点',
  `traffic_amount` BIGINT(20) DEFAULT '0' COMMENT '交通费',
  `hotel_amount` BIGINT(20) DEFAULT '0' COMMENT '住宿费',
  `tax_amount` BIGINT(20) DEFAULT '0' COMMENT '税金',
  `meals_amount` BIGINT(20) DEFAULT '0' COMMENT '误餐费',
  `allowance_amount` BIGINT(20) DEFAULT '0' COMMENT '出差补助',
  `other_amount` BIGINT(20) DEFAULT '0' COMMENT '其他费用',
  `total_amount` BIGINT(20) DEFAULT '0' COMMENT '小计',
  `dept_id` BIGINT(20) DEFAULT NULL COMMENT '部门ID',
  `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='差旅费报销单明细';

CREATE TABLE `form_expense_application` (
  `expense_application_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `form_id` bigint(20) NOT NULL COMMENT '表单ID',
  `bill_no` varchar(32) NOT NULL COMMENT '单据号',
  `voucher_no` varchar(32) DEFAULT NULL COMMENT '凭证号',
  `voucher_status` varchar(50) DEFAULT NULL COMMENT '凭证同步状态',
  `voucher_sync_send_time` datetime DEFAULT NULL COMMENT '同步金碟财务系统发送时间(正常情况是审核通过时间)',
  `voucher_sync_finish_time` datetime DEFAULT NULL COMMENT '凭证号返回时间',
  `voucher_sync_message` text COMMENT '凭证号同步结果',
  `expense_dept_id` bigint(20) DEFAULT NULL COMMENT '报销部门ID',
  `dept_name` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `dept_head` varchar(32) DEFAULT NULL COMMENT '部门负责人',
  `is_official_card` varchar(2) DEFAULT NULL COMMENT '公务卡支付',
  `application_time` datetime DEFAULT NULL COMMENT '申请日期',
  `relation_form` varchar(32) DEFAULT NULL COMMENT '关联出差申请单',
  `agent` varchar(32) DEFAULT NULL COMMENT '经办人',
  `direction` varchar(32) DEFAULT NULL COMMENT '费用方向',
  `payee_id` bigint(20) DEFAULT NULL COMMENT '收款人ID',
  `payee` varchar(32) DEFAULT NULL COMMENT '收款人',
  `bank` varchar(128) DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(128) DEFAULT NULL COMMENT '银行账号',
  `amount` bigint(20) DEFAULT '0' COMMENT '金额',
  `is_reverse` varchar(3) DEFAULT NULL COMMENT '是否冲销',
  `reamount` bigint(20) DEFAULT '0' COMMENT '冲销金额',
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`expense_application_id`),
  UNIQUE KEY `bill_no` (`bill_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='费用支付申请单'

CREATE TABLE `form_expense_application_detail` (
  `detail_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `expense_application_id` BIGINT(20) NOT NULL COMMENT '主表ID',
  `expense_type` VARCHAR(32) DEFAULT NULL COMMENT '费用类型',
  `amount` BIGINT(20) DEFAULT '0' COMMENT '报销金额',
  `tax_amount` BIGINT(20) DEFAULT '0' COMMENT '税金',
  `dept_id` BIGINT(20) DEFAULT NULL COMMENT '部门ID',
  `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='费用支付申请单明细';

CREATE TABLE `cost_category` (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '费用种类ID',
  `name` varchar(128) DEFAULT NULL COMMENT '费用种类名称',
  `account_code` varchar(32) DEFAULT NULL COMMENT '会计科目编号',
  `account_name` varchar(128) DEFAULT NULL COMMENT '会计科目名称',
  `used` varchar(2) DEFAULT NULL COMMENT '是否使用中',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='费用种类';

CREATE TABLE `bank_category` (
  `category_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '银行分类ID',
  `area` VARCHAR(1024) DEFAULT NULL COMMENT '所属区域',
  `bank_category` VARCHAR(1024) DEFAULT NULL COMMENT '银行类型',
  `bank_name` VARCHAR(1024) DEFAULT NULL COMMENT '会计科目名称',
  `status` VARCHAR(32) DEFAULT NULL COMMENT '状态',
  `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='银行分类信息';

CREATE TABLE `budget` (
  `budget_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '预算ID',
  `start_time` datetime DEFAULT NULL COMMENT '预算开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '预算结束时间',
  `budget_dept_id` bigint(20) DEFAULT NULL COMMENT '预算部门ID',
  `budget_dept_name` varchar(128) DEFAULT NULL COMMENT '预算部门名称',
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`budget_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预算管理';

CREATE TABLE `budget_detail` (
  `detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '预算ID明细',
  `budget_id` bigint(20) NOT NULL COMMENT '预算ID',
  `category_id` bigint(20) DEFAULT NULL COMMENT '费用种类ID',
  `amount` bigint(20) DEFAULT '0' COMMENT '预算金额',
  `is_contrl` varchar(2) DEFAULT NULL COMMENT '是否控制',
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预算明细';

CREATE TABLE `form_transfer` (
  `transfer_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `form_id` BIGINT(20) NOT NULL COMMENT '表单ID',
  `bill_no` VARCHAR(32) NOT NULL COMMENT '单据号',
  `voucher_no` VARCHAR(32) DEFAULT NULL COMMENT '凭证号',
  `voucher_status` VARCHAR(50) DEFAULT NULL COMMENT '凭证同步状态',
  `voucher_sync_send_time` DATETIME DEFAULT NULL COMMENT '同步金碟财务系统发送时间(正常情况是审核通过时间)',
  `voucher_sync_finish_time` DATETIME DEFAULT NULL COMMENT '凭证号返回时间',
  `voucher_sync_message` TEXT COMMENT '凭证号同步结果',
  `transfer_dept_id` BIGINT(20) DEFAULT NULL COMMENT '报销部门ID',
  `dept_name` VARCHAR(128) DEFAULT NULL COMMENT '部门名称',
  `dept_head` VARCHAR(32) DEFAULT NULL COMMENT '部门负责人',
  `application_time` DATETIME DEFAULT NULL COMMENT '申请日期',
  `reasons` TEXT COMMENT '用款事由',
  `agent_id` BIGINT(20) DEFAULT NULL COMMENT '经办人ID',
  `agent` VARCHAR(32) DEFAULT NULL COMMENT '经办人',
  `bank_id` BIGINT(20) DEFAULT NULL COMMENT '转入账户',
  `bank_name` VARCHAR(128) DEFAULT NULL COMMENT '开户银行',
  `bank_account` VARCHAR(128) DEFAULT NULL COMMENT '银行账号',
  `amount` BIGINT(20) DEFAULT '0' COMMENT '金额',
  `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`transfer_id`),
  UNIQUE KEY `bill_no` (`bill_no`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='转账申请单';

CREATE TABLE `form_transfer_detail` (
  `detail_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `transfer_id` BIGINT(20) NOT NULL COMMENT '主表ID',
  `bank_id` BIGINT(20) DEFAULT NULL COMMENT '转出账户',
  `bank_name` VARCHAR(128) DEFAULT NULL COMMENT '开户银行',
  `bank_account` VARCHAR(128) DEFAULT NULL COMMENT '银行账号',
  `amount` BIGINT(20) DEFAULT '0' COMMENT '金额',
  `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='转账申请单明细';


--------------20160926  给银行信息添加标记

ALTER TABLE `born_fcs_fm`.`bank_message` 
   ADD COLUMN `default_company_account` VARCHAR(20) NULL COMMENT '是否默认对公支付账户' AFTER `account_no`, 
   ADD COLUMN `default_personal_account` VARCHAR(20) NULL COMMENT '是否默认对私支付账户' AFTER `default_company_account`, 
   ADD COLUMN `deposit_account` VARCHAR(20) NULL COMMENT '是否保证金账户' AFTER `default_personal_account`;
   
   -------------20160928 修订默认科目信息的科目字段
   
      ALTER TABLE `born_fcs_fm`.`sys_subject_message` 
   ADD COLUMN `at_code` VARCHAR(50) NULL COMMENT '会计科目编号' AFTER `subject_cost_type`,
   CHANGE `subject` `at_name` VARCHAR(128) DEFAULT NULL  COMMENT '会计科目名称';
   
 --20161008 jil 收入确认
 
CREATE TABLE income_confirm (
  income_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '收入ID',
  project_code varchar(50) NOT NULL COMMENT '项目编号',
  project_name varchar(128) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '客户ID',
  customer_name varchar(50) DEFAULT NULL COMMENT '客户名称',
  busi_type varchar(50) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(120) DEFAULT NULL COMMENT '业务类型名称',
  charged_amount bigint(20) DEFAULT NULL COMMENT '收费总金额',
  confirmed_income_amount bigint(20) DEFAULT NULL COMMENT '已确认收入金额',
  not_confirmed_income_amount bigint(20) DEFAULT NULL COMMENT '未确认金额',
  this_month_confirmed_income_amount bigint(20) DEFAULT NULL COMMENT '本月确认收入金额',
  income_confirm_status varchar(20) DEFAULT NULL COMMENT '收入确认状态(未完成收入确认，已完成收入确认)',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (income_id),
   INDEX income_confirm_project_code_i (project_code)
)
ENGINE = INNODB
AUTO_INCREMENT = 21
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '收入确认-主表';
--收入确认明细
CREATE TABLE income_confirm_detail (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  income_id bigint(20) NOT NULL COMMENT '收入ID(主表ID)',
  income_period varchar(50) NOT NULL COMMENT '收入期间',
  confirm_status varchar(20) DEFAULT NULL COMMENT '确认状态',
  system_estimated_amount bigint(20) DEFAULT NULL COMMENT '系统预估分摊金额',
  income_confirmed_amount bigint(20) DEFAULT NULL COMMENT '收入确认金额',
  is_confirmed varchar(10) DEFAULT NULL COMMENT '是否确认',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
   INDEX income_confirm_detail_income_id (income_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 21
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '收入确认-明细表';
   
-- 20161011 费用支付申请单
alter table `born_fcs_fm`.`form_expense_application` 
   add column `pay_bank` varchar(128) NULL COMMENT '付款银行' after `reamount`, 
   add column `pay_bank_account` varchar(128) NULL COMMENT '付款银行账号' after `pay_bank`, 
   add column `shlc` varchar(256) NULL COMMENT '审核流程' after `pay_bank_account`;
alter table `born_fcs_fm`.`form_expense_application_detail` 
   add column `fp_amount` binary(20) NULL COMMENT '发票冲销金额' after `tax_amount`, 
   add column `xj_amount` binary(20) NULL COMMENT '现金冲销金额' after `fp_amount`;
-- 20161011 差旅费报销单
alter table `born_fcs_fm`.`form_travel_expense` 
   add column `pay_bank` varchar(128) NULL COMMENT '付款银行' after `bank_account`, 
   add column `pay_bank_account` varchar(128) NULL COMMENT '付款银行账号' after `pay_bank`;
   
   
   
   
   ---------20161018 为预测基础数据表添加子类型两枚，用于报表分析-资金状况表 使用
   
   alter table `born_fcs_fm`.`forecast_account` 
   add column `forecast_child_type_one` varchar(50) NULL COMMENT '子类型一' after `forecast_type`, 
   add column `forecast_child_type_two` varchar(50) NULL COMMENT '子类型二' after `forecast_child_type_one`
   
################ 2016-10-18 以上已执行 ####################


##2016-10-24 wuzj
ALTER TABLE receipt_payment_form
  ADD COLUMN contract_name VARCHAR(200) DEFAULT NULL COMMENT '合同名称' AFTER contract_no,
  ADD COLUMN transfer_name VARCHAR(200) DEFAULT NULL COMMENT '理财转让对象' AFTER contract_name,
  ADD COLUMN product_name VARCHAR(200) DEFAULT NULL COMMENT '理财产品名称' AFTER transfer_name,
  ADD COLUMN attach TEXT DEFAULT NULL COMMENT '附件' AFTER dept_name;
  
ALTER TABLE receipt_payment_form_fee
  ADD COLUMN account VARCHAR(200) DEFAULT NULL COMMENT '收款/付款账号' AFTER amount,
  ADD COLUMN occur_time DATETIME DEFAULT NULL COMMENT '收款/付款时间' AFTER account,
  ADD COLUMN deposit_account VARCHAR(100) DEFAULT NULL COMMENT '保证金账户' AFTER occur_time,
  ADD COLUMN deposit_rate DECIMAL(10, 4) DEFAULT NULL COMMENT '保证金比率' AFTER deposit_account,
  ADD COLUMN deposit_time DATETIME DEFAULT NULL COMMENT '保证金存入/出时间' AFTER deposit_rate,
  ADD COLUMN deposit_period INT(11) DEFAULT NULL COMMENT '保证金存入期限' AFTER deposit_time,
  ADD COLUMN period_unit VARCHAR(20) DEFAULT NULL COMMENT '保证金存入期限单位' AFTER deposit_period,
  ADD COLUMN attach TEXT DEFAULT NULL COMMENT '附件' AFTER period_unit;  
  
ALTER TABLE form_receipt
  ADD COLUMN contract_name VARCHAR(200) DEFAULT NULL COMMENT '合同名称' AFTER contract_no; 
  
ALTER TABLE form_receipt_fee
  ADD COLUMN receipt_date DATETIME DEFAULT NULL COMMENT '收款时间' AFTER at_name;
  
ALTER TABLE form_payment_fee
  ADD COLUMN payment_date DATETIME DEFAULT NULL COMMENT '付款时间' AFTER at_name;  
 
  
####################################################################################


### 20161031 费用支付申请单添加报销事由
ALTER TABLE `born_fcs_fm`.`form_expense_application` 
   ADD COLUMN `reimburse_reason` TEXT NULL COMMENT '报销事由' AFTER `dept_head`;
   
### 20161105  为费用支付和差旅费添加打款状态
ALTER TABLE `born_fcs_fm`.`form_expense_application` 
   ADD COLUMN `account_status` VARCHAR(50) NULL COMMENT '打款状态' AFTER `attachments_num`;
   
ALTER TABLE `born_fcs_fm`.`form_travel_expense` 
   ADD COLUMN `account_status` VARCHAR(50) NULL COMMENT '打款状态' AFTER `attachments_num`;
   
##20161110  为转账表增加收款人信息
   alter table `born_fcs_fm`.`form_transfer` 
   add column `payee_id` bigint(20) NULL COMMENT '收款人id' after `agent`, 
   add column `payee` varchar(128) NULL COMMENT '收款人' after `payee_id`;
   
###############################################11-11##############################




###  20161114  为冲销子表添加已冲销标记

alter table `born_fcs_fm`.`form_expense_application_detail` 
   add column `reverse` varchar(20) NULL COMMENT '是否已冲销' after `amount`;
   
####################################### 11-14 ####################################   
   
   
###   20161117  添加修改记录表
CREATE TABLE `form_pay_change_detail` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `source_id` BIGINT(20) NOT NULL COMMENT '来源方ID',
   `source_type` VARCHAR(50) NOT NULL COMMENT '来源方类型',
   `user_id` BIGINT(20) DEFAULT NULL COMMENT '修改人id',
   `user_name` VARCHAR(50) DEFAULT NULL COMMENT '修改人名',
   `sort` BIGINT(20) NOT NULL COMMENT '序号',
   `document_name` VARCHAR(50) NOT NULL COMMENT '字段name属性',
   `document_describe` VARCHAR(128) DEFAULT NULL COMMENT '字段name描述',
   `document_value_old` VARCHAR(50) DEFAULT NULL COMMENT '字段旧value属性',
   `document_value_new` VARCHAR(50) DEFAULT NULL COMMENT '字段新value属性',
   `document_type` VARCHAR(11) DEFAULT 'input' COMMENT '字段类别-现在只有input',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='资金管理 - 支付管理- 支付属性修改记录'; 
 
 ### 20161117 添加冲销id集合 用于撤销终止单据后 取消已冲销状态
 alter table `born_fcs_fm`.`form_expense_application` 
   add column `cxids` varchar(512) NULL COMMENT '冲销记录id' after `reamount`;
   
##########################################11-18##########################################




### 20161121    为内部借款单添加单据类型

alter table `born_fcs_fm`.`form_inner_loan` 
   add column `inner_loan_type` varchar(50) NULL COMMENT '单据类型' after `form_id`;
   
   
##################################11-25#############################




## 20161209 来源单据ID 更定为varchar类型
alter table `born_fcs_fm`.`receipt_payment_form` 
   change `source_form_id` `source_form_id` varchar(50) NULL  comment '来源单据ID';
   
   alter table `born_fcs_fm`.`form_payment` 
   change `source_form_id` `source_form_id` varchar(50) NULL  comment '来源单据ID';
   
   alter table `born_fcs_fm`.`form_receipt` 
   change `source_form_id` `source_form_id` varchar(50) NULL  comment '来源单据ID';
   
######################################2016-12-20#################################   
   
   ## 20161221 添加待打款时间 
   alter table `born_fcs_fm`.`form_expense_application` 
   add column `wait_pay_time` datetime NULL COMMENT '待打款时间' after `account_status`;
   alter table `born_fcs_fm`.`form_travel_expense` 
   add column `wait_pay_time` datetime NULL COMMENT '待打款时间' after `account_status`;
   
##############################2016-12-23#############################   




   
   ## 2016-12-26 添加劳资表 
   
   
CREATE TABLE `form_labour_capital` (
   `labour_capital_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `form_id` BIGINT(20) NOT NULL COMMENT '表单ID',
   `bill_no` VARCHAR(32) NOT NULL COMMENT '单据号',
   `voucher_no` VARCHAR(32) DEFAULT NULL COMMENT '凭证号',
   `voucher_status` VARCHAR(50) DEFAULT NULL COMMENT '凭证同步状态',
   `voucher_sync_send_time` DATETIME DEFAULT NULL COMMENT '同步金碟财务系统发送时间(正常情况是审核通过时间)',
   `voucher_sync_finish_time` DATETIME DEFAULT NULL COMMENT '凭证号返回时间',
   `voucher_sync_message` TEXT COMMENT '凭证号同步结果',
   `expense_dept_id` BIGINT(20) DEFAULT NULL COMMENT '报销部门ID',
   `dept_name` VARCHAR(128) DEFAULT NULL COMMENT '部门名称',
   `dept_head` VARCHAR(32) DEFAULT NULL COMMENT '部门负责人',
   `reimburse_reason` TEXT COMMENT '报销事由',
   `is_official_card` VARCHAR(2) DEFAULT NULL COMMENT '公务卡支付',
   `application_time` DATETIME DEFAULT NULL COMMENT '申请日期',
   `relation_form` VARCHAR(128) DEFAULT NULL COMMENT '关联出差申请单',
   `agent_id` BIGINT(20) DEFAULT NULL COMMENT '经办人ID',
   `agent` VARCHAR(32) DEFAULT NULL COMMENT '经办人',
   `direction` VARCHAR(32) DEFAULT NULL COMMENT '费用方向',
   `payee_id` BIGINT(20) DEFAULT NULL COMMENT '收款人ID',
   `payee` VARCHAR(128) DEFAULT NULL COMMENT '收款人',
   `bank_id` BIGINT(20) DEFAULT NULL COMMENT '银行账户信息ID',
   `bank` VARCHAR(128) DEFAULT NULL COMMENT '开户银行',
   `bank_account` VARCHAR(128) DEFAULT NULL COMMENT '银行账号',
   `amount` BIGINT(20) DEFAULT '0' COMMENT '金额',
   `is_reverse` VARCHAR(3) DEFAULT NULL COMMENT '是否冲销',
   `reamount` BIGINT(20) DEFAULT '0' COMMENT '冲销金额',
   `cxids` VARCHAR(512) DEFAULT NULL COMMENT '冲销记录id',
   `pay_bank` VARCHAR(128) DEFAULT NULL COMMENT '付款银行',
   `pay_bank_account` VARCHAR(128) DEFAULT NULL COMMENT '付款银行账号',
   `shlc` VARCHAR(256) DEFAULT NULL COMMENT '审核流程',
   `attachments_num` INT(10) DEFAULT NULL COMMENT '附件数',
   `account_status` VARCHAR(50) DEFAULT NULL COMMENT '打款状态',
   `wait_pay_time` DATETIME DEFAULT NULL COMMENT '待打款时间',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (`labour_capital_id`),
   UNIQUE KEY `bill_no` (`bill_no`)
 ) ENGINE=INNODB AUTO_INCREMENT=1043 DEFAULT CHARSET=utf8 COMMENT='劳资申请单';
 
 
 
 CREATE TABLE `form_labour_capital_detail` (
   `detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `labour_capital_id` bigint(20) NOT NULL COMMENT '主表ID',
   `expense_type` varchar(32) DEFAULT NULL COMMENT '费用类型',
   `amount` bigint(20) DEFAULT '0' COMMENT '报销金额',
   `reverse` varchar(20) DEFAULT NULL COMMENT '是否已冲销',
   `tax_amount` bigint(20) DEFAULT '0' COMMENT '税金',
   `fp_amount` bigint(20) DEFAULT NULL COMMENT '发票冲销金额',
   `xj_amount` bigint(20) DEFAULT NULL COMMENT '现金冲销金额',
   `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
   `dept_name` varchar(128) DEFAULT NULL COMMENT '部门',
   `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
   `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (`detail_id`),
   KEY `labour_capital_id` (`labour_capital_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1371 DEFAULT CHARSET=utf8 COMMENT='劳资申请单明细';
 
 
 
   ##20170103 为内部借款单添加单据号
   
   alter table `born_fcs_fm`.`form_inner_loan` 
   add column `bill_no` varchar(32) NULL COMMENT '单据号' after `form_id`;
   
   
   
   ##20170307 外派劳务费修订为劳务费
   UPDATE `born_fcs_fm`.`cost_category` SET `name`='劳务费' WHERE `name`='外派劳务费';
   
 ##################################2019-03-17####################################  
 
 ##20170329付款单明细添加属性
 ALTER TABLE `fcs_fm`.`form_payment_fee` 
   ADD COLUMN `receipt_name` VARCHAR(50) NULL COMMENT '收款人名' AFTER `payment_account`, 
   ADD COLUMN `bank_name` VARCHAR(50) NULL COMMENT '开户行' AFTER `receipt_account`;
   
##########################################################################

 ##2017-05-10 费用支付 分支机构支付款确认付款   
ALTER TABLE form_expense_application
  ADD COLUMN branch_wait_pay_time DATETIME DEFAULT NULL COMMENT '分支机构待确认付款时间' AFTER wait_pay_time,
  ADD COLUMN branch_pay_time DATETIME DEFAULT NULL COMMENT '分支机构确认付款时间' AFTER branch_wait_pay_time,
  ADD COLUMN branch_pay_status VARCHAR(50) DEFAULT NULL COMMENT '分支机构付款状态：非分支机构、审核中、未付款、待付款' AFTER branch_pay_time; 
  
  ################################2017-5-15######################################
  
  ##2017-06-20 差旅费 分支机构支付款确认付款 
ALTER TABLE form_travel_expense 
ADD COLUMN `branch_wait_pay_time` datetime DEFAULT NULL COMMENT '分支机构待确认付款时间' AFTER wait_pay_time,
ADD COLUMN `branch_pay_time` datetime DEFAULT NULL COMMENT '分支机构确认付款时间' AFTER branch_wait_pay_time,
ADD COLUMN `branch_pay_status` varchar(50) DEFAULT NULL COMMENT '分支机构付款状态：非分支机构、审核中、未付款、待付款' AFTER branch_pay_time;

##lirz 2017-06-27 与税（房产土地、印花）相关的都和为税金
update cost_category set used='NO',status='BLOCK_UP' where name = '房产税';
update cost_category set used='NO',status='BLOCK_UP' where name = '车船使用税';
update cost_category set used='NO',status='BLOCK_UP' where name = '土地使用税';
update cost_category set used='NO',status='BLOCK_UP' where name = '印花税';
insert into cost_category (name, used, status, raw_add_time) values('税金', 'IS', 'NORMAL', now());

#################################################

##预测表新增字段
ALTER TABLE forecast_account
  ADD COLUMN project_code VARCHAR(50) DEFAULT NULL COMMENT '关联项目编号（理财项目编号）' AFTER order_no,
  ADD COLUMN customer_id BIGINT DEFAULT NULL COMMENT '关联客户ID（理财产品ID）' AFTER project_code,
  ADD COLUMN customer_name VARCHAR(250) DEFAULT NULL COMMENT '关联客户名称（理财产品名称）' AFTER customer_id,
  ADD COLUMN fee_type VARCHAR(200) DEFAULT NULL COMMENT '对应资金划付/收款通知费用类型（多个,隔开）' AFTER customer_name;

##订正历史数据
UPDATE fcs_fm.forecast_account
SET project_code = SUBSTRING(forecast_memo, POSITION('（' IN forecast_memo) + 1, POSITION('）' IN forecast_memo) - POSITION('（' IN forecast_memo) - 1)
WHERE POSITION('（' IN forecast_memo) > 0 AND system_form != 'FM';

UPDATE fcs_fm.forecast_account
SET project_code = SUBSTRING(forecast_memo, POSITION('(' IN forecast_memo) + 1, POSITION(')' IN forecast_memo) - POSITION('(' IN forecast_memo) - 1)
WHERE POSITION('(' IN forecast_memo) > 0 AND system_form != 'FM';

UPDATE fcs_fm.forecast_account f, fcs_pm.project p
SET f.customer_id = p.customer_id,
    f.customer_name = p.customer_name
WHERE f.project_code = p.project_code;

UPDATE fcs_fm.forecast_account f, fcs_pm.f_project_financial p
SET f.customer_id = p.product_id,
    f.customer_name = p.product_name
WHERE f.project_code = p.project_code;

UPDATE fcs_fm.forecast_account f, fcs_pm.project_financial p
SET f.customer_id = p.product_id,
    f.customer_name = p.product_name
WHERE f.project_code = p.project_code


UPDATE forecast_account f,forecast_account f1
SET f.fee_type =
CASE SUBSTRING(f1.forecast_memo, 1, POSITION('（' IN f1.forecast_memo) - 1) WHEN '保证金划出' THEN 'DEPOSIT_PAY' WHEN '委贷放款' THEN 'WD_LOAN' WHEN '理财产品购买' THEN 'BUY' WHEN '理财产品回购' THEN 'BUY_BACK' WHEN '费用支付' THEN 'EXPENSE' WHEN '劳资及税金' THEN 'LABOUR_EXPENSE' WHEN '差旅费报销' THEN 'TRAVEL_EXPENSE' WHEN '资产转让清收' THEN 'ASSET_TRANSFERQS' WHEN '代偿款划出' THEN 'COMP' WHEN '退还客户保证金' THEN 'REFOUND_DEPOSIT' WHEN '收取承销费' THEN 'CXFEE' WHEN '委贷本金收回' THEN 'REPLAYPLAN' WHEN '委贷利息收回' THEN 'CHARGEPLAN' WHEN '收取客户保证金' THEN 'DEPOSIT_CHARGE' WHEN '收取担保费' THEN 'GUARANTEE_FEE' WHEN '理财产品赎回' THEN 'REDEEM' WHEN '理财产品转让' THEN 'TRANSFER' WHEN '内部借款还款' THEN 'IN_INNER_LOAN' WHEN '资产转让' THEN 'ASSET_TANSFER' WHEN '代偿款收回' THEN 'COMPBACK' ELSE NULL
END
WHERE f.id = f1.id;

UPDATE forecast_account f,forecast_account f1
SET f.fee_type =
CASE SUBSTRING(f1.forecast_memo, 1, POSITION('(' IN f1.forecast_memo) - 1) WHEN '保证金划出' THEN 'DEPOSIT_PAY' WHEN '委贷放款' THEN 'WD_LOAN' WHEN '理财产品购买' THEN 'BUY' WHEN '理财产品回购' THEN 'BUY_BACK' WHEN '费用支付' THEN 'EXPENSE' WHEN '劳资及税金' THEN 'LABOUR_EXPENSE' WHEN '差旅费报销' THEN 'TRAVEL_EXPENSE' WHEN '资产转让清收' THEN 'ASSET_TRANSFERQS' WHEN '代偿款划出' THEN 'COMP' WHEN '退还客户保证金' THEN 'REFOUND_DEPOSIT' WHEN '收取承销费' THEN 'CXFEE' WHEN '委贷本金收回' THEN 'REPLAYPLAN' WHEN '委贷利息收回' THEN 'CHARGEPLAN' WHEN '收取客户保证金' THEN 'DEPOSIT_CHARGE' WHEN '收取担保费' THEN 'GUARANTEE_FEE' WHEN '理财产品赎回' THEN 'REDEEM' WHEN '理财产品转让' THEN 'TRANSFER' WHEN '内部借款还款' THEN 'IN_INNER_LOAN' WHEN '资产转让' THEN 'ASSET_TANSFER' WHEN '代偿款收回' THEN 'COMPBACK' ELSE NULL
END
WHERE f.id = f1.id;

#########################################################################

ALTER TABLE receipt_payment_form 
	ADD COLUMN is_simple varchar(10) DEFAULT 'NO' COMMENT '简单流程（IS/NO）' AFTER remark;
	
update receipt_payment_form set is_simple = 'NO';

ALTER TABLE form_payment
add COLUMN is_simple varchar(10) DEFAULT 'NO' COMMENT '简单流程（IS/NO）' AFTER remark;

update form_payment set is_simple = 'NO';