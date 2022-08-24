package com.sang.common.domain.router.mapper;

import com.sang.common.domain.router.dto.RouterDto;
import com.sang.common.domain.router.entity.Router;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct转换器
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface RouterMapper {

    RouterMapper mapper = Mappers.getMapper( RouterMapper.class );

    List<RouterDto> routerToDtoList(List<Router> list);

    List<Router> dtoToRouterList(List<RouterDto> list);

    Router dtoToRouter(RouterDto routerDto);

}
