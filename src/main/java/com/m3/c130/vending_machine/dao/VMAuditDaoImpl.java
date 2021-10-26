package com.m3.c130.vending_machine.dao;

import com.m3.c130.vending_machine.service.VMDaoException;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class VMAuditDaoImpl implements VMAuditDao {

    @Override
    public void writeAuditEntry(String entry) throws VMDaoException {
        try {
            String FILENAME = "Vending_Machine_Audit.txt";
            PrintWriter writer = new PrintWriter(new FileWriter(FILENAME, true), true);
            writer.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "::" + entry);
            writer.close();
        } catch (IOException e) {
            throw new VMDaoException("Could not save to audit file", e);
        }
    }
}
