package com.m3.c130.vending_machine;

import com.m3.c130.vending_machine.controller.VMController;
import com.m3.c130.vending_machine.dao.VMDao;
import com.m3.c130.vending_machine.dao.VMDaoFileImpl;
import com.m3.c130.vending_machine.service.VMServiceLayer;
import com.m3.c130.vending_machine.service.VMServiceLayerImpl;
import com.m3.c130.vending_machine.ui.UserIO;
import com.m3.c130.vending_machine.ui.VMUserIOImpl;
import com.m3.c130.vending_machine.view.VMView;

public class App {
    public static void main(String[] args) {
        UserIO io = new VMUserIOImpl();
        VMDao dao = new VMDaoFileImpl("Vending_Machine.txt");
        VMServiceLayer service = new VMServiceLayerImpl(dao);
        VMController controller = new VMController(new VMView(io), service);
        controller.run();
    }
}
