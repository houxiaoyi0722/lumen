/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : localhost:3307
 Source Schema         : lumen_server

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 14/02/2023 10:12:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('clusteredScheduler', 'demo1', 'demo', '0 20 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('clusteredScheduler', 'demo1', 'demo', '', 'com.sang.system.job.DemoJob', '1', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400057465737432737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000002740005746573743374000133740004746573747371007E0008000000017800);

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
INSERT INTO `QRTZ_LOCKS` VALUES ('clusteredScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_TRIGGERS` VALUES ('clusteredScheduler', 'demo1', 'demo', 'demo1', 'demo', '', 1661851200000, 1661847600000, 5, 'PAUSED', 'CRON', 1661827087000, 0, NULL, 0, '');

-- ----------------------------
-- Table structure for db_migration
-- ----------------------------
DROP TABLE IF EXISTS `db_migration`;
CREATE TABLE `db_migration`  (
  `id` int(11) NOT NULL,
  `mtype` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mstatus` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mversion` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mcomment` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mchecksum` int(11) NOT NULL,
  `run_on` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `run_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `run_time` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_migration
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(20) NOT NULL,
  `group_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组id',
  `group_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组名称',
  `comment` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` bigint(20) NOT NULL,
  `when_created` datetime(6) NOT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `when_modified` datetime(6) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_data_dictionary_group_id`(`group_id`) USING BTREE,
  UNIQUE INDEX `uq_data_dictionary_group_name`(`group_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '5', '5', '5', 1, '2023-02-13 16:26:57.000000', 'hxy', 'hxy', '2023-02-13 16:27:06.000000', 0);
INSERT INTO `sys_dict` VALUES (2, '6', '6', '6', 1, '2023-02-13 16:26:57.000000', 'hxy', 'hxy', '2023-02-13 16:27:06.000000', 0);
INSERT INTO `sys_dict` VALUES (3, '7', '7', '7', 1, '2023-02-13 16:26:57.000000', 'hxy', 'hxy', '2023-02-13 16:27:06.000000', 0);
INSERT INTO `sys_dict` VALUES (4, '8', '8', '8', 1, '2023-02-13 16:26:57.000000', 'hxy', 'hxy', '2023-02-13 16:27:06.000000', 0);
INSERT INTO `sys_dict` VALUES (5, '9', '9', '9', 1, '2023-02-13 16:26:57.000000', 'hxy', 'hxy', '2023-02-13 16:27:06.000000', 0);
INSERT INTO `sys_dict` VALUES (6, '10', '10', '10', 1, '2023-02-13 16:26:57.000000', 'hxy', 'hxy', '2023-02-13 16:27:06.000000', 0);
INSERT INTO `sys_dict` VALUES (7, '11', '11', '11', 1, '2023-02-13 16:26:57.000000', 'hxy', 'hxy', '2023-02-13 16:27:06.000000', 0);
INSERT INTO `sys_dict` VALUES (8, '12', '12', '12', 1, '2023-02-13 16:26:57.000000', 'hxy', 'hxy', '2023-02-13 16:27:06.000000', 0);
INSERT INTO `sys_dict` VALUES (9, '13', '13', '13', 1, '2023-02-13 16:26:57.000000', 'hxy', 'hxy', '2023-02-13 16:27:06.000000', 0);
INSERT INTO `sys_dict` VALUES (10, '14', '14', '14', 1, '2023-02-13 16:26:57.000000', 'hxy', 'hxy', '2023-02-13 16:27:06.000000', 0);
INSERT INTO `sys_dict` VALUES (11, '15', '15', '15', 1, '2023-02-13 16:26:57.000000', 'hxy', 'hxy', '2023-02-13 16:27:06.000000', 0);
INSERT INTO `sys_dict` VALUES (427893957394632704, 'test3', 'test3', 'test3', 1, '2022-08-03 14:14:24.654000', 'hxy', 'hxy', '2022-08-09 18:07:33.932000', 0);
INSERT INTO `sys_dict` VALUES (428209917393571840, 'test4', 'test4', 'test4', 1, '2022-08-04 11:09:55.386000', 'hxy', 'hxy', '2022-08-04 11:09:55.386000', 0);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` bigint(20) NOT NULL,
  `dictionary_id` bigint(20) NOT NULL,
  `item_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'value',
  `item_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'key',
  `comment` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` bigint(20) NOT NULL,
  `when_created` datetime(6) NOT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `when_modified` datetime(6) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ix_data_dictionary_item_dictionary_id`(`dictionary_id`) USING BTREE,
  CONSTRAINT `fk_data_dictionary_item_dictionary_id` FOREIGN KEY (`dictionary_id`) REFERENCES `sys_dict` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (427893957600153600, 427893957394632704, 'test1-1', '12', 'test1-1', 1, '2022-08-03 14:14:24.683000', 'hxy', 'hxy', '2022-08-03 14:14:24.683000', 0);
INSERT INTO `sys_dict_item` VALUES (427894882758758400, 427893957394632704, 'test3', '123', 'test3', 1, '2022-08-03 14:18:05.278000', 'hxy', 'hxy', '2022-08-04 09:28:34.422000', 0);
INSERT INTO `sys_dict_item` VALUES (428209917502623744, 428209917393571840, 'test4', '4', 'test4', 1, '2022-08-04 11:09:55.410000', 'hxy', 'hxy', '2022-08-04 11:09:55.410000', 0);

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `id` bigint(20) NOT NULL,
  `job_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'job名称',
  `job_group` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'job分组',
  `bean_class` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行类全限定名',
  `job_data_map` json NULL COMMENT '执行类全限定名',
  `cron_expression` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cron',
  `status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `start_time` datetime(6) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(6) NULL DEFAULT NULL COMMENT '结束时间',
  `may_fire_again` tinyint(1) NULL DEFAULT NULL COMMENT '触发器是否会再次触发',
  `job_run_time` bigint(20) NULL DEFAULT NULL COMMENT 'job执行时间,以毫秒为单位',
  `result` json NULL COMMENT 'job执行结果/错误结果',
  `version` bigint(20) NOT NULL,
  `when_created` datetime(6) NOT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `when_modified` datetime(6) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JobLog执行日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------
INSERT INTO `sys_job_log` VALUES (437608054814093312, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{\"test\": 1, \"test2\": 2, \"test3\": \"3\"}', '0/20 * * * * ?', 'COMPLETE', '2022-08-29 16:08:31.000000', NULL, 1, 13977, '\"执行成功\"', 1, '2022-08-30 09:34:45.959000', 'anonymous', 'anonymous', '2022-08-30 09:34:45.959000', 0);
INSERT INTO `sys_job_log` VALUES (437623602373406720, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{\"test\": 1, \"test2\": 2, \"test3\": \"3\"}', '0/20 * * * * ?', 'ERROR', '2022-08-29 16:08:31.000000', NULL, 1, 3001, '\"org.quartz.JobExecutionException: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero] [See nested exception: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:218)\\r\\n\\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\\r\\nCaused by: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:213)\\r\\n\\t... 1 more\\r\\nCaused by: java.lang.ArithmeticException: / by zero\\r\\n\\tat com.sang.system.job.DemoJob.executeInternal(DemoJob.java:22)\\r\\n\\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:75)\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202)\\r\\n\\t... 1 more\\r\\n\"', 1, '2022-08-30 10:36:32.786000', 'anonymous', 'anonymous', '2022-08-30 10:36:32.786000', 0);
INSERT INTO `sys_job_log` VALUES (437623659973783552, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{\"test\": 1, \"test2\": 2, \"test3\": \"3\"}', '0/20 * * * * ?', 'ERROR', '2022-08-29 16:08:31.000000', NULL, 1, 3001, '\"org.quartz.JobExecutionException: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero] [See nested exception: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:218)\\r\\n\\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\\r\\nCaused by: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:213)\\r\\n\\t... 1 more\\r\\nCaused by: java.lang.ArithmeticException: / by zero\\r\\n\\tat com.sang.system.job.DemoJob.executeInternal(DemoJob.java:22)\\r\\n\\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:75)\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202)\\r\\n\\t... 1 more\\r\\n\"', 1, '2022-08-30 10:36:46.519000', 'anonymous', 'anonymous', '2022-08-30 10:36:46.519000', 0);
INSERT INTO `sys_job_log` VALUES (437623994859597824, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{\"test\": 1, \"test2\": 2, \"test3\": \"3\"}', '0/20 * * * * ?', 'ERROR', '2022-08-29 16:08:31.000000', NULL, 1, 3015, '\"org.quartz.JobExecutionException: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero] [See nested exception: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:218)\\r\\n\\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\\r\\nCaused by: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:213)\\r\\n\\t... 1 more\\r\\nCaused by: java.lang.ArithmeticException: / by zero\\r\\n\\tat com.sang.system.job.DemoJob.executeInternal(DemoJob.java:22)\\r\\n\\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:75)\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202)\\r\\n\\t... 1 more\\r\\n\"', 1, '2022-08-30 10:38:06.362000', 'anonymous', 'anonymous', '2022-08-30 10:38:06.362000', 0);
INSERT INTO `sys_job_log` VALUES (437634538312249344, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{\"test\": 1, \"test2\": 2, \"test3\": \"3\"}', '0 20 * * * ?', 'ERROR', '2022-08-30 10:38:07.000000', NULL, 1, 3003, '\"org.quartz.JobExecutionException: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero] [See nested exception: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:218)\\r\\n\\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\\r\\nCaused by: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:213)\\r\\n\\t... 1 more\\r\\nCaused by: java.lang.ArithmeticException: / by zero\\r\\n\\tat com.sang.system.job.DemoJob.executeInternal(DemoJob.java:22)\\r\\n\\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:75)\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202)\\r\\n\\t... 1 more\\r\\n\"', 1, '2022-08-30 11:20:00.117000', 'anonymous', 'anonymous', '2022-08-30 11:20:00.117000', 0);
INSERT INTO `sys_job_log` VALUES (437649637638877184, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{\"test\": 1, \"test2\": 2, \"test3\": \"3\"}', '0 20 * * * ?', 'ERROR', '2022-08-30 10:38:07.000000', NULL, 1, 3000, '\"org.quartz.JobExecutionException: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero] [See nested exception: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:218)\\r\\n\\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\\r\\nCaused by: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:213)\\r\\n\\t... 1 more\\r\\nCaused by: java.lang.ArithmeticException: / by zero\\r\\n\\tat com.sang.system.job.DemoJob.executeInternal(DemoJob.java:22)\\r\\n\\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:75)\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202)\\r\\n\\t... 1 more\\r\\n\"', 1, '2022-08-30 12:20:00.077000', 'anonymous', 'anonymous', '2022-08-30 12:20:00.077000', 0);
INSERT INTO `sys_job_log` VALUES (437664737028419584, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{\"test\": 1, \"test2\": 2, \"test3\": \"3\"}', '0 20 * * * ?', 'ERROR', '2022-08-30 10:38:07.000000', NULL, 1, 3000, '\"org.quartz.JobExecutionException: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero] [See nested exception: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:218)\\r\\n\\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\\r\\nCaused by: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:213)\\r\\n\\t... 1 more\\r\\nCaused by: java.lang.ArithmeticException: / by zero\\r\\n\\tat com.sang.system.job.DemoJob.executeInternal(DemoJob.java:22)\\r\\n\\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:75)\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202)\\r\\n\\t... 1 more\\r\\n\"', 1, '2022-08-30 13:20:00.052000', 'anonymous', 'anonymous', '2022-08-30 13:20:00.052000', 0);
INSERT INTO `sys_job_log` VALUES (437681268823109632, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{\"test\": 1, \"test2\": 2, \"test3\": \"3\"}', '0 20 * * * ?', 'COMPLETE', '2022-08-30 14:25:41.496000', '2022-08-30 14:25:44.613000', 1, 3019, '\"执行成功\"', 1, '2022-08-30 14:25:41.529000', 'anonymous', 'anonymous', '2022-08-30 14:25:41.529000', 0);
INSERT INTO `sys_job_log` VALUES (437694936117882880, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{\"test\": 1, \"test2\": 2, \"test3\": \"3\"}', '0 20 * * * ?', 'COMPLETE', '2022-08-30 15:20:00.066000', '2022-08-30 15:20:03.102000', 1, 3001, '\"执行成功\"', 1, '2022-08-30 15:20:00.075000', 'anonymous', 'anonymous', '2022-08-30 15:20:00.075000', 0);
INSERT INTO `sys_job_log` VALUES (437710035608088576, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{\"test\": 1, \"test2\": 2, \"test3\": \"3\"}', '0 20 * * * ?', 'COMPLETE', '2022-08-30 16:20:00.067000', '2022-08-30 16:20:03.099000', 1, 3002, '\"执行成功\"', 1, '2022-08-30 16:20:00.074000', 'anonymous', 'anonymous', '2022-08-30 16:20:00.074000', 0);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(20) NOT NULL,
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限code',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `router_id` bigint(20) NULL DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `when_created` datetime(6) NOT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `when_modified` datetime(6) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ix_permissions_router_id`(`router_id`) USING BTREE,
  CONSTRAINT `fk_permissions_router_id` FOREIGN KEY (`router_id`) REFERENCES `sys_router` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 'userManage-1111222', '1111112222', '111', 459094420135555072, 2, '2023-01-12 19:52:29.000000', 'hxy', 'hxy', '2023-01-31 16:06:31.017000', 0);
INSERT INTO `sys_permission` VALUES (2, '222', '22222', '2222', 459094420135555072, 2, '2023-01-12 19:52:29.000000', 'hxy', 'hxy', '2023-01-31 16:06:31.023000', 1);
INSERT INTO `sys_permission` VALUES (493514373408194560, 'user-111', 'userManage-111', NULL, 459094420135555072, 1, '2023-01-31 16:06:30.989000', 'hxy', 'hxy', '2023-01-31 16:06:30.989000', 0);
INSERT INTO `sys_permission` VALUES (493518197678112768, 'user-222', 'userManage-222', NULL, 459094420135555072, 1, '2023-01-31 16:21:42.768000', 'hxy', 'hxy', '2023-01-31 16:23:21.951000', 1);
INSERT INTO `sys_permission` VALUES (493518694522781696, 'role-1', 'roleMenage-1', NULL, 459013174491557888, 1, '2023-01-31 16:23:41.235000', 'hxy', 'hxy', '2023-01-31 16:23:41.235000', 0);
INSERT INTO `sys_permission` VALUES (493518697697869824, 'role-1', 'roleMenage-1', NULL, 459013174491557888, 1, '2023-01-31 16:23:41.992000', 'hxy', 'hxy', '2023-01-31 16:24:16.707000', 1);
INSERT INTO `sys_permission` VALUES (494222463082397696, '阿萨达撒', 'as发斯蒂芬', NULL, NULL, 1, '2023-02-02 15:00:12.719000', 'hxy', 'hxy', '2023-02-02 15:00:12.719000', 0);
INSERT INTO `sys_permission` VALUES (494222463183060992, ' 大萨达', '驱蚊器若所', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463187255296, '驱蚊器翁', '驱蚊器翁', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463187255297, '5675675', '345345346', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463187255298, '6457567', '3645645', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463191449600, '123123', '234234', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463191449601, '2423', '123', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463191449602, '1234', '1234', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463195643904, '123', '123', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463195643905, '999', '999', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463195643906, '888', '888', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463199838208, '777', '667', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463199838209, '666', '666', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463199838210, '444', '444', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463199838211, '555', '55', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463204032512, '33', '333', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463204032513, '22', '22', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO `sys_permission` VALUES (494222463204032514, '11', '11', NULL, NULL, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);

-- ----------------------------
-- Table structure for sys_permission_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_sys_role`;
CREATE TABLE `sys_permission_sys_role`  (
  `sys_permission_id` bigint(20) NOT NULL,
  `sys_role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`sys_permission_id`, `sys_role_id`) USING BTREE,
  INDEX `ix_permissions_role_permissions`(`sys_permission_id`) USING BTREE,
  INDEX `ix_permissions_role_role`(`sys_role_id`) USING BTREE,
  CONSTRAINT `fk_permissions_role_permissions` FOREIGN KEY (`sys_permission_id`) REFERENCES `sys_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_permissions_role_role` FOREIGN KEY (`sys_role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission_sys_role
-- ----------------------------
INSERT INTO `sys_permission_sys_role` VALUES (1, 435165139135348736);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL,
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色代码',
  `comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `when_created` datetime(6) NOT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `when_modified` datetime(6) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ix_role_parent_id`(`parent_id`) USING BTREE,
  CONSTRAINT `fk_role_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (435, '菜单管理员', 'menuAdmin', '菜单管理员', NULL, 1, '2022-08-23 15:47:29.470000', 'hxy', 'hxy', '2022-08-23 15:47:29.470000', 0);
INSERT INTO `sys_role` VALUES (435165139135348736, 'admin', 'admin', 'admin', NULL, 1, '2022-08-23 15:47:29.470000', 'hxy', 'hxy', '2023-01-30 11:03:39.594000', 0);
INSERT INTO `sys_role` VALUES (435171606257188864, 'admin1', 'admin1', '啊啊啊', 435165139135348736, 1, '2022-08-23 16:13:11.356000', 'hxy', 'hxy', '2022-10-28 16:31:11.357000', 1);
INSERT INTO `sys_role` VALUES (435181148793716736, 'admin3', 'admin3', '超级管理员3', 435171606257188864, 1, '2022-08-23 16:51:06.473000', 'hxy', 'hxy', '2022-10-28 16:31:11.357000', 1);
INSERT INTO `sys_role` VALUES (435181148802105344, 'admin4', 'admin4', '超级管理员4', 435165139135348736, 1, '2022-08-23 16:51:06.473000', 'hxy', 'hxy', '2022-10-28 16:05:47.263000', 1);
INSERT INTO `sys_role` VALUES (459087206658289664, 'ce是', '杀杀杀', '啊啊啊', NULL, 1, '2022-10-28 16:05:14.782000', 'hxy', 'hxy', '2022-10-28 16:31:27.924000', 1);
INSERT INTO `sys_role` VALUES (459090537438982144, '123', '123', '123', NULL, 1, '2022-10-28 16:18:28.934000', 'hxy', 'hxy', '2022-10-28 16:31:27.924000', 1);
INSERT INTO `sys_role` VALUES (459090685799903232, '1234', '1234', '123', NULL, 1, '2022-10-28 16:19:04.314000', 'hxy', 'hxy', '2022-10-28 16:30:03.525000', 1);
INSERT INTO `sys_role` VALUES (459093504099561472, 'admin4', 'admin4', 'admin4', 435181148793716736, 1, '2022-10-28 16:30:16.249000', 'hxy', 'hxy', '2022-10-28 16:31:11.357000', 1);
INSERT INTO `sys_role` VALUES (459093538924867584, 'admin5', 'admin5', 'admin5', 459093504099561472, 1, '2022-10-28 16:30:24.552000', 'hxy', 'hxy', '2022-10-28 16:31:11.357000', 1);
INSERT INTO `sys_role` VALUES (459093570197598208, 'admin6', 'admin6', 'admin6', 459093538924867584, 1, '2022-10-28 16:30:32.008000', 'hxy', 'hxy', '2022-10-28 16:31:11.357000', 1);
INSERT INTO `sys_role` VALUES (459093636396298240, 'admin7', 'admin7', 'admin7', 459093570197598208, 1, '2022-10-28 16:30:47.791000', 'hxy', 'hxy', '2022-10-28 16:31:11.356000', 1);
INSERT INTO `sys_role` VALUES (459093735222489088, 'admin1', 'admin1', '超级管理员1', 435165139135348736, 1, '2022-10-28 16:31:11.353000', 'hxy', 'hxy', '2022-10-28 16:31:11.353000', 0);

-- ----------------------------
-- Table structure for sys_router
-- ----------------------------
DROP TABLE IF EXISTS `sys_router`;
CREATE TABLE `sys_router`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由名称',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问路径',
  `redirect` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相对路径 根目录开始',
  `component` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'component组件',
  `mate` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '元数据 json格式',
  `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `hidden` tinyint(1) NULL DEFAULT NULL COMMENT '是否隐藏',
  `always_show` tinyint(1) NULL DEFAULT NULL COMMENT 'alwaysShow',
  `order_by` int(11) NULL DEFAULT NULL COMMENT '排序',
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `when_created` datetime(6) NOT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `when_modified` datetime(6) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ix_router_parent_id`(`parent_id`) USING BTREE,
  CONSTRAINT `fk_router_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `sys_router` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '路由表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_router
-- ----------------------------
INSERT INTO `sys_router` VALUES (435576735858933760, 'systemSetting', '/setting', NULL, 'Layout', '{\"title\":\"管理设置\",\"icon\":\"Setting\"}', '', 0, 1, 0, NULL, 1, '2022-08-24 19:03:01.772000', 'hxy', 'hxy', '2022-10-31 14:59:29.449000', 0);
INSERT INTO `sys_router` VALUES (435587805562519552, 'menuManage', 'menu', NULL, 'menage/router-manage', '{\"title\":\"菜单管理\",\"icon\":\"Menu\"}', '', 0, 1, 1, 435576735858933760, 1, '2022-08-24 19:47:01.000000', 'hxy', 'hxy', '2022-10-31 15:05:43.230000', 0);
INSERT INTO `sys_router` VALUES (459013174491557888, 'roleMenage', 'role', NULL, 'menage/role-manage', '{\"title\":\"角色管理\",\"icon\":\"Avatar\"}', '', 0, 1, 2, 435576735858933760, 1, '2022-10-28 11:11:04.167000', 'hxy', 'hxy', '2022-10-28 11:14:17.767000', 0);
INSERT INTO `sys_router` VALUES (459094118854504448, 'userManage', 'user', NULL, 'menage/role-manage', '{\"title\":\"用户管理\",\"icon\":\"User\"}', '', 0, 1, 0, NULL, 1, '2022-10-28 16:32:42.816000', 'hxy', 'hxy', '2022-10-28 16:33:54.668000', 1);
INSERT INTO `sys_router` VALUES (459094420135555072, 'userManage', 'user', NULL, 'menage/user-manage', '{\"title\":\"用户管理\",\"icon\":\"User\"}', '', 0, 1, 3, 435576735858933760, 1, '2022-10-28 16:33:54.649000', 'hxy', 'hxy', '2022-10-31 14:59:36.822000', 0);
INSERT INTO `sys_router` VALUES (460910610671677440, 'userGroupManage', 'userGroup', NULL, 'menage/user-group-manage', '{\"title\":\"用户组管理\",\"icon\":\"userGroup3\"}', '', 0, 1, 4, 435576735858933760, 1, '2022-11-02 16:50:48.200000', 'hxy', 'hxy0722', '2023-02-10 10:35:13.623000', 0);
INSERT INTO `sys_router` VALUES (496435093176025088, 'diceManage', 'dict', NULL, 'menage/dict-manage', '{\"title\":\"字典管理\",\"icon\":\"dict\"}', '', 0, 1, 5, 435576735858933760, 1, '2023-02-08 17:32:24.824000', 'hxy0722', 'hxy0722', '2023-02-10 10:28:55.416000', 0);

-- ----------------------------
-- Table structure for sys_router_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_router_sys_role`;
CREATE TABLE `sys_router_sys_role`  (
  `sys_router_id` bigint(20) NOT NULL,
  `sys_role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`sys_router_id`, `sys_role_id`) USING BTREE,
  INDEX `ix_router_role_router`(`sys_router_id`) USING BTREE,
  INDEX `ix_router_role_role`(`sys_role_id`) USING BTREE,
  CONSTRAINT `fk_router_role_role` FOREIGN KEY (`sys_role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_router_role_router` FOREIGN KEY (`sys_router_id`) REFERENCES `sys_router` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_router_sys_role
-- ----------------------------
INSERT INTO `sys_router_sys_role` VALUES (435576735858933760, 435165139135348736);
INSERT INTO `sys_router_sys_role` VALUES (435587805562519552, 435165139135348736);
INSERT INTO `sys_router_sys_role` VALUES (459013174491557888, 435165139135348736);
INSERT INTO `sys_router_sys_role` VALUES (459094118854504448, 435165139135348736);
INSERT INTO `sys_router_sys_role` VALUES (459094420135555072, 435165139135348736);
INSERT INTO `sys_router_sys_role` VALUES (460910610671677440, 435165139135348736);
INSERT INTO `sys_router_sys_role` VALUES (496435093176025088, 435165139135348736);

-- ----------------------------
-- Table structure for sys_storage
-- ----------------------------
DROP TABLE IF EXISTS `sys_storage`;
CREATE TABLE `sys_storage`  (
  `id` bigint(20) NOT NULL,
  `original_file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '原文件名',
  `etag` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'etag',
  `object` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对象存储地址',
  `storage_bucket` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '存储桶',
  `suffix` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型/后缀名',
  `content_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型/请求头',
  `version_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本id',
  `size` bigint(200) NULL DEFAULT NULL COMMENT '文件大小/b',
  `business_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务代码',
  `business_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `version` bigint(20) NOT NULL,
  `when_created` datetime(6) NOT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `when_modified` datetime(6) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对象存储管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_storage
-- ----------------------------
INSERT INTO `sys_storage` VALUES (417810630545473536, '2022年6月-元歌-JAVA开发-侯晓懿.doc', '55d31dc594b18025012d85c177b15abf', '417810630545473536.doc', 'my-first-bucket', 'doc', 'application/msword', NULL, 104119, NULL, NULL, 1, '2022-07-06 18:26:52.231000', 'hxy', 'hxy', '2022-07-06 18:26:52.231000', 0);
INSERT INTO `sys_storage` VALUES (473976113539530752, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/473976113539530752.png', 'my-first-bucket', 'png', 'image/png', NULL, 1937996, 'hxy', 'Avatar', 1, '2022-12-08 18:09:18.149000', 'hxy', 'hxy', '2022-12-08 18:09:18.149000', 0);
INSERT INTO `sys_storage` VALUES (475362108939911168, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475362108939911168.png', 'my-first-bucket', 'png', 'image/png', NULL, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 13:55:57.105000', 'hxy', 'hxy', '2022-12-12 13:55:57.105000', 0);
INSERT INTO `sys_storage` VALUES (475392829519773696, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475392829519773696.png', 'my-first-bucket', 'png', 'image/png', NULL, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 15:57:58.415000', 'hxy', 'hxy', '2022-12-12 15:57:58.415000', 0);
INSERT INTO `sys_storage` VALUES (475393424884449280, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475393424884449280.png', 'my-first-bucket', 'png', 'image/png', NULL, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 16:00:20.535000', 'hxy', 'hxy', '2022-12-12 16:00:20.535000', 0);
INSERT INTO `sys_storage` VALUES (475396176100409344, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475396176100409344.png', 'my-first-bucket', 'png', 'image/png', NULL, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 16:11:16.321000', 'hxy', 'hxy', '2022-12-12 16:11:16.321000', 0);
INSERT INTO `sys_storage` VALUES (475396310842425344, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475396310842425344.png', 'my-first-bucket', 'png', 'image/png', NULL, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 16:11:48.407000', 'hxy', 'hxy', '2022-12-12 16:11:48.407000', 0);
INSERT INTO `sys_storage` VALUES (475398558943232000, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475398558943232000.png', 'my-first-bucket', 'png', 'image/png', NULL, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 16:20:44.415000', 'hxy', 'hxy', '2022-12-12 16:20:44.415000', 0);
INSERT INTO `sys_storage` VALUES (475400376721031168, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475400376721031168.png', 'my-first-bucket', 'png', 'image/png', NULL, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 16:27:57.778000', 'hxy', 'hxy', '2022-12-12 16:27:57.778000', 0);
INSERT INTO `sys_storage` VALUES (475411726935474176, 'test.png', 'd90520511993b409ecaeef19b3fc3827', 'Avatar/475411726935474176.png', 'my-first-bucket', 'png', 'image/png', NULL, 7002, 'hxy', 'Avatar', 1, '2022-12-12 17:13:03.852000', 'hxy', 'hxy', '2022-12-12 17:13:03.852000', 0);
INSERT INTO `sys_storage` VALUES (476043658492850176, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/476043658492850176.png', 'my-first-bucket', 'png', 'image/png', NULL, 1937996, 'admin', 'Avatar', 1, '2022-12-14 11:04:08.253000', 'hxy', 'hxy', '2022-12-14 11:04:08.253000', 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用',
  `account_non_expired` tinyint(1) NULL DEFAULT NULL COMMENT '账户未过期',
  `account_non_locked` tinyint(1) NULL DEFAULT NULL COMMENT '账户锁定',
  `credentials_non_expired` tinyint(1) NULL DEFAULT NULL COMMENT '凭证未过期',
  `user_group_id` bigint(20) NULL DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `when_created` datetime(6) NOT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `when_modified` datetime(6) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `user_ext_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_name`(`name`) USING BTREE,
  UNIQUE INDEX `uq_user_user_name`(`username`) USING BTREE,
  UNIQUE INDEX `uq_user_password`(`password`) USING BTREE,
  INDEX `ix_user_user_group_id`(`user_group_id`) USING BTREE,
  INDEX `fk_user_user_ext_id`(`user_ext_id`) USING BTREE,
  CONSTRAINT `fk_user_user_group_id` FOREIGN KEY (`user_group_id`) REFERENCES `sys_user_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (417805766834749440, 'hxy0722', 'hxy0722', '$2a$10$F5iQr22D2GkpzEv2jv0JrO.YzH62/JISuyVqCbpIn2sU17uF/uzX6', 1, 1, 1, 1, 1, 1, '2022-07-06 18:07:32.589000', 'anonymous', 'hxy', '2023-02-03 09:37:01.852000', 0, 475361018735443968);
INSERT INTO `sys_user` VALUES (436243035648110592, 'admin', 'admin', '$2a$10$S1N6TDSaJjpooVzAZvzvpO75fJQaByNqZd8EL7Ih4TmesGY78cque', 1, 1, 1, 1, 1, 1, '2022-08-26 15:10:40.025000', 'hxy', 'hxy', '2022-12-06 15:39:03.503000', 0, NULL);
INSERT INTO `sys_user` VALUES (473195416688209920, 'test123', 'test123', '$2a$10$mx04zD0SMmWy9FHEhp69zOlBqsQQX0NB4haeS./zphjsaI4vDsbQ2', 1, 1, 1, 1, 473221598745931776, 1, '2022-12-06 14:26:14.203000', 'hxy', 'hxy', '2022-12-07 18:10:26.304000', 0, NULL);
INSERT INTO `sys_user` VALUES (473220759327289344, 'test1222', 'test2322', '$2a$10$aMMg0jIiScYLVTdVZtC48eSpMNbutX/0ThU9BZm.pljKlOPQUX8XW', 1, 1, 1, 1, 473221598745931776, 1, '2022-12-06 16:06:56.361000', 'hxy', 'hxy', '2022-12-06 18:00:26.224000', 1, NULL);

-- ----------------------------
-- Table structure for sys_user_ext
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_ext`;
CREATE TABLE `sys_user_ext`  (
  `id` bigint(20) NOT NULL,
  `avatar_id` bigint(20) NULL DEFAULT NULL,
  `gender` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `birthday` datetime(6) NULL DEFAULT NULL COMMENT '出生日期',
  `intro` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `mobile_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '移动电话',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `user_id` bigint(20) NULL DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `when_created` datetime(6) NOT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `when_modified` datetime(6) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_ext_avatar_id`(`avatar_id`) USING BTREE,
  UNIQUE INDEX `uq_user_ext_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_user_ext_avatar_id` FOREIGN KEY (`avatar_id`) REFERENCES `sys_storage` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_ext_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户扩展信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_ext
-- ----------------------------
INSERT INTO `sys_user_ext` VALUES (475361018735443968, 475411726935474176, 'MAN', '2022-12-07 00:00:00.000000', '123', '123', '123', '123', '123', 417805766834749440, 1, '2022-12-12 13:51:33.971000', 'hxy', 'hxy', '2022-12-12 17:13:05.231000', 0);
INSERT INTO `sys_user_ext` VALUES (476043666319421440, 476043658492850176, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 436243035648110592, 1, '2022-12-14 11:04:09.841000', 'hxy', 'hxy', '2022-12-14 11:04:09.841000', 0);

-- ----------------------------
-- Table structure for sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group`  (
  `id` bigint(20) NOT NULL,
  `group_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户组名称',
  `group_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户组代码',
  `comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `version` bigint(20) NOT NULL,
  `when_created` datetime(6) NOT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `when_modified` datetime(6) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_group_group_name`(`group_name`) USING BTREE,
  UNIQUE INDEX `uq_user_group_group_code`(`group_code`) USING BTREE,
  INDEX `ix_user_group_parent_id`(`parent_id`) USING BTREE,
  CONSTRAINT `fk_user_group_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `sys_user_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_group
-- ----------------------------
INSERT INTO `sys_user_group` VALUES (1, '管理员', 'admingroup', '11', NULL, 1, '2022-08-26 14:42:46.000000', '1', 'hxy', '2022-11-03 11:21:33.547000', 0);
INSERT INTO `sys_user_group` VALUES (460925297312346112, '啊啊啊', 'aaaaaa', '', NULL, 1, '2022-11-02 17:49:09.784000', 'hxy', 'hxy', '2022-11-02 17:49:46.380000', 1);
INSERT INTO `sys_user_group` VALUES (460925494763401216, 'aaa', 'aaa', 'aasdassssssssss', 1, 1, '2022-11-02 17:49:56.860000', 'hxy', 'hxy', '2022-11-02 17:49:56.860000', 0);
INSERT INTO `sys_user_group` VALUES (473221598745931776, '测试用户1', 'testUser', '', NULL, 1, '2022-12-06 16:10:16.498000', 'hxy', 'hxy', '2022-12-06 16:10:16.498000', 0);

-- ----------------------------
-- Table structure for sys_user_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_sys_role`;
CREATE TABLE `sys_user_sys_role`  (
  `sys_user_id` bigint(20) NOT NULL,
  `sys_role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`sys_user_id`, `sys_role_id`) USING BTREE,
  INDEX `ix_user_role_user`(`sys_user_id`) USING BTREE,
  INDEX `ix_user_role_role`(`sys_role_id`) USING BTREE,
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`sys_role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`sys_user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_sys_role
-- ----------------------------
INSERT INTO `sys_user_sys_role` VALUES (417805766834749440, 435);
INSERT INTO `sys_user_sys_role` VALUES (417805766834749440, 435165139135348736);
INSERT INTO `sys_user_sys_role` VALUES (417805766834749440, 459093735222489088);
INSERT INTO `sys_user_sys_role` VALUES (436243035648110592, 435165139135348736);
INSERT INTO `sys_user_sys_role` VALUES (473195416688209920, 435);
INSERT INTO `sys_user_sys_role` VALUES (473195416688209920, 435165139135348736);
INSERT INTO `sys_user_sys_role` VALUES (473220759327289344, 435165139135348736);

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `id` bigint(20) NOT NULL,
  `group_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组名称',
  `comment` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` bigint(20) NOT NULL,
  `when_created` datetime(6) NOT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `when_modified` datetime(6) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_test_group_name`(`group_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'test' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES (427821887222648832, 'fake_data', 'fake_data', 1, '2022-08-03 09:28:01.781000', 'hxy', 'hxy', '2022-08-03 09:28:01.781000', 0);
INSERT INTO `test` VALUES (427821929845166080, 'fake_data2', 'fake_data2', 1, '2022-08-03 09:28:11.949000', 'hxy', 'hxy', '2022-08-03 09:28:11.949000', 0);
INSERT INTO `test` VALUES (427821948161691648, 'fake26', NULL, 1, '2022-08-03 09:28:16.316000', 'hxy', 'hxy', '2022-08-03 09:31:57.997000', 0);

SET FOREIGN_KEY_CHECKS = 1;
