package com.sang.common.response;

import com.sang.common.constants.ResultCodeEnum;
import io.ebean.PagedList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 分页统一返回对象
 * @author xiaoy
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    /**
     * 消息
     */
    private String message;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 数据
     */
    private List<T> data;
    /**
     * 页码，0表示第一页
     */
    private int page;
    /**
     * 每页结果数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 总数
     */
    private int total;

    public static <T> PageResult<T> empty(String message, Integer code) {
        return new PageResult<T>(message, code);
    }

    public static <T> PageResult<T> ok(PagedList<T> pagedList) {
        return new PageResult<>(ResultCodeEnum.SUCCESS.getMessage(),
                ResultCodeEnum.SUCCESS.getCode(),
                pagedList.getList(),
                pagedList.getPageIndex()/pagedList.getPageSize() + 1, // todo 验证
                pagedList.getPageSize(),
                pagedList.getTotalPageCount(),
                pagedList.getTotalCount()
        );
    }

    public static <T> PageResult<T> ok(List<T> data,Integer page,Integer pageSize,Integer total) {
        pageSize = Math.max(pageSize, 1);
        return new PageResult<T>(ResultCodeEnum.SUCCESS.getMessage(),
                ResultCodeEnum.SUCCESS.getCode(),
                data,
                Math.max(page,0),
                pageSize,
                total % pageSize != 0  ?  total / pageSize + 1  :  total / pageSize,
                total
        );
    }

    public static PageResult unhandledException(String message, Integer code) {
        return new PageResult<>(ResultCodeEnum.ERROR.getMessage(), ResultCodeEnum.ERROR.getCode());
    }

    public PageResult(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
