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
	public void testAMoneyCanBeSubstractedForLesserMoney() throws MoneyCannotSubstractException{
		Money greaterMoney = new Money(5,0);
		Money lesserMoney = new Money(4,0);
		
		Money expected = new Money(1,0);
		Money actual = greaterMoney.minus(lesserMoney);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGivenTwoMoneythatAreEqualsThenSubstractsThemThenReturnsZeroMoney() throws MoneyCannotSubstractException{
		Money aMoney = new Money(10,0);
		
		Money expected = new Money(0,0);
		Money actual = aMoney.minus(aMoney);
		
		assertEquals(expected, actual);
		
	}
	
	@Test(expected=MoneyCannotSubstractException.class)
	public void testMoneyCannotBeSubstractedForGreaterMoneyItThrowsMoneyCannotSubstractException() throws MoneyCannotSubstractException{
		Money lesserMoney = new Money(10,0);
		Money greaterMoney = new Money(20,0);
		
		lesserMoney.minus(greaterMoney);
	}
	
	@Test
	public void testMoneyCanBeMultipliedForAnInteger() {
		
		Money someMoney = new Money(3,50); 
		
		Money expected = new Money(14,0);
		Money actual = someMoney.times(4);
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testPercentajeWithoutRoundingOfAMoreThan2DigitsMoney() {
		
		Money aPrice = new Money(12345,0);
		
		Money expected = new Money(12221,55);
		
		assertEquals(expected,aPrice.percentage(99));
		
	}
	
	@Test
	public void testPercentajeWithRoundingOfAMoreThan2DigitsMoney() {
		
		Money aPrice = new Money(345,87);
		
		Money expected = new Money(10,38);
		
		assertEquals(expected,aPrice.percentage(3));
		
	}
	
	@Test
	public void testPercentajeWithoutRoundingOfA2DigitsMoney() {
		
		Money aPrice = new Money(12,13);
		
		Money expected = new Money(1,20);
		
		assertEquals(expected,aPrice.percentage(10));
		
	}
	
	@Test
	public void testPercentajeWithRoundingOfA2DigitsMoney() {
		
		Money aPrice = new Money(24,67);
		
		Money expected = new Money(6,25);
		
		assertEquals(expected,aPrice.percentage(25));
		
	}
	
	@Test
	public void testPercentajeWithoutRoundingOfA1DigitMoney() {
		
		Money aPrice = new Money(2,10);
		
		Money expected = new Money(1,74);
		
		assertEquals(expected,aPrice.percentage(87));
		
	}
	
	@Test
	public void testPercentajeWithRoundingOfA1DigitMoney() {
		
		Money aPrice = new Money(6,97);
		
		Money expected = new Money(4,27);
		
		assertEquals(expected,aPrice.percentage(61));
		
	}
	
}
