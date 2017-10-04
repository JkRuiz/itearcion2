package vos;

import org.codehaus.jackson.annotate.*;

public class Restaurante {

	/**
	 * Nombre del restaurante
	 */
	@JsonProperty(value="NOMBRE")
	private String nombre;
	
	/**
	 * Representante del restaurante
	 */
	@JsonProperty(value="REPRESENTANTE")
	private String representante;
	
	/**
	 * Tipo de comida del restaurante
	 */
	@JsonProperty(value="TIPOCOMIDA")
	private String tipoComida;
	
	/**
	 * Pagina web del restaurante
	 */
	@JsonProperty(value="PAGINAWEB")
	private String paginaWeb;
	
	/**
	 * Zona del restaurante
	 */
	@JsonProperty(value="ZONA")
	private String zona;
	
	/**
	 * Valor de costos del restaurante
	 */
	@JsonProperty(value="VALOR_COSTOS")
	private float valorCostos;
	
	/**
	 * Valor de ventas del restaurante
	 */
	@JsonProperty(value="VALOR_VENTAS")
	private float valorVentas;
	
	/**
	 * Metodo constructor
	 * @param nombre nombre del restaurante
	 * @param representante representante del restaurante
	 * @param tipoComida tipo de comida del restaurante
	 * @param paginaWeb pagina web del restaurante 
	 * @param zona zona donde se encuentra el restaurante 
	 * @param valorCostos valor de costos del restaurante 
	 * @param valorVentas valor de ventas del restaurante 
	 */
	public Restaurante(@JsonProperty(value="NOMBRE")String nombre, @JsonProperty(value="REPREENTANTE")String representante,@JsonProperty(value="TIPOCOMIDA")String tipoComida,
			@JsonProperty(value="PAGINAWEB")String paginaWeb, @JsonProperty(value="ZONA")String zona, @JsonProperty(value="VALOR_COSTOS")float valorCostos,
			@JsonProperty(value="VALOR_VENTAS")float valorVentas) {
		super();
		this.nombre = nombre;
		this.representante = representante;
		this.tipoComida = tipoComida;
		this.zona = zona;
		this.valorCostos = valorCostos;
		this.valorVentas = valorVentas;
	}

	/**
	 * Metodo getter del atributo nombre
	 * @return nombre del restaurante 
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo setter del atributo nombre
	 * @param nombre nombre del restaurante
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo getter del atributo representante
	 * @return representante del restaurante 
	 */
	public String getRepresentante() {
		return representante;
	}

	/**
	 * Metodo setter del atributo representante
	 * @param representante representante del restaurante 
	 */
	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	/**
	 * Metodo getter del atributo tipoComida
	 * @return tipo comida del restaurante
	 */
	public String getTipoComida() {
		return tipoComida;
	}

	/**
	 * Metodo setter del atributo tipoComida
	 * @param tipoComida tipo comida del restaurante 
	 */
	public void setTipoComida(String tipoComida) {
		this.tipoComida = tipoComida;
	}

	/**
	 * Metodo getter del atributo paginaWeb
	 * @return pagina web del restaurante
	 */
	public String getPaginaWeb() {
		return paginaWeb;
	}

	/**
	 * Metodo setter del atributo paginaWeb
	 * @param paginaWeb pagina web del restaurante
	 */
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	/**
	 * Metodo getter del atributo zona
	 * @return zona del restaurante
	 */
	public String getZona() {
		return zona;
	}

	/**
	 * Metodo setter del atributo zona
	 * @param zona zona del restaurante
	 */
	public void setZona(String zona) {
		this.zona = zona;
	}

	/**
	 * Metodo getter del atributo valorCostos
	 * @return valor de costos del restaurante 
	 */
	public float getValorCostos() {
		return valorCostos;
	}

	/**
	 * Metodo setter del atributo valorCostos
	 * @param valorCostos valor de costos del restaurante 
	 */
	public void setValorCostos(float valorCostos) {
		this.valorCostos = valorCostos;
	}

	/**
	 * Metodo getter del atributo valorVentas
	 * @return valor de ventas del restaurante 
	 */
	public float getValorVentas() {
		return valorVentas;
	}

	/**
	 * Metodo setter del atributo valorVentas
	 * @param valorVentas valor de ventas del restaurante
	 */
	public void setValorVentas(float valorVentas) {
		this.valorVentas = valorVentas;
	}
	
	
	
	
}
