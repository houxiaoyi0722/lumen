-- apply changes
create table dictionary (
  id                            bigint not null,
  group_id                      varchar(10) not null comment '组id',
  group_name                    varchar(10) not null comment '组名称',
  comment                       varchar(50) comment '备注',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  deleted                       tinyint(1) default 0 not null,
  constraint uq_dictionary_group_id unique (group_id),
  constraint uq_dictionary_group_name unique (group_name),
  constraint pk_dictionary primary key (id)
) comment='数据字典';

create table dictionary_item (
  id                            bigint not null,
  dictionary_id                 bigint not null,
  item_value                    varchar(100) not null comment 'value',
  item_key                      varchar(100) not null comment 'key',
  comment                       varchar(100) comment '备注',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  deleted                       tinyint(1) default 0 not null,
  constraint pk_dictionary_item primary key (id)
) comment='数据字典明细';

create table job_log (
  id                            bigint not null,
  job_name                      varchar(190) comment 'job名称',
  job_group                     varchar(190) comment 'job分组',
  bean_class                    varchar(250) comment '执行类全限定名',
  job_data_map                  json comment '执行类全限定名',
  cron_expression               varchar(120) comment 'cron',
  status                        varchar(30) comment '状态',
  start_time                    datetime(6) comment '开始时间',
  end_time                      datetime(6) comment '结束时间',
  may_fire_again                tinyint(1) comment '触发器是否会再次触发',
  job_run_time                  bigint comment 'job执行时间,以毫秒为单位',
  result                        json comment 'job执行结果/错误结果',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  deleted                       tinyint(1) default 0 not null,
  constraint pk_job_log primary key (id)
) comment='JobLog执行日志表';

create table permissions (
  id                            bigint not null,
  code                          varchar(100) comment '权限code',
  name                          varchar(100) comment '权限名称',
  comment                       varchar(200) comment '备注',
  role_id                       bigint,
  router_id                     bigint,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  deleted                       tinyint(1) default 0 not null,
  constraint pk_permissions primary key (id)
) comment='操作权限表';

create table role (
  id                            bigint not null,
  role_name                     varchar(20) not null comment '角色名称',
  role_code                     varchar(20) not null comment '角色代码',
  comment                       varchar(200) comment '备注',
  parent_id                     bigint,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  deleted                       tinyint(1) default 0 not null,
  constraint pk_role primary key (id)
) comment='角色表';

create table router (
  id                            bigint not null,
  name                          varchar(100) comment '路由名称',
  path                          varchar(200) comment '访问路径',
  redirect                      varchar(200) comment '跳转路径',
  component                     varchar(200) comment 'component组件',
  mate                          json comment '元数据 对象传入,json格式存储',
  description                   varchar(300) comment '描述',
  hidden                        tinyint(1) comment '是否隐藏',
  always_show                   tinyint(1) comment 'alwaysShow',
  order_by                      integer comment '排序',
  parent_id                     bigint,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  deleted                       tinyint(1) default 0 not null,
  constraint pk_router primary key (id)
) comment='路由表';

create table router_role (
  router_id                     bigint not null,
  role_id                       bigint not null,
  constraint pk_router_role primary key (router_id,role_id)
);

create table storage (
  id                            bigint not null,
  original_file_name            varchar(100) not null comment '原文件名',
  etag                          varchar(100) not null comment 'etag',
  object                        varchar(200) not null comment '对象存储地址',
  storage_bucket                varchar(200) not null comment '存储桶',
  suffix                        varchar(50) comment '文件类型/后缀名',
  content_type                  varchar(50) comment '文件类型/请求头',
  version_id                    varchar(50) comment '版本id',
  size                          bigint(200) comment '文件大小/b',
  business_code                 varchar(50) comment '业务代码',
  business_type                 varchar(50) comment '业务类型',
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  deleted                       tinyint(1) default 0 not null,
  constraint pk_storage primary key (id)
) comment='对象存储管理';

create table user (
  id                            bigint not null,
  name                          varchar(100) not null comment '姓名',
  username                      varchar(100) default '' not null comment '用户名',
  password                      varchar(100) not null comment '密码',
  enabled                       tinyint(1) comment '是否启用',
  account_non_expired           tinyint(1) comment '账户未过期',
  account_non_locked            tinyint(1) comment '账户锁定',
  credentials_non_expired       tinyint(1) comment '凭证未过期',
  user_group_id                 bigint,
  user_ext_id                   bigint,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  deleted                       tinyint(1) default 0 not null,
  constraint uq_user_name unique (name),
  constraint uq_user_username unique (username),
  constraint uq_user_password unique (password),
  constraint uq_user_user_ext_id unique (user_ext_id),
  constraint pk_user primary key (id)
) comment='角色表';

