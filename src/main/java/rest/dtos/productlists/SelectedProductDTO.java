package rest.dtos.productlists;

import java.util.List;
import java.util.stream.Collectors;

import model.products.SelectedProduct;
import rest.dtos.products.ProductDTO;

public class SelectedProductDTO {
	public Integer quantity;
	public Integer productId;
	public int id;
	public ProductDTO product;
	
	public SelectedProductDTO(){
		
	}
	
	public SelectedProductDTO(SelectedProduct sp) {
		id = sp.getId();
		quantity = sp.getQuantity();
		product = new ProductDTO(sp.getProduct());
	}
	public static List<SelectedProductDTO> createSelectedProducts(List<SelectedProduct> selectedProducts) {
		return selectedProducts.stream().map(x -> new SelectedProductDTO(x)).collect(Collectors.toList());
	}
}
