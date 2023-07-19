package com.sang.flowable.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.sang.common.constants.FlowableConst;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.RepositoryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class FlowableExtendParamHandler {

    @Resource
    private RepositoryService repositoryService;


    /**
     * 获取流程扩展参数
     * @param processDefinitionId 流程定义id
     * @param attribute 扩展字段名称
     * @return
     */
    @Cacheable(value = "processExtend",key = "#processDefinitionId + '-' + #attribute")
    public String getProcessExtendParam(String processDefinitionId,String attribute) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Process mainProcess = bpmnModel.getMainProcess();
        Map<String, List<ExtensionAttribute>> attributes = mainProcess.getAttributes();
        if (MapUtil.isNotEmpty(attributes)) {
            List<ExtensionAttribute> extensionAttributes = attributes.get(attribute);
            if (CollUtil.isNotEmpty(extensionAttributes)) {
                return extensionAttributes.get(0).getValue();
            }
        }

        return null;
    }

    /**
     * task相关扩展字段
     * @param processDefinitionId 流程定义id
     * @param taskDefinitionKey 任务定义id
     * @param attribute 参数名称
     * @return
     */
    @Cacheable(value = "processExtend",key = "#processDefinitionId + '-' + #taskDefinitionKey + '-' + #attribute")
    public String getTaskExtendParam(String processDefinitionId,String taskDefinitionKey, String attribute) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

        FlowElement flowElement = bpmnModel.getMainProcess().getFlowElement(taskDefinitionKey, true);
        Map<String, List<ExtensionAttribute>> attributes = flowElement.getAttributes();

        if (MapUtil.isEmpty(attributes))
            return null;

        if (CollUtil.isNotEmpty(attributes.get(FlowableConst.IS_BATCH_APPROVAL)))
            return attributes.get(FlowableConst.IS_BATCH_APPROVAL).get(0).getValue();

        return null;
    }



}
