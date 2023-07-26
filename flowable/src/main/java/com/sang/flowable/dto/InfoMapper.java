package com.sang.flowable.dto;

import com.fasterxml.jackson.databind.node.ArrayNode;

public interface InfoMapper {

    ArrayNode map(Object element);
}
