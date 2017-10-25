package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class Plato {

	/**
	 * Nombre del plato
	 */
	@JsonProperty(value="nombre")
	private String nombre;
		
	/**
	 * Nombre del plato
	 */
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	/**
	 * Nombre del plato
	 */
	
	@JsonProperty(value="traduccion")
	private String traduccion;
	
	/**
	 * tiempo de preparacion del plato
	 */
	
	@JsonProperty(value="tiempoPreparacion")
	private float tiempoPreparacion;
	
	/**
	 * costo de produccion del plato
	 */
	@JsonProperty(value="costoProduccion")
	private float costoProduccion;
	
	/**
	 * precio de venta del plato
	 */
	@JsonProperty(value="precioVenta")
	private float precioVenta;
	
	/**
	 * Nomrbe del restaurante del plato
	 */
	@JsonProperty(value="restaurante")
	private String restaurante;
	
	/**
	 * id del plato
	 */
	@JsonProperty(value="idPlato")
	private int idPlato;
	
	/**
	 * cantidad de platos vendidos
	 */
	@JsonProperty(value="vendidos")
	private int vendidos;
	
	/**
	 * cantidad de platos disponibles
	 */
	@JsonProperty(value="disponibles")
	private int disponibles;
	
	/**
	 * tipo del plato
	 */
	@JsonProperty(value="tipo")
	private String tipo;
	
	/**
	 * categoria del plato
	 */
	@JsonProperty(value="categoria")
	private String categoria;
	
	@JsonProperty(value="equivalentes")
	private ArrayList equivalentes;

	/**
	 * Metodo constructor 
	 * @param nombre nombre del plato 
	 * @param descripcion descripcion del plato 
	 * @param traduccion traduccion de la descripcion del plato
	 * @param tiempoPreparacion tiempoPreparacion del plato
	 * @param costoProduccion costoProduccion del plato
	 * @param precioVenta precioVenta del plato
	 * @param restaurante nombre del restaurante del plato
	 * @param idPlato id del plato
	 * @param vendidos cantidad de platos vendidos
	 * @param disponibles cantidad de platos disponibles
	 * @param tipo tipo del plato
	 * @param categoria categoria del plato
	 */
	public Plato(@JsonProperty(value="nombre")String nombre,@JsonProperty(value="descripcion")String descripcion, @JsonProperty(value="traduccion")String traduccion, 
			@JsonProperty(value="tiempoPreparacion")float tiempoPreparacion, @JsonProperty(value="costoProduccion")float costoProduccion,
			@JsonProperty(value="precioVenta")float precioVenta, @JsonProperty(value="restaurante")String restaurante, 
			@JsonProperty(value="idPlato")int idPlato,@JsonProperty(value="vendidos") int vendidos, @JsonProperty(value="disponibles")int disponibles, 
			@JsonProperty(value="tipo")String tipo,@JsonProperty(value="categoria")String categoria,@JsonProperty(value="equivalentes") ArrayList equivalentes) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.traduccion = traduccion;
		this.tiempoPreparacion = tiempoPreparacion;
		this.costoProduccion = costoProduccion;
		this.precioVenta = precioVenta;
		this.restaurante = restaurante;
		this.idPlato = idPlato;
		this.vendidos = vendidos;
		this.disponibles = disponibles;
		this.tipo = tipo;
		this.categoria = categoria;
		this.equivalentes = equivalentes;
	}

	public ArrayList getEquivalentes() {
		return equivalentes;
	}

	public void setEquivalentes(ArrayList equivalentes) {
		this.equivalentes = equivalentes;
	}

	/**
	 * Me canse de comentar todo
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTraduccion() {
		return traduccion;
	}

	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}

	public float getTiempoPreparacion() {
		return tiempoPreparacion;
	}

	public void setTiempoPreparacion(float tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	public float getCostoProduccion() {
		return costoProduccion;
	}

	public void setCostoProduccion(float costoProduccion) {
		this.costoProduccion = costoProduccion;
	}

	public float getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(float precioVenta) {
		this.precioVenta = precioVenta;
	}

	public String getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(String restaurante) {
		this.restaurante = restaurante;
	}

	public int getIdPlato() {
		return idPlato;
	}

	public void setIdPlato(int idPlato) {
		this.idPlato = idPlato;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	
	
	
}
