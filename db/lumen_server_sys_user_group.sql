create table sys_user_group
(
    id            bigint               not null
        primary key,
    group_name    varchar(10)          not null comment '用户组名称',
    group_code    varchar(10)          not null comment '用户组代码',
    comment       varchar(200)         null comment '备注',
    parent_id     bigint               null,
    version       bigint               not null,
    when_created  datetime(6)          not null,
    created_by    varchar(255)         not null,
    modified_by   varchar(255)         not null,
    when_modified datetime(6)          not null,
    deleted       tinyint(1) default 0 not null,
    constraint uq_user_group_group_code
        unique (group_code),
    constraint uq_user_group_group_name
        unique (group_name),
    constraint fk_user_group_parent_id
        foreign key (parent_id) references sys_user_group (id)
)
    comment '用户组';

create index ix_user_group_parent_id
    on sys_user_group (parent_id);

INSERT INTO lumen_server.sys_user_group (id, group_name, group_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (1, '管理员', 'admingroup', '11', null, 1, '2022-08-26 14:42:46', '1', 'hxy', '2022-11-03 11:21:33.547000', 0);
INSERT INTO lumen_server.sys_user_group (id, group_name, group_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (460925297312346112, '啊啊啊', 'aaaaaa', '', null, 1, '2022-11-02 17:49:09.784000', 'hxy', 'hxy', '2022-11-02 17:49:46.380000', 1);
INSERT INTO lumen_server.sys_user_group (id, group_name, group_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (460925494763401216, 'aaa', 'aaa', 'aasdassssssssss', 1, 1, '2022-11-02 17:49:56.860000', 'hxy', 'hxy', '2022-11-02 17:49:56.860000', 0);
INSERT INTO lumen_server.sys_user_group (id, group_name, group_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (473221598745931776, '测试用户1', 'testUser', '', null, 1, '2022-12-06 16:10:16.498000', 'hxy', 'hxy', '2022-12-06 16:10:16.498000', 0);