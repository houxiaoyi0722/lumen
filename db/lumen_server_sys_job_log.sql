create table sys_job_log
(
    id              bigint               not null
        primary key,
    job_name        varchar(190)         null comment 'job名称',
    job_group       varchar(190)         null comment 'job分组',
    bean_class      varchar(250)         null comment '执行类全限定名',
    job_data_map    json                 null comment '执行类全限定名',
    cron_expression varchar(120)         null comment 'cron',
    status          varchar(30)          null comment '状态',
    start_time      datetime(6)          null comment '开始时间',
    end_time        datetime(6)          null comment '结束时间',
    may_fire_again  tinyint(1)           null comment '触发器是否会再次触发',
    job_run_time    bigint               null comment 'job执行时间,以毫秒为单位',
    result          json                 null comment 'job执行结果/错误结果',
    version         bigint               not null,
    when_created    datetime(6)          not null,
    created_by      varchar(255)         not null,
    modified_by     varchar(255)         not null,
    when_modified   datetime(6)          not null,
    deleted         tinyint(1) default 0 not null
)
    comment 'JobLog执行日志表';

INSERT INTO lumen_server.sys_job_log (id, job_name, job_group, bean_class, job_data_map, cron_expression, status, start_time, end_time, may_fire_again, job_run_time, result, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (437608054814093312, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{"test": 1, "test2": 2, "test3": "3"}', '0/20 * * * * ?', 'COMPLETE', '2022-08-29 16:08:31', null, 1, 13977, '"执行成功"', 1, '2022-08-30 09:34:45.959000', 'anonymous', 'anonymous', '2022-08-30 09:34:45.959000', 0);
INSERT INTO lumen_server.sys_job_log (id, job_name, job_group, bean_class, job_data_map, cron_expression, status, start_time, end_time, may_fire_again, job_run_time, result, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (437623602373406720, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{"test": 1, "test2": 2, "test3": "3"}', '0/20 * * * * ?', 'ERROR', '2022-08-29 16:08:31', null, 1, 3001, '"org.quartz.JobExecutionException: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero] [See nested exception: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:218)\\r\\n\\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\\r\\nCaused by: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:213)\\r\\n\\t... 1 more\\r\\nCaused by: java.lang.ArithmeticException: / by zero\\r\\n\\tat com.sang.system.job.DemoJob.executeInternal(DemoJob.java:22)\\r\\n\\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:75)\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202)\\r\\n\\t... 1 more\\r\\n"', 1, '2022-08-30 10:36:32.786000', 'anonymous', 'anonymous', '2022-08-30 10:36:32.786000', 0);
INSERT INTO lumen_server.sys_job_log (id, job_name, job_group, bean_class, job_data_map, cron_expression, status, start_time, end_time, may_fire_again, job_run_time, result, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (437623659973783552, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{"test": 1, "test2": 2, "test3": "3"}', '0/20 * * * * ?', 'ERROR', '2022-08-29 16:08:31', null, 1, 3001, '"org.quartz.JobExecutionException: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero] [See nested exception: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:218)\\r\\n\\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\\r\\nCaused by: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:213)\\r\\n\\t... 1 more\\r\\nCaused by: java.lang.ArithmeticException: / by zero\\r\\n\\tat com.sang.system.job.DemoJob.executeInternal(DemoJob.java:22)\\r\\n\\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:75)\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202)\\r\\n\\t... 1 more\\r\\n"', 1, '2022-08-30 10:36:46.519000', 'anonymous', 'anonymous', '2022-08-30 10:36:46.519000', 0);
INSERT INTO lumen_server.sys_job_log (id, job_name, job_group, bean_class, job_data_map, cron_expression, status, start_time, end_time, may_fire_again, job_run_time, result, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (437623994859597824, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{"test": 1, "test2": 2, "test3": "3"}', '0/20 * * * * ?', 'ERROR', '2022-08-29 16:08:31', null, 1, 3015, '"org.quartz.JobExecutionException: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero] [See nested exception: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:218)\\r\\n\\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\\r\\nCaused by: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:213)\\r\\n\\t... 1 more\\r\\nCaused by: java.lang.ArithmeticException: / by zero\\r\\n\\tat com.sang.system.job.DemoJob.executeInternal(DemoJob.java:22)\\r\\n\\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:75)\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202)\\r\\n\\t... 1 more\\r\\n"', 1, '2022-08-30 10:38:06.362000', 'anonymous', 'anonymous', '2022-08-30 10:38:06.362000', 0);
INSERT INTO lumen_server.sys_job_log (id, job_name, job_group, bean_class, job_data_map, cron_expression, status, start_time, end_time, may_fire_again, job_run_time, result, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (437634538312249344, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{"test": 1, "test2": 2, "test3": "3"}', '0 20 * * * ?', 'ERROR', '2022-08-30 10:38:07', null, 1, 3003, '"org.quartz.JobExecutionException: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero] [See nested exception: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:218)\\r\\n\\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\\r\\nCaused by: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:213)\\r\\n\\t... 1 more\\r\\nCaused by: java.lang.ArithmeticException: / by zero\\r\\n\\tat com.sang.system.job.DemoJob.executeInternal(DemoJob.java:22)\\r\\n\\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:75)\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202)\\r\\n\\t... 1 more\\r\\n"', 1, '2022-08-30 11:20:00.117000', 'anonymous', 'anonymous', '2022-08-30 11:20:00.117000', 0);
INSERT INTO lumen_server.sys_job_log (id, job_name, job_group, bean_class, job_data_map, cron_expression, status, start_time, end_time, may_fire_again, job_run_time, result, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (437649637638877184, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{"test": 1, "test2": 2, "test3": "3"}', '0 20 * * * ?', 'ERROR', '2022-08-30 10:38:07', null, 1, 3000, '"org.quartz.JobExecutionException: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero] [See nested exception: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:218)\\r\\n\\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\\r\\nCaused by: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:213)\\r\\n\\t... 1 more\\r\\nCaused by: java.lang.ArithmeticException: / by zero\\r\\n\\tat com.sang.system.job.DemoJob.executeInternal(DemoJob.java:22)\\r\\n\\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:75)\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202)\\r\\n\\t... 1 more\\r\\n"', 1, '2022-08-30 12:20:00.077000', 'anonymous', 'anonymous', '2022-08-30 12:20:00.077000', 0);
INSERT INTO lumen_server.sys_job_log (id, job_name, job_group, bean_class, job_data_map, cron_expression, status, start_time, end_time, may_fire_again, job_run_time, result, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (437664737028419584, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{"test": 1, "test2": 2, "test3": "3"}', '0 20 * * * ?', 'ERROR', '2022-08-30 10:38:07', null, 1, 3000, '"org.quartz.JobExecutionException: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero] [See nested exception: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:218)\\r\\n\\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573)\\r\\nCaused by: org.quartz.SchedulerException: Job threw an unhandled exception. [See nested exception: java.lang.ArithmeticException: / by zero]\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:213)\\r\\n\\t... 1 more\\r\\nCaused by: java.lang.ArithmeticException: / by zero\\r\\n\\tat com.sang.system.job.DemoJob.executeInternal(DemoJob.java:22)\\r\\n\\tat org.springframework.scheduling.quartz.QuartzJobBean.execute(QuartzJobBean.java:75)\\r\\n\\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202)\\r\\n\\t... 1 more\\r\\n"', 1, '2022-08-30 13:20:00.052000', 'anonymous', 'anonymous', '2022-08-30 13:20:00.052000', 0);
INSERT INTO lumen_server.sys_job_log (id, job_name, job_group, bean_class, job_data_map, cron_expression, status, start_time, end_time, may_fire_again, job_run_time, result, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (437681268823109632, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{"test": 1, "test2": 2, "test3": "3"}', '0 20 * * * ?', 'COMPLETE', '2022-08-30 14:25:41.496000', '2022-08-30 14:25:44.613000', 1, 3019, '"执行成功"', 1, '2022-08-30 14:25:41.529000', 'anonymous', 'anonymous', '2022-08-30 14:25:41.529000', 0);
INSERT INTO lumen_server.sys_job_log (id, job_name, job_group, bean_class, job_data_map, cron_expression, status, start_time, end_time, may_fire_again, job_run_time, result, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (437694936117882880, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{"test": 1, "test2": 2, "test3": "3"}', '0 20 * * * ?', 'COMPLETE', '2022-08-30 15:20:00.066000', '2022-08-30 15:20:03.102000', 1, 3001, '"执行成功"', 1, '2022-08-30 15:20:00.075000', 'anonymous', 'anonymous', '2022-08-30 15:20:00.075000', 0);
INSERT INTO lumen_server.sys_job_log (id, job_name, job_group, bean_class, job_data_map, cron_expression, status, start_time, end_time, may_fire_again, job_run_time, result, version, when_created, created_by, modified_by, when_modified, deleted) VALUES (437710035608088576, 'demo1', 'demo', 'com.sang.system.job.DemoJob', '{"test": 1, "test2": 2, "test3": "3"}', '0 20 * * * ?', 'COMPLETE', '2022-08-30 16:20:00.067000', '2022-08-30 16:20:03.099000', 1, 3002, '"执行成功"', 1, '2022-08-30 16:20:00.074000', 'anonymous', 'anonymous', '2022-08-30 16:20:00.074000', 0);