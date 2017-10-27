package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Menu {

	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="costoProd")
	private float costoProd;
	
	@JsonProperty(value="precioVent")
	private float precioVent;
	
	@JsonProperty(value="vendidos")
	private int vendidos;
	
	@JsonProperty(value="disponibles")
	private int disponibles;
	
	@JsonProperty(value="restaurante")
	private String restaurante;
	
	@JsonProperty(value="idMenu")
	private int idMenu;
	
	@JsonProperty(value="productos")
	private List productos;
	
	public Menu(@JsonProperty(value="nombre")String nombre, @JsonProperty(value="costoProd")float costoProd, @JsonProperty(value="precioVent")float precioVent,
			@JsonProperty(value="vendidos")int vendidos, @JsonProperty(value="disponibles")int disponibles,@JsonProperty(value="restaurante")String restaurante,
			@JsonProperty(value="idMenu")int idMenu, @JsonProperty(value="productos") List productos) {
		super();
		this.nombre = nombre;
		this.costoProd = costoProd;
		this.precioVent = precioVent;
		this.vendidos = vendidos;
		this.disponibles = disponibles;
		this.restaurante = restaurante;
		this.idMenu = idMenu;
		this.productos = productos;
	}

	public List getProductos() {
		return productos;
	}

	public void setProductos(List productos) {
		this.productos = productos;
	}

	public int getIdMenu() {
		return idMenu;
	}



	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
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
	
	
	
}
