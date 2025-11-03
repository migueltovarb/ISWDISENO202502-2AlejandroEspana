package momentoUno;

public class Account {
    // Atributos privados
    private String id;
    private String name;
    private int balance = 0;

    // Constructores
    public Account(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Account(String id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    // Getters
    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    // Agrega monto al balance y devuelve el nuevo balance
    public int credit(int amount) {
        balance += amount;
        return balance;
    }

    // Resta monto del balance si hay fondos suficientes
    public int debit(int amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Amount exceeded balance");
        }
        return balance;
    }

    // Transfiere monto a otra cuenta si hay fondos suficientes
    public int transferTo(Account another, int amount) {
        if (amount <= balance) {
            this.balance -= amount;
            another.balance += amount;
        } else {
            System.out.println("Amount exceeded balance");
        }
        return balance;
    }

    // Representación en texto del objeto
    @Override
    public String toString() {
        return "Account[id=" + id + ", name=" + name + ", balance=" + balance + "]";
    }
}
