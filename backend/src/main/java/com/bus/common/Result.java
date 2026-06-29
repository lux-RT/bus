package com.bus.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 122511851
 * @since 2026-05-31 14:30
 */

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    private T data;

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(Constants.SUCCESS_CODE, "操作成功", data);
    }

    public static <T> Result<T> success() {
        return new Result<>(Constants.SUCCESS_CODE, "操作成功", null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(Constants.ERROR_CODE, message, null);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

}