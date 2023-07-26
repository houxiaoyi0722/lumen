package com.sang.flowable.service.flowable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sang.flowable.param.SuspendedActiveParam;
import org.flowable.engine.repository.Deployment;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FlowableProcessManageService {

    Deployment getDeployment(MultipartFile file, String resourceName, String name) throws IOException;

    void suspendedOrActiveProcess(SuspendedActiveParam suspendedActiveParam);

    void deleteProcess(String deploymentId);

    void updateUser(com.sang.common.domain.auth.authentication.user.entity.User userinfo);

    JsonNode getProcessJsonNodes(String processInstanceId);

    ObjectNode getModelHistoryNodes(String processInstanceId);
}
