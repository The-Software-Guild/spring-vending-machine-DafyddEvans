package com.m3.c130.vending_machine.service;

public class VMDaoException extends Exception {
    public VMDaoException(String message) {
        super(message);
    }

    public VMDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
