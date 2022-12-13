package com.sang.common.domain.storage.mapper;

import com.sang.common.domain.storage.dto.StorageDto;
import com.sang.common.domain.storage.entity.Storage;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct转换器
 * hxy 2022-08-30 17:45:30
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface StorageMapper {

    StorageMapper mapper = Mappers.getMapper( StorageMapper.class );

    StorageDto storageToDto(Storage storage);
    
}
