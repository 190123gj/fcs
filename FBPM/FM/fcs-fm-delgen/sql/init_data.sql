--------## 20160810 默认科目信息维护 

CREATE TABLE `sys_subject_message` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `subject_type` VARCHAR(50) DEFAULT NULL COMMENT '费用类型',
   `subject_cost_type` VARCHAR(50) DEFAULT NULL COMMENT '费用种类',
   `subject` VARCHAR(128) DEFAULT NULL COMMENT '默认科目',
   `adjust_project` VARCHAR(128) DEFAULT NULL COMMENT '默认核算项目',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '新增时间',
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='默认科目信息维护';
 
 
 --------##  银行账户信息维护
 
 CREATE TABLE `bank_message` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `bank_code` VARCHAR(50) DEFAULT NULL COMMENT '银行简称',
   `bank_name` VARCHAR(50) DEFAULT NULL COMMENT '开户银行',
   `account_type` VARCHAR(50) DEFAULT NULL COMMENT '资金类型',
   `account_no` VARCHAR(50) DEFAULT NULL COMMENT '账户号码',
   `account_name` VARCHAR(50) DEFAULT NULL COMMENT '户名',
   `subject` VARCHAR(128) DEFAULT NULL COMMENT '关联会计科目',
   `adjust_project` VARCHAR(128) DEFAULT NULL COMMENT '核算项目代码',
   `amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '账户初始余额',
   `cash_deposit_code` VARCHAR(50) DEFAULT NULL COMMENT '保证金账号代码',
   `status` VARCHAR(50) DEFAULT NULL COMMENT '账户状态',
   `department` VARCHAR(50) DEFAULT NULL COMMENT '部门/公司',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '新增时间',
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='银行账户信息维护';
 
 
 
 ---------## 20161008 默认科目信息表基础数据
 INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','GUARANTEE_FEE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','PROJECT_REVIEW_FEE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','ENTRUSTED_LOAN_INTEREST_WITHDRAWAL');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','CONSULT_FEE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','FINANCING_PRODUCT_TRANSFER');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','FINANCING_PRODUCT_TRANSFER_NOT');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','FINANCING_PRODUCT_REDEMPTION');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','INANCING_INVEST_INCOME');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','CONSIGNMENT_INWARD_INCOME');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','SOLD_INCOME');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','RECOVERY_INCOME');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','GUARANTEE_DEPOSIT');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','REFUNDABLE_DEPOSITS_DRAW_BACK');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','COMPENSATORY_PRINCIPAL_WITHDRAWAL');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','COMPENSATORY_INTEREST_WITHDRAWAL');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','COMPENSATORY_DEDIT_WITHDRAWAL');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','COMPENSATORY_OTHER_WITHDRAWAL');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','INNER_ACCOUNT_LENDING');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','EQUITY_INVESTMENT_INCOME');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','EQUITY_INVESTMENT_CAPITAL_BACK');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','INTEREST_INCOME');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','ASSETS_TRANSFER');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','OUTSIDE_FINANCING');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','PROXY_CHARGING');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('RECEIPT','RECEIPT_OTHER');








INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','COMMISSION_LOAN');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','FINANCIAL_PRODUCT');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','FINANCIAL_PRODUCT_BUY_BACK');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','DEPOSIT_PAID');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','CUSTOMER_DEPOSIT_REFUND');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','COMPENSATORY_PRINCIPAL');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','COMPENSATORY_INTEREST');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','COMPENSATORY_PENALTY');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','COMPENSATORY_LIQUIDATED_DAMAGES');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','COMPENSATORY_OTHER');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','PROXY_CHARGING_OUT');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','REFUND');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','INTERNAL_FUND_LENDING');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','FINANCING_PRINCIPAL_REPAYMENT');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','INTEREST_PAYMENTS__FINANCING_MATURITY');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','LONG_TERM_EQUITY_INVESTMENT_EXPENDITURE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','COMMISSION_LOAN_FEE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','OTHER_ASSET_SWAP');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','INTEREST_OUT');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','CASE_ACCEPTANCE_FEE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','PRESERVATION_FEE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','ANNOUNCEMENT_FEE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','APPRAISAL_FEE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','EXPERT_WITNESS_FEE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','LAWYER_FEE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','ASSESSMENT_FEE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','AUCTION_FEE');
INSERT INTO sys_subject_message(subject_type,subject_cost_type) VALUES('PAYMENT','PAYMENT_OTHER');




-------## 20161010 资金预测周期表
CREATE TABLE `sys_forecast_param` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `fund_direction` VARCHAR(50) NOT NULL COMMENT '资金流向 IN/OUT',
   `forecast_type` VARCHAR(50) DEFAULT NULL COMMENT '预测类型',
   `forecast_time` VARCHAR(20) DEFAULT NULL COMMENT '预测时间',
   `forecast_time_type` VARCHAR(20) DEFAULT NULL COMMENT '预测时间单位',
   `forecast_other_time` VARCHAR(20) DEFAULT NULL COMMENT '额外预测时间',
   `forecast_other_time_type` VARCHAR(20) DEFAULT NULL COMMENT '额外预测时间单位',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='资金预测周期表';

 ------------20161012 添加资金预测表
 CREATE TABLE `forecast_account` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `system_form` VARCHAR(50) NOT NULL COMMENT '来源系统',
   `order_no` VARCHAR(256) NOT NULL COMMENT '来源方唯一标识',
   `fund_direction` VARCHAR(50) NOT NULL COMMENT '资金流向 IN/OUT',
   `used_dept_id` VARCHAR(50) NOT NULL COMMENT '用款部门id',
   `used_dept_name` VARCHAR(50) NOT NULL COMMENT '用款部门名',
   `forecast_type` VARCHAR(50) NOT NULL COMMENT '预测类型',
   `forecast_start_time` TIMESTAMP NULL  DEFAULT NULL COMMENT '预计发生时间',
   `forecast_memo` VARCHAR(256) DEFAULT NULL COMMENT '发生事由',
   `amount` DECIMAL(17,0) NOT NULL DEFAULT '0'  COMMENT '金额',
   `last_update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '最近更新时间',
   `update_from` VARCHAR(50) DEFAULT NULL COMMENT '更新来源',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='资金出入预测表';
 
 
 
 ---------20161013 资金余额明细表
 
 CREATE TABLE `account_amount_detail` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `_time` TIMESTAMP NULL DEFAULT NULL COMMENT '时间',
   `start_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT '期初余额',
   `forecast_in_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT '预计收款金额',
   `forecast_out_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT '预计用款金额',
   `forecast_last_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT '预计账户余额',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='资金余额明细表';