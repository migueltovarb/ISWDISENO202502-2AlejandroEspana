package four;

public class Main {
    public static void main(String[] args) {
        // Crear autores
        Author a1 = new Author("Gabriel García Márquez", "ggm@gmail.com", 'm');
        Author a2 = new Author("Isabel Allende", "isabel@gmail.com", 'f');

        // Probar métodos
        System.out.println(a1);
        System.out.println("Nombre: " + a1.getName());
        System.out.println("Email: " + a1.getEmail());
        System.out.println("Género: " + a1.getGender());

        // Cambiar email
        a1.setEmail("gabriel.marquez@literatura.com");
        System.out.println("\nDespués de actualizar el email:");
        System.out.println(a1);

        System.out.println("\nOtro autor:");
        System.out.println(a2);
    }
}