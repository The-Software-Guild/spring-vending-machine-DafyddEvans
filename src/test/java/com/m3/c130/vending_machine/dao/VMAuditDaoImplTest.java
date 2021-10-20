package com.m3.c130.vending_machine.dao;

import com.m3.c130.vending_machine.VMDaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

class VMAuditDaoImplTest {

    private final String FILENAME = "VM_Audit_test.txt";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void writeAuditEntry() throws IOException, VMDaoException {
        createFile();
        VMAuditDao dao1 = new VMAuditDaoImpl(FILENAME);

        dao1.writeAuditEntry("Audit entry 1");
        Assertions.assertEquals(1, counter());
        dao1.writeAuditEntry("Audit entry 2");
        dao1.writeAuditEntry("Audit entry 3");
        Assertions.assertEquals(3, counter());
    }

    private void createFile() {
        try {
            File file = new File(FILENAME);
            FileWriter writer = new FileWriter(file);
            file.createNewFile();
            writer.write("");
            writer.flush();
            writer.close();
        } catch (IOException ignored) {
        }
    }

    private int counter() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }
}