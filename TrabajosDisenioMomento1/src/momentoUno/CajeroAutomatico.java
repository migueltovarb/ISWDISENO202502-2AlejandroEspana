package momentoUno;

import java.util.Scanner;

public class CajeroAutomatico {
 
    public static final double SALDO_INICIAL = 1000.0;

    public static void main(String[] args) {
 
        double saldo = SALDO_INICIAL;
        Scanner scanner = new Scanner(System.in);
        int opcion;

     
        do {

            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Depositar dinero");
            System.out.println("3. Retirar dinero");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();


            switch (opcion) {
                case 1:
                    System.out.println("Tu saldo actual es: $" + saldo);
                    break;

                case 2:
                    System.out.print("Ingrese la cantidad a depositar: ");
                    double deposito = scanner.nextDouble();
                    saldo += deposito;
                    System.out.println("Depósito exitoso. Nuevo saldo: $" + saldo);
                    break;

                case 3:
                    System.out.print("Ingrese la cantidad a retirar: ");
                    double retiro = scanner.nextDouble();
                    if (retiro <= saldo) {
                        saldo -= retiro;
                        System.out.println("Retiro exitoso. Nuevo saldo: $" + saldo);
                    } else {
                        System.out.println("Error: saldo insuficiente.");
                    }
                    break;

                case 4:
                    System.out.println("Gracias por usar el cajero. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción inválida. Intenta nuevamente.");
            }

        } while (opcion != 4);

        scanner.close();
    }
}
