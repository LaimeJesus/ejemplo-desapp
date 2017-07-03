package rest.dtos.productlists;

import java.util.List;
import java.util.stream.Collectors;

import model.products.ProductList;
import rest.dtos.offers.OfferDTO;
import util.Money;

public class ProductListDTO {

	public int id;
	public String name;
	public Money totalAmount;
	public List<SelectedProductDTO> selectedProducts;
	public List<OfferDTO> appliedOffers;
	
	
	public ProductListDTO(){
		
	}
	public ProductListDTO(ProductList pl) {
		id = pl.getId();
		name = pl.getName();
		totalAmount = pl.getMoneyOfProducts();
		selectedProducts = SelectedProductDTO.createSelectedProducts(pl.getAllProducts());
	}

	public static List<ProductListDTO> createProductLists(List<ProductList> productlists) {
		return productlists.stream().map(x -> new ProductListDTO(x)).collect(Collectors.toList());
	}
	
}
