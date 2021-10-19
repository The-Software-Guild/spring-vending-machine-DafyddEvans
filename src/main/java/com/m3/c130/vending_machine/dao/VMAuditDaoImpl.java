package com.m3.c130.vending_machine.dao;

import com.m3.c130.vending_machine.VMDaoException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VMAuditDaoImpl implements VMAuditDao {

    private final String FILENAME;

    public VMAuditDaoImpl(String fileName) {
        this.FILENAME = fileName;
    }

    @Override
    public void writeAuditEntry(String entry) throws VMDaoException {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILENAME, true), true);
            writer.write(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "::" + entry + "\n");
            writer.close();
        } catch (IOException e) {
            throw new VMDaoException("Could not save to audit file", e);
        }
    }
}
