package com.m3.c130.vending_machine;

public enum Change {
    FIFTY_POUND(50, "Fifty pound note"),
    TWENTY_POUND(20, "Twenty pound note"),
    TEN_POUND(10, "Ten pound note"),
    TWO_POUND(2, "Two pound coin"),
    ONE_POUND(1, "One pound coin"),
    FIFTY_PENCE(0.5, "Fifty pence coin"),
    TWENTY_PENCE(0.2, "Twenty pence coin"),
    TEN_PENCE(0.1, "Ten pence coin"),
    FIVE_PENCE(0.05, "Five pence coin"),
    TWO_PENCE(0.02, "Two pence coin"),
    ONE_PENCE(0.01, "One pence coin");

    public double value;
    public String name;

    Change(double i, String name) {
        this.value = i;
        this.name = name;
    }
}
