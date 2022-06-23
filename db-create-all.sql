create table data_dictionary (
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
  constraint uq_data_dictionary_group_id unique (group_id),
  constraint uq_data_dictionary_group_name unique (group_name),
  constraint pk_data_dictionary primary key (id)
) comment='数据字典';

create table data_dictionary_item (
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
  constraint pk_data_dictionary_item primary key (id)
) comment='数据字典明细';

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
  constraint uq_role_role_name unique (role_name),
  constraint uq_role_role_code unique (role_code),
  constraint pk_role primary key (id)
) comment='角色表';

create table router (
  id                            bigint not null,
  name                          varchar(100) comment '路由名称',
  path                          varchar(200) comment '访问路径',
  redirect                      varchar(200) comment '相对路径 根目录开始',
  component                     varchar(200) comment 'component组件',
  mate                          varchar(500) comment '元数据 json格式',
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
  user_name                     varchar(100) not null comment '用户名',
  password                      varchar(100) not null comment '密码',
  phone                         varchar(20) comment '电话',
  mobile_phone                  varchar(20) comment '移动电话',
  address                       varchar(200) comment '地址',
  email                         varchar(50) comment '邮箱地址',
  enabled                       tinyint(1) comment '是否启用',
  account_non_expired           tinyint(1) comment '账户未过期',
  account_non_locked            tinyint(1) comment '账户锁定',
  credentials_non_expired       tinyint(1) comment '凭证未过期',
  user_group_id                 bigint,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  deleted                       tinyint(1) default 0 not null,
  constraint uq_user_name unique (name),
  constraint uq_user_user_name unique (user_name),
  constraint uq_user_password unique (password),
  constraint pk_user primary key (id)
) comment='角色表';

create table user_role (
  user_id                       bigint not null,
  role_id                       bigint not null,
  constraint pk_user_role primary key (user_id,role_id)
);

create table user_group (
  id                            bigint not null,
  group_name                    varchar(10) not null comment '用户组名称',
  group_code                    varchar(10) not null comment '用户组代码',
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

create table user_group_role (
  user_group_id                 bigint not null,
  role_id                       bigint not null,
  constraint pk_user_group_role primary key (user_group_id,role_id)
);

create index ix_data_dictionary_item_dictionary_id on data_dictionary_item (dictionary_id);
alter table data_dictionary_item add constraint fk_data_dictionary_item_dictionary_id foreign key (dictionary_id) references data_dictionary (id) on delete restrict on update restrict;

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

create index ix_user_role_user on user_role (user_id);
alter table user_role add constraint fk_user_role_user foreign key (user_id) references user (id) on delete restrict on update restrict;

create index ix_user_role_role on user_role (role_id);
alter table user_role add constraint fk_user_role_role foreign key (role_id) references role (id) on delete restrict on update restrict;

create index ix_user_group_parent_id on user_group (parent_id);
alter table user_group add constraint fk_user_group_parent_id foreign key (parent_id) references user_group (id) on delete restrict on update restrict;

create index ix_user_group_role_user_group on user_group_role (user_group_id);
alter table user_group_role add constraint fk_user_group_role_user_group foreign key (user_group_id) references user_group (id) on delete restrict on update restrict;

create index ix_user_group_role_role on user_group_role (role_id);
alter table user_group_role add constraint fk_user_group_role_role foreign key (role_id) references role (id) on delete restrict on update restrict;

