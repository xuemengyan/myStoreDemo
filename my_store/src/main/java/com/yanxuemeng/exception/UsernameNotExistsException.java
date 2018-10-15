package com.yanxuemeng.exception;

public class UsernameNotExistsException extends Exception {
    public UsernameNotExistsException() {
    }

    public UsernameNotExistsException(String s) {
        super(s);
    }
}
