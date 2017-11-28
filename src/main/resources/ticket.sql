CREATE DATABASE ticket_assistant;

USE ticket_assistant;

CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `phone` VARCHAR (50) NOT NULL COMMENT '电话',
  `password` VARCHAR (50) NOT NULL COMMENT '密码',
  `email` VARCHAR (50) DEFAULT NULL COMMENT '邮件',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_monitor_ticket` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `state` tinyint(1) NOT NULL COMMENT '订单状态 1-true-已完成 0-false-票量监控中',
  `ticket_count` int(11) NOT NULL COMMENT '票余量',
  `dpt_station_name` VARCHAR (50) NOT NULL COMMENT '出发站',
  `arr_station_name` VARCHAR (50) NOT NULL COMMENT '目的站',
  `train_num` VARCHAR (128) DEFAULT NULL COMMENT '班次',
  `start_date` int(10) NOT NULL COMMENT '开始时间',
  `seats` VARCHAR (128) DEFAULT NULL COMMENT '座位类别',
  `price` FLOAT NOT NULL COMMENT '本次的价格',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ip` (
  `id` int(11) NOT NULL auto_increment,
  `ip` VARCHAR (50) NOT NULL COMMENT 'IP地址',
  `port` int (11) NOT NULL COMMENT '端口',
  `usable` tinyint(1) NOT NULL COMMENT '今日是否可用 0-不可用 1-可用',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `user` VALUES ('1', '15957159500', 'e10adc3949ba59abbe56e057f20f883e','786007448@qq.com');
INSERT INTO `user` VALUES ('2', '15957159501', 'e10adc3949ba59abbe56e057f20f883e','786007448@qq.com');