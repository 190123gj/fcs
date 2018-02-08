ALTER TABLE asset_relation_project
  ADD UNIQUE INDEX UK_asset_relation_project_Index (assets_id, assets_status, project_code);
  
#20161005 jil 资产受让 是否收费
ALTER TABLE f_assets_transfer_application ADD COLUMN is_charge varchar(10) DEFAULT 'NO' COMMENT '是否已收费' AFTER liquidater_status;

#20161005 jil 资产 搜索关键字
ALTER TABLE pledge_asset ADD COLUMN search_key varchar(255) DEFAULT null COMMENT '搜索关键字' AFTER user_name;

#20161011 jil 资产转让  会议退回
ALTER TABLE f_assets_transfer_application ADD COLUMN council_back varchar(10) DEFAULT 'NO' COMMENT '是否会议退回' AFTER is_charge;

################ 2016-10-18 以上已执行 ####################

#20161022 jil
UPDATE pledge_text_custom ptc set ptc.is_required='NO' WHERE ptc.field_name='评估公司';
UPDATE pledge_text_custom ptc set ptc.is_required='IS' WHERE ptc.field_name='评估价格'; 

#20161025 jil
UPDATE pledge_text_custom set is_required='NO' WHERE field_name='登记机关';
#20161026 jil
UPDATE pledge_text_custom set is_required='NO' WHERE field_name='登记机关地址';

#20161027 jil 资产关联项目
ALTER TABLE asset_relation_project ADD COLUMN asset_first_type varchar(255) DEFAULT null COMMENT '资产最初类型（抵押还是质押）' AFTER busi_type_name;

ALTER TABLE asset_relation_project DROP INDEX UK_asset_relation_project_Index;

ALTER TABLE asset_relation_project ADD INDEX UK_asset_relation_project_Index(assets_id,assets_status,project_code,asset_first_type);

##########################################

#20161116 jil 资产关联视频监控
ALTER TABLE pledge_asset ADD COLUMN ralate_video TEXT DEFAULT null COMMENT '关联视频监控' AFTER search_key;

##########################################11-18##########################################

#20161229  jil
ALTER TABLE pledge_type_common ADD COLUMN asset_remark_info TEXT DEFAULT null COMMENT '资产标示信息' AFTER latitude;

#20170105  jil
ALTER TABLE pledge_asset ADD COLUMN asset_remark_info TEXT DEFAULT null COMMENT '资产标示信息' AFTER ralate_video;

-------------------------------------------------------------------------------------------------------------
#20170109  jil  数据订正   -已执行 
UPDATE pledge_text_custom ptc 
  LEFT JOIN pledge_type pt ON ptc.type_id = pt.type_id 
  set ptc.field_name='拟质押金额' 
  WHERE ptc.field_name='评估价格' AND pt.level_two ='应收账款';

  UPDATE pledge_type_attribute pta
    LEFT JOIN pledge_type pt ON pta.type_id = pt.type_id
    set pta.attribute_key='拟质押金额'
    WHERE pta.attribute_key='评估价格' AND pt.level_two ='应收账款';
    
#20170111  jil  数据订正   -已执行 
UPDATE pledge_text_custom ptc
LEFT JOIN pledge_type pt
  ON ptc.type_id = pt.type_id
SET ptc.control_length = 100 ,ptc.field_type = 'TEXT',ptc.measurement_unit=NULL
WHERE (pt.level_two = '居住类房产' OR pt.level_two = '经营类房产' OR pt.level_two = '林权') 
  AND (pt.level_three = '住宅' OR pt.level_three = '别墅' OR pt.level_three = '仓库厂房' OR pt.level_three = '商铺' OR pt.level_three = '写字楼' OR pt.level_three = '林木所有权' OR pt.level_three = '林木使用权' OR pt.level_three = '林地使用权') 
  AND (ptc.field_name = '土地使用权到期日' OR ptc.field_name = '租期' OR ptc.field_name = '林地使用期限');

 ################################## 
  ALTER TABLE pledge_asset
  ADD COLUMN remark MEDIUMTEXT DEFAULT NULL COMMENT '资产备注（非结构化）' AFTER asset_remark_info,
  ADD COLUMN attach TEXT DEFAULT NULL COMMENT '资产附件（非结构化）' AFTER remark;
 
 ##############2017-5-15################# 