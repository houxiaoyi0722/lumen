create table sys_dict_item
(
    id            bigint               not null
        primary key,
    dictionary_id bigint               not null,
    item_value    varchar(100)         not null comment 'value',
    item_key      varchar(100)         not null comment 'key',
    comment       varchar(100)         null comment '备注',
    version       bigint               not null,
    when_created  datetime(6)          not null,
    created_by    varchar(255)         not null,
    modified_by   varchar(255)         not null,
    when_modified datetime(6)          not null,
    deleted       tinyint(1) default 0 not null,
    constraint fk_data_dictionary_item_dictionary_id
        foreign key (dictionary_id) references sys_dict (id)
)
    comment '数据字典明细';

create index ix_data_dictionary_item_dictionary_id
    on sys_dict_item (dictionary_id);

INSERT INTO lumen_server.sys_dict_item (id, dictionary_id, item_value, item_key, comment, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (427893957600153600, 427893957394632704, 'test1-1', '12', 'test1-1', 1, '2022-08-03 14:14:24.683000', 'hxy', 'hxy', '2022-08-03 14:14:24.683000', 0);
INSERT INTO lumen_server.sys_dict_item (id, dictionary_id, item_value, item_key, comment, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (427894882758758400, 427893957394632704, 'test3', '123', 'test3', 1, '2022-08-03 14:18:05.278000', 'hxy', 'hxy', '2022-08-04 09:28:34.422000', 0);
INSERT INTO lumen_server.sys_dict_item (id, dictionary_id, item_value, item_key, comment, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (428209917502623744, 428209917393571840, 'test4', '4', 'test4', 1, '2022-08-04 11:09:55.410000', 'hxy', 'hxy', '2022-08-04 11:09:55.410000', 0);