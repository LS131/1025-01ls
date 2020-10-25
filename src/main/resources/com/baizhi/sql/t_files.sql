/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 80003
Source Host           : 127.0.0.1:3306
Source Database       : lx

Target Server Type    : MYSQL
Target Server Version : 80003
File Encoding         : 65001

Date: 2020-06-20 19:11:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_files
-- ----------------------------
DROP TABLE IF EXISTS `t_files`;
CREATE TABLE `t_files` (
  `id` int(11) NOT NULL,
  `oldFileName` varchar(200) DEFAULT NULL,
  `newFileName` varchar(300) DEFAULT NULL,
  `ext` varchar(20) DEFAULT NULL,
  `path` varchar(300) DEFAULT NULL,
  `size` varchar(200) DEFAULT NULL,
  `type` varchar(120) DEFAULT NULL,
  `isImg` varchar(8) DEFAULT NULL,
  `downcounts` int(6) DEFAULT NULL,
  `uploadTime` datetime DEFAULT NULL,
  `userId` int(8) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
