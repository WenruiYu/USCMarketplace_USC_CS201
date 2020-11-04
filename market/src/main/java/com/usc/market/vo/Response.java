package com.usc.market.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private boolean success;

    private T data;


    public static <T> Response<T> suc(T data) {
        return new Response<>(true, data);
    }

    public static <T> Response<T> fail(T data) {
        return new Response<>(false, data);
    }


}
