alter table data_dictionary_item drop foreign key fk_data_dictionary_item_dictionary_id;
drop index ix_data_dictionary_item_dictionary_id on data_dictionary_item;

alter table role drop foreign key fk_role_parent_id;
drop index ix_role_parent_id on role;

alter table router drop foreign key fk_router_parent_id;
drop index ix_router_parent_id on router;

alter table router_role drop foreign key fk_router_role_router;
drop index ix_router_role_router on router_role;

alter table router_role drop foreign key fk_router_role_role;
drop index ix_router_role_role on router_role;

alter table user drop foreign key fk_user_user_group_id;
drop index ix_user_user_group_id on user;

alter table user_role drop foreign key fk_user_role_user;
drop index ix_user_role_user on user_role;

alter table user_role drop foreign key fk_user_role_role;
drop index ix_user_role_role on user_role;

alter table user_group drop foreign key fk_user_group_parent_id;
drop index ix_user_group_parent_id on user_group;

alter table user_group_role drop foreign key fk_user_group_role_user_group;
drop index ix_user_group_role_user_group on user_group_role;

alter table user_group_role drop foreign key fk_user_group_role_role;
drop index ix_user_group_role_role on user_group_role;

drop table if exists data_dictionary;

drop table if exists data_dictionary_item;

drop table if exists role;

drop table if exists router;

drop table if exists router_role;

drop table if exists storage;

drop table if exists user;

drop table if exists user_role;

drop table if exists user_group;

drop table if exists user_group_role;

