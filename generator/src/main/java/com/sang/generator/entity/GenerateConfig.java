package com.sang.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenerateConfig {

    private List<TemplateConfig> templates;
    private Map<String,Object> dataModel;
}
