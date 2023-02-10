create table sys_role
(
    id            bigint               not null
        primary key,
    role_name     varchar(20)          not null comment '角色名称',
    role_code     varchar(20)          not null comment '角色代码',
    comment       varchar(200)         null comment '备注',
    parent_id     bigint               null,
    version       bigint               not null,
    when_created  datetime(6)          not null,
    created_by    varchar(255)         not null,
    modified_by   varchar(255)         not null,
    when_modified datetime(6)          not null,
    deleted       tinyint(1) default 0 not null,
    constraint fk_role_parent_id
        foreign key (parent_id) references sys_role (id)
)
    comment '角色表';

create index ix_role_parent_id
    on sys_role (parent_id);

INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (435, '菜单管理员', 'menuAdmin', '菜单管理员', null, 1, '2022-08-23 15:47:29.470000', 'hxy', 'hxy', '2022-08-23 15:47:29.470000', 0);
INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (435165139135348736, 'admin', 'admin', 'admin', null, 1, '2022-08-23 15:47:29.470000', 'hxy', 'hxy', '2023-01-30 11:03:39.594000', 0);
INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (435171606257188864, 'admin1', 'admin1', '啊啊啊', 435165139135348736, 1, '2022-08-23 16:13:11.356000', 'hxy', 'hxy', '2022-10-28 16:31:11.357000', 1);
INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (435181148793716736, 'admin3', 'admin3', '超级管理员3', 435171606257188864, 1, '2022-08-23 16:51:06.473000', 'hxy', 'hxy', '2022-10-28 16:31:11.357000', 1);
INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (435181148802105344, 'admin4', 'admin4', '超级管理员4', 435165139135348736, 1, '2022-08-23 16:51:06.473000', 'hxy', 'hxy', '2022-10-28 16:05:47.263000', 1);
INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459087206658289664, 'ce是', '杀杀杀', '啊啊啊', null, 1, '2022-10-28 16:05:14.782000', 'hxy', 'hxy', '2022-10-28 16:31:27.924000', 1);
INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459090537438982144, '123', '123', '123', null, 1, '2022-10-28 16:18:28.934000', 'hxy', 'hxy', '2022-10-28 16:31:27.924000', 1);
INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459090685799903232, '1234', '1234', '123', null, 1, '2022-10-28 16:19:04.314000', 'hxy', 'hxy', '2022-10-28 16:30:03.525000', 1);
INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459093504099561472, 'admin4', 'admin4', 'admin4', 435181148793716736, 1, '2022-10-28 16:30:16.249000', 'hxy', 'hxy', '2022-10-28 16:31:11.357000', 1);
INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459093538924867584, 'admin5', 'admin5', 'admin5', 459093504099561472, 1, '2022-10-28 16:30:24.552000', 'hxy', 'hxy', '2022-10-28 16:31:11.357000', 1);
INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459093570197598208, 'admin6', 'admin6', 'admin6', 459093538924867584, 1, '2022-10-28 16:30:32.008000', 'hxy', 'hxy', '2022-10-28 16:31:11.357000', 1);
INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459093636396298240, 'admin7', 'admin7', 'admin7', 459093570197598208, 1, '2022-10-28 16:30:47.791000', 'hxy', 'hxy', '2022-10-28 16:31:11.356000', 1);
INSERT INTO lumen_server.sys_role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459093735222489088, 'admin1', 'admin1', '超级管理员1', 435165139135348736, 1, '2022-10-28 16:31:11.353000', 'hxy', 'hxy', '2022-10-28 16:31:11.353000', 0);