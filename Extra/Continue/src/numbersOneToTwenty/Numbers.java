package numbersOneToTwenty;

public class Numbers {
    public static void main(String[] args) {
        int number = 20;

        for (int i = 1; i <= number; i++) {
            if (i % 3 != 0) {
            	System.out.print(i + " ");
            }else {
            	continue;
            }
            
        }
    }
}

