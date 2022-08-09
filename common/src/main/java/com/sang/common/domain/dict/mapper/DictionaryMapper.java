package com.sang.common.domain.dict.mapper;

import com.sang.common.domain.dict.dto.DictionaryDto;
import com.sang.common.domain.dict.entity.Dictionary;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct转换器
 */
@Mapper
public interface DictionaryMapper {

    DictionaryMapper mapper = Mappers.getMapper( DictionaryMapper.class );

    List<DictionaryDto> dictionaryToDto(List<Dictionary> list);

}
