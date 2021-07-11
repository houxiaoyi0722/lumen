-- apply changes
create table data_dictionary (
  id                            bigint not null,
  deleted                       tinyint(1) default 0 not null,
  group_id                      varchar(10) not null,
  group_name                    varchar(50) not null,
  comment                       varchar(50),
  version                       bigint not null,
  when_created                  datetime(6) not null,
  created_by                    varchar(255) not null,
  modified_by                   varchar(255) not null,
  when_modified                 datetime(6) not null,
  constraint pk_data_dictionary primary key (id)
);

create table data_dictionary_item (
  id                            bigint not null,
  deleted                       tinyint(1) default 0 not null,
  data_dictionary_id            bigint not null,
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

create index ix_data_dictionary_item_data_dictionary_id on data_dictionary_item (data_dictionary_id);
alter table data_dictionary_item add constraint fk_data_dictionary_item_data_dictionary_id foreign key (data_dictionary_id) references data_dictionary (id) on delete restrict on update restrict;

