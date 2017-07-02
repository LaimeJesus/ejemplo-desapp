package exceptions;

public class UserAlreadyExistsException extends Exception {
  
  public UserAlreadyExistsException(){
    super("Username already used");
  }

	/**
	 * 
	 */
	private static final long serialVersionUID = 2368324315553575980L;

}
