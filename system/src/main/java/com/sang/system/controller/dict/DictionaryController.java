package com.sang.system.controller.dict;

import cn.hutool.core.collection.CollUtil;
import com.sang.common.domain.base.dto.CommonTableDataDto;
import com.sang.common.domain.dict.dto.DictionaryDto;
import com.sang.common.domain.dict.dto.DictionaryItemDto;
import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.common.domain.dict.entity.DictionaryItem;
import com.sang.common.domain.dict.mapper.DictionaryItemMapper;
import com.sang.common.domain.dict.mapper.DictionaryMapper;
import com.sang.common.domain.dict.param.DictionaryQry;
import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import com.sang.system.service.dict.DictionaryItemService;
import com.sang.system.service.dict.DictionaryService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 数据字典类
 */
@RestController
@Validated
@RequestMapping("/dict")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @Resource
    private DictionaryItemService dictionaryItemService;

    private final DictionaryMapper mapper = DictionaryMapper.mapper;
    private final DictionaryItemMapper itemMapper = DictionaryItemMapper.mapper;

    /**
     * 分页查询
     * @param dataDictionaryQry
     * @return
     */
    @PostMapping("/dictionaries")
    public PageResult<DictionaryDto> list(@RequestBody DictionaryQry dataDictionaryQry) {
        PagedList<Dictionary> pagedList = dictionaryService.dictionaryList(dataDictionaryQry);
        return PageResult.ok(mapper.dictionaryToDtoList(pagedList.getList()), pagedList);
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/dictionary")
    public Result<Dictionary> findOne(@RequestParam("id") @NotNull(message = "id不能为空") @Min(value = 1L,message = "编号必须大于1") Long id) {
        return Result.ok(dictionaryService.findOne(id));
    }

    /**
     * 通过dictId查询 item列表
     * @param id
     * @return
     */
    @GetMapping("/items")
    public Result<List<DictionaryItemDto>> findItemList(@RequestParam("id") @NotNull(message = "id不能为空") @Min(value = 1L,message = "编号必须大于1") Long id) {
        return Result.ok(itemMapper.dictionaryItemToDtoList(dictionaryService.findOne(id).getDictionaryItems()));
    }

    /**
     * 保存
     * @param dictionary
     * @return
     */
    @PostMapping("/dictionary")
    public Result<Boolean> save(@RequestBody @Validated(Create.class) DictionaryDto dictionary) {
        dictionaryService.save(mapper.dtoToDictionary(dictionary));
        return Result.ok();
    }

    /**
     * 通过id更新
     * @param dictionary
     * @return
     */
    @PutMapping("/dictionary")
    public Result<Boolean> update(@RequestBody @Validated(Update.class) DictionaryDto dictionary) {
        dictionaryService.update(mapper.dtoToDictionary(dictionary));
        return Result.ok();
    }

    /**
     * 通过id删除
     * @param dictionary
     * @return
     */
    @DeleteMapping("/dictionary")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) DictionaryDto dictionary) {
        dictionaryService.delete(mapper.dtoToDictionary(dictionary));
        return Result.ok();
    }

    /**
     * 列表批量删除
     * @param dictionaries
     * @return
     */
    @DeleteMapping("/dictionaries")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) List<DictionaryDto> dictionaries) {
        dictionaryService.deleteAll(mapper.dtoToDictionaryList(dictionaries));
        return Result.ok();
    }

    /**
     * 字典item 数据更新\新增\删除
     * @param tableDataDto
     * @return
     */
    @Transactional
    @PutMapping("/dict/item")
    public Result<Boolean> dictItemUpdate(@RequestBody @Validated({Create.class, Update.class}) CommonTableDataDto<DictionaryItemDto> tableDataDto) {

        if (CollUtil.isNotEmpty(tableDataDto.getRemoveList())) {
            dictionaryItemService.deleteAll(itemMapper.dtoToDictionaryItemList(tableDataDto.getRemoveList()));
        }

        if (CollUtil.isNotEmpty(tableDataDto.getInsertList())) {
            dictionaryItemService.saveAll(itemMapper.dtoToDictionaryItemList(tableDataDto.getInsertList()));
        }

        if (CollUtil.isNotEmpty(tableDataDto.getUpdateList())) {
            itemMapper.dtoToDictionaryItemList(tableDataDto.getUpdateList()).forEach(DictionaryItem::update);
        }

        return Result.ok();
    }

    /**
     * 更新字典列表
     * @param tableDataDto
     * @return
     */
    @Transactional
    @PutMapping("/dict/listUpdate")
    public Result<Boolean> dictListUpdate(@RequestBody @Validated({Create.class, Update.class}) CommonTableDataDto<DictionaryDto> tableDataDto) {

        if (CollUtil.isNotEmpty(tableDataDto.getRemoveList())) {
            dictionaryService.deleteAll(mapper.dtoToDictionaryList(tableDataDto.getRemoveList()));
        }

        if (CollUtil.isNotEmpty(tableDataDto.getInsertList())) {
            dictionaryService.saveAll(mapper.dtoToDictionaryList(tableDataDto.getInsertList()));
        }

        if (CollUtil.isNotEmpty(tableDataDto.getUpdateList())) {
            mapper.dtoToDictionaryList(tableDataDto.getUpdateList()).forEach(Dictionary::update);
        }

        return Result.ok();
    }

}
