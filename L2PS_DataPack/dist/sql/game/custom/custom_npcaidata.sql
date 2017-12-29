/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : l2ps

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2013-09-23 14:11:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `custom_npcaidata`
-- ----------------------------
DROP TABLE IF EXISTS `custom_npcaidata`;
CREATE TABLE `custom_npcaidata` (
  `npcId` mediumint(7) unsigned NOT NULL,
  `minSkillChance` tinyint(3) unsigned NOT NULL DEFAULT '7',
  `maxSkillChance` tinyint(3) unsigned NOT NULL DEFAULT '15',
  `primarySkillId` smallint(5) unsigned DEFAULT '0',
  `agroRange` smallint(4) unsigned NOT NULL DEFAULT '0',
  `canMove` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `targetable` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `showName` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `minRangeSkill` smallint(5) unsigned DEFAULT '0',
  `minRangeChance` tinyint(3) unsigned DEFAULT '0',
  `maxRangeSkill` smallint(5) unsigned DEFAULT '0',
  `maxRangeChance` tinyint(3) unsigned DEFAULT '0',
  `soulShot` smallint(4) unsigned DEFAULT '0',
  `spiritShot` smallint(4) unsigned DEFAULT '0',
  `spsChance` tinyint(3) unsigned DEFAULT '0',
  `ssChance` tinyint(3) unsigned DEFAULT '0',
  `aggro` smallint(4) unsigned NOT NULL DEFAULT '0',
  `isChaos` smallint(4) unsigned DEFAULT '0',
  `clan` varchar(40) DEFAULT NULL,
  `clanRange` smallint(4) unsigned DEFAULT '0',
  `enemyClan` varchar(40) DEFAULT NULL,
  `enemyRange` smallint(4) unsigned DEFAULT '0',
  `dodge` tinyint(3) unsigned DEFAULT '0',
  `aiType` varchar(8) NOT NULL DEFAULT 'fighter',
  PRIMARY KEY (`npcId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of custom_npcaidata
-- ----------------------------
INSERT INTO `custom_npcaidata` VALUES ('500', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('501', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('502', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('503', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('504', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('505', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7000', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7001', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7002', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7003', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7004', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7005', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7006', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7007', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7008', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7009', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7010', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7011', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7012', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7013', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7014', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7015', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7016', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7017', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7018', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7019', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7020', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7021', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7022', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('7023', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('8888', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('9998', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('9999', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('10000', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('10002', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('50007', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('800000', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('900100', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('900101', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('900102', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('900103', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('900104', '7', '15', '0', '1000', '0', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '300', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('65535', '7', '15', '0', '0', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '0', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('1605', '7', '15', '0', '0', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '0', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('2081', '7', '15', '0', '0', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '0', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('2082', '7', '15', '0', '0', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '0', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('2083', '7', '15', '0', '0', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '0', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('4329', '7', '15', '0', '0', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '0', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('10001', '7', '15', '0', '0', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '0', null, '0', '0', 'fighter');
INSERT INTO `custom_npcaidata` VALUES ('65534', '7', '15', '0', '0', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '0', null, '0', '0', 'fighter');
