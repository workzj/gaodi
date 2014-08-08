delimiter $$

CREATE TABLE `market_compare` (
  `idmarket_compare` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `projectId` bigint(20) unsigned NOT NULL COMMENT '项目ID，关联项目表\\\\\\\\n',
  `cid` tinyint(3) unsigned DEFAULT NULL COMMENT '1、A实例；2、B实例；3、C实例。\\\\\\\\\\\\\\\\n内容的横向列号',
  `row` tinyint(3) unsigned DEFAULT NULL COMMENT '1、坐落；2、交易日期；3、交易情况；4、交易价格；5、商服繁华度；6、景观；7、交通条件；8、区域环境；9、基础设施；10、建筑结构；11、户型；12、采光、通风；13、建筑装修；14、建成年份；15、小区环境；16、建筑朝向；17、楼层；18、配套设施。',
  `type` tinyint(3) unsigned DEFAULT NULL COMMENT '1、记录参数；2、计算参数；3、结果',
  `value` varchar(45) DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`idmarket_compare`),
  UNIQUE KEY `idmarket_compare_UNIQUE` (`idmarket_compare`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8 COMMENT='市场比较法'$$

SELECT * FROM test.market_compare;