package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoneyTest {

	@Test
	public void testWhenAMultiplicationOcurresThenTheCalculusIsCorrect() {
		
		Money someMoney = new Money(3,50); 
		
		Money result = someMoney.times(4);
		
		assertEquals(result, new Money(14,0));
		
	}

}
