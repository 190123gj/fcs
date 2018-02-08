
#fei20160322
alter table council drop column judges, drop column participants, drop column projects;

#fei20160323
alter table council 
   add column decision_institution_id bigint(20) NULL COMMENT '决策机构ID' after status
   
 #fei20160323  
alter table council_projects 
   change council_id council_id bigint(20) NULL  comment '会议ID'   
   
#lirz 20160323
alter table f_investigation_mainly_review add column query_time datetime NULL COMMENT '客户信用状况查询时间' after busi_place;
alter table f_investigation_mainly_review_credit_status drop column query_time;
DROP TABLE IF EXISTS f_investigation_mainly_review_stockholder;
CREATE TABLE f_investigation_mainly_review_stockholder (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  m_review_id bigint(20) NOT NULL COMMENT '对应客户评价ID',
  holder_name varchar(50) DEFAULT NULL COMMENT '股东名称',
  holder_type varchar(50) DEFAULT NULL COMMENT '股东性质',
  holder_cert_type varchar(50) DEFAULT NULL COMMENT '股东证件类型',
  holder_cert_no varchar(50) DEFAULT NULL COMMENT '股东证件号码(注册号)',
  actual_investment bigint(20) DEFAULT NULL COMMENT '实际投资',
  paidin_capital_ratio decimal(12,4) DEFAULT NULL COMMENT '占实收资本比例',
  holder_major_busi varchar(128) DEFAULT NULL COMMENT '股东主营业务',
  capital_scale bigint(20) DEFAULT NULL COMMENT '股东规模（资产）',
  produce_scale bigint(20) DEFAULT NULL COMMENT '股东规模（生产能力）',
  income_scale bigint(20) DEFAULT NULL COMMENT '股东规模（收入）',
  holder_contact_no varchar(16) DEFAULT NULL COMMENT '股东手机号',
  marital_status varchar(128) DEFAULT NULL COMMENT '婚姻情况',
  spouse_name varchar(50) DEFAULT NULL COMMENT '配偶姓名',
  spouse_cert_type varchar(10) DEFAULT NULL COMMENT '配偶证件类型',
  spouse_cert_no varchar(10) DEFAULT NULL COMMENT '配偶证件号码',
  spouse_contact_no varchar(10) DEFAULT NULL COMMENT '配偶联系电话(手机)',
  sort_order int(11) DEFAULT '0',
  raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目调查 - 客户主体评价 - 主要股东情况表';

#wuzj 20160323
alter table f_council_summary 
   add column initiator_id bigint NULL COMMENT '会议召开人ID' after council_code, 
   add column initiator_account varchar(20) NULL COMMENT '会议召开人账号' after initiator_id, 
   add column initiator_name varchar(50) NULL COMMENT '会议召开人名称' after initiator_account,
   change overview overview text NULL  comment '简述'

#wuzj 20160325  
alter table f_council_summary_project drop column loan_start_time, drop column loan_end_time, drop column loan_type, drop column loan_purpose, drop column repay_source, drop column repay_plan, drop column co_institution_id, drop column co_institution_name,
   add column customer_type varchar(20) NULL COMMENT '客户类型' after customer_name, 
   add column busi_type_name varchar(50) NULL COMMENT '业务类型名称' after busi_type, 
   add column repay_way varchar(20) NULL COMMENT '还款方式 多次/一次' after time_unit, 
   add column charge_way varchar(20) NULL COMMENT '收费方式 多次/一次' after repay_way, 
   add column charge_base varchar(20) NULL COMMENT '收费基数基准' after charge_way,
   add column loan_purpose varchar(512) NULL COMMENT '授信用途' after busi_type_name,
   add column is_repay_equal varchar(10) DEFAULT '\'NO\'' NULL COMMENT '是否连续年度等额偿还' after repay_way, 
   add column is_charge_every_beginning varchar(10) DEFAULT '\'NO\'' NULL COMMENT '首次收费外,是否以后为每年度期初' after charge_way,   
   change customer_name customer_name varchar(128) character set utf8 collate utf8_general_ci NULL  comment '对应客户名称', 
   change busi_type busi_type varchar(10) character set utf8 collate utf8_general_ci NULL  comment '业务类型', 
   change amount amount bigint(20) NULL  comment '拟发行金额/保全金额/授信金额', 
   change time_unit time_unit varchar(10) character set utf8 collate utf8_general_ci NULL  comment '担保期限单位'   
   change id sp_id bigint(20) NOT NULL AUTO_INCREMENT comment '主键', 
   
   
   
     #feihong 20160326 默认未投票 
alter table council_project_vote 
   change vote_status vote_status varchar(10) character set utf8 collate utf8_general_ci default 'NO' NULL  comment '投票状态(是否已投票)';
   
 
#feihong 20160326 投票总体结果
alter table council_projects 
   add column voteResult varchar(50)  NULL COMMENT '投票结果',
   add column voteRatio varchar(50)  NULL COMMENT '投票比例';
   
  #feihong 20160329 一票否决 
   alter table council_projects 
   add column one_vote_down varchar(20) DEFAULT 'NO' NULL COMMENT '是否被一票否决' after sort_order
  
#lirz 20160328 方便数据的保存
CREATE TABLE f_investigation_project_status_fund (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  status_id bigint(20) NOT NULL COMMENT '项目情况调查ID',
  item varchar(32) DEFAULT NULL COMMENT '项目',
  item_code varchar(32) DEFAULT NULL COMMENT '项目标识',
  item_amount bigint(20) DEFAULT NULL COMMENT '项目金额',
  item_percent decimal(12,4) DEFAULT NULL COMMENT '项目百分比',  
  fund_source varchar(32) DEFAULT NULL COMMENT '资金来源',
  fund_code varchar(32) DEFAULT NULL COMMENT '资金来源标识',
  fund_amount bigint(20) DEFAULT NULL COMMENT '资金金额',
  fund_percent decimal(12,4) DEFAULT NULL COMMENT '资金百分比',
  sort_order int(11) DEFAULT '0',
  raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目调查 - 项目情况调查 - 项目投资与资金筹措表';

#lirz 20160329
alter table f_investigation_litigation 
   add column co_institution_id bigint NULL COMMENT '合作机构ID' after guarantee_amount, 
   change coop_institution co_institution_name varchar(128) character set utf8 collate utf8_general_ci NULL  comment '合作机构名称';
   
#feihong 20160330    
alter table council_judges 
   add column judge_account varchar(20) NULL COMMENT '评委账号' after judge_name;   
   
   alter table council 
   change council_type council_type bigint(20) NULL  comment '会议类型';
   
   
   alter table council_type 
   change decision_institution_id decision_institution_id bigint(20) NOT NULL comment '决策机构ID';

#lirz 20160405 表结构变动较大
CREATE TABLE f_investigation_credit_scheme_process_control (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  scheme_id bigint(20) NOT NULL COMMENT '授信方案ID f_investigation_credit_scheme.scheme_id',
  type varchar(20) DEFAULT NULL COMMENT '客户主体评价/资产负债率/其它',
  credit_level varchar(32) DEFAULT NULL COMMENT '主体评级',
  level_desc varchar(32) DEFAULT NULL COMMENT '评级描述',  
  adjust_type varchar(32) DEFAULT NULL COMMENT '评级方式(每下调一级)',
  adjust_desc varchar(32) DEFAULT NULL COMMENT '评级方式描述',
  asset_liability_ratio varchar(32) DEFAULT NULL COMMENT '资产负责率',
  threshold_ratio varchar(32) DEFAULT NULL COMMENT '资产负责率警戒值',
  adjust_ratio varchar(32) DEFAULT NULL COMMENT '每超过(%)',
  up_down varchar(50) DEFAULT NULL COMMENT '上调/下调',
  up_down_rate decimal(12,4) DEFAULT NULL COMMENT '上调/下调百分点',
  content varchar(128) DEFAULT NULL COMMENT '过程控制内容',
  raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY scheme_id_i (scheme_id)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='项目调查 - 授信方案 - 过程控制(客户主体评级/资产负债率/其他)';


##feihong 20160405

CREATE TABLE f_charge_notification (
   notification_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
   form_id BIGINT(20) NOT NULL COMMENT '对应表单ID',
   project_code VARCHAR(50) DEFAULT NULL COMMENT '对应项目编号',
   project_name VARCHAR(128) DEFAULT NULL COMMENT '对应项目名称',
   customer_id BIGINT(20) DEFAULT NULL COMMENT '客户ID',
   customer_name VARCHAR(50) DEFAULT NULL COMMENT '客户名称',
   is_agent_pay VARCHAR(50) DEFAULT NULL COMMENT '是否代付',
   pay_amount BIGINT(20) DEFAULT NULL COMMENT '付款金额',
   pay_name VARCHAR(50) DEFAULT NULL COMMENT '付款方户名',
   pay_account VARCHAR(30) DEFAULT NULL COMMENT '付款账号',
   pay_bank VARCHAR(30) DEFAULT NULL COMMENT '付款银行',
   pay_time DATETIME DEFAULT NULL COMMENT '付款时间',
   remark VARCHAR(512) DEFAULT NULL COMMENT '备注',
   raw_add_time TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
   raw_update_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (notification_id),
   KEY f_charge_notification_form_id_i (form_id),
   KEY f_charge_notification_project_code_i (project_code)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='收费通知';
 
 
 
 ##feihong 20160405
 CREATE TABLE f_charge_notification_fee (
   id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   notification_id bigint(20) NOT NULL COMMENT '收费通知ID',
   type varchar(20) DEFAULT NULL COMMENT '费用/保证金',
   charge_type varchar(20) DEFAULT NULL COMMENT '收费类型',
   charge_type_desc varchar(20) DEFAULT NULL COMMENT '收费类型描述',
   charge_way varchar(50) DEFAULT NULL COMMENT '收费方式（一次性收取/按月等）',
   charge_unit varchar(50) DEFAULT NULL COMMENT '按金额收取/按费率收取',
   charge_amount bigint(20) DEFAULT NULL COMMENT '收取金额',
   charge_rate decimal(12,4) DEFAULT NULL COMMENT '收取费率',
   actual_charge_amount bigint(20) DEFAULT NULL COMMENT '实际收取金额',
   actual_charge_amount_check bigint(20) DEFAULT NULL COMMENT '财务确认的收取金额',
   start_time datetime DEFAULT NULL COMMENT '计费开始时间',
   end_time datetime DEFAULT NULL COMMENT '计费结束时间',
   remark varchar(128) DEFAULT NULL COMMENT '收费备注',
   sort_order int(11) DEFAULT NULL,
   raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
   raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收费通知 - 收费明细';
 
 
   ##feihong 20160406
 alter table f_charge_notification_fee 
   change charge_type fee_type varchar(20) character set utf8 collate utf8_general_ci NULL  comment '收费类型', 
   change charge_type_desc fee_type_desc varchar(20) character set utf8 collate utf8_general_ci NULL  comment '收费类型描述';

   
   
   
  ##feihong 20160407  
 alter table f_charge_notification_fee drop column type, 
 drop column charge_unit, 
 drop column actual_charge_amount,
 drop column actual_charge_amount_check,
   change charge_way charge_base varchar(50) character set utf8 collate utf8_general_ci NULL  comment '收费方式（一次性收取/按月等）';  
   
   
   
DROP TABLE  f_pay_notification;
DROP TABLE  f_pay_notification_fee;

#lirz 20160408
alter table f_investigation 
   add column busi_type varchar(10) NULL COMMENT '业务类型' after customer_name, 
   add column busi_type_name varchar(50) NULL COMMENT '业务类型名称' after busi_type,
   add column declares varchar(32) NULL COMMENT '声明不存在以下情形' after busi_type_name;

#lirz 20160409
alter table f_risk_warning 
   add column customer_id bigint NULL COMMENT '对应客户ID' after form_id, 
   add column customer_name varchar(128) NULL COMMENT '对应客户名称' after customer_id, 
   add column signal_level varchar(32) NULL COMMENT '信号等级' after customer_name,
   add column special_signal_desc text COMMENT '特别预警(special)详细信息' after special_signal,
   add column nomal_signal_desc text COMMENT '一般预警(nomal)详细信息' after nomal_signal;
 
   
   
#huangfei 20160409   
alter table f_afterwards_check 
   add column rounds varchar(50) NULL COMMENT '保后检查期次' after check_date ;  
 
   
#lirz 20160411
CREATE TABLE expire_project (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(20) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(128) DEFAULT NULL COMMENT '项目名称',
  expire_date datetime DEFAULT NULL COMMENT '到期时间',
  status varchar(20) DEFAULT NULL COMMENT '到期状态：到期、逾期、已完成',
  repay_certificate varchar(200) DEFAULT NULL COMMENT '还款凭证地址',
  raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  KEY f_financial_project_project_code_i (project_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='到期项目列表';

alter table f_investigation_credit_scheme 
   add column industry_code varchar(50) NULL COMMENT '行业编码' after customer_name, 
   add column industry_name varchar(128) NULL COMMENT '行业名称' after industry_code,
   change customer_name customer_name varchar(128) NULL  comment '对应客户名称';
   
   
   
#huangfei 20160412
alter table f_afterwards_check_content_income_cost drop column cost_remark, drop column check_content,
   add column item varchar(128) NULL COMMENT '科目' after customer_name, 
   add column item_type varchar(20) NULL COMMENT '科目类别（企业收入/企业成本）' after item,
   change incom_remark remark varchar(512) character set utf8 collate utf8_general_ci NULL  comment '说明'; 
   
#huangfei 20160412   
alter table f_afterwards_check_content_income_cost_kpi drop column kpi_type,
   change kpi_code item varchar(128) character set utf8 collate utf8_general_ci NULL  comment '科目', 
   change kpi_name item_code varchar(50) character set utf8 collate utf8_general_ci NULL  comment '指标名', 
   change kpi_value item_value varchar(20) character set utf8 collate utf8_general_ci NULL  comment '指标结果值', 
   change kpi_unit item_unit varchar(20) character set utf8 collate utf8_general_ci NULL  comment '指标单位', 
   change term_type term varchar(20) character set utf8 collate utf8_general_ci NULL  comment '期次（账期yyyyMM）'  
   
   
 #huangfei 20160412     

   
   DROP TABLE  f_afterwards_check_asset_item;
   
   DROP TABLE  f_afterwards_check_asset;
   
   
   
  #huangfei 20160412  
CREATE TABLE f_afterwards_check_asset_liability (
   asset_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
   project_code varchar(20) DEFAULT NULL COMMENT '对应项目编号',
   project_name varchar(128) DEFAULT NULL COMMENT '对应项目名称',
   customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
   customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
   item_type varchar(50) DEFAULT NULL COMMENT '资产/负债',
   item_sub_type varchar(50) DEFAULT NULL COMMENT '科目子类型',
   remark text COMMENT '现场查看说明',
   sort_order int(11) DEFAULT '0',
   raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
   raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (asset_id),
   KEY f_afterwards_check_asset_form_id_id (form_id),
   KEY f_afterwards_check_asset_project_code_i (project_code)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='保后检查 - 企业资产/负债情况';
 
 
  #huangfei 20160412  
CREATE TABLE f_afterwards_check_asset_liability_item (
   id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   asset_id bigint(20) NOT NULL COMMENT '资产类别ID',
   item varchar(50) NOT NULL COMMENT '科目明细/交易对手/对方单位',
   original_receipt_amount bigint(20) DEFAULT NULL COMMENT '原始单据核实金额',
   original_receipt_num int(11) DEFAULT NULL COMMENT '原始单据份数',
   original_receipt_age varchar(20) DEFAULT NULL COMMENT '原始单据账龄',
   estimated_bad_amount bigint(20) DEFAULT NULL COMMENT '估计坏账金额',
   sort_order int(11) DEFAULT '0',
   raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
   raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (id)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='保后检查 - 企业资产/负债明细';
   
alter table f_investigation_credit_scheme 
   add column process_flag varchar(32) NULL COMMENT '过程标志' after statement;
   
   
   #huangfei 20160414    
alter table f_afterwards_check_content 
   add column other_explain text NULL COMMENT '其他需说明的事项' after analysis_conclusion, 
   add column related_enterprise text NULL COMMENT '关联企业检查情况' after other_explain, 
   add column management_matters text NULL COMMENT '重大经营管理事项检查' after related_enterprise, 
   add column customer_suggestion text NULL COMMENT '授信客户意见' after management_matters, 
   add column feedback text NULL COMMENT '向授信客户反馈意见' after customer_suggestion, 
   add column use_way_conditions text NULL COMMENT '授信的用途、还息及纳税检查' after feedback,
   change raw_add_time raw_add_time timestamp default CURRENT_TIMESTAMP NULL  comment '新增时间'   

#lirz 20160414
CREATE TABLE f_investigation_cs_rationality_review_guarantor_ability (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  csr_review_id bigint(20) NOT NULL COMMENT '授信方案主要事项合理性评价ID',
  guarantor varchar(20) DEFAULT NULL COMMENT '保证人姓名',
  ability_level varchar(20) DEFAULT NULL COMMENT '担保能力评级',
  total_capital bigint(20) DEFAULT NULL COMMENT '上年净资产总额',
  intangible_assets bigint(20) DEFAULT NULL COMMENT '除土地使用权以外的无形资产',
  contingent_liability bigint(20) DEFAULT NULL COMMENT '或有负债',
  guarantee_amount bigint(20) DEFAULT NULL COMMENT '对外可提供担保额度',
  sort_order int(11) DEFAULT '0',
  raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目调查 - 授信方案主要事项合理性评价 - 保证人保证能力总体评价';


#lirz 20160415
alter table f_risk_warning 
   add column special_signal varchar(256) NULL COMMENT '特别预警(special)ID加逗号分隔 risk_warning_signal.id' after signal_type, 
   add column nomal_signal varchar(256) NULL COMMENT '一般预警(nomal)ID加逗号分隔 risk_warning_signal.id' after special_signal;

DROP TABLE IF EXISTS f_risk_warning_signal;

CREATE TABLE risk_warning_signal (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  signal_type varchar(32) DEFAULT NULL COMMENT '客户类型(公司类，金融类，小微企业等)',
  signal_level varchar(32) NOT NULL COMMENT '信号种类',
  signal_type_name varchar(128) DEFAULT NULL COMMENT '信号种类名称',
  raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='风险预警处理 - 风险预警信号数据表';

CREATE TABLE f_risk_warning_credit (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  warning_id bigint(20) NOT NULL COMMENT 'f_risk_warning.warning_id',
  issue_date datetime DEFAULT NULL COMMENT '发放日期',
  expire_date datetime DEFAULT NULL COMMENT '到期日期',
  loan_amount bigint(20) NOT NULL COMMENT '借款/担保余额',
  debit_interest bigint(20) NOT NULL COMMENT '欠息/费金额',
  sort_order int(11) NOT NULL DEFAULT '0',
  raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  KEY f_risk_warning_credit_warning_id_i (warning_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='风险预警处理表 -- 授信业务基本情况';

#lirz 20160419
DROP TABLE IF EXISTS f_council_apply_risk_handle;
CREATE TABLE f_council_apply_risk_handle (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  apply_id bigint(20) NOT NULL COMMENT '对应上会申请ID',
  form_id bigint(20) DEFAULT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(128) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '客户ID',
  customer_name varchar(50) DEFAULT NULL COMMENT '客户名称',
  is_repay varchar(10) DEFAULT NULL COMMENT '是否代偿:Y/N',
  company_name varchar(128) DEFAULT NULL COMMENT '企业名称',
  enterprise_type varchar(20) DEFAULT NULL COMMENT '企业性质',
  guarantee_amount bigint(20) DEFAULT NULL COMMENT '担保金额',
  guarantee_rate decimal(12,4) DEFAULT NULL COMMENT '担保费率',
  loan_bank varchar(128) DEFAULT NULL COMMENT '放款银行',
  loan_time_limit varchar(128) DEFAULT NULL COMMENT '放款期限',
  busi_manager_name varchar(50) DEFAULT NULL COMMENT '客户经理名称',
  risk_manager_name varchar(50) DEFAULT NULL COMMENT '风险经理名称',
  repay_amount bigint(20) DEFAULT NULL COMMENT '代偿金额',
  repay_date datetime DEFAULT NULL COMMENT '代偿日期',
  loan_type varchar(50) DEFAULT NULL COMMENT '授信品种',
  credit_time_limit varchar(50) DEFAULT NULL COMMENT '授信期限',
  credit_amount bigint(20) DEFAULT NULL COMMENT '授信金额',
  basic_info text COMMENT '基本情况',
  risk_info text COMMENT '风险事项',
  last_council_decision text COMMENT '前次风险处置会决议事项',
  last_council_check text COMMENT '前次风险处置会决议落实情况',
  this_council_scheme text COMMENT '本次上会提交的代偿方案及追偿方案/本次上会提交的处置方案',
  status varchar(10) DEFAULT NULL COMMENT '状态',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY f_council_apply_risk_handle_apply_id_i (apply_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目上会申请 - 风险处置会/代偿项目上会';

CREATE TABLE f_council_apply_credit (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  handle_id bigint(20) NOT NULL COMMENT 'f_council_apply_risk_handle.id',
  issue_date datetime DEFAULT NULL COMMENT '授信开始日期',
  expire_date datetime DEFAULT NULL COMMENT '授信到期日期',
  loan_amount bigint(20) NOT NULL COMMENT '应还款额',
  debit_interest bigint(20) NOT NULL COMMENT '欠息/费金额',
  sort_order int(11) NOT NULL DEFAULT '0',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  KEY f_council_apply_credit_handle_id_i (handle_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目上会申报 -- 授信业务基本情况';

#feihong 20160419
alter table f_afterwards_check 
   add column edition varchar(32) NULL COMMENT '报告类型（版本）' after customer_name;
   
   
 #feihong 20160419  
 CREATE TABLE f_project_contract (
   contract_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '合同集ID',
   form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
   project_code varchar(20) DEFAULT NULL COMMENT '对应项目编号',
   project_name varchar(128) DEFAULT NULL COMMENT '对应项目名称',
   customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
   customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
   raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
   raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (contract_id),
   KEY f_project_contract_form_id_i (form_id),
   KEY f_project_contract_project_code_i (project_code)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授信前管理 - 项目合同集';
 
 CREATE TABLE f_project_contract_item (
   id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   contract_id bigint(20) NOT NULL COMMENT '合同集ID',
   contract_name varchar(20) DEFAULT NULL COMMENT '合同名',
   pledge_id bigint(20) DEFAULT NULL COMMENT '授信条件ID',
   template_id bigint(20) DEFAULT NULL COMMENT '合同模板',
   contract_type varchar(512) DEFAULT NULL COMMENT '合同类型',
   is_main varchar(10) DEFAULT NULL COMMENT '是否主合同',
   stamp_phase varchar(128) DEFAULT NULL COMMENT '用印阶段',
   cnt bigint(10) DEFAULT NULL COMMENT '一式几（份）',
   file_url varchar(256) DEFAULT NULL COMMENT '合同附件',
   content text COMMENT '合同内容',
   remark varchar(2000) DEFAULT NULL COMMENT '备注',
   audit_info varchar(2000) DEFAULT NULL COMMENT '审核意见',
   sort_order int(11) DEFAULT '0',
   raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
   raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (id)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授信前管理 - 项目合同集- 合同';
 
 
CREATE TABLE contract_template (
   template_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   name varchar(128) NOT NULL COMMENT '模板名',
   contract_type varchar(50) NOT NULL COMMENT '模板类别(制式/非制式)',
   busi_type varchar(50) DEFAULT NULL COMMENT '业务类型',
   is_main varchar(10) DEFAULT NULL COMMENT '是否为某业务类型主合同',
   credit_condition_type varchar(50) DEFAULT NULL COMMENT '反担保措施',
   pledge_type varchar(50) DEFAULT NULL COMMENT '抵押品类型',
   stamp_phase varchar(50) DEFAULT NULL COMMENT '用印阶段',
   template_file varchar(256) DEFAULT NULL COMMENT '模板附件（非制式）',
   template_content text COMMENT '模板内容（制式）',
   raw_add_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   raw_update_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
   PRIMARY KEY (template_id)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='合同模板';


ALTER TABLE f_loan_use_apply
  ADD COLUMN cash_deposit_end_time DATETIME DEFAULT NULL COMMENT '保证金划付截止时间' AFTER cash_deposit_ratio;
  
ALTER TABLE f_loan_use_apply_receipt
  ADD COLUMN other_amount bigint(20) DEFAULT NULL COMMENT '其他金额' AFTER credit_letter_guarantee_amount,
    ADD COLUMN othera_amount_name varchar(256) DEFAULT NULL COMMENT '其他金额描述' AFTER other_amount;
    
    
    
alter table f_afterwards_check 
   add column check_address varchar(256) NULL COMMENT '现场监管地址' after check_date  ;
   
   
alter table f_afterwards_check_collection_doc drop column doc_type, drop column collect_term,
   change collect_id form_id bigint(20) NOT NULL comment '对应表单ID', 
   change doc_type_desc doc_name varchar(50) character set utf8 collate utf8_general_ci NULL  comment '附件名称', 
   change doc_url doc_url varchar(128) character set utf8 collate utf8_general_ci NULL  comment '附件位置', Engine=InnoDB checksum=1 auto_increment=1 comment='保后检查 - 附件上传' delay_key_write=1 row_format=dynamic charset=utf8 collate=utf8_general_ci ;
  
   
alter table f_afterwards_check 
   add column edition varchar(32) NULL COMMENT '报告类型（版本）' after check_address   ;
   
   
   
alter table f_afterwards_check_content_counter_guarantee 
   change check_content type varchar(20) NULL  comment '关注事项类型（担保措施检查|其他重要事项核查|预警信号）'   ;
   
   
   

drop  table f_afterwards_check_content_counter_guarantee;
/*
CREATE TABLE f_afterwards_check_content_counter_guarantee (
   cg_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '反担保情况ID',
   form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
   project_code varchar(20) DEFAULT NULL COMMENT '对应项目编号',
   project_name varchar(128) DEFAULT NULL COMMENT '对应项目名称',
   customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
   customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
   type varchar(20) DEFAULT NULL COMMENT '关注事项类型（担保措施检查|其他重要事项核查|预警信号）',
   other text COMMENT '检查内容(其他重要事项检查)',
   raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
   raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (cg_id),
   KEY f_afterwards_check_content_counter_guarantee_form_id_i (form_id),
   KEY f_afterwards_check_content_counter_guarantee_peoject_code_i (project_code)
 ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='保后检查 - 监管内容 - 反担保措施检查' ;
 */
     


drop  table f_afterwards_check_content_counter_guarantee_item;
/*
CREATE TABLE f_afterwards_check_content_counter_guarantee_item (
   id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   cg_id bigint(20) NOT NULL COMMENT '反担保情况ID',
   type varchar(10) DEFAULT NULL COMMENT '类型(担保措施检查/其他重要事项检查)',
   item_type varchar(20) DEFAULT NULL COMMENT '关注事项类型标识',
   item_type_desc varchar(512) DEFAULT NULL COMMENT '关注事项描述',
   item_value varchar(512) DEFAULT NULL COMMENT '事项值',
   remark varchar(128) DEFAULT NULL COMMENT '说明',
   sort_order int(11) DEFAULT '0',
   raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
   raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (id)
 ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='保后检查 - 监管内容 - （反）担保措施检查事项';
*/       
 


#lirz 20160421
alter table f_investigation_underwriting 
   add column financing_amount bigint(20) NULL COMMENT '本次申请融资金额' after project_gist,
   change collect_scale collect_scale bigint(20) NULL  comment '募集规模';
   
   
alter table f_project 
   add column industry_code varchar(128) NULL COMMENT '所属行业' after busi_type_name;   


#lirz 20160425
alter table f_investigation_underwriting 
   add column law_office_unit varchar(10) NULL COMMENT '律所费率单位(%/年,单)' after law_office_rate,
   add column club_unit varchar(10) NULL COMMENT '会费率单位(%/年,单)' after club_rate,
   add column other_unit varchar(10) NULL COMMENT '其他费率单位(%/年,单)' after other_rate,
   add column underwriting_unit varchar(10) NULL COMMENT '承销费率单位(%/年,单)' after underwriting_rate;
 
   
   
ALTER TABLE council_type 
   ADD COLUMN apply_dept_id BIGINT(20) NULL COMMENT '适用公司/部门id' AFTER decision_institution_name;   
 
#Ji 20160503
alter table decision_institution 
   add column delete_mark varchar(1) DEFAULT '0' COMMENT '删除标记(0:活动,1:删除)' after institution_members;
alter table decision_member 
   add column delete_mark varchar(1) DEFAULT '0' COMMENT '删除标记(0:活动,1:删除)' after sort_order;
alter table council_type 
   add column delete_mark varchar(1) DEFAULT '0' COMMENT '删除标记(0:活动,1:删除)' after summary_code_prefix,
   add column apply_dept_id bigint(20) DEFAULT NULL COMMENT '适用公司/部门id' after decision_institution_name;
   
#20160504   
alter table f_risk_review_report 
   add column case_info text NULL COMMENT '案情介绍' after report_content, 
   add column preserve_content text NULL COMMENT '拟保全标的或内容' after case_info, 
   add column audit_opinion text NULL COMMENT '风险审查意见' after preserve_content, 
   add column synthesize_opinion text NULL COMMENT '项目综合意见' after audit_opinion   ;
   
#20160504  
   CREATE TABLE council_apply (
   apply_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '申请ID',
   project_code VARCHAR(50) DEFAULT NULL COMMENT '对应项目编号',
   project_name VARCHAR(128) DEFAULT NULL COMMENT '对应项目名称',
   amount BIGINT(20) DEFAULT NULL COMMENT '授信金额',
   customer_id BIGINT(20) DEFAULT NULL COMMENT '客户ID',
   customer_name VARCHAR(50) DEFAULT NULL COMMENT '客户名称',
   apply_man_id BIGINT(20) DEFAULT NULL COMMENT '申请人',
   apply_man_name VARCHAR(50) DEFAULT NULL COMMENT '申请人名称',
   apply_dept_id BIGINT(20) DEFAULT NULL COMMENT '申请部门',
   apply_dept_name VARCHAR(50) DEFAULT NULL COMMENT '申请部门名称',
   council_code VARCHAR(20) DEFAULT NULL COMMENT '会议类型',
   council_type BIGINT(20) DEFAULT NULL COMMENT '会议类型ID',
   council_type_desc VARCHAR(50) DEFAULT NULL COMMENT '会议类型描述',
   apply_time DATETIME DEFAULT NULL COMMENT '提交申请时间',
   status VARCHAR(10) NOT NULL DEFAULT 'WAIT' COMMENT '状态',
   raw_add_time TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
   raw_update_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (apply_id)
 ) ENGINE=INNODB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='项目上会申请（待上会项目列表）';			
			
 #lirz 20160506
 alter table f_investigation_litigation 
   add column audit_opinion text NULL COMMENT '风险审查意见' after content, 
   add column synthesize_opinion text NULL COMMENT '项目综合意见' after audit_opinion;
   
#feihong 20160509   
alter table f_afterwards_check_collection 
   add column has_personal_guarantee varchar(10) DEFAULT 'NO' NULL COMMENT '有个人保证' after other_material_desc, 
   add column has_corporate_guarantee varchar(10) DEFAULT 'NO' NULL COMMENT '有法人保证' after has_personal_guarantee, 
   add column has_mortgage varchar(10) DEFAULT 'NO' NULL COMMENT '有抵押' after has_corporate_guarantee, 
   add column has_pledge varchar(10) DEFAULT 'NO' NULL COMMENT '有质押' after has_mortgage, 
   add column has_guarantee_way varchar(10) DEFAULT 'NO' NULL COMMENT '有其它（反）担保方式' after has_pledge,
   change other_material other_material varchar(10) character set utf8 collate utf8_general_ci default 'NO' NULL  comment '有其他资料' ;
   
   
   
   alter table council_projects 
   add column one_vote_down_mark text NULL COMMENT '被一票否决的原因' after one_vote_down
 
#lirz 20160513
ALTER table project_credit_condition add column release_status varchar(10) DEFAULT 'WAITING' COMMENT '解保申请状态(默认待解保)' AFTER status,
add column release_id bigint(20) DEFAULT 0 COMMENT '解保申请的id:f_counter_guarantee_release.id' AFTER release_status,
add column release_reason varchar(512) DEFAULT NULL COMMENT '解除的原因' AFTER release_id,
add column release_gist varchar(512) DEFAULT NULL COMMENT '解除的依据' AFTER release_reason;

DROP TABLE IF EXISTS f_counter_guarantee_release;
CREATE TABLE f_counter_guarantee_release (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(20) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(128) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  contract_number varchar(128) DEFAULT NULL COMMENT '合同编号',
  credit_amount bigint(20) DEFAULT NULL COMMENT '授信金额(已放款总额)',
  time_limit int(11) DEFAULT NULL COMMENT '授信期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '期限单位',
  released_amount bigint(20) DEFAULT NULL COMMENT '已解保金额',
  apply_amount bigint(20) DEFAULT NULL COMMENT '申请解保金额',
  raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  KEY f_counter_guarantee_release_form_id_i (form_id) USING BTREE,
  KEY f_counter_guarantee_release_project_code_i (project_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='解除（反）担保-解保申请';


   
#hjiajie 20160525
DROP TABLE IF EXISTS f_project_contract_extra_value;
CREATE TABLE f_project_contract_extra_value (
   id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  contract_id BIGINT(20) NOT NULL COMMENT '合同集ID',
  contract_item_id BIGINT(20) NOT NULL COMMENT '合同ID',
   contract_code VARCHAR(255) DEFAULT NULL COMMENT '合同编号',
  template_id BIGINT(20) DEFAULT NULL COMMENT '合同模板',
   document_name VARCHAR(50) DEFAULT NULL COMMENT '字段name属性',
   document_value VARCHAR(50) DEFAULT NULL COMMENT '字段value属性',
   document_type VARCHAR(11) DEFAULT 'input' COMMENT '字段类别-现在只有input',
   document_modify_num INT(11) DEFAULT '0' COMMENT '字段修改次数',
   raw_add_time TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
   raw_update_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='授信前管理 - 项目合同集- 合同-制式合同属性';


DROP TABLE IF EXISTS f_project_contract_extra_value_modify;
CREATE TABLE f_project_contract_extra_value_modify (
   id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  contract_id BIGINT(20) NOT NULL COMMENT '合同集ID',
  contract_item_id BIGINT(20) NOT NULL COMMENT '合同ID',
   contract_code VARCHAR(255) DEFAULT NULL COMMENT '合同编号',
  template_id BIGINT(20) DEFAULT NULL COMMENT '合同模板',
   value_id BIGINT(20) NOT NULL COMMENT '对应value表id',
   document_name VARCHAR(50) NOT NULL COMMENT '字段name属性',
   document_value_old VARCHAR(50) DEFAULT NULL COMMENT '字段旧value属性',
   document_value_new VARCHAR(50) DEFAULT NULL COMMENT '字段新value属性',
   document_type VARCHAR(11) DEFAULT 'input' COMMENT '字段类别-现在只有input',
   raw_add_time TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
   raw_update_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='授信前管理 - 项目合同集- 合同-制式合同属性修改记录';


#lirz 20160525
ALTER table project ADD COLUMN released_amount bigint(20) DEFAULT NULL COMMENT '已解保金额' AFTER is_maximum_amount,
ADD COLUMN releasing_amount bigint(20) DEFAULT NULL COMMENT '在保余额' AFTER released_amount;


#wuzj 20160526
ALTER TABLE project
  ADD COLUMN customer_deposit_amount BIGINT(20) DEFAULT NULL COMMENT '客户保证金' AFTER releasing_amount,
  ADD COLUMN self_deposit_amount BIGINT(20) DEFAULT NULL COMMENT '自家保证金' AFTER customer_deposit_amount;
  
#wuzj 20160526
ALTER TABLE f_loan_use_apply_receipt
  ADD COLUMN actual_deposit_amount BIGINT(20) DEFAULT NULL COMMENT '实际保证金' AFTER actual_amount;

#heh 20160526
ALTER TABLE f_charge_notification_fee
  CHANGE COLUMN charge_base charge_base BIGINT DEFAULT NULL COMMENT '收费基数';

 
#lirz
ALTER table f_investigation_mainly_review ADD column is_one_cert varchar(10) DEFAULT 'NO' COMMENT '是否三证合一' after work_address;


 # hjiajie 20160527 为council_apply 项目上会申请（待上会项目列表） 添加form_id
 alter table council_apply 
   add column form_id bigint(20) NULL COMMENT '对应表单ID' after project_name;
   
   #hjiajie 20160530 为评委投票详情添加主部门信息
   alter table council_project_vote 
   add column org_name varchar(50) NULL COMMENT '主部门' after role_name

#lirz
ALTER table f_investigation ADD column amount bigint(20) DEFAULT NULL COMMENT '授信金额' after busi_type_name;
ALTER table f_investigation ADD column investigate_persion_id varchar(128) DEFAULT NULL COMMENT '调查人员ID(多个以逗号分隔)' after investigate_persion;

#hjiajie 20160531  增加合同内容展示，用于word导出合同内容
alter table f_project_contract_item 
   add column content_message text NULL COMMENT '合同内容展示' after content,
   change content content text character set utf8 collate utf8_general_ci NULL  comment '合同内容'
   
   # hjiajie 20160531  添加授信期限和期限单位到待上会列表 
   alter table council_apply 
   add column time_limit int(11) NULL COMMENT '期限' after amount, 
   add column time_unit varchar(10) NULL COMMENT '期限单位' after time_limit
   
   ## hjiajie 20160606  将合同内容设置为mediumtext,避免数据库放不下导致的报错。
   alter table f_project_contract_item 
   change content content mediumtext NULL  comment '合同内容', 
   change content_message content_message mediumtext NULL  comment '合同内容展示';
   alter table contract_template 
   change template_content template_content mediumtext NULL  comment '模板内容（制式）';
   
   ## hjiajie 20160606 为合同信息修改记录表添加修改人和修改人名
   alter table f_project_contract_extra_value_modify 
   add column user_id bigint(20) NULL COMMENT '修改人id' after template_id, 
   add column user_name varchar(50) NULL COMMENT '修改人名' after user_id;
   
   ## hjiajie 20160606 添加合同审核记录表
   CREATE TABLE f_project_contract_check (
   id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   contract_id BIGINT(20) NOT NULL COMMENT '合同集ID',
   contract_item_id BIGINT(20) NOT NULL COMMENT '合同ID',
   user_id BIGINT(20) DEFAULT NULL COMMENT '审核人id',
   user_name VARCHAR(50) DEFAULT NULL COMMENT '审核人名',
   file_url TEXT COMMENT '附件',
   remark VARCHAR(2000) DEFAULT NULL COMMENT '备注',
   raw_add_time TIMESTAMP NULL DEFAULT NULL,
   raw_update_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='授信前管理 - 项目合同集- 合同-合同审核记录';
 
 ## 20160607 会议选择之后使用老会议投票信息来做会议判定
 
   ALTER TABLE council 
   ADD COLUMN  if_vote VARCHAR(10) DEFAULT NULL COMMENT '是否投票' AFTER decision_institution_id, 
   ADD COLUMN  vote_rule_type VARCHAR(20) DEFAULT NULL COMMENT '投票规则类型（通过率/通过人数）'  AFTER if_vote, 
   ADD COLUMN  pass_num INT(11) DEFAULT NULL COMMENT '通过人数（超过该值 - 项目通过）' AFTER vote_rule_type, 
   ADD COLUMN  indeterminate_num INT(11) DEFAULT NULL COMMENT '本次不议人数（超过该值 - 项目重新上会）'  AFTER pass_num;

## lirz 20160607
ALTER table f_investigation_financial_review add column import_excel varchar(16) DEFAULT 'NO' COMMENT '导入过数据:YES/NO' after customer_name;

   ## 20160607 会议选择之后使用老会议投票信息来做会议判定
          ALTER TABLE council 
         ADD COLUMN  major_num INT(11) DEFAULT NULL COMMENT '最高评委人数'  AFTER decision_institution_id, 
   ADD COLUMN  less_num INT(11) DEFAULT NULL COMMENT '最低评委人数' AFTER major_num;
   
   
   ## 20160608 在项目中添加本次不议判定属性，用于风控委秘书本次不议功能
   
   alter table council_projects 
   add column risk_secretary_quit varchar(20) NULL COMMENT '是否本次不议' after sort_order, 
   add column risk_secretary_quit_mark text NULL COMMENT '被本次不议的理由' after risk_secretary_quit
    ## 20160612 会议选择之后使用老会议投票信息来做会议判定
 
  ALTER TABLE council 
    ADD COLUMN  pass_rate DECIMAL(12,4) DEFAULT NULL COMMENT '通过率'  AFTER indeterminate_num, 
   ADD COLUMN  indeterminate_rate DECIMAL(12,4) DEFAULT NULL COMMENT '本次不议率'  AFTER pass_rate;
   
   
   ## 20160630 添加字段中文描述
   alter table f_project_contract_extra_value 
   add column document_describe varchar(128) NULL COMMENT '字段name描述' after document_name;
   
   alter table f_project_contract_extra_value_modify 
   add column document_describe varchar(128) NULL COMMENT '字段name描述' after document_name;
   
   ## 20160713 添加用户额外信息表，用户维护用户额外信息，目前用户评委投票的证书key
   CREATE TABLE user_extra_message (
   id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   user_id BIGINT(20) NOT NULL COMMENT '用户ID',
   user_name VARCHAR(50) NOT NULL COMMENT '用户名，用作查看',
   user_account VARCHAR(50) DEFAULT NULL COMMENT '用户登录名',
   user_judge_key VARCHAR(50) DEFAULT NULL COMMENT '用户投票的证书key',
   raw_add_time TIMESTAMP NULL DEFAULT NULL,
   raw_update_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id),
   KEY user_id_key (user_id),
   KEY user_account_key (user_account)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户信息- 用户额外信息';
 
## 20160719 解保申请，审核不通过后，历史授信条件落实情况记录
CREATE TABLE guarantee_apply_counter (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(20) NOT NULL COMMENT '项目编号',
  form_id bigint(20) NOT NULL COMMENT '关联的表单ID',
  item_desc varchar(512) DEFAULT NULL COMMENT '授信条件文字描述（根据对应抵（质）押、保证生成）',
  release_id bigint(20) DEFAULT '0' COMMENT '解保申请的id:存放关联数据的id',
  release_reason varchar(512) DEFAULT NULL COMMENT '解除的原因',
  release_gist varchar(512) DEFAULT NULL COMMENT '解除的依据',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY project_credit_condition_project_code_i (project_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='解保申请-反担保信息';

ALTER TABLE f_afterwards_check_report_project ADD COLUMN del_able varchar(16) DEFAULT NULL COMMENT '是否可删除(页面上)' AFTER project_desc;
ALTER TABLE f_afterwards_check_report_capital ADD COLUMN del_able varchar(16) DEFAULT NULL COMMENT '是否可删除(页面上)' AFTER capital_value10;
ALTER TABLE f_afterwards_check_report_income ADD COLUMN del_able varchar(16) DEFAULT NULL COMMENT '是否可删除(页面上)' AFTER current_accumulation;
ALTER TABLE f_afterwards_check_report_item ADD COLUMN del_able varchar(16) DEFAULT NULL COMMENT '是否可删除(页面上)' AFTER item_desc;

## lirz 20160720
ALTER table f_afterwards_check_report_content ADD COLUMN import_excel varchar(16) DEFAULT 'NO' COMMENT '导入过数据:YES/NO' AFTER form_id;
CREATE TABLE f_afterwards_check_common (
  common_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(20) NOT NULL COMMENT '项目编号',  
  loan_limit_data varchar(64) DEFAULT NULL COMMENT '贷款期限集中度',  
  loan_amount_data varchar(64) DEFAULT NULL COMMENT '贷款金额集中度',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (common_id),
  UNIQUE KEY project_code (project_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保后检查 - 公共数据';

CREATE TABLE f_afterwards_check_report_content_extend (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  value1 varchar(128) DEFAULT NULL COMMENT '数据1',
  value2 varchar(128) DEFAULT NULL COMMENT '数据2',
  value3 varchar(128) DEFAULT NULL COMMENT '数据3',
  value4 varchar(128) DEFAULT NULL COMMENT '数据4',
  value5 varchar(128) DEFAULT NULL COMMENT '数据5',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY form_id (form_id) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保后检查-监管内容-扩展表';
## 尽调修改
alter table f_investigation_credit_scheme add column charge_type varchar(20) DEFAULT NULL COMMENT '收费类型' after charge_rate;
alter table f_investigation_credit_scheme add column other text COMMENT '其它' after statement;
alter table f_investigation_financial_review add column amount_unit varchar(16) DEFAULT NULL COMMENT '货币单位' after import_excel;



## lirz 2016-07-22
CREATE TABLE f_afterwards_check_litigation (
  litigation_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  case_status varchar(32) DEFAULT NULL COMMENT '案件状态',
  opening_date date DEFAULT NULL COMMENT '开庭时间',
  judge_date date DEFAULT NULL COMMENT '判决时间',  
  remark text COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (litigation_id),
  KEY f_afterwards_check_litigation_form_id_i (form_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保后检查 - 诉讼保函';

## lirz 2016-07-26
alter table f_afterwards_check_report_project add column check_status int(11) default 0 COMMENT '数据较验状态' after del_able;
ALTER TABLE f_afterwards_check_report_content 
ADD COLUMN date1 varchar(32) DEFAULT NULL COMMENT '到期日' AFTER analysis_conclusion,
ADD COLUMN date2 varchar(32) DEFAULT NULL COMMENT '到期日' AFTER date1,
ADD COLUMN date3 varchar(32) DEFAULT NULL COMMENT '到期日' AFTER date2;
alter table f_council_apply_risk_handle add column guarantee_rate_type varchar(20) DEFAULT NULL COMMENT '收费类型' after guarantee_rate;

## lirz 2016-07-27
alter table f_afterwards_check_report_content 
add column amount_unit1 varchar(16) DEFAULT NULL COMMENT '货币单位' after date3,
add column amount_unit2 varchar(16) DEFAULT NULL COMMENT '货币单位' after amount_unit1,
add column amount_unit3 varchar(16) DEFAULT NULL COMMENT '货币单位' after amount_unit2;

## 记录项目历史数据
ALTER TABLE f_afterwards_check_base
ADD COLUMN  project_name varchar(128) DEFAULT NULL COMMENT '项目名称' AFTER form_id,  
ADD COLUMN  project_code varchar(50) DEFAULT NULL COMMENT '项目编号' AFTER project_name,  
ADD COLUMN  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID' AFTER project_code,  
ADD COLUMN  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称' AFTER customer_id,  
ADD COLUMN  busi_type varchar(50) DEFAULT NULL COMMENT '业务类型' AFTER customer_name,  
ADD COLUMN  busi_type_name varchar(120) DEFAULT NULL COMMENT '业务类型名称' AFTER busi_type,    
ADD COLUMN  amount bigint(20) DEFAULT NULL COMMENT '金额' AFTER busi_type_name,  
ADD COLUMN  start_time datetime DEFAULT NULL COMMENT '开始时间' AFTER amount,  
ADD COLUMN  end_time datetime DEFAULT NULL COMMENT '结束时间' AFTER start_time,  
ADD COLUMN  busi_manager_name varchar(50) DEFAULT NULL COMMENT '客户经理名称' AFTER end_time,  
ADD COLUMN  risk_manager_name varchar(50) DEFAULT NULL COMMENT '风险经理名称' AFTER busi_manager_name,  
ADD COLUMN  capital_channel_name varchar(128) DEFAULT NULL COMMENT '资金渠道名称' AFTER risk_manager_name,   
ADD COLUMN  loan_purpose varchar(512) DEFAULT NULL COMMENT '授信用途' AFTER capital_channel_name,   
ADD COLUMN  time_limit int(11) DEFAULT NULL COMMENT '期限' AFTER spend_way,  
ADD COLUMN  time_unit varchar(10) DEFAULT NULL COMMENT '期限单位' AFTER time_limit,  
ADD COLUMN  loaned_amount bigint(20) DEFAULT NULL COMMENT '累计使用金额' AFTER time_unit,  
ADD COLUMN  repayed_amount bigint(20) DEFAULT NULL COMMENT '已还款金额' AFTER loaned_amount,  
ADD COLUMN  available_amount bigint(20) DEFAULT NULL COMMENT '可用金额' AFTER repayed_amount,  
ADD COLUMN  release_balance bigint(20) DEFAULT NULL COMMENT '在保余额' AFTER available_amount,
ADD COLUMN  guarantee_amount bigint(20) DEFAULT NULL COMMENT '已收的担保费' AFTER release_balance,
ADD COLUMN  guarantee_deposit bigint(20) DEFAULT NULL COMMENT '已收的保证金' AFTER guarantee_amount;


##2016-08-02
CREATE TABLE f_internal_opinion_exchange (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '表单ID',
  ex_type varchar(20) DEFAULT NULL COMMENT '类型',
  dept_id bigint DEFAULT NULL COMMENT '审计部门ID',
  dept_name varchar(128) DEFAULT NULL COMMENT '审计部门名称',
  users text DEFAULT NULL COMMENT '审计人员',
  remark text DEFAULT NULL COMMENT '说明',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_internal_opinion_exchange_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '内审意见交换';



## 20160803 为图片表添加子id，暂时用作追偿时的判定 修订类型为128字节
ALTER TABLE common_attachment 
   ADD COLUMN child_id VARCHAR(30) NULL COMMENT '额外的特殊判断用，子id' AFTER biz_no,
   change module_type module_type varchar(128) DEFAULT NULL comment '附件所属模块类型，暂时初审带的附件';

# lirz 2016-08-05
ALTER TABLE f_afterwards_check_litigation
ADD COLUMN  project_name varchar(128) DEFAULT NULL COMMENT '项目名称' AFTER form_id,  
ADD COLUMN  project_code varchar(50) DEFAULT NULL COMMENT '项目编号' AFTER project_name,  
ADD COLUMN  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID' AFTER project_code,  
ADD COLUMN  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称' AFTER customer_id,      
ADD COLUMN  amount bigint(20) DEFAULT NULL COMMENT '金额' AFTER customer_name,  
ADD COLUMN  co_institution_id bigint(20) DEFAULT NULL COMMENT '合作机构ID'AFTER amount,  
ADD COLUMN  co_institution_name varchar(128) DEFAULT NULL COMMENT '合作机构名称'AFTER co_institution_id,  
ADD COLUMN  co_institution_charge decimal(12,4) DEFAULT NULL COMMENT '合作机构服务费 元/%'AFTER co_institution_name,  
ADD COLUMN  co_institution_charge_type varchar(10) DEFAULT NULL COMMENT '合作机构服务费类型 元/%'AFTER co_institution_charge,  
ADD COLUMN  guarantee_fee decimal(12,4) DEFAULT NULL COMMENT '担保费'AFTER co_institution_charge_type,  
ADD COLUMN  guarantee_fee_type varchar(10) DEFAULT NULL COMMENT '担保费类型'AFTER guarantee_fee,  
ADD COLUMN  busi_manager_name varchar(50) DEFAULT NULL COMMENT '客户经理名称' AFTER guarantee_fee_type,  
ADD COLUMN  risk_manager_name varchar(50) DEFAULT NULL COMMENT '风险经理名称' AFTER busi_manager_name, 
ADD COLUMN  assure_object text COMMENT '本次申请保全标的'AFTER risk_manager_name;

ALTER TABLE f_afterwards_check_litigation ADD INDEX project_code (project_code);

## lirz 2016-08-08 通用附件上传，记录上传人
ALTER TABLE common_attachment
  ADD COLUMN uploader_id bigint(20) DEFAULT NULL COMMENT '上传人ID' AFTER request_path,
  ADD COLUMN uploader_account varchar(20) DEFAULT NULL COMMENT '操作人账号' AFTER uploader_id,
  ADD COLUMN uploader_name varchar(50) DEFAULT NULL COMMENT '操作人名称' AFTER uploader_account; 


## wuzj 项目相关人员 2016-08-10
CREATE TABLE project_related_user (
  related_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  user_type varchar(20) DEFAULT 'OTHER' COMMENT '人员类型 (业务经理、法务经理、其他...等)',
  user_id bigint(20) DEFAULT NULL COMMENT '当前人员ID',
  user_account varchar(50) DEFAULT NULL COMMENT '当前人员账号',
  user_name varchar(50) DEFAULT NULL COMMENT '当前人员名称',
  user_mobile varchar(20) DEFAULT NULL COMMENT '当前人员手机',
  user_email varchar(128) DEFAULT NULL COMMENT '当前人员email',
  dept_id bigint(20) DEFAULT NULL COMMENT '当前部门ID',
  dept_code varchar(50) DEFAULT NULL COMMENT '部门编号',
  dept_name varchar(128) DEFAULT NULL COMMENT '部门名称',
  dept_path varchar(1024) DEFAULT NULL COMMENT '部门路径',
  dept_path_name text DEFAULT NULL COMMENT '部门路径名',
  transfer_time datetime DEFAULT NULL COMMENT '转交时间',
  transfer_related_id bigint(20) DEFAULT NULL COMMENT '转交ID -> related_id',
  remark text DEFAULT NULL COMMENT '备注',
  is_current varchar(10) DEFAULT 'IS' COMMENT '是否是当前人员(方便保存历史记录)',
  is_del varchar(10) DEFAULT 'NO' COMMENT '是否删除(删除后不再有权限)',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (related_id),
  INDEX project_related_user_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目相关人员';

## wuzj 表单相关人员 2016-08-10
CREATE TABLE form_related_user (
  related_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '表单ID',
  form_code varchar(128) DEFAULT NULL COMMENT '表单code',
  task_id bigint(20) DEFAULT 0 COMMENT '任务ID',
  task_name varchar(128) DEFAULT NULL COMMENT '任务名称',
  task_opinion varchar(10) DEFAULT NULL COMMENT '任务意见',
  task_start_time datetime DEFAULT NULL COMMENT '任务接收时间',
  task_end_time datetime DEFAULT NULL COMMENT '任务结束时间',
  exe_status varchar(20) DEFAULT NULL COMMENT '任务执行状态',
  project_code varchar(50) DEFAULT NULL COMMENT '相关项目编号',
  user_type varchar(20) DEFAULT 'OTHER' COMMENT '人员类型 (流程候选人、执行人、提交人...)',
  user_id bigint(20) DEFAULT NULL COMMENT '人员ID',
  user_account varchar(50) DEFAULT NULL COMMENT '人员账号',
  user_name varchar(50) DEFAULT NULL COMMENT '人员名称',
  user_mobile varchar(20) DEFAULT NULL COMMENT '人员手机',
  user_email varchar(128) DEFAULT NULL COMMENT '人员email',
  dept_id bigint(20) DEFAULT NULL COMMENT '当前部门ID',
  dept_code varchar(50) DEFAULT NULL COMMENT '部门编号',
  dept_name varchar(128) DEFAULT NULL COMMENT '部门名称',
  dept_path varchar(1024) DEFAULT NULL COMMENT '部门路径',
  dept_path_name text DEFAULT NULL COMMENT '部门路径名',
  remark text DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (related_id),
  INDEX form_related_user_form_id_i (form_id),
  INDEX form_related_user_project_code_i (project_code),
  INDEX form_related_user_task_id_i (task_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单相关人员';

###  为待上会列表添加 信汇所需信息  20160816
alter table council_apply 
   add column company_name varchar(128) DEFAULT 'NORMAL'  COMMENT '公司名' after status, 
   add column mother_company_apply varchar(20) NULL COMMENT '是否母公司上会' after company_name, 
   add column mother_council_code varchar(20) NULL COMMENT '母公司会议类型' after mother_company_apply,
 ADD COLUMN child_id BIGINT(20) DEFAULT NULL  COMMENT '来源申请单id' AFTER apply_id;
   
###  为会议添加 信汇所需信息  20160816
ALTER TABLE council 
   ADD COLUMN company_name VARCHAR(128)  DEFAULT 'NORMAL'  COMMENT '是否母公司上会' AFTER create_man_name
   
## lirz 2016-08-16 承销尽调审核选择是否上母公司会议
ALTER TABLE f_investigation ADD COLUMN council_type varchar(50) DEFAULT NULL COMMENT '上会类型' AFTER reception_duty;
  ADD COLUMN council_apply_id BIGINT(20) DEFAULT NULL COMMENT '会议申请ID' AFTER council_type,
  ADD COLUMN council_status VARCHAR(50) DEFAULT NULL COMMENT '上会状态' AFTER council_apply_id;  


## wuzj 信惠经纪业务  2016-08-17
CREATE TABLE f_broker_business (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  form_id bigint(20) DEFAULT NULL COMMENT '表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '经纪业务项目编号',
  customer_name varchar(128) DEFAULT NULL COMMENT '客户名称（自己填）',
  summary text DEFAULT NULL COMMENT '摘要',
  is_need_council varchar(20) DEFAULT NULL COMMENT '是否需要上会',
  status varchar(50) DEFAULT NULL COMMENT '状态',
  charged_amount bigint(20) DEFAULT NULL COMMENT '已收费金额',
  contract_url text DEFAULT NULL COMMENT '合同地址',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '经纪业务';

## heh 征信报告修改 2016-08-18
ALTER TABLE f_credit_refrerence_apply
  ADD COLUMN is_three_in_one VARCHAR(20) NULL  COMMENT '是否三证合一' AFTER credit_report,
  ADD COLUMN org_code VARCHAR(20) NULL  COMMENT '组织机构代码' AFTER is_three_in_one,
  ADD COLUMN tax_reg_certificate_no VARCHAR(20) NULL  COMMENT '税务登记证号码' AFTER org_code,
  ADD COLUMN social_unity_credit_code VARCHAR(20) NULL  COMMENT '社会统一信用代码' AFTER tax_reg_certificate_no,
   ADD COLUMN loan_card_no VARCHAR(10) NULL  COMMENT '贷款卡号' AFTER social_unity_credit_code,
  ADD COLUMN is_need_attach VARCHAR(10) NULL  COMMENT '是否需要附件' AFTER loan_card_no,
  CHANGE raw_add_time raw_add_time TIMESTAMP NULL  COMMENT '新增时间'  AFTER social_unity_credit_code,
  CHANGE raw_update_time raw_update_time TIMESTAMP NULL  COMMENT '修改时间'  AFTER raw_add_time;

ALTER TABLE f_credit_refrerence_apply
  ADD COLUMN attachment TEXT NULL  COMMENT '附件' AFTER loan_card_no;
    CHANGE credit_report credit_report VARCHAR(500) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '征信报告';

## wuzj 理财项目转让是否要上会  2016-08-16
ALTER TABLE f_project_financial_tansfer_apply
  ADD COLUMN council_type VARCHAR(50) DEFAULT NULL COMMENT '上会类型 null表示不上会' AFTER buy_back_time,
  ADD COLUMN council_apply_id BIGINT(20) DEFAULT NULL COMMENT '会议申请ID' AFTER council_type,
  ADD COLUMN council_status VARCHAR(50) DEFAULT NULL COMMENT '上会状态' AFTER council_apply_id;  
  
## wuzj 理财项目立项上会  2016-08-16
ALTER TABLE f_project_financial
  ADD COLUMN council_type VARCHAR(50) DEFAULT NULL COMMENT '上会类型' AFTER dept_path_name,
  ADD COLUMN council_apply_id BIGINT(20) DEFAULT NULL COMMENT '会议申请ID' AFTER council_type,
  ADD COLUMN council_status VARCHAR(50) DEFAULT NULL COMMENT '上会状态' AFTER council_apply_id;    
  
  ## 20160818 项目名称加长
ALTER TABLE council_projects 
   CHANGE project_name project_name VARCHAR(128) DEFAULT  NULL  COMMENT '项目名称';
    ALTER TABLE council_project_vote 
   CHANGE project_name project_name VARCHAR(128) DEFAULT  NULL  COMMENT '项目名称';

## 函件内容加长  20160820
ALTER TABLE project_recovery_notice_letter 
   CHANGE content content MEDIUMTEXT COMMENT '函件内容',
   CHANGE content_message content_message MEDIUMTEXT COMMENT '函件内容展示';

## 为信惠添加 会议类型所需数据
 ALTER TABLE  council_type 
   ADD COLUMN company_name VARCHAR(128) DEFAULT 'NORMAL' COMMENT '公司名'  AFTER summary_code_prefix;

##lirz 20160823 尽调 授信方案 过程控制表结构修改 先删除，再创建
drop table f_investigation_credit_scheme_process_control;
CREATE TABLE f_investigation_credit_scheme_process_control (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '关联表单ID',
  type varchar(20) DEFAULT NULL COMMENT '客户主体评价/资产负债率/其它',
  up_rate varchar(20) DEFAULT NULL COMMENT '上调百分点',  
  up_bp varchar(20) DEFAULT NULL COMMENT '上调bp',  
  down_rate varchar(20) DEFAULT NULL COMMENT '下降百分点',  
  down_bp varchar(20) DEFAULT NULL COMMENT '下降bp',  
  content varchar(512) DEFAULT NULL COMMENT '信用等级/资产负债率警戒/过程控制内容',
  sort_order int(11) DEFAULT NULL,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY form_id_i (form_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目调查 - 授信方案 - 过程控制(客户主体评级/资产负债率/其他)';

## 保证人 添加一个类型字段
ALTER TABLE f_investigation_credit_scheme_guarantor ADD COLUMN guarantor_type varchar(50) DEFAULT NULL COMMENT '保证类型' AFTER scheme_id;

   
 ##增加formCode长度  
 ALTER TABLE form
  CHANGE COLUMN form_code form_code VARCHAR(50) DEFAULT NULL COMMENT '表单编码',
  CHANGE COLUMN form_name form_name VARCHAR(128) DEFAULT NULL COMMENT '表单名称';

   
  ## 20160826  为资金系统增加额外属性 
  alter table user_extra_message 
   add column oa_system_id varchar(50) NULL COMMENT 'OA系统同步ID' after user_judge_key, 
   add column bank_name varchar(128) NULL COMMENT '银行卡开户行' after oa_system_id, 
   add column bank_account_no varchar(50) NULL COMMENT '银行卡账号' after bank_name;
   
 ##2016-08-27 会议纪要- 保证人 添加一个类型字段  
 ALTER TABLE f_council_summary_project_guarantor
  ADD COLUMN guarantor_type VARCHAR(50) DEFAULT NULL COMMENT '保证类型' AFTER sp_id;  

 ##2016-08-27 会议纪要 过程控制
drop table f_council_summary_project_process_control;
CREATE TABLE f_council_summary_project_process_control (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '项目批复ID f_council_summary_project.sp_id',
  type varchar(20) DEFAULT NULL COMMENT '客户主体评价/资产负债率/其它',
  up_rate varchar(20) DEFAULT NULL COMMENT '上调百分点',
  up_bp varchar(20) DEFAULT NULL COMMENT '上调bp',
  down_rate varchar(20) DEFAULT NULL COMMENT '下降百分点',
  down_bp varchar(20) DEFAULT NULL COMMENT '下降bp',
  content varchar(512) DEFAULT NULL COMMENT '信用等级/资产负债率警戒/过程控制内容',
  sort_order int(11) DEFAULT NULL,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX f_council_summary_project_process_control_sp_id_i (sp_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要-过程控制(客户主体评级/资产负债率/其他)';

## 会议纪要 - 抵（质）押
CREATE TABLE f_council_summary_project_pledge_asset (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '对应项目批复ID',
  type varchar(10) DEFAULT NULL COMMENT '抵/质押',
  type_desc varchar(50) DEFAULT NULL COMMENT '抵押 / 质押',
  assets_id bigint(20) NOT NULL COMMENT '资产id',
  asset_type varchar(100) DEFAULT NULL COMMENT '资产类型(am 存的pledge_goods_type表中three_level_classification_two值)',
  ownership_name varchar(50) DEFAULT NULL COMMENT '所有权利人名称',
  evaluation_price bigint(20) DEFAULT NULL COMMENT '评估价格',
  pledge_rate decimal(12, 4) DEFAULT NULL COMMENT '抵质押率',
  sort_order int(11) DEFAULT NULL,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX f_council_summary_project_pledge_asset_sp_id_i (sp_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 -（反）担保措施 -  抵（质）押';


   ## 20160827 更改合同一式几份的数据类型
   ALTER TABLE `f_project_contract_item`
  CHANGE `cnt` `cnt` VARCHAR(20) NULL  COMMENT '一式几（份）';

  ## 立项新增字段 2016-08-27 wuzj
ALTER TABLE f_project_customer_base_info
  ADD COLUMN is_region_complete VARCHAR(20) DEFAULT NULL COMMENT '所属区域 - 是否选择完整（方便前端）' AFTER county_name;

  ## 新增资产系统支持ID字段 2016-08-29 wuzj
ALTER TABLE project_credit_condition
  CHANGE COLUMN item_id item_id BIGINT(20) NOT NULL COMMENT 'f_council_summary_project_pledge_asset.id,f_council_summary_project_guarantor.id' AFTER project_code,
  CHANGE COLUMN item_desc item_desc VARCHAR(512) DEFAULT NULL COMMENT '授信条件文字描述（根据对应抵（质）押、保证生成）' AFTER item_id,
  CHANGE COLUMN asset_id asset_id BIGINT(20) DEFAULT NULL COMMENT '资产系统对应资产ID' AFTER item_desc;

## 报表系统 统计数据表

CREATE TABLE report_expect_event (
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  amount BIGINT(20) DEFAULT NULL COMMENT '金额',
  dept_id BIGINT(20) DEFAULT NULL COMMENT '所属部门ID',
  dept_code VARCHAR(30) DEFAULT NULL COMMENT '部门编号',
  dept_name VARCHAR(50) DEFAULT NULL COMMENT '所属部门名称',
  TYPE VARCHAR(20) DEFAULT NULL COMMENT '类型(全签、未全签)',
  busi_type VARCHAR(10) DEFAULT NULL COMMENT '业务类型(D:担保 W:委贷)',
  YEAR INT(11) DEFAULT 0 COMMENT '统计年',
  MONTH INT(11) DEFAULT 0 COMMENT '统计月',
  raw_add_time TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '预计发生情况汇总表';

ALTER TABLE f_project_customer_base_info
  CHANGE COLUMN staff_num staff_num BIGINT DEFAULT NULL COMMENT '员工人数';
  
  
  ## 20160916 为追偿 抵质押信息表添加资产id，用于标记资产
  alter table `project_recovery_debtor_reorganization_pledge` 
   add column `asset_id` varchar(50) NULL COMMENT '关联资产表id' after `project_recovery_type`;

  ## 20160912 收费通知单新增字段
  ALTER TABLE `f_charge_notification`
  ADD COLUMN `self_pay` VARCHAR(10) NULL  COMMENT '自付' AFTER `is_agent_pay`,
  ADD COLUMN `pay_for_another` VARCHAR(10) NULL  COMMENT '他付' AFTER `pay_time`,
  ADD COLUMN `another_pay_amount` BIGINT(20) NULL  COMMENT '他付金额' AFTER `pay_for_another`,
  ADD COLUMN `another_pay_name` VARCHAR(50) NULL  COMMENT '他付付款方户名' AFTER `another_pay_amount`,
  ADD COLUMN `another_pay_account` VARCHAR(30) NULL  COMMENT '他付账号' AFTER `another_pay_name`,
  ADD COLUMN `another_pay_bank` VARCHAR(100) NULL  COMMENT '他付银行' AFTER `another_pay_account`,
  ADD COLUMN `attachment` VARCHAR(512) NULL  COMMENT '附件' AFTER `payer`;

  ALTER TABLE `f_charge_notification_fee`
  CHANGE `remark` `remark` VARCHAR(200) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '收费备注';

  ALTER TABLE `f_charge_notification`
  CHANGE `pay_name` `pay_name` VARCHAR(100) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '付款方户名',
  CHANGE `pay_account` `pay_account` VARCHAR(100) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '付款账号',
  CHANGE `another_pay_name` `another_pay_name` VARCHAR(100) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '他付付款方户名',
  CHANGE `another_pay_account` `another_pay_account` VARCHAR(100) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '他付账号';
  CHANGE `attachment` `attachment` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '附件';




  ##20160913 合同增加决策依据
  ALTER TABLE `f_project_contract_item`
  ADD COLUMN `basis_of_decision` VARCHAR(20) NULL  COMMENT '决策依据(项目批复、签报FormId)' AFTER `approved_url`;

  
##20160913 lirz 尽调-授信方案
ALTER TABLE f_investigation_credit_scheme_pledge_asset ADD COLUMN syn_position varchar(32) DEFAULT NULL COMMENT '抵押顺位' AFTER pledge_rate,
ADD COLUMN postposition varchar(32) DEFAULT NULL COMMENT '是否后置抵押' AFTER syn_position,
ADD COLUMN debted_amount bigint(20) DEFAULT NULL COMMENT '已抵债金额' AFTER postposition;

##20160914 lirz 尽调-申明修改
CREATE TABLE `f_investigation_person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `form_id` bigint(20) NOT NULL COMMENT '对应的表单ID',
  `investigate_date` datetime DEFAULT NULL COMMENT '调查日期',  
  `investigate_place` varchar(128) DEFAULT NULL COMMENT '调查地点',
  `main_investigator_id` bigint(20) DEFAULT NULL COMMENT '主要调查人ID',
  `main_investigator_account` varchar(20) DEFAULT NULL COMMENT '主要调查人账号',
  `main_investigator_name` varchar(50) DEFAULT NULL COMMENT '主要调查人名称',
  `assist_investigator_id` varchar(256) DEFAULT NULL COMMENT '协助调查人ID(多个逗号分隔)',
  `assist_investigator_name` varchar(256) DEFAULT NULL COMMENT '协助调查人名称(多个逗号分隔)',  
  `reception_persion` varchar(1024) DEFAULT NULL COMMENT '接待人员',
  `sort_order` int(11) DEFAULT NULL,
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '新增时间',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `form_id_i` (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='尽调 - 调查人员列表';


##20160918 wuzj 立项反担保
ALTER TABLE f_project_counter_guarantee_pledge ADD COLUMN syn_position varchar(32) DEFAULT NULL COMMENT '抵押顺位' AFTER net_value,
ADD COLUMN postposition varchar(32) DEFAULT NULL COMMENT '是否后置抵押' AFTER syn_position;


##20160918 heh 合同模板
ALTER TABLE `contract_template`
  ADD COLUMN `attachment` TEXT NULL  COMMENT '其他附件' AFTER `template_content`,
  ADD COLUMN `remark` VARCHAR(1000) NULL  COMMENT '备注' AFTER `attachment`;
  ALTER TABLE `contract_template`
  ADD COLUMN `template_type` VARCHAR(20) NULL  COMMENT '模板类型(合同模板/函通知书)' AFTER `remark`,
  ADD COLUMN `letter_type` VARCHAR(20) NULL  COMMENT '通知函类别' AFTER `template_type`;
  ALTER TABLE `contract_template`
  CHANGE `pledge_type` `pledge_type` VARCHAR(100) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '抵押品类型';
  
#20160919 wuzj 放用款结息周期
ALTER TABLE f_loan_use_apply
  ADD COLUMN interest_settlement_cycle VARCHAR(500) DEFAULT NULL COMMENT '结息周期' AFTER receipt_account;
  ADD COLUMN `template_type` VARCHAR(20) NULL  COMMENT '模板类型(合同模板/函通知书)' AFTER `remark`,
  ADD COLUMN `letter_type` VARCHAR(20) NULL  COMMENT '通知函类别' AFTER `template_type`;

#20160919 wuzj 放用款回执
ALTER TABLE f_loan_use_apply_receipt
  ADD COLUMN liquidity_loan_amount BIGINT(20) DEFAULT NULL COMMENT '流动资金贷款' AFTER actual_deposit_amount,
  ADD COLUMN fixed_assets_financing_amount BIGINT(20) DEFAULT NULL COMMENT '固定资产融资' AFTER liquidity_loan_amount,
  ADD COLUMN acceptance_bill_amount BIGINT(20) DEFAULT NULL COMMENT '承兑汇票担保' AFTER fixed_assets_financing_amount,
  ADD COLUMN credit_letter_amount BIGINT(20) DEFAULT NULL COMMENT '信用证担保' AFTER acceptance_bill_amount;


##20160973 heh 合同集
ALTER TABLE `f_project_contract`
  ADD COLUMN `free_flow` VARCHAR(10) NULL  COMMENT '是否自由流程' AFTER `customer_name`;

##20160919 lirz 资产复评意见(尽调)
ALTER TABLE f_investigation_credit_scheme_pledge_asset ADD COLUMN remark text COMMENT '复评意见' AFTER debted_amount;

##20160973 qch 风险预警处理表  授信业务基本情况
  ALTER TABLE `f_risk_warning_credit`
ADD COLUMN `project_code`  varchar(64) NULL AFTER `id`,
ADD COLUMN `dept_name`  varchar(64) NULL AFTER `project_code`,
ADD COLUMN `has_repay_plan`  varchar(32) NULL AFTER `debit_interest`;

ALTER TABLE `f_risk_warning_credit`
ADD COLUMN `josn_data`  text NULL AFTER `sort_order`;


ALTER TABLE `f_risk_warning`
DROP COLUMN `warning_bill_type`,
ADD COLUMN `warning_bill_type`  varchar(32) NULL COMMENT '单据类型(风险预警处理表,解除风险预警)' AFTER `form_id`;

ALTER TABLE `f_risk_warning`
ADD COLUMN `lifting_reason`  text NULL COMMENT '解除原因' AFTER `risk_measure`;

ALTER TABLE `f_risk_warning`
 ADD COLUMN   `src_waning_id` bigint(20) DEFAULT NULL COMMENT '关联风险处置表' AFTER `lifting_reason` ;

##2016-9-20 wuzj
 ALTER TABLE f_project_lg_litigation
  CHANGE COLUMN guarantee_fee guarantee_fee DECIMAL(20, 2) DEFAULT NULL COMMENT '担保费用',
  CHANGE COLUMN co_institution_charge co_institution_charge DECIMAL(20, 2) DEFAULT NULL COMMENT '合作机构服务费 元/%',
  CHANGE COLUMN deposit deposit DECIMAL(20, 2) DEFAULT NULL COMMENT '保证金 元/%',
  ADD COLUMN deposit_account VARCHAR(200) DEFAULT NULL COMMENT '保证金存入账户名' AFTER deposit_type;
  
ALTER TABLE  f_project_guarantee_entrusted
  ADD COLUMN deposit decimal(20, 2) DEFAULT NULL COMMENT '保证金 元/%' AFTER has_evaluate_company,
  ADD COLUMN deposit_type varchar(10) DEFAULT NULL COMMENT '保证金类型 元/%' AFTER deposit,
  ADD COLUMN deposit_account varchar(200) DEFAULT NULL COMMENT '保证金存入账户名' AFTER deposit_type;
  
  
  ## 20160920  添加 是否线上会议标记
  
  alter table `council` 
   add column `council_online` varchar(20) NULL COMMENT '是否线上会议' after `council_place`;
   
   ## 20160920  添加 是否主持人标记
   
   alter table `council_judges` 
   add column `compere` varchar(20) NULL COMMENT '是否主持人' after `judge_account`;
   
   ## 20160920  追偿抵质押信息长度增加，原值50
   
   alter table `project_recovery_debtor_reorganization_pledge` 
   change `asset_id` `asset_id` varchar(128) character set utf8 collate utf8_general_ci NULL  comment '关联资产表id', 
   change `pledge_name` `pledge_name` varchar(256) character set utf8 collate utf8_general_ci NULL  comment '名称';
   
   ## 20160921 追偿诉讼执行内容更定为2000
   alter table `project_recovery_litigation_execute_stuff` 
   change `value_stuff` `value_stuff` varchar(20001) character set utf8 collate utf8_general_ci NULL  comment '内容值';
   
   
  

   ## 20160920 风险项目上会申报表
  ALTER TABLE `f_council_apply_credit`
ADD COLUMN `project_code`  varchar(32) NULL AFTER `handle_id`,
ADD COLUMN `project_name`  varchar(64) NULL AFTER `project_code`,
ADD COLUMN `dept_name`  varchar(32) NULL AFTER `project_name`,
ADD COLUMN `credit_amount`  bigint(18) NULL COMMENT '授信金额' AFTER `dept_name`,
ADD COLUMN `guarantee_rate`  varchar(32) NULL AFTER `credit_amount`,
ADD COLUMN `bulgaria_balance`  bigint(18) NULL AFTER `guarantee_rate`,
ADD COLUMN `funding_sources`  varchar(64) NULL AFTER `bulgaria_balance`,
ADD COLUMN `json_data`  text NULL AFTER `sort_order`;

##20160920 尽调 授信方案修改
ALTER TABLE f_investigation_credit_scheme 
ADD COLUMN `deposit` decimal(20,4) DEFAULT NULL COMMENT '保证金 元/%' AFTER charge_type,
ADD COLUMN `deposit_type` varchar(10) DEFAULT NULL COMMENT '保证金类型 元/%' AFTER deposit,
ADD COLUMN `deposit_account` varchar(64) DEFAULT NULL COMMENT '保证金存入账户名' AFTER deposit_type;

ALTER TABLE f_investigation_litigation 
ADD COLUMN  `guarantee_amount` bigint(20) DEFAULT NULL COMMENT '本次申请保全金额' AFTER accept_date,
ADD COLUMN `deposit_account` varchar(64) DEFAULT NULL COMMENT '保证金存入账户名' AFTER guarantee_amount;

##20160920 上会申报记录
CREATE TABLE `f_council_apply_credit_compensation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `handle_id` bigint(20) DEFAULT NULL COMMENT 'f_council_apply_risk_handle.id',
  `project_code` varchar(32) DEFAULT NULL,
  `compensatory_principal` bigint(18) DEFAULT NULL COMMENT '代偿本金',
  `compensatory_interest_other` varchar(64) DEFAULT NULL COMMENT '代偿利息、罚息及其他',
  `expire_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '到期时间',
  `quasi_compensatory_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '拟代偿时间',
  `json_data` text COMMENT '扩展属性',
  `sort_order` bigint(20) DEFAULT NULL,
  `raw_add_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目上会申报 -- 授信业务基本情况--代偿明细表';


##20160920 上会申报记录
ALTER TABLE `f_council_apply_risk_handle`
MODIFY COLUMN `project_code`  varchar(900) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应项目编号' AFTER `form_id`,
MODIFY COLUMN `project_name`  varchar(900) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应项目名称' AFTER `project_code`;

##20160921 项目合同集
ALTER TABLE `f_project_contract`
  ADD COLUMN `apply_type` VARCHAR(20) NULL  COMMENT '申请类别(合同、文书、函)' AFTER `free_flow`;

  ALTER TABLE `f_project_contract_item`
  ADD COLUMN `letter_type` VARCHAR(20) NULL  COMMENT '(函/通知书)类别' AFTER `basis_of_decision`;


  
## 20160921 会议讨论项目添加主持人本次不议后续判定
alter table `council_projects` 
   add column `compere_message` varchar(128) NULL COMMENT '主持人本次不议后续描述' after `project_vote_result`;
   
   
##20160921 lirz 尽调 客户主要高管人员表
CREATE TABLE `f_investigation_mability_review_leading_team_resume` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ma_review_id` bigint(20) NOT NULL COMMENT '对应管理能力评价ID',  
  `owner` varchar(128) DEFAULT NULL COMMENT '所属',
  `tid` bigint(20) NOT NULL COMMENT 'f_investigation_mability_review_leading_team.id',
  `start_date` varchar(128) DEFAULT NULL COMMENT '起日',
  `end_date` varchar(128) DEFAULT NULL COMMENT '止日',
  `company_name` varchar(128) DEFAULT NULL COMMENT '单位',
  `title` varchar(128) DEFAULT NULL COMMENT '职务',
  `sort_order` int(11) DEFAULT '0',
  `raw_add_time` timestamp NULL DEFAULT NULL,
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ma_review_id_owner_i` (`ma_review_id`, `owner`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目调查 - 客户管理能力评价 - 客户主要高管人员表履历';

##20160922 尽调 增加地税号字段
ALTER TABLE f_investigation_mainly_review ADD `local_tax_no` varchar(50) DEFAULT NULL COMMENT '地税税务登记证号' AFTER tax_certificate_no;


##加长字段
ALTER TABLE bornbpm.ty_bm_exe_stack
  CHANGE COLUMN ASSIGNEES ASSIGNEES VARCHAR(2048) DEFAULT NULL COMMENT '执行人IDS，如1,2,3',
  CHANGE COLUMN NODEPATH NODEPATH VARCHAR(2048) DEFAULT NULL COMMENT '节点路径';

##20160922 诉保解保申请
ALTER TABLE f_counter_guarantee_release
ADD COLUMN `busi_type` varchar(50) DEFAULT NULL COMMENT '业务类型' AFTER time_unit,
ADD COLUMN `busi_type_name` varchar(50) DEFAULT NULL COMMENT '业务类型名称' AFTER busi_type,
ADD COLUMN `remark` text COMMENT '解保说明' AFTER busi_type_name;

##20160923
CREATE TABLE `f_investigation_asset_review` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `form_id` bigint(20) NOT NULL COMMENT '对应的表单ID',
  `review` varchar(16) DEFAULT 'NO' COMMENT '复议标识：YES',
  `project_code` varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  `project_name` varchar(128) DEFAULT NULL COMMENT '对应项目名称',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  `customer_name` varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  `busi_type` varchar(10) DEFAULT NULL COMMENT '业务类型',
  `busi_type_name` varchar(50) DEFAULT NULL COMMENT '业务类型名称',
  `amount` bigint(20) DEFAULT NULL COMMENT '授信金额',
  `busi_manager_id` bigint(20) DEFAULT NULL COMMENT '客户经理ID',
  `busi_manager_account` varchar(20) DEFAULT NULL COMMENT '客户经理账号',
  `busi_manager_name` varchar(50) DEFAULT NULL COMMENT '客户经理名称',
  `risk_manager_id` bigint(20) DEFAULT NULL COMMENT '风险经理ID',
  `risk_manager_account` varchar(20) DEFAULT NULL COMMENT '风险经理账号',
  `risk_manager_name` varchar(50) DEFAULT NULL COMMENT '风险经理名称',
  `status` varchar(32) DEFAULT NULL COMMENT '资产复评状态:未复评/复评中/已复评',
  `reviewer_id` bigint(20) DEFAULT NULL COMMENT '复评人ID',
  `reviewer_account` varchar(20) DEFAULT NULL COMMENT '复评人账号',
  `reviewer_name` varchar(50) DEFAULT NULL COMMENT '复评人名称',
  `review_time` datetime DEFAULT NULL COMMENT '复评时间',
  `raw_add_time` timestamp NULL DEFAULT NULL,
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `form_id_i` (`form_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='资产复评意见表'


##20160923 jil 资金划付申请单-是否回执
ALTER TABLE f_capital_appropriation_apply
ADD COLUMN `is_receipt` varchar(10) DEFAULT NULL COMMENT '是否回执' AFTER attach;

##20160924 lirz 保后 资料收集
ALTER TABLE f_afterwards_check_base 
ADD COLUMN `collect_data` text COMMENT '资料收集' AFTER collect_month;

##20160926 lirz 上会退回
ALTER TABLE f_investigation ADD COLUMN `council_back` varchar(16) DEFAULT 'NO' COMMENT '上会退回：YES' AFTER review;


##20160926 heh 征信税务登记证拆分成 国税地税
ALTER TABLE `born_fcs_fbpm`.`f_credit_refrerence_apply`
  ADD COLUMN `local_tax_cert_no` VARCHAR(50) NULL  COMMENT '地税证号码' AFTER `org_code`,
  CHANGE `tax_reg_certificate_no` `tax_reg_certificate_no` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '税务登记证号码(国税)';
  ALTER TABLE `born_fcs_fbpm`.`f_credit_refrerence_apply`
  CHANGE `busi_scope` `busi_scope` VARCHAR(1000) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '经营范围';
  
##20160924 jil 站内信增加 角色选择
ALTER TABLE message_info 
ADD COLUMN `message_sender_role` varchar(50) DEFAULT NULL COMMENT '接收人角色' AFTER message_sender_account;

##20160929 lirz 尽调
alter table f_investigation drop index f_investigation_form_id_i;
alter table f_investigation add unique key form_id_u(`form_id`);
alter table f_investigation_cs_rationality_review drop index f_investigation_cs_rationality_review_form_id_i;
alter table f_investigation_cs_rationality_review add unique key form_id_u(`form_id`);
alter table f_investigation_financial_review drop index f_investigation_financial_review_form_id_i;
alter table f_investigation_financial_review add unique key form_id_u(`form_id`);
alter table f_investigation_litigation drop index f_investigation_form_id_i;
alter table f_investigation_litigation add unique key form_id_u(`form_id`);
alter table f_investigation_mability_review drop index f_investigation_mability_review_form_id_i;
alter table f_investigation_mability_review add unique key form_id_u(`form_id`);

#20160930 lirz 保后 标识同一个项目最新的一期数据
ALTER TABLE f_afterwards_check ADD COLUMN is_lastest varchar(32) DEFAULT 'NO' COMMENT '是否最新的版本数据:YES/NO' AFTER round_time;



