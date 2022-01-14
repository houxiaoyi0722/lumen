-- apply changes
create table user_group_role (
  user_group_id                 bigint not null,
  role_id                       bigint not null,
  constraint pk_user_group_role primary key (user_group_id,role_id)
);

create index ix_user_group_role_user_group on user_group_role (user_group_id);
alter table user_group_role add constraint fk_user_group_role_user_group foreign key (user_group_id) references user_group (id) on delete restrict on update restrict;

create index ix_user_group_role_role on user_group_role (role_id);
alter table user_group_role add constraint fk_user_group_role_role foreign key (role_id) references role (id) on delete restrict on update restrict;

