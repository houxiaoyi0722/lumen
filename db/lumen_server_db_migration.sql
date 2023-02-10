create table db_migration
(
    id        int                                 not null
        primary key,
    mtype     varchar(1)                          not null,
    mstatus   varchar(10)                         not null,
    mversion  varchar(150)                        not null,
    mcomment  varchar(150)                        not null,
    mchecksum int                                 not null,
    run_on    timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    run_by    varchar(30)                         not null,
    run_time  int                                 not null
);

