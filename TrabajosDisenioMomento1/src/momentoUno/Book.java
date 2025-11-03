package momentoUno;

public class Book {
    // Atributos privados
    private String name;
    private Author author; // relación con la clase Author
    private double price;
    private int qty = 0; // valor inicial por defecto

    // Constructor con 3 parámetros
    public Book(String name, Author author, double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    // Constructor con 4 parámetros
    public Book(String name, Author author, double price, int qty) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.qty = qty;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    // Setters
    public void setPrice(double price) {
        this.price = price;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    // Representación en texto
    @Override
    public String toString() {
        // Reutiliza el método toString() de Author
        return "Book[name=" + name + ", " + author.toString() + ", price=" + price + ", qty=" + qty + "]";
    }
}
