package momentoUno;

import java.util.*;

//Clase Llantas
class Llanta {
 private String tamaño;
 private String tipo;

 public Llanta(String tamaño, String tipo) {
     this.tamaño = tamaño;
     this.tipo = tipo;
 }

 @Override
 public String toString() {
     return "Llantas [Tamaño: " + tamaño + ", Tipo: " + tipo + "]";
 }
}

//Clase Chasis
class Chasis {
 private double peso;
 private String material;

 public Chasis(double peso, String material) {
     this.peso = peso;
     this.material = material;
 }

 @Override
 public String toString() {
     return "Chasis [Peso: " + peso + " kg, Material: " + material + "]";
 }
}

//Clase Carro
class Carro {
 private Llanta llantas;
 private Chasis chasis;
 private String color;

 public Carro(Llanta llantas, Chasis chasis, String color) {
     this.llantas = llantas;
     this.chasis = chasis;
     this.color = color;
 }

 @Override
 public String toString() {
     return "Carro [Color: " + color + ", " + llantas + ", " + chasis + "]";
 }
}

//Clase Planta
class Planta {
 private String nombre;
 private String tamañoLlantas;
 private String tipoLlantas;
 private double pesoChasis;
 private String materialChasis;
 private List<String> coloresDisponibles;
 private Random random = new Random();

 public Planta(String nombre, String tamañoLlantas, String tipoLlantas,
               double pesoChasis, String materialChasis, List<String> colores) {
     this.nombre = nombre;
     this.tamañoLlantas = tamañoLlantas;
     this.tipoLlantas = tipoLlantas;
     this.pesoChasis = pesoChasis;
     this.materialChasis = materialChasis;
     this.coloresDisponibles = colores;
 }

 public Carro fabricarCarro() {
     // Crear partes
     Llanta llanta = new Llanta(tamañoLlantas, tipoLlantas);
     Chasis chasis = new Chasis(pesoChasis, materialChasis);
     // Elegir color aleatorio
     String color = coloresDisponibles.get(random.nextInt(coloresDisponibles.size()));
     // Crear carro
     return new Carro(llanta, chasis, color);
 }

 public String getNombre() {
     return nombre;
 }
}

//Clase Principal
public class FabricaCarros {
 public static void main(String[] args) {
     // Crear dos plantas
     Planta planta1 = new Planta(
             "Planta Norte", "17 pulgadas", "Todo Terreno",
             1200.0, "Acero",
             Arrays.asList("Rojo", "Negro", "Gris", "Azul")
     );

     Planta planta2 = new Planta(
             "Planta Sur", "15 pulgadas", "Ciudad",
             950.0, "Aluminio",
             Arrays.asList("Blanco", "Verde", "Plateado", "Amarillo")
     );

     // Cada planta fabrica 100 carros
     System.out.println("🚗 Fabricación en " + planta1.getNombre());
     for (int i = 1; i <= 100; i++) {
         Carro carro = planta1.fabricarCarro();
         System.out.println("Carro #" + i + ": " + carro);
     }

     System.out.println("\n🚙 Fabricación en " + planta2.getNombre());
     for (int i = 1; i <= 100; i++) {
         Carro carro = planta2.fabricarCarro();
         System.out.println("Carro #" + i + ": " + carro);
     }
 }
}
