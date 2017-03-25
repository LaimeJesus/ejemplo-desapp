package exceptions;

@SuppressWarnings("serial")
public class ProductDoesNotExistOnListException extends Exception {
	
	public ProductDoesNotExistOnListException(String message) {
		super(message);
	}
}
