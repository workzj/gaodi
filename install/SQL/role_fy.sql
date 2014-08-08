delimiter $$

CREATE TABLE `role_fy` (
  `idrole_fy` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(45) DEFAULT NULL,
  `role_fycol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idrole_fy`),
  UNIQUE KEY `idrole_fy_UNIQUE` (`idrole_fy`),
  KEY `userid_idx` (`idrole_fy`),
  KEY `test_idx` (`idrole_fy`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='角色表'$$

