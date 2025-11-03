package unidimensionalArray;

import java.util.Scanner;

public class Teacher {

    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        String exit;

        do {
            System.out.println("Type how many students you have:");
            int arraySize = myScanner.nextInt();

            if (arraySize > 0) {
                double[] grades = new double[arraySize];
                double sum = 0;
                double highestGrade = Double.MIN_VALUE;
                double lowestGrade = Double.MAX_VALUE;

                for (int i = 0; i < arraySize; i++) {
                    double grade;
                    do {
                        System.out.print("Enter grade for student " + (i + 1) + ": ");
                        grade = myScanner.nextDouble();
                        if (grade < 0) {
                            System.out.println("Invalid grade. Please enter a value >= 0.");
                        }
                    } while (grade < 0);

                    grades[i] = grade;
                    sum += grade;

                    if (grade > highestGrade) {
                        highestGrade = grade;
                    }
                    if (grade < lowestGrade) {
                        lowestGrade = grade;
                    }
                }

                double average = sum / arraySize;
                System.out.println("The average grade is: " + average);
                System.out.println("The highest grade is: " + highestGrade);
                System.out.println("The lowest grade is: " + lowestGrade);
            } else {
                System.out.println("Please enter a valid number greater than 0.");
            }

            System.out.println("Type 'exit' to quit or anything else to continue:");
            exit = myScanner.next();

        } while (!exit.equalsIgnoreCase("exit"));

        myScanner.close();
    }
}
