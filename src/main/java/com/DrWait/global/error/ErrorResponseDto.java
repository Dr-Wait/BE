package com.DrWait.global.error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseDto {
    private int status;
    private String name;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseDto> from(ErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseDto.builder()
                        .status(e.getHttpStatus().value())
                        .name(e.name())
                        .code(e.getMessage())
                        .message(e.getMessage())
                        .build());
    }
}
