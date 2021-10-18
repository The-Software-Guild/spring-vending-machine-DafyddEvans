package com.m3.c130.vending_machine.dao;

import com.m3.c130.vending_machine.dto.Item;

import java.io.*;
import java.util.*;

public class VMDaoFileImpl implements VMDao {
    private final String FILENAME;
    private final String DELIMITER = "::";
    private Map<String, Item> map = new HashMap<>();

    public VMDaoFileImpl(String fileName) {
        this.FILENAME = fileName;
        loadVM();
    }

    @Override
    public boolean loadVM() {
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));
            map.clear();
            while (sc.hasNextLine()) {
                String[] currentLine = sc.nextLine().split(DELIMITER);
                map.put(currentLine[0], new Item(currentLine[0],
                        Double.parseDouble(currentLine[1]),
                        Integer.parseInt(currentLine[2])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("error");
        }
        return true;
    }

    @Override
    public boolean saveVM() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILENAME));
            for (Item item : map.values()) {
                writer.println(item.getName() + DELIMITER + item.getCost() + DELIMITER + item.getQuantity());
                writer.flush();
            }
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<Item> listVMItems() {
        return new ArrayList<>(map.values());
    }
}
