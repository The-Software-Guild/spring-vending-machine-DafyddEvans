package com.m3.c130.vending_machine.dao;

import com.m3.c130.vending_machine.VMDaoException;

public interface VMAuditDao {
    void writeAuditEntry(String entry) throws VMDaoException;
}
