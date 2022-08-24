package com.sang.common.domain.dict.mapper;

import com.sang.common.domain.dict.dto.DictionaryDto;
import com.sang.common.domain.dict.entity.Dictionary;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct转换器
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface DictionaryMapper {

    DictionaryMapper mapper = Mappers.getMapper( DictionaryMapper.class );

    List<DictionaryDto> dictionaryToDtoList(List<Dictionary> list);

    List<Dictionary> dtoToDictionaryList(List<DictionaryDto> list);

    Dictionary dtoToDictionary(DictionaryDto dictionaryDto);

}
