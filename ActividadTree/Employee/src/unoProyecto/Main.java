package unoProyecto;

public class Main {
    public static void main(String[] args) {
        // Crear empleado
        Employee emp1 = new Employee(1, "Juan", "Pérez", 2500);

        // Probar métodos
        System.out.println(emp1); // Usa toString
        System.out.println("ID: " + emp1.getID());
        System.out.println("Nombre: " + emp1.getName());
        System.out.println("Salario mensual: " + emp1.getSalary());
        System.out.println("Salario anual: " + emp1.getAnnualSalary());

        // Subir salario
        emp1.raiseSalary(10);
        System.out.println("Nuevo salario tras aumento: " + emp1.getSalary());

        // Cambiar salario manualmente
        emp1.setSalary(5000);
        System.out.println("Salario modificado: " + emp1.getSalary());
        System.out.println(emp1);
    }
}
