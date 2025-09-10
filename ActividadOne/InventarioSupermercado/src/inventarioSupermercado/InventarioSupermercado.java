package inventarioSupermercado;

import java.util.Scanner;

public class InventarioSupermercado {
    public static final int MAX_PRODUCTOS = 5;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner myScanner = new Scanner(System.in);
        String[] nombres = new String[MAX_PRODUCTOS];
        int[] cantidades = new int[MAX_PRODUCTOS];
        int totalProductos = 0;

		for (int i = 0; i < MAX_PRODUCTOS; i++) {
			
			System.out.println("ingresa el nombre del producto numero " + (i + 1));
			nombres[i] = myScanner.next().toLowerCase();
			
            int cantidad;
            do {
                System.out.print("Cantidad de " + nombres[i]);
                cantidad = myScanner.nextInt();
                if (cantidad < 0) {
                    System.out.println("Cantidad no válida. Debe ser mayor o igual a 0.");
                }
            } while (cantidad < 0);
            cantidades[i] = cantidad;
            totalProductos += cantidad;
		}
		int opcion;
        do {
            System.out.println("===== MENÚ =====");
            System.out.println("1. Mostrar productos y existencias");
            System.out.println("2. Buscar producto por nombre");
            System.out.println("3. Actualizar inventario");
            System.out.println("4. Alerta de productos con stock menores a 10");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = myScanner.nextInt();
            myScanner.nextLine();


            switch (opcion) {
                case 1:
                    System.out.println("Inventario:");
                    for (int i = 0; i < MAX_PRODUCTOS; i++) {
                        System.out.println(nombres[i] + ": " + cantidades[i] + " unidades");
                    }
                    System.out.println("Total acumulado de productos: " + totalProductos);
                    break;

                case 2:
                    System.out.print("Ingrese el nombre del producto a buscar: ");
                    String buscar = myScanner.nextLine().toLowerCase();
                    boolean encontrado = false;
                    for (int i = 0; i < MAX_PRODUCTOS; i++) {
                        if (nombres[i].equalsIgnoreCase(buscar)) {
                            System.out.println(nombres[i] + ": " + cantidades[i] + " unidades");
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("Ingrese el nombre del producto a actualizar: ");
                    String actualizar = myScanner.nextLine();
                    encontrado = false;
                    for (int i = 0; i < MAX_PRODUCTOS; i++) {
                        if (nombres[i].equalsIgnoreCase(actualizar)) {
                            System.out.print("Ingrese cantidad a agregar (número negativo para restar): ");
                            int cambio = myScanner.nextInt();

                            if (cantidades[i] + cambio < 0) {
                                System.out.println("No se puede dejar la cantidad negativa.");
                            } else {
                                cantidades[i] += cambio;
                                totalProductos += cambio;
                                System.out.println("Inventario actualizado.");
                                System.out.println(nombres[i] + ": " + cantidades[i] + " unidades");
                            }
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 4:
                	int b = 0;
                    System.out.println("Productos con menos de 10 unidades:");
                    for (int i = 0; i < MAX_PRODUCTOS; i++) {
                        if (cantidades[i] < 10) {
                        	b =+ 1;
                            System.out.println(nombres[i] + ": " + cantidades[i] + " unidades");
                            
                        }
                    }
                    if(b == 0) {
                    	System.out.println("Ningun producto tiene menos de 10 unidades");
                    }
                    break;

                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 5);
        
        myScanner.close();


    
	}
		
}
