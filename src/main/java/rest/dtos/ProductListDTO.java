package rest.dtos;

public class ProductListDTO {

	private String username;
	private String prodListName;
	private Integer idProd;
	private Integer quantity;
	
	public ProductListDTO() {
		
	}
	
	public ProductListDTO(String username , String prodListName , Integer idProd , Integer quantity) {
		this.setUsername(username);
		this.setProdListName(prodListName);
		this.setIdProd(idProd);
		this.setQuantity(quantity);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProdListName() {
		return prodListName;
	}

	public void setProdListName(String prodListName) {
		this.prodListName = prodListName;
	}

	public Integer getIdProd() {
		return idProd;
	}

	public void setIdProd(Integer idProd) {
		this.idProd = idProd;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
