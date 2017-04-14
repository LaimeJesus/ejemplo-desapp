package exceptions;


@SuppressWarnings("serial")
public class ProductListAlreadyCreatedException extends Exception {

	public ProductListAlreadyCreatedException(String message) {
		super(message);
	}

	public ProductListAlreadyCreatedException() {
	}
	
}
