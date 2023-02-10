create table QRTZ_LOCKS
(
    SCHED_NAME varchar(120) not null,
    LOCK_NAME  varchar(40)  not null,
    primary key (SCHED_NAME, LOCK_NAME)
);

INSERT INTO lumen_server.QRTZ_LOCKS (SCHED_NAME, LOCK_NAME) VALUES ('clusteredScheduler', 'TRIGGER_ACCESS');