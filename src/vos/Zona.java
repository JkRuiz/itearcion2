package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Zona{

	@JsonProperty(value="NOMBRE")
	private String nombre;

	@JsonProperty(value="CAPACIDAD")
	private int capacidad;

	@JsonProperty(value="ABIERTO")
	private int abierto;

	@JsonProperty(value="APTODISCAPACIDAD")
	private int aptoDiscapacidad;

	@JsonProperty(value="DESCRIPCIONTECNICA")
	private String descripcionTecnica;

	public Zona(@JsonProperty(value="NOMBRE")String nombre,@JsonProperty(value="CAPACIDAD") int capacidad, @JsonProperty(value="ABIERTO")int abierto, 
			@JsonProperty(value="APTODISCAPACIDAD")int aptoDiscapacidad, @JsonProperty(value="DESCRIPCIONTECNICA")String descripcionTecnica) {
		super();
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.abierto = abierto;
		this.aptoDiscapacidad = aptoDiscapacidad;
		this.descripcionTecnica = descripcionTecnica;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getAbierto() {
		return abierto;
	}

	public void setAbierto(int abierto) {
		this.abierto = abierto;
	}

	public int getAptoDiscapacidad() {
		return aptoDiscapacidad;
	}

	public void setAptoDiscapacidad(int aptoDiscapacidad) {
		this.aptoDiscapacidad = aptoDiscapacidad;
	}

	public String getDescripcionTecnica() {
		return descripcionTecnica;
	}

	public void setDescripcionTecnica(String descripcionTecnica) {
		this.descripcionTecnica = descripcionTecnica;
	}


}
