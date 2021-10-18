package com.m3.c130.vending_machine.service;

import com.m3.c130.vending_machine.Change;
import com.m3.c130.vending_machine.dao.VMDao;
import com.m3.c130.vending_machine.dto.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VMServiceLayerImpl implements VMServiceLayer {
    private final VMDao dao;
    private BigDecimal balance = new BigDecimal("0.00");

    public VMServiceLayerImpl(VMDao dao) {
        this.dao = dao;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void addBalance(BigDecimal balance) {
        this.balance = this.balance.add(balance).setScale(2, RoundingMode.DOWN);
    }

    public void subtractBalance(BigDecimal balance) {
        this.balance = this.balance.subtract(balance).setScale(2, RoundingMode.DOWN);
    }

    public List<Item> listVMItems() {
        return dao.listVMItems().stream().filter((i) -> i.getQuantity() != 0).collect(Collectors.toList());
    }

    public boolean purchaseItem(Item item) {
        if (item.getCost().doubleValue() <= balance.doubleValue()) {
            subtractBalance(item.getCost());
            return dispenseItem(item);
        } else {
            return false;
        }
    }

    public boolean dispenseItem(Item item) {
        item.reduceQuantity();
        return dao.saveVM();
    }

    public Map<Change, Integer> balanceToCoins() {
        Map<Change, Integer> map = new HashMap<>();
        for (Change change : Change.values()) {
            int i = balance.divide(BigDecimal.valueOf(change.value), RoundingMode.DOWN).intValue();
            balance = balance.subtract(BigDecimal.valueOf((i * change.value)));
            if (i > 0) {
                map.put(change, i);
            }
            if (balance.doubleValue() == 0) {
                break;
            }
        }
        return map;
    }
}
