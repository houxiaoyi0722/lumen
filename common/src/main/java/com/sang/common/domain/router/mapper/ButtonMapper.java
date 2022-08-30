package com.sang.common.domain.router.mapper;

import com.sang.common.domain.router.dto.ButtonDto;
import com.sang.common.domain.router.entity.Button;
import org.mapstruct.Mapper;
import org.mapstruct.Builder;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct转换器
 * 页面按钮维护
 * 页面按钮维护
 * 模型映射类
 * hxy 2022-08-30 17:45:30
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface ButtonMapper {

    ButtonMapper mapper = Mappers.getMapper( ButtonMapper.class );

    List<ButtonDto> buttonToDtoList(List<Button> list);

    List<Button> dtoToButtonList(List<ButtonDto> list);

    Button dtoToButton(ButtonDto buttonDto);

    ButtonDto buttonToDto(Button button);

}