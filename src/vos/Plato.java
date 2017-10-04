package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Plato {

	/**
	 * Nombre del plato
	 */
	@JsonProperty(value="NOMBRE")
	private String nombre;
		
	/**
	 * Nombre del plato
	 */
	@JsonProperty(value="DESCRIPCION")
	private String descripcion;
	
	/**
	 * Nombre del plato
	 */
	
	@JsonProperty(value="TRADUCCION")
	private String traduccion;
	
	/**
	 * tiempo de preparacion del plato
	 */
	
	@JsonProperty(value="TIEMPOPREPARACION")
	private float tiempoPreparacion;
	
	/**
	 * costo de produccion del plato
	 */
	@JsonProperty(value="COSTOPRODUCCION")
	private float costoProduccion;
	
	/**
	 * precio de venta del plato
	 */
	@JsonProperty(value="PRECIOVENTA")
	private float precioVenta;
	
	/**
	 * Nomrbe del restaurante del plato
	 */
	@JsonProperty(value="RESTAURANTE")
	private String restaurante;
	
	/**
	 * id del plato
	 */
	@JsonProperty(value="ID_PLATO")
	private int idPlato;
	
	/**
	 * cantidad de platos vendidos
	 */
	@JsonProperty(value="VENDIDOS")
	private int vendidos;
	
	/**
	 * cantidad de platos disponibles
	 */
	@JsonProperty(value="DISPONIBLES")
	private int disponibles;
	
	/**
	 * tipo del plato
	 */
	@JsonProperty(value="TIPO")
	private String tipo;
	
	/**
	 * categoria del plato
	 */
	@JsonProperty(value="CATEGORIA")
	private String categoria;

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
	public Plato(@JsonProperty(value="NOMBRE")String nombre,@JsonProperty(value="DESCRIPCION")String descripcion, @JsonProperty(value="TRADUCCION")String traduccion, 
			@JsonProperty(value="TIEMPOPREPARACION")float tiempoPreparacion, @JsonProperty(value="COSTOPRODUCCION")float costoProduccion,
			@JsonProperty(value="PRECIOVENTA")float precioVenta, @JsonProperty(value="RESTAURANTE")String restaurante, 
			@JsonProperty(value="ID_PLATO")int idPlato,@JsonProperty(value="VENDIDOS") int vendidos, @JsonProperty(value="DISPONIBLES")int disponibles, 
			@JsonProperty(value="TIPO")String tipo,@JsonProperty(value="CATEGORIA")String categoria) {
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
