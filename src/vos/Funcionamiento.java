package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Funcionamiento {

	@JsonProperty(value="Dia")
	private String Dia;
	
	@JsonProperty(value="productoMasConsumido")
	private ProductoDetalle productoMasConsumido;

	@JsonProperty(value="productoMenosConsumido")
	private ProductoDetalle productoMenosConsumido;
	
	@JsonProperty(value="restauranteMasFrecuentado")
	private RestauranteDetalle restauranteMasFrecuentado;
	
	@JsonProperty(value="restauranteMenosFrecuentado")
	private RestauranteDetalle restauranteMenosFrecuentado;

	public Funcionamiento(@JsonProperty(value="Dia") String Dia, @JsonProperty(value="productoMasConsumido") ProductoDetalle productoMasConsumido, @JsonProperty(value="productoMenosConsumido") ProductoDetalle productoMenosConsumido,
			@JsonProperty(value="restauranteMasFrecuentado")RestauranteDetalle restauranteMasFrecuentado, @JsonProperty(value="restauranteMenosFrecuentado") RestauranteDetalle restauranteMenosFrecuentado) {
		super();
		this.Dia = Dia;
		this.productoMasConsumido = productoMasConsumido;
		this.productoMenosConsumido = productoMenosConsumido;
		this.restauranteMasFrecuentado = restauranteMasFrecuentado;
		this.restauranteMenosFrecuentado = restauranteMenosFrecuentado;
	}
	
	
	public String getDia() {
		return Dia;
	}


	public void setDia(String dia) {
		Dia = dia;
	}


	public ProductoDetalle getProductoMasConsumido() {
		return productoMasConsumido;
	}

	public void setProductoMasConsumido(ProductoDetalle productoMasConsumido) {
		this.productoMasConsumido = productoMasConsumido;
	}

	public ProductoDetalle getProductoMenosConsumido() {
		return productoMenosConsumido;
	}

	public void setProductoMenosConsumido(ProductoDetalle productoMenosConsumido) {
		this.productoMenosConsumido = productoMenosConsumido;
	}

	public RestauranteDetalle getRestauranteMasFrecuentado() {
		return restauranteMasFrecuentado;
	}

	public void setRestauranteMasFrecuentado(RestauranteDetalle restauranteMasFrecuentado) {
		this.restauranteMasFrecuentado = restauranteMasFrecuentado;
	}

	public RestauranteDetalle getRestauranteMenosFrecuentado() {
		return restauranteMenosFrecuentado;
	}

	public void setRestauranteMenosFrecuentado(RestauranteDetalle restauranteMenosFrecuentado) {
		this.restauranteMenosFrecuentado = restauranteMenosFrecuentado;
	}
	
	
}
