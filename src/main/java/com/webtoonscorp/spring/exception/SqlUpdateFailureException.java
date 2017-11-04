package com.webtoonscorp.spring.exception;

public class SqlUpdateFailureException extends RuntimeException {

    public SqlUpdateFailureException(String message) {

        super(message);
    }
}
