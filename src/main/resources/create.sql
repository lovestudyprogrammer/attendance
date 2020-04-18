create database if not exists attendance default character set utf8 collate utf8_general_ci;

DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';

DROP TABLE IF EXISTS `class_md_user`;
CREATE TABLE `class_md_user` (
  `student_id` int(11) NOT NULL COMMENT '学生id',
  `class_id` int(11) NOT NULL COMMENT '班级id',
  `teacher_id` int(11) NOT NULL COMMENT '老师id',
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `class_id` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生班级中间表';


DROP TABLE IF EXISTS `leave`;
CREATE TABLE `leave` (
  `id` int(11) NOT NULL,
  `stu_id` int(11) NOT NULL COMMENT '学生id',
  `class_id` int(11) NOT NULL COMMENT '班级id',
  `leave_start` datetime NOT NULL COMMENT '请假开始时间',
  `leave_end` datetime NOT NULL COMMENT '请假结束',
  `creat_time` datetime NOT NULL,
  `cause` varchar(255) NOT NULL COMMENT '请假原因',
  `approval_state` int(11) unsigned zerofill NOT NULL COMMENT '审批状态，0为审批中，1为审批通过，2为审批不通过',
  `approval_opinion` varchar(255) DEFAULT NULL COMMENT '审批意见',
  `approval_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '审批时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假表';

DROP TABLE IF EXISTS `punch_clock`;
CREATE TABLE `punch_clock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) NOT NULL COMMENT '班级id',
  `stu_id` int(11) NOT NULL COMMENT '学生id',
--   `punch_clock` int(11) unsigned zerofill NOT NULL COMMENT '打卡状态 0为未打卡 1为打卡',
  `punch_clock_type_id` int(11) NOT NULL COMMENT '考勤类型',
  `create_time` datetime NOT NULL COMMENT '打卡时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考勤记录表';

DROP TABLE IF EXISTS `punch_clock_type`;
CREATE TABLE `punch_clock_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(64) NOT NULL COMMENT '考勤类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考勤类型表，比如：上课考勤，参加集体活动考勤';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL COMMENT '真实姓名',
  `phone` varchar(64) NOT NULL COMMENT '手机号',
  `sex` tinyint(4) NOT NULL COMMENT '性别,0:女,1:男',
  `type` int(11) NOT NULL COMMENT '用户类型,0为管理员，1为学生,2为老师',
  `sno` int(11) DEFAULT NULL COMMENT '学生学号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_phone` (`phone`),
  UNIQUE KEY `u_name_password` (`username`,`password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';