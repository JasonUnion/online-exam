/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : auth

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2017-04-21 12:05:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `authority_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '权限名称',
  PRIMARY KEY (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('1', 'ROLE:TEACHER', '老师');
INSERT INTO `authority` VALUES ('2', 'ROLE:ADMIN', '管理员');
INSERT INTO `authority` VALUES ('3', 'ROLE:STUDENT', '学生');

-- ----------------------------
-- Table structure for classname
-- ----------------------------
DROP TABLE IF EXISTS `classname`;
CREATE TABLE `classname` (
  `id` varchar(255) NOT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classname
-- ----------------------------
INSERT INTO `classname` VALUES ('0488bc9eff3b4e87a7f2f8a4bf35451a', '3班');
INSERT INTO `classname` VALUES ('1', '1班');
INSERT INTO `classname` VALUES ('2', '2班');
INSERT INTO `classname` VALUES ('4', '4班');
INSERT INTO `classname` VALUES ('a24308d588ff439d970d877efc0ffd87', '5班');
INSERT INTO `classname` VALUES ('af54cce4bd9449809bb639c37e37eaa9', '6班');

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `created` varchar(255) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contact
-- ----------------------------
INSERT INTO `contact` VALUES ('1', '发射导弹发射', '阿飞啊', '啊发射点发生', null, 'false');

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `id` varchar(255) NOT NULL,
  `grade_name` varchar(255) DEFAULT NULL,
  `sort` int(20) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('02', '二年级', '16');
INSERT INTO `grade` VALUES ('03', '三年级', '14');
INSERT INTO `grade` VALUES ('04', '四年级', '13');
INSERT INTO `grade` VALUES ('06', '五年级', '12');
INSERT INTO `grade` VALUES ('07', '六年级', '11');
INSERT INTO `grade` VALUES ('08', '初一', '10');
INSERT INTO `grade` VALUES ('09', '初二', '9');
INSERT INTO `grade` VALUES ('10', '初三', '8');
INSERT INTO `grade` VALUES ('11', '高一', '7');
INSERT INTO `grade` VALUES ('12', '高二', '6');
INSERT INTO `grade` VALUES ('13', '高三', '5');
INSERT INTO `grade` VALUES ('14', '大一', '4');
INSERT INTO `grade` VALUES ('15', '大二', '3');
INSERT INTO `grade` VALUES ('16', '大三', '2');
INSERT INTO `grade` VALUES ('17', '大四', '1');
INSERT INTO `grade` VALUES ('1f06d016d9964a63bfa1468ade3b8629', '测试班级1', '55');

-- ----------------------------
-- Table structure for grade_classname
-- ----------------------------
DROP TABLE IF EXISTS `grade_classname`;
CREATE TABLE `grade_classname` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grade_id` varchar(255) DEFAULT NULL,
  `classname_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade_classname
-- ----------------------------
INSERT INTO `grade_classname` VALUES ('21', '03', '1');
INSERT INTO `grade_classname` VALUES ('22', '03', '2');
INSERT INTO `grade_classname` VALUES ('28', '1f06d016d9964a63bfa1468ade3b8629', '1');

-- ----------------------------
-- Table structure for grade_classname_student
-- ----------------------------
DROP TABLE IF EXISTS `grade_classname_student`;
CREATE TABLE `grade_classname_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grade_id` varchar(255) DEFAULT NULL,
  `classname_id` varchar(255) DEFAULT NULL COMMENT '班级名称',
  `student_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade_classname_student
-- ----------------------------
INSERT INTO `grade_classname_student` VALUES ('6', '1f06d016d9964a63bfa1468ade3b8629', '1', 'ccd4651027364eb19a51601e27074de7');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` varchar(255) NOT NULL,
  `exam_number` varchar(20) DEFAULT NULL,
  `student_name` varchar(255) DEFAULT NULL,
  `birthday` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `class_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('ccd4651027364eb19a51601e27074de7', '1212', 'admin', '2017/04/07', '1633736729@qq.com', '男', '1班');

