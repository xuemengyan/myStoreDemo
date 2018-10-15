package com.yanxuemeng.exception;

public class UsernameExistsException extends Exception {
    public UsernameExistsException() {
    }

    public UsernameExistsException(String s) {
        super(s);
    }
}
