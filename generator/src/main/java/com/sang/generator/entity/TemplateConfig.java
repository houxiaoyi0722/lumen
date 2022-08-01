package com.sang.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TemplateConfig {

    private String name;
    private Boolean enable;
    private String fileTypeName;
    private String output;
    private Map<String,Object> exDataModel;

}
