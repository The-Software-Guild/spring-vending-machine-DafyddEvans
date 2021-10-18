package com.m3.c130.vending_machine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {

    private final String name;
    private final BigDecimal cost;
    private int quantity;

    public Item(String name, double cost, int quantity) {
        this.name = name;
        this.cost = new BigDecimal(cost).setScale(2, RoundingMode.DOWN);
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity() {
        this.quantity--;
    }
}
