package util;

import exceptions.MoneyCannotSubstractException;

public class Money implements Comparable<Money>{

	private Integer integer;
	private Integer decimal;
	
	
	public Money() {
		this.setInteger(new Integer(0));
		this.setDecimal(new Integer(0));
	}
	
	private void setDecimal(Integer decimal) {
		this.decimal = decimal;
	}

	public Money(Integer integerPart, Integer decimalPart) {
		this.setInteger(integerPart);
		this.setDecimal(decimalPart);
	}

	private void setInteger(Integer integerPart) {
		this.integer = integerPart;
	}

	@Override
	public String toString () {
		return "$" + this.integer.toString() + "," + this.decimal.toString();
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

	public Money add(Money otherMoney) {
		Integer carry = this.calculateCarry(this.getDecimal() + otherMoney.getDecimal());
		Integer decimal = this.calculateCents(this.getDecimal() + otherMoney.getDecimal());
		Integer integer = this.getInteger() + otherMoney.getInteger();
		return new Money(integer + carry, decimal);
	}

	private Integer calculateCents(int aNumber) {
		return aNumber % 100;
	}

	private Integer calculateCarry(Integer aNumber) {
		return aNumber / 100;
	}

	private Integer getInteger() {
		return this.integer;
	}

	private Integer getDecimal() {
		return this.decimal;
	}

	public boolean greaterThan(Money otherMoney){
		return this.compareTo(otherMoney) == 1;
	}
	
	public boolean lesserThan(Money otherMoney){
		return this.compareTo(otherMoney) == -1;
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

	public Money times(Integer times) {
		Integer carry = this.calculateCarry(this.getDecimal()*times);
		Integer decimal = this.calculateCents(this.getDecimal()*times);
		return new Money(this.getInteger() * times + carry, decimal);
	}
	
}
