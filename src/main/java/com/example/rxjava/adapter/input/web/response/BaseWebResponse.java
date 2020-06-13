package com.example.rxjava.adapter.input.web.response;

import com.example.rxjava.adapter.input.web.exception.ErrorCode;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Kei Takayama
 * Created on 6/12/20.
 */

@Getter
@Builder
public class BaseWebResponse<T> {
    private ErrorCode errorCode;
    private T data;

    public static BaseWebResponse successNoData() {
        return BaseWebResponse.builder()
                .build();
    }

    public static <T> BaseWebResponse<T> successWithData(T data) {
        return BaseWebResponse.<T>builder()
                .data(data)
                .build();
    }

    public static BaseWebResponse error(ErrorCode errorCode) {
        return BaseWebResponse.builder()
                .errorCode(errorCode)
                .build();
    }
}
