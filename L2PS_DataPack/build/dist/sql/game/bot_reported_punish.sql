/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : l2ps

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2013-09-24 12:05:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bot_reported_punish`
-- ----------------------------
DROP TABLE IF EXISTS `bot_reported_punish`;
CREATE TABLE `bot_reported_punish` (
  `charId` int(11) NOT NULL DEFAULT '0',
  `punish_type` varchar(45) DEFAULT NULL,
  `time_left` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;