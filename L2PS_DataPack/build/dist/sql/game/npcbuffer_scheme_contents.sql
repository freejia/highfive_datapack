/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : l2ps

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2013-09-23 12:47:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `npcbuffer_scheme_contents`
-- ----------------------------
DROP TABLE IF EXISTS `npcbuffer_scheme_contents`;
CREATE TABLE `npcbuffer_scheme_contents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scheme_id` int(11) DEFAULT NULL,
  `skill_id` int(8) DEFAULT NULL,
  `skill_level` int(4) DEFAULT NULL,
  `buff_class` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of npcbuffer_scheme_contents
-- ----------------------------
