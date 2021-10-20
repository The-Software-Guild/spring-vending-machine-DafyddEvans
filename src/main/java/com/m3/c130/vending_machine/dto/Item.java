package com.m3.c130.vending_machine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Item {

    private final String name;
    private final BigDecimal cost;
    private int quantity;

    public Item(String name, String cost, int quantity) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return quantity == item.quantity && Objects.equals(name, item.name) && Objects.equals(cost, item.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, quantity);
    }
}
