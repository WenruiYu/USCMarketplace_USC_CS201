package com.usc.market.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class Response<T> {

    private int code;

    private T data;

    private String msg;

    public static <T> Response<T> ok() {
        return ok(null);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(HttpStatus.OK.value(), data, null);
    }

    public static <T> Response<T> fail(HttpStatus code, String msg) {
        return new Response<>(code.value(), null, msg);
    }

    public static <T> Response<T> fail(String msg) {
        return fail(HttpStatus.BAD_REQUEST, msg);
    }

    public static <T> Response<T> failAuth(String msg) {
        return fail(HttpStatus.UNAUTHORIZED, msg);
    }

}
