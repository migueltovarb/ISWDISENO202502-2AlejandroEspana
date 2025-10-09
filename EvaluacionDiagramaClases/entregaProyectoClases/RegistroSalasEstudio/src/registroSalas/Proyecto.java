package registroSalas;



public class Proyecto {
	
	public static void main(String[] args) {
		Estudiante estudiante = new Estudiante("Marcos", "909922", "Ingenieria Software");
		estudiante.registrar();
		Estudiante estudiante2 = new Estudiante("Martin", "95822", "Medicina");
		estudiante2.registrar();
		Sala sala1 = new Sala(1, 30);
		sala1.registrar();
		sala1.reservar();
		sala1.mostrarSalasDisponibles();
		Sala sala2 = new Sala(2, 35);
		sala2.registrar();
		sala2.reservar();
		sala2.reservar();

		Reserva reserva = new Reserva(estudiante, sala2, 2025100800);
		Reserva reserva2 = new Reserva(estudiante2, sala1, 2025100860);
		reserva.registrar();
		reserva2.registrar();
		reserva.mostrarHistorialReservas();
		reserva2.mostrarHistorialReservas();
	}
	
	
}
