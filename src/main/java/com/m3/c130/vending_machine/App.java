package com.m3.c130.vending_machine;

import com.m3.c130.vending_machine.controller.VMController;
import com.m3.c130.vending_machine.service.VMDaoException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.m3.c130.vending_machine");
        appContext.refresh();

        VMController controller = appContext.getBean("VMController", VMController.class);
        try {
            controller.run();
        } catch (VMDaoException e) {
            System.out.println(e.getMessage());
        }

    }
}
