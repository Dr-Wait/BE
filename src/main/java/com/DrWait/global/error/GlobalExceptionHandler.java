package com.DrWait.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // 전역에서 발생할 수 있는 예외를 잡아 처리
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class) // 예외를 잡아 하나의 메소드에서 공통 처리
    protected ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e){
        return ErrorResponseDto.from(e.getErrorCode());
    }
}
