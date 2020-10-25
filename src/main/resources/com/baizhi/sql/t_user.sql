/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 80003
Source Host           : 127.0.0.1:3306
Source Database       : lx

Target Server Type    : MYSQL
Target Server Version : 80003
File Encoding         : 65001

Date: 2020-06-20 19:10:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL,
  `username` varchar(80) DEFAULT NULL,
  `password` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
