package exceptions;


@SuppressWarnings("serial")
public class ProductListDoesNotExistException extends Exception {

	public ProductListDoesNotExistException(String message) {
		super(message);
	}
	
}