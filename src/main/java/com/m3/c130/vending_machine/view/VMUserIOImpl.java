package com.m3.c130.vending_machine.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class VMUserIOImpl implements UserIO {

    private Scanner sc = new Scanner(System.in);

    @Override
    public void print(Object str) {
        System.out.println(str);
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return sc.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        while (true) {
            try {
                print(prompt);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                print("Invalid input, please enter a integer");
            }
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                print(prompt);
                int input = Integer.parseInt(sc.nextLine());
                if (input >= min && input <= max) {
                    return input;
                } else {
                    print("Input out of range, please enter a value between " + min + " and " + max);
                }
            } catch (Exception e) {
                print("Invalid input, please enter a integer");
            }
        }
    }

    @Override
    public double readDouble(String prompt) {
        while (true) {
            try {
                print(prompt);
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                print("Invalid input, please enter a double");
            }
        }
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        while (true) {
            try {
                print(prompt);
                double input = Double.parseDouble(sc.nextLine());
                if (input >= min && input <= max) {
                    return input;
                } else {
                    print("Input out of range, please enter a value between " + min + " and " + max);
                }
            } catch (Exception e) {
                print("Invalid input, please enter a double");
            }
        }
    }
}
