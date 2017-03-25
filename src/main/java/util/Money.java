package util;

public class Money {

	Integer integer;
	Integer cents;
	
	
	public Money(Integer a , Integer b) {
		this.integer = a;
		this.cents = b;
	}
	
	
	
	public Money plus (Money someValue) {
		Integer newCents = this.calculateCents(this.cents , someValue.cents);
		Integer newInteger = this.calculateCarryCents(this.cents , someValue.cents);
		return new Money((this.integer + someValue.integer + newInteger) , newCents);
	}
	
	public Money times (Integer someQuantity) {
		Integer newCents = this.calculateCents(this.cents * someQuantity , 0);
		Integer newInteger = this.calculateCarryCents(this.cents * someQuantity, 0);
		return new Money((this.integer * someQuantity) + newInteger , newCents);
		
	}
	
	public Integer calculateCents(Integer someCents , Integer otherCents) {
		return (someCents + otherCents) % 100;
	}
	
	public Integer calculateCarryCents(Integer someCents , Integer otherCents) {
		return ((someCents + otherCents) / 100);
	}
	
	public void setInteger(Integer newInteger) {
		this.integer = newInteger;
	}
	
	public void setCents(Integer newCents) {
		this.cents = newCents;
	}
	
	@Override
	public String toString () {
		return "$" + this.integer.toString() + "," + this.cents.toString();
	}
	
	@Override
	public boolean equals( Object anotherMoney ) {
		if (anotherMoney != null && anotherMoney instanceof Money) {
			Money newMoney = (Money) anotherMoney;
			return this.equals(newMoney);
		}
		return false;
	}
	
	public boolean equals( Money someMoney ) {
		return (this.integer.equals(someMoney.integer) && this.cents.equals(someMoney.cents));
	}
	
}
