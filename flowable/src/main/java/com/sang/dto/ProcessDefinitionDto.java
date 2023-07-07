package com.sang.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDefinitionDto {

    private String id;

    private String category;

    private String name;

    private String key;

    private String description;

    private String version;

    private String resourceName;

    private String deploymentId;

    private String diagramResourceName;

    private String tenantId;

    private String suspensionState;

//    private boolean hasStartFormKey;
    private boolean isGraphicalNotationDefined;

    private String derivedFrom;

    private String derivedFromRoot;

    private int derivedVersion;

    protected String engineVersion;

    /**
     * 流程处理页面
     */
    protected String processDisposePath;


}
