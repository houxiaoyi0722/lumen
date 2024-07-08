package com.sang.common.domain.${domain}.mapper;

import com.sang.common.domain.${domain}.dto.${model}Dto;
import com.sang.common.domain.${domain}.entity.${model};
import org.mapstruct.Mapper;
import org.mapstruct.Builder;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct转换器
 * ${domainComment}
 * ${modelComment}
 * ${fileComment}
 * @author ${author}
 * @since ${createDate?string("yyyy-MM-dd HH:mm:ss")}
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface ${model}Mapper {

    ${model}Mapper mapper = Mappers.getMapper( ${model}Mapper.class );

    List<${model}Dto> ${model?lower_case}ToDtoList(List<${model}> list);

    List<${model}> dtoTo${model}List(List<${model}Dto> list);

    ${model} dtoTo${model}(${model}Dto ${model?lower_case}Dto);

    ${model}Dto ${model?lower_case}ToDto(${model} ${model?lower_case});

}