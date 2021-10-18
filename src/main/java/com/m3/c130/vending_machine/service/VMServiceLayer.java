package com.m3.c130.vending_machine.service;

import com.m3.c130.vending_machine.Change;
import com.m3.c130.vending_machine.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface VMServiceLayer {

    BigDecimal getBalance();

    void addBalance(BigDecimal balance);

    List<Item> listVMItems();

    boolean purchaseItem(Item item);

    boolean dispenseItem(Item item);

    Map<Change, Integer> balanceToCoins();
}
