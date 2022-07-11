package com.sang.system;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Generate the DB Doc.
 */
@Slf4j
public class GenerateDoc {

  private static final String FILE_OUTPUT_DIR = "./doc";
  private static final String DOC_FILE_NAME = "smart_asst数据库文档";
  private static final String DOC_VERSION = "1.0.0";
  private static final String DOC_DESCRIPTION = "smart_asst数据库文档";


  /**
   * ssh通道转发路径，为  本地发送链接请求:端口localhost：local_port  转发到 remote_host：remote_port
   */
  // 服务器登录名
  private static final String user = "hybrisdev001";
  // 登陆密码
  private static final String password = "dev001#001";
  //服务器公网IP
  private static final String host = "43.254.46.186";
  // 跳板机ssh开放的接口   默认端口 22
  private static final int port = 9094;
  // 这个是本地的端口，很重要！！！选取一个没有占用的port即可
  private static final int local_port = 3307;
  // 要访问的mysql所在的host    服务器局域网IP（127.0.0.1也行）
  private static final String remote_host = "192.168.30.19";
  // 服务器上数据库端口号
  private static final int remote_port = 3306;

  private Session session = null;

  @Test
  public void generate() throws Exception {

    SSHConnection();
    // 创建 screw 的配置
    Configuration config = Configuration.builder()
            .version(DOC_VERSION)  // 版本
            .description(DOC_DESCRIPTION) // 描述
            .dataSource(dataSourceConfig()) // 数据源
            .engineConfig(buildEngineConfig()) // 引擎配置
//            .produceConfig(buildProcessConfig()) // 处理配置
            .build();

    // 执行 screw，生成数据库文档
    new DocumentationExecute(config).execute();

    closeSSH();
  }

  public HikariDataSource dataSourceConfig() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3307/smart_asst?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=GMT%2b8");
    hikariConfig.setUsername("ydpre");
    hikariConfig.setPassword("Ydpre!234");
    hikariConfig.setMinimumIdle(5);
    hikariConfig.setPoolName("HikariCP");
    hikariConfig.setIdleTimeout(600000L);
    hikariConfig.setMaximumPoolSize(10);
    hikariConfig.setMaxLifetime(1800000L);
    hikariConfig.setConnectionTimeout(30000L);
    hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
    hikariConfig.addDataSourceProperty("useInformationSchema", "true");

    return new HikariDataSource(hikariConfig);
  }

  /**
   * 创建 screw 的引擎配置
   */
  private static EngineConfig buildEngineConfig() {
    return EngineConfig.builder()
            .fileOutputDir(FILE_OUTPUT_DIR) // 生成文件路径
            .openOutputDir(false) // 打开目录
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

  /**
   *    建立SSH连接
   */
  public void SSHConnection() throws Exception{
    try {
      JSch jsch = new JSch();
      session = jsch.getSession(user, host, port);
      session.setPassword(password);
      session.setConfig("StrictHostKeyChecking", "no");
      // 日志打印自己脑补
      session.connect();
      session.setPortForwardingL(local_port, remote_host, remote_port);
      log.info("成功建立SSH连接！");
    } catch (Exception e) {
      log.info("成功建立SSH连接！");
      // do something
    }
  }
  /**
   *    断开SSH连接
   */
  public void closeSSH () throws Exception {
    this.session.disconnect();
    log.info("SSH连接已断开");
  }


}