-- ----------------------------
-- Table structure for student_classname
-- ----------------------------
DROP TABLE IF EXISTS `student_classname`;
CREATE TABLE `student_classname` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(255) DEFAULT NULL,
  `classname_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_classname
-- ----------------------------
INSERT INTO `student_classname` VALUES ('11', 'ccd4651027364eb19a51601e27074de7', '1');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` varchar(255) NOT NULL,
  `teacher_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('ccd4651027364eb19a51601e27074de7', 'admin');
INSERT INTO `teacher` VALUES ('ead10c77ad1242c3bb5f1f2e43aa6712', 'test');

-- ----------------------------
-- Table structure for teacher_grade_classname
-- ----------------------------
DROP TABLE IF EXISTS `teacher_grade_classname`;
CREATE TABLE `teacher_grade_classname` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `teacher_id` bigint(20) DEFAULT NULL,
  `grade_id` bigint(20) DEFAULT NULL,
  `classname_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_grade_classname
-- ----------------------------

-- ----------------------------
-- Table structure for teacher_student
-- ----------------------------
DROP TABLE IF EXISTS `teacher_student`;
CREATE TABLE `teacher_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `teacher_id` varchar(255) DEFAULT NULL,
  `student_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_student
-- ----------------------------
INSERT INTO `teacher_student` VALUES ('11', 'ead10c77ad1242c3bb5f1f2e43aa6712', 'ccd4651027364eb19a51601e27074de7');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `firstname` varchar(20) DEFAULT NULL,
  `lastname` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `class_name` varchar(20) DEFAULT NULL,
  `exam_number` varchar(20) DEFAULT NULL,
  `enabled` varchar(20) DEFAULT NULL,
  `last_password_reset_date` datetime DEFAULT NULL,
  `avatar` varchar(50) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `mobile_phone` varchar(20) DEFAULT NULL,
  `identity_card` varchar(255) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `birthday` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('615cba98c83e42a686799e0c0ad991dd', 'test2', '$2a$04$PXhcRRzr7I5FPuHbBxHRK.5nEg7FgD.EVDT4gyfb/2SMrU8yr0QEe', null, null, 'eee@ww.com', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('ccd4651027364eb19a51601e27074de7', 'admin', '$2a$04$bUPHC74g294SB9swwvs8Z.b2719hAskZPdu5ZaFnpD6pHb/n3B/OO', null, null, '1633736729@qq.com', '男', null, null, null, null, 'http://localhost:8871/static/img/admin.png', '发大发', '1633736729@qq.com', '44234234', '分区日2017-4-20人', '2017/04/07');
INSERT INTO `user` VALUES ('ead10c77ad1242c3bb5f1f2e43aa6712', 'test', '$2a$04$VNe/.h1qBw/UTtA63HFrk.g2oPV6HSJOIEIwrJcxv7VcgJT62mD7W', null, null, 'test@test.com', '女', null, null, null, null, null, '法师打发', '答复', 'ad手动阀', '阿发', '2017/03/30');

-- ----------------------------
-- Table structure for user_authority
-- ----------------------------
DROP TABLE IF EXISTS `user_authority`;
CREATE TABLE `user_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `authority_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_authority
-- ----------------------------
INSERT INTO `user_authority` VALUES ('10', 'ead10c77ad1242c3bb5f1f2e43aa6712', '3');
INSERT INTO `user_authority` VALUES ('11', 'ccd4651027364eb19a51601e27074de7', '3');
INSERT INTO `user_authority` VALUES ('12', 'ccd4651027364eb19a51601e27074de7', '1');
INSERT INTO `user_authority` VALUES ('13', 'ccd4651027364eb19a51601e27074de7', '2');
INSERT INTO `user_authority` VALUES ('14', 'ead10c77ad1242c3bb5f1f2e43aa6712', '1');
INSERT INTO `user_authority` VALUES ('15', '615cba98c83e42a686799e0c0ad991dd', '3');
