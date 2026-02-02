package com.star.common.page;

import lombok.Data;

/**
 * 分页请求参数
 */
@Data
public class PageRequest {
    
    /**
     * 页码（从1开始）
     */
    private Integer page = 1;
    
    /**
     * 每页数量
     */
    private Integer pageSize = 10;
    
    /**
     * 获取偏移量（用于数据库查询）
     */
    public int getOffset() {
        return (page - 1) * pageSize;
    }
}
