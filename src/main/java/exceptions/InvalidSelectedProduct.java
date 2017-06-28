package exceptions;

public class InvalidSelectedProduct extends Exception {

	public InvalidSelectedProduct(String msg) {
		super(msg);
	}

	public InvalidSelectedProduct(){
		super("Product can not being selected");
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1910554878396011544L;

}
