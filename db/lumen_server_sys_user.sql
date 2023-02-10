create table sys_user
(
    id                      bigint               not null
        primary key,
    name                    varchar(100)         not null comment '姓名',
    username                varchar(100)         not null comment '用户名',
    password                varchar(100)         not null comment '密码',
    enabled                 tinyint(1)           null comment '是否启用',
    account_non_expired     tinyint(1)           null comment '账户未过期',
    account_non_locked      tinyint(1)           null comment '账户锁定',
    credentials_non_expired tinyint(1)           null comment '凭证未过期',
    user_group_id           bigint               null,
    version                 bigint               not null,
    when_created            datetime(6)          not null,
    created_by              varchar(255)         not null,
    modified_by             varchar(255)         not null,
    when_modified           datetime(6)          not null,
    deleted                 tinyint(1) default 0 not null,
    user_ext_id             bigint               null,
    constraint uq_user_name
        unique (name),
    constraint uq_user_password
        unique (password),
    constraint uq_user_user_name
        unique (username),
    constraint fk_user_user_group_id
        foreign key (user_group_id) references sys_user_group (id)
)
    comment '角色表';

create index fk_user_user_ext_id
    on sys_user (user_ext_id);

create index ix_user_user_group_id
    on sys_user (user_group_id);

INSERT INTO lumen_server.sys_user (id, name, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, user_group_id, version, when_created, created_by, modified_by, when_modified, deleted, user_ext_id) VALUES (417805766834749440, 'hxy0722', 'hxy0722', '$2a$10$F5iQr22D2GkpzEv2jv0JrO.YzH62/JISuyVqCbpIn2sU17uF/uzX6', 1, 1, 1, 1, 1, 1, '2022-07-06 18:07:32.589000', 'anonymous', 'hxy', '2023-02-03 09:37:01.852000', 0, 475361018735443968);
INSERT INTO lumen_server.sys_user (id, name, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, user_group_id, version, when_created, created_by, modified_by, when_modified, deleted, user_ext_id) VALUES (436243035648110592, 'admin', 'admin', '$2a$10$S1N6TDSaJjpooVzAZvzvpO75fJQaByNqZd8EL7Ih4TmesGY78cque', 1, 1, 1, 1, 1, 1, '2022-08-26 15:10:40.025000', 'hxy', 'hxy', '2022-12-06 15:39:03.503000', 0, null);
INSERT INTO lumen_server.sys_user (id, name, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, user_group_id, version, when_created, created_by, modified_by, when_modified, deleted, user_ext_id) VALUES (473195416688209920, 'test123', 'test123', '$2a$10$mx04zD0SMmWy9FHEhp69zOlBqsQQX0NB4haeS./zphjsaI4vDsbQ2', 1, 1, 1, 1, 473221598745931776, 1, '2022-12-06 14:26:14.203000', 'hxy', 'hxy', '2022-12-07 18:10:26.304000', 0, null);
INSERT INTO lumen_server.sys_user (id, name, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, user_group_id, version, when_created, created_by, modified_by, when_modified, deleted, user_ext_id) VALUES (473220759327289344, 'test1222', 'test2322', '$2a$10$aMMg0jIiScYLVTdVZtC48eSpMNbutX/0ThU9BZm.pljKlOPQUX8XW', 1, 1, 1, 1, 473221598745931776, 1, '2022-12-06 16:06:56.361000', 'hxy', 'hxy', '2022-12-06 18:00:26.224000', 1, null);