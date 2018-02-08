
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
ALTER TABLE fcs_bpm.ty_bm_exe_stack
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
ALTER TABLE `f_credit_refrerence_apply`
  ADD COLUMN `local_tax_cert_no` VARCHAR(50) NULL  COMMENT '地税证号码' AFTER `org_code`,
  CHANGE `tax_reg_certificate_no` `tax_reg_certificate_no` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '税务登记证号码(国税)';
  ALTER TABLE `f_credit_refrerence_apply`
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

##20161008 heh 合同模板新增流程
ALTER TABLE `contract_template`
  ADD COLUMN `form_id` BIGINT(20) NULL  COMMENT '表单ID' AFTER `letter_type`,
  ADD COLUMN `user_id` BIGINT NULL  COMMENT '创建人ID' AFTER `form_id`,
  ADD COLUMN `user_name` VARCHAR(50) NULL  COMMENT '创建人名字' AFTER `user_id`,
  ADD COLUMN `user_account` VARCHAR(50) NULL  COMMENT '创建人账号' AFTER `user_name`,
  ADD COLUMN `legal_manager_id` BIGINT(20) NULL  COMMENT '法务经理ID' AFTER `user_account`,
  ADD COLUMN `legal_manager_account` VARCHAR(50) NULL  COMMENT '法务经理账号' AFTER `legal_manager_id`,
  ADD COLUMN `legal_manager_name` VARCHAR(50) NULL  COMMENT '法务经理名称' AFTER `legal_manager_account`,
  CHANGE `raw_add_time` `raw_add_time` TIMESTAMP NULL  COMMENT '新增时间'  AFTER `legal_manager_name`,
  CHANGE `raw_update_time` `raw_update_time` TIMESTAMP NULL  COMMENT '修改时间'  AFTER `raw_add_time`;

ALTER TABLE `contract_template`
ADD COLUMN `is_process` VARCHAR(10) NULL  COMMENT '是否需要走流程' AFTER `legal_manager_name`,
ADD COLUMN `parent_id` BIGINT(20) NULL  COMMENT '被修订模板的ID' AFTER `is_process`;

ALTER TABLE `contract_template`
  ADD COLUMN `revised` VARCHAR(10) NULL  COMMENT '是否被修订' AFTER `parent_id`;

  ALTER TABLE `f_project_contract_item`
  ADD COLUMN `credit_condition_type` VARCHAR(50) NULL  COMMENT '反担保措施' AFTER `letter_type`,
  ADD COLUMN `pledge_type` VARCHAR(100) NULL  COMMENT '抵押品类型' AFTER `credit_condition_type`;



##20161011 lirz 尽调-承销
ALTER TABLE f_investigation_underwriting 
  CHANGE `charge_rate` `charge_rate` decimal(20,4) DEFAULT NULL COMMENT '收费费率',
  CHANGE `charge_unit` `charge_unit` varchar(64) DEFAULT NULL COMMENT '收费费率单位(%/元)',  
  CHANGE `law_office_rate` `law_office_rate` decimal(20,4) DEFAULT NULL COMMENT '律所费率',
  CHANGE `law_office_unit` `law_office_unit` varchar(64) DEFAULT NULL COMMENT '律所费率单位(%/元)',
  CHANGE `club_rate` `club_rate` decimal(20,4) DEFAULT NULL COMMENT '会所费率',
  CHANGE `club_unit` `club_unit` varchar(64) DEFAULT NULL COMMENT '会所费率单位(%/元)',
  CHANGE `other_rate` `other_rate` decimal(20,4) DEFAULT NULL COMMENT '其他费用',
  CHANGE `other_unit` `other_unit` varchar(64) DEFAULT NULL COMMENT '其他费用单位(%/元)',  
  CHANGE `underwriting_rate` `underwriting_rate` decimal(20,4) DEFAULT NULL COMMENT '承销费率',
  CHANGE `underwriting_unit` `underwriting_unit` varchar(64) DEFAULT NULL COMMENT '承销费率单位(%/元)';

ALTER TABLE f_investigation ADD COLUMN `new_form_id` bigint(20) DEFAULT 0 COMMENT '上会退会后新生成的form' AFTER form_id;

##20161012 heh 档案
ALTER TABLE `f_file`
  ADD COLUMN `dept_code` VARCHAR(30) NULL  COMMENT '部门编码' AFTER `file_check_status`,
  ADD COLUMN `dept_name` VARCHAR(50) NULL  COMMENT '部门名称' AFTER `dept_code`,
  ADD COLUMN `update_time` DATETIME NULL  COMMENT '自定义更新时间' AFTER `dept_name`;

##20161014 会议纪要增加保证金字段
 ALTER TABLE f_council_summary_project_bond 
   ADD COLUMN deposit decimal(20, 4) DEFAULT NULL COMMENT '保证金 元/%' AFTER other_fee_type,
   ADD COLUMN deposit_type varchar(10) DEFAULT NULL COMMENT '保证金类型 元/%' AFTER deposit,
   ADD COLUMN deposit_account varchar(200) DEFAULT NULL COMMENT '保证金存入账户名' AFTER deposit_type;


 ALTER TABLE f_council_summary_project_guarantee 
   ADD COLUMN deposit decimal(20, 4) DEFAULT NULL COMMENT '保证金 元/%' AFTER other_fee_type,
   ADD COLUMN deposit_type varchar(10) DEFAULT NULL COMMENT '保证金类型 元/%' AFTER deposit,
   ADD COLUMN deposit_account varchar(200) DEFAULT NULL COMMENT '保证金存入账户名' AFTER deposit_type;


 ALTER TABLE f_council_summary_project_entrusted 
   ADD COLUMN deposit decimal(20, 4) DEFAULT NULL COMMENT '保证金 元/%' AFTER other_fee_type,
   ADD COLUMN deposit_type varchar(10) DEFAULT NULL COMMENT '保证金类型 元/%' AFTER deposit,
   ADD COLUMN deposit_account varchar(200) DEFAULT NULL COMMENT '保证金存入账户名' AFTER deposit_type;

ALTER TABLE f_council_summary_project_lg_litigation 
  CHANGE deposit deposit decimal(20, 4) DEFAULT NULL COMMENT '保证金 元/%',
   ADD COLUMN deposit_account varchar(200) DEFAULT NULL COMMENT '保证金存入账户名' AFTER deposit_type;

##20161015 heh合同
   ALTER TABLE `f_project_contract_item`
  ADD COLUMN `court_ruling_url` VARCHAR(255) NULL  COMMENT '法院裁定书url' AFTER `pledge_type`;
  ALTER TABLE `f_project_contract_item`
  ADD COLUMN `basis_of_decision_type` VARCHAR(50) NULL  COMMENT '决策依据类型(项目批复、签报、风险处置会议纪要)' AFTER `basis_of_decision`;

##20161015 lirz 尽调
alter table f_investigation_mainly_review_credit_info change `remark` `remark` text DEFAULT NULL COMMENT '异常备注';

## 富文本框 加长字段
ALTER TABLE form_change_apply
  CHANGE COLUMN change_remark change_remark MEDIUMTEXT DEFAULT NULL COMMENT '签报事项说明';

ALTER TABLE f_risk_warning
  CHANGE COLUMN risk_signal_detail risk_signal_detail MEDIUMTEXT DEFAULT NULL COMMENT '风险预警信号详细描述',
  CHANGE COLUMN risk_measure risk_measure MEDIUMTEXT DEFAULT NULL COMMENT '风险防范化解措施';
  
ALTER TABLE f_council_apply_risk_handle
  CHANGE COLUMN  basic_info basic_info mediumtext DEFAULT NULL COMMENT '基本情况',
  CHANGE COLUMN  risk_info risk_info mediumtext DEFAULT NULL COMMENT '风险事项',
  CHANGE COLUMN  last_council_decision last_council_decision mediumtext DEFAULT NULL COMMENT '前次风险处置会决议事项',
  CHANGE COLUMN  last_council_check last_council_check mediumtext DEFAULT NULL COMMENT '前次风险处置会决议落实情况',
  CHANGE COLUMN  this_council_scheme this_council_scheme mediumtext DEFAULT NULL COMMENT '本次上会提交的代偿方案及追偿方案/本次上会提交的处置方案',
  CHANGE COLUMN  guarantee_rate guarantee_rate decimal(20, 2) DEFAULT NULL COMMENT '担保费率';
  
ALTER TABLE f_council_summary
  CHANGE COLUMN overview overview MEDIUMTEXT DEFAULT NULL COMMENT '简述';
  
ALTER TABLE f_council_summary_project
  CHANGE COLUMN one_vote_down_mark one_vote_down_mark MEDIUMTEXT DEFAULT NULL COMMENT '一票否决的原因',
  CHANGE COLUMN remark remark MEDIUMTEXT DEFAULT NULL COMMENT '备注',
  CHANGE COLUMN other other MEDIUMTEXT DEFAULT NULL COMMENT '其他反担保措施';
  
ALTER TABLE f_council_summary_project_lg_litigation
  CHANGE COLUMN assure_object assure_object MEDIUMTEXT DEFAULT NULL COMMENT '本次申请保全标的';
  
ALTER TABLE f_council_summary_project_underwriting
  CHANGE COLUMN bourse_fee bourse_fee DECIMAL(20, 2) DEFAULT NULL COMMENT '交易所费',
  CHANGE COLUMN law_firm_fee law_firm_fee DECIMAL(20, 2) DEFAULT NULL COMMENT '律所费',
  CHANGE COLUMN club_fee club_fee DECIMAL(20, 2) DEFAULT NULL COMMENT '会所费',
  CHANGE COLUMN underwriting_fee underwriting_fee DECIMAL(20, 2) DEFAULT NULL COMMENT '承销费',
  CHANGE COLUMN other_fee other_fee DECIMAL(20, 2) DEFAULT NULL COMMENT '其他费用';
  
ALTER TABLE f_council_summary_risk_handle
  CHANGE COLUMN extend_remark extend_remark MEDIUMTEXT DEFAULT NULL COMMENT '展期说明',
  CHANGE COLUMN comp_remark comp_remark MEDIUMTEXT DEFAULT NULL COMMENT '代偿说明',
  CHANGE COLUMN other other MEDIUMTEXT DEFAULT NULL COMMENT '其他组合';
  
ALTER TABLE form_change_record
  CHANGE COLUMN form_data form_data MEDIUMTEXT DEFAULT NULL COMMENT '表单数据（序列化后的表单数据）',
  CHANGE COLUMN original_form_data original_form_data MEDIUMTEXT DEFAULT NULL COMMENT '原始表单数据（序列化后的表单数据）',
  CHANGE COLUMN page_content page_content MEDIUMTEXT DEFAULT NULL COMMENT '页面内容',
  CHANGE COLUMN original_page_content original_page_content MEDIUMTEXT DEFAULT NULL COMMENT '原页面内容';
  
ALTER TABLE form_change_record_detail
  CHANGE COLUMN old_text old_text MEDIUMTEXT DEFAULT NULL COMMENT '旧值描述',
  CHANGE COLUMN old_value old_value MEDIUMTEXT DEFAULT NULL COMMENT '原值',
  CHANGE COLUMN new_value new_value MEDIUMTEXT DEFAULT NULL COMMENT '新值',
  CHANGE COLUMN new_text new_text MEDIUMTEXT DEFAULT NULL COMMENT '新值描述';
  
ALTER TABLE form_message_templete
  CHANGE COLUMN subject subject VARCHAR(500) DEFAULT NULL COMMENT '消息主题（主要用于邮件）',
  CHANGE COLUMN content_html content_html MEDIUMTEXT DEFAULT NULL COMMENT 'html消息内容',
  CHANGE COLUMN content_txt content_txt MEDIUMTEXT DEFAULT NULL COMMENT '文字消息内容';  
  
  
 #20161017 jil 资金划付 
ALTER TABLE f_capital_appropriation_apply
  ADD COLUMN customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID' AFTER project_name,
  ADD COLUMN customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称' AFTER customer_id,
  ADD COLUMN busi_type VARCHAR(50) DEFAULT NULL COMMENT '业务类型' AFTER customer_name,
  ADD COLUMN busi_type_name varchar(120) DEFAULT NULL COMMENT '业务类型名称' AFTER busi_type;  
#20161017 jil 退费申请单 
  ALTER TABLE f_refund_application_fee
  	ADD COLUMN basis_of_decision VARCHAR(20) NULL  COMMENT '决策依据(项目批复、签报FormId)' AFTER remark,
    ADD COLUMN basis_of_decision_type VARCHAR(50) NULL  COMMENT '决策依据类型(项目批复、签报、风险处置会议纪要)' AFTER basis_of_decision;
  
##2016-10-17
  ALTER TABLE f_council_summary_project_process_control
  CHANGE COLUMN content content TEXT DEFAULT NULL COMMENT '信用等级/资产负债率警戒/过程控制内容';

##20161018 heh合同

ALTER TABLE `f_project_contract_item`
  ADD COLUMN `court_ruling_time` TIMESTAMP NULL  COMMENT '裁定书出具时间' AFTER `court_ruling_url`;
  
## 2016-10-18 报表添加字段
ALTER TABLE `project_data_info` 
   ADD COLUMN `channal_code` VARCHAR(32) NULL COMMENT '渠道编号' AFTER `capital_sub_channel_name`, 
   ADD COLUMN `channal_credit_startDate` DATETIME NULL COMMENT '渠道_授信起始时间' AFTER `channal_code`, 
   ADD COLUMN `channal_credit_endDate` DATETIME NULL COMMENT '渠道_授信截止时间' AFTER `channal_credit_startDate`, 
   ADD COLUMN `channal_loss_allocationRate` DOUBLE NULL COMMENT '渠道_风险分摊比例' AFTER `channal_credit_endDate`, 
   ADD COLUMN `channal_single_limit` VARCHAR(128) NULL COMMENT '渠道_单笔限额' AFTER `channal_loss_allocationRate`, 
   ADD COLUMN `channal_credit_amount` VARCHAR(128) NULL COMMENT '渠道_授信额度' AFTER `channal_single_limit`, 
   ADD COLUMN `channal_compensatory_limit` VARCHAR(128) NULL COMMENT '渠道_代偿期限' AFTER `channal_credit_amount`,
   CHANGE `channal_type` `channal_type` VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL  COMMENT '渠道类型' ; 
ALTER TABLE `project_data_info_his_data` 
   ADD COLUMN `channal_code` VARCHAR(32) NULL COMMENT '渠道编号' AFTER `capital_sub_channel_name`, 
   ADD COLUMN `channal_credit_startDate` DATETIME NULL COMMENT '渠道_授信起始时间' AFTER `channal_code`, 
   ADD COLUMN `channal_credit_endDate` DATETIME NULL COMMENT '渠道_授信截止时间' AFTER `channal_credit_startDate`, 
   ADD COLUMN `channal_loss_allocationRate` DOUBLE NULL COMMENT '渠道_风险分摊比例' AFTER `channal_credit_endDate`, 
   ADD COLUMN `channal_single_limit` VARCHAR(128) NULL COMMENT '渠道_单笔限额' AFTER `channal_loss_allocationRate`, 
   ADD COLUMN `channal_credit_amount` VARCHAR(128) NULL COMMENT '渠道_授信额度' AFTER `channal_single_limit`, 
   ADD COLUMN `channal_compensatory_limit` VARCHAR(128) NULL COMMENT '渠道_代偿期限' AFTER `channal_credit_amount`,
   CHANGE `channal_type` `channal_type` VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL  COMMENT '渠道类型' ; 


##20161018 lirz 尽调
ALTER TABLE `f_investigation_credit_scheme`
CHANGE COLUMN `statement` `statement` mediumtext NULL COMMENT '需重点说明的授信事项',
CHANGE COLUMN `other` `other` mediumtext NULL COMMENT '其它';

ALTER TABLE `f_investigation_litigation`
CHANGE COLUMN `case_introduce` `case_introduce` mediumtext COMMENT '案情介绍',
CHANGE COLUMN `content` `content` mediumtext COMMENT '拟保全标的或内容',
CHANGE COLUMN `audit_opinion` `audit_opinion` mediumtext COMMENT '风险审查意见',
CHANGE COLUMN `synthesize_opinion` `synthesize_opinion` mediumtext COMMENT '项目综合意见';

ALTER TABLE `f_investigation_underwriting`
CHANGE COLUMN `intro` `intro` mediumtext COMMENT '发行主体简要介绍';

ALTER TABLE `f_investigation_mainly_review`
CHANGE COLUMN `busi_scope` `busi_scope` mediumtext COMMENT '业务范围',
CHANGE COLUMN `customer_dev_evolution` `customer_dev_evolution` mediumtext COMMENT '客户发展沿革（背景）及重大机构变革',
CHANGE COLUMN `related_trade` `related_trade` mediumtext COMMENT '关联交易情况',
CHANGE COLUMN `related_guarantee` `related_guarantee` mediumtext COMMENT '关联担保情况',
CHANGE COLUMN `related_capital_tieup` `related_capital_tieup` mediumtext COMMENT '关联资金占用',
CHANGE COLUMN `busi_qualification` `busi_qualification` mediumtext COMMENT '主营业务范围及资质情况',
CHANGE COLUMN `busi_place` `busi_place` mediumtext COMMENT '经营场所调查情况',
CHANGE COLUMN `other` `other` mediumtext COMMENT '其他情况';

ALTER TABLE `f_investigation_mability_review` 
CHANGE COLUMN `leader_review` `leader_review` mediumtext COMMENT '领导人整体评价（包括主要领导人简历、管理层的稳定性）',
CHANGE COLUMN `staff_review` `staff_review` mediumtext COMMENT '员工基本情况及整体素质评价';

ALTER TABLE `f_investigation_cs_rationality_review` 
CHANGE COLUMN `amount_limit_review` `amount_limit_review` mediumtext COMMENT '本次授信额度合理性评价',
CHANGE COLUMN `time_limit_review` `time_limit_review` mediumtext COMMENT '本次授信期限合理性评价',
CHANGE COLUMN `loan_purpose_review` `loan_purpose_review` mediumtext COMMENT '授信用途合理性评价',
CHANGE COLUMN `repay_source_review` `repay_source_review` mediumtext COMMENT '第一还款来源分析',
CHANGE COLUMN `guarantor_review` `guarantor_review` mediumtext COMMENT '保证人合法性评价（此处不含担保公司评价）',
CHANGE COLUMN `guarantee_company_info` `guarantee_company_info` mediumtext COMMENT '担保公司基本情况',
CHANGE COLUMN `guarantee_company_ability` `guarantee_company_ability` mediumtext COMMENT '担保公司担保能力总体评价（担保公司股东背景及股份结构、履保情况、合作情况等）',
CHANGE COLUMN `counter_guarantee_info` `counter_guarantee_info` mediumtext COMMENT '客户提供反担保情况（担保方式、资产种类、数量及价值情况）',
CHANGE COLUMN `guarantor_info` `guarantor_info` mediumtext COMMENT '担保人基本情况及合法性评价',
CHANGE COLUMN `pledge_value` `pledge_value` mediumtext COMMENT '担保物基本情况及评估价值评价',
CHANGE COLUMN `review_summary` `review_summary` mediumtext COMMENT '评估机构名称、评估时间、评估方法、初评价值评价',
CHANGE COLUMN `other_repay_source` `other_repay_source` mediumtext COMMENT '其它还款来源';

