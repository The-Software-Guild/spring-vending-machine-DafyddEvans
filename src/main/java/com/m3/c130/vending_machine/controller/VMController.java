package com.m3.c130.vending_machine.controller;

import com.m3.c130.vending_machine.InsufficientFundsException;
import com.m3.c130.vending_machine.NoItemInventoryException;
import com.m3.c130.vending_machine.VMDaoException;
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

    public void run() throws VMDaoException {
        while (true) {
            try {
                view.displayBalance(service.getBalance());
                List<Item> lst = service.listVMItems();
                view.displayMenu(lst);
                if (lst.size() == 0) {
                    view.emptyVM();
                    break;
                }

                int choice = getChoice();
                if (choice == 0) {
                    break;
                } else if (choice > 0) {
                    purchaseItem(lst.get(choice - 1));
                }
            } catch (VMDaoException | NoItemInventoryException e) {
                view.displayErrorMessage(e.getMessage());
            } catch (InsufficientFundsException e) {
                double inserted = view.getMoreBalance(e.getMessage());
                if (inserted == 0) {
                    break;
                } else {
                    service.addBalance(BigDecimal.valueOf(inserted));
                }
            }
        }
        view.dispenseCoins(service.balanceToCoins());
    }

    public int getChoice() throws VMDaoException {
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

    public void purchaseItem(Item item) throws VMDaoException, NoItemInventoryException, InsufficientFundsException {
        if (service.purchaseItem(item)) {
            view.itemPurchased(item);
            view.dispenseCoins(service.balanceToCoins());
        }
    }
}
