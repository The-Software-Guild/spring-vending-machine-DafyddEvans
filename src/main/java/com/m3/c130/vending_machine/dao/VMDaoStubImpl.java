package com.m3.c130.vending_machine.dao;

import com.m3.c130.vending_machine.VMDaoException;
import com.m3.c130.vending_machine.dto.Item;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class VMDaoStubImpl implements VMDao {
    @Override
    public boolean loadVM() throws FileNotFoundException, VMDaoException {
        return true;
    }

    @Override
    public boolean saveVM() throws VMDaoException {
        return true;
    }

    @Override
    public List<Item> listVMItems() {
        Item item = new Item("Pepsi", "1.20", 1);
        return new ArrayList<>(List.of(item));
    }
}
