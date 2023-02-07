alter table sys_dict_item drop foreign key fk_sys_dict_item_dictionary_id;
drop index ix_sys_dict_item_dictionary_id on sys_dict_item;

alter table sys_permission drop foreign key fk_sys_permission_router_id;
drop index ix_sys_permission_router_id on sys_permission;

alter table sys_permission_sys_role drop foreign key fk_sys_permission_sys_role_sys_permission;
drop index ix_sys_permission_sys_role_sys_permission on sys_permission_sys_role;

alter table sys_permission_sys_role drop foreign key fk_sys_permission_sys_role_sys_role;
drop index ix_sys_permission_sys_role_sys_role on sys_permission_sys_role;

alter table sys_role drop foreign key fk_sys_role_parent_id;
drop index ix_sys_role_parent_id on sys_role;

alter table sys_router drop foreign key fk_sys_router_parent_id;
drop index ix_sys_router_parent_id on sys_router;

alter table sys_router_sys_role drop foreign key fk_sys_router_sys_role_sys_router;
drop index ix_sys_router_sys_role_sys_router on sys_router_sys_role;

alter table sys_router_sys_role drop foreign key fk_sys_router_sys_role_sys_role;
drop index ix_sys_router_sys_role_sys_role on sys_router_sys_role;

alter table sys_user drop foreign key fk_sys_user_user_group_id;
drop index ix_sys_user_user_group_id on sys_user;

alter table sys_user drop foreign key fk_sys_user_user_ext_id;

alter table sys_user_sys_role drop foreign key fk_sys_user_sys_role_sys_user;
drop index ix_sys_user_sys_role_sys_user on sys_user_sys_role;

alter table sys_user_sys_role drop foreign key fk_sys_user_sys_role_sys_role;
drop index ix_sys_user_sys_role_sys_role on sys_user_sys_role;

alter table sys_user_ext drop foreign key fk_sys_user_ext_avatar_id;

alter table sys_user_ext drop foreign key fk_sys_user_ext_user_id;

alter table sys_user_group drop foreign key fk_sys_user_group_parent_id;
drop index ix_sys_user_group_parent_id on sys_user_group;

drop table if exists sys_dict;

drop table if exists sys_dict_item;

drop table if exists sys_job_log;

drop table if exists sys_permission;

drop table if exists sys_permission_sys_role;

drop table if exists sys_role;

drop table if exists sys_router;

drop table if exists sys_router_sys_role;

drop table if exists sys_storage;

drop table if exists sys_user;

drop table if exists sys_user_sys_role;

drop table if exists sys_user_ext;

drop table if exists sys_user_group;

drop index ix_sys_job_log_job_name on sys_job_log;
drop index ix_sys_job_log_job_group on sys_job_log;
drop index ix_sys_job_log_status on sys_job_log;
drop index storage_bucket on sys_storage;
drop index suffix on sys_storage;
drop index business_type on sys_storage;
drop index business_code on sys_storage;
drop index user_name on sys_user;
drop index group_code on sys_user_group;
