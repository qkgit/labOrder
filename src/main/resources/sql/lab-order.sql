/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 127.0.0.1:3306
 Source Schema         : lab-order

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 03/05/2021 17:37:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lab_exp
-- ----------------------------
DROP TABLE IF EXISTS `lab_exp`;
CREATE TABLE `lab_exp`  (
  `exp_id` int(10) NOT NULL AUTO_INCREMENT,
  `exp_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验名称',
  `exp_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验类型',
  `create_date` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`exp_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lab_exp
-- ----------------------------
INSERT INTO `lab_exp` VALUES (2, '实验B', '1', '2021-02-28 14:01:33', '0');
INSERT INTO `lab_exp` VALUES (3, '实验A', '2', '2021-02-28 14:01:34', '0');
INSERT INTO `lab_exp` VALUES (4, '测试实验', '2', '2021-02-28 14:45:24', '0');
INSERT INTO `lab_exp` VALUES (8, '测试添加实验A', '3', '2021-02-28 16:00:47', '0');
INSERT INTO `lab_exp` VALUES (6, '实验C', '1', '2021-02-28 14:43:12', '0');
INSERT INTO `lab_exp` VALUES (7, '测试添加实验室', '1', '2021-04-18 17:49:35', '0');
INSERT INTO `lab_exp` VALUES (9, '测试修改实验2', '4', '2021-04-03 14:29:20', '0');
INSERT INTO `lab_exp` VALUES (11, '测试添加实验B', '2', '2021-04-03 14:31:40', '0');
INSERT INTO `lab_exp` VALUES (12, '添加测试', '4', '2021-04-03 14:39:03', '0');

-- ----------------------------
-- Table structure for lab_orders
-- ----------------------------
DROP TABLE IF EXISTS `lab_orders`;
CREATE TABLE `lab_orders`  (
  `lo_id` int(10) NOT NULL AUTO_INCREMENT,
  `lo_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预约实验室的类型 1-开放实验室 2-教学实验室',
  `l_id` int(10) NOT NULL COMMENT '预约实验室id',
  `exp_id` int(10) NULL DEFAULT NULL COMMENT '实验Id',
  `exp_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预约-实验名称',
  `l_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验室名称',
  `l_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验室地址',
  `lo_cap` int(50) NOT NULL COMMENT '容量',
  `lo_odd` int(50) NULL DEFAULT NULL COMMENT '实验室剩余容量',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '更改时间',
  `is_delete` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`lo_id`) USING BTREE,
  INDEX `l_id`(`l_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lab_orders
-- ----------------------------
INSERT INTO `lab_orders` VALUES (1, '1', 1, 3, '实验A', '近代物理实验室', '基础实验楼315-1', 50, 2, '2021-03-07 16:56:33', '2021-03-08 16:56:40', NULL, '0');
INSERT INTO `lab_orders` VALUES (2, '1', 1, 3, '实验A', '近代物理实验室', '基础实验楼315-1', 50, 0, '2021-03-04 16:57:09', '2021-03-05 16:57:13', NULL, '0');
INSERT INTO `lab_orders` VALUES (3, '1', 1, 3, '实验A', '近代物理实验室', '基础实验楼315-1', 50, 25, '2021-03-07 16:56:33', '2021-03-08 16:56:40', NULL, '0');
INSERT INTO `lab_orders` VALUES (4, '1', 1, 3, '实验A', '近代物理实验室', '基础实验楼315-1', 50, 25, '2021-03-06 23:59:56', '2021-03-06 23:59:56', NULL, '0');
INSERT INTO `lab_orders` VALUES (5, '1', 1, 3, '实验A', '近代物理实验室', '基础实验楼315-1', 50, 25, '2021-03-07 00:26:52', '2021-03-07 00:26:52', NULL, '0');
INSERT INTO `lab_orders` VALUES (6, '2', 2, 3, '实验A', '近代化学实验室', '314', 50, 20, '2021-03-30 19:38:12', '2021-04-02 19:38:19', NULL, '0');
INSERT INTO `lab_orders` VALUES (7, '1', 33, 3, '实验A', 'cs', 'f332', 30, 30, '2021-04-05 00:00:00', '2021-04-06 00:00:00', '2021-04-05 14:37:46', '0');
INSERT INTO `lab_orders` VALUES (8, '2', 15, 3, '实验A', '测试修改实验室2', 'D-336', 50, 50, '2021-04-05 00:00:00', '2021-04-05 17:00:00', '2021-04-05 10:19:03', '0');
INSERT INTO `lab_orders` VALUES (10, '1', 3, 2, '实验B', '生物实验室', '225', 50, 50, '2021-04-05 00:00:00', '2021-04-06 00:00:00', '2021-04-05 11:21:38', '0');
INSERT INTO `lab_orders` VALUES (11, '1', 3, 2, '实验B', '生物实验室', '225', 50, 50, '2021-04-05 00:00:00', '2021-04-06 00:00:00', '2021-04-05 11:31:26', '0');
INSERT INTO `lab_orders` VALUES (12, '1', 2, 3, '测试添加实验B', '近代化学实验室', '314', 50, 50, '2021-04-05 00:00:00', '2021-04-06 00:00:00', '2021-04-05 11:34:57', '0');
INSERT INTO `lab_orders` VALUES (18, '1', 1, 2, '实验B', '近代物理实验室', '基础实验楼315-1', 50, 47, '2021-04-07 00:00:00', '2021-04-08 00:00:00', '2021-04-05 20:32:51', '0');
INSERT INTO `lab_orders` VALUES (17, '2', 1, 2, '实验B', '近代物理实验室', '基础实验楼315-1', 50, 0, '2021-04-06 00:00:00', '2021-04-07 00:00:00', '2021-04-05 16:14:24', '0');
INSERT INTO `lab_orders` VALUES (16, '2', 33, 3, '实验A', 'cs', 'f332', 30, 0, '2021-04-05 00:00:00', '2021-04-08 00:00:00', '2021-04-05 20:01:33', '0');
INSERT INTO `lab_orders` VALUES (19, '1', 1, 6, '实验C', '近代物理实验室', '基础实验楼315-1', 50, 50, '2021-04-09 00:00:00', '2021-04-10 00:00:00', '2021-04-06 13:59:08', '0');
INSERT INTO `lab_orders` VALUES (20, '1', 3, 2, '实验B', '生物实验室', '225', 50, 50, '2021-04-07 00:00:00', '2021-04-08 00:00:00', '2021-04-06 14:00:50', '0');
INSERT INTO `lab_orders` VALUES (21, '1', 37, 6, '实验C', '测试实验室', 'c-201', 40, 40, '2021-04-06 00:00:00', '2021-04-07 00:00:00', '2021-04-06 14:14:08', '0');
INSERT INTO `lab_orders` VALUES (22, '1', 15, 2, '实验B', '测试修改实验室2', 'D-336', 50, 50, '2021-04-06 00:00:00', '2021-04-07 00:00:00', '2021-04-06 18:18:05', '0');
INSERT INTO `lab_orders` VALUES (23, '1', 1, 2, '实验B', '近代物理实验室', '基础实验楼315-1', 50, 50, '2021-04-09 08:00:00', '2021-04-09 12:00:00', '2021-04-08 21:48:09', '0');
INSERT INTO `lab_orders` VALUES (24, '1', 3, 6, '实验C', '生物实验室', '225', 50, 49, '2021-04-16 00:00:00', '2021-04-17 00:00:00', '2021-04-15 19:52:03', '0');
INSERT INTO `lab_orders` VALUES (25, '1', 13, 6, '实验C', '更改测试实验室', 'A-557', 50, 48, '2021-04-19 12:00:00', '2021-04-20 16:00:00', '2021-04-15 21:30:03', '0');
INSERT INTO `lab_orders` VALUES (26, '1', 40, 2, '实验B', '本科生创新实验室', 'G-235', 40, 36, '2021-04-26 00:00:00', '2021-04-27 00:00:00', '2021-04-17 18:31:45', '0');
INSERT INTO `lab_orders` VALUES (27, '1', 1, 2, '实验B', '近代物理实验室', '基础实验楼315-1', 50, 48, '2021-04-21 00:00:00', '2021-04-22 00:00:00', '2021-04-18 17:44:48', '0');
INSERT INTO `lab_orders` VALUES (28, '2', 40, 2, '实验B', '本科生创新实验室', 'G-235', 40, 40, '2021-04-20 00:00:00', '2021-04-21 00:00:00', '2021-04-18 17:53:25', '0');
INSERT INTO `lab_orders` VALUES (29, '1', 1, 2, '实验B', '近代物理实验室', '基础实验楼315-1', 50, 48, '2021-04-26 08:00:00', '2021-04-26 12:00:00', '2021-04-19 13:42:38', '0');
INSERT INTO `lab_orders` VALUES (44, '1', 43, 6, '实验C', '雷达信号分析与处理实验室', 'F-123', 30, 30, '2021-04-28 19:00:00', '2021-04-28 21:00:00', '2021-04-26 14:16:09', '0');
INSERT INTO `lab_orders` VALUES (43, '2', 21, 6, '实验C', '测试添加实验室', 'e-964', 60, 60, '2021-04-26 19:00:00', '2021-04-26 21:00:00', '2021-04-26 14:15:12', '0');
INSERT INTO `lab_orders` VALUES (42, '2', 19, 8, '测试添加实验A', '测试实验室3', 'D-331', 50, 50, '2021-04-26 08:00:00', '2021-04-26 10:00:00', '2021-04-26 14:14:46', '0');
INSERT INTO `lab_orders` VALUES (41, '1', 13, 6, '实验C', '更改测试实验室', 'A-557', 50, 50, '2021-04-27 12:00:00', '2021-04-28 11:00:00', '2021-04-26 14:14:18', '0');
INSERT INTO `lab_orders` VALUES (40, '1', 2, 8, '测试添加实验A', '计算机实验室', 'D-224', 50, 50, '2021-04-26 08:08:00', '2021-04-27 08:00:00', '2021-04-26 14:13:38', '0');
INSERT INTO `lab_orders` VALUES (38, '1', 43, 2, '实验B', '雷达信号分析与处理实验室', 'F-123', 30, 30, '2021-04-30 08:00:00', '2021-04-30 11:00:00', '2021-04-26 14:04:03', '0');
INSERT INTO `lab_orders` VALUES (37, '2', 43, 2, '实验B', '雷达信号分析与处理实验室', 'F-123', 30, 0, '2021-04-28 08:00:00', '2021-04-28 18:00:00', '2021-04-20 10:45:43', '0');
INSERT INTO `lab_orders` VALUES (45, '1', 45, 9, '测试修改实验2', '添加实验室测试', 'c-335', 50, 50, '2021-04-29 13:00:00', '2021-04-29 15:00:00', '2021-04-26 14:16:31', '0');
INSERT INTO `lab_orders` VALUES (46, '1', 42, 12, '添加测试', '电子信息实验室', 'B-119', 50, 50, '2021-05-04 06:00:00', '2021-05-04 08:00:00', '2021-04-26 14:16:56', '0');
INSERT INTO `lab_orders` VALUES (47, '1', 40, 7, '测试添加实验室', '本科生创新实验室', 'G-235', 40, 39, '2021-05-26 09:00:00', '2021-05-26 11:00:00', '2021-04-26 14:17:19', '0');

-- ----------------------------
-- Table structure for lab_orders_detail
-- ----------------------------
DROP TABLE IF EXISTS `lab_orders_detail`;
CREATE TABLE `lab_orders_detail`  (
  `lod_id` int(11) NOT NULL AUTO_INCREMENT,
  `lo_id` int(11) NOT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `order_time` datetime(0) NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`lod_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of lab_orders_detail
-- ----------------------------
INSERT INTO `lab_orders_detail` VALUES (14, 18, 20, '2021-04-06 16:52:04', NULL);
INSERT INTO `lab_orders_detail` VALUES (13, 17, 20, '2021-04-07 14:52:13', NULL);
INSERT INTO `lab_orders_detail` VALUES (9, 18, 1, '2021-04-06 15:52:18', NULL);
INSERT INTO `lab_orders_detail` VALUES (7, 16, 1, '2021-04-07 14:52:27', NULL);
INSERT INTO `lab_orders_detail` VALUES (15, 18, 21, '2021-04-07 12:52:34', NULL);
INSERT INTO `lab_orders_detail` VALUES (24, 25, 1, '2021-04-15 21:30:57', NULL);
INSERT INTO `lab_orders_detail` VALUES (23, 24, 1, '2021-04-15 19:52:13', NULL);
INSERT INTO `lab_orders_detail` VALUES (25, 25, 21, '2021-04-17 23:25:26', NULL);
INSERT INTO `lab_orders_detail` VALUES (26, 29, 4, '2021-04-20 11:01:07', NULL);
INSERT INTO `lab_orders_detail` VALUES (32, 26, 5, '2021-04-20 14:03:45', '2021-04-26 00:00:00');
INSERT INTO `lab_orders_detail` VALUES (28, 27, 4, '2021-04-20 11:01:12', NULL);
INSERT INTO `lab_orders_detail` VALUES (33, 26, 4, '2021-04-20 14:06:24', '2021-04-26 00:00:00');
INSERT INTO `lab_orders_detail` VALUES (34, 29, 1, '2021-04-20 14:14:03', '2021-04-26 08:00:00');
INSERT INTO `lab_orders_detail` VALUES (31, 26, 6, '2021-04-20 13:42:39', '2021-04-26 00:00:00');
INSERT INTO `lab_orders_detail` VALUES (35, 27, 4, '2021-04-20 16:38:37', '2021-04-21 00:00:00');
INSERT INTO `lab_orders_detail` VALUES (36, 26, 21, '2021-04-22 18:11:26', '2021-04-26 00:00:00');
INSERT INTO `lab_orders_detail` VALUES (37, 37, 1, '2021-04-23 14:36:55', '2021-04-28 08:00:00');
INSERT INTO `lab_orders_detail` VALUES (38, 47, 21, '2021-04-26 14:24:06', '2021-05-26 09:00:00');

-- ----------------------------
-- Table structure for lab_top
-- ----------------------------
DROP TABLE IF EXISTS `lab_top`;
CREATE TABLE `lab_top`  (
  `lt_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '实验室排行表id 主键',
  `l_id` int(20) NULL DEFAULT NULL COMMENT '实验室id',
  `newest_date` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最新开放时间',
  `all_time` int(50) NULL DEFAULT NULL,
  PRIMARY KEY (`lt_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lab_top
-- ----------------------------
INSERT INTO `lab_top` VALUES (1, 1, '2021-04-19', 5);
INSERT INTO `lab_top` VALUES (2, 3, '2021-04-15', 2);
INSERT INTO `lab_top` VALUES (3, 37, '2021-04-06', 1);
INSERT INTO `lab_top` VALUES (4, 15, '2021-04-06', 1);
INSERT INTO `lab_top` VALUES (5, 13, '2021-04-26', 2);
INSERT INTO `lab_top` VALUES (6, 40, '2021-04-26', 338);
INSERT INTO `lab_top` VALUES (7, 41, '2021-4-17', 302);
INSERT INTO `lab_top` VALUES (8, 42, '2021-04-26', 277);
INSERT INTO `lab_top` VALUES (9, 43, '2021-04-26', 256);
INSERT INTO `lab_top` VALUES (10, 44, '2021-4-17', 204);
INSERT INTO `lab_top` VALUES (11, 2, '2021-04-26', 1);
INSERT INTO `lab_top` VALUES (12, 19, '2021-04-26', 1);
INSERT INTO `lab_top` VALUES (13, 21, '2021-04-26', 1);
INSERT INTO `lab_top` VALUES (14, 45, '2021-04-26', 1);

-- ----------------------------
-- Table structure for labs
-- ----------------------------
DROP TABLE IF EXISTS `labs`;
CREATE TABLE `labs`  (
  `l_id` int(10) NOT NULL AUTO_INCREMENT,
  `l_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验室名称',
  `l_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `l_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验室类型 1-物理 2-化学 3-生物 等',
  `l_cap` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '容量',
  `create_date` timestamp(0) NULL DEFAULT NULL COMMENT '更改时间',
  `is_delete` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`l_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of labs
-- ----------------------------
INSERT INTO `labs` VALUES (1, '近代物理实验室', '基础实验楼315-1', '1', '50', '2021-02-04 12:47:52', '0');
INSERT INTO `labs` VALUES (2, '计算机实验室', 'D-224', '3', '50', '2021-04-05 14:05:54', '0');
INSERT INTO `labs` VALUES (3, '生物实验室', '225', '1', '50', '2021-02-04 12:48:00', '0');
INSERT INTO `labs` VALUES (7, '4545', 'A-557', '1', '50', '2021-02-04 12:48:02', '1');
INSERT INTO `labs` VALUES (8, '4545', 'A-557', '1', '50', '2021-02-04 12:48:03', '1');
INSERT INTO `labs` VALUES (13, '更改测试实验室', 'A-557', '1', '50', '2021-02-04 12:55:39', '0');
INSERT INTO `labs` VALUES (15, '测试修改实验室2', 'D-336', '2', '50', '2021-02-04 13:14:49', '0');
INSERT INTO `labs` VALUES (9, '测试实验室2', 'D-331', '1', '50', '2021-02-04 13:15:58', '1');
INSERT INTO `labs` VALUES (17, '测试修改实验室3', 'D-336', '2', '50', '2021-02-04 13:22:46', '1');
INSERT INTO `labs` VALUES (19, '测试实验室3', 'D-331', '3', '50', '2021-02-04 13:25:58', '0');
INSERT INTO `labs` VALUES (20, '1', '1', '1', '1', '2021-04-02 17:18:59', '1');
INSERT INTO `labs` VALUES (21, '测试添加实验室', 'e-964', '1', '60', '2021-04-02 17:22:48', '0');
INSERT INTO `labs` VALUES (22, 'c测试', 'x-662', '2', '20', '2021-04-02 17:23:32', '0');
INSERT INTO `labs` VALUES (23, '222', '2', '22', '2', '2021-04-02 17:24:40', '1');
INSERT INTO `labs` VALUES (24, '1', '1', '1', '1', '2021-04-02 17:29:43', '1');
INSERT INTO `labs` VALUES (25, '1', '1', '1', '1', '2021-04-02 17:31:20', '1');
INSERT INTO `labs` VALUES (26, '测试', 'd332', '2', '40', '2021-04-02 17:32:46', '1');
INSERT INTO `labs` VALUES (27, '测试', 'd332', '2', '40', '2021-04-02 17:32:49', '0');
INSERT INTO `labs` VALUES (28, '1', '1', '1', '1', '2021-04-02 17:34:46', '1');
INSERT INTO `labs` VALUES (29, '2', '2', '2', '2', '2021-04-02 17:35:14', '1');
INSERT INTO `labs` VALUES (30, '2', '1', '2', '1', '2021-04-02 17:35:50', '1');
INSERT INTO `labs` VALUES (31, '1', 'dd3', '2', '5', '2021-04-02 17:36:47', '1');
INSERT INTO `labs` VALUES (32, 'ccd', 'rrr24', '2', '50', '2021-04-02 17:37:21', '0');
INSERT INTO `labs` VALUES (33, 'cs', 'f332', '2', '30', '2021-04-02 17:38:24', '0');
INSERT INTO `labs` VALUES (34, '1', '4', '2', '60', '2021-04-02 17:40:31', '1');
INSERT INTO `labs` VALUES (35, '22', 'b-336', '2', '40', '2021-04-02 17:41:33', '0');
INSERT INTO `labs` VALUES (36, '测试化学实验室', 'a-996', '2', '50', '2021-04-18 17:48:09', '0');
INSERT INTO `labs` VALUES (37, '测试实验室', 'c-201', '1', '40', '2021-04-02 17:54:18', '0');
INSERT INTO `labs` VALUES (38, '测试添加计算机实验室', 'j-205', '4', '50', '2021-04-03 14:29:13', '0');
INSERT INTO `labs` VALUES (40, '本科生创新实验室', 'G-235', '1', '40', '2021-04-17 18:24:16', '0');
INSERT INTO `labs` VALUES (41, '探测制导与信息对抗实验室', 'G-326', '1', '40', '2021-04-17 18:25:43', '0');
INSERT INTO `labs` VALUES (42, '电子信息实验室', 'B-119', '4', '50', '2021-04-17 18:26:18', '0');
INSERT INTO `labs` VALUES (43, '雷达信号分析与处理实验室', 'F-123', '1', '30', '2021-04-17 18:26:50', '0');
INSERT INTO `labs` VALUES (44, '学科实验室', 'J-231', '4', '50', '2021-04-17 18:27:13', '0');
INSERT INTO `labs` VALUES (45, '添加实验室测试', 'c-335', '4', '50', '2021-04-18 17:48:41', '0');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '留言人id',
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留言人',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留言内容',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '留言事件',
  `is_delete` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`msg_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 1, 'admin', '啊啊啊', '2021-04-11 12:21:26', '0');
INSERT INTO `message` VALUES (2, 1, 'admin', '121313', '2021-04-13 11:34:49', '0');
INSERT INTO `message` VALUES (3, 1, 'admin', '121313', '2021-04-13 11:35:00', '0');
INSERT INTO `message` VALUES (4, 1, 'admin', '121313', '2021-04-13 11:35:05', '0');
INSERT INTO `message` VALUES (5, 1, 'admin', '留言测试', '2021-04-13 11:36:38', '0');
INSERT INTO `message` VALUES (6, 1, 'admin', '测试', '2021-04-13 11:37:24', '0');
INSERT INTO `message` VALUES (7, 21, '1705024101', 'hello world!', '2021-04-13 11:38:47', '0');
INSERT INTO `message` VALUES (8, 21, '1705024101', '清空留言板', '2021-04-13 11:40:05', '0');
INSERT INTO `message` VALUES (9, 21, '1705024101', '长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！长文本测试！', '2021-04-13 11:41:37', '0');
INSERT INTO `message` VALUES (10, 1, 'admin', '建议添加留言字数限制！', '2021-04-13 14:15:12', '0');
INSERT INTO `message` VALUES (11, 1, 'admin', '1231', '2021-04-15 09:41:49', '0');
INSERT INTO `message` VALUES (12, 1, 'admin', '一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五', '2021-04-15 09:49:24', '0');
INSERT INTO `message` VALUES (13, 1, 'admin', '一二三四五一二三四五一二三四五\n一二三四五一二三四五\n一二三四五一二三四五\n\n\n一二三四五一二三四五一二三四五\n一二三四五一二三四五\n', '2021-04-15 09:49:35', '0');
INSERT INTO `message` VALUES (14, 1, 'admin', '字数限制已添加！', '2021-04-15 09:52:13', '0');
INSERT INTO `message` VALUES (15, 1, 'admin', '管理员 需要删除留言 功能', '2021-04-15 09:52:54', '0');
INSERT INTO `message` VALUES (16, 23, '1705024103', '踩', '2021-04-15 13:39:36', '0');
INSERT INTO `message` VALUES (17, 1, 'admin', '12131313213', '2021-04-15 16:36:17', '0');
INSERT INTO `message` VALUES (18, 1, 'admin', '213213\n', '2021-04-15 16:38:17', '0');
INSERT INTO `message` VALUES (19, 1, 'admin', '313213', '2021-04-15 16:38:20', '0');
INSERT INTO `message` VALUES (20, 1, 'admin', '131313', '2021-04-15 16:38:24', '0');
INSERT INTO `message` VALUES (21, 1, 'admin', '1321313213', '2021-04-15 16:38:27', '0');
INSERT INTO `message` VALUES (22, 1, 'admin', '用户要不要只能看到自己和管理员的留言？\n', '2021-04-18 18:01:14', '0');
INSERT INTO `message` VALUES (23, 24, '2015006114', '你好，齐凯，毕设创作顺利！', '2021-04-19 16:15:52', '0');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `news_id` int(10) NOT NULL AUTO_INCREMENT,
  `news_type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章类型',
  `title` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '公告内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建公告时的时间',
  `release_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '发布状态    0-未发布  1-已发布',
  `release_time` datetime(0) NULL DEFAULT NULL COMMENT '文章发布时间',
  `is_delete` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '逻辑删除    0-未删除  1-已删除',
  PRIMARY KEY (`news_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (1, '1', '公告1', '公告1 helloworld ', '2017-03-16 21:21:13', '0', NULL, '1');
INSERT INTO `news` VALUES (2, '1', '2021/3/23公告', NULL, '2017-03-23 16:16:32', '1', '2021-03-23 12:37:34', '0');
INSERT INTO `news` VALUES (3, '1', '2021/2/23 公告2', '2017/2/23 公告2', '2017-03-23 16:17:52', '1', '2021-02-23 12:37:34', '0');
INSERT INTO `news` VALUES (4, '1', '最新新闻', '测试下', '2021-04-16 16:24:53', '1', '2021-02-23 11:27:15', '0');
INSERT INTO `news` VALUES (5, '1', 'xxx新闻', '测试新闻', '2021-04-13 07:59:42', '1', '2021-04-13 13:59:56', '0');
INSERT INTO `news` VALUES (6, '1', '关于五一放假通知', '五月放假 实验室不进行预约', '2021-04-17 11:26:02', '1', '2021-04-17 11:31:07', '0');
INSERT INTO `news` VALUES (7, '1', '世卫组织：新冠病毒D614G变异可能加强', '<p style=\"text-align: left;\">&nbsp; &nbsp; &nbsp; 当地时间7月3日，世卫组织召开新冠肺炎例行发布会，世卫组织首席科学家苏米娅&middot;斯瓦米纳坦表示，实验室研究发现，新冠病毒D614G变异可能导致病毒加速复制，意味着可能加强其传播性。但实验室结果同病毒在实际传播中发生的变化间，还有很大区别，科学家已对该变异进行监测。</p>\n<p>&nbsp; &nbsp; &nbsp; 世卫组织卫生紧急项目技术主管玛丽亚&middot;范&middot;科霍夫表示，其实二月份就已发现D614G变异，欧洲等地发现的早期病毒基因序列中就已出现该变异，有研究显示29%的新冠病毒样本都出现了该变异，但目前并无证据显示其会导致更严重的病情。</p>', '2021-04-16 17:31:05', '1', '2021-04-17 11:31:10', '0');
INSERT INTO `news` VALUES (8, '1', '04/17最新新闻', '<h1>Hello world !!!</h1> ', '2021-04-17 12:29:12', '1', '2021-04-17 12:29:15', '0');
INSERT INTO `news` VALUES (9, '1', '关于填报2021年高校实验室信息的通知', '<p>各学院、相关单位：</p>\n<p>&nbsp;&nbsp; &nbsp;&nbsp; 根据《省教育厅办公室关于做好<span lang=\"EN-US\">2021/2022</span>学年高校实验室信息统计数据报送工作的通知》（冀教办高函﹝<span lang=\"EN-US\">2021</span>﹞<span lang=\"EN-US\">17</span>号）的文件要求，各学院和相关单位要认真做好高校实验室和仪器设备信息统计上报工作。为保证在规定时间顺利完成数据的上报工作，现将有关事项通知如下：</p>\n<p>&nbsp;&nbsp; &nbsp;&nbsp; <strong>一、填报内容</strong></p>\n<ol>\n<li style=\"list-style-type: none;\">\n<ol>\n<li style=\"font-weight: 400;\">精密贵重仪器设备年度使用情况表、实验中心基本情况表。数据统计截止时间为2021年4月1日-2021年6月31日。</li>\n<li style=\"font-weight: 400;\">表格可从国资处网站&ldquo;相关下载&rdquo;版块下载，填写后通过邮件返回。</li>\n</ol>\n</li>\n</ol>\n<p style=\"font-weight: 400;\">&nbsp;&nbsp;&nbsp;&nbsp; <strong>二、相关要求</strong></p>\n<p style=\"font-weight: 400; padding-left: 40px;\">&nbsp;&nbsp;&nbsp;1.表格内各项数据请按照相应填表要求填写。</p>\n<p style=\"font-weight: 400; padding-left: 40px;\">&nbsp;&nbsp;&nbsp;2.上报时间：各单位请于7月10日前将两个表格及时返回。</p>\n<p style=\"font-weight: 400; padding-left: 40px;\">&nbsp;&nbsp;&nbsp;3.联系人：xxxx；&nbsp; 电话：1513121313&nbsp; <span lang=\"EN-US\">Email</span>：<span lang=\"EN-US\"><a href=\"mailto:sssssssss@126.com\" target=\"_blank\" rel=\"noopener\">sssssssss@126.com</a></span></p>\n<p style=\"font-weight: 400;\"><span lang=\"EN-US\">&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;</span></p>\n<p style=\"font-weight: 400;\">&nbsp;</p>\n<p style=\"font-weight: 400;\">&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;国有资产与实验室管理处</p>\n<p style=\"font-weight: 400;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2020年4月2日</p>', '2021-04-17 17:14:39', '1', '2021-04-18 15:32:37', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `roles` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色 0-管理员，1-教师，2-学生',
  `login_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生学号、教师职工号或者管理员工号作为登录时的用户名\r\n初始密码：\r\n管理员- gly123\r\n教师- js123456\r\n学生- xs123456',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码，长度6~8位，字母+数字，不能取汉字或者特殊字符',
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别,1-男，2-女',
  `age` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄',
  `institute` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属学院',
  `major` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
  `is_firstlogin` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否是第一次登陆0-不是 1-是',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `is_delete` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '逻辑删除 0-未删除 1-以删除',
  `create_date` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`, `login_name`) USING BTREE,
  UNIQUE INDEX `login_name`(`login_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '0', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', '1', NULL, '网络中心', '管理员', '0', 'https://gitee.com/qkget233/images/raw/master/PiqFjhwzPilZ.jpg', '0', '2021-04-29 18:24:52');
INSERT INTO `user` VALUES (2, '1', 'Z0000001', 'c20ad4d76fe97759aa27a0c99bff6710', '李教师', '1', NULL, '工程学院', '信管', '0', 'https://gitee.com/qkget233/images/raw/master/PiqFjhwzPilZ.jpg', '0', '2021-04-15 09:36:40');
INSERT INTO `user` VALUES (3, '2', '1309030212', '46498f538ca1a743f4140deda7beec7f', '陈红', '2', NULL, '工程学院', '信管', '1', 'https://gitee.com/qkget233/images/raw/master/PiqFjhwzPilZ.jpg', '0', '2021-04-20 10:49:47');
INSERT INTO `user` VALUES (4, '2', 'xs', 'e10adc3949ba59abbe56e057f20f883e', '张三', '1', NULL, '数软', '软工', '0', '', '0', '2021-02-04 14:41:22');
INSERT INTO `user` VALUES (5, '2', '1309030223', '09167da1cc828c0c37dc42073b25de95', '孙慧', '1', NULL, '信息与安全工程学院', NULL, '1', 'https://gitee.com/qkget233/images/raw/master/PiqFjhwzPilZ.jpg', '0', '2021-04-20 10:49:50');
INSERT INTO `user` VALUES (6, '2', '1309030224', '8661695b5659854c6c3ab042e8e42526', '赵六', '2', NULL, '信息与安全工程学院', NULL, '1', 'https://gitee.com/qkget233/images/raw/master/PiqFjhwzPilZ.jpg', '0', '2021-04-20 10:49:52');
INSERT INTO `user` VALUES (7, '2', '1309030123', 'e10adc3949ba59abbe56e057f20f883e', '王五', '1', NULL, '信息与安全工程学院', '信管', '0', 'https://gitee.com/qkget233/images/raw/master/PiqFjhwzPilZ.jpg', '0', '2021-04-03 16:43:44');
INSERT INTO `user` VALUES (8, '1', '1309030111', 'e10adc3949ba59abbe56e057f20f883e', '李四', '1', NULL, '会计学院', '会计', '0', 'https://gitee.com/qkget233/images/raw/master/PiqFjhwzPilZ.jpg', '0', '2021-04-03 16:43:58');
INSERT INTO `user` VALUES (9, '1', 'js', 'e10adc3949ba59abbe56e057f20f883e', '教师', '2', NULL, '信息与安全工程学院', '信管', '0', 'https://gitee.com/qkget233/images/raw/master/PiqFjhwzPilZ.jpg', '0', '2021-02-04 13:13:57');
INSERT INTO `user` VALUES (15, '0', '1705024318', 'ca00a56e3d878b52698e6b9007382d89', '亚索', '1', NULL, '决斗大师', '浪人', '0', 'http://localhost:5555/downloadFile/27f32bcc-4638-4b4e-8ab8-999e67a07190.png', '0', '2021-04-03 16:44:18');
INSERT INTO `user` VALUES (17, '1', '1705024319', '8eae22c588de35d2e4f4cb87748f45e3', '永恩', '1', NULL, '宗师', '浪人', '1', NULL, '0', '2021-02-04 15:50:17');
INSERT INTO `user` VALUES (18, '2', '1705024326', '7a5df7de191d53934ba311fbf92d6d35', '亚索2', '1', NULL, '决斗大师', '浪人', '1', NULL, '0', '2021-04-03 16:42:11');
INSERT INTO `user` VALUES (19, '1', '1705024311', 'e10adc3949ba59abbe56e057f20f883e', '永恩', '2', NULL, '宗师', '浪人', '0', NULL, '1', '2021-02-23 17:47:14');
INSERT INTO `user` VALUES (20, '1', 'teacher', 'e10adc3949ba59abbe56e057f20f883e', '测试教师', '1', NULL, '数据科学与软件工程', '软件工程', '0', 'https://gitee.com/qkget233/images/raw/master/labOrder/Mlz@wJWIjTFr.png', '0', '2021-04-02 00:12:44');
INSERT INTO `user` VALUES (21, '2', '1705024101', 'e10adc3949ba59abbe56e057f20f883e', '关小羽', '1', NULL, '数软学院', '软件工程', '0', 'https://gitee.com/qkget233/images/raw/master/labOrder/KR6RZEBPrFbj.jpeg', '0', '2021-04-26 14:47:06');
INSERT INTO `user` VALUES (22, '2', '1705024102', 'c3856612817dd967ce7487e6214f1395', '张小飞', '1', NULL, '数软学院', '软件工程', '1', NULL, '0', '2021-04-20 10:59:49');
INSERT INTO `user` VALUES (23, '2', '1705024103', 'e10adc3949ba59abbe56e057f20f883e', '刘小备', '1', NULL, '数软学院', '软件工程', '0', 'http://localhost:5555/downloadFile/e6a6874e-8219-47e9-b333-53dc71da82f0.png', '0', '2021-04-29 17:32:05');
INSERT INTO `user` VALUES (24, '1', '2015006114', 'e10adc3949ba59abbe56e057f20f883e', '赵元', '1', NULL, '佛系学院', '佛系专业', '0', NULL, '0', '2021-04-19 16:12:52');

SET FOREIGN_KEY_CHECKS = 1;
