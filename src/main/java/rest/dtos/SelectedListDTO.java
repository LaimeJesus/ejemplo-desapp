package rest.dtos;

public class SelectedListDTO {
	private String username;
	private String prodListName;
	private Integer idProd;
	private Integer quantity;
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
