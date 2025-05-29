package com.DrWait.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Auth 관련 에러코드
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-001", "사용자를 찾을 수 없습니다."),
    ALREADY_EXISTED_USERNAME(HttpStatus.BAD_REQUEST, "ACCOUNT-002", "존재하는 계정입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "ACCOUNT-003", "비밀번호가 일치하지 않습니다."),
    ALREADY_LOGOUT(HttpStatus.BAD_REQUEST, "ACCOUNT-004", "로그아웃된 사용자입니다."),
    INVALID_REFRESHTOKEN(HttpStatus.BAD_REQUEST, "ACCOUNT-005", "유효하지않은 토큰입니다."),

    // 가족 Group 관련 에러코드
    FAMILY_GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILY-GROUP-001", "해당 가족 그룹을 찾을 수 없습니다."),

    // 가족 Member 관련 에러코드
    ALREADY_JOINED_FAMILY(HttpStatus.CONFLICT, "FAMILY-MEMBER-001", "이미 가족 그룹에 가입된 사용자입니다."),
    FAMILY_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILY-MEMBER-002", "해당 사용자가 등록된 그룹을 찾을 수 없습니다.");


    private final HttpStatus httpStatus; // HttpStatus
    private final String code;           // ACCOUNT-001
    private final String message;        // 설명
}
