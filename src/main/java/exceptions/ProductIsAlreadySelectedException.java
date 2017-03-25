package exceptions;

@SuppressWarnings("serial")
public class ProductIsAlreadySelectedException extends Exception {
	
	public ProductIsAlreadySelectedException(String message) {
		super(message);
	}

}
