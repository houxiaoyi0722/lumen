### flowable表结构解释

- ACT_RE ：'RE'表示 repository。 这个前缀的表包含了流程定义和流程静态资源 （图片，规则，等等）。
- ACT_RU：'RU'表示 runtime。 这些运行时的表，包含流程实例，任务，变量，异步任务，等运行中的数据。 Flowable只在流程实例执行过程中保存这些数据，
  在流程结束时就会删除这些记录。 这样运行时表可以一直很小速度很快。
- ACT_HI：'HI'表示 history。 这些表包含历史数据，比如历史流程实例， 变量，任务等等。
- ACT_GE： GE 表示 general。 通用数据， 用于不同场景下
- ACT_ID:   ’ID’表示identity(组织机构)。这些表包含标识的信息，如用户，用户组，等等。

具体的表结构的含义:
表分类表名解释

| 表名                  | Description               |
|---------------------|---------------------------|
| 一般数据                |
| ACT_GE_BYTEARRAY    | 通用的流程定义和流程资源              |
| ACT_GE_PROPERTY     | 系统相关属性                    |
| 流程历史记录              |
| ACT_HI_ACTINST      | 历史的流程实例                   |
| ACT_HI_ATTACHMENT   | 历史的流程附件                   |
| ACT_HI_COMMENT      | 历史的说明性信息                  |
| ACT_HI_DETAIL       | 历史的流程运行中的细节信息             |
| ACT_HI_IDENTITYLINK | 历史的流程运行过程中用户关系            |
| ACT_HI_PROCINST     | 历史的流程实例                   |
| ACT_HI_TASKINST     | 历史的任务实例                   |
| ACT_HI_VARINST      | 历史的流程运行中的变量信息             |
| 流程定义表               |
| ACT_RE_DEPLOYMENT   | 部署单元信息                    |
| ACT_RE_MODEL        | 模型信息                      |
| ACT_RE_PROCDEF      | 已部署的流程定义                  |
| 运行实例表               |
| ACT_RU_EVENT_SUBSCR | 运行时事件                     |
| ACT_RU_EXECUTION    | 运行时流程执行实例                 |
| ACT_RU_IDENTITYLINK | 运行时用户关系信息，存储任务节点与参与者的相关信息 |
| ACT_RU_JOB          | 运行时作业                     |
| ACT_RU_TASK         | 运行时任务                     |
| ACT_RU_VARIABLE     | 运行时变量表                    |
| 用户用户组表              |
| ACT_ID_BYTEARRAY    | 二进制数据表                    |
| ACT_ID_GROUP        | 用户组信息表                    |
| ACT_ID_INFO         | 用户信息详情表                   |
| ACT_ID_MEMBERSHIP   | 人与组关系表                    |
| ACT_ID_PRIV         | 权限表                       |
| ACT_ID_PRIV_MAPPING | 用户或组权限关系表                 |
| ACT_ID_PROPERTY     | 属性表                       |
| ACT_ID_TOKEN        | 记录用户的token信息              |
| ACT_ID_USER         | 用户表                       |

### Service总览

| service名称         | service作用        |
|-------------------|------------------|
| RepositoryService | Flowable的资源管理类   |
| RuntimeService    | Flowable的流程运行管理类 |
| TaskService       | Flowable的任务管理类   | 
| HistoryService    | Flowable的历史管理类   |
| ManagerService    | Flowable的引擎管理类   |

### 事件类型

然后在FlowableUI中关联对应的监听器

- create:任务创建后触发
- assignment:任务分配后触发
- Delete:任务完成后触发
- All：所有事件都触发