ALTER TABLE `f_investigation_financial_review` 
CHANGE COLUMN `audit_suggest_explain` `audit_suggest_explain` mediumtext COMMENT '审计意见解释与说明',
CHANGE COLUMN `debt_paying_ability` `debt_paying_ability` mediumtext COMMENT '偿债能力解释与说明',
CHANGE COLUMN `operating_ability` `operating_ability` mediumtext COMMENT '运营能力分析解释与说明',
CHANGE COLUMN `profit_ability` `profit_ability` mediumtext COMMENT '盈利能力分析解释与说明',
CHANGE COLUMN `cash_flow_explain` `cash_flow_explain` mediumtext COMMENT '现金流分析解释与说明',
CHANGE COLUMN `asset_quality_review` `asset_quality_review` mediumtext COMMENT '资产质量总体评价';

ALTER TABLE `f_investigation_financial_review_kpi` 
CHANGE COLUMN `remark` `remark` mediumtext DEFAULT NULL COMMENT '说明';

ALTER TABLE `f_investigation_major_events` 
CHANGE COLUMN `financial_condition` `financial_condition` mediumtext COMMENT '其他重点财务情况调查（民间借贷、异常科目等）',
CHANGE COLUMN `investment` `investment` mediumtext COMMENT '多元化投资调查',
CHANGE COLUMN `other_events` `other_events` mediumtext COMMENT '其他重大事项调查（城市开发类项目对当地经济、财政、支持程度的分析填写本项内容）';

ALTER TABLE `f_investigation_opability_review` 
CHANGE COLUMN `strategy_marketpos` `strategy_marketpos` mediumtext COMMENT '客户发展战略及市场定位',
CHANGE COLUMN `industry_env` `industry_env` mediumtext COMMENT '客户所在行业的宏观环境分析',
CHANGE COLUMN `competitiveness_rival` `competitiveness_rival` mediumtext COMMENT '客户核心竞争力评价、主要竞争对手基本情况',
CHANGE COLUMN `explaination` `explaination` mediumtext COMMENT '解释与说明';

ALTER TABLE `f_investigation_project_status` 
CHANGE COLUMN `overview` `overview` mediumtext COMMENT '项目概述（简要说明客户/项目发起人的项目计划，生产规模，地理位置等）',
CHANGE COLUMN `background` `background` mediumtext COMMENT '项目建设背景/必要性',
CHANGE COLUMN `approval` `approval` mediumtext COMMENT '项目审批依据或手续',
CHANGE COLUMN `progress` `progress` mediumtext COMMENT '项目建设进度（如项目已开工，请简述工程形象进度和资金投入进度）',
CHANGE COLUMN `market_outlook` `market_outlook` mediumtext COMMENT '市场前景分析',
CHANGE COLUMN `benefit_review` `benefit_review` mediumtext COMMENT '项目财务效益评估';

ALTER TABLE `f_investigation_risk` 
CHANGE COLUMN `risk_analysis` `risk_analysis` mediumtext COMMENT '风险点分析',
CHANGE COLUMN `conclusion` `conclusion` mediumtext COMMENT '结论意见';

ALTER TABLE f_council_summary_risk_handle
  ADD COLUMN extend_data TEXT DEFAULT NULL COMMENT '展期数据' AFTER extend_remark;

################ 2016-10-18 以上已执行 ####################

## 20161019 heh
ALTER TABLE `project`
  ADD COLUMN `is_court_ruling` VARCHAR(10) DEFAULT 'NO'   NULL  COMMENT '是否上传法院裁定书(只针对诉保项目)' AFTER `last_recouncil_time`;
  ALTER TABLE `project`
  ADD COLUMN `court_ruling_time` DATETIME NULL  COMMENT '出具法院裁定书时间' AFTER `is_court_ruling`;
  
  
############## 2016-10-19 以上已执行 ############

## 20161020 heh
ALTER TABLE `f_file_input_list`
  ADD COLUMN `curr_borrow_id` BIGINT NULL  COMMENT '当前借阅单Id' AFTER `curr_borrow_man_id`;

############# 2016-10-21 以上已执行 ##############


## 20161021 heh
ALTER TABLE `f_project_contract_item`
  ADD COLUMN `contract_amount` BIGINT NULL  COMMENT '合同金额' AFTER `court_ruling_time`;
  ALTER TABLE `project`
  ADD COLUMN `contract_amount` BIGINT NULL  COMMENT '合同金额' AFTER `contract_time`;

##　20161021 渠道报表修改
ALTER TABLE `project_data_info` 
   ADD COLUMN `contract_amount` BIGINT(20) NULL COMMENT '合同金额' AFTER `contract_time`;
ALTER TABLE `project_data_info_his_data` 
   ADD COLUMN `contract_amount` BIGINT(20) NULL COMMENT '合同金额' AFTER `contract_time`;
   
## 2016-10-21 附件管理新增项目编号   
ALTER TABLE common_attachment
  ADD COLUMN project_code VARCHAR(50) DEFAULT NULL COMMENT '项目编号' AFTER uploader_name,
  ADD COLUMN remark TEXT DEFAULT NULL COMMENT '备注' AFTER project_code;

  ## 20161022 jil
