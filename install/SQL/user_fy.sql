delimiter $$

CREATE TABLE `user_fy` (
  `iduser_fy` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `nikename` varchar(45) DEFAULT NULL COMMENT '昵称',
  `rolename` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`iduser_fy`),
  UNIQUE KEY `iduser_fy_UNIQUE` (`iduser_fy`),
  KEY `role_idx` (`iduser_fy`),
  KEY `sss_idx` (`iduser_fy`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8$$

