package com.sang.common.domain.router.mapper;

import com.sang.common.domain.router.dto.RouterDto;
import com.sang.common.domain.router.entity.Router;
import com.sang.common.domain.router.vo.RouterVo;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct转换器
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface RouterMapper {

    RouterMapper mapper = Mappers.getMapper( RouterMapper.class );

    List<RouterDto> routerToDtoList(List<Router> list);

    List<RouterVo> routerToVoList(List<Router> list);

    @Mapping(source = "parentId.id",target = "parentId")
    @Mapping(target = "children",ignore = true)
    RouterVo routerToVo(Router router);

    List<Router> dtoToRouterList(List<RouterDto> list);

    Router dtoToRouter(RouterDto routerDto);

}
