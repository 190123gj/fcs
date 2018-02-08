/*
SQLyog Ultimate v11.13 (64 bit)
MySQL - 5.6.20-log : Database - born_jckapi
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`born_jckapi` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `born_jckapi`;

/*Table structure for table `app_login_info` */

DROP TABLE IF EXISTS `app_login_info`;

CREATE TABLE `app_login_info` (
  `log_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码镜像(用于保证各终端之间密码一致性)',
  `device_no` varchar(50) NOT NULL COMMENT '设备号',
  `token` varchar(50) NOT NULL COMMENT '一次性token',
  `last_log_time` varchar(20) DEFAULT NULL COMMENT '最后一次登陆时间',
  `last_log_ip` varchar(20) DEFAULT NULL COMMENT '最后一次登陆IP',
  `row_add_time` varchar(20) DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`log_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Table structure for table `person_real_info` */

DROP TABLE IF EXISTS `person_real_info`;

CREATE TABLE `person_real_info` (
  `pid` bigint(20) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(50) DEFAULT NULL,
  `cert_no` varchar(50) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL COMMENT 'M为男，F为女',
  `birthday` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `mobile` varchar(20) DEFAULT NULL COMMENT '实名手机号',
  `card_no` varchar(30) DEFAULT NULL COMMENT '银行卡号',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='手机号、银行卡、身份证实名实名信息';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
