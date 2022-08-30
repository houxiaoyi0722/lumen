package com.sang.system.controller.router;

import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.domain.router.mapper.ButtonMapper;
import com.sang.common.domain.router.entity.Button;
import com.sang.common.domain.router.param.ButtonQry;
import com.sang.system.service.router.ButtonService;
import com.sang.common.domain.router.dto.ButtonDto;
import org.springframework.web.bind.annotation.*;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import org.springframework.validation.annotation.Validated;
import io.ebean.PagedList;

import javax.annotation.Resource;
import java.util.List;

/**
 * 页面按钮维护
 * 页面按钮维护
 * 
 * hxy 2022-08-30 17:45:30
 */
@Validated
@RestController
@RequestMapping("/lumen/router/button")
public class ButtonController {

    private final ButtonMapper buttonMapper = ButtonMapper.mapper;

    @Resource
    private ButtonService buttonService;

    /**
     * 分页查询
     *
     * @param qry
     * @return
     */
    @PostMapping("/buttons")
    public PageResult<ButtonDto> list(@RequestBody ButtonQry qry) {
        PagedList<Button> pagedList = buttonService.buttonList(qry);
        // 查询全部字段时可不转换直接给pagedList
        return PageResult.ok(buttonMapper.buttonToDtoList(pagedList.getList()), pagedList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/button")
    public Result<Button> findOne(@RequestParam("id") Long id) {
        return Result.ok(buttonService.findOne(id));
    }

    /**
     * 保存
     *
     * @param button
     * @return
     */
    @PostMapping("/button")
    public Result<Boolean> save(@RequestBody @Validated(Create.class) ButtonDto button) {
        buttonService.save(buttonMapper.dtoToButton(button));
        return Result.ok();
    }

    /**
    * 批量保存
    * @param buttons
    * @return
    */
    @PostMapping("/buttons")
    public Result<Boolean> saveAll(@RequestBody @Validated(Create.class) List<ButtonDto> buttons) {
        buttonService.saveAll(buttonMapper.dtoToButtonList(buttons));
        return Result.ok();
    }

    /**
     * 通过id更新
     *
     * @param button
     * @return
     */
    @PutMapping("/button")
    public Result<Boolean> update(@RequestBody @Validated(Update.class) ButtonDto button) {
        buttonService.update(buttonMapper.dtoToButton(button));
        return Result.ok();
    }

    /**
     * 通过id删除
     *
     * @param button
     * @return
     */
    @DeleteMapping("/button")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) ButtonDto button) {
        buttonService.delete(buttonMapper.dtoToButton(button));
        return Result.ok();
    }

    /**
     * 列表批量删除
     *
     * @param buttons
     * @return
     */
    @DeleteMapping("/buttons")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) List<ButtonDto> buttons) {
        buttonService.deleteAll(buttonMapper.dtoToButtonList(buttons));
        return Result.ok();
    }
}