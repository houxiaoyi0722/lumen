## 文件生成器配置使用说明
```json
{
  "dataModel": {
    "domain": "Test", // 领域，小驼峰方式，一个领域下会有多个model
    "model": "Test", // 单个model名称，大驼峰方式
    "author": "hxy", // author
    "dbComment": "test" // 表db备注
  },
  "templates": [
    {
      "name": "entity.ftl", // 模板文件名，不必修改
      "fileTypeName": "{model}.java", // 生成文件文件名，不必修改
      "output": "common/src/main/java/com/sang/common/domain/{domain}/entity/",// 输出路径，不必修改，{domain}为占位符使用dataModel，和exDataModel key替换
      "exDataModel": { // 文件扩展参数，自定义
        "fileComment": "entity" // 文件描述
      }
    },
    {
      "name": "finder.ftl",
      "fileTypeName": "{model}Finder.java",
      "output": "common/src/main/java/com/sang/common/domain/{domain}/entity/finder/",
      "exDataModel": {
        "fileComment": "finder"
      }
    },
    {
      "name": "repo.ftl",
      "fileTypeName": "{model}Repository.java",
      "output": "common/src/main/java/com/sang/common/domain/{domain}/repo/",
      "exDataModel": {
        "fileComment": "11"
      }
    },
    {
      "name": "Qry.ftl",
      "fileTypeName": "{model}Qry.java",
      "output": "common/src/main/java/com/sang/common/domain/{domain}/param/",
      "exDataModel": {
        "fileComment": "11"
      }
    },
    {
      "name": "service.ftl",
      "fileTypeName": "{model}Service.java",
      "output": "system/src/main/java/com/sang/system/service/{domain}/",
      "exDataModel": {
        "fileComment": "11"
      }
    },
    {
      "name": "serviceImpl.ftl",
      "fileTypeName": "{model}ServiceImpl.java",
      "output": "system/src/main/java/com/sang/system/service/{domain}/impl/",
      "exDataModel": {
        "fileComment": "11"
      }
    }
  ]
}

```