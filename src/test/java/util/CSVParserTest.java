package model;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import exceptions.InvalidArgumentsException;
import exceptions.InvalidCategoryException;
import exceptions.InvalidMoneyException;
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
	public void testParsingACSVFileWith1ProductsReturnsAListWith1Products() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "nameOne,brandOne,stockOne,1.0,Meat";
		List<Product> products = csvparser.toListObject(csvparser.parse(productCSV));
		Assert.assertNotNull(products);
		Assert.assertEquals(1, products.size());
	}

	
	@Test
	public void testParsingACSVFileWith5ProductsReturnsAListWith5Products() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "nameOne,brandOne,stockOne,1.0,Meat\n"
				+ "nameTwo,brandTwo,stockTwo,2.0,Meat\n"
				+ "nameThree,brandThree,stockThree,3.0,Fruit\n"
				+ "nameFour,brandFour,stockFour,4.0,Vegetable\n"
				+ "nameFive,brandFive,stockFive,5.0,Drink\n";
		List<Product> products = csvparser.toListObject(csvparser.parse(productCSV));
		Assert.assertNotNull(products);
		Assert.assertEquals(5, products.size());
	}
	
	
	@Test(expected=InvalidArgumentsException.class)
	public void testParsingACSVFileWithoutSomeValueThrowsAnException() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "nameOne,brandOne,stockOne,1.0";
		
		csvparser.toListObject(csvparser.parse(productCSV));
	}	

	@Test(expected=InvalidCategoryException.class)
	public void testParsingACSVFileContainingACategoryThatDoesnotExistsThrowsAnException() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "nameOne,brandOne,stockOne,0.0,Meal";
		
		csvparser.toListObject(csvparser.parse(productCSV));
	}
	
	@Test(expected=InvalidMoneyException.class)
	public void testMoneyInvalidCaseOnlyIntegerPart() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "nameOne,brandOne,stockOne,1,Meal";
		
		csvparser.toListObject(csvparser.parse(productCSV));
	}
	
	@Test(expected=InvalidMoneyException.class)
	public void testMoneyInvalidCaseOnlyDecimalPart() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "nameOne,brandOne,stockOne,.1,Meal";
		
		csvparser.toListObject(csvparser.parse(productCSV));
	}
	
}
