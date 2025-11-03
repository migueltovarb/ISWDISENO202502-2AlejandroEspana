package momentoUno;

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

    // Devuelve "firstName lastName"
    public String getName() {
        return firstName + " " + lastName;
    }

    public int getSalary() {
        return salary;
    }

    // Setter para salario
    public void setSalary(int salary) {
        this.salary = salary;
    }

    // Calcula salario anual (salary * 12)
    public int getAnnualSalary() {
        return salary * 12;
    }

    // Aumenta salario por porcentaje y devuelve el nuevo salario
    public int raiseSalary(int percent) {
        salary += salary * percent / 100;
        return salary;
    }

    // Representación en texto del empleado
    @Override
    public String toString() {
        return "Employee[id=" + id + ", name=" + getName() + ", salary=" + salary + "]";
    }
}

