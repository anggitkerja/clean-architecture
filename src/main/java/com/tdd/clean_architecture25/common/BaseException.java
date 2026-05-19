package com.tdd.clean_architecture25.common;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final String errorCode;
    private final String englishMessage;
    private final String indonesianMessage;

    protected BaseException(String errorCode, String englishMessage, String indonesianMessage) {
        super(englishMessage);
        this.errorCode = errorCode;
        this.englishMessage = englishMessage;
        this.indonesianMessage = indonesianMessage;
    }
}
