package registroSalas;

public class Sala {
	
	private int numeroSala;
	private int capacidadMaxima;
	private boolean disponibilidad = true;
	
	public Sala() {
		
	}
	

	public Sala(int numeroSala, int capacidadMaxima) {
		super();
		this.numeroSala = numeroSala;
		this.capacidadMaxima = capacidadMaxima;
	}
	public int getNumeroSala() {
		return numeroSala;
	}
	public void setNumeroSala(int numeroSala) {
		this.numeroSala = numeroSala;
	}
	public int getCapacidadMaxima() {
		return capacidadMaxima;
	}
	public void setCapacidadMaxima(int capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}
	public boolean isDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	
	public void registrar() {
		System.out.println("Se registro la sala con exito");
	}
	
	public int mostrarSalasDisponibles() {
		int disponible = 0;
		if(this.disponibilidad == true) {
			 disponible = this.numeroSala;
		}
		return disponible;
	}
	public void reservar() {
		if (!this.disponibilidad){
			System.out.println("sala no disponible");
		}
		else {
			this.disponibilidad = false;
			System.out.println("se ha reservado la sala "+ this.numeroSala);
		}
		
	}
	
	@Override
	public String toString() {
		return "Sala [numeroSala=" + numeroSala + ", capacidadMaxima=" + capacidadMaxima + ", disponibilidad="
				+ disponibilidad + "]";
	}

}
