package rest.dummys;

import java.util.ArrayList;

import model.products.ProductList;

public class DummyLists {

	
	public ArrayList<ProductList> example() {
		ArrayList<ProductList> someNames = new ArrayList<ProductList>();
		someNames.add(new ProductList("Cumple de Chizu"));
		someNames.add(new ProductList("Fulbo del domingo"));
		someNames.add(new ProductList("Final de la champions"));
		someNames.add(new ProductList("sinespacios"));
		return someNames;
	}
	
}
