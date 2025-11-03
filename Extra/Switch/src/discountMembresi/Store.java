package discountMembresi;

import java.util.Scanner;

public class Store {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        String exit;

        do {
            System.out.println("Which plan are you on? (VIP, ESTANDAR, PREMIUM):");
            String plan = myScanner.next().toUpperCase(); 

            switch (plan) {
                case "VIP":
                    System.out.println("Your discount is 20%");
                    break;
                case "ESTANDAR":
                    System.out.println("Your discount is 5%");
                    break;
                case "PREMIUM":
                    System.out.println("Your discount is 10%");
                    break;
                default:
                    System.out.println("Please enter a valid plan");
            }

            System.out.println("Type 'exit' to stop, or press anything else to continue:");
            exit = myScanner.next();

        } while (!exit.equalsIgnoreCase("exit")); 

        myScanner.close();
    }
}
