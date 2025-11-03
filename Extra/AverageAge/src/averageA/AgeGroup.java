package averageA;

public class AgeGroup {
    private int[] ages;
    private int sum;
    

    public AgeGroup(int size) {
        ages = new int[size];
        sum = 0;
    }


    public void addAge(int index, int age) {
        if (age >= 0) {
            ages[index] = age;
            sum += age;
        } else {
            System.out.println("Not negative age");
        }
    }


    public double getAverage() {
        if (ages.length == 0) {
            return 0;
        }
        return (double) sum / ages.length;
    }
}
