package numeroEnteros;

import java.util.Scanner;

public class SumaNumeros {

	public static void main(String[] args) {
		
		Scanner myScanner = new Scanner(System.in);
		
		int number;
		int sum = 0;
		System.out.println("wellcom to our calculator, all the numbers you give are gona sum until you press 0");
		do {
			System.out.println("Give me the numbers you want to sum:");
			number = myScanner.nextInt();
			sum = number + sum;
				
		}while(number != 0);
		
        System.out.println("The total sum is: " + sum);

        myScanner.close();
		// TODO Auto-generated method stub

	}

}