create table user_role (
  user_id                       bigint not null,
  role_id                       bigint not null,
  constraint pk_user_role primary key (user_id,role_id)
);

create table user_ext (
  id                            bigint not null,
  avatar_id                     bigint,
  gender                        varchar(20) comment '性别',
  birthday                      datetime(6) comment '出生日期',
  intro                         varchar(200) comment '简介',
  phone                         varchar(20) comment '电话',
  mobile_phone                  varchar(20) comment '移动电话',
  address                       varchar(200) comment '地址',
  email                         varchar(50) comment '邮箱地址',
  user_id                       bigint,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  deleted                       tinyint(1) default 0 not null,
  constraint uq_user_ext_avatar_id unique (avatar_id),
  constraint uq_user_ext_user_id unique (user_id),
  constraint pk_user_ext primary key (id)
) comment='用户扩展信息表';

create table user_group (
  id                            bigint not null,
  group_name                    varchar(10) not null comment '用户组名称',
  group_code                    varchar(20) not null comment '用户组代码',
  comment                       varchar(200) comment '备注',
  parent_id                     bigint,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  deleted                       tinyint(1) default 0 not null,
  constraint uq_user_group_group_name unique (group_name),
  constraint uq_user_group_group_code unique (group_code),
  constraint pk_user_group primary key (id)
) comment='用户组';

create index ix_job_log_job_name on job_log (job_name);
create index ix_job_log_job_group on job_log (job_group);
create index ix_job_log_status on job_log (status);
create index storage_bucket on storage (storage_bucket);
create index suffix on storage (suffix);
create index business_type on storage (business_type);
create index business_code on storage (business_code);
create index user_name on user (user_name);
create index group_code on user_group (group_code);
create index ix_dictionary_item_dictionary_id on dictionary_item (dictionary_id);
alter table dictionary_item add constraint fk_dictionary_item_dictionary_id foreign key (dictionary_id) references dictionary (id) on delete restrict on update restrict;

create index ix_permissions_role_id on permissions (role_id);
alter table permissions add constraint fk_permissions_role_id foreign key (role_id) references role (id) on delete restrict on update restrict;

create index ix_permissions_router_id on permissions (router_id);
alter table permissions add constraint fk_permissions_router_id foreign key (router_id) references router (id) on delete restrict on update restrict;

create index ix_role_parent_id on role (parent_id);
alter table role add constraint fk_role_parent_id foreign key (parent_id) references role (id) on delete restrict on update restrict;

create index ix_router_parent_id on router (parent_id);
alter table router add constraint fk_router_parent_id foreign key (parent_id) references router (id) on delete restrict on update restrict;

create index ix_router_role_router on router_role (router_id);
alter table router_role add constraint fk_router_role_router foreign key (router_id) references router (id) on delete restrict on update restrict;

create index ix_router_role_role on router_role (role_id);
alter table router_role add constraint fk_router_role_role foreign key (role_id) references role (id) on delete restrict on update restrict;

create index ix_user_user_group_id on user (user_group_id);
alter table user add constraint fk_user_user_group_id foreign key (user_group_id) references user_group (id) on delete restrict on update restrict;

alter table user add constraint fk_user_user_ext_id foreign key (user_ext_id) references user_ext (id) on delete restrict on update restrict;

create index ix_user_role_user on user_role (user_id);
alter table user_role add constraint fk_user_role_user foreign key (user_id) references user (id) on delete restrict on update restrict;

create index ix_user_role_role on user_role (role_id);
alter table user_role add constraint fk_user_role_role foreign key (role_id) references role (id) on delete restrict on update restrict;

alter table user_ext add constraint fk_user_ext_avatar_id foreign key (avatar_id) references storage (id) on delete restrict on update restrict;

alter table user_ext add constraint fk_user_ext_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;

create index ix_user_group_parent_id on user_group (parent_id);
alter table user_group add constraint fk_user_group_parent_id foreign key (parent_id) references user_group (id) on delete restrict on update restrict;

