package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter password: ");
        String password = sc.nextLine();
        PasswordChecker passwordChecker = new PasswordChecker(password);
        System.out.println(passwordChecker.makeChanges());
    }
}