CREATE TABLE f_finance_affirm (
  affirm_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '主表form_id',
  affirm_form_type varchar(128) DEFAULT NULL COMMENT '表单类型（收费、资金划付）',
  amount bigint(20) DEFAULT NULL COMMENT '金额(明细加总)',
  remark text DEFAULT NULL COMMENT '备注',
  attach text DEFAULT NULL COMMENT '附件',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (affirm_id),
  INDEX finance_affirm_id_i (affirm_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '财务确认-资金划付和收费通知';
## 20161022 jil
CREATE TABLE f_finance_affirm_detail (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  affirm_id bigint(20) NOT NULL COMMENT '主表id',
  detail_id bigint(20) NOT NULL COMMENT '对应明细id（收款单，资金划付单）',
  fee_type varchar(50) DEFAULT NULL COMMENT '费用种类',
  pay_amount bigint(20) DEFAULT NULL COMMENT '金额(付款金额，收费金额)',
  pay_time datetime DEFAULT NULL COMMENT '付款时间(收款时间)',
  payee_account_name varchar(100) DEFAULT NULL COMMENT '收款方账户名',
  deposit_account varchar(100) DEFAULT NULL COMMENT '存出保证金账户',
  margin_rate decimal(12, 4) DEFAULT NULL COMMENT '保证金利率',
  deposit_time datetime DEFAULT NULL COMMENT '存入时间',
  period int(11) DEFAULT NULL COMMENT '存入期限',
  period_unit varchar(32) DEFAULT NULL COMMENT '存入期限单位',
  attach text DEFAULT NULL COMMENT '附件',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX finance_affirm_detail_id_i (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '财务确认-资金划付和收费通知-明细';
  
## 20161022 决策依据修改
ALTER TABLE `f_project_contract_item`
  ADD COLUMN `form_change` VARCHAR(1000) NULL  COMMENT '决策依据(签报formId)' AFTER `contract_amount`,
  ADD COLUMN `risk_council_summary` VARCHAR(1000) NULL  COMMENT '决策依据(风险处理会议纪要)' AFTER `form_change`,
  ADD COLUMN `project_approval` VARCHAR(50) DEFAULT 'NO'   NULL  COMMENT '决策依据(项目批复)' AFTER `risk_council_summary`;

##20161022
ALTER TABLE f_project_customer_base_info 
   ADD COLUMN `money_type` VARCHAR(32) NULL COMMENT '币种类型' AFTER `register_capital`, 
   ADD COLUMN `money_type_name` VARCHAR(32) NULL COMMENT '币种名' AFTER `money_type`;
   
##20161022 会议纪要阶段不明确的允许填写文本   
ALTER TABLE f_council_summary_risk_handle
  CHANGE COLUMN comp_interest comp_interest VARCHAR(500) DEFAULT NULL COMMENT '代偿利息',
  CHANGE COLUMN comp_penalty comp_penalty VARCHAR(500) DEFAULT NULL COMMENT '代偿违约金',
  CHANGE COLUMN comp_penalty_interest comp_penalty_interest VARCHAR(500) DEFAULT NULL COMMENT '代偿罚息',
  CHANGE COLUMN comp_other comp_other VARCHAR(500) DEFAULT NULL COMMENT '代偿其他'; 
  
## 20161022 决策依据修改 jil
ALTER TABLE f_refund_application_fee
  ADD COLUMN form_change VARCHAR(1000) NULL  COMMENT '决策依据(签报formId)' AFTER basis_of_decision_type,
  ADD COLUMN risk_council_summary VARCHAR(1000) NULL  COMMENT '决策依据(风险处理会议纪要)' AFTER form_change,
  ADD COLUMN project_approval VARCHAR(50) DEFAULT 'NO'   NULL  COMMENT '决策依据(项目批复IS/NO)' AFTER risk_council_summary;

#### 20161022 尽职字段修订为string
alter table `f_investigation_mainly_review_credit_status` 
   change `loan_cost` `loan_cost` varchar(200) NULL  comment '融资成本';
   
   alter table `f_investigation_mainly_review_asset_and_liability` 
   change `house_num` `house_num` varchar(200) NULL  comment '房屋数量', 
   change `car_num` `car_num` varchar(200) NULL  comment '车辆数量';
   
   alter table `f_investigation_mainly_review_asset_and_liability` 
   change `invest_amount` `invest_amount` varchar(200) NULL  comment '投资金额', 
   change `deposit_amount` `deposit_amount` varchar(200) NULL  comment '个人存款金额';
   
##20161022 lirz 保后 诉保 审核
alter table f_afterwards_check_litigation add column `audit_opinion` mediumtext COMMENT '风险审查意见' after remark;

ALTER TABLE `f_file_output`
  CHANGE `output_reason` `output_reason` VARCHAR(1000) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '出库原因';
ALTER TABLE `f_file_borrow`
  CHANGE `borrow_reason` `borrow_reason` VARCHAR(1000) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '借阅原因';

####################10.22以上已经执行###################  
  
##20161025  
ALTER TABLE `f_charge_notification`
  ADD COLUMN `contract_name` VARCHAR(100) NULL  COMMENT '合同名称' AFTER `contract_code`;
##20161025 heh 合同
  ALTER TABLE `f_project_contract_item`
  ADD COLUMN `refer_attachment` TEXT NULL  COMMENT '参考附件' AFTER `project_approval`;

##20161025 lirz 尽调
ALTER TABLE f_investigation_credit_scheme_guarantor CHANGE COLUMN `guarantee_way` `guarantee_way` text DEFAULT NULL COMMENT '保证方式';

ALTER TABLE f_investigation_opability_review 
ADD COLUMN up_desc mediumtext COMMENT '上游情况' AFTER competitiveness_rival,
ADD COLUMN down_desc mediumtext COMMENT '下游情况' AFTER up_desc;

ALTER TABLE f_investigation_mability_review_leading_team ADD COLUMN birth varchar(128) DEFAULT NULL COMMENT '生日' AFTER sex;

##20161025 jil 发债类项目-分保信息
CREATE TABLE project_bond_reinsurance_information (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(50) DEFAULT NULL COMMENT '对应发债项目编号',
  project_name varchar(128) DEFAULT NULL COMMENT '项目名称',
  reinsurance_object varchar(256) DEFAULT NULL COMMENT '分保方',
  reinsurance_ratio decimal(12, 4) DEFAULT NULL COMMENT '分保比例',
  reinsurance_amount bigint(20) DEFAULT NULL COMMENT '分保金额',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX project_bond_reinsurance_peoject_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '发债类项目-分保信息(第一次发售时填写)';

##20161025 jil 发售信息维护
ALTER TABLE project_issue_information
  ADD COLUMN is_reinsurance VARCHAR(10) NULL  COMMENT '是否分保' AFTER status;
##20161025 jil 授信落实附件增加文本信息填写
ALTER TABLE project_credit_condition
  ADD COLUMN text_info VARCHAR(128) NULL  COMMENT '文本信息（附件上传可选填写文本）' AFTER contract_agreement_url;

ALTER TABLE f_credit_condition_confirm_item
  ADD COLUMN text_info VARCHAR(128) NULL  COMMENT '文本信息（附件上传可选填写文本）' AFTER contract_agreement_url;


##20161026 lirz 风险等级评级
ALTER TABLE f_risk_level
ADD COLUMN `has_evaluation_desc` varchar(128) DEFAULT 'NO' COMMENT '使用初评特别说明:YES/NO' AFTER evaluation_level,
ADD COLUMN `evaluation_desc` mediumtext COMMENT '初评特别说明' AFTER has_evaluation_desc,
ADD COLUMN `has_reevaluation_desc` varchar(128) DEFAULT 'NO' COMMENT '使用复评特别说明:YES/NO' AFTER reevaluation_level,
ADD COLUMN `reevaluation_desc` mediumtext COMMENT '复评特别说明' AFTER has_reevaluation_desc;

##20161026 jil 
ALTER TABLE project_credit_asset_attachment
  ADD COLUMN image_text_value VARCHAR(128) NULL  COMMENT '资产图像信息value值(文本值)' AFTER status;
  
## 20161026 会议修改会议地点
UPDATE council SET council_place = '一会议室(五楼)' WHERE council_place = '一会议室' ;
UPDATE council SET council_place = '二会议室(四楼)' WHERE council_place = '二会议室' ;
UPDATE council SET council_place = '三会议室(三楼)' WHERE council_place = '三会议室' ;
UPDATE council SET council_place = '四会议室(二楼)' WHERE council_place = '四会议室' ;
UPDATE council SET council_place = '五会议室(负一楼)' WHERE council_place = '五会议室' ;

######################2016-10-26 以上已执行#####################

## 20161027 决策依据字段长度修改 jil
ALTER TABLE f_refund_application_fee MODIFY column project_approval varchar(50);


################################################################


##2016-10-27 app配置
CREATE TABLE app_about_conf (
  conf_id bigint(20) NOT NULL COMMENT '主键',
  online varchar(10) DEFAULT NULL COMMENT '是否上线 YES/NO',
  content mediumtext DEFAULT NULL COMMENT '关于我们内容',
  logo varchar(200) DEFAULT NULL COMMENT 'logo地址',
  ios_path varchar(200) DEFAULT NULL COMMENT 'IOS包路径',
  ios_version varchar(50) DEFAULT NULL COMMENT 'IOS版本号',
  ios_change_log text DEFAULT NULL COMMENT '更新内容',
  ios_force_update int(11) DEFAULT NULL COMMENT '强制升级',
  ios_option_update int(11) DEFAULT NULL COMMENT '可选升级',
  ios_two_dimension varchar(200) DEFAULT NULL COMMENT 'IOS二维码',
  android_path varchar(200) DEFAULT NULL COMMENT 'android包路径',
  android_version varchar(50) DEFAULT NULL COMMENT 'android版本',
  android_change_log text DEFAULT NULL COMMENT 'android更新日志',
  android_force_update int(11) DEFAULT NULL COMMENT '强制升级',
  android_option_update int(11) DEFAULT NULL COMMENT '可选升级',
  android_two_dimension varchar(200) DEFAULT NULL COMMENT 'android二维码',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (conf_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = 'app关于我们配置';

### 20161028  追偿主表添加 是否执行其他
alter table `project_recovery` 
   add column `other_on` varchar(20) NULL COMMENT '是否执行其他信息' after `debtor_reorganization_on`;
## 20161028 增加分保信息 jil
ALTER TABLE project
  add COLUMN reinsurance_ratio decimal(12, 4) DEFAULT NULL  COMMENT '分保比例' AFTER court_ruling_time,
  add COLUMN reinsurance_amount bigint(20) DEFAULT NULL  COMMENT '分保金额' AFTER reinsurance_ratio;
    
##20161028 lirz  保后-未收集资料说明
CREATE TABLE `f_afterwards_not_collected_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `form_id` bigint(20) NOT NULL COMMENT '对应的表单ID',
  `type` varchar(128) DEFAULT NULL COMMENT '类型',
  `remark` text COMMENT '说明',
  `raw_add_time` timestamp NULL DEFAULT NULL,
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `form_id` (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保后检查-未收集资料说明';

ALTER TABLE f_afterwards_check_loans ADD COLUMN `cash_deposit` varchar(64) DEFAULT NULL COMMENT '保证金比例' AFTER cash_deposit_rate;



## 20161029 决策依据修改-资金划付 jil
ALTER TABLE f_capital_appropriation_apply_fee
  ADD COLUMN form_change VARCHAR(1000) DEFAULT NULL  COMMENT '决策依据(签报formId)' AFTER remark,
  ADD COLUMN risk_council_summary VARCHAR(1000) DEFAULT NULL  COMMENT '决策依据(风险处理会议纪要)' AFTER form_change,
  ADD COLUMN project_approval VARCHAR(50) DEFAULT NULL  COMMENT '决策依据(项目编号)' AFTER risk_council_summary;
## 20161029  修改  
UPDATE f_refund_application_fee fcaaf set fcaaf.project_approval=NULL WHERE fcaaf.project_approval='NO'; 
ALTER TABLE f_refund_application_fee
  CHANGE project_approval project_approval VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '决策依据(项目批复)';
##20161029 lirz 解保申请
ALTER TABLE f_counter_guarantee_release
  ADD COLUMN form_change VARCHAR(1000) DEFAULT NULL  COMMENT '决策依据(签报formId)' AFTER apply_amount,
  ADD COLUMN risk_council_summary VARCHAR(1000) DEFAULT NULL  COMMENT '决策依据(风险处理会议纪要)' AFTER form_change,
  ADD COLUMN project_approval VARCHAR(50) DEFAULT NULL  COMMENT '决策依据(项目编号)' AFTER risk_council_summary;

CREATE TABLE `f_counter_guarantee_repay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `form_id` bigint(20) NOT NULL COMMENT '对应的表单ID',
  `repay_amount` bigint(20) DEFAULT NULL COMMENT '还款金额',
  `repay_time` varchar(128) DEFAULT NULL COMMENT '还款时间',
  `sort_order` int(11) DEFAULT NULL,  
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '新增时间',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `form_id` (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='解保申请-还款详情';

##20161029 heh 合同修改默认值
ALTER TABLE `f_project_contract_item`
  CHANGE `project_approval` `project_approval` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '决策依据(项目批复)';

########################29以上#############################

##20161031 jil 财务确认
ALTER TABLE f_finance_affirm_detail
  ADD COLUMN  return_customer_amount bigint(20) DEFAULT NULL  COMMENT '退还客户金额(剩余多少)' AFTER pay_amount;
##资金划付明细 选择财务确认收费通知单的存入保证金
ALTER TABLE f_capital_appropriation_apply_fee
  ADD COLUMN  finance_affirm_detail_id varchar(500) DEFAULT NULL  COMMENT '财务确认明细id(可能多个)' AFTER project_approval;
ALTER TABLE f_finance_affirm
  ADD COLUMN   project_code varchar(50) NOT NULL COMMENT '项目编号' AFTER form_id;


## 20161031 heh
ALTER TABLE `f_charge_notification_fee`
  ADD COLUMN `affirm_detail_ids` VARCHAR(1000) NULL  COMMENT '资金划付财务确认明细id' AFTER `end_time`;
## 20161031 jil 
CREATE TABLE charge_notice_capital_approproation (
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  detail_id BIGINT(20) NOT NULL COMMENT '收款通知单/资金划付明细id',
  type VARCHAR(50) DEFAULT NULL COMMENT '业务类型(收费/资金划付)',
  use_amount BIGINT(20) DEFAULT NULL COMMENT '使用金额',
  pay_id VARCHAR(128) DEFAULT NULL COMMENT '财务确认明细id',
  raw_add_time TIMESTAMP NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='收款通知单-资金划付相互关联表';


##heh 20161103 征信查询
ALTER TABLE `f_credit_refrerence_apply`
  CHANGE `busi_license_url` `busi_license_url` VARCHAR(500) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '营业执照附件',
  CHANGE `afmg_approval_url` `afmg_approval_url` VARCHAR(500) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '保后管理征信查询审批表',
  CHANGE `auth_url` `auth_url` VARCHAR(500) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '信查询授权书——企业/法人',
  CHANGE `legal_persion_cert_front` `legal_persion_cert_front` VARCHAR(500) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '法定代表人身份证-正面',
  CHANGE `legal_persion_cert_back` `legal_persion_cert_back` VARCHAR(500) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '法定代表人身份证-反面',
  CHANGE `loan_card_front` `loan_card_front` VARCHAR(500) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '贷款卡-正面',
  CHANGE `loan_card_back` `loan_card_back` VARCHAR(500) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '贷款卡-反面',
  ADD COLUMN `customer_apply_url` VARCHAR(500) NULL  COMMENT '客户申请书url' AFTER `attachment`;
  ########################20161103 征信查询已单独发出#############################
  
  
  
#####################################11.3########################################  
  
  
## wuzj  2016-10-31
ALTER TABLE financial_product
  ADD COLUMN year_day_num INT(11) DEFAULT 365 COMMENT '产品计算收益时候一年的计算天数' AFTER risk_level;  
  
ALTER TABLE f_project_financial
  ADD COLUMN is_roll VARCHAR(10) DEFAULT 'NO'  COMMENT '是否滚动' AFTER can_redeem,
  ADD COLUMN year_day_num INT(11) DEFAULT 365 COMMENT '产品计算收益时候一年的计算天数' AFTER is_roll; 
  
ALTER TABLE project_financial
  ADD COLUMN settlement_amount BIGINT(20) DEFAULT NULL COMMENT '累计结息金额' AFTER actual_interest_rate,
  ADD COLUMN is_roll varchar(10) DEFAULT 'NO'  COMMENT '是否滚动' AFTER can_redeem,
  ADD COLUMN is_open varchar(10) DEFAULT 'NO'  COMMENT '是否开放' AFTER is_roll,
  ADD COLUMN year_day_num INT(11) DEFAULT 365 COMMENT '产品计算收益时候一年的计算天数' AFTER is_open,
  ADD COLUMN cycle_expire_date DATETIME DEFAULT NULL COMMENT '滚动到期时间' AFTER actual_expire_date;
  
ALTER TABLE project_financial_withdrawing
  ADD COLUMN year_day_num INT(11) DEFAULT 365 COMMENT '产品计算收益时候一年的计算天数' AFTER product_name; 

##理财数据订正  
update project_financial set is_roll = 'NO',is_open='NO',year_day_num=365,cycle_expire_date=actual_expire_date;
update f_project_financial set is_roll = 'NO',year_day_num=365
update financial_product set year_day_num = 365;


## wuzj 16-11-2  
CREATE TABLE project_financial_settlement (
  settlement_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  original_code varchar(128) DEFAULT NULL COMMENT '立项时的编号',
  product_id bigint(20) DEFAULT NULL COMMENT '产品ID',
  product_name varchar(128) DEFAULT NULL COMMENT '产品名称',
  settlement_amount bigint DEFAULT NULL COMMENT '结息金额',
  settlement_time datetime DEFAULT NULL COMMENT '结息时间',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (settlement_id),
  INDEX project_financial_settlement_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '理财项目结息信息';    

##20161104 jil 收费&还款计划增加是否确认录入
ALTER TABLE charge_repay_plan
  ADD COLUMN is_affirm varchar(10) DEFAULT 'NO' COMMENT '是否确认录入' AFTER remark;
  
##lirz 20161104 尽调
ALTER TABLE `f_investigation_credit_scheme`
CHANGE COLUMN `charge_rate`  `charge_rate` decimal(20,4) NULL DEFAULT NULL COMMENT '费率';

############################11-7#################################

##20161107 lirz 尽调
CREATE TABLE `f_investigation_cs_rationality_review_guarantor` (
  `guarantor_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `form_id` bigint(20) NOT NULL COMMENT '对应表单ID',
  `guarantor_type` varchar(128) DEFAULT NULL COMMENT '法人/个人',
  `guarantor_name` varchar(128) DEFAULT NULL COMMENT '保证人名称',
  `established_time` varchar(128) DEFAULT NULL COMMENT '成立时间',
  `operating_term` varchar(64) DEFAULT NULL COMMENT '经营期限',
  `legal_persion` varchar(64) DEFAULT NULL COMMENT '法定代表人',
  `org_code` varchar(64) DEFAULT NULL COMMENT '组织机构代码证',
  `living_address` varchar(128) DEFAULT NULL COMMENT '住址',
  `actual_control_person` varchar(64) DEFAULT NULL COMMENT '实际控制人',
  `enterprise_type` varchar(32) DEFAULT NULL COMMENT '企业类型',
  `work_address` varchar(128) DEFAULT NULL COMMENT '办公地址',
  `is_one_cert` varchar(10) DEFAULT 'NO' COMMENT '是否三证合一',
  `busi_license_no` varchar(50) DEFAULT NULL COMMENT '营业执照号',
  `tax_certificate_no` varchar(50) DEFAULT NULL COMMENT '税务登记证号/国税号',
  `local_tax_no` varchar(50) DEFAULT NULL COMMENT '地税税务登记证号',
  `loan_card_no` varchar(50) DEFAULT NULL COMMENT '贷款卡号',
  `last_check_year` varchar(10) DEFAULT NULL COMMENT '最后年检年度',
  `busi_scope` mediumtext COMMENT '业务范围',
  `leader_review` mediumtext COMMENT '领导人整体评价（包括主要领导人简历、管理层的稳定性）',
  `guarantor_cert_type` varchar(50) DEFAULT NULL COMMENT '证件类型',
  `guarantor_sex` varchar(50) DEFAULT NULL COMMENT '保证人性别',
  `guarantor_cert_no` varchar(64) DEFAULT NULL COMMENT '证件号码',
  `guarantor_contact_no` varchar(64) DEFAULT NULL COMMENT '联系电话',
  `guarantor_address` varchar(256) DEFAULT NULL COMMENT '现居住地址',
  `marital_status` varchar(128) DEFAULT NULL COMMENT '婚姻情况',
  `spouse_name` varchar(64) DEFAULT NULL COMMENT '配偶姓名',
  `spouse_cert_type` varchar(50) DEFAULT NULL COMMENT '配偶证件类型',
  `spouse_cert_no` varchar(64) DEFAULT NULL COMMENT '配偶证件号码',
  `spouse_contact_no` varchar(64) DEFAULT NULL COMMENT '配偶联系电话(手机)',
  `name` varchar(50) DEFAULT NULL COMMENT '际控制人或自然人股东姓名',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `cert_no` varchar(64) DEFAULT NULL COMMENT '证件号码',
  `house_num` varchar(50) DEFAULT NULL COMMENT '房屋数量',
  `car_num` varchar(50) DEFAULT NULL COMMENT '车辆数量',
  `invest_amount` varchar(50) DEFAULT NULL COMMENT '投资金额',
  `deposit_amount` varchar(50) DEFAULT NULL COMMENT '个人存款金额',
  `marriage` varchar(128) DEFAULT NULL COMMENT '婚姻情况',
  `mate_name` varchar(50) DEFAULT NULL COMMENT '配有姓名',
  `mate_cert_type` varchar(50) DEFAULT NULL COMMENT '配偶证件类型',
  `mate_cert_no` varchar(128) DEFAULT NULL COMMENT '配偶证件号码',
  `mate_contact_no` varchar(64) DEFAULT NULL COMMENT '配偶联系电话(手机)',
  `spouse_address` varchar(256) DEFAULT NULL COMMENT '配偶家庭住址',
  `spouse_immovable_property` varchar(128) DEFAULT NULL COMMENT '配偶不动产信息',
  `spouse_movable_property` varchar(128) DEFAULT NULL COMMENT '配偶动产信息',
  `has_bank_loan` varchar(10) DEFAULT NULL COMMENT '是否有银行贷款',
  `has_folk_loan` varchar(10) DEFAULT NULL COMMENT '是否有民间贷款',
  `bank_loan_amount` bigint(20) DEFAULT NULL COMMENT '银行贷款总额',
  `folk_loan_amount` bigint(20) DEFAULT NULL COMMENT '民间贷款总额',
  `consumer_loan_bank` varchar(50) DEFAULT NULL COMMENT '消费类贷款银行',
  `consumer_loan_amount` bigint(20) DEFAULT NULL COMMENT '消费类贷款金额',
  `consumer_loan_start_date` varchar(128) DEFAULT NULL COMMENT '消费类贷款开始时间',
  `consumer_loan_end_date` varchar(128) DEFAULT NULL COMMENT '消费类贷款结束时间',
  `busines_loan_bank` varchar(50) DEFAULT NULL COMMENT '个人经营性贷款银行',
  `busines_loan_amount` bigint(20) DEFAULT NULL COMMENT '个人经营性贷款金额',
  `busines_loan_start_date` varchar(128) DEFAULT NULL COMMENT '个人经营性贷款开始时间',
  `busines_loan_end_date` varchar(128) DEFAULT NULL COMMENT '个人经营性贷款结束时间',
  `mortgage_loan_bank` varchar(50) DEFAULT NULL COMMENT '按揭贷款银行',
  `mortgage_loan_amount` bigint(20) DEFAULT NULL COMMENT '按揭贷款金额',
  `mortgage_loan_start_date` varchar(128) DEFAULT NULL COMMENT '按揭贷款开始时间',
  `mortgage_loan_end_date` varchar(128) DEFAULT NULL COMMENT '按揭贷款结束时间',
  `sort_order` int(11) DEFAULT '0',
  `raw_add_time` timestamp NULL DEFAULT NULL,
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`guarantor_id`),
  KEY `form_id` (`form_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='授信方案主要事项合理性评价 - 保证人';

##2016-11-8 新增消息发送设置
ALTER TABLE charge_repay_plan
  ADD COLUMN  before_days int(11) DEFAULT 7 COMMENT '消息发送提前天数' AFTER is_affirm,
  ADD COLUMN  cycle_days int(11) DEFAULT 7 COMMENT '每隔多少天发送' AFTER before_days;
UPDATE charge_repay_plan SET before_days=7,cycle_days = 7;

ALTER TABLE charge_repay_plan_detail
  ADD COLUMN next_notify_time DATETIME DEFAULT NULL COMMENT '下次通知时间' AFTER is_notify,
  ADD COLUMN cycle_days INT(11) DEFAULT NULL COMMENT '每隔多少天发送' AFTER next_notify_time;  
UPDATE charge_repay_plan_detail SET cycle_days = 7;
UPDATE charge_repay_plan_detail SET next_notify_time = DATE_ADD(end_time,INTERVAL -7 DAY) WHERE is_notify = 'NO';

##放用款加个字段来区分放用款明细
ALTER TABLE f_loan_use_apply_receipt
  ADD COLUMN apply_type VARCHAR(20) DEFAULT NULL COMMENT '放款/用款/放用款' AFTER apply_id;

##回执几个字段顺序变更  
ALTER TABLE f_loan_use_apply_receipt
  CHANGE COLUMN project_code project_code VARCHAR(50) DEFAULT NULL AFTER id,
  CHANGE COLUMN apply_id apply_id BIGINT(20) DEFAULT NULL COMMENT '申请ID' AFTER project_code,
  ADD COLUMN apply_type VARCHAR(20) DEFAULT NULL COMMENT '放款/用款/放用款' AFTER apply_id,
  ADD COLUMN apply_amount BIGINT(20) DEFAULT NULL COMMENT '申请金额' AFTER apply_type;
  
##放用款数据订正  
UPDATE f_loan_use_apply_receipt r, f_loan_use_apply a
SET r.apply_type = a.apply_type,
    r.apply_amount = a.amount
WHERE r.apply_id = a.apply_id

##20161109 增加客户入库时间
alter table `project_data_info` 
   add column `customer_add_time` datetime NULL COMMENT '客户入库时间' AFTER `customer_type`,
   add column `project_channel_code` varchar(64) NULL COMMENT '项目渠道编号' after `industry_name`, 
   add column `project_channel_name` varchar(500) NULL COMMENT '项目渠道名' after `project_channel_code`, 
   add column `project_sub_channel_name` varchar(500) NULL COMMENT '二级项目渠道名' after `project_channel_name`;
ALTER TABLE `project_data_info_his_data` 
   ADD COLUMN `customer_add_time` DATETIME NULL COMMENT '客户入库时间' AFTER `customer_type`,
   ADD COLUMN `project_channel_code` VARCHAR(64) NULL COMMENT '项目渠道编号' AFTER `industry_name`, 
   ADD COLUMN `project_channel_name` VARCHAR(500) NULL COMMENT '项目渠道名' AFTER `project_channel_code`, 
   ADD COLUMN `project_sub_channel_name` VARCHAR(500) NULL COMMENT '二级项目渠道名' AFTER `project_channel_name`;   
alter table `project_data_info` 
   add column `project_channel_type` varchar(64) NULL COMMENT '项目渠道类型' after `project_channel_name` ;
alter table `project_data_info_his_data` 
   add column `project_channel_type` varchar(64) NULL COMMENT '项目渠道类型' after `project_channel_name`;   
   
####################################11-11##############################################

## 20161114 heh
ALTER TABLE `f_charge_notification`
  ADD COLUMN `institution_id` BIGINT NULL  COMMENT '融资机构id' AFTER `attachment`,
  ADD COLUMN `institution_name` VARCHAR(200) NULL  COMMENT '融资机构' AFTER `institution_id`,
  ADD COLUMN `charge_name` VARCHAR(50) NULL  COMMENT '费用名称' AFTER `institution_name`,
  ADD COLUMN `charge_fee` DECIMAL(20,4) NULL  COMMENT '担保费' AFTER `charge_name`,
  ADD COLUMN `charge_type` VARCHAR(50) NULL  COMMENT '收费类型' AFTER `charge_fee`;
  
##2016-11-15 项目渠道关系表
CREATE TABLE fcs_pm.project_channel_relation (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  biz_no varchar(50) DEFAULT NULL COMMENT '外部唯一主键',
  phases varchar(50) DEFAULT NULL COMMENT '阶段',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  channel_relation varchar(50) DEFAULT NULL COMMENT '渠道关系（资金/项目渠道）',
  channel_id bigint(20) DEFAULT NULL COMMENT '渠道ID',
  channel_code varchar(50) DEFAULT NULL COMMENT '渠道编码',
  channel_type varchar(30) DEFAULT NULL COMMENT '渠道类型',
  channel_name varchar(128) DEFAULT NULL COMMENT '渠道名称',
  sub_channel_id bigint(20) DEFAULT NULL COMMENT '二级渠道ID',
  sub_channel_code varchar(50) DEFAULT NULL COMMENT '二级渠道编码',
  sub_channel_type varchar(50) DEFAULT NULL COMMENT '二级渠道类型',
  sub_channel_name varchar(128) DEFAULT NULL COMMENT '二级渠道名称',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目渠道关系表';

##########################################11-18##########################################

##放用款回执新增字段
ALTER TABLE f_loan_use_apply_receipt
ADD COLUMN capital_channel_id bigint(20) DEFAULT NULL COMMENT '资金渠道ID' AFTER busi_sub_type_name,
ADD COLUMN capital_channel_code varchar(50) DEFAULT NULL COMMENT '资金渠道编码' AFTER capital_channel_id,
ADD COLUMN capital_channel_type varchar(50) DEFAULT NULL COMMENT '渠道类型' AFTER capital_channel_code,
ADD COLUMN capital_channel_name varchar(128) DEFAULT NULL COMMENT '资金渠道名称' AFTER capital_channel_type,
ADD COLUMN capital_sub_channel_id bigint(20) DEFAULT NULL COMMENT '二级资金渠道' AFTER capital_channel_name,
ADD COLUMN capital_sub_channel_code varchar(50) DEFAULT NULL COMMENT '二级资金渠道编码' AFTER capital_sub_channel_id,
ADD COLUMN capital_sub_channel_type varchar(50) DEFAULT NULL COMMENT '二级资金渠道编码' AFTER capital_sub_channel_code,
ADD COLUMN capital_sub_channel_name varchar(128) DEFAULT NULL COMMENT '二级资金渠道名称' AFTER capital_sub_channel_type;

## 2016-11-22 放用款新增保证金字段
ALTER TABLE f_loan_use_apply
  ADD COLUMN customer_deposit_charge BIGINT(20) DEFAULT NULL COMMENT '客户保证金' AFTER remark,
  ADD COLUMN customer_deposit_refund BIGINT(20) DEFAULT NULL COMMENT '已退换客户保证金' AFTER customer_deposit_charge;

## 2016-11-21 新增字段
ALTER TABLE f_project
  ADD COLUMN belong_to_nc VARCHAR(20) DEFAULT 'NO' COMMENT '是否属于南充分公司 YES/NO' AFTER dept_path_name;
ALTER TABLE project
  ADD COLUMN belong_to_nc VARCHAR(20) DEFAULT 'NO' COMMENT '是否属于南充分公司 YES/NO' AFTER dept_path_name;

ALTER TABLE project_data_info
  ADD COLUMN belong_to_nc VARCHAR(20) DEFAULT 'NO' COMMENT '是否属于南充分公司 YES/NO' AFTER dept_path_name;
ALTER TABLE project_data_info_his_data
  ADD COLUMN belong_to_nc VARCHAR(20) DEFAULT 'NO' COMMENT '是否属于南充分公司 YES/NO' AFTER dept_path_name;  

  #20161123 heh
ALTER TABLE `charge_notice_capital_approproation`
  ADD COLUMN `left_amount` BIGINT(20) NULL  COMMENT '剩余金额' AFTER `use_amount`;
  ALTER TABLE `charge_notice_capital_approproation`
  ADD COLUMN `is_approval` VARCHAR(10) DEFAULT 'NO'  COMMENT '是否审核通过' AFTER `pay_id`;

  
##2016-11-23 自定义字段
ALTER TABLE form
  ADD COLUMN customize_field MEDIUMTEXT DEFAULT NULL COMMENT '表单自定义字段：JSON对象数据' AFTER remark;  
  
 #20161123 jil 保后汇总
ALTER TABLE afterwards_project_summary
  ADD COLUMN form_id BIGINT(20) DEFAULT NULL COMMENT '对应的表单ID' AFTER summary_id;
  
#20161123 lirz 保后 授信落实条件历史记录
CREATE TABLE `f_afterwards_credit_condition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `form_id` bigint(20) NOT NULL COMMENT '对应的表单ID',
  `item_desc` varchar(512) DEFAULT NULL COMMENT '授信条件文字描述（根据对应抵（质）押、保证生成）',
  `type` varchar(20) DEFAULT NULL COMMENT '类型：抵押、质押、保证',
  `confirm_man_name` text COMMENT '落实人名称',
  `confirm_date` datetime DEFAULT NULL COMMENT '落实日期',
  `contract_no` text COMMENT '合同编号',
  `sort_order` int(11) DEFAULT '0',  
  `raw_add_time` timestamp NULL DEFAULT NULL,
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `form_id` (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保后 - 项目授信条件';

##20161124 heh 收费
ALTER TABLE `f_charge_notification`
  CHANGE `institution_id` `institution_id` TEXT NULL  COMMENT '融资机构id',
  CHANGE `institution_name` `institution_name` TEXT CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '融资机构';

##20161124 lirz 尽调 添加form_id唯一键(防重复提交)
alter table f_investigation_risk drop index f_investigation_risk_form_id_i;
alter table f_investigation_risk add unique key `form_id_u` (`form_id`);

##20161124 heh 存入存出计提利息
ALTER TABLE `f_finance_affirm_detail`
  ADD COLUMN `accrued_interest` BIGINT NULL  COMMENT '计提利息' AFTER `period_unit`;

##20161124 lirz 保后-客户资料修改
CREATE TABLE `f_afterwards_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '调查ID',
  `form_id` bigint(20) NOT NULL COMMENT '对应的表单ID',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  `customer_name` varchar(128) DEFAULT NULL COMMENT '对应客户名称',  
  `edit_html` mediumtext COMMENT '编辑的html代码',
  `view_html` mediumtext COMMENT '查看的html代码',
  `status` varchar(128) DEFAULT NULL COMMENT '状态',
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '新增时间',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `form_id_u` (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保后检查 - 客户基本信息';

##2016-11-25 承销与发债备注 jil
ALTER TABLE project_issue_information
  ADD COLUMN remark TEXT DEFAULT NULL COMMENT '备注' AFTER is_reinsurance; 
  
## 2016-11-25 渠道报表
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `view_channel_report` AS (
SELECT DISTINCT
  `pd`.`project_id`               AS `project_id`,
  `pd`.`project_code`             AS `project_code`,
  `pd`.`project_name`             AS `project_name`,
  `pd`.`custom_name1`             AS `custom_name1`,
  `pd`.`phases_status`            AS `phases_status`,
  `pd`.`phases`                   AS `phases`,
  `pd`.`customer_name`            AS `customer_name`,
  `pd`.`enterprise_type`          AS `enterprise_type`,
  `pd`.`customer_level`           AS `customer_level`,
  `pd`.`scale`                    AS `scale`,
  `pd`.`province_code`            AS `province_code`,
  `pd`.`province_name`            AS `province_name`,
  `pd`.`city_code`                AS `city_code`,
  `pd`.`city_name`                AS `city_name`,
  `pd`.`industry_code`            AS `industry_code`,
  `pd`.`industry_name`            AS `industry_name`,
  `pd`.`busi_type`                AS `busi_type`,
  `pd`.`busi_type_name`           AS `busi_type_name`,
  `pd`.`start_time`               AS `start_time`,
  `pd`.`end_time`                 AS `end_time`,
  `pd`.`contract_amount`          AS `contract_amount`,
  `pd`.`blance`                   AS `blance`,
  `pd`.`accumulated_issue_amount` AS `accumulated_issue_amount`,
  `pd`.`loaned_amount`            AS `loaned_amount`,
  `pd`.`used_amount`              AS `used_amount`,
  `pd`.`repayed_amount`           AS `repayed_amount`,
  `pd`.`charged_amount`           AS `charged_amount`,
  `pd`.`refund_amount`            AS `refund_amount`,
  `pd`.`released_amount`          AS `released_amount`,
  `pd`.`amount`                   AS `amount`,
  `pd`.`fan_guarantee_methord`    AS `fan_guarantee_methord`,
  `pc`.`channel_id`               AS `channel_id`,
  `pc`.`channel_code`             AS `channel_code`,
  `pc`.`channel_name`             AS `channel_name`,
  `pc`.`channel_type`             AS `channel_type`,
  `pc`.`sub_channel_name`         AS `sub_channel_name`,
  `pc`.`channel_relation`         AS `channel_relation`,
  `pd`.`setup_date`               AS `setup_date`,
  `pd`.`customer_add_time`        AS `customer_add_time`,
  `pd`.`raw_update_time`          AS `raw_update_time`,
  (SELECT
     COUNT(DISTINCT `ipc`.`channel_code`)
   FROM `project_channel_relation` `ipc`
   WHERE ((`ipc`.`project_code` = `pc`.`project_code`)
          AND (`ipc`.`channel_relation` = `pc`.`channel_relation`)
          AND (((`ipc`.`channel_relation` = 'CAPITAL_CHANNEL')
                AND (`ipc`.`phases` = 'COUNCIL_PHASES'))
                OR ((`ipc`.`channel_relation` = 'PROJECT_CHANNEL')
                    AND (`ipc`.`phases` = 'INVESTIGATING_PHASES'))))) AS `num`
FROM (`project_data_info` `pd`
   LEFT JOIN `project_channel_relation` `pc`
     ON ((`pd`.`project_code` = `pc`.`project_code`)))
WHERE ((`pc`.`channel_code` IS NOT NULL)
       AND (((`pc`.`channel_relation` = 'CAPITAL_CHANNEL')
             AND (`pc`.`phases` = 'COUNCIL_PHASES'))
             OR ((`pc`.`channel_relation` = 'PROJECT_CHANNEL')
                 AND (`pc`.`phases` = 'INVESTIGATING_PHASES'))))
ORDER BY `pd`.`raw_update_time` DESC);


CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `view_channel_report_his` AS (
SELECT DISTINCT
  `pd`.`project_date`             AS `project_date`,
  `pd`.`project_id`               AS `project_id`,
  `pd`.`project_code`             AS `project_code`,
  `pd`.`project_name`             AS `project_name`,
  `pd`.`custom_name1`             AS `custom_name1`,
  `pd`.`phases_status`            AS `phases_status`,
  `pd`.`phases`                   AS `phases`,
  `pd`.`customer_name`            AS `customer_name`,
  `pd`.`enterprise_type`          AS `enterprise_type`,
  `pd`.`customer_level`           AS `customer_level`,
  `pd`.`scale`                    AS `scale`,
  `pd`.`province_code`            AS `province_code`,
  `pd`.`province_name`            AS `province_name`,
  `pd`.`city_code`                AS `city_code`,
  `pd`.`city_name`                AS `city_name`,
  `pd`.`industry_code`            AS `industry_code`,
  `pd`.`industry_name`            AS `industry_name`,
  `pd`.`busi_type`                AS `busi_type`,
  `pd`.`busi_type_name`           AS `busi_type_name`,
  `pd`.`start_time`               AS `start_time`,
  `pd`.`end_time`                 AS `end_time`,
  `pd`.`contract_amount`          AS `contract_amount`,
  `pd`.`blance`                   AS `blance`,
  `pd`.`accumulated_issue_amount` AS `accumulated_issue_amount`,
  `pd`.`loaned_amount`            AS `loaned_amount`,
  `pd`.`used_amount`              AS `used_amount`,
  `pd`.`repayed_amount`           AS `repayed_amount`,
  `pd`.`charged_amount`           AS `charged_amount`,
  `pd`.`refund_amount`            AS `refund_amount`,
  `pd`.`released_amount`          AS `released_amount`,
  `pd`.`amount`                   AS `amount`,
  `pd`.`fan_guarantee_methord`    AS `fan_guarantee_methord`,
  `pc`.`channel_id`               AS `channel_id`,
  `pc`.`channel_code`             AS `channel_code`,
  `pc`.`channel_name`             AS `channel_name`,
  `pc`.`channel_type`             AS `channel_type`,
  `pc`.`sub_channel_name`         AS `sub_channel_name`,
  `pc`.`channel_relation`         AS `channel_relation`,
  `pd`.`setup_date`               AS `setup_date`,
  `pd`.`customer_add_time`        AS `customer_add_time`,
  `pd`.`raw_update_time`          AS `raw_update_time`,
  (SELECT
     COUNT(DISTINCT `ipc`.`channel_code`)
   FROM `project_channel_relation` `ipc`
   WHERE ((`ipc`.`project_code` = `pc`.`project_code`)
          AND (`ipc`.`channel_relation` = `pc`.`channel_relation`)
          AND (((`ipc`.`channel_relation` = 'CAPITAL_CHANNEL')
                AND (`ipc`.`phases` = 'COUNCIL_PHASES'))
                OR ((`ipc`.`channel_relation` = 'PROJECT_CHANNEL')
                    AND (`ipc`.`phases` = 'INVESTIGATING_PHASES'))))) AS `num`
FROM (`project_data_info_his_data` `pd`
   LEFT JOIN `project_channel_relation` `pc`
     ON ((`pd`.`project_code` = `pc`.`project_code`)))
WHERE ((`pc`.`channel_code` IS NOT NULL)
       AND (((`pc`.`channel_relation` = 'CAPITAL_CHANNEL')
             AND (`pc`.`phases` = 'COUNCIL_PHASES'))
             OR ((`pc`.`channel_relation` = 'PROJECT_CHANNEL')
                 AND (`pc`.`phases` = 'INVESTIGATING_PHASES'))))
ORDER BY `pd`.`raw_update_time` DESC);


#20161129 lirz 保后-客户资料修改
ALTER TABLE f_afterwards_customer 
ADD COLUMN `form_data` mediumtext COMMENT '表单数据（序列化后的表单数据）' AFTER customer_name,
ADD COLUMN `user_id` bigint(20) DEFAULT NULL COMMENT '修改人ID' AFTER status,
ADD COLUMN `user_account` varchar(50) DEFAULT NULL COMMENT '修改人账号' AFTER user_id,
ADD COLUMN `user_name` varchar(50) DEFAULT NULL COMMENT '修改人名称' AFTER user_account,
ADD COLUMN `user_ip` varchar(50) DEFAULT NULL COMMENT '修改人用户IP' AFTER user_name,
ADD COLUMN `dept_id` bigint(20) DEFAULT NULL COMMENT '修改人部门ID' AFTER user_ip,
ADD COLUMN `dept_name` varchar(128) DEFAULT NULL COMMENT '修改人部门名称' AFTER dept_id,
ADD COLUMN `session_id` varchar(50) DEFAULT NULL COMMENT '修改当时的sessionId(同一次会话只记录一次)' AFTER dept_name,
ADD COLUMN `access_token` varchar(50) DEFAULT NULL COMMENT '访问密钥' AFTER session_id,
ADD COLUMN `exe_result` text AFTER access_token;

#20161129 lirz 尽调 修改主营业务字数，加到1000字
ALTER TABLE f_investigation_mainly_review 
CHANGE `living_address` `living_address` text COMMENT '住址',
CHANGE `work_address` `work_address` text COMMENT '办公地址';
ALTER TABLE f_investigation_mainly_review_stockholder CHANGE `holder_major_busi` `holder_major_busi` text COMMENT '股东主营业务';
ALTER TABLE f_investigation_mainly_review_related_company CHANGE `major_busi` `major_busi` text COMMENT '主营业务';

##################################11-29#############################################


##2016-12-5 项目收费明细视图
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `view_project_charge_detail` AS 
select
  `p`.`project_code`          AS `project_code`,
  `p`.`project_name`          AS `project_name`,
  `p`.`customer_id`           AS `customer_id`,
  `p`.`customer_name`         AS `customer_name`,
  `p`.`customer_type`         AS `customer_type`,
  `p`.`busi_type`             AS `busi_type`,
  `p`.`busi_type_name`        AS `busi_type_name`,
  `p`.`busi_manager_id`       AS `busi_manager_id`,
  `p`.`busi_manager_account`  AS `busi_manager_account`,
  `p`.`busi_manager_name`     AS `busi_manager_name`,
  `p`.`start_time`            AS `start_time`,
  `p`.`end_time`              AS `end_time`,
  (case when (`p`.`busi_type` like '4%') then ((`p`.`loaned_amount` - `p`.`released_amount`) - `p`.`comp_principal_amount`) else ((`p`.`releasable_amount` - `p`.`released_amount`) - `p`.`comp_principal_amount`) end) AS `balance`,
  `p`.`comp_principal_amount` AS `comp_principal_amount`,
  `d`.`fee_type`              AS `fee_type`,
  `d`.`pay_amount`            AS `charge_amount`,
  `d`.`pay_time`              AS `charge_time`,
  `d`.`payee_account_name`    AS `charge_account`,
  `fe`.`charge_base`          AS `charge_base`,
  `fe`.`charge_rate`          AS `charge_rate`,
  `fe`.`start_time`           AS `charge_start_time`,
  `fe`.`end_time`             AS `charge_end_time`
from (((`project` `p`
     join `f_finance_affirm` `fa`
       on (((`p`.`project_code` = `fa`.`project_code`)
            and (`fa`.`affirm_form_type` = 'CHARGE_NOTIFICATION'))))
    join `f_finance_affirm_detail` `d`
      on ((`fa`.`affirm_id` = `d`.`affirm_id`)))
   left join `f_charge_notification_fee` `fe`
     on ((`d`.`detail_id` = `fe`.`id`)))

##2016-12-5 项目划付明细视图     
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `view_project_pay_detail` AS 
select
  `p`.`project_code`          AS `project_code`,
  `p`.`project_name`          AS `project_name`,
  `p`.`customer_id`           AS `customer_id`,
  `p`.`customer_name`         AS `customer_name`,
  `p`.`customer_type`         AS `customer_type`,
  `p`.`busi_type`             AS `busi_type`,
  `p`.`busi_type_name`        AS `busi_type_name`,
  `p`.`busi_manager_id`       AS `busi_manager_id`,
  `p`.`busi_manager_account`  AS `busi_manager_account`,
  `p`.`busi_manager_name`     AS `busi_manager_name`,
  `p`.`start_time`            AS `start_time`,
  `p`.`end_time`              AS `end_time`,
  (case when (`p`.`busi_type` like '4%') then ((`p`.`loaned_amount` - `p`.`released_amount`) - `p`.`comp_principal_amount`) else ((`p`.`releasable_amount` - `p`.`released_amount`) - `p`.`comp_principal_amount`) end) AS `balance`,
  `p`.`comp_principal_amount` AS `comp_principal_amount`,
  `d`.`fee_type`              AS `fee_type`,
  `d`.`pay_amount`            AS `pay_amount`,
  `d`.`pay_time`              AS `pay_time`,
  `d`.`payee_account_name`    AS `pay_account`
from ((`project` `p`
    join `f_finance_affirm` `fa`
      on (((`p`.`project_code` = `fa`.`project_code`)
           and (`fa`.`affirm_form_type` = 'CAPITAL_APPROPROATION_APPLY'))))
   join `f_finance_affirm_detail` `d`
     on ((`fa`.`affirm_id` = `d`.`affirm_id`)))

##2016-12-5 项目还款明细视图      
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `view_project_repay_detail` AS
SELECT
  `view_project_pay_detail`.`project_code` AS `project_code`,
  `view_project_pay_detail`.`project_name` AS `project_name`,
  `view_project_pay_detail`.`customer_id` AS `customer_id`,
  `view_project_pay_detail`.`customer_name` AS `customer_name`,
  `view_project_pay_detail`.`customer_type` AS `customer_type`,
  `view_project_pay_detail`.`busi_type` AS `busi_type`,
  `view_project_pay_detail`.`busi_type_name` AS `busi_type_name`,
  `view_project_pay_detail`.`busi_manager_id` AS `busi_manager_id`,
  `view_project_pay_detail`.`busi_manager_account` AS `busi_manager_account`,
  `view_project_pay_detail`.`busi_manager_name` AS `busi_manager_name`,
  `view_project_pay_detail`.`balance` AS `balance`,
  `view_project_pay_detail`.`comp_principal_amount` AS `comp_principal_amount`,
  `view_project_pay_detail`.`start_time` AS `start_time`,
  `view_project_pay_detail`.`end_time` AS `end_time`,
  `view_project_pay_detail`.`pay_amount` AS `repay_amount`,
  DATE_FORMAT(`view_project_pay_detail`.`pay_time`, '%Y-%m-%d') AS `repay_time`,
  '代偿' AS `repay_type`
FROM `view_project_pay_detail`
WHERE (`view_project_pay_detail`.`fee_type` = 'COMPENSATORY_PRINCIPAL')
UNION ALL
SELECT
  `view_project_charge_detail`.`project_code` AS `project_code`,
  `view_project_charge_detail`.`project_name` AS `project_name`,
  `view_project_charge_detail`.`customer_id` AS `customer_id`,
  `view_project_charge_detail`.`customer_name` AS `customer_name`,
  `view_project_charge_detail`.`customer_type` AS `customer_type`,
  `view_project_charge_detail`.`busi_type` AS `busi_type`,
  `view_project_charge_detail`.`busi_type_name` AS `busi_type_name`,
  `view_project_charge_detail`.`busi_manager_id` AS `busi_manager_id`,
  `view_project_charge_detail`.`busi_manager_account` AS `busi_manager_account`,
  `view_project_charge_detail`.`busi_manager_name` AS `busi_manager_name`,
  `view_project_charge_detail`.`balance` AS `balance`,
  `view_project_charge_detail`.`comp_principal_amount` AS `comp_principal_amount`,
  `view_project_charge_detail`.`start_time` AS `start_time`,
  `view_project_charge_detail`.`end_time` AS `end_time`,
  `view_project_charge_detail`.`charge_amount` AS `repay_amount`,
  DATE_FORMAT(`view_project_charge_detail`.`charge_time`, '%Y-%m-%d') AS `repay_time`,
  (CASE WHEN (`view_project_charge_detail`.`fee_type` = 'ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL') THEN '委贷本金' ELSE '委贷利息'
  END) AS `repay_type`
FROM `view_project_charge_detail`
WHERE (`view_project_charge_detail`.`fee_type` IN ('ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL', 'ENTRUSTED_LOAN_INTEREST_WITHDRAWAL'))
UNION ALL
SELECT
  `p`.`project_code` AS `project_code`,
  `p`.`project_name` AS `project_name`,
  `p`.`customer_id` AS `customer_id`,
  `p`.`customer_name` AS `customer_name`,
  `p`.`customer_type` AS `customer_type`,
  `p`.`busi_type` AS `busi_type`,
  `p`.`busi_type_name` AS `busi_type_name`,
  `p`.`busi_manager_id` AS `busi_manager_id`,
  `p`.`busi_manager_account` AS `busi_manager_account`,
  `p`.`busi_manager_name` AS `busi_manager_name`,
  (CASE WHEN (`p`.`busi_type` LIKE '4%') THEN ((`p`.`loaned_amount` - `p`.`released_amount`) - `p`.`comp_principal_amount`) ELSE ((`p`.`releasable_amount` - `p`.`released_amount`) - `p`.`comp_principal_amount`)
  END) AS `balance`,
  `p`.`comp_principal_amount` AS `comp_principal_amount`,
  `p`.`start_time` AS `start_time`,
  `p`.`end_time` AS `end_time`,
  `r`.`repay_amount` AS `repay_amount`,
  `r`.`repay_time` AS `repay_time`,
  '解保' AS `repay_type`
FROM (((`project` `p`
  JOIN `f_counter_guarantee_release` `g`
    ON ((`g`.`project_code` = `p`.`project_code`)))
  JOIN `f_counter_guarantee_repay` `r`
    ON ((`r`.`form_id` = `g`.`form_id`)))
  JOIN `form` `f`
    ON (((`r`.`form_id` = `f`.`form_id`)
    AND (`f`.`status` = 'APPROVAL'))))

#20161207 lirz 尽调
ALTER TABLE f_investigation_mainly_review 
ADD COLUMN `subsidiary_remark` mediumtext COMMENT '客户下属公司、全资和控股子公司情况(备注)' AFTER customer_dev_evolution,
ADD COLUMN `participation_remark` mediumtext COMMENT '客户主要参股公司情况(备注)' AFTER subsidiary_remark,
ADD COLUMN `correlation_remark` mediumtext COMMENT '客户其它关联公司情况(备注)' AFTER participation_remark;

ALTER TABLE f_investigation_mainly_review_asset_and_liability 
ADD COLUMN `remark` mediumtext COMMENT '备注' AFTER mortgage_loan_end_date;


CREATE TABLE fcs_pm.sys_config (
  config_id int(11) NOT NULL AUTO_INCREMENT,
  faq mediumtext DEFAULT NULL COMMENT 'faq',
  manual mediumtext DEFAULT NULL COMMENT '操作手册',
  video mediumtext DEFAULT NULL COMMENT '视频',
  background_image varchar(200) DEFAULT NULL COMMENT '登录页背景图',
  info1 text DEFAULT NULL COMMENT '预留',
  info2 text DEFAULT NULL COMMENT '预留',
  info3 text DEFAULT NULL COMMENT '预留',
  info4 text DEFAULT NULL COMMENT '预留',
  info5 text DEFAULT NULL COMMENT '预留',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (config_id)
)
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '系统配置';

##################2016-12-8 ###################################

ALTER TABLE f_council_summary_project ADD COLUMN belong_to_nc varchar(20) COMMENT '是否属于南川分公司  YES/NO' AFTER other;

##jil 20161209
alter table user_extra_message 
   add column cancel_alert varchar(10) DEFAULT 'NO' NULL COMMENT '是否取消弹框' after bank_account_no;
   
##2016-12-13 统一一下字段长度   
ALTER TABLE f_council_summary_project_bond
  CHANGE COLUMN interest_rate interest_rate DECIMAL(20, 4) DEFAULT NULL COMMENT '利率',
  CHANGE COLUMN guarantee_fee guarantee_fee DECIMAL(20, 4) DEFAULT NULL COMMENT '担保费',
  CHANGE COLUMN total_cost total_cost DECIMAL(20, 4) DEFAULT NULL COMMENT '总成本',
  CHANGE COLUMN other_fee other_fee DECIMAL(20, 4) DEFAULT NULL COMMENT '其他费用';
  
ALTER TABLE f_council_summary_project_entrusted
  CHANGE COLUMN interest_rate interest_rate DECIMAL(20, 4) DEFAULT NULL COMMENT '利率',
  CHANGE COLUMN other_fee other_fee DECIMAL(20, 4) DEFAULT NULL COMMENT '其他费用';
  
ALTER TABLE f_council_summary_project_guarantee
  CHANGE COLUMN interest_rate interest_rate DECIMAL(20, 4) DEFAULT NULL COMMENT '利率',
  CHANGE COLUMN guarantee_fee guarantee_fee DECIMAL(20, 4) DEFAULT NULL COMMENT '担保费',
  CHANGE COLUMN total_cost total_cost DECIMAL(20, 4) DEFAULT NULL COMMENT '总成本',
  CHANGE COLUMN other_fee other_fee DECIMAL(20, 4) DEFAULT NULL COMMENT '其他费用';  

ALTER TABLE f_council_summary_project_lg_litigation
  CHANGE COLUMN co_institution_charge co_institution_charge DECIMAL(20, 4) DEFAULT NULL COMMENT '合作机构服务费 元/%',
  CHANGE COLUMN guarantee_fee guarantee_fee DECIMAL(20, 4) DEFAULT NULL COMMENT '担保费',
  CHANGE COLUMN deposit deposit DECIMAL(20, 4) DEFAULT NULL COMMENT '保证金',
  CHANGE COLUMN other_fee other_fee DECIMAL(20, 4) DEFAULT NULL COMMENT '其他费用';
  
ALTER TABLE f_council_summary_project_underwriting
  CHANGE COLUMN release_rate release_rate DECIMAL(20, 4) DEFAULT NULL COMMENT '发行利率',
  CHANGE COLUMN bourse_fee bourse_fee DECIMAL(20, 4) DEFAULT NULL COMMENT '交易所费',
  CHANGE COLUMN law_firm_fee law_firm_fee DECIMAL(20, 4) DEFAULT NULL COMMENT '律所费',
  CHANGE COLUMN club_fee club_fee DECIMAL(20, 4) DEFAULT NULL COMMENT '会所费',
  CHANGE COLUMN underwriting_fee underwriting_fee DECIMAL(20, 4) DEFAULT NULL COMMENT '承销费',
  CHANGE COLUMN other_fee other_fee DECIMAL(20, 4) DEFAULT NULL COMMENT '其他费用';  
  
##lirz 20161214 尽调
ALTER TABLE f_investigation_credit_scheme CHANGE `repay_way` `repay_way` text COMMENT '还款方式';


##lirz 20161214 项目风险评级
ALTER TABLE f_risk_level 
ADD COLUMN check_level1 varchar(50) DEFAULT NULL COMMENT '评级1' AFTER project_type,
ADD COLUMN check_level2 varchar(50) DEFAULT NULL COMMENT '评级2' AFTER check_level1,
ADD COLUMN check_level3 varchar(50) DEFAULT NULL COMMENT '评级3' AFTER check_level2,
ADD COLUMN check_level varchar(50) DEFAULT NULL COMMENT '最终评级' AFTER check_level3;

##订正数据
update f_risk_level set check_level=evaluation_level where evaluation_level is not null and evaluation_level!='';
update f_risk_level set check_level=reevaluation_level where reevaluation_level is not null and reevaluation_level!='';

########################2016-12-16###############################################

## heh 20161221 资金渠道合同金额
ALTER TABLE `project_channel_relation`
  ADD COLUMN `contract_amount` BIGINT(20) DEFAULT 0  COMMENT '合同金额' AFTER `sub_channel_name`;


##lirz 20161221 解保 (pm)
ALTER TABLE guarantee_apply_counter 
ADD COLUMN release_status varchar(64) DEFAULT NULL COMMENT '解除状态 部分/全部解除' AFTER release_gist,
ADD COLUMN release_remark text COMMENT '备注' AFTER release_status;


##渠道相关表新增相关金额
ALTER TABLE project_channel_relation
  ADD COLUMN busi_type VARCHAR(20) DEFAULT NULL COMMENT '项目的业务类型' AFTER project_code;
  ADD COLUMN busi_type_name VARCHAR(50) DEFAULT NULL COMMENT '项目业务类型名称' AFTER busi_type;
  ADD COLUMN loaned_amount BIGINT(20) DEFAULT 0 COMMENT '已放款金额' AFTER contract_amount,
  ADD COLUMN used_amount BIGINT(20) DEFAULT 0 COMMENT '已用款金额' AFTER loaned_amount,
  ADD COLUMN comp_amount BIGINT(20) DEFAULT 0 COMMENT '已代偿本金' AFTER used_amount,
  ADD COLUMN releasable_amount BIGINT(20) DEFAULT 0 COMMENT '可解保总金额' AFTER comp_amount,
  ADD COLUMN released_amount BIGINT(20) DEFAULT 0 COMMENT '已解保金额' AFTER releasable_amount,
  ADD COLUMN repayed_amount BIGINT(20) DEFAULT 0 COMMENT '还款金额' AFTER released_amount;
  ADD COLUMN latest VARCHAR(20) DEFAULT 'NO' COMMENT '是否最新 YES/NO' AFTER repayed_amount;
  
  UPDATE project_channel_relation
SET latest = 'NO';

UPDATE project_channel_relation d, (SELECT
    *
  FROM (SELECT
      MAX(biz_no) biz_no,
      project_code
    FROM project_channel_relation
    WHERE project_code IS NOT NULL
    AND project_code != ''
    AND channel_relation = 'PROJECT_CHANNEL'
    GROUP BY project_code) t) w
SET d.latest = 'YES'
WHERE w.biz_no = d.biz_no
AND channel_relation = 'PROJECT_CHANNEL';

UPDATE project_channel_relation d, (SELECT
    *
  FROM (SELECT
      MAX(biz_no) biz_no,
      project_code
    FROM project_channel_relation
    WHERE project_code IS NOT NULL
    AND project_code != ''
    AND channel_relation = 'CAPITAL_CHANNEL'
    GROUP BY project_code) t) w
SET d.latest = 'YES'
WHERE w.biz_no = d.biz_no
AND channel_relation = 'CAPITAL_CHANNEL';

UPDATE project_channel_relation r
SET r.contract_amount = 0,
    r.loaned_amount = 0,
    r.used_amount = 0,
    r.releasable_amount = 0,
    r.comp_amount = 0,
    r.released_amount = 0,
    r.repayed_amount = 0;

UPDATE project_channel_relation r, project p
SET r.contract_amount = p.contract_amount,
    r.loaned_amount = p.loaned_amount,
    r.used_amount = p.used_amount,
    r.releasable_amount = p.releasable_amount,
    r.released_amount = p.released_amount,
    r.comp_amount = p.comp_principal_amount,
    r.repayed_amount = p.repayed_amount
WHERE r.project_code = p.project_code
AND r.phases = 'COUNCIL_PHASES'
AND r.channel_relation = 'CAPITAL_CHANNEL'
AND p.is_approval = 'IS';

ALTER TABLE f_counter_guarantee_repay
ADD COLUMN capital_channel_id bigint(20) DEFAULT NULL COMMENT '资金渠道ID' AFTER repay_time,
ADD COLUMN capital_channel_code varchar(50) DEFAULT NULL COMMENT '资金渠道编码' AFTER capital_channel_id,
ADD COLUMN capital_channel_type varchar(50) DEFAULT NULL COMMENT '渠道类型' AFTER capital_channel_code,
ADD COLUMN capital_channel_name varchar(128) DEFAULT NULL COMMENT '资金渠道名称' AFTER capital_channel_type,
ADD COLUMN capital_sub_channel_id bigint(20) DEFAULT NULL COMMENT '二级资金渠道' AFTER capital_channel_name,
ADD COLUMN capital_sub_channel_code varchar(50) DEFAULT NULL COMMENT '二级资金渠道编码' AFTER capital_sub_channel_id,
ADD COLUMN capital_sub_channel_type varchar(50) DEFAULT NULL COMMENT '渠道类型' AFTER capital_sub_channel_code,
ADD COLUMN capital_sub_channel_name varchar(128) DEFAULT NULL COMMENT '二级资金渠道名称' AFTER capital_sub_channel_type,
ADD COLUMN actual_deposit_amount bigint(20) DEFAULT NULL COMMENT '实际保证金' AFTER capital_sub_channel_name,
ADD COLUMN liquidity_loan_amount bigint(20) DEFAULT NULL COMMENT '流动资金贷款' AFTER actual_deposit_amount,
ADD COLUMN fixed_assets_financing_amount bigint(20) DEFAULT NULL COMMENT '固定资产融资' AFTER liquidity_loan_amount,
ADD COLUMN acceptance_bill_amount bigint(20) DEFAULT NULL COMMENT '承兑汇票担保' AFTER fixed_assets_financing_amount,
ADD COLUMN credit_letter_amount bigint(20) DEFAULT NULL COMMENT '信用证担保' AFTER acceptance_bill_amount;

  ##lirz 20161222 解保 数据订正(pm)
UPDATE f_counter_guarantee_repay as t_repay
INNER JOIN (
SELECT t_r.id,c.channel_id,c.channel_code,c.channel_name,c.channel_type,c.sub_channel_id,c.sub_channel_code,c.sub_channel_name,c.sub_channel_type FROM f_counter_guarantee_repay t_r 
LEFT JOIN f_counter_guarantee_release t_g ON t_r.form_id=t_g.form_id 
INNER JOIN project_channel_relation c ON t_g.project_code=c.project_code AND c.channel_relation='CAPITAL_CHANNEL' AND c.latest='YES' 
WHERE t_r.capital_channel_code IS NULL
) AS t_t ON t_repay.id=t_t.id 
SET t_repay.capital_channel_id=t_t.channel_id,t_repay.capital_channel_code=t_t.channel_code,t_repay.capital_channel_name=t_t.channel_name,t_repay.capital_channel_type=t_t.channel_type,t_repay.capital_sub_channel_id=t_t.sub_channel_id,t_repay.capital_sub_channel_code=t_t.sub_channel_code,t_repay.capital_sub_channel_name=t_t.sub_channel_name,t_repay.capital_sub_channel_type=t_t.sub_channel_type ;

##数据订正 (pm)
insert into guarantee_apply_counter (project_code,form_id,item_desc,release_id,release_reason,release_gist,release_status,raw_add_time) 
select fc.project_code,fc.form_id,pcc.item_desc,pcc.id,pcc.release_reason,pcc.release_gist,pcc.release_status,now() from f_counter_guarantee_release fc left join project_credit_condition pcc on fc.id=pcc.release_id where pcc.release_status='RELEASED';


ALTER TABLE f_council_summary_project
  CHANGE COLUMN charge_remark charge_remark MEDIUMTEXT DEFAULT NULL COMMENT '收费方式备注';
  
#################2016-12-23##########################  
## 项目渠道关系表新增字段 2016-12-24
alter table `project_channel_relation` 
   add column `in_amount` bigint(20) NULL COMMENT '在保余额' after `repayed_amount`, 
   add column `new_customer` double(14,2) DEFAULT '0' NULL COMMENT '新增客户数' after `in_amount`, 
   add column `new_project` double(14,2) DEFAULT '0' NULL COMMENT '新增项目数' after `new_customer`, 
   add column `in_cutomer` double(14,2) DEFAULT '0' NULL COMMENT '在保客户数' after `new_project`, 
   add column `in_project` double(14,2) DEFAULT '0' NULL COMMENT '在保项目' after `in_cutomer`,
   change `latest` `latest` varchar(20) character set utf8 collate utf8_general_ci default 'NO' NULL  comment '是否最新 YES/NO';

   ##20161227 heh收款通知单增加自付他付付款时间
   ALTER TABLE `fcs_pm`.`f_charge_notification`
  ADD COLUMN `another_pay_time` DATETIME NULL  COMMENT '他付付款时间' AFTER `another_pay_bank`;


##lirz 20161228 尽调-第8个页签 添加富文本框
ALTER TABLE f_investigation_cs_rationality_review_guarantor ADD COLUMN event_desc mediumtext COMMENT '重大事项说明' AFTER leader_review;

##heh 20161228合同回传备注框 sql已发
ALTER TABLE `fcs_pm`.`f_project_contract_item`
  ADD COLUMN `return_remark` TEXT NULL  COMMENT '回传备注' AFTER `refer_attachment`;
  
##jil 20161228 增加授信落实表的长度
ALTER TABLE f_credit_condition_confirm CHANGE column institution_name varchar(1024) COMMENT '融资机构名称';

##heh 20161229 银团类合同回传细分每个业务类型
ALTER TABLE `fcs_pm`.`project_channel_relation`
  ADD COLUMN `liquidity_loans_amount` BIGINT(20) NULL  COMMENT '流动资金贷款(合同金额)' AFTER `contract_amount`,
  ADD COLUMN `financial_amount` BIGINT(20) NULL  COMMENT '固定资产融资(合同金额)' AFTER `liquidity_loans_amount`,
  ADD COLUMN `acceptance_bill_amount` BIGINT NULL  COMMENT '承兑汇票担保(合同金额)' AFTER `financial_amount`,
  ADD COLUMN `credit_amount` BIGINT(20) NULL  COMMENT '信用证担保(合同金额)' AFTER `acceptance_bill_amount`;
  
ALTER TABLE f_afterwards_check_base 
modify COLUMN `collect_data` MEDIUMTEXT COMMENT '资料收集' AFTER collect_month;  


ALTER TABLE project_credit_margin
  CHANGE COLUMN credit_id credit_id VARCHAR(5000) DEFAULT NULL COMMENT '授信落实实体表ID';

##数据订正 发售信息维护到期日大于项目到期日的 项目到期日更新
UPDATE project p , project_issue_information i SET p.end_time = i.expire_time WHERE p.end_time < i.expire_time;  

########################################2016-12-30################################################

ALTER TABLE project_issue_information
  CHANGE COLUMN letter_url letter_url TEXT DEFAULT NULL COMMENT '担保函地址',
  CHANGE COLUMN voucher_url voucher_url TEXT DEFAULT NULL COMMENT '付款凭证地址';

###############################2017-1-5############################# 

##lirz 20170104 解保申请 显示在保余额
ALTER TABLE f_counter_guarantee_release ADD COLUMN release_balance bigint(20) DEFAULT NULL COMMENT '在保余额' AFTER apply_amount;

##理财产品立项-收益率区间
ALTER TABLE f_project_financial
  ADD COLUMN rate_range_start DECIMAL(12, 4) DEFAULT NULL COMMENT '年化收益率区间-开始' AFTER interest_rate,
  ADD COLUMN rate_range_end DECIMAL(12, 4) DEFAULT NULL COMMENT '年化收益率区间-结束' AFTER rate_range_start;

##理财产品-收益率区间
ALTER TABLE financial_product
  ADD COLUMN rate_range_start DECIMAL(12, 4) DEFAULT NULL COMMENT '年化收益率区间-开始' AFTER interest_rate,
  ADD COLUMN rate_range_end DECIMAL(12, 4) DEFAULT NULL COMMENT '年化收益率区间-结束' AFTER rate_range_start;

##lirz 20170105 尽调 抵质押资产信息添加关键信息
ALTER TABLE f_investigation_credit_scheme_pledge_asset ADD asset_remark text COMMENT '关键信息' AFTER debted_amount;


ALTER TABLE project_data_info
  CHANGE COLUMN fan_guarantee_methord fan_guarantee_methord TEXT DEFAULT NULL COMMENT '反担保措施';

ALTER TABLE project_data_info_his_data
  CHANGE COLUMN fan_guarantee_methord fan_guarantee_methord TEXT DEFAULT NULL COMMENT '反担保措施';
  
#########################################2017-1-6######################################

##jil 20170109 退费决策依据增加合同选项
ALTER TABLE f_refund_application_fee ADD contract varchar(1000) COMMENT '决策依据(合同)' AFTER project_approval;

##heh 20170109 文书函增加决策依据已立项
ALTER TABLE `fcs_pm`.`f_project_contract_item`
  ADD COLUMN `project_set_up` VARCHAR(10) NULL  COMMENT '决策依据(已立项)' AFTER `project_approval`;

##lirz 20170109 保后
ALTER TABLE f_afterwards_check_report_financial ADD COLUMN `item_balance` bigint(20) DEFAULT '0' COMMENT '科目余额（元）' AFTER origial_amount;

ALTER TABLE f_afterwards_check_report_content
CHANGE `use_way_conditions` use_way_conditions mediumtext COMMENT '授信的用途、还息及纳税检查(一)',
CHANGE `project_finish_desc` project_finish_desc mediumtext COMMENT '开发项目完成情况检查|银行贷款及其他融资(二)',
CHANGE `income_check_desc` income_check_desc mediumtext COMMENT '企业收入调查工作底稿|企业成本核实情况工作底稿(二)',
CHANGE `management_matters` management_matters mediumtext COMMENT '重大经营管理事项检查|业务流程(三)',
CHANGE `decision_way` decision_way mediumtext COMMENT '审贷会的组成及决议方式(三)',
CHANGE `counter_check` counter_check mediumtext COMMENT '担保措施检查(四)',
CHANGE `related_enterprise` related_enterprise mediumtext COMMENT '关联企业检查情况(五)',
CHANGE `other_explain` other_explain mediumtext COMMENT '其他需说明的事项(八)',
CHANGE `analysis_conclusion` analysis_conclusion mediumtext COMMENT '监管内容分析与结论(九)';

ALTER TABLE f_afterwards_check_report_content 
ADD COLUMN content_remark1 mediumtext COMMENT '备注1' AFTER analysis_conclusion,
ADD COLUMN content_remark2 mediumtext COMMENT '备注2' AFTER content_remark1;

ALTER TABLE f_afterwards_check_base 
ADD COLUMN remark1 mediumtext COMMENT '备注1' AFTER customer_opinion,
ADD COLUMN remark2 mediumtext COMMENT '备注2' AFTER remark1;

CREATE TABLE `f_afterwards_check_report_credit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `form_id` bigint(20) NOT NULL COMMENT '表单ID',
  `loan_institution` varchar(128) DEFAULT NULL COMMENT '融资机构',
  `loan_balance` varchar(50) DEFAULT NULL COMMENT '融资余额',
  `loan_start_date` varchar(128) DEFAULT NULL COMMENT '融资期限起',
  `loan_end_date` varchar(128) DEFAULT NULL COMMENT '融资期限止',
  `loan_cost` varchar(50) DEFAULT NULL COMMENT '融资成本',
  `guarantee_pledge` varchar(512) DEFAULT NULL COMMENT '担保方式及扣保物',
  `credit_remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `sort_order` int(11) DEFAULT '0',
  `raw_add_time` timestamp NULL DEFAULT NULL,
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `form_id` (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保后检查-监管内容-信用评价';

##lirz 20170111 授信条件中解保的状态字段过短
ALTER TABLE project_credit_condition CHANGE `release_status` release_status varchar(64) DEFAULT 'WAITING' COMMENT '解保申请状态(默认待解保)';  

##zhb 20170111 银团细分类合同金额
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `view_bank_financing_type` AS 
SELECT `p`.`project_code` AS `project_code`, SUM(`c`.`liquidity_loans_amount`) AS `fke`, '流动资金贷款' AS `type_name`, 't1' AS `TYPE` FROM (`project_channel_relation` `c` JOIN `project` `p` ON ((`c`.`project_code` = `p`.`project_code`))) WHERE ((`c`.`latest` = 'YES') AND (`c`.`channel_relation` = 'CAPITAL_CHANNEL') AND (`c`.`liquidity_loans_amount` > 0) AND (`p`.`busi_type` = '11')) GROUP BY `p`.`project_code` 
UNION 
SELECT `p`.`project_code` AS `project_code`, SUM(`c`.`financial_amount`) AS `fke`, '固定资产融资' AS `type_name`, 't2' AS `TYPE` FROM (`project_channel_relation` `c` JOIN `project` `p` ON ((`c`.`project_code` = `p`.`project_code`))) WHERE ((`c`.`latest` = 'YES') AND (`c`.`channel_relation` = 'CAPITAL_CHANNEL') AND (`c`.`financial_amount` > 0) AND (`p`.`busi_type` = '11')) GROUP BY `p`.`project_code` 
UNION 
SELECT `p`.`project_code` AS `project_code`, SUM(`c`.`acceptance_bill_amount`) AS `fke`, '承兑汇票担保' AS `type_name`, 't3' AS `TYPE` FROM (`project_channel_relation` `c` JOIN `project` `p` ON ((`c`.`project_code` = `p`.`project_code`))) WHERE ((`c`.`latest` = 'YES') AND (`c`.`channel_relation` = 'CAPITAL_CHANNEL') AND (`c`.`acceptance_bill_amount` > 0) AND (`p`.`busi_type` = '11')) GROUP BY `p`.`project_code` 
UNION 
SELECT `p`.`project_code` AS `project_code`, SUM(`c`.`credit_amount`) AS `fke`, '承兑汇票担保' AS `type_name`, 't4' AS `TYPE` FROM (`project_channel_relation` `c` JOIN `project` `p` ON ((`c`.`project_code` = `p`.`project_code`))) WHERE ((`c`.`latest` = 'YES') AND (`c`.`channel_relation` = 'CAPITAL_CHANNEL') AND (`c`.`credit_amount` > 0) AND (`p`.`busi_type` = '11')) GROUP BY `p`.`project_code`;

ALTER TABLE f_afterwards_check_report_content 
ADD COLUMN `is_collected_data` varchar(128) DEFAULT 'NO' COMMENT '收集到该部分资料:YES/NO' AFTER use_way_conditions,
ADD COLUMN `collected_data_desc` mediumtext COMMENT '未收集到该部分资料的说明' AFTER is_collected_data;

##################################################17-1-13#####################################################=======

##lirz 20170116 风险处置上会申报
ALTER TABLE f_council_apply_credit_compensation 
CHANGE `compensatory_principal` compensatory_principal varchar(512) DEFAULT NULL COMMENT '代偿本金',
CHANGE `compensatory_interest_other` compensatory_interest_other varchar(512) DEFAULT NULL COMMENT '代偿利息、罚息及其他',
ADD `compensatory_interest` varchar(512) DEFAULT NULL COMMENT '代偿利息' AFTER compensatory_principal,
ADD `other_remark` varchar(512) DEFAULT NULL COMMENT '其它' AFTER compensatory_interest_other;

##jil 20170117 资金划付  代偿项目业务种类为银行融资担保时，选择了代偿相关的费用种类 增加渠道
CREATE TABLE fcs_pm.f_capital_appropriation_apply_fee_compensatory_channel (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '表单ID',
  fee_id bigint(20) NOT NULL COMMENT '明细ID',
  capital_channel_id bigint(20) DEFAULT NULL COMMENT '资金渠道ID',
  capital_channel_code varchar(50) DEFAULT NULL COMMENT '资金渠道编码',
  capital_channel_type varchar(50) DEFAULT NULL COMMENT '渠道类型',
  capital_channel_name varchar(128) DEFAULT NULL COMMENT '资金渠道名称',
  capital_sub_channel_id bigint(20) DEFAULT NULL COMMENT '二级资金渠道',
  capital_sub_channel_code varchar(50) DEFAULT NULL COMMENT '二级资金渠道编码',
  capital_sub_channel_type varchar(50) DEFAULT NULL COMMENT '渠道类型',
  capital_sub_channel_name varchar(128) DEFAULT NULL COMMENT '二级资金渠道名称',
  actual_deposit_amount bigint(20) DEFAULT NULL COMMENT '实际保证金',
  liquidity_loan_amount bigint(20) DEFAULT NULL COMMENT '流动资金贷款',
  fixed_assets_financing_amount bigint(20) DEFAULT NULL COMMENT '固定资产融资',
  acceptance_bill_amount bigint(20) DEFAULT NULL COMMENT '承兑汇票担保',
  credit_letter_amount bigint(20) DEFAULT NULL COMMENT '信用证担保',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_capital_appropriation_apply_fee_compensatory_channel_form_id_i (form_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1234
AVG_ROW_LENGTH = 150
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '资金划付申请-资金划付明细-代偿渠道';

##放用款添加已发售金额
ALTER TABLE f_loan_use_apply
  ADD COLUMN issue_amount BIGINT(20) DEFAULT NULL COMMENT '已发售金额' AFTER used_amount;
  
##订正历史发售金额情况  
UPDATE f_loan_use_apply a, project p SET a.issue_amount = p.loaned_amount WHERE a.project_code = p.project_code AND p.busi_type LIKE '12%';

## heh 征信管理改造 20170119
ALTER TABLE `fcs_pm`.`f_credit_refrerence_apply`
  ADD COLUMN `customer_id` BIGINT(20) NULL  COMMENT '客户id' AFTER `form_id`,
  ADD COLUMN `is_company` VARCHAR(10) NULL  COMMENT '是否公司客户(IS/NO)' AFTER `customer_id`,
  ADD COLUMN `purpose` VARCHAR(20) NULL  COMMENT '用途' AFTER `is_company`;

  
##保后字段加长  
ALTER TABLE f_afterwards_check_report_financial
  CHANGE COLUMN remark remark TEXT DEFAULT NULL COMMENT '现场查看说明/分析';
  
  ##短信发送 jil 20170120
  CREATE TABLE fcs_pm.short_message (
	  id bigint(18) NOT NULL AUTO_INCREMENT,
	  message_sender_id bigint(20) DEFAULT NULL COMMENT '短信发送人id',
	  message_sender_name varchar(128) DEFAULT NULL COMMENT '短信发送人',
	  message_sender_account varchar(128) DEFAULT NULL COMMENT '短信发送人账号',
	  message_receiver varchar(5000) DEFAULT NULL COMMENT '短信接收人电话号码(多个)',
	  message_content longtext DEFAULT NULL COMMENT '消息内容',
	  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
	  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	  PRIMARY KEY (id),
	  INDEX short_message_id_index (id)
	)
ENGINE = INNODB
AUTO_INCREMENT = 201156
AVG_ROW_LENGTH = 503
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '简单短信发送';

############################################################

##放用款回执渠道数据订正
UPDATE fcs_pm.f_loan_use_apply_receipt r, fcs_crm.channal_info c
SET r.capital_channel_type = c.channel_type
WHERE r.capital_channel_code = c.channel_code;

UPDATE fcs_pm.f_loan_use_apply_receipt r, fcs_pm.project_channel_relation c
SET r.capital_channel_id = c.channel_id,
    r.capital_channel_type = c.channel_type,
    r.capital_channel_code = c.channel_code,
    r.capital_channel_name = c.channel_name,
    r.capital_sub_channel_id = c.sub_channel_id,
    r.capital_sub_channel_type = c.sub_channel_type,
    r.capital_sub_channel_code = c.sub_channel_code,
    r.capital_sub_channel_name = c.sub_channel_name
WHERE r.project_code = c.project_code
AND r.capital_channel_code IS NULL
AND c.channel_relation = 'CAPITAL_CHANNEL'
AND c.latest = 'YES';
  
## 20170120 系统参数分类
UPDATE sys_param SET extend_attribute_one = 'DEFAULT' WHERE extend_attribute_one IS NULL;
UPDATE sys_param SET extend_attribute_one = 'ACCOUNT_SYSTEM_PARAM' WHERE param_name = 'KINGDEE_API_PARTNER_ID';
UPDATE sys_param SET extend_attribute_one = 'ACCOUNT_SYSTEM_PARAM' WHERE param_name = 'KINGDEE_API_PARTNER_KEY';
UPDATE sys_param SET extend_attribute_one = 'ACCOUNT_SYSTEM_PARAM' WHERE param_name = 'KINGDEE_API_SYSTEM_URL';
UPDATE sys_param SET extend_attribute_one = 'ACCOUNT_SYSTEM_PARAM' WHERE param_name = 'KINGDEE_PARTNER_ID';
UPDATE sys_param SET extend_attribute_one = 'ACCOUNT_SYSTEM_PARAM' WHERE param_name = 'KINGDEE_PARTNER_KEY';
UPDATE sys_param SET extend_attribute_one = 'RISK_SYSTEM' WHERE param_name = 'RISK_PARTNER_ID';
UPDATE sys_param SET extend_attribute_one = 'RISK_SYSTEM' WHERE param_name = 'RISK_PARTNER_KEY';
UPDATE sys_param SET extend_attribute_one = 'RISK_SYSTEM' WHERE param_name = 'RISK_SYSTEM_SWITCH';
UPDATE sys_param SET extend_attribute_one = 'RISK_SYSTEM' WHERE param_name = 'RISK_API_SYSTEM_URL';
UPDATE sys_param SET extend_attribute_one = 'RISK_SYSTEM' WHERE param_name = 'RISK_API_PARTNER_ID';
UPDATE sys_param SET extend_attribute_one = 'RISK_SYSTEM' WHERE param_name = 'RISK_API_PARTNER_KEY';


##lirz 20170122 保后
ALTER TABLE f_afterwards_check_report_content 
ADD COLUMN content_remark3 mediumtext COMMENT '备注3' AFTER content_remark2;

## heh 20170206 印章配置
CREATE TABLE `fcs_pm`.`stamp_configure`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `org_id` VARCHAR(50) COMMENT '组织机构id',
  `org_code` VARCHAR(50) COMMENT '组织机构编码',
  `org_name` VARCHAR(100) COMMENT '组织机构名称',
  `role_code` VARCHAR(50) COMMENT '角色代码',
  `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '新增时间',
  `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `org_id_unique` (`org_id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci
COMMENT='印章配置';
ALTER TABLE `fcs_pm`.`stamp_configure`
  ADD COLUMN `gz_role_code` VARCHAR(50) NULL  COMMENT '公章角色代码' AFTER `org_name`,
  CHANGE `role_code` `frz_role_code` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '法人章角色代码';
  ALTER TABLE `fcs_pm`.`f_stamp_apply`
  ADD COLUMN `org_ids` TEXT NULL  COMMENT '组织机构ids' AFTER `status`,
  ADD COLUMN `org_names` TEXT NULL  COMMENT '组织机构名称' AFTER `org_ids`;
  ALTER TABLE `fcs_pm`.`stamp_configure`
  DROP INDEX `org_id_unique`,
  ADD  UNIQUE INDEX `org_name_unique` (`org_name`);

  ## heh 20170207档案入库申请单增加备注框
  ALTER TABLE `fcs_pm`.`f_file`
  ADD COLUMN `remark` TEXT NULL  COMMENT '备注' AFTER `update_time`;

###########################################2017-02-28#################################################

##资产关键信息存储
ALTER TABLE fcs_pm.f_council_summary_project_pledge_asset
ADD COLUMN asset_remark text DEFAULT NULL COMMENT '关键信息' AFTER debted_amount,
ADD COLUMN remark text DEFAULT NULL COMMENT '复评意见' AFTER asset_remark;

##订正没有关键信息的数据
UPDATE fcs_pm.f_council_summary_project_pledge_asset a, fcs_am.pledge_asset p
SET a.asset_remark = p.asset_remark_info
WHERE a.assets_id = p.assets_id
AND a.assets_id > 0;

UPDATE fcs_pm.project_credit_condition c, fcs_am.pledge_asset p
SET c.remark = p.asset_remark_info,
    c.item_desc = CONCAT("关键信息：", p.asset_remark_info, ";", c.item_desc)
WHERE c.asset_id = p.assets_id
AND c.asset_id > 0
AND c.remark IS NULL
AND p.asset_remark_info IS NOT NULL
AND p.asset_remark_info != '';

UPDATE fcs_pm.project_credit_condition c, fcs_pm.project p, fcs_pm.f_council_summary_project_pledge_asset a
SET c.item_desc = REPLACE(c.item_desc,'是否后置抵押：否', '是否后置抵押：是；')
WHERE a.postposition = 'YES'
AND a.sp_id = p.sp_id
AND p.project_code = c.project_code
AND c.item_id = a.id;


##新增业务细分类的各种金额
ALTER TABLE project_channel_relation
ADD COLUMN loan_liquidity_loans_amount bigint(20) DEFAULT NULL COMMENT '流动资金贷款(放款)' AFTER loaned_amount,
ADD COLUMN loan_financial_amount bigint(20) DEFAULT NULL COMMENT '固定资产融资(放款)' AFTER loan_liquidity_loans_amount,
ADD COLUMN loan_acceptance_bill_amount bigint(20) DEFAULT NULL COMMENT '承兑汇票担保(放款)' AFTER loan_financial_amount,
ADD COLUMN loan_credit_amount bigint(20) DEFAULT NULL COMMENT '信用证担保(放款)' AFTER loan_acceptance_bill_amount,
ADD COLUMN comp_liquidity_loans_amount bigint(20) DEFAULT NULL COMMENT '流动资金贷款(代偿)' AFTER comp_amount,
ADD COLUMN comp_financial_amount bigint(20) DEFAULT NULL COMMENT '固定资产融资(代偿)' AFTER comp_liquidity_loans_amount,
ADD COLUMN comp_acceptance_bill_amount bigint(20) DEFAULT NULL COMMENT '承兑汇票担保(代偿)' AFTER comp_financial_amount,
ADD COLUMN comp_credit_amount bigint(20) DEFAULT NULL COMMENT '信用证担保(代偿)' AFTER comp_acceptance_bill_amount,
ADD COLUMN release_liquidity_loans_amount bigint(20) DEFAULT NULL COMMENT '流动资金贷款(解保金额)' AFTER released_amount,
ADD COLUMN release_financial_amount bigint(20) DEFAULT NULL COMMENT '固定资产融资(解保金额)' AFTER release_liquidity_loans_amount,
ADD COLUMN release_acceptance_bill_amount bigint(20) DEFAULT NULL COMMENT '承兑汇票担保(解保金额)' AFTER release_financial_amount,
ADD COLUMN release_credit_amount bigint(20) DEFAULT NULL COMMENT '信用证担保(解保金额)' AFTER release_acceptance_bill_amount;

ALTER TABLE project_channel_relation_his
ADD COLUMN liquidity_loans_amount bigint(20) DEFAULT NULL COMMENT '流动资金贷款(合同金额)' AFTER contract_amount,
ADD COLUMN financial_amount bigint(20) DEFAULT NULL COMMENT '固定资产融资(合同金额)' AFTER liquidity_loans_amount,
ADD COLUMN acceptance_bill_amount bigint(20) DEFAULT NULL COMMENT '承兑汇票担保(合同金额)' AFTER financial_amount,
ADD COLUMN credit_amount bigint(20) DEFAULT NULL COMMENT '信用证担保(合同金额)' AFTER acceptance_bill_amount,
ADD COLUMN loan_liquidity_loans_amount bigint(20) DEFAULT NULL COMMENT '流动资金贷款(放款)' AFTER loaned_amount,
ADD COLUMN loan_financial_amount bigint(20) DEFAULT NULL COMMENT '固定资产融资(放款)' AFTER loan_liquidity_loans_amount,
ADD COLUMN loan_acceptance_bill_amount bigint(20) DEFAULT NULL COMMENT '承兑汇票担保(放款)' AFTER loan_financial_amount,
ADD COLUMN loan_credit_amount bigint(20) DEFAULT NULL COMMENT '信用证担保(放款)' AFTER loan_acceptance_bill_amount,
ADD COLUMN comp_liquidity_loans_amount bigint(20) DEFAULT NULL COMMENT '流动资金贷款(代偿)' AFTER comp_amount,
ADD COLUMN comp_financial_amount bigint(20) DEFAULT NULL COMMENT '固定资产融资(代偿)' AFTER comp_liquidity_loans_amount,
ADD COLUMN comp_acceptance_bill_amount bigint(20) DEFAULT NULL COMMENT '承兑汇票担保(代偿)' AFTER comp_financial_amount,
ADD COLUMN comp_credit_amount bigint(20) DEFAULT NULL COMMENT '信用证担保(代偿)' AFTER comp_acceptance_bill_amount,
ADD COLUMN release_liquidity_loans_amount bigint(20) DEFAULT NULL COMMENT '流动资金贷款(解保金额)' AFTER released_amount,
ADD COLUMN release_financial_amount bigint(20) DEFAULT NULL COMMENT '固定资产融资(解保金额)' AFTER release_liquidity_loans_amount,
ADD COLUMN release_acceptance_bill_amount bigint(20) DEFAULT NULL COMMENT '承兑汇票担保(解保金额)' AFTER release_financial_amount,
ADD COLUMN release_credit_amount bigint(20) DEFAULT NULL COMMENT '信用证担保(解保金额)' AFTER release_acceptance_bill_amount;

############################################## 2017-2-15 ##############################################

## 2017-03-03 heh档案增加原档案编号
ALTER TABLE `f_file`
  ADD COLUMN `old_file_code` VARCHAR(100) NULL  COMMENT '原档案编号' AFTER `file_code`;
  
  ##批复简述
ALTER TABLE f_council_summary_project
  ADD COLUMN overview MEDIUMTEXT DEFAULT NULL COMMENT '批复简述' AFTER belong_to_nc;

  
############################################################################################


## 20170307 档案批量借阅增加档案编号长度
ALTER TABLE `fcs_pm`.`f_file_borrow`
  CHANGE `file_code` `file_code` VARCHAR(500) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '档案编号';
  ALTER TABLE `fcs_pm`.`f_file_borrow`
  ADD COLUMN `old_file_code` VARCHAR(500) NULL  COMMENT '原档案编号' AFTER `file_code`;
ALTER TABLE `fcs_pm`.`f_file_output`
  ADD COLUMN `old_file_code` VARCHAR(100) NULL  COMMENT '原档案编号' AFTER `file_code`;



##已解保导入放用款回执错误数据订正
UPDATE fcs_pm.f_loan_use_apply_receipt r, fcs_pm.f_loan_use_apply a
SET r.project_code = a.project_code,
    r.apply_type = a.apply_type,
    r.apply_amount = a.amount,
    r.raw_add_time = a.raw_add_time
WHERE r.apply_id = a.apply_id
AND r.project_code IS NULL;

##已解保数据导入，部门编码未存入数据订正
UPDATE fcs_pm.project p, fcs_bpm.ty_sys_org o
SET p.dept_code = o.code
WHERE p.dept_id = o.ORGID
AND p.dept_code IS NULL;

UPDATE fcs_pm.f_project p, fcs_bpm.ty_sys_org o
SET p.dept_code = o.code
WHERE p.dept_id = o.ORGID
AND p.dept_code IS NULL;

UPDATE fcs_pm.project_data_info p, fcs_bpm.ty_sys_org o
SET p.dept_code = o.code
WHERE p.dept_id = o.ORGID
AND p.dept_code IS NULL;

UPDATE fcs_pm.project_data_info_his_data p, fcs_bpm.ty_sys_org o
SET p.dept_code = o.code
WHERE p.dept_id = o.ORGID
AND p.dept_code IS NULL;

alter table f_file_borrow modify borrow_detail_id varchar(1024) DEFAULT NULL COMMENT '借阅文档id';

##############################2017-03-17###################################

 
 CREATE TABLE fcs_pm.f_council_summary_project_text_credit_condition (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '对应项目批复ID',
  content mediumtext DEFAULT NULL COMMENT '文字授信条件',
  remark text DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX f_council_summary_project_text_credit_condition_sp_id_i (sp_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 -（反）担保措施 -  文字授信条件';

##20170320 合同作废申请单
CREATE TABLE fcs_pm.`f_project_contract_item_invalid`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `form_id` BIGINT(20) COMMENT '对应表单id',
  `project_code` VARCHAR(50) COMMENT '项目编号',
  `project_name` VARCHAR(128) COMMENT '项目名称',
  `customer_id` BIGINT(20) COMMENT '客户id',
  `customer_name` VARCHAR(128) COMMENT '客户名称',
  `contract_code` VARCHAR(255) COMMENT '合同编号',
  `invalid_reason` TEXT COMMENT '作废原因',
  `withdraw_all` VARCHAR(10) COMMENT '是否全部收回',
  `remark` TEXT COMMENT '备注',
  `raw_add_time`  TIMESTAMP NULL DEFAULT NULL COMMENT '新增时间',
  `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci ;
ALTER TABLE `fcs_pm`.`f_project_contract_item_invalid`
COMMENT='合同作废申请单';
ALTER TABLE `fcs_pm`.`f_project_contract_item_invalid`
  ADD COLUMN `contract_name` VARCHAR(100) NULL  COMMENT '合同名称' AFTER `contract_code`;
  ALTER TABLE `fcs_pm`.`f_project_contract_item_invalid`
  ADD COLUMN `cnt` BIGINT NULL  COMMENT '需要收回的份数' AFTER `withdraw_all`;

##立项选择货币 2017-03-21
ALTER TABLE fcs_pm.f_project
  ADD COLUMN foreign_amount VARCHAR(128) DEFAULT NULL COMMENT '外币金额' AFTER amount,
  ADD COLUMN foreign_currency_code VARCHAR(50) DEFAULT NULL COMMENT '外币编码' AFTER foreign_amount,
  ADD COLUMN foreign_currency_name VARCHAR(128) DEFAULT NULL COMMENT '外币名称' AFTER foreign_currency_code,
  ADD COLUMN exchange_rate VARCHAR(50) DEFAULT NULL COMMENT '汇率(1外币=xx人民币元)' AFTER foreign_currency_name,
  ADD COLUMN exchange_update_time DATETIME DEFAULT NULL COMMENT '汇率更新时间' AFTER exchange_rate;
  
##2017-03-21   
CREATE TABLE fcs_pm.f_capital_appropriation_apply_transfer (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '主表form_id',
  in_account varchar(128) DEFAULT NULL COMMENT '划入银行',
  out_account varchar(128) DEFAULT NULL COMMENT '划出银行',
  out_amount bigint(20) DEFAULT NULL COMMENT '划出金额',
  remark text DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_capital_appropriation_apply_transfer_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '资金划付申请-资金调动信息';  

##授信条件长度不够
ALTER TABLE fcs_pm.project_credit_condition
  CHANGE COLUMN item_desc item_desc TEXT DEFAULT NULL COMMENT '授信条件文字描述（根据对应抵（质）押、保证、文字生成）';
  
  ##2017-03-23 lirz 授信落实 字段过短
alter table fcs_pm.f_credit_condition_confirm_item change item_desc `item_desc` text COMMENT '授信条件文字描述（根据对应抵（质）押、保证生成）';
  
##数据字典  
CREATE TABLE fcs_pm.sys_data_dictionary (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  parent_id bigint(20) NOT NULL DEFAULT 0 COMMENT '上级节点ID',
  data_code varchar(50) NOT NULL COMMENT '数据编码',
  data_value text DEFAULT NULL COMMENT '数据值',
  data_value1 text DEFAULT NULL COMMENT '数据值1',
  data_value2 text DEFAULT NULL COMMENT '数据值2',
  data_value3 text DEFAULT NULL COMMENT '数据值3',
  children_num bigint(20) DEFAULT 0 COMMENT '子节点数量',
  sort_order bigint(20) DEFAULT 1 COMMENT '排序号',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX sys_data_dictionary_data_code_i (data_code),
  INDEX sys_data_dictionary_parent_id_i (parent_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '系统数据字典';



##授信新增不用落实选项
ALTER TABLE fcs_pm.project_credit_condition
  CHANGE COLUMN is_confirm is_confirm VARCHAR(10) DEFAULT NULL COMMENT '是否落实 IS已落实/YES不用落实/NO暂未落实';
  
ALTER TABLE  
fcs_pm.f_credit_condition_confirm_item  
	CHANGE COLUMN is_confirm is_confirm VARCHAR(10) DEFAULT NULL COMMENT '是否落实 IS已落实/YES不用落实/NO暂未落实';


##20170327 heh  用印明细增加合同作废标志
ALTER TABLE `fcs_pm`.`f_stamp_apply_file`
  ADD COLUMN `invalid` VARCHAR(10) NULL  COMMENT '已作废(合同作废用印记录一起作废)' AFTER `source`;
  ##20170330 heh基础资料用印申请
  CREATE TABLE `f_stamp_basic_data_apply` (
  `apply_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `apply_code` VARCHAR(20) DEFAULT NULL COMMENT '申请单编号',
  `form_id` BIGINT(20) NOT NULL COMMENT '表单ID',
  `receiver` VARCHAR(1000) NOT NULL COMMENT '接收单位',
  `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '新增时间',
  `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`apply_id`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='基础资料用印申请单'


##20170327 heh  基础资料用印清单
CREATE TABLE fcs_pm.stamp_basic_data (
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  file_name VARCHAR(128) DEFAULT NULL COMMENT '文件名称',
  remark TEXT DEFAULT NULL COMMENT '备注',
  raw_add_time TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '用印申请基础资料清单';

CREATE TABLE fcs_pm.`f_stamp_basic_data_apply_file` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `apply_id` BIGINT(20) NOT NULL COMMENT '对应申请ID',
  `file_name` VARCHAR(128) DEFAULT NULL COMMENT '文件名称',
  `file_conent` TEXT COMMENT '文件内容简述',
  `legal_chapter_num` INT(11) DEFAULT '0' COMMENT '法人章份数',
  `cachet_num` INT(11) DEFAULT '0' COMMENT '公章份数',
  `sort_order` INT(11) DEFAULT '0',
  `remark` TEXT,
  `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '新增时间',
  `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='基础资料用印申请单 - 文件列表'

######################2017-03-31###########################

##发售信息维护，新增时间缺失订正
UPDATE project_issue_information
SET raw_add_time = REPLACE(REPLACE(SUBSTRING(voucher_url, POSITION('/110_' IN voucher_url) - 10, 10), '/', '-'), '\\', '-')
WHERE raw_add_time IS NULL;

##20170401 heh
ALTER TABLE `fcs_pm`.`f_stamp_basic_data_apply`
  ADD COLUMN `org_names` TEXT NULL  COMMENT '印章公司' AFTER `receiver`;
  
##20170401 heh增加回传操作时间
ALTER TABLE `fcs_pm`.`f_project_contract_item`
  ADD COLUMN `return_add_time` TIMESTAMP NULL  COMMENT '回传操作时间' AFTER `return_remark`;
ALTER TABLE `fcs_pm`.`f_project_contract_item`
  ADD COLUMN `court_ruling_add_time` TIMESTAMP NULL  COMMENT '上传法院裁定书操作时间' AFTER `court_ruling_time`;

##补充合同回传操作时间
UPDATE f_project_contract_item
  SET return_add_time = REPLACE(REPLACE(SUBSTRING(contract_scan_url, POSITION('/110_' IN contract_scan_url) - 10, 10), '/', '-'), '\\', '-')
  WHERE contract_scan_url IS NOT NULL
  AND contract_scan_url != '';
  
##补充法院裁定书回传操作时间
 UPDATE f_project_contract_item
  SET court_ruling_add_time = REPLACE(REPLACE(SUBSTRING(court_ruling_url, POSITION('/110_' IN court_ruling_url) - 10, 10), '/', '-'), '\\', '-')
  WHERE court_ruling_url IS NOT NULL
  AND court_ruling_url != '';

#######################################2017-04-07#################################################  
  

##复议申请改富文本框加长字段
ALTER TABLE f_re_council_apply
  CHANGE COLUMN content_reason content_reason MEDIUMTEXT DEFAULT NULL COMMENT '复议内容及理由',
  CHANGE COLUMN overview overview MEDIUMTEXT DEFAULT NULL COMMENT '综合意见';
  
##################################################################################################  

##2017-04-11 项目移交明细
CREATE TABLE fcs_pm.project_transfer_detail (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(128) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  busi_type varchar(50) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(100) DEFAULT NULL COMMENT '业务类型名称',
  project_phase VARCHAR(30) DEFAULT NULL COMMENT '移交时项目阶段',
  project_status VARCHAR(30) DEFAULT NULL COMMENT '移交时项目状态',
  project_amount BIGINT(20) DEFAULT NULL COMMENT '授信金额',
  project_balance BIGINT(20) DEFAULT NULL COMMENT '移交时在保余额',
  apply_user_id bigint(20) DEFAULT NULL COMMENT '申请人ID',
  apply_user_account varchar(50) DEFAULT NULL COMMENT '申请人账号',
  apply_user_name varchar(50) DEFAULT NULL COMMENT '申请人名字',
  transfer_status varchar(50) DEFAULT NULL COMMENT '移交状态[草稿、审核中、移交完成、移交失败]',
  transfer_type varchar(20) DEFAULT NULL COMMENT '移交类型[客户经理、风险经理、法务经理、移交至法务部]',
  transfer_time datetime DEFAULT NULL COMMENT '移交时间',
  original_user_id bigint(20) DEFAULT NULL COMMENT '原始人员ID',
  original_user_account varchar(50) DEFAULT NULL COMMENT '原始人员账号',
  original_user_name varchar(50) DEFAULT NULL COMMENT '原始人员名字',
  original_user_dept_id bigint(20) DEFAULT NULL COMMENT '原始人员部门ID',
  original_user_dept_name varchar(50) DEFAULT NULL COMMENT '原始人员部门名字',
  original_userb_id bigint(20) DEFAULT NULL COMMENT '原始人员BID',
  original_userb_account varchar(50) DEFAULT NULL COMMENT '原始人员B账号',
  original_userb_name varchar(50) DEFAULT NULL COMMENT '原始人员B名字',
  original_userb_dept_id bigint(20) DEFAULT NULL COMMENT '原始人员B部门ID',
  original_userb_dept_name varchar(50) DEFAULT NULL COMMENT '原始人员B部门名字',
  accept_user_id bigint(20) DEFAULT NULL COMMENT '接收人员ID',
  accept_user_account varchar(50) DEFAULT NULL COMMENT '接收人员账号',
  accept_user_name varchar(50) DEFAULT NULL COMMENT '接收人员名字',
  accept_user_dept_id bigint(20) DEFAULT NULL COMMENT '接收人员部门ID',
  accept_user_dept_name varchar(50) DEFAULT NULL COMMENT '接收人员部门名字',
  accept_userb_id bigint(20) DEFAULT NULL COMMENT '接收人员BID',
  accept_userb_account varchar(50) DEFAULT NULL COMMENT '接收人员B账号',
  accept_userb_name varchar(50) DEFAULT NULL COMMENT '接收人员B名字',
  accept_userb_dept_id bigint(20) DEFAULT NULL COMMENT '接收人员部门BID',
  accept_userb_dept_name varchar(50) DEFAULT NULL COMMENT '接收人员部门B名字',
  transfer_file text DEFAULT NULL COMMENT '移交文件',
  form_change varchar(1000) DEFAULT NULL COMMENT '决策依据(签报formId)',
  risk_council_summary varchar(1000) DEFAULT NULL COMMENT '决策依据(风险处理会议纪要)',
  project_approval varchar(50) DEFAULT NULL COMMENT '决策依据(批复-项目编号)',
  remark mediumtext DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_project_contract_project_code_i (project_code),
  INDEX project_transfer_detail_form_id_i (form_id)
)
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目移交明细';


##lirz 20170412 解保
alter table fcs_pm.guarantee_apply_counter change column project_code `project_code` varchar(50) NOT NULL COMMENT '项目编号';

##heh 20170414 基础资料用印清单
ALTER TABLE `fcs_pm`.`stamp_basic_data`
  CHANGE `file_name` `file_name` VARCHAR(200) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '文件名称';
  ALTER TABLE `born_fcs_fbpm`.`f_stamp_basic_data_apply_file`
  CHANGE `file_name` `file_name` VARCHAR(200) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '文件名称';

#############################2017-4-18###################################
  
##lirz 20170419 保后房地产类型添加附件上传
alter table f_afterwards_check_report_project add column attachment_info text COMMENT '附件信息' after project_desc;

##heh 20170424 合同申请增加手输的合同编号
ALTER TABLE `fcs_pm`.`f_project_contract_item`
  ADD COLUMN `contract_code2` VARCHAR(100) NULL  COMMENT '手动输入的合同编号,contract_code页面显示为合同ID' AFTER `contract_code`;
  
##2017-05-12新增业务品种 
INSERT INTO busi_type (code, name, parent_id, customer_type, has_children, setup_form_code, sort_order, is_del, raw_add_time)
  VALUES ('136', '商业保理担保', 13, 'ENTERPRISE', 'NO', 'SET_UP_GUARANTEE_ENTRUSTED', 18, no, NOW());  
  
##20170512 尽调审核修改记录表
CREATE TABLE `f_investigation_checking` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `form_id` bigint(20) NOT NULL COMMENT '对应的表单ID',
  `project_code` varchar(50) DEFAULT NULL COMMENT '项目编号',
  `related_form_id` bigint(20) DEFAULT '0' COMMENT '关联表单id',
  `check_point` varchar(30) DEFAULT NULL COMMENT '修改节点',
  `form_code` varchar(50) DEFAULT NULL COMMENT '表单编码，记录修改了哪些表单',
  `user_id` bigint(20) DEFAULT NULL COMMENT '修改用户ID',
  `user_account` varchar(20) DEFAULT NULL COMMENT '操作人账号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '操作人名称',
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '新增时间',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `form_id` (`form_id`) USING BTREE,
  UNIQUE KEY `check_point` (`related_form_id`,`check_point`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='尽职调查 - 审核过程修改记录表';

#######################2017-5-15#####################################

##新增用户档案系统用户名密码
ALTER TABLE user_extra_message
  ADD COLUMN file_sys_uname VARCHAR(50) DEFAULT NULL COMMENT '档案系统用户名' AFTER cancel_alert,
  ADD COLUMN file_sys_psw VARCHAR(50) DEFAULT NULL COMMENT '档案系统密码' AFTER file_sys_uname;

##立项-股权结构新增单位
ALTER TABLE f_project_equity_structure
  ADD COLUMN amount_type VARCHAR(50) DEFAULT '元' COMMENT '货币' AFTER capital_contributions;

##会议纪要期限增加备注  
ALTER TABLE f_council_summary_project
  ADD COLUMN time_remark VARCHAR(255) DEFAULT NULL COMMENT '期限备注' AFTER time_unit; 

##复议记录上会ID
ALTER TABLE f_re_council_apply
  ADD COLUMN council_apply_id BIGINT(20) DEFAULT NULL COMMENT '会议申请ID' AFTER overview,
  ADD COLUMN council_back VARCHAR(20) DEFAULT 'NO' COMMENT '是否上会被退回 YES/NO' AFTER council_apply_id;  

##风险处置会增加重新授信选项  
ALTER TABLE f_council_summary_risk_handle
  ADD COLUMN is_redo VARCHAR(20) DEFAULT NULL COMMENT '是否重新授信' AFTER comp_remark,
  ADD COLUMN redo_amount BIGINT(20) DEFAULT NULL COMMENT '重新授信金额' AFTER is_redo,
  ADD COLUMN redo_time_limit INT(11) DEFAULT NULL COMMENT '重新授信期限' AFTER redo_amount,
  ADD COLUMN redo_time_unit VARCHAR(10) DEFAULT NULL COMMENT '重新授信期限单位' AFTER redo_time_limit,
  ADD COLUMN redo_time_remark VARCHAR(255) DEFAULT NULL COMMENT '重新授信期限备注' AFTER redo_time_unit,
  ADD COLUMN redo_busi_type VARCHAR(10) DEFAULT NULL COMMENT '重新授信业务类型' AFTER redo_time_remark,
  ADD COLUMN redo_busi_type_name VARCHAR(50) DEFAULT NULL COMMENT '重新授信业务类型名称' AFTER redo_busi_type,
  ADD COLUMN redo MEDIUMTEXT DEFAULT NULL COMMENT '重新授信描述' AFTER redo_busi_type_name,
  ADD COLUMN redo_remark MEDIUMTEXT DEFAULT NULL COMMENT '重新授信备注' AFTER redo; 

ALTER TABLE f_council_summary_risk_handle  
  ADD COLUMN redo_project_code VARCHAR(50) DEFAULT NULL COMMENT '重新授信项目编号' AFTER is_redo,
  ADD COLUMN redo_project_name VARCHAR(200) DEFAULT NULL COMMENT '重新授信项目名称' AFTER redo_project_code;

##重新授信标记 
ALTER TABLE project
  ADD COLUMN is_redo VARCHAR(20) DEFAULT NULL COMMENT '是否重新授信' AFTER reinsurance_amount,
  ADD COLUMN is_redo_project VARCHAR(20) DEFAULT NULL COMMENT '是否重新授信衍生项目' AFTER is_redo,
  ADD COLUMN redo_from VARCHAR(50) DEFAULT NULL COMMENT '通过哪个项目（项目编号）重新授信而来' AFTER is_redo_project,
  ADD COLUMN redo_risk_handle_id bigint(20) DEFAULT NULL COMMENT '从哪次风险处置会重新授信而来' AFTER redo_from;
  
ALTER TABLE project_data_info
  ADD COLUMN is_redo VARCHAR(20) DEFAULT NULL COMMENT '是否重新授信' AFTER fan_guarantee_methord,
  ADD COLUMN is_redo_project VARCHAR(20) DEFAULT NULL COMMENT '是否重新授信衍生项目' AFTER is_redo,
  ADD COLUMN redo_from VARCHAR(50) DEFAULT NULL COMMENT '通过哪个项目（项目编号）重新授信而来' AFTER is_redo_project,
  ADD COLUMN redo_risk_handle_id bigint(20) DEFAULT NULL COMMENT '从哪次风险处置会重新授信而来' AFTER redo_from;

ALTER TABLE project_data_info_his_data
  ADD COLUMN is_redo VARCHAR(20) DEFAULT NULL COMMENT '是否重新授信' AFTER fan_guarantee_methord,
  ADD COLUMN is_redo_project VARCHAR(20) DEFAULT NULL COMMENT '是否重新授信衍生项目' AFTER is_redo,
  ADD COLUMN redo_from VARCHAR(50) DEFAULT NULL COMMENT '通过哪个项目（项目编号）重新授信而来' AFTER is_redo_project,
  ADD COLUMN redo_risk_handle_id bigint(20) DEFAULT NULL COMMENT '从哪次风险处置会重新授信而来' AFTER redo_from;

########################################

 ALTER TABLE project
  ADD COLUMN is_his TINYINT(4) DEFAULT 0 COMMENT '是否存量项目 1：是，0：否' AFTER redo_risk_handle_id; 
  
##上会不在2017的认为是历史项目  
UPDATE project p
SET is_his = 1
WHERE p.project_code IN (SELECT
    cp.project_code
  FROM council c
    JOIN council_projects cp
      ON c.council_id = cp.council_id
  WHERE c.council_id IN (SELECT
      MIN(c.council_id) council_id
    FROM council c
      JOIN council_projects cp
        ON c.council_id = cp.council_id
    WHERE c.council_type_code = 'PROJECT_REVIEW'
    GROUP BY cp.project_code)
  AND c.council_code NOT LIKE '%2017%')
######################################################
  
##签报 
 ALTER TABLE form_change_record
 	ADD COLUMN form_content mediumtext DEFAULT NULL COMMENT '更改的表单内容（编辑页面）' AFTER page_content;
 	
##新增理财送审流程
CREATE TABLE f_project_financial_review (
  review_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '表单ID',
  project_code varchar(30) DEFAULT NULL COMMENT '理财项目编号',
  investigation mediumtext DEFAULT NULL COMMENT '尽职调查',
  investigation_attach mediumtext DEFAULT NULL COMMENT '尽职调查-附件',
  risk_review mediumtext DEFAULT NULL COMMENT '风险审查报告',
  risk_review_attach mediumtext DEFAULT NULL COMMENT '风险审查报告-附件',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (review_id),
  INDEX f_project_financial_review_form_id_i (form_id),
  INDEX f_project_financial_review_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '理财项目送审'; 	

##新增理财合同流程
CREATE TABLE f_project_financial_contract (
  contract_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '表单ID',
  project_code varchar(30) DEFAULT NULL COMMENT '理财项目编号',
  contract mediumtext DEFAULT NULL COMMENT '合同内容',
  attach text DEFAULT NULL COMMENT '合同-附件',
  contract_status varchar(50) DEFAULT NULL COMMENT '合同状态',
  contract_return text DEFAULT NULL COMMENT '合同-回传',
  remark text DEFAULT NULL COMMENT '合同-备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (contract_id),
  INDEX f_project_financial_contract_form_id_i (form_id),
  INDEX f_project_financial_contract_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '理财项目合同';

##放用款回执中没有资金渠道的
UPDATE f_loan_use_apply_receipt r,
	   project_channel_relation c
SET r.capital_channel_id = c.channel_id,
    r.capital_channel_code = c.channel_code,
    r.capital_channel_type = c.channel_type,
    r.capital_channel_name = c.channel_name
WHERE c.latest = 'YES'
AND c.channel_relation = 'CAPITAL_CHANNEL'
AND r.project_code = c.project_code
AND (r.capital_channel_code IS NULL
OR r.capital_channel_code = '');

##解保中没有资金渠道的
UPDATE f_counter_guarantee_repay r,
	   f_counter_guarantee_release g,
	   project_channel_relation c
SET r.capital_channel_id = c.channel_id,
    r.capital_channel_code = c.channel_code,
    r.capital_channel_type = c.channel_type,
    r.capital_channel_name = c.channel_name
WHERE c.latest = 'YES'
AND r.form_id = g.form_id
AND c.channel_relation = 'CAPITAL_CHANNEL'
AND g.project_code = c.project_code
AND (r.capital_channel_code IS NULL
OR r.capital_channel_code = '');

##############################################################################

##2017-07-04 lirz 档案管理 批量借阅
ALTER TABLE f_file_borrow 
CHANGE `file_code` `file_code` text COMMENT '档案编号',
CHANGE `old_file_code` `old_file_code` text COMMENT '原档案编号',
CHANGE `project_code` `project_code` varchar(200) DEFAULT NULL COMMENT '对应项目编号',
CHANGE `project_name` `project_name` text COMMENT '对应项目名称',
CHANGE `customer_name` `customer_name` text COMMENT '对应客户名称',
CHANGE `borrow_detail_id` `borrow_detail_id` text COMMENT '借阅文档id';

ALTER TABLE f_file_borrow 
ADD COLUMN is_batch varchar(16) DEFAULT NULL COMMENT '是否批量借阅' AFTER is_return,
ADD COLUMN file_ids text COMMENT '档案编号列表' AFTER is_batch;

ALTER TABLE f_file_borrow
ADD COLUMN borrow_form_id bigint(20) DEFAULT 0 COMMENT '借阅formId' AFTER file_ids;

#2017-07-07 lirz 风险评级表结构修改及数据修正
ALTER TABLE f_risk_level ADD check_phase varchar(50) DEFAULT NULL COMMENT '评定阶段' AFTER credit_amount;
update f_risk_level set check_phase = 'E' where reevaluation_id = 0;
update f_risk_level set check_phase = 'RE' where reevaluation_id > 0;

##2017-07-07 lirz 尽调 财务报表修改
ALTER TABLE f_investigation_financial_review 
ADD COLUMN sub_index int(11) DEFAULT 0 COMMENT '子页面编号' AFTER form_id,
ADD COLUMN is_active varchar(16) DEFAULT NULL COMMENT '是否激活' AFTER sub_index;

ALTER TABLE f_investigation_financial_review drop index form_id_u;
ALTER TABLE f_investigation_financial_review ADD UNIQUE `form_id_u` (`form_id`, `sub_index`);

###############################################################################################

##多次追偿
ALTER TABLE project_recovery
  ADD COLUMN recovery_name VARCHAR(200) DEFAULT NULL COMMENT '追偿方案名称' AFTER legal_manager_name,
  ADD COLUMN is_append VARCHAR(10) DEFAULT NULL COMMENT '是否派生诉讼 IS/NO' AFTER apply_dept_name,
  ADD COLUMN append_recovery_id BIGINT(20) DEFAULT NULL COMMENT '派生来源（追偿方案ID）' AFTER is_append,
  ADD COLUMN append_recovery_name VARCHAR(200) DEFAULT NULL COMMENT '派生来源（追偿方案名称）' AFTER append_recovery_id,
  ADD COLUMN append_remark MEDIUMTEXT DEFAULT NULL COMMENT '派生追偿描述' AFTER append_recovery_name,
  ADD COLUMN recovery_close_time DATETIME DEFAULT NULL COMMENT '追偿关闭时间' AFTER append_remark,
  ADD COLUMN project_close_time DATETIME DEFAULT NULL COMMENT '项目关闭时间' AFTER recovery_close_time;

## 权利凭证展期
CREATE TABLE `f_file_output_extension` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `form_id` BIGINT(20) NOT NULL COMMENT '表单ID',
  `project_code` VARCHAR(50) DEFAULT NULL COMMENT '项目编号',
  `project_name` VARCHAR(128) DEFAULT NULL COMMENT '项目名称',
  `customer_id` BIGINT(20) DEFAULT NULL COMMENT '客户ID',
  `customer_name` VARCHAR(128) DEFAULT NULL COMMENT '客户名称',
  `reason` VARCHAR(1000) DEFAULT NULL COMMENT '申请原因',
  `extension_date` DATETIME DEFAULT NULL COMMENT '展期时间',
  `raw_add_time` TIMESTAMP NULL DEFAULT NULL COMMENT '新增时间',
  `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT
'修改时间',
  PRIMARY KEY (`id`),
  KEY `f_file_output_extension_form_id_i` (`form_id`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='权利凭证出库展期申请单';

ALTER TABLE `project`
  ADD COLUMN `right_voucher_exten_date` DATETIME NULL  COMMENT '权利凭证展期时间'
AFTER `is_his`;

##添加节点诉讼的顺序
ALTER TABLE project_recovery_litigation_before_preservation
  ADD COLUMN litigation_index INT(11) DEFAULT 0 COMMENT '诉讼顺序' AFTER preservation_time;
  
ALTER TABLE project_recovery_litigation_place_on_file
  ADD COLUMN litigation_index INT(11) DEFAULT 10 COMMENT '诉讼顺序' AFTER memo; 
  
ALTER TABLE project_recovery_litigation_preservation
  ADD COLUMN litigation_index INT(11) DEFAULT 20 COMMENT '诉讼顺序' AFTER preservation_time;
  
ALTER TABLE project_recovery_litigation_before_trail
  ADD COLUMN litigation_index INT(11) DEFAULT 30 COMMENT '诉讼顺序' AFTER end_notice;
  
ALTER TABLE project_recovery_litigation_opening
  ADD COLUMN litigation_index INT(11) DEFAULT 40 COMMENT '诉讼顺序' AFTER memo; 
  
ALTER TABLE project_recovery_litigation_referee
  ADD COLUMN litigation_index INT(11) DEFAULT 50 COMMENT '诉讼顺序' AFTER memo;
  
ALTER TABLE project_recovery_litigation_second_appeal
  ADD COLUMN litigation_index INT(11) DEFAULT 60 COMMENT '诉讼顺序' AFTER memo;
  
ALTER TABLE project_recovery_litigation_special_procedure
  ADD COLUMN litigation_index INT(11) DEFAULT 70 COMMENT '诉讼顺序' AFTER memo;
  
ALTER TABLE project_recovery_litigation_certificate
  ADD COLUMN litigation_index INT(11) DEFAULT 80 COMMENT '诉讼顺序' AFTER memo; 
  
ALTER TABLE project_recovery_litigation_execute
  ADD COLUMN litigation_index INT(11) DEFAULT 90 COMMENT '诉讼顺序' AFTER memo; 
  
ALTER TABLE project_recovery_litigation_adjourned_procedure
  ADD COLUMN litigation_index INT(11) DEFAULT 100 COMMENT '诉讼顺序' AFTER memo;
  
ALTER TABLE project_recovery_litigation_adjourned_second
  ADD COLUMN litigation_index INT(11) DEFAULT 110 COMMENT '诉讼顺序' AFTER memo;  
  
ALTER TABLE project_recovery_litigation_execute_gyration
  ADD COLUMN litigation_index INT(11) DEFAULT 120 COMMENT '诉讼顺序' AFTER memo;  
 
##订正历史数据顺序  
update project_recovery_litigation_referee SET litigation_index=51 where project_recovery_referee_type='SECOND_APPEAL';
update project_recovery_litigation_referee SET litigation_index=52 where project_recovery_referee_type='ADJOURNED_PROCEDURE_FIRST';
update project_recovery_litigation_referee SET litigation_index=52 where project_recovery_referee_type='ADJOURNED_PROCEDURE_SECOND';  

ALTER TABLE project_recovery_litigation_referee
  CHANGE COLUMN project_recovery_referee_type project_recovery_referee_type VARCHAR(50) DEFAULT NULL COMMENT '裁判类型';

##订正历史数据顺序
CREATE TABLE IF NOT EXISTS temp_litigation_index (
  seq int(11) DEFAULT NULL,
  id int(11) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '临时表';

TRUNCATE TABLE  temp_litigation_index;
INSERT INTO temp_litigation_index (id, seq)
  SELECT
    p.id,
    (@i := @i + 1) SEQ
  FROM project_recovery_litigation_adjourned_procedure p,
       (SELECT
           @i := 0) AS it
  WHERE project_recovery_id IN (SELECT
      project_recovery_id
    FROM project_recovery_litigation_adjourned_procedure
    GROUP BY project_recovery_id
    HAVING COUNT(*) > 1);

UPDATE project_recovery_litigation_adjourned_procedure p, temp_litigation_index t
SET p.litigation_index = p.litigation_index + t.seq
WHERE p.id = t.id;

TRUNCATE TABLE  temp_litigation_index;
INSERT INTO temp_litigation_index (id, seq)
  SELECT
    p.id,
    (@i := @i + 1) SEQ
  FROM project_recovery_litigation_opening p,
       (SELECT
           @i := 0) AS it
  WHERE project_recovery_id IN (SELECT
      project_recovery_id
    FROM project_recovery_litigation_opening
    GROUP BY project_recovery_id
    HAVING COUNT(*) > 1);

UPDATE project_recovery_litigation_opening p, temp_litigation_index t
SET p.litigation_index = p.litigation_index + t.seq
WHERE p.id = t.id;

TRUNCATE TABLE  temp_litigation_index;
INSERT INTO temp_litigation_index (id, seq)
  SELECT
    p.id,
    (@i := @i + 1) SEQ
  FROM project_recovery_litigation_second_appeal p,
       (SELECT
           @i := 0) AS it
  WHERE project_recovery_id IN (SELECT
      project_recovery_id
    FROM project_recovery_litigation_second_appeal
    GROUP BY project_recovery_id
    HAVING COUNT(*) > 1);

UPDATE project_recovery_litigation_second_appeal p, temp_litigation_index t
SET p.litigation_index = p.litigation_index + t.seq
WHERE p.id = t.id;

DROP TABLE temp_litigation_index;

#######################################2017-07-17########################################


ALTER TABLE project
  CHANGE COLUMN redo_from redo_from TEXT DEFAULT NULL COMMENT '通过哪个项目（项目编号）重新授信而来';
  
##########################################################################################

CREATE TABLE fcs_pm.project_virtual (
  virtual_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号(新生成)',
  project_name varchar(128) DEFAULT NULL COMMENT '项目名称(新生成)',
  customer_id bigint(20) DEFAULT NULL COMMENT '客户ID（虚拟）',
  customer_name varchar(128) DEFAULT NULL COMMENT '客户名称（虚拟）',
  amount bigint(20) DEFAULT NULL COMMENT '授信金额（所选项目金额总和）',
  busi_type varchar(50) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(100) DEFAULT NULL COMMENT '业务类型名称',
  apply_user_id bigint(20) DEFAULT NULL COMMENT '申请人ID',
  apply_user_account varchar(50) DEFAULT NULL COMMENT '申请人账号',
  apply_user_name varchar(50) DEFAULT NULL COMMENT '申请人名字',
  apply_dept_id bigint(20) DEFAULT NULL COMMENT '申请人部门ID',
  apply_dept_name varchar(50) DEFAULT NULL COMMENT '申请人部门名称',
  scheme mediumtext DEFAULT NULL COMMENT '方案',
  remark mediumtext DEFAULT NULL COMMENT '备注',
  attach text DEFAULT NULL COMMENT '移交文件',
  status varchar(50) DEFAULT NULL COMMENT '移交状态[草稿、提交]',
  submit_time datetime DEFAULT NULL COMMENT '提交时间',
  form_names text DEFAULT NULL COMMENT '已经发起的表单（不再数据库中体现，动态查询）',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (virtual_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '虚拟项目';  
  
CREATE TABLE fcs_pm.project_virtual_detail (
  detail_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  virtual_id bigint(20) NOT NULL COMMENT 'project_virtual.virtual_id',
  virtual_project_code varchar(50) DEFAULT NULL COMMENT '虚拟项目编号',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(128) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '客户名称',
  amount bigint(20) DEFAULT NULL COMMENT '授信金额',
  balance bigint(20) DEFAULT NULL COMMENT '在保余额',
  busi_type varchar(50) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(100) DEFAULT NULL COMMENT '业务类型名称',
  busi_manager_id bigint(20) DEFAULT NULL COMMENT '客户经理ID',
  busi_manager_account varchar(50) DEFAULT NULL COMMENT '客户经理账号',
  busi_manager_name varchar(50) DEFAULT NULL COMMENT '客户经理名称',
  dept_id bigint(20) DEFAULT NULL COMMENT '项目所属部门ID',
  dept_name varchar(50) DEFAULT NULL COMMENT '项目所属部门名称',
  remark mediumtext DEFAULT NULL COMMENT '备注',
  attach text DEFAULT NULL COMMENT '附件',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (detail_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '虚拟项目-项目明细';

###########################################################################

## 2017-8-1 10:56:38 heh  合同增加不用印标志
ALTER TABLE `born_fcs_fbpm`.`f_project_contract_item`
  ADD COLUMN `is_need_stamp` VARCHAR(10) NULL  COMMENT '是否需要用印(IS/NO)' AFTER `return_add_time`;
ALTER TABLE `born_fcs_fbpm`.`f_project_contract_item`
  CHANGE `is_need_stamp` `is_need_stamp` VARCHAR(10) CHARSET utf8 COLLATE utf8_general_ci DEFAULT 'IS'   NULL  COMMENT '是否需要用印(IS/NO)';
#########################

ALTER TABLE project
  ADD COLUMN regulator_approval_amount BIGINT DEFAULT NULL COMMENT '监管机构批复金额' AFTER reinsurance_amount;
  
 
ALTER TABLE project_issue_information
  ADD COLUMN actual_occur_amount BIGINT DEFAULT NULL COMMENT '实际金额（分保情况下会乘分保比例）' AFTER actual_amount;
  
UPDATE project_issue_information
SET actual_occur_amount = actual_amount
WHERE is_reinsurance != 'IS' OR is_reinsurance IS null; 

##################################################

ALTER TABLE f_capital_appropriation_apply
	ADD COLUMN is_simple varchar(10) DEFAULT 'NO' COMMENT '简单流程（IS/NO）' AFTER remark;
	
ALTER TABLE f_capital_appropriation_apply_fee
  ADD COLUMN com_type VARCHAR(10) DEFAULT NULL COMMENT '被扣划冻结类型（扣划、冻结、其他）' AFTER appropriate_amount;	
	
update f_capital_appropriation_apply set is_simple = 'NO';	
