package com.m3.c130.vending_machine.controller;

import com.m3.c130.vending_machine.dto.Item;
import com.m3.c130.vending_machine.service.VMServiceLayer;
import com.m3.c130.vending_machine.view.VMView;

import java.math.BigDecimal;
import java.util.List;


public class VMController {

    private final VMView view;
    private final VMServiceLayer service;

    public VMController(VMView view, VMServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        while (true) {
            try {
                view.displayBalance(service.getBalance());
                List<Item> lst = service.listVMItems();
                view.displayMenu(lst);
                int choice;
                if (lst.size() == 0) {
                    view.emptyVM();
                    choice = 0;
                } else {
                    choice = getChoice();
                }
                if (choice == 0) {
                    break;
                } else if (choice > 0) {
                    if (purchaseItem(lst.get(choice - 1))) {
                        view.dispenseCoins(service.balanceToCoins());
                    } else {
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        view.dispenseCoins(service.balanceToCoins());
    }

    public int getChoice() {
        if (service.getBalance().doubleValue() == 0) {
            double inserted = view.zeroBalance();
            if (inserted == 0) {
                return 0;
            } else {
                service.addBalance(BigDecimal.valueOf(inserted));
                return -1;
            }
        } else {
            return view.chooseItem(service.listVMItems());
        }
    }

    public boolean purchaseItem(Item item) {
        if (!service.purchaseItem(item)) {
            view.insufficientFunds(service.getBalance(), item.getCost());
            double inserted = view.getMoreBalance();
            if (inserted == 0) {
                return false;
            } else {
                service.addBalance(BigDecimal.valueOf(inserted));
                purchaseItem(item);
            }
        }
        view.itemPurchased(item);
        return true;
    }
}
