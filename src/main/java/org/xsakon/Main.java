package org.xsakon;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean keepLooping = true;

        while (keepLooping) {
            try {
                displayMenu();
                String choice = scanner.nextLine();
                switch (Integer.parseInt(choice)) {
                    case 1 -> System.out.println("case 1...");
                    case 2 -> keepLooping = false;
                    default -> System.out.println(choice + " not a valid option");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + ". Please enter a number");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("""
                \n
                Menu:
                1️⃣ - Test
                2️⃣ - Exit
                """);
    }
}

