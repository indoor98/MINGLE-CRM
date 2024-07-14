package com.team2final.minglecrm.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ResultResponse<T> {

    private Integer code;
    private String message;
    private T data;

    public ResultResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultResponse<T> success(Integer code, String message, T data) {
        return new ResultResponse<>(code, message, data);
    }
}
