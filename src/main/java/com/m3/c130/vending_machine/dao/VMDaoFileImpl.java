package com.m3.c130.vending_machine.dao;

import com.m3.c130.vending_machine.dto.Item;
import com.m3.c130.vending_machine.service.VMDaoException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class VMDaoFileImpl implements VMDao {
    private final String FILENAME = "Vending_Machine.txt";
    private final String DELIMITER = "::";
    private final List<Item> list = new ArrayList<>();

    public VMDaoFileImpl() throws VMDaoException {
        loadVM();
    }

    @Override
    public boolean loadVM() throws VMDaoException {
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));
            list.clear();
            while (sc.hasNextLine()) {
                String[] currentLine = sc.nextLine().split(DELIMITER);
                list.add(new Item(currentLine[0],
                        (currentLine[1]),
                        Integer.parseInt(currentLine[2])));
            }
        } catch (FileNotFoundException e) {
            throw new VMDaoException("Vending machine contents could not be loaded to memory", e);
        }
        return true;
    }

    @Override
    public boolean saveVM() throws VMDaoException {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILENAME));
            for (Item item : list) {
                writer.println(item.getName() + DELIMITER + item.getCost() + DELIMITER + item.getQuantity());
                writer.flush();
            }
            writer.close();
            return true;
        } catch (IOException e) {
            throw new VMDaoException("Could not save vending machine contents to memory", e);
        }
    }

    @Override
    public List<Item> listVMItems() {
        list.sort((i1, i2) -> i2.getCost().compareTo(i1.getCost()));
        return list;
    }
}
