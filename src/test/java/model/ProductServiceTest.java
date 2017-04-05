package model;

import java.util.List;

import org.junit.Test;

public class ProductServiceTest {
	
	@Test
	public void testParsingACSVFileWith10ProductsReturnsAListWith10Products(){
		CSVParser<Product> csvparser = new CSVParser<Product>();
		String productMatrix = "nameOne,brandOne,stockOne,1.0";
		List<Product> products = csvparser.parse(productMatrix);
	}

}
