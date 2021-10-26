package com.m3.c130.vending_machine.service;

import com.m3.c130.vending_machine.dao.VMAuditDao;
import com.m3.c130.vending_machine.dao.VMAuditDaoStubImpl;
import com.m3.c130.vending_machine.dao.VMDao;
import com.m3.c130.vending_machine.dao.VMDaoStubImpl;
import com.m3.c130.vending_machine.dto.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

class VMServiceLayerImplTest {

    private final VMDao dao = new VMDaoStubImpl();
    private final VMAuditDao audit = new VMAuditDaoStubImpl();
    private final VMServiceLayer service = new VMServiceLayerImpl(dao, audit);
    private final Item item = new Item("Pepsi", "1.20", 1);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getBalance() throws VMDaoException {
        Assertions.assertEquals(bigDec("0"), service.getBalance());
        service.addBalance(bigDec("100"));
        Assertions.assertEquals(bigDec("100"), service.getBalance());
        service.subtractBalance(bigDec("50"));
        Assertions.assertEquals(bigDec("50"), service.getBalance());
        service.subtractBalance(bigDec("0.51"));
        Assertions.assertEquals(bigDec("49.49"), service.getBalance());
    }

    @Test
    void addBalance() throws VMDaoException {
        Assertions.assertEquals(bigDec("0"), service.getBalance());
        service.addBalance(bigDec("0.01"));
        service.addBalance(bigDec("0.01"));
        service.addBalance(bigDec("0.01"));
        service.addBalance(bigDec("1.01"));
        Assertions.assertEquals(bigDec("1.04"), service.getBalance());
        service.addBalance(bigDec("10.25"));
        Assertions.assertEquals(bigDec("11.29"), service.getBalance());
    }

    @Test
    void subtractBalance() throws VMDaoException {
        Assertions.assertEquals(bigDec("0"), service.getBalance());
        service.addBalance(bigDec("20"));
        service.subtractBalance(bigDec("0.01"));
        service.subtractBalance(bigDec("0.01"));
        service.subtractBalance(bigDec("0.01"));
        service.subtractBalance(bigDec("1.01"));
        Assertions.assertEquals(bigDec("18.96"), service.getBalance());
        service.subtractBalance(bigDec("10.25"));
        Assertions.assertEquals(bigDec("8.71"), service.getBalance());
    }

    @Test
    void listVMItems() {
        Assertions.assertEquals(1, service.listVMItems().size());
        Assertions.assertEquals(item, service.listVMItems().get(0));
    }

    @Test
    void purchaseItem() throws NoItemInventoryException, VMDaoException, InsufficientFundsException {
        try {
            service.purchaseItem(item);
            Assertions.fail("Should throw a InsufficientFundsException");
        } catch (InsufficientFundsException ignore) {
        }
        service.addBalance(bigDec("1.2"));
        Assertions.assertTrue(service.purchaseItem(item));
        Assertions.assertEquals(bigDec("0"), service.getBalance());
        try {
            service.purchaseItem(item);
            Assertions.fail("Should throw a NoItemInventoryException");
        } catch (NoItemInventoryException ignore) {
        }

    }

    @Test
    void dispenseItem() throws VMDaoException {
        Assertions.assertEquals(1, item.getQuantity());
        service.dispenseItem(item);
        Assertions.assertEquals(0, item.getQuantity());
    }

    @Test
    void balanceToCoins() throws VMDaoException {
        Map<Change, Integer> map1 = new HashMap<>();
        map1.put(Change.FIFTY_PENCE, 1);
        map1.put(Change.TWENTY_PENCE, 1);
        map1.put(Change.FIVE_PENCE, 1);
        service.addBalance(bigDec("0.75"));
        Assertions.assertEquals(map1, service.balanceToCoins());
        Assertions.assertNotEquals(map1, service.balanceToCoins());

        Map<Change, Integer> map2 = new HashMap<>();
        map2.put(Change.TWO_POUND, 1);
        map2.put(Change.FIFTY_PENCE, 1);
        map2.put(Change.TWENTY_PENCE, 1);
        map2.put(Change.FIVE_PENCE, 1);
        map2.put(Change.TWO_PENCE, 1);
        map2.put(Change.ONE_PENCE, 1);
        service.addBalance(bigDec("2.78"));
        Assertions.assertEquals(map2, service.balanceToCoins());
        Assertions.assertNotEquals(map2, service.balanceToCoins());
    }

    private BigDecimal bigDec(String s) {
        return new BigDecimal(s).setScale(2, RoundingMode.DOWN);
    }
}