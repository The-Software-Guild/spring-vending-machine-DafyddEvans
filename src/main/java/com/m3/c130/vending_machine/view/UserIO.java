package com.m3.c130.vending_machine.view;

public interface UserIO {

    void print(Object str);

    String readString(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

}
