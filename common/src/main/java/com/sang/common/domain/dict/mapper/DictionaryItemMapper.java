package com.sang.common.domain.dict.mapper;

import com.sang.common.domain.dict.dto.DictionaryItemDto;
import com.sang.common.domain.dict.entity.DictionaryItem;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct转换器
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface DictionaryItemMapper {

    DictionaryItemMapper mapper = Mappers.getMapper( DictionaryItemMapper.class );

    List<DictionaryItemDto> dictionaryItemToDtoList(List<DictionaryItem> list);

    List<DictionaryItem> dtoToDictionaryItemList(List<DictionaryItemDto> list);

    DictionaryItem dtoToDictionaryItem(DictionaryItemDto dictionaryDto);

    DictionaryItemDto dictionaryItemToDto(DictionaryItem dictionary);
}
