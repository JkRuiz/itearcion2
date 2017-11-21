package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductoDetalle {
	@JsonProperty(value="nombre")
	private String nombre;

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="categoria")
	private String categoria;
	
	@JsonProperty(value="numVentas")
	private int numVentas;
	
	@JsonProperty(value="dia")
	private String dia;

	public ProductoDetalle(@JsonProperty(value="nombre") String nombre, @JsonProperty(value="id")int id, 
			@JsonProperty(value="categoria") String categoria, @JsonProperty(value="numVentas") int numVentas, 
			@JsonProperty(value="dia") String dia) {
		super();
		this.nombre = nombre;
		this.id = id;
		this.categoria = categoria;
		this.numVentas = numVentas;
		this.dia = dia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getNumVentas() {
		return numVentas;
	}

	public void setNumVentas(int numVentas) {
		this.numVentas = numVentas;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}
	
	
	 

}
