package util;

import exceptions.MoneyCannotSubstractException;

public class Money extends Entity implements Comparable<Money>{

	private Integer integer;
	private Integer decimal;
	
	
	public Money() {
		this.setInteger(new Integer(0));
		this.setDecimal(new Integer(0));
	}
	
	public Money(Integer integerPart, Integer decimalPart) {
		this.setInteger(integerPart);
		this.setDecimal(decimalPart);
	}

	
	
	private void setDecimal(Integer decimal) {
		this.decimal = decimal;
	}

	private void setInteger(Integer integerPart) {
		this.integer = integerPart;
	}
	
	private Integer getInteger() {
		return this.integer;
	}
	
	private Integer getDecimal() {
		return this.decimal;
	}

	public Money add(Money otherMoney) {
		Integer carry = this.calculateCarry(this.getDecimal() + otherMoney.getDecimal());
		Integer decimal = this.calculateCents(this.getDecimal() + otherMoney.getDecimal());
		Integer integer = this.getInteger() + otherMoney.getInteger();
		return new Money(integer + carry, decimal);
	}
	
	public Money times(Integer times) {
		Integer carry = this.calculateCarry(this.getDecimal()*times);
		Integer decimal = this.calculateCents(this.getDecimal()*times);
		return new Money(this.getInteger() * times + carry, decimal);
	}
	
	public Money minus(Money otherMoney) throws MoneyCannotSubstractException {
		if(this.greaterThan(otherMoney) || this.equals(otherMoney)){
			Integer newInteger = this.getInteger() - otherMoney.getInteger();
			Integer newDecimal = this.getDecimal() - otherMoney.getDecimal();
			Integer carry = 0;
			if(newDecimal < 0){
				carry += 1;
				newDecimal = 100 - otherMoney.getDecimal();
			}
			return new Money(newInteger - carry, newDecimal);
		}
		throw new MoneyCannotSubstractException();
	}
	 
	public Money percentage(Integer aPercentage) {
		return this.times(aPercentage).divideBy100();
	}
	
	private Money divideBy100() {
		String parsedInteger = this.getInteger().toString();
		Integer lenght = parsedInteger.length();
		Integer realInteger = 0;
		Integer realDecimal = 0;
		if (lenght > 2) {
			String newInteger = parsedInteger.substring(0,lenght - 2);
			String newDecimal = parsedInteger.substring(lenght - 2 , lenght);
			realInteger = Integer.parseInt(newInteger);
			realDecimal = Integer.parseInt(newDecimal);	
		} else {
			if (lenght == 2) {
				String newDecimal = parsedInteger;
				realDecimal = Integer.parseInt(newDecimal);
			} else {
				String newDecimal = "0" + parsedInteger;
				realDecimal = Integer.parseInt(newDecimal);
			}
		}
		if (this.getDecimal() > 50) {
			realDecimal++;
			if (realDecimal == 100) {
				realDecimal = 0;
				realInteger++;
			}
		}
		return new Money(realInteger,realDecimal);
	}

	@Override
	public String toString () {
		return this.integer.toString() + "." + this.decimal.toString();
	}
	
	@Override
	public boolean equals(Object anotherMoney ) {
		if(this.isMyType(anotherMoney)){
			Money newMoney = (Money) anotherMoney;
			return this.equals(newMoney);
		}
		return false;
	}
	
	private boolean isMyType(Object anotherMoney) {
		return anotherMoney != null && anotherMoney instanceof Money;
	}

	public boolean equals( Money someMoney ) {
		return this.compareTo(someMoney) == 0;
	}


	private Integer calculateCents(int aNumber) {
		return aNumber % 100;
	}
	
	private Integer calculateCarry(Integer aNumber) {
		return aNumber / 100;
	}
	
	public boolean greaterThan(Money otherMoney){
		return this.compareTo(otherMoney) == 1;
	}
	
	public boolean lesserThan(Money otherMoney){
		return this.compareTo(otherMoney) == -1;
	}
	
	@Override
	public int compareTo(Money otherMoney) {
		int integerGreaterThan = this.getInteger().compareTo(otherMoney.getInteger());
		int decimalGreaterThan = this.getDecimal().compareTo(otherMoney.getDecimal());
		if(integerGreaterThan == 1 || (integerGreaterThan == 0 && decimalGreaterThan == 1)){
			return 1;
		}
		else {
			if(integerGreaterThan == 0 && decimalGreaterThan == 0){
				return 0;
			}
			else{
				return -1;
			}
		}
	}

	public static Money toMoney(String aStringMoney) {
		String current = "";
		Integer integer = null;
		Integer decimal = null;
		for(char c : aStringMoney.toCharArray()){
			if(c=='.'){
				integer = Integer.parseInt(current);
				current = "";
			}else{
				current+=c;
			}
		}
		decimal = Integer.parseInt(current);
		return new Money(integer, decimal);
	}
	
	public static boolean isValid(String aMoney) {

		if(aMoney.isEmpty()){
			return false;
		}
		int indexOfDot = aMoney.indexOf('.');
		if(indexOfDot > 0 && aMoney.length() - 1 != indexOfDot){
			String integer = aMoney.substring(0, indexOfDot);
			String decimal = aMoney.substring(indexOfDot+1);				
			boolean stringValid = !integer.isEmpty() && !decimal.isEmpty() && decimal.length() < 3;
			boolean moneyValid = integer.matches("[0-9]+") && decimal.matches("[0-9]+");
			return stringValid && moneyValid;
		}
		else{
			return false;
		}
	}

	public Money divideBy(int dividing) {
		
		Money res = this.round();
		res.setInteger(this.integer/dividing);
		return res;
	}

	public Money round() {
		if(this.decimal > 50){
			return new Money(this.integer + 1, 0);
		}
		else{
			return new Money(this.integer, 0);
		}
	}
		
}
