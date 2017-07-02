package exceptions;

public class ProductNotExistException extends Exception {

	public ProductNotExistException(String msg) {
		super(msg);
	}
	
	public ProductNotExistException() {
    super("Product does not exist");
  }

	/**
	 * 
	 */
	private static final long serialVersionUID = -295416680901765144L;

}
