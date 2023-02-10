create table sys_router
(
    id            bigint               not null
        primary key,
    name          varchar(100)         null comment '路由名称',
    path          varchar(200)         null comment '访问路径',
    redirect      varchar(200)         null comment '相对路径 根目录开始',
    component     varchar(200)         null comment 'component组件',
    mate          varchar(500)         null comment '元数据 json格式',
    description   varchar(300)         null comment '描述',
    hidden        tinyint(1)           null comment '是否隐藏',
    always_show   tinyint(1)           null comment 'alwaysShow',
    order_by      int                  null comment '排序',
    parent_id     bigint               null,
    version       bigint               not null,
    when_created  datetime(6)          not null,
    created_by    varchar(255)         not null,
    modified_by   varchar(255)         not null,
    when_modified datetime(6)          not null,
    deleted       tinyint(1) default 0 not null,
    constraint fk_router_parent_id
        foreign key (parent_id) references sys_router (id)
)
    comment '路由表';

create index ix_router_parent_id
    on sys_router (parent_id);

INSERT INTO lumen_server.sys_router (id, name, path, redirect, component, mate, description, hidden, always_show, order_by, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (435576735858933760, 'systemSetting', '/setting', null, 'Layout', '{"title":"管理设置","icon":"Setting"}', '', 0, 1, 0, null, 1, '2022-08-24 19:03:01.772000', 'hxy', 'hxy', '2022-10-31 14:59:29.449000', 0);
INSERT INTO lumen_server.sys_router (id, name, path, redirect, component, mate, description, hidden, always_show, order_by, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (435587805562519552, 'menuManage', 'menu', null, 'menage/router-manage', '{"title":"菜单管理","icon":"Menu"}', '', 0, 1, 1, 435576735858933760, 1, '2022-08-24 19:47:01', 'hxy', 'hxy', '2022-10-31 15:05:43.230000', 0);
INSERT INTO lumen_server.sys_router (id, name, path, redirect, component, mate, description, hidden, always_show, order_by, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459013174491557888, 'roleMenage', 'role', null, 'menage/role-manage', '{"title":"角色管理","icon":"Avatar"}', '', 0, 1, 2, 435576735858933760, 1, '2022-10-28 11:11:04.167000', 'hxy', 'hxy', '2022-10-28 11:14:17.767000', 0);
INSERT INTO lumen_server.sys_router (id, name, path, redirect, component, mate, description, hidden, always_show, order_by, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459094118854504448, 'userManage', 'user', null, 'menage/role-manage', '{"title":"用户管理","icon":"User"}', '', 0, 1, 0, null, 1, '2022-10-28 16:32:42.816000', 'hxy', 'hxy', '2022-10-28 16:33:54.668000', 1);
INSERT INTO lumen_server.sys_router (id, name, path, redirect, component, mate, description, hidden, always_show, order_by, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459094420135555072, 'userManage', 'user', null, 'menage/user-manage', '{"title":"用户管理","icon":"User"}', '', 0, 1, 3, 435576735858933760, 1, '2022-10-28 16:33:54.649000', 'hxy', 'hxy', '2022-10-31 14:59:36.822000', 0);
INSERT INTO lumen_server.sys_router (id, name, path, redirect, component, mate, description, hidden, always_show, order_by, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (460910610671677440, 'userGroupManage', 'userGroup', null, 'menage/user-group-manage', '{"title":"用户组管理","icon":"UserFilled"}', '', 0, 1, 4, 435576735858933760, 1, '2022-11-02 16:50:48.200000', 'hxy', 'hxy', '2022-11-03 11:26:03.893000', 0);
INSERT INTO lumen_server.sys_router (id, name, path, redirect, component, mate, description, hidden, always_show, order_by, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (496435093176025088, 'diceManage', 'dict', null, 'menage/dict-manage', '{"title":"字典管理","icon":"Notebook"}', '', 0, 1, 5, 435576735858933760, 1, '2023-02-08 17:32:24.824000', 'hxy0722', 'hxy0722', '2023-02-08 17:54:37.454000', 0);