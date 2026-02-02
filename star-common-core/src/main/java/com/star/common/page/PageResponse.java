package com.star.common.page;

import lombok.Data;

import java.util.List;

/**
 * 分页响应包装
 */
@Data
public class PageResponse<T> {
    
    /**
     * 数据列表
     */
    private List<T> data;
    
    /**
     * 总记录数
     */
    private long total;
    
    /**
     * 当前页码
     */
    private Integer page;
    
    /**
     * 每页数量
     */
    private Integer pageSize;
    
    /**
     * 创建分页响应
     */
    public static <T> PageResponse<T> of(List<T> data, long total, PageRequest request) {
        PageResponse<T> response = new PageResponse<>();
        response.setData(data);
        response.setTotal(total);
        response.setPage(request.getPage());
        response.setPageSize(request.getPageSize());
        return response;
    }
    
    /**
     * 创建分页响应
     */
    public static <T> PageResponse<T> of(List<T> data, long total, int page, int pageSize) {
        PageResponse<T> response = new PageResponse<>();
        response.setData(data);
        response.setTotal(total);
        response.setPage(page);
        response.setPageSize(pageSize);
        return response;
    }
}
