create table sys_user_ext
(
    id            bigint               not null
        primary key,
    avatar_id     bigint               null,
    gender        varchar(20)          null comment '性别',
    birthday      datetime(6)          null comment '出生日期',
    intro         varchar(200)         null comment '简介',
    phone         varchar(20)          null comment '电话',
    mobile_phone  varchar(20)          null comment '移动电话',
    address       varchar(200)         null comment '地址',
    email         varchar(50)          null comment '邮箱地址',
    user_id       bigint               null,
    version       bigint               not null,
    when_created  datetime(6)          not null,
    created_by    varchar(255)         not null,
    modified_by   varchar(255)         not null,
    when_modified datetime(6)          not null,
    deleted       tinyint(1) default 0 not null,
    constraint uq_user_ext_avatar_id
        unique (avatar_id),
    constraint uq_user_ext_user_id
        unique (user_id),
    constraint fk_user_ext_avatar_id
        foreign key (avatar_id) references sys_storage (id),
    constraint fk_user_ext_user_id
        foreign key (user_id) references sys_user (id)
)
    comment '用户扩展信息表';

INSERT INTO lumen_server.sys_user_ext (id, avatar_id, gender, birthday, intro, phone, mobile_phone, address, email, user_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (475361018735443968, 475411726935474176, 'MAN', '2022-12-07 00:00:00', '123', '123', '123', '123', '123', 417805766834749440, 1, '2022-12-12 13:51:33.971000', 'hxy', 'hxy', '2022-12-12 17:13:05.231000', 0);
INSERT INTO lumen_server.sys_user_ext (id, avatar_id, gender, birthday, intro, phone, mobile_phone, address, email, user_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (476043666319421440, 476043658492850176, null, null, null, null, null, null, null, 436243035648110592, 1, '2022-12-14 11:04:09.841000', 'hxy', 'hxy', '2022-12-14 11:04:09.841000', 0);