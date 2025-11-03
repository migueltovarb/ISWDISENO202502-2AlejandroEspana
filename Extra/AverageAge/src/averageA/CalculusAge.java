package averageA;

import java.util.Scanner;

public class CalculusAge {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many people are in the group? ");
        int n = scanner.nextInt();

        AgeGroup group = new AgeGroup(n);

        for (int i = 0; i < n; i++) {
            System.out.print("Type the member age " + (i + 1) + ": ");
            int age = scanner.nextInt();
            group.addAge(i, age);
        }

        double promedio = group.getAverage();
        System.out.println("Here is the average age of the group: " + promedio);
        scanner.close();
    }
    
}