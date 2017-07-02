package exceptions;

@SuppressWarnings("serial")
public class ProductDoesNotExistOnListException extends Exception {
	
	public ProductDoesNotExistOnListException(String message) {
		super(message);
	}
	
	public ProductDoesNotExistOnListException() {
    super("Product is not selected on that list");
  }
	
}
