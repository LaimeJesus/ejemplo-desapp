package exceptions;

public class SelectedProductNotExistException extends Exception {

	public SelectedProductNotExistException(String msg) {
		super(msg);
	}
	
	public SelectedProductNotExistException() {
    super("Product does not exist");
  }

	/**
	 * 
	 */
	private static final long serialVersionUID = -7072313502947264104L;

}
