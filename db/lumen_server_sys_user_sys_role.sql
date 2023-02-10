create table sys_user_sys_role
(
    sys_user_id bigint not null,
    sys_role_id bigint not null,
    primary key (sys_user_id, sys_role_id),
    constraint fk_user_role_role
        foreign key (sys_role_id) references sys_role (id),
    constraint fk_user_role_user
        foreign key (sys_user_id) references sys_user (id)
);

create index ix_user_role_role
    on sys_user_sys_role (sys_role_id);

create index ix_user_role_user
    on sys_user_sys_role (sys_user_id);

INSERT INTO lumen_server.sys_user_sys_role (sys_user_id, sys_role_id) VALUES (417805766834749440, 435);
INSERT INTO lumen_server.sys_user_sys_role (sys_user_id, sys_role_id) VALUES (417805766834749440, 435165139135348736);
INSERT INTO lumen_server.sys_user_sys_role (sys_user_id, sys_role_id) VALUES (417805766834749440, 459093735222489088);
INSERT INTO lumen_server.sys_user_sys_role (sys_user_id, sys_role_id) VALUES (436243035648110592, 435165139135348736);
INSERT INTO lumen_server.sys_user_sys_role (sys_user_id, sys_role_id) VALUES (473195416688209920, 435);
INSERT INTO lumen_server.sys_user_sys_role (sys_user_id, sys_role_id) VALUES (473195416688209920, 435165139135348736);
INSERT INTO lumen_server.sys_user_sys_role (sys_user_id, sys_role_id) VALUES (473220759327289344, 435165139135348736);