package com.star.common.page;

import lombok.Data;

/**
 * 分页请求参数
 */
@Data
public class PageRequest {

    /**
     * 页码（从 1 开始）
     */
    private Integer page = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;

    /**
     * 页码，null 或 ≤0 时返回 1
     */
    public int getPage() {
        return page != null && page > 0 ? page : 1;
    }

    /**
     * 每页条数，null 或 ≤0 时返回 10
     */
    public int getPageSize() {
        return pageSize != null && pageSize > 0 ? pageSize : 10;
    }

    /**
     * 偏移量（用于数据库 LIMIT offset, size）
     */
    public int getOffset() {
        return (getPage() - 1) * getPageSize();
    }

    /**
     * 是否显式设置了分页参数（用于决定是否追加 LIMIT）
     */
    public boolean hasPagination() {
        return page != null && pageSize != null && page > 0 && pageSize > 0;
    }
}
