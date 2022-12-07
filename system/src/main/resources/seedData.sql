INSERT INTO lumen_server.role (id, role_name, role_code, comment, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (435165139135348736, 'admin', 'admin', '超级管理员', null, 1, '2022-08-23 15:47:29.470000', 'hxy', 'hxy', '2022-08-23 15:47:29.470000', 0);

INSERT INTO lumen_server.router (id, name, path, redirect, component, mate, description, hidden, always_show, order_by, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (435576735858933760, 'systemSetting', '/setting', null, 'Layout', '{"title":"管理设置","icon":"Setting"}', '', 0, 1, 0, null, 1, '2022-08-24 19:03:01.772000', 'hxy', 'hxy', '2022-10-31 14:59:29.449000', 0);
INSERT INTO lumen_server.router (id, name, path, redirect, component, mate, description, hidden, always_show, order_by, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (435587805562519552, 'menuManage', 'menu', null, 'menage/router-manage', '{"title":"菜单管理","icon":"Menu"}', '', 0, 1, 1, 435576735858933760, 1, '2022-08-24 19:47:01', 'hxy', 'hxy', '2022-10-31 15:05:43.230000', 0);
INSERT INTO lumen_server.router (id, name, path, redirect, component, mate, description, hidden, always_show, order_by, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459013174491557888, 'roleMenage', 'role', null, 'menage/role-manage', '{"title":"角色管理","icon":"Avatar"}', '', 0, 1, 2, 435576735858933760, 1, '2022-10-28 11:11:04.167000', 'hxy', 'hxy', '2022-10-28 11:14:17.767000', 0);
INSERT INTO lumen_server.router (id, name, path, redirect, component, mate, description, hidden, always_show, order_by, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (459094420135555072, 'userManage', 'user', null, 'menage/user-manage', '{"title":"用户管理","icon":"User"}', '', 0, 1, 3, 435576735858933760, 1, '2022-10-28 16:33:54.649000', 'hxy', 'hxy', '2022-10-31 14:59:36.822000', 0);
INSERT INTO lumen_server.router (id, name, path, redirect, component, mate, description, hidden, always_show, order_by, parent_id, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (460910610671677440, 'userGroupManage', 'userGroup', null, 'menage/user-group-manage', '{"title":"用户组管理","icon":"UserFilled"}', '', 0, 1, 4, 435576735858933760, 1, '2022-11-02 16:50:48.200000', 'hxy', 'hxy', '2022-11-03 11:26:03.893000', 0);

INSERT INTO lumen_server.router_role (router_id, role_id) VALUES (435576735858933760, 435165139135348736);
INSERT INTO lumen_server.router_role (router_id, role_id) VALUES (435587805562519552, 435165139135348736);
INSERT INTO lumen_server.router_role (router_id, role_id) VALUES (459013174491557888, 435165139135348736);
INSERT INTO lumen_server.router_role (router_id, role_id) VALUES (459094118854504448, 435165139135348736);
INSERT INTO lumen_server.router_role (router_id, role_id) VALUES (459094420135555072, 435165139135348736);
INSERT INTO lumen_server.router_role (router_id, role_id) VALUES (460910610671677440, 435165139135348736);