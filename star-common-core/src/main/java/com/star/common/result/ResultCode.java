package com.star.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用结果码枚举
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    
    // 认证相关 1000-1099
    UNAUTHORIZED(1001, "未授权"),
    TOKEN_INVALID(1002, "Token无效"),
    TOKEN_EXPIRED(1003, "Token已过期");
    
    private final int code;
    private final String message;
}
