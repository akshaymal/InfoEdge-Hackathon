package com.nnacres.assessment.enums;

import lombok.Getter;


@Getter
public enum ErrorCategory {
    //@formatter:off

    SUCCESS("1000", "SUCCESS"),

    INVALID_DATA_STATE("10001", "system/data is in invalid state"),
    DATA_NOT_FOUND("20001", "data not found"),
    SPHERE_ENGINE_CONNECTION_FAILED("20002", "SPHERE_ENGINE_CONNECTION_FAILED"),
    SPHERE_ENGINE_COMPILATION_FAILED("20003", "SPHERE_ENGINE_CONNECTION_FAILED");
    //@formatter:on

    private String code;
    private String message;

    ErrorCategory(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
