alter table dictionary_item drop foreign key fk_dictionary_item_dictionary_id;
drop index ix_dictionary_item_dictionary_id on dictionary_item;

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

alter table user drop foreign key fk_user_user_ext_id;

alter table user_role drop foreign key fk_user_role_user;
drop index ix_user_role_user on user_role;

alter table user_role drop foreign key fk_user_role_role;
drop index ix_user_role_role on user_role;

alter table user_ext drop foreign key fk_user_ext_avatar_id;

alter table user_ext drop foreign key fk_user_ext_user_id;

alter table user_group drop foreign key fk_user_group_parent_id;
drop index ix_user_group_parent_id on user_group;

drop table if exists dictionary;

drop table if exists dictionary_item;

drop table if exists job_log;

drop table if exists role;

drop table if exists router;

drop table if exists router_role;

drop table if exists storage;

drop table if exists user;

drop table if exists user_role;

drop table if exists user_ext;

drop table if exists user_group;

drop index ix_job_log_job_name on job_log;
drop index ix_job_log_job_group on job_log;
drop index ix_job_log_status on job_log;
drop index storage_bucket on storage;
drop index suffix on storage;
drop index business_type on storage;
drop index business_code on storage;
drop index user_name on user;
drop index group_code on user_group;
