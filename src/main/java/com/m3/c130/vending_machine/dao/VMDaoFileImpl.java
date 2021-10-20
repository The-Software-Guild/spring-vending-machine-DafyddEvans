package com.m3.c130.vending_machine.dao;

import com.m3.c130.vending_machine.VMDaoException;
import com.m3.c130.vending_machine.dto.Item;

import java.io.*;
import java.util.*;

public class VMDaoFileImpl implements VMDao {
    private final String FILENAME;
    private final String DELIMITER = "::";
    private Map<String, Item> map = new HashMap<>();

    public VMDaoFileImpl(String fileName) throws VMDaoException {
        this.FILENAME = fileName;
        loadVM();
    }

    @Override
    public boolean loadVM() throws VMDaoException {
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));
            map.clear();
            while (sc.hasNextLine()) {
                String[] currentLine = sc.nextLine().split(DELIMITER);
                map.put(currentLine[0], new Item(currentLine[0],
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
            for (Item item : map.values()) {
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
        List<Item> lst = new ArrayList<>(map.values());
        lst.sort((i1, i2) -> i2.getCost().compareTo(i1.getCost()));
        return lst;
    }
}
