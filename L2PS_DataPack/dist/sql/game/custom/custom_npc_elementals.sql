/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : l2ps

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2013-08-16 14:17:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `custom_npc_elementals`
-- ----------------------------
DROP TABLE IF EXISTS `custom_npc_elementals`;
CREATE TABLE `custom_npc_elementals` (
  `npc_id` mediumint(7) unsigned NOT NULL,
  `elemAtkType` tinyint(1) NOT NULL DEFAULT '-1',
  `elemAtkValue` int(3) NOT NULL DEFAULT '0',
  `fireDefValue` int(3) NOT NULL DEFAULT '0',
  `waterDefValue` int(3) NOT NULL DEFAULT '0',
  `windDefValue` int(3) NOT NULL DEFAULT '0',
  `earthDefValue` int(3) NOT NULL DEFAULT '0',
  `holyDefValue` int(3) NOT NULL DEFAULT '0',
  `darkDefValue` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`npc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of custom_npc_elementals
-- ----------------------------
INSERT INTO `custom_npc_elementals` VALUES ('500', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('501', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('502', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('503', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('504', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('505', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('1605', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('2081', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('2082', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('2083', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('4329', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7000', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7001', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7002', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7003', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7004', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7005', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7006', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7007', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7008', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7009', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7010', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7011', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7012', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7013', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7014', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7015', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7016', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7017', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7018', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7019', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7020', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7021', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7022', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('7023', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('8888', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('9101', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('9102', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('9103', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('9104', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('9105', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('9106', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('9107', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('9108', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('9109', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('9110', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('9998', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('9999', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('10000', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('10001', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('10002', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('50007', '0', '0', '20', '20', '20', '20', '20', '20');
INSERT INTO `custom_npc_elementals` VALUES ('800000', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `custom_npc_elementals` VALUES ('900100', '0', '0', '20', '20', '20', '20', '20', '20');
INSERT INTO `custom_npc_elementals` VALUES ('900101', '0', '0', '20', '20', '20', '20', '20', '20');
INSERT INTO `custom_npc_elementals` VALUES ('900102', '0', '0', '20', '20', '20', '20', '20', '20');
INSERT INTO `custom_npc_elementals` VALUES ('900103', '0', '0', '20', '20', '20', '20', '20', '20');
INSERT INTO `custom_npc_elementals` VALUES ('900104', '0', '0', '20', '20', '20', '20', '20', '20');
