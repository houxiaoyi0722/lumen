package com.sang.system;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import io.github.yedaxia.apidocs.plugin.markdown.MarkdownDocPlugin;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Generate the Api Doc.
 */
public class GenerateApiDoc {

  @Test
  public void generate() throws Exception {
    DocsConfig config = new DocsConfig();
    config.setProjectPath("D:\\lumen-repo\\lumen"); // root project path
    config.setProjectName("lumen-server"); // project name
    config.setApiVersion("V1.0");       // api version
    config.setDocsPath("./apiDoc"); // api docs target path
    config.setAutoGenerate(Boolean.TRUE);  // auto generate
    config.addPlugin(new MarkdownDocPlugin()); // 使用 MD 插件，额外生成 MD 格式的接口文档

    Docs.buildHtmlDocs(config); // execute to generate
  }

}
