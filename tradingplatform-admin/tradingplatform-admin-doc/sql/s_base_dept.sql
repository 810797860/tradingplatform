/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : myshtp

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2018-10-25 20:09:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for s_base_dept
-- ----------------------------
DROP TABLE IF EXISTS `s_base_dept`;
CREATE TABLE `s_base_dept` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` bigint(11) DEFAULT NULL COMMENT '父部门id',
  `pids` varchar(255) DEFAULT NULL COMMENT '父级ids',
  `simplename` varchar(45) DEFAULT NULL COMMENT '简称',
  `fullname` varchar(255) DEFAULT NULL COMMENT '全称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '版本（乐观锁保留字段）',
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of s_base_dept
-- ----------------------------
INSERT INTO `s_base_dept` VALUES ('24', '1', '0', '[0],', '总公司', '总公司', '', null, null, null, '\0', null, '2018-09-20 15:17:06', null, null);
INSERT INTO `s_base_dept` VALUES ('25', '2', '24', '[0],[24],', '开发部', '开发部', '', null, null, null, '\0', null, '2018-09-20 15:17:06', null, null);
INSERT INTO `s_base_dept` VALUES ('26', '3', '24', '[0],[24],', '运营部', '运营部', '', null, null, null, '\0', null, '2018-09-20 15:17:06', null, null);
INSERT INTO `s_base_dept` VALUES ('27', '4', '24', '[0],[24],', '战略部', '战略部', '', null, null, null, '\0', null, '2018-09-20 15:17:06', null, null);

-- ----------------------------
-- Table structure for s_base_dict
-- ----------------------------
DROP TABLE IF EXISTS `s_base_dict`;
CREATE TABLE `s_base_dict` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` bigint(11) DEFAULT NULL COMMENT '父级字典',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `code` varchar(255) DEFAULT NULL COMMENT '值',
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典表';

-- ----------------------------
-- Records of s_base_dict
-- ----------------------------
INSERT INTO `s_base_dict` VALUES ('50', '0', '0', '性别', null, 'sys_sex', null, null, '\0', null, '2018-09-20 15:22:44', null, '2018-09-20 15:23:54');
INSERT INTO `s_base_dict` VALUES ('51', '1', '50', '男', null, '1', null, null, '\0', null, '2018-09-20 15:22:44', null, '2018-09-20 15:23:54');
INSERT INTO `s_base_dict` VALUES ('52', '2', '50', '女', null, '2', null, null, '\0', null, '2018-09-20 15:22:44', null, '2018-09-20 15:23:54');
INSERT INTO `s_base_dict` VALUES ('53', '0', '0', '状态', null, 'sys_state', null, null, '\0', null, '2018-09-20 15:22:44', null, '2018-09-20 15:23:54');
INSERT INTO `s_base_dict` VALUES ('54', '1', '53', '启用', null, '1', null, null, '\0', null, '2018-09-20 15:22:44', null, '2018-09-20 15:23:54');
INSERT INTO `s_base_dict` VALUES ('55', '2', '53', '禁用', null, '2', null, null, '\0', null, '2018-09-20 15:22:44', null, '2018-09-20 15:23:54');
INSERT INTO `s_base_dict` VALUES ('56', '0', '0', '账号状态', null, 'account_state', null, null, '\0', null, '2018-09-20 15:22:44', null, '2018-09-20 15:23:54');
INSERT INTO `s_base_dict` VALUES ('57', '1', '56', '启用', null, '1', null, null, '\0', null, '2018-09-20 15:22:44', null, '2018-09-20 15:23:54');
INSERT INTO `s_base_dict` VALUES ('58', '2', '56', '冻结', null, '2', null, null, '\0', null, '2018-09-20 15:22:44', null, '2018-09-20 15:23:54');
INSERT INTO `s_base_dict` VALUES ('59', '3', '56', '已删除', null, '3', null, null, '\0', null, '2018-09-20 15:22:44', null, '2018-09-20 15:23:54');

