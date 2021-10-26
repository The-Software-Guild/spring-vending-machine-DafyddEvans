package com.m3.c130.vending_machine.service;

public class NoItemInventoryException extends Exception {
    public NoItemInventoryException(String message) {
        super(message);
    }

    public NoItemInventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
