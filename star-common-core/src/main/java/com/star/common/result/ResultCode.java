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
    
    // 参数错误 400
    PARAM_ERROR(400, "参数错误"),
    
    // 资源不存在 404
    NOT_FOUND(404, "资源不存在"),
    
    // 系统错误 500
    FAIL(500, "操作失败"),
    SYSTEM_ERROR(500, "系统异常"),
    
    // 认证相关 1000-1099
    UNAUTHORIZED(1001, "未授权"),
    TOKEN_INVALID(1002, "Token无效"),
    TOKEN_EXPIRED(1003, "Token已过期"),
    
    // 业务错误 4000-4999
    BUSINESS_ERROR(4000, "业务异常"),
    TASK_NOT_RUNNING(4001, "任务未运行"),
    TASK_NOT_PAUSED(4002, "任务未暂停"),
    TASK_ALREADY_RUNNING(4003, "已有任务在执行中"),
    CANNOT_UPDATE_PRESET(4004, "不能修改预设项"),
    CANNOT_DELETE_WITH_RECORDS(4005, "存在关联记录，不能删除");
    
    private final int code;
    private final String message;
}
