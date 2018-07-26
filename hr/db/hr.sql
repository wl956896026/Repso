/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50714
Source Host           : 127.0.0.1:3306
Source Database       : hr

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-05-06 14:11:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_dept
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dept`;
CREATE TABLE `tbl_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(100) NOT NULL,
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY `dept_name` (`dept_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbl_dept
-- ----------------------------
INSERT INTO `tbl_dept` VALUES ('4', '人事部');
INSERT INTO `tbl_dept` VALUES ('2', '市场部');
INSERT INTO `tbl_dept` VALUES ('1', '开发部');
INSERT INTO `tbl_dept` VALUES ('3', '行政部');

-- ----------------------------
-- Table structure for tbl_emp
-- ----------------------------
DROP TABLE IF EXISTS `tbl_emp`;
CREATE TABLE `tbl_emp` (
  `emp_id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(30) NOT NULL,
  `login_name` varchar(30) NOT NULL,
  `login_password` varchar(30) NOT NULL,
  `emp_photo` varchar(255) NOT NULL,
  `emp_new_photo` varchar(255) DEFAULT NULL,
  `emp_sex` bit(1) NOT NULL,
  `emp_birthday` date NOT NULL,
  `emp_xueli` varchar(30) NOT NULL,
  `emp_major` varchar(100) NOT NULL,
  `emp_school` varchar(100) NOT NULL,
  `emp_phone` varchar(11) NOT NULL,
  `emp_email` varchar(50) NOT NULL,
  `experience_id` int(11) NOT NULL,
  `Emp_put_time` date NOT NULL,
  `position_id` int(11) NOT NULL,
  `account_state` int(11) NOT NULL,
  `emp_state` int(11) NOT NULL,
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `login_name` (`login_name`),
  KEY `experience_id` (`experience_id`),
  KEY `position_id` (`position_id`),
  CONSTRAINT `tbl_emp_ibfk_1` FOREIGN KEY (`experience_id`) REFERENCES `tbl_experience` (`experience_id`),
  CONSTRAINT `tbl_emp_ibfk_2` FOREIGN KEY (`position_id`) REFERENCES `tbl_position` (`position_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_emp
-- ----------------------------
INSERT INTO `tbl_emp` VALUES ('2', '小强', 'qiangge', '123321', 'timg (5).jpg', null, '', '2018-03-13', '本科', '计算机', '宝鸡大学', '110', 'asdf@qq.com', '1', '2018-03-31', '4', '0', '0');
INSERT INTO `tbl_emp` VALUES ('3', '小花', 'huajie', '123321', 'timg (2).jpg', null, '\0', '2018-02-14', '硕士', '计算机', '宝鸡大学', '120', 'huajie@qq.com', '1', '2018-04-01', '2', '0', '0');
INSERT INTO `tbl_emp` VALUES ('4', '翠花', 'huahua', '123456', 'timg (2).jpg', null, '\0', '1990-10-10', '本科', '计算机', '宝大', '110', '1234543@ewrt', '2', '2018-01-10', '1', '0', '0');
INSERT INTO `tbl_emp` VALUES ('7', '翠花2', 'huahua2', '123456', 'timg (2).jpg', null, '\0', '1990-10-10', '本科', '计算机', '宝大', '110', '1234543@ewrt', '7', '2018-01-10', '1', '0', '0');
INSERT INTO `tbl_emp` VALUES ('8', '小张', 'xiaozhang', '000000', 'timg (5).jpg', null, '', '1990-10-10', '本科', '人力资源管理', '宝大', '112', '1234@asdfkl', '8', '2017-10-10', '4', '0', '0');
INSERT INTO `tbl_emp` VALUES ('13', '小草', 'xiaocao', '000000', 'timg (2).jpg', null, '\0', '1989-10-10', '本科', '人力资源管理', '宝大', '112', '1234@asdfkl', '13', '2017-10-10', '1', '0', '0');
INSERT INTO `tbl_emp` VALUES ('14', 'dfdf', 'sadf', '000000', 'NoteDataConverter.dll', null, '\0', '1989-10-10', '硕士', '人力资源管理', '宝大', '112', '1234@asdfkl', '14', '2017-10-10', '4', '0', '0');
INSERT INTO `tbl_emp` VALUES ('15', 'asdf', 'asdf', '000000', 'qrcode_for_gh_6c2f757015b4_344.jpg', null, '', '1989-10-10', '本科', 'asdf', 'asdf', 'asdf', 'asdf', '15', '2017-10-10', '1', '0', '0');
INSERT INTO `tbl_emp` VALUES ('17', '老宋', 'laosong', '000000', 'IMG_2435.bmp', 'a8db137a-ea4e-4b9d-9a52-d36f997d7271.bmp', '', '1989-10-10', '本科', '人力资源管理', '宝大', '112', '1234@asdfkl', '17', '2017-10-10', '4', '0', '0');

-- ----------------------------
-- Table structure for tbl_experience
-- ----------------------------
DROP TABLE IF EXISTS `tbl_experience`;
CREATE TABLE `tbl_experience` (
  `experience_id` int(11) NOT NULL AUTO_INCREMENT,
  `start_time` date NOT NULL,
  `end_time` date NOT NULL,
  `company` varchar(100) NOT NULL,
  `position` varchar(100) NOT NULL,
  `job_content` text NOT NULL,
  PRIMARY KEY (`experience_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_experience
-- ----------------------------
INSERT INTO `tbl_experience` VALUES ('1', '2018-03-17', '2018-03-20', '大山企业', '开发', '开发');
INSERT INTO `tbl_experience` VALUES ('2', '2017-10-10', '2018-01-09', 'xxx', '开发', '开发开发');
INSERT INTO `tbl_experience` VALUES ('4', '2017-10-10', '2018-01-09', 'xxx', '开发', '开发开发11111');
INSERT INTO `tbl_experience` VALUES ('7', '2017-10-10', '2018-01-09', 'xxx', '开发', '开发开发11111');
INSERT INTO `tbl_experience` VALUES ('8', '2016-10-10', '2017-09-09', '阿斯蒂芬', 'asdf', 'sadf');
INSERT INTO `tbl_experience` VALUES ('9', '2016-10-10', '2017-09-09', 'é¿æ¯èè¬', 'asdf', 'dsfdsf');
INSERT INTO `tbl_experience` VALUES ('12', '2016-10-10', '2017-09-09', 'é¿æ¯èè¬', 'asdf', 'sdf');
INSERT INTO `tbl_experience` VALUES ('13', '2016-10-10', '2017-09-09', '阿斯蒂芬', 'asdf', 'sadf');
INSERT INTO `tbl_experience` VALUES ('14', '2016-10-10', '2017-09-09', '阿斯蒂芬', 'asdf', 'dfdf');
INSERT INTO `tbl_experience` VALUES ('15', '2016-10-10', '2017-09-09', 'asdf', 'adsf', 'asdf');
INSERT INTO `tbl_experience` VALUES ('17', '2016-10-10', '2017-09-09', '阿斯蒂芬', 'asdf', '是的是的');

-- ----------------------------
-- Table structure for tbl_position
-- ----------------------------
DROP TABLE IF EXISTS `tbl_position`;
CREATE TABLE `tbl_position` (
  `position_id` int(11) NOT NULL AUTO_INCREMENT,
  `position_name` varchar(50) NOT NULL,
  `position_price` decimal(10,2) NOT NULL,
  `position_desc` text NOT NULL,
  `dept_id` int(11) NOT NULL,
  PRIMARY KEY (`position_id`),
  KEY `dept_id` (`dept_id`),
  CONSTRAINT `tbl_position_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `tbl_dept` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_position
-- ----------------------------
INSERT INTO `tbl_position` VALUES ('1', '初级开发', '5000.00', '工作经验在1-3年', '1');
INSERT INTO `tbl_position` VALUES ('2', '中级开发', '12000.00', '工作经验在3-5年', '1');
INSERT INTO `tbl_position` VALUES ('3', '高级开发', '15000.00', '工作经验在5年以上', '1');
INSERT INTO `tbl_position` VALUES ('4', '人事专员', '3000.00', '招聘，人事档案管理', '4');

-- ----------------------------
-- Table structure for tbl_quit
-- ----------------------------
DROP TABLE IF EXISTS `tbl_quit`;
CREATE TABLE `tbl_quit` (
  `quit_id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL,
  `quit_type` int(11) NOT NULL,
  `quit_time` date NOT NULL,
  `quit_reason` text NOT NULL,
  PRIMARY KEY (`quit_id`),
  KEY `emp_id` (`emp_id`),
  CONSTRAINT `tbl_quit_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `tbl_emp` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_quit
-- ----------------------------
