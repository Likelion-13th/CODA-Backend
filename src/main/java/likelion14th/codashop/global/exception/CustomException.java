package likelion14th.codashop.global.exception;

import likelion14th.codashop.global.api.BaseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final BaseCode errorCode;

    public CustomException(BaseCode errorCode) {
        super(errorCode.getReason().getMessage());
        this.errorCode = errorCode;
    }
}