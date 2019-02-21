package com.higgsup.xshop.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration of REST Error types.
 * 
 * @author vladimir.stankovic
 *
 *         Aug 3, 2016
 */
public enum ErrorCode {
    GLOBAL(2, "System error"),
    VALIDATION(1, "Validation error"),
    AUTHENTICATION(10, "Authentication error"),
    JWT_TOKEN_EXPIRED(11, "Jwt token expired"),

    //Cart error
    AMOUNT_GREATER_THAN_AVAILABLE_PRODUCT(12,
        "Amount product greater than available product"),

    //Product error
    PRODUCT_NOT_FOUND(13, "Product not found");

    private int errorCode;

    private String errorMessage;

    ErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }

    @JsonValue
    public String getErrorMessage() {
        return errorMessage;
    }
}
