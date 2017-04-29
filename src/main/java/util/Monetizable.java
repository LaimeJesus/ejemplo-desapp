package util;

public interface Monetizable {
	
	public Money getMonetizableElement();
	
	public void setMonetizableElement(Money newMoney);
	
	public default String getMoneyValue() {
		return this.getMonetizableElement().toString();
	}
	
	public default void setMoneyValue(String newValue) {
		this.setMonetizableElement(Money.toMoney(newValue));
	}
}
