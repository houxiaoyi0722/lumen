package com.sang.generator;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sang.generator.config.FreemarkerConfig;
import com.sang.generator.entity.GenerateConfig;
import com.sang.generator.entity.TemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器-ebean
 */
@Slf4j
public class CodeGenerator {

    // entity
    // finder
    // repo
    // service
    // controller

    public static void main(String[] args) throws IOException, TemplateException {
        GenerateConfig generateConfig = JSONUtil.readJSON(new File("../config/config.json"), StandardCharsets.UTF_8).toBean(GenerateConfig.class);
        Configuration cfg = FreemarkerConfig.build();

        Map<String, Object> dataModel = generateConfig.getDataModel();
        dataModel.put("createDate",new Date());

        for (TemplateConfig templateConfig : generateConfig.getTemplates()) {
            Map<String, Object> priDataModel = new HashMap<>(dataModel);
            priDataModel.putAll(templateConfig.getExDataModel());

            Template template = cfg.getTemplate(templateConfig.getName());

            FileWriter out = new FileWriter(StrUtil.format(dataModel.get("output").toString(),priDataModel),StandardCharsets.UTF_8);
            template.process(dataModel, out);
            out.close();
            log.info("template:{}输出成功",templateConfig.getName());
        }

    }


}
