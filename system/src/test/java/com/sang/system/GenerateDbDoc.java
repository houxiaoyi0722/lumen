package com.sang.system;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;

/**
 * Generate the DB Migration.
 */
@ActiveProfiles(value = "gs")
@SpringBootTest
public class GenerateDbDoc {

  private static final String FILE_OUTPUT_DIR = "./doc";
  private static final String DOC_FILE_NAME = "数据库文档";
  private static final String DOC_VERSION = "1.0.0";
  private static final String DOC_DESCRIPTION = "文档描述";

  @Resource
  private HikariDataSource dataSource;

  @Test
  public void generate() throws Exception {
    // 创建 screw 的配置
    Configuration config = Configuration.builder()
            .version(DOC_VERSION)  // 版本
            .description(DOC_DESCRIPTION) // 描述
            .dataSource(dataSource) // 数据源
            .engineConfig(buildEngineConfig()) // 引擎配置
//            .produceConfig(buildProcessConfig()) // 处理配置
            .build();

    // 执行 screw，生成数据库文档
    new DocumentationExecute(config).execute();
  }

  /**
   * 创建 screw 的引擎配置
   */
  private static EngineConfig buildEngineConfig() {
    return EngineConfig.builder()
            .fileOutputDir(FILE_OUTPUT_DIR) // 生成文件路径
            .openOutputDir(true) // 打开目录
            .fileType(EngineFileType.HTML) // 文件类型  // 可以设置 Word 或者 Markdown 格式
            .produceType(EngineTemplateType.freemarker) // 文件类型
            .fileName(DOC_FILE_NAME) // 自定义文件名称
            .build();
  }

  /**
   * 创建 screw 的处理配置，一般可忽略
   * 指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
   */
  private static ProcessConfig buildProcessConfig() {
    return ProcessConfig.builder()
            .designatedTableName(Collections.<String>emptyList())  // 根据名称指定表生成
            .designatedTablePrefix(Collections.<String>emptyList()) //根据表前缀生成
            .designatedTableSuffix(Collections.<String>emptyList()) // 根据表后缀生成
            .ignoreTableName(Arrays.asList("test_user", "test_group")) // 忽略表名
            .ignoreTablePrefix(Collections.singletonList("test_")) // 忽略表前缀
            .ignoreTableSuffix(Collections.singletonList("_test")) // 忽略表后缀
            .build();
  }

}
