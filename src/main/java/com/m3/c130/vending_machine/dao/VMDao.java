package com.m3.c130.vending_machine.dao;

import com.m3.c130.vending_machine.dto.Item;
import com.m3.c130.vending_machine.service.VMDaoException;

import java.io.FileNotFoundException;
import java.util.List;

public interface VMDao {
    boolean loadVM() throws FileNotFoundException, VMDaoException;

    boolean saveVM() throws VMDaoException;

    List<Item> listVMItems();
}
