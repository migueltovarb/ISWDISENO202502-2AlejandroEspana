package five;

public class Main {
    public static void main(String[] args) {
        // Crear autor
        Author author1 = new Author("J. K. Rowling", "jkrowling@gmail.com", 'f');

        // Crear libros
        Book book1 = new Book("Harry Potter y la Piedra Filosofal", author1, 39.99);
        Book book2 = new Book("Harry Potter y la Cámara Secreta", author1, 45.50, 10);

        // Probar métodos
        System.out.println(book1);
        System.out.println(book2);

        // Modificar precio y cantidad
        book1.setPrice(42.00);
        book1.setQty(5);

        System.out.println("\nDespués de modificar:");
        System.out.println(book1);

        // Acceder a datos del autor desde Book
        System.out.println("\nAutor del libro 1:");
        System.out.println("Nombre: " + book1.getAuthor().getName());
        System.out.println("Email: " + book1.getAuthor().getEmail());
        System.out.println("Género: " + book1.getAuthor().getGender());
    }
}
