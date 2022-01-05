-- apply changes
create table data_dictionary (
  id                            bigint not null,
  deleted                       tinyint(1) default 0 not null,
  group_id                      varchar(10) not null,
  group_name                    varchar(10) not null,
  comment                       varchar(50),
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  constraint uq_data_dictionary_group_id unique (group_id),
  constraint uq_data_dictionary_group_name unique (group_name),
  constraint pk_data_dictionary primary key (id)
);

create table data_dictionary_item (
  id                            bigint not null,
  deleted                       tinyint(1) default 0 not null,
  dictionary_id                 bigint not null,
  item_value                    varchar(100) not null,
  item_key                      varchar(100) not null,
  comment                       varchar(100),
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  constraint pk_data_dictionary_item primary key (id)
);

create table role (
  id                            bigint not null,
  deleted                       tinyint(1) default 0 not null,
  role_name                     varchar(20) not null,
  role_code                     varchar(20) not null,
  comment                       varchar(200),
  parent_id                     bigint,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  constraint uq_role_role_name unique (role_name),
  constraint uq_role_role_code unique (role_code),
  constraint pk_role primary key (id)
);

create table user (
  id                            bigint not null,
  deleted                       tinyint(1) default 0 not null,
  name                          varchar(100) not null,
  user_name                     varchar(100) not null,
  password                      varchar(100) not null,
  phone                         varchar(20),
  mobile_phone                  varchar(20),
  email                         varchar(50),
  user_group_id                 bigint,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
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
  deleted                       tinyint(1) default 0 not null,
  group_name                    varchar(10) not null,
  group_code                    varchar(10) not null,
  comment                       varchar(200),
  parent_id                     bigint,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  constraint uq_user_group_group_name unique (group_name),
  constraint uq_user_group_group_code unique (group_code),
  constraint pk_user_group primary key (id)
);

create index ix_data_dictionary_item_dictionary_id on data_dictionary_item (dictionary_id);
alter table data_dictionary_item add constraint fk_data_dictionary_item_dictionary_id foreign key (dictionary_id) references data_dictionary (id) on delete restrict on update restrict;

create index ix_role_parent_id on role (parent_id);
alter table role add constraint fk_role_parent_id foreign key (parent_id) references role (id) on delete restrict on update restrict;

create index ix_user_user_group_id on user (user_group_id);
alter table user add constraint fk_user_user_group_id foreign key (user_group_id) references user_group (id) on delete restrict on update restrict;

create index ix_user_role_user on user_role (user_id);
alter table user_role add constraint fk_user_role_user foreign key (user_id) references user (id) on delete restrict on update restrict;

create index ix_user_role_role on user_role (role_id);
alter table user_role add constraint fk_user_role_role foreign key (role_id) references role (id) on delete restrict on update restrict;

create index ix_user_group_parent_id on user_group (parent_id);
alter table user_group add constraint fk_user_group_parent_id foreign key (parent_id) references user_group (id) on delete restrict on update restrict;

