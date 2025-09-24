package secondProyect;

public class Main {
    public static void main(String[] args) {
        // Crear un InvoiceItem
        InvoiceItem item1 = new InvoiceItem("A101", "Laptop", 2, 1500.50);

        // Probar métodos
        System.out.println(item1); // toString
        System.out.println("ID: " + item1.getID());
        System.out.println("Descripción: " + item1.getDesc());
        System.out.println("Cantidad: " + item1.getQty());
        System.out.println("Precio unitario: $" + item1.getUnitPrice());
        System.out.println("Total: $" + item1.getTotal());

        // Modificar cantidad y precio
        item1.setQty(3);
        item1.setUnitPrice(1400.00);

        System.out.println("\n--- Después de modificar ---");
        System.out.println("Cantidad: " + item1.getQty());
        System.out.println("Nuevo precio unitario: $" + item1.getUnitPrice());
        System.out.println("Nuevo total: $" + item1.getTotal());
        System.out.println(item1);
    }
}
