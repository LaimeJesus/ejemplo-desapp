package exceptions;

public class ProductAlreadyCreatedException extends Exception {

  public ProductAlreadyCreatedException(){
    super("Product already exists");
  }
	/**
	 * 
	 */
	private static final long serialVersionUID = 3669054992606171466L;

}
