package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Menu {

	
	@JsonProperty(value="NOMBRE")
	private String nombre;
	
	@JsonProperty(value="COSTOPROD")
	private float costoProd;
	
	@JsonProperty(value="PRECIOVENT")
	private float precioVent;
	
	@JsonProperty(value="VENDIDOS")
	private int vendidos;
	
	@JsonProperty(value="DISPONIBLES")
	private int disponibles;
	
	@JsonProperty(value="NOMBRERESTAURANTE")
	private String restaurante;
	
	@JsonProperty(value="ID_MENU")
	private int id;

	public Menu(@JsonProperty(value="NOMBRE")String nombre, @JsonProperty(value="COSTOPROD")float costoProd, @JsonProperty(value="PRECIOVENT")float precioVent,
			@JsonProperty(value="VENDIDOS")int vendidos, @JsonProperty(value="DISPONIBLES")int disponibles,@JsonProperty(value="NOMBRERESTAURANTE")String restaurante,
			@JsonProperty(value="ID_MENU")int id) {
		super();
		this.nombre = nombre;
		this.costoProd = costoProd;
		this.precioVent = precioVent;
		this.vendidos = vendidos;
		this.disponibles = disponibles;
		this.restaurante = restaurante;
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getCostoProd() {
		return costoProd;
	}

	public void setCostoProd(float costoProd) {
		this.costoProd = costoProd;
	}

	public float getPrecioVent() {
		return precioVent;
	}

	public void setPrecioVent(float precioVent) {
		this.precioVent = precioVent;
	}

	public int getVendidos() {
		return vendidos;
	}

	public void setVendidos(int vendidos) {
		this.vendidos = vendidos;
	}

	public int getDisponibles() {
		return disponibles;
	}

	public void setDisponibles(int disponibles) {
		this.disponibles = disponibles;
	}

	public String getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(String restaurante) {
		this.restaurante = restaurante;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
