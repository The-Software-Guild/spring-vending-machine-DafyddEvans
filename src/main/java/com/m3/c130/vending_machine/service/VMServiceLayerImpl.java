package com.m3.c130.vending_machine.service;

import com.m3.c130.vending_machine.dao.VMAuditDao;
import com.m3.c130.vending_machine.dao.VMDao;
import com.m3.c130.vending_machine.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VMServiceLayerImpl implements VMServiceLayer {
    private final VMDao dao;
    private final VMAuditDao auditDao;
    private BigDecimal balance = new BigDecimal("0.00");

    @Autowired
    public VMServiceLayerImpl(VMDao dao, VMAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void addBalance(BigDecimal balance) throws VMDaoException {
        this.balance = this.balance.add(balance).setScale(2, RoundingMode.DOWN);
        auditDao.writeAuditEntry(String.format("£%.2f added to balance", balance.doubleValue()));
    }

    public void subtractBalance(BigDecimal balance) {
        this.balance = this.balance.subtract(balance).setScale(2, RoundingMode.DOWN);
    }

    public List<Item> listVMItems() {
        return dao.listVMItems();
    }

    public boolean purchaseItem(Item item) throws VMDaoException, NoItemInventoryException, InsufficientFundsException {
        if (item.getQuantity() > 0) {
            if (item.getCost().doubleValue() <= balance.doubleValue()) {
                if (dispenseItem(item)) {
                    auditDao.writeAuditEntry(String.format("%s purchased for £%.2f", item.getName(), item.getCost()));
                    subtractBalance(item.getCost());
                    return true;
                } else {
                    return false;
                }
            } else {
                auditDao.writeAuditEntry("InsufficientFundsException thrown for " + item.getName());
                throw new InsufficientFundsException("You don't have enough funds, please insert more coins");
            }
        } else {
            auditDao.writeAuditEntry("NoItemInventoryException thrown for " + item.getName());
            throw new NoItemInventoryException("This item is out of stock, please make another selection");
        }
    }

    public boolean dispenseItem(Item item) throws VMDaoException {
        item.reduceQuantity();
        return dao.saveVM();
    }

    public Map<Change, Integer> balanceToCoins() throws VMDaoException {
        Map<Change, Integer> map = new HashMap<>();
        for (Change change : Change.values()) {
            int i = balance.divide(BigDecimal.valueOf(change.value), RoundingMode.DOWN).intValue();
            if (i > 0) {
                auditDao.writeAuditEntry("dispensed " + i + " X " + change.name);
                subtractBalance(BigDecimal.valueOf((i * change.value)));
                map.put(change, i);
            }
            if (balance.doubleValue() == 0) {
                break;
            }
        }
        return map;
    }
}
