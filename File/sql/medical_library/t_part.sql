/*
Navicat MySQL Data Transfer

Source Server         : SQL_WJ
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : medical_library

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-07-28 14:24:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_part
-- ----------------------------
DROP TABLE IF EXISTS `t_part`;
CREATE TABLE `t_part` (
  `part_id` int(11) NOT NULL AUTO_INCREMENT,
  `part_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `part_contained_organs` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`part_id`,`part_name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_part
-- ----------------------------
INSERT INTO `t_part` VALUES ('1', '其他', '');
INSERT INTO `t_part` VALUES ('2', '心理', '');
INSERT INTO `t_part` VALUES ('3', '上肢', '肩部，前臂，上臂，手部，肘部');
INSERT INTO `t_part` VALUES ('4', '胸部', '肺，膈肌，乳房，食管，心脏，纵膈');
INSERT INTO `t_part` VALUES ('5', '颈部', '甲状腺，气管');
INSERT INTO `t_part` VALUES ('6', '臀部', '肛门');
INSERT INTO `t_part` VALUES ('7', '下肢', '大腿，膝部，小腿，足部');
INSERT INTO `t_part` VALUES ('8', '盆腔', '膀胱，尿道');
INSERT INTO `t_part` VALUES ('9', '腰部', '肾，肾上腺，输尿管');
INSERT INTO `t_part` VALUES ('10', '腹部', '肠，肠系膜，胆，腹膜，肝，阑尾，脾，胃，胰腺');
INSERT INTO `t_part` VALUES ('11', '女性生殖', '卵巢，输卵管，外阴，阴道，子宫');
INSERT INTO `t_part` VALUES ('12', '骨', '骨髓，关节，脊髓，脊柱，肋骨，颅骨，盆骨，其他骨，上肢骨，下肢骨');
INSERT INTO `t_part` VALUES ('13', '背部', '');
INSERT INTO `t_part` VALUES ('14', '头部', '鼻，耳，口，颅脑，面部，咽喉，眼');
INSERT INTO `t_part` VALUES ('15', '会阴部', '');
INSERT INTO `t_part` VALUES ('16', '男性生殖', '睾丸，前列腺，输精管，阴茎，阴囊');
INSERT INTO `t_part` VALUES ('17', '全身', '肌肉，淋巴，免疫系统，皮肤，血液血管，周围神经系统');
