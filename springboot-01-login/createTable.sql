create table tb_course
(
	course_id varchar(32) not null comment '课程id，主键'
		primary key,
	course_name varchar(32) not null comment '课程名称',
	author varchar(32) not null comment '课程作者（教师）',
	type varchar(32) not null comment '课程类别',
	tag varchar(100) null comment '课程标签',
	time int default 0 null comment '课程时间，以分钟计时',
	picture varchar(100) null comment '课程图片 路径',
	create_time char(19) not null comment '创建时间',
	remark varchar(1024) null comment '课程描述'
)
comment '课程信息表';

create table tb_course_env
(
	env_id varchar(32) not null comment '环境id，主键'
		primary key,
	env_name varchar(32) not null comment '环境名称',
	remark varchar(1024) null comment '环境描述',
	node_name varchar(20) not null comment '节点名称，只能输入小写字母和数字',
	cpu int not null comment 'cpu大小',
	memsize int not null comment '内存大小，单位为GB',
	create_time char(20) null comment '创建时间',
	creator_id varchar(32) null comment '创建人',
	image_id varchar(32) not null comment '镜像id，外键',
	constraint tb_course_env_tb_course_image_image_id_fk
		foreign key (image_id) references tb_course_image (image_id)
)
comment '实验环境表';

create table tb_course_image
(
	image_id varchar(32) not null comment '镜像id，主键'
		primary key,
	image_name varchar(32) not null comment '镜像名称',
	version varchar(20) not null comment '镜像版本号',
	path varchar(1024) not null comment '镜像上传路径',
	create_time char(20) not null comment '创建时间',
	creato_id varchar(32) not null comment '创建人'
)
comment '镜像信息表';

create table tb_course_lab
(
	lab_id varchar(32) not null comment '课程id，主键'
		primary key,
	course_id varchar(32) not null comment '关联课程的id',
	course_name varchar(32) not null comment '关联课程的名称',
	lab_name varchar(32) not null comment '实验名称',
	env_id varchar(32) not null comment '关联环境id',
	doc_path varchar(256) null comment '实验文档路径',
	remarks varchar(1024) null comment '实验描述',
	section_id int not null comment '第几次实验',
	constraint tb_course_lab_tb_course_course_id_fk
		foreign key (course_id) references tb_course (course_id),
	constraint tb_course_lab_tb_course_env_env_id_fk
		foreign key (env_id) references tb_course_env (env_id)
)
comment '实验信息表，每门课程对应多个实验';

create table tb_role
(
	role_id varchar(32) not null comment '角色id，主键'
		primary key,
	name varchar(32) null comment '角色名称',
	nameZh varchar(32) null comment '角色名称中文'
)
comment '角色表';

create table tb_user
(
	user_id varchar(32) not null comment '用户id，主键'
		primary key,
	user_name varchar(32) not null comment '用户名',
	password varchar(80) not null comment '登录密码',
	phone varchar(12) null comment '用户电话',
	email varchar(64) null comment '用户邮箱',
	state int default 1 not null comment '用户状态：1 正常；2 禁止登陆',
	user_type char default '1' null comment '用户类型：0 管理员；1 老师；2 学生',
	constraint tb_user_user_name_uindex
		unique (user_name)
)
comment '用户信息表';

create table tb_user_lab
(
	user_id varchar(32) not null comment '用户id，主键',
	lab_id varchar(32) not null comment '实验id，主键',
	env_id varchar(32) null comment '实验环境id',
	flag char not null comment '试验完成标志：0 未完成；1 已完成',
	end_time char(20) null comment '实验结束时间',
	start_time char(20) null comment '实验开始时间',
	primary key (user_id, lab_id),
	constraint tb_user_lab_tb_course_env_env_id_fk
		foreign key (env_id) references tb_course_env (env_id),
	constraint tb_user_lab_tb_course_lab_lab_id_fk
		foreign key (lab_id) references tb_course_lab (lab_id),
	constraint tb_user_lab_tb_user_user_id_fk
		foreign key (user_id) references tb_user (user_id)
)
comment '我的实验信息表';

