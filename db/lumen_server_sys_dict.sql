create table sys_dict
(
    id            bigint               not null
        primary key,
    group_id      varchar(10)          not null comment '组id',
    group_name    varchar(10)          not null comment '组名称',
    comment       varchar(50)          null comment '备注',
    version       bigint               not null,
    when_created  datetime(6)          not null,
    created_by    varchar(255)         not null,
    modified_by   varchar(255)         not null,
    when_modified datetime(6)          not null,
    deleted       tinyint(1) default 0 not null,
    constraint uq_data_dictionary_group_id
        unique (group_id),
    constraint uq_data_dictionary_group_name
        unique (group_name)
)
    comment '数据字典';

INSERT INTO lumen_server.sys_dict (id, group_id, group_name, comment, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (427893957394632704, 'test3', 'test3', 'test3', 1, '2022-08-03 14:14:24.654000', 'hxy', 'hxy', '2022-08-09 18:07:33.932000', 1);
INSERT INTO lumen_server.sys_dict (id, group_id, group_name, comment, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (428209917393571840, 'test4', 'test4', 'test4', 1, '2022-08-04 11:09:55.386000', 'hxy', 'hxy', '2022-08-04 11:09:55.386000', 0);