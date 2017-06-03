package rest.dtos;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import model.products.ProductList;
import model.products.SelectedProduct;

public class ListDetailDTO {

	public List<SelectionDTO> selections;
	public String totalAmount;
	public String pubDate;
	
	public ListDetailDTO( ProductList list ) {
		selections = new ArrayList<SelectionDTO>();
		toSelections(list.allProducts);
		totalAmount = list.getTotalAmount().toString();
		pubDate = DateTime.now().toString();
	}
	
	public void toSelections(List<SelectedProduct> selected) {
		for (SelectedProduct sp : selected) {
			SelectionDTO selection = new SelectionDTO(sp.getProduct().getName() , sp.getQuantity());
			selections.add(selection);
		}
	}
	
}
