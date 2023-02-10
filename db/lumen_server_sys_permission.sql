create table sys_permission
(
    id            bigint               not null
        primary key,
    code          varchar(100)         null comment '权限code',
    name          varchar(100)         null comment '权限名称',
    comment       varchar(200)         null comment '备注',
    router_id     bigint               null,
    version       bigint               not null,
    when_created  datetime(6)          not null,
    created_by    varchar(255)         not null,
    modified_by   varchar(255)         not null,
    when_modified datetime(6)          not null,
    deleted       tinyint(1) default 0 not null,
    constraint fk_permissions_router_id
        foreign key (router_id) references sys_router (id)
)
    comment '操作权限表';

create index ix_permissions_router_id
    on sys_permission (router_id);

INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (1, 'userManage-1111222', '1111112222', '111', 459094420135555072, 2, '2023-01-12 19:52:29', 'hxy', 'hxy', '2023-01-31 16:06:31.017000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (2, '222', '22222', '2222', 459094420135555072, 2, '2023-01-12 19:52:29', 'hxy', 'hxy', '2023-01-31 16:06:31.023000', 1);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (493514373408194560, 'user-111', 'userManage-111', null, 459094420135555072, 1, '2023-01-31 16:06:30.989000', 'hxy', 'hxy', '2023-01-31 16:06:30.989000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (493518197678112768, 'user-222', 'userManage-222', null, 459094420135555072, 1, '2023-01-31 16:21:42.768000', 'hxy', 'hxy', '2023-01-31 16:23:21.951000', 1);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (493518694522781696, 'role-1', 'roleMenage-1', null, 459013174491557888, 1, '2023-01-31 16:23:41.235000', 'hxy', 'hxy', '2023-01-31 16:23:41.235000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (493518697697869824, 'role-1', 'roleMenage-1', null, 459013174491557888, 1, '2023-01-31 16:23:41.992000', 'hxy', 'hxy', '2023-01-31 16:24:16.707000', 1);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463082397696, '阿萨达撒', 'as发斯蒂芬', null, null, 1, '2023-02-02 15:00:12.719000', 'hxy', 'hxy', '2023-02-02 15:00:12.719000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463183060992, ' 大萨达', '驱蚊器若所', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463187255296, '驱蚊器翁', '驱蚊器翁', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463187255297, '5675675', '345345346', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463187255298, '6457567', '3645645', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463191449600, '123123', '234234', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463191449601, '2423', '123', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463191449602, '1234', '1234', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463195643904, '123', '123', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463195643905, '999', '999', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463195643906, '888', '888', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463199838208, '777', '667', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463199838209, '666', '666', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463199838210, '444', '444', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463199838211, '555', '55', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463204032512, '33', '333', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463204032513, '22', '22', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);
INSERT INTO lumen_server.sys_permission (id, code, name, comment, router_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (494222463204032514, '11', '11', null, null, 1, '2023-02-02 15:00:12.726000', 'hxy', 'hxy', '2023-02-02 15:00:12.726000', 0);