package exceptions;

@SuppressWarnings("serial")
public class MoneyCannotSubstractException extends Exception {
  public MoneyCannotSubstractException(){
    super("Money can not be negative");
  }
}
