/*--客户是否处在签报中 */  
  alter table `customer_base_info` 
   add column `change_status` enum('IS','NO') DEFAULT 'NO' NULL COMMENT '是否处在修改签报中' after `evalue_status`; 
   
  /**业务时间格式修改 2016-10-08*/ 
   alter table `channal_info` 
   change `credit_start_date` `credit_start_date` datetime default '0000-00-00 00:00:00' NULL  comment '授信起始日', 
   change `credit_end_date` `credit_end_date` datetime default '0000-00-00 00:00:00' NULL  comment '授信截止日';
   
   alter table `company_qualification` 
   change `exper_date` `exper_date` datetime default '0000-00-00 00:00:00' NOT NULL comment '证书有效期';
   
   alter table `customer_company_detail` 
   change `established_time` `established_time` datetime default '0000-00-00 00:00:00' NULL  comment '成立时间';
   
   alter table `evalueting_list` 
   change `audit_time` `audit_time` datetime default '0000-00-00 00:00:00' NOT NULL comment '审定日期', 
   change `evalueting_time` `evalueting_time` datetime default '0000-00-00 00:00:00' NULL  comment '外部_评级日期';
   
   alter table `personal_company` 
   change `per_regist_date` `per_regist_date` datetime default '0000-00-00 00:00:00' NULL  comment '注册时间';
   
  /**评级列表 2016-10-13*/
   alter table `evalueting_list` 
   change `audit_time` `audit_time` datetime default '0000-00-00 00:00:00' NULL  comment '审定日期';
   
 ################ 2016-10-18 以上已执行 ####################  
 ##20161027 企业客户增加币种 
 ALTER TABLE `customer_company_detail` 
   ADD COLUMN `money_type` VARCHAR(32) NULL COMMENT '币种类型' AFTER `register_capital`, 
   ADD COLUMN `money_type_name` VARCHAR(32) NULL COMMENT '币种名' AFTER `money_type`;
################ 2016-10-22 以上已执行 #################### 

## 2016-10-25 保存客户经理的所有部门
alter table `customer_base_info` 
   add column `dep_path` varchar(200) NULL COMMENT '包含所有部门' after `dep_name` ;  
   
##2016-10-26 风险提示信息保存
alter table `cutomer_info_verify_message` 
   change `mobile_message` `mobile_message` mediumtext NULL  comment '手机号校验提示', 
   change `card_message` `card_message` mediumtext NULL  comment '实名校验提示';
################ 2016-10-26 以上已执行 ####################    
   
##2016-10-27 渠道表修改
alter table `channal_info` 
   add column `contract_no` varchar(16) NULL COMMENT '关联合同编号' after `is_history`;
   
##2016-10-31 个人客户表修改
alter table `customer_person_detail` 
   change `cert_img` `cert_img` mediumtext NULL  comment '证件照', 
   change `cert_img_font` `cert_img_font` mediumtext NULL  comment '证件照正面', 
   change `cert_img_back` `cert_img_back` mediumtext NULL  comment '证件照反面';
##2016-10-31 渠道合同表
alter table `channal_contract` 
   change `contract` `contract` mediumtext NULL  comment '合同类容', 
   change `contract_no` `contract_no` varchar(16) character set utf8 collate utf8_general_ci NULL  comment '合同编号';
################ 2016-11- 以上已执行 ####################

##2016-11-9 客户表修改
alter table `customer_base_info` 
   add column `contact_mobile` varchar(50) NULL COMMENT '联系电话' after `customer_name`;
################ 2016-11-11前以上已执行 #################### 

##2016-12-7 客户资质证书
alter table`company_qualification` 
   change `qualification_name` `qualification_name` varchar(100) character set utf8 collate utf8_general_ci NOT NULL comment '资质证书名', 
   change `exper_date` `exper_date` datetime default '0000-00-00 00:00:00' NULL  comment '证书有效期';

##2017-2-22 企业客户相亲表加字段
ALTER TABLE `customer_company_detail` 
   ADD COLUMN `total_asset_last_year` BIGINT NULL COMMENT '去年总资产' AFTER `total_asset`, 
   ADD COLUMN `net_asset_last_year` BIGINT NULL COMMENT '去年净资产' AFTER `net_asset`, 
   ADD COLUMN `asset_liability_ratio_last_year` DECIMAL(12,4) DEFAULT '0.0000' NULL  comment '资产负债率' AFTER `asset_liability_ratio`,
   CHANGE `total_asset` `total_asset` BIGINT(20) DEFAULT '0' NULL  COMMENT '总资产', 
   CHANGE `net_asset` `net_asset` BIGINT(20) DEFAULT '0' NULL  COMMENT '净资产', 
   CHANGE `asset_liability_ratio` `asset_liability_ratio` DECIMAL(12,4) DEFAULT '0.0000' NULL  COMMENT '资产负债率';
   