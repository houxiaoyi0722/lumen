create table sys_permission_sys_role
(
    sys_permission_id bigint not null,
    sys_role_id       bigint not null,
    primary key (sys_permission_id, sys_role_id),
    constraint fk_permissions_role_permissions
        foreign key (sys_permission_id) references sys_permission (id),
    constraint fk_permissions_role_role
        foreign key (sys_role_id) references sys_role (id)
);

create index ix_permissions_role_permissions
    on sys_permission_sys_role (sys_permission_id);

create index ix_permissions_role_role
    on sys_permission_sys_role (sys_role_id);

INSERT INTO lumen_server.sys_permission_sys_role (sys_permission_id, sys_role_id) VALUES (1, 435165139135348736);