package com.sang.flowable.service.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.sang.common.constants.FlowableConst;
import com.sang.common.domain.flowable.dto.FlowableTaskInfoDto;
import com.sang.flowable.dto.ProcessDefinitionDto;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 工作流处理相关
 */
@Service
public class FlowableServiceImpl implements FlowableService{

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private IdentityService identityService;

    @Resource
    private RuntimeService runtimeService;


    @Override
    public void updateUser(com.sang.common.domain.auth.authentication.user.entity.User userinfo) {
        // 通过 IdentityService 完成相关的用户和组的管理
        User user = identityService.createUserQuery().userId(userinfo.getUsername()).singleResult();
        user = user == null ? identityService.newUser(userinfo.getUsername()) : user;
        user.setId(userinfo.getUsername());
        user.setDisplayName(userinfo.getName());
        identityService.saveUser(user);

        // 将用户分配给对应的组
        if (userinfo.getUserGroup() != null) {
            identityService.deleteMembership(user.getId(),userinfo.getUserGroup().getGroupCode());
            identityService.createMembership(user.getId(),userinfo.getUserGroup().getGroupCode());
        }
    }

    /**
     * 获取流程扩展参数
     * @param item
     * @return
     */
    @Override
    public String getMainProcessExtendParamByName(ProcessDefinitionDto item) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(item.getId());
        Process mainProcess = bpmnModel.getMainProcess();
        Map<String, List<ExtensionAttribute>> attributes = mainProcess.getAttributes();
        if (MapUtil.isNotEmpty(attributes)) {
            List<ExtensionAttribute> extensionAttributes = attributes.get(FlowableConst.PROCESS_DISPOSE_PATH);
            if (CollUtil.isNotEmpty(extensionAttributes)) {
                return extensionAttributes.get(0).getValue();
            }
        }

        return null;
    }

    /**
     * 设置task相关扩展字段
     * @param item
     */
    @Override
    public void setTaskExtendField(FlowableTaskInfoDto item) {

        BpmnModel bpmnModel = repositoryService.getBpmnModel(item.getProcessDefinitionId());
        Process mainProcess = bpmnModel.getMainProcess();
        // 流程定义相关字段
        if (CollUtil.isNotEmpty(mainProcess.getAttributes().get(FlowableConst.PROCESS_DISPOSE_PATH)))
            item.setProcessDisposePath(mainProcess.getAttributes().get(FlowableConst.PROCESS_DISPOSE_PATH).get(0).getValue());

        // task定义相关字段
        FlowElement flowElement = mainProcess.getFlowElement(item.getTaskDefinitionKey(), true);
        Map<String, List<ExtensionAttribute>> attributes = flowElement.getAttributes();

        if (CollUtil.isNotEmpty(attributes.get(FlowableConst.IS_BATCH_APPROVAL)))
            item.setIsBatchApproval(Boolean.valueOf(attributes.get(FlowableConst.IS_BATCH_APPROVAL).get(0).getValue()));

        if (CollUtil.isNotEmpty(attributes.get(FlowableConst.TASK_DISPOSE_PATH)))
            item.setTaskDisposePath(attributes.get(FlowableConst.TASK_DISPOSE_PATH).get(0).getValue());

        // 流程实例相关字段
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processInstanceId(item.getProcessInstanceId()).list();
        item.setBusinessId(list.get(0).getBusinessKey());
        item.setBusinessStatus(list.get(0).getBusinessStatus());

        item.setStartUserId(list.get(0).getStartUserId());
        item.setStartTime(list.get(0).getStartTime());
        item.setProcessName(list.get(0).getProcessDefinitionName());

        List<User> userList = identityService.createUserQuery().userId(list.get(0).getStartUserId()).list();
        if (CollUtil.isNotEmpty(userList))
            item.setStartUserName(userList.get(0).getDisplayName());
    }


}
