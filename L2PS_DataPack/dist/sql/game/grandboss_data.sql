/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : l2ps

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2013-09-25 14:19:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `grandboss_data`
-- ----------------------------
DROP TABLE IF EXISTS `grandboss_data`;
CREATE TABLE `grandboss_data` (
  `boss_id` smallint(5) unsigned NOT NULL,
  `loc_x` mediumint(6) NOT NULL,
  `loc_y` mediumint(6) NOT NULL,
  `loc_z` mediumint(6) NOT NULL,
  `heading` mediumint(6) NOT NULL DEFAULT '0',
  `respawn_time` bigint(13) unsigned NOT NULL DEFAULT '0',
  `currentHP` decimal(30,15) NOT NULL,
  `currentMP` decimal(30,15) NOT NULL,
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`boss_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grandboss_data
-- ----------------------------
INSERT INTO `grandboss_data` VALUES ('29001', '-21610', '181594', '-5734', '0', '0', '229898.000000000000000', '667.000000000000000', '0');
INSERT INTO `grandboss_data` VALUES ('29006', '17726', '108915', '-6480', '0', '0', '622493.000000000000000', '3793.000000000000000', '0');
INSERT INTO `grandboss_data` VALUES ('29014', '55024', '17368', '-5412', '10126', '0', '622493.000000000000000', '3793.000000000000000', '0');
INSERT INTO `grandboss_data` VALUES ('29020', '115210', '16562', '10078', '61925', '0', '4068372.000000000000000', '39960.000000000000000', '0');
INSERT INTO `grandboss_data` VALUES ('29022', '55312', '219168', '-3223', '0', '0', '858518.360000000000000', '399600.000000000000000', '0');
INSERT INTO `grandboss_data` VALUES ('29028', '213279', '-115672', '-1627', '14518', '0', '62041916.000000000000000', '2248571.000000000000000', '0');
INSERT INTO `grandboss_data` VALUES ('29066', '180443', '114563', '-7623', '36056', '0', '14518000.000000000000000', '3996000.000000000000000', '0');
INSERT INTO `grandboss_data` VALUES ('29067', '178550', '115917', '-7708', '8828', '0', '16184000.000000000000000', '3996000.000000000000000', '0');
INSERT INTO `grandboss_data` VALUES ('29068', '185708', '114298', '-8221', '32768', '0', '62802301.000000000000000', '1998000.000000000000000', '0');
INSERT INTO `grandboss_data` VALUES ('29118', '0', '0', '0', '0', '0', '4109288.000000000000000', '1220547.000000000000000', '0');
