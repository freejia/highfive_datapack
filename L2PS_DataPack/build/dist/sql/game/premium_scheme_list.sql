/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : l2ps

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2013-09-23 12:48:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `premium_scheme_list`
-- ----------------------------
DROP TABLE IF EXISTS `premium_scheme_list`;
CREATE TABLE `premium_scheme_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` varchar(40) DEFAULT NULL,
  `scheme_name` varchar(36) DEFAULT NULL,
  `mod_accepted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=73 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of premium_scheme_list
-- ----------------------------