-- ----------------------------
-- Table structure for s_base_expense
-- ----------------------------
DROP TABLE IF EXISTS `s_base_expense`;
CREATE TABLE `s_base_expense` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `money` decimal(20,2) DEFAULT NULL COMMENT '报销金额',
  `desc` varchar(255) DEFAULT '' COMMENT '描述',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `state` int(11) DEFAULT NULL COMMENT '状态: 1.待提交  2:待审核   3.审核通过 4:驳回',
  `userid` bigint(11) DEFAULT NULL COMMENT '用户id',
  `processId` varchar(255) DEFAULT NULL COMMENT '流程定义id',
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报销表';

-- ----------------------------
-- Records of s_base_expense
-- ----------------------------

-- ----------------------------
-- Table structure for s_base_login_log
-- ----------------------------
DROP TABLE IF EXISTS `s_base_login_log`;
CREATE TABLE `s_base_login_log` (
  `id` bigint(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` bigint(65) DEFAULT NULL COMMENT '管理员id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否执行成功',
  `message` text COMMENT '具体消息',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录ip',
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=220 DEFAULT CHARSET=utf8 COMMENT='登录记录';

-- ----------------------------
-- Records of s_base_login_log
-- ----------------------------
INSERT INTO `s_base_login_log` VALUES ('217', '登录失败日志', null, '2018-09-17 06:18:45', '成功', '账号:admin,账号密码错误', '0:0:0:0:0:0:0:1', null, null, '\0', null, '2018-09-20 15:34:20', null, null);
INSERT INTO `s_base_login_log` VALUES ('218', '登录失败日志', null, '2018-09-17 06:18:57', '成功', '账号:admin,账号密码错误', '0:0:0:0:0:0:0:1', null, null, '\0', null, '2018-09-20 15:34:20', null, null);
INSERT INTO `s_base_login_log` VALUES ('219', '登录日志', '1', '2018-09-17 06:22:02', '成功', null, '0:0:0:0:0:0:0:1', null, null, '\0', null, '2018-09-20 15:34:20', null, null);

-- ----------------------------
-- Table structure for s_base_menu
-- ----------------------------
DROP TABLE IF EXISTS `s_base_menu`;
CREATE TABLE `s_base_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `num` int(65) DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) DEFAULT NULL COMMENT '菜单层级',
  `ismenu` int(11) DEFAULT NULL COMMENT '是否是菜单（1：是  0：不是）',
  `tips` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(65) DEFAULT NULL COMMENT '菜单状态 :  1:启用   0:不启用',
  `isopen` int(11) DEFAULT NULL COMMENT '是否打开:    1:打开   0:不打开',
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of s_base_menu
-- ----------------------------
INSERT INTO `s_base_menu` VALUES ('105', 'system', '0', '[0],', '系统管理', 'fa-user', '#', '4', '1', '1', null, '1', '1', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('106', 'mgr', 'system', '[0],[system],', '用户管理', '', '/mgr', '1', '2', '1', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('107', 'mgr_add', 'mgr', '[0],[system],[mgr],', '添加用户', null, '/mgr/add', '1', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('108', 'mgr_edit', 'mgr', '[0],[system],[mgr],', '修改用户', null, '/mgr/edit', '2', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('109', 'mgr_delete', 'mgr', '[0],[system],[mgr],', '删除用户', null, '/mgr/delete', '3', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('110', 'mgr_reset', 'mgr', '[0],[system],[mgr],', '重置密码', null, '/mgr/reset', '4', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('111', 'mgr_freeze', 'mgr', '[0],[system],[mgr],', '冻结用户', null, '/mgr/freeze', '5', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('112', 'mgr_unfreeze', 'mgr', '[0],[system],[mgr],', '解除冻结用户', null, '/mgr/unfreeze', '6', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('113', 'mgr_setRole', 'mgr', '[0],[system],[mgr],', '分配角色', null, '/mgr/setRole', '7', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('114', 'role', 'system', '[0],[system],', '角色管理', null, '/role', '2', '2', '1', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('115', 'role_add', 'role', '[0],[system],[role],', '添加角色', null, '/role/add', '1', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('116', 'role_edit', 'role', '[0],[system],[role],', '修改角色', null, '/role/edit', '2', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('117', 'role_remove', 'role', '[0],[system],[role],', '删除角色', null, '/role/remove', '3', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('118', 'role_setAuthority', 'role', '[0],[system],[role],', '配置权限', null, '/role/setAuthority', '4', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('119', 'menu', 'system', '[0],[system],', '菜单管理', null, '/menu', '4', '2', '1', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('120', 'menu_add', 'menu', '[0],[system],[menu],', '添加菜单', null, '/menu/add', '1', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('121', 'menu_edit', 'menu', '[0],[system],[menu],', '修改菜单', null, '/menu/edit', '2', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('122', 'menu_remove', 'menu', '[0],[system],[menu],', '删除菜单', null, '/menu/remove', '3', '3', '0', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('128', 'log', 'system', '[0],[system],', '业务日志', null, '/log', '6', '2', '1', null, '1', '0', null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('130', 'druid', 'system', '[0],[system],', '监控管理', null, '/druid', '7', '2', '1', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('131', 'dept', 'system', '[0],[system],', '部门管理', null, '/dept', '3', '2', '1', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('132', 'dict', 'system', '[0],[system],', '字典管理', null, '/dict', '4', '2', '1', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('133', 'loginLog', 'system', '[0],[system],', '登录日志', null, '/loginLog', '6', '2', '1', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('134', 'log_clean', 'log', '[0],[system],[log],', '清空日志', null, '/log/delLog', '3', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('135', 'dept_add', 'dept', '[0],[system],[dept],', '添加部门', null, '/dept/add', '1', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('136', 'dept_update', 'dept', '[0],[system],[dept],', '修改部门', null, '/dept/update', '1', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('137', 'dept_delete', 'dept', '[0],[system],[dept],', '删除部门', null, '/dept/delete', '1', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('138', 'dict_add', 'dict', '[0],[system],[dict],', '添加字典', null, '/dict/add', '1', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('139', 'dict_update', 'dict', '[0],[system],[dict],', '修改字典', null, '/dict/update', '1', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('140', 'dict_delete', 'dict', '[0],[system],[dict],', '删除字典', null, '/dict/delete', '1', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('141', 'notice', 'system', '[0],[system],', '通知管理', null, '/notice', '9', '2', '1', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('142', 'notice_add', 'notice', '[0],[system],[notice],', '添加通知', null, '/notice/add', '1', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('143', 'notice_update', 'notice', '[0],[system],[notice],', '修改通知', null, '/notice/update', '2', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('144', 'notice_delete', 'notice', '[0],[system],[notice],', '删除通知', null, '/notice/delete', '3', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('145', 'hello', '0', '[0],', '通知', 'fa-rocket', '/notice/hello', '1', '1', '1', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('148', 'code', '0', '[0],', '代码生成', 'fa-code', '/code', '3', '1', '1', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('149', 'api_mgr', '0', '[0],', '接口文档', 'fa-leaf', '/swagger-ui.html', '2', '1', '1', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('150', 'to_menu_edit', 'menu', '[0],[system],[menu],', '菜单编辑跳转', '', '/menu/menu_edit', '4', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('151', 'menu_list', 'menu', '[0],[system],[menu],', '菜单列表', '', '/menu/list', '5', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('152', 'to_dept_update', 'dept', '[0],[system],[dept],', '修改部门跳转', '', '/dept/dept_update', '4', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('153', 'dept_list', 'dept', '[0],[system],[dept],', '部门列表', '', '/dept/list', '5', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('154', 'dept_detail', 'dept', '[0],[system],[dept],', '部门详情', '', '/dept/detail', '6', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('155', 'to_dict_edit', 'dict', '[0],[system],[dict],', '修改菜单跳转', '', '/dict/dict_edit', '4', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('156', 'dict_list', 'dict', '[0],[system],[dict],', '字典列表', '', '/dict/list', '5', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('157', 'dict_detail', 'dict', '[0],[system],[dict],', '字典详情', '', '/dict/detail', '6', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('158', 'log_list', 'log', '[0],[system],[log],', '日志列表', '', '/log/list', '2', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('159', 'log_detail', 'log', '[0],[system],[log],', '日志详情', '', '/log/detail', '3', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('160', 'del_login_log', 'loginLog', '[0],[system],[loginLog],', '清空登录日志', '', '/loginLog/delLoginLog', '1', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('161', 'login_log_list', 'loginLog', '[0],[system],[loginLog],', '登录日志列表', '', '/loginLog/list', '2', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('162', 'to_role_edit', 'role', '[0],[system],[role],', '修改角色跳转', '', '/role/role_edit', '5', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('163', 'to_role_assign', 'role', '[0],[system],[role],', '角色分配跳转', '', '/role/role_assign', '6', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('164', 'role_list', 'role', '[0],[system],[role],', '角色列表', '', '/role/list', '7', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('165', 'to_assign_role', 'mgr', '[0],[system],[mgr],', '分配角色跳转', '', '/mgr/role_assign', '8', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('166', 'to_user_edit', 'mgr', '[0],[system],[mgr],', '编辑用户跳转', '', '/mgr/user_edit', '9', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');
INSERT INTO `s_base_menu` VALUES ('167', 'mgr_list', 'mgr', '[0],[system],[mgr],', '用户列表', '', '/mgr/list', '10', '3', '0', null, '1', null, null, null, '\0', null, '2018-09-20 15:57:21', null, '2018-09-20 15:58:36');

-- ----------------------------
-- Table structure for s_base_notice
-- ----------------------------
DROP TABLE IF EXISTS `s_base_notice`;
CREATE TABLE `s_base_notice` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `content` text COMMENT '内容',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='通知表';

-- ----------------------------
-- Records of s_base_notice
-- ----------------------------
INSERT INTO `s_base_notice` VALUES ('6', '世界', '10', '欢迎使用Guns管理系统', '2017-01-11 08:53:20', '1', null, null, '\0', null, null, null, '2018-09-21 14:17:45');
INSERT INTO `s_base_notice` VALUES ('8', '你好', null, '你好', '2017-05-10 19:28:57', '1', null, null, '\0', null, null, null, '2018-09-21 14:17:45');

-- ----------------------------
-- Table structure for s_base_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `s_base_operation_log`;
CREATE TABLE `s_base_operation_log` (
  `id` bigint(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logtype` varchar(255) DEFAULT NULL COMMENT '日志类型',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` bigint(65) DEFAULT NULL COMMENT '用户id',
  `classname` varchar(255) DEFAULT NULL COMMENT '类名称',
  `method` text COMMENT '方法名称',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否成功',
  `message` text COMMENT '备注',
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Records of s_base_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for s_base_relation
-- ----------------------------
DROP TABLE IF EXISTS `s_base_relation`;
CREATE TABLE `s_base_relation` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menuid` bigint(11) DEFAULT NULL COMMENT '菜单id',
  `roleid` bigint(11) DEFAULT NULL COMMENT '角色id',
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3792 DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of s_base_relation
-- ----------------------------
INSERT INTO `s_base_relation` VALUES ('3377', '105', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3378', '106', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3379', '107', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3380', '108', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3381', '109', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3382', '110', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3383', '111', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3384', '112', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3385', '113', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3386', '114', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3387', '115', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3388', '116', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3389', '117', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3390', '118', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3391', '119', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3392', '120', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3393', '121', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3394', '122', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3395', '150', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3396', '151', '5', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3737', '105', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3738', '106', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3739', '107', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3740', '108', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3741', '109', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3742', '110', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3743', '111', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3744', '112', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3745', '113', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3746', '165', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3747', '166', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3748', '167', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3749', '114', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3750', '115', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3751', '116', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3752', '117', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3753', '118', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3754', '162', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3755', '163', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3756', '164', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3757', '119', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3758', '120', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3759', '121', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3760', '122', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3761', '150', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3762', '151', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3763', '128', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3764', '134', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3765', '158', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3766', '159', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3767', '130', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3768', '131', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3769', '135', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3770', '136', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3771', '137', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3772', '152', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3773', '153', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3774', '154', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3775', '132', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3776', '138', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3777', '139', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3778', '140', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3779', '155', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3780', '156', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3781', '157', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3782', '133', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3783', '160', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3784', '161', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3785', '141', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3786', '142', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3787', '143', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3788', '144', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3789', '145', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3790', '148', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');
INSERT INTO `s_base_relation` VALUES ('3791', '149', '1', null, null, '\0', null, '2018-09-21 16:28:41', null, '2018-09-21 16:29:52');

-- ----------------------------
-- Table structure for s_base_resources
-- ----------------------------
DROP TABLE IF EXISTS `s_base_resources`;
CREATE TABLE `s_base_resources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '资源名称',
  `resUrl` varchar(255) DEFAULT NULL COMMENT '资源url',
  `type` int(11) DEFAULT NULL COMMENT '资源类型   1:菜单    2：按钮',
  `parentId` int(11) DEFAULT NULL COMMENT '父资源',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_base_resources
-- ----------------------------
INSERT INTO `s_base_resources` VALUES ('1', '系统设置', '/system', '0', '0', '1', null, null, '\0', null, '2018-10-20 17:08:02', null, '2018-10-20 17:08:50');
INSERT INTO `s_base_resources` VALUES ('2', '用户管理', '/usersPage', '1', '1', '2', null, null, '\0', null, '2018-10-20 17:08:02', null, '2018-10-20 17:08:50');
INSERT INTO `s_base_resources` VALUES ('3', '角色管理', '/rolesPage', '1', '1', '3', null, null, '\0', null, '2018-10-20 17:08:02', null, '2018-10-20 17:08:50');
INSERT INTO `s_base_resources` VALUES ('4', '资源管理', '/resourcesPage', '1', '1', '4', null, null, '\0', null, '2018-10-20 17:08:02', null, '2018-10-20 17:08:50');
INSERT INTO `s_base_resources` VALUES ('5', '添加用户', '/users/add', '2', '2', '5', null, null, '\0', null, '2018-10-20 17:08:02', null, '2018-10-20 17:08:50');
INSERT INTO `s_base_resources` VALUES ('6', '删除用户', '/users/delete', '2', '2', '6', null, null, '\0', null, '2018-10-20 17:08:02', null, '2018-10-20 17:08:50');
INSERT INTO `s_base_resources` VALUES ('7', '添加角色', '/roles/add', '2', '3', '7', null, null, '\0', null, '2018-10-20 17:08:02', null, '2018-10-20 17:08:50');
INSERT INTO `s_base_resources` VALUES ('8', '删除角色', '/roles/delete', '2', '3', '8', null, null, '\0', null, '2018-10-20 17:08:02', null, '2018-10-20 17:08:50');
INSERT INTO `s_base_resources` VALUES ('9', '添加资源', '/resources/add', '2', '4', '9', null, null, '\0', null, '2018-10-20 17:08:02', null, '2018-10-20 17:08:50');
INSERT INTO `s_base_resources` VALUES ('10', '删除资源', '/resources/delete', '2', '4', '10', null, null, '\0', null, '2018-10-20 17:08:02', null, '2018-10-20 17:08:50');
INSERT INTO `s_base_resources` VALUES ('11', '分配角色', '/users/saveUserRoles', '2', '2', '11', null, null, '\0', null, '2018-10-20 17:08:02', null, '2018-10-20 17:08:50');
INSERT INTO `s_base_resources` VALUES ('13', '分配权限', '/roles/saveRoleResources', '2', '3', '12', null, null, '\0', null, '2018-10-20 17:08:02', null, '2018-10-20 17:08:50');

-- ----------------------------
-- Table structure for s_base_role
-- ----------------------------
DROP TABLE IF EXISTS `s_base_role`;
CREATE TABLE `s_base_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `roleDesc` varchar(255) DEFAULT NULL,
  `num` int(11) DEFAULT NULL COMMENT '序号',
  `pid` bigint(11) DEFAULT NULL COMMENT '父角色id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `deptid` bigint(11) DEFAULT NULL COMMENT '部门名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(255) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of s_base_role
-- ----------------------------
INSERT INTO `s_base_role` VALUES ('1', '管理员', null, null, null, null, null, null, null, null, '\0', null, '2018-10-24 15:20:09', null, '2018-10-24 15:20:09');
INSERT INTO `s_base_role` VALUES ('2', '普通用户', null, null, null, null, null, null, null, null, '\0', null, '2018-10-24 15:20:18', null, '2018-10-24 15:20:18');
INSERT INTO `s_base_role` VALUES ('3', '超级管理员', null, null, null, null, null, null, null, null, '\0', null, '2018-10-24 15:20:25', null, '2018-10-24 15:20:25');

-- ----------------------------
-- Table structure for s_base_role_resources
-- ----------------------------
DROP TABLE IF EXISTS `s_base_role_resources`;
CREATE TABLE `s_base_role_resources` (
  `roleId` int(11) NOT NULL,
  `resourcesId` int(11) NOT NULL,
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`roleId`,`resourcesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_base_role_resources
-- ----------------------------
INSERT INTO `s_base_role_resources` VALUES ('1', '2', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('1', '3', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('1', '4', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('1', '5', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('1', '6', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('1', '7', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('1', '8', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('1', '9', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('1', '10', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('1', '11', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('1', '13', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('2', '2', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('2', '3', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('2', '4', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('2', '9', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('3', '2', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('3', '3', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('3', '4', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('3', '5', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('3', '7', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('3', '8', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('3', '9', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('3', '10', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');
INSERT INTO `s_base_role_resources` VALUES ('9', '9', null, null, '\0', null, '2018-10-20 17:20:48', null, '2018-10-20 17:26:49');

-- ----------------------------
-- Table structure for s_base_user
-- ----------------------------
DROP TABLE IF EXISTS `s_base_user`;
CREATE TABLE `s_base_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account` varchar(45) DEFAULT NULL COMMENT '账号',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',
  `user_name` varchar(45) DEFAULT NULL COMMENT '名字',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `roleId` varchar(255) DEFAULT NULL COMMENT '角色id',
  `deptId` bigint(20) DEFAULT NULL COMMENT '部门id',
  `status` int(11) DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `version` int(11) DEFAULT NULL COMMENT '保留字段',
  `enable` bit(1) DEFAULT b'0' COMMENT '是否启用',
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of s_base_user
-- ----------------------------
INSERT INTO `s_base_user` VALUES ('1', 'girl.gif', 'admin', '3ef7164d1f6167cb9f2658c07d3c2f0a', '8pgby', 'admin', '2017-05-05 00:00:00', '0', 'sn93@qq.com', '18200000000', '1', '27', '1', '2016-01-29 08:49:53', '25', '', null, null, '\0', null, '2018-09-21 16:44:55', null, '2018-10-24 22:11:28');
INSERT INTO `s_base_user` VALUES ('44', null, 'test', '45abb7879f6a8268f1ef600e6038ac73', 'ssts3', 'test', '2017-05-01 00:00:00', '1', 'abc@123.com', '', '5', '26', '3', '2017-05-16 20:33:37', null, '\0', null, null, '\0', null, '2018-09-21 16:44:55', null, '2018-10-10 19:59:31');
INSERT INTO `s_base_user` VALUES ('45', null, 'boss', '71887a5ad666a18f709e1d4e693d5a35', '1f7bf', '老板', '2017-12-04 00:00:00', '1', '', '', '1', '24', '1', '2017-12-04 22:24:02', null, '\0', null, null, '\0', null, '2018-09-21 16:44:55', null, '2018-09-21 16:46:28');
INSERT INTO `s_base_user` VALUES ('46', null, 'manager', 'b53cac62e7175637d4beb3b16b2f7915', 'j3cs9', '经理', '2017-12-04 00:00:00', '1', '', '', '1', '24', '1', '2017-12-04 22:24:24', null, '\0', null, null, '\0', null, '2018-09-21 16:44:55', null, '2018-09-21 16:46:28');

-- ----------------------------
-- Table structure for s_base_user_role
-- ----------------------------
DROP TABLE IF EXISTS `s_base_user_role`;
CREATE TABLE `s_base_user_role` (
  `userId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  `uuid` varchar(32) DEFAULT NULL COMMENT '全局id',
  `description` varchar(1024) DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_base_user_role
-- ----------------------------
INSERT INTO `s_base_user_role` VALUES ('23', '2', null, null, '\0', null, '2018-10-20 17:33:53', null, '2018-10-20 17:34:46');
INSERT INTO `s_base_user_role` VALUES ('1', '1', null, null, '\0', null, '2018-10-20 17:33:53', null, '2018-10-20 17:34:46');
INSERT INTO `s_base_user_role` VALUES ('2', '2', null, null, '\0', null, '2018-10-20 17:33:53', null, '2018-10-20 17:34:46');
