/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : l2ps

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2013-09-24 12:05:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bot_report`
-- ----------------------------
DROP TABLE IF EXISTS `bot_report`;
CREATE TABLE `bot_report` (
  `report_id` int(10) NOT NULL AUTO_INCREMENT,
  `reported_name` varchar(45) DEFAULT NULL,
  `reported_objectId` int(10) DEFAULT NULL,
  `reporter_name` varchar(45) DEFAULT NULL,
  `reporter_objectId` int(10) DEFAULT NULL,
  `date` decimal(20,0) NOT NULL DEFAULT '0',
  `read` enum('true','false') NOT NULL DEFAULT 'false',
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;