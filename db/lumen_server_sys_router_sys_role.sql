create table sys_router_sys_role
(
    sys_router_id bigint not null,
    sys_role_id   bigint not null,
    primary key (sys_router_id, sys_role_id),
    constraint fk_router_role_role
        foreign key (sys_role_id) references sys_role (id),
    constraint fk_router_role_router
        foreign key (sys_router_id) references sys_router (id)
);

create index ix_router_role_role
    on sys_router_sys_role (sys_role_id);

create index ix_router_role_router
    on sys_router_sys_role (sys_router_id);

INSERT INTO lumen_server.sys_router_sys_role (sys_router_id, sys_role_id) VALUES (435576735858933760, 435165139135348736);
INSERT INTO lumen_server.sys_router_sys_role (sys_router_id, sys_role_id) VALUES (435587805562519552, 435165139135348736);
INSERT INTO lumen_server.sys_router_sys_role (sys_router_id, sys_role_id) VALUES (459013174491557888, 435165139135348736);
INSERT INTO lumen_server.sys_router_sys_role (sys_router_id, sys_role_id) VALUES (459094118854504448, 435165139135348736);
INSERT INTO lumen_server.sys_router_sys_role (sys_router_id, sys_role_id) VALUES (459094420135555072, 435165139135348736);
INSERT INTO lumen_server.sys_router_sys_role (sys_router_id, sys_role_id) VALUES (460910610671677440, 435165139135348736);
INSERT INTO lumen_server.sys_router_sys_role (sys_router_id, sys_role_id) VALUES (496435093176025088, 435165139135348736);