/*
Navicat MySQL Data Transfer

Source Server         : SQL_WJ
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : medical_library

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-07-28 14:24:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_organ
-- ----------------------------
DROP TABLE IF EXISTS `t_organ`;
CREATE TABLE `t_organ` (
  `organ_id` int(11) NOT NULL AUTO_INCREMENT,
  `organ_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `organ_own_part` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`organ_id`,`organ_name`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_organ
-- ----------------------------
INSERT INTO `t_organ` VALUES ('1', '肩部', '上肢');
INSERT INTO `t_organ` VALUES ('2', '前臂', '上肢');
INSERT INTO `t_organ` VALUES ('3', '上臂', '上肢');
INSERT INTO `t_organ` VALUES ('4', '手部', '上肢');
INSERT INTO `t_organ` VALUES ('5', '肘部', '上肢');
INSERT INTO `t_organ` VALUES ('6', '肺', '胸部');
INSERT INTO `t_organ` VALUES ('7', '膈肌', '胸部');
INSERT INTO `t_organ` VALUES ('8', '乳房', '胸部');
INSERT INTO `t_organ` VALUES ('9', '食管', '胸部');
INSERT INTO `t_organ` VALUES ('10', '心脏', '胸部');
INSERT INTO `t_organ` VALUES ('11', '纵膈', '胸部');
INSERT INTO `t_organ` VALUES ('12', '甲状腺', '颈部');
INSERT INTO `t_organ` VALUES ('13', '气管', '颈部');
INSERT INTO `t_organ` VALUES ('14', '肛门', '臀部');
INSERT INTO `t_organ` VALUES ('15', '大腿', '下肢');
INSERT INTO `t_organ` VALUES ('16', '膝部', '下肢');
INSERT INTO `t_organ` VALUES ('17', '小腿', '下肢');
INSERT INTO `t_organ` VALUES ('18', '足部', '下肢');
INSERT INTO `t_organ` VALUES ('19', '膀胱', '盆腔');
INSERT INTO `t_organ` VALUES ('20', '尿道', '盆腔');
INSERT INTO `t_organ` VALUES ('21', '肾', '腰部');
INSERT INTO `t_organ` VALUES ('22', '肾上腺', '腰部');
INSERT INTO `t_organ` VALUES ('23', '输尿管', '腰部');
INSERT INTO `t_organ` VALUES ('24', '肠', '腹部');
INSERT INTO `t_organ` VALUES ('25', '肠系膜', '腹部');
INSERT INTO `t_organ` VALUES ('26', '胆', '腹部');
INSERT INTO `t_organ` VALUES ('27', '腹膜', '腹部');
INSERT INTO `t_organ` VALUES ('28', '肝', '腹部');
INSERT INTO `t_organ` VALUES ('29', '阑尾', '腹部');
INSERT INTO `t_organ` VALUES ('30', '脾', '腹部');
INSERT INTO `t_organ` VALUES ('31', '胃', '腹部');
INSERT INTO `t_organ` VALUES ('32', '胰腺', '腹部');
INSERT INTO `t_organ` VALUES ('33', '卵巢', '女性生殖');
INSERT INTO `t_organ` VALUES ('34', '输卵管', '女性生殖');
INSERT INTO `t_organ` VALUES ('35', '外阴', '女性生殖');
INSERT INTO `t_organ` VALUES ('36', '阴道', '女性生殖');
INSERT INTO `t_organ` VALUES ('37', '子宫', '女性生殖');
INSERT INTO `t_organ` VALUES ('38', '骨髓', '骨');
INSERT INTO `t_organ` VALUES ('39', '关节', '骨');
INSERT INTO `t_organ` VALUES ('40', '脊髓', '骨');
INSERT INTO `t_organ` VALUES ('41', '脊柱', '骨');
INSERT INTO `t_organ` VALUES ('42', '肋骨', '骨');
INSERT INTO `t_organ` VALUES ('43', '颅骨', '骨');
INSERT INTO `t_organ` VALUES ('44', '盆骨', '骨');
INSERT INTO `t_organ` VALUES ('45', '其他骨', '骨');
INSERT INTO `t_organ` VALUES ('46', '上肢骨', '骨');
INSERT INTO `t_organ` VALUES ('47', '下肢骨', '骨');
INSERT INTO `t_organ` VALUES ('48', '鼻', '头部');
INSERT INTO `t_organ` VALUES ('49', '耳', '头部');
INSERT INTO `t_organ` VALUES ('50', '口', '头部');
INSERT INTO `t_organ` VALUES ('51', '颅脑', '头部');
INSERT INTO `t_organ` VALUES ('52', '面部', '头部');
INSERT INTO `t_organ` VALUES ('53', '咽喉', '头部');
INSERT INTO `t_organ` VALUES ('54', '眼', '头部');
INSERT INTO `t_organ` VALUES ('55', '睾丸', '男性生殖');
INSERT INTO `t_organ` VALUES ('56', '前列腺', '男性生殖');
INSERT INTO `t_organ` VALUES ('57', '输精管', '男性生殖');
INSERT INTO `t_organ` VALUES ('58', '阴茎', '男性生殖');
INSERT INTO `t_organ` VALUES ('59', '阴囊', '男性生殖');
INSERT INTO `t_organ` VALUES ('60', '肌肉', '全身');
INSERT INTO `t_organ` VALUES ('61', '淋巴', '全身');
INSERT INTO `t_organ` VALUES ('62', '免疫系统', '全身');
INSERT INTO `t_organ` VALUES ('63', '皮肤', '全身');
INSERT INTO `t_organ` VALUES ('64', '血液血管', '全身');
INSERT INTO `t_organ` VALUES ('65', '周围神经系统', '全身');
