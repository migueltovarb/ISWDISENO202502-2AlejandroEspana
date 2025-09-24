package unoProyecto;

public class Employee {
    // Atributos privados
    private int id;
    private String firstName;
    private String lastName;
    private int salary;

    // Constructor
    public Employee(int id, String firstName, String lastName, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    // Métodos getter
    public int getID() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public int getSalary() {
        return salary;
    }

    // Setter
    public void setSalary(int salary) {
        this.salary = salary;
    }

    // Salario anual
    public int getAnnualSalary() {
        return salary * 12;
    }

    // Aumentar salario por porcentaje
    public int raiseSalary(int percent) {
        salary += salary * percent / 100;
        return salary;
    }

    // toString
    @Override
    public String toString() {
        return "Employee[id=" + id + ", name=" + firstName + " " + lastName + ", salary=" + salary + "]";
    }
}
