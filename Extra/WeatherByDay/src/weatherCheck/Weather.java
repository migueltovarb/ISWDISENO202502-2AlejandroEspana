package weatherCheck;

import java.util.Scanner;

public class Weather {

	public static void main(String[] args) {
		Scanner myScanner = new Scanner(System.in);
		String exit;
		String dayName = null;
		final double highTemperature = 30;
		final double lowTemperature = 15;

		
		
		
		do {
			double highestTemperature = Double.MIN_VALUE;
			double lowestTemperature = Double.MAX_VALUE;
			int coldDay = 0;
			int hotDay = 0;
			double sum = 0;
			double[] temperature = new double[7];

			for(int i = 0; i < temperature.length; i++ ) {
				switch(i){
					case 0:
						dayName = "monday";
						break;
					case 1:
						dayName = "tuesday";
						break;
					case 2:
						dayName = "wednesday ";
						break;
					case 3:
						dayName = "thursday";
						break;
					case 4:
						dayName = "fryday";
						break;
					case 5:
						dayName = "saturday";
						break;
					case 6:
						dayName = "sunday";
						break;
				}
					
				System.out.println("set the temperature for " + dayName);
				temperature[i] = myScanner.nextDouble();
				sum += temperature[i];
                if (temperature[i] > highestTemperature) {
                	highestTemperature = temperature[i];
                }
                if (temperature[i] < lowestTemperature) {
                	lowestTemperature = temperature[i];
                }	
                if (temperature[i] >= highTemperature) {
                	hotDay += 1;
                }		
                if (temperature[i] <= lowTemperature) {
                	coldDay += 1;
                }  
			}
			double average = sum / temperature.length; 
			System.out.println("the average temperature of the week was: " + average);
            System.out.println("the highest temperature of the week was: "+ highestTemperature);
            System.out.println("the lowest temperature of the week was: "+ lowestTemperature);
            System.out.println("days that are extreamly cold where: " + coldDay);
            System.out.println("days that are extreamly hot where:" + hotDay);
			System.out.println("Type exit to stop, whatever else to continue");
			exit = myScanner.next();
		}while(!exit.equalsIgnoreCase("exit"));
		myScanner.close();
	}

}
