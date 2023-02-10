create table test
(
    id            bigint               not null
        primary key,
    group_name    varchar(10)          not null comment '组名称',
    comment       varchar(50)          null comment '备注',
    version       bigint               not null,
    when_created  datetime(6)          not null,
    created_by    varchar(255)         not null,
    modified_by   varchar(255)         not null,
    when_modified datetime(6)          not null,
    deleted       tinyint(1) default 0 not null,
    constraint uq_test_group_name
        unique (group_name)
)
    comment 'test';

INSERT INTO lumen_server.test (id, group_name, comment, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (427821887222648832, 'fake_data', 'fake_data', 1, '2022-08-03 09:28:01.781000', 'hxy', 'hxy', '2022-08-03 09:28:01.781000', 0);
INSERT INTO lumen_server.test (id, group_name, comment, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (427821929845166080, 'fake_data2', 'fake_data2', 1, '2022-08-03 09:28:11.949000', 'hxy', 'hxy', '2022-08-03 09:28:11.949000', 0);
INSERT INTO lumen_server.test (id, group_name, comment, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (427821948161691648, 'fake26', null, 1, '2022-08-03 09:28:16.316000', 'hxy', 'hxy', '2022-08-03 09:31:57.997000', 0);