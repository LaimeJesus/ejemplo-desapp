package util;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import exceptions.InvalidArgumentsException;
import exceptions.InvalidCategoryException;
import exceptions.InvalidDurationException;
import exceptions.InvalidMoneyException;
import model.products.Product;
import util.CSVProductParser;

public class CSVParserTest {
	
	@Test
	public void testParsingAnEmptyCSVFileReturnsAnEmptyList() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String emptyCSV = "";
		List<Product> products = csvparser.toListObject(csvparser.parse(emptyCSV));
		Assert.assertNotNull(products);
		Assert.assertEquals(0, products.size());
	}

	@Test
	public void testParsingACSVFileWith1ProductsReturnsAListWith1Product() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "1,nameOne,brandOne,1,1.0,Meat,1,urlOne";
		List<Product> products = csvparser.toListObject(csvparser.parse(productCSV));
		Assert.assertNotNull(products);
		Assert.assertEquals(1, products.size());
		
		Product aProduct = products.get(0);
		
		Assert.assertEquals(new Integer(1), aProduct.getId());
		Assert.assertEquals("nameOne", aProduct.getName());
		Assert.assertEquals("brandOne", aProduct.getBrand());
		Assert.assertEquals(new Integer(1), aProduct.getStock());
		Assert.assertEquals(new Money(1,0), aProduct.getPrice());
		Assert.assertEquals(Category.Meat, aProduct.getCategory());
		Assert.assertEquals(1L, aProduct.getProcessingTime().getMillis());
		Assert.assertEquals("urlOne", aProduct.getImageUrl());
	
	}

	
	@Test
	public void testParsingACSVFileWith5ProductsReturnsAListWith5Products() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "1,nameOne,brandOne,1,1.0,Meat,1,urlOne\n"
				+ "2,nameTwo,brandTwo,1,2.0,Meat,2,urlTwo\n"
				+ "3,nameThree,brandThree,1,3.0,Fruit,3,urlThree\n"
				+ "4,nameFour,brandFour,1,4.0,Vegetable,4,urlFour\n"
				+ "5,nameFive,brandFive,1,5.0,Drink,5,urlFive\n";
		List<Product> products = csvparser.toListObject(csvparser.parse(productCSV));
		Assert.assertNotNull(products);
		Assert.assertEquals(5, products.size());
	}
	
	
	@Test(expected=InvalidArgumentsException.class)
	public void testParsingACSVFileWithoutSomeValueThrowsAnException() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "nameOne,brandOne,1,1.0";
		
		csvparser.toListObject(csvparser.parse(productCSV));
	}	

	@Test(expected=InvalidCategoryException.class)
	public void testParsingACSVFileContainingACategoryThatDoesnotExistsThrowsAnException() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "1,nameOne,brandOne,1,0.0,Meal,1,urlOne";
		
		csvparser.toListObject(csvparser.parse(productCSV));
	}
	
	@Test(expected=InvalidMoneyException.class)
	public void testMoneyInvalidCaseOnlyIntegerPart() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "1,nameOne,brandOne,1,1,Meat,1,urlOne";
		
		csvparser.toListObject(csvparser.parse(productCSV));
	}
	
	@Test(expected=InvalidMoneyException.class)
	public void testMoneyInvalidCaseOnlyDecimalPart() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "1,nameOne,brandOne,1,.1,Meat,1,urlOne";
		
		csvparser.toListObject(csvparser.parse(productCSV));
	}
	
	@Test(expected=InvalidDurationException.class)
	public void testDurationInvalid() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "1,nameOne,brandOne,1,1.1,Meat,e,urlOne";
		csvparser.toListObject(csvparser.parse(productCSV));				
	}
	
}
