package exceptions;

public class UserDoesNotExistException extends Exception {

	public UserDoesNotExistException(String msg) {
		super(msg);
	}
	
	public UserDoesNotExistException(){
		super("User does not exist");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1398946747507327275L;

}
