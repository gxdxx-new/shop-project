package com.gxdxx.shop.exception;

public class MemberEmailAlreadyExistsException extends RuntimeException {

    public MemberEmailAlreadyExistsException(String message) {
        super(message);
    }

}