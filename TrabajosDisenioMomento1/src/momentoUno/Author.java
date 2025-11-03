package momentoUno;

public class Author {
    // Atributos privados
    private String name;
    private String email;
    private char gender; // 'm' o 'f'

    // Constructor (sin valores por defecto)
    public Author(String name, String email, char gender) {
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    // Getter del nombre
    public String getName() {
        return name;
    }

    // Getter del email
    public String getEmail() {
        return email;
    }

    // Setter del email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter del género
    public char getGender() {
        return gender;
    }

    // Representación en texto del objeto
    @Override
    public String toString() {
        return "Author[name=" + name + ", email=" + email + ", gender=" + gender + "]";
    }
}
