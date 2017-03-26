package util;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.MoneyCannotSubstractException;

public class MoneyTest {

	@Test
	public void testWhenICreateANewMoneyItHasZeroDollars(){
		Money newMoney = new Money();
		Money anotherMoney = new Money(0,0);
		
		assertEquals(anotherMoney, newMoney);
	}
	
	@Test
	public void testTwoMoneyObjectCanBeAdded(){
		Money someMoney = new Money(1,0);
		Money otherMoney = new Money(4,0);
		
		Money expected = new Money(5,0);
		Money actual = someMoney.add(otherMoney);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBorderCaseAddingZeroMoneyReturnsTheOtherMoney(){
		Money zeroMoney = new Money();
		Money otherMoney = new Money(5,0);
	
		Money expected = otherMoney;
		Money actual = zeroMoney.add(otherMoney);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBorderCaseAddingTwoMoneyWhenTheSumOfItsDecimalPartisGreaterThanOrEqual100ThenItAdds1ToIntegerPart(){
		Money newMoney = new Money(1,40);
		Money anotherMoney = new Money(0,60);
		
		Money expected = new Money(2,0);
		Money actual = newMoney.add(anotherMoney);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testAMoneyThatIsGreaterThanAnotherMoneyCanBeSubstracted() throws MoneyCannotSubstractException{
		Money greaterMoney = new Money(5,0);
		Money lesserMoney = new Money(4,0);
		
		Money expected = new Money(1,0);
		Money actual = greaterMoney.minus(lesserMoney);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testWhenAMultiplicationOcurresThenTheCalculusIsCorrect() {
		
		Money someMoney = new Money(3,50); 
		
		Money expected = new Money(14,0);
		Money actual = someMoney.times(4);
		
		assertEquals(expected, actual);
		
	}
	
}
