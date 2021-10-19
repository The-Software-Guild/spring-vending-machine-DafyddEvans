package com.m3.c130.vending_machine.view;

import com.m3.c130.vending_machine.Change;
import com.m3.c130.vending_machine.dto.Item;
import com.m3.c130.vending_machine.ui.UserIO;

import java.util.List;
import java.util.Map;


public class VMView {

    private final UserIO io;

    public VMView(UserIO io) {
        this.io = io;
    }

    public void displayBalance(Object balance) {
        io.print("\nYour balance is £" + balance);
    }

    public void displayMenu(List<Item> lst) {
        for (int i = 0; i < lst.size(); i++) {
            Item item = lst.get(i);
            io.print(i + 1 + ". " + item.getName() + " = £" + item.getCost());
        }
        io.print("0. Exit vending machine");
    }

    public double zeroBalance() {
        io.print("You have 0 balance");
        return increaseBalance();
    }

    public double getMoreBalance(String message) {
        io.print(message);
        return increaseBalance();
    }

    private double increaseBalance() {
        return io.readDouble("Please insert some money (enter 0 to leave the vending machine)", 0.00, 100.00);
    }

    public int chooseItem(List<Item> lst) {
        return io.readInt("What item would you like to purchase?", 0, lst.size());
    }

    public void itemPurchased(Item item) {
        io.print("Dispensing one " + item.getName() + ", thank you for your purchase");
    }

    public void dispenseCoins(Map<Change, Integer> map) {
        if (map.size() == 0) {
            io.print("Your balance is empty, no change required");
        } else {
            io.print("Your change is:");
            for (Map.Entry<Change, Integer> entry : map.entrySet()) {
                io.print(entry.getValue() + " X " + entry.getKey().name);
            }
        }
    }

    public void emptyVM() {
        io.print("The vending machine is empty, please come back when the vending machine is restocked");
    }

    public void displayErrorMessage(String message) {
        io.print(message);
    }
}
