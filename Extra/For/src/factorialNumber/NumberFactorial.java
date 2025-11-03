package factorialNumber;

import java.util.Scanner;

public class NumberFactorial {

    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        String exit;

        do {
            System.out.println("Number to calculate factorial:");
            int number = myScanner.nextInt();
            int factorial = 1;

            if (number >= 0) {
                for (int i = number; i > 0; i--) {
                    factorial *= i;
                }
                System.out.println("The factorial of " + number + " is: " + factorial);
            } else {
                System.out.println("Factorial is not defined for negative numbers.");
            }

            System.out.println("Enter 'exit' to stop, or press anything else to continue:");
            exit = myScanner.next();

        } while (!exit.equals("exit")); // ignore case for better UX

        myScanner.close();
    }
}
