package com.m3.c130.vending_machine.service;

import com.m3.c130.vending_machine.Change;
import com.m3.c130.vending_machine.InsufficientFundsException;
import com.m3.c130.vending_machine.NoItemInventoryException;
import com.m3.c130.vending_machine.VMDaoException;
import com.m3.c130.vending_machine.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface VMServiceLayer {

    BigDecimal getBalance();

    void addBalance(BigDecimal balance) throws VMDaoException;

    List<Item> listVMItems();

    boolean purchaseItem(Item item) throws VMDaoException, NoItemInventoryException, InsufficientFundsException;

    boolean dispenseItem(Item item) throws VMDaoException;

    Map<Change, Integer> balanceToCoins() throws VMDaoException;
}
