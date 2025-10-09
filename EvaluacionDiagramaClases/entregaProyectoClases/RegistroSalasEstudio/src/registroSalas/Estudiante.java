package registroSalas;

public class Estudiante {
	private String nombre; 
	private String programaEstudiante; 
	private String programaAcademico; 
	
	public Estudiante() {
		
	}
	public Estudiante(String nombre, String programaEstudiante, String programaAcademico) {
		super();
		this.nombre = nombre;
		this.programaEstudiante = programaEstudiante;
		this.programaAcademico = programaAcademico;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getProgramaEstudiante() {
		return programaEstudiante;
	}
	public void setProgramaEstudiante(String programaEstudiante) {
		this.programaEstudiante = programaEstudiante;
	}
	public String getProgramaAcademico() {
		return programaAcademico;
	}
	
	public void setProgramaAcademico(String programaAcademico) {
		this.programaAcademico = programaAcademico;
	}
	
	public void registrar() {
		System.out.println("el Estudiante se registro con exito");
	}
	
	@Override
	public String toString() {
		return "Estudiante [nombre=" + nombre + ", programaEstudiante=" + programaEstudiante + ", programaAcademico="
				+ programaAcademico + "]";
	}

}
