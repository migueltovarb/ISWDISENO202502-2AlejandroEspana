package tree;

public class Main {
    public static void main(String[] args) {
        // Crear cuentas
        Account acc1 = new Account("A101", "Juan", 1000);
        Account acc2 = new Account("A102", "Maria");

        // Mostrar cuentas iniciales
        System.out.println(acc1);
        System.out.println(acc2);

        // Depositar en acc2
        acc2.credit(500);
        System.out.println("Después de depositar 500 a María: " + acc2);

        // Retirar de acc1
        acc1.debit(200);
        System.out.println("Después de retirar 200 a Juan: " + acc1);

        // Transferir de acc1 a acc2
        acc1.transferTo(acc2, 300);
        System.out.println("Después de transferir 300 de Juan a María:");
        System.out.println(acc1);
        System.out.println(acc2);

        // Intentar transferir más de lo que tiene
        acc1.transferTo(acc2, 2000);
        System.out.println(acc1);
        System.out.println(acc2);
    }
}
