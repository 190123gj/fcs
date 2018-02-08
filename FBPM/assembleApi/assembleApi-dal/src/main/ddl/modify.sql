CREATE TABLE `mobile_info` (
  `mid` bigint(20) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(50) DEFAULT NULL,
  `cert_no` varchar(50) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '实名手机号',
  `sex` varchar(5) DEFAULT NULL COMMENT 'M为男，F为女',
  `birthday` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `province` varchar(100) DEFAULT NULL COMMENT '手机号归属(省)',
  `city` varchar(100) DEFAULT NULL COMMENT '手机号归属(市)',
  `operator` varchar(50) DEFAULT NULL COMMENT '运营商',
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手机号实名信息';

ALTER TABLE person_real_info drop COLUMN mobile;
ALTER TABLE person_real_info drop COLUMN card_no;
ALTER TABLE person_real_info ADD card_photo longtext COMMENT '照片';

##以上已执行#################################################
CREATE TABLE `card_info` (
  `card_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `cert_no` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `mobile` varchar(20) DEFAULT NULL COMMENT '实名手机号',
  `card_no` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `bank_name` varchar(100) DEFAULT NULL COMMENT '开户行名称',
  PRIMARY KEY (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行卡实名信息'
##以上已执行#################################################
CREATE TABLE `message_read_status` (
  `m_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户名',
  `related_id` varchar(50) DEFAULT NULL COMMENT '风险预警ID、关联ID',
  `row_add_time` varchar(20) DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8
##以上已执行#################################################
CREATE TABLE `apix_very_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增加',
  `order_no` varchar(40) NOT NULL COMMENT '请求订单号',
  `service_name` varchar(50) NOT NULL COMMENT '请求服务名称',
  `req_param` varchar(200) NOT NULL COMMENT '请求参数[0:验证信息一致]',
  `very_code` varchar(20) NOT NULL COMMENT '校验结果[code]',
  `very_msg` varchar(50) NOT NULL COMMENT '校验结果[msg]',
  `row_add_time` varchar(20) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='apix请求纪录表'
##以上已执行#################################################
CREATE TABLE `sms_send_record` (
  `rid` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `content` varchar(200) NOT NULL COMMENT '短信内容',
  `channel` varchar(20) DEFAULT NULL COMMENT '短信渠道号',
  `status` varchar(5) DEFAULT NULL COMMENT '状态：1 成功 0 失败',
  `row_add_time` varchar(20) DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信服务调用记录'

ALTER TABLE app_login_info ADD um_device_no varchar(80) COMMENT '友盟设备号';