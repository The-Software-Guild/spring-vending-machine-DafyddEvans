package com.m3.c130.vending_machine.dao;

import com.m3.c130.vending_machine.VMDaoException;
import com.m3.c130.vending_machine.dto.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

class VMDaoFileImplTest {

    private final String FILENAME = "VM_test.txt";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void loadVM() {
        createFile();
        VMDao dao = null;
        Item item = new Item("Drink", "1.00", 100);
        try {
            dao = new VMDaoFileImpl("FILENAME");
            Assertions.fail("Should have thrown a VMDaoException");
        } catch (VMDaoException ignored) {
        }
        try {
            dao = new VMDaoFileImpl(FILENAME);
        } catch (VMDaoException e) {
            Assertions.fail("Shouldn't have thrown a VMDaoException");
        }
        List<Item> lst = dao.listVMItems();
        Assertions.assertEquals(3, lst.size());
        Assertions.assertEquals(item, lst.get(1));
    }

    @Test
    void saveVM() throws VMDaoException {
        createFile();
        VMDao dao1 = new VMDaoFileImpl(FILENAME);
        try {
            dao1.saveVM();
        } catch (VMDaoException e) {
            Assertions.fail("Shouldn't have thrown a VMDaoException");
        }
        Item item1 = dao1.listVMItems().get(0);
        Assertions.assertEquals(99, dao1.listVMItems().get(0).getQuantity());
        item1.reduceQuantity();
        dao1.saveVM();

        VMDao dao2 = new VMDaoFileImpl(FILENAME);
        Item item2 = dao2.listVMItems().get(0);
        Assertions.assertEquals(98, dao2.listVMItems().get(0).getQuantity());

    }

    @Test
    void listVMItems() throws VMDaoException {
        createFile();
        VMDao dao = new VMDaoFileImpl(FILENAME);
        Item item1 = new Item("Food", "1.50", 99);
        Item item2 = new Item("Drink", "1.00", 100);
        Item item3 = new Item("Other", "0.80", 150);

        List<Item> lst = dao.listVMItems();
        Assertions.assertEquals(lst.get(0), item1);
        Assertions.assertEquals(lst.get(1), item2);
        Assertions.assertEquals(lst.get(2), item3);
        Assertions.assertNotEquals(lst.get(0), item3);
        Assertions.assertNotEquals(lst.get(1), item1);
        Assertions.assertNotEquals(lst.get(2), item2);
    }

    private void createFile() {
        try {
            File file = new File(FILENAME);
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            file.createNewFile();
            writer.println("Food::1.50::" + 99);
            writer.println("Drink::1.00::" + 100);
            writer.println("Other::0.80::" + 150);
            writer.flush();
            writer.close();
        } catch (IOException ignored) {
        }
    }
}