package com.ai.kids.cartoncharactor.enums;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(0, "成功"),
    SERVER_EXCEPTION(500, "出错了，请稍后再试"),
    ;
    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
