package registroSalas;

public class Reserva {
	private Estudiante estudiante;
	private Sala sala;
	private int fechaHora; 
	
	public Reserva() {
		
	}
	
	public Reserva(Estudiante estudiante, Sala sala, int fechaHora) {
		super();
		this.estudiante = estudiante;
		this.sala = sala;
		this.fechaHora = fechaHora;
	}
	
	public Estudiante getEstudiante() {
		return estudiante;
	}
	
	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}
	
	public Sala getSalas() {
		return sala;
	}
	
	public void setSalas(Sala salas) {
		this.sala = salas;
	}
	
	public int getFechaHora() {
		return fechaHora;
	}
	
	public void setFechaHora(int fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	public void registrar() {
		System.out.println("se registro la reserva correctamente");
	}

	
	public void mostrarHistorialReservas() {
		if (this.sala.isDisponibilidad() == true) {
			System.out.println(this.sala.toString());
		}
		
	}
	@Override
	public String toString() {
		return "Reserva [estudiante=" + estudiante + ", sala=" + sala + ", fechaHora=" + fechaHora + "]";
	}
	

}
