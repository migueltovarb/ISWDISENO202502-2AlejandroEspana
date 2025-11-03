package arrayBreak;

import java.util.Scanner;

public class BreakArray {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        int[] numbers = {1, 2, 3, 4, 5};
        String option;

        do {
            System.out.println("What number would you like to find?");
            int numberToFind = myScanner.nextInt();

            boolean found = false;
            for (int i = 0; i < numbers.length; i++) {
                if (numbers[i] == numberToFind) {
                    System.out.println("Number found at position: " + i);
                    found = true;
                    break; // Salimos del bucle porque ya encontramos el número
                }
            }

            if (!found) {
                System.out.println("Number not found.");
            }

            System.out.println("Type 'exit' to quit or anything else to continue:");
            option = myScanner.next();

        } while (!option.equals("exit"));

        myScanner.close();
    }
}
