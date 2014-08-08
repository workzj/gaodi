delimiter $$

CREATE TABLE `project_file_fy` (
  `idproject_file_fy` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `filepath` varchar(256) DEFAULT NULL COMMENT '文件路径',
  `name` varchar(128) DEFAULT NULL COMMENT '文件名',
  `type` int(10) DEFAULT NULL COMMENT '文件类型',
  `projectcode` bigint(20) unsigned DEFAULT NULL COMMENT '关联项目ID',
  `filesize` int(32) DEFAULT NULL COMMENT '文件大小',
  `userid` int(10) DEFAULT NULL COMMENT '用户ID',
  `uploadTime` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`idproject_file_fy`),
  UNIQUE KEY `idproject_file_fy_UNIQUE` (`idproject_file_fy`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 COMMENT='项目附件表'$$

