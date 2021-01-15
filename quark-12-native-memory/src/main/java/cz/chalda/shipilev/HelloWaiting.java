package cz.chalda.shipilev;

import java.util.Scanner;

public class HelloWaiting {
    public static void main(String[] args) {
        System.out.println("Enter 'q' to exit...");
        String inputString = null;

        try(Scanner inputScanner = new Scanner(System.in)) {
            while (!"q".equalsIgnoreCase(inputString)) {
                inputString = inputScanner.next();
            }
        }
    }
}
