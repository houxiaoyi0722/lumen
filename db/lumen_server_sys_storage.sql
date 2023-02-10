create table sys_storage
(
    id                 bigint               not null
        primary key,
    original_file_name varchar(100)         not null comment '原文件名',
    etag               varchar(100)         not null comment 'etag',
    object             varchar(200)         not null comment '对象存储地址',
    storage_bucket     varchar(200)         not null comment '存储桶',
    suffix             varchar(50)          null comment '文件类型/后缀名',
    content_type       varchar(50)          null comment '文件类型/请求头',
    version_id         varchar(50)          null comment '版本id',
    size               bigint(200)          null comment '文件大小/b',
    business_code      varchar(50)          null comment '业务代码',
    business_type      varchar(50)          null comment '业务类型',
    version            bigint               not null,
    when_created       datetime(6)          not null,
    created_by         varchar(255)         not null,
    modified_by        varchar(255)         not null,
    when_modified      datetime(6)          not null,
    deleted            tinyint(1) default 0 not null
)
    comment '对象存储管理';

INSERT INTO lumen_server.sys_storage (id, original_file_name, etag, object, storage_bucket, suffix, content_type, version_id, size, business_code, business_type, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (417810630545473536, '2022年6月-元歌-JAVA开发-侯晓懿.doc', '55d31dc594b18025012d85c177b15abf', '417810630545473536.doc', 'my-first-bucket', 'doc', 'application/msword', null, 104119, null, null, 1, '2022-07-06 18:26:52.231000', 'hxy', 'hxy', '2022-07-06 18:26:52.231000', 0);
INSERT INTO lumen_server.sys_storage (id, original_file_name, etag, object, storage_bucket, suffix, content_type, version_id, size, business_code, business_type, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (473976113539530752, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/473976113539530752.png', 'my-first-bucket', 'png', 'image/png', null, 1937996, 'hxy', 'Avatar', 1, '2022-12-08 18:09:18.149000', 'hxy', 'hxy', '2022-12-08 18:09:18.149000', 0);
INSERT INTO lumen_server.sys_storage (id, original_file_name, etag, object, storage_bucket, suffix, content_type, version_id, size, business_code, business_type, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (475362108939911168, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475362108939911168.png', 'my-first-bucket', 'png', 'image/png', null, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 13:55:57.105000', 'hxy', 'hxy', '2022-12-12 13:55:57.105000', 0);
INSERT INTO lumen_server.sys_storage (id, original_file_name, etag, object, storage_bucket, suffix, content_type, version_id, size, business_code, business_type, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (475392829519773696, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475392829519773696.png', 'my-first-bucket', 'png', 'image/png', null, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 15:57:58.415000', 'hxy', 'hxy', '2022-12-12 15:57:58.415000', 0);
INSERT INTO lumen_server.sys_storage (id, original_file_name, etag, object, storage_bucket, suffix, content_type, version_id, size, business_code, business_type, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (475393424884449280, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475393424884449280.png', 'my-first-bucket', 'png', 'image/png', null, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 16:00:20.535000', 'hxy', 'hxy', '2022-12-12 16:00:20.535000', 0);
INSERT INTO lumen_server.sys_storage (id, original_file_name, etag, object, storage_bucket, suffix, content_type, version_id, size, business_code, business_type, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (475396176100409344, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475396176100409344.png', 'my-first-bucket', 'png', 'image/png', null, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 16:11:16.321000', 'hxy', 'hxy', '2022-12-12 16:11:16.321000', 0);
INSERT INTO lumen_server.sys_storage (id, original_file_name, etag, object, storage_bucket, suffix, content_type, version_id, size, business_code, business_type, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (475396310842425344, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475396310842425344.png', 'my-first-bucket', 'png', 'image/png', null, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 16:11:48.407000', 'hxy', 'hxy', '2022-12-12 16:11:48.407000', 0);
INSERT INTO lumen_server.sys_storage (id, original_file_name, etag, object, storage_bucket, suffix, content_type, version_id, size, business_code, business_type, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (475398558943232000, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475398558943232000.png', 'my-first-bucket', 'png', 'image/png', null, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 16:20:44.415000', 'hxy', 'hxy', '2022-12-12 16:20:44.415000', 0);
INSERT INTO lumen_server.sys_storage (id, original_file_name, etag, object, storage_bucket, suffix, content_type, version_id, size, business_code, business_type, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (475400376721031168, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/475400376721031168.png', 'my-first-bucket', 'png', 'image/png', null, 1937996, 'hxy', 'Avatar', 1, '2022-12-12 16:27:57.778000', 'hxy', 'hxy', '2022-12-12 16:27:57.778000', 0);
INSERT INTO lumen_server.sys_storage (id, original_file_name, etag, object, storage_bucket, suffix, content_type, version_id, size, business_code, business_type, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (475411726935474176, 'test.png', 'd90520511993b409ecaeef19b3fc3827', 'Avatar/475411726935474176.png', 'my-first-bucket', 'png', 'image/png', null, 7002, 'hxy', 'Avatar', 1, '2022-12-12 17:13:03.852000', 'hxy', 'hxy', '2022-12-12 17:13:03.852000', 0);
INSERT INTO lumen_server.sys_storage (id, original_file_name, etag, object, storage_bucket, suffix, content_type, version_id, size, business_code, business_type, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (476043658492850176, '1.png', '9d8a19984e34f98dba77b6a2957ebb60', 'Avatar/476043658492850176.png', 'my-first-bucket', 'png', 'image/png', null, 1937996, 'admin', 'Avatar', 1, '2022-12-14 11:04:08.253000', 'hxy', 'hxy', '2022-12-14 11:04:08.253000', 0);