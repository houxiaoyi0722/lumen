package com.sang.flowable.service.base;

import com.sang.common.domain.flowable.dto.FlowableTaskInfoDto;
import com.sang.flowable.dto.ProcessDefinitionDto;

public interface FlowableService {

    void updateUser(com.sang.common.domain.auth.authentication.user.entity.User userinfo);

    String getMainProcessExtendParamByName(ProcessDefinitionDto item);

    void setTaskExtendField(FlowableTaskInfoDto item);
}
